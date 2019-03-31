package invasion_extraterrestre;

public class Alien extends Graficos {
	
	 
    //La clase juego controla a todo el juego y las teclas con las que se mueve la nave
    Juego juego;
	
        public Alien(Juego juego, String imagen, int columna, int fila){
		
            super(imagen, columna, fila);
            this.juego = juego;
            desplazamiento_columna = -75;
	}


        @Override
	public void mover(long valor) {
		
            if ((desplazamiento_columna < 0) && (columna < 10)){
              juego.alcanzado_limite = true;
            }

            if ((desplazamiento_columna > 0) && (columna > 750)){
              juego.alcanzado_limite = true;
            }

            super.mover(valor);
	}
	
	@Override
	public void avanzar_aliens(){
            
            desplazamiento_columna = - desplazamiento_columna;
            fila += 10;
            if (fila > 570){ //Si los aliens llegan a la base se termina el juego
              juego.notificar_perdedor();
            }
	}
	
	@Override
	public void colisiona_con(Graficos grafico) {
		
	}
}