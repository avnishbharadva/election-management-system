export interface UpdateDialogProps {
    open: boolean;
    title?: string;
    handleClose: () => void;
    handleConfirm: (data: Record<string, any>) => void;
    originalData: Record<string, any>;
    updatedData: Record<string, any>;
  }
   