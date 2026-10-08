package io.github.dorgyuk.omgo.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RenjuRuleEvaluatorTest {
   
    @Test 
    void evaluate는_흑이_가로로_오목을_완성하면_WIN을_반환해야_함() {
        // 준비
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board board = new Board();
        Position position = new Position(7, 7);
        for (int i = 0; i < 4; i++) {
            board = board.withStone(position, Stone.BLACK);
            position = new Position(position.row(), position.column() + 1);
        }

        // 실행
        MoveEvaluation actual = renjuRuleEvaluator.evaluate(board, position, Stone.BLACK);

        // 검증
        assertEquals(MoveEvaluation.WIN, actual);

        // 준비
        board = new Board();
        position = new Position(0, 1);
        for (int i = 0; i < 4; i++) {
            board = board.withStone(position, Stone.BLACK);
            position = new Position(position.row(), position.column() + 1);
        }

        // 실행
        actual = renjuRuleEvaluator.evaluate(board, new Position(0,0), Stone.BLACK);

        // 검증
        assertEquals(MoveEvaluation.WIN, actual);
    }

    @Test 
    void evaluate는_흑이_세로로_오목을_완성하면_WIN을_반환해야_함() {
        // 준비
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board board = new Board();
        Position position = new Position(7, 7);
        for (int i = 0; i < 4; i++) {
            board = board.withStone(position, Stone.BLACK);
            position = new Position(position.row() + 1, position.column());
        }

        // 실행
        MoveEvaluation actual = renjuRuleEvaluator.evaluate(board, position, Stone.BLACK);

        // 검증
        assertEquals(MoveEvaluation.WIN, actual);
    }

    @Test 
    void evaluate는_흑이_우하향_대각선으로_오목을_완성하면_WIN을_반환해야_함() {
        // 준비
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board board = new Board();
        Position position = new Position(7, 7);
        for (int i = 0; i < 4; i++) {
            board = board.withStone(position, Stone.BLACK);
            position = new Position(position.row() + 1, position.column() + 1);
        }

        // 실행
        MoveEvaluation actual = renjuRuleEvaluator.evaluate(board, position, Stone.BLACK);

        // 검증
        assertEquals(MoveEvaluation.WIN, actual);
    }

    @Test 
    void evaluate는_흑이_우상향_대각선으로_오목을_완성하면_WIN을_반환해야_함() {
        // 준비
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board board = new Board();
        Position position = new Position(7, 7);

        for (int i = 0; i < 4; i++) {
            board = board.withStone(position, Stone.BLACK);
            position = new Position(position.row() - 1, position.column() + 1);
        }
        
        // 실행
        MoveEvaluation actual = renjuRuleEvaluator.evaluate(board, position, Stone.BLACK);

        // 검증
        assertEquals(MoveEvaluation.WIN, actual);
    }

    @Test 
    void evaluate는_흑이_가로의_빈틈을_채워_오목을_완성하면_WIN을_반환해야_함() {
        // 준비
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();

        Board board = new Board();
        board = board.withStone(new Position(7, 7), Stone.BLACK);
        board = board.withStone(new Position(7, 8), Stone.BLACK);
        board = board.withStone(new Position(7, 10), Stone.BLACK);
        board = board.withStone(new Position(7, 11), Stone.BLACK);

        // 실행
        MoveEvaluation actual = renjuRuleEvaluator.evaluate(board, new Position(7, 9),Stone.BLACK);

        // 검증
        assertEquals(MoveEvaluation.WIN, actual);
    }

    @Test 
    void evaluate는_백이_가로로_오목을_완성하면_WIN을_반환해야_함() {
        // 준비
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board board = new Board();
        Position position = new Position(7, 7);
        for (int i = 0; i < 4; i++) {
            board = board.withStone(position, Stone.WHITE);
            position = new Position(position.row(), position.column() + 1);
        }

        // 실행
        MoveEvaluation actual = renjuRuleEvaluator.evaluate(board, position, Stone.WHITE);

        // 검증
        assertEquals(MoveEvaluation.WIN, actual);
    }

    @Test
    void evaluate는_백이_세로로_오목을_완성하면_WIN을_반환해야_함() {
        // 준비
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board board = new Board();
        Position position = new Position(7, 7);
        for (int i = 0; i < 4; i++) {
            board = board.withStone(position, Stone.WHITE);
            position = new Position(position.row() + 1, position.column());
        }

        // 실행
        MoveEvaluation actual = renjuRuleEvaluator.evaluate(board, position, Stone.WHITE);

        // 검증
        assertEquals(MoveEvaluation.WIN, actual);
    }

    @Test
    void evaluate는_백이_우하향_대각선으로_오목을_완성하면_WIN을_반환해야_함() {
        // 준비
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board board = new Board();
        Position position = new Position(7, 7);
        for (int i = 0; i < 4; i++) {
            board = board.withStone(position, Stone.WHITE);
            position = new Position(position.row() + 1, position.column() + 1);
        }

        // 실행
        MoveEvaluation actual = renjuRuleEvaluator.evaluate(board, position, Stone.WHITE);

        // 검증
        assertEquals(MoveEvaluation.WIN, actual);
    }

    @Test
    void evaluate는_백이_우상향_대각선으로_오목을_완성하면_WIN을_반환해야_함() {
        // 준비
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board board = new Board();
        Position position = new Position(7, 7);
        for (int i = 0; i < 4; i++) {
            board = board.withStone(position, Stone.WHITE);
            position = new Position(position.row() - 1, position.column() + 1);
        }

        // 실행
        MoveEvaluation actual = renjuRuleEvaluator.evaluate(board, position, Stone.WHITE);

        // 검증
        assertEquals(MoveEvaluation.WIN, actual);
    }

    @Test
    void evaluate는_백이_가로의_빈틈을_채워_오목을_완성하면_WIN을_반환해야_함() {
        // 준비
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board board = new Board();
        board = board.withStone(new Position(7, 7), Stone.WHITE);
        board = board.withStone(new Position(7, 8), Stone.WHITE);
        board = board.withStone(new Position(7, 10), Stone.WHITE);
        board = board.withStone(new Position(7, 11), Stone.WHITE);

        // 실행
        MoveEvaluation actual = renjuRuleEvaluator.evaluate(board, new Position(7, 9), Stone.WHITE);

        // 검증
        assertEquals(MoveEvaluation.WIN, actual);
    }

    @Test
    void evaluate는_백이_가로의_빈틈을_채워_육목을_완성하면_WIN을_반환해야_함() {
        // 준비
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board board = new Board();
        board = board.withStone(new Position(7, 6), Stone.WHITE);
        board = board.withStone(new Position(7, 7), Stone.WHITE);
        board = board.withStone(new Position(7, 8), Stone.WHITE);
        board = board.withStone(new Position(7, 10), Stone.WHITE);
        board = board.withStone(new Position(7, 11), Stone.WHITE);

        // 실행
        MoveEvaluation actual = renjuRuleEvaluator.evaluate(board, new Position(7, 9), Stone.WHITE);

        // 검증
        assertEquals(MoveEvaluation.WIN, actual);
    }

    @Test 
    void evaluate는_흑이_가로로_사목을_완성하면_LEGAL을_반환해야_함() {
        // 준비
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board board = new Board();
        for (int i = 7; i < 10; i++) {
            board = board.withStone(new Position(7, i), Stone.BLACK);
        }
        
        // 실행
        MoveEvaluation actual = renjuRuleEvaluator.evaluate(board, new Position(7, 10), Stone.BLACK);

        // 검증
        assertEquals(MoveEvaluation.LEGAL, actual);
    }
    
    @Test 
    void evaluate는_흑_돌이_5개여도_중간이_연결되지_않으면_LEGAL을_반환해야_함() {
        // 준비
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board board = new Board();
        for (int i = 6; i < 11; i++) {
            if (i == 9) continue;
            board = board.withStone(new Position(6, i), Stone.BLACK);
        }
        Board board2 = board.withStone(new Position(6, 9), Stone.WHITE);

        // 1. 중간에 빈 칸이 있을 경우
        // 실행
        MoveEvaluation actual = renjuRuleEvaluator.evaluate(board, new Position(6, 11), Stone.BLACK);

        // 검증
        assertEquals(MoveEvaluation.LEGAL, actual);

        // 2. 중간에 백 돌이 있을 경우
        // 실행
        actual = renjuRuleEvaluator.evaluate(board2, new Position(6, 11), Stone.BLACK);

        // 검증
        assertEquals(MoveEvaluation.LEGAL, actual);
    }

    @Test 
    void evaluate는_흑_돌이_5개여도_중간에_백_돌이_있으면_LEGAL을_반환해야_함() {
        // 준비
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board board = new Board();
        for (int i = 6; i < 11; i++) {
            if (i == 9) {
                board = board.withStone(new Position(6, i), Stone.WHITE);
                continue;
            }
            board = board.withStone(new Position(6, i), Stone.BLACK);
        }
        
        // 실행
        MoveEvaluation actual = renjuRuleEvaluator.evaluate(board, new Position(6, 11), Stone.BLACK);

        // 검증
        assertEquals(MoveEvaluation.LEGAL, actual);   
    }

    @Test
    void evaluate는_백이_가로로_사목을_완성하면_LEGAL을_반환해야_함() {
        // 준비
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board board = new Board();
        for (int i = 7; i < 10; i++) {
            board = board.withStone(new Position(7, i), Stone.WHITE);
        }

        // 실행
        MoveEvaluation actual = renjuRuleEvaluator.evaluate(board, new Position(7, 10), Stone.WHITE);

        // 검증
        assertEquals(MoveEvaluation.LEGAL, actual);
    }

    @Test
    void evaluate는_백_돌이_5개여도_중간이_연결되지_않으면_LEGAL을_반환해야_함() {
        // 준비
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board board = new Board();
        for (int i = 6; i < 11; i++) {
            if (i == 9) continue;
            board = board.withStone(new Position(6, i), Stone.WHITE);
        }
        Board board2 = board.withStone(new Position(6, 9), Stone.BLACK);

        // 1. 중간에 빈 칸이 있을 경우
        // 실행
        MoveEvaluation actual = renjuRuleEvaluator.evaluate(board, new Position(6, 11), Stone.WHITE);

        // 검증
        assertEquals(MoveEvaluation.LEGAL, actual);

        // 2. 중간에 흑돌이 있을 경우
        // 실행
        actual = renjuRuleEvaluator.evaluate(board2, new Position(6, 11), Stone.WHITE);

        // 검증
        assertEquals(MoveEvaluation.LEGAL, actual);
    }

    @Test 
    void evaluate는_평가_후에도_원본을_유지해야_함() {

        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board board = new Board();
        Position position = new Position(7, 7);
        Stone stone = Stone.BLACK;

        renjuRuleEvaluator.evaluate(board, position, stone);

        assertEquals(Stone.EMPTY, board.stoneAt(position));
    }

    @Test
    void evaluate는_흑이_가로의_빈틈을_채워_육목을_완성하면_FORBIDDEN을_반환해야_함() {
        // 준비
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board board = new Board();

        for (int i = 0; i < 6; i++) {
            if (i == 3) continue;
            Position position = new Position(0, i);
            board = board.withStone(position, Stone.BLACK);
        }

        // 실행
        MoveEvaluation actual = renjuRuleEvaluator.evaluate(board, new Position(0, 3), Stone.BLACK);

        // 검증
        assertEquals(MoveEvaluation.FORBIDDEN, actual);
    }

    @Test
    void evaluate는_흑이_오목과_육목을_동시에_완성하면_WIN을_반환해야_함() {
        // 준비
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board board = new Board();

        for (int i = 5; i < 10; i++) {
            if (i == 7) continue;
            Position position = new Position(i, 7);
            board = board.withStone(position, Stone.BLACK);
        }

        for (int i = 4; i < 10; i++ ) {
            if (i == 7) continue;
            Position position = new Position(7, i);
            board = board.withStone(position, Stone.BLACK);
        }

        // 실행
        MoveEvaluation actual = renjuRuleEvaluator.evaluate(board, new Position(7, 7), Stone.BLACK);

        // 검증
        assertEquals(MoveEvaluation.WIN, actual);

        board = new Board();

        for (int i = 5; i < 10; i++) {
            if (i == 7) continue;
            Position position = new Position(i, i);
            board = board.withStone(position, Stone.BLACK);
        }

        for (int i = 0; i < 6; i++ ) {
            if (i == 3) continue;
            Position position = new Position(10 - i, 4 + i);
            board = board.withStone(position, Stone.BLACK);
        }

        // 실행
        actual = renjuRuleEvaluator.evaluate(board, new Position(7, 7), Stone.BLACK);

        // 검증
        assertEquals(MoveEvaluation.WIN, actual);

    }

    @Test
    void evaluate는_흑이_4_4를_완성할_경우_FORBIDDEN을_반환해야_함() {
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board board = new Board();

        for (int i = 0; i < 3; i++) {
            board = board.withStone(new Position(0, i), Stone.BLACK);
            board = board.withStone(new Position(i + 1, 3), Stone.BLACK);
        }

        // 실행
        MoveEvaluation actual = renjuRuleEvaluator.evaluate(board, new Position(0,3), Stone.BLACK);

        // 검증
        assertEquals(MoveEvaluation.FORBIDDEN, actual);

    }

    @Test
    void countFours는_흑돌_하나만_있으면_0을_반환해야_함() {
        // 준비
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Position position = new Position(7, 7);
        Board boardAfterMove = new Board().withStone(position, Stone.BLACK);

        // 실행
        int actual = renjuRuleEvaluator.countFours(boardAfterMove, position, 0, 1);

        // 검증
        assertEquals(0, actual);
    }

    @Test
    void countFours는_가로에_4가_하나_있으면_1을_반환해야_함() {
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board board = new Board();

        for (int i = 0; i < 4; i++) {
            board = board.withStone(new Position(0, i), Stone.BLACK);
        }

        // 실행
        int actual = renjuRuleEvaluator.countFours(board, new Position(0, 3), 0, 1);

        // 검증
        assertEquals(1, actual); 
    }

    @Test 
    void countFours는_열린_4를_중복으로_세지_않아야_함() {
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board board = new Board();

        for (int i = 1; i < 5; i++) {
            board = board.withStone(new Position(0, i), Stone.BLACK);
        }

        int actual = renjuRuleEvaluator.countFours(board, new Position(0,4), 0, 1);

        assertEquals(1, actual);
    }
    @Test
    void countFours는_같은_방향의_서로_다른_4를_각각_세어야_함() {
        // 준비: * _ * o * _ * (o는 판정 대상 돌)
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board boardAfterMove = new Board();
        for (int column : new int[]{3, 5, 6, 7, 9}) {
            boardAfterMove = boardAfterMove.withStone(new Position(7, column), Stone.BLACK);
        }

        // 실행
        int actual = renjuRuleEvaluator.countFours(boardAfterMove, new Position(7, 6), 0, 1);

        // 검증: 두 빈칸에서 완성되는 4의 좌표 묶음은 서로 다르다.
        assertEquals(2, actual);
    }
}
