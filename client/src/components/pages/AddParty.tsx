import React from 'react'
import Sidebar from '../ui/Sidebar'

const AddParty = () => {
  return (
<div style={{ display: "flex", minHeight: "100vh" }}>
      <Sidebar />
      <div style={{ flex: 1, padding: "1rem" }}>
        <h2>Add Party</h2>
      </div>
    </div>
  )
}

export default AddParty