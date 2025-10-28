export interface CategoryPromotion {
  id?: number;
  category: string;
  discountPercentage: number;
  startDate: string; // e.g., "2025-10-28"
  endDate: string;
  active: boolean;
}
