package ua.training.view;

import lombok.extern.log4j.Log4j2;
import ua.training.constant.Mess;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.Locale;

import static java.util.Objects.isNull;

/**
 * Description: This is class for custom tag to write current date according to locale
 *
 * @author Zakusylo Pavlo
 */
@Log4j2
public class CustomDateTag extends SimpleTagSupport {
    private String langCount;
    private LocalDate localDate;

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public void setLangCount(String langCount) {
        this.langCount = langCount;
    }

    @Override
    public void doTag() {
        String[] langCountArr = langCount.split("_");
        Locale locale = new Locale(langCountArr[0], langCountArr[1]);
        DateTimeFormatter df = new DateTimeFormatterBuilder()
                .append(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))
                .toFormatter(locale);

        if (isNull(localDate)) {
            localDate = LocalDate.now();
        }

        try {
            getJspContext()
                    .getOut()
                    .write(df.format(localDate));
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage() + Mess.LOG_NOT_WRITE_CUSTOM_TAG);
        }
    }
}
