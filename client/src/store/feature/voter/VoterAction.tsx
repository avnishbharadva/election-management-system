import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import dataURLtoFile from "../../../helper/dataURLtoFile";


const voterApi = createApi({
    reducerPath: 'voters',
    baseQuery: fetchBaseQuery({
        baseUrl: "http://172.16.16.67:8081",
    }),
    tagTypes: ['Voters'],
    endpoints: (builder) => ({
        searchVoters: builder.query({
            query: ({ page = 0, size = 10, firstName, lastName, dateOfBirth, dmvNumber, ssnNumber }) => {
                const filters = { page, size, firstName, lastName, dateOfBirth, dmvNumber, ssnNumber };
                const queryParams = Object.entries(filters)
                    .filter(([key, value]) => value != null && value !== '')
                    .map(([key, value]) => `${key}=${encodeURIComponent(value)}`)
                    .join('&');
                console.log(queryParams)
                return `/api/voters/search?${queryParams}`;
            },
            transformResponse: (response: any) => {
                console.log('Raw Response:', response); // Log the response from the server
                return {
                  data: response.content,
                  totalElements: response.totalElements,
                };
              },
              
             
        }),

        registerVoter: builder.mutation({
            query: ({ post, img, sign }: any) => {
                let formData = new FormData();

                const voterData = new Blob([JSON.stringify(post)], { type: "application/json" });
                formData.append('voter', voterData);

                if (img) {
                    const profileFile = dataURLtoFile(img, "profile.jpg");
                    formData.append('image', profileFile);
                }

                if (sign) {
                    const signatureFile = dataURLtoFile(sign, "signature.jpg");
                    formData.append('signature', signatureFile);
                }

                return {
                    url: '/api/voters/register',
                    method: 'POST',
                    body: formData,
                }


            },
       
        }),




        editVoter: builder.mutation({
            query: ({ voterId, post, img, sign }: any) => {

                const formData = new FormData()

                formData.append(
                    "voter",
                    new Blob([JSON.stringify(post)], { type: "application/json" })
                )

                if (img) {
                    const profileFile = dataURLtoFile(img, "profile.jpg");
                    formData.append('image', profileFile);
                }

                // If signature is provided, convert it and append it to the formData
                if (sign) {
                    const signatureFile = dataURLtoFile(sign, "signature.jpg");
                    formData.append('signature', signatureFile);
                }
                return {
                    url: `api/voters/${voterId}`,
                    method: "PATCH",
                    body: formData,

                }
            },
          


        })
    })
})
export const { useEditVoterMutation,
    useRegisterVoterMutation,
    useSearchVotersQuery } = voterApi
export default voterApi


