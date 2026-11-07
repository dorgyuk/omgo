import { useState } from 'react'
import type { SubmitEvent } from 'react'
import './NicknamePage.css'

export default function NicknamePage() {
  const [submitMessage, setSubmitMessage] = useState('')

  function handleSubmit(event: SubmitEvent<HTMLFormElement>) {
    event.preventDefault()
    setSubmitMessage('아직 닉네임을 저장할 수 없습니다. 가입 기능을 준비 중이에요.')
  }

  return (
    <main className="nickname-main">
      <section className="nickname-card" aria-labelledby="nickname-title">
        <div className="nickname-heading">
          <h1 id="nickname-title">어떤 이름으로 만날까요?</h1>
          <p>대국에서 사용할 닉네임을 정해 주세요.</p>
        </div>

        <form className="nickname-form" onSubmit={handleSubmit} onChange={() => setSubmitMessage('')}>
          <div className="nickname-field">
            <label htmlFor="nickname">닉네임</label>
            <input
              id="nickname"
              name="nickname"
              type="text"
              autoComplete="nickname"
              placeholder="대국에서 사용할 이름"
              minLength={2}
              maxLength={12}
              pattern="[가-힣a-zA-Z0-9]{2,12}"
              title="한글 완성형, 영문, 숫자로 2~12자를 입력해 주세요."
              aria-describedby="nickname-hint nickname-policy"
              required
            />
            <p id="nickname-hint" className="nickname-hint">한글, 영문, 숫자 2~12자 · 공백과 특수문자는 사용할 수 없어요.</p>
          </div>

          <p id="nickname-policy" className="nickname-policy">닉네임은 가입 후 변경할 수 없어요.<br />마음에 드는 이름을 신중하게 골라 주세요.</p>

          <button className="primary-button nickname-submit" type="submit">시작하기</button>
          {submitMessage && <p className="nickname-submit-message" role="status">{submitMessage}</p>}
        </form>
      </section>
    </main>
  )
}
