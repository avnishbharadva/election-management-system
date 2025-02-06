import Sidebar from "../ui/Sidebar";

const AddCandidate = () => {
  
 
  return (
    <div style={{ display: "flex", minHeight: "100vh" }}>
      <Sidebar />
      <div style={{ flex: 1, padding: "1rem" }}>
        <h2>Add Candidate</h2>
      </div>
    </div>
  );
};

export default AddCandidate;
