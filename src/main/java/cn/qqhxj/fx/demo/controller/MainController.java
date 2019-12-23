package cn.qqhxj.fx.demo.controller;

import cn.qqhxj.common.rxtx.processor.SerialDataProcessor;
import cn.qqhxj.fx.demo.DemoApplication;
import cn.qqhxj.fx.demo.view.SettingView;
import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

@Slf4j
@FXMLController
public class MainController implements Initializable {

    @FXML
    private WebView webView;

    @FXML
    private VBox root;

    public static WebEngine webEngine;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        root.widthProperty().addListener((a, oldV, newV) -> {
            webView.setPrefWidth(newV.doubleValue());
        });
        root.heightProperty().addListener((a, oldV, newV) -> {
            webView.setPrefHeight(newV.doubleValue());
        });
        webEngine = webView.getEngine();
        String url = "http://wechatmeaL.qqhxj.cn";
        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
            String url1 = properties.getProperty("url");
            if (!StringUtils.isEmpty(url1)) {
                url = url1;
            }

        } catch (Exception e) {

        }
        webEngine.load(url);
        Worker<Void> loadWorker = webView.getEngine().getLoadWorker();
        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (Worker.State.SUCCEEDED == newValue) {
                    try {
                        webEngine.executeScript("login('admin','123456')");
                    }catch (Exception e){
                    }

                }
            }
        };
        loadWorker.stateProperty().addListener(changeListener);
    }
}
