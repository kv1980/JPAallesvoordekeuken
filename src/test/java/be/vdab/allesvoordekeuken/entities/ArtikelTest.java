package be.vdab.allesvoordekeuken.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import be.vdab.allesvoordekeuken.valueobjects.Korting;

public class ArtikelTest {
	private Artikelgroep artikelgroep1,artikelgroep2;
	private Artikel artikel;
	private Korting korting;

	@Before
	public void before() {
		artikelgroep1 = new Artikelgroep("testGroep1");
		artikelgroep2 = new Artikelgroep("testGroep2");
		artikel = new FoodArtikel("testNaam",BigDecimal.ONE,BigDecimal.TEN,artikelgroep1,7);
		korting = new Korting(5,BigDecimal.TEN);
	}
	
	@Test
	public void artikelIsVerbondenMetArtikelgroep1() {
		assertEquals(artikel.getArtikelgroep())
	}
	
	@Test
	public void nieuwArtikelHeeftGeenKorting() {
		assertEquals(0,artikel.getKortingen().size());
	}
	
	@Test
	public void addKortingVoegtEenKortingToe() {
		assertTrue(artikel.addKorting(korting));
		assertEquals(1,artikel.getKortingen().size());
		assertTrue(artikel.getKortingen().contains(korting));
	}
	
	@Test (expected = NullPointerException.class)
	public void addKortingKanGeenNullToevoegen() {
		artikel.addKorting(null);
	}
	
	@Test
	public void addKortingMagGeenDezelfdeKortingenToevoegen() {
		assertTrue(artikel.addKorting(korting));
		assertFalse(artikel.addKorting(korting));
		assertEquals(1,artikel.getKortingen().size());
		assertTrue(artikel.getKortingen().contains(korting));
	}
}