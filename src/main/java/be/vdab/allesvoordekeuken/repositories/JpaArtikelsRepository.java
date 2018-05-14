package be.vdab.allesvoordekeuken.repositories;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import be.vdab.allesvoordekeuken.entities.Artikel;

@Repository
class JpaArtikelsRepository implements ArtikelsRepository {
	private final EntityManager manager;

	public JpaArtikelsRepository(EntityManager manager) {
		this.manager = manager;
	}
	
	@Override
	public void create(Artikel artikel) {
		manager.persist(artikel);
	}

	@Override
	public Optional<Artikel> read(long id) {
		return Optional.ofNullable(manager.find(Artikel.class,id));
	}


	
}
