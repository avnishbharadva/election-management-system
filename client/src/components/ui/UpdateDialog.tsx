import {
    Dialog, DialogTitle, DialogContent, DialogActions,
    Button, Typography, Box, Grid
  } from "@mui/material";
   
  interface UpdateDialogProps {
    open: boolean;
    title?: string;
    handleClose: () => void;
    handleConfirm: () => void;
    originalData: Record<string, any>;
    updatedData: Record<string, any>;
    ignoredKeys?: string[];
  }
   
  const UpdateDialog: React.FC<UpdateDialogProps> = ({
    open, title = "Confirm Changes", handleClose, handleConfirm,
    originalData, updatedData, ignoredKeys = []
  }) => {
   
    // Compare data while ignoring specified keys
    const changes = originalData && updatedData ? Object.keys(originalData).reduce((acc: any, key) => {
      if (!ignoredKeys.includes(key) && originalData[key] !== updatedData[key]) {
        acc[key] = { old: originalData[key], new: updatedData[key] };
      }
      return acc;
    }, {}) : {};
   
    return (
      <Dialog open={open} onClose={handleClose} maxWidth="sm" fullWidth>
        <DialogTitle sx={{ bgcolor: "#1976d2", color: "#fff", textAlign: "center", fontWeight: "bold" }}>
          {title}
        </DialogTitle>
        <DialogContent sx={{ padding: "20px" }}>
          {Object.keys(changes).length > 0 ? (
            <Grid container spacing={2}>
              {Object.entries(changes).map(([key, value]: any) => (
                <Grid item xs={12} key={key}>
                  <Box sx={{
                    display: "flex", justifyContent: "space-between", alignItems: "center",
                    bgcolor: "#f5f5f5", padding: "10px", borderRadius: "8px"
                  }}>
                    <Typography variant="body1" fontWeight="bold" sx={{ textTransform: "capitalize" }}>
                      {key.replace(/([A-Z])/g, " $1")}:
                    </Typography>
   
                    {/* Handle Image Preview */}
                    {key.toLowerCase().includes("image") || key.toLowerCase().includes("signature") ? (
                      <Box sx={{ display: "flex", alignItems: "center", gap: "10px" }}>
                        {value.old && (
                          <img
                            src={value.old.startsWith("data:image") ? value.old : `data:image/png;base64,${value.old}`}
                            alt="Old"
                            width="100"
                            height="100"
                            style={{ borderRadius: key.toLowerCase().includes("candidate") ? "50%" : "0" }}
                          />
                        )}
                        <Typography variant="body1" sx={{ fontWeight: "bold", color: "gray" }}>→</Typography>
                        {value.new && (
                          <img
                            src={value.new.startsWith("data:image") ? value.new : `data:image/png;base64,${value.new}`}
                            alt="New"
                            width="100"
                            height="100"
                            style={{ borderRadius: key.toLowerCase().includes("candidate") ? "50%" : "0" }}
                          />
                        )}
                      </Box>
                    ) : (
                      /* Handle Text Changes */
                      typeof value.old === "object" && typeof value.new === "object" ? (
                        <Typography variant="body1" color="error">
                          {Object.entries(value.old).map(([field, oldVal]: any) => (
                            <span key={field}>
                              {field !== "id" && ( // Ignore ID fields
                                <>
                                  <b>{field}:</b> <span style={{ color: "red" }}>{oldVal}</span> →
                                  <span style={{ color: "green" }}>{value.new[field]}</span> <br />
                                </>
                              )}
                            </span>
                          ))}
                        </Typography>
                      ) : (
                        <Typography variant="body1" color="error">
                          <span style={{ color: "red" }}>{value.old}</span> →
                          <span style={{ color: "green" }}>{value.new}</span>
                        </Typography>
                      )
                    )}
                  </Box>
                </Grid>
              ))}
            </Grid>
          ) : (
            <Typography sx={{ textAlign: "center", fontStyle: "italic", color: "gray", padding: "10px" }}>
              No changes made.
            </Typography>
          )}
        </DialogContent>
        <DialogActions sx={{ justifyContent: "center", padding: "15px" }}>
          <Button onClick={handleClose} variant="outlined" color="secondary" sx={{ width: "120px" }}>
            Cancel
          </Button>
          <Button onClick={handleConfirm} variant="contained" color="primary" sx={{ width: "120px" }}>
            Confirm
          </Button>
        </DialogActions>
      </Dialog>
    );
  };
   
  export default UpdateDialog;