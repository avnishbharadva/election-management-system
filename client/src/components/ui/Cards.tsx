import React from "react";
import Sidebar from "./Sidebar";
import {  CardContent ,CardActionArea } from "@mui/material";
import AccountBoxIcon from "@mui/icons-material/AccountBox";
import HowToVoteIcon from "@mui/icons-material/HowToVote";
import GroupsIcon from "@mui/icons-material/Groups";
import BallotIcon from "@mui/icons-material/Ballot";
import {CardContainer, Container, Content, Graph, HeaderContainer} from '../../style/CardCss'
import {ContentWrapper} from '../../style/CardCss'
import {CardsContainerLeft} from '../../style/CardCss'
import {StyledCard} from '../../style/CardCss'
import {ChartContainer} from '../../style/CardCss'
interface CardData {
  id: number; 
  title: string;
  count: number;
  icon: JSX.Element;
}

const cards: CardData[] = [
  {
    id: 1,
    title: "Candidate",
    count: 4021,
    icon: <AccountBoxIcon fontSize="large" sx={{ color: "#02B2AF" }} />,
  },
  {
    id: 2,
    title: "Voters",
    count: 15000,
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

const Cards: React.FC = () => {
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

          {/* Chart on Right */}
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
