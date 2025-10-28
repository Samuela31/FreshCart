import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Customer } from 'src/app/model/customer/customer';

@Injectable({
  providedIn: 'root',
})
export class CustomerService {
  private baseUrl = 'http://localhost:55420/grocery-project/api/customers'; // Spring Boot base URL

  constructor(private http: HttpClient) {}

  // Action anyone can do
  // Create a new customer (sign-up)
  createCustomer(customer: Customer): Observable<Customer> {
    return this.http.post<Customer>(this.baseUrl, customer);
  }

  /****************************************
   * Actions permitted for role: CUSTOMER
   ****************************************/
  // Update customer (update profile)
  updateCustomer(id: number, updatedCustomer: Customer): Observable<Customer> {
    return this.http.put<Customer>(`${this.baseUrl}/${id}`, updatedCustomer);
  }

  // Delete customer (delete account)
  deleteCustomer(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  /*************************************
   * Actions permitted for role: ADMIN
   *************************************/
  // Get all customers (ADMIN)
  getAllCustomers(): Observable<Customer[]> {
    return this.http.get<Customer[]>(this.baseUrl);
  }

  // Get customer by ID (ADMIN)
  getCustomerById(id: number): Observable<Customer> {
    return this.http.get<Customer>(`${this.baseUrl}/${id}`);
  }
}
