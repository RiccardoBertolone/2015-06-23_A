package it.polito.tdp.music.model;

public class Track {
	
	private int id ;
	private String track ;
	
	public Track(int id, String track) {
		super();
		this.id = id;
		this.track = track;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getArtist() {
		return track;
	}
	public void setArtist(String artist) {
		this.track = artist;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Track other = (Track) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return track;
	}
	
	
	
}
