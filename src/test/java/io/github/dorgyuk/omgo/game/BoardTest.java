package io.github.dorgyuk.omgo.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class BoardTest {
   
    @Test
    void 새_보드는_전부_비어_있어야_함() {
        // 준비
        Board board = new Board();

        for (int i = 0; i < Board.SIZE; i++) {
            for (int j = 0; j < Board.SIZE; j++) {
                // 실행
                Stone actual = board.stoneAt(new Position(i, j));

                // 검증
                assertEquals(Stone.EMPTY, actual);
            }
        }
    }

    @Test
    void stoneAt은_지정한_위치의_돌을_반환해야_함() {
        // 준비
        Position pos = new Position(7, 7);
        for (Stone expected : Stone.values()) {
            Board board = new Board();

            // 실행
            if (expected != Stone.EMPTY) {
                board = board.withStone(pos, expected);
            }

            // 검증
            assertEquals(expected, board.stoneAt(pos));
        }
    }

    @Test 
    void contains는_pos가_보드_내부에_있다면_true를_반환해야_함() {
        // 준비
        Board board = new Board();

        // 범위 안: 네 모서리
        Position[] positions = new Position[4];
        positions[0] = new Position(0, 0);
        positions[1] = new Position(0, Board.SIZE - 1);
        positions[2] = new Position(Board.SIZE - 1, 0);
        positions[3] = new Position(Board.SIZE - 1, Board.SIZE - 1);

        for (int i = 0; i < positions.length; i++) {
            // 실행 및 검증
            assertTrue(board.contains(positions[i]));
        }
    }

    @Test 
    void contains는_pos가_보드_외부에_있다면_false를_반환해야_함() {
        // 준비
        Board board = new Board();

        Position[] positions = new Position[4];
        positions[0] = new Position(-1, 5);
        positions[1] = new Position(Board.SIZE, 6);
        positions[2] = new Position(4, -1);
        positions[3] = new Position(9, Board.SIZE);

        for (int i = 0; i < positions.length; i++) {
           // 실행 및 검증
           assertFalse(board.contains(positions[i]));
        }
    }

    @Test 
    void withStone은_지정한_위치에_돌을_놓은_새_보드를_반환해야_함() {
        // 준비
        Board original = new Board();
        Position pos = new Position(7, 7);

        // 실행
        Board result = original.withStone(pos, Stone.BLACK);

        // 검증
        assertNotSame(original, result);
        assertEquals(Stone.BLACK, result.stoneAt(pos));
    }

    @Test 
    void withStone은_원본_보드를_변경하지_않아야_함() {
        // 준비
        Board original = new Board();
        Position pos = new Position(7, 7);

        // 실행
        Board result = original.withStone(pos, Stone.BLACK);

        // 검증
        assertEquals( Stone.EMPTY, original.stoneAt(pos));
    }

    @Test 
    void withStone은_기존_돌을_보존해야_함() {
        // 준비
        Board first = new Board().withStone(new Position(7, 7), Stone.BLACK);
        Board second = first.withStone(new Position(7, 8), Stone.WHITE);
        Position pos = new Position(7,7);

        // 실행 및 검증
        assertEquals(Stone.BLACK,second.stoneAt(pos));
    }

    @Test
    void withStone은_잘못된_착수를_거부해야_함() {
        // 준비
        Position pos = new Position(7, 7);
        Board board = new Board().withStone(pos, Stone.BLACK);
        
        // 실행 및 검증
        assertThrows(
            IllegalArgumentException.class,
            () -> board.withStone(pos, Stone.WHITE));
    }

    @Test 
    void withStone은_null_좌표를_거부해야_함() {
        // 준비
        Board board = new Board();

        // 실행 및 검증
        assertThrows(
            IllegalArgumentException.class,
            () -> board.withStone(null, Stone.BLACK));
    }

    @Test 
    void withStone은_범위_밖_좌표를_거부해야_함() {
        // 준비
        Board board = new Board();
        Position[] positions = new Position[4];

        positions[0] = new Position(-1, 6);
        positions[1] = new Position(Board.SIZE, 4);
        positions[2] = new Position(3, -1);
        positions[3] = new Position(3, Board.SIZE);

        for (Position position : positions) {
            // 실행 및 검증
            assertThrows(
                IllegalArgumentException.class,
                () -> board.withStone(position, Stone.BLACK));
        }
    }

    @Test 
    void withStone은_null_돌을_거부해야_함() {
        // 준비
        Board board = new Board();

        // 실행 및 검증
        assertThrows(
            IllegalArgumentException.class,
            () -> board.withStone(new Position(7,7), null));
    }

    @Test 
    void withStone은_EMPTY_돌을_거부해야_함() {
        // 준비
        Board board = new Board();

        // 실행 및 검증
        assertThrows(
            IllegalArgumentException.class,
            () -> board.withStone(new Position(7, 7), Stone.EMPTY));
    }
}
