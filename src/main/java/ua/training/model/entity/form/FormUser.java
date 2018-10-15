package ua.training.model.entity.form;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import ua.training.constant.Mess;
import ua.training.constant.RegexExpress;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * Verify input from http form
 * {@link ua.training.model.entity.User}
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class FormUser implements Mess, RegexExpress {
    /**
     * Verify on not NULL and PATTERN
     */
    @NotBlank(message = USER_VALID_NAME_NOT_BLANK)
    @Pattern(regexp = USER_NAME_PATTERN, message = USER_VALID_NAME_WRONG)
    private String name;
    /**
     * Date of Birthday
     * Verify on not NULL
     * DateTimeFormat for input from <spring:form>
     */
    @NotNull(message = USER_VALID_DOB_NOT_NULL)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;
    /**
     * Verify on not NULL and PATTERN and EMAIL
     */
    @Email
    @NotBlank(message = USER_VALID_EMAIL_NOT_BLANK)
    @Pattern(regexp = USER_EMAIL_PATTERN, message = USER_VALID_EMAIL_WRONG)
    private String email;
    /**
     * Verify on not NULL and on MIN 3 symbols
     */
    @NotBlank(message = USER_VALID_PASSWORD_NOT_BLANK)
    @Size(min = 3, message = USER_VALID_PASSWORD_SIZE)
    private String password;
    /**
     * For confirm when want to change the password
     */
    private String passwordConfirm;
    /**
     * Verify on not NULL and on MIN and MAX
     */
    @NotNull(message = USER_VALID_HEIGHT_SIZE)
    @Min(value = 50, message = USER_VALID_HEIGHT_SIZE)
    @Max(value = 250, message = USER_VALID_HEIGHT_SIZE)
    private Double height;
    /**
     * Verify on not NULL and on MIN and MAX
     */
    @NotNull(message = USER_VALID_WEIGHT_SIZE)
    @Min(value = 50, message = USER_VALID_WEIGHT_SIZE)
    @Max(value = 150, message = USER_VALID_WEIGHT_SIZE)
    private Double weight;
    /**
     * Verify on not NULL and on MIN and MAX
     */
    @NotNull(message = USER_VALID_WEIGHT_SIZE)
    @Min(value = 50, message = USER_VALID_WEIGHT_SIZE)
    @Max(value = 150, message = USER_VALID_WEIGHT_SIZE)
    private Double weightDesired;
    /**
     * Verify on not NULL
     */
    @NotNull(message = USER_VALID_LIFE_STYLE_COEFFICIENT)
    private Double lifeStyleCoefficient;
}
