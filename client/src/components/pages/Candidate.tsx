import SearchComponent from "../modules/candidate/SearchCandidate";
import CandidateData from "../modules/candidate/CandidateData";
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
