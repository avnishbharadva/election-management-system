import { TextField, MenuItem, FormControl, InputLabel, Select, Radio, RadioGroup, FormLabel, FormControlLabel, FormHelperText, CircularProgress, Typography, Box } from '@mui/material';
import { Controller } from 'react-hook-form';
import { useStatusQuery } from '../store/feature/voter/VoterAction';
import { usePartyListQuery } from '../store/feature/party/partyAction'
import ImageUpload from './ImageUpload';
import { FieldProps, ManuItem } from '../Types/FormField.types';


 
    

  export const NumberField = ({
    control,
    name,
    label,
    minLength = 5,
    maxLength = 11,
    isRequired = true,
    fixedLength,
    customfield = {}
}: FieldProps) => {
    const validationRules: any = {
        ...(isRequired && { required: `*${label} is required` }),
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

    if (fixedLength) {
        validationRules.minLength = {
            value: fixedLength,
            message: `Must be exactly ${fixedLength} digits`
        };
        validationRules.maxLength = {
            value: fixedLength,
            message: `Must be exactly ${fixedLength} digits`
        };
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
                        readOnly: customfield?.readOnly || false,
                    }}
                    value={field.value ?? ''} // Ensure value is never undefined/null
                    onChange={(e) => {
                        let value = e.target.value;

                        // Enforce only digits
                        value = value.replace(/[^0-9]/g, '');

                        // Enforce length constraints
                        if (fixedLength && value.length > fixedLength) {
                            value = value.slice(0, fixedLength);
                        } else if (value.length > maxLength) {
                            value = value.slice(0, maxLength);
                        }

                        field.onChange(value); // Update the form state
                    }}
                />
            )}
            {...customfield}
        />
    );
};
      
   
     
export const NameField = ({
    control,
    name,
    label,
    isRequired = true,
    minLength = 0,
    maxLength = 1000,
    customfield = {}
}: FieldProps) => {
    return (
        <Controller
            name={name}
            control={control}
            rules={{
                ...(isRequired && { required: `*${label} is required` }), 
                minLength: {
                    value: minLength,
                    message: `Minimum length is ${minLength} characters`,
                },
                maxLength: {
                    value: maxLength,
                    message: `Maximum length is ${maxLength} characters`,
                },
            }}
            render={({ field, fieldState }) => (
                <TextField
                    {...field} 
                    label={label}
                    variant="outlined"
                    fullWidth
                    error={!!fieldState?.error} 
                    helperText={fieldState?.error?.message} 
                    inputProps={{
                        readOnly: customfield?.readOnly || false,  
                    }}
                    InputLabelProps={{ shrink: true }}
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
                required: `*${label} is required`,
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

export const GenderField = ({ control, name, label }: FieldProps) => {
    return (
        <Controller
            name={name}
            control={control}
            rules={{
                required: `*${label} is required`
            }}
            render={({ field, fieldState }) => (
                <>
                    <FormLabel>Gender:</FormLabel>
                    <RadioGroup {...field} row>
                        <FormControlLabel 
                            value="MALE" 
                            control={<Radio />} 
                            label="Male" 
                            style={{ marginLeft: "2px" }}
                        />
                        <FormControlLabel 
                            value="FEMALE" 
                            control={<Radio />} 
                            label="Female" 
                        />
                    </RadioGroup>
                    {fieldState?.error && (
                        <FormHelperText style={{ color: 'red' }}>{fieldState?.error.message}</FormHelperText>
                    )}
                </>
            )}
        />
    );
};


export const StatusField = ({ control, name }: FieldProps) => {
    const {data ,isLoading, isError,error:statusError} = useStatusQuery({})

    if (isLoading) {
        return (
          <div>
            <FormLabel>Status:</FormLabel>
            <CircularProgress size={24} />
          </div>
        );
      }
      if (isError) {
        return (
          <div>
            <FormLabel>Status:</FormLabel>
            <Typography color="error">
              {statusError instanceof Error ? statusError.message : 'Failed to load status options'}
            </Typography>
          </div>
        );
      }

    const status = data?.data

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
            
                {status?.map((status:any, index:any) => {
                    return (
                        <FormControlLabel 
                            key={index} 
                            value={status?.statusDesc} 
                            control={<Radio />} 
                            label={status?.statusDesc} 
                        />
                    )
                })}
            
            </RadioGroup>
            {fieldState?.error && (
                        <FormHelperText style={{ color: 'red' }}>{fieldState?.error.message}</FormHelperText>
                    )}    </>
        )}
      />
    );
}


export const PartyField = ({ control, name, label }: FieldProps) => {
    const { data, isLoading, isError, error } = usePartyListQuery({});
const PartyData= data?.data
    return (
        <Controller
            name={name}
            control={control}
            defaultValue=""
            rules={ { required: "Party selection is required" }}
            render={({ field, fieldState }) => (
                <FormControl fullWidth error={!!fieldState?.error || isError}>
                    <InputLabel>{label || "Select Party"}</InputLabel>
                    <Select
                        label={label || "Select Party"}
                        {...field}
                        value={field.value ?? ''}
                        disabled={isLoading || isError}
                    >
                        <MenuItem value="" disabled>Select a party</MenuItem>
                        {isLoading ? (
                            <MenuItem value="" disabled><CircularProgress size={20} /> Loading...</MenuItem>
                        ) : isError ? (
                            <MenuItem value="" disabled>Error loading parties</MenuItem>
                        ) : PartyData?.map((party: any) => (
                            <MenuItem key={party.partyId} value={party.partyName}>{party.partyName}</MenuItem>
                        ))}
                    </Select>
                    {(fieldState?.error || isError) && (
                        <FormHelperText style={{ color: 'red' }}>
                            {fieldState?.error?.message || (isError && (error instanceof Error ? error.message : "Failed to load parties"))}
                        </FormHelperText>
                    )}
                </FormControl>
            )}
        />
    );
};


export const ManuItemComponet = ({ control,
    name,
    label,
    loading = false,
    error = '',
    isError = false,
    data = [],
    valueKey,
    displayKey,
    idKey}: ManuItem) => {
    return (
            <Controller
              name={name}
              control={control}
              defaultValue=""
              rules={{ required: `${label} is required` }}
              render={({ field, fieldState }) => (
                <FormControl fullWidth error={!!fieldState?.error || isError}>
                  <InputLabel>{label}</InputLabel>
                  <Select
                    label={label}
                    {...field}
                    value={field.value ?? ''}
                    disabled={loading || isError}
                  >
                    <MenuItem value="" disabled>
                      Select a {label}
                    </MenuItem>
                    {loading ? (
                      <MenuItem value="" disabled>
                        <CircularProgress size={20} /> Loading...
                      </MenuItem>
                    ) : isError ? (
                      <MenuItem value="" disabled>
                        Error loading {label.toLowerCase()}
                      </MenuItem>
                    ) : data.length > 0 ? (
                      data.map((item) => (
                        <MenuItem
                          key={item[idKey]}
                          value={item[valueKey]}
                        >
                          {item[displayKey]}
                        </MenuItem>
                      ))
                    ) : (
                      <MenuItem value="" disabled>
                        No {label.toLowerCase()} available
                      </MenuItem>
                    )}
                  </Select>
                  {(fieldState?.error || isError) && (
                    <FormHelperText style={{ color: 'red' }}>
                      {fieldState?.error?.message ||
                        (isError &&
                          (error instanceof Error ? error.message : `Failed to load ${label.toLowerCase()}`))}
                    </FormHelperText>
                  )}
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
          required: `*${label} is required`,
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


export const FirstVotedYear = ({ control, name,disabled=false }: FieldProps) => {
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
                <TextField
                    {...field}
                    label="First Voter Year"
                    variant="outlined"
                    fullWidth
                    type="number"
                    InputLabelProps={{ shrink: true }}
                    error={!!fieldState?.error}
                    helperText={fieldState?.error?.message}
                    value={field.value || ""}
                    disabled={disabled}
                />
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
            render={({ field }) => (
                <>
                    <FormControl fullWidth>
                    <InputLabel>Has Voter Before</InputLabel>
                    <Select 
                    {...field} 
                    label = { label }
                    onChange={(e) => field.onChange(e.target.value === 'true')}
                    >
                        <MenuItem value='false'>No</MenuItem>
                        <MenuItem value='true'>Yes</MenuItem>
                    </Select>
                    </FormControl>
                
                </>
            )}
        />
    );
};

export const FormImage=({name,control,label, isRequired=true}:FieldProps)=>{
    return(
        <Controller
        name={name}
        control={control}
        rules={{
            required: isRequired ? ` *${label} is required` : false,
          }}
          render={({ field, fieldState: { error } }) => (
            <Box sx={{ display: 'flex', flexDirection:'column'}} >

            <ImageUpload
              label={label}
              onImageUpload={(imageData) => field.onChange(imageData)}
              imagePreview={field.value}
              borderRadius="8px"
            />
            
            <Typography color="error" variant="body2">
              {error && error.message}
            </Typography>
        
              </Box>
          )}
        />
    )
}