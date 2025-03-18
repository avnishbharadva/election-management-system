// import React, { useState, useCallback, useEffect } from "react";
// import { useForm } from "react-hook-form";
// import CandidateForm from "./CandidateForm";
// import { Candidate } from "../../../store/feature/candidate/types";

// interface CandidateContainerProps {
//   handleClose: () => void;
//   selectedCandidate: Candidate | null;
//   actionType: "edit" | "add" | null;
// }

// const CandidateContainer: React.FC<CandidateContainerProps> = ({ handleClose, selectedCandidate }) => {

//   const [editId, setEditId] = useState<string | undefined>(undefined);
//   const [profilePic, setProfilePic] = useState<string | null>(null);
//   const [signature, setSignature] = useState<string | null>(null);

//   const defaultValues = {
//     candidateName: { firstName: "", middleName: "", lastName: "" },
//     gender: "",
//     dateOfBirth: "",
//     partyId: 0,
//     electionId: 0,
//     candidateEmail: "",
//     maritalStatus: "",
//     noOfChildren: 0,
//     spouseName: "",
//     stateName: "New York",
//     residentialAddress: { street: "", city: "", zipcode: 0 },
//     mailingAddress: { street: "", city: "", zipcode: 0 },
//     bankDetails: { bankName: "", bankAddress: "" },
//     candidateSSN: "",
//     candidateImage: null,
//     candidateSignature: null,
//   };

//   const { register, handleSubmit, reset, control, formState: { errors } } = useForm({
//     defaultValues
//   });

//   const onDropProfile = useCallback((acceptedFiles: File[]) => {
//     const file = acceptedFiles[0];
//     if (file) {
//       const reader = new FileReader();
//       reader.onload = () => setProfilePic(reader.result as string);
//       reader.readAsDataURL(file);
//     }
//   }, []);

//   const onDropSignature = useCallback((acceptedFiles: File[]) => {
//     const file = acceptedFiles[0];
//     if (file) {
//       const reader = new FileReader();
//       reader.onload = () => setSignature(reader.result as string);
//       reader.readAsDataURL(file);
//     }
//   }, []);


//   useEffect(() => {
//     if (selectedCandidate) {
//       setEditId(selectedCandidate.candidateId.toString());
//       reset({
//         candidateName: selectedCandidate.candidateName || defaultValues.candidateName,
//         gender: selectedCandidate.gender || "",
//         dateOfBirth: selectedCandidate.dateOfBirth || "",
//         partyId: selectedCandidate.partyId || 0,
//         candidateEmail: selectedCandidate.candidateEmail || "",
//         candidateSSN: selectedCandidate.candidateSSN || "",
//         maritalStatus: selectedCandidate.maritalStatus || "",
//         noOfChildren: selectedCandidate.noOfChildren || 0,
//         spouseName: selectedCandidate.spouseName || "",
//         stateName: selectedCandidate.stateName || "",
//         residentialAddress: selectedCandidate.residentialAddress || defaultValues.residentialAddress,
//         mailingAddress: selectedCandidate.mailingAddress || defaultValues.mailingAddress,
//         bankDetails: selectedCandidate.bankDetails || defaultValues.bankDetails,
//       });
//       setProfilePic(selectedCandidate.candidateImage || null);
//       setSignature(selectedCandidate.candidateSignature || null);
//     }
//   }, [selectedCandidate, reset]);

//   return (
//     <CandidateForm
//       editId={editId ?? undefined}
//       register={register}
//       errors={errors}
//       control={control}
//       handleSubmit={handleSubmit}
//       profilePic={profilePic}
//       signature={signature}
//       onDropProfile={onDropProfile}
//       onDropSignature={onDropSignature}
//       handleClose={handleClose}  // Pass handleClose here
//       selectedCandidate={selectedCandidate ?? null}
//       actionType={editId ? "edit" : "add"}
//     />
//   );
// };

// export default CandidateContainer;

import React, { useState, useCallback, useEffect } from "react";
import { useForm } from "react-hook-form";
import CandidateForm from "./CandidateForm";
import { Candidate } from "../../../store/feature/candidate/types";

interface CandidateContainerProps {
  handleClose: () => void;
  selectedCandidate: Candidate | null;
  actionType: "edit" | "add" | null;
}

const CandidateContainer: React.FC<CandidateContainerProps> = ({ handleClose, selectedCandidate }) => {
  const [profilePic, setProfilePic] = useState<File | null>(null);
  const [signature, setSignature] = useState<File | null>(null);

  // Default form values
  const defaultValues = {
    candidateName: { firstName: "", middleName: "", lastName: "" },
    gender: "",
    dateOfBirth: "",
    partyId: 0,
    electionId: 0,
    candidateEmail: "",
    maritalStatus: "",
    noOfChildren: 0,
    spouseName: "",
    stateName: "New York",
    residentialAddress: { street: "", city: "", zipcode: "" },
    mailingAddress: { street: "", city: "", zipcode: "" },
    bankDetails: { bankName: "", bankAddress: "" },
    candidateSSN: "",
    candidateImage: "",
    candidateSignature: "",
  };

  const { register, handleSubmit, control, formState: { errors }, reset } = useForm({
    defaultValues: selectedCandidate ? selectedCandidate : defaultValues,
  });

  // Convert File to Base64
  const fileToBase64 = (file: File): Promise<string> => {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result as string);
      reader.onerror = (error) => reject(error);
    });
  };

  // Handle file upload for profile picture
  const onDropProfile = useCallback((acceptedFiles: File[]) => {
    if (acceptedFiles.length > 0) {
      setProfilePic(acceptedFiles[0]);
    }
  }, []);

  // Handle file upload for signature
  const onDropSignature = useCallback((acceptedFiles: File[]) => {
    if (acceptedFiles.length > 0) {
      setSignature(acceptedFiles[0]);
    }
  }, []);

  // Update form values when `selectedCandidate` changes
  useEffect(() => {
    if (selectedCandidate) {
      reset({
        ...selectedCandidate,
        candidateImage: selectedCandidate.candidateImage || "",
        candidateSignature: selectedCandidate.candidateSignature || "",
      });
      setProfilePic(null);
      setSignature(null);
    }
  }, [selectedCandidate, reset]);

  // Handle form submission
  const onSubmit = async (data: any) => {
    const formData = { ...data };

    // Convert images to Base64 if uploaded
    if (profilePic) {
      formData.candidateImage = await fileToBase64(profilePic);
    }
    if (signature) {
      formData.candidateSignature = await fileToBase64(signature);
    }

    console.log("Final form data:", formData);
    // Send `formData` to your backend API here
  };

  return (
    <CandidateForm
      register={register}
      errors={errors}
      control={control}
      handleSubmit={handleSubmit(onSubmit)} // Attach the updated submit function
      profilePic={profilePic}
      signature={signature}
      onDropProfile={onDropProfile}
      onDropSignature={onDropSignature}
      handleClose={handleClose}
      selectedCandidate={selectedCandidate}
      actionType={selectedCandidate ? "edit" : "add"}
    />
  );
};

export default CandidateContainer;
