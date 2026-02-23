import {
    isValidProduct,
    isValidSize,
    isValidExtra,
} from "../utils/domainValidators.js";
import { assertValid } from "../exceptions/assertValid.js";

export class OrderItem {
    constructor(itemString) {
        const [product, size, amount, extras = ""] = itemString.split("|");

        // Check product;
        assertValid(isValidProduct(product), `Invalid product: ${product}`);
        this.product = product;

        // Check size;
        assertValid(isValidSize(size), `Invalid size: ${size}`);
        this.size = size;

        // Check amount;
        const parsedAmount = parseInt(amount || "1", 10);
        assertValid(isNumberValid(parsedAmount), `Invalid Amount: ${amount}`)
        this.amount = parsedAmount;

        // Check extras;
        this.extras = [];
        if (extras.length > 0) {
            const extrasArray = extras.split(",");
            for (const extra of extrasArray) {
                assertValid(isValidExtra(extra), `Invalid Extra`);
                this.extras.push(extra);
            }
        }
    }
}
