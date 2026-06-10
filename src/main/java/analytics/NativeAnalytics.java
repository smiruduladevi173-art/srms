package analytics;

import database.DBConnection;

import java.io.*;
import java.sql.*;

public class NativeAnalytics {

    // =====================================
    // EXPORT MARKS
    // =====================================

    public static void exportMarks() {

        try {

            Connection con =
                    DBConnection.connect();

            String sql =
                    "SELECT marks FROM marks";

            PreparedStatement pst =
                    con.prepareStatement(
                            sql
                    );

            ResultSet rs =
                    pst.executeQuery();

            FileWriter writer =
                    new FileWriter(
                            "cbackend/input.txt"
                    );

            while (rs.next()) {

                writer.write(
                        rs.getInt(
                                "marks"
                        )
                                + "\n"
                );
            }

            writer.close();

            rs.close();

            pst.close();

        }

        catch (Exception e) {

            e.printStackTrace();
        }
    }

// =====================================
// RUN C ENGINE
// =====================================

public static void runEngine() {

    try {

        File exe =
                new File(
                        "cbackend/analytics_engine.exe"
                );

        System.out.println(
                "EXE EXISTS = "
                        + exe.exists()
        );

        System.out.println(
                "EXE PATH = "
                        + exe.getAbsolutePath()
        );

        ProcessBuilder pb =
                new ProcessBuilder(
                        exe.getAbsolutePath()
                );

        pb.directory(
                new File(
                        "cbackend"
                )
        );

        Process process =
                pb.start();

        int exitCode =
                process.waitFor();

        System.out.println(
                "C EXIT CODE = "
                        + exitCode
        );

    }

    catch (Exception e) {

        e.printStackTrace();
    }
}

    // =====================================
    // READ RESULTS
    // =====================================

    public static String readResults() {

        StringBuilder sb =
                new StringBuilder();

        try {

            File file =
                    new File(
                            "cbackend/output.txt"
                    );

            System.out.println(
                    "Working Directory: "
                            + System.getProperty(
                                    "user.dir"
                            )
            );

            System.out.println(
                    "OUTPUT EXISTS = "
                            + file.exists()
            );

            System.out.println(
                    "OUTPUT SIZE = "
                            + file.length()
            );

            if (!file.exists()) {

                return "Analytics output not generated";
            }

            BufferedReader br =
                    new BufferedReader(
                            new FileReader(
                                    file
                            )
                    );

            String line;

            while (
                    (line = br.readLine())
                            != null
            ) {

                sb.append(
                        line
                ).append(
                        "\n"
                );
            }

            br.close();

        }

        catch (Exception e) {

            e.printStackTrace();

            return "Analytics Error";
        }

        return sb.toString();
    }

    // =====================================
    // GENERATE
    // =====================================

    public static String generateAnalytics() {

        System.out.println(
                "NativeAnalytics FINAL VERSION"
        );

        exportMarks();

        runEngine();

        return readResults();
    }
}