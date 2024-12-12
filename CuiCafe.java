package com.example.cui_cafe;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.stream.Collectors;

;

public class CuiCafe extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Root Pane
        StackPane root = new StackPane();

        // Background image path
        String backgroundImage = getClass().getResource("/background.png") != null
                ? getClass().getResource("/background.png").toExternalForm()
                : "file:path/to/default/image.png";

        // Set background style
        root.setStyle("-fx-background-image: url('" + backgroundImage + "');" +
                "-fx-background-size: cover; -fx-background-position: center;");

        // Logo
        Text logo = new Text("CUI CAFE");
        logo.setFont(Font.font("Times New Roman", 30)); // Logo font and size
        logo.setFill(Color.WHITE);
        logo.setEffect(new DropShadow(5, Color.BLACK));

        // Navigation Buttons
        Button btnHome = createNavButton("Home");
        Button btnStudent = createNavButton("Student_Corner");
        Button btnFaculty = createNavButton("Faculty_Corner");
        Button btnStaff = createNavButton("Staff");
        Button btnMenu = createNavButton("Menu");
        Button btnReviews = createNavButton("Reviews");
        Button btnExit = createNavButton("Exit");

        //buttons action
        btnReviews.setOnAction(e -> {
            CafeReviews reviews = new CafeReviews();
            TableView<CafeReviews.Review> tableView = reviews.showReviews();
            Stage reviewStage = new Stage();
            reviewStage.setTitle("Cafe Reviews");
            Pane reviewPane = new Pane();
            tableView.setPrefSize(750, 370);
            reviewPane.getChildren().add(tableView);

            Text selectingDesignation = new Text();
            selectingDesignation.setText("Select Designation:");
            selectingDesignation.setLayoutX(20);
            selectingDesignation.setLayoutY(400);
            selectingDesignation.setFill(Color.BLACK);
            selectingDesignation.setFont(Font.font("Times New Roman", 16));

            ComboBox<String> designationComboBox = new ComboBox<>();
            designationComboBox.getItems().addAll("Student", "Faculty");
            designationComboBox.setLayoutX(20);
            designationComboBox.setLayoutY(420);
            designationComboBox.setPrefSize(150, 30);

            Button filterByDesignation = new Button("Filter by Designation");
            filterByDesignation.setLayoutX(180);
            filterByDesignation.setLayoutY(420);
            filterByDesignation.setPrefSize(150, 30);
            filterByDesignation.setOnAction(event -> {
                String selectedDesignation = designationComboBox.getValue();
                if (selectedDesignation != null) {
                    ObservableList<CafeReviews.Review> filteredReviews = reviews.getReviews().stream()
                            .filter(review -> review.getDesignation().equalsIgnoreCase(selectedDesignation))
                            .collect(Collectors.toCollection(FXCollections::observableArrayList));
                    tableView.setItems(filteredReviews);
                }
            });

            Button sortByRating = new Button("Sort by Rating");
            sortByRating.setLayoutX(400);
            sortByRating.setLayoutY(420);
            sortByRating.setPrefSize(150, 30);
            sortByRating.setOnAction(event -> {
                ObservableList<CafeReviews.Review> sortedReviews = reviews.getReviews().stream()
                        .sorted(Comparator.comparingInt(CafeReviews.Review::getRating).reversed())
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));
                tableView.setItems(sortedReviews);
            });

            Button backButton = new Button("Back");
            backButton.setLayoutX(560);
            backButton.setLayoutY(420);
            backButton.setPrefSize(150, 30);
            backButton.setOnAction(event -> reviewStage.close());

            reviewPane.getChildren().addAll(selectingDesignation, designationComboBox, filterByDesignation, sortByRating, backButton);

            Scene reviewScene = new Scene(reviewPane, 750, 500);
            reviewStage.setScene(reviewScene);
            reviewStage.setResizable(false);
            reviewStage.show();
        });
        //displaying menu
        btnMenu.setOnAction(e -> {
            try {
                new MenuClient().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        //admin
        btnStaff.setOnAction(e -> {
            try {
                new AdminClient().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        //student corner
        btnStudent.setOnAction(e -> {
            try{
                new StudentClient().start(new Stage());
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        });
        //faculty corner
        btnFaculty.setOnAction(e -> {
            try{
                new FacultyClient().start(new Stage());
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        });
        // Exit Button Action
        btnExit.setOnAction(e -> primaryStage.close());

        // Navigation Bar
        HBox navBar = new HBox(20, logo, btnHome, btnStudent, btnFaculty, btnStaff, btnMenu, btnReviews, btnExit);
        navBar.setAlignment(Pos.CENTER);
        navBar.setStyle("-fx-padding: 10; -fx-background-color: rgba(0, 0, 0, 0.7);");
        navBar.setSpacing(30);

        // Title
        Text title = new Text("CUI CAFE MANAGEMENT");
        title.setFont(Font.font("Times New Roman", 60)); // Updated font and size
        title.setFill(Color.WHITE);
        title.setEffect(new DropShadow(20, Color.BLACK));

        // Buttons
        Button btnAboutUs = createStyledButton("ABOUT US", "rgba(255, 0, 0, 0.8)", "white");
        Button btnCallNow = createStyledButton("CALL NOW", "white", "black");

        // Layout for Buttons
        VBox buttonsLayout = new VBox(20, btnAboutUs, btnCallNow);
        buttonsLayout.setAlignment(Pos.TOP_CENTER);

        // Main Layout
        VBox layout = new VBox(60, navBar, title, buttonsLayout);
        layout.setAlignment(Pos.TOP_CENTER);

        // Add to Root
        root.getChildren().add(layout);

        // Scene
        Scene scene = new Scene(root, 1150, 600);

        primaryStage.setTitle("CUI Cafe Management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createNavButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: transparent;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 16;" +
                "-fx-font-weight: bold;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-font-size: 16; -fx-font-weight: bold;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 16; -fx-font-weight: bold;"));
        return button;
    }

    private Button createStyledButton(String text, String backgroundColor, String textColor) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + backgroundColor + ";" +
                "-fx-border-color: " + textColor + ";" +
                "-fx-border-width: 2;" +
                "-fx-text-fill: " + textColor + ";" +
                "-fx-font-size: 18;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 10;" +
                "-fx-padding: 10 30;");

        button.setEffect(new DropShadow(3, Color.BLACK));

        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + textColor + ";" +
                "-fx-border-color: " + textColor + ";" +
                "-fx-text-fill: " + backgroundColor + ";" +
                "-fx-font-size: 18;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 10;" +
                "-fx-padding: 10 30;"));

        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + backgroundColor + ";" +
                "-fx-border-color: " + textColor + ";" +
                "-fx-text-fill: " + textColor + ";" +
                "-fx-font-size: 18;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 10;" +
                "-fx-padding: 10 30;"));
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
