import { createAsyncThunk } from "@reduxjs/toolkit";
import { Officer } from "./types";
import axios from "axios";
import axiosInstance from "../../app/axiosInstance";
import { toast } from "react-toastify";

export const officerLogin = createAsyncThunk('officer/login',async(offcierData:Officer,{rejectWithValue})=>{
    try {
          const response =await axios.post("http://localhost:8082/authenticate",offcierData)
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
            toast.error(error.response?.data?.message);
            return rejectWithValue(error.response?.data?.message);
          }
        return rejectWithValue(error.response?.data?.message || "Failed to Login");
        
    }
})