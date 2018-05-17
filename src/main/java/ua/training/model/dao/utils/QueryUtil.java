package ua.training.model.dao.utils;

/**
 * Description: Class that add attributes for query with clause 'IN"
 *
 * @author Zakusylo Pavlo
 */
public class QueryUtil {
    /**
     * @param query  String
     * @param number int
     * @return new query builder
     */
    public static String addParamAccordingToArrSize(String query, int number) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        for (int i = 1; i <= number; i++) {
            if (i == number) {
                stringBuilder.append("?");
            } else {
                stringBuilder.append("?")
                        .append(",");
            }
        }
        stringBuilder.append(")");

        query = query.replace("(?)", stringBuilder.toString());

        return query;
    }
}
