import React, { useEffect, useState } from "react";
import { CardContent, CardActionArea } from "@mui/material";
import AccountBoxIcon from "@mui/icons-material/AccountBox";
import HowToVoteIcon from "@mui/icons-material/HowToVote";
import GroupsIcon from "@mui/icons-material/Groups";
import BallotIcon from "@mui/icons-material/Ballot";
import {
  Container,
  CardWrapper,
  ChartWrapper,
  StyledCard,
  Content,
  Graph,
} from "../../style/CardCss";
import axiosInstance from "../../store/app/axiosInstance";

interface CardData {
  id: number;
  title: string;
  count: number;
  icon: JSX.Element;
}

const Cards: React.FC = () => {
  const [countsData, setCountsData] = useState({
    candidates: 0,
    voters: 0,
    parties: 0,
    elections: 0,
  });

  useEffect(() => {
    const fetchCounts = async () => {
      try {
        const response = await axiosInstance.get("api/counts");
        setCountsData(response.data);
      } catch (error) {
        console.error("Error fetching counts:", error);
      }
    };

    fetchCounts();
  }, []);

  const cards: CardData[] = [
    {
      id: 1,
      title: "Candidate",
      count: countsData.candidates,
      icon: <AccountBoxIcon fontSize="large" sx={{ color: "#02B2AF" }} />,
    },
    {
      id: 2,
      title: "Voters",
      count: countsData.voters,
      icon: <HowToVoteIcon fontSize="large" sx={{ color: "#02B2AF" }} />,
    },
    {
      id: 3,
      title: "Party",
      count: countsData.parties,
      icon: <GroupsIcon fontSize="large" sx={{ color: "#02B2AF" }} />,
    },
    {
      id: 4,
      title: "Election",
      count: countsData.elections,
      icon: <BallotIcon fontSize="large" sx={{ color: "#02B2AF" }} />,
    },
  ];

  const chartData = cards.map((card) => card.count);
  const chartLabels = cards.map((card) => card.title);

  return (
    <Container>
      <CardWrapper>
        {cards.map((card) => (
          <StyledCard key={card.id}>
            <CardActionArea>
              <CardContent>
                <Content variant="h6">{card.title}</Content>
                {card.icon}
                <Content variant="h4">{card.count}</Content>
              </CardContent>
            </CardActionArea>
          </StyledCard>
        ))}
      </CardWrapper>
      <ChartWrapper>
        <Graph
          xAxis={[{ id: "categories", data: chartLabels, scaleType: "band" }]}
          series={[{ data: chartData }]}
        />
      </ChartWrapper>
    </Container>
  );
};

export default Cards;
