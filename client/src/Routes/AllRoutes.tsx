import { Route, Routes } from 'react-router-dom'
import Login from '../components/pages/Login'
import AddCandidate from '../components/pages/AddCandidate'
import Cards from '../components/ui/Cards'
import AddVoter from '../components/pages/AddVoter'
import AddParty from '../components/pages/AddParty'
import AddElection from '../components/pages/AddElection'
import Layout from '../components/ui/Layout'

const AllRoutes = () => {
  return (
    <>   
    <Routes>
    
        <Route path="/" element={<Layout />}>
        <Route path='/dashboard' element={<Cards/>}></Route>
        <Route path='/candidate' element={<AddCandidate/>}></Route>
        <Route path='/voter' element={<AddVoter/>}></Route>
        <Route path='/party' element={<AddParty/>}></Route>
        <Route path='/election' element={<AddElection/>}></Route>
        </Route>
        <Route  path='/login' element={<Login/>}></Route>
        
        
    </Routes>
    </>
  )
}

export default AllRoutes