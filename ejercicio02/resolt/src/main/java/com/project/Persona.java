package main.java.com.project;

public abstract class Persona {
    protected int id;
    protected String nom;

    public Persona(int id, String nom) {
        this.id = id;
        this.nom = nom;
    }

    public abstract String toString();
}
