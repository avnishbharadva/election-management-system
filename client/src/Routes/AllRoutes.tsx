import React from 'react'
import { Route, Routes } from 'react-router-dom'
import Login from '../components/pages/Login'
import AddCandidate from '../components/pages/AddCandidate'
import Cards from '../components/ui/Cards'
import Navbar from '../components/ui/Navbar'
import AddVoter from '../components/pages/AddVoter'
import AddParty from '../components/pages/AddParty'
import AddElection from '../components/pages/AddElection'

const AllRoutes = () => {
  return (
    
    <>
    <Navbar/>

    <Routes>
        <Route path='/' element={<Login/>}></Route>
        <Route path='/dashboard/cards' element={<Cards/>}></Route>
        <Route path='/dashboard/candidate' element={<AddCandidate/>}></Route>
        <Route path='/dashboard/voter' element={<AddVoter/>}></Route>
        <Route path='/dashboard/party' element={<AddParty/>}></Route>
        <Route path='/dashboard/election' element={<AddElection/>}></Route>
        
    </Routes>
    </>
  )
}

export default AllRoutes