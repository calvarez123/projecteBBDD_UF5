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
        
    }

    public static void addProfessor(Connection conn, int id, String nom, String departament) throws SQLException {
   
    }

    public static void addCourse(Connection conn, int id, String nom, int professorId) throws SQLException {
     
    }

    public static void listProfessors(Connection conn) throws SQLException {
      
    }
    
    public static void listCourses(Connection conn) throws SQLException {
        
    }

    public static void deleteCourse(Connection conn, int courseId) throws SQLException {
      
    }
    

    public static void updateProfessorDepartment(Connection conn, int id, String newDepartament) throws SQLException {
        
    }
    
    
}
