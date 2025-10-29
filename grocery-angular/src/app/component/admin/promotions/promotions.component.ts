import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CategoryPromotion } from 'src/app/model/category-promotions/category-promotion';
import { CategoryPromotionService } from 'src/app/service/category-promotions/category-promotion.service';
import { FormsModule, NgForm } from '@angular/forms';

@Component({
  selector: 'app-promotions',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './promotions.component.html',
  styleUrls: ['./promotions.component.css'],
})
export class PromotionsComponent implements OnInit {
  promotions: CategoryPromotion[] = [];
  newPromotion: CategoryPromotion = {
    id: 0,
    category: '',
    startDate: '',
    endDate: '',
    discountPercentage: 0,
    active: true,
  };
  today = new Date();

  constructor(private promotionService: CategoryPromotionService) {}

  ngOnInit(): void {
    this.loadPromotions();
  }

  loadPromotions() {
    this.promotionService.getAllPromotions().subscribe({
      next: (data) => (this.promotions = data),
      error: (err) => console.error('Failed to load promotions', err),
    });
  }

  createPromotion(form: NgForm) {
    if (form.invalid) {
      alert('Please fill all required fields correctly!');
      return;
    }

    // Convert Date objects/ string inputs to ISO strings for backend
    const promotionToSend = {
      ...this.newPromotion,
      startDate: new Date(this.newPromotion.startDate).toISOString(),
      endDate: new Date(this.newPromotion.endDate).toISOString(),
    };

    this.promotionService.createPromotion(promotionToSend).subscribe({
      next: (res) => {
        alert(`Promotion for category "${res.category}" created successfully!`);
        // Reset form and model
        this.newPromotion = {
          id: 0,
          category: '',
          startDate: '',
          endDate: '',
          discountPercentage: 0,
          active: true,
        };
        form.resetForm();
        this.loadPromotions();
      },
      error: (err) => {
        console.error('Failed to create promotion', err);
        alert(
          'Failed to create promotion. Please check the input values (Category should be unique)!'
        );
      },
    });
  }

  stopPromotion(promo: CategoryPromotion) {
    if (!confirm(`Stop promotion for category "${promo.category}"?`)) return;

    this.promotionService.stopPromotion(promo.id!).subscribe({
      next: () => {
        alert(`Promotion for category "${promo.category}" stopped.`);
        this.loadPromotions();
      },
      error: (err) => console.error('Failed to stop promotion', err),
    });
  }

  deletePromotion(promo: CategoryPromotion) {
    if (
      !confirm(`Delete promotion for category "${promo.category}" permanently?`)
    )
      return;

    this.promotionService.deletePromotion(promo.id!).subscribe({
      next: () => {
        alert(`Promotion deleted successfully.`);
        this.loadPromotions();
      },
      error: (err) => console.error('Failed to delete promotion', err),
    });
  }
}
