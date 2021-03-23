package main

import (
	"fmt"
	"math/rand"
	"os"
	"time"
	"sync"
)

const GARDEN_SIZE = 6
var gardenMatrix [][]bool
var file, _ = os.Create("GardenState.txt")
var mutex sync.RWMutex
var waitGroup sync.WaitGroup

func check(e error) {
	if e != nil {
		panic(e)
	}
}

func toStringBool(value bool) string{
	if value {
		return "1"
	} else {
		return "0"
	}
}

func initializeMatrix() {
	gardenMatrix = make([][]bool, GARDEN_SIZE)

	for i := range gardenMatrix {
		gardenMatrix[i] = make([]bool, GARDEN_SIZE)
	}
}

func toStringMatrix() string{
	matrixString := ""
	for i := 0; i < GARDEN_SIZE; i++ {
		for j := 0; j < GARDEN_SIZE; j++ {
			matrixString += toStringBool(gardenMatrix[i][j])
			matrixString += " "
		}
		matrixString += "\n"
	}
	matrixString += "\n"

	return matrixString
}

func printMatrix() {
	defer waitGroup.Done()
	for{
		mutex.RLock()
		fmt.Print(toStringMatrix())
		time.Sleep(time.Millisecond * 1500)
		mutex.RUnlock()
	}
}

func writeMatrixToFile() {
	defer waitGroup.Done()
	for{
		mutex.RLock()
		_, err := file.WriteString(toStringMatrix())
		check(err)
		_ = file.Sync()

		time.Sleep(time.Millisecond * 2000)
		mutex.RUnlock()
	}
}

func natureMatrix() {
	defer waitGroup.Done()
	for{
		randGenerator := rand.New(rand.NewSource(time.Now().UnixNano()))
		i := randGenerator.Intn(GARDEN_SIZE - 1)
		j := randGenerator.Intn(GARDEN_SIZE - 1)

		mutex.Lock()
		time.Sleep(time.Millisecond * 1000)
		gardenMatrix[i][j] = !gardenMatrix[i][j]
		fmt.Println("Nature changed [", i, j, "] to the value:", toStringBool(gardenMatrix[i][j]))
		mutex.Unlock()
	}
}

func gardenerMatrix() {
	defer waitGroup.Done()
	for{
		var iIndex, jIndex int
		for i := 0; i < GARDEN_SIZE; i++ {
			for j := 0; j < GARDEN_SIZE; j++ {
				if gardenMatrix[i][j] == false {
					iIndex, jIndex = i, j
					mutex.Lock()
					time.Sleep(time.Millisecond * 700)
					gardenMatrix[iIndex][jIndex] = true
					fmt.Println("Gardener changed [", iIndex, jIndex, "] to the value: 1")
					mutex.Unlock()
				}
			}
		}


	}

}

func main() {
	defer file.Close()

	initializeMatrix()
	waitGroup.Add(4)

	go printMatrix()
	go writeMatrixToFile()
	go natureMatrix()
	go gardenerMatrix()

	waitGroup.Wait()
}
