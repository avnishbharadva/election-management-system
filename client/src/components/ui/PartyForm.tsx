import { Box, Button, Stack, TextField, Typography } from "@mui/material";
import { Dropzone, Imagepreview } from "../../style/PartyStyle";
import { useCallback, useState } from "react";
import { useDropzone } from "react-dropzone";
import {  StyledButton } from "../../style/CommanStyle";
import { Resolver, useForm } from "react-hook-form";
import { ErrorMsg } from "../../style/LoginStyle";

type FormValues = {
  party_name: string;
  leader: string;
  founder_name: string;
  found_year: string;
  party_ideology: string;
  headquarters: string;
  website: string;
  file: File | null;
};

const resolver: Resolver<FormValues> = async (values) => {
  const errors: Record<string, object> = {};

  if (!values.party_name) {
    errors.party_name = {
      type: "required",
      message: "Party Name is required.",
    };
  }

  if (!values.leader) {
    errors.leader = {
      type: "required",
      message: "Leader Name is required.",
    };
  }

  if (!values.founder_name) {
    errors.founder_name = {
      type: "required",
      message: "Founder Name is required.",
    };
  }

  if (!values.found_year) {
    errors.found_year = {
      type: "required",
      message: "Found Year is required.",
    };
  }

  if (!values.party_ideology) {
    errors.party_ideology = {
      type: "required",
      message: "Party Ideology is required.",
    };
  }

  if (!values.headquarters) {
    errors.headquarters = {
      type: "required",
      message: "Headquarters is required.",
    };
  }

  if (!values.website) {
    errors.website = {
      type: "required",
      message: "Website is required.",
    };
  }

  if (!values.file) {
    errors.file = {
      type: "required",
      message: "Party Symbol is required.",
    };
  }

  return {
    values: Object.keys(errors).length === 0 ? values : {},
    errors,
  };
};

const PartyForm = () => {
  const {
    register,
    handleSubmit,
    formState: { errors },
    setValue,
  } = useForm<FormValues>({ resolver });

  const onSubmit = handleSubmit((data: FormValues) => {
  });

  const [file, setFile] = useState<File | null>(null);
  const [error, setError] = useState<string>("");

  const onDrop = useCallback(
    (acceptedFiles: File[]) => {
      if (acceptedFiles.length > 0) {
        const selectedFile = acceptedFiles[0];
        setFile(selectedFile);
        setValue("file", selectedFile, { shouldValidate: true });
        setError("");
      } else {
        setError("No file selected.");
      }
    },
    [setValue]
  );

  const { getRootProps, getInputProps, isDragActive } = useDropzone({
    onDrop,
    accept: { "image/*": [] },
    multiple: false,
  });

  return (
    <Box>
      <Typography align="center" variant="h5" mb="15px">
        Register Party
      </Typography>
      <form onSubmit={onSubmit}>
        <Box display="flex" flexDirection="column" gap={2}>
          <Box display="flex" flexDirection="row" gap={2}>
            <Box>
              <TextField
                fullWidth
                label="Party Name"
                {...register("party_name", { required: true })}
              />
              {errors?.party_name && <ErrorMsg>{errors.party_name.message}</ErrorMsg>}
            </Box>
            <Box>
              <TextField
                fullWidth
                label="Leader Name"
                {...register("leader", { required: true })}
              />
              {errors?.leader && <ErrorMsg>{errors.leader.message}</ErrorMsg>}
            </Box>
          </Box>
          <Box display="flex" flexDirection="row" gap={2}>
            <Box>
              <TextField
                fullWidth
                label="Founder Name"
                {...register("founder_name", { required: true })}
              />
              {errors?.founder_name && <ErrorMsg>{errors.founder_name.message}</ErrorMsg>}
            </Box>
            <Box>
              <TextField
                fullWidth
                label="Found Year"
                {...register("found_year", { required: true })}
              />
              {errors?.found_year && <ErrorMsg>{errors.found_year.message}</ErrorMsg>}
            </Box>
          </Box>
          <Box display="flex" flexDirection="row" gap={2}>
            <Box>
              <TextField
                fullWidth
                label="Party Ideology"
                {...register("party_ideology", { required: true })}
              />
              {errors?.party_ideology && (
                <ErrorMsg>{errors.party_ideology.message}</ErrorMsg>
              )}
            </Box>
            <Box>
              <TextField
                fullWidth
                label="Headquarters"
                {...register("headquarters", { required: true })}
              />
              {errors?.headquarters && (
                <ErrorMsg>{errors.headquarters.message}</ErrorMsg>
              )}
            </Box>
          </Box>
          <Box>
            <TextField
              fullWidth
              label="Website"
              {...register("website", { required: true })}
            />
            {errors?.website && <ErrorMsg>{errors.website.message}</ErrorMsg>}
          </Box>
          <Stack>
            <Dropzone {...getRootProps()}>
              <input {...getInputProps()} />
              {isDragActive ? (
                <Typography>Drop the Party Symbol here ...</Typography>
              ) : (
                <Typography>
                  Drag &apos;n&apos; drop a Party Symbol here, or click to select one
                </Typography>
              )}
              {file && (
                <Imagepreview>
                  <Typography component="h4">File Preview:</Typography>
                  <Typography component="p">File Name: {file.name}</Typography>
                  <Typography component="p">
                    File Size: {(file.size / 1024).toFixed(2)} KB
                  </Typography>
                  {file.type.startsWith("image/") && (
                    <img
                      height="200px"
                      width="200px"
                      src={URL.createObjectURL(file)}
                      alt="Preview"
                      style={{ borderRadius: "50%" }}
                    />
                  )}
                </Imagepreview>
              )}
              {error && <Box>{error}</Box>}
            </Dropzone>
            {errors?.file && <ErrorMsg>{errors.file.message}</ErrorMsg>}
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
      </form>
    </Box>
  );
};

export default PartyForm;
