const stones = [
  { row: 7, col: 7, color: 'black' },
  { row: 7, col: 8, color: 'white' },
  { row: 8, col: 8, color: 'black' },
  { row: 6, col: 6, color: 'white' },
  { row: 9, col: 9, color: 'black' },
  { row: 6, col: 8, color: 'white' },
  { row: 8, col: 7, color: 'black' },
]

export default function Board() {
  return (
    <svg className="board" viewBox="0 0 480 480" role="img" aria-label="15줄 오목판과 예시 흑돌 4개, 백돌 3개">
      <rect className="board-surface" width="480" height="480" rx="10" />
      {Array.from({ length: 15 }, (_, i) => {
        const position = 30 + i * 30
        return (
          <g key={i} className="board-line">
            <line x1="30" y1={position} x2="450" y2={position} />
            <line x1={position} y1="30" x2={position} y2="450" />
          </g>
        )
      })}
      {[3, 7, 11].flatMap((row) => [3, 7, 11].map((col) => (
        <circle key={`${row}-${col}`} className="star-point" cx={30 + col * 30} cy={30 + row * 30} r="3" />
      )))}
      {stones.map(({ row, col, color }) => (
        <circle key={`${row}-${col}`} className={`board-stone ${color}`} cx={30 + col * 30} cy={30 + row * 30} r="13" />
      ))}
      <circle className="last-move" cx="240" cy="270" r="3" />
    </svg>
  )
}
