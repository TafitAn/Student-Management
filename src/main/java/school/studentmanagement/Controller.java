package school.studentmanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private AnchorPane Bulletin;

    @FXML
    private Button Closebtn;

    @FXML
    private Button Closebtn1;

    @FXML
    private AnchorPane anchor;

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private Button bulletinB;

    @FXML
    private Button classBtn;

    @FXML
    private Button clearSearch;

    @FXML
    private Label idLabel;

    @FXML
    private TextField keywordT;

    @FXML
    private Button matBtn;

    @FXML
    private Button minimButn;

    @FXML
    private Label moyenneLabel;

    @FXML
    private TableColumn<?, ?> nivCol;

    @FXML
    private Label niveauLabel;

    @FXML
    private TableColumn<?, ?> nomCol;

    @FXML
    private Label nomLabel;

    @FXML
    private VBox noteList;

    @FXML
    private TableColumn<?, ?> numCol;

    @FXML
    private Label obsLabel;

    @FXML
    private TableView<Etudiant> studenTable;





    Stage stage;
    HttpURLConnection connection;


    @FXML
    private void closeButton(ActionEvent event){
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    private void minimizeButton(ActionEvent event){
        stage = (Stage)anchorpane.getScene().getWindow();
        stage.setIconified(true);
    }
    @FXML
    private  void handleButton(ActionEvent e){
        if(e.getSource()==classBtn){
            stage = (Stage)((Button)e.getSource()).getScene().getWindow();
            changeScene(stage,"Classes.fxml");

        }
        if (e.getSource()==matBtn){
            stage = (Stage)((Button)e.getSource()).getScene().getWindow();
            changeScene(stage,"Matiere.fxml");
        }
        if(e.getSource() == clearSearch){
            keywordT.clear();
        }
        if(e.getSource() == bulletinB){

            Etudiant etudiantSelected = new Etudiant();
            etudiantSelected = (Etudiant) studenTable.getSelectionModel().getSelectedItem();
            int id = etudiantSelected.getId();
            String niv = etudiantSelected.getNiveau();
            generateBulletin(id, niv);
            Bulletin.setVisible(true);
        }
        if(e.getSource() == Closebtn1){
            Bulletin.setVisible(false);
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        numCol.setCellValueFactory(new PropertyValueFactory<>("num_Et"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        nivCol.setCellValueFactory(new PropertyValueFactory<>("niveau"));
        studenTable.setItems(getList());

        FilteredList<Etudiant> filtereddata = new FilteredList<>(getList(), b-> true);

        keywordT.textProperty().addListener((observable, oldValue, newValue) -> {

            filtereddata.setPredicate(etudiant -> {

                //if blank
                if (newValue.isEmpty() || newValue.isEmpty() || newValue == null) {

                    anchor.setVisible(false);
                    bulletinB.setVisible(false);
                    clearSearch.setVisible(false);
                    return true;
                }

                String searchKeyWord = newValue.toLowerCase();

                if (String.valueOf(etudiant.getNum_Et()).toLowerCase().indexOf(searchKeyWord) > -1) {
                    anchor.setVisible(true);
                    bulletinB.setVisible(true);
                    clearSearch.setVisible(true);
                    return true;
                }else if (etudiant.getNom().toLowerCase().indexOf(searchKeyWord) > -1) {
                    anchor.setVisible(true);
                    bulletinB.setVisible(true);
                    clearSearch.setVisible(true);
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<Etudiant> sortedData = new SortedList<>(filtereddata);
        //bind sorted data with table view
        sortedData.comparatorProperty().bind(studenTable.comparatorProperty());
        //apply filtered and sorted data to table view
        studenTable.setItems(sortedData);
    }

    private void changeScene(Stage stage, String fxml){
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            this.stage.setScene(new Scene(root));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    //-----------------------------------------------------------------------------get the list of all student-----------------------------------------------
    public ObservableList<Etudiant> getList() {

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        ObservableList<Etudiant> list;
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
            list = FXCollections.observableArrayList();

            for (int i = 0; i < etudiants.length(); i++) {
                JSONObject etudiant = etudiants.getJSONObject(i);
                //String note_Et = etudiant.getString("note_Et");
                int id = etudiant.getInt("id");
                int num_Et = etudiant.getInt("num_Et");
                String nom = etudiant.getString("nom");
                String niveau = etudiant.getString("niveau");

                Etudiant student = new Etudiant(id, num_Et, nom, niveau);
                    list.add(student);
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            connection.disconnect();
        }
        return list;
    }
    public void generateBulletin(int id, String niv){

        noteList.getChildren().clear();
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        ObservableList<Notes> list;
        double total = 0;
        double coefT = 0;
        double moyenne;
        final DecimalFormat df = new DecimalFormat("0.00");

        try {
            URL urlGet = new URL("http://localhost/java/api/notes/read.php");
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
            JSONArray notes = new JSONArray(responseContent.toString());
            list = FXCollections.observableArrayList();
            Node[] nodes = new Node[notes.length()];

            for (int i = 0; i < notes.length(); i++) {
                JSONObject note = notes.getJSONObject(i);

                int num_inscription = note.getInt("num_inscription");
                int num_Et = note.getInt(("num_Et"));
                String nom_Et = note.getString("nom_Et");
                String nom_mat = note.getString("nom_mat");
                int codemat = note.getInt("codemat");
                double grad = note.getDouble("note");
                int coefficient = note.getInt("coefficient");

                Notes grade = new Notes(num_inscription, num_Et, nom_Et, nom_mat, codemat, grad, coefficient);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("note.fxml"));


                if ((grade.getNum_inscription()) == id) {
                    nodes[i] = loader.load();
                    bullNoteController taolana = loader.getController();
                    taolana.getCoefLabel().setText(String.valueOf(coefficient));
                    taolana.getLibLabel().setText("   "+nom_mat);
                    taolana.getNoteLabel().setText(String.valueOf(grad));
                    taolana.getPondLabel().setText(String.valueOf((grad)*(coefficient)));
                    noteList.getChildren().add(nodes[i]);
                    list.add(grade);
                    idLabel.setText(String.valueOf(num_inscription));
                    nomLabel.setText(nom_Et);
                    niveauLabel.setText(niv);
                    coefT = coefT + grade.getCoefficient();
                    total = total + ((grad)*(coefficient));
                }
            }
            moyenne = total / coefT;

            System.out.println(moyenne);

            moyenneLabel.setText(String.valueOf(df.format(moyenne)));

            if (moyenne >= 10){
                obsLabel.setText("Admis");
            }else if(moyenne > 7.5 && moyenne < 10){
                obsLabel.setText("Redoublant");
            }else{
                obsLabel.setText("Remis à sa famille");
            }
        } catch (ProtocolException ex) {
            throw new RuntimeException(ex);
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
            connection.disconnect();
    }
}
