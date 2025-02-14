import React, { useCallback, useState } from "react";
import {
  Button,
  Typography,
  RadioGroup,
  FormControlLabel,
  Radio,
  Select,
  MenuItem,
  Box,
  TextField,
  FormLabel,
  FormControl,
  InputLabel,
  FormHelperText,
} from "@mui/material";
import { useDropzone } from "react-dropzone";
import { DropzoneContainer, Title } from "../../style/CandidateFormCss";
import { Section } from "../../style/CandidateFormCss";
import { Row } from "../../style/CandidateFormCss";
import { DocumentContainer } from "../../style/CandidateFormCss";
import { FlexCenter } from "../../style/CandidateFormCss";
import { DividerStyle } from "../../style/CandidateFormCss";
import CloseIcon from "@mui/icons-material/Close";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { Controller, SubmitHandler, useForm } from "react-hook-form";
import {
  addCandidate,
  fetchCandidates,
} from "../../store/feature/candidate/candidateAPI";
import { Form } from "../../style/CommanStyle";
import { IFormInput } from "../../store/feature/candidate/types";

const CandidateForm: React.FC = () => {
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

  const { getRootProps: getProfileProps, getInputProps: getProfileInputProps } =
    useDropzone({ onDrop: onDropProfile });
  const {
    getRootProps: getSignatureProps,
    getInputProps: getSignatureInputProps,
  } = useDropzone({ onDrop: onDropSignature });

  const dispatch = useDispatch();
  const { loading, success, error } = useSelector((state) => state.candidate);

  const {
    register,
    handleSubmit,
    reset,
    control,
    formState: { errors },
  } = useForm<IFormInput>();

  const onSubmit: SubmitHandler<IFormInput> = async (data) => {
    if (!data.candidateSSN) {
      data.candidateSSN = Math.floor(
        100000000 + Math.random() * 900000000
      ).toString(); // 9-digit random number
    }
    const formData = new FormData();
    formData.append(
      "candidate",
      new Blob([JSON.stringify({
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
      })],{type:"application/json"})
    );
    // formData.append("candidateImage", data.candidateImage);
    // formData.append("candidateSignature", data.candidateSignature);

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
        <Box sx={{ display: "flex", justifyContent: "flex-end" }}>
          <CloseIcon />
        </Box>
        <Title variant="h5" align="center" gutterBottom>
          CANDIDATE REGISTRATION
        </Title>

        <Section>
          <Title variant="h6">Personal Information</Title>
          <DividerStyle />
          <Row>
            <TextField
              fullWidth
              label="First Name"
              {...register("candidateName.firstName", {
                required: "First name is required",
              })}
              error={!!errors.candidateName?.firstName}
              helperText={errors.candidateName?.firstName?.message}
            />
            <TextField
              fullWidth
              label="Middle Name"
              {...register("candidateName.middleName", {
                required: "Middle name is required",
              })}
              error={!!errors.candidateName?.middleName}
              helperText={errors.candidateName?.middleName?.message}
            />
            <TextField
              fullWidth
              label="Last Name"
              {...register("candidateName.lastName", {
                required: "Last name is required",
              })}
              error={!!errors.candidateName?.lastName}
              helperText={errors.candidateName?.lastName?.message}
            />
          </Row>
          <Row>
            <TextField
              fullWidth
              type="date"
              label="Date of Birth"
              {...register("dateOfBirth", {
                required: "Date of Birth is required",
                validate: (value) => {
                  const today = new Date();
                  const birthDate = new Date(value);
                  const age = today.getFullYear() - birthDate.getFullYear();
                  const m = today.getMonth() - birthDate.getMonth();
                  return (
                    (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())
                      ? age - 1
                      : age) >= 18 || "Candidate must be at least 18 years old"
                  );
                },
              })}
              InputLabelProps={{ shrink: true }}
              error={!!errors.dateOfBirth}
              helperText={errors.dateOfBirth?.message}
            />
            <TextField
              fullWidth
              label="Email"
              type="email"
              {...register("candidateEmail", { required: "Email is required" })}
              error={!!errors.candidateEmail}
              helperText={errors.candidateEmail?.message}
            />
          </Row>
          <Row>
          <FormControl component="fieldset" error={!!errors.gender}>
        <FormLabel component="legend">Gender</FormLabel>
        <Controller
          name="gender"
          control={control}
          rules={{ required: "Please select a gender." }}
          render={({ field }) => (
            <RadioGroup {...field} row>
              <FormControlLabel value="MALE" control={<Radio />} label="Male" />
              <FormControlLabel value="FEMALE" control={<Radio />} label="Female" />
            </RadioGroup>
          )}
        />
        {errors.gender && <FormHelperText>{errors.gender.message}</FormHelperText>}
      </FormControl>
          </Row>
          <Row>
          <FormControl fullWidth error={!!errors.maritialStatus} >
        <InputLabel id="marital-status-label">Marital Status</InputLabel>
        <Controller
          name="maritialStatus"
          control={control}
          rules={{ required: "Marital status is required" }}
          render={({ field }) => (
            <Select
              {...field}
              labelId="marital-status-label"
              label="Marital Status"
            >
              <MenuItem value="SINGLE">Single</MenuItem>
              <MenuItem value="MARRIED">Married</MenuItem>
              <MenuItem value="DIVORCED">Divorced</MenuItem>
              <MenuItem value="WIDOWED">Widowed</MenuItem>
            </Select>
          )}
        />
        
      </FormControl>

            <TextField
              fullWidth
              label="Spouse Name"
              {...register("spouseName")}
            />
            <TextField
              fullWidth
              label="No of Children"
              {...register("noOfChildren", { valueAsNumber: true })}
              type="number"
              onKeyDown={(evt) =>
                ["e", "E", "+", "-"].includes(evt.key) && evt.preventDefault()
              }
            />
          </Row>
          {errors.maritialStatus && (
          <FormHelperText>{errors.maritialStatus.message}</FormHelperText>
        )}
        </Section>

        <Section>
          <Title variant="h6">Election Details</Title>
          <DividerStyle />
          <Row>
          <FormControl fullWidth error={!!errors.electionId} >
        <InputLabel id="election-type-label">Election Type</InputLabel>
        <Controller
          name="electionId"
          control={control}
          rules={{ required: "Election type is required" }}
          render={({ field }) => (
            <Select
              {...field}
              labelId="election-type-label"
              label="Election Type"
            >
              <MenuItem value="1">General</MenuItem>
              <MenuItem value="2">Primary</MenuItem>
              <MenuItem value="3">Runoff</MenuItem>
              <MenuItem value="4">Special</MenuItem>
            </Select>
          )}
        />
        {errors.electionId && (
          <FormHelperText>{errors.electionId.message}</FormHelperText>
        )}
      </FormControl>


      <FormControl fullWidth error={!!errors.partyId} >
        <InputLabel id="party-label">Party</InputLabel>
        <Controller
          name="partyId"
          control={control}
          rules={{ required: "Party selection is required" }}
          render={({ field }) => (
            <Select
              {...field}
              labelId="party-label"
              label="Party"
            >
              <MenuItem value="1">Democratic</MenuItem>
              <MenuItem value="2">Republican</MenuItem>
              <MenuItem value="3">Independent</MenuItem>
              <MenuItem value="4">Green</MenuItem>
            </Select>
          )}
        />
        {errors.partyId && (
          <FormHelperText>{errors.partyId.message}</FormHelperText>
        )}
      </FormControl>
            <TextField
              fullWidth
              label="State"
              value={"New York"}
              {...register("stateName", { required: "State name is required" })}
              error={!!errors.stateName}
              helperText={errors.stateName?.message}
            />
          </Row>
        </Section>

        <Section>
          <Title variant="h6">Residential Address</Title>
          <DividerStyle />
          <Row>
            <TextField
              fullWidth
              label="Street"
              {...register("residentialAddress.street", {
                required: "Street is required",
              })}
              error={!!errors.residentialAddress?.street}
              helperText={errors.residentialAddress?.street?.message}
            />
            <TextField
              fullWidth
              label="City"
              {...register("residentialAddress.city", {
                required: "City is required",
              })}
              error={!!errors.residentialAddress?.city}
              helperText={errors.residentialAddress?.city?.message}
            />
            <TextField
              fullWidth
              label="Zipcode"
              {...register("residentialAddress.zipcode", {
                required: "Zipcode is required",
                validate: (value: unknown) =>
                  /^\d{5}$/.test(value) || "Zipcode must be 5 digits",
              })}
              error={!!errors.residentialAddress?.zipcode}
              helperText={errors.residentialAddress?.zipcode?.message}
            />
          </Row>
        </Section>

        <Section>
          <Title variant="h6">Mailing Address</Title>
          <DividerStyle />
          <Row>
            <TextField
              fullWidth
              label="Street"
              {...register("mailingAddress.street", {
                required: "Street is required",
              })}
              error={!!errors.mailingAddress?.street}
              helperText={errors.mailingAddress?.street?.message}
            />
           
            <TextField
              fullWidth
              label="City"
              {...register("mailingAddress.city", {
                required: "City is required",
              })}
              error={!!errors.mailingAddress?.city}
              helperText={errors.mailingAddress?.city?.message}
            />
           
            <TextField
              fullWidth
              label="Zipcode"
              {...register("mailingAddress.zipcode", {
                required: "Zipcode is required",
                valueAsNumber: true,
                validate: (value) =>
                  /^\d{5}$/.test(value?.toString() || "") ||
                  "Zipcode must be 5 digits",
              })}
              error={!!errors.mailingAddress?.city}
              helperText={errors.mailingAddress?.city?.message}
            />
          
          </Row>
        </Section>

        <Section>
          <Title variant="h6">Bank Details</Title>
          <DividerStyle />
          <Row>
            <TextField
              fullWidth
              label="Bank Name"
              {...register("bankDetails.bankName", {
                required: "Bank name is required",
              })}
              error={!!errors.bankDetails?.bankName}
              helperText={errors.bankDetails?.bankName?.message}
            />
           
            <TextField
              fullWidth
              label="Bank Address"
              {...register("bankDetails.bankAddress", {
                required: "Bank address is required",
              })}
               error={!!errors.bankDetails?.bankAddress}
              helperText={errors.bankDetails?.bankAddress?.message}
            />
           
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
                {profilePic ? (
                  <img
                    src={profilePic}
                    alt="Profile"
                    width="100"
                    height="100"
                    style={{ borderRadius: "50%" }}
                  />
                ) : (
                  <Typography>Drag & Drop or Click</Typography>
                )}
              </DropzoneContainer>
            </Box>
            <Box>
              <Typography variant="subtitle1">Signature</Typography>
              <DropzoneContainer {...getSignatureProps()}>
                <input {...getSignatureInputProps()} />
                {signature ? (
                  <img
                    src={signature}
                    alt="Signature"
                    width="200"
                    height="50"
                  />
                ) : (
                  <Typography>Drag & Drop or Click</Typography>
                )}
              </DropzoneContainer>
            </Box>
          </DocumentContainer>
        </Section>

        <FlexCenter>
          <Button variant="contained" type="submit">
            Submit
          </Button>
          <Button variant="contained">Cancel</Button>
        </FlexCenter>
      </Form>
    </>
  );
};

export default CandidateForm;
