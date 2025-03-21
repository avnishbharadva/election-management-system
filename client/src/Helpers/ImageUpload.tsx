import React, { useCallback, useState } from "react";
import { Box, Typography } from "@mui/material";
import { useDropzone } from "react-dropzone";
import { DropzoneContainer } from '../style/CandidateFormCss';
 
 
interface ImageUploadProps {
  label: string;
  onImageUpload: (imageData: string) => void;
  imagePreview: string | null;
  borderRadius?: string
}
 
const ImageUpload: React.FC<ImageUploadProps> = ({ label, onImageUpload, imagePreview, borderRadius = "50%" }) => {
  const [alert, setAlert]= useState("")
  const MAX_IMG_SIZE = 1024 * 1024 * 1; // 1MB
  const acceptedFileTypes = [
    'image/jpeg',
    'image/png',
    'image/jpg',
  ]
 
  const onDrop = useCallback((acceptedFiles: File[]) => {
    const file = acceptedFiles[0];
 
    if (file.size > MAX_IMG_SIZE) {
      setAlert(`File size exceeds the maximum limit of 1MB`);
      return;
    }
    if (!acceptedFileTypes.includes(file.type)) {
      setAlert('Please upload a valid image file (JPEG, PNG, SVG)');
      return;
    }
    setAlert("")
    const reader = new FileReader();
    reader.onload = () => onImageUpload(reader.result as string)
    reader.readAsDataURL(file);
  }, [onImageUpload]);
 
  const { getRootProps, getInputProps } = useDropzone({ onDrop });
 
  const imagePreviewUrl = imagePreview ? imagePreview.replace(/^data:image\/(png|jpeg|svg\+xml);base64,/, '') : null;
 
  return (
    <Box>
      <Typography variant="subtitle1">{label}</Typography>
     
      <DropzoneContainer {...getRootProps()}>
        <input {...getInputProps()} />
        {imagePreview ? (
          <img src={`data:image/png/jpeg/jpg;base64,${imagePreviewUrl}`} alt={label} width="100" height="100" style={{ borderRadius: borderRadius }}  />
        ) : (
          <Typography>Drag & Drop or Click to upload {label}</Typography>
        )}  
      </DropzoneContainer>
      <Typography color="error"> {alert} </Typography>
    </Box>
  );
};
 
export default ImageUpload;
 











// import React, { useCallback, useEffect, useState } from "react";
// import { Box, Typography } from "@mui/material";
// import { useDropzone } from "react-dropzone";
// import { DropzoneContainer } from '../style/CandidateFormCss';
 
// interface ImageUploadProps {
//   label: string; // The label for the upload (e.g., "Profile Picture", "Signature")
//   onImageUpload: (imageData: string) => void; // Callback function to handle the uploaded image data
//   imagePreview: string | null; // The image preview (to display the uploaded image)
//   borderRadius?: string
// }
 
// const ImageUpload: React.FC<ImageUploadProps> = ({ label, onImageUpload, imagePreview, borderRadius = "50%" }) => {
//   const onDrop = useCallback((acceptedFiles: File[]) => {
//     const file = acceptedFiles[0];
//     const reader = new FileReader();
//     reader.onload = () => onImageUpload(reader.result as string)
//     reader.readAsDataURL(file);
//   }, [onImageUpload]);
 
//   useEffect(() => {},[imagePreview]);

//   const { getRootProps, getInputProps } = useDropzone({ onDrop });
//   // console.log(imagePreview);

//   return (
//     <Box>
//       <Typography variant="subtitle1">{label}</Typography>
//       <DropzoneContainer {...getRootProps()}>
//         <input {...getInputProps()} />
//         {imagePreview ? (
//           <img src={`data:image/png;base64,${imagePreview}`} alt={label} width="100" height="100" style={{ borderRadius: borderRadius }} />
//         ) : (
//           <Typography>Drag & Drop or Click to upload {label}</Typography>
//         )}
//       </DropzoneContainer>
//     </Box>
//   );
// };
 
// export default ImageUpload;