package Main.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TableauxCtrl implements Initializable {

    @FXML
    private Button date2;

    @FXML
    private Button search;

    @FXML
    private StackPane stack;

    @FXML
    private VBox content;

    private Node s1, s2;

    public void date2Handle() throws IOException {
        this.s1.toFront();
    }

    public void searchHandle(){
        this.s2.toFront();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            this.s1 = FXMLLoader.load(getClass().getResource("../View/Component/Research2Date.fxml"));
            this.s1.prefWidth( 600 );
            System.out.println(this.stack.getPrefWidth());
            this.s2 = FXMLLoader.load(getClass().getResource("../View/Component/ResearchMonth.fxml"));
            this.s2.prefHeight(700);
            this.s2.prefWidth(this.stack.getWidth());

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.content.getChildren().addAll(this.s2);

    }
}
