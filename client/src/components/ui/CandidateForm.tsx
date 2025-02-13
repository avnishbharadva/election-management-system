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
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { SubmitHandler, useForm } from "react-hook-form";
import { addCandidate, fetchCandidates } from "../../store/feature/candidate/candidateAPI";
import { Form } from "../../style/CommanStyle";
import { Address, BankDetails, CandidateName } from "../../store/feature/candidate/types";
// import { Address } from "cluster";
 

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
  interface IFormInput {
    candidateName: CandidateName;
    candidateSSN: string;
    dateOfBirth: string;
    gender: string;
    maritialStatus: string;
    noOfChildren: number;
    spouseName: string;
    partyId: number;
    residentialAddress: Address;
    mailingAddress: Address;
    stateName: string;
    candidateEmail: string;
    electionId: number;
    bankDetails: BankDetails;
    candidateImage: string;
    candidateSignature: string;
  }
  const dispatch = useDispatch();
  const { loading, success, error } = useSelector((state) => state.candidate);

  const { register, handleSubmit, reset } = useForm<IFormInput>();

  const onSubmit: SubmitHandler<IFormInput> =async (data) => {
    if (!data.candidateSSN) {
      data.candidateSSN = Math.floor(100000000 + Math.random() * 900000000).toString(); // 9-digit random number
    }
    const formData = new FormData();
    formData.append("candidate", JSON.stringify({
      candidateName: data.candidateName,
      residentialAddress: data.residentialAddress,
      mailingAddress: data.mailingAddress,
      bankDetails: data.bankDetails,
      candidateSSN: data.candidateSSN,
      dateOfBirth: data.dateOfBirth,
      gender: data.gender,
      maritialStatus: data.maritialStatus,
      noOfChildren: data.noOfChildren,
      spouseName: data.spouseName,
      partyId: data.partyId,
      stateName: data.stateName,
      candidateEmail: data.candidateEmail,
      electionId: data.electionId,
    }));
  
    // Append the uploaded files
    if (profilePic) {
      const profileFile = dataURLtoFile(profilePic, "profile.jpg");
      formData.append("candidateImage", profileFile);
    }
    if (signature) {
      const signatureFile = dataURLtoFile(signature, "signature.jpg");
      formData.append("candidateSignature", signatureFile);
    }
  
   await dispatch(addCandidate(formData));
     dispatch(fetchCandidates());
  };
  const dataURLtoFile = (dataURL: string, filename: string): File => {
    const [mimePart, base64Data] = dataURL.split(",");
    const mime = mimePart.match(/:(.*?);/)![1];
    const byteString = atob(base64Data);
    const arrayBuffer = new Uint8Array(byteString.length);
    for (let i = 0; i < byteString.length; i++) {
      arrayBuffer[i] = byteString.charCodeAt(i);
    }
    return new File([arrayBuffer], filename, { type: mime });
  };
  return (
    <>
      <Form onSubmit={handleSubmit(onSubmit)}>
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
              <TextField fullWidth label="First Name" {...register("candidateName.firstName", { required: "First name is required" })}/>
              <TextField fullWidth label="Middle Name"  {...register("candidateName.middleName")}/>
              <TextField fullWidth label="Last Name" {...register("candidateName.lastName", { required: "Last name is required" })}/>
            </Row>
            <Row>
              <TextField fullWidth type="date" {...register("dateOfBirth", { required: "Date of Birth is required" })} label="Date of Birth" InputLabelProps={{ shrink: true }} />
              <TextField fullWidth label="Email" type="email" {...register("candidateEmail", { required: "Email is required" })}/>
            </Row>
            <Row>
              <FormLabel>Gender:</FormLabel>
              <RadioGroup  row {...register("gender", { required: "Gender is required" })}>
                <FormControlLabel value="MALE" control={<Radio />} label="Male" />
                <FormControlLabel value="FEMALE" control={<Radio />} label="Female" />
              </RadioGroup>
            </Row>
            <Row>
              <FormControl fullWidth>
                <InputLabel>Marital Status</InputLabel>
                <Select label="Marital Status" {...register("maritialStatus", { required: "Marital status is required" })}>
                  <MenuItem value="SINGLE">Single</MenuItem>
                  <MenuItem value="MARRIED">Married</MenuItem>
                  <MenuItem value="DIVORCED">Divorced</MenuItem>
                  <MenuItem value="WIDOWED">Widowed</MenuItem>
                </Select>
              </FormControl>
              <TextField fullWidth label="Spouse Name"  {...register("spouseName")}/>
              <TextField fullWidth label="No of Children"   {...register("noOfChildren", { valueAsNumber: true })} type="number" onKeyDown={(evt) => ["e", "E", "+", "-"].includes(evt.key) && evt.preventDefault()}/>
            </Row>
          </Section>

          <Section>
            <Title variant="h6">Election Details</Title>
            <DividerStyle />
            <Row>
              <FormControl fullWidth>
                <InputLabel>Election Name</InputLabel>
                <Select fullWidth label="Choose Election" {...register("electionId", { required: "Election is required" })}>
                  <MenuItem value="1">Presidential Election 2024</MenuItem>
                  <MenuItem value="State Senate Election">State Senate Election</MenuItem>
                  <MenuItem value="Mayoral Election">Mayoral Election</MenuItem>
                  <MenuItem value="Congressional Election">Congressional Election</MenuItem>
                  <MenuItem value="Senate Election">Senate Election</MenuItem>
                </Select>
              </FormControl>
              
              <FormControl fullWidth>
                <InputLabel>Choose Party</InputLabel>
                <Select fullWidth label="Choose Party" {...register("partyId", { required: "Party is required" })}>
                  <MenuItem value="1">Republican Party</MenuItem>
                  <MenuItem value="Democratic">Democratic Party</MenuItem>
                </Select>
              </FormControl>
              <TextField fullWidth label="State" value={"New York"} {...register("stateName", { required: "State name is required" })}/>
            </Row>
          </Section>

          <Section>
            <Title variant="h6">Residential Address</Title>
            <DividerStyle />
            <Row>
              <TextField fullWidth label="Street"  {...register("residentialAddress.street", { required: "Street is required" })}/>
              <TextField fullWidth label="City"  {...register("residentialAddress.city", { required: "City is required" })}/>
              <TextField fullWidth label="Zipcode" {...register("residentialAddress.zipcode", { valueAsNumber: true })}/>
            </Row>
          </Section>

          <Section>
            <Title variant="h6">Mailing Address</Title>
            <DividerStyle />
            <Row>
              <TextField fullWidth label="Street" {...register("mailingAddress.street", { required: "Street is required" })}/>
              <TextField fullWidth label="City" {...register("mailingAddress.city", { required: "City is required" })}/>
              <TextField fullWidth label="Zipcode" {...register("mailingAddress.zipcode", { valueAsNumber: true })}/>
            </Row>
          </Section>

          <Section>
            <Title variant="h6">Bank Details</Title>
            <DividerStyle />
            <Row>
              <TextField fullWidth label="Bank Name"  {...register("bankDetails.bankName", { required: "Bank name is required" })}/>
              <TextField fullWidth label="Bank Address"  {...register("bankDetails.bankAddress", { required: "Bank address is required" })}/>
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
            <Button variant="contained" type="submit">Submit</Button>
            <Button variant="contained">Cancel</Button>
          </FlexCenter>
      </Form>
  </>
  );
};

export default CandidateForm;
