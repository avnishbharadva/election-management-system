import React from "react";
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
  imageKey?: string; // Candidate Image or Party Symbol
  signatureKey?: string; // Candidate Signature
  title: string;
  imagelabel : string,
  signaturelabel: string
}
 
export default function ViewDetailsDialog({
  open,
  handleClose,
  data,
  sections,
  imageKey,
  signatureKey,
  title,
  imagelabel,
  signaturelabel
}: ViewDetailsDialogProps) {
  if (!data) return null;
 
  // Extract profile image or party symbol
  const imageSrc = imageKey && data[imageKey] ? getImageSrc(data[imageKey]) : null;
 
  // Extract candidate signature
  const signatureSrc = signatureKey && data[signatureKey] ? getImageSrc(data[signatureKey]) : null;
 
  function getImageSrc(imageData: string) {
    if (!imageData) return "";
    if (imageData.startsWith("data:image")) return imageData;
 
    let mimeType = "image/jpeg"; // Default to JPEG
    if (imageData.startsWith("/9j/")) mimeType = "image/jpeg"; // JPEG
    if (imageData.startsWith("iVBOR")) mimeType = "image/png"; // PNG
    if (imageData.startsWith("PHN2")) mimeType = "image/svg+xml"; // SVG
 
    return `data:${mimeType};base64,${imageData}`;
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
        {title}
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
                  {/* ✅ First Section: Candidate Image / Party Symbol on Right Side */}
                  {index === 0 ? (
                    <>
                      <Grid item xs={8}>
                        <Grid container spacing={2}>
                          {section.fields.map((field, fieldIndex) => {
                            const value =
                              field.key.split(".").reduce((o, k) => (o || {})[k], data) || "N/A";
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
 
                      {/* 🖼️ Right Side Image + Signature */}
                      {imageSrc && (
                        <Grid item xs={4} sx={{ textAlign: "center" }}>
                          <Box sx={{ display: "flex", flexDirection: "column", alignItems: "center" }}>
                            {/* 🎭 Candidate Image or Party Symbol */}
                            <img
                              src={imageSrc}
                              alt="Profile"
                              style={{
                                width: "150px",
                                height: "150px",
                                borderRadius: "8px",
                                boxShadow: "2px 2px 5px rgba(0,0,0,0.2)",
                              }}
                              onError={(e) => {
                                console.error("Error loading image", imageKey, e);
                                (e.target as HTMLImageElement).src = "/default-placeholder.png";
                              }}
                            />
                            <Typography variant="h6" sx={{ fontWeight: "bold", color: "#1976d2", marginTop: 1 }}>
                             {imagelabel}
                            </Typography>
 
                            {/* ✍️ Candidate Signature (Below Image) */}
                            {signatureSrc && (
                              <Box sx={{ marginTop: 2 }}>
                                <img
                                  src={signatureSrc}
                                  alt="Signature"
                                  style={{
                                    width: "200px",
                                    height: "80px",
                                    borderRadius: "4px",
                                    boxShadow: "2px 2px 5px rgba(0,0,0,0.2)",
                                  }}
                                  onError={(e) => {
                                    console.error("Error loading signature", signatureKey, e);
                                    (e.target as HTMLImageElement).src = "/default-signature.png";
                                  }}
                                />
                                <Typography variant="h6" sx={{ fontWeight: "bold", color: "#1976d2", marginTop: 1 }}>
                                  {signaturelabel}
                                </Typography>
                              </Box>
                            )}
                          </Box>
                        </Grid>
                      )}
                    </>
                  ) : (
                    // 🔹 Other Sections (Regular Fields)
                    section.fields.map((field, fieldIndex) => {
                      const value =
                        field.key.split(".").reduce((o, k) => (o || {})[k], data) || "N/A";
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
      <DialogActions
        sx={{
          justifyContent: "center",
          padding: "10px",
          backgroundColor: "#f1f1f1",
        }}
      >
        <Button onClick={handleClose} variant="contained" color="primary">
          Close
        </Button>
      </DialogActions>
 
    </Dialog>
  );
}