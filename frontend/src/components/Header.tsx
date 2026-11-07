import { Link } from 'react-router'

type HeaderProps = {
  showAccount?: boolean
  nickname?: string
}

export default function Header({ showAccount = false, nickname }: HeaderProps) {
  return (
    <header className="site-header">
      <div className="header-content">
        <Link className="brand" to="/" aria-label="omgo 홈">omgo</Link>
        {showAccount && (
          <div className="header-account">
            {nickname ? (
              <><span className="account-avatar" aria-hidden="true">오</span><span>{nickname}</span></>
            ) : (
              <>
                <Link className="header-login" to="/signin">로그인</Link>
                <Link className="header-signup" to="/signup">회원가입</Link>
              </>
            )}
          </div>
        )}
      </div>
    </header>
  )
}
