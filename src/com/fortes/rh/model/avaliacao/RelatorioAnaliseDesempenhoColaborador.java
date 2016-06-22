package com.fortes.rh.model.avaliacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import com.fortes.rh.model.captacao.NivelCompetencia;
import com.fortes.rh.model.geral.Colaborador;
import com.ibm.icu.text.DecimalFormat;

public class RelatorioAnaliseDesempenhoColaborador
{
	private Colaborador avaliado;
	private Integer valorMaximoGrafico = 10;
	private Integer notaMinimaMediaGeralCompetencia = 0;
	private Collection<ResultadoCompetenciaColaborador> resultadosCompetenciaColaborador = new ArrayList<ResultadoCompetenciaColaborador>();
	@SuppressWarnings("unused")
	private Collection<ResultadoCompetencia> resultadoCompetenciaFinal = new ArrayList<ResultadoCompetencia>();
	private Collection<NivelCompetencia> niveisCompeteencias = new ArrayList<NivelCompetencia>();
	
	public static String AUTOAVALIACAO = "Auto-Avaliação";
	public static String OUTROSAVALIADORES = "Outros Avaliadores";
	public static String MEDIA = "Média";
	public static String MEDIAGERAL = "Média Geral";

	public Colaborador getAvaliado() {
		return avaliado;
	}
	public void setAvaliado(Colaborador avaliado) {
		this.avaliado = avaliado;
	}
	public Collection<ResultadoCompetenciaColaborador> getResultadosCompetenciaColaborador() {
		return resultadosCompetenciaColaborador;
	}
	public void setResultadosCompetenciaColaborador(Collection<ResultadoCompetenciaColaborador> resultadosCompetenciaColaborador) {
		this.resultadosCompetenciaColaborador = resultadosCompetenciaColaborador;
	}
	public Integer getValorMaximoGrafico() {
		return valorMaximoGrafico;
	}
	public void setValorMaximoGrafico(Integer valorMaximoGrafico) {
		this.valorMaximoGrafico = valorMaximoGrafico;
	}
	public Integer getNotaMinimaMediaGeralCompetencia() {
		return notaMinimaMediaGeralCompetencia;
	}
	public void setNotaMinimaMediaGeralCompetencia(Integer notaMinimaMediaGeralCompetencia) {
		this.notaMinimaMediaGeralCompetencia = notaMinimaMediaGeralCompetencia;
	}
	public Collection<ResultadoCompetencia> getResultadoCompetenciaFinal() {
		LinkedList<ResultadoCompetencia> resultadoCompetenciaRetorno = new LinkedList<ResultadoCompetencia>();
		LinkedHashMap<String, Integer> mapResultadoCompetenciaRetorno = new LinkedHashMap<String, Integer>();
		Integer acimaDaNotaMinimaMediaGeral = 0, totalDeResultados = 0;
		
		mapResultadoCompetenciaRetorno.put(AUTOAVALIACAO, 0);
		
		for (ResultadoCompetenciaColaborador resultadoCompetenciaColaborador : resultadosCompetenciaColaborador) {
			for (ResultadoCompetencia resultadoCompetencia : resultadoCompetenciaColaborador.getResultadoCompetencias()) {
				if(resultadoCompetencia.getNome().equals(MEDIA)) continue;

				totalDeResultados++;
				
				if(!mapResultadoCompetenciaRetorno.containsKey(resultadoCompetencia.getNome()))
					mapResultadoCompetenciaRetorno.put(resultadoCompetencia.getNome(), 0);
					
				if(resultadoCompetencia.getOrdem() >= notaMinimaMediaGeralCompetencia){
					mapResultadoCompetenciaRetorno.put(resultadoCompetencia.getNome(), mapResultadoCompetenciaRetorno.get(resultadoCompetencia.getNome()) + 1);
					acimaDaNotaMinimaMediaGeral++;
				}
			}
		}
		if(mapResultadoCompetenciaRetorno.get(AUTOAVALIACAO) == 0) mapResultadoCompetenciaRetorno.remove(AUTOAVALIACAO);
		
		if(mapResultadoCompetenciaRetorno.containsKey(OUTROSAVALIADORES)){
			Integer resultadoOuros = mapResultadoCompetenciaRetorno.get(OUTROSAVALIADORES);
			mapResultadoCompetenciaRetorno.remove(OUTROSAVALIADORES);
			mapResultadoCompetenciaRetorno.put(OUTROSAVALIADORES, resultadoOuros);
		}
		
		for (String nome : mapResultadoCompetenciaRetorno.keySet()) {
			Double percentual = (mapResultadoCompetenciaRetorno.get(nome).doubleValue() / resultadosCompetenciaColaborador.size()) * 100;
			resultadoCompetenciaRetorno.add(new ResultadoCompetencia(nome, Double.valueOf(new DecimalFormat("#.##").format(percentual))));
		}
		
		Double percentual = (acimaDaNotaMinimaMediaGeral.doubleValue()/totalDeResultados) * 100;
		resultadoCompetenciaRetorno.add(new ResultadoCompetencia(MEDIAGERAL, Double.valueOf(new DecimalFormat("#.##").format(percentual))));
		
		return resultadoCompetenciaRetorno;
	}
	public Collection<NivelCompetencia> getNiveisCompeteencias() {
		return niveisCompeteencias;
	}
	public void setNiveisCompeteencias(
			Collection<NivelCompetencia> niveisCompeteencias) {
		this.niveisCompeteencias = niveisCompeteencias;
	}
	public Integer getTamanhoCollectionNiveisCompeteencias(){
		return this.niveisCompeteencias.size(); 
	} 
}
