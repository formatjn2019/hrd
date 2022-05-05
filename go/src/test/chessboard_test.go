package hrd

import (
	"fmt"
	"hrd/hrd"
	"testing"
)

func TestChessboard(t *testing.T) {
	//棋盘 9359870633857936
	chessboard := hrd.CreateChessboard(9359870633857936)

	for k, v := range chessboard.Chessmans {
		fmt.Printf("%d %64b\t %s \n", k, v.HashCode(), v)
		fmt.Println(v.Value())
	}
	fmt.Printf("  %64b\n", chessboard.Hashcode())
	fmt.Printf("  %64b\n", 9359870633857936)
	//fmt.Println(chessboard)
	fmt.Println(chessboard.Hashcode())
	println(chessboard.GetMirror())
	//chessboard.Chessmans.Show()
	println(chessboard.GetAdjMirr())
	//chessboard.Chessmans.Show()

	//fmt.Println(chessboard)
	fmt.Println(chessboard.Hashcode())
}
