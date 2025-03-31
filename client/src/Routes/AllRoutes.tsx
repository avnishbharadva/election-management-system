
import { Route, Routes, Navigate } from "react-router-dom";
import Login from "../components/pages/Login";
import AddCandidate from "../components/pages/Candidate";
import AddVoter from "../components/pages/Voter";
import AddParty from "../components/pages/Party";
import AddElection from "../components/pages/Election";
import Layout from "../components/ui/Layout";
import ProtectedRoute from "./ProtectedRoute";
import Dashboard from "../components/pages/Dashboard";

const AllRoutes = () => {
  return (
    <Routes>
      {/* Public Route */}
      <Route path="/" element={<Login />} />

      {/* Protected Routes */}
      <Route
        path="/dashboard"
        element={
          <ProtectedRoute>
            <Layout />
          </ProtectedRoute>
        }
      >
       
        <Route index element={<Dashboard/>} /> 
        <Route path="candidates" element={<AddCandidate />} /> 
        <Route path="voters" element={<AddVoter />} /> 
        <Route path="parties" element={<AddParty />} /> 
        <Route path="elections" element={<AddElection />} /> 
      </Route>

     
      <Route path="*" element={<Navigate to="/" />} />
    </Routes>
  );
};

export default AllRoutes;
