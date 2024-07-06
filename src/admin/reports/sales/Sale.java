package admin.reports.sales;

import java.util.Date;

public class Sale {
    private Date saleDate;
    private int hoursOpen;
    private int hoursClosed;
    private int productsSold;
    private double tax;
    private double totalDiscount;
    private double totalSales;

    // Getters and setters
    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public int getHoursOpen() {
        return hoursOpen;
    }

    public void setHoursOpen(int hoursOpen) {
        this.hoursOpen = hoursOpen;
    }

    public int getHoursClosed() {
        return hoursClosed;
    }

    public void setHoursClosed(int hoursClosed) {
        this.hoursClosed = hoursClosed;
    }

    public int getProductsSold() {
        return productsSold;
    }

    public void setProductsSold(int productsSold) {
        this.productsSold = productsSold;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }
}