package io.github.dorgyuk.omgo.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RenjuRuleEvaluatorTest {
   
    @Test 
    void evaluate는_흑_돌이_가로로_오목을_완성하면_WIN을_반환해야_함() {
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
    }

    @Test 
    void evaluate는_흑_돌이_세로로_오목을_완성하면_WIN을_반환해야_함() {
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
    void evaluate는_흑_돌이_우하향_대각선으로_오목을_완성하면_WIN을_반환해야_함() {
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
    void evaluate는_흑_돌이_우상향_대각선으로_오목을_완성하면_WIN을_반환해야_함() {
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
    void evaluate는_백_돌이_가로로_오목을_완성하면_WIN을_반환해야_함() {
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
    void evaluate는_백_돌이_세로로_오목을_완성하면_WIN을_반환해야_함() {
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
    void evaluate는_백_돌이_우하향_대각선으로_오목을_완성하면_WIN을_반환해야_함() {
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
    void evaluate는_백_돌이_우상향_대각선으로_오목을_완성하면_WIN을_반환해야_함() {
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
}
