package com.willy.smith;

import com.willy.smith.chart.SmithChart;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main
  extends Application
{
  private static final double width = 60.0;
  private static final double height = width * 0.6;
  private static final double scale = 21;
  private SmithChart smith = new SmithChart();
  
  public static void main(String[] args)
  {
    launch(args);
  }
  
  public void start(Stage primaryStage)
  {
    Pane root = new Pane();
    root.setLayoutX(0.0D);
    root.setLayoutY(0.0D);
    primaryStage.setMaximized(true);
    primaryStage.setScene(createScene(root, width * scale, height * scale));
    primaryStage.setTitle("Smith Chart Utility");
    Image img = new Image(getClass().getResource("/res/Help64.png").toExternalForm());
    primaryStage.getIcons().add(img);
    primaryStage.show();
  }
  
  private Scene createScene(Pane root, double cWidth, double cHeight)
  {
    this.smith.setLayoutX(30.0D);
    root.getChildren().addAll(new Node[] { this.smith });
    Scene scene = new Scene(root, cWidth, cHeight);
    
    return scene;
  }
  
  public static double getWidth()
  {
    return 1260.0D;
  }
  
  public static double getHeight()
  {
    return 655.2D;
  }
}
