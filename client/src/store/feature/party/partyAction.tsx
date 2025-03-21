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
          },
     }),  
    tagTypes: ['party'],
   
    endpoints: (builder) => ({
 
        PartyList: builder.query({
            query: () => 'party',
            providesTags: ['party'],
        }
        ),
        PartyById: builder.query({
            query: (partyId) => `party/${partyId}`
        }),
 
        registerParty: builder.mutation({
            query: ({ post, img }: any) => {
                if(!img){
                    console.error("party symbole is required")
                }
                const formData = {...post ,partySymbol: img}
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
            console.log(formData)
                return {
                    url: `party/${partyId}`,
                    method: 'PATCH',
                    body: formData,
                }
                },
                invalidatesTags: ['party'],
                transformResponse: (response: any, meta, arg) => {
                    console.log('Response:', response); 
                    console.log('Meta:', meta); 
                    console.log('Args:', arg); 
                    return {
                      data: response,
                      metadata: {
                        responseReceivedAt: new Date().toISOString(),
                        requestArgs: arg, 
                    
                      },             

                }}
            }),
           
    })
})
 
export const { useRegisterPartyMutation, usePartyListQuery, usePartyByIdQuery,useEditPartyMutation } = partyApi;
 
export default partyApi