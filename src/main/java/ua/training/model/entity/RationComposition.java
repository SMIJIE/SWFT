package ua.training.model.entity;

import ua.training.model.entity.enums.FoodIntake;

public class RationComposition implements Entity<Integer> {
    private Integer id;
    private DayRation dayRation;
    /**
     * Food intake per day
     */
    private FoodIntake foodIntake;
    private Dish dish;
    private Integer numberOfDish;
    private Integer caloriesOfDish;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DayRation getDayRation() {
        return dayRation;
    }

    public void setDayRation(DayRation dayRation) {
        this.dayRation = dayRation;
    }

    public FoodIntake getFoodIntake() {
        return foodIntake;
    }

    public void setFoodIntake(FoodIntake foodIntake) {
        this.foodIntake = foodIntake;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Integer getNumberOfDish() {
        return numberOfDish;
    }

    public void setNumberOfDish(Integer numberOfDish) {
        this.numberOfDish = numberOfDish;
    }

    public Integer getCaloriesOfDish() {
        return caloriesOfDish;
    }

    public void setCaloriesOfDish(Integer caloriesOfDish) {
        this.caloriesOfDish = caloriesOfDish;
    }

    public static final class RationCompositionBuilder implements EntityBuilder<RationComposition> {
        private Integer id;
        private DayRation dayRation;
        private FoodIntake foodIntake;
        private Dish dish;
        private Integer numberOfDish;
        private Integer caloriesOfDish;

        public RationCompositionBuilder setId(Integer id) {
            this.id = id;
            return this;
        }

        public RationCompositionBuilder setDayRation(DayRation dayRation) {
            this.dayRation = dayRation;
            return this;
        }

        public RationCompositionBuilder setFoodIntake(FoodIntake foodIntake) {
            this.foodIntake = foodIntake;
            return this;
        }

        public RationCompositionBuilder setDish(Dish dish) {
            this.dish = dish;
            return this;
        }

        public RationCompositionBuilder setNumberOfDish(Integer numberOfDish) {
            this.numberOfDish = numberOfDish;
            return this;
        }

        public RationCompositionBuilder setCaloriesOfDish(Integer caloriesOfDish) {
            this.caloriesOfDish = caloriesOfDish;
            return this;
        }

        @Override
        public RationComposition build() {
            RationComposition rationComposition = new RationComposition();
            rationComposition.setId(this.id);
            rationComposition.setDayRation(this.dayRation);
            rationComposition.setFoodIntake(this.foodIntake);
            rationComposition.setDish(this.dish);
            rationComposition.setNumberOfDish(this.numberOfDish);
            rationComposition.setCaloriesOfDish(this.caloriesOfDish);
            return rationComposition;
        }

        public RationComposition buildLazy() {
            RationComposition rationComposition = new RationComposition();
            rationComposition.setId(this.id);
            rationComposition.setFoodIntake(this.foodIntake);
            rationComposition.setNumberOfDish(this.numberOfDish);
            rationComposition.setCaloriesOfDish(this.caloriesOfDish);
            return rationComposition;
        }
    }
}
