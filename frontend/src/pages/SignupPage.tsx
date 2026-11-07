import { useRef, useState } from 'react'
import type { SubmitEvent } from 'react'
import { Link } from 'react-router'
import './SignupPage.css'

export default function SignupPage() {
  const [passwordError, setPasswordError] = useState('')
  const [submitMessage, setSubmitMessage] = useState('')
  const confirmationRef = useRef<HTMLInputElement>(null)

  function handleSubmit(event: SubmitEvent<HTMLFormElement>) {
    event.preventDefault()
    setSubmitMessage('')
    const values = new FormData(event.currentTarget)

    if (values.get('password') !== values.get('passwordConfirmation')) {
      setPasswordError('비밀번호가 일치하지 않아요.')
      confirmationRef.current?.focus()
      return
    }

    setPasswordError('')
    setSubmitMessage('아직 회원가입을 처리할 수 없습니다. 가입 기능을 준비 중이에요.')
  }

  function clearFeedback() {
    setPasswordError('')
    setSubmitMessage('')
  }

  return (
    <main className="signup-main">
      <section className="signup-card" aria-labelledby="signup-title">
        <div className="signup-heading">
          <h1 id="signup-title">omgo에 오신 걸 환영해요</h1>
          <p>나만의 한 수, 함께 시작해요.</p>
        </div>

        <form className="signup-form" onSubmit={handleSubmit} onChange={clearFeedback}>
          <div className="signup-field">
            <label htmlFor="signup-email">이메일</label>
            <input id="signup-email" name="email" type="email" autoComplete="email" placeholder="you@example.com" required />
          </div>
          <div className="signup-field">
            <label htmlFor="signup-password">비밀번호</label>
            <input id="signup-password" name="password" type="password" autoComplete="new-password" placeholder="비밀번호를 입력해 주세요" required />
          </div>
          <div className="signup-field">
            <label htmlFor="signup-password-confirmation">비밀번호 확인</label>
            <input ref={confirmationRef} id="signup-password-confirmation" name="passwordConfirmation" type="password" autoComplete="new-password" placeholder="비밀번호를 한 번 더 입력해 주세요" required aria-invalid={passwordError ? true : undefined} aria-describedby={passwordError ? 'signup-password-error' : undefined} />
            {passwordError && <p id="signup-password-error" className="signup-error" role="alert">{passwordError}</p>}
          </div>
          <div className="signup-field">
            <label htmlFor="signup-nickname">닉네임</label>
            <input id="signup-nickname" name="nickname" type="text" autoComplete="nickname" placeholder="대국에서 사용할 이름" minLength={2} maxLength={12} pattern="[가-힣a-zA-Z0-9]{2,12}" title="한글 완성형, 영문, 숫자로 2~12자를 입력해 주세요." aria-describedby="signup-nickname-hint" required />
            <p id="signup-nickname-hint" className="signup-hint">한글, 영문, 숫자 2~12자 · 가입 후 변경할 수 없어요.</p>
          </div>

          <button className="primary-button signup-submit" type="submit">회원가입</button>
          {submitMessage && <p className="signup-submit-message" role="status">{submitMessage}</p>}
        </form>

        <div className="signup-divider"><span>또는</span></div>

        <button className="signup-google" type="button" disabled>
          <svg viewBox="0 0 24 24" width="18" height="18" aria-hidden="true">
            <path fill="#4285f4" d="M21.6 12.2c0-.7-.1-1.4-.2-2.1H12v4h5.4a4.7 4.7 0 0 1-2 3.1v2.6h3.3c1.9-1.8 2.9-4.4 2.9-7.6Z" />
            <path fill="#34a853" d="M12 22c2.7 0 5-.9 6.7-2.4l-3.3-2.6c-.9.6-2 .9-3.4.9-2.6 0-4.8-1.7-5.6-4H3v2.7A10 10 0 0 0 12 22Z" />
            <path fill="#fbbc05" d="M6.4 13.9a6 6 0 0 1 0-3.8V7.4H3a10 10 0 0 0 0 9.2l3.4-2.7Z" />
            <path fill="#ea4335" d="M12 6.1c1.5 0 2.8.5 3.8 1.5l2.9-2.8A9.6 9.6 0 0 0 12 2a10 10 0 0 0-9 5.4l3.4 2.7c.8-2.3 3-4 5.6-4Z" />
          </svg>
          Google 계정으로 로그인
        </button>

        <div className="signup-login">이미 계정이 있나요? <Link to="/signin">로그인</Link></div>
      </section>
    </main>
  )
}
