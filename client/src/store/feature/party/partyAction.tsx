import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import dataURLtoFile from '../../../Helpers/dataURLtoFile';

 
const partyApi = createApi({
    reducerPath: 'partyApi',
    baseQuery: fetchBaseQuery({ baseUrl: "http://localhost:8082",
        prepareHeaders: (headers:any) => {
            const token = localStorage.getItem('token');
            console.log(token)
            if (token) {
              headers.set('Authorization', `Bearer ${token}`);
            }
            return headers;
          },
     }),  
    tagTypes: ['party'],
   
    endpoints: (builder) => ({
 
        PartyList: builder.query({
            query: () => '/api/party',
            providesTags: ['party'],
        }
        ),
        PartyById: builder.query({
            query: (partyId) => `/api/party/${partyId}`
        }),
 
        registerParty: builder.mutation({
            query: ({ post, img }: any) => {
                const formData = new FormData()
 
                formData.append(
                    "party",
                    new Blob([JSON.stringify(post)], { type: "application/json" })
                    // post,
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
 
            },
            invalidatesTags: ['party']
        }),
        editParty: builder.mutation({
            query: ({ post, img, partyId }: any) => {
                const formData = {...post ,partySymbol: img}
            console.log(formData)
                return {
                    url: `/api/party/${partyId}`,
                    method: 'PATCH',
                    body: formData,
                }
                },
                invalidatesTags: ['party'],
                transformResponse: (response: any, meta, arg) => {
                    console.log('Response:', response); // The server response
                    console.log('Meta:', meta); // Additional fetch metadata (e.g., headers)
                    console.log('Args:', arg); // Original arguments (post, img, metadata)
                    return {
                      data: response,
                      metadata: {
                        responseReceivedAt: new Date().toISOString(), // Client-side metadata
                        requestArgs: arg, // Include original args in response
                    
                      },             

                }}
            }),
           
    })
})
 
export const { useRegisterPartyMutation, usePartyListQuery, usePartyByIdQuery,useEditPartyMutation } = partyApi;
 
export default partyApi