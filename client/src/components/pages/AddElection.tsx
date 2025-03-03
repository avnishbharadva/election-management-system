import { useState } from "react";
import { Button, Modal, Box } from "@mui/material";
import ElectionForm from "../ui/ElectionForm";
import ElectionData from "../ui/ElectionData";

const AddElection = () => {
  const [open, setOpen] = useState(false);
  const [selectedElection, setSelectedElection] = useState(null);

  const handleOpenModel = (election: any) => {
    setSelectedElection(election);
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    setSelectedElection(null);
  };

  return (
    <Box sx={{ display: "flex", flexDirection:'column', justifyContent: "center", alignItems: "center" }}>
      <Button onClick={() => setOpen(true)} variant="contained" color="primary">
        Add Election
      </Button>

      <ElectionData handleOpenModel={handleOpenModel} />

      <Modal open={open} onClose={handleClose}>
        <Box sx={{ display: "flex", justifyContent: "center", alignItems: "center", height: "100vh" }}>
          <ElectionForm selectedElection={selectedElection} closeModal={handleClose} />
        </Box>
      </Modal>
    </Box>
  );
};

export default AddElection;
