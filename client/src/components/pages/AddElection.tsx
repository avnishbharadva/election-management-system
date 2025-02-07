import React from 'react'
import Sidebar from '../ui/Sidebar'

const AddElection = () => {
  return (
    <div style={{ display: "flex", minHeight: "100vh" }}>
    <Sidebar />
    <div style={{ flex: 1, padding: "1rem" }}>
      <h2>Add Election</h2>
    </div>
  </div>
  )
}

export default AddElection