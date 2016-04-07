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
		poi = service.save(poi);
		
		if (poi == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		HttpHeaders headers = new HttpHeaders();

	    headers.setLocation(ucBuilder.path("/ws/poi/{id}").buildAndExpand(poi.getId()).toUri());

	    return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Void> edit(@RequestBody PontoInteresse poi) {
		poi = service.save(poi);
		
		if (poi == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

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
}
