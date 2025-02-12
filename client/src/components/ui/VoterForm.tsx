import { useState } from 'react'
import Address, { AddressLine, AptNumber, City } from '../ui/voter/AddressDetails'
import Voterdetails, { FirstVoterYear, Gender, PartyId } from './voter/VoterDetails'
import { NameField } from './voter/VoterDetails'
import { Email } from './voter/VoterDetails'
import {VoterNumber} from './voter/VoterDetails'
import { DateOfBirth } from './voter/VoterDetails'
import { HasVotedBefore } from './voter/VoterDetails'
import {Zipcode} from './voter/AddressDetails'
import {County} from './voter/AddressDetails'
import { Title, DividerStyle } from '../../style/CandidateFormCss'
import ImageUpload from './voter/ImageUpload'
import { Box, Button, Checkbox, FormControlLabel } from '@mui/material'

const VoterForm = () => {
  const [profilePic, setProfilePic] = useState<string | null>(null);
  const [signature, setSignature] = useState<string | null>(null);
  const [sameAddress, setSameAddress] = useState(false);
  return (
        <>
          <Title variant='h5' align='center' gutterBottom>VOTER REGISTRATION</Title>
          <Voterdetails>

              <Title variant='h6' sx={{marginTop:'20px'}}>Personal Information</Title>
              <DividerStyle />
              <Box display='flex' alignItems='center' gap='1rem'>
                <NameField id="FirstName" />
                <NameField id="MiddleName" />
                <NameField id="LastName" />
              </Box>

              <Box sx={{ display:'flex', alignItems:'center', gap:'1rem',  justifyContent:'center', marginTop:'20px'}} >
                <DateOfBirth id="DateOfBirth" />
                <Email id="Email" />
                <VoterNumber id="Phone Number"/>
              </Box>

              <Box display='flex' alignItems='center' sx={{marginTop:'20px'}}>
                <Gender /> 
              </Box>

              <Box display='flex' alignItems='center' gap='1rem'>
                <NameField id="SuffixName" />
                <VoterNumber id="SSN Number" />
                <VoterNumber id="DMV Number" />
              </Box>

            <Box sx={{marginTop:'20px'}}>
              <Title variant='h6'>Voting Information</Title>
              <DividerStyle />
              <Box display='flex' alignItems='center' gap='2.5rem'>
                <PartyId id="PartyId" />
                <HasVotedBefore id="Has Voted Before" />
                  <FirstVoterYear id="First Vote Year" />
                </Box>
              </Box>

          </Voterdetails>

          <Box sx={{marginTop:'20px'}}>
        <Address>
          <Title variant='h6'>Residential Address</Title>
          <DividerStyle />
          <Box display='flex' alignItems='center' gap='1rem'>
            <AddressLine id="Addressline"/>
            <AptNumber id="AptNumber"/>
          </Box>
          <Box  display='flex' alignItems='center' gap='1rem' sx={{marginTop:'20px'}}>
            <City id="City" />
            <County id="County"/>
            <Zipcode id="Zipcode"/>
          </Box>
        </Address>       
        </Box>

        <FormControlLabel
        control={<Checkbox checked={sameAddress} onChange={(e) => setSameAddress(e.target.checked)} />}
        label="Same as Residential Address"
        sx={{ marginTop: '20px' }}
      />

          {!sameAddress && <Box sx={{marginTop:'20px'}}>
          <Address>
            <Title variant='h6'>Mailing Address</Title>
            <DividerStyle />
            <Box display='flex' alignItems='center' gap='1rem'>
              <AddressLine id="Addressline"/>
              <AptNumber id="AptNumber"/>
            </Box>
            <Box  display='flex' alignItems='center' gap='1rem' sx={{marginTop:'20px'}}>
              <City id="City" />
              <County id="County"/>
              <Zipcode id="Zipcode"/>
            </Box>
          </Address>
          </Box>
          }

        <Title variant='h6' sx={{marginTop:'20px'}}>Upload Documents</Title> 
        <DividerStyle />
        <Box display='flex' alignItems='center' justifyContent='center' gap='2rem'>
          <ImageUpload
            label="Profile Picture"
            onImageUpload={setProfilePic}
            imagePreview={profilePic}
          />
          <ImageUpload
            label="Signature"
            onImageUpload={setSignature}
            imagePreview={signature}
            borderRadius="0%"
          />
        </Box>
        <Box display='flex' alignItems='center' justifyContent='center' gap='2rem' sx={{marginTop:'20px'}}>
        <Button variant="contained">Submit</Button>
        <Button variant="contained">Cancel</Button>
        </Box>
      </>
    
  )
}

export default VoterForm
