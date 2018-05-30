package be.vdab.allesvoordekeuken.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import be.vdab.allesvoordekeuken.entities.Artikel;

public interface ArtikelsRepository {
	void create(Artikel artikel);
	Optional<Artikel> read(long id);
	void delete(long id);
	List<Artikel> findByWoord(String woord);
	int algemenePrijsverhoging(BigDecimal percentage);
}