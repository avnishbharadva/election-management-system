// import { IFormInput } from '../candidate/types';
// import { officerLogin } from './officerApi';
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