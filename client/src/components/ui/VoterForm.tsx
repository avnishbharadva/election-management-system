import Address, { AddressLine, AptNumber, City, Country, Pincode } from '../ui/voter/AddressDetails'
// import Model from '../ui/Model'
import Voterdetails, { FirstVoterYear, Gender } from './voter/VoterDetails'
import { NameField } from './voter/VoterDetails'
import { Email } from './voter/VoterDetails'
import {VoterNumber} from './voter/VoterDetails'
import { DateOfBirth } from './voter/VoterDetails'
import { HasVotedBefore } from './voter/VoterDetails'
import { Title, Section, DividerStyle } from '../../style/CandidateFormCss'

const VoterForm = () => {
  return (
        <>
        <Title variant='h5' align='center' gutterBottom>VOTER REGISTRATION</Title>
        <Voterdetails>
          <Section>
              <Title variant='h6'>Personal Information</Title>
              <DividerStyle />
              <NameField id="FirstName" />
              <NameField id="MiddleName" />
              <NameField id="LastName" />
              <DateOfBirth id="DateOfBirth"/>
              <Email id="Email" />
              <VoterNumber id="Phone Number"/>
              <Gender /> 
              <VoterNumber id="SSN Number" />
              <VoterNumber id="DMV Number" />
            </Section> 
            <Section>
              <Title variant='h6'>Voting Information</Title>
              <HasVotedBefore/>
              <FirstVoterYear id="First Vote Year" />
            </Section>
        </Voterdetails>
       <Address>
                <Title variant='h6'>Residential Address</Title>
                <AddressLine id="Addressline"/>
                <AptNumber id="AptNumber"/>
                <City id="City" />
                <Country id="Country"/>
                <Pincode id="Pincode"/>
        </Address>
        <Address>
                <Title variant='h6'>Mailing Address</Title>
                <AddressLine id="Addressline"/>
                <AptNumber id="AptNumber"/>
                <City id="City" />
                <Country id="Country"/>
                <Pincode id="Pincode"/>
        </Address>
        </>
    
  )
}

export default VoterForm
