

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
}

export interface FormValues {
    electionName: string;
    electionType: string;
    electionDate: string;
    electionState: string;
    totalSeats: number;
  }