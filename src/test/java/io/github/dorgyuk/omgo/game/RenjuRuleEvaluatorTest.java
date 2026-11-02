package io.github.dorgyuk.omgo.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RenjuRuleEvaluatorTest {
   
    @Test 
    void evaluate는_흑이_가로로_오목을_완성하면_WIN을_반환해야_함() {
        // 준비: 7행의 7~10열에 연속된 흑돌 네 개를 놓는다.
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

        // 준비: 위쪽 경계인 0행의 1~4열에 연속된 흑돌 네 개를 놓는다.
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
        // 준비: 7열의 7~10행에 연속된 흑돌 네 개를 놓는다.
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
        // 준비: (7, 7)부터 (10, 10)까지 우하향 대각선에 흑돌 네 개를 놓는다.
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
        // 준비: (7, 7)부터 (4, 10)까지 우상향 대각선에 흑돌 네 개를 놓는다.
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
        // 준비: 7행의 7, 8, 10, 11열에 흑돌을 놓고 가운데 9열을 비운다.
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
        // 준비: 7행의 7~10열에 연속된 백돌 네 개를 놓는다.
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
        // 준비: 7열의 7~10행에 연속된 백돌 네 개를 놓는다.
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
        // 준비: (7, 7)부터 (10, 10)까지 우하향 대각선에 백돌 네 개를 놓는다.
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
        // 준비: (7, 7)부터 (4, 10)까지 우상향 대각선에 백돌 네 개를 놓는다.
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
        // 준비: 7행의 7, 8, 10, 11열에 백돌을 놓고 가운데 9열을 비운다.
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
        // 준비: 7행의 6~11열 중 9열만 비우고 백돌 다섯 개를 놓는다.
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
        // 준비: 7행의 7~9열에 연속된 흑돌 세 개를 놓는다.
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
        // 준비: 6행의 6, 7, 8, 10열에 흑돌을 놓는다.
        // 9열이 빈 보드와 9열을 백돌로 막은 보드를 각각 준비한다.
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board board = new Board();
        for (int i = 6; i < 11; i++) {
            if (i == 9) continue;
            board = board.withStone(new Position(6, i), Stone.BLACK);
        }
        Board board2 = board.withStone(new Position(6, 9), Stone.WHITE);

        // 실행 1: 9열이 빈 상태에서 (6, 11)에 흑돌을 놓는 착수를 평가한다.
        MoveEvaluation actual = renjuRuleEvaluator.evaluate(board, new Position(6, 11), Stone.BLACK);

        // 검증 1: 흑돌은 다섯 개지만 빈칸으로 끊겨 있으므로 승리하지 않는다.
        assertEquals(MoveEvaluation.LEGAL, actual);

        // 실행 2: 9열에 백돌이 있는 상태에서 같은 위치의 착수를 평가한다.
        actual = renjuRuleEvaluator.evaluate(board2, new Position(6, 11), Stone.BLACK);

        // 검증 2: 상대 돌을 사이에 둔 흑돌들은 연속된 5목으로 인정하지 않는다.
        assertEquals(MoveEvaluation.LEGAL, actual);
    }

    @Test 
    void evaluate는_흑_돌이_5개여도_중간에_백_돌이_있으면_LEGAL을_반환해야_함() {
        // 준비: 6행의 6~10열에 흑돌을 놓되 9열에는 백돌을 놓아 연결을 끊는다.
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
        // 준비: 7행의 7~9열에 연속된 백돌 세 개를 놓는다.
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
        // 준비: 6행의 6, 7, 8, 10열에 백돌을 놓는다.
        // 9열이 빈 보드와 9열을 흑돌로 막은 보드를 각각 준비한다.
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board board = new Board();
        for (int i = 6; i < 11; i++) {
            if (i == 9) continue;
            board = board.withStone(new Position(6, i), Stone.WHITE);
        }
        Board board2 = board.withStone(new Position(6, 9), Stone.BLACK);

        // 실행 1: 9열이 빈 상태에서 (6, 11)에 백돌을 놓는 착수를 평가한다.
        MoveEvaluation actual = renjuRuleEvaluator.evaluate(board, new Position(6, 11), Stone.WHITE);

        // 검증 1: 백돌은 다섯 개지만 빈칸으로 끊겨 있으므로 승리하지 않는다.
        assertEquals(MoveEvaluation.LEGAL, actual);

        // 실행 2: 9열에 흑돌이 있는 상태에서 같은 위치의 착수를 평가한다.
        actual = renjuRuleEvaluator.evaluate(board2, new Position(6, 11), Stone.WHITE);

        // 검증 2: 상대 돌을 사이에 둔 백돌들은 연속된 5목으로 인정하지 않는다.
        assertEquals(MoveEvaluation.LEGAL, actual);
    }

    @Test 
    void evaluate는_평가_후에도_원본을_유지해야_함() {
        // 준비: 빈 보드와 흑의 착수 위치 (7, 7)을 준비한다.
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board board = new Board();
        Position position = new Position(7, 7);
        Stone stone = Stone.BLACK;

        // 실행: 원본 보드를 전달해 흑의 착수를 평가한다.
        renjuRuleEvaluator.evaluate(board, position, stone);

        // 검증: 가상 착수는 원본에 반영되지 않아 해당 위치가 여전히 비어 있다.
        assertEquals(Stone.EMPTY, board.stoneAt(position));
    }

    @Test
    void evaluate는_흑이_가로의_빈틈을_채워_육목을_완성하면_FORBIDDEN을_반환해야_함() {
        // 준비: 0행의 0~5열 중 3열만 비우고 흑돌 다섯 개를 놓는다.
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board board = new Board();

        for (int i = 0; i < 6; i++) {
            if (i == 3) continue;
            Position position = new Position(0, i);
            board = board.withStone(position, Stone.BLACK);
        }

        // 실행: 빈틈 (0, 3)에 흑돌을 놓아 여섯 돌을 연결한다.
        MoveEvaluation actual = renjuRuleEvaluator.evaluate(board, new Position(0, 3), Stone.BLACK);

        // 검증: 정확한 5목 없이 흑의 장목만 생기므로 금수다.
        assertEquals(MoveEvaluation.FORBIDDEN, actual);
    }

    @Test
    void evaluate는_흑이_오목과_육목을_동시에_완성하면_WIN을_반환해야_함() {
        // 준비 1: (7, 7)을 비워 두고 세로 5~9행, 가로 4~9열에 흑돌을 놓는다.
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

        // 실행 1: (7, 7)에 흑돌을 놓아 세로 5목과 가로 6목을 동시에 만든다.
        MoveEvaluation actual = renjuRuleEvaluator.evaluate(board, new Position(7, 7), Stone.BLACK);

        // 검증 1: 가로 장목을 먼저 발견해도 세로의 정확한 5목 승리가 우선한다.
        assertEquals(MoveEvaluation.WIN, actual);

        // 준비 2: (7, 7)을 비워 두고 우하향 대각선에 네 돌, 우상향 대각선에 다섯 돌을 놓는다.
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

        // 실행 2: (7, 7)에 흑돌을 놓아 우하향 5목과 우상향 6목을 동시에 만든다.
        actual = renjuRuleEvaluator.evaluate(board, new Position(7, 7), Stone.BLACK);

        // 검증 2: 정확한 5목을 먼저 발견하는 방향 순서에서도 승리로 판정한다.
        assertEquals(MoveEvaluation.WIN, actual);

    }

    @Test
    void evaluate는_흑이_4_4를_완성할_경우_FORBIDDEN을_반환해야_함() {
        // 준비: 0행의 0~2열과 3열의 1~3행에 흑돌을 놓고 교차점 (0, 3)을 비운다.
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board board = new Board();

        for (int i = 0; i < 3; i++) {
            board = board.withStone(new Position(0, i), Stone.BLACK);
            board = board.withStone(new Position(i + 1, 3), Stone.BLACK);
        }

        // 실행: (0, 3)에 흑돌을 놓아 가로와 세로에 각각 4를 만든다.
        MoveEvaluation actual = renjuRuleEvaluator.evaluate(board, new Position(0,3), Stone.BLACK);

        // 검증: 서로 다른 두 방향의 4가 착수 위치를 공유하므로 4·4 금수다.
        assertEquals(MoveEvaluation.FORBIDDEN, actual);

    }

    @Test
    void countFours는_흑돌_하나만_있으면_0을_반환해야_함() {
        // 준비: (7, 7)에 흑돌 하나만 놓인 착수 후 보드를 준비한다.
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Position position = new Position(7, 7);
        Board boardAfterMove = new Board().withStone(position, Stone.BLACK);

        // 실행: (7, 7)을 포함하는 가로 방향의 4를 센다.
        int actual = renjuRuleEvaluator.countFours(boardAfterMove, position, 0, 1);

        // 검증: 한 돌을 더 놓아도 5목이 될 수 없으므로 4가 없다.
        assertEquals(0, actual);
    }

    @Test
    void countFours는_가로에_4가_하나_있으면_1을_반환해야_함() {
        // 준비: 0행의 0~3열에 흑돌 네 개를 놓아 왼쪽이 경계로 막힌 4를 만든다.
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board board = new Board();

        for (int i = 0; i < 4; i++) {
            board = board.withStone(new Position(0, i), Stone.BLACK);
        }

        // 실행: (0, 3)을 포함하는 가로 방향의 4를 센다.
        int actual = renjuRuleEvaluator.countFours(board, new Position(0, 3), 0, 1);

        // 검증: (0, 4)에서 정확한 5목이 되므로 한쪽만 열린 4도 하나로 센다.
        assertEquals(1, actual); 
    }

    @Test 
    void countFours는_열린_4를_중복으로_세지_않아야_함() {
        // 준비: 0행의 1~4열에 흑돌을 놓고 양 끝 0열과 5열은 비워 둔다.
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board board = new Board();

        for (int i = 1; i < 5; i++) {
            board = board.withStone(new Position(0, i), Stone.BLACK);
        }

        // 실행: (0, 4)를 포함하는 가로 방향의 4를 센다.
        int actual = renjuRuleEvaluator.countFours(board, new Position(0,4), 0, 1);

        // 검증: 양 끝에서 5목을 완성할 수 있어도 같은 네 돌이므로 4 하나로 센다.
        assertEquals(1, actual);
    }

    @Test
    void countFours는_같은_방향의_서로_다른_4를_각각_세어야_함() {
        // 준비: 7행의 3, 5, 6, 7, 9열에 흑돌을 놓고 판정 대상은 6열로 정한다.
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board boardAfterMove = new Board();
        for (int column : new int[]{3, 5, 6, 7, 9}) {
            boardAfterMove = boardAfterMove.withStone(new Position(7, column), Stone.BLACK);
        }

        // 실행: (7, 6)을 포함하는 가로 방향의 4를 센다.
        int actual = renjuRuleEvaluator.countFours(boardAfterMove, new Position(7, 6), 0, 1);

        // 검증: 4열에서 완성되는 {3, 5, 6, 7}과 8열에서 완성되는 {5, 6, 7, 9}는 별개의 4다.
        assertEquals(2, actual);
    }

    @Test 
    void countThrees는_가로에_열린_3이_하나_있으면_1을_반환해야_함() {
        // 준비: 7행의 6~8열에 흑돌을 놓고 양쪽 확장 공간은 비워 둔다.
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board boardAfterMove = new Board();

        for (int column = 6; column <= 8; column++) {
            boardAfterMove = boardAfterMove.withStone(new Position(7, column), Stone.BLACK);
        }

        // 실행: (7, 7)을 포함하는 가로 방향의 유효한 3을 센다.
        int actual = renjuRuleEvaluator.countThrees(boardAfterMove, new Position(7, 7), 0, 1);

        // 검증: 5열과 9열 모두에서 열린 4로 확장할 수 있지만 같은 세 돌이므로 하나로 센다.
        assertEquals(1, actual);
    }

    @Test
    void countThrees는_양옆이_비어도_열린_4로_발전할_수_없으면_0을_반환해야_함() {
        // 준비: ○ · ● ● ● · ○ (○는 백돌, ●는 흑돌)
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board boardAfterMove = new Board();
        boardAfterMove = boardAfterMove.withStone(new Position(7, 4), Stone.WHITE);
        for (int column = 6; column <= 8; column++) {
            boardAfterMove = boardAfterMove.withStone(new Position(7, column), Stone.BLACK);
        }
        boardAfterMove = boardAfterMove.withStone(new Position(7, 10), Stone.WHITE);

        // 실행: (7, 7)을 포함하는 가로 방향의 유효한 3을 센다.
        int actual = renjuRuleEvaluator.countThrees(boardAfterMove, new Position(7, 7), 0, 1);

        // 검증: 5열이나 9열에 놓아도 한쪽이 백돌로 막힌 4만 만들어진다.
        assertEquals(0, actual);
    }

    @Test
    void countThrees는_열린_4로_만드는_유일한_후보가_장목이면_0을_반환해야_함() {
        // 준비: 가로의 6~8열에 흑돌, 10열에 백돌이 있다.
        // 5열에 놓으면 가로 열린 4가 되지만, 세로로 4~9행의 장목이 된다.
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board boardAfterMove = new Board();
        for (int column = 6; column <= 8; column++) {
            boardAfterMove = boardAfterMove.withStone(new Position(7, column), Stone.BLACK);
        }
        boardAfterMove = boardAfterMove.withStone(new Position(7, 10), Stone.WHITE);
        for (int row : new int[]{4, 5, 6, 8, 9}) {
            boardAfterMove = boardAfterMove.withStone(new Position(row, 5), Stone.BLACK);
        }

        // 실행: (7, 7)을 포함하는 가로 3이 금수 없이 열린 4로 확장 가능한지 판정한다.
        int actual = renjuRuleEvaluator.countThrees(boardAfterMove, new Position(7, 7), 0, 1);

        // 검증: 5열은 장목 금수이고, 9열은 닫힌 4만 만들므로 유효한 3이 아니다.
        assertEquals(0, actual);
    }
    @Test
    void countThrees는_열린_4로_만드는_유일한_후보가_동시에_5목을_만들면_0을_반환해야_함() {
        // 준비: 가로의 6~8열에 흑돌, 10열에 백돌이 있다.
        // 5열에 놓으면 가로 열린 4와 세로 5~9행의 정확한 5목이 동시에 만들어진다.
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board boardAfterMove = new Board();
        for (int column = 6; column <= 8; column++) {
            boardAfterMove = boardAfterMove.withStone(new Position(7, column), Stone.BLACK);
        }
        boardAfterMove = boardAfterMove.withStone(new Position(7, 10), Stone.WHITE);
        for (int row : new int[]{5, 6, 8, 9}) {
            boardAfterMove = boardAfterMove.withStone(new Position(row, 5), Stone.BLACK);
        }

        // 실행: (7, 7)을 포함하는 가로 3에 동시 5목을 만들지 않는 확장 후보가 있는지 판정한다.
        int actual = renjuRuleEvaluator.countThrees(boardAfterMove, new Position(7, 7), 0, 1);

        // 검증: 5열은 동시에 5목을 만들고, 9열은 닫힌 4만 만들므로 유효한 3이 아니다.
        assertEquals(0, actual);
    }

    @Test
    void evaluate는_흑이_가로와_세로에_유효한_3을_동시에_만들면_FORBIDDEN을_반환해야_함() {
        // 준비: (7, 7)에 놓으면 가로와 세로에 각각 양쪽이 열린 연속 3이 생긴다.
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board board = new Board();
        board = board.withStone(new Position(7, 6), Stone.BLACK);
        board = board.withStone(new Position(7, 8), Stone.BLACK);
        board = board.withStone(new Position(6, 7), Stone.BLACK);
        board = board.withStone(new Position(8, 7), Stone.BLACK);

        // 실행: 교차점 (7, 7)에 흑돌을 놓는 착수를 평가한다.
        MoveEvaluation actual = renjuRuleEvaluator.evaluate(board, new Position(7, 7), Stone.BLACK);

        // 검증: 두 방향의 3 모두 금수 없이 열린 4로 확장 가능하므로 3·3 금수다.
        assertEquals(MoveEvaluation.FORBIDDEN, actual);
    }

    @Test
    void countThrees는_열린_4로_만드는_유일한_후보가_4_4이면_0을_반환해야_함() {
        // 준비: (7, 5)에 놓으면 가로와 세로에 각각 열린 4가 생긴다.
        // (7, 9)는 10열의 백돌 때문에 열린 4를 만들 수 없다.
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board boardAfterMove = new Board();
        for (int column = 6; column <= 8; column++) {
            boardAfterMove = boardAfterMove.withStone(new Position(7, column), Stone.BLACK);
        }
        boardAfterMove = boardAfterMove.withStone(new Position(7, 10), Stone.WHITE);
        for (int row = 4; row <= 6; row++) {
            boardAfterMove = boardAfterMove.withStone(new Position(row, 5), Stone.BLACK);
        }

        // 실행: (7, 7)을 포함하는 가로 3의 확장 후보를 다른 방향의 4까지 고려해 판정한다.
        int actual = renjuRuleEvaluator.countThrees(boardAfterMove, new Position(7, 7), 0, 1);

        // 검증: 유일한 확장 후보가 4·4 금수이므로 유효한 3이 아니다.
        assertEquals(0, actual);
    }

    @Test
    void countThrees는_열린_4로_만드는_유일한_후보가_금수_3_3이면_0을_반환해야_함() {
        // 준비: (7, 5)에 놓으면 가로 열린 4와 함께 세로 및 우하향 대각선의 3이 생긴다.
        // 세로의 3은 (5, 5), 대각선의 3은 (5, 3)에서 금수 없이 열린 4로 확장 가능하다.
        // (7, 9)는 10열의 백돌 때문에 열린 4를 만들 수 없다.
        RenjuRuleEvaluator renjuRuleEvaluator = new RenjuRuleEvaluator();
        Board boardAfterMove = new Board();
        for (int column = 6; column <= 8; column++) {
            boardAfterMove = boardAfterMove.withStone(new Position(7, column), Stone.BLACK);
        }
        boardAfterMove = boardAfterMove.withStone(new Position(7, 10), Stone.WHITE);
        boardAfterMove = boardAfterMove.withStone(new Position(6, 5), Stone.BLACK);
        boardAfterMove = boardAfterMove.withStone(new Position(8, 5), Stone.BLACK);
        boardAfterMove = boardAfterMove.withStone(new Position(6, 4), Stone.BLACK);
        boardAfterMove = boardAfterMove.withStone(new Position(8, 6), Stone.BLACK);

        // 실행: (7, 7)을 포함하는 가로 3을 세면서 확장 후보에서 생기는 3의 유효성도 재귀 검사한다.
        int actual = renjuRuleEvaluator.countThrees(boardAfterMove, new Position(7, 7), 0, 1);

        // 검증: 유일한 확장 후보가 금수 3·3이므로 원래의 가로 3은 유효하지 않다.
        assertEquals(0, actual);
    }

    @Test
    void evaluate는_승리나_금수_없이_마지막_칸을_채우면_DRAW를_반환해야_함() {
        RenjuRuleEvaluator evaluator = new RenjuRuleEvaluator();
        Position lastPosition = new Position(Board.SIZE - 1, Board.SIZE - 1);

        for (Stone lastStone : new Stone[]{Stone.BLACK, Stone.WHITE}) {
            Board board = new Board();
            for (int row = 0; row < Board.SIZE; row++) {
                for (int column = 0; column < Board.SIZE; column++) {
                    Position position = new Position(row, column);
                    if (position.equals(lastPosition)) {
                        continue;
                    }
                    // 네 방향 모두 같은 색이 최대 두 개만 연속되는 배치다.
                    Stone stone = (row + 2 * column) % 4 < 2 ? Stone.BLACK : Stone.WHITE;
                    board = board.withStone(position, stone);
                }
            }

            MoveEvaluation actual = evaluator.evaluate(board, lastPosition, lastStone);

            assertEquals(MoveEvaluation.DRAW, actual, "마지막 착수 색: " + lastStone);
            assertEquals(Stone.EMPTY, board.stoneAt(lastPosition));
        }
    }

    @Test
    void evaluate는_마지막_칸에서_정확한_오목을_완성하면_DRAW보다_WIN을_우선해야_함() {
        RenjuRuleEvaluator evaluator = new RenjuRuleEvaluator();
        Position lastPosition = new Position(Board.SIZE - 1, Board.SIZE - 1);
        Board board = new Board();
        for (int row = 0; row < Board.SIZE; row++) {
            for (int column = 0; column < Board.SIZE; column++) {
                Position position = new Position(row, column);
                if (position.equals(lastPosition)) {
                    continue;
                }
                Stone stone = (row + 2 * column) % 4 < 2 ? Stone.BLACK : Stone.WHITE;
                // 마지막 행의 10~13열을 흑으로 채우고, 9열은 백으로 막는다.
                if (row == Board.SIZE - 1 && column >= Board.SIZE - 5) {
                    stone = Stone.BLACK;
                } else if (row == Board.SIZE - 1 && column == Board.SIZE - 6) {
                    stone = Stone.WHITE;
                }
                board = board.withStone(position, stone);
            }
        }

        MoveEvaluation actual = evaluator.evaluate(board, lastPosition, Stone.BLACK);

        assertEquals(MoveEvaluation.WIN, actual);
    }

    @Test
    void evaluate는_마지막_칸에서_장목을_만들면_DRAW보다_FORBIDDEN을_우선해야_함() {
        RenjuRuleEvaluator evaluator = new RenjuRuleEvaluator();
        Position lastPosition = new Position(Board.SIZE - 1, Board.SIZE - 1);
        Board board = new Board();
        for (int row = 0; row < Board.SIZE; row++) {
            for (int column = 0; column < Board.SIZE; column++) {
                Position position = new Position(row, column);
                if (position.equals(lastPosition)) {
                    continue;
                }
                Stone stone = (row + 2 * column) % 4 < 2 ? Stone.BLACK : Stone.WHITE;
                if (row == Board.SIZE - 1 && column >= Board.SIZE - 6) {
                    stone = Stone.BLACK;
                }
                // 착수 전에는 오목이 없고, 마지막 착수로 3+2개의 흑돌을 연결해 육목이 된다.
                if (row == Board.SIZE - 1 && column == Board.SIZE - 3) {
                    continue;
                }
                board = board.withStone(position, stone);
            }
        }
        // 오른쪽 끝은 미리 채우고, 두 흑돌 묶음 사이를 마지막 빈칸으로 사용한다.
        board = board.withStone(lastPosition, Stone.BLACK);
        Position gapPosition = new Position(Board.SIZE - 1, Board.SIZE - 3);

        MoveEvaluation actual = evaluator.evaluate(board, gapPosition, Stone.BLACK);

        assertEquals(MoveEvaluation.FORBIDDEN, actual);
    }
}
