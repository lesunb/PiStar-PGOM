package br.com.unb.domain;

public class PistarServiceFactory {
	private String  method;
	private String url;
	private Object data;
	private String resultNameFile;
	private String paramType;
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getResultNameFile() {
		return resultNameFile;
	}
	public void setResultNameFile(String resultPath) {
		this.resultNameFile = resultPath;
	}
	public String getParamType() {
		return paramType;
	}
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
}