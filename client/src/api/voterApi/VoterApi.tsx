// import axios from 'axios'


// const api = axios.create({
//     baseURL: "http://172.16.16.67:8081"
// })

// ///for search voter
// function searchVoters({ page = 1, size = 10, firstName, lastName, dateOfBirth, dmvNumber, ssnNumber }) {
//     // Create an object with the filters to pass in the query
//     const filters = { page, size, firstName, lastName, dateOfBirth, dmvNumber, ssnNumber };

//     // Remove any filters with undefined or null values
//     const queryParams = Object.entries(filters)
//         .filter(([key, value]) => value != null && value !== '')  // Exclude empty or null values
//         .map(([key, value]) => `${key}=${encodeURIComponent(value)}`)  // Format key=value
//         .join('&');  // Join all key=value pairs with "&"

//     // Construct the API URL with the dynamically generated query string
//     const apiUrl = `/api/voters/search?${queryParams}`;

//     // Make the API request
//     api.get(apiUrl)
//         .then(response => {
//             console.log(response.data); // Handle the response here
//         })
//         .catch(error => {
//             console.error('Error fetching voter data:', error);
//         });
// }



// // // Helper function to convert Data URL to File
// const dataURLtoFile = (dataURL: string, filename: string): File => {
//     const [mimePart, base64Data] = dataURL.split(",");
//     const mime = mimePart.match(/:(.*?);/)![1];
//     const byteString = atob(base64Data);
//     const arrayBuffer = new Uint8Array(byteString.length);
//     for (let i = 0; i < byteString.length; i++) {
//         arrayBuffer[i] = byteString.charCodeAt(i);
//     }
//     return new File([arrayBuffer], filename, { type: mime });
// };


// export const voterPost = async ({ post, img, sign }) => {
//     const formData = new FormData();


//     formData.append(
//         "voter",
//         new Blob([JSON.stringify(post)], { type: "application/json" })
//     );

//     if (img) {
//         const profileFile = dataURLtoFile(img, "profile.jpg");
//         formData.append("image", profileFile);
//     }
//     if (sign) {
//         const signatureFile = dataURLtoFile(sign, "signature.jpg");
//         formData.append("signature", signatureFile);
//     }

//     try {
//         const response = await api.post("/api/voters/register", formData, {
//             headers: {
//                 "Content-Type": "multipart/form-data",
//             },
//         });

//         console.log("Voter registered successfully:", response.data);
//         return response.data;
//     } catch (error) {
//         console.error("Error registering voter:", error.response?.data || error.message);
//         throw error;
//     }
// };
