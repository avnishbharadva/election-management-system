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
  maritalStatus: "SINGLE" | "MARRIED" | "DIVORCED" | "WIDOWED";
  noOfChildren: number;
  spouseName?: string;
  partyId: number;
  residentialAddress: Address;
  mailingAddress: Address;
  stateName: string;
  candidateEmail: string;
  electionId: number;
  bankDetails: BankDetails;
  candidateSignature?: string | null; 
  candidateImage?: string | null;      
}

export interface CandidateState {
  searchQuery: string;
  allCandidates: any[]; 
  filteredCandidate: any | null; 
  notFound: boolean;
  loading: boolean;
  error: string | null;
  success: boolean;
  candidate: any | null;
  searchedSSN:string | null;
  currentPage: number;
  totalPages:number;
  totalRecords:number;
  perPage:5;
  sortBy: string;
  sortDir: string;
  }

export interface IFormInput {
candidateId?: number;
candidateName: CandidateName;
candidateSSN: string;
dateOfBirth: string;
gender: string;
maritalStatus: string;
noOfChildren: number;
spouseName: string;
partyId: number;
residentialAddress: Address;
mailingAddress: Address;
stateName: string;
candidateEmail: string;
electionId: number;
bankDetails: BankDetails;
candidateImage: string;
candidateSignature: string;
sameMailingAddress: boolean;
}

export interface ModalData {
    open: boolean;
    actionType: "edit" | "add" | null;
    selectedCandidate: Candidate | null;
  }

  const defaultValues = {
  candidateName: {
    firstName: null,
    middleName: null,
    lastName: null,
  },
  gender: "",
  dateOfBirth: "",
  partyId: "",
  candidateEmail: "",
  candidateSSN: searchQuery, // Preserving searchQuery
  maritalStatus: "",
  noOfChildren: null,
  spouseName: "",
  stateName: "New York",
  residentialAddress: {
    street: "",
    city: "",
    zipcode: "",
  },
  mailingAddress: {
    street: "",
    city: "",
    zipcode: "",
  },
  bankDetails: {
    bankName: "",
    bankAddress: "",
  },
};