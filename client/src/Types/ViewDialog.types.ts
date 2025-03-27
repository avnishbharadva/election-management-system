export interface SectionConfig {
    title: string;
    fields: { label: string; key: string }[];
  }
  
export interface ViewDetailsDialogProps {
    open: boolean;
    handleClose: () => void;
    data: any;
    sections: SectionConfig[];
    imageKey?: string;
    signatureKey?: string; 
    title: string;
    imagelabel : string,
    signaturelabel: string
  }