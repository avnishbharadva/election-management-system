import { useState } from "react";
<<<<<<< HEAD
import { Button, Modal, Box } from "@mui/material";
import ElectionForm from "../ui/ElectionForm";
import ElectionData from "../ui/ElectionData";

=======
import { Button, Box } from "@mui/material";
import ElectionForm from "../ui/ElectionForm";
import ElectionData from "../ui/ElectionData";
import Model from "../ui/Model";
>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486
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
<<<<<<< HEAD
    <Box sx={{ display: "flex", flexDirection:'column', justifyContent: "center", alignItems: "center" }}>
=======
    <Box sx={{ display: "flex", flexDirection:'column', justifyContent: "center", alignItems: "flex-end" }}>
>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486
      <Button onClick={() => setOpen(true)} variant="contained" color="primary">
        Add Election
      </Button>

      <ElectionData handleOpenModel={handleOpenModel} />

<<<<<<< HEAD
      <Modal open={open} onClose={handleClose}>
        <Box sx={{ display: "flex", justifyContent: "center", alignItems: "center", height: "100vh" }}>
          <ElectionForm selectedElection={selectedElection} closeModal={handleClose} />
        </Box>
      </Modal>
=======
      <Model open={open} handleClose={handleClose}>
        <Box sx={{ display: "flex", justifyContent: "center", alignItems: "center"}}>
          <ElectionForm selectedElection={selectedElection} closeModal={handleClose} />
        </Box>
      </Model>
>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486
    </Box>
  );
};

export default AddElection;
