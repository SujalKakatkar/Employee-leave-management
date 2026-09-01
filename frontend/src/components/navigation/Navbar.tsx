import { ThemeToggle } from '../Themetoggle'
import { Menu } from 'lucide-react'


function Navbar() {
  return (
    <div className="w-full sticky top-0 h-20  flex justify-center px-fib-13 pt-fib-21">
      <nav className="w-full max-w-4xl flex items-center justify-between rounded-full border border-border bg-card px-fib-21 py-fib-8 shadow-sm">

        {/* Logo — left */}
        <a href="/" className="text-fib-21 font-bold text-foreground">
          TimeOff
        </a>

        {/* Links — center (desktop) */}
        <ul className="hidden md:flex items-center gap-fib-21">
          <li><a href="/product" className="text-fib-13 text-muted-foreground hover:text-foreground transition-colors">Product</a></li>
          <li><a href="/features" className="text-fib-13 text-muted-foreground hover:text-foreground transition-colors">Features</a></li>
          <li><a href="/pricing" className="text-fib-13 text-muted-foreground hover:text-foreground transition-colors">Pricing</a></li>
          <li><a href="/resources" className="text-fib-13 text-muted-foreground hover:text-foreground transition-colors">Resources</a></li>
        </ul>

        {/* Right side — desktop */}
        <div className="hidden md:flex items-center gap-fib-8">
         
         <ThemeToggle/>


          <a
            href="/auth/signin"
            className="rounded-full bg-primary px-fib-13 py-fib-5 text-fib-13 text-primary-foreground hover:opacity-90 transition-opacity"
          >
            Sign in
          </a>
        </div>

        {/* Hamburger — mobile only */}
        <button
          type="button"
          aria-label="Open menu"
          className="md:hidden p-fib-5 rounded-full text-foreground hover:bg-accent transition-colors"
        >
          <Menu className="size-fib-21" />
        </button>

      </nav >
    </div >
  )
}

export default Navbar