package admin.reports.sales;

public class SoldProduct {
    private int productId;
    private int quantity;

    // Default constructor
    public SoldProduct() {
    }

    // Constructor with parameters
    public SoldProduct(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    // Getters and setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
