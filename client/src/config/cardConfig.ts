export interface CardConfig {
    id: number;
    title: string;
    icon: string; 
    countKey: keyof CountsData; 
    link:string;
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
      title: "Candidates",
      icon: "AccountBoxIcon", 
      countKey: "candidates",
      link:'/dashboard/candidates'

    },
    {
      id: 2,
      title: "Voters",
      icon: "HowToVoteIcon",
      countKey: "voters",
       link:'/dashboard/voters'
    },
    {
      id: 3,
      title: "Parties",
      icon: "GroupsIcon",
      countKey: "parties",
       link:'/dashboard/parties'
    },
    {
      id: 4,
      title: "Elections",
      icon: "BallotIcon",
      countKey: "elections",
       link:'/dashboard/elections'
    },
  ];
  