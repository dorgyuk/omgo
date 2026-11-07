import { useState } from 'react'
import { Link } from 'react-router'
import GameDialog from './GameDialog'
import type { DialogKind } from './GameDialog'
import GameResultDialog from './GameResultDialog'
import type { GameResult } from './GameResultDialog'
import type { GameConnection } from './GameConnectionNotice'

type GameInfoProps = {
  result?: GameResult
  connection: GameConnection
}

export default function GameInfo({ result, connection }: GameInfoProps) {
  const [dialog, setDialog] = useState<DialogKind | null>(null)
  const [dismissedResult, setDismissedResult] = useState<GameResult | undefined>()
  const showResult = result !== undefined && result !== dismissedResult
  const isUnavailable = !result && connection.status !== 'connected' && connection.status !== 'opponent-disconnected'

  return (
    <aside className="game-sidebar" aria-label="대국 정보">
      <section className="info-panel">
        <h2 className="sr-only">대국 정보</h2>
        <div className={result ? 'player-card' : 'player-card active'}>
          <span className="player-stone white" aria-hidden="true" />
          <div><span className="player-label">백 · 상대</span><strong>Player</strong>{!result && connection.status === 'opponent-disconnected' && <span className="player-connection-label">복귀 대기 중</span>}</div>
          <div className="player-clock"><span>{result ? '종료 시 남은 시간' : '남은 시간'}</span><strong>04:32</strong></div>
        </div>
        <div className="player-card">
          <span className="player-stone black" aria-hidden="true" />
          <div><span className="player-label">흑 · 나</span><strong>오목한수</strong>{isUnavailable && <span className="player-connection-label">{connection.status === 'syncing' ? '대국 상태 확인 중' : '연결 복구 대기 중'}</span>}</div>
          <div className="player-clock"><span>{result ? '종료 시 남은 시간' : '남은 시간'}</span><strong>04:48</strong></div>
        </div>
        <div className={`turn-banner${result ? ' game-ended-banner' : ''}`}>
          {result ? (result.outcome === 'void' ? '대국이 무효 처리되었습니다' : '대국이 종료되었습니다') : <><span className="status-dot" />백의 차례입니다</>}
        </div>
      </section>

      <section className="info-panel chat-panel" aria-labelledby="chat-title">
        <div className="panel-heading"><h2 id="chat-title">채팅</h2></div>
        <ol className="chat-messages" aria-label="대국 채팅 메시지">
          <li className="chat-system">대국이 시작되었습니다.</li>
          <li><span className="chat-author">Player</span><p className="chat-bubble">안녕하세요! 잘 부탁드립니다.</p></li>
          <li className="my-message"><span className="chat-author">오목한수 · 나</span><p className="chat-bubble">반갑습니다. 즐거운 대국 해요!</p></li>
          {result && <li className="chat-system">{result.outcome === 'void' ? '대국이 무효 처리되었습니다.' : '대국이 종료되었습니다.'}</li>}
        </ol>
        <div className="chat-input-row">
          <input aria-label="채팅 메시지" placeholder={isUnavailable ? '연결 복구 후 입력할 수 있어요' : '메시지를 입력하세요'} maxLength={300} disabled={isUnavailable} />
          <button type="button" disabled>전송</button>
        </div>
      </section>

      <section className={`info-panel action-panel${result ? ' game-ended-actions' : ''}`} aria-label="대국 메뉴">
        {result && (
          <>
            <button type="button" className="primary-button game-result-button" onClick={() => setDismissedResult(undefined)}>결과 다시 보기</button>
            <Link className="secondary-button game-lobby-link" to="/">로비로 나가기</Link>
          </>
        )}
        <button type="button" className="secondary-button" disabled={!!result || isUnavailable} onClick={() => setDialog('resign')}>기권</button>
        <button type="button" className="secondary-button" disabled={isUnavailable} onClick={() => setDialog('report')}>상대 신고</button>
      </section>
      {dialog && !showResult && !isUnavailable && (dialog !== 'resign' || !result) && <GameDialog kind={dialog} onClose={() => setDialog(null)} />}
      {showResult && result && <GameResultDialog result={result} nickname="오목한수" onClose={() => { setDismissedResult(result); setDialog(null) }} />}
    </aside>
  )
}
