import { Select, TextField, MenuItem, InputLabel, FormControl } from '@mui/material'
import React, { forwardRef, PropsWithChildren } from 'react'
import { useState } from 'react'

type InputFieldProps = {
    id:string,
    onChange?: (e: React.ChangeEvent<{ value: unknown }>) => void | ((e: any)=>void) ,
    inputRef?:React.Ref<HTMLInputElement>
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


export const AddressLine= ({id,onChange,inputRef:ref}:InputFieldProps) =>{
    const [error, setError] = useState(false);

    const handleChange = (e: any) => {
        if(onChange){
            onChange(e);
        }
    }
    return(
        <TextField
            required
            id={id}
            label={id}
            inputRef={ref}
            onChange={handleChange}
            variant='outlined'
            error={error}
            autoFocus={error}
            fullWidth
            
        />
    )
}

export const AptNumber = ({id,onChange,inputRef:ref}:InputFieldProps) =>{
    const [error, setError] = useState(false);
    const handleChange = (e: any) => {
        if(onChange){
            onChange(e);
        }
    }
    return(
        <TextField
        required
        id={id}
        label={id}
        inputRef={ref}
        onChange={handleChange}
        variant='outlined'
        error={error}
        fullWidth
        />
    )
}

// export const City = ({id, onChange,inputRef:ref}:InputFieldProps) =>{
//     const handleChange = (e: any) => {
//         if(onChange){
//             onChange(e);
//         }
//     }
//     return(
//         <>
//         <FormControl fullWidth>
//         <InputLabel>City</InputLabel>
//         <Select
//         required
//         id={id}
//         value={ref}
//         label={id}
//         onChange={handleChange}
//         fullWidth
//         >
//             <MenuItem value="option1">New York</MenuItem>
//                 <MenuItem value="option2">New Jersey</MenuItem>
//                 <MenuItem value="option3">Las Vegas</MenuItem>
//                 <MenuItem value="option4">California</MenuItem>
//         </Select>
//         </FormControl>
//         </>
//     )
// }


//  not apply input ref add staticly

export const City = forwardRef<HTMLSelectElement, InputFieldProps>(({ id, onChange, inputRef }, ref) => {
    const handleChange = (e:any) => {
        if (onChange) {
            onChange(e);
        }
    };

    return (
        <>
            <FormControl fullWidth>
                <InputLabel>{id}</InputLabel>
                <Select
                    required
                    id={id}
                    // value={inputRef?.current?.value || ""}
                    label={id}
                    onChange={handleChange}
                    ref={ref}
                    fullWidth
                >
                    <MenuItem value="option1">New York</MenuItem>
                    <MenuItem value="option2">New Jersey</MenuItem>
                    <MenuItem value="option3">Las Vegas</MenuItem>
                    <MenuItem value="option4">California</MenuItem>
                </Select>
            </FormControl>
        </>
    );
});

export const Zipcode = ({id,onChange,inputRef:ref }:InputFieldProps) =>{
    const [error, setError] = useState(false)
    const handleChange = (e: any) => {
        if(onChange){
            onChange(e);
        }
    }
    return(
        <TextField
        required
        id={id}
        label={id}
        inputRef={ref}
        type='number'
        onChange={handleChange}
        error={error}
        fullWidth
        onKeyDown={(evt) => ["e", "E", "+", "-"].includes(evt.key) && evt.preventDefault()}
        />
    )
}


export const County = ({id,onChange, inputRef:ref}:InputFieldProps) =>{
    const [error, setError] = useState(false)
    const handleChange = (e: any) => {
        if(onChange){
            onChange(e);
        }
    }
    return(
        <TextField 
        required
        id={id}
        label={id}
        inputRef={ref}
        onChange={handleChange}
        error={error}
        fullWidth
        />
    )
}
export default Address
