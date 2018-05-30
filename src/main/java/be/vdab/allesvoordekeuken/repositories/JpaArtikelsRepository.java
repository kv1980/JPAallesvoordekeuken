package be.vdab.allesvoordekeuken.repositories;

import java.util.List;
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

	@Override
	public void delete(long id) {
		read(id).ifPresent(artikel -> manager.remove(artikel));	
	}

	@Override
	public List<Artikel> findByWoord(String woord) {
		return manager.createNamedQuery("Artikel.findByWoord",Artikel.class)
				      .setParameter("patroon","%"+woord+"%")
				      .getResultList();
	}	
}
