package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

func createMatrix(n, m int) [][]bool {
	matrix := make([][]bool, n)
	for i := range matrix {
		matrix[i] = make([]bool, m)
	}

	return matrix
}

func addBear(matrix [][]bool, n, m int) {
	rand.Seed(time.Now().UnixNano())
	var (
		randX = rand.Intn(n)
		randY = rand.Intn(m)
	)
	matrix[randX][randY] = true

	fmt.Println("Bear in the", randX, "region.")
}

func findBear(id int, wg *sync.WaitGroup, regions <-chan int, matrix [][]bool) {
	defer wg.Done()

	for region := range regions {
		fmt.Println("Bees group", id, "searching in region", region)

		for i := range matrix[region] {
			time.Sleep(100 * time.Microsecond)

			if matrix[region][i] {
				fmt.Println("Bees group", id, "found the bear in region", region)

				//Remove other regions from channel
				for range regions {
				}

				fmt.Println("Bees group", id, "returned.")
				return
			}
		}

		fmt.Println("Bees group", id, "returned.")
	}
}

func main() {
	n := 10
	m := 100
	beesNum := 4

	matrix := createMatrix(n, m)
	addBear(matrix, n, m)

	regions := make(chan int, n)

	wg := &sync.WaitGroup{}

	//Add all regions to channel
	for i := 0; i < n; i++ {
		regions <- i
	}

	for i := 0; i < beesNum; i++ {
		wg.Add(1)
		go findBear(i, wg, regions, matrix)
	}

	close(regions)
	wg.Wait()
}
