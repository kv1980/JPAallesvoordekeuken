package be.vdab.allesvoordekeuken.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class ArtikelGroepTest {
	private Artikelgroep artikelgroep1,artikelgroep2;
	private Artikel artikel1,artikel2;
	
	@Before
	public void before() {
		artikelgroep1 = new Artikelgroep("testGroep1");
		artikelgroep2 = new Artikelgroep("testGroep2");
		artikel1 = new FoodArtikel("testNaam",BigDecimal.ONE,BigDecimal.TEN,artikelgroep1,7);
		artikel2 = new FoodArtikel("andereTestNaam",BigDecimal.ONE,BigDecimal.TEN,artikelgroep1,7);
	}
	
	@Test
	public void nieuweArtikelgroepBevatGeenArtikels() {
		assertEquals(0,new Artikelgroep("nieuwegroep").getArtikels().size());
	}
	
	@Test
	public void eenArtikelgroepKanMeerdereArtikelsBevatten() {
		assertEquals(2,artikelgroep1.getArtikels().size());
		assertTrue(artikelgroep1.getArtikels().contains(artikel1));
		assertTrue(artikelgroep1.getArtikels().contains(artikel2));
	}
	
	@Test (expected = NullPointerException.class)
	public void addVoegtGeenNullToe() {
		artikelgroep1.add(null);
	}
	
	@Test
	public void addVerplaatstEenArtikelNaarEenAndereArtikelgroep() {
		artikelgroep2.add(artikel2);
		assertEquals(1,artikelgroep1.getArtikels().size());
		assertEquals(1,artikelgroep2.getArtikels().size());
		assertTrue(artikelgroep1.getArtikels().contains(artikel1));
		assertTrue(artikelgroep2.getArtikels().contains(artikel2));
	}
	
	@Test
	public void addVerplaatstEenArtikelNaarDezelfdeArtikelgroep() {
		artikelgroep1.add(artikel2);
		assertEquals(2,artikelgroep1.getArtikels().size());
		assertEquals(0,artikelgroep2.getArtikels().size());
		assertTrue(artikelgroep1.getArtikels().contains(artikel1));
		assertTrue(artikelgroep1.getArtikels().contains(artikel2));
	}
	
	
}
