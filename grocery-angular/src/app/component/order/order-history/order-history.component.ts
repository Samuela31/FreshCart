import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Order } from 'src/app/model/order/order';
import { OrderService } from 'src/app/service/order/order.service';

@Component({
  selector: 'app-order-history',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './order-history.component.html',
  styleUrls: ['./order-history.component.css'],
})
export class OrderHistoryComponent implements OnInit {
  orders: Order[] = [];
  userEmail: string | null = null;
  loading: boolean = true;

  constructor(private orderService: OrderService) {}

  ngOnInit(): void {
    this.userEmail = localStorage.getItem('userEmail');

    if (!this.userEmail) {
      alert('Please log in again.');
      this.loading = false;
      return;
    }

    this.orderService.getOrdersByCustomerEmail(this.userEmail).subscribe({
      next: (data) => {
        this.orders = data;
        this.loading = false;
        console.log('Orders:', data);
      },
      error: (err) => {
        console.error('Error fetching orders:', err);
        alert('Failed to load your orders. Please try again later.');
        this.loading = false;
      },
    });
  }
}
