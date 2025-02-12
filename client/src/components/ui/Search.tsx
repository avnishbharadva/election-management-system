import { TextField, InputAdornment, IconButton, Box } from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";
import CloseIcon from "@mui/icons-material/Close";
import '../../style/Search.css';

interface SearchProps {
  searchQuery: string;
  setSearchQuery: (query: string) => void;
}

const SearchComponent: React.FC<SearchProps> = ({ searchQuery, setSearchQuery }) => {

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSearchQuery(event.target.value);
  };

  const clearSearch = () => {
    setSearchQuery("");
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
        InputProps={{
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
