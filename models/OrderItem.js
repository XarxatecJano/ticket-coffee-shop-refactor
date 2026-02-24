import { isValidProduct, isValidSize, isValidExtra } from "../utils/domainValidators.js";
import { isValidNumber } from "../utils/validators.js";
import { assertValid } from "../exceptions/assertValid.js";

const parseExtra = (extraString) => {
    if(!extraString || extraString.length === 0) return [];

    const extrasArray = [];
    for(const extra of extraString.split(",")) {
        assertValid(isValidExtra(extra), `Invalid Extra`);
        extrasArray.push(extra);
    }
    return extrasArray;
};

export class OrderItem {
    constructor(itemString) {
        const [product, size, amount, extras = ""] = itemString.split("|");

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
