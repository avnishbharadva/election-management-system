import { useEffect, useState } from 'react';
import { useForm, SubmitHandler, useFormContext } from 'react-hook-form';
import { Box, Button, Checkbox, FormControlLabel, Snackbar, Alert, Typography } from '@mui/material';

import { Title, DividerStyle } from '../../style/CandidateFormCss';
import ImageUpload from './voter/ImageUpload';
import { NumberField, NameField, EmailField, GenderField, PartyField, DateOfBirthField, FirstVoterYear, HasVotedBefore, StatusField } from './voter/FormFields';
import { StyledButton } from '../../style/CommanStyle';
import { useEditVoterMutation, useRegisterVoterMutation } from '../../store/feature/voter/VoterAction';
import { toast } from 'react-toastify';



type Address = {
  addressLine: string;
  aptNumber: string;
  city: string;
  county: string;
  zipCode: number | string;
  adressType: string
}

type FormData = {
  statusId: number | string
  firstName: string
  middleName: string
  lastName: string
  suffixName: string
  email: string
  ssnNumber: string
  phoneNumber: number | string
  dmvNumber: number | string
  gender: string
  partyId: number | string
  dateOfBirth: string
  firstVotedYear: number | string
  hasVotedBefore: boolean
  residentialAddress: Address;
  mailingAddress: Address;
}


const VoterForm = ({ voter,ssnNumber  }: any) => {
  const [profilePic, setProfilePic] = useState<string | null>(null);
  const [signature, setSignature] = useState<string | null>(null);
  const [sameAddress, setSameAddress] = useState(false);

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
  }, [voter, reset]);


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
    <>
      <form onSubmit={handleSubmit(onSubmit)}>
        <Title variant='h5' align='center' gutterBottom>{voter?.voterId ? "Update Voter " : "Register Voter"}</Title>
        {voter?.voterId && <Typography variant='overline' gutterBottom>voterId:{voter?.voterId}</Typography>}
        <DividerStyle />

        {/* Personal Information Section */}
        <Title variant='h6' sx={{ marginTop: '20px' , marginBottom:'20px'  
        }}>Personal Information</Title>
        <Box display='flex' alignItems='center' gap='1rem'>
          <NameField control={control} name="firstName" label="First Name" minLength={3} maxLength={100} />
          <NameField control={control} name="middleName" label="Middle Name" minLength={3} maxLength={100} />
          <NameField control={control} name="lastName" label="Last Name" minLength={3} maxLength={100} />
        </Box>

        <Box sx={{ display: 'flex', alignItems: 'center', gap: '1rem', justifyContent: 'center', marginTop: '20px' }}>
          <DateOfBirthField control={control} name="dateOfBirth" label="Date of Birth" />
          <EmailField control={control} name="email" label="Email" />
          <NumberField control={control} name="phoneNumber" label="Phone Number" fixedLength={11} />
        </Box>

        <Box display='flex' alignItems='center' sx={{ marginTop: '20px' }}>
          <GenderField label='Gender: ' name='gender' control={control} />
        </Box>


        <Box display='flex' alignItems='center' gap='1rem' sx={{ marginTop: '20px' }}>
          <NameField control={control} name="suffixName" label="Suffix Name" minLength={0} maxLength={10} />
          <NumberField control={control} name="ssnNumber" label="SSN Number" fixedLength={9} customfield={{readOnly:true}}  />
          <NumberField control={control} name="dmvNumber" label="DMV Number" fixedLength={9} />

        </Box>

        {/* Voting Information Section */}
        <Box sx={{ marginTop: '20px' }}>
          <Title variant='h6'>Voting Information</Title>
          <DividerStyle />
          <Box display='flex' alignItems='center' gap='2.5rem'>
            <PartyField name='partyId' control={control} label={'Select Party'} />
            <HasVotedBefore name='hasVotedBefore' control={control} label='Has Voted Before' />
            <FirstVoterYear name='firstVoterYear' control={control} label='First Voter Year' />
          </Box>
          <Box display='flex' alignItems='center' sx={{ marginTop: '20px' }}>
            <StatusField label='status: ' name='statusId' control={control} />
          </Box>
        </Box>

        {/* Address Sections */}
        <Box sx={{ marginTop: '20px' }}>
          <Title variant='h6'>Residential Address</Title>
          <DividerStyle />
          <Box display='flex' alignItems='center' gap='1rem'>
            <NameField label='Address Line' name='residentialAddress.addressLine' control={control} />
            <NameField label='Apt Number' name='residentialAddress.aptNumber' control={control} />
          </Box>
          <Box display='flex' alignItems='center' gap='1rem' sx={{ marginTop: '20px' }}>
            <NameField label='City' name="residentialAddress.city" control={control} />
            <NameField label='County' name='residentialAddress.county' control={control} />
            <NameField label="Zipcode" name='residentialAddress.zipCode' control={control} />
          </Box>
        </Box>

        <FormControlLabel
          control={<Checkbox checked={sameAddress} onChange={(e) => setSameAddress(e.target.checked)} />}
          label="Same as Residential Address"
          sx={{ marginTop: '20px' }}
        />

        {/*Mailing Address */}
        {!sameAddress && (
          <Box sx={{ marginTop: '20px' }}>
            <Title variant='h6'>Mailing Address</Title>
            <DividerStyle />
            <Box display='flex' alignItems='center' gap='1rem'>
              <NameField label='Address Line' name='mailingAddress.addressLine' control={control} isRequired={false} />
              <NameField label='Apt Number' name='mailingAddress.aptNumber' control={control} isRequired={false} />
            </Box>
            <Box display='flex' alignItems='center' gap='1rem' sx={{ marginTop: '20px' }}>
              <NameField label='City' name="mailingAddress.city" control={control} isRequired={false} />
              <NameField label='County' name='mailingAddress.county' control={control} isRequired={false} />
              <NameField label="Zipcode" name='mailingAddress.zipCode' control={control} isRequired={false} />
            </Box>
          </Box>
        )}

        {/* Upload Documents */}
        <Title variant='h6' sx={{ marginTop: '20px' }}>Upload Documents</Title>
        <DividerStyle />
        <Box display='flex' alignItems='center' justifyContent='center' gap='2rem'>
          <ImageUpload label="Profile Picture" onImageUpload={setProfilePic} imagePreview={profilePic} />
          <ImageUpload label="Signature" onImageUpload={setSignature} imagePreview={signature} borderRadius="0%" />
        </Box>

        {/* Submit and Reset Buttons */}
        <Box display='flex' alignItems='center' justifyContent='center' gap='2rem' sx={{ marginTop: '20px' }}>
          <StyledButton variant="contained" type="submit" disabled={isSubmitting || !isValid}>  { `${voter?.voterId ? 'Update Voter' : 'Add Voter'}` } </StyledButton>
          <StyledButton variant="contained" type="reset" onClick={()=>reset()}>Reset</StyledButton>
        </Box>
      </form>
    </>
  );
};

export default VoterForm;
