import { ThemeProvider } from './components/theme-provider'
import { RouterProvider } from 'react-router'
import { routes } from './routes/routes'

function Root() {



    return (
        <ThemeProvider >
            {/* dark mode fliker needs to fix */}
            <RouterProvider router={routes} />
        </ThemeProvider >
    )
}

export default Root