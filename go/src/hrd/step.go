package hrd

import "fmt"

type dir int

type Step struct {
	dir
	spaceChanged int
	ChessmanInuse
}

func (s Step) String() string {
	return fmt.Sprintf("%v  %v  %d", s.ChessmanInuse, s.dir, s.spaceChanged)
}

func (d dir) String() string {
	switch d {
	case UP1:
		return "上1"
	case UPRIGHT:
		return "右上"
	case RIGHT1:
		return "右"
	case DOWNRIGHT:
		return "右下"
	case DOWN1:
		return "下"
	case DONWLEFT:
		return "左下"
	case LEFT1:
		return "左"
	case UPLEFT:
		return "左上"
	case UP2:
		return "上2"
	case LEFT2:
		return "左2"
	case DOWN2:
		return "下2"
	case RIGHT2:
		return "右2"
	default:
		return "错误"
	}
}

func (d dir) getDistence() (x, y int8) {
	switch d {
	case UP1:
		return 0, -1
	case UPRIGHT:
		return 1, -1
	case RIGHT1:
		return 1, 0
	case DOWNRIGHT:
		return 1, 1
	case DOWN1:
		return 0, 1
	case DONWLEFT:
		return -1, 1
	case LEFT1:
		return -1, 0
	case UPLEFT:
		return -1, -1
	case UP2:
		return 0, -2
	case LEFT2:
		return -2, 0
	case DOWN2:
		return 0, 2
	case RIGHT2:
		return 2, 0
	default:
		return 0, 0
	}
}

func (s Step) moveChessman() ChessmanInuse {
	chessmanInuse := s.ChessmanInuse
	disx, disy := s.getDistence()
	chessmanInuse.X += disx
	chessmanInuse.Y += disy
	return chessmanInuse
}

const (
	UP1 = iota
	UPRIGHT
	RIGHT1
	DOWNRIGHT
	DOWN1
	DONWLEFT
	LEFT1
	UPLEFT
	UP2
	RIGHT2
	DOWN2
	LEFT2
)
