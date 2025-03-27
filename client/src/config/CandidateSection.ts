export const candidateSections = [
    {
      title: "Personal Information",
      fields: [
        { label: "Candidate ID", key: "candidateId" },
        { label: "SSN", key: "candidateSSN" },
        { label: "First Name", key: "candidateName.firstName" },
        { label: "Middle Name", key: "candidateName.middleName" },
        { label: "Last Name", key: "candidateName.lastName" },
        { label: "Email", key: "candidateEmail" },
        { label: "Gender", key: "gender" },
        { label: "Date of Birth", key: "dateOfBirth" },
        { label: "Marital Status", key: "maritalStatus" },
        { label: "Spouse Name", key: "spouseName" },
        { label: "Number of Children", key: "noOfChildren" },
        { label: "State", key: "stateName" }
      ],
    },
    {
      title: "Election Details",
      fields: [
        { label: "Election Name", key: "electionName" },
        { label: "Party Name", key: "partyName" }
      ],
    },
    {
      title: "Address Details",
      fields: [
        { label: "Mailing Address", key: "mailingAddress.street" },
        { label: "Mailing City", key: "mailingAddress.city" },
        { label: "Mailing Zipcode", key: "mailingAddress.zipcode" },
        { label: "Residential Address", key: "residentialAddress.street" },
        { label: "Residential City", key: "residentialAddress.city" },
        { label: "Residential Zipcode", key: "residentialAddress.zipcode" }
      ],
    },
    {
      title: "Bank Details",
      fields: [
        { label: "Bank Name", key: "bankDetails.bankName" },
        { label: "Bank Address", key: "bankDetails.bankAddress" }
      ],
    }
  ];
  