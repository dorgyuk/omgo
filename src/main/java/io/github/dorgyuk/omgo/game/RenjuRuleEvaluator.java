package io.github.dorgyuk.omgo.game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 렌주 규칙에 따라 착수의 승리 및 흑의 금수 여부를 평가한다.
 *
 * <p>흑의 정확한 5목 승리는 장목, 4·4, 3·3 금수보다 우선하며, 백은 5목 이상이면 승리한다.
 * 가상 착수에는 새로운 보드를 사용하므로 입력 보드는 변경하지 않는다.
 */
public class RenjuRuleEvaluator {
    /**
     * 착수 전 보드에 돌을 가상 착수하여 승리, 금수 또는 일반 착수로 평가한다.
     *
     * <p>흑은 정확히 5개, 백은 5개 이상의 돌이 연속되면 승리한다.
     * 흑의 장목, 4·4, 3·3은 금수지만, 정확한 5목 승리를 우선한다.
     * 백에게는 금수가 없으며, 원본 보드는 변경하지 않는다.
     * 
     * @param board 착수 전 보드
     * @param position 착수할 위치
     * @param stone 놓을 돌의 색깔
     * @return 승리하면 {@link MoveEvaluation#WIN}, 흑의 금수이면
     *         {@link MoveEvaluation#FORBIDDEN}, 그 외에는 {@link MoveEvaluation#LEGAL}
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
        
        // 승리 조건 확인
        for (int[] direction : directions) {
            int count = countLine(boardAfterMove, position, stone, direction[0], direction[1]);
            if ((stone == Stone.BLACK && count == 5) 
                || (stone == Stone.WHITE && count >= 5)) {
                return MoveEvaluation.WIN;
            }
        }
        
        // 백은 금수가 없음
        if (stone == Stone.WHITE) {
            return MoveEvaluation.LEGAL;
        }
        
        // 흑 금수 확인
        if (isForbiddenMove(boardAfterMove, position)) {
            return MoveEvaluation.FORBIDDEN;
        }
        
        return MoveEvaluation.LEGAL;
    }
    
    /**
     * 지정한 위치를 중심으로 한 직선의 양방향에서 같은 색의 연속된 돌을 센다.
     *
     * <p>중심 위치에는 이미 해당 색의 돌이 놓여 있어야 한다.
     * 보드 경계, 빈칸 또는 다른 색의 돌을 만나면 해당 방향의 탐색을 종료한다.
     * 방향 벡터 {@code (dr, dc)}는 가로, 세로 또는 대각선을 나타내며 둘 다 0일 수 없다.
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
     *
     * <p>빈칸에 흑돌을 가상 착수하여, 판정 대상 돌을 포함하는 정확한 5목이 완성되는지 확인한다.
     * 동일한 네 돌을 양 끝에서 5목으로 완성할 수 있어도 하나의 4로 센다.
     * 
     * @param boardAfterMove 판정 대상 위치에 흑돌이 놓인 착수 후 보드
     * @param position 4의 생성 여부를 판정할 흑돌의 위치
     * @param dr 탐색 방향의 행 변화량 (-1, 0, 1 중 하나)
     * @param dc 탐색 방향의 열 변화량 (-1, 0, 1 중 하나)
     * @return 서로 다른 4의 개수
     */
    int countFours(Board boardAfterMove, Position position, int dr, int dc) {
        return findFours(boardAfterMove, position, dr, dc).size();
    }

    /**
     * 지정한 방향에서 판정 대상 흑돌을 포함하는 4와 각 4를 정확한 5목으로 완성하는 빈칸을 찾는다.
     *
     * <p>판정 대상 위치의 앞뒤 4칸 이내에 가상 착수하여 정확한 5목을 확인한다.
     * 반환 맵의 키는 기존 네 흑돌의 좌표 집합이고, 값은 그 4를 완성하는 착수 위치의 집합이다.
     * 따라서 같은 네 돌을 완성하는 후보들은 하나의 키로 묶이며,
     * 완성 위치가 두 개인 4는 열린 4에 해당한다.
     *
     * @param boardAfterMove 판정 대상 위치에 흑돌이 놓인 착수 후 보드
     * @param position 4에 포함되어야 하는 흑돌의 위치
     * @param dr 탐색 방향의 행 변화량 (-1, 0, 1 중 하나)
     * @param dc 탐색 방향의 열 변화량 (-1, 0, 1 중 하나)
     * @return 네 흑돌의 좌표 집합을 키로, 정확한 5목을 완성하는 빈칸의 집합을 값으로 갖는 맵
     */
    private Map<Set<Position>, Set<Position>> findFours(Board boardAfterMove, Position position, int dr, int dc) {
        Map<Set<Position>, Set<Position>> fours = new HashMap<>();

        for (int offset = -4; offset <= 4; offset++) {
            Position candidate = new Position(position.row() + dr * offset,
                                              position.column() + dc * offset);
            if (boardAfterMove.contains(candidate) && boardAfterMove.stoneAt(candidate) == Stone.EMPTY) {
                Board candidateBoard = boardAfterMove.withStone(candidate, Stone.BLACK);
                Set<Position> stones = getConnectedStones(candidateBoard, candidate, dr, dc);

                if (stones.size() == 5 && stones.contains(position)) {
                    stones.remove(candidate);
                    fours.computeIfAbsent(stones,  key -> new HashSet<>()).add(candidate);
                }
            }
        }

        return fours;
    }

    /**
     * 지정한 방향에서 판정 대상 흑돌을 포함하는 서로 다른 유효한 3의 개수를 센다.
     *
     * <p>한 돌을 추가해 원래의 세 돌을 포함하는 열린 4를 만들 수 있어야 한다.
     * 이 확장 착수가 동시에 정확한 5목을 만들거나 금수가 되면 해당 후보를 제외한다.
     * 다른 유효한 확장 후보가 하나라도 있으면 그 3은 인정하며,
     * 같은 세 돌은 여러 후보로 확장할 수 있어도 한 번만 센다.
     *
     * <p>확장 후보가 금수 3·3인지 확인할 때는 {@link #isForbiddenMove(Board, Position)}를
     * 통해 후보 위치에서 생기는 3의 유효성을 재귀적으로 검사한다.
     *
     * @param boardAfterMove 판정 대상 위치에 흑돌이 놓인 착수 후 보드
     * @param position 3에 포함되어야 하는 흑돌의 위치
     * @param dr 탐색 방향의 행 변화량 (-1, 0, 1 중 하나)
     * @param dc 탐색 방향의 열 변화량 (-1, 0, 1 중 하나)
     * @return 서로 다른 유효한 3의 개수
     */
    int countThrees(Board boardAfterMove, Position position, int dr, int dc) {
        Set<Set<Position>> threes = new HashSet<>();
        for (int offset = -3; offset <= 3; offset++) {
            Position candidate = new Position(position.row() + dr * offset,
                                              position.column() + dc * offset);
            if (boardAfterMove.contains(candidate) && boardAfterMove.stoneAt(candidate) == Stone.EMPTY) {
                Board candidateBoard = boardAfterMove.withStone(candidate, Stone.BLACK);
                Map<Set<Position>, Set<Position>> fours = findFours(candidateBoard, candidate, dr, dc);
                
                for (Map.Entry<Set<Position>, Set<Position>> entry : fours.entrySet()) {
                    Set<Position> stones = entry.getKey();
                    Set<Position> winningMoves = entry.getValue();

                    // 열린 4면 threes에 후보를 제외한 돌들 추가
                    if (winningMoves.size() == 2 
                        && stones.contains(position) 
                        && !didAttainFive(candidateBoard, candidate) 
                        && !isForbiddenMove(candidateBoard, candidate)) {
                        Set<Position> three = new HashSet<>(stones);
                        three.remove(candidate);
                        threes.add(three);
                    }

                }
            }
        }
        return threes.size();
    }

    /**
     * 흑의 착수 위치를 중심으로 네 방향의 장목, 4·4, 금수 3·3 여부를 확인한다.
     *
     * <p>판정 위치에는 이미 흑돌이 놓여 있어야 한다.
     * 어느 방향이든 정확한 5목이 있으면 다른 금수 형태와 관계없이 {@code false}를 반환한다.
     * 정확한 5목이 없으면 장목, 서로 다른 4의 합계, 유효한 3의 합계 순으로 검사한다.
     * 유효한 3의 개수는 {@link #countThrees(Board, Position, int, int)}로 재귀적으로 확인한다.
     *
     * @param boardAfterMove 판정 위치에 흑돌이 놓인 착수 후 보드
     * @param position 금수 여부를 판정할 흑돌의 위치
     * @return 금수이면 {@code true}, 정확한 5목이 있거나 금수가 아니면 {@code false}
     */
    private boolean isForbiddenMove(Board boardAfterMove, Position position) {
        int[][] directions = {
            {0, 1},
            {1, 0},
            {1, 1},
            {-1, 1}
        };
        
        boolean hasOverline = false;
        for(int[] direction : directions) {
            int count = countLine(boardAfterMove, position, Stone.BLACK, direction[0], direction[1]);
            
            // 장목 체크
            if (count == 5) {
                return false;
            } else if (count >= 6) {
                hasOverline = true;
            }
        }
        if (hasOverline) {
            return true;
        }
        
        // 4·4 금수 확인
        int fours = 0;
        for(int[] direction : directions) {
            fours += countFours(boardAfterMove, position, direction[0], direction[1]);
            if (fours >= 2) return true;
        }
        
        // 3·3 금수 확인
        int threes = 0;
        for(int[] direction : directions) {
            threes += countThrees(boardAfterMove, position, direction[0], direction[1]);
            if (threes >= 2) return true;
        }
        
        return false;        
    }

    /**
     * 지정한 흑돌을 포함하는 정확한 5목이 네 방향 중 하나라도 있는지 확인한다.
     *
     * <p>판정 위치에는 이미 흑돌이 놓여 있어야 하며, 장목은 5목으로 인정하지 않는다.
     * 3을 열린 4로 만드는 확장 착수가 동시에 5목을 만드는지 확인할 때 사용한다.
     *
     * @param boardAfterMove 판정 위치에 흑돌이 놓인 착수 후 보드
     * @param position 정확한 5목에 포함되어야 하는 흑돌의 위치
     * @return 어느 방향이든 정확히 5개가 연속되면 {@code true}, 그 외에는 {@code false}
     */
    private boolean didAttainFive(Board boardAfterMove, Position position) {
        int[][] directions = {
           {0, 1},
           {1, 0},
           {1, 1},
           {-1, 1}
        };

        for(int[] direction : directions) {
            int count = countLine(boardAfterMove, position, Stone.BLACK, direction[0], direction[1]);
            if (count == 5) return true;
            
        }
        return false;
    }

    /**
     * 후보 위치를 중심으로 지정한 직선의 양방향에 연속된 흑돌 좌표를 수집한다.
     *
     * <p>후보 위치에는 이미 흑돌이 놓여 있어야 한다.
     * 보드 경계, 빈칸 또는 백돌을 만나면 해당 방향의 탐색을 종료한다.
     * 반환 집합에는 후보 위치 자체가 포함되며, 원본 보드는 변경하지 않는다.
     * 
     * @param candidateBoard 후보 위치에 흑돌을 가상 착수한 보드
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
