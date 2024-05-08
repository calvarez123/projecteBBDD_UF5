package com.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.github.stefanbirkner.systemlambda.SystemLambda;

import java.io.File;
import java.sql.*;
import java.util.Locale;

public class TestMain {

    @Test
    public void testMainOutputA() throws Exception {
        System.setProperty("environment", "test");

        String text = SystemLambda.tapSystemOut(() -> {
            String[] args = {}; 
            MainA.main(args);
        });
        text = text.replace("\r\n", "\n");

        String expectedOutput = """
            Agregando estudiantes...
            id: 1, nom: Juan, cognoms: Perez, anyMatricula: 2022
            id: 2, nom: Ana, cognoms: Garcia, anyMatricula: 2021

            Agregando asignaturas...
            id: 1, nom: Matemáticas, credits: 3
            id: 2, nom: Literatura, credits: 4

            Agregando matriculaciones...
            id estudiante: 1, id asignatura: 1, notaFinal: 5
            id estudiante: 2, id asignatura: 2, notaFinal: 8

            Actualizando estudiante...
            id: 1, nom: Juan, cognoms: Lopez, anyMatricula: 2022
            id: 2, nom: Ana, cognoms: Garcia, anyMatricula: 2021

            Eliminando un estudiante...
            id: 1, nom: Juan, cognoms: Lopez, anyMatricula: 2022
            """.replace("\r\n", "\n").replace("            ","");

        String diff = TestStringUtils.findFirstDifference(text, expectedOutput);
        assertTrue(diff.compareTo("identical") == 0, 
            "\n>>>>>>>>>> >>>>>>>>>>\n" +
            diff +
            "<<<<<<<<<< <<<<<<<<<<\n");
    }

    // Additional test methods can be modeled after testMainOutputA.

    @Test
    public void testMainTablesA() throws SQLException {
        System.setProperty("environment", "test");
        String url = "jdbc:sqlite:dades.sqlite";

        File dbFile = new File("dades.sqlite");
        assertTrue(dbFile.exists(), "The database file does not exist.");

        try (Connection conn = DriverManager.getConnection(url)) {
            DatabaseMetaData dbMetaData = conn.getMetaData();

            // Check for existence of Estudiants table
            checkTableExists(dbMetaData, "Estudiants", "estudiantId", "nom", "cognoms", "anyMatricula");

            // Check for existence of Assignatures table
            checkTableExists(dbMetaData, "Assignatures", "assignaturaId", "nom", "credits");

            // Check for existence of Matriculacions table
            checkTableExists(dbMetaData, "Matriculacions", "estudiantId", "assignaturaId", "notaFinal");
        }
    }

    private void checkTableExists(DatabaseMetaData metaData, String tableName, String... columnNames) throws SQLException {
        try (ResultSet rs = metaData.getTables(null, null, tableName, null)) {
            assertTrue(rs.next(), "Table " + tableName + " does not exist.");
        }

        for (String columnName : columnNames) {
            try (ResultSet rs = metaData.getColumns(null, null, tableName, columnName)) {
                assertTrue(rs.next(), "Column " + columnName + " does not exist in table " + tableName);
            }
        }
    }

}
