package br.com.unb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.unb.domain.PistarServiceFactory;
import br.com.unb.service.PistarService;

@SpringBootTest
class PistarModelFactoryApplicationTests {
	@Autowired
	private PistarService service;

	@Test
	void contextLoads() {
		PistarServiceFactory pistarServiceFactory = new PistarServiceFactory();
//		pistarServiceFactory.setUrl("http://localhost:8080/executePistarFactory");
//		pistarServiceFactory.setMethod(RequestMethod.POST.name());
//		pistarServiceFactory.setData(pistarServiceFactory);
		
		pistarServiceFactory.setUrl("http://localhost:8080/teste");
		pistarServiceFactory.setMethod(RequestMethod.GET.name());
		pistarServiceFactory.setData(null);
		
		Object result = this.service.executePistarFactoryService(pistarServiceFactory);

		System.out.println(result);
//		System.out.println(result.getStatusCodeValue());
	}

}
