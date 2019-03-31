package invasion_extraterrestre;

public class Misil extends Graficos {
    
    double desplazamiento = -300;
    Juego juego;
    boolean misil_disparado = false;
	
    public Misil(Juego juego,String imagen,int x,int y){
            
        super(imagen,x,y);
        this.juego = juego;
        desplazamiento_fila = desplazamiento;
    }
    
    @Override
    public void mover(long valor){
        super.mover(valor);
    }

    @Override
    public void colisiona_con(Graficos grafico) {

        if (misil_disparado){
           return;
        }

        if (grafico instanceof Alien){

            juego.lista_eliminados.add(grafico);
            juego.descontar_eliminados();
            misil_disparado = true;
        }
    }
}