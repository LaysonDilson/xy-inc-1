package br.com.zup.xyinc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.zup.xyinc.model.PontoInteresse;
import br.com.zup.xyinc.service.PontoInteresseService;

@RestController
@RequestMapping(value = "/ws/poi")
public class PontoInteresseController {

	@Autowired
	private PontoInteresseService service;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<PontoInteresse> list() {
		return (List<PontoInteresse>) service.findAll();
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> add(@RequestBody PontoInteresse poi, UriComponentsBuilder ucBuilder) {
		if (!isValid(poi)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		poi = service.save(poi);

		HttpHeaders headers = new HttpHeaders();

	    headers.setLocation(ucBuilder.path("/ws/poi/{id}").buildAndExpand(poi.getId()).toUri());

	    return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> edit(@RequestBody PontoInteresse poi) {
		if (!isValid(poi)) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}

		service.save(poi);

		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<PontoInteresse> search(@RequestParam(value = "x") Long posX, @RequestParam(value = "y") Long posY, @RequestParam(value = "dMax") Double dMax) {
		List<PontoInteresse> result = service.findByCoordinate(posX, posY, dMax);

		return result;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id) {
		PontoInteresse poi = service.findOne(id);
		
		if (poi == null) {
			throw new ResourceNotFoundException();
		}

		service.delete(id);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public PontoInteresse findOne(@PathVariable("id") Long id) {
		PontoInteresse poi = service.findOne(id);
		
		if (poi == null) {
			throw new ResourceNotFoundException();
		}

		return poi;
	}
	
	private boolean isValid(PontoInteresse poi) {
		if ((poi.getX() != null && poi.getX() < 0) || (poi.getY() != null && poi.getY() < 0)) {
			return false;
		}
		
		return true;
	}
	
//	private void parseNegativeValues(PontoInteresse poi) {
//		if (poi.getX() < 0) {
//			poi.setX(poi.getX() * -1);
//		}
//		
//		if (poi.getY() < 0) {
//			poi.setY(poi.getY() * -1);
//		}
//	}

}
