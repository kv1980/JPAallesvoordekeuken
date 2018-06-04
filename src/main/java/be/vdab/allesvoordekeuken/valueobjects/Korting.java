package be.vdab.allesvoordekeuken.valueobjects;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Embeddable;

@Embeddable
public class Korting implements Serializable {
	private static final long serialVersionUID = 1L;
	private int vanafAantal;
	private BigDecimal percentage;

	protected Korting() {
	}

	public Korting(int vanafAantal, BigDecimal percentage) {
		this.vanafAantal = vanafAantal;
		this.percentage = percentage;
	}

	public int getVanafAantal() {
		return vanafAantal;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}
	
	@Override
	public int hashCode() {
		return vanafAantal;
	}
	
	@Override
	public boolean equals(Object object) {
		if(!(object instanceof Korting)) {
			return false;
		}
		Korting andereKorting = (Korting) object;
		return vanafAantal == andereKorting.vanafAantal;
	}
}