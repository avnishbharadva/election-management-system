import React, { useState, useCallback, useEffect } from "react";
import { useForm } from "react-hook-form";
import CandidateForm from "./CandidateForm";
import { CandidateContainerProps } from "../../../store/feature/candidate/types";

const CandidateContainer: React.FC<CandidateContainerProps> = ({ handleClose, selectedCandidate }) => {
  const [profilePic, setProfilePic] = useState<File | null>(null);
  const [signature, setSignature] = useState<File | null>(null);

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

  const fileToBase64 = (file: File): Promise<string> => {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result as string);
      reader.onerror = (error) => reject(error);
    });
  };

  const onDropProfile = useCallback((acceptedFiles: File[]) => {
    if (acceptedFiles.length > 0) {
      setProfilePic(acceptedFiles[0]);
    }
  }, []);

  const onDropSignature = useCallback((acceptedFiles: File[]) => {
    if (acceptedFiles.length > 0) {
      setSignature(acceptedFiles[0]);
    }
  }, []);

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

  const onSubmit = async (data: any) => {
    const formData = { ...data };

    if (profilePic) {
      formData.candidateImage = await fileToBase64(profilePic);
    }
    if (signature) {
      formData.candidateSignature = await fileToBase64(signature);
    }
  };

  return (
    <CandidateForm
      register={register}
      errors={errors}
      control={control}
      handleSubmit={handleSubmit(onSubmit)}
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
