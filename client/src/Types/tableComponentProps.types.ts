interface Header {
    label: string;
    id: string;
    minWidth?: number;
    align?: "left" | "center" | "right";
}
 
interface Row {
    [key: string]: string | React.ReactNode | (() => React.ReactNode);
}
 
export interface TableComponentProps {
    headers: Header[];
    rows: Row[];
    loading?: boolean;
    error?: string | Boolean;
    noDataFound?: string | React.ReactNode | (() => React.ReactNode);
    children?: React.ReactNode;
}
 