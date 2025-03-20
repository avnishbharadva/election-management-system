import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { 
  FormControl, InputLabel, Select, MenuItem, 
  FormHelperText, TextField 
} from "@mui/material";
import { Controller } from "react-hook-form";
import { fetchAllElection } from "../../../store/feature/election/electionApi";
import { RootState, AppDispatch } from "../../../store/app/store";
import { DividerStyle, Row, Section, Title } from "../../../style/CandidateFormCss";
import axiosInstance from "../../../store/app/axiosInstance";

const ElectionDetails = ({ control, errors, register }: any) => {
  const dispatch: AppDispatch = useDispatch();
  const elections = useSelector((state: RootState) => state.election.elections) || [];
  const [parties, setParties] = useState<{ partyId: number; partyName: string }[]>([]);

  const [dropdownOpened, setDropdownOpened] = useState(false);

  useEffect(() => {
    if (dropdownOpened) {
      dispatch(fetchAllElection());
      fetchParties();
    }
  }, [dropdownOpened, dispatch]);

  const fetchParties = async () => {
    try {
      const response = await axiosInstance.get<{ partyId: number; partyName: string }[]>("/party");
      setParties(response?.data?.data || []);
    } catch (error) {
      console.error("Error fetching parties:", error);
      setParties([]);
    }
  };

  return (
    <Section>
      <Title variant="h6">Election Details</Title>
      <DividerStyle />
      <Row>
        <FormControl fullWidth error={!!errors.electionId}>
          <InputLabel id="election-label">Election Type</InputLabel>
          <Controller
            name="electionId"
            control={control}
            rules={{ required: "Election type is required" }}
            render={({ field }) => (
              <Select {...field} 
                onOpen={() => setDropdownOpened(true)} 
                labelId="election-label" 
                label="Election Type"
                value={field.value ?? ""}
              >
                {elections.length > 0 ? (
                  elections.map((e: any) => (
                    <MenuItem key={e.electionId} value={e.electionId}>
                      {e.electionName}
                    </MenuItem>
                  ))
                ) : (
                  <MenuItem disabled>No elections found</MenuItem>
                )}
              </Select>
            )}
          />
          {errors.electionId && <FormHelperText>{errors.electionId.message}</FormHelperText>}
        </FormControl>

        <FormControl fullWidth error={!!errors.partyId}>
          <InputLabel id="party-label">Party</InputLabel>
          <Controller
            name="partyId"
            control={control}
            rules={{ required: "Party selection is required" }}
            render={({ field }) => (
              <Select
              {...field}
              labelId="party-label"
              label="Party"
              value={ field.value ?? ""} // Ensures valid value
              onOpen={() => setDropdownOpened(true)}
              onChange={(event) => field.onChange(event.target.value)}
            >
                {parties.length > 0 ? (
                  parties.map((party) => (
                    <MenuItem key={party.partyId} value={party.partyId}>
                      {party.partyName}
                    </MenuItem>
                  ))
                ) : (
                  <MenuItem disabled>No parties found</MenuItem>
                )}
              </Select>
            )}
          />
          {errors.partyId && <FormHelperText>{errors.partyId.message}</FormHelperText>}
        </FormControl>
        <TextField
          fullWidth
          label="State"
          value="New York"
          {...register("stateName", {
            required: "State name is required",
            validate: (value: string) => value === "New York" || "State name must be New York",
          })}
          error={!!errors.stateName}
          helperText={errors.stateName?.message}
          InputLabelProps={{ shrink: true }}
          disabled
        />
      </Row>
    </Section>
  );
};

export default ElectionDetails;

