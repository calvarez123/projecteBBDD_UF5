package com.project;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainA {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);

        AppData db = AppData.getInstance();

        // Crear las tablas relacionadas con estudiantes, asignaturas y matriculaciones
        createTables();

        // Agregar estudiantes y listarlos
        System.out.println("Agregando estudiantes...");
        addEstudiant("Juan", "Perez", 2022);
        addEstudiant("Ana", "Garcia", 2021);
        listEstudiants();

        // Agregar asignaturas y listarlas
        System.out.println("\nAgregando asignaturas...");
        addAssignatura("Matemáticas", 3);
        addAssignatura("Literatura", 4);
        listAssignatures();

        // Agregar matriculaciones y listarlas
        System.out.println("\nAgregando matriculaciones...");
        addMatriculacio(1, 1, 5);
        addMatriculacio(2, 2, 8);
        listMatriculacions();

        // Actualizar la información de un estudiante
        System.out.println("\nActualizando estudiante...");
        updateEstudiant(1, "Juan", "Lopez", 2022);
        listEstudiants();

        // Eliminar un estudiante y listar nuevamente
        System.out.println("\nEliminando un estudiante...");
        deleteEstudiant(2);
        listEstudiants();

        // Cerrar la conexión a la base de datos
        db.close();
    }

    public static void createTables() {
        AppData db = AppData.getInstance();
        db.update("DROP TABLE IF EXISTS Estudiants");
        db.update("DROP TABLE IF EXISTS Assignatures");
        db.update("DROP TABLE IF EXISTS Matriculacions");
        db.update("CREATE TABLE Estudiants (estudiantId INTEGER PRIMARY KEY, nom TEXT, cognoms TEXT, anyMatricula INTEGER)");
        db.update("CREATE TABLE Assignatures (assignaturaId INTEGER PRIMARY KEY, nom TEXT, credits INTEGER)");
        db.update("CREATE TABLE Matriculacions (estudiantId INTEGER, assignaturaId INTEGER, notaFinal INTEGER, FOREIGN KEY (estudiantId) REFERENCES Estudiants(estudiantId), FOREIGN KEY (assignaturaId) REFERENCES Assignatures(assignaturaId))");
    }

    public static void addEstudiant(String nom, String cognoms, int anyMatricula) {
        String sql = "INSERT INTO Estudiants (nom, cognoms, anyMatricula) VALUES ('" + escapeSQL(nom) + "', '" + escapeSQL(cognoms) + "', " + anyMatricula + ")";
        AppData.getInstance().update(sql);
    }

    public static void addAssignatura(String nom, int credits) {
        String sql = "INSERT INTO Assignatures (nom, credits) VALUES ('" + escapeSQL(nom) + "', " + credits + ")";
        AppData.getInstance().update(sql);
    }

    public static void addMatriculacio(int estudiantId, int assignaturaId, int notaFinal) {
        String sql = "INSERT INTO Matriculacions (estudiantId, assignaturaId, notaFinal) VALUES (" + estudiantId + ", " + assignaturaId + ", " + notaFinal + ")";
        AppData.getInstance().update(sql);
    }

    public static void listEstudiants() {
        String sql = "SELECT * FROM Estudiants";
        List<Map<String, Object>> estudiants = AppData.getInstance().query(sql);
        estudiants.forEach(e -> System.out.println("id: " + e.get("estudiantId") + ", nom: " + e.get("nom") + ", cognoms: " + e.get("cognoms") + ", anyMatricula: " + e.get("anyMatricula")));
    }

    public static void listAssignatures() {
        String sql = "SELECT * FROM Assignatures";
        List<Map<String, Object>> assignatures = AppData.getInstance().query(sql);
        assignatures.forEach(a -> System.out.println("id: " + a.get("assignaturaId") + ", nom: " + a.get("nom") + ", credits: " + a.get("credits")));
    }

    public static void listMatriculacions() {
        String sql = "SELECT * FROM Matriculacions";
        List<Map<String, Object>> matriculacions = AppData.getInstance().query(sql);
        matriculacions.forEach(m -> System.out.println("id estudiante: " + m.get("estudiantId") + ", id asignatura: " + m.get("assignaturaId") + ", notaFinal: " + m.get("notaFinal")));
    }

    public static void updateEstudiant(int estudiantId, String nom, String cognoms, int anyMatricula) {
        String sql = "UPDATE Estudiants SET nom = '" + escapeSQL(nom) + "', cognoms = '" + escapeSQL(cognoms) + "', anyMatricula = " + anyMatricula + " WHERE estudiantId = " + estudiantId;
        AppData.getInstance().update(sql);
    }

    public static void deleteEstudiant(int estudiantId) {
        String sql = "DELETE FROM Estudiants WHERE estudiantId = " + estudiantId;
        AppData.getInstance().update(sql);
    }

    private static String escapeSQL(String input) {
        return input.replace("'", "''");
    }
}
