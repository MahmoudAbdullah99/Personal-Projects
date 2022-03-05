package src;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ComputerSoftwareSecurity extends Application {

    @Override
    public void start(final Stage stage) {
        //here I set the name appear on the window frame.
        stage.setTitle("BEBARS CO. for Software Security");
        //here I made a label containing the steps for the user on using the program.
        Label lbl1 = new Label("""
                How to use:
                1-Choose the process you want either Encryption or Decryption by clicking on its button below.
                2-Choose the text file containing message from the first pop up window.
                3-Choose the text file containing key(only numerical) from the second pop up window.
                4-Choose where to save the output text file also it's name from the third pop up window.

                """);
        //I made a new label to show a message to show up if an error occurs (red text color).
        Label lbl2 = new Label();
        lbl2.setTextFill((Color.web("#ff0000")));
        //I made a file chooser which using extension filter to only shows the text files to the user to choose the message and the key from.
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        //I made a button used to give an order to encrypt and named it the same, also I set the action caused byclicking it.
        Button but1 = new Button("Encrypt");
        but1.setOnAction( e -> {
            boolean msgchk = false;
            boolean keychk = false;
            String inmsg = "";
            String key = "";
            lbl2.setText("");
            //here the first open dialog will pop up to the user to choose the message file.
            File file1 = fileChooser.showOpenDialog(stage);
            if (file1 != null) {
                try {
                    //reading the file the user choose.
                    inmsg = rdFile(file1);
                    msgchk = true;
                } catch (IOException ex) {
                    Logger.getLogger(ComputerSoftwareSecurity.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //After choosing the message file, the second file chooser will pop up to the user to choose the key file.
            if (msgchk){
                //Show open file dialog
                File file2 = fileChooser.showOpenDialog(stage);
                if (file2 != null) {
                    try {
                        //Read the key file to check it consists only of digits.
                        if (isNumeric(file2)){
                            key = rdKey(file2);
                            keychk = true;
                        }else{
                            //If the key containing only one digit an error will appear to the user in the secondlabel.
                            lbl2.setText("Error : Please choose a key file that contains only numbers!");
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ComputerSoftwareSecurity.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            //After we are sure that the message and the key files are chosen, and the key contains only digits.
            if (keychk){
                String outmsg = msgEncrypt(inmsg, key);
                //Set extension filter
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);
                //Show save file dialog
                File file3 = fileChooser.showSaveDialog(stage);
                if(file3 != null){
                    SaveFile(outmsg, file3);
                }
            }
        });

        Button but2 = new Button("Decrypt");
        but2.setOnAction( e -> {
            boolean msgchk = false;
            boolean keychk = false;
            String inmsg = "";
            String key = "";
            lbl2.setText("");
            //here the first open dialog will pop up to the user to choose the message file.
            File file1 = fileChooser.showOpenDialog(stage);
            if (file1 != null) {
                try {
                    //reading the file the user choose.
                    inmsg = rdFile(file1);
                    msgchk = true;
                } catch (IOException ex) {
                    Logger.getLogger(ComputerSoftwareSecurity.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            //After choosing the message file, the second file chooser will pop up to the user to choose the key file.
            if (msgchk){
                //Show open file dialog
                File file2 = fileChooser.showOpenDialog(stage);
                if (file2 != null) {
                    try {
                        //here we read the key file to check it consists only of digits.
                        if (isNumeric(file2)){
                            key = rdKey(file2);
                            keychk = true;
                        }else{
                            //if the key containing only one digit an error will appear to the user in the second label.
                            lbl2.setText("Error : Please choose a key file that contains only numbers!");
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ComputerSoftwareSecurity.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            //After we are sure that the message and the key files are chosen and the key contains only digits.
            if (keychk){
                String outmsg = msgDecrypt(inmsg, key);
                //Set extension filter
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                fileChooser.getExtensionFilters().add(extFilter);
                //Show save file dialog
                File file3 = fileChooser.showSaveDialog(stage);
                if(file3 != null){
                    SaveFile(outmsg, file3);
                }
            }
        });

        //Design of the GUI using 2 horizontal boxes and one vertical box.
        HBox hBox1 = new HBox(25);
        hBox1.setAlignment(Pos.CENTER);
        hBox1.getChildren().add(lbl1);
        HBox hBox2 = new HBox(25);
        hBox2.setAlignment(Pos.CENTER);
        hBox2.getChildren().addAll(but1, but2);
        //The vertical box will be the father of the two horizontal boxes
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.BASELINE_CENTER);
        vBox.getChildren().addAll();
        vBox.getChildren().addAll(hBox1, hBox2, lbl2);
        //Add vBox children to the pane
        Pane rootGroup = new VBox();
        rootGroup.getChildren().addAll(vBox);
        rootGroup.setPadding(new Insets(10, 10, 10, 10));
        //Add the pane which contain all the components to the scene.
        stage.setScene(new Scene(rootGroup));
        stage.show();
    }
    //The function to check if the key is in only digits or no and return the result in a boolean
    public static boolean isNumeric(File file) throws IOException {
        String key = rdKey(file);
        try {
            //Check the key validation and if it valid the next statement will return true.
            Double.parseDouble(key);
            return true;
        }catch(NumberFormatException e){
            //If an error occured that means the key contains something non-digits, the funtion will return false.
            return false;
        }
    }

    //The function to read the message file and return a string of the text inside the file.
    public static String rdFile(File ms) throws IOException{
        BufferedReader br = null;
        //Define the string we will use to store the text in.
        StringBuilder str = new StringBuilder();
        try{
            //Read the file
            br = new BufferedReader(new FileReader(ms));
            String line;
            //Keep reading the file and storing the text til contains no more lines.
            while((line = br.readLine()) != null){
                str.append(line).append("\n");
            }
        }
        catch(IOException e){
            //Close the file after finishing reading it.
        }finally{
            if (br != null) {
                br.close();
            }
        }
        //Retur the string containing the message.
        return str.toString();
    }
    //The function to read the key file and return a string of the text inside the file.
    public static String rdKey(File ms) throws IOException{
        BufferedReader br = null;
        //define the string we will use to store the text in.
        StringBuilder str = new StringBuilder();
        try{
            //Read the file
            br = new BufferedReader(new FileReader(ms));
            String line;
            //Keep reading the file and storing the text til contains no more lines.
            while((line = br.readLine()) != null){
                str.append(line);
            }
        }
        catch(IOException e){
            //Close the file after finishing reading it.
        }finally{
            if (br != null) {
                br.close();
            }
        }
        //Return the string containing the key.
        return str.toString();
    }
    //The function I use to encrypt the message using the key.
    public static String msgEncrypt(String msg, String key){
        StringBuilder encMsg = new StringBuilder();
        //Iterate throw the message string and the key string also to encrypt each letter by the one digit.
        for (int i = 0; i < msg.length(); i++){
            char ch = msg.charAt(i);
            //making the char in the key as value as the the digit it represent
            int key_ch = (int) key.charAt(i % key.length()) - 48;
            //If the letter in the upper case, perform that then add it.
            if (ch >= 'A' && ch <= 'Z'){
                encMsg.append((char) (((ch + key_ch - 65) % 26) + 65));
                //If the letter in the upper case, perform that then add it.
            }else if (ch >= 'a' && ch <= 'z'){
                encMsg.append((char) (((ch + key_ch - 97) % 26) + 97));
                //If the character isn't alphabetic, add it without any change.
            }else{
                encMsg.append(ch);
            }
        }
        //Return the whole message after encryption.
        return encMsg.toString();
    }
    //The function I use to decrypt the message using the key.
    public static String msgDecrypt(String msg, String key){
        StringBuilder decMsg = new StringBuilder();
        //Iterate throw the message string and the key string also to decrypt each letter by the one digit.
        for (int i = 0; i < msg.length(); i++){
            char ch = msg.charAt(i);
            //Make the char in the key as value as the the digit it represents.
            int key_ch = (int) key.charAt(i % key.length()) - 48;
            //If the letter in the upper case, perform that then add it.
            if (ch >= 'A' && ch <= 'Z'){
                decMsg.append((char) (((ch - key_ch - 39) % 26) + 65));
                //If the letter in the upper case, perform that then add it.
            }else if (ch >= 'a' && ch <= 'z'){
                decMsg.append((char) (((ch - key_ch - 71) % 26) + 97));
                //If the character is not alphabetic, add it without any change.
            }else{
                decMsg.append(ch);
            }
        }
        //Return the whole message after decryption.
        return decMsg.toString();
    }
    //The function I use to write the output message in the file.
    private void SaveFile(String content, File file){
        try {
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(content);
            }
        } catch (IOException ex) {
            Logger.getLogger(ComputerSoftwareSecurity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //The main function to run the program.
    public static void main(String[] args) {
        Application.launch(args);
    }

}
