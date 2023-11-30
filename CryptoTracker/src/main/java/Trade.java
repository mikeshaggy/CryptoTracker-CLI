import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Trade implements Serializable {
    private final String coinName;
    private final double price;
    private final double value;
    private final double quantity;

    public Trade(String coinName, double price, double quantity) {
        this.coinName = coinName;
        this.price = price;
        this.quantity = quantity;
        this.value = quantity * price;
    }

    public String getCoinName() {
        return coinName;
    }

    public double getPrice() {
        return price;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "name: %s | quantity: %.9f | value: %.2f$"
        .formatted(coinName, quantity, value);
    }
}