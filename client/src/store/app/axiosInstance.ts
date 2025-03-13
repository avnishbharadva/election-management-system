import axios from "axios";


const axiosInstance = axios.create({
<<<<<<< HEAD
  baseURL: "http://localhost:8082/api", // Replace with your API base URL
=======
  baseURL: "http://localhost:8082/", 
>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486
});


const token = localStorage.getItem("token");
if (token) {
  axiosInstance.defaults.headers.common["Authorization"] = `Bearer ${token}`;
}

export default axiosInstance;
