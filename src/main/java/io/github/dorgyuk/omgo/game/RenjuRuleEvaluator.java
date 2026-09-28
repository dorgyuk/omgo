package io.github.dorgyuk.omgo.game;


public class RenjuRuleEvaluator {
    
    public MoveEvaluation evaluate(Board board, Position position, Stone stone) {
        Board copy = board.withStone(position, stone);

        // 가로 확인
        int countHorizontal = this.countHorizontal(copy, position,stone);
        if (stone == Stone.BLACK && countHorizontal == 5) {
            return MoveEvaluation.WIN;
        }
        if (stone == Stone.WHITE && countHorizontal >= 5) {
            return MoveEvaluation.WIN;
        }
        
        // 세로 확인
        int countVertical = this.countVertical(copy, position, stone);
        if (stone == Stone.BLACK && countVertical == 5) {
            return MoveEvaluation.WIN;
        }
        if (stone == Stone.WHITE && countVertical >= 5) {
            return MoveEvaluation.WIN;
        }

        // 우하향 대각 확인
        int countRightDownDiagonal = this.countRightDownDiagonal(copy, position, stone);
        if (stone == Stone.BLACK && countRightDownDiagonal == 5) {
            return MoveEvaluation.WIN;
        }
        if (stone == Stone.WHITE && countRightDownDiagonal >= 5) {
            return MoveEvaluation.WIN;
        }

        // 우상향 대각 확인
        int countRightUpDiagonal = this.countRightUpDiagonal(copy, position, stone);
        if (stone == Stone.BLACK && countRightUpDiagonal == 5) {
            return MoveEvaluation.WIN;
        }
        if (stone == Stone.WHITE && countRightUpDiagonal >= 5) {
            return MoveEvaluation.WIN;
        }

        return MoveEvaluation.LEGAL;
    }

    private int countHorizontal(Board copy, Position position, Stone stone) {
        int count = 1;

        // 서쪽 진행
        Position cursor = new Position(position.row(), position.column() - 1);

        while (copy.contains(cursor) && copy.stoneAt(cursor) == stone) {
            count++;
            cursor = new Position(cursor.row(), cursor.column() - 1);
        }
        
        // 동쪽 진행
        cursor = new Position(position.row(), position.column() + 1);

        while (copy.contains(cursor) && copy.stoneAt(cursor) == stone) {
            count++;
            cursor = new Position(cursor.row(), cursor.column() + 1);
        }
        return count;
    }

    private int countVertical(Board copy, Position position, Stone stone) {
        int count = 1;

        // 북쪽 진행
        Position cursor = new Position(position.row() - 1, position.column());

        while (copy.contains(cursor) && copy.stoneAt(cursor) == stone) {
            count++;
            cursor = new Position(cursor.row() - 1, cursor.column());
        }

        // 남쪽 진행
        cursor = new Position(position.row() + 1, position.column());

        while (copy.contains(cursor) && copy.stoneAt(cursor) == stone) {
            count++;
            cursor = new Position(cursor.row() + 1, cursor.column());
        }

        return count;
    }

    private int countRightDownDiagonal(Board copy, Position position, Stone stone) {
        int count = 1;

        //북서쪽 진행
        Position cursor = new Position(position.row() - 1, position.column() - 1);
        while (copy.contains(cursor) && copy.stoneAt(cursor) == stone) {
            count++;
            cursor = new Position(cursor.row() - 1, cursor.column() - 1);
        }
        
        //남동쪽 진행
        cursor = new Position(position.row() + 1, position.column() + 1);
        while (copy.contains(cursor) && copy.stoneAt(cursor) == stone) {
            count++;
            cursor = new Position(cursor.row() + 1, cursor.column() + 1);
        }

        return count;
    }
    
    private int countRightUpDiagonal(Board copy, Position position, Stone stone) {
        int count = 1;

        // 북동쪽 진행
        Position cursor = new Position(position.row() - 1, position.column() + 1);
        while(copy.contains(cursor) && copy.stoneAt(cursor) == stone) {
            count++;
            cursor = new Position(cursor.row() - 1, cursor.column() + 1);
        }

        // 남서쪽 진행
        cursor = new Position(position.row() + 1, position.column() - 1);
        while(copy.contains(cursor) && copy.stoneAt(cursor) == stone) {
            count++;
            cursor = new Position(cursor.row() + 1, cursor.column() - 1);
        }

        return count;
    }
}
