package ua.training.model.entity.form;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ua.training.model.entity.User;
import ua.training.model.entity.enums.FoodIntake;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class FormDayRationComposition extends GeneralFormEntity {
    private User user;
    private FoodIntake foodIntake;
    private Integer numberOfDish;
    private Integer[] breakfast;
    private Integer[] dinner;
    private Integer[] supper;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
