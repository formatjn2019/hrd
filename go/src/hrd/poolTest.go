package hrd

import "fmt"

func ChainCreate(id int, ch chan int, result chan string) {
	for val := range ch {
		result <- fmt.Sprintf("%d %d\t", id, val)
	}
}

func ChanCreate(ranges int, ch chan int) {
	for i := 0; i < ranges; i++ {
		ch <- i
	}
}
func UseResult(result chan string) {
	i := 0
	for str := range result {
		print(str)
		i++
		if i%10 == 0 {
			println()
		}
	}
}

func calculate(done chan struct{}, input, output chan int) {
	for {
		select {
		case <-done:
			return
		default:
			tp := <-input
			println(tp)

		}

	}
}

