import React, { useState, useCallback, useEffect } from "react";
import { useForm } from "react-hook-form";
import CandidateForm from "./CandidateForm";
import { CandidateContainerProps, defaultValues } from "../../../store/feature/candidate/types";

const CandidateContainer: React.FC<CandidateContainerProps> = ({ handleClose, selectedCandidate }) => {
  const [profilePic, setProfilePic] = useState<File | null>(null);
  const [signature, setSignature] = useState<File | null>(null);
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
        electionId: typeof selectedCandidate.electionId === "number" ? selectedCandidate.electionId : undefined,
        electionName: selectedCandidate.electionName || "",
        partyId: typeof selectedCandidate.partyId === "number" ? selectedCandidate.partyId : undefined,
        partyName: selectedCandidate.partyName || "",
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
