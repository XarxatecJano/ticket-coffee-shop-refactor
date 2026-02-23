import { PRODUCTS } from "../items/enumProducts.js";
import { SIZES } from "../items/enumSizes.js";
import { VALID_EXTRAS } from "../items/enumExtras.js";
import { isValidEnum } from "./validators.js";

export const isValidProduct = (product) => isValidEnum(PRODUCTS, product);
export const isValidSize = (size) => isValidEnum(SIZES, size);
export const isValidExtra = (extra) => isValidEnum(VALID_EXTRAS, extra);