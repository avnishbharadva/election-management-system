

import {
  Dialog, DialogTitle, DialogContent, DialogActions,
  Button, Typography, Box, Grid
} from "@mui/material";
import { useState, useEffect } from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import { Checkbox, FormControlLabel } from '@mui/material';
import { Title } from '../style/CandidateFormCss';
import ImageUpload from '../Helpers/ImageUpload';
import { NumberField, NameField, EmailField, GenderField, PartyField, DateOfBirthField, FirstVoterYear, HasVotedBefore, StatusField } from '../Helpers/FormFields';
import { StyledButton } from '../style/CommanStyle';
import { useEditVoterMutation, useRegisterVoterMutation } from '../store/feature/voter/VoterAction';
import { toast } from 'react-toastify';
import { HeaderStyles, Container, FormRow, DividerStyle, FormRowCenter, FormRowGap, FormRowWide, AddressField, UploadDocuments, FormRowCenterGap2, VotingInfo, FormRowCenterGap } from "../style/VoterStyleCss";
// import { voterStyles, dividerStyle } from '../style/VoterStyleCss';

interface UpdateDialogProps {
  open: boolean;
  title?: string;
  handleClose: () => void;
  handleConfirm: () => void;
  originalData: Record<string, any>;
  updatedData: Record<string, any>;
  ignoredKeys?: string[];
}

const UpdateDialog: React.FC<UpdateDialogProps> = ({
  open, title = "Confirm Changes", handleClose, handleConfirm,
  originalData, updatedData, ignoredKeys = []
}) => {

  // Compare data while ignoring specified keys
  const changes = originalData && updatedData ? Object.keys(originalData).reduce((acc: any, key) => {
    if (!ignoredKeys.includes(key) && originalData[key] !== updatedData[key]) {
      acc[key] = { old: originalData[key], new: updatedData[key] };
    }
    return acc;
  }, {}) : {};

  return (
    <Dialog open={open} onClose={handleClose} maxWidth="sm" fullWidth>
      <DialogTitle sx={{ bgcolor: "#1976d2", color: "#fff", textAlign: "center", fontWeight: "bold" }}>
        {title}
      </DialogTitle>
      <DialogContent sx={{ padding: "20px" }}>
        {Object.keys(changes).length > 0 ? (
          <Grid container spacing={2}>
            {Object.entries(changes).map(([key, value]: any) => (
              <Grid item xs={12} key={key}>
                <Box sx={{
                  display: "flex", justifyContent: "space-between", alignItems: "center",
                  bgcolor: "#f5f5f5", padding: "10px", borderRadius: "8px"
                }}>
                  <Typography variant="body1" fontWeight="bold" sx={{ textTransform: "capitalize" }}>
                    {key.replace(/([A-Z])/g, " $1")}:
                  </Typography>

                  {/* Handle Image Preview */}
                  {key.toLowerCase().includes("image") || key.toLowerCase().includes("signature") ? (
                    <Box sx={{ display: "flex", alignItems: "center", gap: "10px" }}>
                      {value.old && (
                        <img
                          src={value.old.startsWith("data:image") ? value.old : `data:image/png;base64,${value.old}`}
                          alt="Old"
                          width="100"
                          height="100"
                          style={{ borderRadius: key.toLowerCase().includes("candidate") ? "50%" : "0" }}
                        />
                      )}
                      <Typography variant="body1" sx={{ fontWeight: "bold", color: "gray" }}>→</Typography>
                      {value.new && (
                        <img
                          src={value.new.startsWith("data:image") ? value.new : `data:image/png;base64,${value.new}`}
                          alt="New"
                          width="100"
                          height="100"
                          style={{ borderRadius: key.toLowerCase().includes("candidate") ? "50%" : "0" }}
                        />
                      )}
                    </Box>
                  ) : (
                    /* Handle Text Changes */
                    typeof value.old === "object" && typeof value.new === "object" ? (
                      <Typography variant="body1" color="error">
                        {Object.entries(value.old).map(([field, oldVal]: any) => (
                          <span key={field}>
                            {field !== "id" && ( // Ignore ID fields
                              <>
                                <b>{field}:</b> <span style={{ color: "red" }}>{oldVal}</span> →
                                <span style={{ color: "green" }}>{value.new[field]}</span> <br />
                              </>
                            )}
                          </span>
                        ))}
                      </Typography>
                    ) : (
                      <Typography variant="body1" color="error">
                        <span style={{ color: "red" }}>{value.old}</span> →
                        <span style={{ color: "green" }}>{value.new}</span>
                      </Typography>
                    )
                  )}
                </Box>
              </Grid>
            ))}
          </Grid>
        ) : (
          <Typography sx={{ textAlign: "center", fontStyle: "italic", color: "gray", padding: "10px" }}>
            No changes made.
          </Typography>
        )}
      </DialogContent>
      <DialogActions sx={{ justifyContent: "center", padding: "15px" }}>
        <Button onClick={handleClose} variant="outlined" color="secondary" sx={{ width: "120px" }}>
          Cancel
        </Button>
        <Button onClick={handleConfirm} variant="contained" color="primary" sx={{ width: "120px" }}>
          Confirm
        </Button>
      </DialogActions>
    </Dialog>
  );
};

type Address = {
  addressLine: string;
  aptNumber: string;
  city: string;
  county: string;
  zipCode: number | string;
  addressType: string;
};

type FormData = {
  firstName: string;
  middleName: string;
  lastName: string;
  suffixName: string;
  email: string;
  ssnNumber: string;
  phoneNumber: string;
  dmvNumber: string;
  gender: string;
  partyId: string;
  dateOfBirth: string;
  firstVotedYear: string;
  hasVotedBefore: boolean;
  residentialAddress: Address;
  mailingAddress: Address;
  statusId: string | null;
};

interface VoterFormProps {
  voter?: any;
  ssnNumber?: string;
  onCancel: () => void;
}

const VoterForm: React.FC<VoterFormProps> = ({ voter, ssnNumber, onCancel }) => {
  const [profilePic, setProfilePic] = useState<string | null>(null);
  const [signature, setSignature] = useState<string | null>(null);
  const [sameAddress, setSameAddress] = useState(false);
  const [openDialog, setOpenDialog] = useState(false);
  const [originalData, setOriginalData] = useState<Record<string, any>>({});
  const [updatedData, setUpdatedData] = useState<Record<string, any>>({});

  const defaultValues: FormData = {
    firstName: "",
    middleName: "",
    lastName: "",
    email: "",
    ssnNumber: " ",
    phoneNumber: "",
    dmvNumber: "",
    gender: "",
    suffixName: "",
    partyId: "",
    dateOfBirth: "",
    firstVotedYear: "",
    hasVotedBefore: false,
    statusId: "",
    residentialAddress: {
      addressLine: "",
      aptNumber: "",
      city: "",
      county: "",
      zipCode: "",
      addressType: "RESIDENTIAL"
    },
    mailingAddress: {
      addressLine: "",
      aptNumber: "",
      city: "",
      county: "",
      zipCode: "",
      addressType: "MAILING"
    }
  };

  const { control, handleSubmit, formState, reset, getValues } = useForm<FormData>({
    defaultValues,
    mode: "onTouched",
  });
  const { isSubmitting } = formState;

  useEffect(() => {
    ssnNumber && reset((prv)=>({...prv,ssnNumber:ssnNumber}))
    if (voter) {
      reset({
        ...defaultValues,
        ...voter
      })
      console.log(getValues())
    }}, [voter, reset,ssnNumber]); 
 

  const [voterPost,] = useRegisterVoterMutation();
  const [voterEdit] = useEditVoterMutation();

  const onSubmit: SubmitHandler<FormData> = async () => {

    if (!profilePic) {
      toast.error("Please upload profile picture");
      return;
    }
    if (!signature) { toast.error("Please upload signature"); return; }

    setUpdatedData({ ...getValues(), profilePic, signature });
    setOpenDialog(true);
  };

  const handleConfirm = async () => {
    setOpenDialog(false);

    try {
      const data = getValues();
      const result: any = await toast.promise(
        voter?.voterId ? voterEdit({ voterId: voter?.voterId, post: data, img: profilePic, sign: signature }).unwrap()
          : voterPost({ post: data, img: profilePic, sign: signature }).unwrap(),
        {
          pending: "please wait...",
          success: "Successfull!",

        },
      );
      if (result) {
        reset(defaultValues);
        setProfilePic(null);
        setSignature(null);
        if(voter?.voterId){
          setOriginalData(getValues());
        }
      }

    }
    catch (err: any) {
      console.log(err);
      toast.error(`Error: ${err.data?.message || err.message || 'An unexpected error occurred'}`);
    }
  };

  const handleCloseDialog = () => {
    setOpenDialog(false);
  };

  const handleCancel = () => {
    reset(defaultValues);
    setProfilePic(null);
    setSignature(null);
    onCancel();
  };

  return (
    <>
      <form onSubmit={handleSubmit(onSubmit)} >
        <HeaderStyles>
          <Title variant="h5" align="center" gutterBottom>
            VOTER REGISTRATION
          </Title>
          <DividerStyle />
          <Container>
            <Title variant="h6">
              Personal Information
            </Title>
            <DividerStyle/>
            <FormRow>
              <NameField control={control} name="firstName" label="First Name" minLength={3} maxLength={100} isRequired />
              <NameField control={control} name="middleName" label="Middle Name" minLength={3} maxLength={100} />
              <NameField control={control} name="lastName" label="Last Name" minLength={3} maxLength={100} isRequired />
            </FormRow>

            <FormRowCenter>
              <DateOfBirthField control={control} name="dateOfBirth" label="Date of Birth" isRequired />
              <EmailField control={control} name="email" label="Email" isRequired />
              <NumberField control={control} name="phoneNumber" label="Phone Number" fixedLength={11} isRequired />
            </FormRowCenter>

            <FormRow>
              <GenderField label="Gender: " name="gender" control={control} isRequired />
            </FormRow>

            <FormRow>
              <NameField control={control} name="suffixName" label="Suffix Name" minLength={3} maxLength={7} />
              <NumberField control={control} name="ssnNumber" label="SSN Number" fixedLength={9} customfield={{ readOnly: true }} />
              <NumberField control={control} name="dmvNumber" label="DMV Number" fixedLength={9} isRequired />
            </FormRow>

            <VotingInfo>
              <Title variant="h6">Voting Information</Title>
              <DividerStyle/>
              <FormRowGap>
                <PartyField name="partyId" control={control} label={"Select Party"} isRequired />
                <HasVotedBefore name="hasVotedBefore" control={control} label="Has Voted Before" />
                <FirstVoterYear name="firstVotedYear" control={control} label="First Voted Year" />
              </FormRowGap>

              <FormRow>
                <StatusField label="status: " name="statusId" control={control} />
              </FormRow>
            </VotingInfo>

            <AddressField>
              <Title variant="h6">Residential Address</Title>
              <DividerStyle/>
              <FormRowWide>
                <NameField label="Address Line" name="residentialAddress.addressLine" control={control} isRequired />
                <NameField label="Apt Number" name="residentialAddress.aptNumber" control={control} />
              </FormRowWide>
              <FormRow>
                <NameField label="City" name="residentialAddress.city" control={control} isRequired />
                <NameField label="County" name="residentialAddress.county" control={control} isRequired />
                <NumberField label="Zipcode" name="residentialAddress.zipCode" control={control} isRequired maxLength={5} />
              </FormRow>
            </AddressField>

            <FormControlLabel
              control={<Checkbox checked={sameAddress} onChange={(e) => setSameAddress(e.target.checked)} />}
              label="Same as Residential Address"
            />

            {!sameAddress && (
              <AddressField>
                <Title variant="h6">Mailing Address</Title>
                <DividerStyle/>
                <FormRowWide>
                  <NameField label="Address Line" name="mailingAddress.addressLine" control={control} isRequired />
                  <NameField label="Apt Number" name="mailingAddress.aptNumber" control={control} />
                </FormRowWide>
                <FormRow>
                  <NameField label="City" name="mailingAddress.city" control={control} isRequired />
                  <NameField label="County" name="mailingAddress.county" control={control} isRequired />
                  <NumberField label="Zipcode" name="mailingAddress.zipCode" control={control} isRequired maxLength={5} />
                </FormRow>
              </AddressField>
            )}

            <Title variant="h6" sx={{ marginTop: '20px' }}>
              Upload Documents
            </Title>
            <DividerStyle/>
            <FormRowCenterGap>
              <ImageUpload label="Profile Picture" onImageUpload={setProfilePic} imagePreview={profilePic} />
              <ImageUpload label="Signature" onImageUpload={setSignature} imagePreview={signature} borderRadius="0%" />
            </FormRowCenterGap>

            <FormRowCenterGap2 >
              <StyledButton variant="contained" type="submit" disabled={isSubmitting}>
                {voter?.voterId ? 'Update Voter' : 'Add Voter'}


            </StyledButton>
              <StyledButton variant="contained" type="button" onClick={handleCancel}>
                Cancel
              </StyledButton>
            </FormRowCenterGap2>
          </Container>
        </HeaderStyles>
      </form>
      <UpdateDialog
        open={openDialog}
        handleClose={handleCloseDialog}
        handleConfirm={handleConfirm}
        originalData={originalData}
        updatedData={updatedData}
        ignoredKeys={["profilePic", "signature"]}
      />
    </>
  );
};

export default VoterForm;















// import { useState, useEffect } from 'react';
// import { useForm, SubmitHandler } from 'react-hook-form';
// import { Box, Checkbox, FormControlLabel, Divider } from '@mui/material';
// import { Title } from '../style/CandidateFormCss';
// import ImageUpload from '../Helpers/ImageUpload';
// import { NumberField, NameField, EmailField, GenderField, PartyField, DateOfBirthField, FirstVoterYear, HasVotedBefore, StatusField } from '../Helpers/FormFields';
// import { StyledButton } from '../style/CommanStyle';
// import { useEditVoterMutation, useRegisterVoterMutation } from '../store/feature/voter/VoterAction';
// import { toast } from 'react-toastify';
// import { voterStyles, dividerStyle } from '../style/VoterStyleCss';

// type Address = {
//   addressLine: string;
//   aptNumber: string;
//   city: string;
//   county: string;
//   zipCode: number | string;
//   addressType: string;
// };

// type FormData = {
//   firstName: string;
//   middleName: string;
//   lastName: string;
//   suffixName: string;
//   email: string;
//   ssnNumber: string;
//   phoneNumber: string;
//   dmvNumber: string;
//   gender: string;
//   partyId: string;
//   dateOfBirth: string;
//   firstVotedYear: string;
//   hasVotedBefore: boolean;
//   residentialAddress: Address;
//   mailingAddress: Address;
//   statusId: string | null;
// };

// interface VoterFormProps {
//   voter?: any;
//   ssnNumber?: string;
//   onCancel: () => void; // Add onCancel prop
// }

// const VoterForm: React.FC<VoterFormProps> = ({ voter, ssnNumber, onCancel }) => {
//   const [profilePic, setProfilePic] = useState<string | null>(null);
//   const [signature, setSignature] = useState<string | null>(null);
//   const [sameAddress, setSameAddress] = useState(false);
//   console.log(voter, ssnNumber);

//   const defaultValues: FormData = {
//     firstName: "",
//     middleName: "",
//     lastName: "",
//     email: "",
//     ssnNumber: " ",
//     phoneNumber: "",
//     dmvNumber: "",
//     gender: "",
//     suffixName: "",
//     partyId: "",
//     dateOfBirth: "",
//     firstVotedYear: "",
//     hasVotedBefore: false,
//     statusId: "",
//     residentialAddress: {
//       addressLine: "",
//       aptNumber: "",
//       city: "",
//       county: "",
//       zipCode: "",
//       addressType: "RESIDENTIAL"
//     },
//     mailingAddress: {
//       addressLine: "",
//       aptNumber: "",
//       city: "",
//       county: "",
//       zipCode: "",
//       addressType: "MAILING"
//     }
//   };

//   const { control, handleSubmit, formState, reset } = useForm<FormData>({
//     defaultValues,
//     mode: "onTouched",
//   });
//   const { isSubmitting} = formState;

//   useEffect(() => {
//     ssnNumber && reset((prv) => ({ ...prv, ssnNumber: ssnNumber }));
//     if (voter) {
//       reset({
//         firstName: voter.firstName || "",
//         middleName: voter.middleName || "",
//         lastName: voter.lastName || "",
//         email: voter.email || "",
//         ssnNumber: voter.ssnNumber || "",
//         phoneNumber: voter.phoneNumber || "",
//         dmvNumber: voter.dmvNumber || "",
//         gender: voter.gender || "",
//         suffixName: voter.suffixName || "",
//         partyId: voter.partyId || "",
//         dateOfBirth: voter.dateOfBirth || "",
//         firstVotedYear: voter.firstVotedYear || "",
//         hasVotedBefore: voter.hasVotedBefore || false,
//         statusId: voter.statusId || "",
//         residentialAddress: {
//           addressLine: voter.residentialAddress?.addressLine || "",
//           aptNumber: voter.residentialAddress?.aptNumber || "",
//           city: voter.residentialAddress?.city || "",
//           county: voter.residentialAddress?.county || "",
//           zipCode: voter.residentialAddress?.zipCode || "",
//           addressType: voter.residentialAddress?.addressType
//         },
//         mailingAddress: {
//           addressLine: voter.mailingAddress?.addressLine || "",
//           aptNumber: voter.mailingAddress?.aptNumber || "",
//           city: voter.mailingAddress?.city || "",
//           county: voter.mailingAddress?.county || "",
//           zipCode: voter.mailingAddress?.zipCode || "",
//           addressType: voter.mailingAddress?.addressType
//         }
//       });
//     }
//   }, [voter, reset, ssnNumber]);

//   const [voterPost,] = useRegisterVoterMutation();
//   const [voterEdit] = useEditVoterMutation();

//   const onSubmit: SubmitHandler<FormData> = async (data) => {

//     if (!profilePic) {
//       toast.error("Please upload profile picture");
//       return;
//     }
//     if (!signature) { toast.error("Please upload signature"); return; }
//     console.log(data, ssnNumber);

//     try {
//       const result: any = await toast.promise(
//         voter?.voterId ? voterEdit({ voterId: voter?.voterId, post: data, img: profilePic, sign: signature }).unwrap()
//           : voterPost({ post: data, img: profilePic, sign: signature }).unwrap(),
//         {
//           pending: "please wait...",
//           success: "Successfull!",

//         },
//       );
//       if (result) {
//         reset(defaultValues);
//         setProfilePic(null);
//         setSignature(null);
//       }

//     }
//     catch (err: any) {
//       console.log(err);
//       toast.error(`Error: ${err.data?.message || err.message || 'An unexpected error occurred'}`);
//     }
//   };

//   const handleCancel = () => {
//     reset(defaultValues);
//     setProfilePic(null);
//     setSignature(null);
//     onCancel();
//   };

//   return (
//     <form onSubmit={handleSubmit(onSubmit)} >
//       <Box sx={voterStyles.form}>
//         <Title variant="h5" align="center" gutterBottom sx={voterStyles.head}>
//           VOTER REGISTRATION
//         </Title>
//         <Divider sx={dividerStyle} />
//         <Box sx={voterStyles.container}>
//           <Title variant="h6">
//             Personal Information
//           </Title>
//           <Divider sx={dividerStyle} />
//           <Box sx={voterStyles.formRow}>
//             <NameField control={control} name="firstName" label="First Name" minLength={3} maxLength={100} isRequired />
//             <NameField control={control} name="middleName" label="Middle Name" minLength={3} maxLength={100} />
//             <NameField control={control} name="lastName" label="Last Name" minLength={3} maxLength={100} isRequired />
//           </Box>

//           <Box sx={voterStyles.formRowCenter}>
//             <DateOfBirthField control={control} name="dateOfBirth" label="Date of Birth" isRequired />
//             <EmailField control={control} name="email" label="Email" isRequired />
//             <NumberField control={control} name="phoneNumber" label="Phone Number" fixedLength={11} isRequired />
//           </Box>

//           <Box sx={voterStyles.formRow}>
//             <GenderField label="Gender: " name="gender" control={control} isRequired />
//           </Box>

//           <Box sx={voterStyles.formRow}>
//             <NameField control={control} name="suffixName" label="Suffix Name" minLength={3} maxLength={7} />
//             <NumberField control={control} name="ssnNumber" label="SSN Number" fixedLength={9} customfield={{ readOnly: true }} />
//             <NumberField control={control} name="dmvNumber" label="DMV Number" fixedLength={9} isRequired />
//           </Box>

//           <Box sx={voterStyles.votingInfo}>
//             <Title variant="h6">Voting Information</Title>
//             <Divider sx={dividerStyle} />
//             <Box sx={voterStyles.formRowGap}>
//               <PartyField name="partyId" control={control} label={"Select Party"} isRequired />
//               <HasVotedBefore name="hasVotedBefore" control={control} label="Has Voted Before" />
//               <FirstVoterYear name="firstVotedYear" control={control} label="First Voted Year" />
            
//             </Box>
//             <Box sx={voterStyles.formRow}>
//               <StatusField label="status: " name="statusId" control={control} />
//             </Box>
//           </Box>

//           <Box sx={voterStyles.address}>
//             <Title variant="h6">Residential Address</Title>
//             <Divider sx={dividerStyle} />
//             <Box sx={voterStyles.formRowWide}>
//               <NameField label="Address Line" name="residentialAddress.addressLine" control={control} isRequired />
//               <NameField label="Apt Number" name="residentialAddress.aptNumber" control={control} />
//             </Box>
//             <Box sx={voterStyles.formRow}>
//               <NameField label="City" name="residentialAddress.city" control={control} isRequired />
//               <NameField label="County" name="residentialAddress.county" control={control} isRequired />
//               <NameField label="Zipcode" name="residentialAddress.zipCode" control={control} isRequired />
//             </Box>
//           </Box>

//           <FormControlLabel
//             control={<Checkbox checked={sameAddress} onChange={(e) => setSameAddress(e.target.checked)} />}
//             label="Same as Residential Address"
//             sx={voterStyles.formControlLabel}
//           />

//           {!sameAddress && (
//             <Box sx={voterStyles.address}>
//               <Title variant="h6">Mailing Address</Title>
//               <Divider sx={dividerStyle} />
//               <Box sx={voterStyles.formRowWide}>
//                 <NameField label="Address Line" name="mailingAddress.addressLine" control={control} isRequired />
//                 <NameField label="Apt Number" name="mailingAddress.aptNumber" control={control} />
//               </Box>
//               <Box sx={voterStyles.formRow}>
//                 <NameField label="City" name="mailingAddress.city" control={control} isRequired />
//                 <NameField label="County" name="mailingAddress.county" control={control} isRequired />
//                 <NameField label="Zipcode" name="mailingAddress.zipCode" control={control} isRequired />
//               </Box>
//             </Box>
//           )}

//           <Title variant="h6" sx={voterStyles.uploadDocuments}>
//             Upload Documents
//           </Title>
//           <Divider sx={dividerStyle} />
//           <Box sx={voterStyles.formRowCenterGap}>
//             <ImageUpload label="Profile Picture" onImageUpload={setProfilePic} imagePreview={profilePic} />
//             <ImageUpload label="Signature" onImageUpload={setSignature} imagePreview={signature} borderRadius="0%" />
//           </Box>

//           <Box sx={voterStyles.formRowCenterGap2}>
//             <StyledButton variant="contained" type="submit" disabled={isSubmitting}>
//               {voter?.voterId ? 'Update Voter' : 'Add Voter'}
//             </StyledButton>
//             <StyledButton variant="contained" type="button" onClick={handleCancel}>
//               Cancel
//             </StyledButton>
//           </Box>
//         </Box>
//       </Box>
//     </form>
//   );
// };

// export default VoterForm;