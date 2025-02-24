import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import dataURLtoFile  from "../../../helper/dataURLtoFile";

 const partyApi = createApi({
    reducerPath: 'partyApi',
    baseQuery: fetchBaseQuery({ baseUrl: "http://172.16.16.67:8081" }),
    endpoints: (builder) => ({

        PartyList: builder.query({
            query: () => '/api/party'
        }),

        PaetyById:builder.query({
            query: (partyId) => `/api/party/${partyId}`
        }),

        registerParty: builder.mutation({
            query: ({ post, img }: any) => {  
                const formData = new FormData()

                formData.append(
                    "party",    
                    new Blob([JSON.stringify(post)], { type: "application/json" })
                )

                img ? formData.append('image', dataURLtoFile(img, 'profile.jpg')) : console.error(" voter image is not defined");

                return {
                    url: '/api/party',
                    method: 'POST',
                    body: formData,
                    header: {
                        'Content-Type': 'multipart/form-data'
                    }
                }
            }
        })
    })
})

export const {useRegisterPartyMutation, usePartyListQuery} = partyApi

export default partyApi