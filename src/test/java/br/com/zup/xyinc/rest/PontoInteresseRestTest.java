package br.com.zup.xyinc.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import br.com.zup.xyinc.BootApplication;
import br.com.zup.xyinc.model.PontoInteresse;
import br.com.zup.xyinc.service.PontoInteresseService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BootApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class PontoInteresseRestTest {

	private static String BASE_URL 		= "/ws/poi";
	private static String URL_SEPARATOR = "/";
	private static long	  POINT_X = 27;
	private static long	  POINT_Y = 12;
	
	@Autowired
	private WebApplicationContext ctx;
	private MockMvc mvc;
	private HttpMessageConverter mappingJackson2HttpMessageConverter;
	
	@Autowired
	private PontoInteresseService service;
	
	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters){

	    List<HttpMessageConverter<?>> converterList = Arrays.asList(converters);

	    for(HttpMessageConverter<?> converter : converterList){
	        if(converter instanceof MappingJackson2HttpMessageConverter){
	            this.mappingJackson2HttpMessageConverter = converter;
	        }
	    }

	    Assert.assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}
	
	@Before
	public void setUp() {
		mvc = webAppContextSetup(ctx).build();
	}
	
	@Test
	public void testAdd() throws Exception {
		PontoInteresse poi = new PontoInteresse("Lanchonete", 5, 5);

		mvc.perform(post(BASE_URL)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(json(poi)))
				.andExpect(status().isCreated());
	}
	
	@Test
	public void testEdit() throws Exception {
		PontoInteresse poi = new PontoInteresse(1L, "Nova Lanchonete", POINT_X, POINT_Y);

		mvc.perform(put(BASE_URL)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(json(poi)))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testEditInvalidValues() throws Exception {
		PontoInteresse poi = new PontoInteresse(1L, "Nova Lanchonete", -5, -5);

		mvc.perform(put(BASE_URL)
				.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.content(json(poi)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void testFindAll() throws Exception {
		int currentSize = service.findAll().size();

		mvc.perform(get(BASE_URL)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(currentSize)));
	}
	
	@Test
	public void testFindOne() throws Exception {
		mvc.perform(get(BASE_URL + URL_SEPARATOR + 1L))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.id").value(Integer.parseInt(String.valueOf(1L))));
	}
	
	@Test
	public void testFindOneNotFound() throws Exception {
		mvc.perform(get(BASE_URL + URL_SEPARATOR + 2L))
			.andExpect(status().isNotFound());
	}
	
	@Test
	public void testSearch() throws Exception {
		String x = "20";
		String y = "10";
		String dMax = "10";
		
		mvc.perform(get(BASE_URL + URL_SEPARATOR + "search")
					.param("x", x)
					.param("y", y)
					.param("dMax", dMax))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$", Matchers.hasSize(1)));
	}
	
	@Test
	public void testSearchNotFound() throws Exception {
		String x = "10";
		String y = "5";
		String dMax = "1";
		
		mvc.perform(get(BASE_URL + URL_SEPARATOR + "search")
					.param("x", x)
					.param("y", y)
					.param("dMax", dMax))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$", Matchers.hasSize(0)));
	}
	
	@Test
	public void testDelete() throws Exception {
		PontoInteresse poi = new PontoInteresse("Supermercado", 30, 30);
		
		poi = service.save(poi);
		
		mvc.perform(delete(BASE_URL + URL_SEPARATOR + poi.getId())
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNoContent());
	}

	protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();

        mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);

        return mockHttpOutputMessage.getBodyAsString();
    }
}
