package com.fortes.rh.business.desenvolvimento;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.dao.desenvolvimento.ColaboradorPresencaDao;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorPresenca;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.DiaTurma;

@Component
public class ColaboradorPresencaManagerImpl extends GenericManagerImpl<ColaboradorPresenca, ColaboradorPresencaDao> implements ColaboradorPresencaManager
{
	private ColaboradorTurmaManager colaboradorTurmaManager;
	private ColaboradorCertificacaoManager colaboradorCertificacaoManager;
	private CertificacaoManager certificacaoManager;
	
	@Autowired
	ColaboradorPresencaManagerImpl(ColaboradorPresencaDao dao) {
		setDao(dao);
	}

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

	public Collection<ColaboradorTurma> updateFrequencia(Long diaTurmaId, Long colaboradorTurmaId, boolean presenca, boolean validarCertificacao) throws Exception
	{
		ColaboradorTurma colaboradorTurma = colaboradorTurmaManager.findByProjection(colaboradorTurmaId);
		Collection<ColaboradorTurma> colaboradoresTurmaCertificados = new ArrayList<ColaboradorTurma>();
		
		if (presenca){
			DiaTurma diaTurma = new DiaTurma();
			diaTurma.setId(diaTurmaId);
			ColaboradorPresenca colaboradorPresenca = new ColaboradorPresenca(colaboradorTurma, diaTurma, true);
			getDao().save(colaboradorPresenca);
		}else{
			getDao().remove(diaTurmaId, colaboradorTurmaId);
		}
			
		getDao().getHibernateTemplateByGenericDao().flush();
		boolean colaboradorTurmaAprovado = colaboradorTurmaManager.aprovarOrReprovarColaboradorTurma(colaboradorTurma.getId(), colaboradorTurma.getTurma().getId(), colaboradorTurma.getCurso().getId());
		colaboradorTurma.setAprovado(colaboradorTurmaAprovado);
		checaCertificacao(validarCertificacao, colaboradorTurma, colaboradoresTurmaCertificados);
		
		if(validarCertificacao && colaboradoresTurmaCertificados.size() > 0)
			colaboradorCertificacaoManager.setCertificaçõesNomesInColaboradorTurmas(colaboradoresTurmaCertificados);
		
		return colaboradoresTurmaCertificados;
	}

	public Collection<ColaboradorTurma> marcarTodos(Long diaTurmaId, Long turmaId, boolean validarCertificacao)
	{
		DiaTurma diaTurma = new DiaTurma();
		diaTurma.setId(diaTurmaId);
		Collection<ColaboradorTurma> colaboradorTurmas = colaboradorTurmaManager.findByTurmaSemPresenca(turmaId, diaTurmaId);
		Collection<ColaboradorTurma> colaboradoresTurmaCertificados = new ArrayList<ColaboradorTurma>();
		
		if(colaboradorTurmas != null && colaboradorTurmas.size() > 0)
		{
			for (ColaboradorTurma colaboradorTurma : colaboradorTurmas)
			{
				if(!colaboradorCertificacaoManager.existeColaboradorCertificadoEmUmaTurmaPosterior(turmaId, colaboradorTurma.getColaborador().getId())){
					getDao().save(new ColaboradorPresenca(colaboradorTurma, diaTurma, true));
					getDao().getHibernateTemplateByGenericDao().flush();
					boolean colaboradorTurmaAprovado = colaboradorTurmaManager.aprovarOrReprovarColaboradorTurma(colaboradorTurma.getId(), colaboradorTurma.getTurma().getId(), colaboradorTurma.getCurso().getId());
					colaboradorTurma.setAprovado(colaboradorTurmaAprovado);
					checaCertificacao(validarCertificacao, colaboradorTurma, colaboradoresTurmaCertificados);
				}
			}
			
			if(validarCertificacao && colaboradoresTurmaCertificados.size() > 0)
				colaboradorCertificacaoManager.setCertificaçõesNomesInColaboradorTurmas(colaboradoresTurmaCertificados);
		}
		
		return colaboradoresTurmaCertificados;
	}

	private void checaCertificacao(boolean validarCertificacao, ColaboradorTurma colaboradorTurma, Collection<ColaboradorTurma> colaboradoresTurmaCertificados) {
		if(validarCertificacao){
			getDao().getHibernateTemplateByGenericDao().flush();
			if(colaboradorTurma.isAprovado()){
				Collection<ColaboradorCertificacao> colaboradoresCertificados = colaboradorCertificacaoManager.certificaColaborador(colaboradorTurma.getId(), null, null, certificacaoManager);
				if(colaboradoresCertificados.size() > 0)
					colaboradoresTurmaCertificados.add(colaboradorTurma);
			}else
				colaboradorCertificacaoManager.descertificarColaboradorByColaboradorTurma(colaboradorTurma.getId());
		}
	}

	public void removeByDiaTurma(Long diaTurmaId, Long turmaId, boolean validarCertificacao) throws Exception
	{
		if(validarCertificacao){
			Collection<ColaboradorTurma> colaboradorTurmas = colaboradorTurmaManager.findByTurmaPresenteNoDiaTurmaId(turmaId, diaTurmaId);
			for (ColaboradorTurma colaboradorTurma : colaboradorTurmas) {
				if(!colaboradorCertificacaoManager.existeColaboradorCertificadoEmUmaTurmaPosterior(turmaId, colaboradorTurma.getColaborador().getId())){
					getDao().remove(diaTurmaId, colaboradorTurma.getId());
					boolean colaboradorTurmaAprovado = colaboradorTurmaManager.aprovarOrReprovarColaboradorTurma(colaboradorTurma.getId(), colaboradorTurma.getTurma().getId(), colaboradorTurma.getCurso().getId());
					getDao().getHibernateTemplateByGenericDao().flush();
					if(!colaboradorTurmaAprovado)
						colaboradorCertificacaoManager.descertificarColaboradorByColaboradorTurma(colaboradorTurma.getId());
				}
			}
		}
		else{
			Collection<ColaboradorTurma> colaboradorTurmas = colaboradorTurmaManager.findByTurmaPresenteNoDiaTurmaId(turmaId, diaTurmaId);
			getDao().remove(diaTurmaId, null);
			for (ColaboradorTurma colaboradorTurma : colaboradorTurmas) {
				colaboradorTurmaManager.aprovarOrReprovarColaboradorTurma(colaboradorTurma.getId(), colaboradorTurma.getTurma().getId(), colaboradorTurma.getCurso().getId());
			}
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