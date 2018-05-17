package ua.training.view;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class CustomDateTagTest {
    private String langCount;
    private LocalDate localDate;
    private Locale locale;
    private String[] langCountArr;
    private DateTimeFormatter df;

    @Before
    public void setUp() {
        langCount = "uk_UA";
        localDate = LocalDate.parse("2018-05-15");
        langCountArr = langCount.split("_");
        locale = new Locale(langCountArr[0], langCountArr[1]);
        df = new DateTimeFormatterBuilder()
                .append(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))
                .toFormatter(locale);
    }

    @After
    public void tearDown() {
        langCount = null;
        localDate = null;
        langCountArr = null;
        locale = null;
        df = null;
    }

    @Test
    public void doTag() {
        assertEquals("15.05.18", df.format(localDate));

        langCount = "en_US";
        langCountArr = langCount.split("_");
        locale = new Locale(langCountArr[0], langCountArr[1]);
        df = new DateTimeFormatterBuilder()
                .append(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))
                .toFormatter(locale);

        assertEquals("5/15/18", df.format(localDate));
    }
}