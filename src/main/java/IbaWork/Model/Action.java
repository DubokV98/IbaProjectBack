package IbaWork.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "action")
public class Action {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String request;
    private Date date;
    private String result;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}
