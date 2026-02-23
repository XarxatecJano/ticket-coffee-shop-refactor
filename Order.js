import { OrderItem } from "./items/OrderItem.js";
import { isArrayNonEmpty } from "./utils/arrayValidators.js";

export class Order {
    constructor({ items = [], coupon = "", vip = false, happyHour = false }) {
        isArrayNonEmpty(items, "The order cannot be empty and must contain a list of items.")
        
        this.items = items.map((itemString) => new OrderItem(itemString));
        this.coupon = typeof coupon === "string" ? coupon.trim() : "";
        this.vip = Boolean(vip);
        this.happyHour = Boolean(happyHour)
    }
}
