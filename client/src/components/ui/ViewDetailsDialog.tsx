import {
  Box,
  Button,
  Card,
  CardContent,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Divider,
  Grid,
  Typography,
} from "@mui/material";
 
interface SectionConfig {
  title: string;
  fields: { label: string; key: string }[];
}
 
interface ViewDetailsDialogProps {
  open: boolean;
  handleClose: () => void;
  data: any;
  sections: SectionConfig[];
  imageKeys?: string[]; // Array of keys for images (can be empty or have multiple values)
}
 
export default function ViewDetailsDialog({
  open,
  handleClose,
  data,
  sections,
  imageKeys = [], // Default empty array if no images
}: ViewDetailsDialogProps) {
  if (!data) {
    return null;
  }
 
  return (
<Dialog open={open} onClose={handleClose} fullWidth maxWidth="md">
<DialogTitle
        sx={{
          backgroundColor: "#1976d2",
          color: "white",
          textAlign: "center",
          fontWeight: "bold",
        }}
>
        Details
</DialogTitle>
 
      <DialogContent sx={{ padding: "20px", backgroundColor: "#f9f9f9" }}>
<Box sx={{ display: "flex", flexDirection: "column", gap: 3 }}>
          {sections.map((section, index) => (
<Card key={index} sx={{ boxShadow: 2, borderRadius: 2 }}>
<CardContent>
<Typography variant="h6" sx={{ fontWeight: "bold", color: "#1976d2" }}>
                  {section.title}
</Typography>
<Divider sx={{ marginBottom: 2 }} />
 
                <Grid container spacing={2}>
                  {/* Personal Information Section - Show Text + Images */}
                  {section.title === "Personal Information" ? (
<>
<Grid item xs={imageKeys.length > 0 ? 8 : 12}>
<Grid container spacing={2}>
                          {section.fields.map((field, fieldIndex) => {
                            const value = field.key.split('.').reduce((o, k) => (o || {})[k], data) || "N/A";
                            return (
<Grid key={fieldIndex} container item xs={12} spacing={2}>
<Grid item xs={5}>
<Typography sx={{ fontWeight: "bold" }}>{field.label}:</Typography>
</Grid>
<Grid item xs={7}>
<Typography>{value}</Typography>
</Grid>
</Grid>
                            );
                          })}
</Grid>
</Grid>
 
                      {/* Image Section - Dynamically Handles 0, 1, or 2 Images */}
                      {imageKeys.length > 0 && (
<Grid item xs={4} sx={{ textAlign: "center" }}>
                          {imageKeys.map((key, index) => {
                            const imageSrc = data[key];
 
                            return (
                              imageSrc && (
<Box key={index} sx={{ marginBottom: 2 }}>
<img
                                    src={imageSrc.startsWith("data:image") ? imageSrc : `data:image/png;base64,${imageSrc}`}
                                    alt={key}
                                    style={{
                                      width: "150px",
                                      height: "150px",
                                      borderRadius: "8px",
                                      boxShadow: "2px 2px 5px rgba(0,0,0,0.2)",
                                    }}
                                  />
<Typography variant="h6" sx={{ fontWeight: "bold", color: "#1976d2", marginTop: 1 }}>
                                    {key.replace(/([A-Z])/g, " $1").trim()}
</Typography>
</Box>
                              )
                            );
                          })}
</Grid>
                      )}
</>
                  ) : (
                    // Other Sections - Only Text Fields
                    section.fields.map((field, fieldIndex) => {
                      const value = field.key.split('.').reduce((o, k) => (o || {})[k], data) || "N/A";
                      return (
<Grid key={fieldIndex} container item xs={12} spacing={2}>
<Grid item xs={4}>
<Typography sx={{ fontWeight: "bold" }}>{field.label}:</Typography>
</Grid>
<Grid item xs={8}>
<Typography>{value}</Typography>
</Grid>
</Grid>
                      );
                    })
                  )}
</Grid>
</CardContent>
</Card>
          ))}
</Box>
</DialogContent>
 
      <DialogActions sx={{ justifyContent: "center", padding: "10px", backgroundColor: "#f1f1f1" }}>
<Button onClick={handleClose} variant="contained" color="primary">
          Close
</Button>
</DialogActions>
</Dialog>
  );
}