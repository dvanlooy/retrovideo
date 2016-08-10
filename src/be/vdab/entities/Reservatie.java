package be.vdab.entities;

import java.sql.Date;

public class Reservatie {
	private long klantid;
	private long filmid;
	private Date reservatieDatum;
	
	//constructors
	public Reservatie(long klantid, long filmid, Date reservatieDatum) {
		super();
		this.klantid = klantid;
		this.filmid = filmid;
		this.reservatieDatum = reservatieDatum;
	}
	
	//GETTERS & SETTERS
	public long getKlantid() {
		return klantid;
	}
	public void setKlantid(long klantid) {
		this.klantid = klantid;
	}
	public long getFilmid() {
		return filmid;
	}
	public void setFilmid(long filmid) {
		this.filmid = filmid;
	}
	public Date getReservatieDatum() {
		return reservatieDatum;
	}
	public void setReservatieDatum(Date reservatieDatum) {
		this.reservatieDatum = reservatieDatum;
	}
	
	
}
