import React, { useEffect, useState } from "react";
import { CardContent, CardActionArea } from "@mui/material";
import AccountBoxIcon from "@mui/icons-material/AccountBox";
import HowToVoteIcon from "@mui/icons-material/HowToVote";
import GroupsIcon from "@mui/icons-material/Groups";
import BallotIcon from "@mui/icons-material/Ballot";
import {
  CardContainer,
  Container,
  Content,
  Graph,
  HeaderContainer,
} from "../../style/CardCss";
import { ContentWrapper } from "../../style/CardCss";
import { CardsContainerLeft } from "../../style/CardCss";
import { StyledCard } from "../../style/CardCss";
import { ChartContainer } from "../../style/CardCss";
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

  const fetchCounts = async () => {
    try {
      // const token = localStorage.getItem("token");
      const response = await axiosInstance.get("api/counts", {
        // headers: {
        //   Authorization: `Bearer ${token}`,
        // },
      });
      setCountsData(response.data); // Set the API data into state
    } catch (error) {
      console.error("Error fetching counts:", error);
    }
  };

  useEffect(() => {
    fetchCounts();
  }, []);

  // Dynamically populate cards from API data
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
      {/* <Sidebar /> */}
      <ContentWrapper>
        <CardContainer>
          {/* Cards on Left */}
          <CardsContainerLeft>
            {cards.map((card) => (
              <StyledCard key={card.id}>
                <CardActionArea>
                  <CardContent>
                    <HeaderContainer>
                      <Content variant="h6">{card.title}</Content>
                      {card.icon}
                    </HeaderContainer>
                    <Content variant="h4">{card.count}</Content>
                  </CardContent>
                </CardActionArea>
              </StyledCard>
            ))}
          </CardsContainerLeft>

          <ChartContainer>
            <Graph
              xAxis={[{ id: "barCategories", data: chartLabels, scaleType: "band" }]}
              series={[{ data: chartData }]}
            />
          </ChartContainer>
        </CardContainer>
      </ContentWrapper>
    </Container>
  );
};

export default Cards;
