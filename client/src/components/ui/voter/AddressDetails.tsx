import { Select, TextField, MenuItem, InputLabel } from '@mui/material'
import React, { PropsWithChildren } from 'react'
import { useState } from 'react'

type InputFieldProps = {
    id:string,
    onChange?:()=>void,
    ref?:React.Ref<HTMLInputElement>
    max?:Number
    min?:Number
}

type AddressProps = PropsWithChildren<{
    AddressLine?: string,
    AptNumber?: string,
    City?: string,
    Country?:string,
    Pincode?:number,
    title?:string
}>


const Address = ({children, title}: AddressProps) => {
  return (
    <div>
        <h2>{title}</h2>
      {children}
    </div>
  )
}


export const AddressLine= ({id,onChange,ref}:InputFieldProps) =>{
    const [error, setError] = useState(false);

    return(
        <TextField
            required
            id={id}
            label={id}
            inputRef={ref}
            onChange={onChange}
            variant='outlined'
            error={error}
            autoFocus={error}
            sx={{}}
            
        />
    )
}

export const AptNumber = ({id,onChange,ref}:InputFieldProps) =>{
    const [error, setError] = useState(false);
    return(
        <TextField
        required
        id={id}
        label={id}
        inputRef={ref}
        onChange={onChange}
        variant='outlined'
        error={error}
        sx={{}}
        />
    )
}

export const City = ({id, onChange}:InputFieldProps) =>{
    return(
        <>
        <InputLabel>City</InputLabel>
        <Select
        required
        id={id}
        labelId={id}
        onChange={onChange}
        sx={{}}
        >
            <MenuItem value="option1">New York</MenuItem>
                <MenuItem value="option2">New Jersey</MenuItem>
                <MenuItem value="option3">Las Vegas</MenuItem>
                <MenuItem value="option4">California</MenuItem>
        </Select>
        </>
    )
}

export const Pincode = ({id,onChange }:InputFieldProps) =>{
    const [error, setError] = useState(false)
    return(
        <TextField
        required
        id={id}
        label={id}
        type='number'
        onChange={onChange}
        error={error}
        sx={{}}
        />
    )
}


export const Country = ({id,ref,onChange}:InputFieldProps) =>{
    const [error, setError] = useState(false)

    return(
        <TextField 
        required
        id={id}
        label={id}
        inputRef={ref}
        onChange={onChange}
        error={error}
        sx={{}}
        />
    )
}
export default Address
