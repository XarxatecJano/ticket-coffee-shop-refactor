export class ReceiptPrinter {
    static print(order, total) {
        let receipt = `*** BYTE & BEAN ***\n`;
        receipt += "VIP:" + (order.vip ? "YES" : "NO");
        receipt += " | HAPPY:" + (order.happyHour ? "YES" : "NO") + "\n";

        for (const item of order.items) {
            const extrasText =
                item.extras.length > 0 ? item.extras.join(",") : "No extras";
            receipt += `${item.product} ${item.size} x${item.amount} extras:${extrasText}\n`;
        }
        receipt += "COUPON: " + (order.coupon || "") + "\n";
        receipt += "TOTAL=" + total + " EUR\n";

        return receipt;
    }
}
