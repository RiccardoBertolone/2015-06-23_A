package it.polito.tdp.music.model;

public class MieiArchi {

	private Country c1 ;
	private Country c2 ;
	private int peso ;
	
	public MieiArchi(Country c1, Country c2, int peso) {
		super();
		this.c1 = c1;
		this.c2 = c2;
		this.peso = peso;
	}

	public Country getC1() {
		return c1;
	}

	public void setC1(Country c1) {
		this.c1 = c1;
	}

	public Country getC2() {
		return c2;
	}

	public void setC2(Country c2) {
		this.c2 = c2;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}
	
	
	
	
}
