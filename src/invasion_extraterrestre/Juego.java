package invasion_extraterrestre;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Juego extends Canvas{
	
    BufferStrategy buffer;
    ArrayList lista_imagenes = new ArrayList();
    ArrayList lista_eliminados = new ArrayList();
    Graficos nave;
    double desplazamiento = 400;
    long tiempo_ultimo_disparo = 0;
    long intervalo_disparo = 300;
    int num_aliens;
    String mensaje = "";
    boolean juego_iniciado = true;
    boolean tecla_no_pulsada = true;
    boolean flecha_izquierda_pulsada = false;
    boolean flecha_derecha_pulsada = false;
    boolean disparado = false;
    boolean alcanzado_limite = false;
    int nivel = 0;

	public Juego(){
            
            JFrame ventana = new JFrame();
            ventana.getContentPane().setPreferredSize(new Dimension(800,600));
            ventana.getContentPane().add(this);
            setIgnoreRepaint(true);
            ventana.pack();
            ventana.setResizable(false);
            ventana.setVisible(true);
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            addKeyListener(new Control_tecla_pulsada());
            requestFocus();
            createBufferStrategy(2);
            buffer = getBufferStrategy();
            añadir_imagenes();
	}
	
	void iniciar_juego() {
            
            lista_imagenes.clear();
            añadir_imagenes();
            flecha_izquierda_pulsada = false;
            flecha_derecha_pulsada = false;
            disparado = false;
	}
	
	void añadir_imagenes() {
		
           
            Graficos meteoro = new Meteoro(this, "imagenes/meteorBig.png", 120, 200);
            lista_imagenes.add(meteoro);
            num_aliens = 0;
            
            Graficos meteorito = new Meteoro(this, "imagenes/meteorSmall.png", 520, 300);
            lista_imagenes.add(meteorito);
            num_aliens = 0;
            
                        
            Graficos nebula = new Meteoro(this, "imagenes/nebula.png", 500, 50);
            lista_imagenes.add(nebula);
            num_aliens = 0;
            
            Graficos star = new Meteoro(this, "imagenes/starBig.png", 90, 200);
            lista_imagenes.add(star);
            num_aliens = 0;
            
            Graficos star1 = new Meteoro(this, "imagenes/starBig.png", 200, 90);
            lista_imagenes.add(star1);
            num_aliens = 0;
            
            
            nave = new Nave_espacial(this, "imagenes/nave.png", 370, 550);
            lista_imagenes.add(nave);
            num_aliens = 0;

                
            if (nivel == 0 || nivel == 1){
                for (int i=0; i<5; i++){
                    for (int j=0; j<12; j++){
                        Graficos alien = new Alien(this, "imagenes/alien.png", 100 + (j*50), (50) + i * 30);
                        lista_imagenes.add(alien);
                        num_aliens ++;
                    }
                }
            } else if (nivel == 2){
                for(int i = 0; i < 5; i ++){
                    for(int j = 0; j < 8; j ++){
                        Graficos boss = new Alien(this, "imagenes/enemyUFO.png", 500, 30);
                        //Graficos boss1 = new Alien(this, "imagenes/enemyUFO.png", 200, 30);
                        lista_imagenes.add(boss);
                        //lista_imagenes.add(boss1);
                        num_aliens ++; 
                    }
                }
            }
	}
	
	public void notificar_perdedor(){
            
            mensaje = "HAS PERDIDO INSECTO";
            tecla_no_pulsada = true;
	}
	
	public void notificar_ganador(){
            mensaje = "Bien hecho, Woody";    
            tecla_no_pulsada = true;
	}
	
	public void descontar_eliminados() {
		num_aliens --;
		
		if (num_aliens == 0){
                    
                    //notificar_ganador();
                    subir_nivel();
                    
		}
		
		for (int i=0; i<lista_imagenes.size(); i++){
                    
                    Graficos grafico = (Graficos) lista_imagenes.get(i);
		
                    if (grafico instanceof Alien) {
				
                        grafico.desplazamiento_columna = grafico.desplazamiento_columna * 1.02;
                    }
		}
	}
        
        public void subir_nivel(){
            nivel ++;
            iniciar_juego();
            if (nivel == 3){
                notificar_ganador();
            }
            
        }
	
	public void añadir_misil(){
		
            if (System.currentTimeMillis() - tiempo_ultimo_disparo < intervalo_disparo){
             return;
            }

            tiempo_ultimo_disparo = System.currentTimeMillis();

            Misil misil = new Misil(this,"imagenes/misil.png",((int)nave.columna) +10,((int)nave.fila)-30);

            lista_imagenes.add(misil);

            }
	
	public void controlar_juego(){
            
            long ultimo_tiempo = System.currentTimeMillis();
		
		while (juego_iniciado) {
			
                    long valor = System.currentTimeMillis() - ultimo_tiempo;
                        
                    ultimo_tiempo = System.currentTimeMillis();
			
                    Graphics2D G2D = (Graphics2D) buffer.getDrawGraphics();
                        
		    G2D.setColor(Color.black);
                        
                    G2D.fillRect(0,0,800,600);
                        
                    if (!tecla_no_pulsada){
			for (int i=0;i<lista_imagenes.size();i++){
                            
                            Graficos grafico = (Graficos) lista_imagenes.get(i);		
                            grafico.mover(valor);
			}
                    }
			
		    for (int i=0;i<lista_imagenes.size();i++){
                        
                        Graficos grafico = (Graficos) lista_imagenes.get(i);	
			grafico.dibujar_graficos(G2D);
                    }
			
		    for (int k=0; k<lista_imagenes.size(); k++){
			for (int s=k+1; s<lista_imagenes.size(); s++){
                            
                            Graficos grafico1 = (Graficos) lista_imagenes.get(k);           
                            Graficos grafico2 = (Graficos) lista_imagenes.get(s);
					
                            if (grafico1.choca(grafico2)){
				
                                grafico1.colisiona_con(grafico2);                
				grafico2.colisiona_con(grafico1);
                            }
			}
                    }
			
                    lista_imagenes.removeAll(lista_eliminados);
                        
                    lista_eliminados.clear();

			if (alcanzado_limite){
                            for (int i=0;i<lista_imagenes.size();i++){
                                
                                Graficos grafico = (Graficos) lista_imagenes.get(i);
                                grafico.avanzar_aliens();
                            }
				
                            alcanzado_limite = false;
			}
			
			if (tecla_no_pulsada){
                            
                            G2D.setColor(Color.white);    
                            G2D.drawString(mensaje, (800-G2D.getFontMetrics().stringWidth(mensaje))/2, 250);
                            G2D.drawString("Pulse una tecla para continuar", 300, 400);
			}
			
			G2D.dispose();
                        
			buffer.show();
			
			nave.desplazamiento_columna = 0;
			
			if (flecha_izquierda_pulsada){
                            
                            nave.desplazamiento_columna = -desplazamiento;
                                
			} else if (flecha_derecha_pulsada){
                            
                            nave.desplazamiento_columna = desplazamiento;
                        }
			
			if(disparado){
                            
                            añadir_misil();
			}
			
			try { Thread.sleep(10); } catch (Exception e) {}
		}
	}
	
	
	class Control_tecla_pulsada extends KeyAdapter{
		
		int pulsaciones = 1;
		
		public void keyPressed(KeyEvent e){
			
			if (tecla_no_pulsada){
                            return;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_LEFT){
                            flecha_izquierda_pulsada = true;
			}
                        
			if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                            flecha_derecha_pulsada = true;
			}
                        
			if (e.getKeyCode() == KeyEvent.VK_SPACE){
                            disparado= true;
			}
		} 
		
		public void keyReleased(KeyEvent e){
			
			if (tecla_no_pulsada){
                            return;
			}
                        
		        if (e.getKeyCode() == KeyEvent.VK_LEFT){
                            flecha_izquierda_pulsada = false;
			}
                        
			if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                            flecha_derecha_pulsada = false;
			}
                        
			if (e.getKeyCode() == KeyEvent.VK_SPACE){
                            disparado = false;
			}
		}

		public void keyTyped(KeyEvent e) {
			
			if (tecla_no_pulsada){
                            
                            if (pulsaciones == 1){
                                
                                tecla_no_pulsada = false;
                                iniciar_juego();
                                pulsaciones = 0;
                                   
                            } else {
                                pulsaciones++;
                            }
			}
			
	}
}
	
    public static void main(String argv[]){
        new Juego().controlar_juego();
    }
}
