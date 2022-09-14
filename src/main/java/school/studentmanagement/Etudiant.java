package school.studentmanagement;

public class Etudiant {

    private int id;
    private int num_Et;
    private String nom;
    private String niveau;

    public Etudiant() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNum_Et() {
        return num_Et;
    }

    public void setNum_Et(int num_Et) {
        this.num_Et = num_Et;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public Etudiant(int id, int num_Et, String nom, String niveau) {

        this.id = id;
        this.num_Et = num_Et;
        this.nom = nom;
        this.niveau = niveau;
    }
}