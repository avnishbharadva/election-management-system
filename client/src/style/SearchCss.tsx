import { Box, Button, styled, TextField } from "@mui/material";
import SearchIcon from '@mui/icons-material/Search';
import CloseIcon from '@mui/icons-material/Close';

export const StyledSearchContainer = styled(Box)`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  max-width: 400px;
  margin: 6px auto;
`;

export const StyledSearchInput = styled(TextField)`
  .MuiOutlinedInput-root {
    transition: all 0.3s ease-in-out;
    &:hover {
      box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    }
    &.Mui-focused {
      box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
    }
  }
`;

export const SearchIconStyled = styled(SearchIcon)`
  color: #1e90ff;
`;

export const ClearIconStyled = styled(CloseIcon)`
  color: #ff4d4d;
  cursor: pointer;
`;

export const StyledSearchButton = styled(Button)`
  margin-left: 10px;
  padding:15px 36px;
`;