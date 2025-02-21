import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import dataURLtoFile  from "../../../helper/dataURLtoFile.tsx"

const voterApi = createApi({
    reducerPath: 'voters',
    baseQuery: fetchBaseQuery({ baseUrl: "http://172.16.16.67:8081" }),
    endpoints: (builder) => ({
        searchVoters: builder.query({
            query: ({ page = 0, size = 10, firstName, lastName, dateOfBirth, dmvNumber, ssnNumber }) => {

                const filters = { page, size, firstName, lastName, dateOfBirth, dmvNumber, ssnNumber };
                
                const queryParams = Object.entries(filters)
                    .filter(([key, value]) => value != null && value !== '')
                    .map(([key, value]) => `${key}=${encodeURIComponent(value)}`)
                    .join('&');

                return `/api/voters/search?${queryParams}`
            },
            transformResponse: (response: any) => response.content,
        }),

        registerVoter: builder.mutation({
            query: ({ post, img, sign }: any) => {
                const formData = new FormData()

                formData.append(
                    "voter",
                    new Blob([JSON.stringify(post)], { type: "application/json" })
                )

                img ? formData.append('image', dataURLtoFile(img, 'profile.jpg')) : console.error(" voter image is not defined");

                sign ? formData.append('signature', dataURLtoFile(sign, 'signature.jpg')) : console.error(" voter signature is not defined");

                return {
                    url: '/api/voters/register',
                    method: 'POST',
                    body: formData,
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                }

            }


        }),

        editVoter: builder.mutation({
            query: ({ post, img, sign }: any) => {

                const formData = new FormData()

                formData.append(
                    "voter",
                    new Blob([JSON.stringify(post)], { type: "application/json" })
                )

                img ? formData.append('image', dataURLtoFile(img, 'profile.jpg')) : console.error("image is not defined");

                sign ? formData.append('signature', dataURLtoFile(sign, 'signature.jpg')) : console.error("signature is not defined");

                return {
                    url:   `api/voters/${post.voterId}`,
                    method:"PATCH",
                    body: formData,
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                }
            }
        })
    })
})
export const { useEditVoterMutation ,useRegisterVoterMutation , useSearchVotersQuery } = voterApi
export default voterApi