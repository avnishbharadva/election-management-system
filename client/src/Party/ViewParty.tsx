
import React from 'react';
import { Modal, Box, Typography, Button, Card, CardContent, Grid, Divider } from '@mui/material';
import { ModelBox } from '../style/ModelCss'; 

interface ViewPartyProps {
  party: any;
  open: boolean;
  handleClose: () => void;
}

const ViewParty: React.FC<ViewPartyProps> = ({ party, open, handleClose }) => {
  if (!party) return null;

  return (
    <Modal
      open={open}
      onClose={handleClose}
      aria-labelledby="view-party-modal-title"
      aria-describedby="view-party-modal-description"
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

            <Grid container spacing={2} alignItems="center">
              {/* Personal Details - Left Side */}
              <Grid item xs={8}>
                <Grid container spacing={2}>
                  <Grid item xs={4} sx={{ fontWeight: 'bold', display: 'inline' }}>Party Id: </Grid>
                  <Grid item xs={8} sx={{ display: 'inline' }}>{party.partyId}</Grid>
                          
                  <Grid item xs={4} sx={{ fontWeight: 'bold', display: 'inline' }}>Party Name: </Grid>
                  <Grid item xs={8} sx={{ display: 'inline' }}>{party.partyName}</Grid>
                                
                  <Grid item xs={4} sx={{ fontWeight: 'bold', display: 'inline' }}>Abbreviation: </Grid>
                  <Grid item xs={8} sx={{ display: 'inline' }}>{party.partyAbbreviation}</Grid>                 
              
                  <Grid item xs={4} sx={{ fontWeight: 'bold', display: 'inline' }}>Leader: </Grid>
                  <Grid item xs={8} sx={{ display: 'inline' }}>{party.partyLeaderName}</Grid>
                            
                  <Grid item xs={4} sx={{ fontWeight: 'bold', display: 'inline' }}>Foundation Year: </Grid>
                  <Grid item xs={8} sx={{ display: 'inline' }}>{party.partyFoundationYear}</Grid>

                  <Grid item xs={4} sx={{ fontWeight: 'bold', display: 'inline' }}>Website: </Grid>
                  <Grid item xs={8} sx={{ display: 'inline' }}>{party.partyWebSite}</Grid>            
              
                  <Grid item xs={4} sx={{ fontWeight: 'bold', display: 'inline' }}>Headquarters: </Grid>
                  <Grid item xs={8} sx={{ display: 'inline' }}>{party.headQuarters}</Grid>           
        
                  <Grid item xs={4} sx={{ fontWeight: 'bold', display: 'inline' }}>Founder Name: </Grid>
                  <Grid item xs={8} sx={{ display: 'inline' }}>{party.partyFounderName}</Grid>
                  </Grid>
              </Grid>

               {/* Party Symbol */}
              <Grid item xs={4} sx={{ display: "flex", flexDirection: "column", alignItems: "center", justifyContent: "center"}} >
                <Box sx={{ display: 'flex', alignItems: 'center'}}>
                        {party.partySymbol ? (
                          <img
                          src={`data:image/png;base64,${party.partySymbol}`} 
                          alt="Party Symbol" 
                          style={{ maxHeight: '100px', maxWidth: '100px', marginRight: '10px' }} />
                        ) : (
                          <Typography sx={{ display: 'inline', marginRight: '60px' }}> </Typography>
                        )}
                </Box>
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

