package com.fortes.rh.business.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.business.GenericManagerImpl;
import com.fortes.rh.business.avaliacao.AvaliacaoPraticaManager;
import com.fortes.rh.business.cargosalario.FaixaSalarialManager;
import com.fortes.rh.dao.desenvolvimento.CertificacaoDao;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.Certificado;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.relatorio.CertificacaoTreinamentosRelatorio;
import com.fortes.rh.model.desenvolvimento.relatorio.MatrizTreinamento;
import com.fortes.rh.util.LongUtil;
import com.fortes.web.tags.CheckBox;

@Component
public class CertificacaoManagerImpl extends GenericManagerImpl<Certificacao, CertificacaoDao> implements CertificacaoManager
{
	private FaixaSalarialManager faixaSalarialManager;
	private ColaboradorCertificacaoManager colaboradorCertificacaoManager;
	private ColaboradorAvaliacaoPraticaManager colaboradorAvaliacaoPraticaManager; 
	private AvaliacaoPraticaManager avaliacaoPraticaManager;
	
	@Autowired
	CertificacaoManagerImpl(CertificacaoDao dao) {
		setDao(dao);
	}
	
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
	
	public Collection<CheckBox> populaCheckBox(Long empresaId){
		try	{
			Collection<CheckBox> checkboxes = new ArrayList<CheckBox>();
			Collection<Certificacao> certificacoesTemp = getDao().findAllSelect(empresaId);
			CheckBox checkBox = null;
			
			for (Certificacao certificacao : certificacoesTemp)	{
				checkBox = new CheckBox();
				checkBox.setId(certificacao.getId());
				checkBox.setNome(certificacao.getNome());
				checkboxes.add(checkBox);
			}
			
			return checkboxes;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ArrayList<CheckBox>();
	}


	public void setFaixaSalarialManager(FaixaSalarialManager faixaSalarialManager)
	{
		this.faixaSalarialManager = faixaSalarialManager;
	}

	public Collection<CertificacaoTreinamentosRelatorio> montaCertificacao(Long certificacaoId, String[] colaboradoresCheck, Certificado certificado, Collection<Curso> cursos, boolean vencimentoPorCertificacao){
		for (Curso curso : cursos) {
			curso.setNome("Treinamento: " + curso.getNome());
			curso.setInformacao("Carga Horária: " + curso.getCargaHorariaMinutos());
		}

		Collection<AvaliacaoPratica> avaliacoesPraticas = new ArrayList<AvaliacaoPratica>();
		if(vencimentoPorCertificacao)
			avaliacoesPraticas = avaliacaoPraticaManager.findByCertificacaoId(certificacaoId);
		
		Collection<CertificacaoTreinamentosRelatorio> certificacaoTreinamentos = new ArrayList<CertificacaoTreinamentosRelatorio>(colaboradoresCheck.length);
		for (String colabId: colaboradoresCheck){
			CertificacaoTreinamentosRelatorio certificacaoTreinamentosRelatorio = new CertificacaoTreinamentosRelatorio();
			certificacaoTreinamentosRelatorio.setCertificado(certificado);
			certificacaoTreinamentosRelatorio.setCursos(new ArrayList<Curso>());
			certificacaoTreinamentosRelatorio.getCursos().addAll(cursos);
			certificacaoTreinamentos.add(certificacaoTreinamentosRelatorio);

			if(vencimentoPorCertificacao)
				montaAvaliacoesPraticas(colabId, certificacaoId, avaliacoesPraticas, certificacaoTreinamentosRelatorio);
		}
		return certificacaoTreinamentos;
	}

	private void montaAvaliacoesPraticas(String colabId, Long certificacaoId, Collection<AvaliacaoPratica> avaliacoesPraticas, CertificacaoTreinamentosRelatorio certificacaoTreinamentosRelatorio) throws NumberFormatException {
		Curso curso = null;
		Long colaboradorId = new Long(colabId);
		Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacoesPraticas = new ArrayList<ColaboradorAvaliacaoPratica>();

		Long colaboradorCertificacaoId = null;
		ColaboradorCertificacao colaboradorCertificacao = colaboradorCertificacaoManager.findUltimaCertificacaoByColaboradorIdAndCertificacaoId(colaboradorId, certificacaoId);
		if(colaboradorCertificacao != null && colaboradorCertificacao.getId() != null)
			colaboradorCertificacaoId = colaboradorCertificacao.getId();
		
		String notaAvPatica;
		colaboradorAvaliacoesPraticas = colaboradorAvaliacaoPraticaManager.findByColaboradorIdAndCertificacaoId(colaboradorId, certificacaoId, colaboradorCertificacaoId, null, true, true);
		for (AvaliacaoPratica avaliacaoPratica : avaliacoesPraticas) {
			notaAvPatica = "-";
			for (ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica : colaboradorAvaliacoesPraticas) {
				if(colaboradorAvaliacaoPratica.getAvaliacaoPratica() != null && colaboradorAvaliacaoPratica.getAvaliacaoPratica().getId() != null 
						&& colaboradorAvaliacaoPratica.getNota() != null && avaliacaoPratica.getId().equals(colaboradorAvaliacaoPratica.getAvaliacaoPratica().getId())){
					notaAvPatica = colaboradorAvaliacaoPratica.getNota().toString();
					break;
				}
			}
			curso = new Curso();
			curso.setNome("Avaliação Prática: " + avaliacaoPratica.getTitulo());
			curso.setInformacao("Nota: " + notaAvPatica);
			certificacaoTreinamentosRelatorio.getCursos().add(curso);
		}
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
	
	public Collection<Curso> findCursosByCertificacaoId(Long id) {
		return getDao().findCursosByCertificacaoId(id);
	}
	
	public Collection<Certificacao> findCollectionByIdProjection(Long[] certificacoesIds) {
		return getDao().findCollectionByIdProjection(certificacoesIds);
	}

	public void setColaboradorCertificacaoManager(ColaboradorCertificacaoManager colaboradorCertificacaoManager) {
		this.colaboradorCertificacaoManager = colaboradorCertificacaoManager;
	}

	public void setColaboradorAvaliacaoPraticaManager(ColaboradorAvaliacaoPraticaManager colaboradorAvaliacaoPraticaManager) {
		this.colaboradorAvaliacaoPraticaManager = colaboradorAvaliacaoPraticaManager;
	}

	public void setAvaliacaoPraticaManager(
			AvaliacaoPraticaManager avaliacaoPraticaManager) {
		this.avaliacaoPraticaManager = avaliacaoPraticaManager;
	}

}