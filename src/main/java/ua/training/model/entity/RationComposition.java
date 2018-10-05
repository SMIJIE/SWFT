package ua.training.model.entity;

import lombok.*;
import ua.training.model.entity.enums.FoodIntake;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "rationcomposition")
public class RationComposition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRC", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "idDayRation", nullable = false)
    private DayRation dayRation;
    /**
     * Food intake per day
     */
    @Column(nullable = false)
    private FoodIntake foodIntake;
    @ManyToOne
    @JoinColumn(name = "idDish", nullable = false)
    private Dish dish;
    @Column(nullable = false)
    private Integer numberOfDish;
    @Column(nullable = false)
    private Integer caloriesOfDish;
}
