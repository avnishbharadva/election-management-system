import React from "react";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  Typography,
  Box,
  Grid,
} from "@mui/material";
import { StyledBox } from "../../style/CommanStyle";

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
  title = "Confirm Candidate Updates",
  handleClose,
  handleConfirm,
  originalData = {},
  updatedData = {},
}) => {
  const handleConfirmClick = () => {
    handleConfirm(updatedData);
  };
  
  const compareObjects = (obj1: any, obj2: any, parentKey = "") => {
    let diff: any = {};
    const allKeys = new Set([...Object.keys(obj1 || {}), ...Object.keys(obj2 || {})]);
  
    allKeys.forEach((key) => {
      const fullKey = parentKey ? `${parentKey}.${key}` : key;
      const val1 = obj1?.[key] ?? "N/A";
      const val2 = obj2?.[key] ?? "N/A"; 
      
      if (typeof val2 === "object" && val2 !== null && !Array.isArray(val2)) {
        const nestedDiff = compareObjects(val1 || {}, val2, fullKey);
        if (Object.keys(nestedDiff).length > 0) {
          diff = { ...diff, ...nestedDiff };
        }
      } else if (val1 !== val2 && !(val1 === "N/A" && val2 === "N/A")) {
        diff[fullKey] = { old: val1, new: val2 };
      }
    });
  
    return diff;
  };
  
  const renderChanges = (diff: any) => {
    return Object.entries(diff).map(([key, value]: any) => (
      <Grid item xs={12} key={key}>
        <StyledBox>
          <Typography variant="body1" fontWeight="bold" sx={{ textTransform: "capitalize" }}>
            {key.replace(/([A-Z])/g, " $1")}:
          </Typography>
          {key.toLowerCase().includes("image") || key.toLowerCase().includes("signature") ? (
            <Box sx={{ display: "flex", alignItems: "center", gap: "10px" }}>
              {value.old !== "N/A" && (
                <img
                  src={`data:image/png;base64,${value.old}`}
                  alt="Old"
                  width="100"
                  height="50"
                  style={{ borderRadius: "4px", objectFit: "cover" }}
                />
              )}
              <Typography variant="body1" sx={{ fontWeight: "bold", color: "gray" }}>→</Typography>
              {value.new !== "N/A" && (
                <img
                  src={`data:image/png;base64,${value.new}`}
                  alt="New"
                  width="100"
                  height="50"
                  style={{ borderRadius: "4px", objectFit: "cover" }}
                />
              )}
            </Box>
          ) : (
            <Typography variant="body1" color="error">
              <span style={{ color: "red" }}>
                {typeof value.old === "object" ? JSON.stringify(value.old) : value.old || "N/A"}
              </span> →{" "}
              <span style={{ color: "green" }}>
                {typeof value.new === "object" ? JSON.stringify(value.new) : value.new || "N/A"}
              </span>
            </Typography>
          )}
        </StyledBox>
      </Grid>
    ));
  };


  const changes = compareObjects(originalData, updatedData);

  return (
    <Dialog open={open} onClose={handleClose}>
      <DialogTitle>{title}</DialogTitle>
      <DialogContent>
        <Grid container spacing={2}>
          {Object.keys(changes).length > 0 ? (
            renderChanges(changes)
          ) : (
            <Typography variant="body1" color="textSecondary" sx={{marginTop: 2, textAlign: "center"}}>
              No changes detected.
            </Typography>
          )}
        </Grid>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleClose} color="secondary">Cancel</Button>
        <Button onClick={handleConfirmClick} color="primary" variant="contained">
          Confirm
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default UpdateDialog;