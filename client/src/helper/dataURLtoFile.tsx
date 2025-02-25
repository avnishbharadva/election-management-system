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

export default dataURLtoFile;
