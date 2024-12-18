package modelo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Random;

@SuppressWarnings("deprecation")

public class TableroModelo extends Observable {

    private Integer id;

    private Jugador jugador;
    private Jugador computadora;

    private Campos campoJugador;
    private Campos campoComputadora;

    private Tablero tablero;

    private List<CartaMounstro> mounstruosQueAtacaron;

    public TableroModelo(Integer id) {
        this.id = id;
        jugador = new Jugador("Jugador1", this.bajarDeck());
        computadora = new Jugador("Computadora", this.bajarDeck());
        this.campoComputadora = new Campos();
        this.campoJugador = new Campos();
        this.mounstruosQueAtacaron = new ArrayList<>();

        TableroDAO tableros = new TableroDAO();
        Random random = new Random();

        try {
            // Obtener la lista de tableros una sola vez
            ArrayList<Tablero> tableroAleatorio = tableros.obtenerTableros();
            this.tablero = tableroAleatorio.get(random.nextInt(tableroAleatorio.size()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Carta> bajarDeck() {
        ArrayList<Carta> deck = new ArrayList<>();
        CartaDAO dao = new CartaDAO();
        try {
            deck = dao.obtenerCartas();
            Collections.shuffle(deck);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deck;
    }

    public void atacarEnTablero(Jugador atacante, CartaMounstro cartaAtacante, Jugador atacado,
            CartaMounstro cartaAtacada) throws Exception {
        if (!cartaAtacante.realizoAtaque()) {
            atacante.atacarCarta(cartaAtacante, cartaAtacada, atacado);
            cartaAtacante.yaAtacoEnTurno();
            mounstruosQueAtacaron.add(cartaAtacante);
        }
        if (!cartaAtacante.getActivo()) {
            campoJugador.removerCarta(cartaAtacante);
            campoComputadora.removerCarta(cartaAtacante);
        }
        if (!cartaAtacada.getActivo()) {
            campoComputadora.removerCarta(cartaAtacada);
            campoJugador.removerCarta(cartaAtacada);
        }
        notifyObservers();
        setChanged();
    }

    public void atacarJugador(CartaMounstro cartaAtacante, Jugador jugador_atacante, Jugador jugador_atacado) {
        if (!cartaAtacante.realizoAtaque()) {
            jugador_atacante.atacarJugador(cartaAtacante, jugador_atacado);
            cartaAtacante.yaAtacoEnTurno();
            mounstruosQueAtacaron.add(cartaAtacante);
        }

        notifyObservers();
        setChanged();
    }

    public void reiniciarAtaqueMounstruos() {
        for (CartaMounstro carta : mounstruosQueAtacaron) {
            carta.resetearAtaqueEnTurno();
            mounstruosQueAtacaron.remove(carta);
        }

        notifyObservers();
        setChanged();
    }

    public void jugarCartaEnTablero(CartaMounstro carta_jugada) throws Exception {
        if (carta_jugada.getElemento() == tablero.getTipo_elemento_tablero()) {
            carta_jugada.setAtaque(carta_jugada.getAtaque() + 600);
            carta_jugada.setBuffTabla(600);
        }
        if (carta_jugada.getNombre() == "MILEI") {
            //reproducir
        } else if (carta_jugada.getNombre() == "Nacho Scosso") {
            //reproducir
        }
        jugador.jugarCarta(carta_jugada, this, campoJugador);
        notifyObservers();
        setChanged();
    }

    public void jugarCartaEnTablero(CartaMagica carta_jugada) throws Exception {
        computadora.jugarCarta(carta_jugada, this, campoComputadora);
        notifyObservers();
        setChanged();
    }

    public void colocarCarta(CartaMagica carta, Campos campo) throws Exception {
        campo.agregarCartas(carta);
        carta.colocar();
        notifyObservers();
        setChanged();
    }

    public void colocarCarta(CartaMounstro carta, Campos campo) throws Exception {
        campo.agregarCartas(carta);
        carta.colocar();
        notifyObservers();
        setChanged();
    }

    public void removerCartaDeTablero(CartaMounstro carta, Campos campo) throws Exception {
        campo.removerCarta(carta);
        notifyObservers();
        setChanged();
    }

    // Para hechizos
    public void usarMagia(CartaMagicaArrojadiza carta, Jugador jugador, Campos campo) throws Exception {
        carta.activar_efecto(jugador);
        campo.removerCarta(carta);
        notifyObservers();
        setChanged();
    }

    // Para equipamento
    public void usarMagia(CartaMagicaEquipada carta, CartaMounstro monstruo, Campos campo) throws Exception {
        carta.activar_efecto(monstruo);
        campo.removerCarta(carta);
        notifyObservers();
        setChanged();
    }

    public Boolean TerminarBatalla(Jugador jugaLag) {
        if (jugaLag.getPuntosVida() <= 0) {
            return true;
        } else {
            return false;
        }
    }
    public Integer getIdUsuario() {
    	return id;
    }

    public Campos getCampoJugador() {
        return this.campoJugador;
    }

    public Campos getCampoComputadora() {
        return this.campoComputadora;
    }

    public String getImagenUrlTablero() {
        return tablero.getImagenUrlTablero();
    }

    public Jugador getJugador() {
        return jugador;
    }

    public Jugador getComputadora() {
        return computadora;
    }

    public Tablero getTablero() {
        return tablero;
    }

}