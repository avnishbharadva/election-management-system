
import { Box, Stack, Typography } from "@mui/material";
import { Form, StyledButton } from "../../style/CommanStyle";
import { NameField, NumberField } from "./voter/FormFields";
import ImageUpload from "./voter/ImageUpload";
import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import { useEditPartyMutation, useRegisterPartyMutation } from "../../store/feature/party/partyAction";
import { toast } from "react-toastify";



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

const PartyForm = ({ party }: any) => {
  const [logo, setlogo] = useState<any>(party?.image || null);

  const { control, handleSubmit, formState, reset } = useForm<FormData>({
    defaultValues,
    mode: "onTouched",
  });


  const { isSubmitting, isValid } = formState

  console.log("PartyForm: Party Data:", party);

  useEffect(() => {
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

  const [registerParty] = useRegisterPartyMutation();
  const [editParty] = useEditPartyMutation();


  const onSubmit = async (data: FormData) => {

    if(!logo){
      toast.error("Please upload Party Logo")
      return
    }
    try {
      const result = await toast.promise(
        party?.partyId ? editParty({post:data, img:logo,partyId:party?.partyId}).unwrap()
          : registerParty({post:data, img:logo}).unwrap(),
        {
          pending: "please wait ..." ,
          success: " Successfull",

        }
      )
      if (result) {
        reset(defaultValues)
        setlogo(null)
      }
    } catch (error) {
      console.log("PartyForm: Error on Party Submit:", error);
      toast.error("Error Submitting Party");
    }


  };

  return (
    <Box>
      <Typography align="center" variant="h5" mb="15px">
        {party?.partyId ? "Update Party" : "Register Party"}
      </Typography>
      <Form onSubmit={handleSubmit(onSubmit)}>
        <Box display="flex" flexDirection="column" gap={2}>
          <Box display="flex" flexDirection="row" gap={2}>
            <Box>
              <NameField
                control={control}
                name="partyName"
                label="Party Name"
                minLength={2}
                maxLength={50}
              />
            </Box>
            <Box>

              <NameField
                control={control}
                name="leader"
                label="Leader Name"
                minLength={3}
                maxLength={100}
              />
            </Box>
          </Box>
          <Box display="flex" flexDirection="row" gap={2}>
            <Box>
              <NameField
                control={control}
                name="founderName"
                label="Founder Name"
                minLength={3}
                maxLength={100}
              />
            </Box>
            <Box>
              <NumberField
                control={control}
                name="partyFoundationYear"
                label="Foundation Year"
                fixedLength={4}

              />

            </Box>
          </Box>
          <Box display="flex" flexDirection="row" gap={2}>
            <Box>
              <NameField
                control={control}
                name="partyAbbreviation"
                label="Party Abbreviation"
                minLength={2}
                maxLength={7}
              />
            </Box>
            <Box>
              <NameField
                control={control}
                name="headQuarters"
                label="Headquarters"
                minLength={3}
                maxLength={1000}
              />
            </Box>
          </Box>
          <Box>
            <NameField
              control={control}
              name="partyWebSite"
              label="Website"
              minLength={3}
              maxLength={100}
            />

          </Box>
          <Stack direction='column'  >
            <Typography variant="body1" color="text.secondary" align="left">
              Party Logo
            </Typography>
            <ImageUpload
              label=""
              onImageUpload={setlogo}
              imagePreview={logo}
              borderRadius="0%"
            />
          </Stack>
          <Box
            display="flex"
            alignContent="center"
            justifyContent="center"
            flexDirection="row"
            gap={2}
          >
            <StyledButton type="submit" variant="contained" disabled={isSubmitting || !isValid}>
              Submit
            </StyledButton>
          </Box>
        </Box>
      </Form>
    </Box>
  );
};

export default PartyForm;