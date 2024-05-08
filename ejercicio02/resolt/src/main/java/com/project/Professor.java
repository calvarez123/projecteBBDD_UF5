package main.java.com.project;

public class Professor extends Persona {
    private String departament;

    public Professor(int id, String nom, String departament) {
        super(id, nom);
        this.departament = departament;
    }

    @Override
    public String toString() {
        return String.format("[professorId=%d, nom='%s', departament='%s']", id, nom, departament);
    }
}