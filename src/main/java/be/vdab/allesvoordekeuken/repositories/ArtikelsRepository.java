package be.vdab.allesvoordekeuken.repositories;

import java.util.Optional;

import be.vdab.allesvoordekeuken.entities.Artikel;

public interface ArtikelsRepository {
	void create(Artikel artikel);
	Optional<Artikel> read(long id);
}
