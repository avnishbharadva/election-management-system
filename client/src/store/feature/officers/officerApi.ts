import { createAsyncThunk } from "@reduxjs/toolkit";
import { Officer } from "./types";
import axiosInstance from "../../app/axiosInstance";
import { toast } from "react-toastify";
import axios from "axios";

export const officerLogin = createAsyncThunk('officer/login',async(offcierData:Officer,{rejectWithValue})=>{
    try {
          const response =await axios.post("http://localhost:8082/officers/authenticate",offcierData);
          if (response.status === 200) {
            const token = response.data.token;
            localStorage.setItem("token", token);
           axiosInstance.defaults.headers.common["Authorization"] = `Bearer ${token}`;
           toast.success("Sign In Successfully!");
           return token
          }          
    } catch (error:any) {
      console.log(error);
        if (error.response?.status === 500) {
            toast.error("Invalid Credentials");
            return rejectWithValue(error.response?.data?.message);
          }
          toast.error("Something went wrong")
        return rejectWithValue(error.response?.data?.message || "Failed to Login");
        
    }
})