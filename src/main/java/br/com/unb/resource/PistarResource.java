package br.com.unb.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.unb.domain.PistarServiceFactory;
import br.com.unb.service.PistarService;

@RestController
public class PistarResource {
	
	@Autowired
	private PistarService service;
	
	@RequestMapping(value = "/executePistarFactoryService", method = RequestMethod.POST)
	public Object executePistarFactoryService(@RequestBody PistarServiceFactory pistarFactory) {
		return this.service.executePistarFactoryService(pistarFactory);
	}
}