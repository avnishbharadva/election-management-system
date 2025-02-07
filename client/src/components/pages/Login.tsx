import { Box, Button, TextField, Typography } from "@mui/material";
import dummy from "../../assets/images/NYC_Flag.jpg";
import { Resolver, useForm } from "react-hook-form";
import '../../style/login.css'
import { useNavigate } from "react-router-dom";

type FormValues = {
  email: string;
  password: string;
};

const resolver: Resolver<FormValues> = async (values) => {
  const errors: Record<string, object> = {};

  if (!values.email) {
    errors.email = {
      type: "required",
      message: "Email is required.",
    };
  }

  if (!values.password) {
    errors.password = {
      type: "required",
      message: "Password is required.",
    };
  }

  return {
    values: Object.keys(errors).length === 0 ? values : {},
    errors,
  };
};

const Login = () => {

  const navigate = useNavigate()

  const redirect = ()=>{
    navigate('/dashboard/cards')
  }
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<FormValues>({ resolver });

  const onSubmit = handleSubmit((data: object) => console.log(data));
  return (
    <>
      <Box className="loginBox">
        <img src={dummy} className="loginImg" alt="" />
        <Box className="formCard">
          <form className="loginform" onSubmit={handleSubmit(onSubmit)}>
            <Typography variant="h5">Sign In Here!</Typography>
            <Box className="loginField">
              <TextField
                fullWidth
                sx={{ mt: "20px" }}
                {...register("email", { required: true })}
                label="Email"
              />
              {errors?.email && (
                <p className="errorMsg">{errors.email.message}</p>
              )}
            </Box>
            <Box className="loginField">
              <TextField
                fullWidth
                {...register("password", { required: true })}
                label="Password"
              />
              {errors?.password && (
                <p className="errorMsg">{errors.password.message}</p>
              )}
            </Box>

            <Button
              type="submit"
              sx={{ mt: "20px" }}
              fullWidth
              variant="contained"
              onClick={redirect}
            >
              Login
            </Button>
          </form>
        </Box>
      </Box>
    </>
  );
};

export default Login;
