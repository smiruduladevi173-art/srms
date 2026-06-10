package auth;

import database.DBConnection;
import student.StudentSession;
import utils.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Authentication {

    // =====================================
    // STORE LOGGED-IN STUDENT ID
    // =====================================

    public static int loggedStudentId = -1;

    // =====================================
    // LOGIN METHOD
    // =====================================

    public static String login(
            String username,
            String password
    ) {

        try {

            Connection conn =
                    DBConnection.connect();

            // =====================================
            // REMOVE EXTRA SPACES
            // =====================================

            username = username.trim();

            password = password.trim();

            // =====================================
            // GET USER BY USERNAME ONLY
            // =====================================

            String sql = """
                    SELECT *
                    FROM users
                    WHERE username = ?
                    """;

            PreparedStatement pst =
                    conn.prepareStatement(sql);

            pst.setString(1, username);

            ResultSet rs =
                    pst.executeQuery();

            // =====================================
            // USER FOUND
            // =====================================

            if (rs.next()) {

                String dbPassword =
                        rs.getString("password");

                String role =
                        rs.getString("role");

                int userId =
                        rs.getInt("id");

                // =====================================
                // PASSWORD VERIFICATION
                // =====================================

                boolean valid = false;

                // =====================================
                // ARGON2 HASHED PASSWORD
                // =====================================

                if(dbPassword.startsWith("$argon2")) {

                    System.out.println(
                            "DB PASSWORD = "
                                    + dbPassword
                    );

                    System.out.println(
                            "ENTERED PASSWORD = "
                                    + password
                    );

                    valid =
                            PasswordUtil.verifyPassword(
                                    dbPassword,
                                    password
                            );

                    System.out.println(
                            "VALID = " + valid
                    );

                } else {

                    // =====================================
                    // OLD PLAINTEXT PASSWORD SUPPORT
                    // =====================================

                    valid =
                            password.equals(dbPassword);
                }

                // =====================================
                // LOGIN SUCCESS
                // =====================================

                if(valid) {

                    System.out.println(
                            "LOGIN SUCCESS: "
                                    + username
                                    + " ROLE: "
                                    + role
                    );

                    // RESET SESSION

                    loggedStudentId = -1;

                    StudentSession.loggedStudentId = -1;

                    StudentSession.loggedInUserId =
                            userId;

                    // =====================================
                    // IF STUDENT LOGIN
                    // =====================================

                    if(role.equals("STUDENT")) {

                        String studentSql = """
                                SELECT student_id
                                FROM students
                                WHERE user_id = ?
                                """;

                        PreparedStatement pst2 =
                                conn.prepareStatement(
                                        studentSql
                                );

                        pst2.setInt(1, userId);

                        ResultSet rs2 =
                                pst2.executeQuery();

                        if(rs2.next()) {

                            int studentId =
                                    rs2.getInt(
                                            "student_id"
                                    );

                            loggedStudentId =
                                    studentId;

                            StudentSession.loggedStudentId =
                                    studentId;

                            System.out.println(
                                    "LOGGED STUDENT ID = "
                                            + studentId
                            );
                        }

                        rs2.close();

                        pst2.close();
                    }

                    rs.close();

                    pst.close();

                    return role;
                }
            }

            // =====================================
            // LOGIN FAILED
            // =====================================

            System.out.println(
                    "INVALID USERNAME OR PASSWORD"
            );

            rs.close();

            pst.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }
}