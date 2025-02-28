import React, { useCallback, useEffect, useState } from "react";
import {  toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
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
  Checkbox,
} from "@mui/material";
import { useDropzone } from "react-dropzone";
import { DropzoneContainer, Title } from "../../style/CandidateFormCss";
import { Section } from "../../style/CandidateFormCss";
import { Row } from "../../style/CandidateFormCss";
import { FlexCenter } from "../../style/CandidateFormCss";
import { DividerStyle } from "../../style/CandidateFormCss";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { Controller, SubmitHandler, useForm } from "react-hook-form";
import {
  addCandidate,
  fetchCandidates,
  updateCandidateData,
} from "../../store/feature/candidate/candidateAPI";
import { Form } from "../../style/CommanStyle";
import { IFormInput } from "../../store/feature/candidate/types";
import { clearSearchQuery } from "../../store/feature/candidate/candidateSlice";
import { AppDispatch } from "../../store/app/store";
import { RootState } from '../../store/app/store';
import axios from "axios";
import { fetchAllElection } from "../../store/feature/election/electionApi";
 
interface CandidateFormProps {
  handleClose: () => void;
  selectedCandidate: any;
}
 
const CandidateForm: React.FC<CandidateFormProps> = ({handleClose}) => {
  const dispatch = useDispatch<AppDispatch>();
  const [editId, seteditId] = useState<string| null>('')
  const [profilePic, setProfilePic] = useState<string | null>('');
  const [signature, setSignature] = useState<string | null>('');
  const {candidate} = useSelector((state:RootState)=>state.candidate)
 
  const searchQuery = useSelector((state: RootState) => state.candidate.searchQuery);
  const onDropProfile = useCallback((acceptedFiles: File[]) => {
    const file = acceptedFiles[0];
    if (!file) {
      toast.error("Invalid profile picture file.");
      return;
    }
 
    const reader = new FileReader();
    reader.onload = () => {
      if (typeof reader.result === "string") {
        setProfilePic(reader.result);
      } else {
        toast.error("Failed to load profile picture.");
      }
    };
    reader.readAsDataURL(file);
  }, []);
 
  const onDropSignature = useCallback((acceptedFiles: File[]) => {
    const file = acceptedFiles[0];
    if (!file) {
      toast.error("Invalid signature file.");
      return;
    }
 
    const reader = new FileReader();
    reader.onload = () => {
      if (typeof reader.result === "string") {
        setSignature(reader.result);
      } else {
        toast.error("Failed to load signature.");
      }
    };
    reader.readAsDataURL(file);
  }, []);
 
  const { getRootProps: getProfileProps, getInputProps: getProfileInputProps } =
    useDropzone({ onDrop: onDropProfile });
  const {
    getRootProps: getSignatureProps,
    getInputProps: getSignatureInputProps,
  } = useDropzone({ onDrop: onDropSignature });
 
  // const dispatch = useDispatch();
 
  const {
    register,
    handleSubmit,
    reset,
    control,
    watch,
    setValue,
    formState: { errors },
  } = useForm<IFormInput>();
  const maritalStatus = watch("maritalStatus", editId ? candidate?.candidate?.maritialStatus || "" : "");
 
  const isSingle = maritalStatus === "SINGLE";
  React.useEffect(() => {
    if (searchQuery) {
      setValue("candidateSSN", searchQuery); // Updates the react-hook-form field value
    }
  }, [searchQuery, setValue]);
  useEffect(() => {
    if (candidate?.candidate) {
      seteditId(candidate?.candidate.candidateId)
     setValue('candidateId',candidate?.candidate.candidateId || "")
     
      setProfilePic(candidate?.candidateImage );
      setSignature(candidate?.candidateSignature );
    }
  }, [candidate, setValue]);
  useEffect(() => {
    if (candidate && editId) {
    console.log(candidate.candidateImage)
     
      setProfilePic(candidate?.candidateImage );
      setSignature(candidate?.candidateSignature );
    }
  }, [candidate,editId,setValue])
 
  const onSubmit: SubmitHandler<IFormInput> = async (data) => {
    if (!profilePic) {
      toast.error("Profile picture is required!");
      return;
    }
 
    if (!signature) {
      toast.error("Signature is required!");
      return;
    }
 
    const formData = new FormData();
    formData.append(
      "candidate",
      new Blob(
        [
          JSON.stringify({
            candidateName: data.candidateName,
            residentialAddress: data.residentialAddress,
            mailingAddress: data.mailingAddress,
            bankDetails: data.bankDetails,
            candidateSSN: data.candidateSSN,
            dateOfBirth: data.dateOfBirth,
            gender: data.gender,
            maritialStatus: data.maritalStatus,
            noOfChildren: data.noOfChildren,
            spouseName: data.spouseName,
            partyId: data.partyId,
            stateName: data.stateName,
            candidateEmail: data.candidateEmail,
            electionId: data.electionId,
          }),
        ],
        { type: "application/json" }
      )
    );
 
    const profileFile = dataURLtoFile(profilePic, "profile.jpg");
    console.log(profileFile)
    formData.append("candidateImage", profileFile);
 
    const signatureFile = dataURLtoFile(signature, "signature.jpg");
    formData.append("candidateSignature", signatureFile);
 
    try {
      if (candidate && editId !== "") {
       
        if (editId) {
          await dispatch(updateCandidateData({ candidateId: editId, candidateData: formData }));
        }
        clearSearch();
        handleClose();
        reset();
        dispatch(fetchCandidates({ page: 0, perPage: 10 }));
      } else {
       
        await dispatch(addCandidate(formData));
        clearSearch();
        handleClose();
        reset();
        dispatch(fetchCandidates({ page: 0, perPage: 10 }));
       
      }
    } catch (error) {
      toast.error(candidate ? "Failed to update candidate." : "Failed to add candidate.");
    }
  };
 
  const clearSearch = () => {
      dispatch(clearSearchQuery());
    };
    const dataURLtoFile = (dataURL: string, filename: string): File => {
      if (!dataURL || !dataURL.includes(',')) {
        throw new Error("Invalid data URL format");
      }
   
      const [mimePart, base64Data] = dataURL.split(",");
      const mimeMatch = mimePart.match(/:(.*?);/);
   
      if (!mimeMatch || !mimeMatch[1]) {
        throw new Error("Invalid data URL format");
      }
   
      const mime = mimeMatch[1];
      const byteString = atob(base64Data);
      const arrayBuffer = new Uint8Array(byteString.length);
      for (let i = 0; i < byteString.length; i++) {
        arrayBuffer[i] = byteString.charCodeAt(i);
      }
      return new File([arrayBuffer], filename, { type: mime });
    };
    useEffect(() => {
      if (editId && candidate) {
        reset({
          candidateName: {
            firstName: candidate?.candidate?.candidateName?.firstName ,
            middleName: candidate?.candidate?.candidateName?.middleName,
            lastName:candidate?.candidate?.candidateName?.lastName
          },
          gender:candidate?.candidate?.gender,
          dateOfBirth: candidate?.candidate?.dateOfBirth,
          partyId:candidate?.candidate?.partyName,
          candidateEmail: candidate?.candidate?.candidateEmail,
    candidateSSN: candidate?.candidate?.candidateSSN,
          maritalStatus:candidate?.candidate?.maritialStatus,
          noOfChildren: candidate?.candidate?.noOfChildren ,
      spouseName: candidate?.candidate?.spouseName ,
         
      stateName:candidate?.candidate?.stateName,
      residentialAddress:{
street: candidate?.candidate?.residentialAddress?.street,
city:candidate?.candidate?.residentialAddress?.city,
zipcode:candidate?.candidate?.residentialAddress?.zipcode
      },
      mailingAddress:{
        street: candidate?.candidate?.mailingAddress?.street,
city:candidate?.candidate?.mailingAddress?.city,
zipcode:candidate?.candidate?.mailingAddress?.zipcode
      },
      bankDetails:{
        bankName:candidate?.candidate?.bankDetails?.bankName,
        bankAddress: candidate?.candidate?.bankDetails?.bankAddress
      },
     
 
        });
        if (candidate?.candidateImage) {
         
          if (candidate?.candidateImage) {
            setProfilePic(`data:image/png;base64,${candidate?.candidateImage}`);
          } else {
            console.warn("Invalid profile image URL.");
          }
        } else {
          setProfilePic(null); // Clear profile picture if not provided
        }
   
        // Only set signature if signature exists
        if (candidate?.candidateSignature) {
         
         
         
          if (candidate.candidateSignature) {
            setSignature(`data:image/png;base64,${candidate.candidateSignature}`);
          } else {
            console.warn("Invalid signature URL.");
          }
        } else {
          setSignature(null); // Clear signature if not provided
        }
     
      }
    }, [editId, candidate, reset,setProfilePic, setSignature]);
    console.log("hiiii"+profilePic)
    const election = useSelector((state: any) => state.election.election || []);
    const elections = election?.data || [];
    useEffect(() => {
      dispatch(fetchAllElection());
    }, [dispatch]);
  
    interface Party {
      partyId: number;
      partyName: string;
    }
   
    const [parties, setParties] = useState<Party[]>([]);
    const sameMailingAddress = watch("sameMailingAddress");
   
  // Watch individual fields inside residentialAddress
  const residentialStreet = watch("residentialAddress.street");
  const residentialCity = watch("residentialAddress.city");
  const residentialZipcode = watch("residentialAddress.zipcode");
  console.log("Checkbox state:", sameMailingAddress);
  console.log("Residential Address:", {
    street: residentialStreet,
    city: residentialCity,
    zipcode: residentialZipcode,
  });
   
  useEffect(() => {
    console.log("Checkbox state:", sameMailingAddress);
    console.log("Residential Address:", {
      street: residentialStreet,
      city: residentialCity,
      zipcode: residentialZipcode,
    });
   
    if (sameMailingAddress) {
      setValue("mailingAddress.street", residentialStreet || "");
      setValue("mailingAddress.city", residentialCity || "");
      setValue("mailingAddress.zipcode", residentialZipcode);
    }
  }, [sameMailingAddress, residentialStreet, residentialCity, residentialZipcode, setValue]);
   
    useEffect(() => {
      // Fetch party data from API
      const fetchParties = async () => {
        try {
          const response = await axios.get("http://localhost:8082/api/party");
          setParties(response.data); // Assuming response.data is an array of party objects
        } catch (error) {
          console.error("Error fetching parties:", error);
        }
      };
   
      fetchParties();
    }, []);
  return (
    <>
     
      <Form onSubmit={handleSubmit(onSubmit)}>
       
        <Title variant="h5" align="center" gutterBottom>
        {candidate ? "EDIT CANDIDATE" : "ADD CANDIDATE"}
        </Title>
 
        <Section>
          <Title variant="h6">Personal Information</Title>
          <DividerStyle />
          <Row>
            <TextField
              fullWidth
              label="First Name"
              defaultValue={editId ? candidate?.candidate?.candidateName?.firstName : ""}
              {...register("candidateName.firstName", {
                required: "First name is required",
              })}
              InputLabelProps={{ shrink: true }}
              error={!!errors.candidateName?.firstName}
              helperText={errors.candidateName?.firstName?.message}
            />
            <TextField
              fullWidth
              label="Middle Name"
              defaultValue={editId ? candidate?.candidate?.candidateName?.middleName : ""}
              {...register("candidateName.middleName", {
                required: "Middle name is required",
              })}
              InputLabelProps={{ shrink: true }}
              error={!!errors.candidateName?.middleName}
              helperText={errors.candidateName?.middleName?.message}
            />
            <TextField
              fullWidth
              label="Last Name"
              defaultValue={editId ? candidate?.candidate?.candidateName?.lastName : ""}
              {...register("candidateName.lastName", {
                required: "Last name is required",
              })}
              error={!!errors.candidateName?.lastName}
              InputLabelProps={{ shrink: true }}
              helperText={errors.candidateName?.lastName?.message}
            />
          </Row>
          <Row>
            <TextField
              fullWidth
              type="date"
              label="Date of Birth"
              defaultValue={editId ? candidate?.candidate?.dateOfBirth : ""}
 
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
              defaultValue={editId ? candidate?.candidate?.candidateEmail : ""}
              InputLabelProps={{ shrink: true }}
              {...register("candidateEmail", { required: "Email is required" })}
              error={!!errors.candidateEmail}
              helperText={errors.candidateEmail?.message}
            />
             <TextField
        fullWidth
        label="SSN"
        value={editId ? candidate?.candidate?.candidateSSN :searchQuery}
        // type="text"
        // defaultValue={searchQuery} // Pre-fills the SSN with the searched number
        {...register("candidateSSN")}
        error={!!errors.candidateSSN}
        helperText={errors.candidateSSN?.message}
        InputLabelProps={{ shrink: true }}
        InputProps={{
          readOnly: true, // Make it read-only as it should not be editable
        }}
      />
          </Row>
          <Row>
            <FormControl component="fieldset" error={!!errors.gender}>
              <FormLabel component="legend">Gender</FormLabel>
              <Controller
                name="gender"
                control={control}
                rules={{ required: "Please select a gender." }}
                defaultValue={editId ? candidate?.candidate?.gender : ""}
 
                render={({ field }) => (
                  <RadioGroup {...field} row>
                    <FormControlLabel
                      value="MALE"
                      control={<Radio />}
                      label="Male"
                    />
                    <FormControlLabel
                      value="FEMALE"
                      control={<Radio />}
                      label="Female"
                    />
                  </RadioGroup>
                )}
              />
              {errors.gender && (
                <FormHelperText>{errors.gender.message}</FormHelperText>
              )}
            </FormControl>
          </Row>
          <Row>
          <FormControl fullWidth error={!!errors.maritalStatus}>
        <InputLabel id="marital-status-label">Marital Status</InputLabel>
        <Controller
          name="maritalStatus"
          control={control}
          rules={{ required: "Marital status is required" }}
          defaultValue={editId ? candidate?.candidate?.maritialStatus || "" : ""}
          render={({ field }) => (
            <Select {...field} labelId="marital-status-label" label="Marital Status">
              <MenuItem value="SINGLE">Single</MenuItem>
              <MenuItem value="MARRIED">Married</MenuItem>
              <MenuItem value="DIVORCED">Divorced</MenuItem>
              <MenuItem value="WIDOWED">Widowed</MenuItem>
            </Select>
          )}
        />
        {!!errors.maritalStatus && (
          <FormHelperText>{errors.maritalStatus.message}</FormHelperText>
        )}
      </FormControl>
 
      <TextField
        fullWidth
        label="Spouse Name"
        {...register("spouseName")}
        InputLabelProps={{ shrink: true }}
        defaultValue={editId ? candidate?.candidate?.spouseName || "" : ""}
        disabled={isSingle}
      />
      <TextField
        fullWidth
        label="No of Children"
        {...register("noOfChildren", { valueAsNumber: true })}
        defaultValue={editId ? candidate?.candidate?.noOfChildren || "" : ""}
        type="number"
        InputLabelProps={{ shrink: true }}
        disabled={isSingle}
        onKeyDown={(evt) =>
          ["e", "E", "+", "-"].includes(evt.key) && evt.preventDefault()
        }
      />
          </Row>
          {errors.maritalStatus && (
            <FormHelperText>{errors.maritalStatus.message}</FormHelperText>
          )}
        </Section>
 
        <Section>
          <Title variant="h6">Election Details</Title>
          <DividerStyle />
          <Row>
            <FormControl fullWidth error={!!errors.electionId}>
              <InputLabel id="election-type-label">Election Type</InputLabel>
              <Controller
                name="electionId"
                control={control}
                render={({ field }) => (
                  <Select
                    {...field}
                    labelId="election-type-label"
                    value={field.value ?? ""}
                    onChange={(event) => field.onChange(event.target.value)}
                  >
                    {Array.isArray(elections) && elections.map((election: any) => (
                      <MenuItem key={election.electionId} value={election.electionId}>
                        {election.electionName}
                      </MenuItem>
                    ))}
                  </Select>
                )}
              />
              {errors.electionId && <FormHelperText>{errors.electionId.message}</FormHelperText>}
            </FormControl>
            <FormControl fullWidth error={!!errors.partyId}>
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
                      value={field.value ?? ""} // Ensures the value is controlled
                      onChange={(event) => field.onChange(event.target.value)}
                    >
                      {parties.length === 0 ? (
                        <MenuItem disabled>No parties found</MenuItem>
                      ) : (
                       
                        parties.map((party) => (
                          <MenuItem key={party.partyId} value={party.partyId}>
                            {party.partyName}
                          </MenuItem>
                        ))
                      )}
                    </Select>
                    )}
                  />
                {errors.partyId && <FormHelperText>{errors.partyId.message}</FormHelperText>}
              </FormControl>
 
 
 
 
<TextField
  fullWidth
  label="State"
  value="New York" // Always display New York
  defaultValue="New York" // Default value for the form
  {...register("stateName", {
    required: "State name is required",
    validate: (value) => value === "New York" || "State name must be New York", // Validate the static value
  })}
  error={!!errors.stateName}
  helperText={errors.stateName?.message}
  InputLabelProps={{ shrink: true }}
  disabled // Disable input to keep it static
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
              defaultValue={editId ? candidate?.candidate?.residentialAddress.street : ""}
              InputLabelProps={{ shrink: true }}
              error={!!errors.residentialAddress?.street}
              helperText={errors.residentialAddress?.street?.message}
            />
            <TextField
              fullWidth
              label="City"
              {...register("residentialAddress.city", {
                required: "City is required",
              })}
              defaultValue={editId ? candidate?.candidate?.residentialAddress.city : ""}
              InputLabelProps={{ shrink: true }}
              error={!!errors.residentialAddress?.city}
              helperText={errors.residentialAddress?.city?.message}
            />
            <TextField
              fullWidth
              label="Zipcode"
              {...register("residentialAddress.zipcode", {
                required: "Zipcode is required",
                validate: (value: unknown) =>
                  /^\d{5}$/.test(value as string) || "Zipcode must be 5 digits",
              })}
              defaultValue={editId ? candidate?.candidate?.residentialAddress.zipcode : ""}
              InputLabelProps={{ shrink: true }}
              error={!!errors.residentialAddress?.zipcode}
              helperText={errors.residentialAddress?.zipcode?.message}
            />
          </Row>
        </Section>
        <Section>
          <Typography>
          <FormControlLabel control={<Checkbox {...register("sameMailingAddress")} />} label="Same as Residential Address" />
 
          </Typography>
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
              defaultValue={editId ? candidate?.candidate?.mailingAddress.street : ""}
              InputLabelProps={{ shrink: true }}
              error={!!errors.mailingAddress?.street}
              helperText={errors.mailingAddress?.street?.message}
            />
 
            <TextField
              fullWidth
              label="City"
              {...register("mailingAddress.city", {
                required: "City is required",
              })}
              defaultValue={editId ? candidate?.candidate?.mailingAddress.city : ""}
              InputLabelProps={{ shrink: true }}
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
              defaultValue={editId ? candidate?.candidate?.mailingAddress.zipcode : ""}
              InputLabelProps={{ shrink: true }}
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
              defaultValue={editId ? candidate?.candidate?.bankDetails?.bankName : ""}
              InputLabelProps={{ shrink: true }}
              error={!!errors.bankDetails?.bankName}
              helperText={errors.bankDetails?.bankName?.message}
            />
 
            <TextField
              fullWidth
              label="Bank Address"
              {...register("bankDetails.bankAddress", {
                required: "Bank address is required",
              })}
              defaultValue={editId ? candidate?.candidate?.bankDetails.bankAddress : ""}
              InputLabelProps={{ shrink: true }}
              error={!!errors.bankDetails?.bankAddress}
              helperText={errors.bankDetails?.bankAddress?.message}
            />
          </Row>
        </Section>
 
       {editId ? <Section>
          <Title variant="h6">Upload Documents</Title>
          <DividerStyle />
          <FlexCenter>
            <Box>
              <Typography variant="subtitle1">Candidate Image</Typography>
              <DropzoneContainer {...getProfileProps()}>
                <input {...getProfileInputProps()} />
               {profilePic ? (
                  <img
                  src={typeof profilePic === "string" ? profilePic : URL.createObjectURL(profilePic)}
                    alt="Profile"
                    width="100"
                    height="100"
                    style={{ borderRadius: "50%" }}
                  />
                 )
                 : (
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
                  src={typeof signature === "string" ? signature : URL.createObjectURL(signature)}
                    alt="Signature"
                    width="200"
                    height="50"
                  />
                ) : (
                  <Typography>Drag & Drop or Click</Typography>
                )}
              </DropzoneContainer>
            </Box>
          </FlexCenter>
        </Section> :  <Section>
          <Title variant="h6">Upload Documents</Title>
          <DividerStyle />
          <FlexCenter>
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
          </FlexCenter>
        </Section> }
 
        <FlexCenter>
          <Button variant="contained" type="submit">
          {candidate ? "Update" : "Submit"}
          </Button>
          <Button variant="contained" onClick={handleClose}>Cancel</Button>
        </FlexCenter>
      </Form>
    </>
  );
};
 
export default CandidateForm;
 