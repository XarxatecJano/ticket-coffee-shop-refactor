import { OrderItem } from "./OrderItem.js";
import { assertValid } from "../exceptions/assertValid.js";
import { isArrayNonEmpty } from "../utils/validators.js";
import { sanitizeString } from "../utils/sanitizers.js";

export class Order {
    constructor({ items = [], coupon = "", vip = false, happyHour = false }) {
        
        assertValid(isArrayNonEmpty(items), "The order cannot be empty and must contain a list of items.");
        this.items = items.map((itemString) => new OrderItem(itemString));
        this.coupon = sanitizeString(coupon);
        this.vip = Boolean(vip);
        this.happyHour = Boolean(happyHour)
    }
}
