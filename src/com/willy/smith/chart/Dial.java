package com.willy.smith.chart;

import javafx.collections.ObservableList;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Dial
  extends Parent
{
  private final double radius;
  private final Font NUMBER_FONT = Font.font("Comic Sans", FontWeight.BOLD, 8.0D);
  private int numOfMarks;
  private int numOfMinorMarks;
  
  public Dial(double radius, int numOfMarks, int numOfMinorMarks)
  {
    this.radius = radius;
    this.numOfMarks = numOfMarks;
    this.numOfMinorMarks = numOfMinorMarks;
    
    getChildren().addAll(new Node[] { createTickMarks() });
  }
  
  private Group createTickMarks()
  {
    Group group = new Group();
    for (int i = 0; i < this.numOfMarks; i++)
    {
      double angle = 360 / this.numOfMarks * i;
      group.getChildren().addAll(new Node[] { createMajorTic(angle, this.radius * 0.03D, this.radius * 0.008D) });
    }
    for (int i = 0; i < this.numOfMinorMarks; i++)
    {
      double angle = 360 / this.numOfMinorMarks * i;
      group.getChildren().add(createTic(angle, this.radius * 0.015D, this.radius * 0.004D));
    }
    return group;
  }
  
  private Group createMajorTic(double angle, double width, double height)
  {
    Group gp = new Group();
    Rectangle rectangle = new Rectangle(-width / 2.0D, -height / 2.0D, width, height);
    Text t = new Text("");
    
    rectangle.setFill(Color.BLACK);
    rectangle.setRotate(angle);
    rectangle.setLayoutX(this.radius * Math.cos(Math.toRadians(angle)));
    rectangle.setLayoutY(this.radius * Math.sin(Math.toRadians(angle)));
    
    t.setFont(this.NUMBER_FONT);
    t.setTextOrigin(VPos.TOP);
    t.setRotate(angle);
    t.setLayoutX(rectangle.getLayoutX());
    t.setLayoutY(rectangle.getLayoutY());
    int temp = (int)angle;
    if (angle < 180.0D) {
      temp = -(int)angle;
    } else {
      temp = 360 - temp;
    }
    t.setText("" + temp);
    
    gp.getChildren().addAll(new Node[] { rectangle, t });
    return gp;
  }
  
  private Rectangle createTic(double angle, double width, double height)
  {
    Rectangle rectangle = new Rectangle(-width / 2.0D, -height / 2.0D, width, height);
    rectangle.setFill(Color.BLUEVIOLET);
    rectangle.setRotate(angle);
    rectangle.setLayoutX(this.radius * Math.cos(Math.toRadians(angle)));
    rectangle.setLayoutY(this.radius * Math.sin(Math.toRadians(angle)));
    return rectangle;
  }
}
