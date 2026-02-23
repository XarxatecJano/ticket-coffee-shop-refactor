export const isNumberValid = (number) => {
    return Number.isInteger(number) && number > 0;
};