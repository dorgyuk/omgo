import { useEffect, useId, useRef } from 'react'
import { Link } from 'react-router'
import './GameResultDialog.css'

export type GameResult =
  | { outcome: 'win' | 'loss'; reason: 'five' | 'timeout' | 'resign' | 'disconnect' }
  | { outcome: 'draw'; reason: 'board-full' }
  | { outcome: 'void'; reason: 'interrupted' }

const outcomeLabels = { win: '승리', loss: '패배', draw: '무승부', void: '대국 무효' }

function describeResult(result: GameResult) {
  switch (result.reason) {
    case 'five': return result.outcome === 'win' ? '오목을 완성했어요.' : '상대가 오목을 완성했어요.'
    case 'timeout': return result.outcome === 'win' ? '상대의 대국 시간이 만료되었어요.' : '대국 시간이 만료되었어요.'
    case 'resign': return result.outcome === 'win' ? '상대가 기권했어요.' : '기권으로 대국을 마쳤어요.'
    case 'disconnect': return result.outcome === 'win' ? '상대가 제한 시간 안에 복귀하지 못했어요.' : '제한 시간 안에 대국에 복귀하지 못했어요.'
    case 'board-full': return '승자 없이 오목판이 모두 채워졌어요.'
    case 'interrupted': return '정상적으로 대국을 진행할 수 없어 종료되었어요.'
  }
}

type GameResultDialogProps = {
  result: GameResult
  nickname: string
  onClose: () => void
}

export default function GameResultDialog({ result, nickname, onClose }: GameResultDialogProps) {
  const dialogRef = useRef<HTMLDialogElement>(null)
  const titleId = useId()
  const descriptionId = useId()

  useEffect(() => {
    const dialog = dialogRef.current
    dialog?.showModal()
    return () => dialog?.close()
  }, [])

  return (
    <dialog
      ref={dialogRef}
      className="game-dialog result-dialog"
      aria-labelledby={titleId}
      aria-describedby={descriptionId}
      onClose={(event) => { if (!event.currentTarget.open) onClose() }}
    >
      <div className="dialog-heading">
        <span className="eyebrow">GAME RESULT</span>
        <button className="dialog-close" type="button" aria-label="결과창 닫기" onClick={onClose}>×</button>
      </div>
      <h2 id={titleId}>대국이 종료되었습니다</h2>
      <div className={`result-summary result-${result.outcome}`}>
        <span>흑 · {nickname}</span>
        <strong>{outcomeLabels[result.outcome]}</strong>
        <p id={descriptionId}>{describeResult(result)}</p>
      </div>
      {result.outcome === 'void' && <p className="dialog-description">무효 대국은 전적에 포함되지 않습니다.</p>}
      <div className="dialog-actions">
        <button className="secondary-button" type="button" onClick={onClose}>보드 보기</button>
        <Link className="primary-button" to="/">로비로 이동</Link>
      </div>
    </dialog>
  )
}
