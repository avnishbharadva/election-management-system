
import { TextField, MenuItem, FormControl, InputLabel, Select, Radio, RadioGroup, FormLabel, FormControlLabel } from '@mui/material';
import { Controller } from 'react-hook-form';

// Type for the props of each form field component
type FieldProps = {
    control: any;
    name: string;
    label: string;
    fixedLength?: number;
    minLength?: number;
    maxLength?: number;
    isRequired?: boolean;
    rules?: any;
    isdisabled?: boolean;
    customfield?: {
        readOnly?: boolean;
        [key: string]: any;

    }};

 
    export const NumberField = ({
        control,
        name,
        label,
        minLength = 5,
        maxLength = 11,
        fixedLength,
        customfield={
        }
    }: FieldProps) => {
     
        const validationRules: any = {
            required: `${label} is required`,
            minLength: {
                value: minLength,
                message: `Minimum length is ${minLength} digits`
            },
            maxLength: {
                value: maxLength,
                message: `Maximum length is ${maxLength} digits`
            },
            pattern: {
                value: /^[0-9]+$/,
                message: "Only digits are allowed"
            }
        };
     
        // If fixedLength is provided, we override the validation to ensure the exact length
        if (fixedLength) {
            validationRules.minLength = {
                value: fixedLength,
                message: `Must be exactly ${fixedLength} digits`
            }
            validationRules.maxLength = {
                value: fixedLength,
                message: `Must be exactly ${fixedLength} digits`
            }
        }
     
        return (
            <Controller
                name={name}
                control={control}
                rules={validationRules}
               
                render={({ field, fieldState }) => (
                    <TextField
                        {...field}
                        label={label}
                        variant="outlined"
                        fullWidth
                        error={!!fieldState?.error}
                        helperText={fieldState?.error?.message}
     
                        inputProps={{
                            maxLength: fixedLength || maxLength,
                            readOnly: customfield?.readOnly|| false,  
                        }}
                        onInput={(e: any) => {
                            let value = e.target.value;
     
                            // Enforce only digits
                            e.target.value = value.replace(/[^0-9]/g, '');
     
                            // If fixedLength is set, slice the input to that length
                            if (fixedLength && value.length > fixedLength) {
                                e.target.value = value.slice(0, fixedLength);
                            }
                            // Enforce maxLength and minLength
                            if (value.length > maxLength) {
                                e.target.value = value.slice(0, maxLength);
                            }
                        }}
                    />
                )}
                {...customfield}
            />
        );
    }
     
// Name Field
export const NameField = ({
    control,
    name,
    label,
    isRequired = true ,
    minLength = 0,
    maxLength = 1000
}: FieldProps) => {
    return (
        <Controller
            name={name}
            control={control}
            rules={{
                ...(isRequired && { required: `${label} is required` }),
                minLength: {
                    value: minLength,
                    message: `Minimum length is ${minLength} characters`
                },
                maxLength: {
                    value: maxLength,
                    message: `Maximum length is ${maxLength} characters`
                }
            }}
            render={({ field, fieldState }) => (
                <TextField
                    {...field}
                    label={label}
                    variant="outlined"
                    fullWidth
                    error={!!fieldState?.error}
                    helperText={fieldState?.error?.message}
                />
            )}
        />
    );
};

// Email Field
export const EmailField = ({ control, name, label }: FieldProps) => {
    return (
        <Controller
            name={name}
            control={control}
            rules={{
                required: `${label} is required`,
                pattern: {
                    value: /^[a-zA-Z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,8}(.[a-z]{2,8})?$/,
                    message: "Invalid email format"
                }
            }}
            render={({ field, fieldState }) => (
                <TextField
                    {...field}
                    label={label}
                    variant="outlined"
                    fullWidth
                    error={!!fieldState?.error}
                    helperText={fieldState?.error?.message}
                />
            )}
        />
    );
};

// Gender Field (Radio Buttons)
export const GenderField = ({ control, name, label }: FieldProps) => {
    return (
        <Controller
            name={name}

            control={control}
            rules={{
                required: `${label} is required`
            }}
            render={({ field, fieldState }) => (
                <>
                    <FormLabel helperText={fieldState?.error?.message}>Gender:</FormLabel>
                    <RadioGroup {...field} row>
                        <FormControlLabel value="MALE" control={<Radio />} label="Male" style={{ marginLeft: "2px" }}/>
                        <FormControlLabel value="FEMALE" control={<Radio />} label="Female" />
                    </RadioGroup>
                    
                </>
            )}
        />
    );
};

//Status field (Radio Buttons)
export const StatusField = ({ control, name }: FieldProps) => {
    return (
      <Controller
        name={name}
        control={control}
        rules={{ required: "Status is required" }}
        defaultValue={1} 
        render={({ field, fieldState }) => (
          <>
            <FormLabel>Status:</FormLabel>
            <RadioGroup {...field} row>
              <FormControlLabel value={1} control={<Radio />} label="Active"  style={{ marginLeft: "2px" }}/>
              <FormControlLabel value={2} control={<Radio />} label="Inactive" />
              <FormControlLabel value={3} control={<Radio />} label="Reject" />
              <FormControlLabel value={4} control={<Radio />} label="Under Review" /> {/* Corrected typo */}
              <FormControlLabel value={5} control={<Radio />} label="Cancel" />
            </RadioGroup>
            {fieldState?.error && <p style={{ color: 'red' }}>{fieldState?.error.message}</p>}
          </>
        )}
      />
    );
  };

// Party Selector Field
export const PartyField = ({ control, name , label}: FieldProps) => {
    return (
        <Controller
            name={name}
            control={control}
            rules={{
                
                required: "Party selection is required"
            }}
            render={({ field, fieldState }) => (
                <FormControl fullWidth>
                    <InputLabel>Select Party</InputLabel>
                    <Select 
                    label = { label }

                    {...field}>
                        <MenuItem value={1}>Democratic Party</MenuItem>
                        <MenuItem value={2}>Republican Party</MenuItem>
                        <MenuItem value={3}>Independent</MenuItem>
                    </Select>
                    {fieldState?.error && <p style={{ color: 'red' }}>{fieldState?.error.message}</p>}
                </FormControl>
            )}
        />
    );
};


export const DateOfBirthField = ({ control, name, label, rules }: FieldProps) => {
    const validateDateOfBirth = (value: string) => {
       
            const today = new Date();
            const birthDate = new Date(value);
            const age = today.getFullYear() - birthDate.getFullYear();
            const m = today.getMonth() - birthDate.getMonth();
            return (
              (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())
                ? age - 1
                : age) >= 18 || "Candidate must be at least 18 years old"
            );
          }
    
  
    return (
      <Controller
        name={name}
        control={control}
        rules={{
          required: `${label} is required`,
          validate: validateDateOfBirth,
          ...rules, 
        }}
        render={({ field, fieldState }) => (
          <TextField
            {...field}
            label={label}
            type="date"
            fullWidth
            InputLabelProps={{ shrink: true }}
            error={!!fieldState?.error}
            helperText={fieldState?.error?.message}
          />
        )}
      />
    );
  };

  export const FirstVoterYear = ({ control, name }: FieldProps) => {
    return (
        <Controller
            name={name}
            control={control}
            rules={{
                // Ensuring it's a valid year
                validate: (value) => {
                    const year = new Date(value).getFullYear();
                    return year <= new Date().getFullYear() || "Please select a valid year";
                },
            }}
            render={({ field, fieldState }) => (
                <>
                    <TextField
                        {...field}
                        label="First Voter Year"
                        variant="outlined"
                        fullWidth
                        type="number"
                        InputLabelProps={{ shrink: true }}
                        error={!!fieldState?.error}
                        helperText={fieldState?.error?.message}
                       
                    />
                </>
            )}
        />
    );
};

export const HasVotedBefore = ({ control, name , label}: FieldProps) => {
    return (
        <Controller
            name={name}
            control={control}
            rules={{
                // This field is not required, so no validation rules are applied here
            }}
            render={({ field, fieldState }) => (
                <>
                    <FormControl fullWidth>
                    <InputLabel>Has Voter Before</InputLabel>
                    <Select 
                    {...field} 
                    label = { label }
                    
                    >
                        <MenuItem value= 'false'>No</MenuItem>
                        <MenuItem value='true'>Yes</MenuItem>
                        
                    </Select>
                    </FormControl>
                    {fieldState?.error && <p style={{ color: 'red' }}>{fieldState?.error.message}</p>}
                </>
            )}
        />
    );
};