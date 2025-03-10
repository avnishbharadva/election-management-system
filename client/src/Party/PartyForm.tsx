import { Box, Stack, Typography} from "@mui/material";

import {toast} from 'react-toastify';
import { useRegisterPartyMutation, useEditPartyMutation } from "../store/feature/party/partyAction";
import { useState, useEffect, useRef } from "react";
import { useForm } from "react-hook-form";
// import { Form } from "react-router-dom";
import { NameField, NumberField } from "../Helpers/FormFields";
import ImageUpload from "../Helpers/ImageUpload";
import { StyledButton } from "../style/CommanStyle";
import { formStyles } from "../style/PartyStyle";


interface FormData {
  partyName: string;
  partyAbbreviation: string;
  partyFoundationYear: string;
  founderName: string;
  partyWebSite: string;
  party_ideology: string;
  headQuarters: string;
}
 
 
const defaultValues: FormData = {
  partyName: "",
  partyAbbreviation: "",
  partyFoundationYear: "",
  founderName: "",
  partyWebSite: "",
  party_ideology: "",
  headQuarters: "",
};
 
const PartyForm = ({ party, onClose }: { party: any; onClose: () => void }) => {
  const [logo, setlogo] = useState<any>(party?.image || null);
  const isCancelled = useRef(false); 
  const { control, handleSubmit, formState, reset, getValues } = useForm({
    defaultValues,
    mode: "onTouched",
  });
 
 
  const { isSubmitting, dirtyFields } = formState
  
  useEffect(() => {
    isCancelled.current = false;
    reset(defaultValues);
    if (party) {
      reset({
        partyName: party.partyName || "",
        partyAbbreviation: party.partyAbbreviation || "",
        partyFoundationYear: party.partyFoundationYear || "",
        founderName: party.founderName || "",
        partyWebSite: party.partyWebSite || "",
        headQuarters: party.headQuarters || "",
      });
    }
  }, [party, reset]);

console.log(isCancelled)
 
  const [registerParty] = useRegisterPartyMutation();
  const [editParty, {meta}] = useEditPartyMutation();
 
 
  const onSubmit = async (data: FormData) => {
    // console.log("Change party fields",dirtyFields)
    if (!logo) {
      toast.error("Please upload Party Logo")
      return
    }
    try {
     
      const result = await toast.promise(
        party?.partyId ? editParty({ post: data, img: logo, partyId: party?.partyId }).unwrap()
          : registerParty({ post: data, img: logo }).unwrap(),
        {
          pending: "please wait ...",
          success: " Successfull",
          
        }
      )
      console.log(meta)
      if (result) {
        reset(defaultValues)
        setlogo(null)
      }
    } catch (error) {
      console.log("PartyForm: Error on Party Submit:", error);
      toast.error("Error Submitting Party");
      console.log("Meta inside Catch", meta) 
    }
 
  };

  const handleCancel = () => {
    console.log("handleCancel was called");
    isCancelled.current = true;
    reset(defaultValues);
    setlogo(party?.image || null);
    console.log("PartyForm onClose function:", onClose);
    if(typeof onClose === 'function'){
        onClose();
    } else {
        console.error("onClose is not a function:", onClose)
    }
};
  return (
    <Box sx={formStyles.mainHead}>
      <Typography align="center" variant="h5">
        {party?. partyId ? "Update Party" : "Register Party"}

      </Typography> 
        <form onSubmit={handleSubmit(onSubmit)}>
          <Box sx={formStyles.form}>
         <Box sx={formStyles.container } >
           <Box sx={formStyles.row}>
             <Box>
               <NameField
                 control={control}
                 name="partyName"
                 label="Party Name"
                 minLength={2}
                 maxLength={50}
                 isRequired
               />
             </Box>
             <Box>
               <NameField
                 control={control}
                 name="leader"
                 label="Leader Name"
                 minLength={3}
                 maxLength={100}
                 isRequired
               />
             </Box>
           </Box>
           <Box sx={formStyles.row}>
             <Box>
               <NameField
                 control={control}
                 name="founderName"
                 label="Founder Name"
                 minLength={3}
                 maxLength={100}
                 isRequired
               />
             </Box>
             <Box>
               <NumberField
                 control={control}
                 name="partyFoundationYear"
                 label="Foundation Year"
                 fixedLength={4}
                 isRequired
               />
             </Box>
           </Box>
           <Box sx={formStyles.row}>
             <Box>
               <NameField
                 control={control}
                 name="partyAbbreviation"
                 label="Party Abbreviation"
                 minLength={2}
                 maxLength={7}
                 isRequired
               />
             </Box>
             <Box>
               <NameField
                 control={control}
                 name="headQuarters"
                 label="Headquarters"
                 minLength={3}
                 maxLength={1000}
                 isRequired
               />
             </Box>
           </Box>
           <Box sx={formStyles.gridContainer}>
             <NameField
               control={control}
               name="partyWebSite"
               label="Website"
               minLength={3}
               maxLength={100}
               isRequired
             />
           </Box>
           <Stack direction="column" sx={{ margin: '0 auto'}} >
             <Typography variant="body1" color="text.secondary" align="left">
               Party Logo
             </Typography>
             <Box sx={{width:'100%'}}>
             <ImageUpload
               label=""
               onImageUpload={setlogo}
               imagePreview={logo}
               borderRadius="0%"
             />
             </Box>
           </Stack>
           <Box sx={formStyles.submitButtonContainer}>
           <StyledButton type="submit" variant="contained" disabled={isSubmitting}>
              Submit
            </StyledButton>
             <StyledButton variant="contained" onClick={handleCancel}>
               Cancel
             </StyledButton>
           </Box>
         </Box>
         </Box>
       </form>
    </Box>
  );
};

export default PartyForm;