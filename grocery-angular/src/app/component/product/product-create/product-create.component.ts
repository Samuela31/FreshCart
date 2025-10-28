import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { ProductService } from 'src/app/service/product/product.service';
import { Router } from '@angular/router';
import { Product } from 'src/app/model/product/product';

@Component({
  selector: 'app-product-create',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './product-create.component.html',
  styleUrls: ['./product-create.component.css'],
})
export class ProductCreateComponent {
  product: Product = {
    productName: '',
    description: '',
    price: 0,
    quantity: 0,
    category: '',
    imageUrl: '',
  };

  categories: string[] = [
    'Fruits',
    'Vegetables',
    'Dairy Items',
    'Provision',
    'Snacks',
  ];

  successMessage = '';
  errorMessage = '';
  submitted = false;

  constructor(private productService: ProductService) {}

  onSubmit(form: NgForm) {
    this.submitted = true;
    this.successMessage = '';
    this.errorMessage = '';

    if (form.invalid) {
      this.errorMessage = 'Please correct the errors before submitting.';
      return;
    }

    this.productService.createProduct(this.product).subscribe({
      next: (response) => {
        this.successMessage = `Product "${response.productName}" created successfully!`;
        form.resetForm();
        this.submitted = false;
      },
      error: (err) => {
        console.error('Error creating product:', err);
        this.errorMessage = 'Failed to create product. Please try again.';
      },
    });
  }
}
