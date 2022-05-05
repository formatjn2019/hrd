package hrd

import (
	"hrd/hrd"
	"testing"
)

func TestTree(t *testing.T) {
	//建树
	tree := hrd.Tree{Root: hrd.CreateTreeNode(9359870633857936)}
	//tree.Calculate()
	//tree.ShowTree()
	tree.CalculateBy()
}
