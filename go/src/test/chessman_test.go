package hrd

import (
	"fmt"
	"hrd/hrd"
	"testing"
)

func TestChessMan(t *testing.T) {
	//棋子
	for k, v := range hrd.CHESSMAN {
		fmt.Println(k, v)
	}
	chessman := hrd.CHESSMAN[hrd.CAO_CAO]
	var chess1 = hrd.Chessman{X: 1, Y: 0, ChessmanType: chessman}
	println(chess1.HashCode())
}
