package hrd

import (
	"fmt"
	"hrd/hrd"
	"testing"
)

func TestTreeNode(t *testing.T) {
	//树节点
	treeNode := hrd.CreateTreeNode(9078396738425744)
	fmt.Println(treeNode)
	steps := treeNode.GetAllStep()
	for _, step := range steps {
		fmt.Println(step)
	}

	testChildren := treeNode.CreateTreeNodeByStep(steps[3])
	fmt.Println(testChildren)
	testChildren = treeNode.CreateTreeNodeByStep(steps[3])
	fmt.Println(testChildren)
	for _, step := range steps {
		fmt.Println(step)
		treeNodechildren := treeNode.CreateTreeNodeByStep(step)
		fmt.Println(treeNodechildren)
	}
}