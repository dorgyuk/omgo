package io.github.dorgyuk.omgo.game;

public class Board {
    public static final int SIZE = 15;
    private final Stone[][] board = new Stone[SIZE][SIZE];
    
    /**
     * 모든 칸이 Stone.EMPTY인 15x15 배열을 생성한다.
     */
    public Board() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                this.board[i][j] = Stone.EMPTY;
            }
        }
    }

    /**
     * 주어진 위치의 돌을 반환한다.
     * 
     * @param position 확인할 위치
     * @return 검은 돌이면 Stone.BLACK, 흰 돌이면 Stone.WHITE, 빈 칸이면 Stone.EMPTY
     */
    public Stone stoneAt(Position position) {
        return this.board[position.row()][position.column()];
    }

    /**
     * 해당 좌표가 보드 범위 안에 있는지 확인한다.
     * 
     * @param position 확인할 위치
     * @return 좌표가 보드 범위 내에 있다면 true, 범위를 벗어났다면 false
     */
    public boolean contains(Position position) {
        if (0 <= position.row() && position.row() < SIZE) {
            if (0 <= position.column() && position.column() < SIZE) {
                return true;
            }
        }

        return false;
    }

    /**
     * 원본 오목판을 변경하지 않고, 지정한 위치에 돌을 놓은 새 보드를 반환한다.
     * 
     * @param position 착수할 위치
     * @param stone 놓을 돌의 색깔
     * @return 기존 돌 배치를 유지하면서 지정한 위치에 돌이 놓인 새 보드
     */
    public Board withStone(Position position, Stone stone) {

        // 착수 위치 검사하기
        if (position == null || !this.contains(position)) {
            throw new IllegalArgumentException("정상적인 착수 위치가 아닙니다.");
        }
        if (this.stoneAt(position) != Stone.EMPTY) {
            throw new IllegalArgumentException("이미 놓여진 돌이 있는 자리입니다.");
        }
       
        // 돌 유효성 검사하기
        if (stone == null || stone == Stone.EMPTY) {
            throw new IllegalArgumentException("잘못된 돌입니다");
        }

        // 오목판 복사하기
        Board copy = new Board();
        for (int i = 0; i < SIZE; i++) {
            copy.board[i] = this.board[i].clone();
        }

        // 착수하기
        copy.board[position.row()][position.column()] = stone;

        return copy;
    }
}
