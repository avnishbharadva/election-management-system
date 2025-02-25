import React, { useCallback, useState } from "react";
import { Button, Typography, RadioGroup, FormControlLabel, Radio, Select, MenuItem, Box, TextField, FormLabel, FormControl, InputLabel } from "@mui/material";
import { useDropzone } from "react-dropzone";
import {DropzoneContainer, Title} from '../../style/CandidateFormCss';
import {Section} from '../../style/CandidateFormCss';
import {Row} from '../../style/CandidateFormCss';
import {DocumentContainer} from '../../style/CandidateFormCss';
import {FlexCenter} from '../../style/CandidateFormCss';
import {DividerStyle} from '../../style/CandidateFormCss';
import CloseIcon from '@mui/icons-material/Close';

const CandidateForm: React.FC = ()=> {
  const [profilePic, setProfilePic] = useState<string | null>(null);
  const [signature, setSignature] = useState<string | null>(null);

  const onDropProfile = useCallback((acceptedFiles: File[]) => {
    const file = acceptedFiles[0];
    const reader = new FileReader();
    reader.onload = () => setProfilePic(reader.result as string);
    reader.readAsDataURL(file);
  }, []);

  const onDropSignature = useCallback((acceptedFiles: File[]) => {
    const file = acceptedFiles[0];
    const reader = new FileReader();
    reader.onload = () => setSignature(reader.result as string);
    reader.readAsDataURL(file);
  }, []);

  const { getRootProps: getProfileProps, getInputProps: getProfileInputProps } = useDropzone({ onDrop: onDropProfile });
  const { getRootProps: getSignatureProps, getInputProps: getSignatureInputProps } = useDropzone({ onDrop: onDropSignature });

  return (
    <>
      <Box>
          <Box sx={{display:'flex', justifyContent:'flex-end'}}>
            <CloseIcon/>
          </Box>
          <Title variant="h5" align="center" gutterBottom>
            CANDIDATE REGISTRATION
          </Title>

          <Section>
            <Title variant="h6">Personal Information</Title>
            <DividerStyle />
            <Row>
              <TextField fullWidth label="First Name" />
              <TextField fullWidth label="Middle Name" />
              <TextField fullWidth label="Last Name" />
            </Row>
            <Row>
              <TextField fullWidth type="date" label="Date of Birth" InputLabelProps={{ shrink: true }} />
              <TextField fullWidth label="Email" type="email" />
            </Row>
            <Row>
              <FormLabel>Gender:</FormLabel>
              <RadioGroup name="Gender" row>
                <FormControlLabel value="option1" control={<Radio />} label="Male" />
                <FormControlLabel value="option2" control={<Radio />} label="Female" />
              </RadioGroup>
            </Row>
            <Row>
              <FormControl fullWidth>
                <InputLabel>Marital Status</InputLabel>
                <Select label="Marital Status">
                  <MenuItem value="option1">Single</MenuItem>
                  <MenuItem value="option2">Married</MenuItem>
                  <MenuItem value="option3">Divorced</MenuItem>
                  <MenuItem value="option4">Widowed</MenuItem>
                </Select>
              </FormControl>
              <TextField fullWidth label="Spouse Name" name="spouse_name" />
              <TextField fullWidth label="No of Children" name="no_of_children" type="number" onKeyDown={(evt) => ["e", "E", "+", "-"].includes(evt.key) && evt.preventDefault()}/>
            </Row>
          </Section>

          <Section>
            <Title variant="h6">Election Details</Title>
            <DividerStyle />
            <Row>
              <FormControl fullWidth>
                <InputLabel>Election Name</InputLabel>
                <Select fullWidth label="Choose Election">
                  <MenuItem value="Presidential Election 2024">Presidential Election 2024</MenuItem>
                  <MenuItem value="State Senate Election">State Senate Election</MenuItem>
                  <MenuItem value="Mayoral Election">Mayoral Election</MenuItem>
                  <MenuItem value="Congressional Election">Congressional Election</MenuItem>
                  <MenuItem value="Senate Election">Senate Election</MenuItem>
                </Select>
              </FormControl>
              
              <FormControl fullWidth>
                <InputLabel>Choose Party</InputLabel>
                <Select fullWidth label="Choose Party">
                  <MenuItem value="Republican">Republican Party</MenuItem>
                  <MenuItem value="Democratic">Democratic Party</MenuItem>
                </Select>
              </FormControl>
              <TextField fullWidth label="State" value={"New York"} />
            </Row>
          </Section>

          <Section>
            <Title variant="h6">Residential Address</Title>
            <DividerStyle />
            <Row>
              <TextField fullWidth label="Street" />
              <TextField fullWidth label="City" />
              <TextField fullWidth label="Zipcode" />
            </Row>
          </Section>

          <Section>
            <Title variant="h6">Mailing Address</Title>
            <DividerStyle />
            <Row>
              <TextField fullWidth label="Street" />
              <TextField fullWidth label="City" />
              <TextField fullWidth label="Zipcode" />
            </Row>
          </Section>

          <Section>
            <Title variant="h6">Bank Details</Title>
            <DividerStyle />
            <Row>
              <TextField fullWidth label="Bank Name" />
              <TextField fullWidth label="Bank Address" />
            </Row>
          </Section>

          <Section>
            <Title variant="h6">Upload Documents</Title>
            <DividerStyle />
            <DocumentContainer>
              <Box>
                <Typography variant="subtitle1">Candidate Image</Typography>
                <DropzoneContainer {...getProfileProps()}>
                  <input {...getProfileInputProps()} />
                  {profilePic ? <img src={profilePic} alt="Profile" width="100" height="100" style={{ borderRadius: "50%" }} /> : <Typography>Drag & Drop or Click</Typography>}
                </DropzoneContainer>
              </Box>
              <Box>
                <Typography variant="subtitle1">Signature</Typography>
                <DropzoneContainer {...getSignatureProps()}>
                  <input {...getSignatureInputProps()} />
                  {signature ? <img src={signature} alt="Signature" width="200" height="50" /> : <Typography>Drag & Drop or Click</Typography>}
                </DropzoneContainer>
              </Box>
            </DocumentContainer>
          </Section>

          <FlexCenter>
            <Button variant="contained">Submit</Button>
            <Button variant="contained">Cancel</Button>
          </FlexCenter>
      </Box>
  </>
  );
};

export default CandidateForm;
