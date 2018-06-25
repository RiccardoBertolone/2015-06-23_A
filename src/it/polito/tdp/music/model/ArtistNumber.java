package it.polito.tdp.music.model;

public class ArtistNumber {
	
	private Artist a ;
	private int number ;
	
	public ArtistNumber(Artist a, int number) {
		super();
		this.a = a;
		this.number = number;
	}

	public Artist getA() {
		return a;
	}

	public void setA(Artist a) {
		this.a = a;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	

}
