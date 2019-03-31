package invasion_extraterrestre;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Graficos {
	
    Dibujo_imagen dibujo_imagen;
    double columna;
    double fila;
    double desplazamiento_columna;
    double desplazamiento_fila;

    //Sirven para saber si hay una colision
    Rectangle grafico1 = new Rectangle();
    Rectangle grafico2 = new Rectangle();

    public Graficos(String imagen,int columna,int fila) {

        this.dibujo_imagen = Configuracion_imagen.configuracion.transparentar_imagen(imagen);
        this.columna = columna;
        this.fila = fila;
     }
	
    public void mover(long valor){
        columna += (valor * desplazamiento_columna) / 1000;
        fila += (valor * desplazamiento_fila) / 1000;
    }
	
    public void dibujar_graficos (Graphics grafico){
        
        dibujo_imagen.dibujar(grafico,(int)columna,(int) fila);
    }
	
    public void avanzar_aliens() {}
	
    public boolean choca(Graficos imagen){
        
        grafico1.setBounds((int)columna,(int)fila,dibujo_imagen.getWidth(),dibujo_imagen.getHeight());
        grafico2.setBounds((int)imagen.columna,(int)imagen.fila,imagen.dibujo_imagen.getWidth(),imagen.dibujo_imagen.getHeight());
        return grafico1.intersects(grafico2);
    }
	
    public abstract void colisiona_con(Graficos imagen);
}	