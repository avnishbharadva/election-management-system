import { Box, Button, Card, CardContent, Dialog, DialogActions, DialogContent, DialogTitle, Divider,Grid, Typography } from "@mui/material";

interface ViewCandidateProps {
  open: boolean;
  handleClose: () => void;
  selectedCandidate: any;
}

export default function ViewCandidate({ open, handleClose, selectedCandidate }: ViewCandidateProps) {
  return (
            <>
              {/* Candidate View Dialog */}
              <Dialog open={open} onClose={handleClose} fullWidth maxWidth="md">
              {/* Header */}
              <DialogTitle sx={{ backgroundColor: "#1976d2", color: "white", textAlign: "center", fontWeight: "bold" }}>
                Candidate Details
              </DialogTitle>
              
              <DialogContent sx={{ padding: "20px", backgroundColor: "#f9f9f9" }}>
                {selectedCandidate ? (
                  <Box sx={{ display: "flex", flexDirection: "column", gap: 3 }}>
                  {/* Personal Information Section */}
                    <Card sx={{ boxShadow: 2, borderRadius: 2 }}>
                      <CardContent>
                        <Typography variant="h6" sx={{ fontWeight: "bold", color: "#1976d2" }}>
                          Personal Information
                        </Typography>
                        <Divider sx={{ marginBottom: 2 }} />

                        <Grid container spacing={2} alignItems="center">
                          {/* Personal Details - Left Side */}
                          <Grid item xs={8}>
                            <Grid container spacing={2}>
                              <Grid item xs={4}><Typography sx={{ fontWeight: "bold" }}>ID:</Typography></Grid>

                              <Grid item xs={8}><Typography>{selectedCandidate?.candidateId}</Typography></Grid>

                              <Grid item xs={4}><Typography sx={{ fontWeight: "bold" }}>SSN:</Typography></Grid>
                              <Grid item xs={8}><Typography>{selectedCandidate?.candidateSSN}</Typography></Grid>

                              <Grid item xs={4}><Typography sx={{ fontWeight: "bold" }}>Name:</Typography></Grid>
                              <Grid item xs={8}>
                                <Typography>
                                  {selectedCandidate?.candidateName?.firstName} 
                                  {selectedCandidate?.candidateName?.middleName ? ` ${selectedCandidate?.candidateName?.middleName}` : ""} 
                                  {selectedCandidate?.candidateName?.lastName}
                                </Typography>
                              </Grid>

                              <Grid item xs={4}><Typography sx={{ fontWeight: "bold" }}>Email:</Typography></Grid>

                              <Grid item xs={8}><Typography>{selectedCandidate?.candidateEmail}</Typography></Grid>

                              <Grid item xs={4}><Typography sx={{ fontWeight: "bold" }}>Gender:</Typography></Grid>
                              <Grid item xs={8}><Typography>{selectedCandidate?.gender}</Typography></Grid>

                              <Grid item xs={4}><Typography sx={{ fontWeight: "bold" }}>Date of Birth:</Typography></Grid>
                              <Grid item xs={8}><Typography>{selectedCandidate?.dateOfBirth}</Typography></Grid>

                              <Grid item xs={4}><Typography sx={{ fontWeight: "bold" }}>Marital Status:</Typography></Grid>
                              <Grid item xs={8}><Typography>{selectedCandidate?.maritialStatus}</Typography></Grid>

                              <Grid item xs={4}><Typography sx={{ fontWeight: "bold" }}>Spouse Name:</Typography></Grid>
                              <Grid item xs={8}><Typography>{selectedCandidate?.spouseName}</Typography></Grid>

                              <Grid item xs={4}><Typography sx={{ fontWeight: "bold" }}>Number of Children:</Typography></Grid>
                              <Grid item xs={8}><Typography>{selectedCandidate?.noOfChildren}</Typography></Grid>

                              <Grid item xs={4}><Typography sx={{ fontWeight: "bold" }}>State:</Typography></Grid>
                              <Grid item xs={8}><Typography>{selectedCandidate?.stateName}</Typography></Grid>
                            </Grid>
                          </Grid>

                          {/* Candidate Image - Right Side */}
                          <Grid item xs={4} sx={{ display: "flex", flexDirection: "column", alignItems: "center", justifyContent: "center" }}>
                          {selectedCandidate?.candidateImage && (
                            <Box sx={{ textAlign: "center", marginBottom: "4rem" }}>
                              <img
                                src={`data:image/png;base64,${selectedCandidate?.candidateImage}`}
                                alt="Candidate"
                                style={{
                                  width: "150px",
                                  height: "150px",
                                  borderRadius: "8px",
                                  boxShadow: "2px 2px 5px rgba(76, 53, 53, 0.2)"
                                }}
                              />
                              <Typography variant="h6" sx={{ fontWeight: "bold", color: "#1976d2" }}>
                                Candidate Image
                              </Typography>
                            </Box>
                          )}

                          {selectedCandidate?.candidateSignature && (
                            <Box sx={{ textAlign: "center" }}>
                              <img
                                src={`data:image/png;base64,${selectedCandidate?.candidateSignature}`}
                                alt="Signature"
                                style={{
                                  width: "150px",
                                  height: "80px",
                                  borderRadius: "8px",
                                  boxShadow: "2px 2px 5px rgba(0,0,0,0.2)"
                                }}
                              />
                              <Typography variant="h6" sx={{ fontWeight: "bold", color: "#1976d2" }}>
                                Signature
                              </Typography>
                            </Box>
                          )}
                        </Grid>
                        </Grid>
                      </CardContent>
                    </Card>
                    <Card sx={{ boxShadow: 2, borderRadius: 2 }}>
                      <CardContent>
                        <Typography variant="h6" sx={{ fontWeight: "bold", color: "#1976d2" }}>
                          Election Details
                        </Typography>
                        <Divider sx={{ marginBottom: 2 }} />

                        <Grid container spacing={2}>
                          {/* Contact Number */}
                          <Grid item xs={4}>
                            <Typography sx={{ fontWeight: "bold" }}>Election Name:</Typography>
                          </Grid>
                          <Grid item xs={8}>

                            <Typography>{selectedCandidate?.electionName}</Typography>
                          </Grid>

                          {/* Email */}
                          <Grid item xs={4}>
                            <Typography sx={{ fontWeight: "bold" }}>Party Name:</Typography>
                          </Grid>
                          <Grid item xs={8}>

                            <Typography>{selectedCandidate?.partyName}</Typography>
                          </Grid>
                        </Grid>
                      </CardContent>
                    </Card>

                    {/* Address Section */}
                    <Card sx={{ boxShadow: 2, borderRadius: 2 }}>
                      <CardContent>
                        <Typography variant="h6" sx={{ fontWeight: "bold", color: "#1976d2" }}>
                          Address Details
                        </Typography>
                        <Divider sx={{ marginBottom: 2 }} />

                        <Grid container spacing={2}>
                          {/* Mailing Address */}
                          <Grid item xs={4}>
                            <Typography sx={{ fontWeight: "bold" }}>Mailing Address:</Typography>
                          </Grid>
                          <Grid item xs={8}>
                            <Typography>

                            </Typography>
                          </Grid>

                          {/* Residential Address */}
                          <Grid item xs={4}>
                            <Typography sx={{ fontWeight: "bold" }}>Residential Address:</Typography>
                          </Grid>
                          <Grid item xs={8}>
                            <Typography>
 

                              {selectedCandidate?.residentialAddress?.street}, {selectedCandidate?.residentialAddress?.city}, {selectedCandidate?.residentialAddress?.zipcode}
                            </Typography>
                          </Grid>
                        </Grid>
                      </CardContent>
                    </Card>

        
                    {/* Bank Details Section */}
                    <Card sx={{ boxShadow: 2, borderRadius: 2 }}>
                      <CardContent>
                        <Typography variant="h6" sx={{ fontWeight: "bold", color: "#1976d2" }}>
                          Bank Details
                        </Typography>
                        <Divider sx={{ marginBottom: 2 }} />

                        <Grid container spacing={2}>
                          {/* Bank Name */}
                          <Grid item xs={4}>
                            <Typography sx={{ fontWeight: "bold" }}>Bank Name:</Typography>
                          </Grid>
                          <Grid item xs={8}>
                            <Typography>{selectedCandidate?.bankDetails?.bankName}</Typography>
                          </Grid>

                          {/* Bank Address */}
                          <Grid item xs={4}>
                            <Typography sx={{ fontWeight: "bold" }}>Bank Address:</Typography>
                          </Grid>
                          <Grid item xs={8}>

                            <Typography>{selectedCandidate?.bankDetails?.bankAddress}</Typography>
                          </Grid>
                        </Grid>
                      </CardContent>
                    </Card>        
                  </Box>
                ) : (
                  <Typography variant="body1" sx={{ textAlign: "center", color: "#757575" }}>
                    Loading...
                  </Typography>
                )}
              </DialogContent>
        
              {/* Close Button */}
              <DialogActions sx={{ justifyContent: "center", padding: "10px", backgroundColor: "#f1f1f1" }}>
                <Button onClick={handleClose} variant="contained" color="primary">
                  Close
                </Button>
              </DialogActions>
            </Dialog>
    </>
  )
}
