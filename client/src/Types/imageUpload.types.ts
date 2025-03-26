export interface ImageUploadProps {
  label: string; 
  onImageUpload: (imageData: string) => void; 
  imagePreview: string | null;
  borderRadius?: string
}