import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Control, FieldErrors, SubmitHandler, useForm, UseFormHandleSubmit, UseFormRegister } from "react-hook-form";
import { toast } from "react-toastify";
import {
  addCandidate,
  updateCandidateData,
  fetchCandidates,
} from "../../../store/feature/candidate/candidateAPI";
import { AppDispatch, RootState } from "../../../store/app/store";
import PersonalInfo from "./PersonalInfo";
import ElectionDetails from "./ElectionDetails";
import AddressForm from "./AddressForm";
import BankDetails from "./BankDetails";
import UploadDocuments from "./UploadDocuments";
import { FormContent, Heading, ModalFooter } from "../../../style/CandidateFormCss";
import { Box, Button, IconButton } from "@mui/material";
import { FormValues } from "../../../store/feature/election/types";
import { Candidate, IFormInput } from "../../../store/feature/candidate/types";
import { clearCandidate, clearSearchQuery } from "../../../store/feature/candidate/candidateSlice";
import CloseIcon from "@mui/icons-material/Close";
import { Form } from "../../../style/CommanStyle";

interface CandidateFormProps {
  editId: string | undefined;
  register: UseFormRegister<IFormInput>;
  errors: FieldErrors<IFormInput>;
  control: Control<IFormInput>;
  handleSubmit: UseFormHandleSubmit<IFormInput>;
  onSubmit: (data: IFormInput) => void;
  profilePic: string | null;
  signature: string | null;
  onDropProfile: (acceptedFiles: File[]) => void;
  onDropSignature: (acceptedFiles: File[]) => void;
  handleClose: () => void;  // Add handleClose here
  selectedCandidate: Candidate | null;
  actionType: "edit" | "add" | null;
}

const CandidateForm: React.FC<CandidateFormProps> = ({ handleClose}) => {
  const dispatch: AppDispatch = useDispatch();
  const [editId, setEditId] = useState<number | null>(null);
  const [profilePic, setProfilePic] = useState<File | null>(null);
  const [signature, setSignature] = useState<File | null>(null);
  const { candidate } = useSelector((state: RootState) => state.candidate);
  const searchQuery = useSelector((state: RootState) => state.candidate.searchQuery); // ✅ Get it from Redux

  const {
    register,
    handleSubmit,
    reset,
    control,
    setValue,
    formState: { errors },
    watch,
  } = useForm<IFormInput>();

    React.useEffect(() => {
      if (searchQuery) {
        setValue("candidateSSN", searchQuery); // Updates the react-hook-form field value
      }
    }, [searchQuery, setValue]);
  
  useEffect(() => {
    if (candidate && Object.keys(candidate).length > 0) {
      reset({
        ...candidate,
        partyId: Number(candidate.partyId) || "", // Ensure it's a number
      });
    }
  }, [candidate, reset]);

  useEffect(() => {
    if (candidate && Object.keys(candidate).length > 0) {
      setEditId(candidate.candidateId);
      reset({
        ...candidate,
        maritalStatus: candidate.maritialStatus || "", // Fix typo: "maritialStatus" -> "maritalStatus"
      });
  
      // Set existing images (Base64 format)
      setProfilePic(candidate.candidateImage || null);
      setSignature(candidate.candidateSignature || null);
    }
  }, [candidate, reset]);
  

  const handleProfileUpload = (acceptedFiles: File[]) => {
    if (acceptedFiles.length > 0) {
      setProfilePic(acceptedFiles[0]);
    }
  };

  const handleSignatureUpload = (acceptedFiles: File[]) => {
    if (acceptedFiles.length > 0) {
      setSignature(acceptedFiles[0]);
    }
  };

  const defaultValues = {
    candidateName: {
      firstName: "",
      middleName: "",
      lastName: "",
    },
    gender: "",
    dateOfBirth: "",
    partyId: 0, // Change this to a number
    candidateEmail: "",
    candidateSSN: searchQuery,
    maritalStatus: "",
    noOfChildren: undefined,
    spouseName: "",
    stateName: "New York",
    residentialAddress: {
      street: "",
      city: "",
      zipcode: 0,
    },
    mailingAddress: {
      street: "",
      city: "",
      zipcode: 0,
    },
    bankDetails: {
      bankName: "",
      bankAddress: "",
    },
  };

const onSubmit: SubmitHandler<IFormInput> = async (data) => {
  // Validate Profile Picture and Signature
  if (!profilePic) {
    toast.error("Profile picture is required!");
    return;
  }

  if (!signature) {
    toast.error("Signature is required!");
    return;
  }

  // Convert Image Files to Base64
  const toBase64 = (file: File | string): Promise<string> =>
    new Promise((resolve, reject) => {
      if (typeof file === "string") return resolve(file); // If already Base64, return as is
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        const result = reader.result as string;
        // Remove the 'data:image/png;base64,' prefix (or any other image type prefix)
        const base64String = result.split(',')[1];
        resolve(base64String); // Return only the base64 part
      };
      reader.onerror = (error) => reject(error);
    });

  try {
    const base64ProfilePic = await toBase64(profilePic);
    const base64Signature = await toBase64(signature);

    // Remove unnecessary IDs & Prepare Form Data
    const newFormData = {
      candidateName: data.candidateName,
      residentialAddress: {
        street: data.residentialAddress.street,
        city: data.residentialAddress.city,
        zipcode: data.residentialAddress.zipcode,
      },
      mailingAddress: {
        street: data.mailingAddress.street,
        city: data.mailingAddress.city,
        zipcode: data.mailingAddress.zipcode,
      },
      bankDetails: {
        bankName: data.bankDetails.bankName,
        bankAddress: data.bankDetails.bankAddress,
      },
      candidateSSN: data.candidateSSN,
      dateOfBirth: data.dateOfBirth,
      gender: data.gender,
      maritalStatus: data.maritalStatus,
      noOfChildren: data.noOfChildren,
      spouseName: data.spouseName,
      partyId: data.partyId,
      stateName: data.stateName,
      candidateEmail: data.candidateEmail,
      electionId: data.electionId,
      candidateImage: base64ProfilePic, 
      candidateSignature: base64Signature, 
    };

    const updatedData: Partial<Record<keyof IFormInput, any>> = {};
    if (editId && candidate) {
      Object.keys(newFormData).forEach((key) => {
        if (JSON.stringify(newFormData[key as keyof typeof newFormData]) !== JSON.stringify(candidate[key as keyof typeof candidate])) {
          updatedData[key as keyof IFormInput] = newFormData[key as keyof typeof newFormData]; // Only include changed fields
        }
      });
    }

    if (editId && candidate) {
      // Update Candidate
      await dispatch(updateCandidateData({ candidateId: editId.toString(), candidateData: updatedData }));
      toast.success("Candidate updated successfully.");
    } else {
      // Add Candidate
      await dispatch(addCandidate(newFormData)).unwrap();
      toast.success("Candidate added successfully.");
      toast.success("Registration Mail Sent successfully!");
    }

    // Reset Form After Submission
    reset(defaultValues);
    clearSearch();
    setEditId(null);
    setProfilePic(null);
    setSignature(null);
    dispatch(clearCandidate());
    dispatch(fetchCandidates({ page: 0, perPage: 5 }));
    
    // Close the form after submit
    handleClose(); // Close the form after successful submit
  } catch (error) {
    console.error(error);
    toast.error(editId ? "Failed to update candidate." : "Failed to add candidate.");
  }
};

    const handleCancel = () =>{    
      setEditId(null);
      setProfilePic(null);
      setSignature(null);
      dispatch(clearCandidate());
      dispatch(fetchCandidates({ page: 0, perPage: 5 }));
      reset(defaultValues);
      handleClose();
    }

    const clearSearch = () => {
      // dispatch(resetState());
      dispatch(clearSearchQuery());
    };

  return (
    <Form onSubmit={handleSubmit(onSubmit)}>
       <Box position="relative" display="flex" alignItems="center" justifyContent="center">
         <Heading variant="h5" gutterBottom mt="5px">
           {editId ? "EDIT CANDIDATE" : "ADD CANDIDATE"}
         </Heading>
         <IconButton onClick={handleCancel} sx={{ position: "absolute", right: 0 }}>
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
        onDropProfile={handleProfileUpload}
        onDropSignature={handleSignatureUpload}
        editId={editId}
      />
      </FormContent>
      <ModalFooter sx={{mt:"15px"}}>
          <Button variant="contained" type="submit">
          {candidate ? "Update" : "Submit"}
          </Button>
          <Button variant="contained" onClick={handleCancel}>Cancel</Button>
      </ModalFooter>
    </Form>
  );
};

export default CandidateForm;
