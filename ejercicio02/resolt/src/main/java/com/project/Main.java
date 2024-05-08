package com.project;

import java.sql.*;
import java.util.Locale;
import main.java.com.project.Curs;
import main.java.com.project.Professor;

public class Main {

    private static String URL = "jdbc:mysql://localhost:3308/institut?useSSL=false&allowPublicKeyRetrieval=true";
    private static String USER = "root";
    private static String PASSWORD = "pwd";

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            conn.setAutoCommit(false);
    
            createTables(conn);
    
            // Añadir profesores y cursos
            addProfessor(conn, 1, "Juan Pérez", "Matemáticas");
            addProfessor(conn, 2, "Ana Gómez", "Física");
            addCourse(conn, 1, "Cálculo", 1);
            addCourse(conn, 2, "Mecánica Clásica", 2);
    
            // Actualizar el departamento de un profesor
            updateProfessorDepartment(conn, 1, "Ciencias Computacionales");
    
            // Eliminar un curso
            deleteCourse(conn, 2);
    
            listProfessors(conn);
            listCourses(conn);
    
            conn.commit();
    
            conn.close();
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        if (!"test".equals(System.getProperty("environment"))) {
            System.exit(0);
        }
    }

    public static void createTables(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("SET FOREIGN_KEY_CHECKS=0;");

            stmt.execute("DROP TABLE IF EXISTS Cursos");
            stmt.execute("DROP TABLE IF EXISTS Professors");

            stmt.execute("CREATE TABLE Professors (" +
                         "professorId INTEGER PRIMARY KEY," +
                         "nom VARCHAR(255)," +
                         "departament VARCHAR(255));");

            stmt.execute("CREATE TABLE Cursos (" +
                         "cursId INTEGER PRIMARY KEY," +
                         "nom VARCHAR(255)," +
                         "professorId INTEGER," +
                         "FOREIGN KEY (professorId) REFERENCES Professors(professorId));");
        }
    }

    public static void addProfessor(Connection conn, int id, String nom, String departament) throws SQLException {
        String sql = "INSERT INTO Professors (professorId, nom, departament) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, nom);
            pstmt.setString(3, departament);
            pstmt.executeUpdate();
        }
    }

    public static void addCourse(Connection conn, int id, String nom, int professorId) throws SQLException {
        String sql = "INSERT INTO Cursos (cursId, nom, professorId) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, nom);
            pstmt.setInt(3, professorId);
            pstmt.executeUpdate();
        }
    }

    public static void listProfessors(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Professors";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("professorId");
                String nom = rs.getString("nom");
                String departament = rs.getString("departament");
    
                Professor professor = new Professor(id, nom, departament);
                System.out.println(professor);
            }
        }
    }
    
    public static void listCourses(Connection conn) throws SQLException {
        String sql = "SELECT C.cursId, C.nom, P.professorId, P.nom AS professorNom, P.departament " +
                     "FROM Cursos C JOIN Professors P ON C.professorId = P.professorId";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int cursId = rs.getInt("cursId");
                String nom = rs.getString("nom");
                int professorId = rs.getInt("professorId");
                String professorNom = rs.getString("professorNom");
                String departament = rs.getString("departament");
    
                Professor professor = new Professor(professorId, professorNom, departament);
                Curs course = new Curs(cursId, nom, professor);
                System.out.println(course);
            }
        }
    }

    public static void deleteCourse(Connection conn, int courseId) throws SQLException {
        String sql = "DELETE FROM Cursos WHERE cursId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, courseId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Curso eliminado con éxito con ID: " + courseId);
            } else {
                System.out.println("No se encontró el curso con ID: " + courseId);
            }
        }
    }
    

    public static void updateProfessorDepartment(Connection conn, int id, String newDepartament) throws SQLException {
        String sql = "UPDATE Professors SET departament = ? WHERE professorId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newDepartament);
            pstmt.setInt(2, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Departamento actualizado para el profesor con ID: " + id);
            } else {
                System.out.println("No se encontró el profesor con ID: " + id);
            }
        }
    }
    
    
}
