package hrd

import "time"

type Tree struct {
	Root         TreeNode
	ResultsChain []uint64
}

func (t Tree) ShowTree() {
	t.Calculate()

}

//计算结果
//BFS 分支限界法
func (t Tree) Calculate() {
	startTime := time.Now().UnixNano()

	nodes := map[uint64]bool{}
	nodes[t.Root.GetMirror()] = true
	nodes[t.Root.GetAdjMirr()] = true
	nodeList := []TreeNode{t.Root}
	//层级计算
outter:
	for len(nodeList) > 0 {
		nextNodeList := make([]TreeNode, 0)
		for _, node := range nodeList {
			for _, step := range node.GetAllStep() {
				newNode := node.CreateTreeNodeByStep(step)
				mirror := newNode.GetMirror()
				_, contains := nodes[mirror]
				if !contains {
					if isEndNode(newNode.State) {
						//记录所有状态
						for tempNode := newNode; tempNode.parent != nil; tempNode = *tempNode.parent {
							t.ResultsChain = append(t.ResultsChain, tempNode.State)
						}
						break outter
					}
					nextNodeList = append(nextNodeList, newNode)
					nodes[newNode.GetMirror()] = true
					nodes[newNode.GetAdjMirr()] = true
				}
			}
		}
		nodeList = nextNodeList
	}
	println(t.ResultsChain)
	println(len(t.ResultsChain))
	for i := len(t.ResultsChain) - 1; i >= 0; i-- {
		println((t.ResultsChain)[i])
	}
	endTime := time.Now().UnixNano()
	println(endTime - startTime)
	println((endTime - startTime) / 1e6)
}

//计算结果
//BFS
func (t Tree) calculate() {

	nodes := map[uint64]TreeNode{}
	nodes[t.Root.State] = t.Root
	nodeList := []TreeNode{t.Root}
	//层级计算
outter:
	for len(nodeList) > 0 {
		nextNodeList := make([]TreeNode, 0)
		for _, node := range nodeList {
			for _, step := range node.GetAllStep() {
				newNode := node.CreateTreeNodeByStep(step)
				_, contains := nodes[newNode.State]
				if !contains {
					if isEndNode(newNode.State) {
						//记录所有状态
						for tempNode := newNode; tempNode.parent != nil; tempNode = *tempNode.parent {
							t.ResultsChain = append(t.ResultsChain, tempNode.State)
						}
						break outter
					}
					nextNodeList = append(nextNodeList, newNode)
					nodes[newNode.State] = newNode
				}
			}
		}
		nodeList = nextNodeList
	}
	println(t.ResultsChain)
	println(len(t.ResultsChain))
	for i := len(t.ResultsChain) - 1; i >= 0; i-- {
		println((t.ResultsChain)[i])
	}
}

//评定是否为终止节点
func isEndNode(state uint64) bool {
	return state>>50 == 0xb
}
