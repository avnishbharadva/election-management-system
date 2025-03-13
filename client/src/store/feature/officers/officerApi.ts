import { createAsyncThunk } from "@reduxjs/toolkit";
import { Officer } from "./types";
import axios from "axios";
import axiosInstance from "../../app/axiosInstance";
import { toast } from "react-toastify";

export const officerLogin = createAsyncThunk('officer/login',async(offcierData:Officer,{rejectWithValue})=>{
    try {
<<<<<<< HEAD
          const response =await axios.post("http://localhost:8082/api/officers/authenticate",offcierData)
=======
          const response =await axios.post("http://localhost:8082/authenticate",offcierData)
>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486
          console.log(response.status)
          if (response.status === 200) {
            const token = response.data.token;
            localStorage.setItem("token", token);
           axiosInstance.defaults.headers.common["Authorization"] = `Bearer ${token}`;
           toast.success("Signin successful!");
           return token
          }
           
    } catch (error:any) {
      console.log(error);
        if (error.response?.status === 500) {
            toast.error("Invalid Credentials");
            return rejectWithValue(error.response?.data?.message);
          }
        return rejectWithValue(error.response?.data?.message || "Failed to Login");
        
    }
})