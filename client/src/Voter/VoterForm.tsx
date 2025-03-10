import { useState, useEffect } from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import { Box, Checkbox, FormControlLabel, Divider } from '@mui/material';
import { Title } from '../style/CandidateFormCss';
import ImageUpload from '../Helpers/ImageUpload';
import { NumberField, NameField, EmailField, GenderField, PartyField, DateOfBirthField, FirstVoterYear, HasVotedBefore, StatusField } from '../Helpers/FormFields';
import { StyledButton } from '../style/CommanStyle';
import { useEditVoterMutation, useRegisterVoterMutation } from '../store/feature/voter/VoterAction';
import { toast } from 'react-toastify';
import { voterStyles, dividerStyle } from '../style/VoterStyleCss'; 

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
  phoneNumber: number;
  dmvNumber: number;
  gender: string;
  partyId: number;
  dateOfBirth: string;
  firstVoterYear: number;
  hasVotedBefore: boolean;
  residentialAddress: Address;
  mailingAddress: Address;
  statusId: number | null;
};

 const VoterForm = ({ voter,ssnNumber  }: any) => {
  const [profilePic, setProfilePic] = useState<string | null>(null);
  const [signature, setSignature] = useState<string | null>(null);
  const [sameAddress, setSameAddress] = useState(false);
  console.log(voter,ssnNumber)
   
    const defaultValues: FormData = {
      firstName: "",
      middleName: "",
      lastName: "",
      email: "",
      ssnNumber:" ",
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
        adressType: "RESIDENTIAL"
      },
      mailingAddress: {
        addressLine: "",
        aptNumber: "",
        city: "",
        county: "",
        zipCode: "",
        adressType: "MAILING"
      }
    }
   
    const { control, handleSubmit, formState, reset } = useForm<FormData>({
      defaultValues,
      mode: "onTouched",
    })
    const { isSubmitting,  isValid } = formState
   
   
   
    useEffect(() => {
      ssnNumber && reset((prv)=>({...prv,ssnNumber:ssnNumber}))
      if (voter) {
        reset({
          firstName: voter.firstName || "",
          middleName: voter.middleName || "",
          lastName: voter.lastName || "",
          email: voter.email || "",
          ssnNumber: voter.ssnNumber || "",
          phoneNumber: voter.phoneNumber || "",
          dmvNumber: voter.dmvNumber || "",
          gender: voter.gender || "",
          suffixName: voter.suffixName || "",
          partyId: voter.partyId || "",
          dateOfBirth: voter.dateOfBirth || "",
          firstVotedYear: voter.firstVotedYear || "",
          hasVotedBefore: voter.hasVotedBefore || false,
          statusId: voter.statusId || "",
          residentialAddress: {
            addressLine: voter.residentialAddress?.addressLine || "",
            aptNumber: voter.residentialAddress?.aptNumber || "",
            city: voter.residentialAddress?.city || "",
            county: voter.residentialAddress?.county || "",
            zipCode: voter.residentialAddress?.zipCode || "",
            adressType: voter.residentialAddress?.adressType
          },
          mailingAddress: {
            addressLine: voter.mailingAddress?.addressLine || "",
            aptNumber: voter.mailingAddress?.aptNumber || "",
            city: voter.mailingAddress?.city || "",
            county: voter.mailingAddress?.county || "",
            zipCode: voter.mailingAddress?.zipCode || "",
            adressType: voter.mailingAddress?.adressType
          }
        });
      }
    }, [voter, reset,ssnNumber]);
   
    const [voterPost, ] = useRegisterVoterMutation()
    const [voterEdit] = useEditVoterMutation()
   
    const onSubmit: SubmitHandler<FormData> = async (data) => {
   
      if (!profilePic) {
        toast.error("Please upload profile picture");
        return;
      }
      if (!signature) { toast.error("Please upload signature"); return; }
      console.log(data ,ssnNumber)
   
      try {
        const result: any = await toast.promise(
          voter?.voterId ? voterEdit({ voterId: voter?.voterId, post: data, img: profilePic, sign: signature }).unwrap()
            : voterPost({ post: data, img: profilePic, sign: signature }).unwrap(),
          {
            pending: "please wait...",
            success: "Successfull!",
         
          },
        )
        if(result){
          reset(defaultValues)
          setProfilePic(null)  
          setSignature(null)
        }
   
      }
      catch (err:any) {
        console.log(err)
        toast.error(`Error: ${err.data?.message || err.message || 'An unexpected error occurred'}`);
      }
    }

  return (
    <form onSubmit={handleSubmit(onSubmit)} >
      <Box sx={voterStyles.form}>
      <Title variant="h5" align="center" gutterBottom sx={voterStyles.head}>
        VOTER REGISTRATION
      </Title>
      <Divider sx={dividerStyle} />
      <Box sx={voterStyles.container}>
      <Title variant="h6">
        Personal Information
      </Title>
      <Divider sx={dividerStyle} />
      <Box sx={voterStyles.formRow}>
        <NameField control={control} name="firstName" label="First Name" minLength={3} maxLength={100} isRequired />
        <NameField control={control} name="middleName" label="Middle Name" minLength={3} maxLength={100} />
        <NameField control={control} name="lastName" label="Last Name" minLength={3} maxLength={100} isRequired />
      </Box>

      <Box sx={voterStyles.formRowCenter}>
        <DateOfBirthField control={control} name="dateOfBirth" label="Date of Birth" isRequired />
        <EmailField control={control} name="email" label="Email" isRequired />
        <NumberField control={control} name="phoneNumber" label="Phone Number" fixedLength={11} isRequired />
      </Box>

      <Box sx={voterStyles.formRow}>
        <GenderField label="Gender: " name="gender" control={control} isRequired />
      </Box>

      <Box sx={voterStyles.formRow}>
        <NameField control={control} name="suffixName" label="Suffix Name" minLength={3} maxLength={7} />
        <NumberField control={control} name="ssnNumber" label="SSN Number" fixedLength={9} customfield={{ readOnly: true }}/>
        <NumberField control={control} name="dmvNumber" label="DMV Number" fixedLength={9} isRequired />
      </Box>

      <Box sx={voterStyles.votingInfo}>
        <Title variant="h6">Voting Information</Title>
        <Divider sx={dividerStyle} />
        <Box sx={voterStyles.formRowGap}>
          <PartyField name="partyId" control={control} label={"Select Party"} isRequired />
          <HasVotedBefore name="hasVotedBefore" control={control} label="Has Voted Before" />
          <FirstVoterYear name="firstVotedYear" control={control} label="First Voted Year" />
        </Box>
        <Box sx={voterStyles.formRow}>
          <StatusField label="status: " name="statusId" control={control} />
        </Box>
      </Box>

      <Box sx={voterStyles.address}>
        <Title variant="h6">Residential Address</Title>
        <Divider sx={dividerStyle} />
        <Box sx={voterStyles.formRowWide}>
          <NameField label="Address Line" name="residentialAddress.addressLine" control={control} isRequired />
          <NameField label="Apt Number" name="residentialAddress.aptNumber" control={control} />
        </Box>
        <Box sx={voterStyles.formRow}>
          <NameField label="City" name="residentialAddress.city" control={control} isRequired />
          <NameField label="County" name="residentialAddress.county" control={control} isRequired />
          <NameField label="Zipcode" name="residentialAddress.zipCode" control={control} isRequired />
        </Box>
      </Box>

      <FormControlLabel
        control={<Checkbox checked={sameAddress} onChange={(e) => setSameAddress(e.target.checked)} />}
        label="Same as Residential Address"
        sx={voterStyles.formControlLabel}
      />

      {!sameAddress && (
        <Box sx={voterStyles.address}>
          <Title variant="h6">Mailing Address</Title>
          <Divider sx={dividerStyle} />
          <Box sx={voterStyles.formRowWide}>
            <NameField label="Address Line" name="mailingAddress.addressLine" control={control} isRequired />
            <NameField label="Apt Number" name="mailingAddress.aptNumber" control={control} />
          </Box>
          <Box sx={voterStyles.formRow}>
            <NameField label="City" name="mailingAddress.city" control={control} isRequired />
            <NameField label="County" name="mailingAddress.county" control={control} isRequired />
            <NameField label="Zipcode" name="mailingAddress.zipCode" control={control} isRequired />
          </Box>
        </Box>
      )}

      <Title variant="h6" sx={voterStyles.uploadDocuments}>
        Upload Documents
      </Title>
      <Divider sx={dividerStyle} />
      <Box sx={voterStyles.formRowCenterGap}>
        <ImageUpload label="Profile Picture" onImageUpload={setProfilePic} imagePreview={profilePic} />
        <ImageUpload label="Signature" onImageUpload={setSignature} imagePreview={signature} borderRadius="0%" />
      </Box>

      <Box sx={voterStyles.formRowCenterGap2}>
        <StyledButton variant="contained" type="submit" disabled={isSubmitting}>
          {voter?.voterId ? 'Update Voter' : 'Add Voter'}
        </StyledButton>
        <StyledButton variant="contained" type="reset" onClick={()=>reset()}>
          Reset
        </StyledButton>
      </Box>
      </Box>
      </Box>
    </form>
  );
};

export default VoterForm;