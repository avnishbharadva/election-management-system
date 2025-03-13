import { Box, Stack, Typography} from "@mui/material";

import { toast } from 'react-toastify';
import { useRegisterPartyMutation, useEditPartyMutation } from "../store/feature/party/partyAction";
import { useState, useEffect, useRef } from "react";
import { useForm } from "react-hook-form";
import { NameField, NumberField } from "../Helpers/FormFields";
import ImageUpload from "../Helpers/ImageUpload";
import { StyledButton } from "../style/CommanStyle";
import { MainHead, Form, Container, Row, GridContainer, SubmitButtonContainer } from "../style/PartyStyle";
import UpdateDialog from "../components/ui/UpdateDialog"

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
  const [logo, setLogo] = useState<any>(party?.partySymbol || null);
  const isCancelled = useRef(false);
  const { control, handleSubmit, formState, reset, getValues } = useForm({
      defaultValues,
      mode: "onTouched",
  });

  const { isSubmitting } = formState;
//   console.log("PartyForm: Party Data:", party.image);
  useEffect(() => {
    if (party) {
        reset({
            ...defaultValues,
            ...party,
        });
        setLogo(party?.partySymbol); // Update logo if party changes
        console.log("Form values after reset:", getValues());
    } else {
        reset(defaultValues);
        setLogo(null);
    }
}, [party, reset, getValues]);

  console.log(isCancelled);

  const [registerParty] = useRegisterPartyMutation();
  const [editParty] = useEditPartyMutation();

  // State for UpdateDialog
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [originalPartyData, setOriginalPartyData] = useState<Record<string, any>>({});
  const [updatedPartyData, setUpdatedPartyData] = useState<Record<string, any>>({});

  const onSubmit = async (data: FormData) => {
    if (!logo) {
        toast.error("Please upload Party Logo");
        return;
    }
    try {
        const result = await toast.promise(
            party?.partyId
                ? editParty({ post: data, img: logo, partyId: party?.partyId }).unwrap()
                : registerParty({ post: data, img: logo }).unwrap(),
            {
                pending: "please wait ...",
                success: "Successful",
            }
        );
        if (result) {
            reset(defaultValues);
            setLogo(null);
            onClose();
        }
    } catch (error) {
        console.log("PartyForm: Error on Party Submit:", error);
        toast.error("Error Submitting Party");
    }
};
    console.log("PartyForm: Party Data:", party);

  const handleConfirm = () => {
    if (party?.partyId) {
        setOriginalPartyData(party);
        setUpdatedPartyData(updatedPartyData);
        setIsDialogOpen(true);
      }
      else{
      onSubmit(updatedPartyData as FormData);
    }
  };

  const handleCancel = () => {
      console.log("handleCancel was called");
      isCancelled.current = true;
      reset(defaultValues);
      setLogo(party?.partySymbol || null);
      console.log("PartyForm onClose function:", onClose);
      if (typeof onClose === 'function') {
          onClose();
      } else {
          console.error("onClose is not a function:", onClose);
      }
  };

  return (
      <MainHead>
          <Typography align="center" variant="h5">
              {party?.partyId ? "Update Party" : "Register Party"}
          </Typography>
          <form onSubmit={handleSubmit(onSubmit)}>
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
                              <ImageUpload
                                  label=""
                                  onImageUpload={setLogo}
                                  imagePreview={logo}
                                  borderRadius="0%"
                              />
                          </Box>
                      </Stack>
                      <SubmitButtonContainer>
                          <StyledButton type="submit" variant="contained" disabled={isSubmitting}>
                              Submit
                          </StyledButton>
                          <StyledButton variant="contained" onClick={handleCancel}>
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
                handleConfirm={handleConfirm}
                originalData={originalPartyData}
                updatedData={updatedPartyData}
                ignoredKeys={["image"]} 
            />
        </MainHead>
    );
};

export default PartyForm;