package invasion_extraterrestre;

public class Meteoro extends Graficos{
    
    Juego juego;
    
    public Meteoro(Juego juego, String imagen, int columna, int fila){
		
            super(imagen, columna, fila);
            this.juego = juego;
	}
    
    @Override
    public void colisiona_con(Graficos grafico) {
		
    }
}
