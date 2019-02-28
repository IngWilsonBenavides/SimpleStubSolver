package com.willy.smith.chart;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SmithChart
  extends Parent
{
  private static final Font FONT = Font.font("Comic Sans", FontWeight.BOLD, 30.0D);
  private SmithPanel panel = new SmithPanel(this);
  private List<Object> elements = new ArrayList<Object>();
  private Group gpSmith = new Group();
  
  public SmithChart()
  {
    Separator separator = new Separator();
    separator.setOrientation(Orientation.VERTICAL);
    HBox hbox = new HBox(50.0D);
    hbox.getChildren().addAll(new Node[] { configureLeftPanel(), separator, this.panel });
    getChildren().addAll(new Node[] { hbox });
  }
  
  public Group getGpSmith()
  {
    return this.gpSmith;
  }
  
  public void setGpSmith(Group gpSmith)
  {
    this.gpSmith = gpSmith;
  }
  
  private Node configureLeftPanel()
  {
    double radio = 234.0D;
    double origenX = 0.0D;
    double origenY = 0.0D;
    
    VBox vbox = new VBox(25.0D);
    Text text = new Text("La carta de Smith");
    
    Dial mainDial = new Dial(radio * 1.05D, 36, 180);
    
    Circle degrees = new Circle(origenX, origenY, radio + radio * 0.05D, Color.TRANSPARENT);
    Circle separator = new Circle(origenX, origenY, radio + radio * 0.1D, Color.TRANSPARENT);
    Circle lambda = new Circle(origenX, origenY, radio + radio * 0.15D, Color.TRANSPARENT);
    Circle stroke = new Circle(origenX, origenY, radio + radio * 0.2D, Color.TRANSPARENT);
    
    degrees.setStroke(Color.DARKGRAY);
    degrees.setStrokeWidth(1.0D);
    separator.setStroke(Color.DARKGRAY);
    separator.setStrokeWidth(1.0D);
    lambda.setStroke(Color.DARKGRAY);
    lambda.setStrokeWidth(1.0D);
    stroke.setStroke(Color.DARKGRAY);
    stroke.setStrokeWidth(1.0D);
    
    text.setFill(Color.BLACK);
    text.setFont(FONT);
    text.setTranslateX(80.0D);
    
    this.gpSmith.getChildren().addAll(new Node[] { degrees, lambda, stroke, separator, drawSmithChart(20), mainDial });
    vbox.getChildren().addAll(new Node[] { text, this.gpSmith });
    return vbox;
  }
  
  private Group drawSmithChart(int step)
  {
    Group gp = new Group();
    
    double x0 = 0.0D;
    double y0 = 0.0D;
    double r = 234.0D;
    
    Circle center = new Circle();
    center.setRadius(3.0D);
    center.setFill(Color.DARKGRAY);
    center.setCenterX(x0);
    center.setCenterY(y0);
    
    Line line = new Line();
    line.setStartX(x0 - r);
    line.setStartY(0.0D);
    line.setEndX(x0 + r);
    line.setEndY(0.0D);
    line.setFill(Color.DARKGRAY);
    line.setStroke(Color.DARKGRAY);
    line.setStrokeWidth(2.0D);
    for (int i = 0; i < 100; i += step)
    {
      SmithCircle smith = new SmithCircle(i * 0.01D, i * 0.01D, Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY);
      SmithCircle smith2 = new SmithCircle(i * 0.01D, -i * 0.01D, Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY);
      if (smith.getReactCircle() != null) {
        gp.getChildren().addAll(new Node[] { smith.getReactCircle(), smith2.getReactCircle() });
      }
      gp.getChildren().addAll(new Node[] { smith.getResCircle() });
      this.elements.add(smith);
      this.elements.add(smith2);
    }
    for (int i = 0; i < 100; i += step)
    {
      SmithCircle smith = new SmithCircle(1.0D + i * 0.01D, 1.0D + i * 0.01D, Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY);
      SmithCircle smith2 = new SmithCircle(1.0D + i * 0.01D, -(1.0D + i * 0.01D), Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY);
      gp.getChildren().addAll(new Node[] { smith.getResCircle() });
      if (smith.getReactCircle() != null) {
        gp.getChildren().addAll(new Node[] { smith.getReactCircle(), smith2.getReactCircle() });
      }
      this.elements.add(smith);
      this.elements.add(smith2);
    }
    for (int i = 0; i < 100; i += step)
    {
      SmithCircle smith = new SmithCircle(2.0D + i * 0.01D, 2.0D + i * 0.01D, Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY);
      SmithCircle smith2 = new SmithCircle(2.0D + i * 0.01D, -(2.0D + i * 0.01D), Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY);
      gp.getChildren().addAll(new Node[] { smith.getResCircle() });
      if (smith.getReactCircle() != null) {
        gp.getChildren().addAll(new Node[] { smith.getReactCircle(), smith2.getReactCircle() });
      }
      this.elements.add(smith);
      this.elements.add(smith2);
    }
    for (int i = 0; i < 80; i += step)
    {
      SmithCircle smith = new SmithCircle(3.0D + i * 0.1D, 3.0D + i * 0.1D, Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY);
      SmithCircle smith2 = new SmithCircle(3.0D + i * 0.1D, -(3.0D + i * 0.1D), Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY);
      gp.getChildren().addAll(new Node[] { smith.getResCircle() });
      if (smith.getReactCircle() != null) {
        gp.getChildren().addAll(new Node[] { smith.getReactCircle(), smith2.getReactCircle() });
      }
      this.elements.add(smith);
      this.elements.add(smith2);
    }
    for (int i = 0; i < 40; i += step)
    {
      SmithCircle smith = new SmithCircle(20 + i, 20 + i, Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY);
      SmithCircle smith2 = new SmithCircle(20 + i, -(20 + i), Color.DARKGRAY, Color.DARKGRAY, Color.DARKGRAY);
      gp.getChildren().addAll(new Node[] { smith.getResCircle() });
      if (smith.getReactCircle() != null) {
        gp.getChildren().addAll(new Node[] { smith.getReactCircle(), smith2.getReactCircle() });
      }
      this.elements.add(smith);
      this.elements.add(smith2);
    }
    gp.getChildren().addAll(new Node[] { center, line });
    
    return gp;
  }
  
  EventHandler<MouseEvent> mouseEntered = new EventHandler<MouseEvent>()
  {
    public void handle(MouseEvent me)
    {
      for (Object obj : SmithChart.this.elements) {
        if ((obj instanceof Label))
        {
          ((Label)obj).toFront();
          ((Label)obj).setVisible(true);
        }
      }
      System.out.println("Entered!");
    }
  };
  EventHandler<MouseEvent> mouseExited = new EventHandler<MouseEvent>()
  {
    public void handle(MouseEvent me)
    {
      for (Object obj : SmithChart.this.elements) {
        if ((obj instanceof Label)) {
          ((Label)obj).setVisible(false);
        }
      }
      System.out.println("Exited!");
    }
  };
  
  public void drawSmithCircle(double rl, double xl)
  {
    SmithCircle smith = new SmithCircle(rl, xl, Color.DARKRED, Color.DARKRED, Color.DARKRED);
    Label lbz = smith.getLabelZl();
    lbz.toFront();
    smith.getZl().setOnMouseEntered(this.mouseEntered);
    smith.getZl().setOnMouseExited(this.mouseExited);
    
    this.elements.add(smith);
    this.elements.add(lbz);
    
    this.gpSmith.getChildren().addAll(new Node[] { smith.getResCircle(), smith.getReactCircle(), smith.getZl(), lbz });
  }
  
  public void addElement(Node object)
  {
    this.gpSmith.getChildren().add(object);
  }
  
  public void showElements()
  {
    for (Object obj : this.elements) {
      System.out.println(obj);
    }
    System.out.println("size: " + this.elements.size());
  }
  
  public void drawSmithNotNormalize(double rl, double xl, double z0)
  {
    double r = rl / z0;
    double x = xl / z0;
    
    drawSmithCircle(r, x);
  }
  
  public List<Object> getElements()
  {
    return this.elements;
  }
  
  public void setElements(List<Object> elements)
  {
    this.elements = elements;
  }
}
