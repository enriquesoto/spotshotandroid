package com.aqpup.waitforit.navigation;

public class Item_navigation_drawer {
	private String titulo;
	private int icono;
	public Item_navigation_drawer(String title, int icon) {
		  this.titulo = title;
	      this.icono = icon;		    
	}	
    public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public int getIcono() {
		return icono;
	}
	public void setIcono(int icono) {
		this.icono = icono;
	}   
}
