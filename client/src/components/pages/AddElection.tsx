import { useState } from "react";
import { Button, Box } from "@mui/material";
import ElectionForm from "../ui/ElectionForm";
import ElectionData from "../ui/ElectionData";
import Model from "../ui/Model";
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

      <Model open={open} handleClose={handleClose} actionType="add">
        <Box sx={{ display: "flex", justifyContent: "center", alignItems: "center"}}>
          <ElectionForm selectedElection={selectedElection} closeModal={handleClose} />
        </Box>
      </Model>
    </Box>
  );
};

export default AddElection;
