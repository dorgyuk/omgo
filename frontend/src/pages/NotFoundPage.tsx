import './NotFoundPage.css'

export default function NotFoundPage() {
  return (
    <main className="not-found-main">
      <section className="not-found-content" aria-labelledby="not-found-title">
        <p className="not-found-code">404</p>
        <h1 id="not-found-title">페이지를 찾을 수 없어요</h1>
        <p className="not-found-description">주소가 잘못되었거나 더 이상 존재하지 않는 페이지예요.</p>
      </section>
    </main>
  )
}
