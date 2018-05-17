package ua.training.model.entity;

import ua.training.model.dao.proxy.UserLazy;
import ua.training.model.entity.enums.Roles;

import java.time.LocalDate;
import java.util.ArrayList;

import static java.util.Objects.isNull;

public class User implements Entity<Integer> {
    private Integer id;
    private String name;
    /**
     * Date of Birthday
     */
    private LocalDate dob;
    private String email;
    private String password;
    /**
     * Roles of user
     */
    private Roles role;
    private Integer height;
    private Integer weight;
    private Integer weightDesired;
    /**
     * For the Mifflin-San Jehora formula
     */
    private Integer lifeStyleCoefficient;
    /**
     * All dishes of the user
     */
    private ArrayList<Dish> listDishes;
    /**
     * All day rations of the user
     */
    private ArrayList<DayRation> dayRations;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getWeightDesired() {
        return weightDesired;
    }

    public void setWeightDesired(Integer weightDesired) {
        this.weightDesired = weightDesired;
    }

    public Integer getLifeStyleCoefficient() {
        return lifeStyleCoefficient;
    }

    public void setLifeStyleCoefficient(Integer lifeStyleCoefficient) {
        this.lifeStyleCoefficient = lifeStyleCoefficient;
    }

    public ArrayList<Dish> getListDishes() {
        return listDishes;
    }

    public void setListDishes(ArrayList<Dish> listDishes) {
        this.listDishes = listDishes;
    }

    public ArrayList<DayRation> getDayRations() {
        return dayRations;
    }

    public void setDayRations(ArrayList<DayRation> dayRations) {
        this.dayRations = dayRations;
    }

    public static final class UserBuilder implements EntityBuilder<User> {
        private Integer id;
        private String name;
        private LocalDate dob;
        private String email;
        private String password;
        private Roles role;
        private Integer height;
        private Integer weight;
        private Integer weightDesired;
        private Integer lifeStyleCoefficient;
        private ArrayList<Dish> listDishes;
        private ArrayList<DayRation> dayRations;

        public UserBuilder setId(Integer id) {
            this.id = id;
            return this;
        }

        public UserBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder setDob(LocalDate dob) {
            this.dob = dob;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder setRole(Roles role) {
            this.role = role;
            return this;
        }

        public UserBuilder setHeight(Integer height) {
            this.height = height;
            return this;
        }

        public UserBuilder setWeight(Integer weight) {
            this.weight = weight;
            return this;
        }

        public UserBuilder setWeightDesired(Integer weightDesired) {
            this.weightDesired = weightDesired;
            return this;
        }

        public UserBuilder setLifeStyleCoefficient(Integer lifeStyleCoefficient) {
            this.lifeStyleCoefficient = lifeStyleCoefficient;
            return this;
        }

        public UserBuilder setListDishes(ArrayList<Dish> listDishes) {
            this.listDishes = listDishes;
            return this;
        }

        public UserBuilder setDayRations(ArrayList<DayRation> dayRations) {
            this.dayRations = dayRations;
            return this;
        }

        @Override
        public User build() {
            User user = new User();
            user.setId(this.id);
            user.setName(this.name);
            user.setDob(this.dob);
            user.setEmail(this.email);
            user.setPassword(this.password);
            user.setRole(this.role);
            user.setHeight(this.height);
            user.setWeight(this.weight);
            user.setWeightDesired(this.weightDesired);
            user.setLifeStyleCoefficient(this.lifeStyleCoefficient);
            user.setListDishes(this.listDishes);
            user.setDayRations(this.dayRations);
            return user;
        }

        public UserLazy buildLazy() {
            UserLazy userLazy = new UserLazy();
            if (!isNull(this.id)) {
                userLazy.setId(this.id);
            }
            userLazy.setName(this.name);
            userLazy.setDob(this.dob);
            userLazy.setEmail(this.email);
            userLazy.setPassword(this.password);
            userLazy.setRole(this.role);
            userLazy.setHeight(this.height);
            userLazy.setWeight(this.weight);
            userLazy.setWeightDesired(this.weightDesired);
            userLazy.setLifeStyleCoefficient(this.lifeStyleCoefficient);
            return userLazy;
        }
    }
}
