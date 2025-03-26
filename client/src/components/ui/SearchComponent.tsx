import React, { useState, useEffect, useCallback } from 'react';
import { Box, TextField, Button, InputAdornment, IconButton, Tooltip } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';
import CloseIcon from '@mui/icons-material/Close';
// import '../../style/Search.css';
import RefreshIcon from '@mui/icons-material/Refresh';
import { CenterBox } from '../../style/VoterStyleCss';

type SearchProps = {
  input: string;  
  onChange?: (value:any) => void;  
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
  }, [length, onChange]); 

const handleReload=()=>{
  setSearchQuery("")
  if (onReload) {
    onReload();
  }
}

  return (
    <CenterBox>
      <Box display={'flex'} sx={{gap:'8px'}}>
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
              <IconButton onClick={handleReload} edge="end" >
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
      <Button
        variant="contained"
        color="primary"
        onClick={() => onChange && onChange(searchQuery)}  
        disabled={searchQuery.length !== length} 
        className="search-button"
        sx={{display:'grid', gridTemplateColumns:'1fr auto', alignItems:'center', gap:2, height:'3.45rem', width:'9rem'}}
      >
        Search
      </Button>
      </Box>
    </CenterBox>
  );
};

export default SearchComponent;
