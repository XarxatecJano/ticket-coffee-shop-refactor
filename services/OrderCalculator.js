import { BASE_PRICES, EXTRA_PRICES, COUPON, VIP_COUPON, calcIVA } from "../config/pricing.js";
import { PRODUCTS } from "../models/enum/enumProducts.js"


const calcItemPrice = (item, isHappyHour) => {
    let itemPrice = BASE_PRICES[item.product][item.size];

    if(item.product === PRODUCTS.coffee && isHappyHour) {
        itemPrice *= COUPON.HAPPY_HOUR;
    }

    let extraPrice = 0;
    for (const extra of item.extras) {
        extraPrice += EXTRA_PRICES[extra];
    }

    const BASE_TOTAL = (itemPrice + extraPrice) * item.amount;
    return BASE_TOTAL;
};

const applyCoupon = (subtotal, coupon, items) => {
    if (!coupon) return subtotal;

    if (coupon === "SAVE10") {
        return subtotal * COUPON.SAVE_COUPON;
    }

    if (coupon === "FREEMUFFIN") {
        const hasMuffin = items.some((item) => item.product === PRODUCTS.muffin);
        if (hasMuffin) {
            return subtotal - COUPON.FREE_MUFFIN;
        }
    }
    return subtotal;
};

const applyVipCoupon = (subtotal, isVip) => {
    if (isVip && subtotal > VIP_COUPON.MIN_ORDER) {
        return subtotal - VIP_COUPON.VIP;
    }
    return subtotal;
};

export class OrderCalculator {
    static calculateTotal(order) {
        let subtotal = 0;

        for(const item of order.items) {
            subtotal += calcItemPrice(item, order.happyHour)
        }

        subtotal = applyCoupon(subtotal, order.coupon, order.items)
        subtotal = applyVipCoupon(subtotal, order.vip)

        const totalPrice = calcIVA(subtotal);

        return totalPrice;
    }
}

// MAGIC TEXT!!!!!!!!!!!!!!!!!!!!!! coupon === "SAVE10" coupon === "FREEMUFFIN"