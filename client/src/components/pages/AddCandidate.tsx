import SearchComponent from "../ui/SearchCandidate";
import CandidateData from "../ui/CandidateData";
import { FlexBoxColumn } from "../../style/CommanStyle";

const AddCandidate = () => {
  return (
    <FlexBoxColumn>
      <SearchComponent />
      <CandidateData />
    </FlexBoxColumn>
  );
};

export default AddCandidate;
