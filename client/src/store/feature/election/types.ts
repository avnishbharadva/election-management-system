export interface Election {
  electionName: string;
  electionType: string;
  electionDate: string;
  electionState: string;
  totalSeats: number;
}

export interface ElectionState {
  loading: boolean;
  error: string | null;
  success: boolean;
  elections?: { elections: any[] }, 
  currentPage: number;
  totalPages:number;
  totalRecords:number;
  perPage:number;
  sortDir: string;
}

export interface FormValues {
    electionId: number;
    electionName: string;
    electionType: string;
    electionDate: string;
    electionState: string;
    totalSeats: number;
}
