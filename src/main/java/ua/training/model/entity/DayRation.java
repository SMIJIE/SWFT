package ua.training.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "dayration")
public class DayRation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDR", nullable = false)
    private Integer id;
    /**
     * Date of creation
     */
    @Column(nullable = false)
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "idUser", nullable = false)
    private User user;
    /**
     * List of dishes of the ration
     */
    @ManyToMany(mappedBy = "dayRation")
    private List<RationComposition> compositions;
    /**
     * Data for graphical visualisation
     */
    @Column(nullable = false)
    private Integer userCalories;
    /**
     * Data for graphical visualisation
     */
    @Column(nullable = false)
    private Integer userCaloriesDesired;
}
