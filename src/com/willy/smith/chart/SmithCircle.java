package com.willy.smith.chart;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SmithCircle
{
  private static final Font FONT2 = Font.font("Comic Sans", FontWeight.BOLD, 12.0D);
  static final double radio = 234.0D;
  static final double origenX = 0.0D;
  static final double origenY = 0.0D;
  private Circle resCircle;
  private Arc reactCircle;
  private Circle zl;
  private Circle yl;
  private Label labelZl;
  private Line lineReflexCoefZl;
  private Line lineReflexCoefYl;
  private Circle reflexConst;
  private Circle zlStroke;
  private Circle ylStroke;
  private double resistence;
  private double reactance;
  private double angleZl;
  private double angleYl;
  private double lambda;
  private Circle bluepoint;
  private Line zlCompLine;
  private Line ylCompLine;
  private Circle solution1;
  private Circle solution2;
  private Circle daStroke;
  private Line da;
  private Circle dbStroke;
  private Line db;
  private Arc daArc;
  private Circle unit;
  private Arc dbArc;
  private double angleSol1;
  private double angleSol2;
  private Arc Ls1Arc;
  private Arc Ls2Arc;
  
  public SmithCircle(double res, double reac, Color resCol, Color reactCol, Color zlCol)
  {
    this.reactance = reac;
    this.resistence = res;
    this.unit = drawResistance(1.0D, Color.web("#27ae60"), 2);
    drawSmithCircle(resCol, reactCol, zlCol);
  }
  
  private Parent drawSmithCircle(Color resCol, Color reactCol, Color crossCol)
  {
    double xl = getReactance();
    double rl = getResistence();
    Group gp = new Group();
    
    this.resCircle = drawResistance(rl, resCol, 1);
    if (xl > 0.0D) {
      this.reactCircle = drawPositiveReactance(rl, xl, reactCol, crossCol);
    } else if (xl < 0.0D) {
      this.reactCircle = drawNegativeReactance(rl, xl, reactCol, crossCol);
    }
    return gp;
  }
  
  private Arc drawPositiveReactance(double rl, double xl, Color reactCol, Color crossCol)
  {
    double x = 1.0D;
    
    double yp = 2.0D * xl / (1.0D + xl * xl);
    double xp = 1.0D - yp / xl;
    double ratio = 1.0D / xl;
    double y = 1.0D / xl;
    double xi = -2.0D * (1.0D + rl) / (Math.pow(1.0D + rl, 2.0D) + Math.pow(xl, 2.0D)) + 1.0D;
    double yi = xl * (xi - 1.0D) / (1.0D + rl);
    
    this.bluepoint = new Circle(0.0D + xp * 234.0D, 0.0D - yp * 234.0D, 4.0D, Color.BLUE);
    Circle pReactance = new Circle(0.0D + x * 234.0D, 0.0D - y * 234.0D, ratio * 234.0D, Color.TRANSPARENT);
    Arc react = configurePositiveArc(pReactance.getCenterX(), pReactance.getCenterY(), xl, pReactance.getRadius(), reactCol, this.bluepoint.getCenterX());
    
    this.zl = new Circle(0.0D + xi * 234.0D, 0.0D + yi * 234.0D, 5.0D, crossCol);
    this.yl = new Circle(0.0D - xi * 234.0D, 0.0D - yi * 234.0D, 5.0D, crossCol);
    this.angleZl = configureAngle(this.zl.getCenterX(), this.zl.getCenterY(), 0.0D, 0.0D);
    this.angleYl = configureAngle(this.yl.getCenterX(), this.yl.getCenterY(), 0.0D, 0.0D);
    this.labelZl = configureLabelMark(rl, xl, xi, yi);
    this.lineReflexCoefZl = configureLine(0.0D, 0.0D, this.zl.getCenterX(), this.zl.getCenterY(), Color.web("#2c3e50"));
    this.lineReflexCoefYl = configureLine(0.0D, 0.0D, this.yl.getCenterX(), this.yl.getCenterY(), Color.web("#2c3e50"));
    this.reflexConst = configureReflexionConst(this.zl.getCenterX(), this.zl.getCenterY());
    this.zlStroke = configureStroke1(this.angleZl, crossCol);
    this.ylStroke = configureStroke2(this.angleZl, crossCol);
    this.zlCompLine = configureLine(this.zl.getCenterX(), this.zl.getCenterY(), this.zlStroke.getCenterX(), this.zlStroke.getCenterY(), Color.web("#2c3e50"));
    this.ylCompLine = configureLine(this.yl.getCenterX(), this.yl.getCenterY(), this.ylStroke.getCenterX(), this.ylStroke.getCenterY(), Color.web("#2c3e50"));
    this.solution1 = configureSolution(this.reflexConst.getCenterX(), this.reflexConst.getRadius(), 1);
    this.solution2 = configureSolution(this.reflexConst.getCenterX(), this.reflexConst.getRadius(), -1);
    this.lambda = configureLambda(this.angleZl);
    
    this.angleSol1 = configureAngle(this.solution1.getCenterX(), this.solution1.getCenterY(), 0.0D, 0.0D);
    this.angleSol2 = configureAngle(this.solution2.getCenterX(), this.solution2.getCenterY(), 0.0D, 0.0D);
    this.daStroke = configureStroke1(this.angleSol1, Color.web("#1abc9c"));
    this.dbStroke = configureStroke1(this.angleSol2, Color.web("#1abc9c"));
    this.db = configureLine(0.0D, 0.0D, this.dbStroke.getCenterX(), this.dbStroke.getCenterY(), Color.web("#2c3e50"));
    this.da = configureLine(0.0D, 0.0D, this.daStroke.getCenterX(), this.daStroke.getCenterY(), Color.web("#2c3e50"));
    setDaArc(configureDa(this.angleZl, this.angleSol1, 1.05D));
    setDbArc(configureDa(this.angleZl, this.angleSol2, 1.1D));
    
    double cxl = getXlSolution1();
    double crl = getRlSolution1();
    
    setLs1Arc(0.0D, this.angleSol1, 1.15D);
    setLs2Arc(0.0D, this.angleSol2, 1.15D);
    
    System.out.println(" xl : " + cxl + " rl : " + crl);
    return react;
  }
  
  public double getRlSolution1()
  {
    double nxPos = (this.solution1.getCenterX() - 0.0D) / 234.0D;
    double nyPos = (0.0D - this.solution1.getCenterY()) / 234.0D;
    double crl = 2.0D * (1.0D - nxPos) / (Math.pow(1.0D - nxPos, 2.0D) + Math.pow(nyPos, 2.0D)) - 1.0D;
    return crl;
  }
  
  public double getXlSolution1()
  {
    double nxPos = (this.solution1.getCenterX() - 0.0D) / 234.0D;
    double nyPos = (0.0D - this.solution1.getCenterY()) / 234.0D;
    double cxl = 2.0D * nyPos / (Math.pow(1.0D - nxPos, 2.0D) + Math.pow(nyPos, 2.0D));
    return cxl;
  }
  
  public double getRlSolution2()
  {
    double nxPos = (this.solution2.getCenterX() - 0.0D) / 234.0D;
    double nyPos = (0.0D - this.solution2.getCenterY()) / 234.0D;
    double crl = 2.0D * (1.0D - nxPos) / (Math.pow(1.0D - nxPos, 2.0D) + Math.pow(nyPos, 2.0D)) - 1.0D;
    return crl;
  }
  
  public double getXlSolution2()
  {
    double nxPos = (this.solution2.getCenterX() - 0.0D) / 234.0D;
    double nyPos = (0.0D - this.solution2.getCenterY()) / 234.0D;
    double cxl = 2.0D * nyPos / (Math.pow(1.0D - nxPos, 2.0D) + Math.pow(nyPos, 2.0D));
    return cxl;
  }
  
  private Arc drawNegativeReactance(double rl, double xl, Color reactCol, Color crossCol)
  {
    double x = 1.0D;
    
    double yp = 2.0D * xl / (1.0D + xl * xl);
    double xp = 1.0D - yp / xl;
    double y = 1.0D / -xl;
    double ratio = 1.0D / -xl;
    double xi = -2.0D * (1.0D + rl) / (Math.pow(1.0D + rl, 2.0D) + Math.pow(xl, 2.0D)) + 1.0D;
    double yi = xl * (xi - 1.0D) / (1.0D + rl);
    
    Circle greenpoint = new Circle(234.0D, 0.0D, 4.0D, Color.GREEN);
    Circle nReactance = new Circle(0.0D + x * 234.0D, 0.0D + y * 234.0D, ratio * 234.0D, Color.TRANSPARENT);
    
    this.zl = new Circle(0.0D + xi * 234.0D, 0.0D + yi * 234.0D, 5.0D, crossCol);
    this.yl = new Circle(0.0D - xi * 234.0D, 0.0D - yi * 234.0D, 5.0D, crossCol);
    this.bluepoint = new Circle(0.0D + xp * 234.0D, 0.0D - yp * 234.0D, 4.0D, Color.RED);
    
    Arc react = configureNegativeReactance(nReactance, greenpoint, xl, reactCol);
    this.angleZl = configureAngle(this.zl.getCenterX(), this.zl.getCenterY(), 0.0D, 0.0D);
    this.angleYl = configureAngle(this.yl.getCenterX(), this.yl.getCenterY(), 0.0D, 0.0D);
    this.labelZl = configureLabelMark(rl, xl, xi, yi);
    this.lineReflexCoefZl = configureLine(0.0D, 0.0D, this.zl.getCenterX(), this.zl.getCenterY(), Color.web("#2c3e50"));
    this.lineReflexCoefYl = configureLine(0.0D, 0.0D, this.yl.getCenterX(), this.yl.getCenterY(), Color.web("#2c3e50"));
    this.reflexConst = configureReflexionConst(this.zl.getCenterX(), this.zl.getCenterY());
    this.zlStroke = configureStroke1(this.angleZl, crossCol);
    this.ylStroke = configureStroke2(this.angleZl, crossCol);
    this.zlCompLine = configureLine(this.zl.getCenterX(), this.zl.getCenterY(), this.zlStroke.getCenterX(), this.zlStroke.getCenterY(), Color.web("#2c3e50"));
    this.ylCompLine = configureLine(this.yl.getCenterX(), this.yl.getCenterY(), this.ylStroke.getCenterX(), this.ylStroke.getCenterY(), Color.web("#2c3e50"));
    this.solution1 = configureSolution(this.reflexConst.getCenterX(), this.reflexConst.getRadius(), 1);
    this.solution2 = configureSolution(this.reflexConst.getCenterX(), this.reflexConst.getRadius(), -1);
    this.lambda = configureLambda(this.angleZl);
    
    this.angleSol1 = configureAngle(this.solution1.getCenterX(), this.solution1.getCenterY(), 0.0D, 0.0D);
    this.angleSol2 = configureAngle(this.solution2.getCenterX(), this.solution2.getCenterY(), 0.0D, 0.0D);
    this.daStroke = configureStroke1(this.angleSol1, Color.web("#1abc9c"));
    this.dbStroke = configureStroke1(this.angleSol2, Color.web("#1abc9c"));
    this.db = configureLine(0.0D, 0.0D, this.dbStroke.getCenterX(), this.dbStroke.getCenterY(), Color.web("#2c3e50"));
    this.da = configureLine(0.0D, 0.0D, this.daStroke.getCenterX(), this.daStroke.getCenterY(), Color.web("#2c3e50"));
    setDaArc(configureDa(this.angleZl, this.angleSol1, 1.05D));
    setDbArc(configureDa(this.angleZl, this.angleSol2, 1.1D));
    
    return react;
  }
  
  private Arc configureNegativeReactance(Circle nReactance, Circle greenpoint, double xl, Color reactCol)
  {
    double gpxDown = (greenpoint.getCenterX() - nReactance.getCenterX()) / 234.0D;
    double rpx = Math.acos((this.bluepoint.getCenterX() - nReactance.getCenterX()) / nReactance.getRadius());
    
    double gpangleDown = 180.0D * Math.acos(gpxDown) / 3.141592653589793D;
    double rpangle = Math.abs(xl) > 1.0D ? 360.0D - 180.0D * rpx / 3.141592653589793D : 180.0D * rpx / 3.141592653589793D;
    double length = rpangle - gpangleDown;
    Arc react = new Arc();
    react.setCenterX(nReactance.getCenterX());
    react.setCenterY(nReactance.getCenterY());
    react.setRadiusX(nReactance.getRadius());
    react.setRadiusY(nReactance.getRadius());
    react.setStartAngle(gpangleDown);
    react.setLength(length);
    react.setFill(Color.TRANSPARENT);
    react.setStroke(reactCol);
    react.setType(ArcType.OPEN);
    return react;
  }
  
  public Arc configureDa(double angle, double angle2, double scale)
  {
    double length = angle - angle2 > 0.0D ? angle - angle2 : 360.0D - (angle2 - angle);
    Arc temp = new Arc();
    temp.setCenterX(0.0D);
    temp.setCenterY(0.0D);
    temp.setRadiusX(scale * 234.0D);
    temp.setRadiusY(scale * 234.0D);
    temp.setStartAngle(angle2);
    temp.setLength(length);
    temp.setFill(Color.TRANSPARENT);
    temp.setStroke(Color.web("#9b59b6"));
    temp.setStrokeWidth(4.0D);
    temp.setType(ArcType.OPEN);
    return temp;
  }
  
  private double configureLambda(double angle)
  {
    double lambda = 0.25D - angle * 0.5D / 360.0D;
    return lambda;
  }
  
  private Circle configureSolution(double refl, double r1, int sgn)
  {
    Circle c = drawResistance(1.0D, Color.VIOLET, 1);
    double x0 = (c.getCenterX() - refl) / 234.0D;
    double r = r1 / 234.0D;
    double k = c.getRadius() / 234.0D;
    
    double xPos = (Math.pow(r, 2.0D) + Math.pow(x0, 2.0D) - Math.pow(k, 2.0D)) / (2.0D * x0);
    double yPos = Math.sqrt(Math.pow(r, 2.0D) - Math.pow(xPos, 2.0D));
    Circle temp = new Circle(0.0D + 234.0D * xPos, 0.0D - sgn * 234.0D * yPos, 5.0D, Color.web("#f39c12"));
    return temp;
  }
  
  private Arc configurePositiveArc(double centerX, double centerY, double xl, double radius, Color reactCol, double blue)
  {
    double bpx = Math.acos((blue - centerX) / radius);
    double start = xl > 1.0D ? Math.toDegrees(bpx) : 360.0D - Math.toDegrees(bpx);
    double length = 270.0D - start;
    Arc react = new Arc();
    react.setCenterX(centerX);
    react.setCenterY(centerY);
    react.setRadiusX(radius);
    react.setRadiusY(radius);
    react.setStartAngle(start);
    react.setLength(length);
    react.setFill(Color.TRANSPARENT);
    react.setStroke(reactCol);
    react.setType(ArcType.OPEN);
    return react;
  }
  
  private Circle configureStroke2(double angle, Color crossCol)
  {
    double xxp = 0.0D + 234.0D * Math.cos(Math.toRadians(angle + 180.0D));
    double yyp = 0.0D - 234.0D * Math.sin(Math.toRadians(angle + 180.0D));
    Circle temp = new Circle(xxp, yyp, 5.0D, crossCol);
    return temp;
  }
  
  private Circle configureStroke1(double angle, Color crossCol)
  {
    double xx = 0.0D + 234.0D * Math.cos(Math.toRadians(angle));
    double yy = 0.0D - 234.0D * Math.sin(Math.toRadians(angle));
    Circle temp = new Circle(xx, yy, 5.0D, crossCol);
    return temp;
  }
  
  public double configureAngle(double x0, double y0, double origenX, double origenY)
  {
    double angle = 0.0D;
    double num = origenY - y0;
    double den = x0 - origenX;
    double m = num / den;
    if ((num > 0.0D) && (den > 0.0D)) {
      angle = Math.toDegrees(Math.atan(m));
    } else if ((num > 0.0D) && (den < 0.0D)) {
      angle = 180.0D + Math.toDegrees(Math.atan(m));
    } else if ((num < 0.0D) && (den > 0.0D)) {
      angle = 360.0D + Math.toDegrees(Math.atan(m));
    } else {
      angle = 180.0D + Math.toDegrees(Math.atan(m));
    }
    return angle;
  }
  
  private Circle configureReflexionConst(double zlx, double zly)
  {
    double tx = Math.pow(zlx - 0.0D, 2.0D);
    double ty = Math.pow(zly - 0.0D, 2.0D);
    double r = Math.sqrt(ty + tx);
    Circle reflexConst = new Circle(0.0D, 0.0D, r, Color.TRANSPARENT);
    reflexConst.setStroke(Color.web("#d35400"));
    reflexConst.setStrokeWidth(2.0D);
    return reflexConst;
  }
  
  private Line configureLine(double startx, double starty, double endx, double endy, Color color)
  {
    Line reflex = new Line();
    reflex.setStartX(startx);
    reflex.setStartY(starty);
    reflex.setEndX(endx);
    reflex.setEndY(endy);
    reflex.setFill(color);
    reflex.setStroke(color);
    reflex.setStrokeWidth(2.0D);
    return reflex;
  }
  
  private Label configureLabelMark(double rl, double xl, double xi, double yi)
  {
    rl *= 100.0D;
    rl = (int)rl;
    rl /= 100.0D;
    xl *= 100.0D;
    xl = (int)xl;
    xl /= 100.0D;
    Label mark = new Label("(" + rl + ", " + xl + ")");
    mark.setLayoutX(0.0D + xi * 234.0D + 11.700000000000001D);
    mark.setLayoutY(0.0D + yi * 234.0D);
    mark.setVisible(false);
    mark.setTextFill(Color.web("#2c3e50"));
    mark.setFont(FONT2);
    return mark;
  }
  
  public Circle drawResistance(double rl, Color resCol, int stroke)
  {
    double xc = rl / (1.0D + rl);
    double yc = 0.0D;
    double ratio = 1.0D / (1.0D + rl);
    
    Circle res = new Circle(0.0D + xc * 234.0D, 0.0D + yc, ratio * 234.0D, Color.TRANSPARENT);
    res.setStroke(resCol);
    res.setStrokeWidth(stroke);
    return res;
  }
  
  public Circle getUnitResistence()
  {
    return this.unit;
  }
  
  public Circle getYlStroke()
  {
    return this.ylStroke;
  }
  
  public Circle getYl()
  {
    return this.yl;
  }
  
  public double getResistence()
  {
    return this.resistence;
  }
  
  public Circle getZlStroke()
  {
    return this.zlStroke;
  }
  
  public double getReactance()
  {
    return this.reactance;
  }
  
  public Circle getResCircle()
  {
    return this.resCircle;
  }
  
  public Arc getReactCircle()
  {
    return this.reactCircle;
  }
  
  public Circle getZl()
  {
    return this.zl;
  }
  
  public Label getLabelZl()
  {
    return this.labelZl;
  }
  
  public Circle getReflexConst()
  {
    return this.reflexConst;
  }
  
  public Circle getBluepoint()
  {
    return this.bluepoint;
  }
  
  public double getAngleZl()
  {
    return this.angleZl;
  }
  
  public Line getZlCompLine()
  {
    return this.zlCompLine;
  }
  
  public Line getYlCompLine()
  {
    return this.ylCompLine;
  }
  
  public Circle getSolution1()
  {
    return this.solution1;
  }
  
  public Circle getSolution2()
  {
    return this.solution2;
  }
  
  public Circle getDaStroke()
  {
    return this.daStroke;
  }
  
  public double getLambda()
  {
    return this.lambda;
  }
  
  public Line getDa()
  {
    return this.da;
  }
  
  public Circle getDbStroke()
  {
    return this.dbStroke;
  }
  
  public Line getDb()
  {
    return this.db;
  }
  
  public Line getLineReflexCoefYl()
  {
    return this.lineReflexCoefYl;
  }
  
  public Line getLineReflexCoefZl()
  {
    return this.lineReflexCoefZl;
  }
  
  public Arc getDaArc()
  {
    return this.daArc;
  }
  
  public void setDaArc(Arc daArc)
  {
    this.daArc = daArc;
  }
  
  public double getAngleSol1()
  {
    return this.angleSol1;
  }
  
  public double getAngleSol2()
  {
    return this.angleSol2;
  }
  
  public double getAngleYl()
  {
    return this.angleYl;
  }
  
  public Arc getDbArc()
  {
    return this.dbArc;
  }
  
  public void setDbArc(Arc dbArc)
  {
    this.dbArc = dbArc;
  }
  
  public Arc getLs1Arc()
  {
    return this.Ls1Arc;
  }
  
  public Arc getLs2Arc()
  {
    return this.Ls2Arc;
  }
  
  public void setLs1Arc(double start, double length, double scale)
  {
    Arc temp = new Arc();
    temp.setCenterX(0.0D);
    temp.setCenterY(0.0D);
    temp.setRadiusX(scale * 234.0D);
    temp.setRadiusY(scale * 234.0D);
    temp.setStartAngle(start);
    temp.setLength(length);
    temp.setFill(Color.TRANSPARENT);
    temp.setStroke(Color.web("#9b59b6"));
    temp.setStrokeWidth(4.0D);
    temp.setType(ArcType.OPEN);
    this.Ls1Arc = temp;
  }
  
  public void setLs2Arc(double start, double length, double scale)
  {
    Arc temp = new Arc();
    temp.setCenterX(0.0D);
    temp.setCenterY(0.0D);
    temp.setRadiusX(scale * 234.0D);
    temp.setRadiusY(scale * 234.0D);
    temp.setStartAngle(start);
    temp.setLength(length);
    temp.setFill(Color.TRANSPARENT);
    temp.setStroke(Color.web("#9b59b6"));
    temp.setStrokeWidth(4.0D);
    temp.setType(ArcType.OPEN);
    this.Ls2Arc = temp;
  }
}
