import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

const voterApi = createApi({
    reducerPath: 'voters',
    baseQuery: fetchBaseQuery({
        baseUrl: "http://localhost:8082",
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
                return `/api/voters/search?${queryParams}`;
            },
            transformResponse: (response: any) => {
                return {
                  data: response.content,
                  totalElements: response.totalElements,
                };
              },              
        }),

        registerVoter: builder.mutation({
            query: ({ post, img, sign }: any) => {
                let formData = new FormData();
                console.log(post)
                console.log(sign)
                console.log(img)

                const voterData = new Blob([JSON.stringify(post)], { type: "application/json" });
                formData.append('voter', voterData);

                if (img) {
                    // const profileFile =img;
                    formData.append('image', img);
                }

                if (sign) {
                
                    formData.append('signature', sign);
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
                    const profileFile = img;
                    formData.append('image', profileFile);
                }

                // If signature is provided, convert it and append it to the formData
                if (sign) {
                    const signatureFile = sign;
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


