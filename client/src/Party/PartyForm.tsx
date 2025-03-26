import { Box, Stack, Typography } from "@mui/material";
import { toast } from 'react-toastify';
import { useRegisterPartyMutation, useEditPartyMutation } from "../store/feature/party/partyAction";
import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import { NameField, NumberField } from "../Helpers/FormFields";
import { StyledButton } from "../style/CommanStyle";
import CloseIcon from "@mui/icons-material/Close";
import { MainHead, Form, Container, Row, GridContainer, SubmitButtonContainer, CloseIconButton, PartyFormContainer } from "../style/PartyStyle";
import UpdateDialog from "../components/ui/UpdateDialog"
import { FormImage } from "../Helpers/FormFields";
import { FormData } from "../Types/PartyForm.types";
import Button from "@mui/material/Button";

const defaultValues: FormData = {
    partyName: "",
    partyAbbreviation: "",
    partyFoundationYear: "",
    partyFounderName: "",
    partyWebSite: "",
    partySymbol: "",
    headQuarters: "",
    partyLeaderName: "",
};

const PartyForm = ({ party, onClose }: { party: any; onClose: () => void }) => {
    // State for UpdateDialog
    const [isDialogOpen, setIsDialogOpen] = useState(false);
    const [originalPartyData, setOriginalPartyData] = useState<Record<string, any>>({});
    const [updatedPartyData, setUpdatedPartyData] = useState<Record<string, any>>({});

    const { control, handleSubmit, formState, reset, getValues } = useForm({
        defaultValues,
        mode: "onTouched",
    });

    const { isSubmitting } = formState;

    useEffect(() => {
        setOriginalPartyData(party);
        if (party) {
            reset({
                ...defaultValues,
                ...party,
            });
        } else {
            reset(defaultValues);
        }
    }, [party, reset, getValues]);

    const [registerParty] = useRegisterPartyMutation();
    const [editParty] = useEditPartyMutation();

    const toBase64 = (file: File | string): Promise<string> =>
        new Promise((resolve, reject) => {
            if (typeof file === "string") return resolve(file);
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = () => resolve(reader.result?.toString().split(",")[1] || "");
            reader.onerror = (error) => reject(error);
        });

    const onSubmit = async (data: FormData) => {
        try {

            let base64PartySymbol = "";
            if (data.partySymbol) {
                base64PartySymbol = await toBase64(data.partySymbol);
            }

            const newFormData: Partial<FormData> = {
                ...data,
            };

            const result = await toast.promise(
                party?.partyId
                    ? editParty({ post: newFormData, img: base64PartySymbol ,partyId: party?.partyId }).unwrap()
                    : registerParty({ post: { ...newFormData, partySymbol: base64PartySymbol } }).unwrap(),
                {
                    pending: "please wait ...",
                    success: "Successful",
                }
            );

            if (result) {
                reset(defaultValues);
                onClose();
            }
        } catch (error) {
            console.error("Error Submitting Party:", error); // Detailed error logging
            toast.error("Error Submitting Party");
        }
    };

    const handleConfirmSubmit = async (data: any) => {
        if (party?.partyId) {
            setUpdatedPartyData(data);
            setOriginalPartyData(party);
            setIsDialogOpen(true);
        } else {
            onSubmit(data);
        }
    };

    const handleClose = () => {
        onClose();
    };

    return (
        <MainHead>
            <PartyFormContainer>
                <Typography align="center" variant="h5">
                    {party?.partyId ? "Update Party" : "Register Party"}
                </Typography>
                <CloseIconButton onClick={handleClose}>
                    <CloseIcon />
                </CloseIconButton>
            </PartyFormContainer>
            <form onSubmit={handleSubmit(handleConfirmSubmit)}>
                <Form>
                    <Container>
                        <Row>
                            <Box>
                                <NameField
                                    control={control}
                                    name="partyName"
                                    label="Party Name"
                                    minLength={3}
                                    maxLength={33}
                                />
                            </Box>
                            <Box>
                                <NameField
                                    control={control}
                                    name="partyLeaderName"
                                    label="Leader Name"
                                    minLength={3}
                                    maxLength={20}
                                />
                            </Box>
                        </Row>
                        <Row>
                            <Box>
                                <NameField
                                    control={control}
                                    name="partyFounderName"
                                    label="Founder Name"
                                    minLength={2}
                                    maxLength={20}
                                />
                            </Box>
                            <Box>
                                <NumberField
                                    control={control}
                                    name="partyFoundationYear"
                                    label="Foundation Year"
                                    fixedLength={4}
                                />
                            </Box>
                        </Row>
                        <Row>
                            <Box>
                                <NameField
                                    control={control}
                                    name="partyAbbreviation"
                                    label="Party Abbreviation"
                                    minLength={2}
                                    maxLength={7}
                                />
                            </Box>
                            <Box>
                                <NameField
                                    control={control}
                                    name="headQuarters"
                                    label="Headquarters"
                                    minLength={3}
                                    maxLength={1000}
                                />
                            </Box>
                        </Row>
                        <GridContainer>
                            <NameField
                                control={control}
                                name="partyWebSite"
                                label="Website"
                                minLength={3}
                                maxLength={100}
                            />
                        </GridContainer>
                        <Stack direction="column" sx={{ margin: '0 auto' }} >
                            <Typography variant="body1" color="text.secondary" align="left">
                                Party Logo
                            </Typography>
                            <Box sx={{ width: '100%' }}>
                                <FormImage
                                    control={control}
                                    name="partySymbol"
                                    label=""
                                />
                            </Box>
                        </Stack>
                        <SubmitButtonContainer>
                            <Button type="submit" variant="contained" disabled={isSubmitting}>
                                Submit
                            </Button>
                            <Button variant="contained" onClick={onClose}>
                                Cancel
                            </Button>
                        </SubmitButtonContainer>
                    </Container>
                </Form>
            </form>
            <UpdateDialog
                open={isDialogOpen}
                title="Confirm Party Update"
                handleClose={() => setIsDialogOpen(false)}
                handleConfirm={async (data) => {
                    await onSubmit(data); // Call the original onSubmit
                    setIsDialogOpen(false); 
                }}
                originalData={originalPartyData}
                updatedData={updatedPartyData}
            />
        </MainHead>
    );
};

export default PartyForm;
