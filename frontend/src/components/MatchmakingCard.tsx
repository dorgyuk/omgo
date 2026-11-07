import { useState } from 'react'
import './MatchmakingCard.css'

export default function MatchmakingCard() {
  const [isSearching, setIsSearching] = useState(false)

  return (
    <section className="home-play-card matchmaking-card" aria-labelledby="play-title">
      <div className="home-card-heading">
        <span className="home-section-label">PLAY OMGO</span>
        {isSearching && <span className="matchmaking-badge">매칭 중</span>}
      </div>

      <div className="matchmaking-status" role="status" aria-atomic="true">
        <h2 id="play-title">{isSearching ? '함께할 상대를 찾고 있어요' : '한 판, 시작해 볼까요?'}</h2>
        <p>{isSearching ? '상대가 정해지면 바로 대국이 시작돼요.' : '새로운 상대와 함께 나만의 한 수를 놓아보세요.'}</p>
        {isSearching && (
          <div className="matchmaking-waiting">
            <span className="matchmaking-spinner" aria-hidden="true" />
            <span>잠시만 기다려 주세요.</span>
          </div>
        )}
      </div>

      <button
        className={`${isSearching ? 'secondary-button' : 'primary-button'} home-play-button`}
        type="button"
        onClick={() => setIsSearching(!isSearching)}
      >
        {isSearching ? '매칭 취소' : '대국 찾기'}
        {!isSearching && <span aria-hidden="true">↗</span>}
      </button>
    </section>
  )
}
