package juego;

import processing.core.PApplet;
import processing.core.PImage;

public class GameOfKnight extends PApplet {

    // --- IMÁGENES ---
    PImage imgFondo, imgHeroe1, imgHeroe21, imgHeroe22, imgHeroe23, imgHeroe24,
            imgHeroe31, imgHeroe32, imgHeroe33, imgHeroe34, imgEnemigo,
            imgHeroe41, imgHeroe42, imgHeroe43, imgHeroe44, imgHeroe51, imgHeroe52,
            imgHeroe53, imgHeroe54, imgTesoro, imgHeroe2;

    // --- POSICIONES ---
    float xHeroe = 100, yHeroe = 420;
    float xEnemigo = 700, yEnemigo = 420;
    float xTesoro = 700, yTesoro = 450;

    int frameCorrer = 0;

    float velocidadY = 0;
    float gravedad = 2.5f;
    float fuerzaSalto = -20;
    boolean enSuelo = false;
    

    // --- MOVIMIENTO SUAVE (Interruptores) --
    boolean arriba, abajo, izquierda, derecha, space, muereenemigo;
    float velocidad = 10;
    boolean ultimoderecha=true;
    // --- ESTADO DEL JUEGO ---
    // 0 = Jugando, 1 = Ganaste, 2 = Perdiste
    int estadoJuego = 0;

    public static void main(String[] args) {
        PApplet.main("juego.GameOfKnight");
    }

    @Override
    public void settings() {
        size(800, 600);
    }

    @Override
    public void setup() {
        frameRate(30); // fotogramas por segundo
        PImage hojaCompleta = loadImage("./imagenes/knight.png");
        PImage hojaCompleta3 = loadImage("./imagenes/knight3.png");

        int w = hojaCompleta.width / 4;
        int h = hojaCompleta.height / 4;

        int w3 = hojaCompleta3.width / 4;
        int h3 = hojaCompleta3.height / 4;

        imgHeroe1 = hojaCompleta.get(w * 3, 0, w, h);
        imgHeroe2 = hojaCompleta3.get(0, 0, w3, h3);// Primera fila, primera columna

        // Segunda fila (Correr): Y siempre es 'h'
        imgHeroe21 = hojaCompleta.get(0, h, w, h);
        imgHeroe22 = hojaCompleta.get(w, h, w, h);
        imgHeroe23 = hojaCompleta.get(w * 2, h, w, h);
        imgHeroe24 = hojaCompleta.get(w * 3, h, w, h);

        imgHeroe31 = hojaCompleta3.get(w3 * 3, h3, w3, h3);
        imgHeroe32 = hojaCompleta3.get(w3 * 2, h3, w3, h3);
        imgHeroe33 = hojaCompleta3.get(w3, h3, w3, h3);
        imgHeroe34 = hojaCompleta3.get(0, h3, w3, h3);

        imgHeroe41 = hojaCompleta.get(0, 2 * h, w, h);
        imgHeroe42 = hojaCompleta.get(w, 2 * h, w, h);
        imgHeroe43 = hojaCompleta.get(w * 2, 2 * h, w, h);
        imgHeroe44 = hojaCompleta.get(w * 3, 2 * h, w, h);

        imgHeroe51 = hojaCompleta3.get(w3 * 3, 2 * h3, w3, h3);
        imgHeroe52 = hojaCompleta3.get(w3 * 2, 2 * h3, w3, h3);
        imgHeroe53 = hojaCompleta3.get(w3, 2 * h3, w3, h3);
        imgHeroe54 = hojaCompleta3.get(0, 2 * h3, w3, h3);

        // Carga las imágenes (recuerda ponerlas en la carpeta del proyecto)
        imgFondo = loadImage("./imagenes/fondo3.jpg");
        imgEnemigo = loadImage("./imagenes/fantasma.png");
        imgTesoro = loadImage("./imagenes/cofre.png");

        // Redimensionar para que encajen
        imgFondo.resize(width, height);
        imgHeroe1.resize(70, 70);
        imgHeroe21.resize(70, 70);
        imgHeroe22.resize(70, 70);
        imgHeroe23.resize(70, 70);
        imgHeroe24.resize(70, 70);
        imgHeroe31.resize(70, 70);
        imgHeroe32.resize(70, 70);
        imgHeroe33.resize(70, 70);
        imgHeroe34.resize(70, 70);
        imgHeroe41.resize(70, 70);
        imgHeroe42.resize(70, 70);
        imgHeroe43.resize(70, 70);
        imgHeroe44.resize(70, 70);
        imgHeroe51.resize(70, 70);
        imgHeroe52.resize(70, 70);
        imgHeroe53.resize(70, 70);
        imgHeroe54.resize(70, 70);
        imgEnemigo.resize(70, 70);
        imgTesoro.resize(40, 40);
    }

    @Override
    public void draw() {

        if (estadoJuego == 0) {
            dibujarEscenario();
            moverPersonaje();
            moverEnemigoIA();
            verificarColisiones();
            dibujarSprites();
        } else {
            mostrarPantallaFinal();
        }

    }

    void dibujarEscenario() {
        image(imgFondo, 0, 0);
    }

    // --- 2. GESTIONA FLAGS Y COORDENADAS ---
    void moverPersonaje() {
        // --- Horizontal Movement ---
        if (izquierda) {
            xHeroe -= velocidad;
            ultimoderecha = false;
        }
        if (derecha) {
            xHeroe += velocidad;
            ultimoderecha = true;
        }

        // --- Gravity Logic ---
        velocidadY += gravedad; 
        yHeroe += velocidadY;   

        // --- Floor Collision (Simulated Ground) ---
        float sueloY = height - 190; 

        if (yHeroe > sueloY) {
            yHeroe = sueloY;    
            velocidadY = 0;    
            enSuelo = true;     
        } else {
            enSuelo = false;  
        }

        // Keep within screen horizontally
        xHeroe = constrain(xHeroe, 0, width - 70);
    }

    // --- 3. LÓGICA DE PERSECUCIÓN ---
    void moverEnemigoIA() {
        float velEnemigo = 2.5f;
        if (xHeroe > xEnemigo) {
            xEnemigo += velEnemigo;
        }
        if (xHeroe < xEnemigo) {
            xEnemigo -= velEnemigo;
        }
        if (yHeroe > yEnemigo) {
            yEnemigo += velEnemigo;
        }

    }

    // --- 4. DETECTAR CHOQUES ---
    void verificarColisiones() {
        // Colisión con Tesoro
        if (dist(xHeroe, yHeroe, xTesoro, yTesoro) < 55) {
            estadoJuego = 1;
        }

        // Colisión con Enemigo
        float distanciaAlEnemigo = dist(xHeroe, yHeroe, xEnemigo, yEnemigo);

        if (distanciaAlEnemigo < 50) { 
            if (space) {
                
                xEnemigo = -1000;
                yEnemigo = -1000;
            } else if (distanciaAlEnemigo < 30) {
               
                estadoJuego = 2;
            }
        }
    }

    // --- 5. PINTA LAS IMÁGENES ---
    void dibujarSprites() {
        image(imgTesoro, xTesoro, yTesoro);
        image(imgEnemigo, xEnemigo, yEnemigo);

        if (!derecha && !izquierda && space) {
            if (ultimoderecha) {
                if (frameCount % 1 == 0) {
                    frameCorrer = (frameCorrer + 17) % 4; 
                }

                switch (frameCorrer) {
                    case 0:
                        image(imgHeroe41, xHeroe, yHeroe);
                        break;
                    case 1:
                        image(imgHeroe42, xHeroe, yHeroe);
                        break;
                    case 2:
                        image(imgHeroe43, xHeroe, yHeroe);
                        break;
                    case 3:
                        image(imgHeroe44, xHeroe, yHeroe);
                        break;
                    default:
                        break;
                }
            } else {
                if (frameCount % 1 == 0) {
                    frameCorrer = (frameCorrer + 17) % 4; 
                }

              
                switch (frameCorrer) {
                    case 0:
                        image(imgHeroe51, xHeroe, yHeroe);
                        break;
                    case 1:
                        image(imgHeroe52, xHeroe, yHeroe);
                        break;
                    case 2:
                        image(imgHeroe53, xHeroe, yHeroe);
                        break;
                    case 3:
                        image(imgHeroe54, xHeroe, yHeroe);
                        break;
                    default:
                        break;
                }
            }

        } else if (derecha) {
            if (space) {
                if (frameCount % 1 == 0) {
                    frameCorrer = (frameCorrer + 17) % 4; // Ciclo: 0, 1, 2, 3, 0...
                }

                
                switch (frameCorrer) {
                    case 0:
                        image(imgHeroe41, xHeroe, yHeroe);
                        break;
                    case 1:
                        image(imgHeroe42, xHeroe, yHeroe);
                        break;
                    case 2:
                        image(imgHeroe43, xHeroe, yHeroe);
                        break;
                    case 3:
                        image(imgHeroe44, xHeroe, yHeroe);
                        break;
                    default:
                        break;
                }
            } else {
             
                if (frameCount % 1 == 0) {
                    frameCorrer = (frameCorrer + 17) % 4; // Ciclo: 0, 1, 2, 3, 0...
                }

                // Seleccionamos la imagen según el frameCorrer
                switch (frameCorrer) {
                    case 0:
                        image(imgHeroe21, xHeroe, yHeroe);
                        break;
                    case 1:
                        image(imgHeroe22, xHeroe, yHeroe);
                        break;
                    case 2:
                        image(imgHeroe23, xHeroe, yHeroe);
                        break;
                    case 3:
                        image(imgHeroe24, xHeroe, yHeroe);
                        break;
                    default:
                        break;
                }
            }

        } else if (izquierda) {
            if (space) {
                if (frameCount % 1 == 0) {
                    frameCorrer = (frameCorrer + 1) % 4; // Ciclo: 0, 1, 2, 3, 0...
                }

                
                switch (frameCorrer) {
                    case 0:
                        image(imgHeroe51, xHeroe, yHeroe);
                        break;
                    case 1:
                        image(imgHeroe52, xHeroe, yHeroe);
                        break;
                    case 2:
                        image(imgHeroe53, xHeroe, yHeroe);
                        break;
                    case 3:
                        image(imgHeroe54, xHeroe, yHeroe);
                        break;
                    default:
                        break;
                }
            } else {
                
                if (frameCount % 1 == 0) {
                    frameCorrer = (frameCorrer + 17) % 4; // Ciclo: 0, 1, 2, 3, 0...
                }

                // Seleccionamos la imagen según el frameCorrer
                switch (frameCorrer) {
                    case 0:
                        image(imgHeroe31, xHeroe, yHeroe);
                        break;
                    case 1:
                        image(imgHeroe32, xHeroe, yHeroe);
                        break;
                    case 2:
                        image(imgHeroe33, xHeroe, yHeroe);
                        break;
                    case 3:
                        image(imgHeroe34, xHeroe, yHeroe);
                        break;
                    default:
                        break;
                }
            }

        } else {
            // Si está quieto, dibujamos el sprite de reposo
            if (ultimoderecha) {
                image(imgHeroe1, xHeroe, yHeroe);
            } else {
                image(imgHeroe2, xHeroe, yHeroe);
            }
        }
    }

    // --- 6. MENSAJES DE GANAR/PERDER ---
    void mostrarPantallaFinal() {
        textAlign(CENTER);
        if (estadoJuego == 1) {
            background(0, 200, 0);
            fill(255);
            textSize(50);
            text("¡HAS GANADO!", width / 2, height / 2);
        } else if (estadoJuego == 2) {
            background(200, 0, 0);
            fill(255);
            textSize(50);
            text("¡GAME OVER!", width / 2, height / 2);
        }
        textSize(20);
        text("Pulsa R para reiniciar", width / 2, height / 2 + 60);
    }

    // --- CONTROL DE TECLADO (EVENTOS) ---
    // Esto se ejecuta AUTOMÁTICAMENTE cuando pulsas una tecla
    @Override
    public void keyPressed() {
        if ((keyCode == UP || key == 'w') && enSuelo) {
            velocidadY = fuerzaSalto; // Give an upward "kick"
            enSuelo = false;
        }
        if (keyCode == DOWN || key == 's') {
            abajo = true;
        }
        if (keyCode == LEFT || key == 'a') {
            izquierda = true;
        }
        if (keyCode == RIGHT || key == 'd') {
            derecha = true;
        }
        if (key == ' ') {
            space = true;
        }

        // Reiniciar juego con 'r'
        if (key == 'r' || key == 'R') {
            xHeroe = 100;
            yHeroe = 350;
            xEnemigo = 700;
            yEnemigo = 420;
            estadoJuego = 0;
        }
    }

    // Esto se ejecuta AUTOMÁTICAMENTE cuando sueltas una tecla
    @Override
    public void keyReleased() {
        if (keyCode == UP || key == 'w') {
            arriba = false;
        }
        if (keyCode == DOWN || key == 's') {
            abajo = false;
        }
        if (keyCode == LEFT || key == 'a') {
            izquierda = false;
        }
        if (keyCode == RIGHT || key == 'd') {
            derecha = false;
        }
        if (key == ' ') {
            space = false;
        }
    }
}
