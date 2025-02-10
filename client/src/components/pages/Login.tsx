// import { styled } from "@mui/system";
import { Button, TextField, Typography } from "@mui/material";
import dummy from "../../assets/images/NYC_Flag.jpg";
import { Resolver, useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { ErrorMsg, FormCard, LoginBox, LoginField, LoginForm, LoginImg } from "../../style/LoginStyle";

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
  const navigate = useNavigate();

  // const redirect = () => {
  //   navigate("/dashboard/cards");
  // };

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<FormValues>({ resolver });

  const onSubmit = handleSubmit((data: FormValues) => console.log(data));

  return (
    <>
      <LoginBox>
        <LoginImg src={dummy} alt="" />
        <FormCard>
          <LoginForm onSubmit={handleSubmit(onSubmit)}>
            <Typography variant="h5">Sign In Here!</Typography>
            <LoginField>
              <TextField
                fullWidth
                sx={{
                  mt: "20px",
                  backgroundColor: "#fff",
                  borderRadius: "5px",
                }}
                {...register("email", { required: true })}
                label="Email"
              />
              {errors?.email && <ErrorMsg>{errors.email.message}</ErrorMsg>}
            </LoginField>
            <LoginField>
              <TextField
                fullWidth
                sx={{
                  backgroundColor: "#fff",
                  borderRadius: "5px",
                }}
                {...register("password", { required: true })}
                label="Password"
              />
              {errors?.password && <ErrorMsg>{errors.password.message}</ErrorMsg>}
            </LoginField>
            <Button
              type="submit"
              sx={{ mt: "20px" }}
              fullWidth
              variant="contained"
              // onClick={redirect}
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
