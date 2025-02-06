import * as React from "react";
import Box from "@mui/material/Box";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import CardActionArea from "@mui/material/CardActionArea";
import AccountBoxIcon from "@mui/icons-material/AccountBox";
import HowToVoteIcon from "@mui/icons-material/HowToVote";
import GroupsIcon from "@mui/icons-material/Groups";
import BallotIcon from "@mui/icons-material/Ballot";

const cards = [
  {
    id: 1,
    title: "Candidate",
    count: 4021,
    icon: <AccountBoxIcon fontSize="large" sx={{ color: "#1976d2" }} />,
  },
  {
    id: 2,
    title: "Voters",
    count: 15000,
    icon: <HowToVoteIcon fontSize="large" sx={{ color: "#1976d2" }} />,
  },
  {
    id: 3,
    title: "Party",
    count: 120,
    icon: <GroupsIcon fontSize="large" sx={{ color: "#1976d2" }} />,
  },
  {
    id: 4,
    title: "Election",
    count: 5,
    icon: <BallotIcon fontSize="large" sx={{ color: "#1976d2" }} />,
  },
];

function Cards() {
  const [selectedCard, setSelectedCard] = React.useState(0);

  return (
    <Box
      sx={{
        display: "flex", // Display in a row
        gap: 2, // Space between cards
        justifyContent: "center", // Center cards horizontally
        padding: 3,
        flexWrap: "nowrap", // Ensures cards stay in a single row
        overflowX: "auto", // Allows horizontal scrolling if needed
      }}
      className='card-wrapper'
    >
      {cards.map((card, index) => (
        <Card
          key={card.id}
          sx={{
            width: 320, // Fixed width for each card
            height: 180,
            display: "flex",
            flexDirection: "column",
            justifyContent: "center",
            alignItems: "center",
            textAlign: "center",
            borderRadius: "15px",
            boxShadow: "0px 6px 10px rgba(0,0,0,0.2)",
            transition: "0.3s",
            "&:hover": { transform: "scale(1.05)" }, // Hover effect
          }}
          className="card-container"
        >
          <CardActionArea
            className='card-action'
            onClick={() => setSelectedCard(index)}
            data-active={selectedCard === index ? "" : undefined}
            sx={{
              height: "100%",
              display: "flex",
              flexDirection: "column",
              alignItems:'center',
              justifyContent: "space-between",
              padding: 2,
              "&[data-active]": {
                backgroundColor: "action.selected",
                "&:hover": {
                  backgroundColor: "action.selectedHover",
                },
              },
            }}
          >
            <CardContent>
              <Box sx={{ display: "flex", gap:'10rem'}} className='card-box1'>
                <Typography variant="h6" sx={{ fontWeight: "bold" }} className='card-title'>
                  {card.title}
                </Typography>
                {card.icon} 
              </Box>
              {/* Display count instead of description */}
              <Box sx={{display:'flex', justifyContent:'flex-start'}} className='card-box1'>
              <Typography variant="h4" sx={{ fontWeight: "bold", mt: 1 }} className='card-title'>
                {card.count}
              </Typography>
              </Box>
            </CardContent>
          </CardActionArea>
        </Card>
      ))}
    </Box>
  );
}

export default Cards;

// import React from "react";
// import Box from "@mui/material/Box";
// import Card from "@mui/material/Card";
// import CardContent from "@mui/material/CardContent";
// import Typography from "@mui/material/Typography";
// import CardActionArea from "@mui/material/CardActionArea";
// import AccountBoxIcon from "@mui/icons-material/AccountBox";
// import HowToVoteIcon from "@mui/icons-material/HowToVote";
// import GroupsIcon from "@mui/icons-material/Groups";
// import BallotIcon from "@mui/icons-material/Ballot";
// import "../style/Cards.css"; // Import custom CSS

// const cards = [
//   {
//     id: 1,
//     title: "Candidate",
//     count: 4021,
//     icon: <AccountBoxIcon fontSize="large" sx={{ color: "#1976d2" }} />,
//   },
//   {
//     id: 2,
//     title: "Voters",
//     count: 15000,
//     icon: <HowToVoteIcon fontSize="large" sx={{ color: "#1976d2" }} />,
//   },
//   {
//     id: 3,
//     title: "Party",
//     count: 120,
//     icon: <GroupsIcon fontSize="large" sx={{ color: "#1976d2" }} />,
//   },
//   {
//     id: 4,
//     title: "Election",
//     count: 5,
//     icon: <BallotIcon fontSize="large" sx={{ color: "#1976d2" }} />,
//   },
// ];

// function Cards() {
//   const [selectedCard, setSelectedCard] = React.useState(0);

//   return (
//     <Box className="cards-container">
//       {cards.map((card, index) => (
//         <Card key={card.id} className="card">
//           <CardActionArea
//             onClick={() => setSelectedCard(index)}
//             data-active={selectedCard === index ? "" : undefined}
//           >
//             <CardContent className='card-content'>
//               <Box className='card-header'>
//                 <Typography className='card-title'>{card.title}</Typography>
//                 {card.icon}
//               </Box>
//               <Box>
//                 <Typography variant="h4" sx={{ fontWeight: "bold", mt: 1, display:'flex', justifyContent:'flex-start' }}>
//                  {card.count}
//                 </Typography>
//                </Box>
//             </CardContent>
//           </CardActionArea>
//         </Card>
//       ))}
//     </Box>
//   );
// }

// export default Cards;
