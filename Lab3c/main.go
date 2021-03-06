package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

var smokersNum = 0

func getString(element int) string {
	switch element {
	case 0:
		return "Tabacco"
	case 1:
		return "Paper"
	case 2:
		return "Matches"
	default:
		return "unknown"
	}
}

func smoker(table *[]bool, id int, smokersSemaphore, emptyTableSemaphore, resetSemaphore chan bool, wg *sync.WaitGroup) {
	defer wg.Done()
	for {
		<-smokersSemaphore
		fmt.Println("Smoker with", getString(id), "checks the table")
		if !(*table)[id] {
			fmt.Println("Smoker with", getString(id), "smoking the cigarette...")
			time.Sleep(time.Second * 2)
			for i := range *table {
				(*table)[i] = false
			}

			for i := 0; i < smokersNum; i++ {
				resetSemaphore <- true
			}

			smokersNum = 0
			fmt.Println()
			emptyTableSemaphore <- true
		} else {
			smokersNum++
			smokersSemaphore <- true
			<-resetSemaphore
		}
	}
}

func broker(table *[]bool, smokersSemaphore chan bool, emptyTableSemaphore chan bool, wg *sync.WaitGroup) {
	defer wg.Done()
	for {
		<-emptyTableSemaphore
		var firstComponent, secondComponent = generateTwoComponents()
		(*table)[firstComponent] = true
		(*table)[secondComponent] = true
		fmt.Println("Broker put", getString(firstComponent), "and", getString(secondComponent))
		smokersSemaphore <- true
	}
}

func generateTwoComponents() (int, int) {
	randGenerator := rand.New(rand.NewSource(time.Now().UnixNano()))

	firstComponent := randGenerator.Intn(3)
	secondComponent := randGenerator.Intn(3)

	for firstComponent == secondComponent {
		secondComponent = randGenerator.Intn(3)
	}

	return firstComponent, secondComponent
}

func main() {
	var wg sync.WaitGroup
	var table = make([]bool, 3)

	var smokersSemaphore = make(chan bool, 1)
	var emptyTableSemaphore = make(chan bool, 1)
	var resetSemaphore = make(chan bool)

	emptyTableSemaphore <- true
	wg.Add(1)
	go broker(&table, smokersSemaphore, emptyTableSemaphore, &wg)

	for i := 0; i < 3; i++ {
		wg.Add(1)
		go smoker(&table, i, smokersSemaphore, emptyTableSemaphore, resetSemaphore, &wg)
	}
	wg.Wait()
}
