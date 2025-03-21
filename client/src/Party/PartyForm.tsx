import { Box, Stack, Typography} from "@mui/material";

import { toast } from 'react-toastify';
import { useRegisterPartyMutation, useEditPartyMutation } from "../store/feature/party/partyAction";
import { useState, useEffect, useRef } from "react";
import { useForm } from "react-hook-form";
import { NameField, NumberField } from "../Helpers/FormFields";
import ImageUpload from "../Helpers/ImageUpload";
import { StyledButton } from "../style/CommanStyle";
import CloseIcon from "@mui/icons-material/Close";
import { MainHead, Form, Container, Row, GridContainer, SubmitButtonContainer, CloseIconButton, PartyFormContainer } from "../style/PartyStyle";
import UpdateDialog from "../components/ui/UpdateDialog"
import { partySections } from "./ViewParty";
import ViewDetailsDialog from "../components/ui/ViewDetailsDialog";
import { FormImage } from "../Helpers/FormFields";

interface FormData { 
  partyName: string;
  partyAbbreviation: string;
  partyFoundationYear: string;
  partyFounderName: string;
  partyWebSite: string;
  headQuarters: string;
  partySymbol: string;
  partyLeaderName: string;
}

const defaultValues: FormData = { 
  partyName: "",
  partyAbbreviation: "",
  partyFoundationYear: "",
  partyFounderName: "",
  partyWebSite: "",
  partySymbol: "",
  headQuarters: "",
  partyLeaderName: "",
};

const PartyForm = ({ party, onClose }: { party: any; onClose: () => void }) => {
    // State for UpdateDialog
    const [isDialogOpen, setIsDialogOpen] = useState(false);
    const [originalPartyData, setOriginalPartyData] = useState<Record<string, any>>({});
    const [updatedPartyData, setUpdatedPartyData] = useState<Record<string, any>>({}); 

  const { control, handleSubmit, formState, reset, getValues } = useForm({
      defaultValues,
      mode: "onTouched",
  });

  const { isSubmitting } = formState;

  useEffect(() => {
    setOriginalPartyData(party);
    if (party) {
        reset({
            ...defaultValues,
            ...party,
        });
    

    } else {
        reset(defaultValues);
    
    }
}, [party, reset, getValues]);

  const [registerParty] = useRegisterPartyMutation();
  const [editParty] = useEditPartyMutation();


  const onSubmit = async (data: FormData) => {
    try {
        console.log("Data being sent to API:", data); // Debugging

        const result = await toast.promise(
            party?.partyId
                ? editParty({ post: data, partyId: party?.partyId }).unwrap()
                : registerParty({ post: data }).unwrap(),
            {
                pending: "please wait ...",
                success: "Successful",
            }
        );

        console.log("API result:", result); // Debugging

        if (result) {
            reset(defaultValues);
            onClose();
        }
    } catch (error) {
        console.error("Error Submitting Party:", error); // Detailed error logging
        toast.error("Error Submitting Party");
    }
};



const handleConfirmSubmit = async (data) => {
    let imageurl = data?.partySymbol;
 
    // Ensure the image has the correct prefix
    if (imageurl && !imageurl.startsWith("data:image")) {
        imageurl = `data:image/png;base64,${imageurl}`;
    }
 
    console.log("Processed imageurl:", imageurl);
 
    const updatedData = { ...data, partySymbol: imageurl };
 
    if (party?.partyId) {
        setUpdatedPartyData(updatedData);
        setOriginalPartyData(party);
        setIsDialogOpen(true);
    } else {
        onSubmit(updatedData);
    }
};

    const handleClose = () => {
        onClose();
      };

 
  return (
      <MainHead>
        <PartyFormContainer>
          <Typography align="center" variant="h5">
              {party?.partyId ? "Update Party" : "Register Party"}
          </Typography>
          
            <CloseIconButton onClick={handleClose}>
                <CloseIcon />
            </CloseIconButton>
            </PartyFormContainer>    
        
          <form onSubmit={handleSubmit(handleConfirmSubmit)}>
              <Form>
                  <Container>
                      <Row>
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
                                  name="partyLeaderName"
                                  label="Leader Name"
                                  minLength={3}
                                  maxLength={20}
                                  isRequired
                              />
                          </Box>
                      </Row>
                      <Row>
                          <Box>
                              <NameField
                                  control={control}
                                  name="partyFounderName"
                                  label="Founder Name"
                                  minLength={2}
                                  maxLength={20}
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
                      </Row>
                      <Row>
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
                      </Row>
                      <GridContainer>
                          <NameField
                              control={control}
                              name="partyWebSite"
                              label="Website"
                              minLength={3}
                              maxLength={100}
                              isRequired
                          />
                      </GridContainer>
                      <Stack direction="column" sx={{ margin: '0 auto' }} >
                          <Typography variant="body1" color="text.secondary" align="left">
                              Party Logo
                          </Typography>
                          <Box sx={{ width: '100%' }}>
                             

                        <FormImage 
                            control={control} 
                            name="partySymbol"
                            label="" 
                        />
 
                          </Box>
                      </Stack>
                      <SubmitButtonContainer>
                          <StyledButton type="submit" variant="contained" disabled={isSubmitting}>
                              Submit
                          </StyledButton>
                          <StyledButton variant="contained" onClick={onClose}>
                              Cancel
                          </StyledButton>
                      </SubmitButtonContainer>
                  </Container>
              </Form>
          </form>
           
            
          <UpdateDialog
                open={isDialogOpen}
                title="Confirm Party Update"
                handleClose={() => setIsDialogOpen(false)}
                handleConfirm={async (data) => {
                    await onSubmit(data); // Call the original onSubmit
                    setIsDialogOpen(false); // Close the UpdateDialog
                }}
                originalData={originalPartyData}
                updatedData={updatedPartyData}
            />
        </MainHead>
    );
};

export default PartyForm;
