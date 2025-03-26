export type Address = {
    addressLine: string;
    aptNumber: string;
    city: string;
    county: string;
    town:string;
    state:string;
    zipCode: number | null;
    addressType: "RESIDENTIAL" | "MAILING"
}


export type FormData = {
    voterId?:number
    firstName: string;
    middleName: string;
    lastName: string;
    suffixName: string;
    email: string;
    ssnNumber: number | null;
    phoneNumber: number | null;
    dmvNumber: number | null;
    gender: string;
    partyId?: number| null;
    dateOfBirth: string;
    firstVotedYear?: number| null;
    hasVotedBefore: boolean;
    residentialAddress: Address;
    mailingAddress?: Address;
    statusId?: number | null;
    image?:string;
    status?:string;
    party?:string;
    signature?:string
  };

