/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

import java.awt.Toolkit;
import java.net.URL;

/**
 *
 * @author ppesq
 */
public class Malo extends Base {

    //Variable de clase contador
    private static int cont = -1;

    //Variable con el lado por el que va a entrar (izquierda 1 o derecha 2)
    private int lado;
    private final int DEFAULT_LADO = 1;

    //Control de movimiento
    private int speed;
    private final int DEFAULT_SPEED = 10;
    private int direccion;

    //Control de colisiones
    private boolean inCollision;
    private int collisionCycles;
    private final int defaultCollisionCycles = 10;

    //URLs de cuadros de las animaciones
    private URL[] umbrellaURLs = {
        this.getClass().getResource("images/umbrella01_01.png"),
        this.getClass().getResource("images/umbrella02_02.png"),
        this.getClass().getResource("images/umbrella03_03.png"),
        this.getClass().getResource("images/umbrella04_04.png"),
        this.getClass().getResource("images/umbrella05_05.png"),
        this.getClass().getResource("images/umbrella06_06.png")
    };
    private URL[] umbrellaCollisionURLs = {
        this.getClass().getResource("images/umbrellaCollision01_01.png"),
        this.getClass().getResource("images/umbrellaCollision02_02.png")
    };

    /**
     * Default constructor that loads images from the arrays mentioned above.
     */
    public Malo() {
        if (cont < 0) {
            cont = 0;
        } else {
            //modifico el contador? 
        }
        Animacion main = new Animacion();
        Animacion collision = new Animacion();
        lado = DEFAULT_LADO;
        inCollision = false;
        collisionCycles = -1;
        /*
         Agrega todos los cuadros a la animacion main con 100ms de duracion
         */
        for (URL umbrella : umbrellaURLs) {
            main.sumaCuadro(Toolkit.getDefaultToolkit().getImage(umbrella), 100);
        }
        /*
         Agrega todos los cuadros a la animacion collision con 100ms de duracion
         */
        for (URL coll : umbrellaCollisionURLs) {
            collision.sumaCuadro(Toolkit.getDefaultToolkit().getImage(coll), 100);
        }

        //Agrega las animaciones creadas al personaje en su clase Base. 
        setAnimacionBasica(main);
        setAnimacionColision(collision);

        //direccion inicial del malo
        setCorriendoAnimacionBasica(true);

    }

    public Malo(int posX, int posY, Animacion animacionBasica) {
        super(posX, posY, animacionBasica);
    }

    public Malo(int posX, int posY, Animacion animacionCaminarIzquierda, Animacion animacionCaminarDerecha) {
        super(posX, posY, animacionCaminarIzquierda, animacionCaminarDerecha);
    }

    /* COMPORTAMIENTOS */
    /**
     * Metodo collide que actualiza la posicion del paraguas y
     *
     */
    public void collide(int appletWidth) {
        randomReset(appletWidth);
    }

    /**
     * Metodo collide que pone al objeto en estado de colision.
     *
     */
    public void collideSides() {
//        setColisionando(true);
//        setCorriendoAnimacionBasica(false);
        setCollisionCycles(defaultCollisionCycles);
        inCollision = true;
    }

    public void decreaseCollisionCounter() {
        setCollisionCycles(getCollisionCycles() - 1);
    }

    /**
     * Método fall
     *
     * Modifica la posición del objeto malo, aumentando su posición en Y para
     * que caiga.
     */
    public void fall() {
        setPosY(getPosY() + speed);
    }

    /**
     * Método move
     *
     * Modifica la posición del objeto malo, aumentando su posición en X para
     * que caiga.
     */
    public void move() {
        switch (lado) {
            case 1:
                setPosX(getPosX() + speed);
                break;
            case 2:
                setPosX(getPosX() - speed);
                break;
        }
    }

    /**
     * Metodo setRandomSpeed
     *
     * Cambia la velocidad del objeto a un numero aleatorio entre los parametros
     * enviados.
     *
     * @param lower velocidad minima de tipo <code>int</code>
     * @param upper velocidad maxima de tipo <code>int</code>
     */
    public void setRandomSpeed(int lower, int upper) {
        int R = (int) (Math.random() * (upper - lower)) + lower;
        setSpeed(R);
    }

    /**
     * Metodo randomReset Resetea la posicion del objeto afuera del applet en
     * todo X
     *
     * @param appletWidth
     */
    public void randomReset(int appletWidth) {
        //formula random
        //Math.random() * (upper - lower)) + lower

        //posiciona al objeto en su mitad
        if (getLado() == 1) {
            setPosX((int) (Math.random() * (appletWidth / 2 - 1)));
        } else {
            setPosX((int) (Math.random() * (appletWidth - appletWidth / 2) + appletWidth / 2));
        }
        //corrige la posicion si se paso
        if (getPosX() > appletWidth - getAncho()) {
            //correct displacement out of screen
            setPosX(appletWidth - getAncho());
        }
        setPosY((int) (Math.random() * -200));
    }

    /**
     * Metodo randomReset Resetea la posicion del objeto afuera del applet en
     * todo Y
     *
     * @param appletWidth
     */
    public void randomResetSide(int appletHeight, int appletWidth) {
        //formula random
        //Math.random() * (upper - lower)) + lower

        //posiciona al objeto en su mitad
        if (getLado() == 1) {
//            setPosY((int) (Math.random() * (appletHeight - 15)) + 15);
            setPosX((int) (Math.random() * (0 - appletWidth / 4 - appletWidth / 4)));
        } else {
//            setPosY((int) (Math.random() * (appletHeight - appletHeight / 2) + appletHeight / 2));
            setPosX((int) (Math.random() * (appletWidth + appletWidth / 4 - appletWidth) + appletWidth));
        }
        
        //el +15 es porque ahora el limite inferior en y no es 0 por la barra que agrega el JFrame a la aplicación.
        setPosY((int) (Math.random() * (appletHeight - 15)) + 15);

        //corrige la posicion si se paso
        if (getPosY() > appletHeight - getAlto()) {
            //correct displacement out of screen
            setPosY(appletHeight - getAlto());
        }

        

//        setColisionando(false);
//        setCorriendoAnimacionBasica(true);
        setCollisionCycles(-1);
        setInCollision(false);
    }

    /* COMPORTAMIENTOS */
    /* SETTERS Y GETTERS */
    public void setInCollision(boolean b) {
        this.inCollision = b;
    }

    public boolean isInCollision() {
        return this.inCollision;
    }

    /**
     * Metodo de modificacion setCont
     *
     * que modifica el valor de la variable lado
     *
     * @param c variable de tipo <code>int</code> con nuevo score
     */
    public void setCont(int c) {
        this.cont = c;
    }

    /**
     * Metodo de acceso getCont
     *
     * que regresa
     *
     * @return variable de tipo <code>int</code> llamada cont con score
     */
    public int getCont() {
        return cont;
    }

    public int getCollisionCycles() {
        return collisionCycles;
    }

    public void setCollisionCycles(int collisionCycles) {
        this.collisionCycles = collisionCycles;
    }

    /**
     * Metodo de modificacion setLado
     *
     * que modifica el valor de la variable lado
     *
     * @param lado variable de tipo <code>int</code> que puede ser o por la
     * mitad izquierda (1) o por la mitad derecha (2)
     */
    public void setLado(int lado) {
        this.lado = lado;
    }

    /**
     * Metodo de acceso getLado
     *
     * que regresa
     *
     * @return variable de tipo <code>int</code> llamada lado con lugar por
     * donde sale
     */
    public int getLado() {
        return lado;
    }

    /**
     * Metodo getSpeed regresa la velocidad del objeto
     *
     * @return speed de tipo <code>int</code>
     */
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Metodo getUmbrellaURLS regresa URLs de imágenes animación básica de
     * umbrella.
     *
     * @return umbrellaURLs <code>URL[]</code>
     */
    public URL[] getUmbrellaURLs() {
        return umbrellaURLs;
    }

    public void setUmbrellaURLs(URL[] umbrellaURLs) {
        this.umbrellaURLs = umbrellaURLs;
    }

    /**
     * Metodo getUmbrellaCOllisionURLs Regresa el arrgeglo con URLs de imagenes
     * de colisión
     *
     * @return umbrellaCollisionURLs tipo <code>URL[]</code>
     */
    public URL[] getUmbrellaCollisionURLs() {
        return umbrellaCollisionURLs;
    }

    public void setUmbrellaCollisionURLs(URL[] umbrellaCollisionURLs) {
        this.umbrellaCollisionURLs = umbrellaCollisionURLs;
    }
    /* SETTERS Y GETTERS */

}
