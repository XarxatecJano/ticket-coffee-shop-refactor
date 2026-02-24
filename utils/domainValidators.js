import { PRODUCTS } from "../models/enum/enumProducts.js";
import { SIZES } from "../models/enum/enumSizes.js";
import { VALID_EXTRAS } from "../models/enum/enumExtras.js";
import { isValidEnum } from "./validators.js";

export const isValidProduct = (product) => isValidEnum(PRODUCTS, product);
export const isValidSize = (size) => isValidEnum(SIZES, size);
export const isValidExtra = (extra) => isValidEnum(VALID_EXTRAS, extra);