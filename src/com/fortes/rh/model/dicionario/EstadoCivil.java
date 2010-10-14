package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

public class EstadoCivil extends LinkedHashMap
{

	public static final String SOLTEIRO = "01";
	public static final String CASADO_COMUNHAO_UNIVERSAL = "02";
	public static final String CASADO_COMUNHAO_PARCIAL = "03";
	public static final String CASADO_SEPARACAO_DE_BENS = "04";
	public static final String VIUVO = "05";
	public static final String SEPARADO_JUDIALMENTE = "06";
	public static final String DIVORCIADO = "07";
	public static final String CASADO_REGIME_TOTAL = "08";
	public static final String CASADO_REGIME_MISTO_ESPECIAL = "09";
	public static final String UNIAO_ESTAVEL = "10";

	@SuppressWarnings("unchecked")
	public EstadoCivil()
	{
		put(CASADO_COMUNHAO_PARCIAL, "Casado - Comunhão Parcial");
		put(CASADO_COMUNHAO_UNIVERSAL, "Casado - Comunhão Universal");
		put(CASADO_REGIME_TOTAL, "Casado - Regime Total");
		put(CASADO_REGIME_MISTO_ESPECIAL, "Casado - Regime Misto ou Especial");
		put(CASADO_SEPARACAO_DE_BENS, "Casado - Separação de Bens");
		put(DIVORCIADO, "Divorciado");
		put(SEPARADO_JUDIALMENTE, "Separado Judicialmente");
		put(SOLTEIRO, "Solteiro");
		put(UNIAO_ESTAVEL, "União Estável");
		put(VIUVO, "Viúvo");
	}
}