package hrd

import (
	"fmt"
)

type space struct {
	X, Y int8
}

const (
	SP1 = iota
	SP2
	SP12
	SP21
)

type TreeNode struct {
	parentState uint64
	space1      space
	space2      space
	*Chessboard
}

//转字符串
//转字符串显示
func (t TreeNode) String() string {
	return fmt.Sprintf("%s space1: %v space2: %v", t.Chessboard, t.space1, t.space2)
}

//仅供初始化使用，能自动找寻空格位置
func CreateTreeNode(state uint64) TreeNode {
	result := TreeNode{Chessboard: CreateChessboard(state), space1: space{X: -1, Y: -1}, space2: space{X: -1, Y: -1}}
	matrix := result.getMatrix()
outter:
	for i := 0; i < len(matrix); i++ {
		for j := 0; j < len(matrix[i]); j++ {
			if matrix[i][j] == 0 {
				if result.space1.X == -1 {
					result.space1.X = int8(j)
					result.space1.Y = int8(i)
				} else {
					result.space2.X = int8(j)
					result.space2.Y = int8(i)
					break outter
				}
			}
		}
	}
	return result
}

//父类类型和步骤生成子类
func (parent *TreeNode) CreateTreeNodeByStep(step Step) TreeNode {
	childrenState := parent.State
	cover := uint64(0)
	//掩码，用于覆盖
	cover |= 0x1F << (5 * (step.id + 1))
	//获取要移动棋子的坐标
	x, y := childrenState&cover>>(5*(step.id+1))>>3, childrenState&cover>>(5*(step.id+1))%8
	//移动
	disx, disy := step.getDistence()
	//新状态
	childrenState = childrenState&(^cover) | ((x+uint64(disx))<<3+(y+uint64(disy)))<<(5*(step.id+1))
	//新空格
	childrenSpace1 := parent.space1
	childrenSpace2 := parent.space2
	//获取空格移动的方向
	tx, ty := step.getDistence()
	tx, ty = -tx, -ty
	switch step.spaceChanged {
	//不忽略厚度
	case SP1:
		childrenSpace1.X, childrenSpace1.Y = childrenSpace1.X+tx*step.width, childrenSpace1.Y+ty*step.height
	case SP2:
		childrenSpace2.X, childrenSpace2.Y = childrenSpace2.X+tx*step.width, childrenSpace2.Y+ty*step.height
	case SP12:
		childrenSpace1.X, childrenSpace1.Y = childrenSpace1.X+tx*step.width, childrenSpace1.Y+ty*step.height
		childrenSpace2.X, childrenSpace2.Y = childrenSpace2.X+tx*step.width, childrenSpace2.Y+ty*step.height
	default:
		//忽略厚度移动，特殊情况
		childrenSpace1.X, childrenSpace1.Y = childrenSpace1.X+tx, childrenSpace1.Y+ty
		childrenSpace2.X, childrenSpace2.Y = childrenSpace2.X+tx, childrenSpace2.Y+ty
	}

	result := TreeNode{parentState: parent.State, Chessboard: CreateChessboard(childrenState), space1: childrenSpace1, space2: childrenSpace2}
	return result
}

func (t *TreeNode) GetAllStep() (resultSteps []Step) {
	//矩阵
	matrix := t.getMatrix()
	spaces := []space{t.space1, t.space2}

	dirs := [4]int{}
	lenth := [2]int8{}
	//resultSteps := make([]Step, 0)
	spaceChanged := [2]int{SP1, SP2}
	for currentSpace := 0; currentSpace < 2; currentSpace++ {
		for i := 0; i < 4; i++ {
			dirs[i] = spaces[currentSpace].searchChessmanId(&matrix, dir(i*2))
		}
		for i := 0; i < 4; i++ {
			//dirs[i]为当前方向
			current := dirs[i]
			if current < 0 {
				continue
			}
			nowChessman := t.Chessboard.Chessmans[current]
			lenth[0] = nowChessman.width
			lenth[1] = nowChessman.height

			//与当前方向呈直角为1
			if lenth[i%2] == 1 {
				//加入当前方向
				resultSteps = append(resultSteps, Step{dir: dir((2*i + 4) % 8), spaceChanged: spaceChanged[currentSpace], Chessman: nowChessman})
				//与当前方向平行也为1
				if lenth[(i+1)%2] == 1 {
					//同方向移动两步
					if dirs[(i+2)%4] == -1 {
						resultSteps = append(resultSteps, Step{dir: dir((i+2)%4 + 8), spaceChanged: spaceChanged[1-currentSpace], Chessman: nowChessman})

					}
					//判断逆时针45度方向
					if dirs[(i+1)%4] == -1 {
						resultSteps = append(resultSteps, Step{dir: dir((2*i + 3) % 8), spaceChanged: spaceChanged[1-currentSpace], Chessman: nowChessman})
					}
					//判断顺时针45度方向
					if dirs[(i+3)%4] == -1 {
						resultSteps = append(resultSteps, Step{dir: dir((2*i + 5) % 8), spaceChanged: spaceChanged[1-currentSpace], Chessman: nowChessman})
					}
				} else { //与当前平行方向为2
					//同方向移动两步
					if dirs[(i+2)%4] == -1 {
						resultSteps = append(resultSteps, Step{dir: dir((i+2)%4 + 8), spaceChanged: SP21, Chessman: nowChessman})
					}
				}

			} else { //与当前方向呈直角为2
				//判断顺时针90方向
				if dirs[(i+1)%4] == -1 && spaces[1-currentSpace].searchChessmanId(&matrix, dir(2*i)) == current {
					resultSteps = append(resultSteps, Step{dir: dir((2*i + 4) % 8), spaceChanged: SP12, Chessman: nowChessman})
				}
			}
		}
	}
	return resultSteps
}

//获取棋子ID
func (s space) searchChessmanId(matrix *[5][4]rune, d dir) int {
	disx, disy := d.getDistence()
	x, y := disx+s.X, disy+s.Y
	if x >= 0 && x < 4 && y >= 0 && y < 5 {
		if (*matrix)[y][x] == 0 {
			return -1
		}
		return int('j' - (*matrix)[y][x])
	} else {
		return -2
	}

}
