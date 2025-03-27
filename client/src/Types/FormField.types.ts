import { SerializedError } from "@reduxjs/toolkit";
import { FetchBaseQueryError } from "@reduxjs/toolkit/query";

export interface FieldProps {
    control: any;
    name: string;
    label: string;
    fixedLength?: number;
    minLength?: number;
    maxLength?: number;
    isRequired?: boolean;
    rules?: any;
    disabled?: boolean

    customfield?: {
        readOnly?: boolean;
        [key: string]: any;

    }
};


export interface ManuItem extends FieldProps {
    data: any[];
    value?: any;
    label: string;
    loading?: boolean;
    error?: string | FetchBaseQueryError | SerializedError; 
    isError?: boolean;
    valueKey: string;
    displayKey: string;
    idKey: string;

}