export const isValidEnum = (enumObject, value) =>
    Object.values(enumObject).includes(value);

export const isArrayNonEmpty = (arrayCheck) =>
    Array.isArray(arrayCheck) && arrayCheck.length > 0;

export const isValidNumber = (number) => 
    Number.isInteger(number) && number > 0;
