import { useEffect, useState } from "react";
import {  Box, Typography } from "@mui/material";
import { useDropzone } from "react-dropzone";
import {
  Section,
  Title,
  DividerStyle,
  DropzoneContainer,
  FlexCenter,
} from "../../../style/CandidateFormCss";
import { UploadDocumentsProps } from "../../../store/feature/candidate/types";

const UploadDocuments: React.FC<UploadDocumentsProps> = ({
  profilePic,
  signature,
  onDropProfile,
  onDropSignature,
}) => {
  const [profilePicPreview, setProfilePicPreview] = useState<string | null>(null);
  const [signaturePreview, setSignaturePreview] = useState<string | null>(null);

  useEffect(() => {
    if (profilePic instanceof File) {
      setProfilePicPreview(URL.createObjectURL(profilePic));
    } else if (typeof profilePic === "string") {
      setProfilePicPreview(`data:image/png;base64,${profilePic}`);
    } else {
      setProfilePicPreview(null);
    }

    if (signature instanceof File) {
      setSignaturePreview(URL.createObjectURL(signature));
    } else if (typeof signature === "string") {
      setSignaturePreview(`data:image/png;base64,${signature}`);
    } else {
      setSignaturePreview(null);
    }

    return () => {
      if (profilePicPreview) URL.revokeObjectURL(profilePicPreview);
      if (signaturePreview) URL.revokeObjectURL(signaturePreview);
    };
  }, [profilePic, signature]);

  const { getRootProps: getProfileProps, getInputProps: getProfileInputProps } = useDropzone({
    onDrop: onDropProfile,
    accept: { "image/*": [] },
  });

  const { getRootProps: getSignatureProps, getInputProps: getSignatureInputProps } = useDropzone({
    onDrop: onDropSignature,
    accept: { "image/*": [] },
  });

  return (
    <Section>
      <Title variant="h6">Upload Documents</Title>
      <DividerStyle />
      <FlexCenter>
        <Box>
          <Typography variant="subtitle1">Candidate Image</Typography>
          <DropzoneContainer {...getProfileProps()}>
            <input {...getProfileInputProps()} />
            {profilePicPreview ? (
              <img
                src={profilePicPreview}
                alt="Profile"
                width="100"
                height="100"
                style={{ borderRadius: "50%" }}
              />
            ) : (
              <Typography>Drag & Drop or Click</Typography>
            )}
          </DropzoneContainer>
        </Box>

        <Box>
          <Typography variant="subtitle1">Signature</Typography>
          <DropzoneContainer {...getSignatureProps()}>
            <input {...getSignatureInputProps()} />
            {signaturePreview ? (
              <img src={signaturePreview} alt="Signature" width="200" height="50" />
            ) : (
              <Typography>Drag & Drop or Click</Typography>
            )}
          </DropzoneContainer>
        </Box>
      </FlexCenter>
    </Section>
  );
};

export default UploadDocuments;
