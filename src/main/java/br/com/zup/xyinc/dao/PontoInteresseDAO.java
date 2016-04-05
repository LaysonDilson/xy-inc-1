package br.com.zup.xyinc.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.zup.xyinc.model.PontoInteresse;

@RepositoryRestResource
@Transactional
public interface PontoInteresseDAO extends CrudRepository<PontoInteresse, Long> {

	@Query("SELECT p FROM PontoInteresse p WHERE round(sqrt( pow((p.x - :posX), 2) + pow((p.y - :posY),2)), 2) <= :distancia")
	List<PontoInteresse> findByCoordinate(@Param("posX") Long x, @Param("posY") Long y, @Param("distancia") Double distancia);

}
