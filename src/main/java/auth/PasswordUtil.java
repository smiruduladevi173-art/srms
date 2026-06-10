package auth;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class PasswordUtil {

    public static String hashPassword(
            String password
    ) {

        Argon2 argon2 =
                Argon2Factory.create();

        String hash =
                argon2.hash(
                        3,
                        65536,
                        1,
                        password.toCharArray()
                );

        System.out.println("HASH = " + hash);

        return hash;
    }

    public static boolean verifyPassword(
            String hash,
            String password
    ) {

        Argon2 argon2 =
                Argon2Factory.create();

        return argon2.verify(
                hash,
                password.toCharArray()
        );
    }
}