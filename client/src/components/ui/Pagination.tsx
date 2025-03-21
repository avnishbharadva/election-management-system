import React from 'react';
import { TablePagination } from '@mui/material';
 
type PaginationProps = {
    totalElements: number;
    setPage: (page: number) => void;
    page: number;
    setRowsPerPage: (rows: number) => void;
    rowsPerPage: number;
};
 
const Pagination = ({
    totalElements,
    setPage,
    page,
    setRowsPerPage,
    rowsPerPage = 5,
}:PaginationProps) => {
 
    const handleChangePage = (_: React.MouseEvent<HTMLButtonElement> | null, newPage: number) => {
        setPage(newPage);
    };
 
    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0); // Reset to first page
    };
 
    return (
        <>
        <TablePagination
            rowsPerPageOptions={[5, 10, 25]}
            component="div"
            count={totalElements}
            rowsPerPage={rowsPerPage}
            page={page}
            onPageChange={handleChangePage}
            onRowsPerPageChange={handleChangeRowsPerPage}
        />
        </>
    );
};
 
export default Pagination;