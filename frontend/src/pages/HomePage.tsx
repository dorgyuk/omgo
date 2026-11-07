import { Link } from 'react-router'
import MatchmakingCard from '../components/MatchmakingCard'
import './HomePage.css'

type HomeMember = {
  nickname: string
  wins: number
  draws: number
  losses: number
}

type HomePageProps = {
  member?: HomeMember
}

const stones = [
  { x: 3, y: 3, color: 'black' },
  { x: 4, y: 3, color: 'white' },
  { x: 4, y: 4, color: 'black' },
  { x: 5, y: 3, color: 'white' },
  { x: 5, y: 5, color: 'black' },
  { x: 3, y: 4, color: 'white' },
  { x: 6, y: 6, color: 'black' },
]

export default function HomePage({ member }: HomePageProps) {
  const totalGames = member ? member.wins + member.draws + member.losses : 0
  const winRate = member && totalGames > 0 ? `${(member.wins / totalGames * 100).toFixed(1)}%` : '—'

  return (
    <main className="home-main">
      <section className="home-hero" aria-labelledby="home-title">
        <div className="home-intro">
          <span className="home-kicker">잠깐의 여유, 한 판의 오목</span>
          <h1 id="home-title">한 수씩,<br />즐거움이 이어지는 곳.</h1>
          <p className="home-description">익숙한 바둑판 위에서 만나는 새로운 상대.<br />가볍게 시작하고, 나만의 한 수를 놓아보세요.</p>
        </div>

        <div className="home-board-art" aria-hidden="true">
          <div className="home-art-grid">
            {stones.map(({ x, y, color }) => (
              <span key={`${x}-${y}`} className={`home-art-stone ${color}`} style={{ left: `${x * 12.5}%`, top: `${y * 12.5}%` }} />
            ))}
            <span className="home-art-next" />
          </div>
        </div>
      </section>

      <div className="home-lobby">
        {member ? <MatchmakingCard /> : (
          <section className="home-play-card" aria-labelledby="play-title">
            <div className="home-card-heading">
              <span className="home-section-label">PLAY OMGO</span>
            </div>
            <h2 id="play-title">첫 한 수를 시작해 보세요</h2>
            <p>로그인하고 나면 바로 대국 상대를 찾을 수 있어요.</p>
            <Link className="primary-button home-play-button" to="/signin">로그인하고 대국 시작하기<span aria-hidden="true">↗</span></Link>
          </section>
        )}

        <section className="home-profile-card" aria-labelledby="profile-title">
          <span className="home-section-label">MY OMGO</span>
          {member ? (
            <>
              <div className="home-profile-heading">
                <span className="home-profile-avatar" aria-hidden="true">{member.nickname.slice(0, 1)}</span>
                <div>
                  <h2 id="profile-title">{member.nickname}<span>님</span></h2>
                  <p>오늘도 반가워요.</p>
                </div>
              </div>
              <dl className="home-records">
                <div><dt>전체 대국</dt><dd>{totalGames}</dd></div>
                <div><dt>승</dt><dd>{member.wins}</dd></div>
                <div><dt>무</dt><dd>{member.draws}</dd></div>
                <div><dt>패</dt><dd>{member.losses}</dd></div>
                <div className="home-win-rate"><dt>승률</dt><dd>{winRate}</dd></div>
              </dl>
            </>
          ) : (
            <>
              <h2 id="profile-title" className="home-guest-title">한 판 한 판, 나만의 기록으로.</h2>
              <p className="home-profile-description">대국을 마치면 전적이 차곡차곡 쌓여요.<br />로그인하고 나의 오목 이야기를 시작하세요.</p>
              <div className="home-empty-records" aria-hidden="true"><span>승 —</span><span>무 —</span><span>패 —</span></div>
            </>
          )}
        </section>
      </div>

    </main>
  )
}
