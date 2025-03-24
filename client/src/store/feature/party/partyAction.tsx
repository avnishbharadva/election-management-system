import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

 
 
const partyApi = createApi({
    reducerPath: 'partyApi',
    baseQuery: fetchBaseQuery({ baseUrl: "http://localhost:8082/",
        prepareHeaders: (headers:any) => {
            const token = localStorage.getItem('token');
            if (token) {
              headers.set('Authorization', `Bearer ${token}`);
            }
            return headers;             
            //                    
          },
     }),  
    tagTypes: ['party'],
   
    endpoints: (builder) => ({
 
            PartyList: builder.query({
                query: () => {
                    console.log('Fetching data for party...'); // Log request or other information
                    return 'party';
                }, 
                providesTags: ['party'],
            }
            ),
        
        PartyById: builder.query({
            query: (partyId) => `party/${partyId}`,
            providesTags: ['party']
        }),
 
        registerParty: builder.mutation({
            query: ({ post }: any) => {
                
                const formData = {...post }
                console.log(formData)
                 return {
                    url: 'party',
                    method: 'POST',
                    body: formData,
                }
 
            },
            invalidatesTags: ['party']
        }),
        editParty: builder.mutation({
            query: ({ post, img, partyId }: any) => {
                const formData = {...post ,partySymbol: img}
                return {
                    url: `party/${partyId}`,
                    method: 'PATCH',
                    body: formData,
                }
                },
                invalidatesTags: ['party'],
                transformResponse: (response: any, arg) => {            
                    return {
                      data: response,
                      metadata: {
                        responseReceivedAt: new Date().toISOString(), // Client-side metadata
                        requestArgs: arg, // Include original args in response
                   
                      },            
 
                }}
            }),

            deleteParty: builder.mutation({
                query: (partyId) => ({
                    url: `party/${partyId}`,
                    method: 'DELETE',
                }),
                invalidatesTags: ['party'],
            }),
            
           
    })
})
 
export const { useRegisterPartyMutation, usePartyListQuery, usePartyByIdQuery,useEditPartyMutation, useDeletePartyMutation } = partyApi;
 
export default partyApi