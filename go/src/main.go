package main

import (
	"fmt"
	"hrd/hrd"
)

func main() {
	for k, v := range hrd.Chessmans {
		fmt.Println(k, v)
	}
	chessman := hrd.Chessmans[hrd.CAO_CAO]
	var chess1 = hrd.ChessmanInuse{X: 1, Y: 0, Chessman: chessman}
	println(chess1.Hash())

	chessboard := hrd.CreateChessboard(9359138341900176)
	for k, v := range chessboard.Chessmans {
		fmt.Printf("%d %64b %s \n", k, v.Hash(), v)
	}
	fmt.Printf("  %64b", chessboard.Hashcode())
}
