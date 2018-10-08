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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idU", nullable = false)
    private Integer id;
    /**
     * Verify on not EMPTY or NULL and PATTERN
     */
    @NotNull(message = Mess.USER_VALID_NAME_NOT_EMPTY_NULL)
    @NotEmpty(message = Mess.USER_VALID_NAME_NOT_EMPTY_NULL)
    @Pattern(regexp = RegexExpress.USER_NAME_PATTERN,
            message = Mess.USER_VALID_NAME_WRONG)
    @Column(nullable = false)
    private String name;
    /**
     * Date of Birthday
     * Verify on not NULL
     * DateTimeFormat for input from <spring:form>
     */
    @NotNull(message = Mess.USER_VALID_DOB_NOT_NULL)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate dob;
    /**
     * Verify on not EMPTY or NULL and PATTERN and EMAIL
     */
    @Email
    @NotNull(message = Mess.USER_VALID_EMAIL_NOT_EMPTY_NULL)
    @NotEmpty(message = Mess.USER_VALID_EMAIL_NOT_EMPTY_NULL)
    @Pattern(regexp = RegexExpress.USER_EMAIL_PATTERN,
            message = Mess.USER_VALID_EMAIL_WRONG)
    @Column(nullable = false)
    private String email;
    /**
     * Verify on not EMPTY or NULL and on MIN 3 symbols
     */
    @NotNull(message = Mess.USER_VALID_PASSWORD_NOT_EMPTY_NULL)
    @NotEmpty(message = Mess.USER_VALID_PASSWORD_NOT_EMPTY_NULL)
    @Size(min = 3, message = Mess.USER_VALID_PASSWORD_SIZE)
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
    @NotNull(message = Mess.USER_VALID_HEIGHT_SIZE)
    @Size(min = 50 * 100, max = 250 * 100, message = Mess.USER_VALID_HEIGHT_SIZE)
    @Column(nullable = false)
    private Integer height;
    /**
     * Verify on not NULL and on MIN and MAX
     */
    @NotNull(message = Mess.USER_VALID_WEIGHT_SIZE)
    @Size(min = 50 * 1000, max = 150 * 1000, message = Mess.USER_VALID_WEIGHT_SIZE)
    @Column(nullable = false)
    private Integer weight;

    @Column(nullable = false)
    private Integer weightDesired;
    /**
     * For the Mifflin-San Jehora formula
     * Verify on not NULL
     */
    @NotNull(message = Mess.USER_VALID_LIFE_STYLE_COEFFICIENT)
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

    /**
     * Overload for input from <spring:form>
     */
    public void setWeight(String weight) {
        double weightTemp = Double.valueOf(weight);
        this.weight = (int) (weightTemp * 1000);
    }

    /**
     * Overload for input from <spring:form>
     */
    public void setWeightDesired(String weightDesired) {
        double weightDesiredTemp = isNullOrEmpty(weightDesired) ? 0 : Double.valueOf(weightDesired);
        this.weightDesired = (int) (weightDesiredTemp * 1000);
    }

    /**
     * Overload for input from <spring:form>
     */
    public void setLifeStyleCoefficient(String lifeStyleCoefficient) {
        double lifeStyleCoefficientTemp = Double.valueOf(lifeStyleCoefficient);
        this.lifeStyleCoefficient = (int) (lifeStyleCoefficientTemp * 1000);
    }
}
