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
import br.com.unb.exception.ResponseException;
import br.com.unb.utils.ConvertUtils;
import br.com.unb.utils.ManageWriter;

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
			UriComponents uri = UriComponentsBuilder.newInstance().host(url.getHost() + ":" + url.getPort())
					.path(url.getPath()).scheme(scheme).build();

			Object result = null;
			try {
				// Primeira tentativa pra um serviço que não retorne uma response como Object
				ResponseEntity<Object> entity = template.exchange(uri.toUriString(), method, request, Object.class);
				result = entity.getBody();
			} catch (Exception ex) {
				ResponseEntity<String> entity = template.exchange(uri.toUriString(), method, request, String.class);
				result = entity.getBody();
			}

			this.generateModelZip(result, pistarServiceFactory.getResultNameFile());
			return result;
		} catch (Exception ex) {
			throw new ResponseException(ex);
		}
	}

	private void generateModelZip(Object results, String fileName) {
		String pathOutput = "src/main/webapp/resultsZip/" + fileName + ".zip";

		try {
			if (results != null) {
				String result = null;
				if (results instanceof String) {
					result = ConvertUtils.objectToString(results);
				} else {
					result = ConvertUtils.objectToJSON(results);
					result = ConvertUtils.objectToString(result);
				}
				ManageWriter.toCompact("teste", pathOutput);;
			}
		} catch (Exception ex) {
			throw new ResponseException(ex.getMessage());
		}
	}

}
