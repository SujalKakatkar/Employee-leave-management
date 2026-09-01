import { Footer } from '@/components/navigation/Footer'
import Navbar from '@/components/navigation/Navbar'
import { Outdent } from 'lucide-react'
import React from 'react'
import { Outlet } from 'react-router'

function Herolayout() {
  return (
    <>
    <Navbar/>
    <Outlet/>
    <Footer/>
    </>
  )
}

export default Herolayout