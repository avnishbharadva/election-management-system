
export const formStyles = {

  mainHead:{
    marginTop:'20px',
  },

  form:{
    margin: '20px',
    zindex: 10,
  },

  container: {
    display: 'flex',
    flexDirection: 'column',
    gap: 2,
    flex: 1, 
    overflowY: "auto", 
    maxHeight: "60vh"
  },
  row: {
    marginTop:'15px',
    display: 'flex',
    flexDirection: 'row',
    gap: 2,
  },
  gridContainer: {
    display: 'grid',
    gridTemplateColumns: '1fr 1fr',
    gap: '10px',
  },
  submitButtonContainer: {
    display: 'flex',
    alignContent: 'center',
    justifyContent: 'center',
    flexDirection: 'row',
    position: "sticky",
    bottom: 0,
    background: "white",
    padding: "16px",
    borderTop: "1px solid #ccc",
    zIndex: 10,
    gap:'2rem',
  },
};

//tableStyles
export const tableStyles = {
  tableContainer: {
    marginTop: 2,
    overflow: 'auto',
    maxHeight: '461px',
  },
  table: {
    minWidth: 'max-content',
    tableLayout: 'auto',
    whiteSpace: 'nowrap',
  },
  tableCellSticky: {
    position: 'sticky',
    top: 0,
    background: 'white',
    zIndex: 1,
  },
  addButtonContainer: {
    display: 'flex',
    justifyContent: 'flex-end',
    marginBottom: 2,
  },
};