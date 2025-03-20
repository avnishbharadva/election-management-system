
import React from 'react';
import { TableBody, TableCell, TableHead, TableRow, CircularProgress, Typography } from '@mui/material';
import {TableMain,  TableCellSticky, TableContainerMain } from '../../style/VoterStyleCss';
import {TableComponentProps} from '../../Types/TypeTable'
 
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