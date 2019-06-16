package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Timer;
import java.util.TimerTask;

public class Controller {

    @FXML
    private TextField tfTest;

    @FXML
    private Button btnTest;

    @FXML
    private Label lbTest;

    private Integer value;
    private Automata auto;

    private Timer t;
    private TimerTask task;

    private boolean start=false;
    @FXML
    void press() {
        if(start==false) {
            t=new Timer();
            task = new TimerTask()
            {
                public void run()
                {
                    // еще одна обертка нужна для корректного
                    // обновения Label
                    Platform.runLater(new Runnable() {
                        public void run() {
                            ++value;
                            tfTest.setText(value.toString());
                            lbTest.setText(value.toString());
                        }
                    });
                }
            };

            Thread tr=new Thread(new Runnable() {
                @Override
                public void run() {
                    auto.cook();
                    start=false;
                    t.cancel();
                    t.purge();
                    t=null;
                }
            });
            tr.start();
            t.schedule(task, 0, 500);
            start = true;
        }
        else
        {
            start=false;
            t.cancel();
            t.purge();
            t=null;
        }
    }

    @FXML
    public void initialize() {

        //t=new Timer();
        value=new Integer(0);
        auto=new Automata();


    }
}
