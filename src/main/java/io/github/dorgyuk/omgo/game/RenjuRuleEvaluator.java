package io.github.dorgyuk.omgo.game;

import java.util.HashSet;
import java.util.Set;

public class RenjuRuleEvaluator {
    /**
     * 착수 전 보드에 가상으로 돌을 놓아 승리 여부를 평가한다.
     * 원본 보드는 변경하지 않는다.
     * 
     * 흑은 정확히 5개, 백은 5개 이상의 돌이 연속되면 승리로 판정한다.
     * 흑의 장목과 4·4는 금수지만, 정확한 5목 승리를 우선한다.
     * 
     * @param board 착수 전 보드
     * @param position 착수할 위치
     * @param stone 놓을 돌의 색깔
     * @return 승리 조건을 만족하면 WIN, 구현된 금수 조건이면 FORBIDDEN, 그 외에는 LEGAL
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
            if ((stone == Stone.BLACK && count == 5) ||
                (stone == Stone.WHITE && count >= 5)) {
                return MoveEvaluation.WIN;
            }
            if (stone == Stone.BLACK && count >= 6) {
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
        int fours = 0;
        for(int[] direction : directions) {
            fours += countFours(boardAfterMove, position, direction[0], direction[1]);    
        }
        if (fours >= 2) return MoveEvaluation.FORBIDDEN;

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
     * 착수 후 보드에서 지정한 방향으로 판정 대상 흑돌을 포함하는 서로 다른 4의 개수를 센다.
     * 빈칸에 흑돌을 가상 착수하여, 판정 대상 돌을 포함하는 정확한 5목이 완성되는지 확인한다.
     * 
     * @param boardAfterMove 판정 대상 위치에 흑돌이 놓인 착수 후 보드
     * @param position 4의 생성 여부를 판정할 흑돌의 위치
     * @param dr 탐색 방향의 행 변화량 (-1, 0, 1 중 하나)
     * @param dc 탐색 방향의 열 변화량 (-1, 0, 1 중 하나)
     * @return 서로 다른 4의 개수
     */
    int countFours(Board boardAfterMove, Position position, int dr, int dc) {
        Set<Set<Position>> fours = new HashSet<>();

        // dr, dc의 방향으로 poon기준 -4, +4 범위에서 탐색
        for (int offset = -4; offset <= 4; offset++) {
            Position candidate = new Position(position.row() + offset * dr,
                                              position.column() + offset * dc);
            if (boardAfterMove.contains(candidate) &&
                boardAfterMove.stoneAt(candidate) == Stone.EMPTY) {
                Board candidateBoard = boardAfterMove.withStone(candidate, Stone.BLACK);
                Set<Position> stones = getConnectedStones(candidateBoard, candidate, dr, dc);
                if (stones.size() == 5 && stones.contains(position)) {
                    stones.remove(candidate);
                    fours.add(stones);
                }
            }
        }        

        return fours.size();
    }

    /**
     * 후보 위치를 중심으로 지정한 지선의 양방향에 연속된 흑돌 좌표를 수집한다.
     * 보드 경계, 빈칸 또는 백돌을 만나면 해당 방향의 탐색을 종료한다.
     * 원본 보드는 변경하지 않는다.
     * 
     * @param candidateBoard 후보위치에 흑돌을 가상 착수한 보드
     * @param candidate 연결된 흑돌을 탐색하기 위해 가상 착수된 돌의 위치
     * @param dr 탐색 방향의 행 변화량 (-1, 0, 1 중 하나) 
     * @param dc 탐색 방향의 열 변화량 (-1, 0, 1 중 하나)
     * @return 중심 위치를 포함한 양방향의 연속된 흑돌 좌표 집합
     */
    private Set<Position> getConnectedStones (Board candidateBoard, Position candidate, int dr, int dc) {
        Set<Position> stones = new HashSet<>();
        stones.add(candidate);
        
        for (int sign : new int[] {1, -1}) {
            int stepR = sign * dr;
            int stepC = sign * dc;
            
            Position cursor = new Position(candidate.row() + stepR, candidate.column() + stepC);
            while(candidateBoard.contains(cursor) && candidateBoard.stoneAt(cursor) == Stone.BLACK) {
                stones.add(cursor);
                cursor = new Position(cursor.row() + stepR, cursor.column() + stepC);
            }
        }

        return stones;
    }
}
