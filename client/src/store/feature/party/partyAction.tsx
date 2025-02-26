import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import dataURLtoFile  from "../../../helper/dataURLtoFile";

 const partyApi = createApi({
    reducerPath: 'partyApi',
    baseQuery: fetchBaseQuery({ baseUrl: "http://172.16.16.67:8081" }),
    endpoints: (builder) => ({

        PartyList: builder.query({
            query: () => '/api/party'
        }),

        PartyById:builder.query({
            query: (partyId) => `/api/party/${partyId}`
        }),

        registerParty: builder.mutation({
            query: ({ post, img }: any) => {  
                const formData = new FormData()

                formData.append(
                    "party",    
                    new Blob([JSON.stringify(post)], { type: "application/json" })
                )
                if (img) {
                    const profileFile = dataURLtoFile(img, "profile.jpg");
                    formData.append('image', profileFile);
                }
                return {
                    url: '/api/party',
                    method: 'POST',
                    body: formData,        
                }
            }
        })
    })
})

export const {useRegisterPartyMutation,  usePartyListQuery , usePartyByIdQuery} = partyApi;

export default partyApi