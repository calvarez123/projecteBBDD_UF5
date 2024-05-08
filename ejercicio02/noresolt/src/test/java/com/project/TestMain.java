package com.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.github.stefanbirkner.systemlambda.SystemLambda;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.Locale;

public class TestMain {

    private static final String URL = "jdbc:mysql://localhost:3308/institut?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";
    private static final String PASSWORD = "pwd";

    @Test
    public void testMainOutput() throws Exception {
        System.setProperty("environment", "test");

        String text = SystemLambda.tapSystemOut(() -> {
            // Establecer el idioma predeterminado para asegurarse de que las salidas de fecha se comporten de manera consistente
            Locale.setDefault(Locale.US);

            // Crea y usa una conexión que será cerrada automáticamente al final del bloque try
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                conn.setAutoCommit(false);

                Main.createTables(conn);
                Main.addProfessor(conn, 1, "Juan Pérez", "Matemáticas");
                Main.addProfessor(conn, 2, "Ana Gómez", "Física");
                Main.addCourse(conn, 1, "Cálculo", 1);
                Main.addCourse(conn, 2, "Mecánica Clásica", 2);

                Main.listProfessors(conn);
                Main.listCourses(conn);

                conn.commit();
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        });

        String expectedOutput = """
            [professorId=1, nom='Juan Pérez', departament='Matemáticas']
            [professorId=2, nom='Ana Gómez', departament='Física']
            [cursId=1, nom='Cálculo', professor=[professorId=1, nom='Juan Pérez', departament='Matemáticas']]
            [cursId=2, nom='Mecánica Clásica', professor=[professorId=2, nom='Ana Gómez', departament='Física']]
            """;

        assertTrue(text.contains(expectedOutput), "Output does not contain expected text.");
    }

    @Test
    public void testMainTables() throws Exception {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            DatabaseMetaData dbMetaData = conn.getMetaData();

            checkTableExists(dbMetaData, "Professors");
            checkTableExists(dbMetaData, "Cursos");
        }
    }

    private void checkTableExists(DatabaseMetaData metaData, String tableName) throws SQLException {
        try (ResultSet rs = metaData.getTables(null, null, tableName, null)) {
            assertTrue(rs.next(), "Table " + tableName + " does not exist.");
        }
    }

    @Test
    public void testMainCalls() throws NoSuchMethodException {
        Class<Main> clazz = Main.class;

        // Verify that all the expected methods are present and have correct modifiers
        Method createTables = clazz.getDeclaredMethod("createTables", Connection.class);
        assertTrue(Modifier.isStatic(createTables.getModifiers()), "createTables should be static");

        Method addProfessor = clazz.getDeclaredMethod("addProfessor", Connection.class, int.class, String.class, String.class);
        assertTrue(Modifier.isStatic(addProfessor.getModifiers()), "addProfessor should be static");

        Method addCourse = clazz.getDeclaredMethod("addCourse", Connection.class, int.class, String.class, int.class);
        assertTrue(Modifier.isStatic(addCourse.getModifiers()), "addCourse should be static");

        Method listProfessors = clazz.getDeclaredMethod("listProfessors", Connection.class);
        assertTrue(Modifier.isStatic(listProfessors.getModifiers()), "listProfessors should be static");

        Method listCourses = clazz.getDeclaredMethod("listCourses", Connection.class);
        assertTrue(Modifier.isStatic(listCourses.getModifiers()), "listCourses should be static");
    }

    @Test
    public void testUpdateProfessorDepartment() throws Exception {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            conn.setAutoCommit(false);
            
            Main.createTables(conn);
            Main.addProfessor(conn, 3, "Carlos Ruiz", "Historia");
            Main.updateProfessorDepartment(conn, 3, "Ciencias Sociales");

            // Verificar el cambio
            String sql = "SELECT departament FROM Professors WHERE professorId = 3";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
                assertTrue(rs.next(), "Debe encontrar el profesor actualizado");
                assertEquals("Ciencias Sociales", rs.getString("departament"), "El departamento debe haber sido actualizado");
            }

            conn.rollback();  // Revertimos para no afectar otros tests
        }
    }

  
}
