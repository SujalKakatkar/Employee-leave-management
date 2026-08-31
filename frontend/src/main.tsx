import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import { RouterProvider } from 'react-router'
import { routes } from './routes/routes.tsx'
import { ThemeProvider } from './components/theme-provider.tsx'
import Root from './Root.tsx'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
   <Root/>
  </StrictMode>,
)
