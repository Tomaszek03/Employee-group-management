package aoim.zad4;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rate")
public class RateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = " rate_id", nullable = false)
    private int rate_id;

    @Column(name = "group_id", nullable = false)
    private int group_id;

    @Column(name = "rate", nullable = false)
    private int rate;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "comment", nullable = false)
    private String comment;

    public int getRate_id() {
        return rate_id;
    }

    public void setRate_id(int rate_id) {
        this.rate_id = rate_id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = LocalDateTime.now();
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
