import { useEffect, useState } from "react";
import {
  DividerStyle,
  Row,
  Section,
  Title,
} from "../../../style/CandidateFormCss";
import {
  Checkbox,
  FormControlLabel,
  TextField,
  Typography,
} from "@mui/material";
import { AddressFormProps} from "../../../store/feature/candidate/types";

export default function AddressForm({
  register,
  errors,
  watch,
  setValue,
}: AddressFormProps) {
  const [sameMailingAddress, setSameMailingAddress] = useState(false);
  const residentialAddress = watch("residentialAddress");

  useEffect(() => {
    if (sameMailingAddress) {
      setValue("mailingAddress", residentialAddress);
    }
  }, [sameMailingAddress, residentialAddress, setValue]);

  return (
    <>
      <Section>
        <Title variant="h6">Residential Address</Title>
        <DividerStyle />
        <Row>
          <TextField
            fullWidth
            label="Street"
            {...register("residentialAddress.street", {
              required: "Street is required",
            })}
            InputLabelProps={{ shrink: true }}
            error={!!errors.residentialAddress?.street}
            helperText={errors.residentialAddress?.street?.message}
          />
          <TextField
            fullWidth
            label="City"
            {...register("residentialAddress.city", {
              required: "City is required",
            })}
            InputLabelProps={{ shrink: true }}
            error={!!errors.residentialAddress?.city}
            helperText={errors.residentialAddress?.city?.message}
          />
          <TextField
            fullWidth
            label="Zipcode"
            {...register("residentialAddress.zipcode", {
              required: "Zipcode is required",
              validate: (value) =>
                /^\d{5}$/.test(value.toString()) || "Zipcode must be 5 digits",
            })}
            InputLabelProps={{ shrink: true }}
            error={!!errors.residentialAddress?.zipcode}
            helperText={errors.residentialAddress?.zipcode?.message}
          />
        </Row>
      </Section>
      <Section>
        <Typography>
          <FormControlLabel
            control={
              <Checkbox
                checked={sameMailingAddress}
                onChange={(e) => setSameMailingAddress(e.target.checked)}
              />
            }
            label="Same as Residential Address"
          />
        </Typography>
      </Section>     
      <Section>
        <Title variant="h6">Mailing Address</Title>
        <DividerStyle />
        <Row>
          <TextField
            fullWidth
            label="Street"
            {...register("mailingAddress.street", {
              required: !sameMailingAddress && "Street is required",
            })}
            InputLabelProps={{ shrink: true }}
            error={!!errors.mailingAddress?.street}
            helperText={errors.mailingAddress?.street?.message}
            disabled={sameMailingAddress}
          />
          <TextField
            fullWidth
            label="City"
            {...register("mailingAddress.city", {
              required: !sameMailingAddress && "City is required",
            })}
            InputLabelProps={{ shrink: true }}
            error={!!errors.mailingAddress?.city}
            helperText={errors.mailingAddress?.city?.message}
            disabled={sameMailingAddress}
          />
          <TextField
            fullWidth
            label="Zipcode"
            {...register("mailingAddress.zipcode", {
              required: !sameMailingAddress && "Zipcode is required",
              validate: (value) =>
                /^\d{5}$/.test(value.toString()) || "Zipcode must be 5 digits",
            })}
            InputLabelProps={{ shrink: true }}
            error={!!errors.mailingAddress?.zipcode}
            helperText={errors.mailingAddress?.zipcode?.message}
            disabled={sameMailingAddress}
          />
        </Row>
      </Section>
    </>
  );
}
