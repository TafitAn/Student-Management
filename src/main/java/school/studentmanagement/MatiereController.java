package school.studentmanagement;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class MatiereController implements Initializable {

    @FXML
    private Button Closebtn;

    @FXML
    private Button Closebtn1;
    @FXML
    private Button addBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button updateButton;

    @FXML
    private Pane L1pane;

    @FXML
    private Pane L2pane;

    @FXML
    private Pane L3pane;

    @FXML
    private Pane M1pane;

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private AnchorPane anchorpane1;

    @FXML
    private Label coefLabel;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<?, ?> idStudent;

    @FXML
    private Button logbtn;

    @FXML
    private Pane m2pane;

    @FXML
    private Button minimButn;

    @FXML
    private Label niveauMat;

    @FXML
    private TableColumn<?, ?> nomStudent;

    @FXML
    private Label nomatLabel;

    @FXML
    private TextField noteField;

    @FXML
    private TableColumn<?, ?> noteStudent;

    @FXML
    private TableColumn<?, ?> numStudent;

    @FXML
    private VBox subjectl1list;

    @FXML
    private VBox subjectl2list;

    @FXML
    private VBox subjectl3list;

    @FXML
    private VBox subjectm1list;

    @FXML
    private VBox subjectm2list;

    @FXML
    private TableView<Notes> tableNote;
    @FXML
    private TextField coefT;
    @FXML
    private TextField libT;
    @FXML
    private ComboBox<String> niveauComb;

    @FXML
    private Label codematLab;
    @FXML
    private AnchorPane updateBox;
    @FXML
    private Button updateN;

    @FXML
    private Button updateY;
    @FXML
    private TextField codemaT;
    @FXML
    private AnchorPane deleteBox;

    @FXML
    private Button deleteN;

    @FXML
    private Button deleteY;

    @FXML
    private AnchorPane AddN;
    @FXML
    private AnchorPane delN;

    @FXML
    private AnchorPane UpN;
    @FXML
    private AnchorPane UpNote;




    Stage stage;
    static HttpURLConnection connection, connection1;
    ObservableList<Matiere> list;

    @FXML
    private void closeButton(ActionEvent event) {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void minimizeButton(ActionEvent event) {
        stage = (Stage) anchorpane.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void handleButton(ActionEvent e) throws Exception {

        if (e.getSource() == logbtn) {
            stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            changeScene(stage, "home.fxml");
        }
        if(e.getSource() == Closebtn1){

            anchorpane1.setVisible(false);
        }
        if(e.getSource() == addBtn){
            String libelle = libT.getText();
            int coef = Integer.parseInt(coefT.getText());
            String niveau = niveauComb.getValue();

            creatSubject(libelle, coef, niveau);
            libT.clear();
            coefT.clear();
            codemaT.clear();
            AddN.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> AddN.setVisible(false));
            pause.play();

            getListL1();
            getListL2();
            getListL3();
            getListM1();
            getListM2();

        }
        if (e.getSource() == updateBtn){

            updateBox.setVisible(true);
        }
        if (e.getSource() == deleteBtn){
            deleteBox.setVisible(true);

        }
        if(e.getSource() == updateN){
            updateBox.setVisible(false);
        }
        if (e.getSource() == updateY){
            updateBox.setVisible(false);
            int codemat = Integer.parseInt(codemaT.getText());
            String libelle = libT.getText();
            int coef = Integer.parseInt(coefT.getText());
            String niveau = niveauComb.getValue();

            updateSubject(codemat, libelle, coef, niveau);
            UpN.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> UpN.setVisible(false));
            pause.play();

            getListL1();
            getListL2();
            getListL3();
            getListM1();
            getListM2();
            libT.clear();
            coefT.clear();
            codemaT.clear();
        }
        if (e.getSource() == deleteN){
            deleteBox.setVisible(false);
        }
        if(e.getSource() == deleteY){
            deleteBox.setVisible(false);
            int x = Integer.parseInt(codematLab.getText());
            deletSubject(x);
            getListL1();
            getListL2();
            getListL3();
            getListM1();
            getListM2();
            anchorpane1.setVisible(false);
            delN.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> delN.setVisible(false));
            pause.play();

        }
        if(e.getSource() == updateButton){
            Notes noteselected = new Notes();
            noteselected = (Notes)tableNote.getSelectionModel().getSelectedItem();
            double grad = Double.parseDouble(noteField.getText());
            updateNote(noteselected.getCodemat(), noteselected.getNum_inscription(), grad);
            int x = Integer.parseInt(codematLab.getText());
            tableNote.setItems(getgradList(x));
            noteField.clear();
            UpNote.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> UpNote.setVisible(false));
            pause.play();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //addNoteAll();
        getListL1();
        getListL2();
        getListL3();
        getListM1();
        getListM2();

        idStudent.setCellValueFactory(new PropertyValueFactory<>("num_inscription"));
        numStudent.setCellValueFactory(new PropertyValueFactory<>("num_Et"));
        nomStudent.setCellValueFactory(new PropertyValueFactory<>("nom_Et"));
        noteStudent.setCellValueFactory(new PropertyValueFactory<>("note"));

        niveauComb.getItems().addAll("M2", "M1", "L3", "L2", "L1");

    }

    private void changeScene(Stage stage, String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            this.stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------Get list of M2 subject----------------------------------------------------------------------
    public void getListL1() {
        subjectl1list.getChildren().clear();
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        ObservableList<Matiere> listL1;
        try {
            URL urlGet = new URL("http://localhost/java/api/matiere/read.php");
            connection = (HttpURLConnection) urlGet.openConnection();

            //setup
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            //System.out.println(status);
            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }

            JSONArray matieres = new JSONArray(responseContent.toString());
            listL1 = FXCollections.observableArrayList();
            Node[] nodes = new Node[matieres.length()];

            for (int i = 0; i < matieres.length(); i++) {
                JSONObject matiere = matieres.getJSONObject(i);
                //String note_Et = etudiant.getString("note_Et");
                int codemat = matiere.getInt("codemat");
                String libelle = matiere.getString("libelle");
                int coef = matiere.getInt("coef");
                String niveau = matiere.getString("niveau");

                Matiere subject = new Matiere(codemat, libelle, coef, niveau);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("subject.fxml"));

                if ((subject.getNiveau()).equals("L1")) {
                    nodes[i] = loader.load();
                    SubjectController taolana = loader.getController();
                    taolana.getSubjectbarlabel().setText(codemat+"  "+ libelle);

                    codematLab.setText(String.valueOf(codemat));
                    taolana.getSubjectbarlabel().setOnAction(e -> {

                        anchorpane1.setVisible(true);
                        ObservableList<Notes> listA = getgradList(codemat);
                        tableNote.setItems(listA);
                        nomatLabel.setText(libelle);
                        coefLabel.setText(String.valueOf(coef));
                        niveauMat.setText(niveau);
                        codematLab.setText(String.valueOf(codemat));
                    });
                    subjectl1list.getChildren().add(nodes[i]);

                }
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            connection.disconnect();
        }
    }

    //-------------------------------------------------------------------------Get list of L2 subject----------------------------------------------------------------------
    public void getListL2() {
        subjectl2list.getChildren().clear();
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        ObservableList<Matiere> listL2;
        try {
            URL urlGet = new URL("http://localhost/java/api/matiere/read.php");
            connection = (HttpURLConnection) urlGet.openConnection();

            //setup
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            //System.out.println(status);
            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }

            JSONArray matieres = new JSONArray(responseContent.toString());
            listL2 = FXCollections.observableArrayList();
            Node[] nodes = new Node[matieres.length()];

            for (int i = 0; i < matieres.length(); i++) {
                JSONObject matiere = matieres.getJSONObject(i);
                //String note_Et = etudiant.getString("note_Et");
                int codemat = matiere.getInt("codemat");
                String libelle = matiere.getString("libelle");
                int coef = matiere.getInt("coef");
                String niveau = matiere.getString("niveau");

                Matiere subject = new Matiere(codemat, libelle, coef, niveau);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("subjectL2.fxml"));

                if ((subject.getNiveau()).equals("L2")) {
                    nodes[i] = loader.load();
                    SubjectL2Controller taolana = loader.getController();
                    taolana.getSubjectL2barlabel().setText(codemat+"  "+ libelle);

                    taolana.getSubjectL2barlabel().setOnAction(e -> {

                        anchorpane1.setVisible(true);
                        ObservableList<Notes> listB = getgradList(codemat);
                        nomatLabel.setText(libelle);
                        coefLabel.setText(String.valueOf(coef));
                        niveauMat.setText(niveau);
                        tableNote.setItems(listB);
                        codematLab.setText(String.valueOf(codemat));
                    });
                    subjectl2list.getChildren().add(nodes[i]);

                }
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            connection.disconnect();
        }
    }

    //-------------------------------------------------------------------------Get list of L2 subject----------------------------------------------------------------------
    public void getListL3() {
        subjectl3list.getChildren().clear();
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        ObservableList<Matiere> listL3;
        try {
            URL urlGet = new URL("http://localhost/java/api/matiere/read.php");
            connection = (HttpURLConnection) urlGet.openConnection();

            //setup
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            //System.out.println(status);
            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }

            JSONArray matieres = new JSONArray(responseContent.toString());
            listL3 = FXCollections.observableArrayList();
            Node[] nodes = new Node[matieres.length()];

            for (int i = 0; i < matieres.length(); i++) {
                JSONObject matiere = matieres.getJSONObject(i);
                //String note_Et = etudiant.getString("note_Et");
                int codemat = matiere.getInt("codemat");
                String libelle = matiere.getString("libelle");
                int coef = matiere.getInt("coef");
                String niveau = matiere.getString("niveau");

                Matiere subject = new Matiere(codemat, libelle, coef, niveau);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("subjectL3.fxml"));

                if ((subject.getNiveau()).equals("L3")) {
                    nodes[i] = loader.load();
                    SubjectL3Controller taolana = loader.getController();
                    taolana.getSubjectL3barlabel().setText(codemat+"  "+ libelle);

                    taolana.getSubjectL3barlabel().setOnAction(e -> {
                        anchorpane1.setVisible(true);
                        ObservableList<Notes> list = getgradList(codemat);
                        nomatLabel.setText(libelle);
                        coefLabel.setText(String.valueOf(coef));
                        niveauMat.setText(niveau);
                        tableNote.setItems(list);
                        codematLab.setText(String.valueOf(codemat));
                    });
                    subjectl3list.getChildren().add(nodes[i]);

                }
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            connection.disconnect();
        }
    }

    //-------------------------------------------------------------------------Get list of M2 subject----------------------------------------------------------------------
    public void getListM1() {
        subjectm1list.getChildren().clear();
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        ObservableList<Matiere> listM1;
        try {
            URL urlGet = new URL("http://localhost/java/api/matiere/read.php");
            connection = (HttpURLConnection) urlGet.openConnection();

            //setup
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            //System.out.println(status);
            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }

            JSONArray matieres = new JSONArray(responseContent.toString());
            listM1 = FXCollections.observableArrayList();
            Node[] nodes = new Node[matieres.length()];

            for (int i = 0; i < matieres.length(); i++) {
                JSONObject matiere = matieres.getJSONObject(i);
                //String note_Et = etudiant.getString("note_Et");
                int codemat = matiere.getInt("codemat");
                String libelle = matiere.getString("libelle");
                int coef = matiere.getInt("coef");
                String niveau = matiere.getString("niveau");

                Matiere subject = new Matiere(codemat, libelle, coef, niveau);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("subjectM1list.fxml"));

                if ((subject.getNiveau()).equals("M1")) {
                    nodes[i] = loader.load();
                    SubjectM1Controller taolana = loader.getController();
                    taolana.getSubjectm2barlabel().setText(codemat+"  "+ libelle);

                    taolana.getSubjectm2barlabel().setOnAction(e -> {

                        anchorpane1.setVisible(true);
                        ObservableList<Notes> list = getgradList(codemat);
                        nomatLabel.setText(libelle);
                        coefLabel.setText(String.valueOf(coef));
                        niveauMat.setText(niveau);
                        tableNote.setItems(list);
                        codematLab.setText(String.valueOf(codemat));
                    });
                    subjectm1list.getChildren().add(nodes[i]);

                }
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            connection.disconnect();
        }
    }

    //-------------------------------------------------------------------------Get list of M2 subject----------------------------------------------------------------------
    public void getListM2() {
        subjectm2list.getChildren().clear();
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        ObservableList<Matiere> listM2;
        try {
            URL urlGet = new URL("http://localhost/java/api/matiere/read.php");
            connection = (HttpURLConnection) urlGet.openConnection();

            //setup
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            //System.out.println(status);
            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }

            JSONArray matieres = new JSONArray(responseContent.toString());
            listM2 = FXCollections.observableArrayList();
            Node[] nodes = new Node[matieres.length()];

            for (int i = 0; i < matieres.length(); i++) {
                JSONObject matiere = matieres.getJSONObject(i);
                //String note_Et = etudiant.getString("note_Et");
                int codemat = matiere.getInt("codemat");
                String libelle = matiere.getString("libelle");
                int coef = matiere.getInt("coef");
                String niveau = matiere.getString("niveau");

                Matiere subject = new Matiere(codemat, libelle, coef, niveau);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("subjectM2.fxml"));

                if ((subject.getNiveau()).equals("M2")) {
                    nodes[i] = loader.load();
                    SubjectM2Controller taolana = loader.getController();
                    taolana.getSubjectm2barlabel().setText(codemat+"  "+ libelle);
                    taolana.getSubjectm2barlabel().setOnAction(e -> {

                        anchorpane1.setVisible(true);
                        ObservableList<Notes> list = getgradList(codemat);
                        nomatLabel.setText(libelle);
                        coefLabel.setText(String.valueOf(coef));
                        niveauMat.setText(niveau);
                        tableNote.setItems(list);
                        codematLab.setText(String.valueOf(codemat));

                    });
                    subjectm2list.getChildren().add(nodes[i]);

                }
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            connection.disconnect();
        }
    }

    //----------------------------------------------------------------

    public ObservableList<Notes> getgradList(int cm) {

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        ObservableList<Notes> gradlist;
        try {
            URL urlGet = new URL("http://localhost/java/api/notes/read.php");
            connection = (HttpURLConnection) urlGet.openConnection();

            //setup
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();

            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }

            JSONArray notes = new JSONArray(responseContent.toString());
            gradlist = FXCollections.observableArrayList();

            for (int i = 0; i < notes.length(); i++) {
                JSONObject grad = notes.getJSONObject(i);
                //String note_Et = etudiant.getString("note_Et");

                int num_inscription = grad.getInt("num_inscription");
                int num_Et = grad.getInt("num_Et");
                String nom_Et = grad.getString("nom_Et");
                String nom_mat = grad.getString("nom_mat");
                int codemat = grad.getInt("codemat");
                double note = grad.getDouble("note");
                int coefficient = grad.getInt("coefficient");

                Notes grade = new Notes(num_inscription, num_Et, nom_Et,nom_mat, codemat, note, coefficient);

                if (grade.getCodemat() == cm) {
                    gradlist.add(grade);
                }
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            connection.disconnect();
        }
        return gradlist;
    }

    //---------------------------------------------------------Create Subject--------------------------------------------------------------------
    public void creatSubject(String lib, int coef, String niv ) throws Exception{

        URL url = new URL("http://localhost/java/api/matiere/create.php");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        String jsonInputString = "{\"libelle\":\""+lib+"\", \"coef\":\""+coef+"\", \"niveau\":\""+niv+"\"}";

        try(OutputStream os = conn.getOutputStream()){

            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        try(BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null){
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }
        Matiere subject = getsubject(lib, niv);

        studentList(subject.getCodemat(), niv);
    }
    ////-------------------------------------------------------------------------Delete Subjrct----------------------------------------------------------------------
    public static  void deletSubject(int id) throws Exception{

        URL url = new URL("http://localhost/java/api/matiere/delete.php");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        String jsonInputString = "{\"codemat\":\""+id+"\"}";

        try(OutputStream os = conn.getOutputStream()){

            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        try(BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null){
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
            deleteNote(id);
        }
    }
    //-------------------------------------------------------------------------Update subject----------------------------------------------------------------------
    public static  void updateSubject(int codemat, String libelle, int coef, String niveau ) throws Exception{

        URL url = new URL("http://localhost/java/api/matiere/update.php");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        String jsonInputString = "{\"codemat\":\""+codemat+"\", \"libelle\":\""+libelle+"\", \"coef\":\""+coef+"\", \"niveau\":\""+niveau+"\"}";

        try(OutputStream os = conn.getOutputStream()){

            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        try(BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null){
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }
    }
    //------------------------------------------------------------------------GET SINGLE SUBJECT-----------------------------------------------
    public Matiere getsubject(String lib, String niv) throws IOException {

        URL url = new URL("http://localhost/java/api/matiere/read_single.php");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        String jsonInputString = "{\"libelle\":\"" + lib + "\",\"niveau\":\"" + niv + "\"}";

        try (OutputStream os = conn.getOutputStream()) {

            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        StringBuilder response;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }
        JSONArray matieres = new JSONArray(response.toString());


        Matiere subject = null;
        for (int i = 0; i < matieres.length(); i++) {
            JSONObject matiere = matieres.getJSONObject(i);
            //String note_Et = etudiant.getString("note_Et");
            int codemat = matiere.getInt("codemat");
            int coef = matiere.getInt("coef");
            String libelle = matiere.getString("libelle");
            String niveau = matiere.getString("niveau");

            subject = new Matiere(codemat, libelle, coef, niveau);
        }
        return subject;
    }
    //-----------------------------------------------------------------------------------------------Student list--------
    public void studentList(int codemat, String niv){

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        try {
            URL urlGet = new URL("http://localhost/java/api/etudiant/read.php");
            connection = (HttpURLConnection) urlGet.openConnection();

            //setup
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();
            //System.out.println(status);
            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }

            JSONArray etudiants = new JSONArray(responseContent.toString());
            for (int i = 0; i < etudiants.length(); i++) {
                JSONObject etudiant = etudiants.getJSONObject(i);
                //String note_Et = etudiant.getString("note_Et");
                int id = etudiant.getInt("id");
                int num_Et = etudiant.getInt("num_Et");
                String nom = etudiant.getString("nom");
                String niveau = etudiant.getString("niveau");

                Etudiant student = new Etudiant(id, num_Et, nom, niveau);

                if ((student.getNiveau()).equals(niv)) {
                    creatNote(student.getId(), codemat);
                }
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            connection.disconnect();
        }
    }
    //----------------------------------------------------------------------CREATE NOTE------------------------------------------------
    public void creatNote(int id, int cod) throws Exception{

        URL url = new URL("http://localhost/java/api/notes/create.php");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);


        String jsonInputString = "{ \"codemat\":\""+cod+"\",\"num_inscription\":\""+id+"\", \"note\":\"0\"}";

        try(OutputStream os = conn.getOutputStream()){

            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        try(BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null){
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }
    }
    ////-------------------------------------------------------------------------Delete note----------------------------------------------------------------------
    public static  void deleteNote(int cod) throws Exception{

        URL url = new URL("http://localhost/java/api/notes/delete.php");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        String jsonInputString = "{\"codemat\":\""+cod+"\"}";

        try(OutputStream os = conn.getOutputStream()){

            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        try(BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null){
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }
    }
    //------------------------------------------------------------------------------------------------UPDATE NOTE--------------------------------------------
    public void updateNote(int cod, int num, double grad) throws IOException {

        URL url = new URL("http://localhost/java/api/notes/update.php");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        String jsonInputString = "{\"codemat\":\""+cod+"\", \"num_inscription\":\""+num+"\", \"note\":\""+grad+"\"}";

        try(OutputStream os = conn.getOutputStream()){

            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        try(BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null){
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }
    }
}