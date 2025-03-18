import { Button, TextField, Typography } from "@mui/material";
import bgImg from "../../assets/images/NYC_Flag.jpg";
import { Resolver, useForm,SubmitHandler } from 'react-hook-form';
import { useNavigate } from "react-router-dom";
import {
 
  FormCard,
  LoginBox,
  LoginField,
  LoginForm,
  LoginImg,
  LoginTitle,
} from "../../style/LoginStyle";
import { AppDispatch } from "../../store/app/store";
import { useDispatch } from "react-redux";
import { officerLogin } from "../../store/feature/officers/officerApi";
import Navbar from "../ui/Navbar";

type FormValues = {
  email: string;
  password: string;
};



const Login = () => {
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<FormValues>();

  const onSubmit: SubmitHandler<FormValues> = async (officerData) => {
    try {
        await dispatch(officerLogin(officerData)).unwrap()
       navigate("/dashboard");
    } catch (error) {
      
      // console.error("Login failed:", error);
     
    }
  };
  

  return (
    <>
         
     <LoginBox>
    {/* <Typography>Election Management System</Typography> */}
  <LoginImg src={bgImg} alt="" />
  <FormCard>
  <LoginTitle>
          
          Election Management System
        </LoginTitle>
    <LoginForm onSubmit={handleSubmit(onSubmit)}>
      <Typography variant="h6">Sign In Here!</Typography>
      <LoginField>
        <TextField
          fullWidth
          
          {...register("email", { 
            required: "Email is required", 
            pattern: { 
              value: /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/, 
              message: "Invalid email format" 
            } 
          })}
          label="Email"
          error={!!errors?.email}
          helperText={errors?.email?.message}
        />
      </LoginField>
      <LoginField>
        <TextField
          fullWidth
         type="password"
          {...register("password", { 
            required: "Password is required", 
            minLength: { 
              value: 8, 
              message: "Password must be at least 8 characters long" 
            } 
          })}
          label="Password"
          error={!!errors?.password}
          helperText={errors?.password?.message}
        />
      </LoginField>
      <Button
        type="submit"
        sx={{ mt: "20px" }}
        fullWidth
        variant="contained"
      >
        Login
      </Button>
    </LoginForm>
  </FormCard>
</LoginBox>

    </>
  );
};

export default Login;
