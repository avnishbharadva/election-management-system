// VoterForm.styles.js

export const voterStyles = {
  // Styles from AddVoter.js
  searchContainer: {
    display: 'grid',
    gridTemplateColumns: '1fr auto',
    alignItems: 'center',
    gap: 2,
  },
  tableContainer: {
    marginTop: 2,
    overflow: 'auto',
    maxHeight: '461px',
  },
  table: {
    minWidth: 'max-content',
    tableLayout: 'auto',
    whiteSpace: 'nowrap',
    height: '383px',
  },
  tableHead: {
    position: 'sticky',
    top: 0,
    background: 'white',
    zIndex: 1,
  },

//VoterForm.js

  form:{
    margin:'20px',
  },

  container: {
    display: 'flex',
    flexDirection: 'column',
    // gap: 2,
    flex: 1, 
    overflowY: "auto", 
    maxHeight: "60vh"
  },

  head:{
    color:'#003153',
    position: "sticky",
    top: 0,
    background: "white",
    padding: "16px",
    borderBottom: "1px solid #ccc",
    zIndex: 10,
    fontWeight: "bold",
    fontSize: "20px",   
  },

  title: {
    marginTop: '20px',
  },
  votingInfo: {
    marginTop: '20px',
  },
  address: {
    marginTop: '20px',
  },
  uploadDocuments: {
    marginTop: '20px',
  },
  formControlLabel: {
    marginTop: '20px',
  },
  formRowfirst: {
    display: 'flex',
    alignItems: 'center',
    gap: '1rem',
    // marginTop:'20px',
  },
  formRow: {
    display: 'flex',
    alignItems: 'center',
    gap: '1rem',
    marginTop:'20px',
  },
  formRowWide: {
    display: 'flex',
    alignItems: 'center',
    gap: '1rem',
    width: '66%',
  },
  formRowCenter: {
    display: 'flex',
    alignItems: 'center',
    gap: '1rem',
    justifyContent: 'center',
    marginTop: '20px',
  },
  formRowGap: {
    display: 'flex',
    alignItems: 'center',
    gap: '2.5rem',
  },

  formRowCenterGap:{
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    gap: '2rem',
    marginTop: '20px',
  },

  formRowCenterGap2: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    gap: '2rem',
    marginTop: '20px',
    position: 'sticky',
    bottom:0,
    borderTop: '1px solid #ccc',
    zindex:10,
    background: "white",
  },
};

export const dividerStyle = {
  marginTop: '10px',
  marginBottom: '10px',
};