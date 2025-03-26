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
                query: () => {
                    return 'party';
                }, 
                providesTags: ['party'],
            }
            ),                      
 
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
                transformResponse: (response: any) => {            
                    return {
                      data: response,       
 
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
 
export const { useRegisterPartyMutation, usePartyListQuery, useEditPartyMutation, useDeletePartyMutation } = partyApi;
 
export default partyApi