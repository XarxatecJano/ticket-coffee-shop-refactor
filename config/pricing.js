export const BASE_PRICES = {
    coffee: { S: 2.0, M: 2.5, L: 3.0 },
    tea: { S: 1.5, M: 2.0, L: 2.3 },
    muffin: { S: 2.2, M: 2.2, L: 2.2 },
};

export const EXTRA_PRICES = {
    milk: 0.2,
    shot: 0.8,
    syrup: 0.5,
};

export const COUPON = {
    HAPPY_HOUR: 0.8,
    SAVE_COUPON: 0.9,
    FREE_MUFFIN: BASE_PRICES.muffin.S,
};

export const VIP_COUPON = {
    VIP: 0.5,
    MIN_ORDER: 10
}

export const calcIVA = (price) => {
    const IVA = 1.1
    const totalPrice = price * IVA;
    return Math.round(totalPrice * 100) / 100;  
}