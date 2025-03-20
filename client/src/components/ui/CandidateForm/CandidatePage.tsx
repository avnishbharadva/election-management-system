import React, { useState, useCallback, useEffect } from "react";
import { useForm } from "react-hook-form";
import CandidateForm from "./CandidateForm";
import { CandidateContainerProps, defaultValues } from "../../../store/feature/candidate/types";

const CandidateContainer: React.FC<CandidateContainerProps> = ({ handleClose, selectedCandidate}) => {
  const [profilePic, setProfilePic] = useState<File | null>(null);
  const [signature, setSignature] = useState<File | null>(null);

  

  const { register, handleSubmit, control, formState: { errors }, reset } = useForm({
    defaultValues: selectedCandidate ? selectedCandidate : defaultValues,
  });

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

  return (
    <CandidateForm
      register={register}
      errors={errors}
      control={control}
      handleSubmit={handleSubmit(onsubmit)}
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
