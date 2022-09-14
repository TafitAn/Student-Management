package school.studentmanagement;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class bullNoteController {


    @FXML
    private Label coefLabel;

    @FXML
    private Label libLabel;

    @FXML
    private Label noteLabel;

    @FXML
    private Label pondLabel;

    @FXML
    private AnchorPane subjectm2Bar;

    public Label getCoefLabel() {
        return coefLabel;
    }

    public Label getLibLabel() {
        return libLabel;
    }

    public Label getNoteLabel() {
        return noteLabel;
    }

    public Label getPondLabel() {
        return pondLabel;
    }

    public AnchorPane getSubjectm2Bar() {
        return subjectm2Bar;
    }
}
