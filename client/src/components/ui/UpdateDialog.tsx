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

interface UpdateDialogProps {
  open: boolean;
  title?: string;
  handleClose: () => void;
  handleConfirm: (data: Record<string, any>) => void;
  originalData: Record<string, any>;
  updatedData: Record<string, any>;
  ignoredKeys?: string[];
}

const UpdateDialog: React.FC<UpdateDialogProps> = ({
  open,
  title = "Confirm Changes",
  handleClose,
  handleConfirm,
  originalData = {},
  updatedData = {},
  ignoredKeys = [],
}) => {
  // Compare objects and find differences
  const compareObjects = (obj1: any, obj2: any): any => {
    let diff: any = {};

    for (const key in obj1) {
      if (ignoredKeys.includes(key)) continue;

      const val1 = obj1[key];
      const val2 = obj2?.[key]; // Handle undefined safely

      if (typeof val1 === "object" && val1 !== null && !Array.isArray(val1)) {
        const nestedDiff = compareObjects(val1, val2 || {});
        if (Object.keys(nestedDiff).length > 0) diff[key] = nestedDiff;
      } else if (val1 !== val2) {
        diff[key] = { old: val1, new: val2 };
      }
    }

    return diff;
  };

  // Render changes in a readable format, including images
  const renderChanges = (diff: any, parentKey = "") => {
    return Object.entries(diff).map(([key, value]: any) => (
      <Grid item xs={12} key={parentKey + key}>
        <Box
          sx={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
            bgcolor: "#f5f5f5",
            padding: "10px",
            borderRadius: "8px",
          }}
        >
          <Typography variant="body1" fontWeight="bold" sx={{ textTransform: "capitalize" }}>
            {parentKey + key.replace(/([A-Z])/g, " $1")}:
          </Typography>
          {key.toLowerCase().includes("image") || key.toLowerCase().includes("signature") ? (
            <Box sx={{ display: "flex", alignItems: "center", gap: "10px" }}>
              {value.old && (
                <img
                  src={value.old.startsWith("data:image") ? value.old : `data:image/png;base64,${value.old}`}
                  alt="Old Signature"
                  width="100"
                  height="50"
                  style={{ borderRadius: "4px", objectFit: "cover" }}
                />
              )}
              <Typography variant="body1" sx={{ fontWeight: "bold", color: "gray" }}>
                →
              </Typography>
              {value.new && (
                <img
                  src={value.new.startsWith("data:image") ? value.new : `data:image/png;base64,${value.new}`}
                  alt="New Signature"
                  width="100"
                  height="50"
                  style={{ borderRadius: "4px", objectFit: "cover" }}
                />
              )}
            </Box>
          ) : typeof value === "object" && "old" in value ? (
            <Typography variant="body1" color="error">
              <span style={{ color: "red" }}>{value.old || "N/A"}</span> →{" "}
              <span style={{ color: "green" }}>{value.new || "N/A"}</span>
            </Typography>
          ) : (
            renderChanges(value, `${key}.`)
          )}
        </Box>
      </Grid>
    ));
  };

  const changes = compareObjects(originalData, updatedData);

  return (
    <Dialog open={open} onClose={handleClose} maxWidth="md" fullWidth>
      <DialogTitle
        sx={{
          bgcolor: "#1976d2",
          color: "#fff",
          textAlign: "center",
          fontWeight: "bold",
        }}
      >
        {title}
      </DialogTitle>
      <DialogContent sx={{ padding: "20px" }}>
        {Object.keys(changes).length > 0 ? (
          <Grid container spacing={2}>{renderChanges(changes)}</Grid>
        ) : (
          <Typography
            sx={{ textAlign: "center", fontStyle: "italic", color: "gray", padding: "10px" }}
          >
            No changes made.
          </Typography>
        )}
      </DialogContent>
      <DialogActions sx={{ justifyContent: "center", padding: "15px" }}>
        <Button onClick={handleClose} variant="outlined" color="secondary" sx={{ width: "120px" }}>
          Cancel
        </Button>
        <Button
          onClick={() => {
            handleConfirm(changes); // Pass only changed data
          }}
          variant="contained"
          color="primary"
          sx={{ width: "120px" }}
        >
          Confirm
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default UpdateDialog;
