import { VALID_EXTRAS } from "./enumExtras.js";


// Nos quedamos aquí
// ARREGLAR VALIDACIÓN EXTRA!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

const EXTRAS = Object.values(VALID_EXTRAS).join("|");



export const isValidExtra = (extra) => Object.values(EXTRAS).includes(extra);




