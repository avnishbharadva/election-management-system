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
  
  interface ViewVoterProps {
    handleClose: () => void; // Directly pass the close function
    voter?: any;
    open:  boolean;
  }
  
  export default function ViewVoter({ handleClose, voter,open }: ViewVoterProps) {
    const voterImage = voter?.image;
    const voterSignature = voter?.signature;
  
    return (
        <Dialog open={open} onClose={handleClose} fullWidth maxWidth="md">
            <DialogTitle sx={{ backgroundColor: "#1976d2", color: "white", textAlign: "center", fontWeight: "bold" }}>
                Voter Details
            </DialogTitle>
  
            <DialogContent sx={{ padding: "20px", backgroundColor: "#f9f9f9" }}>
                  {voter ? (
                      <Box sx={{ display: "flex", flexDirection: "column", gap: 3 }}>
                          <Card sx={{ boxShadow: 2, borderRadius: 2 }}>
                              <CardContent>
                                  <Typography variant="h6" sx={{ fontWeight: "bold", color: "#1976d2" }}>
                                      Personal Information
                                  </Typography>
                                  <Divider sx={{ marginBottom: 2 }} />
  
                                  <Grid container spacing={2} alignItems="center">
                                      <Grid item xs={8}>
                                          <Grid container spacing={2}>
                                              <Grid item xs={4}>
                                                  <Typography sx={{ fontWeight: "bold" }}>Voter ID:</Typography>
                                              </Grid>
                                              <Grid item xs={8}>
                                                  <Typography>{voter?.voterId}</Typography>
                                              </Grid>
  
                                              <Grid item xs={4}>
                                                  <Typography sx={{ fontWeight: "bold" }}>Name:</Typography>
                                              </Grid>
                                              <Grid item xs={8}>
                                                  <Typography>
                                                      {voter?.firstName} {voter?.middleName ? ` ${voter?.middleName}` : ""} {voter?.lastName}
                                                  </Typography>
                                              </Grid>
  
                                              <Grid item xs={4}>
                                                  <Typography sx={{ fontWeight: "bold" }}>Email:</Typography>
                                              </Grid>
                                              <Grid item xs={8}>
                                                  <Typography>{voter?.email}</Typography>
                                              </Grid>
  
                                              <Grid item xs={4}>
                                                  <Typography sx={{ fontWeight: "bold" }}>Gender:</Typography>
                                              </Grid>
                                              <Grid item xs={8}>
                                                  <Typography>{voter?.gender}</Typography>
                                              </Grid>
  
                                              <Grid item xs={4}>
                                                  <Typography sx={{ fontWeight: "bold" }}>Date of Birth:</Typography>
                                              </Grid>
                                              <Grid item xs={8}>
                                                  <Typography>{voter?.dateOfBirth}</Typography>
                                              </Grid>
  
                                              <Grid item xs={4}>
                                                  <Typography sx={{ fontWeight: "bold" }}>Phone Number:</Typography>
                                              </Grid>
                                              <Grid item xs={8}>
                                                  <Typography>{voter?.phoneNumber}</Typography>
                                              </Grid>
  
                                              <Grid item xs={4}>
                                                  <Typography sx={{ fontWeight: "bold" }}>Suffix Name:</Typography>
                                              </Grid>
                                              <Grid item xs={8}>
                                                  <Typography>{voter?.suffixName}</Typography>
                                              </Grid>
  
                                              <Grid item xs={4}>
                                                  <Typography sx={{ fontWeight: "bold" }}>SSN Number:</Typography>
                                              </Grid>
                                              <Grid item xs={8}>
                                                  <Typography>{voter?.ssnNumber}</Typography>
                                              </Grid>
  
                                              <Grid item xs={4}>
                                                  <Typography sx={{ fontWeight: "bold" }}>DMV Number:</Typography>
                                              </Grid>
                                              <Grid item xs={8}>
                                                  <Typography>{voter?.dmvNumber}</Typography>
                                              </Grid>
                                          </Grid>
                                      </Grid>
  
                                      <Grid item xs={4} sx={{ display: "flex", justifyContent: "center", alignItems: "center" }}>
                                          {voterImage && (
                                              <Box sx={{ textAlign: "center", marginBottom: "13rem" }}>
                                                  <img
                                                      src={`data:image/png;base64,${voterImage}`}
                                                      alt="Voter"
                                                      style={{
                                                          width: "150px",
                                                          height: "150px",
                                                          borderRadius: "8px",
                                                          boxShadow: "2px 2px 5px rgba(0,0,0,0.2)",
                                                      }}
                                                  />
                                                  <Typography variant="h6" sx={{ fontWeight: "bold", color: "#1976d2" }}>
                                                      Voter Image
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
                                      Voting Details
                                  </Typography>
                                  <Divider sx={{ marginBottom: 2 }} />
  
                                  <Grid container spacing={2} alignItems="center">
                                      <Grid item xs={8}>
                                          <Grid container spacing={2}>
                                              <Grid item xs={4}>
                                                  <Typography sx={{ fontWeight: "bold" }}>Status: </Typography>
                                              </Grid>
                                              <Grid item xs={8}>
                                                  <Typography>{voter?.statusId}</Typography>
                                              </Grid>
  
                                              <Grid item xs={4}>
                                                  <Typography sx={{ fontWeight: "bold" }}>PartyId:</Typography>
                                              </Grid>
                                              <Grid item xs={8}>
                                                  <Typography>{voter?.partyId}</Typography>
                                              </Grid>
  
                                              <Grid item xs={4}>
                                                  <Typography sx={{ fontWeight: "bold" }}>Has Voted Before:</Typography>
                                              </Grid>
                                              <Grid item xs={8}>
                                                  <Typography>{voter?.hasVotedBefore ? "true" : "false"}</Typography>
                                              </Grid>
  
                                              <Grid item xs={4}>
                                                  <Typography sx={{ fontWeight: "bold" }}>First Voted Year:</Typography>
                                              </Grid>
                                              <Grid item xs={8}>
                                                  <Typography>{voter?.firstVotedYear}</Typography>
                                              </Grid>
                                          </Grid>
                                      </Grid>
                                  </Grid>
                              </CardContent>
                          </Card>
  
                          <Card sx={{ boxShadow: 2, borderRadius: 2 }}>
                              <CardContent>
                                  <Typography variant="h6" sx={{ fontWeight: "bold", color: "#1976d2" }}>
                                      Address Details
                                  </Typography>
                                  <Divider sx={{ marginBottom: 2 }} />
  
                                  <Grid container spacing={2}>
                                      <Grid item xs={4}>
                                          <Typography sx={{ fontWeight: "bold" }}>Mailing Address:</Typography>
                                      </Grid>
                                      <Grid item xs={8}>
                                          <Typography>
                                              {voter?.mailingAddress?.addressLine}, {voter?.mailingAddress?.aptNumber},{voter?.mailingAddress?.city},{voter?.mailingAddress?.county}, {voter?.mailingAddress?.zipcode}
                                          </Typography>
                                      </Grid>
  
                                      <Grid item xs={4}>
                                          <Typography sx={{ fontWeight: "bold" }}>Residential Address:</Typography>
                                      </Grid>
                                      <Grid item xs={8}>
                                          <Typography>
                                              {voter?.residentialAddress?.addressLine}, {voter?.residentialAddress?.aptNumber},{voter?.residentialAddress?.city},{voter?.residentialAddress?.county}, {voter?.residentialAddress?.zipcode}
                                          </Typography>
                                      </Grid>
                                  </Grid>
                              </CardContent>
                          </Card>
  
                          <Card sx={{ boxShadow: 2, borderRadius: 2 }}>
                              <CardContent sx={{ display: "flex", flexDirection: "row", gap: 3, justifyContent: "center", alignItems: "center" }}>
                                  {voterSignature && (
                                      <Box sx={{ textAlign: "center" }}>
                                          <img
                                              src={`data:image/png;base64,${voterSignature}`}
                                              alt="Signature"
                                              style={{
                                                  width: "150px",
                                                  height: "80px",
                                                  borderRadius: "8px",
                                                  boxShadow: "2px 2px 5px rgba(0,0,0,0.2)",
                                              }}
                                          />
                                          <Typography variant="h6" sx={{ fontWeight: "bold", color: "#1976d2" }}>Signature</Typography>
                                      </Box>
                                  )}
                              </CardContent>
                          </Card>
                      </Box>
                  ) : (
                      <Typography variant="body1" sx={{ textAlign: "center", color: "#757575" }}>
                          Loading...
                      </Typography>
                  )}
              </DialogContent>
  
              <DialogActions>
                  <Button onClick={handleClose}>Close</Button>
              </DialogActions>
          </Dialog>
      );
  }