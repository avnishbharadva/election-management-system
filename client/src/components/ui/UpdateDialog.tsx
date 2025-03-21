import React from "react";
import {
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  Typography,
  Box,
  Grid,
} from "@mui/material";
import { ChangeBox, ImagePreview, StyledDialog } from "../../style/UpdateDialogcss";
 
interface UpdateDialogProps {
  open: boolean;
  title?: string;
  handleClose: () => void;
  handleConfirm: (data: Record<string, any>) => void;
  originalData: Record<string, any>;
  updatedData: Record<string, any>;
}
 
const UpdateDialog: React.FC<UpdateDialogProps> = ({
  open,
  title = "",
  handleClose,
  handleConfirm,
  originalData = {},
  updatedData = {},
}) => {
  const handleConfirmClick = () => {
    handleConfirm(updatedData);
  };
  console.log("Original", originalData)
  console.log("Updated", updatedData)
 
  const compareObjects = (obj1: any, obj2: any, parentKey = "") => {
    let diff: any = {};
    const allKeys = new Set([...Object.keys(obj1 || {}), ...Object.keys(obj2 || {})]);
 
    allKeys.forEach((key) => {
      const fullKey = parentKey ? `${parentKey}.${key}` : key;
      const val1 = obj1?.[key] ?? "N/A";
      const val2 = obj2?.[key];
 
      if (typeof val2 === "object" && val2 !== null && !Array.isArray(val2)) {
        const nestedDiff = compareObjects(val1 || {}, val2, fullKey);
        if (Object.keys(nestedDiff).length > 0) {
          diff = { ...diff, ...nestedDiff };
        }
      } else if (val1 !== val2) {
        diff[fullKey] = { old: val1, new: val2 };
        diff[fullKey] = { old: val1, new: val2 };
      }
    });
    return diff;
  };
 
  const renderChanges = (diff: any) => {
    return Object.entries(diff).map(([key, value]: any) => (
      <Grid item xs={12} key={key}>
        <ChangeBox>
          <Typography variant="body1" fontWeight="bold">
            {key.replace(/([A-Z])/g, " $1")}:
          </Typography>
 
          {key.toLowerCase().includes("image") || key.toLowerCase().includes("signature") ? (
            <Box sx={{ display: "flex", alignItems: "center", gap: "15px" }}>
              {value.old !== "N/A" && <ImagePreview src={`data:image/png;base64,${value.old}`} alt="Old" />}
              <Typography variant="body1" sx={{ fontWeight: "bold", color: "gray" }}>→</Typography>
              {value.new !== "N/A" && <ImagePreview src={`data:image/png;base64,${value.new}`} alt="New" />}
            </Box>
          ) : (
            <Typography variant="body1" sx={{ fontWeight: "bold" }}>
              <span style={{ color: "#D32F2F" }}>{value.old || "N/A"}</span>
              <span style={{ margin: "0 8px", color: "#616161" }}>→</span>
              <span style={{ color: "#388E3C" }}>{value.new || "N/A"}</span>
            </Typography>
          )}
        </ChangeBox>
      </Grid>
    ));
  };
 
  const changes = compareObjects(originalData, updatedData);
 
  return (
    <StyledDialog open={open} onClose={handleClose} maxWidth="sm" fullWidth>
      <DialogTitle>{title}</DialogTitle>
      <DialogContent sx={{marginTop:"calc(1* 16px)"}}>
        <Grid container spacing={2} sx={{marginTop:"calc(1* 16px)"}}>
          {Object.keys(changes).length > 0 ? (
            renderChanges(changes)
          ) : (
            <Typography variant="body1" color="textSecondary" sx={{ textAlign: "center", width: "100%"}}>
              No changes detected.
            </Typography>
          )}
        </Grid>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleClose} color="primary" sx={{ fontWeight: "bold", textTransform: "none" }}>
          Cancel
        </Button>
        <Button
          onClick={handleConfirmClick}
          color="primary"
          variant="contained"
        >
          Confirm
        </Button>
      </DialogActions>
    </StyledDialog>
  );
};
 
export default UpdateDialog;



