 package modelo;

 public class CartaMagicaHerida extends CartaMagicaArrojadiza {

	public CartaMagicaHerida(String nombre, String descripcion, int cantidad_efecto, String imagen) {
		super(nombre, descripcion, cantidad_efecto, imagen);
	}

	
	@Override

	public void activar_efecto(Jugador jugador) {
		jugador.recibirDaño(this.cantidad_efecto);
	}

	
}
