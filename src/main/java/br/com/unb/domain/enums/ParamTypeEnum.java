package br.com.unb.domain.enums;

public enum ParamTypeEnum {
	BODY_PARAM("bodyParam"),
	QUERY_PARAM("queryParam");

	private String type;
	
	ParamTypeEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
}
