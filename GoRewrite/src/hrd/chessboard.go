package hrd

type Chessboard struct {
	Chessmans [10]ChessmanInuse
	State     uint64
	mirror    uint64
	adjMirr   uint64
}

func CreateChessboard(state uint64) *Chessboard {
	var result = &Chessboard{Chessmans: [10]ChessmanInuse{}, State: state}
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
		result.Chessmans[i] = ChessmanInuse{X: uint8(x), Y: uint8(y), Chessman: Chessmans.GetChess(uint8(i), types[i])}
	}
	return result
}

func (c Chessboard) Hashcode() uint64 {
	result := uint64(0)
	for i := 4; i < 9; i++ {
		if Chessmans[i].height == 1 {
			result |= 1 << (i - 4)
		}
	}

	for _, v := range c.Chessmans {
		result |= v.Hash()
	}
	return result
}
