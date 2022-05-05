package hrd

import (
	"fmt"
)

//常量，用于标记唯一值，及位移索引
const (
	BING4_ID = iota
	BING3_ID
	BING2_ID
	BING1_ID
	HUANG_ZHONG_ID
	MA_CHAO_ID
	ZHAO_YUN_ID
	ZHANG_FEI_ID
	GUAN_YU_ID
	CAO_CAO_ID
)
const (
	CAO_CAO = iota
	GUAN_YU
	GUAN_YU2
	ZHANG_FEI
	ZHANG_FEI2
	ZHAO_YUN
	ZHAO_YUN2
	MA_CHAO
	MA_CHAO2
	HUANG_ZHONG
	HUANG_ZHONG2
	BING1
	BING2
	BING3
	BING4
)

//棋子
type ChessmanType struct {
	id     int8
	width  int8
	height int8
}

//翻译
func (c ChessmanType) translate() string {
	var result = ""
	switch c.id {
	case BING4_ID:
		result = "BING4"
	case BING3_ID:
		result = "BING3"
	case BING2_ID:
		result = "BING2"
	case BING1_ID:
		result = "BING1"
	case HUANG_ZHONG_ID:
		result = "HUANG_ZHONG"
	case MA_CHAO_ID:
		result = "MA_CHAO"
	case ZHAO_YUN_ID:
		result = "ZHAO_YUN"
	case ZHANG_FEI_ID:
		result = "ZHANG_FEI"
	case GUAN_YU_ID:
		result = "GUAN_YU"
	case CAO_CAO_ID:
		result = "CAO_CAO"
	}
	return result
}

//转字符串
func (c ChessmanType) String() string {
	return fmt.Sprintf("name:%s id:%d width:%d height:%d", c.translate(), c.id, c.width, c.height)
}

//带有坐标的棋子
type Chessman struct {
	X, Y int8
	ChessmanType
}

//计算位置
func (c Chessman) HashCode() uint64 {
	pos := uint64((c.X << 3) + c.Y)
	return pos << (c.id*5 + 5)
}

//实现排序相关
//获取自身的值
func (c Chessman) Value() int {
	result := 0
	if c.width == 2 {
		result |= 2
	}
	if c.height == 2 {
		result |= 1
	}
	return result<<5 | int(c.X)<<3 | int(c.Y)
}

//字符串
func (c Chessman) String() string {
	return fmt.Sprintf("%s X: %d Y: %d", c.ChessmanType, c.X, c.Y)
}

type ChessDic map[int]ChessmanType

var CHESSMAN = ChessDic{
	CAO_CAO:      {id: CAO_CAO_ID, width: 2, height: 2},
	GUAN_YU:      {id: GUAN_YU_ID, width: 2, height: 1},
	GUAN_YU2:     {id: GUAN_YU_ID, width: 1, height: 2},
	ZHANG_FEI:    {id: ZHANG_FEI_ID, width: 2, height: 1},
	ZHANG_FEI2:   {id: ZHANG_FEI_ID, width: 1, height: 2},
	ZHAO_YUN:     {id: ZHAO_YUN_ID, width: 2, height: 1},
	ZHAO_YUN2:    {id: ZHAO_YUN_ID, width: 1, height: 2},
	MA_CHAO:      {id: MA_CHAO_ID, width: 2, height: 1},
	MA_CHAO2:     {id: MA_CHAO_ID, width: 1, height: 2},
	HUANG_ZHONG:  {id: HUANG_ZHONG_ID, width: 2, height: 1},
	HUANG_ZHONG2: {id: HUANG_ZHONG_ID, width: 1, height: 2},
	BING1:        {id: BING1_ID, width: 1, height: 1},
	BING2:        {id: BING2_ID, width: 1, height: 1},
	BING3:        {id: BING3_ID, width: 1, height: 1},
	BING4:        {id: BING4_ID, width: 1, height: 1},
}

func (cd ChessDic) GetChess(id int8, dir bool) ChessmanType {
	switch id {
	case BING4_ID:
		return CHESSMAN[BING4]
	case BING3_ID:
		return CHESSMAN[BING3]
	case BING2_ID:
		return CHESSMAN[BING2]
	case BING1_ID:
		return CHESSMAN[BING1]
	case HUANG_ZHONG_ID:
		if dir {
			return CHESSMAN[HUANG_ZHONG]
		} else {
			return CHESSMAN[HUANG_ZHONG2]
		}
	case MA_CHAO_ID:
		if dir {
			return CHESSMAN[MA_CHAO]
		} else {
			return CHESSMAN[MA_CHAO2]
		}
	case ZHAO_YUN_ID:
		if dir {
			return CHESSMAN[ZHAO_YUN]
		} else {
			return CHESSMAN[ZHAO_YUN2]
		}
	case ZHANG_FEI_ID:
		if dir {
			return CHESSMAN[ZHANG_FEI]
		} else {
			return CHESSMAN[ZHANG_FEI2]
		}
	case GUAN_YU_ID:
		if dir {
			return CHESSMAN[GUAN_YU]
		} else {
			return CHESSMAN[GUAN_YU2]
		}
	case CAO_CAO_ID:
		return CHESSMAN[CAO_CAO]
	}
	panic("id 错误!")
}
