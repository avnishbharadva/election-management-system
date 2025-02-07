import React from 'react'
import Sidebar from '../ui/Sidebar'

const AddVoter = () => {
  return (
    <div style={{ display: "flex", minHeight: "100vh" }}>
      <Sidebar />
      <div style={{ flex: 1, padding: "1rem" }}>
        <h2>Add Voter</h2>
      </div>
    </div>
  )
}

export default AddVoter