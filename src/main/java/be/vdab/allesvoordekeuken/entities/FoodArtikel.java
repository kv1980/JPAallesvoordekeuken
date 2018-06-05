package be.vdab.allesvoordekeuken.entities;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("F")
public class FoodArtikel extends Artikel {
	private static final long serialVersionUID = 1L;
	private int houdbaarheid;

	protected FoodArtikel() {
	}

	public FoodArtikel(String naam, BigDecimal aankoopprijs, BigDecimal verkoopprijs, Artikelgroep artikelgroep, int houdbaarheid) {
		super(naam,aankoopprijs,verkoopprijs,artikelgroep);
		this.houdbaarheid = houdbaarheid;
	}

	public int getHoudbaarheid() {
		return houdbaarheid;
	}
}