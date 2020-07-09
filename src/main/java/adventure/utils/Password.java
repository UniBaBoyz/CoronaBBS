package adventure.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 * @author Corona-Extra
 */
public class Password {

    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public static boolean checkPass(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

}
