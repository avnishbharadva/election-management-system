import {
  Box,
  Button,
  Card,
  CardContent,
  Dialog,
  Divider,
  Grid,
  Typography,
} from "@mui/material";
import { DialogAction, DialogContainer, DialogWrapper, ImageBox, ImageSign, ImageWarpper, Title } from "../../style/CommanStyle";
import { ViewDetailsDialogProps } from "../../Types/ViewDialog.types";

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

  const imageSrc = imageKey && data[imageKey] ? getImageSrc(data[imageKey]) : null;
  const signatureSrc = signatureKey && data[signatureKey] ? getImageSrc(data[signatureKey]) : null;

  function getImageSrc(imageData: string) {
    if (!imageData) return "";
    if (imageData.startsWith("data:image")) return imageData;

    let mimeType = "image/jpeg"; 
    if (imageData.startsWith("/9j/")) mimeType = "image/jpeg"; 
    if (imageData.startsWith("iVBOR")) mimeType = "image/png"; 
    if (imageData.startsWith("PHN2")) mimeType = "image/svg+xml";

    return `data:${mimeType};base64,${imageData}`;
  }

  return (
    <Dialog open={open} onClose={handleClose} fullWidth maxWidth="md">
      <Title>
        {title}
      </Title>
      <DialogWrapper>
        <DialogContainer>
          {sections.map((section, index) => (
            <Card key={index} sx={{ boxShadow: 2, borderRadius: 2 }}>
              <CardContent>
                <Typography variant="h6" sx={{ fontWeight: "bold", color: "#1976d2" }}>
                  {section.title}
                </Typography>
                <Divider sx={{ marginBottom: 2 }} />

                <Grid container spacing={2}>
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
                      {imageSrc && (
                        <Grid item xs={4} sx={{ textAlign: "center" }}>
                          <ImageWarpper>
                            <ImageBox
                              src={imageSrc}
                              alt="Profile"
                              onError={(e) => {
                                console.error("Error loading image", imageKey, e);
                                (e.target as HTMLImageElement).src = "/default-placeholder.png";
                              }}
                            />
                            <Typography variant="h6" sx={{ fontWeight: "bold", color: "#1976d2", marginTop: 1 }}>
                             {imagelabel}
                            </Typography>
                            {signatureSrc && (
                              <Box sx={{ marginTop: 2 }}>
                                <ImageSign
                                  src={signatureSrc}
                                  alt="Signature"
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
                          </ImageWarpper>
                        </Grid>
                      )}
                    </>
                  ) : (
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
        </DialogContainer>
      </DialogWrapper>
      <DialogAction>
        <Button onClick={handleClose} variant="contained" color="primary">
          Close
        </Button>
      </DialogAction>
    </Dialog>
  );
}

