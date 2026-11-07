import Board from '../components/Board'
import GameInfo from '../components/GameInfo'
import type { GameResult } from '../components/GameResultDialog'
import GameConnectionNotice from '../components/GameConnectionNotice'
import type { GameConnection } from '../components/GameConnectionNotice'

type GamePageProps = {
  result?: GameResult
  connection?: GameConnection
  onReconnect?: () => void
}

export default function GamePage({ result, connection = { status: 'connected' }, onReconnect }: GamePageProps) {
  return (
    <main className="game-main" aria-label="오목 대국">
      {!result && <GameConnectionNotice connection={connection} onReconnect={onReconnect} />}
      <div className="game-content">
        <section className="board-panel" aria-label="대국판">
          <Board />
        </section>
        <GameInfo result={result} connection={connection} />
      </div>
    </main>
  )
}
