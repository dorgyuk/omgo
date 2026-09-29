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

    /**
     * 지정한 위치를 중심으로 한 직선의 양방향에서 같은 색의 연속된 돌을 센다.
     * 보드 경계, 빈 칸, 다른 색의 돌을 만나면 해당 방향의 탐색을 종료한다.
     * 
     * @param copy 중심 위치에 해당 색의 돌이 놓인 가상 보드
     * @param position 연속된 돌을 셀 중심 위치
     * @param stone 셀 돌의 색깔
     * @param dr 탐색 방향의 행 변화량 (-1, 0, 1 중 하나)
     * @param dc 탐색 방향의 열 변화량 (-1, 0, 1 중 하나)
     * @return 중심 위치를 포함한 양방향의 같은 색 연속 돌 개수
     */
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

    /**
     * 돌의 색깔과 연속된 개수를 기준으로 승리 조건 충족 여부를 판단한다.
     * 
     * @param stone 판정할 돌의 색깔
     * @param count 한 직선에서 연속된 돌의 개수
     * @return 흑은 정확히 5개, 백은 5개 이상이면 true, 그 외에는 false
     */
    private boolean isWinningCount(Stone stone, int count) {
        return (stone == Stone.BLACK && count == 5)
            || (stone == Stone.WHITE && count >= 5);
    }
}
