import { Box } from "@mui/material";
import Cards from "../ui/Cards";

export default function Dashboard() {
  return (
    <Box display="flex">
      <Box flexGrow={1}>      
        <Cards />
      </Box>
    </Box>
  );
}
