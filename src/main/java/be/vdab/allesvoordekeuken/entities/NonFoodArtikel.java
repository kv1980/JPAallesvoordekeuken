package be.vdab.allesvoordekeuken.entities;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("NF")
public class NonFoodArtikel extends Artikel {
	private static final long serialVersionUID=1L;
	private int garantie;

	protected NonFoodArtikel() {
	}

	public NonFoodArtikel(String naam, BigDecimal aankoopprijs, BigDecimal verkoopprijs, Artikelgroep artikelgroep, int  garantie) {
		super(naam,aankoopprijs,verkoopprijs,artikelgroep);
		this.garantie = garantie;
	}

	public int getGarantie() {
		return garantie;
	}
}