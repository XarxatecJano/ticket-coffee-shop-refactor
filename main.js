import { Order } from "./models/Order.js";
import { OrderCalculator } from "./services/OrderCalculator.js";
import { ReceiptPrinter } from "./services/ReceiptPrinter.js";

// ---- "tests" cutres pero útiles ----
let testPassed = true;


const order1 = {
    items: ["coffee|M|2|milk,shot", "tea|S|1|", "muffin|S|1|"],
    coupon: "SAVE10",
    vip: true,
    happyHour: true,
};

const orderClean1 = new Order(order1);
const totalOrder1 = OrderCalculator.calculateTotal(orderClean1);
const testPassed1 = totalOrder1 === 9.6;
if(!testPassed1) testPassed = false;

console.assert(testPassed1, "order1 total should be 9.60€ but was " + totalOrder1,);
console.log("---- ORDER 1 ----");
console.log(ReceiptPrinter.print(orderClean1, totalOrder1));

//----------------------------------

const order2 = {
    items: ["muffin|L|2|", "coffee|S|1|syrup"],
    coupon: "FREEMUFFIN",
    vip: false,
    happyHour: false,
};

const orderClean2 = new Order(order2);
const totalOrder2 = OrderCalculator.calculateTotal(orderClean2);
const testPassed2 = totalOrder2 === 5.17;
if(!testPassed2) testPassed = false;

console.assert(testPassed2, "order2 total should be 5.17€ but was " + totalOrder2,);
console.log("---- ORDER 2 ----");
console.log(ReceiptPrinter.print(orderClean2, totalOrder2));

console.log();
if (testPassed) {
    console.log("All assertions passed ✅");
} else {
    console.log("Some assertions failed ❌");
}