package be.vdab.allesvoordekeuken.valueobjects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class KortingTest {
	private Korting korting1,korting2,korting3;
	
	@Before
	public void before() {
		korting1 = new Korting(5,BigDecimal.TEN);
		korting2 = new Korting(5,BigDecimal.TEN);
		korting3 = new Korting(10,BigDecimal.valueOf(20));
	}
	
	@Test
	public void kortingenZijnGelijkAlsHetAantalGelijkIs() {
		assertEquals(korting1,korting2);
	}
	
	@Test
	public void kortingenZijnNietGelijkAlsHetAantalNietGelijkIs() {
		assertNotEquals(korting1,korting3);
	}
	
	@Test
	public void eenKortingVerschiltVanNull() {
		assertNotEquals(korting1,null);
	}
	
	@Test
	public void eenKortingVerschiltVanEenAnderTypeObject() {
		assertNotEquals(korting1,"String");
	}
	
	@Test
	public void tweeGelijkeKortingenHebbenDezelfdeHashCode() {
		assertEquals(korting1.hashCode(),korting2.hashCode());
	}
}
