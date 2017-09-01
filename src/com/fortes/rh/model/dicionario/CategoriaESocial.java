package com.fortes.rh.model.dicionario;

public enum CategoriaESocial {
	
	CATEGORIA_101("101", Vinculo.EMPREGO),
	CATEGORIA_104("104", Vinculo.EMPREGO),
	CATEGORIA_201("201", Vinculo.EMPREGO),
	CATEGORIA_202("202", Vinculo.EMPREGO),
	CATEGORIA_301("301", Vinculo.EMPREGO),
	CATEGORIA_302("302", Vinculo.EMPREGO),
	CATEGORIA_303("303", Vinculo.EMPREGO),
	CATEGORIA_305("305", Vinculo.EMPREGO),
	CATEGORIA_306("306", Vinculo.EMPREGO),
	CATEGORIA_307("307", Vinculo.EMPREGO),
	CATEGORIA_308("308", Vinculo.EMPREGO),
	CATEGORIA_309("309", Vinculo.EMPREGO),
	CATEGORIA_401("401", Vinculo.EMPREGO),
	CATEGORIA_410("410", Vinculo.EMPREGO),
	CATEGORIA_711("711", Vinculo.EMPREGO),
	CATEGORIA_712("712", Vinculo.EMPREGO),
	CATEGORIA_731("731", Vinculo.EMPREGO),
	CATEGORIA_734("734", Vinculo.EMPREGO),
	CATEGORIA_738("738", Vinculo.EMPREGO),
	CATEGORIA_741("741", Vinculo.EMPREGO),
	CATEGORIA_751("751", Vinculo.EMPREGO),
	CATEGORIA_761("761", Vinculo.EMPREGO),
	CATEGORIA_771("771", Vinculo.EMPREGO),
	CATEGORIA_781("781", Vinculo.EMPREGO),
	CATEGORIA_901("901", Vinculo.ESTAGIO),
	CATEGORIA_902("902", Vinculo.ESTAGIO),
	CATEGORIA_903("903", Vinculo.ESTAGIO),
	CATEGORIA_103("103", Vinculo.APRENDIZ),
	CATEGORIA_102("102", Vinculo.TEMPORARIO),
	CATEGORIA_105("105", Vinculo.TEMPORARIO),
	CATEGORIA_106("106", Vinculo.TEMPORARIO),
	CATEGORIA_721("721", Vinculo.SOCIO),
	CATEGORIA_722("722", Vinculo.SOCIO),
	CATEGORIA_723("723", Vinculo.SOCIO);
	
	String codigo;
	String colocacao;
	
	private CategoriaESocial(String codigo, String colocacao){
		this.codigo = codigo;
		this.colocacao = colocacao;
	}
	
	public String getCodigo(){
		return this.codigo;
	}
	
	public String getColocacao(){
		return this.colocacao;
	}
	
	public static String getCategoriaESocial(String codigo){
		return CategoriaESocial.valueOf("CATEGORIA_"+codigo).getColocacao();        
	}
}