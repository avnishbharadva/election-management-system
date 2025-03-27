export const viewVoter = [
    {
        title: 'Personal Details',
        fields: [
            { label: 'voterId', key: 'voterId' },
            { label: 'SSN Number', key: 'ssnNumber' },
            { label: 'DMV Number', key: 'dmvNumber' },
            { label: 'First Name', key: 'firstName' },
            { label: 'Middle Name', key: 'middleName' },
            { label: 'Last Name', key: 'lastName' },
            { label: "Suffix Name", key: "suffixName" },
            { label: 'Date of Birth', key: 'dateOfBirth' },
            { label: "Gender", key: "gender" },
        ]
    },
    {
        title: 'Contact Details',
        fields: [
            { label: 'Email', key: 'email' },
            { label: 'Phone', key: 'phoneNumber' },
        ]
    },
    {
        title: 'voting details',
        fields: [
            { label: "Status", key: "status" },
            { label: "HasVotedBefore", key: "hasVotedBefore" }
        ]
    },
    {
        title: 'ResidentialAddress',
        fields: [
            { label: "AddressLine", key: "residentialAddress.addressLine" },
            { label: "City", key: "residentialAddress.city" },
            { label: "State", key: "residentialAddress.state" },
            { label: "County", key: "residentialAddress.county" },
            { label: "Town", key: "residentialAddress.town" },
            { label: "aptNumber", key: "residentialAddress.aptNumber" },
            { label: "ZipCode", key: "residentialAddress.zipCode" }
        ]
    },
    {
        title: 'MailingAddress',
        fields: [
            { label: "AddressLine", key: "mailingAddress.addressLine" },
            { label: "City", key: "mailingAddress.city" },
            { label: "State", key: "mailingAddress.state" },
            { label: "County", key: "mailingAddress.county" },
            { label: "Town", key: "mailingAddress.town" },
            { label: "aptNumber", key: "mailingAddress.aptNumber" },
            { label: "ZipCode", key: "mailingAddress.zipCode" }
        ]
    },
];


export const voterTableHeader =[
    { id: 'status', label: 'Status' },
    { id: 'ssn', label: 'SSN' },
    { id: 'dmv', label: 'DMV' },
    { id: 'firstName', label: 'First Name' },
    { id: 'middleName', label: 'Middle Name' },
    { id: 'lastName', label: 'Last Name' },
    { id: 'gender', label: 'Gender' },
    { id: 'dob', label: 'DOB' },
    { id: 'emailId', label: 'Email Id' },
    { id: 'action', label: 'Action' },
]