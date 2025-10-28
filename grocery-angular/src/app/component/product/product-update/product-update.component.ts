import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Product } from 'src/app/model/product/product';
import { ProductService } from 'src/app/service/product/product.service';
import { FormsModule, NgForm } from '@angular/forms';

@Component({
  selector: 'app-product-update',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './product-update.component.html',
  styleUrls: ['./product-update.component.css'],
})
export class ProductUpdateComponent implements OnInit {
  allProducts: Product[] = [];
  productIdToEdit: number | null = null;
  selectedProduct: Product | null = null;
  submitted = false;
  successMessage = '';
  errorMessage = '';

  categories: string[] = [
    'Fruits',
    'Vegetables',
    'Dairy Items',
    'Provision',
    'Snacks',
  ];

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.loadAllProducts();
  }

  loadAllProducts(): void {
    this.productService.getAllProducts().subscribe({
      next: (products) => (this.allProducts = products),
      error: (err) => console.error('Error fetching products', err),
    });
  }

  fetchProduct(): void {
    this.successMessage = '';
    this.errorMessage = '';
    this.selectedProduct = null;
    this.submitted = false;

    if (this.productIdToEdit != null) {
      this.productService.getProductById(this.productIdToEdit).subscribe({
        next: (product) => (this.selectedProduct = { ...product }), // clone to avoid direct mutation
        error: (err) => {
          console.error(err);
          this.errorMessage = 'Product not found!';
        },
      });
    }
  }

  onSubmit(form: NgForm): void {
    this.submitted = true;
    this.successMessage = '';
    this.errorMessage = '';

    if (form.invalid || !this.selectedProduct || !this.productIdToEdit) return;

    this.productService
      .updateProduct(this.productIdToEdit, this.selectedProduct)
      .subscribe({
        next: (updatedProduct) => {
          this.successMessage = `Product "${updatedProduct.productName}" updated successfully!`;
          this.selectedProduct = null;
          this.productIdToEdit = null;
          form.resetForm();
          this.loadAllProducts(); // refresh table
        },
        error: (err) => {
          console.error(err);
          this.errorMessage = 'Failed to update product!';
        },
      });
  }
}
