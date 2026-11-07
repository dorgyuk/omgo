import { Routes, Route, useMatch } from 'react-router'
import Header from './components/Header'
import GamePage from './pages/GamePage'
import HomePage from './pages/HomePage'
import SignupPage from './pages/SignupPage'
import SigninPage from './pages/SigninPage'
import NicknamePage from './pages/NicknamePage'
import NotFoundPage from './pages/NotFoundPage'
import './App.css'

function App() {
  const isHome = useMatch('/') !== null

  return (
    <div className="app-shell">
      <Header showAccount={isHome} />
      
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/signup" element={<SignupPage />} />
        <Route path="/signin" element={<SigninPage />} />
        <Route path="/signup/nickname" element={<NicknamePage />} />
        <Route path="/game" element={<GamePage />}/>
        <Route path="*" element={<NotFoundPage />} />
      </Routes>
    </div>
  )
}

export default App
