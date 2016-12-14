package com.fortes.rh.business.avaliacao;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.annotations.TesteAutomatico;
import com.fortes.rh.dao.avaliacao.PeriodoExperienciaDao;
import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.avaliacao.PeriodoExperiencia;
import com.fortes.rh.model.avaliacao.relatorio.FaixaPerformanceAvaliacaoDesempenho;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.CheckListBoxUtil;
import com.fortes.web.tags.CheckBox;

public class PeriodoExperienciaManagerImpl extends GenericManagerImpl<PeriodoExperiencia, PeriodoExperienciaDao> implements PeriodoExperienciaManager
{
	public Collection<PeriodoExperiencia> findAllSelect(Long empresaId, boolean orderDiasDesc)
	{
		return getDao().findAllSelect(empresaId, orderDiasDesc, null);
	}

	public Collection<PeriodoExperiencia> findByIdsOrderDias(Long[] PeriodoExperienciaIds)
	{
		return getDao().findByIdsOrderDias(PeriodoExperienciaIds);
	}

	public String findRodapeDiasDoPeriodoDeExperiencia(Collection<PeriodoExperiencia> periodoExperiencias) {
		String rodapeRelatorioPeriodoExperiencia = ""; 
		boolean decisao = false;
		
		if(periodoExperiencias.size() > 0  )
			rodapeRelatorioPeriodoExperiencia = "Períodos de Acompanhamento de Experiência "; 
		
		for (PeriodoExperiencia periodoExperiencia : periodoExperiencias) {
		
			if (decisao)
				rodapeRelatorioPeriodoExperiencia += ", ";
			
			rodapeRelatorioPeriodoExperiencia += periodoExperiencia.getDias().toString();
			decisao = true;
		}
		
		
		return rodapeRelatorioPeriodoExperiencia;
	}
	
	public Collection<CheckBox> populaCheckBox(Long empresaId)
	{
		try
		{
			Collection<PeriodoExperiencia> periodos = getDao().findAllSelect(empresaId, false, true);
			return CheckListBoxUtil.populaCheckListBox(periodos, "getId", "getDiasDescricao", null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return new ArrayList<CheckBox>();
	}

	public Collection<CheckBox> populaCheckBoxDistinctDias(Long empresaId) {
		try
		{
			Collection<PeriodoExperiencia> periodos = getDao().findAllSelectDistinctDias(empresaId);
			Collection<CheckBox> listBox = new ArrayList<CheckBox>();
			for (PeriodoExperiencia periodo : periodos)
			{
				CheckBox checkBox = new CheckBox();
				checkBox.setId(periodo.getDias().longValue());
				checkBox.setNome(periodo.getDias() + " dias");
				listBox.add(checkBox);
			}
			
			return listBox;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return new ArrayList<CheckBox>();
	}

	public Collection<FaixaPerformanceAvaliacaoDesempenho> agrupaFaixaAvaliacao(Collection<Colaborador> colaboradores, String[] percentualInicial, String[] percentualFinal) throws Exception, ColecaoVaziaException 
	{
		Collection<FaixaPerformanceAvaliacaoDesempenho> faixas = new ArrayList<FaixaPerformanceAvaliacaoDesempenho>();
		
		for (int i = 0; i < percentualInicial.length; i++) 
		{
			if(percentualInicial == null || percentualFinal == null || StringUtils.isEmpty(percentualInicial[i]) || StringUtils.isEmpty(percentualFinal[i]))
				continue;
				
			double percentIni = Double.valueOf(percentualInicial[i]);
			double percentFim = Double.valueOf(percentualFinal[i]);
			
			if(percentIni < percentFim)
				faixas.add(new FaixaPerformanceAvaliacaoDesempenho(percentIni, percentFim, colaboradores.size()));
		}

		if(faixas.isEmpty())
			throw new ColecaoVaziaException("Não existem colaboradores dentro das faixas solicitadas.");
		
		for (Colaborador colaborador : colaboradores)
		{
			FaixaPerformanceAvaliacaoDesempenho.selecionaColaborador(colaborador, faixas);
		}
		
		return faixas;
	}

	public PeriodoExperiencia clonarPeriodoExperiencia(Long id, Empresa empresa) 
	{
		PeriodoExperiencia periodoExperienciaClone = (PeriodoExperiencia) getDao().findById(id).clone();
		
		Collection<PeriodoExperiencia> periodoExperiencias =  getDao().findAllSelect(empresa.getId(), false, true);
		
		boolean existe = false;
		for (PeriodoExperiencia periodoExperiencia : periodoExperiencias) 
		{
			if (periodoExperiencia.getDias().equals(periodoExperienciaClone.getDias()) && periodoExperiencia.getDescricao().equals(periodoExperienciaClone.getDescricao()))
			{
				periodoExperienciaClone = periodoExperiencia;
				existe = true;
				break;
			}
		}
		if(!existe)
		{
			periodoExperienciaClone.setId(null);
			periodoExperienciaClone.setEmpresa(empresa);
			periodoExperienciaClone.setAtivo(true);
			getDao().save(periodoExperienciaClone);
		}
		
		return periodoExperienciaClone;
	}

	@TesteAutomatico
	public Collection<PeriodoExperiencia> findAllAtivos() {
		return getDao().findAllAtivos();
	}
	
	public Collection<PeriodoExperiencia> findPeriodosAtivosAndPeriodosConfiguradosParaColaborador(Long empresaId, Long colaboradorId){
		return getDao().findPeriodosAtivosAndPeriodosConfiguradosParaColaborador(empresaId, colaboradorId);
	}
	
	public Collection<PeriodoExperiencia> findPeriodosAtivosAndPeriodoDaAvaliacaoId(Long empresaId, Long avaliacaoId){
		return getDao().findPeriodosAtivosAndPeriodoDaAvaliacaoId(empresaId, avaliacaoId);
	}
}