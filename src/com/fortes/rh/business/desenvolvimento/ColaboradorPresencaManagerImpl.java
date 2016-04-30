package com.fortes.rh.business.desenvolvimento;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.desenvolvimento.ColaboradorPresencaDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorPresenca;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.DiaTurma;
import com.fortes.rh.thread.certificaColaboradorThread;

public class ColaboradorPresencaManagerImpl extends GenericManagerImpl<ColaboradorPresenca, ColaboradorPresencaDao> implements ColaboradorPresencaManager
{
	private ColaboradorTurmaManager colaboradorTurmaManager;
	private ColaboradorCertificacaoManager colaboradorCertificacaoManager;
	private CertificacaoManager certificacaoManager;

	public void setColaboradorTurmaManager(ColaboradorTurmaManager colaboradorTurmaManager)
	{
		this.colaboradorTurmaManager = colaboradorTurmaManager;
	}

	public Collection<ColaboradorPresenca> findPresencaByTurma(Long id)
	{
		return getDao().findPresencaByTurma(id);
	}

	public boolean existPresencaByTurma(Long turmaId)
	{
		return !getDao().existPresencaByTurma(turmaId).isEmpty();
	}

	public void updateFrequencia(Long diaTurmaId, Long colaboradorTurmaId, boolean presenca, boolean validarCertificacao) throws Exception
	{
		ColaboradorTurma colaboradorTurma = colaboradorTurmaManager.findByProjection(colaboradorTurmaId);
		
		if (presenca)
		{
			DiaTurma diaTurma = new DiaTurma();
			diaTurma.setId(diaTurmaId);
			ColaboradorPresenca colaboradorPresenca = new ColaboradorPresenca(colaboradorTurma, diaTurma, true);
			getDao().save(colaboradorPresenca);
			getDao().getHibernateTemplateByGenericDao().flush();
			
			colaboradorTurmaManager.aprovarOrReprovarColaboradorTurma(colaboradorTurma.getId(), colaboradorTurma.getTurma().getId(), colaboradorTurma.getCurso().getId());
			
			if(validarCertificacao)
				new certificaColaboradorThread(colaboradorCertificacaoManager, colaboradorTurma.getId(), certificacaoManager).start();
		}
		else{
			getDao().remove(diaTurmaId, colaboradorTurmaId);
			colaboradorTurmaManager.aprovarOrReprovarColaboradorTurma(colaboradorTurma.getId(), colaboradorTurma.getTurma().getId(), colaboradorTurma.getCurso().getId());
			colaboradorCertificacaoManager.descertificarColaboradorByColaboradorTurma(colaboradorTurmaId, false);
		}
		
	}

	public void marcarTodos(Long diaTurmaId, Long turmaId, boolean validarCertificacao)
	{
		DiaTurma diaTurma = new DiaTurma();
		diaTurma.setId(diaTurmaId);
		Collection<ColaboradorTurma> colaboradorTurmas = colaboradorTurmaManager.findByTurmaSemPresenca(turmaId, diaTurmaId);
		
		if(colaboradorTurmas != null && colaboradorTurmas.size() > 0)
		{
			for (ColaboradorTurma colaboradorTurma : colaboradorTurmas)
			{
				if(!colaboradorCertificacaoManager.existeColaboradorCertificadoEmUmaTurmaPosterior(turmaId, colaboradorTurma.getColaborador().getId())){
					getDao().save(new ColaboradorPresenca(colaboradorTurma, diaTurma, true));
					colaboradorTurmaManager.aprovarOrReprovarColaboradorTurma(colaboradorTurma.getId(), colaboradorTurma.getTurma().getId(), colaboradorTurma.getCurso().getId());
					getDao().getHibernateTemplateByGenericDao().flush();
						
					if(validarCertificacao)
						new certificaColaboradorThread(colaboradorCertificacaoManager, colaboradorTurma.getId(), certificacaoManager).start();
				}
			}
		}
	}

	public void removeByDiaTurma(Long diaTurmaId, Long turmaId, boolean validarCertificacao) throws Exception
	{
		if(validarCertificacao){
			Collection<ColaboradorTurma> colaboradorTurmas = colaboradorTurmaManager.findByTurmaPresenteNoDiaTurmaId(turmaId, diaTurmaId);
			for (ColaboradorTurma colaboradorTurma : colaboradorTurmas) {
				if(!colaboradorCertificacaoManager.existeColaboradorCertificadoEmUmaTurmaPosterior(turmaId, colaboradorTurma.getColaborador().getId())){
					getDao().remove(diaTurmaId, colaboradorTurma.getId());
					colaboradorTurmaManager.aprovarOrReprovarColaboradorTurma(colaboradorTurma.getId(), colaboradorTurma.getTurma().getId(), colaboradorTurma.getCurso().getId());
					colaboradorTurma = colaboradorTurmaManager.findByProjection(colaboradorTurma.getId());
					if(!colaboradorTurma.isAprovado())
						colaboradorCertificacaoManager.descertificarColaboradorByColaboradorTurma(colaboradorTurma.getId(), false);
				}
			}
		}
		else{
			getDao().remove(diaTurmaId, null);
		}
	}

	public Integer qtdDiaPresentesTurma(Date dataIni, Date dataFim, Long[] empresaIds, Long[] cursoIds, Long[] areasIds, Long[] estabelecimentosIds)
	{
		return getDao().qtdDiaPresentesTurma(dataIni, dataFim, empresaIds, cursoIds, areasIds, estabelecimentosIds);
	}

	public String calculaFrequencia(Long colaboradorTurmaId, Integer qtdDias)
	{
		if(qtdDias.equals(0))
			return "0,00";
		
		Integer qtdPresenca = getDao().getCount(new String[]{"colaboradorTurma.id"}, new Object[]{colaboradorTurmaId});
		
		Double resultado = qtdPresenca.doubleValue() / qtdDias.doubleValue();
		
		DecimalFormat formata = (DecimalFormat) DecimalFormat.getInstance(new Locale("pt", "BR"));
		formata.applyPattern("#0.00");
		return formata.format(resultado * 100);
	}

	public Collection<ColaboradorTurma> preparaLinhaEmBranco(Collection<ColaboradorTurma> colaboradorTurmas, int qtdMaxLinha, Long estabelecimentoId)
	{
		if(colaboradorTurmas.size() < qtdMaxLinha)
		{
			int linhasEmBranco = qtdMaxLinha - colaboradorTurmas.size();

			for(int i = 0; i < linhasEmBranco; i++)
			{
				ColaboradorTurma colaboradorTurmaAux = new ColaboradorTurma();
				colaboradorTurmaAux.setProjectionEstabelecimentoId(estabelecimentoId);
				colaboradorTurmas.add(colaboradorTurmaAux);
			}
		}
		
		return colaboradorTurmas;
	}

	public void removeByColaboradorTurma(Long[] colaboradorTurmaIds)
	{
		getDao().removeByColaboradorTurma(colaboradorTurmaIds);
	}

	public Integer qtdColaboradoresPresentesByDiaTurmaIdAndEstabelecimentoId(Long diaTurmaId, Long estabelecimentoId) 
	{
		return getDao().qtdColaboradoresPresentesByDiaTurmaIdAndEstabelecimentoId(diaTurmaId, estabelecimentoId);
	}

	public void setColaboradorCertificacaoManager(
			ColaboradorCertificacaoManager colaboradorCertificacaoManager) {
		this.colaboradorCertificacaoManager = colaboradorCertificacaoManager;
	}

	public void setCertificacaoManager(CertificacaoManager certificacaoManager) {
		this.certificacaoManager = certificacaoManager;
	} 
	
}