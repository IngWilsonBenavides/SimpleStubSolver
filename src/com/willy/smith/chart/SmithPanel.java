package com.willy.smith.chart;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SmithPanel
  extends Parent
{
  private static final Font BODY_FONT = Font.font("Comic Sans", FontWeight.MEDIUM, 13.0D);
  private static final Font TITLE_FONT = Font.font("Comic Sans", FontWeight.MEDIUM, 18.0D);
  private static SmithChart smithChart;
  Text text = new Text("Caracter�sticas de la l�nea de transmisi�n");
  Label lbz0 = new Label("Zo = ");
  Label zl = new Label("Zl = ");
  TextField tfz0 = new TextField();
  TextField tfrl = new TextField();
  TextField tfxl = new TextField();
  Button agregar = new Button("agregar");
  GridPane grid = new GridPane();
  Button quitar = new Button("quitar");
  Button next = new Button("Siguiente");
  Button back = new Button("Anterior");
  TextArea taOp = new TextArea();
  private ChoiceBox<Object> stubType;
  private ChoiceBox<Object> stubConf;
  private ChoiceBox<Object> stubTerm;
  
  public SmithPanel(final SmithChart smithChart)
  {
    this.smithChart = smithChart;
    
    VBox vbox = new VBox(25.0D);
    GridPane grid = new GridPane();
    HBox hbox = new HBox(30.0D);
    
    this.taOp.setPrefHeight(340.0D);
    this.taOp.setPrefWidth(320.0D);
    this.taOp.setWrapText(true);
    this.taOp.setEditable(false);
    grid.setPadding(new Insets(10.0D, 10.0D, 10.0D, 10.0D));
    grid.setVgap(20.0D);
    grid.setHgap(5.0D);
    
    this.text.setFont(TITLE_FONT);
    this.lbz0.setFont(BODY_FONT);
    this.tfz0.setFont(BODY_FONT);
    this.zl.setFont(BODY_FONT);
    this.tfrl.setFont(BODY_FONT);
    this.tfxl.setFont(BODY_FONT);
    this.zl.setFont(BODY_FONT);
    this.agregar.setFont(BODY_FONT);
    this.quitar.setFont(BODY_FONT);
    this.next.setFont(BODY_FONT);
    this.back.setFont(BODY_FONT);
    this.quitar.setDisable(true);
    this.back.setDisable(true);
    this.next.setDisable(true);
    
    this.tfz0.setPromptText("Impedancia caracter�stica");
    this.tfz0.setPrefColumnCount(12);
    this.tfrl.setPromptText("Resistencia (Rl)");
    this.tfxl.setPromptText("Reactancia (XL)");
    
    this.stubType = new ChoiceBox<Object>();
    this.stubType.getItems().addAll(new Object[] { "STUB Simple", new Separator() });
    this.stubType.getSelectionModel().selectFirst();
    this.stubType.setTooltip(new Tooltip("Seleccione el tipo de STUB"));
    
    this.stubConf = new ChoiceBox<Object>();
    this.stubConf.getItems().addAll(new Object[] { "SERIE", new Separator(), "PARALELO" });
    this.stubConf.getSelectionModel().selectFirst();
    this.stubConf.setTooltip(new Tooltip("Seleccione la configuraci�n del STUB"));
    
    this.stubTerm = new ChoiceBox<Object>();
    this.stubTerm.getItems().addAll(new Object[] { "Corto Circuito", new Separator(), "Circuito Abierto" });
    this.stubTerm.getSelectionModel().selectFirst();
    this.stubTerm.setTooltip(new Tooltip("Seleccione la terminaci�n del STUB"));
    
    grid.add(this.lbz0, 0, 0);
    grid.add(this.tfz0, 1, 0);
    grid.add(this.zl, 0, 1);
    grid.add(this.tfrl, 1, 1);
    grid.add(this.tfxl, 2, 1);
    grid.add(this.agregar, 1, 2);
    grid.add(this.quitar, 2, 2);
    
    this.agregar.setOnAction(this.addButton);
    this.quitar.setOnAction(this.remButton);
    this.next.setOnAction(this.nextButton);
    this.back.setOnAction(this.backButton);
    this.grid = grid;
    
    this.stubConf.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
    {
      public void changed(ObservableValue ov, Number value, Number new_value)
      {
        SmithPanel.this.itemStub = new_value.intValue();
        
        int size = 0;
        for (Object obj : smithChart.getElements()) {
          if ((obj instanceof SmithCircle)) {
            size = smithChart.getElements().indexOf(obj);
          }
        }
        SmithCircle sc = (SmithCircle)smithChart.getElements().get(size);
        
        boolean yl1 = smithChart.getGpSmith().getChildren().contains(sc.getYl());
        boolean yl2 = smithChart.getGpSmith().getChildren().contains(sc.getYlStroke());
        boolean yl3 = smithChart.getGpSmith().getChildren().contains(sc.getLineReflexCoefYl());
        boolean yl4 = smithChart.getGpSmith().getChildren().contains(sc.getYlCompLine());
        boolean xl1 = smithChart.getGpSmith().getChildren().contains(sc.getZl());
        boolean xl2 = smithChart.getGpSmith().getChildren().contains(sc.getZlStroke());
        boolean xl3 = smithChart.getGpSmith().getChildren().contains(sc.getLineReflexCoefZl());
        boolean xl4 = smithChart.getGpSmith().getChildren().contains(sc.getZlCompLine());
        if ((new_value.intValue() == 2) && (!yl1) && (!yl2) && (!yl3) && (!yl4))
        {
          smithChart.getGpSmith().getChildren().remove(sc.getLineReflexCoefZl());
          smithChart.getGpSmith().getChildren().remove(sc.getZlStroke());
          smithChart.getGpSmith().getChildren().remove(sc.getZlCompLine());
          smithChart.getGpSmith().getChildren().remove(sc.getZl());
          
          smithChart.addElement(sc.getYl());
          smithChart.addElement(sc.getYlStroke());
          smithChart.addElement(sc.getLineReflexCoefYl());
          smithChart.addElement(sc.getYlCompLine());
        }
        if ((new_value.intValue() == 0) && (!xl1) && (!xl2) && (!xl3) && (!xl4))
        {
          smithChart.getGpSmith().getChildren().remove(sc.getLineReflexCoefYl());
          smithChart.getGpSmith().getChildren().remove(sc.getYlStroke());
          smithChart.getGpSmith().getChildren().remove(sc.getYlCompLine());
          smithChart.getGpSmith().getChildren().remove(sc.getYl());
          
          smithChart.addElement(sc.getZl());
          smithChart.addElement(sc.getZlStroke());
          smithChart.addElement(sc.getLineReflexCoefZl());
          smithChart.addElement(sc.getZlCompLine());
        }
      }
    });
    hbox.getChildren().addAll(new Node[] { this.back, this.next });
    vbox.getChildren().addAll(new Node[] { this.text, grid, this.taOp, hbox });
    getChildren().add(vbox);
  }
  
  EventHandler<ActionEvent> addButton = new EventHandler<ActionEvent>()
  {
    public void handle(ActionEvent e)
    {
      double xl = Double.parseDouble(SmithPanel.this.tfxl.getText());
      double rl = Double.parseDouble(SmithPanel.this.tfrl.getText());
      double z0 = Double.parseDouble(SmithPanel.this.tfz0.getText());
      String sgn = xl > 0.0D ? "+" : "";
      SmithPanel.this.taOp.appendText("zl = " + rl / z0 + sgn + xl / z0 + "j\t(impedancia de carga normalizada)\n");
      
      SmithPanel.smithChart.drawSmithNotNormalize(rl, xl, z0);
      SmithPanel.this.tfz0.setDisable(true);
      SmithPanel.this.tfrl.setDisable(true);
      SmithPanel.this.tfxl.setDisable(true);
      SmithPanel.this.agregar.setDisable(true);
      SmithPanel.this.quitar.setDisable(false);
      SmithPanel.this.next.setDisable(false);
    }
  };
  int step = 0;
  private int itemStub;
  EventHandler<ActionEvent> nextButton = new EventHandler<ActionEvent>()
  {
    public void handle(ActionEvent e)
    {
      System.out.println("next! " + SmithPanel.this.step);
      int size = 0;
      for (Object obj : SmithPanel.smithChart.getElements()) {
        if ((obj instanceof SmithCircle)) {
          size = SmithPanel.smithChart.getElements().indexOf(obj);
        }
      }
      SmithCircle sc = (SmithCircle)SmithPanel.smithChart.getElements().get(size);
      
      boolean xl1 = SmithPanel.smithChart.getGpSmith().getChildren().contains(sc.getLineReflexCoefZl());
      boolean xl2 = SmithPanel.smithChart.getGpSmith().getChildren().contains(sc.getReflexConst());
      boolean xl3 = SmithPanel.smithChart.getGpSmith().getChildren().contains(sc.getZlStroke());
      boolean xl4 = SmithPanel.smithChart.getGpSmith().getChildren().contains(sc.getZlCompLine());
      
      boolean yl1 = SmithPanel.smithChart.getGpSmith().getChildren().contains(sc.getYl());
      boolean yl2 = SmithPanel.smithChart.getGpSmith().getChildren().contains(sc.getLineReflexCoefYl());
      boolean yl3 = SmithPanel.smithChart.getGpSmith().getChildren().contains(sc.getYlStroke());
      boolean yl4 = SmithPanel.smithChart.getGpSmith().getChildren().contains(sc.getYlCompLine());
      
      SmithPanel.this.step += 1;
      if (SmithPanel.this.step < 1) {
        SmithPanel.this.step = 1;
      }
      SmithPanel.this.back.setDisable(false);
      if (SmithPanel.this.step == 1)
      {
        if ((!SmithPanel.this.grid.getChildren().contains(SmithPanel.this.stubType)) && (!SmithPanel.this.grid.getChildren().contains(SmithPanel.this.stubConf)) && (!SmithPanel.this.grid.getChildren().contains(SmithPanel.this.stubTerm)))
        {
          SmithPanel.this.grid.add(SmithPanel.this.stubType, 1, 3);
          SmithPanel.this.grid.add(SmithPanel.this.stubConf, 2, 3);
          SmithPanel.this.grid.add(SmithPanel.this.stubTerm, 3, 3);
        }
        if (SmithPanel.this.itemStub == 0)
        {
          if (!xl1) {
            SmithPanel.smithChart.addElement(sc.getLineReflexCoefZl());
          }
          if (!xl2) {
            SmithPanel.smithChart.addElement(sc.getReflexConst());
          }
          if (!xl3) {
            SmithPanel.smithChart.addElement(sc.getZlStroke());
          }
          if (!xl4) {
            SmithPanel.smithChart.addElement(sc.getZlCompLine());
          }
        }
        else if (SmithPanel.this.itemStub == 2)
        {
          if (!yl1) {
            SmithPanel.smithChart.addElement(sc.getYl());
          }
          if (!yl2) {
            SmithPanel.smithChart.addElement(sc.getLineReflexCoefYl());
          }
          if (!xl2) {
            SmithPanel.smithChart.addElement(sc.getReflexConst());
          }
          if (!yl3) {
            SmithPanel.smithChart.addElement(sc.getYlStroke());
          }
          if (!yl4) {
            SmithPanel.smithChart.addElement(sc.getYlCompLine());
          }
        }
      }
      if (SmithPanel.this.step == 2)
      {
        SmithPanel.smithChart.addElement(sc.getUnitResistence());
        SmithPanel.smithChart.addElement(sc.getSolution1());
        SmithPanel.smithChart.addElement(sc.getSolution2());
      }
      if (SmithPanel.this.step == 3)
      {
        SmithPanel.smithChart.addElement(sc.getDa());
        SmithPanel.smithChart.addElement(sc.getDb());
        SmithPanel.smithChart.addElement(sc.getDaStroke());
        SmithPanel.smithChart.addElement(sc.getDbStroke());
      }
      if (SmithPanel.this.step == 4) {
        if (SmithPanel.this.itemStub == 0)
        {
          sc.setDaArc(sc.configureDa(sc.getAngleZl(), sc.getAngleSol1(), 1.05D));
          SmithPanel.smithChart.addElement(sc.getDaArc());
        }
        else if (SmithPanel.this.itemStub == 2)
        {
          sc.setDaArc(sc.configureDa(sc.getAngleYl(), sc.getAngleSol1(), 1.05D));
          SmithPanel.smithChart.addElement(sc.getDaArc());
        }
      }
      if (SmithPanel.this.step == 5) {
        if (SmithPanel.this.itemStub == 0)
        {
          sc.setDbArc(sc.configureDa(sc.getAngleZl(), sc.getAngleSol2(), 1.1D));
          SmithPanel.smithChart.addElement(sc.getDbArc());
        }
        else if (SmithPanel.this.itemStub == 2)
        {
          sc.setDbArc(sc.configureDa(sc.getAngleYl(), sc.getAngleSol2(), 1.1D));
          SmithPanel.smithChart.addElement(sc.getDbArc());
        }
      }
      if (SmithPanel.this.step == 6)
      {
        SmithPanel.this.removeSmithCircle();
        SmithCircle Ls2 = new SmithCircle(sc.getRlSolution2(), sc.getXlSolution2(), Color.RED, Color.RED, Color.RED);
        
        SmithPanel.smithChart.addElement(Ls2.getReactCircle());
        SmithPanel.smithChart.addElement(Ls2.getBluepoint());
        double x0 = Ls2.getBluepoint().getCenterX();
        double y0 = Ls2.getBluepoint().getCenterY();
        double origenX = 0.0D;
        double origenY = 0.0D;
        sc.setLs2Arc(0.0D, Ls2.configureAngle(x0, y0, origenX, origenY) - 360.0D, 1.15D);
        SmithPanel.smithChart.addElement(sc.getLs2Arc());
      }
      if (SmithPanel.this.step == 7)
      {
        System.out.println("paso 7");
        
        SmithCircle Ls1 = new SmithCircle(sc.getRlSolution1(), sc.getXlSolution1(), Color.RED, Color.RED, Color.RED);
        
        SmithPanel.smithChart.addElement(Ls1.getReactCircle());
        SmithPanel.smithChart.addElement(Ls1.getBluepoint());
        double x0 = Ls1.getBluepoint().getCenterX();
        double y0 = Ls1.getBluepoint().getCenterY();
        double origenX = 0.0D;
        double origenY = 0.0D;
        sc.setLs1Arc(0.0D, Ls1.configureAngle(x0, y0, origenX, origenY) - 360.0D, 1.2D);
        SmithPanel.smithChart.addElement(sc.getLs1Arc());
      }
    }
  };
  EventHandler<ActionEvent> backButton = new EventHandler<ActionEvent>()
  {
    public void handle(ActionEvent e)
    {
      System.out.println("back! " + SmithPanel.this.step);
      if (SmithPanel.this.step < 1) {
        SmithPanel.this.step = 0;
      }
      SmithCircle sc = SmithPanel.getFinalIndex();
      if (SmithPanel.this.step == 1)
      {
        SmithPanel.smithChart.getGpSmith().getChildren().remove(sc.getLineReflexCoefZl());
        SmithPanel.smithChart.getGpSmith().getChildren().remove(sc.getReflexConst());
        SmithPanel.smithChart.getGpSmith().getChildren().remove(sc.getZlStroke());
        SmithPanel.smithChart.getGpSmith().getChildren().remove(sc.getZlCompLine());
        SmithPanel.smithChart.getGpSmith().getChildren().remove(sc.getYlCompLine());
        SmithPanel.smithChart.getGpSmith().getChildren().remove(sc.getYlStroke());
        SmithPanel.smithChart.getGpSmith().getChildren().remove(sc.getLineReflexCoefYl());
        SmithPanel.smithChart.getGpSmith().getChildren().remove(sc.getYl());
      }
      if (SmithPanel.this.step == 2)
      {
        SmithPanel.smithChart.getGpSmith().getChildren().remove(sc.getUnitResistence());
        SmithPanel.smithChart.getGpSmith().getChildren().remove(sc.getSolution1());
        SmithPanel.smithChart.getGpSmith().getChildren().remove(sc.getSolution2());
      }
      if (SmithPanel.this.step == 3)
      {
        SmithPanel.smithChart.getGpSmith().getChildren().remove(sc.getDa());
        SmithPanel.smithChart.getGpSmith().getChildren().remove(sc.getDb());
        SmithPanel.smithChart.getGpSmith().getChildren().remove(sc.getDaStroke());
        SmithPanel.smithChart.getGpSmith().getChildren().remove(sc.getDbStroke());
      }
      if (SmithPanel.this.step == 4) {
        SmithPanel.smithChart.getGpSmith().getChildren().remove(sc.getDaArc());
      }
      if (SmithPanel.this.step == 5) {
        SmithPanel.smithChart.getGpSmith().getChildren().remove(sc.getDbArc());
      }
      if (SmithPanel.this.step == 6)
      {
        System.out.println("atas step 6");
        SmithPanel.this.restoreSmithCircle();
      }
      SmithPanel.this.step -= 1;
    }
  };
  EventHandler<ActionEvent> remButton = new EventHandler<ActionEvent>()
  {
    public void handle(ActionEvent e)
    {
      SmithPanel.this.removeSmithCircle();
      
      SmithPanel.this.tfz0.setDisable(false);
      SmithPanel.this.tfrl.setDisable(false);
      SmithPanel.this.tfxl.setDisable(false);
      SmithPanel.this.agregar.setDisable(false);
      SmithPanel.this.quitar.setDisable(true);
      SmithPanel.this.next.setDisable(true);
    }
  };
  
  public GridPane getGrid()
  {
    return this.grid;
  }
  
  protected static SmithCircle getFinalIndex()
  {
    int size = 0;
    for (Object obj : smithChart.getElements()) {
      if ((obj instanceof SmithCircle)) {
        size = smithChart.getElements().indexOf(obj);
      }
    }
    SmithCircle sc = (SmithCircle)smithChart.getElements().get(size);
    return sc;
  }
  
  public void removeSmithCircle()
  {
    SmithCircle sc = getFinalIndex();
    smithChart.getGpSmith().getChildren().remove(sc.getResCircle());
    smithChart.getGpSmith().getChildren().remove(sc.getReactCircle());
    smithChart.getGpSmith().getChildren().remove(sc.getZl());
    smithChart.getGpSmith().getChildren().remove(sc.getYl());
    smithChart.getGpSmith().getChildren().remove(sc.getReflexConst());
    smithChart.getGpSmith().getChildren().remove(sc.getZlStroke());
    smithChart.getGpSmith().getChildren().remove(sc.getYlStroke());
    smithChart.getGpSmith().getChildren().remove(sc.getDaStroke());
    smithChart.getGpSmith().getChildren().remove(sc.getDbStroke());
    smithChart.getGpSmith().getChildren().remove(sc.getZlCompLine());
    smithChart.getGpSmith().getChildren().remove(sc.getDb());
    smithChart.getGpSmith().getChildren().remove(sc.getDa());
    smithChart.getGpSmith().getChildren().remove(sc.getDaArc());
    smithChart.getGpSmith().getChildren().remove(sc.getDbArc());
    smithChart.getGpSmith().getChildren().remove(sc.getUnitResistence());
    smithChart.getGpSmith().getChildren().remove(sc.getBluepoint());
    smithChart.getGpSmith().getChildren().remove(sc.getSolution1());
    smithChart.getGpSmith().getChildren().remove(sc.getSolution2());
    smithChart.getGpSmith().getChildren().remove(sc.getLineReflexCoefZl());
  }
  
  public void restoreSmithCircle()
  {
    SmithCircle sc = getFinalIndex();
    smithChart.addElement(sc.getDaArc());
    smithChart.addElement(sc.getDbArc());
    smithChart.addElement(sc.getLineReflexCoefZl());
    smithChart.addElement(sc.getUnitResistence());
    smithChart.addElement(sc.getReactCircle());
    smithChart.addElement(sc.getZl());
    smithChart.addElement(sc.getReflexConst());
    smithChart.addElement(sc.getZlStroke());
    smithChart.addElement(sc.getDaStroke());
    smithChart.addElement(sc.getDbStroke());
    smithChart.addElement(sc.getZlCompLine());
    smithChart.addElement(sc.getDb());
    smithChart.addElement(sc.getDa());
    smithChart.addElement(sc.getSolution1());
    smithChart.addElement(sc.getSolution2());
    smithChart.getElements().add(sc);
  }
}
