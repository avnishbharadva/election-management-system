
import { useState, useEffect } from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import { Checkbox, FormControlLabel, Typography } from '@mui/material';
import { Title } from '../style/CandidateFormCss';
import ImageUpload from '../Helpers/ImageUpload';
import { NumberField, NameField, EmailField, GenderField, PartyField, DateOfBirthField, FirstVotedYear, HasVotedBefore, StatusField } from '../Helpers/FormFields';
import { StyledButton } from '../style/CommanStyle';
// import { useEditVoterMutation, useRegisterVoterMutation } from '../store/feature/voter/VoterAction';

import { useEditVoterMutation, useRegisterVoterMutation } from '../store/feature/voter/VoterAction';
import { toast } from 'react-toastify';
import { HeaderStyles, Container, FormRow, DividerStyle, FormRowCenter, FormRowGap, FormRowWide, AddressField, UploadDocuments, FormRowCenterGap2, VotingInfo, FormRowCenterGap } from "../style/VoterStyleCss";

import { updateFormValue } from '../Helpers/updateFormValue';
import UpdateDialog from '../components/ui/UpdateDialog';

// import { voterStyles, dividerStyle } from '../style/VoterStyleCss';
 
type Address = {
  addressLine: string;
  aptNumber: string;
  city: string;
  county: string;
  zipCode: number | null;
  addressType: string;
};
 

type FormData = {
  firstName: string;
  middleName: string;
  lastName: string;
  suffixName: string;
  email: string;
  ssnNumber: number | null;
  phoneNumber: number | null;
  dmvNumber: number | null;
  gender: string;
  partyId: number| null;
  dateOfBirth: string;
  firstVotedYear?: number| null;
  hasVotedBefore: boolean;
  residentialAddress: Address;
  mailingAddress?: Address;
  statusId: number | null;
};
const defaultValues: FormData = {
  firstName: "",
  middleName: "",
  lastName: "",
  suffixName: "",
  email: "",
  ssnNumber:null, 
  phoneNumber:null,
  dmvNumber: null,
  gender: "",
  partyId:null,
  dateOfBirth: "",
  firstVotedYear: null,
  hasVotedBefore: false,
  statusId: null,
  residentialAddress: {
    addressLine: "",
    aptNumber: "",
    city: "",
    county: "",
    zipCode: null,
    addressType: "RESIDENTIAL",
  },
  mailingAddress: {
    addressLine: "",
    aptNumber: "",
    city: "",
    county: "",
    zipCode: null,
    addressType: "MAILING",
  },
};
 
 const VoterForm = ({ voter,ssnNumber,onClose}: any) => {
  const [profilePic, setProfilePic] = useState<string | null>(null);
  const [signature, setSignature] = useState<string | null>(null);
  const [sameAddress, setSameAddress] = useState(false);
  const [openDialog, setOpenDialog] = useState(false);
  const [oldVoterData,setOldVoter]= useState({});
  const [updateVoterData,setUpdateVoterData]= useState({});
  

    const { control, handleSubmit, formState, watch,reset , getValues,setValue} = useForm<FormData>({
      defaultValues,
      mode: "onTouched",
    })
    const { isSubmitting,dirtyFields} = formState
   

      useEffect(() => {
        ssnNumber && reset((prv)=>({...prv,ssnNumber:ssnNumber}))
        if (voter) {
          reset({
            ...defaultValues,
            ...voter
          });
          
      }
    }, [voter, reset,ssnNumber]);
   
    const [voterPost, ] = useRegisterVoterMutation()
    const [voterEdit] = useEditVoterMutation( )
   
    const onSubmit = async (data: Record<string, any>) => {
      const updateData = updateFormValue({
        dirtyFields,
        getValues: getValues,  
      })
   
      try {
        const result: any = await toast.promise(
          voter?.voterId ? voterEdit({ voterId: voter?.voterId, post: updateData, image: profilePic, signature: signature }).unwrap()
            : voterPost({ post: data, img: profilePic, sign: signature }).unwrap(),
          {
            pending: "please wait...",
            success: "Successfull!",
         
          },
        )
        if(result){
          setProfilePic(null)  
          setSignature(null)
          setOpenDialog(false)
          reset()
          onClose()

        }
      }
      catch (err:any) {
        
        console.log(err)
        toast.error(`Error: ${err.data?.message || err.message || 'An unexpected error occurred'}`);
      }
    }

    const handleSameAddressChange = (checked: boolean) => {
      setSameAddress(checked);
      if (checked) {
        setValue("mailingAddress", {
          ...defaultValues.residentialAddress,
          addressType: "MAILING",
        });
      } else {
        // If unchecked, reset mailingAddress to empty
        setValue("mailingAddress", {
          addressLine: "",
          aptNumber: "",
          city: "",
          county: "",
          zipCode: null,
          addressType: "MAILING",
        });
      }
    };
    const handleConfirmSubmit: SubmitHandler<FormData> = (data) => {
      
      if (voter?.voterId) {
        setOldVoter(voter);
        setUpdateVoterData(data);
        setOpenDialog(true);
      } else {
        onSubmit(data);
      }
    };

    const hasVoted =  !watch("hasVotedBefore");
    

  return (
    <>
    <form onSubmit={handleSubmit(handleConfirmSubmit)} >
      <HeaderStyles>
        <Title variant="h5" align="center" gutterBottom>
        
          {voter?.voterId ? 'UPDATE VOTER' : 'VOTER REGISTRATION'}  
        </Title>
        <DividerStyle />
        <Container>
          <Title variant="h6">
            Personal Information
          </Title>
          <DividerStyle/>
          <FormRow>
            <NameField control={control} name="firstName" label="First Name" minLength={3} maxLength={100} />
            <NameField control={control} name="middleName" label="Middle Name" minLength={3} maxLength={100} />
            <NameField control={control} name="lastName" label="Last Name" minLength={3} maxLength={100} />
          </FormRow>

          <FormRowCenter>
            <DateOfBirthField control={control} name="dateOfBirth" label="Date of Birth" />
            <EmailField control={control} name="email" label="Email" />
            <NumberField control={control} name="phoneNumber" label="Phone Number" fixedLength={11} />
          </FormRowCenter>

          <FormRow>
            <GenderField label="Gender: " name="gender" control={control} />
          </FormRow>

          <FormRow>
            <NameField control={control} name="suffixName" label="Suffix Name" minLength={3} maxLength={7} />
            <NumberField control={control} name="ssnNumber" label="SSN Number" fixedLength={9} customfield={{ readOnly: true }} />
            <NumberField control={control} name="dmvNumber" label="DMV Number" fixedLength={9} />
          </FormRow>

          <VotingInfo>
            <Title variant="h6">Voting Information</Title>
            <DividerStyle/>
            <FormRowGap>
              <PartyField name="partyId" control={control} label={"Select Party"} />
              <HasVotedBefore name="hasVotedBefore" control={control} label="Has Voted Before" />
              <FirstVotedYear name="firstVotedYear" control={control} label='firstVotedYear' disabled={hasVoted} />
            </FormRowGap>

            <FormRow>
              <StatusField label="status: " name="statusId" control={control} />
            </FormRow>
          </VotingInfo>

          <AddressField>
            <Title variant="h6">Residential Address</Title>
            <DividerStyle/>
            <FormRowWide>
              <NameField label="Address Line" name="residentialAddress.addressLine" control={control} />
              <NameField label="Apt Number" name="residentialAddress.aptNumber" control={control} />
              <NameField label="City" name="residentialAddress.city" control={control} />
              <NameField label="County" name="residentialAddress.county" control={control} />
              <NumberField label="Zipcode" name="residentialAddress.zipCode" control={control} maxLength={5} />
         
            </FormRowWide>
         
          </AddressField>

          <FormControlLabel
        control={
          <Checkbox
            checked={sameAddress}
            onChange={(e) => handleSameAddressChange(e.target.checked)}
          />
        }
        label="Same as Residential Address"
      />
          {!sameAddress && (
            <AddressField>
              <Title variant="h6">Mailing Address</Title>
              <DividerStyle/>
              <FormRowWide>
                <NameField label="Address Line" name="mailingAddress.addressLine" control={control} isRequired={false} />
                <NameField label="Apt Number" name="mailingAddress.aptNumber" control={control} isRequired={false} />
                <NameField label="City" name="mailingAddress.city" control={control} isRequired={false} />
                <NameField label="County" name="mailingAddress.county" control={control} isRequired={false} />
                <NumberField label="Zipcode" name="mailingAddress.zipCode" control={control} isRequired={false} maxLength={5} />
              </FormRowWide>
          
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
            <StyledButton variant="contained" type="button" onClick={onClose}>
              Cancel
            </StyledButton>
          </FormRowCenterGap2>
        </Container>
    
      </HeaderStyles>
    </form>
  
    <UpdateDialog
      open={openDialog}
      handleClose={() => setOpenDialog(false)}
      handleConfirm={onSubmit}
      originalData={oldVoterData}
      updatedData={updateVoterData}
     
    />

  </>
  );
};

export default VoterForm;