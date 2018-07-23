package ua.training.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.training.model.dao.proxy.DayRationLazy;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "dayration")
public class DayRation implements EntityObject<Integer> {
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

    public static final class DayRationBuilder implements EntityBuilder<DayRation> {
        private Integer id;
        private LocalDate date;
        private User user;
        private ArrayList<RationComposition> compositions;
        private Integer userCalories;
        private Integer userCaloriesDesired;

        public DayRationBuilder setId(Integer id) {
            this.id = id;
            return this;
        }

        public DayRationBuilder setDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public DayRationBuilder setUser(User user) {
            this.user = user;
            return this;
        }

        public DayRationBuilder setCompositions(ArrayList<RationComposition> compositions) {
            this.compositions = compositions;
            return this;
        }

        public DayRationBuilder setUserCalories(Integer userCalories) {
            this.userCalories = userCalories;
            return this;
        }

        public DayRationBuilder setUserCaloriesDesired(Integer userCaloriesDesired) {
            this.userCaloriesDesired = userCaloriesDesired;
            return this;
        }

        @Override
        public DayRation build() {
            DayRation dayRation = new DayRation();
            dayRation.setId(this.id);
            dayRation.setDate(this.date);
            dayRation.setUser(this.user);
            dayRation.setCompositions(this.compositions);
            dayRation.setUserCalories(this.userCalories);
            dayRation.setUserCaloriesDesired(this.userCaloriesDesired);
            return dayRation;
        }

        @Override
        public DayRationLazy buildLazy() {
            DayRationLazy dayRationLazy = new DayRationLazy();
            if (!isNull(this.id)) {
                dayRationLazy.setId(this.id);
            }
            dayRationLazy.setDate(this.date);
            if (!isNull(this.user)) {
                dayRationLazy.setUser(this.user);
            }
            dayRationLazy.setUserCalories(this.userCalories);
            dayRationLazy.setUserCaloriesDesired(this.userCaloriesDesired);
            return dayRationLazy;
        }
    }
}
