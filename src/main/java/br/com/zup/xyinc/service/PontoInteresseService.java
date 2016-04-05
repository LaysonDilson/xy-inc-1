package br.com.zup.xyinc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.zup.xyinc.dao.PontoInteresseDAO;
import br.com.zup.xyinc.model.PontoInteresse;

@Service
public class PontoInteresseService {

	@Autowired
	private PontoInteresseDAO dao;
	
	public List<PontoInteresse> findAll() {
		return (List<PontoInteresse>) dao.findAll();
	}
	
	public List<PontoInteresse> findByCoordinate(Long x, Long y, Double distancia) {
		return dao.findByCoordinate(x, y, distancia);
	}
	
	public PontoInteresse save(PontoInteresse poi) {
		return dao.save(poi);
	}
	
	public PontoInteresse findOne(Long id) {
		return dao.findOne(id);
	}
	
	public void delete(Long id) {
		dao.delete(id);
	}
	
}
