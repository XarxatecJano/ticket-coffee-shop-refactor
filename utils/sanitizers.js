export const sanitizeString = (word) => {
    if(typeof word !== "string") return "";
    return word.trim()
}