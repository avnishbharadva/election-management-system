
import { useState, useEffect } from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import { Button, Checkbox, FormControlLabel} from '@mui/material';
import { ModalFooter, Title } from '../style/CandidateFormCss';
import { NumberField, NameField, EmailField, GenderField, PartyField, DateOfBirthField, FirstVotedYear, HasVotedBefore, StatusField, FormImage } from '../Helpers/FormFields';
import { StyledButton } from '../style/CommanStyle';
import { useEditVoterMutation, useRegisterVoterMutation } from '../store/feature/voter/VoterAction';
import { toast } from 'react-toastify';
import { HeaderStyles, Container, FormRow, DividerStyle, FormRowWide, AddressField, FormRowCenterGap2, VotingInfo, FormRowCenterGap } from "../style/VoterStyleCss";

import { updateFormValue } from '../Helpers/updateFormValue';
import UpdateDialog from '../components/ui/UpdateDialog';
import { FormData } from '../store/feature/voter/type';

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
  party:"",
  dateOfBirth: "",
  firstVotedYear: null,
  hasVotedBefore: false,
  status: "",
  residentialAddress: {
    addressLine: "",
    aptNumber: "",
    city: "",
    county: "",
    zipCode: null,
    town:"",
    state:"",
    addressType: "RESIDENTIAL",
  },
  mailingAddress: {
    addressLine: "",
    aptNumber: "",
    city: "",
    county: "",
    town:"",
    state:"",
    zipCode: null,
    addressType: "MAILING",
  },
  image:"",
  signature:""
};
type  voterFormProps={
  voter:FormData,
  ssnNumber:number,
  onClose:() => void
}

 const VoterForm = ({ voter,ssnNumber,onClose}:voterFormProps) => {
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
        reset(defaultValues)
        ssnNumber && reset((prv)=>({...prv,ssnNumber:ssnNumber}))
        if (voter) {
          reset({
            ...defaultValues,
            ...voter
          });
          
      }
    }, [voter, reset,ssnNumber]);
   
    const [voterPost, ] = useRegisterVoterMutation()
    const [voterEdit] = useEditVoterMutation()
   
    const onSubmit = async (data: Record<string, any>) => {
      const updateData = updateFormValue({
        dirtyFields,
        getValues: getValues,  
      })
   
      try {
        const result: any = await toast.promise(
          voter?.voterId ? voterEdit({ voterId: voter?.voterId, post: updateData }).unwrap()
            : voterPost({ post: data }).unwrap(),
          {
            pending: "please wait...",
            success: {
              render({ data }) {
        
                const successMessage = data?.message || "Successful!";
                return successMessage;
              }
            },
         
          },
        )
        if(result){
          setOpenDialog(false)
          reset()
          onClose()

        }
      }
      catch (err:any) {
        console.error(err)
        toast.error(`Error: ${err.data?.message || err.message || 'An unexpected error occurred'}`);
      }
    }

    const handleSameAddressChange = (checked: boolean) => {
      setSameAddress(checked);
      if (checked) {
        const residentialAddress = getValues("residentialAddress");
        watch("mailingAddress", {
          ...residentialAddress,
          addressType: "MAILING",
        });   
      } else {
        setValue("mailingAddress", {
          addressLine: "",
          aptNumber: "",
          city: "",
          county: "",
          town:"",
          state:"",
          zipCode: null,
          addressType: "MAILING",
        });
        
      }
    };

    const handleConfirmSubmit: SubmitHandler<FormData> = (data) => {

      const imageurl = data?.image && data?.image.replace(/^data:image\/(png|jpeg|svg\+xml);base64,/, '') 
      const SignatureUrl= data?.signature && data?.signature.replace(/^data:image\/(png|jpeg|svg\+xml);base64,/, '')

      const updateData= {...data,image:imageurl,signature:SignatureUrl}
      if (voter?.voterId) {
        setOldVoter(voter);
        setUpdateVoterData(updateData);
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
          <FormRowWide>
            <NameField control={control} name="firstName" label="First Name" minLength={3} maxLength={100} />
            <NameField control={control} name="middleName" label="Middle Name" minLength={3} maxLength={100} />
            <NameField control={control} name="lastName" label="Last Name" minLength={3} maxLength={100} />
            <DateOfBirthField control={control} name="dateOfBirth" label="Date of Birth" />
            <EmailField control={control} name="email" label="Email" />
            <NumberField control={control} name="phoneNumber" label="Phone Number" fixedLength={11} />
          </FormRowWide>

          <FormRow>
            <GenderField label="Gender: " name="gender" control={control} />
          </FormRow>

          <FormRowWide>
            <NameField control={control} name="suffixName" label="Suffix Name" minLength={3} maxLength={7} />
            <NumberField control={control} name="ssnNumber" label="SSN Number" fixedLength={9} customfield={{ readOnly: true }} />
            <NumberField control={control} name="dmvNumber" label="DMV Number" fixedLength={9} />
          </FormRowWide>

          <VotingInfo>
            <Title variant="h6">Voting Information</Title>
            <DividerStyle/>
            <FormRowWide>
              <PartyField name="party" control={control} label={"Select Party"} />
              <HasVotedBefore name="hasVotedBefore" control={control} label="Has Voted Before" />
              <FirstVotedYear name="firstVotedYear" control={control} label='firstVotedYear' disabled={hasVoted} />
            </FormRowWide>
            <FormRow>
              <StatusField label="status: " name="status" control={control} />
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
              <NameField label='town' name="residentialAddress.town" control={control}  maxLength={30} />
              <NameField label='state' name="residentialAddress.state" control={control}  maxLength={30} />
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
                <NameField label="Address Line" name="mailingAddress.addressLine" control={control}  />
                <NameField label="Apt Number" name="mailingAddress.aptNumber" control={control}  />
                <NameField label="City" name="mailingAddress.city" control={control}  />
                <NameField label="County" name="mailingAddress.county" control={control}  />
                <NameField label='town' name="mailingAddress.town" control={control}  maxLength={30} />
                <NameField label='state' name="mailingAddress.state" control={control}  maxLength={30} />
                <NumberField label="Zipcode" name="mailingAddress.zipCode" control={control}  maxLength={5} />
          
                
              </FormRowWide>
          
            </AddressField>
          )}

          <Title variant="h6" sx={{ marginTop: '20px' }}>
            Upload Documents
          </Title>
          <DividerStyle/>
          <FormRowCenterGap>
            <FormImage label='Profile' control={control} name='image' />
            <FormImage label= 'Signature' control={control} name='signature' / >
          </FormRowCenterGap>

          <ModalFooter>
            <Button variant="contained" type="submit" disabled={isSubmitting}>
              {voter?.voterId ? 'Update Voter' : 'Add Voter'}
          </Button>
            <Button variant="contained" type="button" onClick={onClose}>
              Cancel
            </Button>
            </ModalFooter>
        </Container>
    
      </HeaderStyles>
    </form>
  
    <UpdateDialog
    title="update Voter"
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