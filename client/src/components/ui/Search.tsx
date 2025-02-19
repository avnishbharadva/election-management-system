import React, { useState, useEffect, useCallback } from 'react';
import { Box, TextField, Button, InputAdornment, IconButton, Tooltip, FormHelperText } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';
import CloseIcon from '@mui/icons-material/Close';
import '../../style/Search.css';
import RefreshIcon from '@mui/icons-material/Refresh';

type SearchProps = {
  
  input: string;  
  onChange?: (value: string) => void;  
  name: string;
  length: number;
  onReload?: () => void;
};

const SearchComponent = (props: SearchProps) => {
  const {name, input, onChange, length, onReload } = props;
  const [searchQuery, setSearchQuery] = useState(input);

  useEffect(() => {
    setSearchQuery(input);
  }, [input]);

  // Handle change event
  const handleChange = useCallback((e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    const numericValue = value.replace(/[^0-9]/g, '');
    setSearchQuery(numericValue)

    if (value.length === length && onChange) {
      onChange(value); 
      console.log("search query is equal to length");
    }
  }, [length, onChange]); 

const handleReload=()=>{
  if (onReload) {
    onReload();
  }
}

  return (
    <Box className="search-container">
      <Box display={'flex'} flexDirection={'column'}>
      <TextField
      id={name}
        fullWidth
        variant="outlined"
        placeholder={`Enter ${name}...`} 
        value={searchQuery}  
        onChange={handleChange} 
        className="search-input"
        type='text'
        onKeyDown={(evt) => ["e", "E", "+", "-"].includes(evt.key) && evt.preventDefault()}
        InputProps={{
          inputProps: { maxLength: length, pattern: "[0-9]*" },
          startAdornment: (
            <InputAdornment position="start">
              <SearchIcon className="search-icon" />
            </InputAdornment>
          ),
          endAdornment: searchQuery ? (
            <InputAdornment position="end">
              <Tooltip title="Clear">
              <IconButton onClick={handleReload} edge="end">
                <CloseIcon className="clear-icon" />
              </IconButton>
              </Tooltip>
            </InputAdornment>
          ) : (
            <InputAdornment position="end">
              <Tooltip title="Reload">
              <IconButton onClick={handleReload} edge="end">
                <RefreshIcon className="reload-icon" />
              </IconButton>
              </Tooltip>
            </InputAdornment>
          ),
        }}
      />

<FormHelperText>
        {/* {`${searchQuery.length} / ${length}`} */}
        {`${name} must enter be ${length} digits`}

      </FormHelperText>
      </Box>
      <Button
        variant="contained"
        color="primary"
        onClick={() => onChange && onChange(searchQuery)}  // Pass searchQuery on button click
        disabled={searchQuery.length !== length} 
        className="search-button"
        sx={{ ml: 2 }}
      >
        Search
      </Button>
    </Box>
  );
};

export default SearchComponent;
