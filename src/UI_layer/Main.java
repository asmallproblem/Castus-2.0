package UI_layer;

import DB_layer.UserDB;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/*
    the Main layer´, our GUI for the project.
    Where the customer can subscribe to a contest.
*/

/**
 * Created by Soeren Schou 31-05-2017.
 * Created by Magnus Thygesen 31-05-2017.
 * Created by Jonas Overgaard 31-05-2017.
 */
public class Main extends Application
{
    //out Fields for the class

    //Initializing our methods from our database class to be used in this class
    UserDB userDB = new UserDB();

    //the different text labels to guide our user threw the GUI.
    Label lFull_Name = new Label("Your full name?");
    Label lGot_Kids = new Label("Do you have kids?");
    Label lHow_Many_Kids = new Label("How many kids do you have?");
    Label lTlf = new Label("Your phone number?");
    Label lEmail = new Label("Your email?");
    Label lAddress = new Label("Your address");
    Label lFavorite_Product = new Label("What is your favorite produkt?");

    //Combobox
    ComboBox<String> cGot_kids;
    ComboBox<Integer> cNumber_of_kids;
    ComboBox<String> cFavorite_product;

    //Button
    Button b_SignUp = new Button("Sign up");

    //Textfield
    TextField tFull_name = new TextField();
    TextField tTlf = new TextField();
    TextField tEmail = new TextField();
    TextField tAdresse = new TextField();


    private void signUpButtonHitted()
    {
        // initializing different values to be used.
        String name;
        String gotKids;
        int numberOfKids;
        int phoneNumber;
        String email;
        String address;
        String favoriteProduct;
        String alertBoxtitle = "Tilmelding";
        String alertBoxMessage = "Du er nu tilmeldt konkurrencen";

        // our String name and gotkids, get the value from the textfield and combobox
        name = tFull_name.getText();
        gotKids = cGot_kids.getValue();


        // the same for the rest of our values
        phoneNumber = Integer.parseInt(tTlf.getText());
        email = tEmail.getText();
        address = tAdresse.getText();
        favoriteProduct = cFavorite_product.getValue();
        AlertBox alertBox = new AlertBox();
        alertBox.display(alertBoxtitle, alertBoxMessage);

        // for a senario if the user says yes or no to kids.
        if(cNumber_of_kids.getValue() == null)
        {
            // if No, the value for "Antal_Boern" is 0
            // our reformed data, is getting inserted into our DB, by using our DB method "InsertIntoDB"
            // from the UserDB class, with the values from this class we have initializied above
            int fisk = 0;
            userDB.insertIntoDB(name,gotKids,fisk,phoneNumber,email,address);
        }
        else
        {
            // same here, except the value for "Antal_Boern is the value from the combobox.
            numberOfKids = cNumber_of_kids.getValue();
            userDB.insertIntoDB(name,gotKids,numberOfKids,phoneNumber,email,address);
        }

        // Caling to the method "findLastID" from the UserDB class
        userDB.findLastID();
        // Caling to the method "addToCompetionTable" from the UserDB class, with a parameter
        // thats the value of our favorite_product combobox-
        userDB.addToCompetionTable(favoriteProduct);
    }


    public static void main(String[] args)
    {
        //launch's the application for out GUI
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception
    {
        //the Start method builds our GUI

        //Layout
        VBox vBox = new VBox(10);

        //Background from our Castus picture Magnus made
        BackgroundImage myBI= new BackgroundImage(new Image("http://i.imgur.com/FwuQ7XA.png"),
        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        vBox.setBackground(new Background(myBI));

        // to reduce redundancy we have made methods for our textfields and combobox's
        buildName();
        buildPhone();
        buildEmail();
        buildAddress();
        buildGotKids();
        buildHowManyKids();
        buildFavoriteProduct();
        buildButton();

        // adding all the pieces to the layout.
        vBox.getChildren().addAll(lFull_Name, tFull_name,cGot_kids,cNumber_of_kids,lTlf,tTlf,
               lEmail, tEmail, lAddress, tAdresse,cFavorite_product,b_SignUp);

        // removes the "Antal_Boern" combobox if the user said no for having kids at all
       cGot_kids.setOnAction(event -> {
            if(cGot_kids.getValue() == "No")
            {
                vBox.getChildren().remove(cNumber_of_kids);
            }
       });


        // Build the scene
        Scene scene = new Scene(vBox, 670 , 630);

        //adding the scene to our Stage
        primaryStage.setResizable(true);
        primaryStage.setTitle("Castus Konkurrence");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void buildName()
    {
        // building the name textfield
        lFull_Name.setTranslateY(50);
        lFull_Name.setTranslateX(280);

        tFull_name.setTranslateX(210);
        tFull_name.setTranslateY(45);
        tFull_name.setMaxWidth(230);
    }


    public void buildPhone()
    {
        // building the TLF textfield
        lTlf.setTranslateX(265);
        lTlf.setTranslateY(55);
        tTlf.setTranslateX(210);
        tTlf.setTranslateY(50);
        tTlf.setMaxWidth(230);
    }


    public void buildEmail()
    {
        // building the email textfield
        lEmail.setTranslateX(295);
        lEmail.setTranslateY(50);
        tEmail.setTranslateX(210);
        tEmail.setTranslateY(45);
        tEmail.setMaxWidth(230);
    }


    public void buildAddress()
    {
        // building the address textfield
        lAddress.setTranslateX(290);
        lAddress.setTranslateY(40);
        tAdresse.setTranslateX(210);
        tAdresse.setTranslateY(35);
        tAdresse.setMaxWidth(230);
    }


    public void buildGotKids()
    {
        // building the got kids combobox
        cGot_kids = new ComboBox<>();
        cGot_kids.setPromptText("Do you have kids");
        cGot_kids.getItems().addAll(
                "Yes","No"
        );
        cGot_kids.setTranslateX(260);
        cGot_kids.setTranslateY(50);
    }


    public void buildHowManyKids()
    {
        // building the number of kids combobox
        cNumber_of_kids = new ComboBox<>();
        cNumber_of_kids.setPromptText("Number of kids?");
        cNumber_of_kids.getItems().addAll(
                1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16
        );
        cNumber_of_kids.setTranslateX(260);
        cNumber_of_kids.setTranslateY(55);
    }


    public void buildFavoriteProduct()
    {
        // building the favorite produkt combobox
        cFavorite_product = new ComboBox<>();
        cFavorite_product.setPromptText("Favorite Product");
        cFavorite_product.getItems().addAll(
                "Frugtstænger","Grinebidder","Boost me Up","Frugtpålæg","Frugtruller"
        );
        cFavorite_product.setTranslateX(260);
        cFavorite_product.setTranslateY(30);
    }


    public void buildButton()
    {
        // building the sign up buttong
        b_SignUp.setTranslateX(275);
        b_SignUp.setTranslateY(30);
        b_SignUp.setMaxWidth(100);

        b_SignUp.setOnAction(event -> signUpButtonHitted());
    }
}
