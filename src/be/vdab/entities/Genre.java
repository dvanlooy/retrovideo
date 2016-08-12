package be.vdab.entities;

import be.vdab.exceptions.RetroException;
import be.vdab.util.Invoercontrole;

public class Genre {
	private static final long serialVersionUID = 1L;
	private long id;
	private String naam;

	// constructors
	public Genre(long id, String naam) throws RetroException {
		setId(id);
		setNaam(naam);
	}

	//GETTERS & SETTERS
	public long getId() {
		return id;
	}

	public void setId(long id) throws RetroException {
		if (Invoercontrole.isLongPositive(id)) {
			this.id = id;
		} else {
			throw new RetroException("Genre id mag niet negatief zijn");
		}
	}

	public String getNaam() {
		return naam;
	}

	public void setNaam(String naam) throws RetroException {
		if (Invoercontrole.isStringNotNullOrEmpty(naam)) {
			this.naam = naam;
		} else {
			throw new RetroException("Genre naam mag niet leeg of null zijn");
		}
	}

	//HASHCODE & EQUALS
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((naam == null) ? 0 : naam.hashCode());
		return result;
	}

	@Override
	/**
	 * Genre Objects are equal when both id and naam are the same
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Genre))
			return false;
		Genre other = (Genre) obj;
		if (id != other.id)
			return false;
		if (naam == null) {
			if (other.naam != null)
				return false;
		} else if (!naam.equals(other.naam))
			return false;
		return true;
	}

	//TOSTRING
	@Override
	public String toString() {
		return "Genre [id=" + id + ", naam=" + naam + "]";
	}
	
	
	

}
