import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import dataURLtoFile from "../../../helper/dataURLtoFile";

const partyApi = createApi({
    reducerPath: 'partyApi',
    baseQuery: fetchBaseQuery({ baseUrl: "http://172.16.16.67:8081" }),
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
                    url: `/api/party/${partyId}`,
                    method: 'PATCH',
                    body: formData,
                }
                },
                invalidatesTags: ['party']
                }),
            
    })
})

export const { useRegisterPartyMutation, usePartyListQuery, usePartyByIdQuery,useEditPartyMutation } = partyApi;

export default partyApi