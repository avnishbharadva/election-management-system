
export const formStyles = {
    container: {
      display: 'flex',
      flexDirection: 'column',
      gap: 2,
    },
    row: {
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
      gap: 2,
    },
  };
  
  export const tableStyles = {
    tableContainer: {
      marginTop: 2,
      overflow: 'auto',
      maxHeight: '500px',
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
      justifyContent: 'center',
      marginBottom: 2,
    },
  };