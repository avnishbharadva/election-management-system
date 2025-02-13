import { Box, FormControlLabel, FormLabel, InputLabel, MenuItem, Radio, RadioGroup, Select, TextField, Divider, FormControl } from '@mui/material'
import React, { forwardRef, PropsWithChildren, useState } from 'react'
import { Row } from '../../../style/CandidateFormCss'

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
    onChange?: (e: any) => void, 
    inputRef?: React.Ref<HTMLInputElement>,
    min?: Number,
    max?: Number,
}

const Voterdetails = forwardRef(({ children }: PropsWithChildren) => { 
    return (
        <div>            
            <Divider />
         {children}
        </div>
    )
}
)

export const NameField = ({ id, onChange, inputRef : ref }: InputFieldProps) => {
    const [error, setError] = useState(false);

    const handleChange = (e: any) => {
        if (onChange) {
            onChange(e);
        }
    };

    return (
    <TextField
        required
        label={id}
        id={id}
        inputRef={ref}
        onChange={handleChange} 
        variant="outlined"
        error={error}
        autoFocus={error}
        fullWidth
    />
)
}

export const Email = ({ id, onChange, inputRef: ref }: InputFieldProps) => {
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
            fullWidth
        />
    )
}
// toDo  make value is not pass in ref
export const Gender = ({inputRef: ref}:InputFieldProps) => {
    return (
        <>
            <br/>
            <Row> 
            <FormLabel id="Gender">Gender:</FormLabel>
            <RadioGroup
                aria-labelledby="Gender"
                defaultValue="female"
                name="Gender"
                ref={ref}
                row
            >
                <FormControlLabel value="MALE" control={<Radio />} label="Male" />
                <FormControlLabel value="FEMALE" control={<Radio />} label="Female" />
            </RadioGroup>
            </Row>

        </>
    )
}

export const DateOfBirth = ({ id, onChange, inputRef: ref }: InputFieldProps) => {
    const [error, setError] = useState(false)

    const handleChange = (e: any) => {
        if (onChange) {
            onChange(e);
        }
    }

    return (
        <>
   
            <Box  >
            {/* <FormLabel id="DateofBirth">Date of Birth</FormLabel> */}
        
            <TextField
                id={id}
                label="Date of Birth"
                type='date'
                variant="outlined"
                onChange={handleChange} 
                error={error}
                autoFocus={error}
                inputRef={ref}
                fullWidth
                sx={{width: '253px'}}
                InputLabelProps={{shrink:true}}
            />
            </Box>

            </>
         )   
}

export const VoterNumber = ({ id, onChange, inputRef: ref }: InputFieldProps) => {
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
            inputRef={ref}
            required
            fullWidth
            onChange={handleChange} 
            onKeyDown={(evt) => ["e", "E", "+", "-"].includes(evt.key) && evt.preventDefault()}
        />
    </>)
}

export const HasVotedBefore =({ id, onChange, inputRef: ref }: InputFieldProps) => {
    const handleChange = (e: any) => {
        if (onChange) {
            onChange(e);
        }
    }

    return (
    <>
        <FormControl fullWidth>
            <InputLabel>Has Voted Before:</InputLabel>
        <Select
             id={id}
             label={id}
             onChange={handleChange} 
             inputRef={ref}

        >
            <MenuItem value="true" >Yes</MenuItem>
            <MenuItem value="false">No</MenuItem>
        </Select>
        </FormControl>
    </>)
}

export const FirstVoterYear = ({ id, onChange, inputRef: ref }: InputFieldProps) => {
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
            inputRef={ref}
            fullWidth
            onKeyDown={(evt) => ["e", "E", "+", "-"].includes(evt.key) && evt.preventDefault()}
            onChange={handleChange} 
        />
    </>
}


export const PartyId = ({ id, onChange, inputRef: ref }: InputFieldProps) => {
    const handleChange = (e: any) => {
        if (onChange) {
            onChange(e);
        }
    };

    return (
        <>
            <FormControl fullWidth>
                <InputLabel>Party Id</InputLabel>
            <Select
                id={id}
                label={id}
                onChange={handleChange} 
                inputRef={ref}
            >
                <MenuItem value={1}>Democratic party</MenuItem>
                <MenuItem value={20}>Republican party</MenuItem>
                <MenuItem value={30}>Conservative party</MenuItem>
                <MenuItem value={40}>Working Families party</MenuItem>
                <MenuItem value={50}>Other</MenuItem>
                <MenuItem value={60}>No Party</MenuItem>
            </Select>
            </FormControl>
        </>
    )
}

export default Voterdetails

