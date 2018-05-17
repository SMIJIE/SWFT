package ua.training.constant;

/**
 * Description: This is list of regular expressions
 *
 * @author Zakusylo Pavlo
 */
public interface RegexExpress {
    String LANG_PAGE = "/swft/[A-Za-z]{2,}";
    String REDIRECT_PAGE = ".*/swft/";

    String USER_NAME_US = "^[A-Z][a-z]+$";
    String USER_NAME_US2 = "^[A-Z][a-z]+-[A-Z][a-z]+$";
    String USER_NAME_UA = "^[\u0410-\u0429\u042C\u042E\u042F\u0407\u0406\u0404\u0490]" +
            "\'?([\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+" +
            "\'?)?[\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+$";
    String USER_NAME_UA2 = "^[\u0410-\u0429\u042C\u042E\u042F\u0407\u0406\u0404\u0490]" +
            "\'?([\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+" +
            "\'?)?[\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+-" +
            "[\u0410-\u0429\u042C\u042E\u042F\u0407\u0406\u0404\u0490]" +
            "\'?([\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+" +
            "\'?)?[\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+$";

    String USER_EMAIL = "\\w{2,}@[a-z]{3,}\\.[a-z]{2,}";
    String USER_EMAIL2 = "\\w{2,}@[a-z]{3,}\\.[a-z]{3,}\\.[a-z]{2,}";
    String USER_EMAIL3 = "\\w{2,}@.{3,}\\.[a-z]{3,}\\.[a-z]{2,}";

    String DISH_NAME_US = "^[A-Z][a-z]+(\\s[A-Za-z]+)?(\\s[A-Za-z]+)?$";
    String DISH_NAME_UA = "^[\u0410-\u0429\u042C\u042E\u042F\u0407\u0406\u0404\u0490][`´''ʼ’ʼ’]?" +
            "([\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+[`´''ʼ’ʼ’]?)?" +
            "[\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+" +
            "(\\s[\u0410-\u0429\u042C\u042E\u042F\u0407\u0406\u0404\u0490\u0430-" +
            "\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491][`´''ʼ’ʼ’]?" +
            "([\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+[`´''ʼ’ʼ’]?)?" +
            "[\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+)?" +
            "(\\s[\u0410-\u0429\u042C\u042E\u042F\u0407\u0406\u0404\u0490\u0430-" +
            "\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491][`´''ʼ’ʼ’]?" +
            "([\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+[`´''ʼ’ʼ’]?)?" +
            "[\u0430-\u0449\u044C\u044E\u044F\u0457\u0456\u0454\u0491]+)?";

}
