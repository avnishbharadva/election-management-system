import { TextField, InputAdornment, IconButton, Box } from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";
import CloseIcon from "@mui/icons-material/Close";
import { useDispatch, useSelector } from "react-redux";
import "../../style/Search.css";
import { AppDispatch } from "../../store/app/store";
import { clearSearchQuery, setSearchQuery } from "../../store/feature/candidate/candidateSlice";
import { fetchCandidateBySSN } from "../../store/feature/candidate/candidateAPI";

const SearchComponent: React.FC = () => {
  const dispatch = useDispatch<AppDispatch>();
  const searchQuery = useSelector((state:any) => state.candidate.searchQuery);
  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const query = event.target.value;
    dispatch(setSearchQuery(query)); // ✅ Store search query in Redux
    // ✅ Fetch candidate data if SSN is at least 9 characters
    if (query?.length >= 9) {
      dispatch(fetchCandidateBySSN(query));
    }
  };

  const clearSearch = () => {
    dispatch(clearSearchQuery()); // ✅ Clear search query & candidate data
  };

  return (
    <Box className="search-container">
      <TextField
        fullWidth
        variant="outlined"
        placeholder="Search by SSN..."
        value={searchQuery}
        onChange={handleChange}
        className="search-input"
        type="number" // Ensures only numbers are allowed
        InputProps={{
          inputProps:{ min: 10, max: 10},
          startAdornment: (
            <InputAdornment position="start">
              <SearchIcon className="search-icon" />
            </InputAdornment>
          ),
          endAdornment: searchQuery && (
            <InputAdornment position="end">
              <IconButton onClick={clearSearch} edge="end">
                <CloseIcon className="clear-icon" />
              </IconButton>
            </InputAdornment>
          ),
        }}
      />
    </Box>
  );
};

export default SearchComponent;
