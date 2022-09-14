package school.studentmanagement;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class SubjectM2Controller {



    @FXML
    private AnchorPane subjectm2Bar;

    @FXML
    private Button subjectm2barlabel;
    /*@FXML
    void displayNote(ActionEvent event) throws IOException {


    }*/

    public AnchorPane getSubjectm2Bar() {
        return subjectm2Bar;
    }

    public Button getSubjectm2barlabel() {
        return subjectm2barlabel;
    }
}