export interface CardConfig {
    id: number;
    title: string;
    icon: string; 
    countKey: keyof CountsData; 
  }
  
  export interface CountsData {
    candidates: number;
    voters: number;
    parties: number;
    elections: number;
  }
  
  export const cardsConfig: CardConfig[] = [
    {
      id: 1,
      title: "Candidate",
      icon: "AccountBoxIcon", 
      countKey: "candidates",
    },
    {
      id: 2,
      title: "Voters",
      icon: "HowToVoteIcon",
      countKey: "voters",
    },
    {
      id: 3,
      title: "Party",
      icon: "GroupsIcon",
      countKey: "parties",
    },
    {
      id: 4,
      title: "Election",
      icon: "BallotIcon",
      countKey: "elections",
    },
  ];
  