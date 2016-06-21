package com.fortes.rh.model.avaliacao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
		HashMap<String, Integer> mapResultadoCompetenciaRetorno = new HashMap<String, Integer>();
		Integer acimaDaNotaMinimaMediaGeral = 0;
		Integer totalDeResultados = 0;
		
		for (ResultadoCompetenciaColaborador resultadoCompetenciaColaborador : resultadosCompetenciaColaborador) {
			for (ResultadoCompetencia resultadoCompetencia : resultadoCompetenciaColaborador.getResultadoCompetencias()) {
				if(resultadoCompetencia.getNome().equals("Média"))
					continue;

				totalDeResultados++;
				
				if(!mapResultadoCompetenciaRetorno.containsKey(resultadoCompetencia.getNome()))
					mapResultadoCompetenciaRetorno.put(resultadoCompetencia.getNome(), 0);
					
				if(resultadoCompetencia.getOrdem() >= notaMinimaMediaGeralCompetencia){
					mapResultadoCompetenciaRetorno.put(resultadoCompetencia.getNome(), mapResultadoCompetenciaRetorno.get(resultadoCompetencia.getNome()) + 1);
					acimaDaNotaMinimaMediaGeral++;
				}
			}
		}
		
		for (String nome : mapResultadoCompetenciaRetorno.keySet()) {
			Double percentual = (mapResultadoCompetenciaRetorno.get(nome).doubleValue() / resultadosCompetenciaColaborador.size()) * 100;
			DecimalFormat formato = new DecimalFormat("#.##");      
			resultadoCompetenciaRetorno.add(new ResultadoCompetencia(nome, Double.valueOf(formato.format(percentual))));
		}
		
		Double percentual = (acimaDaNotaMinimaMediaGeral.doubleValue()/totalDeResultados) * 100;
		DecimalFormat formato = new DecimalFormat("#.##");      
		resultadoCompetenciaRetorno.add(new ResultadoCompetencia("Média Geral", Double.valueOf(formato.format(percentual))));
		
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
