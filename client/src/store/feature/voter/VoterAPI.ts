import axios from "axios";

const api = axios.create({
    baseURL: "http://172.16.16.67:8081"
})
 
interface SearchVoterParams {
    page?: number;
    size?: number;
    firstName?: string;
    lastName?: string;
    dateOfBirth?: string;
    dmvNumber?: string;
    ssnNumber?: string;
} 

//for search voter
export const searchVoters = async({ page = 0, size = 10, firstName, lastName, dateOfBirth, dmvNumber, ssnNumber }: SearchVoterParams)=> {
   
    const filters = { page, size, firstName, lastName, dateOfBirth, dmvNumber, ssnNumber };
 
   
    const queryParams = Object.entries(filters)
        .filter(([key, value]) => value != null && value !== '')  
        .map(([key, value]) => `${key}=${encodeURIComponent(value)}`)
        .join('&');
 
    const apiUrl = `/api/voters/search?${queryParams}`;
 
 
    try {
        const response = await api.get(apiUrl);  
        // console.log(response.data.content)
        return response.data.content;  
      } catch (error) {
        console.error('Error fetching voter data:', error);
        throw error;
      }
}

//helper function to convert data URL to File
const dataURLtoFile = (dataURL: string, filename: string): File => {
    const [mimePart, base64Data] = dataURL.split(",");
    const mime = mimePart.match(/:(.*?);/)![1];
    const byteString = atob(base64Data);
    const arrayBuffer = new Uint8Array(byteString.length);
    for (let i = 0; i < byteString.length; i++) {
        arrayBuffer[i] = byteString.charCodeAt(i);
    }
    return new File([arrayBuffer], filename, { type: mime });
};
 
 
export const voterPost = async ({ post, img, sign }:any) => {
    const formData = new FormData();
 
    formData.append(
        "voter",
        new Blob([JSON.stringify(post)], { type: "application/json" })
    );
 
    if (img) {
        const profileFile = dataURLtoFile(img, "profile.jpg");
        formData.append("image", profileFile);
    }
    if (sign) {
        const signatureFile = dataURLtoFile(sign, "signature.jpg");
        formData.append("signature", signatureFile);
    }
 
    try {
        const response = await api.post("/api/voters/register", formData, {
            headers: {
                "Content-Type": "multipart/form-data",
            },
        });
 
        console.log("Voter registered successfully:", response.data);
        return response;
    } catch (error) {
        // console.error("Error registering voter:", error.response?.data || error.message);
        throw error;
    }
};