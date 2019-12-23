package cn.qqhxj.fx.demo.controller;

import cn.qqhxj.common.rxtx.SerialContext;
import cn.qqhxj.fx.demo.DemoApplication;
import cn.qqhxj.fx.demo.view.SettingView;
import de.felixroske.jfxsupport.FXMLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

@FXMLController
public class HeaderController implements Initializable {

    @FXML
    public void doLogin(ActionEvent actionEvent) {
        MainController.webEngine.executeScript("login('admin','123456')");
    }

    @FXML
    public void openSetting(ActionEvent actionEvent) {
        DemoApplication.showView(SettingView.class, Modality.APPLICATION_MODAL);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
