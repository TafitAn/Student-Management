package school.studentmanagement;

public class Matiere {

    private int codemat;
    private String libelle;
    private  int coef;
    private String niveau;

    public Matiere(){

    }
    public Matiere(int codemat, String libelle, int coef, String niveau){

        this.codemat = codemat;
        this.libelle = libelle;
        this.coef = coef;
        this.niveau = niveau;
    }

    public int getCodemat() {
        return codemat;
    }

    public void setCodemat(int codemat) {
        this.codemat = codemat;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getCoef() {
        return coef;
    }

    public void setCoef(int coef) {
        this.coef = coef;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }
}
