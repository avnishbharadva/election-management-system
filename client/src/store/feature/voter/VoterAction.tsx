import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

const voterApi = createApi({
    reducerPath: 'voters',
    baseQuery: fetchBaseQuery({
        baseUrl: "http://localhost:8082/api/",
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
                return `voters/search?${queryParams}`;
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
            query: ({ post, img, sign }: any) => {
                console.log(img)
                console.log(sign)
                if (!img) {
                    console.error('voter image is not found')
                }

                if (!sign) {
                    console.error('voter signature is not found')
                }
                const formData = { ...post, image: img, signature: sign }
                console.log(formData)

                return {
                    url: 'voters',
                    method: 'POST',
                    body: formData,
                }
            },
            invalidatesTags: ['Voters'],

        }),

        editVoter: builder.mutation({
            query: ({ voterId, post, image, signature }: any) => {

                !image && console.error('in voter update image is undefine')
                !signature && console.error('in voter update  sign  is undefine')
                const payload = {
                    ...post,
                    image: image ? image : undefined,
                    signature: signature ? signature : undefined,
                };
            
                return {
                    url: `voters/${voterId}`,
                    method: 'PATCH',
                    body: payload,
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
    }),
    overrideExisting: false, 
});


export const { useStatusQuery } = statusApi;


export const {
    useEditVoterMutation,
    useRegisterVoterMutation,
    useSearchVotersQuery } = voterApi
export default voterApi




