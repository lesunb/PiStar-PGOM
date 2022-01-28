package br.com.unb.service;

import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.unb.domain.PistarServiceFactory;
import br.com.unb.domain.enums.ParamTypeEnum;
import br.com.unb.exception.ResponseException;
import br.com.unb.utils.ConvertUtils;
import br.com.unb.utils.ManageWriter;

@Service
public class PistarService {

	public Object executePistarFactoryService(PistarServiceFactory pistarServiceFactory) {
		try {
			RestTemplate template = new RestTemplate();
			Object data = pistarServiceFactory.getData();
			String urlString = pistarServiceFactory.getUrl();
			String type = pistarServiceFactory.getParamType();
			HttpEntity<Object> request = new HttpEntity<>(data);
			UriComponents uri = this.insertParams(urlString, type, data);
			HttpMethod method = HttpMethod.valueOf(pistarServiceFactory.getMethod());
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

	@SuppressWarnings("unchecked")
	private UriComponents insertParams(String urlString, String typeParam, Object data) {
		try {
			URL url = new URL(urlString);
			String scheme = "http";

			if (urlString.contains("https")) {
				scheme = "https";
			}

			UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance()
					.host(url.getHost() + ":" + url.getPort()).path(url.getPath()).scheme(scheme);

			if (typeParam != null && typeParam.equals(ParamTypeEnum.QUERY_PARAM.getType())) {
				ObjectMapper objMapper = new ObjectMapper();
				Map<String, Object> map = objMapper.convertValue(data, Map.class);
				Set<Entry<String, Object>> entries = map.entrySet();
				for (Entry<String, Object> entry : entries) {
					uriComponentsBuilder.queryParam(entry.getKey(), entry.getValue());
				}
			}

			UriComponents uri = uriComponentsBuilder.build();

			return uri;
		} catch (Exception e) {
			throw new ResponseException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void generateModelZip(Object results, String fileName) {
		try {
			String pathZip = "src/main/webapp/results/zip/";
			String pathFiles = "src/main/webapp/results/files/";
			String pathOutput = pathZip + fileName + ".zip";

			if (results != null) {
				String result = null;
				ManageWriter.cleanFolder(pathZip);
				ManageWriter.cleanFolder(pathFiles);
				if (results instanceof String) {
					result = ConvertUtils.objectToString(results);
					ManageWriter.generateFile(pathFiles, result);
				} else {
					result = new ObjectMapper().writeValueAsString(results);
					ManageWriter.generateFile(pathFiles, "resultFile.txt", result);

					ObjectMapper objMapper = new ObjectMapper();
					Map<String, Object> map = objMapper.convertValue(results, Map.class);
					Set<Entry<String, Object>> entries = map.entrySet();
					for (Entry<String, Object> entry : entries) {
						String value = new ObjectMapper().writeValueAsString(entry.getValue());
						ManageWriter.generateFile(pathFiles, entry.getKey() + ".txt", value);
					}
				}
				ManageWriter.toCompactFolder(pathFiles, pathOutput);
			}
		} catch (Exception ex) {
			throw new ResponseException(ex.getMessage());
		}
	}

}
