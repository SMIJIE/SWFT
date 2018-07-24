package ua.training.model.entity;

import lombok.*;
import ua.training.model.entity.enums.Roles;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "user")
public class User implements EntityObject<Integer> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idU", nullable = false)
    private Integer id;
    @Column(nullable = false)
    private String name;
    /**
     * Date of Birthday
     */
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
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Dish> listDishes;
    /**
     * All day rations of the user
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<DayRation> dayRations;
}
