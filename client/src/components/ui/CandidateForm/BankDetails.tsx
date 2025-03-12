import { TextField } from "@mui/material";
import { FieldErrors, UseFormRegister } from "react-hook-form";
import { DividerStyle, Row, Section, Title } from "../../../style/CandidateFormCss";
import { IFormInput } from "../../../store/feature/candidate/types";

interface BankDetailsProps {
  register: UseFormRegister<IFormInput>;
  errors: FieldErrors<IFormInput>;
}

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
          error={!!errors.bankDetails?.bankName} // ✅ TypeScript will now recognize this
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
          error={!!errors.bankDetails?.bankAddress} // ✅ Ensuring `bankDetails` exists
          helperText={errors.bankDetails?.bankAddress?.message}
        />

      </Row>
    </Section>
  );
};

export default BankDetails;
