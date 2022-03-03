package hrd

import (
	"fmt"
)

// 棋子类型
type Chessmantype struct {
	chessType int8
	width     int8
	height    int8
}

//棋子
type Chessman struct {
	id int8
	Chessmantype
}

//翻译
func (c Chessman) translate() string {
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

func (c Chessman) String() string {
	return fmt.Sprintf("name:%s id:%d width:%d height:%d", c.translate(), c.id, c.width, c.height)
}

//带有坐标的棋子
type ChessmanInuse struct {
	X, Y int8
	*Chessman
}

//计算位置
func (c ChessmanInuse) HashCode() uint64 {
	pos := uint64((c.X << 3) + c.Y)
	return pos << (c.id*5 + 5)
}

//实现排序
func (c ChessmanInuse) Value() int {
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
func (c ChessmanInuse) String() string {
	return fmt.Sprintf("%s X: %d Y: %d", c.Chessman, c.X, c.Y)
}

const (
	CAO_T = iota
	HENG_T
	SHU_T
	BING_T
)

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

type ChessDic map[int]*Chessman

var CHESSMANS = ChessDic{
	CAO_CAO:      {id: CAO_CAO_ID, Chessmantype: Chessmantype{chessType: CAO_T, width: 2, height: 2}},
	GUAN_YU:      {id: GUAN_YU_ID, Chessmantype: Chessmantype{chessType: HENG_T, width: 2, height: 1}},
	GUAN_YU2:     {id: GUAN_YU_ID, Chessmantype: Chessmantype{chessType: SHU_T, width: 1, height: 2}},
	ZHANG_FEI:    {id: ZHANG_FEI_ID, Chessmantype: Chessmantype{chessType: HENG_T, width: 2, height: 1}},
	ZHANG_FEI2:   {id: ZHANG_FEI_ID, Chessmantype: Chessmantype{chessType: SHU_T, width: 1, height: 2}},
	ZHAO_YUN:     {id: ZHAO_YUN_ID, Chessmantype: Chessmantype{chessType: HENG_T, width: 2, height: 1}},
	ZHAO_YUN2:    {id: ZHAO_YUN_ID, Chessmantype: Chessmantype{chessType: SHU_T, width: 1, height: 2}},
	MA_CHAO:      {id: MA_CHAO_ID, Chessmantype: Chessmantype{chessType: HENG_T, width: 2, height: 1}},
	MA_CHAO2:     {id: MA_CHAO_ID, Chessmantype: Chessmantype{chessType: SHU_T, width: 1, height: 2}},
	HUANG_ZHONG:  {id: HUANG_ZHONG_ID, Chessmantype: Chessmantype{chessType: HENG_T, width: 2, height: 1}},
	HUANG_ZHONG2: {id: HUANG_ZHONG_ID, Chessmantype: Chessmantype{chessType: SHU_T, width: 1, height: 2}},
	BING1:        {id: BING1_ID, Chessmantype: Chessmantype{chessType: BING_T, width: 1, height: 1}},
	BING2:        {id: BING2_ID, Chessmantype: Chessmantype{chessType: BING_T, width: 1, height: 1}},
	BING3:        {id: BING3_ID, Chessmantype: Chessmantype{chessType: BING_T, width: 1, height: 1}},
	BING4:        {id: BING4_ID, Chessmantype: Chessmantype{chessType: BING_T, width: 1, height: 1}},
}

func (cd ChessDic) GetChess(id int8, dir bool) *Chessman {
	switch id {
	case BING4_ID:
		return CHESSMANS[BING4]
	case BING3_ID:
		return CHESSMANS[BING3]
	case BING2_ID:
		return CHESSMANS[BING2]
	case BING1_ID:
		return CHESSMANS[BING1]
	case HUANG_ZHONG_ID:
		if dir {
			return CHESSMANS[HUANG_ZHONG]
		} else {
			return CHESSMANS[HUANG_ZHONG2]
		}
	case MA_CHAO_ID:
		if dir {
			return CHESSMANS[MA_CHAO]
		} else {
			return CHESSMANS[MA_CHAO2]
		}
	case ZHAO_YUN_ID:
		if dir {
			return CHESSMANS[ZHAO_YUN]
		} else {
			return CHESSMANS[ZHAO_YUN2]
		}
	case ZHANG_FEI_ID:
		if dir {
			return CHESSMANS[ZHANG_FEI]
		} else {
			return CHESSMANS[ZHANG_FEI2]
		}
	case GUAN_YU_ID:
		if dir {
			return CHESSMANS[GUAN_YU]
		} else {
			return CHESSMANS[GUAN_YU2]
		}
	case CAO_CAO_ID:
		return CHESSMANS[CAO_CAO]
	}
	return nil
}
