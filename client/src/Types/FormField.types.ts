export type FieldProps = {
    control: any;
    name: string;
    label: string;
    fixedLength?: number;
    minLength?: number;
    maxLength?: number;
    isRequired?: boolean;
    rules?: any;
    disabled?:boolean

    customfield?: {
        readOnly?: boolean;
        [key: string]: any;

    }};
