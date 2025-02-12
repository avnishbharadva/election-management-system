interface Address {
    addressId: number;
    street: string;
    city: string;
    zipcode: number;
  }
  
  interface CandidateName {
    firstName: string;
    middleName?: string;
    lastName: string;
  }
  
  interface BankDetails {
    bankDetailsId: number;
    bankName: string;
    bankAddress: string;
  }
  
  export interface Candidate {
    candidateId: number;  // ✅ Added candidateId field
    candidateName: CandidateName;
    candidateSSN: string;
    dateOfBirth: string;
    gender: "MALE" | "FEMALE" | "OTHER";
    maritialStatus: "SINGLE" | "MARRIED" | "DIVORCED" | "WIDOWED";
    noOfChildren: number;
    spouseName?: string;
    partyId: number;
    residentialAddress: Address;
    mailingAddress: Address;
    stateName: string;
    candidateEmail: string;
    electionId: number;
    bankDetails: BankDetails;
    candidateSignature?: string | null;  // ✅ Optional candidateSignature
    candidateImage?: string | null;      // ✅ Optional candidateImage
  }
  
//  export interface CandidateState {
//     searchQuery: string; // ✅ Added searchQuery inside candidate state
//     candidate: Candidate | null;
//     loading: boolean;
//     error: string | null;
//   }
  
  export interface CandidateState {
    searchQuery: string;
    allCandidates: any[]; // Store all candidates
    filteredCandidate: any | null; // Store searched candidate
    notFound: boolean;
    loading: boolean;
    error: string | null;
  }
  
//   const initialState: CandidateState = {
//     searchQuery: "",
//     allCandidates: [],
//     filteredCandidate: null,
//     notFound: false,
//     loading: false,
//     error: null,
//   };