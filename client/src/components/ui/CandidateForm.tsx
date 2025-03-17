import React, { useCallback, useEffect, useState } from "react";
import { toast } from "react-toastify";
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
  IconButton,
} from "@mui/material";
import { useDropzone } from "react-dropzone";
import { DropzoneContainer, Title } from "../../style/CandidateFormCss";
import { Section } from "../../style/CandidateFormCss";
import { Row } from "../../style/CandidateFormCss";
import { FlexCenter,DividerStyle } from "../../style/CandidateFormCss";
import { useSelector,useDispatch } from "react-redux";
import { Controller, SubmitHandler, useForm } from "react-hook-form";
import {
  addCandidate,
  fetchCandidates,
  updateCandidateData,
} from "../../store/feature/candidate/candidateAPI";
import { Form } from "../../style/CommanStyle";
import { IFormInput, ModalData } from "../../store/feature/candidate/types";
import {
  clearCandidate,
  clearSearchQuery,
  resetState,
} from "../../store/feature/candidate/candidateSlice";
import { AppDispatch } from "../../store/app/store";
import { RootState } from "../../store/app/store";
import { fetchAllElection } from "../../store/feature/election/electionApi";
import axiosInstance from "../../store/app/axiosInstance";
import CloseIcon from "@mui/icons-material/Close";
import UpdateDialog from "./UpdateDialog";
interface CandidateFormProps {
  handleClose: () => void;
  selectedCandidate: any;
}
const CandidateForm: React.FC<CandidateFormProps> = ({ handleClose }) => {
  const dispatch = useDispatch<AppDispatch>();
  const [editId, seteditId] = useState<string | null>("");
  const [profilePic, setProfilePic] = useState<string | null>("");
  const [signature, setSignature] = useState<string | null>("");
  const { candidate } = useSelector((state: RootState) => state.candidate);
  const [loading, setLoading] = useState(false);
  const [openUpdateDialog, setOpenUpdateDialog] = useState(false);
  const [originalData, setOriginalData] = useState<IFormInput | null>(null);
  const [updatedData, setUpdatedData] = useState<IFormInput | null>(null);
  const searchQuery = useSelector(
    (state: RootState) => state.candidate.searchQuery
  );
  const defaultValues = {
    candidateName: {
      firstName: null,
      middleName: null,
      lastName: null,
    },
    gender: "",
    dateOfBirth: "",
    partyId: "",
    candidateEmail: "",
    candidateSSN: searchQuery,
    maritalStatus: "",
    noOfChildren: null,
    spouseName: "",
    stateName: "New York",
    residentialAddress: {
      street: "",
      city: "",
      zipcode: "",
    },
    mailingAddress: {
      street: "",
      city: "",
      zipcode: "",
    },
    bankDetails: {
      bankName: "",
      bankAddress: "",
    },
  };
  const onDropProfile = useCallback((acceptedFiles: File[]) => {
    const file = acceptedFiles[0];
    if (!file) {
      toast.error("Invalid profile picture file.");
      return;
    }
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => {
      if (typeof reader.result === "string") {
        const base64Data = reader.result.split(",")[1];
        if (base64Data) {
          setProfilePic(base64Data); 
        } else {
          toast.error("Invalid profile picture format.");
        }
      } else {
        toast.error("Failed to load profile picture.");
      }
    };
    console.log(profilePic);
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
        const base64Data = reader.result.split(",")[1];
        if (base64Data) {
          setSignature(base64Data); 
        } else {
          toast.error("Invalid signature format.");
        }
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
  const {
    register,
    handleSubmit,
    reset,
    control,
    watch,
    setValue,
    formState: { errors },
  } = useForm<IFormInput>({});
  const maritalStatus = watch(
    "maritalStatus",
    editId ? candidate?.candidate?.maritialStatus || "" : ""
  );
  const isSingle = maritalStatus === "SINGLE";
  useEffect(() => {
    if (searchQuery) {
      setValue("candidateSSN", searchQuery); 
    }
  }, [searchQuery, setValue]);
  useEffect(() => {
    if (candidate) {
      seteditId(candidate?.candidateId);
      setValue("candidateId", candidate?.candidateId || "");
    }
  }, [editId, candidate, setValue]);
  const onSubmit: SubmitHandler<IFormInput> = async (data) => {
    if (!profilePic) {
      toast.error("Profile picture is required!");
      return;
    }
    if (!signature) {
      toast.error("Signature is required!");
      return;
    }
    const formData = {
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
      candidateImage: profilePic,
      candidateSignature: signature,
    };
    try {
      setLoading(true); 
      if (editId && candidate) {
        const updatedFields = Object.keys(formData).reduce((acc, key) => {
          if (JSON.stringify(candidate[key]) !== JSON.stringify(formData[key])) {
            acc[key] = formData[key];
          }
          return acc;
        }, {});
        if (Object.keys(updatedFields).length > 0) {
          setUpdatedData(updatedFields); 
          setOpenUpdateDialog(true); 
        } else {
          toast.info("No changes detected. Please make some changes before updating.");
        }
      } else {
        await dispatch(addCandidate(formData)).unwrap();
    dispatch(fetchCandidates({ page: 0, perPage: 5 }));
        toast.success("Candidate added successfully.");
        toast.success("Registration Mail Sent successfully!");
        handleFormReset();
      }
    } catch (error) {
      toast.error(candidate ? "Failed to update candidate." : "Failed to add candidate.");
    } finally {
      setLoading(false); 
    }
  };
const handleConfirmUpdate = async () => { 
    if (editId && updatedData) {
      try {
        setLoading(true); 
        toast.info("Updating candidate...");
        await dispatch(updateCandidateData({ candidateId: editId, candidateData: updatedData }));
    dispatch(fetchCandidates({ page: 0, perPage: 5 }));
        toast.success("Candidate updated successfully.");
        handleFormReset();
      } catch (error) {
        toast.error("Failed to update candidate.");
      } finally {
        setLoading(false);
        setOpenUpdateDialog(false); 
      }
    }
  };
  const handleFormReset = () => {
    reset(defaultValues);
    clearSearch();
    seteditId(null);
    setProfilePic("");
    setSignature("");
    dispatch(clearCandidate());
    handleClose();
    dispatch(fetchCandidates({ page: 0, perPage: 5 }));
  };
  const clearSearch = () => {
    dispatch(clearSearchQuery());
  };
  useEffect(() => {
    if (editId && candidate) {
      reset({
        candidateName: {
          firstName: candidate?.candidateName?.firstName || "",
          middleName: candidate?.candidateName?.middleName || "",
          lastName: candidate?.candidateName?.lastName || "",
        },
        gender: candidate?.gender || "",
        dateOfBirth: candidate?.dateOfBirth || "",
        partyId: candidate?.partyId || "",
        candidateEmail: candidate?.candidateEmail || "",
        candidateSSN: candidate?.candidateSSN,
        maritalStatus: candidate?.maritialStatus,
        noOfChildren: candidate?.noOfChildren,
        spouseName: candidate?.spouseName,
        electionId: candidate.electionId,
        stateName: candidate?.stateName,
        residentialAddress: {
          street: candidate?.residentialAddress?.street,
          city: candidate?.residentialAddress?.city,
          zipcode: candidate?.residentialAddress?.zipcode,
        },
        mailingAddress: {
          street: candidate?.mailingAddress?.street,
          city: candidate?.mailingAddress?.city,
          zipcode: candidate?.mailingAddress?.zipcode,
        },
        bankDetails: {
          bankName: candidate?.bankDetails?.bankName,
          bankAddress: candidate?.bankDetails?.bankAddress,
        },
      });
      if (candidate?.candidateImage) {
        if (candidate?.candidateImage) {
          setProfilePic(`${candidate?.candidateImage}`);
        } else {
          console.warn("Invalid profile image URL.");
        }
      } else {
        setProfilePic(null);
      }
      if (candidate?.candidateSignature) {
        if (candidate.candidateSignature) {
          setSignature(`${candidate.candidateSignature}`);
        } else {
          console.warn("Invalid signature URL.");
        }
      } else {
        setSignature(null);
      }
      setOriginalData({
        candidateName: {
          firstName: candidate?.candidateName?.firstName || "",
          middleName: candidate?.candidateName?.middleName || "",
          lastName: candidate?.candidateName?.lastName || "",
        },
        gender: candidate?.gender,
        dateOfBirth:candidate?.dateOfBirth,
        partyId: candidate?.partyName,
        candidateEmail: candidate?.candidateEmail,
        candidateSSN: candidate?.candidateSSN,
        maritalStatus: candidate?.maritialStatus,
        noOfChildren: candidate?.candidate?.noOfChildren,
        spouseName: candidate?.spouseName,
        stateName: candidate?.stateName,
       residentialAddress: {
          street: candidate?.residentialAddress?.street,
          city: candidate?.residentialAddress?.city,
          zipcode: candidate?.residentialAddress?.zipcode,
        },
        mailingAddress: {
          street: candidate?.mailingAddress?.street,
          city: candidate?.mailingAddress?.city,
          zipcode: candidate?.mailingAddress?.zipcode,
        },
        bankDetails: {
          bankName: candidate?.bankDetails?.bankName,
          bankAddress: candidate?.bankDetails?.bankAddress,
        },
        electionId: candidate?.electionId,
        candidateImage: candidate?.candidateImage,
        candidateSignature: candidate?.candidateSignature,
        sameMailingAddress: candidate?.sameMailingAddress,
      });
    }
  }, [editId, candidate, reset, setProfilePic, setSignature]);
  const { elections } = useSelector((state: any) => state.election || []);
  useEffect(() => {
    dispatch(fetchAllElection());
  }, [dispatch]);
  interface Party {
    partyId: number;
    partyName: string;
  }
  const [parties, setParties] = useState<Party[]>([]);
  const sameMailingAddress = watch("sameMailingAddress");
  const residentialStreet = watch("residentialAddress.street");
  const residentialCity = watch("residentialAddress.city");
  const residentialZipcode = watch("residentialAddress.zipcode");
  useEffect(() => {
    if (sameMailingAddress) {
      setValue("mailingAddress.street", residentialStreet || "");
      setValue("mailingAddress.city", residentialCity || "");
      setValue("mailingAddress.zipcode", residentialZipcode);
    }
  }, [
    sameMailingAddress,
    residentialStreet,
    residentialCity,
    residentialZipcode,
    setValue,
  ]);
  const fetchParties = async () => {
    try {
      const response = await axiosInstance.get("api/party");
      setParties(response.data);
    } catch (error) {
      console.error("Error fetching parties:", error);
    }
  };
  useEffect(() => {
    fetchParties();
  }, []);
  
  const handleCancel = () => {
    seteditId(null);
    setProfilePic("");
    setSignature(""); 
    dispatch(clearCandidate()); 
    dispatch(fetchCandidates({ page: 0, perPage: 5 }));
    reset(defaultValues); 
    handleClose();
  };
  return (
    <>
      <Form onSubmit={handleSubmit(onSubmit)}>
        <Box
          position="relative"
          display="flex"
          alignItems="center"
          justifyContent="center"
        >
          <Title variant="h5" gutterBottom mt="5px">
            {editId && candidate ? "EDIT CANDIDATE" : "ADD CANDIDATE"}
          </Title>
          <IconButton
            onClick={handleCancel}
            sx={{ position: "absolute", right: 0 }}
          >
            <CloseIcon />
          </IconButton>
        </Box>
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
              InputLabelProps={{ shrink: true }}
              error={!!errors.candidateName?.firstName}
              helperText={errors.candidateName?.firstName?.message}
            />
            <TextField
              fullWidth
              label="Middle Name"
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
              InputLabelProps={{ shrink: true }}
              {...register("candidateEmail", { required: "Email is required" })}
              error={!!errors.candidateEmail}
              helperText={errors.candidateEmail?.message}
            />
            <TextField
              fullWidth
              label="SSN"
              value={editId ? candidate?.candidate?.candidateSSN : searchQuery}
              {...register("candidateSSN")}
              error={!!errors.candidateSSN}
              helperText={errors.candidateSSN?.message}
              InputLabelProps={{ shrink: true }}
              InputProps={{
                readOnly: true, 
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
                defaultValue={editId ? candidate?.gender : ""}
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
                defaultValue={editId ? candidate?.maritialStatus || "" : ""}
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
              {!!errors.maritalStatus && (
                <FormHelperText>{errors.maritalStatus.message}</FormHelperText>
              )}
            </FormControl>
            <TextField
              fullWidth
              label="Spouse Name"
              {...register("spouseName")}
              InputLabelProps={{ shrink: true }}
              disabled={isSingle}
            />
            <TextField
              fullWidth
              label="No of Children"
              {...register("noOfChildren", { valueAsNumber: true })}
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
        {Array.isArray(elections.data) &&
          elections.data.map((election) => (
            <MenuItem
              key={election.electionId}
              value={election.electionId} 
            >
              {election.electionName} {/* Display electionName in the dropdown */}
            </MenuItem>
          ))}
      </Select>
    )}
  />
  {errors.electionId && (
    <FormHelperText>{errors.electionId.message}</FormHelperText>
  )}
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
                    value={field.value ?? ""} 
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
              {errors.partyId && (
                <FormHelperText>{errors.partyId.message}</FormHelperText>
              )}
            </FormControl>
            <TextField
              fullWidth
              label="State"
              value="New York" 
              {...register("stateName", {
                required: "State name is required",
                validate: (value) =>
                  value === "New York" || "State name must be New York", 
              })}
              error={!!errors.stateName}
              helperText={errors.stateName?.message}
              InputLabelProps={{ shrink: true }}
              disabled 
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
              InputLabelProps={{ shrink: true }}
              error={!!errors.residentialAddress?.zipcode}
              helperText={errors.residentialAddress?.zipcode?.message}
            />
          </Row>
        </Section>
        <Section>
          <Typography>
            <FormControlLabel
              control={<Checkbox {...register("sameMailingAddress")} />}
              label="Same as Residential Address"
            />
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
              InputLabelProps={{ shrink: true }}
              error={!!errors.bankDetails?.bankAddress}
              helperText={errors.bankDetails?.bankAddress?.message}
            />
          </Row>
        </Section>
        {editId ? (
          <Section>
            <Title variant="h6">Upload Documents</Title>
            <DividerStyle />
            <FlexCenter>
              <Box>
                <Typography variant="subtitle1">Candidate Image</Typography>
                <DropzoneContainer {...getProfileProps()}>
                  <input {...getProfileInputProps()} />
                  {profilePic ? (
                    <img
                      src={`data:image/*;base64,${profilePic}`}
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
                      src={`data:image/*;base64,${signature}`}
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
          </Section>
        ) : (
          <Section>
            <Title variant="h6">Upload Documents</Title>
            <DividerStyle />
            <FlexCenter>
              <Box>
                <Typography variant="subtitle1">Candidate Image</Typography>
                <DropzoneContainer {...getProfileProps()}>
                  <input {...getProfileInputProps()} />
                  {profilePic ? (
                    <img
                      src={`data:image/*;base64,${profilePic}`}
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
                      src={`data:image/*;base64,${signature}`}
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
          </Section>
        )}
        <FlexCenter sx={{ mt: "15px" }}>
          <Button variant="contained" type="submit" disabled={loading}>
            {loading
              ? editId && candidate
                ? "Updating..."
                : "Submitting..."
              : editId && candidate
              ? "Update"
              : "Submit"}
          </Button>
          <UpdateDialog
          open={openUpdateDialog}
          handleClose={() => setOpenUpdateDialog(false)}
          handleConfirm={handleConfirmUpdate}
          originalData={originalData!}
          updatedData={updatedData!}
          ignoredKeys={["candidateId"]}
          title="Confirm Candidate Changes"
        />
          <Button variant="contained" onClick={handleCancel}>
            Cancel
          </Button>
        </FlexCenter>
      </Form>
    </>
  );
};
export default CandidateForm;
