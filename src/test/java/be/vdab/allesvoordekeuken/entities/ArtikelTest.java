package be.vdab.allesvoordekeuken.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import be.vdab.allesvoordekeuken.valueobjects.Korting;

public class ArtikelTest {
	private Artikelgroep artikelgroep1,artikelgroep2;
	private Artikel artikel1, kopieArtikel1, artikel2;
	private Korting korting;

	@Before
	public void before() {
		artikelgroep1 = new Artikelgroep("testGroep1");
		artikelgroep2 = new Artikelgroep("testGroep2");
		artikel1 = new FoodArtikel("testNaam",BigDecimal.ONE,BigDecimal.TEN,artikelgroep1,7);
		kopieArtikel1 = new FoodArtikel("testNaam",BigDecimal.ONE,BigDecimal.TEN,artikelgroep1,7);
		artikel2 = new FoodArtikel("andereTestNaam",BigDecimal.ONE,BigDecimal.TEN,artikelgroep1,7);
		korting = new Korting(5,BigDecimal.TEN);
	}
	
	@Test
	public void artikelsZijnGelijkAlsHunNaamHetzelfdeIs() {
		assertEquals(artikel1,kopieArtikel1);
	}
	
	@Test
	public void artikelsZijnNietGelijkAlsHunNaamVerschillendIs() {
		assertNotEquals(artikel1,artikel2);
	}
	
	@Test
	public void eenArtikelVerschiltVanNull() {
		assertNotEquals(artikel1,null);
	}
	
	@Test
	public void eenArtikelVerschiltVanEenAnderTypeObject() {
		assertNotEquals(artikel1,"voorHansEenString");
	}
	
	@Test
	public void dezelfdeArtikelsHebbenDezelfdeHashCode() {
		assertEquals(artikel1.hashCode(),kopieArtikel1.hashCode());
	}
	
	@Test
	public void verschillendeArtikelsHebbenVerschillendeHashCode() {
		assertNotEquals(artikel1.hashCode(),artikel2.hashCode());
	}
	
	@Test
	public void artikelIsVerbondenMetArtikelgroep1() {
		assertEquals(artikel1.getArtikelgroep().getNaam(),"testGroep1");	
		assertTrue(artikelgroep1.getArtikels().contains(artikel1));
	}
	
	@Test
	public void artikelIsVerbondenMetArtikelgroep2NaVerandering() {
		artikel1.setArtikelgroep(artikelgroep2);
		assertNotEquals(artikel1.getArtikelgroep().getNaam(),"testGroep1");	
		assertEquals(artikel1.getArtikelgroep().getNaam(),"testGroep2");	
		assertFalse(artikelgroep1.getArtikels().contains(artikel1));
		assertTrue(artikelgroep2.getArtikels().contains(artikel1));
	}
	
	@Test (expected = NullPointerException.class)
	public void artikelKanNietVerbondenWordenMetArtikelgroepNull() {
		artikel1.setArtikelgroep(null);
	}
	
	@Test
	public void nieuwArtikelHeeftGeenKorting() {
		assertEquals(0,artikel1.getKortingen().size());
	}
	
	@Test
	public void addKortingVoegtEenKortingToe() {
		assertTrue(artikel1.addKorting(korting));
		assertEquals(1,artikel1.getKortingen().size());
		assertTrue(artikel1.getKortingen().contains(korting));
	}
	
	@Test (expected = NullPointerException.class)
	public void addKortingKanGeenNullToevoegen() {
		artikel1.addKorting(null);
	}
	
	@Test
	public void addKortingMagGeenDezelfdeKortingenToevoegen() {
		assertTrue(artikel1.addKorting(korting));
		assertFalse(artikel1.addKorting(korting));
		assertEquals(1,artikel1.getKortingen().size());
		assertTrue(artikel1.getKortingen().contains(korting));
	}
}