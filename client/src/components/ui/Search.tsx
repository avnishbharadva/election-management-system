import  { useState } from "react";
import { TextField, InputAdornment, IconButton, Box } from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";
import CloseIcon from "@mui/icons-material/Close";
import '../../style/Search.css';

const SearchComponent = () => {
  const [searchTerm, setSearchTerm] = useState("");

  const handleSearch = () => {
    setSearchTerm('');
  };

  const clearSearch = () => {
    setSearchTerm("");
  };

  return (
    <Box className="search-container">
      <TextField
        fullWidth
        variant="outlined"
        placeholder="Search..."
        value={searchTerm}
        onChange={handleSearch}
        className="search-input"
        InputProps={{
          startAdornment: (
            <InputAdornment position="start">
              <SearchIcon className="search-icon" />
            </InputAdornment>
          ),
          endAdornment: searchTerm && (
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
