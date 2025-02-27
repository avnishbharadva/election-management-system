
import Model from '../ui/Model'
import ElectionForm from '../ui/ElectionForm'
import { Button } from '@mui/material'
import { useState } from 'react';

const AddElection = () => {
   const [modalData, setModalData] = useState<{ open: boolean; actionType: "add" | "edit"; selectedCandidate?: any }>({
      open: false,
      actionType: "add",
      selectedCandidate: null,
    });
  
    const handleOpenModal = (actionType: "add" | "edit", candidate?: any) => {
      setModalData({ open: true, actionType, selectedCandidate: candidate || null });
    };
  
    const handleCloseModal = () => {
      setModalData({ open: false, actionType: "add", selectedCandidate: null });
    };
  
  return (
   <>
      <h2>Add Election</h2>
       <Button variant="contained" onClick={() => handleOpenModal("add")}>
                        Add Election
                      </Button>
                      <Model open={modalData.open} handleClose={handleCloseModal} >

     
        <ElectionForm/>
      </Model>
      </>
  )
}

export default AddElection