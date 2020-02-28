package models;

import java.time.LocalDate;

public class Order {
    private LocalDate returnDate;

    public Order() {
        this.returnDate = LocalDate.now().plusDays(15);
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}

