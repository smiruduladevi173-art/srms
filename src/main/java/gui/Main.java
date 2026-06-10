package gui;

import utils.PasswordUtil;

public class Main {

    public static void main(String[] args) {

        String hash =
                PasswordUtil.hashPassword(
                    "staff123"
                );

        System.out.println(hash);
    }
}