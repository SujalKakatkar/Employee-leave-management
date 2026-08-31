import React, { createContext, useEffect, useState, useContext } from "react"

type Theme = "light" | "dark" | "system"

interface ThemeContextType {
    theme: Theme
    setTheme: (theme: Theme) => void
    toggleTheme: () => void
}

const ThemeContext = createContext<ThemeContextType | null>(null)

const STORAGE_KEY = "vite-ui-theme"

function getSystemTheme(): "light" | "dark" {
    return window.matchMedia("(prefers-color-scheme: dark)").matches ? "dark" : "light"
}

export function ThemeProvider({ children }: { children: React.ReactNode }) {
    const [theme, setThemeState] = useState<Theme>(() => {
        const saved = localStorage.getItem(STORAGE_KEY) as Theme | null
        return saved ?? "system"
    })

    useEffect(() => {
        const css = document.createElement('style')
        css.appendChild(
            document.createTextNode(
                `* {
                -webkit-transition: none !important;
                -moz-transition: none !important;
                -o-transition: none !important;
                -ms-transition: none !important;
                transition: none !important;
                }`
            )
        )
        document.head.appendChild(css)

        const resolvedTheme = theme === "system" ? getSystemTheme() : theme

        document.documentElement.classList.remove("light", "dark")
        document.documentElement.classList.add(resolvedTheme)
        localStorage.setItem(STORAGE_KEY, theme)

        // Force browser to paint the new theme without transition
        void window.getComputedStyle(css).opacity

        setTimeout(() => {
            document.head.removeChild(css)
        }, 1)
    }, [theme])

    // keep in sync with OS theme changes when "system" is selected
    useEffect(() => {
        if (theme !== "system") return

        const mediaQuery = window.matchMedia("(prefers-color-scheme: dark)")

        const handleChange = () => {
            const resolvedTheme = getSystemTheme()
            document.documentElement.classList.remove("light", "dark")
            document.documentElement.classList.add(resolvedTheme)
        }

        mediaQuery.addEventListener("change", handleChange)
        return () => mediaQuery.removeEventListener("change", handleChange)
    }, [theme])

    return (
        <ThemeContext.Provider value={{
            theme,
            setTheme: (newTheme: Theme) => setThemeState(newTheme),
            toggleTheme: () => setThemeState(theme === "dark" ? "light" : "dark")
        }}>
            {children}
        </ThemeContext.Provider>
    )
}

// eslint-disable-next-line react-refresh/only-export-components
export function useTheme() {
    const ctx = useContext(ThemeContext)
    if (!ctx) throw new Error("useTheme must be used inside ThemeProvider")
    return ctx
}