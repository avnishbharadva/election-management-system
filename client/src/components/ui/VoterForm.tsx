
import { Title, DividerStyle } from '../../style/CandidateFormCss'
import ImageUpload from './voter/ImageUpload'
import { Box, Button, Checkbox, FormControlLabel, Alert, Snackbar } from '@mui/material'
import { voterPost } from '../../api/voterApi/VoterApi'
import { useState } from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import { NumberField, NameField, EmailField, GenderField, PartyField, DateOfBirthField, FirstVoterYear, HasVotedBefore } from './voter/FormFields';
import { StyledButton } from '../../style/CommanStyle';


type Address={
  addressLine: string;
  aptNumber: string;
  city: string;
  county: string;
  zipCode: number | String
  adressType:string

}

type FormData = {
  statusId:number
  firstName: string
  middleName: string
  lastName: string
  suffixName:string
  email: string
  ssnNumber: string
  phoneNumber:number
  dmvNumber:number
  gender: string
  partyId: number
  dateOfBirth: string
  firstVoterYear: number
  hasVotedBefore: boolean
  residentialAddress: Address;
  mailingAddress: Address;
  statusId: number | null

}





const VoterForm = () => {


  const [profilePic, setProfilePic] = useState<string | null>(null)
  const [signature, setSignature] = useState<string | null>(null)
  const [sameAddress, setSameAddress] = useState(false)
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [openSnackbar, setOpenSnackbar] = useState(false); // State for Snackbar
  const [snackbarOpen, setSnackbarOpen] = useState(false); // Snackbar state
  const [snackbarMessage, setSnackbarMessage] = useState("");
  const [snackbarSeverity, setSnackbarSeverity] = useState("success");

  const defaultValues: FormData = {
    firstName: "",
    middleName: "",
    lastName: "",
    email: "",
    ssnNumber : "",
    phoneNumber:0,
    dmvNumber:0,
    gender: "",
    suffixName:"",
    partyId: 0,
    dateOfBirth: "",
    firstVoterYear: 0,
    hasVotedBefore: false,
    statusId:1,
    residentialAddress: {
      addressLine: "",
      aptNumber: "",
      city: "",
      county: "",
      zipCode: " ",
      adressType:"RESIDENTIAL"
    },
    mailingAddress: {
      addressLine: "",
      aptNumber: "",
      city: "",
      county: "",
      zipCode:"" ,
      adressType:"MAILING"
    },
    statusId: null
  }

  const { control, handleSubmit, formState: { errors } } = useForm<FormData>({
    defaultValues 
  })
  


  const onSubmit: SubmitHandler<FormData> = async (data) => {
    setIsSubmitting(true); // this will disable the voter
    console.log("Form Data:", data);
    try {
      await new Promise(resolve => setTimeout(resolve, 2000)) 
      const response = await voterPost({ post: data, img: profilePic, sign: signature });
      console.log('Response',response);
      console.log("Snackbar should open");
      setSnackbarOpen(true); // Show Snackbar when form is successfully submitted

      //Reset after successfull
      reset();
      setProfilePic(null);
      setSignature(null);
      setSameAddress(false);

    } catch (error) {
       console.error("Error registering voter:", error);
            setSnackbarMessage("Error registering voter. Please try again.");
            setSnackbarSeverity("error");
            setOpenSnackbar(true);
    
  } finally {
    setIsSubmitting(false); // Re-enable the button in either case
}
  };



  return (
    <>
      <form onSubmit={handleSubmit(onSubmit)}>
        <Title variant='h5' align='center' gutterBottom>VOTER REGISTRATION</Title>
        <DividerStyle />

        <Title variant='h6' sx={{ marginTop: '20px' }}>Personal Information</Title>
        <Box display='flex' alignItems='center' gap='1rem'>

          <NameField
            control={control}
            name="firstName"
            label="FirstName"
            minLength={3}
            maxLength={100}
          />

          <NameField
            control={control}
            name="middleName"
            label="MiddleName"
            minLength={3}
            maxLength={100}
          />
          <NameField
            control={control}
            name="lastName"
            label="LastName"
            minLength={3}
            maxLength={100}
          />

        </Box>

        <Box sx={{ display: 'flex', alignItems: 'center', gap: '1rem', justifyContent: 'center', marginTop: '20px' }} >

          <DateOfBirthField
            control={control}
            name="dateOfBirth"
            label="Date of Birth"
          />

          <EmailField
            control={control}
            name="email"
            label="Email"
          />

          <NumberField
            control={control}
            name="phoneNumber"
            label="Phone Number"
            fixedLength={11}
          />


        </Box>

        <Box display='flex' alignItems='center' sx={{ marginTop: '20px' }}>
          <GenderField
            label='Gender'
            name='gender'
            control={control} />
        </Box>

        <Box display='flex' alignItems='center' gap='1rem'>

          <NameField
            control={control}
            name="suffixName"
            label="Suffix Name"
            minLength={3}
            isRequired={false}
            maxLength={100}
          />



          <NumberField
            control={control}
            name="ssnNumber"
            label="SSN Number"
            fixedLength={9}
          />
          <NumberField
            control={control}
            name="dmvNumber"
            label="DMV Number"
            fixedLength={9}
          />

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



        <Box sx={{ marginTop: '20px' }}>

          <Title variant='h6'>Residential Address</Title>
          <DividerStyle />
          <Box display='flex' alignItems='center' gap='1rem'>
            <NameField label='Addressline' name='residentialAddress.addressLine' control={control} />
            <NameField label='Apt Number' name='residentialAddress.aptNumber'control={control} />

            {/* <AddressLine id="Addressline" inputRef={addressLineRef} />
              <AptNumber id="AptNumber" inputRef={aptNumberRef} /> */}
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
        {/* Mailing Address */}
        {!sameAddress && <Box sx={{ marginTop: '20px' }}>

          <Title variant='h6'>Mailing Address</Title>
          <DividerStyle />
          <Box display='flex' alignItems='center' gap='1rem'>
            <NameField label='Addressline' name='mailingAddress.addressLine' control={control} />
            <NameField label='Apt Number' name='mailingAddress.aptNumber' control={control} />

          </Box>
          <Box display='flex' alignItems='center' gap='1rem' sx={{ marginTop: '20px' }}>
            <NameField label='City' name="mailingAddress.city" control={control} />
            <NameField label='County' name='mailingAddress.County' control={control} />
            <NameField label="Zipcode" name='mailingAddress.zipCode' control={control} />
          </Box>

        </Box>
        }

        <Title variant='h6' sx={{ marginTop: '20px' }}>Upload Documents</Title>
        <DividerStyle />
        <Box display='flex' alignItems='center' justifyContent='center' gap='2rem'>
          <ImageUpload
            label="Profile Picture"
            onImageUpload={setProfilePic}
            imagePreview={profilePic}
          />
          <ImageUpload
            label="Signature"
            onImageUpload={setSignature}
            imagePreview={signature}
            borderRadius="0%"
          />
        </Box>
        <Box display='flex' alignItems='center' justifyContent='center' gap='2rem' sx={{ marginTop: '20px' }}>
          <StyledButton variant="contained" type="submit" disabled={isSubmitting}> {isSubmitting ? "Submitting..." : "Submit"} </StyledButton>
          <StyledButton variant="contained" type="reset">Reset</StyledButton>
        </Box>
      </form >

            {/* Snackbar Component */}
      <Snackbar open={snackbarOpen} autoHideDuration={5000} onClose={() => setSnackbarOpen(false)}>
        <Alert onClose={() => setSnackbarOpen(false)} severity="success" sx={{ width: '100%' }}>
          Form submitted successfully!
        </Alert>
      </Snackbar>
    </>

  )

}
export default VoterForm



