package be.vdab.allesvoordekeuken.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import be.vdab.allesvoordekeuken.valueobjects.Korting;

@Entity
@Table(name = "artikels")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "soort")
public abstract class Artikel implements Serializable {

	// -------------------ATTRIBUTES--------------------

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String naam;
	private BigDecimal aankoopprijs;
	private BigDecimal verkoopprijs;
	@ElementCollection
	@CollectionTable(name = "kortingen", joinColumns = @JoinColumn(name = "artikelid"))
	@OrderBy("vanafAantal")
	private Set<Korting> kortingen;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "artikelgroepid")
	private Artikelgroep artikelgroep;

	// ------------------CONSTRUCTORS-------------------

	protected Artikel() {
	}

	public Artikel(String naam, BigDecimal aankoopprijs, BigDecimal verkoopprijs, Artikelgroep artikelgroep) {
		this.naam = naam;
		this.aankoopprijs = aankoopprijs;
		this.verkoopprijs = verkoopprijs;
		this.kortingen = new LinkedHashSet();
		setArtikelgroep(artikelgroep);
	}

	// ---------------------GETTERS---------------------

	public long getId() {
		return id;
	}

	public String getNaam() {
		return naam;
	}

	public BigDecimal getAankoopprijs() {
		return aankoopprijs;
	}

	public BigDecimal getVerkoopprijs() {
		return verkoopprijs;
	}

	public Set<Korting> getKortingen() {
		return Collections.unmodifiableSet(kortingen);
	}

	public Artikelgroep getArtikelgroep() {
		return artikelgroep;
	}

	// --------------------SETTERS----------------------

	public void setArtikelgroep(Artikelgroep artikelgroep) {
		if (artikelgroep == null) {
			throw new NullPointerException();
		}
		if (!artikelgroep.getArtikels().contains(this)) {
			artikelgroep.add(this);
		}
		this.artikelgroep = artikelgroep;
	}

	// ---------------SPECIFIEKE-FUNCTIES---------------

	public boolean addKorting(Korting korting) {
		if (korting == null) {
			throw new NullPointerException();
		}
		return kortingen.add(korting);
	}

	// ----------------OVERRIDE-FUNCTIES----------------

	@Override
	public int hashCode() {
		return naam.toUpperCase().hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Artikel)) {
			return false;
		}
		Artikel anderArtikel = (Artikel) object;
		return naam.equalsIgnoreCase(anderArtikel.naam);
	}
}