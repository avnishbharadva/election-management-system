import { TextField } from "@mui/material";
import { DividerStyle, Row, Section, Title } from "../../../style/CandidateFormCss";
import { BankDetailsProps } from "../../../store/feature/candidate/types";

const BankDetails = ({ register, errors }: BankDetailsProps) => {
  return (
   <Section>
      <Title variant="h6">Bank Details</Title>
      <DividerStyle />
      <Row>
      <TextField
          style={{width: '15.4rem'}}
          fullWidth
          label="Bank Name"
          {...register("bankDetails.bankName", {
            required: "Bank name is required",
          })}
          InputLabelProps={{ shrink: true }}
          error={!!errors.bankDetails?.bankName} 
          helperText={errors.bankDetails?.bankName?.message}
        />
        <TextField
          style={{width: '15.4rem'}}
          fullWidth
          label="Bank Address"
          {...register("bankDetails.bankAddress", {
            required: "Bank address is required",
          })}
          InputLabelProps={{ shrink: true }}
          error={!!errors.bankDetails?.bankAddress} 
          helperText={errors.bankDetails?.bankAddress?.message}
        />
      </Row>
    </Section>
  );
};

export default BankDetails;
