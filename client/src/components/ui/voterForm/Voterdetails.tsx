import { FormControlLabel, FormLabel, InputLabel, MenuItem, Radio, RadioGroup, Select, TextField } from '@mui/material'
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

const Voterdetail = ({ children }: VoterdetailProps) => { 
    return (
        <div>
            {children}
        </div>
    )
}

export const NameFild = ({ id, onChange, ref }: InputFieldProps) => {
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
            <TextField
                id={id}
                label="Date Of Birth"
                type='date'
                variant="outlined"
                onChange={handleChange} 
                error={error}
                autoFocus={error}
                inputRef={ref}
                sx={{ mb: 2 }}
            />
        </>)
}

export const voterNumber = ({ id, onChange, ref }: InputFieldProps) => {
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
            onChange={handleChange} // Use handleChange to avoid infinite loop
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

export default Voterdetail

