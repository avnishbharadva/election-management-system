export interface Address {
    addressLine: string;
    aptNumber: string;
    city: string;
    county: string;
    zipCode: number | string;
    addressType: "RESIDENTIAL" | "MAILING"
}

export interface Voter {
status:number
voterId?: number;
firstName: string;
middleName: string;
lastName: string;
suffixName: string;
email: string;
ssnNumber: string;
phoneNumber: number;
dmvNumber: number;
gender: string;
partyId: number;
dateOfBirth: string;
firstVoterYear: number;
hasVotedBefore: boolean;
residentialAddress: Address;
mailingAddress?: Address;
}

export interface VoterState {
allVoters: Voter[];
loading: boolean;
error: string | null;
success: boolean;
selectedVoter: Voter | null;
totalCount: number;
currentPage: number;
searchParams: {
    ssnNumber: string | null;
};
}