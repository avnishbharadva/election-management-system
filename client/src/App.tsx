import { ToastContainer } from "react-toastify";
import AllRoutes from "./routes/AllRoutes";

function App() {
  return (
    <>
      <AllRoutes />
      <ToastContainer position="top-right" autoClose={3000} hideProgressBar />
    </>
  );
}

export default App;
