import { InputAdornment, IconButton} from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import { clearSearchQuery, setSearchedSSN, setSearchQuery } from "../../store/feature/candidate/candidateSlice";
import { AppDispatch } from "../../store/app/store";
import { fetchCandidateBySSN } from "../../store/feature/candidate/candidateAPI";
import { ClearIconStyled, SearchIconStyled, StyledSearchButton, StyledSearchContainer, StyledSearchInput } from "../../style/SearchCss";
 
const SearchComponent: React.FC = () => {
  const dispatch = useDispatch<AppDispatch>();
  const searchQuery = useSelector((state: any) => state.candidate.searchQuery);
 
  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const query = event.target.value;
   
    if (/^\d{0,9}$/.test(query)) {
      dispatch(setSearchQuery(query));
    }
  };
 
  const handleSearch =async () => {
    if (searchQuery.length === 9) {
  const result =   await  dispatch(fetchCandidateBySSN(searchQuery));
   
 
    if (!result) {
      dispatch(setSearchedSSN(searchQuery));
    }}
   
  };
 
  const clearSearch = () => {
    dispatch(clearSearchQuery());
  };
 
  return (
    <StyledSearchContainer>
      <StyledSearchInput
        fullWidth
        variant="outlined"
        placeholder="Enter Last 9-digit SSN..."
        value={searchQuery}
        onChange={handleChange}
        type="text"
        InputProps={{
          inputProps: { maxLength: 9, pattern: "[0-9]*" },
          startAdornment: (
            <InputAdornment position="start">
              <SearchIconStyled />
            </InputAdornment>
          ),
          endAdornment: searchQuery && (
            <InputAdornment position="end">
              <IconButton onClick={clearSearch} edge="end">
                <ClearIconStyled />
              </IconButton>
            </InputAdornment>
          ),
        }}
      />
      <StyledSearchButton
        variant="contained"
        color="primary"
        onClick={handleSearch}
        disabled={searchQuery.length !== 9}
      >
        Search
      </StyledSearchButton>
    </StyledSearchContainer>
  );
};
 
export default SearchComponent;
 