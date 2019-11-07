package sample;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Calendar;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private TextField txtphone;

    @FXML
    private TextField txtotp;

    boolean flag = false;
    long time_send ;
    String otp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    void send(ActionEvent event) {
        if (!txtphone.getText().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Otp Send");
            int otp = generateOtp();
            sendOtp(txtphone.getText());
            time_send = Calendar.getInstance().getTimeInMillis();
            flag = true;
            alert.showAndWait();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING,"Please enter the phone no");
            alert.showAndWait();
        }
    }

    private void sendOtp(String text) {
        otp = generateOtp()+"";
        try {
            HttpResponse response = (HttpResponse) Unirest.post("https://www.fast2sms.com/dev/bulk")
                    .header("authorization", "9hkQLBXsCHiFI0Ym7yVJ5Mad6zSNAGe4UlDKwRjP3f1xcTWZnEJz2PTOMsRrIAdBXK5a4fEDZxiqQGt1")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .body("sender_id=FSTSMS&message=OTP%20for%20MLT%20System%20is%20"+otp+"&language=english&route=p&numbers="+txtphone.getText())

                    //  +"&variables={AA}&variables_values="+generateOtp())
                    .asString();


        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void verify(ActionEvent event) {
        String number = txtphone.getText();
        System.out.println("starting for verification");
        if (flag)
        {
            long nowTime = Calendar.getInstance().getTimeInMillis();
            if(txtotp.getText.equals(otp)
               {
                    if (nowTime-time_send <= 120000)
                        {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION,number +" has been varified");
                            flag = false;
                            alert.showAndWait();
                        }
                        else
                        {
                            Alert alert = new Alert(Alert.AlertType.WARNING,"OTP has been expired ... pls send otp again");
                            flag = false;
                            alert.showAndWait();
                        }
               }
                else
               {
                            Alert alert = new Alert(Alert.AlertType.WARNING,"Wrong OTP");
                            alert.showAndWait();
               }

        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.WARNING,"pls send otp");
            alert.showAndWait();
        }

    }

    public int generateOtp(){
        int otp;
        Random r = new Random();
        otp = 1000 + Math.abs(r.nextInt()%9000);
        return otp;
    }
}
