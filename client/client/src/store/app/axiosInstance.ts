import axios from "axios";


const axiosInstance = axios.create({
  baseURL: "http://localhost:8082/", 
});


const token = localStorage.getItem("token");
if (token) {
  axiosInstance.defaults.headers.common["Authorization"] = `Bearer ${token}`;
}

export default axiosInstance;
