package be.vdab.allesvoordekeuken.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import be.vdab.allesvoordekeuken.entities.Artikel;
import be.vdab.allesvoordekeuken.entities.Artikelgroep;
import be.vdab.allesvoordekeuken.entities.FoodArtikel;
import be.vdab.allesvoordekeuken.entities.NonFoodArtikel;
import be.vdab.allesvoordekeuken.valueobjects.Korting;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(JpaArtikelsRepository.class)
@Sql("/insertArtikel.sql")
public class JpaArtikelsRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests{
	@Autowired
	private JpaArtikelsRepository repository;
	@Autowired
	private EntityManager manager;
	private static final String ARTIKELS = "artikels";
	private FoodArtikel foodArtikel;
	private NonFoodArtikel nonFoodArtikel;
	private Artikelgroep artikelgroep;
	
	@Before
	public void before() {
		artikelgroep = new Artikelgroep("testgroep");
		foodArtikel = new FoodArtikel("testFood",BigDecimal.TEN,BigDecimal.TEN,artikelgroep,14);
		nonFoodArtikel = new NonFoodArtikel("testNonFood",BigDecimal.TEN,BigDecimal.TEN,artikelgroep,12);
	}
	
	private long idVanTestartikelFood() {
		return super.jdbcTemplate.queryForObject(
				"select id from artikels where naam = 'testartikelFood'",Long.class);
	}
	
	private long idVanTestartikelNonFood() {
		return super.jdbcTemplate.queryForObject(
				"select id from artikels where naam = 'testartikelNonFood'",Long.class);
	}
	
	@Test
	public void create_voegt_een_food_artikel_toe() {
		int aantalArtikels = super.countRowsInTableWhere(ARTIKELS,"soort='F'");
		repository.create(foodArtikel);
		assertEquals(aantalArtikels+1,super.countRowsInTableWhere(ARTIKELS,"soort='F'"));
		assertNotEquals(0,foodArtikel.getId());
		assertEquals(1,super.countRowsInTableWhere(ARTIKELS, "id = "+foodArtikel.getId()));
		assertNull(super.jdbcTemplate.queryForObject("select garantie from artikels where id=?",Integer.class,foodArtikel.getId()));

	}
	
	@Test
	public void create_voegt_een_non_food_artikel_toe() {
		int aantalArtikels = super.countRowsInTableWhere(ARTIKELS,"soort='NF'");
		repository.create(nonFoodArtikel);
		assertEquals(aantalArtikels+1,super.countRowsInTableWhere(ARTIKELS,"soort='NF'"));
		assertNotEquals(0,nonFoodArtikel.getId());
		assertEquals(1,super.countRowsInTableWhere(ARTIKELS, "id = "+nonFoodArtikel.getId()));
		assertNull(super.jdbcTemplate.queryForObject("select houdbaarheid from artikels where id=?", Integer.class,nonFoodArtikel.getId()));
	}
	
	@Test
	public void read_leest_bestaand_food_artikel_in() {
		Optional<Artikel> optioneelArtikel = repository.read(idVanTestartikelFood());
		assertEquals(7,((FoodArtikel) optioneelArtikel.get()).getHoudbaarheid());
	}
	
	@Test
	public void read_leest_bestaand_non_food_artikel_in() {
		Optional<Artikel> optioneelArtikel = repository.read(idVanTestartikelNonFood());
		assertEquals(12,((NonFoodArtikel) optioneelArtikel.get()).getGarantie());
	}
	
	@Test
	public void read_leest_onbestaand_artikel_niet_in() {
		assertFalse(repository.read(-1L).isPresent());
	}
	
/*	@Test
	public void delete_verwijdert_een_food_artikel() {
		int aantalArtikels = super.countRowsInTable(ARTIKELS);
		long id = idVanTestartikelFood();
		repository.delete(id);
		manager.flush();
		assertEquals(aantalArtikels-1,super.countRowsInTable(ARTIKELS));
		assertEquals(0,super.countRowsInTableWhere(ARTIKELS,"id = "+id));
	}*/
	
/*	@Test
	public void delete_verwijdert_een_non_food_artikel() {
		int aantalArtikels = super.countRowsInTable(ARTIKELS);
		long id = idVanTestartikelNonFood();
		repository.delete(id);
		manager.flush();
		assertEquals(aantalArtikels-1,super.countRowsInTable(ARTIKELS));
		assertEquals(0,super.countRowsInTableWhere(ARTIKELS,"id = "+id));
	}*/
	
	//-----------------------korter---------------------------------
	@Test
	public void findByWoord_geeft_lijst_met_artikels_met_woord_in_de_naam() {
		List<Artikel> artikels = repository.findByWoord("testartikel");
		assertEquals(2,artikels.size());
		String vorigeArtikelNaam = "";
		artikels.forEach(artikel -> {
			assertTrue(artikel.getNaam().toLowerCase().contains("testartikel"));
			assertTrue(artikel.getNaam().compareToIgnoreCase(vorigeArtikelNaam) >= 0);
		});
	}
	
	@Test
	public void algemenePrijsverhoging_voor_food_artikels() {
		int aantalRecordsAangepast = repository.algemenePrijsverhoging(BigDecimal.TEN);
		assertEquals(super.countRowsInTable(ARTIKELS),aantalRecordsAangepast);
		BigDecimal nieuweVerkoopprijs = super.jdbcTemplate.queryForObject("select verkoopprijs from artikels where id=?",BigDecimal.class,idVanTestartikelFood());
		assertEquals(0,nieuweVerkoopprijs.compareTo(BigDecimal.valueOf(220)));
	}
	
	@Test
	public void algemenePrijsverhoging_voor_non_food_artikels() {
		int aantalRecordsAangepast = repository.algemenePrijsverhoging(BigDecimal.TEN);
		assertEquals(super.countRowsInTable(ARTIKELS),aantalRecordsAangepast);
		BigDecimal nieuweVerkoopprijs = super.jdbcTemplate.queryForObject("select verkoopprijs from artikels where id=?",BigDecimal.class,idVanTestartikelNonFood());
		assertEquals(0,nieuweVerkoopprijs.compareTo(BigDecimal.valueOf(440)));
	}
	
	@Test
	public void kortingenLezen() {
		Artikel artikel = repository.read(idVanTestartikelFood()).get();
		assertEquals(1,artikel.getKortingen().size());
		assertTrue(artikel.getKortingen().contains(new Korting(10,BigDecimal.TEN)));
	}
	
	@Test
	public void kortingToevoegen() {
		repository.create(foodArtikel);
		foodArtikel.addKorting(new Korting(25,BigDecimal.valueOf(33)));
		manager.flush();
		assertEquals(BigDecimal.valueOf(33).setScale(2),super.jdbcTemplate.queryForObject("select percentage from kortingen where artikelid=? and vanafAantal=25",BigDecimal.class,foodArtikel.getId()));
	}
}
