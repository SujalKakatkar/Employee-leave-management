import Navbar from '@/components/Navbar'
import { Outdent } from 'lucide-react'
import React from 'react'
import { Outlet } from 'react-router'

function Herolayout() {
  return (
    <>
    <Navbar/>
    <Outlet/>
    </>
  )
}

export default Herolayout