package main

import (
	"fmt"
	"hrd/hrd"
)

func main() {
	//棋子
	//for k, v := range hrd.CHESSMANS {
	//	fmt.Println(k, v)
	//}
	//chessman := hrd.CHESSMANS[hrd.CAO_CAO]
	//var chess1 = hrd.ChessmanInuse{X: 1, Y: 0, Chessman: chessman}
	//println(chess1.HashCode())

	//棋盘
	//9359870633857936
	//chessboard := hrd.CreateChessboard(9359870633857936)
	//
	//for k, v := range chessboard.Chessmans {
	//	fmt.Printf("%d %64b\t %s \n", k, v.HashCode(), v)
	//	fmt.Println(v.Value())
	//}
	//fmt.Printf("  %64b\n", chessboard.Hashcode())
	//fmt.Printf("  %64b\n", 9359870633857936)
	//println(chessboard.GetMirror())
	//println(chessboard.GetAdjMirr())
	//fmt.Println(chessboard)
	//fmt.Println(chessboard.Hashcode())

	//树节点
	treeNode := hrd.CreateTreeNode(9078396772798096)
	fmt.Println(treeNode)
	steps := treeNode.GetAllStep()
	for _, step := range steps {
		fmt.Println(step)
	}

	testChildren := treeNode.CreateTreeNodeByStep(steps[3])
	fmt.Println(testChildren)
	//testChildren = hrd.CreateTreeNodeByStep(treeNode, steps[3])
	//fmt.Println(testChildren)
	//for _, step := range steps {
	//	fmt.Println(step)
	//	treeNodechildren := hrd.CreateTreeNodeByStep(treeNode, step)
	//	fmt.Println(treeNodechildren)
	//}

	tree := hrd.Tree{Root: hrd.CreateTreeNode(9359870633857936), ResultsChain: make([]uint64, 0, 200)}
	//tree.Calculate()
	tree.ShowTree()
}
