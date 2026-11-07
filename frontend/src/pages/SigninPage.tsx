import { useState } from 'react'
import type { SubmitEvent } from 'react'
import { Link } from 'react-router'
import './SigninPage.css'

export default function SigninPage() {
  const [submitMessage, setSubmitMessage] = useState('')

  function handleSubmit(event: SubmitEvent<HTMLFormElement>) {
    event.preventDefault()
    setSubmitMessage('아직 로그인을 처리할 수 없습니다. 로그인 기능을 준비 중이에요.')
  }

  return (
    <main className="signin-main">
      <section className="signin-card" aria-labelledby="signin-title">
        <div className="signin-heading">
          <h1 id="signin-title">다시 만나 반가워요</h1>
          <p>로그인하고 다음 한 수를 이어가세요.</p>
        </div>

        <form className="signin-form" onSubmit={handleSubmit} onChange={() => setSubmitMessage('')}>
          <div className="signin-field">
            <label htmlFor="signin-email">이메일</label>
            <input id="signin-email" name="email" type="email" autoComplete="username" placeholder="you@example.com" required />
          </div>
          <div className="signin-field">
            <label htmlFor="signin-password">비밀번호</label>
            <input id="signin-password" name="password" type="password" autoComplete="current-password" placeholder="비밀번호를 입력해 주세요" required />
          </div>

          <button className="primary-button signin-submit" type="submit">로그인</button>
          {submitMessage && <p className="signin-submit-message" role="status">{submitMessage}</p>}
        </form>

        <div className="signin-divider"><span>또는</span></div>

        <button className="signin-google" type="button" disabled>
          <svg viewBox="0 0 24 24" width="18" height="18" aria-hidden="true">
            <path fill="#4285f4" d="M21.6 12.2c0-.7-.1-1.4-.2-2.1H12v4h5.4a4.7 4.7 0 0 1-2 3.1v2.6h3.3c1.9-1.8 2.9-4.4 2.9-7.6Z" />
            <path fill="#34a853" d="M12 22c2.7 0 5-.9 6.7-2.4l-3.3-2.6c-.9.6-2 .9-3.4.9-2.6 0-4.8-1.7-5.6-4H3v2.7A10 10 0 0 0 12 22Z" />
            <path fill="#fbbc05" d="M6.4 13.9a6 6 0 0 1 0-3.8V7.4H3a10 10 0 0 0 0 9.2l3.4-2.7Z" />
            <path fill="#ea4335" d="M12 6.1c1.5 0 2.8.5 3.8 1.5l2.9-2.8A9.6 9.6 0 0 0 12 2a10 10 0 0 0-9 5.4l3.4 2.7c.8-2.3 3-4 5.6-4Z" />
          </svg>
          Google 계정으로 로그인
        </button>

        <div className="signin-signup">아직 계정이 없나요? <Link to="/signup">회원가입</Link></div>
      </section>
    </main>
  )
}
