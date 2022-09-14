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
import java.net.ProtocolException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class ClassesController implements Initializable {

    @FXML
    private AnchorPane AddN;

    @FXML
    private AnchorPane Bulletin;

    @FXML
    private Button Closebtn;

    @FXML
    private Button Closebtn1;

    @FXML
    private Button L1;

    @FXML
    private Button L2;

    @FXML
    private Button L3;

    @FXML
    private Button M1;

    @FXML
    private Button M2;

    @FXML
    private AnchorPane UpN;

    @FXML
    private Button addButton;

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private AnchorPane delN;

    @FXML
    private AnchorPane deleteBox;

    @FXML
    private Button deleteButton;

    @FXML
    private Button deleteN;

    @FXML
    private Button deleteY;

    @FXML
    private Label idLabel;

    @FXML
    private TableColumn<?, ?> idStudent;

    @FXML
    private Button logbtn;

    @FXML
    private Button minimButn;

    @FXML
    private Label moyenneLabel;

    @FXML
    private TextField nameField;

    @FXML
    private ComboBox<String> niveauComb;

    @FXML
    private Label niveauLabel;

    @FXML
    private TableColumn<?, ?> niveauStudent;

    @FXML
    private Label nomLabel;

    @FXML
    private TableColumn<?, ?> nomStudent;

    @FXML
    private VBox noteList;

    @FXML
    private TextField numField;

    @FXML
    private TableColumn<?, ?> numStudent;

    @FXML
    private Label obsLabel;

    @FXML
    private TableView<Etudiant> tableStudent;

    @FXML
    private AnchorPane updateBox;

    @FXML
    private Button updateButton;

    @FXML
    private Button updateN;

    @FXML
    private Button updateY;

    @FXML
    private Button bulletinB;



    HttpURLConnection connection;
    Stage stage;
    ObservableList<Etudiant>list;

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

    //---------------------------------------------------------------------------------INIT CLASSES WINDOW--------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idStudent.setCellValueFactory(new PropertyValueFactory<>("id"));
        numStudent.setCellValueFactory(new PropertyValueFactory<>("num_Et"));
        nomStudent.setCellValueFactory(new PropertyValueFactory<>("nom"));
        niveauStudent.setCellValueFactory(new PropertyValueFactory<>("niveau"));
        tableStudent.setItems(getListM2());

        niveauComb.getItems().addAll("M2", "M1", "L3", "L2", "L1");


    }
    //------------------------------------------------------------------------------HANDLE BUTTON ACTION---------------------------------------------------------------------------------------------
    @FXML
    private  void handleButton(ActionEvent e) throws Exception {

        //---------------------------------Back to home page---------------------------------------------------------------------------------------------------------------------------------
        if(e.getSource()==logbtn){
            stage = (Stage)((Button)e.getSource()).getScene().getWindow();
            changeScene(stage,"home.fxml");
        }
        //------------------------------------Classes-------------------------------------------------------------------
        if(e.getSource()==M1){
            tableStudent.setItems(getListM1());
            //System.out.println(getstudent(1, "M2"));
        }
        if(e.getSource()==L3){
            tableStudent.setItems(getListL3());
        }
        if(e.getSource()==L2){
            tableStudent.setItems(getListL2());
        }
        if(e.getSource()==L1){
            tableStudent.setItems(getListL1());
        }
        if(e.getSource()==M2){
            tableStudent.setItems(getListM2());
        }
        //---------------------------------Add student----------------------------------------------------------------------------------------------------------------------------------------------------
        if(e.getSource()==addButton){

            int num_Et = Integer.parseInt(numField.getText());
            String nom = nameField.getText();
            String niveau = niveauComb.getValue();
            creatEtudiant(num_Et, nom, niveau);
            nameField.clear();
            numField.clear();
            if(niveauComb.getValue() == "M2"){
                tableStudent.setItems(getListM2());
            }else if(niveauComb.getValue() == "M1"){
                tableStudent.setItems(getListM1());
            }else if(niveauComb.getValue() == "L3"){
                tableStudent.setItems(getListL3());
            }else if(niveauComb.getValue() == "L2"){
                tableStudent.setItems(getListL2());
            }else tableStudent.setItems(getListL1());
            AddN.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> AddN.setVisible(false));
            pause.play();

        }
        //------------------------------------------------------------------Delete student-------------------------------------------------------------
        if (e.getSource()==deleteButton){

            deleteBox.setVisible(true);
        }
        if (e.getSource() == deleteN){
            deleteBox.setVisible(false);
        }
        if(e.getSource() == deleteY){
            Etudiant etudiantSelected = new Etudiant();
            etudiantSelected = (Etudiant) tableStudent.getSelectionModel().getSelectedItem();
            deletEtudiant(etudiantSelected.getId());
            (tableStudent.getSelectionModel().getSelectedItems()).forEach((tableStudent.getItems())::remove);
            deleteBox.setVisible(false);
            delN.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> delN.setVisible(false));
            pause.play();

        }
        //---------------------------------------------------------------------Update student-------------------------------------------------------------
        if(e.getSource()==updateButton){

            updateBox.setVisible(true);
        }
        if(e.getSource() == updateN){
            updateBox.setVisible(false);
        }
        if(e.getSource() == updateY){
            Etudiant etudiantSelected = new Etudiant();
            etudiantSelected = (Etudiant) tableStudent.getSelectionModel().getSelectedItem();

            int num_Et = Integer.parseInt(numField.getText());
            String nom = nameField.getText();
            String niveau = niveauComb.getValue();

            updatEtudiant(etudiantSelected.getId(), num_Et, nom, niveau);

            nameField.clear();
            numField.clear();
            if(niveauComb.getValue() == "M2"){
                tableStudent.setItems(getListM2());
            }else if(niveauComb.getValue() == "M1"){
                tableStudent.setItems(getListM1());
            }else if(niveauComb.getValue() == "L3"){
                tableStudent.setItems(getListL3());
            }else if(niveauComb.getValue() == "L2"){
                tableStudent.setItems(getListL2());
            }else tableStudent.setItems(getListL1());
            updateBox.setVisible(false);
            UpN.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> UpN.setVisible(false));
            pause.play();
        }
        //-----------------------------------------------------------------------Bulletin-----------------------------------------------------
        if(e.getSource() == Closebtn1){
            Bulletin.setVisible(false);
        }
        if(e.getSource() == bulletinB){

            Etudiant etudiantSelected = new Etudiant();
            etudiantSelected = (Etudiant) tableStudent.getSelectionModel().getSelectedItem();
            int id = etudiantSelected.getId();
            String niv = etudiantSelected.getNiveau();
            generateBulletin(id, niv);
            Bulletin.setVisible(true);
        }
    }
    //________________________________________________________________Chenge scene methode-------------------------------------------------------------------------------------------

    private void changeScene(Stage stage, String fxml){
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            this.stage.setScene(new Scene(root));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    //-------------------------------------------------------------------------Get list of M2 student----------------------------------------------------------------------
    public ObservableList<Etudiant> getListM2() {

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        ObservableList<Etudiant> listM2;
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
            listM2 = FXCollections.observableArrayList();

            for (int i = 0; i < etudiants.length(); i++) {
                JSONObject etudiant = etudiants.getJSONObject(i);
                //String note_Et = etudiant.getString("note_Et");
                int id = etudiant.getInt("id");
                int num_Et = etudiant.getInt("num_Et");
                String nom = etudiant.getString("nom");
                String niveau = etudiant.getString("niveau");

                Etudiant student = new Etudiant(id, num_Et, nom, niveau);

                if ((student.getNiveau()).equals("M2")) {
                    listM2.add(student);
                }
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            connection.disconnect();
        }
        return listM2;
    }
    //-------------------------------------------------------------------------Get list of M1 student----------------------------------------------------------------------
    public ObservableList<Etudiant> getListM1() {

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        ObservableList<Etudiant> listM1;
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
            listM1 = FXCollections.observableArrayList();

            for (int i = 0; i < etudiants.length(); i++) {
                JSONObject etudiant = etudiants.getJSONObject(i);
                //String note_Et = etudiant.getString("note_Et");
                int id = etudiant.getInt("id");
                int num_Et = etudiant.getInt("num_Et");
                String nom = etudiant.getString("nom");
                String niveau = etudiant.getString("niveau");

                Etudiant student = new Etudiant(id, num_Et, nom, niveau);

                if ((student.getNiveau()).equals("M1")) {
                    listM1.add(student);
                }
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            connection.disconnect();
        }
        return listM1;
    }
    //-------------------------------------------------------------------------Get list of L3 student----------------------------------------------------------------------
    public ObservableList<Etudiant> getListL3() {

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        ObservableList<Etudiant> listL3;
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
            listL3 = FXCollections.observableArrayList();

            for (int i = 0; i < etudiants.length(); i++) {
                JSONObject etudiant = etudiants.getJSONObject(i);
                //String note_Et = etudiant.getString("note_Et");
                int id = etudiant.getInt("id");
                int num_Et = etudiant.getInt("num_Et");
                String nom = etudiant.getString("nom");
                String niveau = etudiant.getString("niveau");

                Etudiant student = new Etudiant(id, num_Et, nom, niveau);

                if ((student.getNiveau()).equals("L3")) {
                    listL3.add(student);
                }
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            connection.disconnect();
        }
        return listL3;
    }
    //-------------------------------------------------------------------------Get list of L2 student----------------------------------------------------------------------
    public ObservableList<Etudiant> getListL2() {

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        ObservableList<Etudiant> listL2;
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
            listL2 = FXCollections.observableArrayList();

            for (int i = 0; i < etudiants.length(); i++) {
                JSONObject etudiant = etudiants.getJSONObject(i);
                //String note_Et = etudiant.getString("note_Et");
                int id = etudiant.getInt("id");
                int num_Et = etudiant.getInt("num_Et");
                String nom = etudiant.getString("nom");
                String niveau = etudiant.getString("niveau");

                Etudiant student = new Etudiant(id, num_Et, nom, niveau);

                if ((student.getNiveau()).equals("L2")) {
                    listL2.add(student);
                }
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            connection.disconnect();
        }
        return listL2;
    }
    //-------------------------------------------------------------------------Get list of L1 student----------------------------------------------------------------------
    public ObservableList<Etudiant> getListL1() {

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        ObservableList<Etudiant> listL1;
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
            listL1 = FXCollections.observableArrayList();

            for (int i = 0; i < etudiants.length(); i++) {
                JSONObject etudiant = etudiants.getJSONObject(i);
                //String note_Et = etudiant.getString("note_Et");
                int id = etudiant.getInt("id");
                int num_Et = etudiant.getInt("num_Et");
                String nom = etudiant.getString("nom");
                String niveau = etudiant.getString("niveau");

                Etudiant student = new Etudiant(id, num_Et, nom, niveau);

                if ((student.getNiveau()).equals("L1")) {
                    listL1.add(student);
                }
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            connection.disconnect();
        }
        return listL1;
    }
    //-------------------------------------------------------------------------Create student method----------------------------------------------------------------------
    public void creatEtudiant(int num_Et, String nom, String niveau) throws Exception{

        URL url = new URL("http://localhost/java/api/etudiant/create.php");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        String jsonInputString = "{\"num_Et\":\""+num_Et+"\", \"nom\":\""+nom+"\", \"niveau\":\""+niveau+"\"}";

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
            Etudiant etud = getstudent(num_Et, niveau);

            subList(etud.getId(), niveau);

        }
    }
    ////-------------------------------------------------------------------------Delete student----------------------------------------------------------------------
    public static  void deletEtudiant(int id) throws Exception{

        URL url = new URL("http://localhost/java/api/etudiant/delete.php");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        String jsonInputString = "{\"id\":\""+id+"\"}";

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
        deleteNote(id);
    }
    //-------------------------------------------------------------------------Update student----------------------------------------------------------------------
    public static  void updatEtudiant(int id, int num_Et, String nom, String niveau ) throws Exception{

        URL url = new URL("http://localhost/java/api/etudiant/update.php");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        String jsonInputString = "{\"id\":\""+id+"\", \"num_Et\":\""+num_Et+"\", \"nom\":\""+nom+"\", \"niveau\":\""+niveau+"\"}";

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
    //-----------------------------------------------------------------------------------GET SINGLE STUDENT -------------------------------
    public Etudiant getstudent(int n, String niv) throws IOException {

        URL url = new URL("http://localhost/java/api/etudiant/read_single.php");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        String jsonInputString = "{\"num_Et\":\"" + n + "\",\"niveau\":\"" + niv + "\"}";

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
        JSONArray etudiants = new JSONArray(response.toString());


        Etudiant student = null;
        for (int i = 0; i < etudiants.length(); i++) {
            JSONObject etudiant = etudiants.getJSONObject(i);
            int id = etudiant.getInt("id");
            int num_Et = etudiant.getInt("num_Et");
            String nom = etudiant.getString("nom");
            String niveau = etudiant.getString("niveau");

            student = new Etudiant(id, num_Et, nom, niveau);
        }
        return student;
    }
    //------------------------------------------------------------------------------------get subject list--------------
    public void subList(int id, String niv) {
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

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
            }System.out.println(responseContent.toString());

            JSONArray matieres = new JSONArray(responseContent.toString());

            for (int i = 0; i < matieres.length(); i++) {
                JSONObject matiere = matieres.getJSONObject(i);
                //String note_Et = etudiant.getString("note_Et");
                int codemat = matiere.getInt("codemat");
                String libelle = matiere.getString("libelle");
                int coef = matiere.getInt("coef");
                String niveau = matiere.getString("niveau");

                Matiere subject = new Matiere(codemat, libelle, coef, niveau);

                if (subject.getNiveau().equals(niv)) {
                    creatNote(id, subject.getCodemat());
                }

                }
            } catch (ProtocolException ex) {
            throw new RuntimeException(ex);
        } catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        connection.disconnect();

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
    public static  void deleteNote(int id) throws Exception{

        URL url = new URL("http://localhost/java/api/notes/delete.php");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        String jsonInputString = "{\"num_inscription\":\""+id+"\"}";

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