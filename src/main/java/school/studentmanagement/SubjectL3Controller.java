package school.studentmanagement;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class SubjectL3Controller {

    public AnchorPane getSubjectL3Bar() {
        return subjectL3Bar;
    }

    public Button getSubjectL3barlabel() {
        return subjectL3barlabel;
    }

    @FXML
    private AnchorPane subjectL3Bar;

    @FXML
    private Button subjectL3barlabel;
}