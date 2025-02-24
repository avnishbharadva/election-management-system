import { Box,  Stack,  Typography } from "@mui/material";
 
import { Form, StyledButton } from "../../style/CommanStyle";
import { NameField, NumberField } from "./voter/VoterDetails";
import ImageUpload from "./voter/ImageUpload";
import { useState } from "react";
import { useForm } from "react-hook-form";
 
type FormData = {
  party_name: string;
  leader: string;
  founder_name: string;
  found_year: string;
  party_ideology: string;
  headquarters: string;
  website: string;
};
 
const defaultValues: FormData = {
  party_name: "",
  leader: "",
  founder_name: "",
  found_year: "",
  party_ideology: "",
  headquarters: "",
  website: "",
}
 
 
const PartyForm = () => {
 
  const [logo, setlogo] = useState<string | null>(null);
 
  const { control, handleSubmit, formState } = useForm<FormData>({
    defaultValues,
    mode: "onTouched",
  })
 
  // const { isSubmitting, isSubmitSuccessful, isValid } = formState
  
 
  const onSubmit = async (data: FormData) => {
    console.log(data);
  }
 
  return (
    <Box>
      <Typography align="center" variant="h5" mb="15px">
        Register Party
      </Typography>
      <Form onSubmit={handleSubmit(onSubmit)}>
        <Box display="flex" flexDirection="column" gap={2}>
          <Box display="flex" flexDirection="row" gap={2}>
            <Box>
 
              <NameField
                control={control}
                name="party_name"
                label="Party Name"
                minLength={3}
                maxLength={100} />
            </Box>
            <Box>
 
              <NameField
                control={control}
                name="leader"
                label="Leader Name"
                minLength={3}
                maxLength={100} />
            </Box>
          </Box>
          <Box display="flex" flexDirection="row" gap={2}>
            <Box>
              <NameField
                control={control}
                name="founder_name"
                label="Founder Name"
                minLength={3}
                maxLength={100} />
            </Box>
            <Box>
              <NumberField
                control={control}
                name="found_year"
                label="Found Year"
                fixedLength={4}
              />
 
            </Box>
          </Box>
          <Box display="flex" flexDirection="row" gap={2}>
            <Box>
              <NameField
                control={control}
                name="party_ideology"
                label="Party Ideology"
                minLength={10}
                maxLength={10000} />
     
            </Box>
            <Box>
              <NameField
                control={control}
                name="headquarters"
                label="Headquarters"
                minLength={3}
                maxLength={1000} />
            </Box>
          </Box>
          <Box>
            <NameField
              control={control}
              name="website"
              label="Website"
              minLength={3}
              maxLength={100} />
           
          </Box>
          <Stack>
            <ImageUpload
              label="Party Logo"
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
            <StyledButton type="submit" variant="contained">
              Submit
            </StyledButton>
          </Box>
        </Box>
      </Form>
    </Box>
  );
};
 
export default PartyForm;  