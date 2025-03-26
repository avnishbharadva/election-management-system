import SearchComponent from "../ui/SearchCandidate";
import CandidateData from "../ui/CandidateData";
import { FlexBoxColumn } from "../../style/CommanStyle";
import { Box, Button } from "@mui/material";


const AddCandidate = () => {
  return (
    <>
    <FlexBoxColumn>
      <SearchComponent />
      </FlexBoxColumn>
    <CandidateData/>
    </>
  );
};

export default AddCandidate;
