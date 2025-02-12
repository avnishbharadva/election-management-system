import { Box, FormControlLabel, FormLabel, InputLabel, MenuItem, Radio, RadioGroup, Select, TextField, Divider, Grid } from '@mui/material'
import React, { PropsWithChildren, useState } from 'react'

type VoterdetailProps = PropsWithChildren<{
    firstName?: String,
    lastName?: String,
    middleName?: String,
    suffixName?: String,
    dateOfBirth?: String,
    gender?: String,
    dmvNumber?: Number,
    ssnNumber?: Number,
    email?: String,
    phoneNumber?: Number,
    hasVotedBefore?: Boolean,
    firstVotedYear?: Number,
    partyId?: Number,
}>

type InputFieldProps = {
    id: string,
    onChange?: (e: any) => void, // Fixed typo of e
    ref?: React.Ref<HTMLInputElement>,
    min?: Number,
    max?: Number,
}

const Voterdetails = ({ children }: PropsWithChildren) => { 
    return (
        <div>            
            <Divider />
         {children}
        </div>
    )
}

export const NameField = ({ id, onChange, ref }: InputFieldProps) => {
    const [error, setError] = useState(false);

    const handleChange = (e: any) => {
        if (onChange) {
            onChange(e);
        }
    };

    return (<TextField
        required
        label={id}
        id={id}
        inputRef={ref}
        onChange={handleChange} 
        variant="outlined"
        error={error}
        autoFocus={error}
        sx={{ mb: 2 }}
    />)
}

export const Email = ({ id, onChange, ref }: InputFieldProps) => {
    const [error, setError] = useState(false);

    const handleChange = (e: any) => {
        const regEx = /^[a-zA-Z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,8}(.[a-z]{2,8})?$/;
        if (regEx.test(e.target.value.toLowerCase())) {
            setError(false);
            if (onChange) {
                onChange(e);
            }
        } else {
            setError(true);
        }
    };

    return (
        <TextField
            id={id}
            label="Email ID"
            type="email"
            variant="outlined"
            onChange={handleChange} 
            error={error}
            autoFocus={error}
            inputRef={ref}
            sx={{ mb: 2 }}
        />
    )
}

export const Gender = () => {
    return (
        <>
            <br/>
            <Box sx={{ ml: 2 }}> 
            <FormLabel id="Gender">Gender</FormLabel>
            <RadioGroup
                aria-labelledby="Gender"
                defaultValue="other"
                name="Gender"
            >
                <FormControlLabel value="female" control={<Radio />} label="Female" />
                <FormControlLabel value="male" control={<Radio />} label="Male" />
                <FormControlLabel value="other" control={<Radio />} label="Other" />
            </RadioGroup>
            </Box>

        </>
    )
}

export const DateOfBirth = ({ id, onChange, ref }: InputFieldProps) => {
    const [error, setError] = useState(false)

    const handleChange = (e: any) => {
        if (onChange) {
            onChange(e);
        }
    }

    return (
        <>
        <Grid container spacing={2}>
            <Grid item xs={12} sm={6}>
            <Box sx={{mb: 2, display:"flex"}} >
            <FormLabel id="DateofBirth">Date of Birth</FormLabel>
        
            <TextField
                id={id}
                type='date'
                variant="outlined"
                onChange={handleChange} 
                error={error}
                autoFocus={error}
                inputRef={ref}
                sx={{ mb: 2 }}
                fullWidth
            />
            </Box>
            </Grid>
            </Grid>
            </>
         )   
}

export const VoterNumber = ({ id, onChange, ref }: InputFieldProps) => {
    const handleChange = (e: any) => {
        if (onChange) {
            onChange(e);
        }
    };

    return (<>
        <TextField
            id={id}
            label={id}
            variant="outlined"
            type='Number'
            sx={{ mb: 2 }}
            inputRef={ref}
            required
            onChange={handleChange} 
        />
    </>)
}

export const HasVotedBefore = () => {
    return (<>
        <FormLabel id="hasVotedBefore">Has Voted Before</FormLabel>
        <RadioGroup
            aria-labelledby="hasVotedBefore"
            defaultValue="true"
            name="hasVotedBefore"
        >
            <FormControlLabel value="true" control={<Radio />} label="Yes" />
            <FormControlLabel value="false" control={<Radio />} label="No" />
        </RadioGroup>
    </>)
}

export const FirstVoterYear = ({ id, onChange, ref }: InputFieldProps) => {
    const handleChange = (e: any) => {
        if (onChange) {
            onChange(e);
        }
    };

    return <>
        <TextField
            id={id}
            label={id}
            variant="outlined"
            type='Number'
            sx={{ mb: 2 }}
            inputRef={ref}
            required
            onChange={handleChange} 
        />
    </>
}

export const PartyId = ({ id, onChange, ref }: InputFieldProps) => {
    const handleChange = (e: any) => {
        if (onChange) {
            onChange(e);
        }
    };

    return (
        <>
            <FormLabel id="PartyId">Party Id</FormLabel>
            <Select
                id={id}
                defaultValue={10}
                onChange={handleChange} 
                inputRef={ref}
                sx={{ mb: 2 }}
            >
                <MenuItem value={10}>Democratic party</MenuItem>
                <MenuItem value={20}>Republican party</MenuItem>
                <MenuItem value={30}>Conservative party</MenuItem>
                <MenuItem value={40}>Working Families party</MenuItem>
                <MenuItem value={50}>Other</MenuItem>
                <MenuItem value={60}>No Party</MenuItem>
            </Select>
        </>
    )
}

export default Voterdetails

