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

func smoker(table *[]bool, id int, smokerCheckingSemaphore, emptyTableSemaphore, resetSemaphore chan bool, waitGroup *sync.WaitGroup) {
	defer waitGroup.Done()
	for {
		<-smokerCheckingSemaphore
		fmt.Println("Smoker with", getString(id), "checks the table")
		if !(*table)[id] {
			time.Sleep(time.Millisecond*1500)
			fmt.Println("Smoker with", getString(id), "smoking the cigarette...")
			time.Sleep(time.Second * 1)
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
			smokerCheckingSemaphore <- true
			<-resetSemaphore
		}
	}
}

func broker(table *[]bool, smokerCheckingSemaphore chan bool, emptyTableSemaphore chan bool, waitGroup *sync.WaitGroup) {
	defer waitGroup.Done()
	for {
		<-emptyTableSemaphore
		var firstComponent, secondComponent = generateTwoComponents()
		(*table)[firstComponent] = true
		(*table)[secondComponent] = true
		fmt.Println("Broker put", getString(firstComponent), "and", getString(secondComponent))
		smokerCheckingSemaphore <- true
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
	var waitGroup sync.WaitGroup
	var table = make([]bool, 3)

	var smokerCheckingSemaphore = make(chan bool, 1)
	var emptyTableSemaphore = make(chan bool, 1)
	var resetSemaphore = make(chan bool)

	emptyTableSemaphore <- true
	waitGroup.Add(1)
	go broker(&table, smokerCheckingSemaphore, emptyTableSemaphore, &waitGroup)

	for i := 0; i < 3; i++ {
		waitGroup.Add(1)
		go smoker(&table, i, smokerCheckingSemaphore, emptyTableSemaphore, resetSemaphore, &waitGroup)
	}
	waitGroup.Wait()
}
