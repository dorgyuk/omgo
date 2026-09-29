package io.github.dorgyuk.omgo.game;


public class RenjuRuleEvaluator {
    
    public MoveEvaluation evaluate(Board board, Position position, Stone stone) {
        Board copy = board.withStone(position, stone);

        // 가로 확인
        int countHorizontal = this.countLine(copy, position, stone, 0, 1);
        if (isWinningCount(stone, countHorizontal)) return MoveEvaluation.WIN;
        
        // 세로 확인
        int countVertical = this.countLine(copy, position, stone, 1, 0);
        if (isWinningCount(stone, countVertical)) return MoveEvaluation.WIN;

        // 우하향 대각 확인
        int countRightDownDiagonal = this.countLine(copy, position, stone, 1, 1);
        if (isWinningCount(stone, countRightDownDiagonal)) return MoveEvaluation.WIN;

        // 우상향 대각 확인
        int countRightUpDiagonal = this.countLine(copy, position, stone, -1, 1);
        if (isWinningCount(stone, countRightUpDiagonal)) return MoveEvaluation.WIN;

        return MoveEvaluation.LEGAL;
    }

    private int countLine (Board copy, Position position, Stone stone, int dr, int dc) {
        int count = 1;

        Position cursor = new Position(position.row() + dr, position.column() + dc);

        while(copy.contains(cursor) && copy.stoneAt(cursor) == stone) {
            count++;
            cursor = new Position(cursor.row() + dr, cursor.column() + dc);
        }

        // 반대 방향
        cursor = new Position(position.row() - dr, position.column() - dc);
        while(copy.contains(cursor) && copy.stoneAt(cursor) == stone) {
            count++;
            cursor = new Position(cursor.row() - dr, cursor.column() - dc);
        }

        return count;
    }

    private boolean isWinningCount(Stone stone, int count) {
        return (stone == Stone.BLACK && count == 5)
            || (stone == Stone.WHITE && count >= 5);
    }
}
