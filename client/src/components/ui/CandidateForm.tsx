import React, { useState } from "react";
import { TextField, Button, Box, Paper, RadioGroup, FormControlLabel, Radio, FormLabel, FormControl, InputLabel, Select, MenuItem, Typography } from "@mui/material";

const CandidateForm: React.FC = () => {
    const [profilePic, setProfilePic] = useState<string | null>(null);
    const [signature, setSignature] = useState<string | null>(null);
  
    const handleProfilePicChange = (event: React.ChangeEvent<HTMLInputElement>) => {
      if (event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        const reader = new FileReader();
        reader.onloadend = () => {
          setProfilePic(reader.result as string);
        };
        reader.readAsDataURL(file);
      }
    };
  
    const handleSignatureChange = (event: React.ChangeEvent<HTMLInputElement>) => {
      if (event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        const reader = new FileReader();
        reader.onloadend = () => {
          setSignature(reader.result as string);
        };
        reader.readAsDataURL(file);
      }
    };
  return (
   <>
     <Box sx={{display: 'flex', justifyContent: 'center', alignItems:"center"}}>
      <Paper elevation={3} sx={{ padding: 4 , width:'auto'}}>
        <Typography variant="h5" textAlign="center" mb={2}>
          Register Candidate
        </Typography>
        <form>
          {/* First Row */}
          <Box sx={{ display: 'flex', gap: '2rem', mb: 2 }}>
            <TextField fullWidth label="First Name" name="firstname" />
            <TextField fullWidth label="Middle Name" name="middlename" />
            <TextField fullWidth label="Last Name" name="lastname" />
          </Box>

          {/* Second Row */}
          <FormLabel>Date of Birth</FormLabel>
          <Box sx={{ display: 'flex', gap: '2rem', mb: 2 }}>
            <TextField
              fullWidth
              name="dob"
              type="date"
              InputLabelProps={{ shrink: true }}
            />
            <TextField
              fullWidth
              label="Email"
              name="email"
              type="email"
            />
            <TextField
              fullWidth
              label="State Name"
              name="state_name"
              value="New York"
              aria-readonly
            />
            
          </Box>

          {/*Third Row */}
            <Box sx={{ display: 'flex', gap: '2rem', mb: 2, alignItems:'center' }}>
                <FormLabel>Gender:</FormLabel>
                <RadioGroup
                    name="Gender"
                    row >
                    <FormControlLabel value="option1" control={<Radio />} label="Male" />
                    <FormControlLabel value="option2" control={<Radio />} label="Female" />
                </RadioGroup>
            </Box>

            {/* Fourth Row */}
            <Box sx={{ display: 'flex', gap: '2rem', mb: 2 }}>
            <FormControl fullWidth>
                <InputLabel>Marital Status</InputLabel>
                <Select
                label="Choose Status"
                >
                <MenuItem value="option1">Single</MenuItem>
                <MenuItem value="option2">Married</MenuItem>
                <MenuItem value="option3">Divorced</MenuItem>
                <MenuItem value="option4">Widowed</MenuItem>
                </Select>
            </FormControl>
            <TextField
              fullWidth
              label="Spouse Name"
              name="spouse_name"
            />
            <TextField
              fullWidth
              label="No of Children"
              name="no_of_children"
              type="number"
            />
            </Box>

            {/*Fifth Row */}
            <Box sx={{ display: 'flex', gap: '2rem', mb: 2 }}>
                <TextField fullWidth label="Candidate SSN" name="candidate_ssn"/>
                <FormControl fullWidth>
                    <InputLabel>Party Name</InputLabel>
                    <Select
                    label="Choose Status"
                    >
                    <MenuItem value="option1">BJP</MenuItem>
                    <MenuItem value="option2">Congress</MenuItem>
                    <MenuItem value="option3">AAP</MenuItem>
                    <MenuItem value="option4">Others</MenuItem>
                    </Select>
                </FormControl>
                <FormControl fullWidth>
                    <InputLabel>Election Type</InputLabel>
                    <Select
                    label="Choose Election"
                    >
                    <MenuItem value="option1">State</MenuItem>
                    <MenuItem value="option2">Country</MenuItem>
                    </Select>
                </FormControl>  
            </Box>

            {/*Sixth Row */}
            <FormLabel>Residencial Address</FormLabel>
            <Box sx={{ display: 'flex', gap: '2rem', mb: 2 }}>
                <TextField
                    fullWidth
                    label="Street"
                    name="street"
                />
                <TextField
                    fullWidth
                    label="City"
                    name="city"
                />
                <TextField
                    fullWidth
                    label="Zipcode"
                    name="zipcode"
                />
            </Box>

             {/*Seventh Row */}
            <FormLabel>Mailing Address</FormLabel>
            <Box sx={{ display: 'flex', gap: '2rem', mb: 2 }}>
                <TextField
                    fullWidth
                    label="Street"
                    name="street"
                />
                <TextField
                    fullWidth
                    label="City"
                    name="city"
                />
                <TextField
                    fullWidth
                    label="Zipcode"
                    name="zipcode"
                />
            </Box>

            {/*Eigth row */}
            <FormLabel>Bank Details</FormLabel>
            <Box sx={{display: "flex", gap: "2rem", mb: 2 }}>
                <TextField
                    label="Bank Name"
                    name="bank_name"
                />
                <TextField
                    fullWidth
                    label="Bank Address"
                    name="bank_address"
                />
            </Box>

            {/*Nineth row */}
            <Box sx={{display: "flex", gap: "2rem", mb: 2 }}>
            <Box sx={{ display: "flex", gap: "2rem", mb: 2, alignItems: "center" }}>
              <input
                type="file"
                accept="image/*"
                onChange={handleProfilePicChange}
                style={{ display: "none" }}
                id="profilePicUpload"
              />
              <label htmlFor="profilePicUpload">
                <Button variant="contained" component="span">
                  Upload Profile Picture
                </Button>
              </label>
              {profilePic && (
                <Box sx={{ marginLeft: "1rem" }}>
                  <img
                    src={profilePic}
                    alt="Profile"
                    style={{ width: "100px", height: "100px", borderRadius: "50%" }}
                  />
                </Box>
              )}
            </Box>

            {/* Signature Upload */}
            <Box sx={{ display: "flex", gap: "2rem", mb: 2, alignItems: "center" }}>
              <input
                type="file"
                accept="image/*"
                onChange={handleSignatureChange}
                style={{ display: "none" }}
                id="signatureUpload"
              />
              <label htmlFor="signatureUpload">
                <Button variant="contained" component="span">
                  Upload Signature
                </Button>
              </label>
              {signature && (
                <Box sx={{ marginLeft: "1rem" }}>
                  <img
                    src={signature}
                    alt="Signature"
                    style={{ width: "200px", height: "50px", objectFit: "contain" }}
                  />
                </Box>
              )}
            </Box>
            </Box>

          <Button
            fullWidth
            type="submit"
            variant="contained"
            sx={{
              background: "linear-gradient(90deg, #1E90FF, rgb(29, 38, 154))",
              color: "white",
              padding: "0.8rem",
              textTransform: "none",
              "&:hover": {
                background: "linear-gradient(90deg, #007BFF, #0056b3)",
              },
            }}
          >
            Submit
          </Button>
        </form>
      </Paper>
    </Box>
   </>
  );
};

export default CandidateForm;
