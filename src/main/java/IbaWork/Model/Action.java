package IbaWork.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "action")
public class Action {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String request;
    private LocalDate date;
    private String result;
    private String time;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
}
