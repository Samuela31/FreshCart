export interface Product {
  productId?: number; // optional for new product creation
  productName: string;
  description?: string; // optional
  price: number; // BigDecimal in backend maps to number in TS
  quantity: number;
  category: string;
  imageUrl: string;
}
