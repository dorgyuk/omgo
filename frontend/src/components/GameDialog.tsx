import { useEffect, useId, useRef } from 'react'

export type DialogKind = 'resign' | 'report'

const titles = {
  resign: '기권하시겠어요?',
  report: '상대 플레이어 신고',
}

export default function GameDialog({ kind, onClose }: { kind: DialogKind; onClose: () => void }) {
  const dialogRef = useRef<HTMLDialogElement>(null)
  const titleId = useId()

  useEffect(() => {
    const dialog = dialogRef.current
    dialog?.showModal()
    return () => dialog?.close()
  }, [])

  return (
    <dialog ref={dialogRef} className="game-dialog" aria-labelledby={titleId} onClose={(event) => { if (!event.currentTarget.open) onClose() }}>
      <div className="dialog-heading">
        <span className="eyebrow">GAME MENU</span>
        <button className="dialog-close" type="button" aria-label="닫기" onClick={onClose}>×</button>
      </div>
      <h2 id={titleId}>{titles[kind]}</h2>
      {kind === 'resign' && <p className="dialog-description">기권하면 현재 대국이 패배로 종료됩니다. 기권하시겠어요?</p>}
      {kind === 'report' && (
        <>
          <p className="dialog-description">신고 대상: Player</p>
          <label className="report-label" htmlFor={`${titleId}-reason`}>신고 사유</label>
          <select id={`${titleId}-reason`} defaultValue="abuse">
            <option value="abuse">욕설·비방</option>
            <option value="disruption">대국 진행 방해</option>
            <option value="other">기타</option>
          </select>
          <label className="report-label" htmlFor={`${titleId}-detail`}>상세 내용</label>
          <textarea id={`${titleId}-detail`} rows={4} maxLength={1000} placeholder="신고 내용을 입력하세요" />
        </>
      )}
      <div className="dialog-actions">
        <button type="button" className="secondary-button" onClick={onClose}>취소</button>
        <button type="button" className="primary-button" disabled>{kind === 'resign' ? '기권하기' : '신고하기'}</button>
      </div>
    </dialog>
  )
}
