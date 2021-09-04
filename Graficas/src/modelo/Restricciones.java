/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Gina
 */
public class Restricciones {
    String x, y, z;
    String signo;

    public String getSigno() {
        return signo;
    }

    public void setSigno(String signo) {
        this.signo = signo;
    }
    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }

    public Restricciones(String x, String y, String z, String signo) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.signo = signo;
    }
    public Restricciones() {
        x = "";
        y = "";
        z = "";
        signo = "";
    }
        public Object [] datos() {
            String fobj = "";
            if(x.equals("1")){
                
                fobj = "X+"+y+"Y "+signo+" "+z;
            }
            else if(y.equals("1")){
                 fobj = x+"X+"+"Y "+signo+" "+z;
            }
            else if(x.equals("1")&&((y.equals("1")))){
                fobj = "X+"+"Y "+signo+" "+z;
            }else{
                fobj = x+"X+"+y+"Y "+signo+" "+z;
            }
            
        return new Object[]{
                fobj
        };
    }
    public Object [] datos1(int i){  
        return new Object[]{
            ("Variable"+i),getX(),getY(),getZ()
        };
    }  
}
