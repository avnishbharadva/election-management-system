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
  election?: any[];
  elections: [], 
  currentPage: 0;
  rowsPerPage: 5;
  totalElements: 0;
}

export interface FormValues {
    electionId: number;
    electionName: string;
    electionType: string;
    electionDate: string;
    electionState: string;
    totalSeats: number;
}