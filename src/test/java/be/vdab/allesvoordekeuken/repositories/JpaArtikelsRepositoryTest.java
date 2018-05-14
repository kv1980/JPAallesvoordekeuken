package be.vdab.allesvoordekeuken.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(JpaArtikelsRepository.class)
@Sql("/insertArtikel.sql")
public class JpaArtikelsRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests{
	@Autowired
	private JpaArtikelsRepository repository;
	
	private long idVanTestartikel() {
		return super.jdbcTemplate.queryForObject(
				"select id from artikels where naam = 'testartikel'",Long.class);
	}
	
	@Test
	public void read_leest_bestaand_artikel_in() {
		assertEquals("testartikel",repository.read(idVanTestartikel()).get().getNaam());
	}
	
	@Test
	public void read_leest_onbestaand_artikel_niet_in() {
		assertFalse(repository.read(-1L).isPresent());
	}
}
