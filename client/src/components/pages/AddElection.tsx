import { useState } from "react";
import { Button } from "@mui/material";
import ElectionForm from "../ui/ElectionForm";
import ElectionData from "../ui/ElectionData";
import Model from "../ui/Model";
import { FlexBox } from "../../style/ElectionCss";
import { FlexBoxCenter } from "../../style/CommanStyle";
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
    <FlexBox>
      <Button onClick={() => setOpen(true)} variant="contained" color="primary">
        Add Election
      </Button>

      <ElectionData handleOpenModel={handleOpenModel} />

      <Model open={open} handleClose={handleClose}>
        <FlexBoxCenter>
          <ElectionForm
            selectedElection={selectedElection}
            closeModal={handleClose}
          />
        </FlexBoxCenter>
      </Model>
    </FlexBox>
  );
};

export default AddElection;
