import { isValidProduct, isValidSize, isValidExtra } from "../utils/domainValidators.js";
import { isValidNumber } from "../utils/validators.js";
import { assertValid } from "../exceptions/assertValid.js";
import { sanitizeString } from "../utils/sanitizers.js";

const parseExtra = (extraString) => {
    if(!extraString || extraString.length === 0) return [];

    const extrasArray = [];
    for(const extra of extraString.split(",")) {
        const extraClean = sanitizeString(extra);
        assertValid(isValidExtra(extraClean), `Invalid Extra`);
        extrasArray.push(extraClean);
    }
    return extrasArray;
};

export class OrderItem {
    constructor(itemString) {
        let [product, size, amount, extras = ""] = itemString.split("|");
        product = sanitizeString(product);
        size = sanitizeString(size);

        assertValid(isValidProduct(product), `Invalid product: ${product}`);
        this.product = product;

        assertValid(isValidSize(size), `Invalid size: ${size}`);
        this.size = size;

        const parsedAmount = parseInt(amount || "1", 10);
        assertValid(isValidNumber(parsedAmount), `Invalid Amount: ${amount}`);
        this.amount = parsedAmount;
        this.extras = parseExtra(extras);
    }
}
