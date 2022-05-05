package hrd

import (
	"fmt"
	"sort"
	"strings"
)

type chessbox []Chessman

type Chessboard struct {
	Chessmans chessbox
	State     uint64
	mirror    uint64
	adjMirr   uint64
}

//排序相关
func (cb chessbox) Len() int {
	return len(cb)
}

//交换
func (cb chessbox) Swap(i, j int) {
	cb[i], cb[j] = cb[j], cb[i]
}

//比较
func (cb chessbox) Less(i, j int) bool {
	return cb[i].Value() > cb[j].Value()
}

//复制
func (cb chessbox) clone() chessbox {
	result := make([]Chessman, 10)
	copy(result, cb)
	return result
}

//棋盘的64位存储情况（宽度为5位）
//平面直角坐标系，X轴正方向2位，Y轴负方向3位
//曹 关 张 赵 马 黄 兵1 兵2 兵3 兵4 横竖情况
func CreateChessboard(state uint64) *Chessboard {
	var result = &Chessboard{Chessmans: make([]Chessman, 10), State: state}
	types := [10]bool{}
	tempstate := state
	for i := 4; i < 9; i++ {
		types[i] = tempstate&0x1 == 1
		tempstate >>= 1
	}
	for i := 0; i < 10; i++ {
		y := tempstate & 0x7
		tempstate >>= 3
		x := tempstate & 0x3
		tempstate >>= 2
		result.Chessmans[i] = Chessman{X: int8(x), Y: int8(y), ChessmanType: CHESSMAN.GetChess(int8(i), types[i])}
	}
	return result
}

//棋盘唯一值
func (c Chessboard) Hashcode() uint64 {
	if c.State != 0 {
		return c.State
	}
	result := uint64(0)
	for i := 4; i < 9; i++ {
		if c.Chessmans[i].height == 1 {
			result |= 1 << (i - 4)
		}
	}

	for _, v := range c.Chessmans {
		result |= v.HashCode()
	}
	c.State = result
	return result
}

//计算镜像
func calculateMirror(chessmans *chessbox) (result uint64) {
	sort.Sort(chessmans)
	for _, chessman := range *chessmans {
		result = result<<5 | uint64(chessman.X)<<3 | uint64(chessman.Y)
	}
	return result
}

//当前棋局镜像
func (c Chessboard) GetMirror() uint64 {
	if c.mirror != 0 {
		return c.mirror
	}
	cb := c.Chessmans.clone()
	c.mirror = calculateMirror(&cb)
	return c.mirror
}

//棋局对称映像
//纵向中心对称
func (c Chessboard) GetAdjMirr() uint64 {
	if c.adjMirr != 0 {
		return c.adjMirr
	}
	cbc := c.Chessmans.clone()
	for index := range cbc {
		cbc[index].X = 4 - cbc[index].width - cbc[index].X
	}
	c.adjMirr = calculateMirror(&cbc)
	return c.adjMirr
}

//展示矩阵
func (c Chessboard) getMatrix() (result [5][4]rune) {
	for _, chessman := range c.Chessmans {
		for i := chessman.Y; i < chessman.Y+chessman.height; i++ {
			for j := chessman.X; j < chessman.X+chessman.width; j++ {
				result[i][j] = rune((9 - chessman.id) + 'a')
			}
		}
	}
	return result
}

//转字符串显示
func (c Chessboard) String() string {
	builder := strings.Builder{}
	arr := c.getMatrix()

	line := "-----------------------------------------\n"
	builder.WriteString(line)
	for _, line := range arr {
		builder.WriteRune('|')
		builder.WriteRune('\t')
		for _, c := range line {
			builder.WriteRune(c)
			builder.WriteRune('\t')
		}
		builder.WriteRune('|')
		builder.WriteRune('\n')
	}
	builder.WriteString(line)
	builder.WriteString(fmt.Sprintf("状态:%d\n", c.State))
	builder.WriteString(fmt.Sprintf("镜像:%d\n", c.GetMirror()))
	builder.WriteString(fmt.Sprintf("对称镜像:%d\n", c.GetAdjMirr()))
	return builder.String()
}
