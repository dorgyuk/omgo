import './GameConnectionNotice.css'

export type GameConnection =
  | { status: 'connected' }
  | { status: 'disconnected' | 'reconnecting' | 'opponent-disconnected'; remainingSeconds: number }
  | { status: 'syncing' }

type GameConnectionNoticeProps = {
  connection: GameConnection
  onReconnect?: () => void
}

const messages = {
  disconnected: {
    title: '대국 서버와 연결이 끊겼어요',
    description: '인터넷 연결을 확인해 주세요. 60초 안에 복귀하면 대국을 이어갈 수 있어요.',
  },
  reconnecting: {
    title: '대국에 다시 연결하고 있어요',
    description: '잠시만 기다려 주세요. 연결이 복구되면 현재 대국 상태를 불러옵니다.',
  },
  syncing: {
    title: '현재 대국 상태를 불러오고 있어요',
    description: '오목판과 남은 시간을 확인하고 있습니다. 준비가 끝나면 대국을 이어갈 수 있어요.',
  },
  'opponent-disconnected': {
    title: '상대의 연결이 끊겼어요',
    description: '상대가 돌아오기를 기다리고 있어요. 복귀 제한 시간은 최대 60초입니다.',
  },
}

export default function GameConnectionNotice({ connection, onReconnect }: GameConnectionNoticeProps) {
  if (connection.status === 'connected') return null

  const message = messages[connection.status]
  // 남은 시간은 서버 상태로 전달받는다. 이 UI에서 패배나 시간 만료를 판정하지 않는다.
  const remaining = 'remainingSeconds' in connection ? Math.max(0, Math.ceil(connection.remainingSeconds)) : null

  return (
    <section className={`connection-notice connection-${connection.status}`} aria-label="대국 연결 상태">
      <div className="connection-message" role="status">
        <h2>{message.title}</h2>
        <p>{message.description}</p>
        <p className="connection-time-note">연결이 끊겨도 대국 시간은 계속 흐릅니다.</p>
      </div>
      <div className="connection-details">
        {remaining !== null && (
          <div className="connection-countdown">
            <span>복귀 가능 남은 시간</span>
            <strong>{remaining}<small>초</small></strong>
            {remaining === 0 && <span>대국 결과 확인 중</span>}
          </div>
        )}
        {connection.status === 'disconnected' && (
          <button className="secondary-button" type="button" onClick={onReconnect} disabled={!onReconnect}>다시 연결</button>
        )}
      </div>
    </section>
  )
}
