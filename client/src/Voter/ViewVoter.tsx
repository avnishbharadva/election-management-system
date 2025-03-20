import { Box, Card, CardContent, Typography, Divider, Grid, Dialog, DialogContent, DialogTitle } from '@mui/material';
import { BoxContainer, CardContainer, MainContainer, StyleDivider, ImageContent,BoldTypography } from '../style/VoterStyleCss'; 
import { useSearchVotersQuery } from '../store/feature/voter/VoterAction';
interface ViewVoterProps {
    handleClose: () => void;
    ssnNumber?: number | null ;
    open:  boolean;
  }
 
 
const ViewVoter =  ({ handleClose, ssnNumber,open }: ViewVoterProps) => {
   
  const {data}= useSearchVotersQuery({ssnNumber:ssnNumber},
    {skip: !ssnNumber}
  ) 

const voter=data?.data[0]
 
 
  return (
    <>
    <Dialog open={open} onClose={handleClose} fullWidth maxWidth="md">
          <DialogTitle sx={{ backgroundColor: "#1976d2", color: "white", textAlign: "center", fontWeight: "bold" }}>
              Voter Details
          </DialogTitle>

          <DialogContent sx={{ padding: "20px", backgroundColor: "#f9f9f9" }}>
          {ssnNumber &&  (
        <BoxContainer>
            <CardContainer>
                <CardContent>
                    <MainContainer>
                        Personal Information
                    </MainContainer>
                    <StyleDivider />
 
                    <Grid container spacing={2} alignItems="center">
                        <Grid item xs={8}>
                            <Grid container spacing={2}>
                                <Grid item xs={4}>
                                    <BoldTypography>Voter ID:</BoldTypography>
                                </Grid>
                                <Grid item xs={8}>
                                    <Typography>{voter?.voterId}</Typography>
                                </Grid>
 
                                <Grid item xs={4}>
                                    <BoldTypography>Name:</BoldTypography>
                                </Grid>
                                <Grid item xs={8}>
                                    <Typography>
                                        {voter?.firstName} {voter?.middleName ? ` ${voter?.middleName}` : ""} {voter?.lastName}
                                    </Typography>
                                </Grid>
 
                                <Grid item xs={4}>
                                    <BoldTypography>Email:</BoldTypography>
                                </Grid>
                                <Grid item xs={8}>
                                    <Typography>{voter?.email}</Typography>
                                </Grid>
 
                                <Grid item xs={4}>
                                    <BoldTypography>Gender:</BoldTypography>
                                </Grid>
                                <Grid item xs={8}>
                                    <Typography>{voter?.gender}</Typography>
                                </Grid>
 
                                <Grid item xs={4}>
                                    <BoldTypography>Date of Birth:</BoldTypography>
                                </Grid>
                                <Grid item xs={8}>
                                    <Typography>{voter?.dateOfBirth}</Typography>
                                </Grid>
 
                                <Grid item xs={4}>
                                    <BoldTypography>Phone Number:</BoldTypography>
                                </Grid>
                                <Grid item xs={8}>
                                    <Typography>{voter?.phoneNumber}</Typography>
                                </Grid>
 
                                <Grid item xs={4}>
                                    <BoldTypography>Suffix Name:</BoldTypography>
                                </Grid>
                                <Grid item xs={8}>
                                    <Typography>{voter?.suffixName}</Typography>
                                </Grid>
 
                                <Grid item xs={4}>
                                    <BoldTypography>SSN Number:</BoldTypography>
                                </Grid>
                                <Grid item xs={8}>
                                    <Typography>{voter?.ssnNumber}</Typography>
                                </Grid>
 
                                <Grid item xs={4}>
                                    <BoldTypography>DMV Number:</BoldTypography>
                                </Grid>
                                <Grid item xs={8}>
                                    <Typography>{voter?.dmvNumber}</Typography>
                                </Grid>
                            </Grid>
                        </Grid>
 
                        <Grid item xs={4} sx={{ display: "flex", justifyContent: "center", alignItems: "center" }}>
                         
                                <Box sx={{ textAlign: "center", marginBottom: "13rem" }}>
                                    <img
                                        src={`data:image/png;base64,${voter?.image}`}
                                        alt="Voter"
                                        style={{
                                            width: "150px",
                                            height: "150px",
                                            borderRadius: "8px",
                                            boxShadow: "2px 2px 5px rgba(0,0,0,0.2)",
                                        }}
                                    />
                                    <MainContainer >
                                        Voter Image
                                    </MainContainer>
                                </Box>
                            
                        </Grid>
                    </Grid>
                </CardContent>
            </CardContainer>
 
            <CardContainer>
                <CardContent>
                    <Typography variant="h6" sx={{ fontWeight: "bold", color: "#1976d2" }}>
                        Voting Details
                    </Typography>
                    <Divider sx={{ marginBottom: 2 }} />
 
                    <Grid container spacing={2} alignItems="center">
                        <Grid item xs={8}>
                            <Grid container spacing={2}>
                                <Grid item xs={4}>
                                    <BoldTypography>Status: </BoldTypography>
                                </Grid>
                                <Grid item xs={8}>
                                    <Typography>{voter?.status}</Typography>
                                </Grid>
 
                                <Grid item xs={4}>
                                    <BoldTypography>Party</BoldTypography>
                                </Grid>
                                <Grid item xs={8}>
                                    <Typography>{voter?.party}</Typography>
                                </Grid>
 
                                <Grid item xs={4}>
                                    <BoldTypography>Has Voted Before:</BoldTypography>
                                </Grid>
                                <Grid item xs={8}>
                                    <Typography>{voter?.hasVotedBefore ? "true" : "false"}</Typography>
                                </Grid>
 
                                <Grid item xs={4}>
                                    <BoldTypography>First Voted Year:</BoldTypography>
                                </Grid>
                                <Grid item xs={8}>
                                    <Typography>{voter?.firstVotedYear}</Typography>
                                </Grid>
                            </Grid>
                        </Grid>
                    </Grid>
                </CardContent>
            </CardContainer>
 
            <Card sx={{ boxShadow: 2, borderRadius: 2 }}>
                <CardContent>
                    <Typography variant="h6" sx={{ fontWeight: "bold", color: "#1976d2" }}>
                        Address Details
                    </Typography>
                    <Divider sx={{ marginBottom: 2 }} />
 
                    <Grid container spacing={2}>
                        <Grid item xs={4}>
                            <BoldTypography>Mailing Address:</BoldTypography>
                        </Grid>
                        <Grid item xs={8}>
                            <Typography>
                                {voter?.mailingAddress?.addressLine}, {voter?.mailingAddress?.aptNumber},{voter?.mailingAddress?.city},{voter?.mailingAddress?.county}, {voter?.mailingAddress?.zipCode}
                            </Typography>
                        </Grid>
 
                        <Grid item xs={4}>
                            <BoldTypography>Residential Address:</BoldTypography>
                        </Grid>
                        <Grid item xs={8}>
                            <Typography>
                                {voter?.residentialAddress?.addressLine}, {voter?.residentialAddress?.aptNumber},{voter?.residentialAddress?.city},{voter?.residentialAddress?.county}, {voter?.residentialAddress?.zipCode}
                            </Typography>
                        </Grid>
                    </Grid>
                </CardContent>
            </Card>
 
            <CardContainer>
                <ImageContent>

                        <Box sx={{ textAlign: "center" }}>
                            <img
                                src={`data:image/png;base64,${voter?.signature}`}
                                alt="Signature"
                                style={{
                                    width: "150px",
                                    height: "80px",
                                    borderRadius: "8px",
                                    boxShadow: "2px 2px 5px rgba(0,0,0,0.2)",
                                }}
                            />
                            <MainContainer>Signature</MainContainer>
                        </Box>
                
                </ImageContent>
            </CardContainer>
        </BoxContainer>
    ) }
          </DialogContent>
          </Dialog>
   
    </>
  )
}
 
export default ViewVoter;