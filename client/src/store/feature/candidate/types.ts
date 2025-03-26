import { Control, FieldError, FieldErrors, UseFormRegister, UseFormSetValue, UseFormWatch } from "react-hook-form";

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
  candidateId: number;  
  candidateName: CandidateName;
  candidateSSN: string;
  dateOfBirth: string;
  gender: "MALE" | "FEMALE";
  maritalStatus: "SINGLE" | "MARRIED" | "DIVORCED" | "WIDOWED";
  noOfChildren: number;
  spouseName?: string;
  partyId: number;
  partyName: string;
  residentialAddress: Address;
  mailingAddress: Address;
  stateName: string;
  candidateEmail: string;
  electionId: number;
  electionName: string;
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

export interface AddressFormProps {
  register: UseFormRegister<IFormInput>;
  errors: FieldErrors<IFormInput>;
  watch: UseFormWatch<IFormInput>;
  setValue: UseFormSetValue<IFormInput>;
}

export interface BankDetailsProps {
  register: UseFormRegister<IFormInput>;
  errors: FieldErrors<IFormInput>;
}

export interface CandidateContainerProps {
  handleClose: () => void;
  selectedCandidate: Candidate | null;
  actionType: "edit" | "add" | null;
}

export interface CandidateNameErrors {
  firstName?: FieldError;
  middleName?: FieldError;
  lastName?: FieldError;
}

export interface PersonalInfoProps {
  register: UseFormRegister<any>;
  errors: FieldErrors & {
    candidateName?: CandidateNameErrors;
  };
  control: Control<any>;
  editId?: string | null;
  candidate?: any;
  searchQuery?: string; 
  watch: (name: string) => any;
}

export interface UploadDocumentsProps {
  profilePic: File | string | null;
  signature: File | string | null;
  onDropProfile: (acceptedFiles: File[]) => void;
  onDropSignature: (acceptedFiles: File[]) => void;
  editId: number | null;
}
