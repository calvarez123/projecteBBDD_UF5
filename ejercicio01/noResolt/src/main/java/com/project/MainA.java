package com.project;

import java.util.List;
import java.util.Locale;

public class MainA {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);

        AppData db = AppData.getInstance();

        // Crear las tablas
        createTables();

        // Agregar estudiantes y listarlos
        System.out.println("Agregando estudiantes iniciales...");
        addEstudiant("Maria", "Gomez", 2019);
        addEstudiant("Carlos", "Perez", 2020);
        listEstudiants();

        // Agregar asignaturas y listarlas
        System.out.println("\nAgregando asignaturas...");
        addAssignatura("Matemáticas", 4);
        addAssignatura("Física", 3);
        listAssignatures();

        // Matricular estudiantes y listar matriculaciones
        System.out.println("\nMatriculando estudiantes...");
        addMatriculacio(1, 1, 10);
        addMatriculacio(2, 2, 9);
        listMatriculacions();

        // Actualizar información de un estudiante
        System.out.println("\nActualizando información de un estudiante...");
        updateEstudiant(1, "Maria", "Gomez Ruiz", 2019);
        listEstudiants();

        // Borrar un estudiante y listar nuevamente
        System.out.println("\nEliminando un estudiante...");
        deleteEstudiant(2);
        listEstudiants();

        // Mostrar nombre del estudiante con la nota más alta en una asignatura
        System.out.println("\nEstudiante con la mejor nota en Matemáticas...");
        nombreAlumnoPorAsignatura(1);

        // Cerrar la conexión a la base de datos
        db.close();
    }

    public static void createTables() {
    }

    public static void addEstudiant(String nom, String cognoms, int anyMatricula) {
    }

    public static void addAssignatura(String nom, int credits) {
    }

    public static void addMatriculacio(int estudiantId, int assignaturaId, int notaFinal) {
    }

    public static void listEstudiants() {
        }

    public static void listAssignatures() {
     }

    public static void listMatriculacions() {
       }

    public static void updateEstudiant(int estudiantId, String nom, String cognoms, int anyMatricula) {
       }

    public static void deleteEstudiant(int estudiantId) {
       }

    public static void deleteAssignatura(int assignaturaId) {
       }

    public static void nombreAlumnoPorAsignatura(int assignaturaId) {
        }

    // Método auxiliar para escapar comillas simples que podrían romper la cadena SQL
    private static String escapeSQL(String input) {
        return input.replace("'", "''");
    }
}
