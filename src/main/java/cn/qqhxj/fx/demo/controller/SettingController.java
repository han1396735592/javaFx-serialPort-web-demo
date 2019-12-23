package cn.qqhxj.fx.demo.controller;

import cn.qqhxj.common.rxtx.SerialContext;
import cn.qqhxj.common.rxtx.SerialUtils;
import cn.qqhxj.common.rxtx.processor.SerialDataProcessor;
import de.felixroske.jfxsupport.FXMLController;
import de.felixroske.jfxsupport.GUIState;
import gnu.io.SerialPort;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author Administrator
 */
@Slf4j
@FXMLController
public class SettingController implements Initializable, SerialDataProcessor<String> {

    @FXML
    private ComboBox<Integer> baudRate;


    @FXML
    private ComboBox<String> portName;


    @FXML
    private ToggleButton btn;

    @FXML
    private TextArea textArea;

    @FXML
    private TextArea sendText;

    @FXML
    public void clear(ActionEvent actionEvent) {
        textArea.clear();
    }

    @FXML
    private void send(ActionEvent actionEvent) {
        SerialContext.sendData(sendText.getText().getBytes());
    }

    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> items = portName.getItems();
        ArrayList<String> commNames = SerialUtils.getCommNames();
        items.addAll(commNames);
        baudRate.getItems().addAll(9600, 19200, 152000);
        SerialPort serialPort = SerialContext.getSerialPort();
        if (serialPort != null) {
            baudRate.setValue(serialPort.getBaudRate());
            portName.setValue(serialPort.getName().substring(4));
            btn.setDisable(false);
            btn.setSelected(true);
        } else {
            btn.setDisable(true);
            btn.setSelected(false);
        }
        System.out.println("se");
        //noinspection AlibabaLowerCamelCaseVariableNaming
        portName.valueProperty().addListener((o, oV, nV) -> {
            if (StringUtils.isEmpty(nV)) {
                btn.setDisable(true);
            } else if (baudRate.getValue() != null) {
                btn.setDisable(false);
            }
        });
        baudRate.valueProperty().addListener((o, oV, nV) -> {
            if (StringUtils.isEmpty(nV)) {
                btn.setDisable(true);
            } else if (!StringUtils.isEmpty(nV)) {
                btn.setDisable(false);
            }
        });
        btn.setText(SerialContext.getSerialPort() != null ? "断开" : "连接");
    }


    @FXML
    public void connect(ActionEvent actionEvent) throws Exception {

        ToggleButton source = (ToggleButton) actionEvent.getSource();
        if (source.isSelected()) {
            String portNameValue = portName.getValue();
            Integer value = baudRate.getValue();
            SerialPort connect = SerialUtils.connect(portNameValue, value);
            SerialContext.setSerialPort(connect);
            log.info("SerialPort use {}", connect);
        } else {
            SerialPort serialPort = SerialContext.getSerialPort();
            serialPort.close();
            SerialContext.setSerialPort(null);
            log.info("SerialPort is close");
        }
        btn.setText(SerialContext.getSerialPort() != null ? "断开" : "连接");
    }


    @Override
    public void process(String s) {
        Platform.runLater(() -> textArea.appendText("\n" + s));
    }
}
