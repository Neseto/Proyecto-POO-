package modelo;

public class CartaMagicaBuff extends CartaMagicaEquipada {


	public CartaMagicaBuff(String nombre, String descripcion, int cantidad_efecto, String efecto) {
		super(nombre, descripcion, cantidad_efecto, efecto);
	}

	
	@Override

	public void activar_efecto(CartaMounstro carta) {
		carta.setAtaque(carta.getAtaque() + cantidad_efecto);
	}

	
}