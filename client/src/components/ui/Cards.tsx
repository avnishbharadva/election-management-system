import React from "react";
import Sidebar from "./Sidebar";
import Box from "@mui/material/Box";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import CardActionArea from "@mui/material/CardActionArea";
import AccountBoxIcon from "@mui/icons-material/AccountBox";
import HowToVoteIcon from "@mui/icons-material/HowToVote";
import GroupsIcon from "@mui/icons-material/Groups";
import BallotIcon from "@mui/icons-material/Ballot";
import { BarChart } from "@mui/x-charts";

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
const chartData = cards.map((card) => card.count); // Extract counts for the chart
  const chartLabels = cards.map((card) => card.title);
function Cards() {
  return (
    <div style={{ display: "flex" ,minHeight: "100vh" }}>
      <Sidebar />
      <div style={{ flex: 1, padding: "1rem" }}>
        <Box
          sx={{
            display: "flex",
            gap: 2,
            justifyContent: "center",
            flexWrap: "wrap",
          }}
        >
          {cards.map((card) => (
            <Card
              key={card.id}
              sx={{
                width: 280,
                height: 180,
                display: "flex",
                flexDirection: "column",
                justifyContent: "center",
                alignItems: "center",
                textAlign: "center",
                borderRadius: "15px",
                boxShadow: "0px 6px 10px rgba(0,0,0,0.2)",
                transition: "0.3s",
                "&:hover": { transform: "scale(1.05)" },
              }}
            >
              <CardActionArea>
                <CardContent>
                  <Box sx={{ display: "flex", justifyContent: "space-between" }}>
                    <Typography variant="h6" sx={{ fontWeight: "bold" }}>
                      {card.title}
                    </Typography>
                    {card.icon}
                  </Box>
                  <Typography variant="h4" sx={{ fontWeight: "bold", mt: 1 }}>
                    {card.count}
                  </Typography>
                </CardContent>
              </CardActionArea>
            </Card>
          ))}
        </Box>
        <BarChart
        sx={{ml:"30px"}}
        
      xAxis={[
        {
          id: 'barCategories',
          data: chartLabels,
          scaleType: 'band',
        },
      ]}
      series={[
        {
          data:chartData,
        },
      ]}
      width={500}
      height={300}
    />
      </div>
      
    </div>
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
