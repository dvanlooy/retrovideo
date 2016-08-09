package be.vdab.entities;

public class Genre {
	private long id;
	private String naam;

	// constructors
	public Genre(long id, String naam) {
		setId(id);
		setNaam(naam);
	}

	//GETTERS & SETTERS
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

}
