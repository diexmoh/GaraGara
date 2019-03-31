package invasion_extraterrestre;

public class Nave_espacial extends Graficos{
    
    Juego juego;
		
    public Nave_espacial(Juego juego, String imagen, int x, int y){
    
        super(imagen,x,y);	
	this.juego = juego;
    
    }
	
	
    public void mover(long valor){

        if ((desplazamiento_columna < 0) && (columna< 10)){
            return;
        }

        if ((desplazamiento_columna > 0) && (columna> 750)){
            return;
        }

            super.mover(valor);
    }
	
	
	public void colisiona_con(Graficos grafico) {
		
		if (grafico instanceof Alien) 
                {
			juego.notificar_perdedor();
		}
	}
}