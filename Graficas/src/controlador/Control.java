package controlador;

import java.awt.*;
import javax.swing.JPanel;
import vista.principalfrm;
import org.math.plot.Plot2DPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Restricciones;
import org.jvnet.substance.SubstanceLookAndFeel;

public class Control implements ActionListener {

    Plot2DPanel plot = new Plot2DPanel();
    principalfrm frmprincipal;
    ArrayList<double[]> opx = new ArrayList();
    ArrayList<double[]> opy = new ArrayList();
    ArrayList<Double> puntosy = new ArrayList();
    ArrayList<Double> puntosx = new ArrayList();
    ArrayList<Double> solucionesx = new ArrayList();
    ArrayList<Double> solucionesy = new ArrayList();
    ArrayList<Double> rsx = new ArrayList();
    ArrayList<Double> rsy = new ArrayList();
    ArrayList<Double> rsz = new ArrayList();
    ArrayList<Double> rx = new ArrayList();
    ArrayList<Double> ry = new ArrayList();
    ArrayList<Double> rz = new ArrayList();
    ArrayList<Restricciones> res = new ArrayList();
    ArrayList<Double> variables = new ArrayList();
    Restricciones re = new Restricciones();
    double mayorx = 0.0;
    double mayory = 0.0;
    double menorx = 0.0;
    double menory = 0.0;
    int contador = 0;
    int contado = 0;
    int contadoy = 0;
    int contadox = 0;
    int vars;
    int vals;
    double valmax = 0;
    double defy = 0, defx = 0;
    double ay, ax;
    double y, x;
    double iniciox = 0.0;
    double inicioy = 0.0;
    double valorx = 0.0;
    double valory = 0.0;

    public Control() {
        frmprincipal = new principalfrm();
        frmprincipal.getBtnempezar().addActionListener((ActionListener) this);
        frmprincipal.getBtnresolver().addActionListener((ActionListener) this);
        frmprincipal.getBtnSalir().addActionListener((ActionListener) this);
        SubstanceLookAndFeel.setSkin("org.jvnet.substance.skin.FindingNemoSkin");
    }

    public void iniciar() {
        //Deshabilitar 
        deshabilitar();
        //Objeto que permite graficar

        frmprincipal.setLayout(new GridLayout());
        frmprincipal.add(plot, GradientPaint.OPAQUE);//Se posiciona la grafica en el panel principal

        double[] x4 = {0.0, 0.0};
        double[] y4 = {0.0, 0.0};
        plot.addScatterPlot("Inicio", Color.black, y4, x4);

        frmprincipal.setVisible(true);
    }

    @SuppressWarnings("empty-statement")
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frmprincipal.getBtnSalir()) {
            System.exit(0);
        }
        if (e.getSource() == frmprincipal.getBtnempezar()) {
            vars = frmprincipal.getComboboxnumres().getSelectedIndex();
            vals = frmprincipal.getComboboxminmax().getSelectedIndex();
            //Habilitar
            if (vals == 0) {
                frmprincipal.getLabelmayor().setVisible(true);
                frmprincipal.getLabelmenor().setVisible(false);
                frmprincipal.getMaximo().setVisible(true);
            } else {
                frmprincipal.getLabelmayor().setVisible(false);
                frmprincipal.getLabelmenor().setVisible(true);
                frmprincipal.getMinimo().setVisible(true);
            }
            habilitar();
            resticciones(vars);
        }

        if (e.getSource() == frmprincipal.getBtnresolver()) {
            boolean bandera = true;
            try {
                String st1 = frmprincipal.getTxtX().getText();
                String st2 = frmprincipal.getTxtY().getText();
                ax = Double.parseDouble(st1);
                ay = Double.parseDouble(st2);
                valmax = 0.0;
                graficar(vars);
                sustitucion();
                table();
                for (int o = 0; o < res.size(); o++) {
                                        graficas(res.get(o).getX(), res.get(o).getY(), res.get(o).getZ(), mayorx, mayory);

                                    }
                if (vals == 0) {
                    maximo();
                   
                } else {
                    minimo();
                }
                frmprincipal.getBtnresolver().setEnabled(false);

            } catch (Exception s) {
                JOptionPane.showMessageDialog(null, "Valores Incorrectos");
                frmprincipal.getBtnresolver().setEnabled(true);
            }
           

        }
    }




private static BigDecimal truncateDecimal(double x,int numberofDecimals)
{
    if ( x > 0) {
        return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
    } else {
        return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
    }
}

    public void validador(double xs, double ys, double zs) {
        boolean dev = false;
        int con = 0;
        for (int i = 0; i < res.size(); i++) {
            double defx = (Double.parseDouble(res.get(i).getX()));
            double defy = (Double.parseDouble(res.get(i).getY()));
            double xsss = truncateDecimal(xs, 2).doubleValue();
            double ysss = truncateDecimal(ys, 2).doubleValue();
            if (res.get(i).getSigno().equals("≥")) {
                if (defx*xsss + defy*ysss >= Double.parseDouble(res.get(i).getZ())) {
                    if (xs > 0.0 && ys > 0.0) {
                        con++;
                    }

                }
            } else {
                if (defx*xsss + defy*ysss <= Double.parseDouble(res.get(i).getZ())) {
                    if (xs > 0.0 && ys > 0.0) {
                        con++;
                    }
                }
            }

        }
        defx = xs * ax;
        defy = ys * ay;
        if (defx + defy == zs) {
            if (xs > 0.0 && ys > 0.0) {
                con++;
            }

        }
        if (con == res.size() + 1) {
            solucionesx.add(xs);
            solucionesy.add(ys);
        }

        int maz = 0;

    }

    public void maximo() {

        double val = 1.0;
        boolean bandera = false;

        for (int i = 0; i < puntosx.size(); i++) {

            double vx = (double) puntosx.get(i);
            double vy = (double) puntosy.get(i);
            val = vx * (ax) + vy * (ay);
            
            validador(vx, vy, val);
        }

        if (solucionesx.size() > 0) {
            int indice;
            double yu;
            double xu;
            double menor = 0;
            int ince = 0;
            double distancia = 0;
            try {
                yu = solucionesy.get(0);
                xu = solucionesx.get(0);
                distancia = yu * ay + xu * ax;
                menor = distancia;
            } catch (Exception e) {

            }

            for (int i = 0; i < solucionesx.size(); i++) {

                yu = solucionesy.get(i);
                xu = solucionesx.get(i);

                distancia = yu * ay + xu * ax;

                if (distancia > menor) {
                    ince = i;
                }
            }
            double vx = solucionesx.get(ince);
            double vy = solucionesy.get(ince);
            val = vx * (ax) + vy * (ay);
            double objx = val-(vy*ay)/vx;
            double objy = val-(vx*ax)/vy;
            
            
            
            frmprincipal.getTxtmax().setText("" + val);
            frmprincipal.getTxtYs().setText("" + vy);
            frmprincipal.getTxtXs().setText("" + vx);
            double[] x4 = {vx, vy};
            double[] y4 = {vx, vy};
            double[] x7 = {0.0, objy};
            double[] y7 = {objx, 0.0};
            /*double[] x6 = {vx, vy};
            double[] y6 = {iniciox, menory};
            plot.addScatterPlot("Datos", Color.blue, y6, x6);
            plot.addLinePlot("Linea", Color.blue, y6, x6);
            double[] x5 = {vx, vy};
            double[] y5 = {menorx, inicioy};
            plot.addScatterPlot("Datos", Color.blue, y5, x5);
            plot.addLinePlot("Linea", Color.blue, y5, x5);*/

            plot.addScatterPlot("Datos", Color.yellow, y4, x4);
            plot.addLinePlot("Linea", Color.yellow, y4, x4);

            plot.addScatterPlot("Datos", Color.green, y7, x7);
            plot.addLinePlot("Linea", Color.green, y7, x7);

        } else if (solucionesx.size() == 0) {
            

                JOptionPane.showMessageDialog(null, "No hay solucion");
            

        }

    }

    public void graficas(String a, String b, String c, double xM, double yM) {
        double varx = 0;
        double varz = 0;
        double vary = 0;
        //////////////////////////////////////////////////////////////////////////

        varx = Double.parseDouble(a);
        vary = Double.parseDouble(b);
        varz = Double.parseDouble(c);

        if (varx == 0.0) {
            rsy.add(vary);
            rsx.add(varx);
            rsz.add(varz);
            ry.add(vary);
            rx.add(varx);
            rz.add(varz);
            varx = varz / vary;
            double[] x1 = {100, varx};
            double[] y1 = {0, varx};
            opx.add(x1);
            opy.add(y1);
            //Agregar datos a la Grafica      
            plot.addScatterPlot("Datos", Color.MAGENTA, y1, x1);
            plot.addLinePlot("Linea", Color.red, y1, x1);

        } else if (vary == 0.0) {
            rsx.add(varx);
            rsy.add(vary);
            rsz.add(varz);
            rx.add(varx);
            ry.add(vary);
            rz.add(varz);
            varx = (varz / varx);
            vary = varz;
            double[] x1 = {varx, 100};
            double[] y1 = {varx, 0.0};
            opx.add(x1);
            opy.add(y1);
            //Agregar datos a la Grafica      
            plot.addScatterPlot("Datos", Color.MAGENTA, y1, x1);
            plot.addLinePlot("Linea", Color.red, y1, x1);
        }
    }

    public void minimo() {
        double val = 1.0;
        boolean bandera = false;

        for (int i = 0; i < puntosx.size(); i++) {

            double vx = (double) puntosx.get(i);
            double vy = (double) puntosy.get(i);
            val = vx * (ax) + vy * (ay);
            validador(vx, vy, val);
        }

        if (solucionesx.size() > 0) {
            int indice;
            double yu;
            double xu;
            double menor = 0;
            int ince = 0;
            double distancia = 0;
            try {
                yu = solucionesy.get(0);
                xu = solucionesx.get(0);
                distancia = yu * ay + xu * ax;
                menor = distancia;
            } catch (Exception e) {

            }

            for (int i = 0; i < solucionesx.size(); i++) {

                yu = solucionesy.get(i);
                xu = solucionesx.get(i);

                distancia = yu * ay + xu * ax;

                if (distancia < menor) {
                    ince = i;
                }
            }
            double vx = solucionesx.get(ince);
            double vy = solucionesy.get(ince);
            val = vx * (ax) + vy * (ay);
            double objx = val/vx;
            double objy = val/vy;
            
            
            
            frmprincipal.getTxtmax().setText("" + val);
            frmprincipal.getTxtYs().setText("" + vy);
            frmprincipal.getTxtXs().setText("" + vx);
            double[] x4 = {vx, vy};
            double[] y4 = {vx, vy};
            double[] x7 = {0.0, objy};
            double[] y7 = {objx, 0.0};
           /*double[] x6 = {vx, vy};
            double[] y6 = {iniciox, menory};
            plot.addScatterPlot("Datos", Color.blue, y6, x6);
            plot.addLinePlot("Linea", Color.blue, y6, x6);
            double[] x5 = {vx, vy};
            double[] y5 = {menorx, inicioy};
            plot.addScatterPlot("Datos", Color.blue, y5, x5);
            plot.addLinePlot("Linea", Color.blue, y5, x5);*/

            plot.addScatterPlot("Datos", Color.yellow, y4, x4);
            plot.addLinePlot("Linea", Color.yellow, y4, x4);

            plot.addScatterPlot("Datos", Color.green, y7, x7);
            plot.addLinePlot("Linea", Color.green, y7, x7);

        } else if (solucionesx.size() == 0) {
           
                JOptionPane.showMessageDialog(null, "No hay solucion");
            
        }

    }

    public void listarProcesos(ArrayList<Restricciones> Lista, JTable tabla) {
        DefaultTableModel plantilla = (DefaultTableModel) tabla.getModel();
        plantilla.setRowCount(0);
        for (int i = 0; i < Lista.size(); i++) {
            plantilla.addRow(Lista.get(i).datos());
        }
    }

    public void listarProcesos1(ArrayList<Restricciones> Lista, JTable tabla) {
        DefaultTableModel plantilla = (DefaultTableModel) tabla.getModel();
        plantilla.setRowCount(0);
        for (int i = 0; i < Lista.size(); i++) {
            plantilla.addRow(Lista.get(i).datos1(i + 1));
        }
    }

    public void table() {
        String x, y, z;

        x = frmprincipal.getTxtX().getText();
        y = frmprincipal.getTxtY().getText();
        ax = Double.parseDouble(x);
        ay = Double.parseDouble(y);
        if (vals == 0) {
            z = "MAXZ = ";
        } else {
            z = "MINZ = ";
        }
        String fobj;
        if (x.equals("1")) {
            fobj = z + "X+" + y + "Y";
        } else if (y.equals("1")) {
            fobj = z + x + "X+" + "Y";
        } else if (x.equals("1") && ((y.equals("1")))) {
            fobj = z + "X+" + "Y";
        } else {
            fobj = z + x + "X+" + y + "Y";
        }
        frmprincipal.getTxtfobjetivo().setText(fobj);
        listarProcesos(res, frmprincipal.getjTableres());
        listarProcesos1(res, frmprincipal.getJtableVars());
    }

    public void graficas(String a, String b, String c) {
        double varx = 0;
        double varz = 0;
        double vary = 0;
        //////////////////////////////////////////////////////////////////////////

        varx = Double.parseDouble(a);
        vary = Double.parseDouble(b);
        varz = Double.parseDouble(c);

        if (varx == 0.0) {
            rsy.add(vary);
            rsx.add(varx);
            rsz.add(varz);
            ry.add(vary);
            rx.add(varx);
            rz.add(varz);
            System.out.println("Vary");
            varx = varz / vary;
            double[] x1 = {0.0, varx};
            double[] y1 = {0, varx};
            opx.add(x1);
            opy.add(y1);
            //Agregar datos a la Grafica      
            plot.addScatterPlot("Datos", y1, x1);
            //graficar en linea
            plot.addLinePlot("Linea", y1, x1);

        } else if (vary == 0.0) {
            rsx.add(varx);
            rsy.add(vary);
            rsz.add(varz);
            rx.add(varx);
            ry.add(vary);
            rz.add(varz);
            varx = (varz / varx);
            vary = varz;

            double[] x1 = {varx, 0.0};
            double[] y1 = {varx, 0.0};
            opx.add(x1);
            opy.add(y1);
            //Agregar datos a la Grafica      
            plot.addScatterPlot("Datos", Color.MAGENTA, y1, x1);
            plot.addLinePlot("Linea", Color.red, y1, x1);
        } else {

            rsy.add(vary);
            rsx.add(varx);
            rsz.add(varz);
            ry.add(vary);
            rx.add(varx);
            rz.add(varz);
            varx = varz / varx;
            vary = varz / vary;

            if (vary < 0) {
                if (contadox == 0) {
                    if (varx > iniciox) {
                        menorx = varx;
                    }
                    contadox++;
                }
                if ((varx < menorx) && (varx > iniciox)) {
                    menorx = varx;
                }
                double[] x1 = {varx, 0.0};
                double[] y1 = {(varx * 2), vary * -1};
                opx.add(x1);
                opy.add(y1);
                //Agregar datos a la Grafica      
                plot.addScatterPlot("Datos", Color.MAGENTA, y1, x1);
                plot.addLinePlot("Linea", Color.red, y1, x1);

            } else if (varx < 0) {
                if (contadoy == 0) {
                    if (varx > iniciox) {
                        menorx = varx;
                    }
                    contadoy++;
                }
                if ((vary < menory) && (vary > inicioy)) {
                    menory = vary;
                }

                double[] x1 = {0.0, vary};
                double[] y1 = {varx * -1, vary * 2};
                opx.add(x1);
                opy.add(y1);
                //Agregar datos a la Grafica      
                plot.addScatterPlot("Datos", Color.MAGENTA, y1, x1);
                plot.addLinePlot("Linea", Color.red, y1, x1);
            } else {
                if ((iniciox == 0.0) && (inicioy == 0.0)) {
                    if (contado == 0) {
                        if (varx > iniciox) {
                            menorx = varx;
                        }
                        if (vary > inicioy) {
                            menory = vary;
                        }
                        contado++;
                    }
                    if ((varx < menorx) && (varx > iniciox)) {
                        menorx = varx;
                    }
                    if ((vary < menory) && (vary > inicioy)) {
                        menory = vary;
                    }

                } else if (inicioy != 0.0) {
                    if (contadox == 0) {
                        if (varx > iniciox) {
                            menorx = varx;
                        }
                        contadox++;
                    }
                    if ((varx < menorx) && (varx > iniciox)) {
                        menorx = varx;
                    }
                } else if (iniciox != 0.0) {
                    if (contadoy == 0) {
                        if (varx > iniciox) {
                            menorx = varx;
                        }
                        contadoy++;
                    }
                    if ((vary < menory) && (vary > inicioy)) {
                        menory = vary;
                    }
                }

                double[] x1 = {varx, 0.0};
                double[] y1 = {0.0, vary};
                opx.add(x1);
                opy.add(y1);
                //Agregar datos a la Grafica      
                plot.addScatterPlot("Datos", Color.MAGENTA, y1, x1);
                plot.addLinePlot("Linea", Color.red, y1, x1);
            }

        }

    }

    public void sustitucion() {
        int count = 0;
        double au1, au2, au3, au4;

        for (int i = 0; i < vars; i++) {
            for (int j = i; j < vars + 1; j++) {

                au1 = rx.get(i);
                au2 = rx.get(j);
                au3 = ry.get(i);
                au4 = ry.get(j);
                sustituir(i, j);
                if (recta(au1, au2, au3, au4) > 0) {

                    System.out.println(recta(au1, au2, au3, au4));
                    System.out.println(i + "-" + j);
                    count++;
                }
            }

        }

    }

    public void sustituir(int i, int q) {
        double x = 0.0, y = 0.0, z = 0.0;
        if (ry.get(i).equals(ry.get(q)) && rx.get(i).equals(rx.get(q))) {

        } else if (rx.get(i).equals(0.0) && ry.get(q).equals(0.0)) {

            double aux = 0, aux1 = 0, aux2 = 0;
            double auy = 0, auy1 = 0, auy2 = 0;
            double auz = 0, auz1 = 0, auz2 = 0;

            aux = rx.get(i);//0
            aux1 = rx.get(q);//1

            auy = ry.get(i);//1
            auy1 = ry.get(q);//0

            auz = rz.get(i);//3
            auz1 = rz.get(q);//3

            x = auz1 / aux1;
            y = auz / auy;

            System.out.println(aux + "X+" + auy + "y = " + auz);
            System.out.println(aux1 + "X+" + auy1 + "y = " + auz1);
            System.out.println("Entro");
            System.out.println("Y = " + y);
            System.out.println("X = " + x);
            double[] x4 = {x, y};
            double[] y4 = {x, y};

            if (x > 0 && y > 0) {

                puntosx.add(x);
                puntosy.add(y);
                if (x > mayorx) {
                    mayorx = x;
                }
                if (y > mayory) {
                    mayory = y;
                }
                plot.addScatterPlot("Datos", Color.MAGENTA, y4, x4);
                plot.addLinePlot("Linea", Color.red, y4, x4);

            }

        } else if (rx.get(q).equals(0.0) && ry.get(i).equals(0.0)) {
            double aux = 0, aux1 = 0, aux2 = 0;
            double auy = 0, auy1 = 0, auy2 = 0;
            double auz = 0, auz1 = 0, auz2 = 0;

            aux = rx.get(i);//1
            aux1 = rx.get(q);//0

            auy = ry.get(i);//0
            auy1 = ry.get(q);//1

            auz = rz.get(i);//3
            auz1 = rz.get(q);//3

            x = auz / aux;
            y = auz1 / auy1;

            System.out.println(aux + "X+" + auy + "y = " + auz);
            System.out.println(aux1 + "X+" + auy1 + "y = " + auz1);

            System.out.println("Y = " + y);
            System.out.println("X = " + x);
            double[] x4 = {x, y};
            double[] y4 = {x, y};

            if (x > 0 && y > 0) {

                puntosx.add(x);
                puntosy.add(y);
                if (x > mayorx) {
                    mayorx = x;
                }
                if (y > mayory) {

                    mayory = y;
                }
                plot.addScatterPlot("Datos", Color.MAGENTA, y4, x4);
                plot.addLinePlot("Linea", Color.red, y4, x4);

            }
        } else if ((rx.get(i).equals(0.0)) && (ry.get(i) != 0.0) && (ry.get(q) != 0.0) && (rx.get(q) != 0.0)) {

            double aux = 0, aux1 = 0, aux2 = 0;
            double auy = 0, auy1 = 0, auy2 = 0;
            double auz = 0, auz1 = 0, auz2 = 0;

            aux = rx.get(i);//0
            aux1 = rx.get(q);//x

            auy = ry.get(i);//x
            auy1 = ry.get(q);//x

            auz = rz.get(i);//x
            auz1 = rz.get(q);//x

            y = auz / auy;
            x = (auz1 - (auy1 * y)) / aux1;

            System.out.println(aux + "X2+" + auy + "y = " + auz);
            System.out.println(aux1 + "X+" + auy1 + "y = " + auz1);

            System.out.println("Y = " + y);
            System.out.println("X = " + x);
            double[] x4 = {x, y};
            double[] y4 = {x, y};

            if (x > 0 && y > 0) {
                if (contadox == 0) {

                    if (x > iniciox) {
                        menorx = x;
                    }
                    contadox++;
                }
                if ((x < menorx) && (x > iniciox)) {
                    menorx = x;
                }
                if (x > mayorx) {
                    mayorx = x;
                }
                if (y > mayory) {
                    mayory = y;
                }
                puntosx.add(x);
                puntosy.add(y);
                plot.addScatterPlot("Datos", Color.MAGENTA, y4, x4);
                plot.addLinePlot("Linea", Color.red, y4, x4);

            }

        } else if ((rx.get(q).equals(0.0)) && (ry.get(i) != 0.0) && (ry.get(q) != 0.0) && (rx.get(i) != 0.0)) {

            double aux = 0, aux1 = 0, aux2 = 0;
            double auy = 0, auy1 = 0, auy2 = 0;
            double auz = 0, auz1 = 0, auz2 = 0;

            aux = rx.get(i);//x
            aux1 = rx.get(q);//0

            auy = ry.get(i);//x
            auy1 = ry.get(q);//x

            auz = rz.get(i);//x
            auz1 = rz.get(q);//x

            y = auz1 / auy1;
            x = (auz + (-auy * y)) / aux;

            double[] x4 = {x, y};
            double[] y4 = {x, y};

            if (x > 0 && y > 0) {
                if (contadox == 0) {

                    if (x > iniciox) {
                        menorx = x;
                    }
                    contadox++;
                }
                if ((x < menorx) && (x > iniciox)) {
                    menorx = x;
                }

                puntosx.add(x);
                puntosy.add(y);
                if (x > mayorx) {
                    mayorx = x;
                }
                if (y > mayory) {
                    mayory = y;
                }
                plot.addScatterPlot("Datos", Color.MAGENTA, y4, x4);
                plot.addLinePlot("Linea", Color.red, y4, x4);

            }

        } else if (ry.get(i).equals(0.0) && rx.get(i) != 0.0 && rx.get(q) != 0.0 && ry.get(q) != 0.0) {
            double aux = 0, aux1 = 0, aux2 = 0;
            double auy = 0, auy1 = 0, auy2 = 0;
            double auz = 0, auz1 = 0, auz2 = 0;

            aux = rx.get(i);//x
            aux1 = rx.get(q);//x

            auy = ry.get(i);//0
            auy1 = ry.get(q);//x

            auz = rz.get(i);//x
            auz1 = rz.get(q);//x

            x = auz / aux;
            y = (auz1 + (-aux1 * x)) / auy1;

            System.out.println(aux + "X+" + auy + "y = " + auz);
            System.out.println(aux1 + "X+" + auy1 + "y = " + auz1);

            System.out.println("Y = " + y);
            System.out.println("X = " + x);
            double[] x4 = {x, y};
            double[] y4 = {x, y};

            if (x > 0 && y > 0) {
                if (contadoy == 0) {

                    if (y > inicioy) {
                        menory = y;
                    }
                    contadoy++;
                }
                if ((y < menory) && (y > inicioy)) {
                    menory = y;
                }
                puntosx.add(x);
                puntosy.add(y);
                if (x > mayorx) {
                    mayorx = x;
                }
                if (y > mayory) {
                    mayory = y;
                }
                plot.addScatterPlot("Datos", Color.MAGENTA, y4, x4);
                plot.addLinePlot("Linea", Color.red, y4, x4);

            }
        } else if (ry.get(q).equals(0.0) && rx.get(i) != 0.0 && rx.get(q) != 0.0 && ry.get(i) != 0.0) {

            double aux = 0, aux1 = 0, aux2 = 0;
            double auy = 0, auy1 = 0, auy2 = 0;
            double auz = 0, auz1 = 0, auz2 = 0;

            aux = rx.get(i);//x
            aux1 = rx.get(q);//x

            auy = ry.get(i);//x
            auy1 = ry.get(q);//0

            auz = rz.get(i);//x
            auz1 = rz.get(q);//x

            x = auz1 / aux1;
            y = (auz + (-aux * x)) / auy;

            double[] x4 = {x, y};
            double[] y4 = {x, y};

            if (x > 0 && y > 0) {
                if (contadoy == 0) {

                    if (y > inicioy) {
                        menory = y;
                    }
                    contadoy++;
                }
                if ((y < menory) && (y > inicioy)) {
                    menory = y;
                }
                puntosx.add(x);
                puntosy.add(y);
                if (x > mayorx) {
                    mayorx = x;
                }
                if (y > mayory) {
                    mayory = y;
                }
                plot.addScatterPlot("Datos", Color.MAGENTA, y4, x4);
                plot.addLinePlot("Linea", Color.red, y4, x4);

            }
        } else if (ry.get(q) != 0.0 && rx.get(i) != 0.0 && rx.get(q) != 0.0 && ry.get(i) != 0.0) {
            double aux = 0, aux1 = 0, aux2 = 0;
            double auy = 0, auy1 = 0, auy2 = 0;
            double auz = 0, auz1 = 0, auz2 = 0;
            if (rx.get(q).equals(rx.get(i)) && ry.get(i) != ry.get(q)) {

                aux = rx.get(i);//1
                aux1 = rx.get(q);//1

                auy = ry.get(i);//x
                auy1 = ry.get(q);//x

                auz = rz.get(i);//x
                auz1 = rz.get(q);//x

                if (rx.get(i) > 0) {

                    aux = aux * -1;
                    auy = auy * -1;
                    auz = auz * -1;
                }

                aux2 = aux + aux1;
                auy2 = auy + auy1;
                auz2 = auz + auz1;

                System.out.println(aux + "X+" + auy + "y = " + auz);
                System.out.println(aux1 + "X+" + auy1 + "y = " + auz1);
                System.out.println(aux2 + "X+" + auy2 + "y = " + auz2);

                y = auz2 / auy2;
                x = (auz + (-auy * y)) / aux;

                System.out.println("Y = " + y);
                System.out.println("X = " + x);
                double[] x4 = {x, y};
                double[] y4 = {x, y};

                if (x > 0 && y > 0) {

                    if (x > mayorx) {
                        mayorx = x;
                    }
                    if (y > mayory) {
                        mayory = y;
                    }
                    puntosx.add(x);
                    puntosy.add(y);
                    plot.addScatterPlot("Datos", Color.MAGENTA, y4, x4);
                    plot.addLinePlot("Linea", Color.red, y4, x4);
                }
            } else if (ry.get(q).equals(ry.get(i)) && rx.get(i) != rx.get(q)) {

                aux = aux1 = aux2 = 0;
                auy = auy1 = auy2 = 0;
                auz = auz1 = auz2 = 0;

                aux = rx.get(i);//x
                aux1 = rx.get(q);//x

                auy = ry.get(i);//1
                auy1 = ry.get(q);//1

                auz = rz.get(i);//x
                auz1 = rz.get(q);//x

                if (ry.get(i) > 0) {
                    aux = aux * -1;
                    auy = auy * -1;
                    auz = auz * -1;
                }

                aux2 = aux + aux1;
                auy2 = auy + auy1;
                auz2 = auz + auz1;

                System.out.println(aux + "X+" + auy + "y = " + auz);
                System.out.println(aux1 + "X+" + auy1 + "y = " + auz1);
                System.out.println(aux2 + "X+" + auy2 + "y = " + auz2);

                x = auz2 / aux2;
                y = (auz + (-aux * x)) / auy;

                System.out.println("Y = " + y);
                System.out.println("X = " + x);
                double[] x5 = {x, y};
                double[] y5 = {x, y};

                if (x > 0 && y > 0) {

                    puntosx.add(x);
                    puntosy.add(y);
                    if (x > mayorx) {
                        mayorx = x;
                    }
                    if (y > mayory) {
                        mayory = y;
                    }
                    plot.addScatterPlot("Datos", Color.MAGENTA, y5, x5);
                    plot.addLinePlot("Linea", Color.red, y5, x5);

                }
            } else if (rx.get(i) != rx.get(q) && ry.get(i) != ry.get(q)) {

                double aux3, aux4, auy3, auy4, auz3, auz4;
                if (rx.get(i) < rx.get(q)) {
                    aux = rx.get(i);
                    aux1 = rx.get(q);
                    auy = ry.get(i);
                    auy1 = ry.get(q);

                    auz = rz.get(i);
                    auz1 = rz.get(q);

                    aux2 = aux * aux1;
                    auy2 = auy * aux1;
                    auz2 = auz * aux1;

                    aux3 = aux1 * aux;
                    auy3 = auy1 * aux;
                    auz3 = auz1 * aux;

                    aux2 = aux2 * -1;
                    auy2 = auy2 * -1;
                    auz2 = auz2 * -1;

                    aux4 = aux2 + aux3;
                    auy4 = auy2 + auy3;
                    auz4 = auz2 + auz3;

                    System.out.println(aux + "X+" + auy + "y = " + auz);
                    System.out.println(aux1 + "X+" + auy1 + "y = " + auz1);
                    System.out.println(aux2 + "X+" + auy2 + "y = " + auz2);
                    System.out.println(aux3 + "X+" + auy3 + "y = " + auz3);
                    System.out.println(aux4 + "X+" + auy4 + "y = " + auz4);
                    y = auz4 / auy4;
                    x = (auz - (auy * y)) / aux;

                    System.out.println("Y = " + y);
                    System.out.println("X = " + x);

                    double[] x6 = {x, y};
                    double[] y6 = {x, y};

                    if (x > 0 && y > 0) {

                        puntosx.add(x);
                        puntosy.add(y);
                        if (x > mayorx) {
                            mayorx = x;
                        }
                        if (y > mayory) {
                            mayory = y;
                        }

                        plot.addScatterPlot("Datos", Color.MAGENTA, y6, x6);
                        plot.addLinePlot("Linea", Color.red, y6, x6);
                    }
                } else {
                    aux = rx.get(i);
                    aux1 = rx.get(q);
                    auy = ry.get(i);
                    auy1 = ry.get(q);

                    auz = rz.get(i);
                    auz1 = rz.get(q);

                    aux2 = aux * aux1;
                    auy2 = auy * aux1;
                    auz2 = auz * aux1;

                    aux3 = aux1 * aux;
                    auy3 = auy1 * aux;
                    auz3 = auz1 * aux;

                    aux3 = aux3 * -1;
                    auy3 = auy3 * -1;
                    auz3 = auz3 * -1;

                    aux4 = aux2 + aux3;
                    auy4 = auy2 + auy3;
                    auz4 = auz2 + auz3;

                    System.out.println(aux + "X+" + auy + "y = " + auz);
                    System.out.println(aux1 + "X+" + auy1 + "y = " + auz1);
                    System.out.println(aux2 + "X+" + auy2 + "y = " + auz2);
                    System.out.println(aux3 + "X+" + auy3 + "y = " + auz3);
                    System.out.println(aux4 + "X+" + auy4 + "y = " + auz4);
                    y = auz4 / auy4;
                    x = (auz - (auy * y)) / aux;

                    System.out.println("Y = " + y);
                    System.out.println("X = " + x);

                    double[] x6 = {x, y};
                    double[] y6 = {x, y};

                    if (x > 0 && y > 0) {

                        puntosx.add(x);
                        puntosy.add(y);
                        if (x > mayorx) {
                            mayorx = x;
                        }
                        if (y > mayory) {
                            mayory = y;
                        }
                        plot.addScatterPlot("Datos", Color.MAGENTA, y6, x6);
                        plot.addLinePlot("Linea", Color.red, y6, x6);
                    }
                }
            }
        }
    }

    public void deshabilitar() {
        frmprincipal.getBtnresolver().setEnabled(false);
        frmprincipal.getTxtX().setEditable(false);
        frmprincipal.getTxtY().setEditable(false);
        frmprincipal.getTxtfobjetivo().setEditable(false);
        frmprincipal.getTxtmax().setEditable(false);
        frmprincipal.getComboxxy1().setEditable(false);
        frmprincipal.getComboxxy2().setEditable(false);
        frmprincipal.getComboxxy13().setEditable(false);
        frmprincipal.getComboxxy4().setEditable(false);
        frmprincipal.getComboxxy5().setEditable(false);
        frmprincipal.getComboxxy6().setEditable(false);
        frmprincipal.getComboxxy7().setEditable(false);
        frmprincipal.getComboxxy8().setEditable(false);
        frmprincipal.getComboxxy9().setEditable(false);
        frmprincipal.getComboxxy10().setEditable(false);
        frmprincipal.getComboxxy11().setEditable(false);
        frmprincipal.getComboxxy12().setEditable(false);
        frmprincipal.getLabelmenor().setVisible(false);
        frmprincipal.getLabelmayor().setVisible(false);
        frmprincipal.getMaximo().setVisible(false);
        frmprincipal.getMinimo().setVisible(false);
        frmprincipal.getTxtXs().setEditable(false);
        frmprincipal.getTxtYs().setEditable(false);
    }

    public void habilitar() {
        frmprincipal.getComboboxminmax().setEnabled(false);
        frmprincipal.getComboboxnumres().setEnabled(false);
        frmprincipal.getTxtVariables().setEnabled(false);
        frmprincipal.getBtnempezar().setEnabled(false);

        frmprincipal.getBtnresolver().setEnabled(true);
        frmprincipal.getTxtX().setEditable(true);
        frmprincipal.getTxtY().setEditable(true);

    }

    public double recta(double x1, double x2, double y1, double y2) {
        double a = 0;
        a = ((0 - x1) * (y2 - 0)) - ((0 - x2) * (y1 - 0));
        return a;
    }

    public void resticciones(int vars) {
        switch (vars) {
            case 1: {
                frmprincipal.getTxtEntradaZ3().setEditable(true);
                frmprincipal.getTxtEntradaX3().setEditable(true);
                frmprincipal.getTxtEntradaY3().setEditable(true);
                frmprincipal.getTxtEntradaX2().setEditable(true);
                frmprincipal.getTxtEntradaY2().setEditable(true);
                frmprincipal.getTxtEntradaZ2().setEditable(true);

                break;
            }
            case 0: {
                frmprincipal.getTxtEntradaX2().setEditable(true);
                frmprincipal.getTxtEntradaY2().setEditable(true);
                frmprincipal.getTxtEntradaZ2().setEditable(true);
                break;
            }
            case 2: {
                frmprincipal.getTxtEntradaY4().setEditable(true);
                frmprincipal.getComboxxy13().setEditable(true);
                frmprincipal.getTxtEntradaX4().setEditable(true);
                frmprincipal.getTxtEntradaZ3().setEditable(true);
                frmprincipal.getTxtEntradaX3().setEditable(true);
                frmprincipal.getTxtEntradaY3().setEditable(true);
                frmprincipal.getTxtEntradaX2().setEditable(true);
                frmprincipal.getTxtEntradaY2().setEditable(true);
                frmprincipal.getTxtEntradaZ2().setEditable(true);
                break;
            }
            case 3: {
                frmprincipal.getTxtEntradaY4().setEditable(true);
                frmprincipal.getComboxxy13().setEditable(true);
                frmprincipal.getTxtEntradaX4().setEditable(true);
                frmprincipal.getTxtEntradaZ3().setEditable(true);
                frmprincipal.getTxtEntradaX3().setEditable(true);
                frmprincipal.getTxtEntradaY3().setEditable(true);
                frmprincipal.getTxtEntradaX2().setEditable(true);
                frmprincipal.getTxtEntradaY2().setEditable(true);
                frmprincipal.getTxtEntradaZ2().setEditable(true);
                frmprincipal.getComboxxy4().setEditable(true);
                frmprincipal.getTxtEntradaY5().setEditable(true);
                frmprincipal.getTxtEntradaX5().setEditable(true);
                break;
            }
            case 4: {
                frmprincipal.getComboxxy5().setEditable(true);
                frmprincipal.getTxtEntradaX6().setEditable(true);
                frmprincipal.getTxtEntradaY6().setEditable(true);
                frmprincipal.getTxtEntradaY4().setEditable(true);
                frmprincipal.getComboxxy13().setEditable(true);
                frmprincipal.getTxtEntradaX4().setEditable(true);
                frmprincipal.getTxtEntradaZ3().setEditable(true);
                frmprincipal.getTxtEntradaX3().setEditable(true);
                frmprincipal.getTxtEntradaY3().setEditable(true);
                frmprincipal.getTxtEntradaX2().setEditable(true);
                frmprincipal.getTxtEntradaY2().setEditable(true);
                frmprincipal.getTxtEntradaZ2().setEditable(true);
                frmprincipal.getComboxxy4().setEditable(true);
                frmprincipal.getTxtEntradaY5().setEditable(true);
                frmprincipal.getTxtEntradaX5().setEditable(true);
                break;
            }
            case 5: {
                frmprincipal.getComboxxy6().setEditable(true);
                frmprincipal.getTxtEntradaX7().setEditable(true);
                frmprincipal.getTxtEntradaY7().setEditable(true);
                frmprincipal.getComboxxy5().setEditable(true);
                frmprincipal.getTxtEntradaX6().setEditable(true);
                frmprincipal.getTxtEntradaY6().setEditable(true);
                frmprincipal.getTxtEntradaY4().setEditable(true);
                frmprincipal.getComboxxy13().setEditable(true);
                frmprincipal.getTxtEntradaX4().setEditable(true);
                frmprincipal.getTxtEntradaZ3().setEditable(true);
                frmprincipal.getTxtEntradaX3().setEditable(true);
                frmprincipal.getTxtEntradaY3().setEditable(true);
                frmprincipal.getTxtEntradaX2().setEditable(true);
                frmprincipal.getTxtEntradaY2().setEditable(true);
                frmprincipal.getTxtEntradaZ2().setEditable(true);
                frmprincipal.getComboxxy4().setEditable(true);
                frmprincipal.getTxtEntradaY5().setEditable(true);
                frmprincipal.getTxtEntradaX5().setEditable(true);
                break;
            }
            case 6: {
                frmprincipal.getComboxxy7().setEditable(true);
                frmprincipal.getTxtEntradaX8().setEditable(true);
                frmprincipal.getTxtEntradaY8().setEditable(true);
                frmprincipal.getComboxxy6().setEditable(true);
                frmprincipal.getTxtEntradaX7().setEditable(true);
                frmprincipal.getTxtEntradaY7().setEditable(true);
                frmprincipal.getComboxxy5().setEditable(true);
                frmprincipal.getTxtEntradaX6().setEditable(true);
                frmprincipal.getTxtEntradaY6().setEditable(true);
                frmprincipal.getTxtEntradaY4().setEditable(true);
                frmprincipal.getComboxxy13().setEditable(true);
                frmprincipal.getTxtEntradaX4().setEditable(true);
                frmprincipal.getTxtEntradaZ3().setEditable(true);
                frmprincipal.getTxtEntradaX3().setEditable(true);
                frmprincipal.getTxtEntradaY3().setEditable(true);
                frmprincipal.getTxtEntradaX2().setEditable(true);
                frmprincipal.getTxtEntradaY2().setEditable(true);
                frmprincipal.getTxtEntradaZ2().setEditable(true);
                frmprincipal.getComboxxy4().setEditable(true);
                frmprincipal.getTxtEntradaY5().setEditable(true);
                frmprincipal.getTxtEntradaX5().setEditable(true);
                break;
            }
            case 7: {
                frmprincipal.getComboxxy8().setEditable(true);
                frmprincipal.getTxtEntradaX9().setEditable(true);
                frmprincipal.getTxtEntradaY9().setEditable(true);
                frmprincipal.getComboxxy7().setEditable(true);
                frmprincipal.getTxtEntradaX8().setEditable(true);
                frmprincipal.getTxtEntradaY8().setEditable(true);
                frmprincipal.getComboxxy6().setEditable(true);
                frmprincipal.getTxtEntradaX7().setEditable(true);
                frmprincipal.getTxtEntradaY7().setEditable(true);
                frmprincipal.getComboxxy5().setEditable(true);
                frmprincipal.getTxtEntradaX6().setEditable(true);
                frmprincipal.getTxtEntradaY6().setEditable(true);
                frmprincipal.getTxtEntradaY4().setEditable(true);
                frmprincipal.getComboxxy13().setEditable(true);
                frmprincipal.getTxtEntradaX4().setEditable(true);
                frmprincipal.getTxtEntradaZ3().setEditable(true);
                frmprincipal.getTxtEntradaX3().setEditable(true);
                frmprincipal.getTxtEntradaY3().setEditable(true);
                frmprincipal.getTxtEntradaX2().setEditable(true);
                frmprincipal.getTxtEntradaY2().setEditable(true);
                frmprincipal.getTxtEntradaZ2().setEditable(true);
                frmprincipal.getComboxxy4().setEditable(true);
                frmprincipal.getTxtEntradaY5().setEditable(true);
                frmprincipal.getTxtEntradaX5().setEditable(true);
                break;
            }
            case 8: {
                frmprincipal.getComboxxy9().setEditable(true);
                frmprincipal.getTxtEntradaX10().setEditable(true);
                frmprincipal.getTxtEntradaY10().setEditable(true);
                frmprincipal.getComboxxy8().setEditable(true);
                frmprincipal.getTxtEntradaX9().setEditable(true);
                frmprincipal.getTxtEntradaY9().setEditable(true);
                frmprincipal.getComboxxy7().setEditable(true);
                frmprincipal.getTxtEntradaX8().setEditable(true);
                frmprincipal.getTxtEntradaY8().setEditable(true);
                frmprincipal.getComboxxy6().setEditable(true);
                frmprincipal.getTxtEntradaX7().setEditable(true);
                frmprincipal.getTxtEntradaY7().setEditable(true);
                frmprincipal.getComboxxy5().setEditable(true);
                frmprincipal.getTxtEntradaX6().setEditable(true);
                frmprincipal.getTxtEntradaY6().setEditable(true);
                frmprincipal.getTxtEntradaY4().setEditable(true);
                frmprincipal.getComboxxy13().setEditable(true);
                frmprincipal.getTxtEntradaX4().setEditable(true);
                frmprincipal.getTxtEntradaZ3().setEditable(true);
                frmprincipal.getTxtEntradaX3().setEditable(true);
                frmprincipal.getTxtEntradaY3().setEditable(true);
                frmprincipal.getTxtEntradaX2().setEditable(true);
                frmprincipal.getTxtEntradaY2().setEditable(true);
                frmprincipal.getTxtEntradaZ2().setEditable(true);
                frmprincipal.getComboxxy4().setEditable(true);
                frmprincipal.getTxtEntradaY5().setEditable(true);
                frmprincipal.getTxtEntradaX5().setEditable(true);
                break;
            }
            case 9: {
                frmprincipal.getComboxxy10().setEditable(true);
                frmprincipal.getTxtEntradaX11().setEditable(true);
                frmprincipal.getTxtEntradaY11().setEditable(true);
                frmprincipal.getComboxxy9().setEditable(true);
                frmprincipal.getTxtEntradaX10().setEditable(true);
                frmprincipal.getTxtEntradaY10().setEditable(true);
                frmprincipal.getComboxxy8().setEditable(true);
                frmprincipal.getTxtEntradaX9().setEditable(true);
                frmprincipal.getTxtEntradaY9().setEditable(true);
                frmprincipal.getComboxxy7().setEditable(true);
                frmprincipal.getTxtEntradaX8().setEditable(true);
                frmprincipal.getTxtEntradaY8().setEditable(true);
                frmprincipal.getComboxxy6().setEditable(true);
                frmprincipal.getTxtEntradaX7().setEditable(true);
                frmprincipal.getTxtEntradaY7().setEditable(true);
                frmprincipal.getComboxxy5().setEditable(true);
                frmprincipal.getTxtEntradaX6().setEditable(true);
                frmprincipal.getTxtEntradaY6().setEditable(true);
                frmprincipal.getTxtEntradaY4().setEditable(true);
                frmprincipal.getComboxxy13().setEditable(true);
                frmprincipal.getTxtEntradaX4().setEditable(true);
                frmprincipal.getTxtEntradaZ3().setEditable(true);
                frmprincipal.getTxtEntradaX3().setEditable(true);
                frmprincipal.getTxtEntradaY3().setEditable(true);
                frmprincipal.getTxtEntradaX2().setEditable(true);
                frmprincipal.getTxtEntradaY2().setEditable(true);
                frmprincipal.getTxtEntradaZ2().setEditable(true);
                frmprincipal.getComboxxy4().setEditable(true);
                frmprincipal.getTxtEntradaY5().setEditable(true);
                frmprincipal.getTxtEntradaX5().setEditable(true);
                break;
            }
            case 10: {
                frmprincipal.getComboxxy11().setEditable(true);
                frmprincipal.getTxtEntradaX12().setEditable(true);
                frmprincipal.getTxtEntradaY12().setEditable(true);
                frmprincipal.getComboxxy10().setEditable(true);
                frmprincipal.getTxtEntradaX11().setEditable(true);
                frmprincipal.getTxtEntradaY11().setEditable(true);
                frmprincipal.getComboxxy9().setEditable(true);
                frmprincipal.getTxtEntradaX10().setEditable(true);
                frmprincipal.getTxtEntradaY10().setEditable(true);
                frmprincipal.getComboxxy8().setEditable(true);
                frmprincipal.getTxtEntradaX9().setEditable(true);
                frmprincipal.getTxtEntradaY9().setEditable(true);
                frmprincipal.getComboxxy7().setEditable(true);
                frmprincipal.getTxtEntradaX8().setEditable(true);
                frmprincipal.getTxtEntradaY8().setEditable(true);
                frmprincipal.getComboxxy6().setEditable(true);
                frmprincipal.getTxtEntradaX7().setEditable(true);
                frmprincipal.getTxtEntradaY7().setEditable(true);
                frmprincipal.getComboxxy5().setEditable(true);
                frmprincipal.getTxtEntradaX6().setEditable(true);
                frmprincipal.getTxtEntradaY6().setEditable(true);
                frmprincipal.getTxtEntradaY4().setEditable(true);
                frmprincipal.getComboxxy13().setEditable(true);
                frmprincipal.getTxtEntradaX4().setEditable(true);
                frmprincipal.getTxtEntradaZ3().setEditable(true);
                frmprincipal.getTxtEntradaX3().setEditable(true);
                frmprincipal.getTxtEntradaY3().setEditable(true);
                frmprincipal.getTxtEntradaX2().setEditable(true);
                frmprincipal.getTxtEntradaY2().setEditable(true);
                frmprincipal.getTxtEntradaZ2().setEditable(true);
                frmprincipal.getComboxxy4().setEditable(true);
                frmprincipal.getTxtEntradaY5().setEditable(true);
                frmprincipal.getTxtEntradaX5().setEditable(true);
                break;
            }
            case 11: {
                frmprincipal.getComboxxy12().setEditable(true);
                frmprincipal.getTxtEntradaX13().setEditable(true);
                frmprincipal.getTxtEntradaY13().setEditable(true);
                frmprincipal.getComboxxy11().setEditable(true);
                frmprincipal.getTxtEntradaX12().setEditable(true);
                frmprincipal.getTxtEntradaY12().setEditable(true);
                frmprincipal.getComboxxy10().setEditable(true);
                frmprincipal.getTxtEntradaX11().setEditable(true);
                frmprincipal.getTxtEntradaY11().setEditable(true);
                frmprincipal.getComboxxy9().setEditable(true);
                frmprincipal.getTxtEntradaX10().setEditable(true);
                frmprincipal.getTxtEntradaY10().setEditable(true);
                frmprincipal.getComboxxy8().setEditable(true);
                frmprincipal.getTxtEntradaX9().setEditable(true);
                frmprincipal.getTxtEntradaY9().setEditable(true);
                frmprincipal.getComboxxy7().setEditable(true);
                frmprincipal.getTxtEntradaX8().setEditable(true);
                frmprincipal.getTxtEntradaY8().setEditable(true);
                frmprincipal.getComboxxy6().setEditable(true);
                frmprincipal.getTxtEntradaX7().setEditable(true);
                frmprincipal.getTxtEntradaY7().setEditable(true);
                frmprincipal.getComboxxy5().setEditable(true);
                frmprincipal.getTxtEntradaX6().setEditable(true);
                frmprincipal.getTxtEntradaY6().setEditable(true);
                frmprincipal.getTxtEntradaY4().setEditable(true);
                frmprincipal.getComboxxy13().setEditable(true);
                frmprincipal.getTxtEntradaX4().setEditable(true);
                frmprincipal.getTxtEntradaZ3().setEditable(true);
                frmprincipal.getTxtEntradaX3().setEditable(true);
                frmprincipal.getTxtEntradaY3().setEditable(true);
                frmprincipal.getTxtEntradaX2().setEditable(true);
                frmprincipal.getTxtEntradaY2().setEditable(true);
                frmprincipal.getTxtEntradaZ2().setEditable(true);
                frmprincipal.getComboxxy4().setEditable(true);
                frmprincipal.getTxtEntradaY5().setEditable(true);
                frmprincipal.getTxtEntradaX5().setEditable(true);
                break;
            }
            default: {
                System.out.println("Error");
                break;
            }
        }

    }

    public void graficar(int vars) {
        double varx;
        double varz;
        double vary;
        switch (vars) {
            case 0: {
                re = new Restricciones();
                varx = Double.parseDouble(frmprincipal.getTxtEntradaX2().getText());
                vary = Double.parseDouble(frmprincipal.getTxtEntradaY2().getText());
                varz = Double.parseDouble(frmprincipal.getTxtEntradaZ2().getText());
                re.setX(varx + "");
                re.setY(vary + "");
                re.setZ(varz + "");
                if (frmprincipal.getComboxxy1().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                if (varx == 0.0) {
                    System.out.println("x");
                    vary = varz / vary;
                    varx = varz;
                    double[] x1 = {10.0, vary};
                    double[] y1 = {0.0, vary};
                    opx.add(x1);
                    opy.add(y1);
                    //Agregar datos a la Grafica      
                    plot.addScatterPlot("Datos", y1, x1);
                    //graficar en linea
                    plot.addLinePlot("Linea", y1, x1);
                } else if (vary == 0.0) {
                    System.out.println("y");
                    varx = varz / varx;
                    vary = varz;
                    double[] x1 = {varx, 10.0};
                    double[] y1 = {varx, 0.0};
                    opx.add(x1);
                    opy.add(y1);
                    //Agregar datos a la Grafica      
                    plot.addScatterPlot("Datos", y1, x1);
                    //graficar en linea
                    plot.addLinePlot("Linea", y1, x1);
                } else {

                    varx = varz / varx;
                    vary = varz / vary;
                    double[] x1 = {varx, 0.0};
                    double[] y1 = {0.0, vary};
                    opx.add(x1);
                    opy.add(y1);
                    //Agregar datos a la Grafica      
                    plot.addScatterPlot("Datos", y1, x1);
                    //graficar en linea
                    plot.addLinePlot("Linea", y1, x1);
                }
                break;
            }
            case 1: {

                double au1, au2, au3, au4;
                String a, b, c;
                a = frmprincipal.getTxtEntradaX2().getText();
                b = frmprincipal.getTxtEntradaY2().getText();
                au1 = Double.parseDouble(a);
                au2 = Double.parseDouble(b);
                c = frmprincipal.getTxtEntradaZ2().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getComboxxy1().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX3().getText();
                b = frmprincipal.getTxtEntradaY3().getText();
                au3 = Double.parseDouble(a);
                au4 = Double.parseDouble(b);
                c = frmprincipal.getTxtEntradaZ3().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getComboxxy2().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);

                break;
            }

            case 2: {
                String a, b, c;
                a = frmprincipal.getTxtEntradaX2().getText();
                b = frmprincipal.getTxtEntradaY2().getText();
                c = frmprincipal.getTxtEntradaZ2().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getComboxxy1().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX3().getText();
                b = frmprincipal.getTxtEntradaY3().getText();
                c = frmprincipal.getTxtEntradaZ3().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getComboxxy2().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX4().getText();
                b = frmprincipal.getTxtEntradaY4().getText();
                c = frmprincipal.getComboxxy13().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox5().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                break;
            }
            case 3: {
                String a, b, c;
                a = frmprincipal.getTxtEntradaX2().getText();
                b = frmprincipal.getTxtEntradaY2().getText();
                c = frmprincipal.getTxtEntradaZ2().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getComboxxy1().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX3().getText();
                b = frmprincipal.getTxtEntradaY3().getText();
                c = frmprincipal.getTxtEntradaZ3().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getComboxxy2().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX4().getText();
                b = frmprincipal.getTxtEntradaY4().getText();
                c = frmprincipal.getComboxxy13().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox5().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX5().getText();
                b = frmprincipal.getTxtEntradaY5().getText();
                c = frmprincipal.getComboxxy4().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox6().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                break;
            }
            case 4: {
                String a, b, c;
                a = frmprincipal.getTxtEntradaX2().getText();
                b = frmprincipal.getTxtEntradaY2().getText();
                c = frmprincipal.getTxtEntradaZ2().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getComboxxy1().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX3().getText();
                b = frmprincipal.getTxtEntradaY3().getText();
                c = frmprincipal.getTxtEntradaZ3().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getComboxxy2().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX4().getText();
                b = frmprincipal.getTxtEntradaY4().getText();
                c = frmprincipal.getComboxxy13().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox5().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX5().getText();
                b = frmprincipal.getTxtEntradaY5().getText();
                c = frmprincipal.getComboxxy4().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox6().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX6().getText();
                b = frmprincipal.getTxtEntradaY6().getText();
                c = frmprincipal.getComboxxy5().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox7().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                break;
            }
            case 5: {
                String a, b, c;
                a = frmprincipal.getTxtEntradaX2().getText();
                b = frmprincipal.getTxtEntradaY2().getText();
                c = frmprincipal.getTxtEntradaZ2().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getComboxxy1().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX3().getText();
                b = frmprincipal.getTxtEntradaY3().getText();
                c = frmprincipal.getTxtEntradaZ3().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getComboxxy2().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX4().getText();
                b = frmprincipal.getTxtEntradaY4().getText();
                c = frmprincipal.getComboxxy13().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox5().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX5().getText();
                b = frmprincipal.getTxtEntradaY5().getText();
                c = frmprincipal.getComboxxy4().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox6().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX6().getText();
                b = frmprincipal.getTxtEntradaY6().getText();
                c = frmprincipal.getComboxxy5().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox7().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX7().getText();
                b = frmprincipal.getTxtEntradaY7().getText();
                c = frmprincipal.getComboxxy6().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox8().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                break;
            }
            case 6: {
                String a, b, c;
                a = frmprincipal.getTxtEntradaX2().getText();
                b = frmprincipal.getTxtEntradaY2().getText();
                c = frmprincipal.getTxtEntradaZ2().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getComboxxy1().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX3().getText();
                b = frmprincipal.getTxtEntradaY3().getText();
                c = frmprincipal.getTxtEntradaZ3().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getComboxxy2().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX4().getText();
                b = frmprincipal.getTxtEntradaY4().getText();
                c = frmprincipal.getComboxxy13().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox5().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX5().getText();
                b = frmprincipal.getTxtEntradaY5().getText();
                c = frmprincipal.getComboxxy4().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox6().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX6().getText();
                b = frmprincipal.getTxtEntradaY6().getText();
                c = frmprincipal.getComboxxy5().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox7().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX7().getText();
                b = frmprincipal.getTxtEntradaY7().getText();
                c = frmprincipal.getComboxxy6().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox8().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX8().getText();
                b = frmprincipal.getTxtEntradaY8().getText();
                c = frmprincipal.getComboxxy7().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox9().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                break;
            }
            case 7: {
                String a, b, c;
                a = frmprincipal.getTxtEntradaX2().getText();
                b = frmprincipal.getTxtEntradaY2().getText();
                c = frmprincipal.getTxtEntradaZ2().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getComboxxy1().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX3().getText();
                b = frmprincipal.getTxtEntradaY3().getText();
                c = frmprincipal.getTxtEntradaZ3().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getComboxxy2().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX4().getText();
                b = frmprincipal.getTxtEntradaY4().getText();
                c = frmprincipal.getComboxxy13().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox5().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX5().getText();
                b = frmprincipal.getTxtEntradaY5().getText();
                c = frmprincipal.getComboxxy4().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox6().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX6().getText();
                b = frmprincipal.getTxtEntradaY6().getText();
                c = frmprincipal.getComboxxy5().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox7().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX7().getText();
                b = frmprincipal.getTxtEntradaY7().getText();
                c = frmprincipal.getComboxxy6().getText();
                graficas(a, b, c);
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox8().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX8().getText();
                b = frmprincipal.getTxtEntradaY8().getText();
                c = frmprincipal.getComboxxy7().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox9().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX9().getText();
                b = frmprincipal.getTxtEntradaY9().getText();
                c = frmprincipal.getComboxxy8().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox10().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                break;
            }
            case 8: {
                String a, b, c;
                a = frmprincipal.getTxtEntradaX2().getText();
                b = frmprincipal.getTxtEntradaY2().getText();
                c = frmprincipal.getTxtEntradaZ2().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getComboxxy1().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX3().getText();
                b = frmprincipal.getTxtEntradaY3().getText();
                c = frmprincipal.getTxtEntradaZ3().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getComboxxy2().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX4().getText();
                b = frmprincipal.getTxtEntradaY4().getText();
                c = frmprincipal.getComboxxy13().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox5().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX5().getText();
                b = frmprincipal.getTxtEntradaY5().getText();
                c = frmprincipal.getComboxxy4().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox6().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX6().getText();
                b = frmprincipal.getTxtEntradaY6().getText();
                c = frmprincipal.getComboxxy5().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox7().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX7().getText();
                b = frmprincipal.getTxtEntradaY7().getText();
                c = frmprincipal.getComboxxy6().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox8().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX8().getText();
                b = frmprincipal.getTxtEntradaY8().getText();
                c = frmprincipal.getComboxxy7().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox9().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX9().getText();
                b = frmprincipal.getTxtEntradaY9().getText();
                c = frmprincipal.getComboxxy8().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox10().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX10().getText();
                b = frmprincipal.getTxtEntradaY10().getText();
                c = frmprincipal.getComboxxy9().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox11().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                break;
            }
            case 9: {
                String a, b, c;
                a = frmprincipal.getTxtEntradaX2().getText();
                b = frmprincipal.getTxtEntradaY2().getText();
                c = frmprincipal.getTxtEntradaZ2().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getComboxxy1().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX3().getText();
                b = frmprincipal.getTxtEntradaY3().getText();
                c = frmprincipal.getTxtEntradaZ3().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getComboxxy2().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX4().getText();
                b = frmprincipal.getTxtEntradaY4().getText();
                c = frmprincipal.getComboxxy13().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox5().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX5().getText();
                b = frmprincipal.getTxtEntradaY5().getText();
                c = frmprincipal.getComboxxy4().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox6().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX6().getText();
                b = frmprincipal.getTxtEntradaY6().getText();
                c = frmprincipal.getComboxxy5().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox7().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX7().getText();
                b = frmprincipal.getTxtEntradaY7().getText();
                c = frmprincipal.getComboxxy6().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox8().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX8().getText();
                b = frmprincipal.getTxtEntradaY8().getText();
                c = frmprincipal.getComboxxy7().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox9().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX9().getText();
                b = frmprincipal.getTxtEntradaY9().getText();
                c = frmprincipal.getComboxxy8().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox10().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX10().getText();
                b = frmprincipal.getTxtEntradaY10().getText();
                c = frmprincipal.getComboxxy9().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox11().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX11().getText();
                b = frmprincipal.getTxtEntradaY11().getText();
                c = frmprincipal.getComboxxy10().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox12().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                break;
            }
            case 10: {
                String a, b, c;
                a = frmprincipal.getTxtEntradaX2().getText();
                b = frmprincipal.getTxtEntradaY2().getText();
                c = frmprincipal.getTxtEntradaZ2().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getComboxxy1().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX3().getText();
                b = frmprincipal.getTxtEntradaY3().getText();
                c = frmprincipal.getTxtEntradaZ3().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getComboxxy2().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX4().getText();
                b = frmprincipal.getTxtEntradaY4().getText();
                c = frmprincipal.getComboxxy13().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox5().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX5().getText();
                b = frmprincipal.getTxtEntradaY5().getText();
                c = frmprincipal.getComboxxy4().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox6().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX6().getText();
                b = frmprincipal.getTxtEntradaY6().getText();
                c = frmprincipal.getComboxxy5().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox7().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX7().getText();
                b = frmprincipal.getTxtEntradaY7().getText();
                c = frmprincipal.getComboxxy6().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox8().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX8().getText();
                b = frmprincipal.getTxtEntradaY8().getText();
                c = frmprincipal.getComboxxy7().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox9().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX9().getText();
                b = frmprincipal.getTxtEntradaY9().getText();
                c = frmprincipal.getComboxxy8().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox10().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX10().getText();
                b = frmprincipal.getTxtEntradaY10().getText();
                c = frmprincipal.getComboxxy9().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox11().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX11().getText();
                b = frmprincipal.getTxtEntradaY11().getText();
                c = frmprincipal.getComboxxy10().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox12().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX12().getText();
                b = frmprincipal.getTxtEntradaY12().getText();
                c = frmprincipal.getComboxxy11().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox14().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                break;
            }
            case 11: {
                String a, b, c;
                a = frmprincipal.getTxtEntradaX2().getText();
                b = frmprincipal.getTxtEntradaY2().getText();
                c = frmprincipal.getTxtEntradaZ2().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getComboxxy1().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX3().getText();
                b = frmprincipal.getTxtEntradaY3().getText();
                c = frmprincipal.getTxtEntradaZ3().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getComboxxy2().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX4().getText();
                b = frmprincipal.getTxtEntradaY4().getText();
                c = frmprincipal.getComboxxy13().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox5().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX5().getText();
                b = frmprincipal.getTxtEntradaY5().getText();
                c = frmprincipal.getComboxxy4().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox6().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX6().getText();
                b = frmprincipal.getTxtEntradaY6().getText();
                c = frmprincipal.getComboxxy5().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox7().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX7().getText();
                b = frmprincipal.getTxtEntradaY7().getText();
                c = frmprincipal.getComboxxy6().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox8().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX8().getText();
                b = frmprincipal.getTxtEntradaY8().getText();
                c = frmprincipal.getComboxxy7().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox9().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX9().getText();
                b = frmprincipal.getTxtEntradaY9().getText();
                c = frmprincipal.getComboxxy8().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox10().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX10().getText();
                b = frmprincipal.getTxtEntradaY10().getText();
                c = frmprincipal.getComboxxy9().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox11().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX11().getText();
                b = frmprincipal.getTxtEntradaY11().getText();
                c = frmprincipal.getComboxxy10().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox12().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX12().getText();
                b = frmprincipal.getTxtEntradaY12().getText();
                c = frmprincipal.getComboxxy11().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox14().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                //////////////////////////////////////////////
                a = frmprincipal.getTxtEntradaX13().getText();
                b = frmprincipal.getTxtEntradaY13().getText();
                c = frmprincipal.getComboxxy12().getText();
                re = new Restricciones();
                re.setX(a);
                re.setY(b);
                re.setZ(c);
                if (frmprincipal.getjComboBox15().getSelectedIndex() == 0) {
                    re.setSigno("≥");
                } else {
                    re.setSigno("≤");
                }
                res.add(re);
                graficas(a, b, c);
                break;
            }
            default: {
                System.out.println("Error");
                break;
            }

        }
    }
}
