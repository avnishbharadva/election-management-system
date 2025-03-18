// import React from "react";
import { TextField, FormControl, FormLabel, RadioGroup, FormControlLabel, Radio, FormHelperText, InputLabel, Select, MenuItem } from "@mui/material";
import { Controller, FieldError } from "react-hook-form";
import { FieldErrors, Control, UseFormRegister } from "react-hook-form";
import { DividerStyle, Row, Section, Title } from "../../../style/CandidateFormCss";

interface CandidateNameErrors {
  firstName?: FieldError;
  middleName?: FieldError;
  lastName?: FieldError;
}

interface PersonalInfoProps {
  register: UseFormRegister<any>;
  errors: FieldErrors & {
    candidateName?: CandidateNameErrors;
  };
  control: Control<any>;
  editId?: string | null;
  candidate?: any;
  searchQuery?: string; 
  watch: (name: string) => any;
}

const PersonalInfo = ({ register, errors, control, watch, editId, candidate, searchQuery }: PersonalInfoProps) => {

  return (
    <Section>
      <Title variant="h6">Personal Information</Title>
      <DividerStyle />
      <Row>
      <TextField
        fullWidth
        label="First Name"
        {...register("candidateName.firstName", { required: "First name is required" })}  
        InputLabelProps={{ shrink: true }}
        error={!!errors.candidateName?.firstName}
        helperText={errors.candidateName?.firstName?.message}
        />
        <TextField
        fullWidth
        label="Middle Name"
        {...register("candidateName.middleName", { required: "Middle name is required" })}  
        InputLabelProps={{ shrink: true }}
        error={!!errors.candidateName?.middleName}
        helperText={errors.candidateName?.middleName?.message}
        />
        <TextField
        fullWidth
        label="Last Name"
        {...register("candidateName.lastName", { required: "Last name is required" })}  
        InputLabelProps={{ shrink: true }}
        error={!!errors.candidateName?.lastName}
        helperText={errors.candidateName?.lastName?.message}
        />

      </Row>
       <Row>
          <TextField
            fullWidth
            type="date"
            label="Date of Birth"
            defaultValue={editId ? candidate?.candidate?.dateOfBirth : ""}
            {...register("dateOfBirth", {
              required: "Date of Birth is required",
              validate: (value) => {
                const today = new Date();
                const birthDate = new Date(value);
                const age = today.getFullYear() - birthDate.getFullYear();
                const m = today.getMonth() - birthDate.getMonth();
                return (
                  (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())
                    ? age - 1
                    : age) >= 18 || "Candidate must be at least 18 years old"
                );
              },
            })}
            InputLabelProps={{ shrink: true }}
            error={!!errors.dateOfBirth}
            helperText={errors.dateOfBirth?.message?.toString()}
          />
          <TextField
            fullWidth
            label="Email"
            type="email"
            InputLabelProps={{ shrink: true }}
            {...register("candidateEmail", { required: "Email is required" })}
            error={!!errors.candidateEmail}
            helperText={errors.candidateEmail?.message?.toString()}
          />
          <TextField
            fullWidth
            label="SSN"
            defaultValue={editId ? candidate?.candidate?.candidateSSN : searchQuery || ""} // ✅ Use searchQuery safely
            {...register("candidateSSN")}
            error={!!errors.candidateSSN}
            helperText={errors.candidateSSN?.message?.toString()}
            InputLabelProps={{ shrink: true }}
            InputProps={{
              readOnly: true,
            }}
            disabled
          />
        </Row>
        <Row>
          <FormControl component="fieldset" error={!!errors.gender}>
            <FormLabel component="legend">Gender</FormLabel>
            <Controller
              name="gender"
              control={control}
              rules={{ required: "Please select a gender." }}
              defaultValue={editId ? candidate?.candidate?.gender : ""}
              render={({ field }) => (
                <RadioGroup {...field} row>
                  <FormControlLabel
                    value="MALE"
                    control={<Radio />}
                    label="Male"
                  />
                  <FormControlLabel
                    value="FEMALE"
                    control={<Radio />}
                    label="Female"
                  />
                </RadioGroup>
              )}
            />
            {errors.gender && (
              <FormHelperText>{errors.gender.message?.toString()}</FormHelperText>
            )}
          </FormControl>
        </Row>
        <Row>
        <FormControl fullWidth error={!!errors.maritalStatus}>
          <InputLabel id="marital-status-label">Marital Status</InputLabel>
          <Controller
            name="maritalStatus"
            control={control}
            rules={{ required: "Marital status is required" }}
            defaultValue={editId ? candidate?.maritalStatus ?? "" : ""}
            render={({ field }) => (
              <Select 
                {...field} 
                labelId="marital-status-label" 
                label="Marital Status" 
              >
                <MenuItem value="SINGLE">Single</MenuItem>
                <MenuItem value="MARRIED">Married</MenuItem>
                <MenuItem value="DIVORCED">Divorced</MenuItem>
                <MenuItem value="WIDOWED">Widowed</MenuItem>
              </Select>
            )}
          />
          {!!errors.maritalStatus && (
            <FormHelperText>{errors.maritalStatus.message?.toString()}</FormHelperText>
          )}
        </FormControl>

                <TextField
                  fullWidth
                  label="Spouse Name"
                  {...register("spouseName")}
                  InputLabelProps={{ shrink: true }}
                  disabled={watch("maritalStatus") === "SINGLE"} 
                />

                <TextField
                  fullWidth
                  label="No of Children"
                  {...register("noOfChildren", { valueAsNumber: true })}
                  type="number"
                  InputLabelProps={{ shrink: true }}
                  disabled={watch("maritalStatus") === "SINGLE"}
                  onKeyDown={(evt) =>
                    ["e", "E", "+", "-"].includes(evt.key) && evt.preventDefault()
                  }
                />
              </Row>
    </Section>
  );
};

export default PersonalInfo;
