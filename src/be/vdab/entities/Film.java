package be.vdab.entities;

import java.math.BigDecimal;

import be.vdab.exceptions.RetroException;
import be.vdab.util.Invoercontrole;

public class Film implements Comparable<Film>{
	private long id;
	private Genre genre;
	private String titel;
	private int voorraad;
	private int gereserveerd;
	private BigDecimal prijs;

	// CONSTRUCTORS
	public Film(long id, Genre genre, String titel, int voorraad, int gereserveerd, BigDecimal prijs) throws RetroException {
		setId(id);
		setGenre(genre);
		setTitel(titel);
		setVoorraad(voorraad);
		setGereserveerd(gereserveerd);
		setPrijs(prijs);
	}

	// GETTERS & SETTERS
	public long getId() {
		return id;
	}

	public void setId(long id) throws RetroException {
		if (Invoercontrole.isLongPositive(id)) {
			this.id = id;
		} else {
			throw new RetroException("Film id mag niet negatief zijn");
		}
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

	public void setTitel(String titel) throws RetroException {
		if (Invoercontrole.isStringNotNullOrEmpty(titel)) {
			this.titel = titel;
		} else {
			throw new RetroException("Film titel mag niet leeg of null zijn");
		}

	}

	public int getVoorraad() {
		return voorraad;
	}

	public void setVoorraad(int voorraad) throws RetroException {
		if (Invoercontrole.isIntPositive(voorraad)) {
			this.voorraad = voorraad;
		} else {
			throw new RetroException("Film voorraad mag niet negatief zijn");
		}

	}

	public int getGereserveerd() {
		return gereserveerd;
	}

	public void setGereserveerd(int gereserveerd) throws RetroException {
		if (Invoercontrole.isIntPositive(gereserveerd)) {
			this.gereserveerd = gereserveerd;
		} else {
			throw new RetroException("Films gereserveerd mag niet negatief zijn");
		}

	}

	public BigDecimal getPrijs() {
		return prijs;
	}

	public void setPrijs(BigDecimal prijs) throws RetroException  {
		if (Invoercontrole.isBigDecimalPositive(prijs)) {
			this.prijs = prijs;
		} else {
			throw new RetroException("Prijs mag niet negatief zijn");
		}

	}

	// HASHCODE & EQUALS
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((titel == null) ? 0 : titel.hashCode());
		return result;
	}

	@Override
	/**
	 * Film Objects are equal when both id and titel are the same
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Film))
			return false;
		Film other = (Film) obj;
		if (id != other.id)
			return false;
		if (titel == null) {
			if (other.titel != null)
				return false;
		} else if (!titel.equals(other.titel))
			return false;
		return true;
	}
	
	

	@Override
	public String toString() {
		return "Film [id=" + id + ", genre=" + genre + ", titel=" + titel + ", voorraad=" + voorraad + ", gereserveerd="
				+ gereserveerd + ", prijs=" + prijs + "]";
	}

	@Override
	public int compareTo(Film arg0) {
		if (arg0 == null) {
			throw new NullPointerException();
			}
			else {
			return this.titel.compareTo(arg0.getTitel());
			}
	}

}
