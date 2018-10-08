package ua.training.model.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ua.training.model.entity.enums.Roles;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idU", nullable = false)
    private Integer id;
    @Column(nullable = false)
    private String name;
    /**
     * Date of Birthday
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate dob;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    /**
     * Roles of user
     */
    @Column(nullable = false)
    private Roles role;
    @Column(nullable = false)
    private Integer height;
    @Column(nullable = false)
    private Integer weight;
    @Column(nullable = false)
    private Integer weightDesired;
    /**
     * For the Mifflin-San Jehora formula
     */
    @Column(nullable = false)
    private Integer lifeStyleCoefficient;
    /**
     * All dishes of the user
     */
    @OneToMany(mappedBy = "user")
    private List<Dish> listDishes;
    /**
     * All day rations of the user
     */
    @OneToMany(mappedBy = "user")
    private List<DayRation> dayRations;

    public void setHeight(String height) {
        double heightTemp = Double.valueOf(height);
        this.height = (int) (heightTemp * 100);
    }

    public void setWeight(String weight) {
        double weightTemp = Double.valueOf(weight);
        this.weight = (int) (weightTemp * 1000);
    }

    public void setWeightDesired(String weightDesired) {
        double weightDesiredTemp = weightDesired.equals("") ? 0 : Double.valueOf(weightDesired);
        this.weightDesired = (int) (weightDesiredTemp * 1000);
    }

    public void setLifeStyleCoefficient(String lifeStyleCoefficient) {
        double lifeStyleCoefficientTemp = Double.valueOf(lifeStyleCoefficient);
        this.lifeStyleCoefficient = (int) (lifeStyleCoefficientTemp * 1000);
    }
}
