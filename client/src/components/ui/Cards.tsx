<<<<<<< HEAD
import React from "react";
import Sidebar from "./Sidebar";
import {  CardContent ,CardActionArea } from "@mui/material";
=======
import React, { useEffect, useState } from "react";
import { CardContent, CardActionArea } from "@mui/material";
>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486
import AccountBoxIcon from "@mui/icons-material/AccountBox";
import HowToVoteIcon from "@mui/icons-material/HowToVote";
import GroupsIcon from "@mui/icons-material/Groups";
import BallotIcon from "@mui/icons-material/Ballot";
<<<<<<< HEAD
import {CardContainer, Container, Content, Graph, HeaderContainer} from '../../style/CardCss'
import {ContentWrapper} from '../../style/CardCss'
import {CardsContainerLeft} from '../../style/CardCss'
import {StyledCard} from '../../style/CardCss'
import {ChartContainer} from '../../style/CardCss'
import { useSelector } from "react-redux";
interface CardData {
  id: number;
  title: string;
  count: number;
  icon: JSX.Element;
}
const Cards: React.FC = () => {
  // const { allCandidates } = useSelector(
  //   (state: any) => state.candidate
  // );
  // console.log(allCandidates.candidates.length)
  
  const cards: CardData[] = [
    // {
    //   id: 1,
    //   title: "Candidate",
    //   // count:  allCandidates.candidates.length,
    //   icon: <AccountBoxIcon fontSize="large" sx={{ color: "#02B2AF" }} />,
    // },
    {
      id: 2,
      title: "Voters",
      count: 1000,
      icon: <HowToVoteIcon fontSize="large" sx={{ color: "#02B2AF" }} />,
    },
    {
      id: 3,
      title: "Party",
      count: 120,
      icon: <GroupsIcon fontSize="large" sx={{ color: "#02B2AF" }} />,
    },
    {
      id: 4,
      title: "Election",
      count: 5,
      icon: <BallotIcon fontSize="large" sx={{ color: "#02B2AF" }} />,
    },
  ];
  
  const chartData = cards.map((card) => card.count);
  const chartLabels = cards.map((card) => card.title);
  return (
    <Container>
      <Sidebar />
      <ContentWrapper>
        <CardContainer>
          {/* Cards on Left */}
          <CardsContainerLeft>
            {cards.map((card) => (
              <StyledCard key={card.id}>
                <CardActionArea>
                  <CardContent>
                    <HeaderContainer>
                      <Content variant="h6">
                        {card.title}
                      </Content>
                      {card.icon}
                    </HeaderContainer>
                    <Content variant="h4">
                      {card.count}
                    </Content>
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
=======
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

  const chartData = cardsConfig.map((card) => countsData[card.countKey]);
  const chartLabels = cardsConfig.map((card) => card.title);

  return (
    <Container>
      <CardWrapper>
        {cardsConfig.map((card) => (
          <StyledCard key={card.id}>
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
>>>>>>> cf4afc743fe89eaec57d6809ebef6b51b80c0486
    </Container>
  );
};

export default Cards;
