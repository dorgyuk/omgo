package io.github.dorgyuk.omgo.game;


public class RenjuRuleEvaluator {
    /**
     * 착수 전 보드에 가상으로 돌을 놓아 승리 여부를 평가한다.
     * 원본 보드는 변경하지 않는다.
     * 
     * 흑은 정확히 5개, 백은 5개 이상의 돌이 연속되면 승리로 판정한다.
     * 
     * @param board 착수 전 보드
     * @param position 착수할 위치
     * @param stone 놓을 돌의 색깔
     * @return 승리 조건을 만족하면 WIN
     * @throws IllegalArgumentException 위치가 null이거나, 보드 범위를 벗어날 경우,
     *                                  이미 돌이 있는 위치일 경우,
     *                                  돌이 null이거나, EMPTY인 경우
     */
    public MoveEvaluation evaluate(Board board, Position position, Stone stone) {
        Board boardAfterMove = board.withStone(position, stone);

        int[][] directions = {
            {0, 1},
            {1, 0},
            {1, 1},
            {-1, 1}
        };

        // 승리 조건 및 장목(흑 돌)확인
        boolean hasOverline = false;

        for (int[] direction : directions) {
            int count = countLine(boardAfterMove, position, stone, direction[0], direction[1]);
            if (isWinningCondition(stone, count)) {
                return MoveEvaluation.WIN;
            }
            if (stone == Stone.BLACK && isOverline(count)) {
                hasOverline = true;
            }
        }
        // 금수 조건 확인

        // 백은 금수가 없음
        if (stone == Stone.WHITE) {
            return MoveEvaluation.LEGAL;
        }

        // 흑 장목 확인
        if (hasOverline) {
            return MoveEvaluation.FORBIDDEN;
        }

        // 흑 4·4 확인

        // 흑 3·3 확인


        return MoveEvaluation.LEGAL;
    }

    /**
     * 지정한 위치를 중심으로 한 직선의 양방향에서 같은 색의 연속된 돌을 센다.
     * 보드 경계, 빈 칸, 다른 색의 돌을 만나면 해당 방향의 탐색을 종료한다.
     * 
     * @param boardAfterMove 중심 위치에 해당 색의 돌이 놓인 가상 보드
     * @param position 연속된 돌을 셀 중심 위치
     * @param stone 셀 돌의 색깔
     * @param dr 탐색 방향의 행 변화량 (-1, 0, 1 중 하나)
     * @param dc 탐색 방향의 열 변화량 (-1, 0, 1 중 하나)
     * @return 중심 위치를 포함한 양방향의 같은 색 연속 돌 개수
     */
    private int countLine (Board boardAfterMove, Position position, Stone stone, int dr, int dc) {
        int count = 1;

        Position cursor = new Position(position.row() + dr, position.column() + dc);

        while(boardAfterMove.contains(cursor) && boardAfterMove.stoneAt(cursor) == stone) {
            count++;
            cursor = new Position(cursor.row() + dr, cursor.column() + dc);
        }

        // 반대 방향
        cursor = new Position(position.row() - dr, position.column() - dc);
        while(boardAfterMove.contains(cursor) && boardAfterMove.stoneAt(cursor) == stone) {
            count++;
            cursor = new Position(cursor.row() - dr, cursor.column() - dc);
        }

        return count;
    }

    /**
     * 돌의 색깔과 연속된 개수를 기준으로 승리 조건 충족 여부를 판단한다.
     * 
     * @param stone 판정할 돌의 색깔
     * @param count 한 직선에서 연속된 돌의 개수
     * @return 흑은 정확히 5개, 백은 5개 이상이면 true, 그 외에는 false
     */
    private boolean isWinningCondition(Stone stone, int count) {
        return (stone == Stone.BLACK && count == 5)
            || (stone == Stone.WHITE && count >= 5);
    }

    /**
     * 연속된 돌의 개수를 기준으로 장목 여부를 판단한다.
     *
     * @param count 연속된 돌의 개수
     * @return count가 6이상이면 true, 그 외에는 false
     */
    private boolean isOverline(int count) {
        if (count >= 6) return true;
        else return false;
    }
}
