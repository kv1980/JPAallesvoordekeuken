package be.vdab.allesvoordekeuken.entities;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name="artikelgroepen")
public class Artikelgroep {
	
	//-------------------ATTRIBUTES--------------------
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String naam;
	@OneToMany(mappedBy = "artikelgroep" )
	@OrderBy("naam")
	private Set<Artikel> artikels;
	
	//------------------CONSTRUCTORS-------------------
	
	protected Artikelgroep() {
	}

	public Artikelgroep(String naam) {
		this.naam = naam;
		this.artikels = new LinkedHashSet();
	}
	
	//---------------------GETTERS---------------------
	
	public String getNaam() {
		return naam;
	}
	
	public Set<Artikel> getArtikels() {
		return Collections.unmodifiableSet(artikels);
	}
	
	//---------------SPECIFIEKE-FUNCTIES---------------
		
	public boolean add(Artikel artikel) {
		if (artikel == null) {
			throw new NullPointerException();
		}
		boolean toegevoegd = artikels.add(artikel);
		Artikelgroep oudeArtikelgroep = artikel.getArtikelgroep();
		if (oudeArtikelgroep != this && oudeArtikelgroep != null) {
			oudeArtikelgroep.artikels.remove(artikel);
		}
		if (oudeArtikelgroep != this) {
			artikel.setArtikelgroep(this);
		}
		return toegevoegd;
	}
}