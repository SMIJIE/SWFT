package ua.training.model.dao.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Description: Encode users password into md5(128-bit hash algorithm)
 *
 * @author Zakusylo Pavlo
 */
public class PasswordEncoder {
    /**
     * @param password user password
     * @return md5Hex encoded password
     */
    public static String encodePassword(String password) {
        return DigestUtils.md5Hex(password);
    }
}
