package modelo;

import javax.swing.ImageIcon;

public class Hijo {
	
	public String name;
	public ImageIcon icono;

	public Hijo(String name) {		
		this.name = name;
	}
	
	public void setIcon(ImageIcon icono) {
		this.icono = icono;
	}
	
	public ImageIcon getIcon() {
		return this.icono;
	}
	
	public String toString() {
		return this.name;
	}
}
