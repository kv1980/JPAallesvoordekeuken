package be.vdab.allesvoordekeuken.repositories;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;

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
	private Artikel artikel;
	
	@Before
	public void before() {
		artikel = new Artikel("testartikel",BigDecimal.TEN,BigDecimal.TEN);
	}
	
	private long idVanTestartikel() {
		return super.jdbcTemplate.queryForObject(
				"select id from artikels where naam = 'testartikel'",Long.class);
	}
	
	@Test
	public void create_voegt_een_artikel_toe() {
		int aantalArtikels = super.countRowsInTable(ARTIKELS);
		repository.create(artikel);
		assertEquals(aantalArtikels+1,super.countRowsInTable(ARTIKELS));
		assertNotEquals(0,artikel.getId());
		assertEquals(1,super.countRowsInTableWhere(ARTIKELS, "id = "+artikel.getId()));
	}
	
	@Test
	public void read_leest_bestaand_artikel_in() {
		assertEquals("testartikel",repository.read(idVanTestartikel()).get().getNaam());
	}
	
	@Test
	public void read_leest_onbestaand_artikel_niet_in() {
		assertFalse(repository.read(-1L).isPresent());
	}
	
	@Test
	public void delete_verwijdert_een_artikel() {
		int aantalArtikels = super.countRowsInTable(ARTIKELS);
		long id = idVanTestartikel();
		repository.delete(id);
		manager.flush();
		assertEquals(aantalArtikels-1,super.countRowsInTable(ARTIKELS));
		assertEquals(0,super.countRowsInTableWhere(ARTIKELS,"id = "+id));
	}
	
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
	public void algemenePrijsverhoging() {
		int aantalRecordsAangepast = repository.algemenePrijsverhoging(BigDecimal.TEN);
		assertEquals(super.countRowsInTable(ARTIKELS),aantalRecordsAangepast);
		BigDecimal nieuweVerkoopprijs = super.jdbcTemplate.queryForObject("select verkoopprijs from artikels where id=?",BigDecimal.class,idVanTestartikel());
		assertEquals(0,nieuweVerkoopprijs.compareTo(BigDecimal.valueOf(220)));
	}
}
