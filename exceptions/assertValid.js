export const assertValid = (isValid, message) => {
    if (!isValid) {
        throw new Error(`+ ERROR! ${message}`);
    }
};
