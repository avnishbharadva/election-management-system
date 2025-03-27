import React, { useEffect, useState } from "react";
import { CardContent, CardActionArea } from "@mui/material";
import AccountBoxIcon from "@mui/icons-material/AccountBox";
import HowToVoteIcon from "@mui/icons-material/HowToVote";
import GroupsIcon from "@mui/icons-material/Groups";
import BallotIcon from "@mui/icons-material/Ballot";
import { useNavigate } from "react-router-dom";
import {
  Container,
  CardWrapper,
  ChartWrapper,
  StyledCard,
  Content,
  Graph,
} from "../../style/CardCss";
import axiosInstance from "../../store/app/axiosInstance";
import { cardsConfig, CountsData } from "../../config/cardConfig";

const iconMap: { [key: string]: JSX.Element } = {
  AccountBoxIcon: <AccountBoxIcon fontSize="large" sx={{ color: "#02B2AF" }} />,
  HowToVoteIcon: <HowToVoteIcon fontSize="large" sx={{ color: "#02B2AF" }} />,
  GroupsIcon: <GroupsIcon fontSize="large" sx={{ color: "#02B2AF" }} />,
  BallotIcon: <BallotIcon fontSize="large" sx={{ color: "#02B2AF" }} />,
};

const Cards: React.FC = () => {
  const [countsData, setCountsData] = useState<CountsData>({
    candidates: 0,
    voters: 0,
    parties: 0,
    elections: 0,
  });

  const navigate = useNavigate();

  useEffect(() => {
    const fetchCounts = async () => {
      try {
        const response = await axiosInstance.get("counts");
        setCountsData(response.data);
      } catch (error) {
        console.error("Error fetching counts:", error);
      }
    };

    fetchCounts();
  }, []);

  const chartData = cardsConfig.map((card) => countsData[card.countKey]);
  const chartLabels = cardsConfig.map((card) => card.title);

  return (
    <Container>
      <CardWrapper>
        {cardsConfig.map((card) => (
          <StyledCard key={card.id} onClick={() => navigate(card.link)}>
            <CardActionArea>
              <CardContent>
                <Content variant="h6">{card.title}</Content>
                {iconMap[card.icon]}
                <Content variant="h4">{countsData[card.countKey]}</Content>
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
