package be.vdab.entities;

import be.vdab.exceptions.RetroException;
import be.vdab.util.Invoercontrole;

public class Klant {
	private long id;
	private String familienaam;
	private String voornaam;
	private String straatNummer;
	private String postcode;
	private String gemeente;
	
	
	//constructors
	public Klant(long id, String familienaam, String voornaam, String straatNummer, String postcode, String gemeente) throws RetroException {
		setId(id);
		setFamilienaam(familienaam);
		setVoornaam(voornaam);
		setStraatNummer(straatNummer);
		setPostcode(postcode);
		setGemeente(gemeente);
	}

	//GETTERS & SETTERS
	public long getId() {
		return id;
	}


	public void setId(long id) throws RetroException {
		if (Invoercontrole.isLongPositive(id)) {
			this.id = id;
		} else {
			throw new RetroException("Klant id mag niet negatief zijn");
		}
	}


	public String getFamilienaam() {
		return familienaam;
	}


	public void setFamilienaam(String familienaam) throws RetroException {
		if (Invoercontrole.isStringNotNullOrEmpty(familienaam)) {
			this.familienaam = familienaam;
		} else {
			throw new RetroException("Familienaam mag niet leeg of null zijn");
		}
	}


	public String getVoornaam() {
		return voornaam;
	}


	public void setVoornaam(String voornaam) throws RetroException {
		if (Invoercontrole.isStringNotNullOrEmpty(voornaam)) {
			this.voornaam = voornaam;
		} else {
			throw new RetroException("Voornaam mag niet leeg of null zijn");
		}
	}


	public String getStraatNummer() {
		return straatNummer;
	}


	public void setStraatNummer(String straatNummer) throws RetroException {
		if (Invoercontrole.isStringNotNullOrEmpty(straatNummer)) {
			this.straatNummer = straatNummer;
		} else {
			throw new RetroException("StraatNummer mag niet leeg of null zijn");
		}
	}


	public String getPostcode() {
		return postcode;
	}


	public void setPostcode(String postcode) throws RetroException {
		if (Invoercontrole.isStringNotNullOrEmpty(postcode)) {
			this.postcode = postcode;
		} else {
			throw new RetroException("Postcode mag niet leeg of null zijn");
		}
	}


	public String getGemeente() {
		return gemeente;
	}


	public void setGemeente(String gemeente) throws RetroException {
		if (Invoercontrole.isStringNotNullOrEmpty(gemeente)) {
			this.gemeente = gemeente;
		} else {
			throw new RetroException("Gemeente mag niet leeg of null zijn");
		}
	}

	//HASHCODE & EQUALS
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((familienaam == null) ? 0 : familienaam.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((voornaam == null) ? 0 : voornaam.hashCode());
		return result;
	}

	@Override
	/**
	 * Klant Objects are equal when both id, familienaam and voornaam are the same
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Klant))
			return false;
		Klant other = (Klant) obj;
		if (familienaam == null) {
			if (other.familienaam != null)
				return false;
		} else if (!familienaam.equals(other.familienaam))
			return false;
		if (id != other.id)
			return false;
		if (voornaam == null) {
			if (other.voornaam != null)
				return false;
		} else if (!voornaam.equals(other.voornaam))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Klant [id=" + id + ", familienaam=" + familienaam + ", voornaam=" + voornaam + ", straatNummer="
				+ straatNummer + ", postcode=" + postcode + ", gemeente=" + gemeente + "]";
	}
	
	
	

	
}
