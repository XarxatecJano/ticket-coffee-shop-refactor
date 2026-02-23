import { assertValid } from "../exceptions/assertValid.js"; 

export const isArrayNonEmpty = (arrayCheck, message) => {
    assertValid(Array.isArray(arrayCheck) && arrayCheck.length > 0, message);
}