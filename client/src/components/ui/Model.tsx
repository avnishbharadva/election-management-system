// import React from 'react';
// import Box from '@mui/material/Box';
// import Modal from '@mui/material/Modal';
// import Button from '@mui/material/Button';
// import { ModelBox } from '../../style/ModelCss';
// import EditIcon from '@mui/icons-material/Edit';
// import VisibilityIcon from '@mui/icons-material/Visibility';
// import { styled } from "@mui/system";
// import PersonAddIcon from "@mui/icons-material/PersonAdd";




// const StyledButton = styled(Button)({
 
//   textTransform: "none",
//   background: "linear-gradient(90deg, #1E90FF, rgb(29, 38, 154))",
//   color: "white",
//   "&:hover": {
//     background: "linear-gradient(90deg, #007BFF, #0056b3)",
//   },
// });
 

// interface ModelProps {
//   open: boolean;
//   handleClose: () => void;
//   children: React.ReactNode; // Accept child components
//   actionType: 'edit' | 'view'| 'delete';
//   selectedVoter? : any
// }

// const Model: React.FC<ModelProps> = ({ children, actionType, open, handleClose, selectedVoter }) => {
//   // const [open, setOpen] = React.useState(false);
//   const handleOpen = () => setOpen(true);
//   // const handleClose = () => setOpen(false);
//   const getButtonProps = () => {
//     switch (actionType) {
//       case "view":
//         return { label: "View", icon: <VisibilityIcon />, color: "primary" };
//       case "edit":
//         return { label: "Edit", icon: <EditIcon /> };
//       default:
//         return { label: "Add", icon:  <PersonAddIcon />, color: "default" };
//     }
//   };

//   const {label, icon} = getButtonProps();

//   return (
//     <div>
//       <Button variant="contained" startIcon={icon} onClick={handleOpen}>
//         {label}
//       </Button>
//       <Modal
//         keepMounted
//         open={open}
//         onClose={handleClose}
//         aria-labelledby="keep-mounted-modal-title"
//         aria-describedby="keep-mounted-modal-description"
//       >
//         <ModelBox >
          
//           <Box>
          
//             {children}
//           </Box>
//         </ModelBox>
//       </Modal>
//     </div>
//   );
// };

// export default Model;


// import React from "react";
// import Box from "@mui/material/Box";
// import Modal from "@mui/material/Modal";
// import IconButton from "@mui/material/IconButton";
// import CloseIcon from "@mui/icons-material/Close";
// import PersonAddIcon from "@mui/icons-material/PersonAdd";
// import EditIcon from "@mui/icons-material/Edit";
// import { StyledButton } from "../../style/CommanStyle";
// import { ModelBox } from "../../style/ModelCss";
 
// interface ModelProps {
//   open: boolean;
//   handleClose: () => void;
//   actionType: "add" | "edit";
//   selectedCandidate?: any;
//   children: React.ReactNode;
// }
 
// const Model: React.FC<ModelProps> = ({
//   open,
//   handleClose,
//   actionType,
//   selectedCandidate,
//   children,
// }) => {
//   const getButtonProps = () => {
//     switch (actionType) {
//       case "add":
//         return { };
//       case "edit":
//         return { };
//       default:
//         return { };
//     }
//   };
 
//   const { label, icon } = getButtonProps();
 
//   return (
// <Modal keepMounted open={open} onClose={handleClose} aria-labelledby="modal-title">
// <ModelBox>
//         {/* Close Icon */}
// <Box
//           sx={{
//             position: "absolute",
//             top: "10px",
//             right: "10px",
//           }}
// >
// <IconButton onClick={handleClose}>
// <CloseIcon />
// </IconButton>
// </Box>
 
//         {/* Modal Content */}
// <h2>
//           {icon} {label}
// </h2>
//         {React.isValidElement(children) &&
//           React.cloneElement(children, { handleClose, selectedCandidate } as any)}
// </ModelBox>
// </Modal>
//   );
// };
 
// export default Model;


// import React from 'react';
// import Box from '@mui/material/Box';
// import Modal from '@mui/material/Modal';
// import Button from '@mui/material/Button';
// import { ModelBox } from '../../style/ModelCss';
// import EditIcon from '@mui/icons-material/Edit';
// import VisibilityIcon from '@mui/icons-material/Visibility';
// import { styled } from "@mui/system";
// import PersonAddIcon from "@mui/icons-material/PersonAdd";

// const StyledButton = styled(Button)({
//   textTransform: "none",
//   background: "linear-gradient(90deg, #1E90FF, rgb(29, 38, 154))",
//   color: "white",
//   "&:hover": {
//     background: "linear-gradient(90deg, #007BFF, #0056b3)",
//   },
// });

// interface ModelProps {
//   open: boolean;
//   handleClose: () => void;
//   children: React.ReactNode;
//   actionType: 'edit' | 'view' | 'delete';
//   selectedVoter?: any;
//   handleOpen: () => void; // Add handleOpen prop
// }

// const Model: React.FC<ModelProps> = ({ children, actionType, open, handleClose, selectedVoter, handleOpen }) => {
//   const getButtonProps = () => {
//     switch (actionType) {
//       case "view":
//         return { label: "View", icon: <VisibilityIcon />, color: "primary" };
//       case "edit":
//         return { label: "Edit", icon: <EditIcon /> };
//       default:
//         return { label: "Add", icon: <PersonAddIcon />, color: "default" };
//     }
//   };

//   const { label, icon } = getButtonProps();

//   return (
//     <div>
//       <Button variant="contained" startIcon={icon} onClick={handleOpen}>
//         {label}
//       </Button>
//       <Modal
//         keepMounted
//         open={open}
//         onClose={handleClose}
//         aria-labelledby="keep-mounted-modal-title"
//         aria-describedby="keep-mounted-modal-description"
//       >
//         <ModelBox>
//           <Box>
//             {children}
//           </Box>
//         </ModelBox>
//       </Modal>
//     </div>
//   );
// };

// export default Model;


// import React from 'react';
// import Box from '@mui/material/Box';
// import Modal from '@mui/material/Modal';
// import Button from '@mui/material/Button';
// import { ModelBox } from '../../style/ModelCss';
// import EditIcon from '@mui/icons-material/Edit';
// import VisibilityIcon from '@mui/icons-material/Visibility';
// import { styled } from "@mui/system";
// import PersonAddIcon from "@mui/icons-material/PersonAdd";
// import CloseIcon from '@mui/icons-material/Close'; // Import CloseIcon
// import IconButton from '@mui/material/IconButton'; // Import IconButton

// const StyledButton = styled(Button)({
//   textTransform: "none",
//   background: "linear-gradient(90deg, #1E90FF, rgb(29, 38, 154))",
//   color: "white",
//   "&:hover": {
//     background: "linear-gradient(90deg, #007BFF, #0056b3)",
//   },
// });

// interface ModelProps {
//   open: boolean;
//   handleClose: () => void;
//   children: React.ReactNode;
//   actionType: 'edit' | 'view' | 'delete';
//   selectedVoter?: any;
//   handleOpen: () => void;
// }

// const Model: React.FC<ModelProps> = ({ children, actionType, open, handleClose, selectedVoter, handleOpen }) => {
//   const getButtonProps = () => {
//     switch (actionType) {
//       case "view":
//         return { label: "View", icon: <VisibilityIcon />, color: "primary" };
//       case "edit":
//         return { label: "Edit", icon: <EditIcon /> };
//       default:
//         return { label: "Add", icon: <PersonAddIcon />, color: "default" };
//     }
//   };

//   const { label, icon } = getButtonProps();

//   return (
//     <div>
//       <Button variant="contained" startIcon={icon} onClick={handleOpen}>
//         {label}
//       </Button>
//       <Modal
//         keepMounted
//         open={open}
//         onClose={handleClose}
//         aria-labelledby="keep-mounted-modal-title"
//         aria-describedby="keep-mounted-modal-description"
//       >
//         <ModelBox>
//           <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 2 }}>
//             <Box /> {/* Empty Box to balance the layout */}
//             <IconButton onClick={handleClose} aria-label="close">
//               <CloseIcon />
//             </IconButton>
//           </Box>
//           <Box>
//             {children}
//           </Box>
//         </ModelBox>
//       </Modal>
//     </div>
//   );
// };

// export default Model;

import Box from '@mui/material/Box';
import Modal from '@mui/material/Modal';
import Button from '@mui/material/Button';
import React from 'react';
import { ModelBox } from '../../style/ModelCss';



interface ModelProps {
  children: React.ReactNode; // Accept child components
}

const Model: React.FC<ModelProps> = ({ children }) => {
  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  return (
    <div>
      <Button onClick={handleOpen}>Add</Button>
      <Modal
        keepMounted
        open={open}
        onClose={handleClose}
        aria-labelledby="keep-mounted-modal-title"
        aria-describedby="keep-mounted-modal-description"
      >
        <ModelBox >
          
          <Box>
          
            {children}
          </Box>
        </ModelBox>
      </Modal>
    </div>
  );
};

export default Model;
