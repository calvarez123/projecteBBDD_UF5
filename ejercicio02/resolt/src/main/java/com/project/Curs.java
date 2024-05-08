package main.java.com.project;


public class Curs {
    private int cursId;
    private String nom;
    private Professor professor;

    public Curs(int cursId, String nom, Professor professor) {
        this.cursId = cursId;
        this.nom = nom;
        this.professor = professor;
    }

    @Override
    public String toString() {
        return String.format("[cursId=%d, nom='%s', professor=%s]", cursId, nom, professor);
    }
}