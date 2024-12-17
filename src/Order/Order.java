package Order;
import java.util.List;
public class Order {
    private static int orderCounter = 1;
    private int orderId;
    private List<OrderItem> items;
    private double totalAmount;
    private String status;

    public Order(List<OrderItem> items, String customerId) {
        this.orderId = orderCounter++;
        this.items = items;
        this.status = "PLACED";
        calculateTotalAmount();
        reduceStockForOrderItems();
    }

    private void calculateTotalAmount() {
        this.totalAmount = 0.0;
        for (OrderItem item : items) {
            this.totalAmount += item.getTotalPrice();
        }
    }

    private void reduceStockForOrderItems() {
        for (OrderItem item : items) {
            item.reduceStock();
        }
    }

    public int getOrderId() {
        return orderId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String newStatus) {
        if (newStatus == null || newStatus.trim().isEmpty()) {
            throw new IllegalArgumentException("Status cannot be null or empty.");
        }

        if (("PLACED".equals(this.status) && "SHIPPED".equals(newStatus)) ||
                ("SHIPPED".equals(this.status) && "DELIVERED".equals(newStatus)))
        {
            this.status = newStatus;
            System.out.printf("Status updated to '%s'.%n", newStatus);
        }
        else if ("DELIVERED".equals(this.status) && "CANCELLED".equals(newStatus))
        {
            throw new IllegalStateException("Cannot cancel a delivered order.");
        }
        else if ("CANCELLED".equals(this.status))
        {
            throw new IllegalStateException("Cannot change the status of a cancelled order.");
        }
        else
        {
            throw new IllegalStateException("Invalid status transition from " + this.status + " to " + newStatus);
        }
    }

    public void displayOrderDetails() {
        System.out.println("Order ID: " + orderId);
        System.out.println("Status: " + status);
        System.out.println("The Items in Order:");
        for (OrderItem item : items) {
            System.out.println("- " + item.getBook().getTitle() + " (Quantity: " + item.getQuantity() + ", Total Price: $" + item.getTotalPrice() + ")");        }
        System.out.println("Total Order Amount: $" + totalAmount);
    }

}

