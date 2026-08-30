import Authlayout from '@/layouts/Authlayout'
import Herolayout from '@/layouts/Herolayout'
import Mainlayout from '@/layouts/Mainlayout'
import EmployeeDashboardPage from '@/pages/EmployeeDashboardPage'
import HeroPage from '@/pages/HeroPage'
import LoginPage from '@/pages/LoginPage'
import SignupPage from '@/pages/SignupPage'
import { createBrowserRouter } from 'react-router'


export const routes = createBrowserRouter([
  
    {
        element: <Herolayout />,
        children: [
            {
                path: "/",
                element: <HeroPage />
            }
            //todo: also increate term and conditons page pricing
        ]
    },

    //todo:add layouts base on the role of the user
    {
        element: <Mainlayout />,
        children: [
            {
                path:"/",
                element:<EmployeeDashboardPage/>
            }
        ]

    },
    //todo:create layout without navbar where only logo is placed on the top left for auth routes
    {
        element:<Authlayout/>,
        children:[
            {
                path:"/auth/login",
                element:<LoginPage/>

            },
            {
                path:"/auth/signup",
                element:<SignupPage/>
            },
            // {
            //     path:"/auth/reset-password"
            // }
        ]
    }

])