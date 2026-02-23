import { Order } from "./Order.js";

const order1 = {
    items: ["coffee|M|2|milk,shot", "tea|S|1|", "muffin|S|1|"],
    coupon: "SAVE10",
    vip: true,
    happyHour: true,
};

try {
    const miPedidoLimpio = new Order(order1);

    console.log("Order created successfully!");
    console.log(JSON.stringify(miPedidoLimpio, null, 2));
} catch (error) {
    console.error(error.message);
}
