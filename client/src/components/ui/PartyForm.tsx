import { Box, Stack, Typography } from "@mui/material";
import { Form, StyledButton } from "../../style/CommanStyle";
import { NameField, NumberField } from "./voter/FormFields";
import ImageUpload from "./voter/ImageUpload";
import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import { formStyles } from '../../style/PartyStyle'; // Import styles


interface FormData {
  partyName: string;
  partyAbbreviation: string | null;
  partyFoundationYear: string | null;
  founderName: string;
  partyWebSite: string;
  headQuarters: string;
  image: string | null;
}

interface PartyFormProps {
  onPartySubmit: (data: FormData, image: string | null) => void;
  initialValues?: FormData;
}

const PartyForm: React.FC<PartyFormProps> = ({ onPartySubmit, initialValues }) => {
  const [logo, setlogo] = useState<string | null>(initialValues?.image || null);

  const { control, handleSubmit, formState, reset, setValue } = useForm<FormData>({
    defaultValues: initialValues || {
      partyName: "",
      partyAbbreviation: "",
      partyFoundationYear: null,
      founderName: "",
      partyWebSite: "",
      headQuarters: "",
      image: null,
    },
    mode: "onTouched",
  });

  useEffect(() => {
    if (initialValues) {
      Object.entries(initialValues).forEach(([name, value]) => {
        setValue(name as keyof FormData, value);
      });
    }
  }, [initialValues, setValue]);

  const onSubmit = async (data: FormData) => {
    console.log("PartyForm: Data before onPartySubmit:", data);
    onPartySubmit(data, logo);
    reset();
    setlogo(null);
  };

  return (
    <Box>
      <Typography align="center" variant="h5" mb="15px">
        {initialValues ? "Edit Party" : "Register Party"}
      </Typography>
      <Form onSubmit={handleSubmit(onSubmit)}>
        <Box sx={formStyles.container}>
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