package ua.training.model.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ua.training.constant.Mess;
import ua.training.constant.RegexExpress;
import ua.training.model.entity.enums.Roles;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

import static com.mysql.jdbc.StringUtils.isNullOrEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "user")
public class User implements Mess, RegexExpress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idU", nullable = false)
    private Integer id;
    /**
     * Verify on not NULL and PATTERN
     */
    @NotBlank(message = USER_VALID_NAME_NOT_BLANK)
    @Pattern(regexp = USER_NAME_PATTERN, message = USER_VALID_NAME_WRONG)
    @Column(nullable = false)
    private String name;
    /**
     * Date of Birthday
     * Verify on not NULL
     * DateTimeFormat for input from <spring:form>
     */
    @NotNull(message = USER_VALID_DOB_NOT_NULL)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate dob;
    /**
     * Verify on not NULL and PATTERN and EMAIL
     */
    @Email
    @NotBlank(message = USER_VALID_EMAIL_NOT_BLANK)
    @Pattern(regexp = USER_EMAIL_PATTERN, message = USER_VALID_EMAIL_WRONG)
    @Column(nullable = false)
    private String email;
    /**
     * Verify on not NULL and on MIN 3 symbols
     */
    @NotBlank(message = USER_VALID_PASSWORD_NOT_BLANK)
    @Size(min = 3, message = USER_VALID_PASSWORD_SIZE)
    @Column(nullable = false)
    private String password;
    /**
     * Roles of user
     */
    @Column(nullable = false)
    private Roles role;
    /**
     * Verify on not NULL and on MIN and MAX
     */
    @NotNull(message = USER_VALID_HEIGHT_SIZE)
    @Min(value = 50 * 100, message = USER_VALID_HEIGHT_SIZE)
    @Max(value = 250 * 100, message = USER_VALID_HEIGHT_SIZE)
    @Column(nullable = false)
    private Integer height;
    /**
     * Verify on not NULL and on MIN and MAX
     */
    @NotNull(message = USER_VALID_WEIGHT_SIZE)
    @Min(value = 50 * 1000, message = USER_VALID_WEIGHT_SIZE)
    @Max(value = 150 * 1000, message = USER_VALID_WEIGHT_SIZE)
    @Column(nullable = false)
    private Integer weight;
    /**
     * Has default value
     */
    @Column(nullable = false)
    private Integer weightDesired;
    /**
     * For the Mifflin-San Jehora formula
     * Verify on not NULL
     */
    @NotNull(message = USER_VALID_LIFE_STYLE_COEFFICIENT)
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

    /**
     * Overload for input from <spring:form>
     */
    public void setHeight(String height) {
        double heightTemp = Double.valueOf(height);
        this.height = (int) (heightTemp * 100);
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * Overload for input from <spring:form>
     */
    public void setWeight(String weight) {
        double weightTemp = Double.valueOf(weight);
        this.weight = (int) (weightTemp * 1000);
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**
     * Overload for input from <spring:form>
     */
    public void setWeightDesired(String weightDesired) {
        double weightDesiredTemp = isNullOrEmpty(weightDesired) ? 0 : Double.valueOf(weightDesired);
        this.weightDesired = (int) (weightDesiredTemp * 1000);
    }

    public void setWeightDesired(Integer weightDesired) {
        this.weightDesired = weightDesired;
    }

    /**
     * Overload for input from <spring:form>
     */
    public void setLifeStyleCoefficient(Object lifeStyleCoefficient) {
        double lifeStyleCoefficientTemp = Double.valueOf(lifeStyleCoefficient.toString());
        this.lifeStyleCoefficient = (int) (lifeStyleCoefficientTemp * 1000);
    }

    public void setLifeStyleCoefficient(Integer lifeStyleCoefficient) {
        this.lifeStyleCoefficient = lifeStyleCoefficient;
    }
}
