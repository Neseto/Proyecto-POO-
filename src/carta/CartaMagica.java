package carta;

public abstract class CartaMagica extends Carta{
    
    protected int cantidad_efecto; // se usara o para curar, o para inflijir daño, o buffar, depende del texto

    public CartaMagica(String nombre, String descripcion, int cantidad_efecto) {
        super(nombre, descripcion);
        this.cantidad_efecto = cantidad_efecto;
    }

    public abstract void activar_efecto(); //agregar a los jugadores aqui, dependiendo de lo que haga el efecto
                                           //se dara los parametros, no siempre se usaran ambos
}
