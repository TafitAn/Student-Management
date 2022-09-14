package school.studentmanagement;

public class Notes {
    int num_inscription;
    int num_Et;
    String nom_Et;
    String nom_mat;
    int codemat;
    double note;
    int coefficient;

    public  Notes(){}

    public Notes(int num_inscription, int num_Et, String nom_Et, String nom_mat, int codemat, double note, int coefficient){

        this.num_inscription = num_inscription;
        this.num_Et = num_Et;
        this.nom_Et = nom_Et;
        this.nom_mat = nom_mat;
        this.codemat = codemat;
        this.note = note;
        this.coefficient = coefficient;
    }

    public int getNum_inscription() {
        return num_inscription;
    }

    public void setNum_inscription(int num_inscription) {
        this.num_inscription = num_inscription;
    }

    public int getNum_Et() {
        return num_Et;
    }

    public void setNum_Et(int num_Et) {
        this.num_Et = num_Et;
    }

    public String getNom_Et() {
        return nom_Et;
    }

    public void setNom_Et(String nom_Et) {
        this.nom_Et = nom_Et;
    }

    public String getNom_mat() {
        return nom_mat;
    }

    public void setNom_mat(String nom_mat) {
        this.nom_mat = nom_mat;
    }

    public int getCodemat() {
        return codemat;
    }

    public void setCodemat(int codemat) {
        this.codemat = codemat;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }
}