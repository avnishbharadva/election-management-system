import React, { useCallback, useEffect, useState } from "react";
import { Box, Typography } from "@mui/material";
import { useDropzone } from "react-dropzone";
import { DropzoneContainer } from '../style/CandidateFormCss';
 
interface ImageUploadProps {
  label: string; // The label for the upload (e.g., "Profile Picture", "Signature")
  onImageUpload: (imageData: string) => void; // Callback function to handle the uploaded image data
  imagePreview: string | null; // The image preview (to display the uploaded image)
  borderRadius?: string
}
 
const ImageUpload: React.FC<ImageUploadProps> = ({ label, onImageUpload, imagePreview, borderRadius = "50%" }) => {
  const onDrop = useCallback((acceptedFiles: File[]) => {
    const file = acceptedFiles[0];
    const reader = new FileReader();
    reader.onload = () => onImageUpload(reader.result as string)
    reader.readAsDataURL(file);
  }, [onImageUpload]);
 
  useEffect(() => {},[imagePreview]);

  const { getRootProps, getInputProps } = useDropzone({ onDrop });
  console.log(imagePreview);

  return (
    <Box>
      <Typography variant="subtitle1">{label}</Typography>
      <DropzoneContainer {...getRootProps()}>
        <input {...getInputProps()} />
        {imagePreview ? (
          <img src={imagePreview} alt={label} width="100" height="100" style={{ borderRadius: borderRadius }} />
        ) : (
          <Typography>Drag & Drop or Click to upload {label}</Typography>
        )}
      </DropzoneContainer>
    </Box>
  );
};
 
export default ImageUpload;