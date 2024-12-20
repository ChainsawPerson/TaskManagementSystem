import java.time.LocalDate;

public class Reminder {
    private LocalDate date;

    // Constructors

    public Reminder(LocalDate d) {
        date = d;
    }

    // Getters

    public LocalDate getReminder() {
        return date;
    }

    // Setters

    public void setReminder(LocalDate d) {
        date = d;
    }

}
