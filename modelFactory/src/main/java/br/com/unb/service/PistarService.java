package br.com.unb.service;

import java.net.URL;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.unb.domain.PistarServiceFactory;

@Service
public class PistarService {

	public Object executePistarFactoryService(PistarServiceFactory pistarServiceFactory) {
		try {
			HttpMethod method = HttpMethod.valueOf(pistarServiceFactory.getMethod());
			URL url = new URL(pistarServiceFactory.getUrl());
			String scheme = "http";

			if (pistarServiceFactory.getUrl().contains("https")) {
				scheme = "https";
			}

			RestTemplate template = new RestTemplate();
			HttpEntity<Object> request = new HttpEntity<>(pistarServiceFactory.getData());
			UriComponents uri = UriComponentsBuilder.newInstance()
					.host(url.getHost() + ":" + url.getPort())
					.path(url.getPath())
					.scheme(scheme)
					.build();

			ResponseEntity<Object> entity = template.exchange(uri.toUriString(), method, request, Object.class);
			
			return entity.getBody();
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

}

