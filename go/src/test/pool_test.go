package hrd

import (
	"hrd/hrd"
	"testing"
	"time"
)

func TestChin(t *testing.T) {
	ch := make(chan int, 10)
	resultChan := make(chan string, 10)
	go hrd.ChanCreate(10000, ch)
	go hrd.ChainCreate(1, ch, resultChan)
	go hrd.ChainCreate(2, ch, resultChan)
	go hrd.UseResult(resultChan)
	time.Sleep(1 * time.Second)
}
