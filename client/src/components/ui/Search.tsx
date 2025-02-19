import { TextField, InputAdornment, IconButton, Box } from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";
import CloseIcon from "@mui/icons-material/Close";
import { useDispatch, useSelector } from "react-redux";
import "../../style/Search.css";
import { AppDispatch } from "../../store/app/store";
import { clearSearchQuery, setSearchQuery } from "../../store/feature/candidate/candidateSlice";
import { fetchCandidateBySSN } from "../../store/feature/candidate/candidateAPI";

import { TextField, InputAdornment, IconButton, Box, Button } from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";
import CloseIcon from "@mui/icons-material/Close";
import { useDispatch, useSelector } from "react-redux";
import "../style/Search.css";
import { AppDispatch } from "../store/app/store";
import { clearSearchQuery, setSearchQuery } from "../store/feature/candidate/candidateSlice";
import { fetchCandidateBySSN } from "../store/feature/candidate/candidateAPI";

const SearchComponent: React.FC = () => {
  const dispatch = useDispatch<AppDispatch>();
  const searchQuery = useSelector((state: any) => state.candidate.searchQuery);

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    let query = event.target.value;

    // Allow only numbers & limit to 9 digits
    if (/^\d{0,9}$/.test(query)) {
      dispatch(setSearchQuery(query));
    }
  };

  const handleSearch = () => {
    if (searchQuery.length === 9) {
      dispatch(fetchCandidateBySSN(searchQuery));
    }
  };

  const clearSearch = () => {
    dispatch(clearSearchQuery());
  };

  return (
    <Box className="search-container">
      <TextField
        fullWidth
        variant="outlined"
        placeholder="Enter 9-digit SSN..."
        value={searchQuery}
        onChange={handleChange}
        className="search-input"
        type="text"
        InputProps={{
          inputProps: { maxLength: 9, pattern: "[0-9]*" }, // Restricts input to 9 digits
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
      <Button
        variant="contained"
        color="primary"
        onClick={handleSearch}
        disabled={searchQuery.length !== 9} // Disabled until exactly 9 digits are entered
        className="search-button"
      >
        Search
      </Button>
    </Box>
  );
};

export default SearchComponent;
