import { PRODUCTS } from "./enumProducts.js";

export const isValidProduct = (product) => Object.values(PRODUCTS).includes(product);