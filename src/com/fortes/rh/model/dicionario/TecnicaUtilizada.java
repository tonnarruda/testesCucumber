package com.fortes.rh.model.dicionario;

import java.util.LinkedHashMap;

@SuppressWarnings("serial")
public class TecnicaUtilizada extends LinkedHashMap
{
	public static final int MEDICAO = 1;
	public static final int LUXIMETRO = 2;
	public static final int TERMOMETRO_GLOBO = 3;
	public static final int AVALIACAO_INSTANTANEA = 4;
	public static final int AUDIOSIMETRIA = 5;
	public static final int MONITORAMENTO_ATIVO = 6;
	public static final int MONITORAMENTO_PASSIVO = 7;
	public static final int DOSIMETRIA = 8;
	public static final int HIGROMETRO = 9;
	public static final int MONITORAMENTO_BIOLOGICO = 10;

	@SuppressWarnings("unchecked")
	public TecnicaUtilizada()
	{
		put(MEDICAO, "Medição por aparelhos");
		put(LUXIMETRO, "Luximetro");
		put(TERMOMETRO_GLOBO, "Termômetro de Globo");
		put(AVALIACAO_INSTANTANEA, "Avaliação Instantânea");
		put(AUDIOSIMETRIA, "Audiosimetria");
		put(MONITORAMENTO_ATIVO, "Monitoramento Ativo");
		put(MONITORAMENTO_PASSIVO, "Monitoramento Passivo");
		put(DOSIMETRIA, "Dosimetria");
		put(HIGROMETRO, "Higrômetro");
		put(MONITORAMENTO_BIOLOGICO, "Monitoramento Biológico");
	}
}