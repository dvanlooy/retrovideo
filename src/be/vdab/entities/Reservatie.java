package be.vdab.entities;

import java.sql.Date;

import be.vdab.exceptions.RetroException;
import be.vdab.util.Invoercontrole;

public class Reservatie {
	private long klantid;
	private long filmid;
	private Date reservatieDatum;
	
	//constructors
	public Reservatie(long klantid, long filmid, Date reservatieDatum) throws RetroException {
		super();
		setKlantid(klantid);
		setFilmid(filmid);
		setReservatieDatum(reservatieDatum);
	}
	
	//GETTERS & SETTERS
	public long getKlantid() {
		return klantid;
	}
	public void setKlantid(long klantid) throws RetroException {
		if (Invoercontrole.controleerLong(klantid)) {
			this.klantid = klantid;
		} else {
			throw new RetroException("Klantid mag niet negatief zijn");
		}
	}
	public long getFilmid() {
		return filmid;
	}
	public void setFilmid(long filmid) throws RetroException {
		if (Invoercontrole.controleerLong(filmid)) {
			this.filmid = filmid;
		} else {
			throw new RetroException("Filmid mag niet negatief zijn");
		}
	}
	public Date getReservatieDatum() {
		return reservatieDatum;
	}
	public void setReservatieDatum(Date reservatieDatum){
		this.reservatieDatum = reservatieDatum;
	}

	//HASHCODE & EQUALS
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (filmid ^ (filmid >>> 32));
		result = prime * result + (int) (klantid ^ (klantid >>> 32));
		result = prime * result + ((reservatieDatum == null) ? 0 : reservatieDatum.hashCode());
		return result;
	}

	@Override
	/**
	 * Reservatie Objects are equal when both filmid, klantid and reservatieDatum are the same
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Reservatie))
			return false;
		Reservatie other = (Reservatie) obj;
		if (filmid != other.filmid)
			return false;
		if (klantid != other.klantid)
			return false;
		if (reservatieDatum == null) {
			if (other.reservatieDatum != null)
				return false;
		} else if (!reservatieDatum.equals(other.reservatieDatum))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reservatie [klantid=" + klantid + ", filmid=" + filmid + ", reservatieDatum=" + reservatieDatum + "]";
	}
	

	
	
	
}
