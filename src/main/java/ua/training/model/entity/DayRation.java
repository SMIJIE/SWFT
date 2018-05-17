package ua.training.model.entity;

import ua.training.model.dao.proxy.DayRationLazy;

import java.time.LocalDate;
import java.util.ArrayList;

import static java.util.Objects.isNull;

public class DayRation implements Entity<Integer> {
    private Integer id;
    /**
     * Date of creation
     */
    private LocalDate date;
    private User user;
    /**
     * List of dishes of the ration
     */
    private ArrayList<RationComposition> compositions;
    /**
     * Data for graphical visualisation
     */
    private Integer userCalories;
    /**
     * Data for graphical visualisation
     */
    private Integer userCaloriesDesired;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<RationComposition> getCompositions() {
        return compositions;
    }

    public void setCompositions(ArrayList<RationComposition> compositions) {
        this.compositions = compositions;
    }

    public Integer getUserCalories() {
        return userCalories;
    }

    public void setUserCalories(Integer userCalories) {
        this.userCalories = userCalories;
    }

    public Integer getUserCaloriesDesired() {
        return userCaloriesDesired;
    }

    public void setUserCaloriesDesired(Integer userCaloriesDesired) {
        this.userCaloriesDesired = userCaloriesDesired;
    }

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
