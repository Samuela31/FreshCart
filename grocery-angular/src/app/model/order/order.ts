import { Customer } from '../customer/customer';
import { OrderItem } from '../order-item/order-item';

export interface Order {
  orderId?: number;
  paymentId: number;
  customer: Customer;
  deliveryAddress: string;
  totalAmount: number;
  orderStatus: string;
  orderDate: string;
  orderItems: OrderItem[];
}
