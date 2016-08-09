package be.vdab.entities;

import java.math.BigDecimal;

public class Film {
	private long id;
	private Genre genre;
	private String titel;
	private int voorraad;
	private int gereserveerd;
	private BigDecimal prijs;

	// CONSTRUCTORS
	public Film(long id, Genre genre, String titel, int voorraad, int gereserveerd, BigDecimal prijs) {
		setId(id);
		setGenre(genre);
		setTitel(titel);
		setVoorraad(voorraad);
		setGereserveerd(gereserveerd);
		setPrijs(prijs);
	}

//	public Film createFilm(long id, Genre genre, String titel, int voorraad, int gereserveerd, BigDecimal prijs) {
//		return new Film(id, genre, titel, voorraad, gereserveerd, prijs);
//	}

	// GETTERS & SETTERS
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public int getVoorraad() {
		return voorraad;
	}

	public void setVoorraad(int voorraad) {
		this.voorraad = voorraad;
	}

	public int getGereserveerd() {
		return gereserveerd;
	}

	public void setGereserveerd(int gereserveerd) {
		this.gereserveerd = gereserveerd;
	}

	public BigDecimal getPrijs() {
		return prijs;
	}

	public void setPrijs(BigDecimal prijs) {
		this.prijs = prijs;
	}

}
