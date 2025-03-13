import React from 'react';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, CircularProgress, Typography } from '@mui/material';
// import { tableStyles } from '../../style/PartyStyle';
import { TableMain, TableCellSticky, TableContainerMain } from '../../style/VoterStyleCss';
 
interface Header {
    label: string;
    id: string;
    minWidth?: number;
    align?: "left" | "center" | "right";
}
 
interface Row {
    [key: string]: string | React.ReactNode | (() => React.ReactNode);
}
 
interface TableComponentProps {
    headers: Header[];
    rows: Row[];
    loading?: boolean;
    error?: string | Boolean;
    noDataFound?: string | React.ReactNode | (() => React.ReactNode);
    children?: React.ReactNode;
}
 
// Helper function to resolve value to ReactNode
const resolveToReactNode = (value: string | React.ReactNode | (() => React.ReactNode)): React.ReactNode => {
    return typeof value === 'function' ? value() : value;
};
 
const TableComponent: React.FC<TableComponentProps> = ({
    headers,
    rows,
    loading = false,
    error = null,
    noDataFound = 'No data available',
    children,
}) => {
    return (
        <>
            <TableContainerMain >
                <TableMain>
                    <TableHead>
                        <TableRow>
                            {headers.map((header, index) => (
                                <TableCellSticky
                                    key={index}
                                >
                                    <b>{header.label}</b>
                                </TableCellSticky>
                            ))}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {loading ? (
                            <TableRow>
                                <TableCell colSpan={headers.length} align="center">
                                    <CircularProgress />
                                </TableCell>
                            </TableRow>
                        ) : error ? (
                            <TableRow>
                                <TableCell colSpan={headers.length} align="center">
                                    <Typography color="error">{String(error)}</Typography>
                                </TableCell>
                            </TableRow>
                        ) : rows.length > 0 ? (
                            rows.map((row, rowIndex) => (
                                <TableRow key={rowIndex}>
                                    {headers.map((header, cellIndex) => (
                                        <TableCell key={cellIndex} align={header.align || 'left'}>
                                            {resolveToReactNode(row[header.id])}
                                        </TableCell>
                                    ))}
                                </TableRow>
                            ))
                        ) : (
                            <TableRow>
                                <TableCell colSpan={headers.length} align="center">
                                    {resolveToReactNode(noDataFound)}
                                </TableCell>
                            </TableRow>
                        )}
                    </TableBody>
                </TableMain>
            </TableContainerMain>
            {children}
        </>
    );
};
 
export default TableComponent;