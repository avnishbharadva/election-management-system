import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { SubmitHandler, useForm } from "react-hook-form";
import { toast } from "react-toastify";
import { addCandidate, updateCandidateData, fetchCandidates } from "../../../store/feature/candidate/candidateAPI";
import { AppDispatch, RootState } from "../../../store/app/store";
import PersonalInfo from "./PersonalInfo";
import ElectionDetails from "./ElectionDetails";
import AddressForm from "./AddressForm";
import BankDetails from "./BankDetails";
import UploadDocuments from "./UploadDocuments";
import { FormContent, Heading, ModalFooter } from "../../../style/CandidateFormCss";
import { Box, Button, IconButton } from "@mui/material";
import { defaultValues, IFormInput } from "../../../store/feature/candidate/types";
import { clearCandidate, clearSearchQuery } from "../../../store/feature/candidate/candidateSlice";
import CloseIcon from "@mui/icons-material/Close";
import UpdateDialog from "../UpdateDialog";

const CandidateForm: React.FC<{ handleClose: () => void }> = ({ handleClose }) => {
  const dispatch: AppDispatch = useDispatch();
  const { candidate, searchQuery } = useSelector((state: RootState) => state.candidate);
  const [editId, setEditId] = useState<number | null>(null);
  const [profilePic, setProfilePic] = useState<File | null>(null);
  const [signature, setSignature] = useState<File | null>(null);
  const [updatedData, setUpdatedData] = useState<Record<string, any>>({});
  const [confirmDialogOpen, setConfirmDialogOpen] = useState(false);
console.log("hiii"+editId)
  const {
    register,
    handleSubmit,
    reset,
    control,
    setValue,
    formState: { errors },
    watch,
  } = useForm<IFormInput>();

  useEffect(() => {
    if (searchQuery) setValue("candidateSSN", searchQuery);
  }, [searchQuery, setValue]);
  useEffect(() => {
    if (candidate && Object.keys(candidate).length > 0) {
      setEditId(candidate.candidateId);
      reset({
        ...candidate,
        electionName: candidate.electionName
      })
      setProfilePic(candidate.candidateImage || null);
      setSignature(candidate.candidateSignature || null);
    }
  }, [candidate, reset]);

  const handleFileUpload = (setter: React.Dispatch<React.SetStateAction<File | null>>) => (acceptedFiles: File[]) => {
    if (acceptedFiles.length > 0) setter(acceptedFiles[0]);
  };

  const toBase64 = (file: File | string): Promise<string> =>
    new Promise((resolve, reject) => {
      if (typeof file === "string") return resolve(file);
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result?.toString().split(",")[1] || "");
      reader.onerror = (error) => reject(error);
    });

  const compareCandidateData = (oldData: any, newData: any) => {
    let diff: Record<string, any> = {};
  
    Object.keys(newData).forEach((key) => {
      const val1 = oldData?.[key];
      let val2 = newData[key];
      if (typeof val2 === "object" && val2 !== null) {
        val2 = { ...val2 }; 
        delete val2.bankDetailsId;
        delete val2.addressId;
      }
  
      if (JSON.stringify(val1) !== JSON.stringify(val2)) {
        diff[key] = val2;
      }
    });
  
    return diff;
  };
  
  const onSubmit: SubmitHandler<IFormInput> = async (data) => {
    if (!profilePic || !signature) {
      toast.error("Profile picture and Signature are required!");
      return;
    }
  
    try {
      const base64ProfilePic = await toBase64(profilePic);
      const base64Signature = await toBase64(signature);
  
      const newFormData: Partial<IFormInput> = {
        ...data,
        candidateImage: base64ProfilePic,
        candidateSignature: base64Signature,
      };
  
      if (editId) {
        const updatedFields = compareCandidateData(candidate, newFormData);
        
        if (Object.keys(updatedFields).length > 0) {
          setUpdatedData(newFormData); 
          setConfirmDialogOpen(true); 
        } else {
          toast.info("No changes detected.");
        }
      } else {
        await dispatch(addCandidate(newFormData)).unwrap();
        dispatch(fetchCandidates({ page: 0, perPage: 5 }));
        resetForm();
      }
    } catch (error) {
      toast.error("Failed to submit candidate details.");
    }
  };
  
  const handleConfirmUpdate = async () => {
    if (editId && updatedData) {
      const filteredData = compareCandidateData(candidate, updatedData); 
      if (Object.keys(filteredData).length === 0) {
        toast.info("No changes detected.");
        return;
      } 
      setConfirmDialogOpen(false);   
      try {
        await dispatch(updateCandidateData({ candidateId: editId, candidateData: filteredData })).unwrap();
        dispatch(fetchCandidates({ page: 0, perPage: 5 }));
        
        resetForm();
      } catch (error) {
        toast.error("Failed to update candidate details.");
      }
    }
  };
   
  const resetForm = () => {
    reset(defaultValues);
    setEditId(null);
    setProfilePic(null);
    setSignature(null);
    dispatch(clearSearchQuery());
    dispatch(clearCandidate());
    handleClose();
  };
  

  return (
    <>
      <form onSubmit={handleSubmit(onSubmit)}>
        <Box position="relative" display="flex" alignItems="center" justifyContent="center">
          <Heading variant="h5">{editId ? "EDIT CANDIDATE" : "ADD CANDIDATE"}</Heading>
          <IconButton onClick={resetForm} sx={{ position: "absolute", right: 0 }}>
            <CloseIcon />
          </IconButton>
        </Box>
        <FormContent>
          <PersonalInfo register={register} errors={errors} control={control} watch={watch} />
          <ElectionDetails control={control} errors={errors} register={register} />
          <AddressForm register={register} errors={errors} watch={watch} setValue={setValue} />
          <BankDetails register={register} errors={errors} />
          <UploadDocuments
            profilePic={profilePic}
            signature={signature}
            onDropProfile={handleFileUpload(setProfilePic)}
            onDropSignature={handleFileUpload(setSignature)}
            editId={editId}
          />
        </FormContent>
        <ModalFooter>
          <Button variant="contained" type="submit">{editId ? "Update" : "Submit"}</Button>
          <Button variant="contained" onClick={resetForm}>Cancel</Button>
        </ModalFooter>
      </form>

      <UpdateDialog
        open={confirmDialogOpen}
        title="Confirm Candidate Updates"
        originalData={candidate}
        updatedData={updatedData}
        handleClose={() => setConfirmDialogOpen(false)}
        handleConfirm={handleConfirmUpdate}
      />
    </>
  );
};

export default CandidateForm;
