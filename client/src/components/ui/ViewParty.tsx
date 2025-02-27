
import { Modal, Box, Typography, Button, Card, CardContent, Grid, Divider } from '@mui/material';
import { ModelBox } from '../../style/ModelCss'; // Assuming you have ModelBox styles
 

 
const ViewParty = ({ party, open, handleClose }:any) => {
  if (!party) return;
 
  return (
    <Modal
      open={open}
      onClose={handleClose}
      aria-labelledby="view-party-modal-title"
      aria-describedby="view-party-modal-description"
      fullWidth
      maxWidth="md"
    >
      <ModelBox>
        <Typography
          id="view-party-modal-title"
          variant="h6"
          component="h2"
          sx={{ backgroundColor: '#1976d2', color: 'white', textAlign: 'center', fontWeight: 'bold', padding: '10px' }}
        >
          Party Details
        </Typography>
 
        <Card sx={{ boxShadow: 2, borderRadius: 2, margin: '20px' }}>
          <CardContent>
            <Typography variant="h6" sx={{ fontWeight: 'bold', color: '#1976d2' }}>
              Party Information
            </Typography>
            <Divider sx={{ marginBottom: 2 }} />
 
            <Grid container>
              <Grid item xs={12}>
                <Typography sx={{ fontWeight: 'bold', display: 'inline' }}>Party Id: </Typography>
                <Typography sx={{ display: 'inline' }}>{party.partyId}</Typography>
              </Grid>
              <Grid item xs={12}>
                <Typography sx={{ fontWeight: 'bold', display: 'inline' }}>Party Name: </Typography>
                <Typography sx={{ display: 'inline' }}>{party.partyName}</Typography>
              </Grid>
 
              <Grid item xs={12}>
                <Grid container alignItems="center" justifyContent="space-between">
                  <Grid item>
                    <Typography sx={{ fontWeight: 'bold', display: 'inline' }}>Abbreviation: </Typography>
                    <Typography sx={{ display: 'inline' }}>{party.partyAbbreviation}</Typography>
                  </Grid>
                  <Grid item>
                    <Box sx={{ display: 'flex', alignItems: 'center' }}>
                      {party.image ? (
                        <img src={party.image} alt="Party Symbol" style={{ maxHeight: '50px', maxWidth: '100px', marginRight: '10px' }} />
                      ) : (
                        <Typography sx={{ display: 'inline', marginRight: '10px' }}> </Typography>
                      )}
                      <Typography sx={{ fontWeight: 'bold', display: 'inline' }}>Symbol: </Typography>
                    </Box>
                  </Grid>
                </Grid>
              </Grid>
 
              <Grid item xs={12}>
                <Typography sx={{ fontWeight: 'bold', display: 'inline' }}>Foundation Year: </Typography>
                <Typography sx={{ display: 'inline' }}>{party.partyFoundationYear}</Typography>
              </Grid>
              <Grid item xs={12}>
                <Typography sx={{ fontWeight: 'bold', display: 'inline' }}>Website: </Typography>
                <Typography sx={{ display: 'inline' }}>{party.partyWebSite}</Typography>
              </Grid>
 
              <Grid item xs={12}>
                <Typography sx={{ fontWeight: 'bold', display: 'inline' }}>Headquarters: </Typography>
                <Typography sx={{ display: 'inline' }}>{party.headQuarters}</Typography>
              </Grid>
              <Grid item xs={12}>
                <Typography sx={{ fontWeight: 'bold', display: 'inline' }}>Founder Name: </Typography>
                <Typography sx={{ display: 'inline' }}>{party.founderName}</Typography>
              </Grid>
            </Grid>
          </CardContent>
        </Card>
 
        <Box sx={{ mt: 2, display: 'flex', justifyContent: 'flex-end', padding: '0 20px 20px 20px' }}>
          <Button onClick={handleClose} variant="outlined">
            Close
          </Button>
        </Box>
      </ModelBox>
    </Modal>
  );
};
 
export default ViewParty;
 