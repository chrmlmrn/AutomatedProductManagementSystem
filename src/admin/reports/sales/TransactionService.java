package admin.reports.sales;

import java.util.List;

public class TransactionService {
    public void handleNewTransaction(Transaction transaction, List<SoldProduct> soldProducts) {
        for (SoldProduct soldProduct : soldProducts) {
            System.out
                    .println("Product ID: " + soldProduct.getProductId() + ", Quantity: " + soldProduct.getQuantity());
        }

        // Your existing logic to handle the transaction

        // For debugging, log the transaction and sold products
        // System.out.println("Transaction Details:");
        // System.out.println("Subtotal: " + transaction.getSubtotal());
        // System.out.println("Discount: " + transaction.getDiscount());
        // System.out.println("VAT: " + transaction.getVat());
        // System.out.println("Total: " + transaction.getTotal());
        // System.out.println("Products Sold: " + transaction.getProductsSold());
    }
}
