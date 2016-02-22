package com.fortes.rh.business.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.dao.desenvolvimento.CertificacaoDao;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.Certificado;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.relatorio.CertificacaoTreinamentosRelatorio;
import com.fortes.rh.model.desenvolvimento.relatorio.MatrizTreinamento;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.LongUtil;
import com.fortes.web.tags.CheckBox;

public class CertificacaoManagerImpl extends GenericManagerImpl<Certificacao, CertificacaoDao> implements CertificacaoManager
{
	private FaixaSalarialManager faixaSalarialManager;
	private ColaboradorCertificacaoManager colaboradorCertificacaoManager;
	private ColaboradorAvaliacaoPraticaManager colaboradorAvaliacaoPraticaManager; 
	
	public Collection<Certificacao> findAllSelect(Long empresaId)
	{
		return getDao().findAllSelect(empresaId);
	}

	public Collection<Certificacao> findByFaixa(Long faixaId)
	{
		return getDao().findByFaixa(faixaId);
	}

	public Collection<MatrizTreinamento> getByFaixasOrCargos(String[] faixaSalarialsCheck, String[] cargosCheck)
	{
		Collection<Long> faixaIds = null;
		
		if (faixaSalarialsCheck != null && faixaSalarialsCheck.length > 0)
			faixaIds = LongUtil.arrayStringToCollectionLong(faixaSalarialsCheck);
		else
		{
			Collection<FaixaSalarial> faixas = faixaSalarialManager.findByCargos(LongUtil.arrayStringToArrayLong(cargosCheck)); 
			faixaIds = LongUtil.collectionToCollectionLong(faixas);
		}

		return getDao().findMatrizTreinamento(faixaIds);
	}
	
	public Collection<CheckBox> populaCheckBoxSemPeriodicidade(Long empresaId)
	{
		try
		{
			Collection<CheckBox> checkboxes = new ArrayList<CheckBox>();
			Collection<Certificacao> certificacoesTemp = getDao().findAllSelect(empresaId);
			CheckBox checkBox = null;
			
			for (Certificacao certificacao : certificacoesTemp)
			{
				checkBox = new CheckBox();
				checkBox.setId(certificacao.getId());
				checkBox.setNome(certificacao.getNome());
				
				if (certificacao.getPeriodicidade() != null)
					checkboxes.add(checkBox);
			}
			
			return checkboxes;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return new ArrayList<CheckBox>();
	}


	public void setFaixaSalarialManager(FaixaSalarialManager faixaSalarialManager)
	{
		this.faixaSalarialManager = faixaSalarialManager;
	}

	public Collection<CertificacaoTreinamentosRelatorio> montaCertificacao(Long certificacaoId, String[] colaboradoresCheck, Certificado certificado, Collection<Curso> cursos, boolean vencimentoPorCertificacao)
	{
		ColaboradorCertificacao colaboradorCertificacao;
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacoesPraticas;
		
		if(vencimentoPorCertificacao){
			for (Curso curso : cursos) {
				curso.setNome("Treinamento: " + curso.getNome());
				curso.setInformacao("Carga Horária: " + curso.getCargaHorariaMinutos());
			}
		}
		
		Collection<CertificacaoTreinamentosRelatorio> certificacaoTreinamentos = new ArrayList<CertificacaoTreinamentosRelatorio>(colaboradoresCheck.length);
		for (String id: colaboradoresCheck)
		{
			Certificado certificadoTmp = new Certificado();
			certificadoTmp.setImprimirMoldura(certificado.isImprimirMoldura());
			
			CertificacaoTreinamentosRelatorio certificacaoTreinamentosRelatorio = new CertificacaoTreinamentosRelatorio();
			certificacaoTreinamentosRelatorio.setCertificado(certificado);
			certificacaoTreinamentos.add(certificacaoTreinamentosRelatorio);
			certificacaoTreinamentosRelatorio.setCursos(new ArrayList<Curso>());
			certificacaoTreinamentosRelatorio.getCursos().addAll(cursos);
			
			if(vencimentoPorCertificacao)
			{
				Long colaboradorId = new Long(id);
				
				colaboradorCertificacao = colaboradorCertificacaoManager.findUltimaCertificacaoByColaboradorIdAndCertificacaoId(colaboradorId, certificacaoId);
				colaboradorAvaliacoesPraticas = new ArrayList<ColaboradorAvaliacaoPratica>();
				
				if(colaboradorCertificacao != null && colaboradorCertificacao.getId() != null)
					colaboradorAvaliacoesPraticas = colaboradorAvaliacaoPraticaManager.findByColaboradorIdAndCertificacaoId(colaboradorId, certificacaoId, colaboradorCertificacao.getId());
				
				Curso curso = null;
				for (ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica : colaboradorAvaliacoesPraticas) {
					curso = new Curso();
					curso.setNome("Avaliação Prática: " + colaboradorAvaliacaoPratica.getAvaliacaoPratica().getTitulo());
					curso.setInformacao("Nota: " + colaboradorAvaliacaoPratica.getNota());
					certificacaoTreinamentosRelatorio.getCursos().add(curso);
				}
			}

		}

		return certificacaoTreinamentos;
	}
	
	public Certificacao findByIdProjection(Long id)
	{
		return getDao().findByIdProjection(id);
	}

	public Collection<MatrizTreinamento> montaMatriz(boolean imprimirMatriz, String[] faixaSalarialId, Collection<ColaboradorTurma> colaboradorTurmas)
	{
		if (imprimirMatriz)
		{
			Collection<MatrizTreinamento> matrizTreinamentos = getByFaixasOrCargos(faixaSalarialId, null);
			for (MatrizTreinamento matrizTreinamento : matrizTreinamentos)
			{
				for (ColaboradorTurma ct : colaboradorTurmas)
				{
					if (ct.getCurso().getId().equals(matrizTreinamento.getCursoId()))
					{
						matrizTreinamento.setRealizado(ct.isAprovado());
						break;
					}
				}
			}

			return matrizTreinamentos;
		}
		else
			return null;
	}

	public Collection<Certificacao> findAllSelect(Integer page, Integer pagingSize, Long id, String nomeBusca) {
		return getDao().findAllSelect(page, pagingSize, id, nomeBusca);
	}

	public Integer getCount(Long empresaId, String nomeBusca) 
	{
		return getDao().getCount(empresaId, nomeBusca);
	}

	public void deleteByFaixaSalarial(Long[] faixaIds) throws Exception {
		getDao().deleteByFaixaSalarial(faixaIds);
	}

	public Collection<Colaborador> findColaboradoresNaCertificacao(Long certificacaoId) 
	{
		return getDao().findColaboradoresNaCertificacao(certificacaoId);
	}

	public Collection<Certificacao> findAllSelectNotCertificacaoIdAndCertificacaoPreRequisito(Long empresaId, Long certificacaoId) {
		Collection<Certificacao> certificacaos =  getDao().findAllSelectNotCertificacaoIdAndCertificacaoPreRequisito(empresaId, certificacaoId);
		
		if(certificacaoId != null)
			certificacaos.removeAll(certificacaoDescendenteRecursivo(certificacaos, certificacaoId));
		
		return certificacaos;
	}

	private Collection<Certificacao> certificacaoDescendenteRecursivo(Collection<Certificacao> certificacoes, Long certificacaoId){
		Collection<Certificacao> certificacaosDescendente = new ArrayList<Certificacao>();
		
		for (Certificacao certificacao : certificacoes){ 
			if(certificacao.getCertificacaoPreRequisito() != null && certificacao.getCertificacaoPreRequisito().getId() != null && certificacaoId.equals(certificacao.getCertificacaoPreRequisito().getId())){
				certificacaosDescendente.add(certificacao);
				certificacaosDescendente.addAll(certificacaoDescendenteRecursivo(certificacoes, certificacao.getId()));
			}
		}
		
		return certificacaosDescendente;
	}
	
	public Collection<Certificacao> findByCursoId(Long cursoId) {
		return getDao().findByCursoId(cursoId);
	}

	public Collection<Certificacao> findDependentes(Long certificacaoId) {
		return getDao().findDependentes(certificacaoId);
	}

	public Collection<Certificacao> findOsQuePossuemAvaliacaoPratica(Long empresaId) {
		return getDao().findOsQuePossuemAvaliacaoPratica(empresaId);
	}

	public void setColaboradorCertificacaoManager(ColaboradorCertificacaoManager colaboradorCertificacaoManager) {
		this.colaboradorCertificacaoManager = colaboradorCertificacaoManager;
	}

	public void setColaboradorAvaliacaoPraticaManager(ColaboradorAvaliacaoPraticaManager colaboradorAvaliacaoPraticaManager) {
		this.colaboradorAvaliacaoPraticaManager = colaboradorAvaliacaoPraticaManager;
	}
}