import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

const voterApi = createApi({
    reducerPath: 'voters',
    baseQuery: fetchBaseQuery({
        baseUrl: "http://localhost:8082/",
        prepareHeaders: (headers: any) => {
            const token = localStorage.getItem('token');

            if (token) {
                headers.set('Authorization', `Bearer ${token}`);
            }
         
            return headers;
        },
    }),
    tagTypes: ['Voters'],
    endpoints: (builder) => ({
        searchVoters: builder.query({
            query: ({ page = 0, size = 10, firstName, lastName, dateOfBirth, dmvNumber, ssnNumber }) => {
                const filters = { page, size, firstName, lastName, dateOfBirth, dmvNumber, ssnNumber };
                const queryParams = Object.entries(filters)
                    .filter(([_, value]) => value != null && value !== '')
                    .map(([key, value]) => `${key}=${encodeURIComponent(value)}`)
                    .join('&');
                return `voters?${queryParams}`;
            },
            transformResponse: (response: any) => {
                return {
                    data: response.data,    
                    totalElements: response.totalElements,
                };
            },
            providesTags: ['Voters'],
        }),

        registerVoter: builder.mutation({
            query: ({ post}: any) => {
                return {
                    url: 'voters',
                    method: 'POST',
                    body: post,
                }
            },
            invalidatesTags: ['Voters'],

        }),

        editVoter: builder.mutation({
            query: ({ voterId, post }: any) => {

            
                return {
                    url: `voters/${voterId}`,
                    method: 'PATCH',
                    body: post,
                };
            },
            invalidatesTags: ['Voters'],

        }),

    })
})


const statusApi = voterApi.injectEndpoints({
    endpoints: (builder) => ({
        status: builder.query({
            query: () => 'voters/status'
        }),
        towns: builder.query({
            query: () => 'voters/towns'
            }),
        counties: builder.query({
            query: () => 'voters/counties'
            }),
    }),
    overrideExisting: false, 
});


export const { useStatusQuery,useCountiesQuery,useTownsQuery } = statusApi;


export const {
    useEditVoterMutation,
    useRegisterVoterMutation,
    useSearchVotersQuery } = voterApi
export default voterApi




