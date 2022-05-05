package hrd

import (
	"time"
)

const (
	THREAD = 8
)

type Tree struct {
	Root TreeNode
}

//多线程求解
func (t Tree) CalculateBy() (result []uint64) {
	startTime := time.Now().UnixNano()
	//记忆已经使用的节点
	nodes := map[uint64]bool{}
	nodes[t.Root.GetMirror()] = true
	nodes[t.Root.GetAdjMirr()] = true
	nodeList := []TreeNode{t.Root}
	//记忆父节点
	mem := map[uint64]TreeNode{}
	doneChan := make(chan struct{}, 0)

	//层级计算
outter:
	for len(nodeList) > 0 {
		outputChan := make(chan TreeNode, 100)
		nextNodeList := make([]TreeNode, 0)
		used := 0
		for start, end, step, remainder := 0, len(nodeList), len(nodeList)/THREAD, len(nodeList)%THREAD; start < end; remainder-- {
			tempend := start + step + 1
			if remainder > 0 {
				tempend++
			}
			if tempend > len(nodeList) {
				tempend = len(nodeList)
			}
			go t.calculateNode(doneChan, nodeList[start:tempend], outputChan)
			start = tempend
			used++
		}
		for newNode := range outputChan {
			if _, ok := <-doneChan; ok {
				used--
				if used == 0 {
					close(outputChan)
				}
			}

			_, contains := nodes[newNode.GetMirror()]
			if !contains {
				mem[newNode.State] = newNode
				if isEndNode(newNode.State) {
					//记录所有状态
					for tempNode := newNode; tempNode.parentState != 0; tempNode = mem[tempNode.parentState] {
						result = append(result, tempNode.State)
					}
					break outter
				}
				nextNodeList = append(nextNodeList, newNode)
				nodes[newNode.GetMirror()] = false
				nodes[newNode.GetAdjMirr()] = false
			}

		}
		nodeList = nextNodeList
	}
	endTime := time.Now().UnixNano()
	println(endTime - startTime)
	println((endTime - startTime) / 1e6)
	return result
}

//计算线程
func (tree Tree) calculateNode(done chan struct{}, input []TreeNode, output chan TreeNode) {

	for _, node := range input {
		for _, step := range node.GetAllStep() {
			children := node.CreateTreeNodeByStep(step)
			children.GetMirror()
			output <- children
		}
	}
	done <- struct{}{}

}

//单线程求解
//计算结果
//BFS 分支限界法
func (t Tree) Calculate() (result []uint64) {
	startTime := time.Now().UnixNano()
	//记忆已经使用的节点
	nodes := map[uint64]bool{}
	nodes[t.Root.GetMirror()] = true
	nodes[t.Root.GetAdjMirr()] = true
	nodeList := []TreeNode{t.Root}
	//记忆父节点
	mem := map[uint64]TreeNode{}
	//层级计算
outter:
	for len(nodeList) > 0 {
		nextNodeList := make([]TreeNode, 0)
		for _, node := range nodeList {
			for _, step := range node.GetAllStep() {
				newNode := node.CreateTreeNodeByStep(step)
				_, contains := nodes[newNode.GetMirror()]
				if !contains {
					mem[newNode.State] = newNode
					if isEndNode(newNode.State) {
						//记录所有状态
						for tempNode := newNode; tempNode.parentState != 0; tempNode = mem[tempNode.parentState] {
							result = append(result, tempNode.State)
						}
						break outter
					}
					nextNodeList = append(nextNodeList, newNode)
					nodes[newNode.GetMirror()] = false
					nodes[newNode.GetAdjMirr()] = false
				}
			}
		}
		nodeList = nextNodeList
	}
	endTime := time.Now().UnixNano()
	println(endTime - startTime)
	println((endTime - startTime) / 1e6)
	return result
}

//展示结果树
func (t Tree) ShowTree() {
	states := t.Calculate()
	println("长度", len(states))
	for index := len(states) - 1; index >= 0; index-- {
		//chessboard := *CreateChessboard(states[index])
		//fmt.Println(chessboard)
	}

}

//计算结果
//BFS
//func (t Tree) calculate() (result []uint64) {
//
//	nodes := map[uint64]TreeNode{}
//	nodes[t.Root.State] = t.Root
//	nodeList := []TreeNode{t.Root}
//	//层级计算
//outter:
//	for len(nodeList) > 0 {
//		nextNodeList := make([]TreeNode, 0)
//		for _, node := range nodeList {
//			for _, step := range node.GetAllStep() {
//				newNode := node.CreateTreeNodeByStep(step)
//				_, contains := nodes[newNode.State]
//				if !contains {
//					if isEndNode(newNode.State) {
//						//记录所有状态
//						for tempNode := newNode; tempNode.parent != nil; tempNode = *tempNode.parent {
//							result = append(result, tempNode.State)
//							fmt.Println(tempNode)
//						}
//						break outter
//					}
//					nextNodeList = append(nextNodeList, newNode)
//					nodes[newNode.State] = newNode
//				}
//			}
//		}
//		nodeList = nextNodeList
//	}
//	return result
//}

//评定是否为终止节点
func isEndNode(state uint64) bool {
	return state>>50 == 0xb
}
