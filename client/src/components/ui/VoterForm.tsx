  import { useRef, useState } from 'react'
  import Address, { AddressLine, AptNumber, City } from '../ui/voter/AddressDetails'
  import Voterdetails, { FirstVoterYear, Gender, PartyId } from './voter/VoterDetails'
  import { NameField } from './voter/VoterDetails'
  import { Email } from './voter/VoterDetails'
  import { VoterNumber } from './voter/VoterDetails'
  import { DateOfBirth } from './voter/VoterDetails'
  import { HasVotedBefore } from './voter/VoterDetails'
  import { Zipcode } from './voter/AddressDetails'
  import { County } from './voter/AddressDetails'
  import { Title, DividerStyle } from '../../style/CandidateFormCss'
  import ImageUpload from './voter/ImageUpload'
  import { Box, Button, Checkbox, FormControlLabel } from '@mui/material'
  import { voterPost } from '../../api/voterApi/VoterApi'

  const VoterForm = () => {
    const [profilePic, setProfilePic] = useState<string | null>(null);
    const [signature, setSignature] = useState<string | null>(null);
    const [sameAddress, setSameAddress] = useState(false);

    const firstNameRef = useRef<HTMLInputElement>(null);
    const middleNameRef = useRef<HTMLInputElement>(null);
    const lastNameRef = useRef<HTMLInputElement>(null);
    const suffixNameRef = useRef<HTMLInputElement>(null);
    const dateOfBirthRef = useRef<HTMLInputElement>(null)
    const genderRef = useRef(null); // Ref with getGenderValue method


    const dmvNumberRef = useRef<HTMLInputElement>(null);
    const ssnNumberRef = useRef<HTMLInputElement>(null);
    const hasVotedBeforeRef = useRef<HTMLInputElement>(null)
    const emailRef = useRef<HTMLInputElement>(null);
    const phoneNumberRef = useRef<HTMLInputElement>(null);
    const firstVotedYearRef = useRef<HTMLInputElement>(null);
    const partyIdRef = useRef<HTMLInputElement>(null);


    const addressLineRef = useRef<HTMLInputElement>(null);
    const aptNumberRef = useRef<HTMLInputElement>(null);
    const cityRef = useRef<HTMLInputElement>(null);
    const countyRef = useRef<HTMLInputElement>(null);
    const zipCodeRef = useRef<HTMLInputElement>(null);


    //mail address ref
    const mailingAddressLineRef = useRef<HTMLInputElement>(null);
    const mailingAptNumberRef = useRef<HTMLInputElement>(null);
    const mailingCityRef = useRef<HTMLInputElement>(null);
    const mailingCountyRef = useRef<HTMLInputElement>(null);
    const mailingZipCodeRef = useRef<HTMLInputElement>(null);
    




    const handleSubmit = async (e: any) => {
      e.preventDefault()

      const postData = {
        firstName: firstNameRef.current?.value,
        middleName: middleNameRef.current?.value,
        lastName: lastNameRef.current?.value,
        suffixName: suffixNameRef.current?.value,
        dateOfBirth: dateOfBirthRef.current?.value,
        gender: "MALE",  // make dynamic
        dmvNumber: dmvNumberRef.current?.value,
        ssnNumber: ssnNumberRef.current?.value,
        email: emailRef.current?.value,
        phoneNumber: phoneNumberRef.current?.value,
        hasVotedBefore: hasVotedBeforeRef.current?.value,
        firstVotedYear: firstVotedYearRef.current?.value,
        partyId: 1, // partyIdRef.current?.value,
        address: [
          {
            addressLine: addressLineRef.current?.value,
            aptNumber: aptNumberRef.current?.value,
            // city: cityRef.current?.value,
            city:"New York",
            county: countyRef.current?.value,
            zipCode: zipCodeRef.current?.value,
            // addressType:"RESIDENTIAL"
            addressType:` ${sameAddress ? 'SAME': "RESIDENTIAL"}`
          },
          ...(!sameAddress ? [{
            addressLine: mailingAddressLineRef.current?.value,
            aptNumber: mailingAptNumberRef.current?.value,
            // city: mailingCityRef.current?.value,
            city:"New York",
            county: mailingCountyRef.current?.value,
            zipCode: mailingZipCodeRef.current?.value,
            addressType: 'MAILING',
          }] : []),
          
        ],
        // image: profilePic,
        // signature: signature
      };


      console.log(postData)
      try {
        // await voterPost({ post: postData })
        await voterPost({ post: postData ,img:profilePic,sign:signature})
      } catch (error) {
        console.log(error)
      }
    }



    return (
      <>
        <Title variant='h5' align='center' gutterBottom>VOTER REGISTRATION</Title>
        <Voterdetails>

          <Title variant='h6' sx={{ marginTop: '20px' }}>Personal Information</Title>
          <DividerStyle />
          <Box display='flex' alignItems='center' gap='1rem'>
            <NameField id="FirstName" inputRef={firstNameRef} />
            <NameField id="MiddleName" inputRef={middleNameRef} />
            <NameField id="LastName" inputRef={lastNameRef} />
          </Box>

          <Box sx={{ display: 'flex', alignItems: 'center', gap: '1rem', justifyContent: 'center', marginTop: '20px' }} >
            <DateOfBirth id="DateOfBirth" inputRef={dateOfBirthRef} />
            <Email id="Email" inputRef={emailRef} />
            <VoterNumber id="Phone Number" inputRef={phoneNumberRef} />
          </Box>

          <Box display='flex' alignItems='center' sx={{ marginTop: '20px' }}>
            <Gender id={'gender'} 
            // inputRef={genderRef}  // not applicable
            />
          </Box>

          <Box display='flex' alignItems='center' gap='1rem'>
            <NameField id="SuffixName" inputRef={suffixNameRef} />
            <VoterNumber id="SSN Number" inputRef={ssnNumberRef} />
            <VoterNumber id="DMV Number" inputRef={dmvNumberRef} />
          </Box>

          <Box sx={{ marginTop: '20px' }}>
            <Title variant='h6'>Voting Information</Title>
            <DividerStyle />
            <Box display='flex' alignItems='center' gap='2.5rem'>
              <PartyId id="PartyId" inputRef={partyIdRef} />  {/* partyId Static pass is 1 */}
              <HasVotedBefore id="Has Voted Before" inputRef={hasVotedBeforeRef} />
              <FirstVoterYear id="First Vote Year" inputRef={firstVotedYearRef} />
            </Box>
          </Box>

        </Voterdetails>

        <Box sx={{ marginTop: '20px' }}>
        <Address>
            <Title variant='h6'>Residential Address</Title>
            <DividerStyle />
            <Box display='flex' alignItems='center' gap='1rem'>
              <AddressLine id="Addressline" inputRef={addressLineRef} />
              <AptNumber id="AptNumber" inputRef={aptNumberRef} />
            </Box>
            <Box display='flex' alignItems='center' gap='1rem' sx={{ marginTop: '20px' }}>
              <City id="City" inputRef={cityRef} />
              <County id="County" inputRef={countyRef} />
              <Zipcode id="Zipcode" inputRef={zipCodeRef} />
            </Box>
          </Address>
        </Box>

        <FormControlLabel
          control={<Checkbox checked={sameAddress} onChange={(e) => setSameAddress(e.target.checked)} />}
          label="Same as Residential Address"
          sx={{ marginTop: '20px' }}
        />
 {/* Mailing Address */}
        {!sameAddress && <Box sx={{ marginTop: '20px' }}>
          <Address>
            <Title variant='h6'>Mailing Address</Title>
            <DividerStyle />
            <Box display='flex' alignItems='center' gap='1rem'>
              <AddressLine id="Addressline" inputRef={mailingAddressLineRef} />
              <AptNumber id="AptNumber" inputRef={mailingAptNumberRef} />
            </Box>
            <Box display='flex' alignItems='center' gap='1rem' sx={{ marginTop: '20px' }}>
              <City id="City" inputRef={mailingCityRef} />
              <County id="County" inputRef={mailingCountyRef} />
              <Zipcode id="Zipcode" inputRef={mailingZipCodeRef} />
            </Box>
          </Address>
        </Box>
        }

        <Title variant='h6' sx={{ marginTop: '20px' }}>Upload Documents</Title>
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
        <Box display='flex' alignItems='center' justifyContent='center' gap='2rem' sx={{ marginTop: '20px' }}>
          <Button variant="contained" onClick={handleSubmit}>Submit</Button>
          <Button variant="contained">Cancel</Button>
        </Box>
      </>

    )
  }

  export default VoterForm
