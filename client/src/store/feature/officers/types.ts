export interface Officer {
    email:string,
    password:string
}

export interface LoginInput{
    email:string,
    password:string
}
export interface OfficerState{
    success:boolean;
    loading: boolean;
    error: string | null;
}
export interface FormValues  {
    email: string;
    password: string;
  };