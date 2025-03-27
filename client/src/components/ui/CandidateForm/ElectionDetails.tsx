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

const ElectionDetails = ({ control, errors, register, setValue }: any) => {
  const dispatch: AppDispatch = useDispatch();
  const elections = useSelector((state: RootState) => state.election.elections) || [];
  const { candidate } = useSelector((state: RootState) => state.candidate);

  const [parties, setParties] = useState<{ partyId: number; partyName: string }[]>([]);
  const [selectedElectionId, setSelectedElectionId] = useState<number | "">("");
  const [selectedPartyId, setSelectedPartyId] = useState<number | "">("");
  const [dropdownOpened, setDropdownOpened] = useState(false);

  useEffect(() => {
    if (candidate) {
      setSelectedElectionId(candidate.electionId || "");
      setSelectedPartyId(candidate.partyId || "");
      setValue("electionId", candidate.electionId || "");
      setValue("partyId", candidate.partyId || "");
    }
  }, [candidate, setValue]);

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
              <Select
                {...field}
                labelId="election-label"
                label="Election Type"
                value={selectedElectionId || ""}
                onOpen={() => setDropdownOpened(true)}
                onChange={(event) => {
                  setSelectedElectionId(event.target.value === "" ? "" : Number(event.target.value));
                  field.onChange(event.target.value);
                }}
              >
                {selectedElectionId && !elections.some(e => e.electionId === selectedElectionId) && (
                  <MenuItem key={selectedElectionId} value={selectedElectionId}>
                    {candidate?.electionName || "Selected Election"}
                  </MenuItem>
                )}
                {elections.length > 0 ? (
                  elections.map((e) => (
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
                value={selectedPartyId || ""}
                onOpen={() => setDropdownOpened(true)}
                onChange={(event) => {
                  setSelectedPartyId(event.target.value === "" ? "" : Number(event.target.value));
                  field.onChange(event.target.value);
                }}
              >
                {selectedPartyId && !parties.some(p => p.partyId === selectedPartyId) && (
                  <MenuItem key={selectedPartyId} value={selectedPartyId}>
                    {candidate?.partyName || "Selected Party"}
                  </MenuItem>
                )}
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
