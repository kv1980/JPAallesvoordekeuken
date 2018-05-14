package be.vdab.allesvoordekeuken.repositories;

import java.util.Optional;

import be.vdab.allesvoordekeuken.entities.Artikel;

public interface ArtikelsRepository {
	Optional<Artikel> read(long id);
}
