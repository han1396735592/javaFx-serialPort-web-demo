package cn.qqhxj.fx.demo;

import cn.qqhxj.fx.demo.view.MainView;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("cn.qqhxj")
@SpringBootApplication
public class DemoApplication extends AbstractJavaFxApplicationSupport {

    public static void main(String[] args) {
        launch(DemoApplication.class, MainView.class, args);
    }


}
