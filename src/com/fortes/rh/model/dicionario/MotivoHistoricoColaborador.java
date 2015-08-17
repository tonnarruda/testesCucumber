package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class MotivoHistoricoColaborador extends LinkedHashMap<String, String>
{
	//motivo tambem pode ser setado pelo dicionario OrigemCandidato
	public static final String CONTRATADO = "C";
	public static final String PROMOCAO_HORIZONTAL = "H";
	public static final String PROMOCAO = "P";
	public static final String REAJUSTE = "R";
	public static final String TRANSFERENCIA = "T";
	public static final String IMPORTADO = "I";
	public static final String MUDANCA_FUNCAO = "F";
	public static final String DISSIDIO = "D";
	public static final String SEM_MOTIVO = "S";

	public MotivoHistoricoColaborador()
	{
		put(CONTRATADO, "Contratado");
		put(PROMOCAO_HORIZONTAL, "Promoção Horizontal");
		put(PROMOCAO, "Promoção");
		put(REAJUSTE, "Reajuste");
		put(TRANSFERENCIA, "Transferência");
		put(IMPORTADO, "Importado do Fortes Pessoal");
		put(MUDANCA_FUNCAO, "Mudança de Função");
		put(DISSIDIO, "Dissídio");
		put(SEM_MOTIVO, "Sem Motivo");
	}
}
