package br.com.unb.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.unb.domain.PistarServiceFactory;
import br.com.unb.service.PistarService;
import br.com.unb.utils.Teste;

@RestController
public class PistarResource {
	
	@Autowired
	private PistarService service;
	
	@RequestMapping(value = "/executePistarFactoryService", method = RequestMethod.POST)
	public Object executePistarFactoryService(@RequestBody PistarServiceFactory pistarFactory) {
		return this.service.executePistarFactoryService(pistarFactory);
	}

	@RequestMapping(value = "/teste2", method = RequestMethod.GET)
	public Object teste() {
		Teste t = new Teste();
		t.setLon(1L);
		t.setSa("Teste");
		return t;
	}

	@RequestMapping(value = "/teste", method = RequestMethod.GET)
	public Object teste2(@RequestParam(name = "sa") String sa, @RequestParam(name = "lon") Long lon) {
		Teste t = new Teste();
		t.setLon(lon);
		t.setSa(sa);
		
		return t.getSa();
	}

	@RequestMapping(value = "/teste3", method = RequestMethod.POST)
	public Object teste2(@RequestBody Teste teste) {
		return teste;
	}
}