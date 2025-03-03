
import { ToastContainer } from 'react-toastify'
import './App.css'
import AllRoutes from './Routes/AllRoutes'

function App() {
 
  return (

    <>
   <AllRoutes/>
   <ToastContainer position="top-right" autoClose={3000} hideProgressBar />
    </>
  )
}

export default App
