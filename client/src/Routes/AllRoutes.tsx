// import { Route, Routes } from 'react-router-dom'
// import Login from '../components/pages/Login'
// import AddCandidate from '../components/pages/AddCandidate'
// import Cards from '../components/ui/Cards'
// import AddVoter from '../components/pages/AddVoter'
// import AddParty from '../components/pages/AddParty'
// import AddElection from '../components/pages/AddElection'
// import Layout from '../components/ui/Layout'

// const AllRoutes = () => {
//   return (
//     <>   
//     <Routes>
    
//         <Route path="/" element={<Layout />}>
//         <Route path='/dashboard' element={<Cards/>}></Route>
//         <Route path='/candidate' element={<AddCandidate/>}></Route>
//         <Route path='/voter' element={<AddVoter/>}></Route>
//         <Route path='/party' element={<AddParty/>}></Route>
//         <Route path='/election' element={<AddElection/>}></Route>
//         </Route>
//         <Route  path='/login' element={<Login/>}></Route>
        
        
//     </Routes>
//     </>
//   )
// }

// export default AllRoutes
import { Route, Routes, Navigate } from "react-router-dom";
import Login from "../components/pages/Login";
import AddCandidate from "../components/pages/AddCandidate";
import Cards from "../components/ui/Cards";
import AddVoter from "../components/pages/AddVoter";
import AddParty from "../components/pages/AddParty";
import AddElection from "../components/pages/AddElection";
import Layout from "../components/ui/Layout";
import ProtectedRoute from "./ProtectedRoute";

const AllRoutes = () => {
  return (
    <Routes>
      {/* Public Route */}
      <Route path="/" element={<Login />} />
      
      {/* Protected Routes */}
      <Route
        path="/app"
        element={
          <ProtectedRoute>
            <Layout />
          </ProtectedRoute>
        }
      >
        <Route path="/app/dashboard" element={<Cards />} />
        <Route path="/app/candidate" element={<AddCandidate />} />
        <Route path="/app/voter" element={<AddVoter />} />
        <Route path="/app/party" element={<AddParty />} />
        <Route path="/app/election" element={<AddElection />} />
      </Route>

      {/* Redirect unknown routes to the login page */}
      <Route path="*" element={<Navigate to="/" />} />
    </Routes>
  );
};

export default AllRoutes;
// import { Route, Routes, Navigate } from "react-router-dom";
// import Login from "../components/pages/Login";
// import AddCandidate from "../components/pages/AddCandidate";
// import Cards from "../components/ui/Cards";
// import AddVoter from "../components/pages/AddVoter";
// import AddParty from "../components/pages/AddParty";
// import AddElection from "../components/pages/AddElection";
// import Layout from "../components/ui/Layout";
// import ProtectedRoute from "./ProtectedRoute";

// const AllRoutes = () => {
//   return (
//     <Routes>
//       {/* Public Route */}
//       <Route path="/" element={<Login />} />
      
//       {/* Protected Routes */}
//       <Route
//         element={
//           <ProtectedRoute>
//             <Layout />
//           </ProtectedRoute>
//         }
//       >
//         <Route path="/dashboard" element={<Cards />} />
//         <Route path="/candidate" element={<AddCandidate />} />
//         <Route path="/voter" element={<AddVoter />} />
//         <Route path="/party" element={<AddParty />} />
//         <Route path="/election" element={<AddElection />} />
//       </Route>

//       {/* Redirect unknown routes */}
//       <Route path="*" element={<Navigate to="/" />} />
//     </Routes>
//   );
// };

// export default AllRoutes;
