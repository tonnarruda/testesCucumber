package com.fortes.rh.web.action.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import com.fortes.rh.business.avaliacao.AvaliacaoPraticaManager;
import com.fortes.rh.business.desenvolvimento.CertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorAvaliacaoPraticaManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorCertificacaoManager;
import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.model.avaliacao.AvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.Certificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorAvaliacaoPratica;
import com.fortes.rh.model.desenvolvimento.ColaboradorCertificacao;
import com.fortes.rh.model.desenvolvimento.ColaboradorTurma;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.web.action.MyActionSupportList;
import com.opensymphony.xwork.Action;

public class ColaboradorAvaliacaoPraticaEditAction extends MyActionSupportList
{
	private static final long serialVersionUID = 1L;
	private ColaboradorAvaliacaoPraticaManager colaboradorAvaliacaoPraticaManager;
	private CertificacaoManager certificacaoManager;
	private ColaboradorTurmaManager colaboradorTurmaManager;
	private AvaliacaoPraticaManager avaliacaoPraticaManager;
	private ColaboradorCertificacaoManager colaboradorCertificacaoManager;

	private ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica;
	private Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacaoPraticas;
	
	private Collection<Certificacao> certificacoes = new ArrayList<Certificacao>();
	private Collection<Colaborador>  colaboradores = new ArrayList<Colaborador>();
	private Collection<ColaboradorTurma> colaboradorTurmas = new ArrayList<ColaboradorTurma>();
	private Collection<ColaboradorCertificacao> colaboradorCertificacaos = new ArrayList<ColaboradorCertificacao>();
	
	private AvaliacaoPratica avaliacaoPratica;
	private Collection<AvaliacaoPratica> avaliacoesPraticas = new ArrayList<AvaliacaoPratica>();
	
	private Certificacao certificacao;
	private Colaborador colaborador; 
	private ColaboradorCertificacao colaboradorCertificacao;
	private boolean possivelInserirNotaAvPratica;
	
	public String prepare() throws Exception
	{
		certificacoes = certificacaoManager.findOsQuePossuemAvaliacaoPratica(getEmpresaSistema().getId());
		
		if(colaboradorAvaliacaoPratica != null && colaboradorAvaliacaoPratica.getId() != null)
			colaboradorAvaliacaoPratica = (ColaboradorAvaliacaoPratica) colaboradorAvaliacaoPraticaManager.findById(colaboradorAvaliacaoPratica.getId());
		
		return Action.SUCCESS;
	}
	
	public String buscaColaboradores() throws Exception
	{
		prepare();
		if(certificacao == null || certificacao.getId() == null)
			return Action.SUCCESS;

		colaboradores = colaboradorCertificacaoManager.colaboradoresQueParticipamDaCertificacao(certificacao.getId());

		if(colaborador != null && colaborador.getId() != null && certificacao != null && certificacao.getId() != null)	{
			colaboradorCertificacaos = colaboradorCertificacaoManager.findByColaboradorIdAndCertificacaoId(colaborador.getId(), certificacao.getId());
			populaColaboradorTurma();
			checaSePossivelInserirNotaAvPratica();
			populaColaboradorAvaliacaoPratica();
		}
		
		if(colaboradorCertificacao!= null && colaboradorCertificacao.getId() != null && colaborador != null  && colaborador.getId() != null  && certificacao != null && certificacao.getId() != null)
			colaboradorCertificacao = colaboradorCertificacaoManager.findColaboradorCertificadoInfomandoSeEUltimaCertificacao(colaboradorCertificacao.getId(), colaborador.getId(), certificacao.getId());
		
		return Action.SUCCESS;
	}

	private void checaSePossivelInserirNotaAvPratica() {
		if(!possivelInserirNotaAvPratica){
			if(colaboradorCertificacaos.size() >= 1){
				colaboradorCertificacao = (ColaboradorCertificacao) colaboradorCertificacaos.toArray()[0];
				populaColaboradorTurma();	
				possivelInserirNotaAvPratica = false;
			}else
				possivelInserirNotaAvPratica = true;
		}
	}

	private void populaColaboradorTurma(){
		colaboradorTurmas = new ArrayList<ColaboradorTurma>();
		Collection<Curso> cursos = certificacaoManager.findCursosByCertificacaoId(certificacao.getId());
		Collection<ColaboradorTurma> colaboradorTurmasRealizadas = colaboradorTurmaManager.findByColaboradorIdAndCertificacaoIdAndColabCertificacaoId(certificacao.getId(), colaboradorCertificacao != null ? colaboradorCertificacao.getId() : null, colaborador.getId());
		possivelInserirNotaAvPratica = colaboradorCertificacao != null && colaboradorCertificacao.getId() != null;
		for (Curso curso : cursos) {
			boolean realizouTurma = false;
			for (ColaboradorTurma colaboradorTurma : colaboradorTurmasRealizadas) {
				if(colaboradorTurma.getCurso().getId().equals(curso.getId())){
					colaboradorTurmas.add(colaboradorTurma);
					realizouTurma = true;
					
					if(!colaboradorTurma.isAprovado())
						possivelInserirNotaAvPratica = false;

					continue;
				}
			}
			
			if(!realizouTurma){
				ColaboradorTurma colaboradorTurma = new ColaboradorTurma();
				colaboradorTurma.setCurso(curso);
				colaboradorTurma.setTurmaDescricao("Não realizou o curso");
				colaboradorTurmas.add(colaboradorTurma);
				possivelInserirNotaAvPratica = false;
			}
		}
		
	}
	
	private void populaColaboradorAvaliacaoPratica() 
	{
		Collection<AvaliacaoPratica> avaliacoesPraticasDoCertificado = avaliacaoPraticaManager.findByCertificacaoId(certificacao.getId());
		Collection<ColaboradorAvaliacaoPratica> avaliacoesPraticasDoColaboradorRealizadas = colaboradorAvaliacaoPraticaManager.findByColaboradorIdAndCertificacaoId(colaborador.getId(), certificacao.getId(), colaboradorCertificacao != null ? colaboradorCertificacao.getId() : null, null, true, true);
		colaboradorAvaliacaoPraticas = new ArrayList<ColaboradorAvaliacaoPratica>();
		ColaboradorAvaliacaoPratica ColaboradorAvaliacaoPratica = null;
		
		boolean existeNota = false; 
		for (AvaliacaoPratica avaliacaoPraticaDoCertificado : avaliacoesPraticasDoCertificado) 
		{
			for (ColaboradorAvaliacaoPratica avaliacaoPraticaDoColaboradorRealizada : avaliacoesPraticasDoColaboradorRealizadas) 
			{
				if(avaliacaoPraticaDoColaboradorRealizada.getAvaliacaoPratica().getId().equals(avaliacaoPraticaDoCertificado.getId()))
				{
					avaliacaoPraticaDoColaboradorRealizada.setAvaliacaoPratica(avaliacaoPraticaDoCertificado);
					colaboradorAvaliacaoPraticas.add(avaliacaoPraticaDoColaboradorRealizada);
					existeNota = true;
					break;
				}
			}
			
			if(!existeNota && avaliacaoPraticaDoCertificado.getId() != null){
				ColaboradorAvaliacaoPratica = new ColaboradorAvaliacaoPratica();
				ColaboradorAvaliacaoPratica.setAvaliacaoPratica(avaliacaoPraticaDoCertificado);
				colaboradorAvaliacaoPraticas.add(ColaboradorAvaliacaoPratica);
			}
		}
	}
	
	public String insertOrUpdate() throws Exception
	{
		for (ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica : colaboradorAvaliacaoPraticas) 
		{
			if(colaboradorAvaliacaoPratica.getData() != null && colaboradorAvaliacaoPratica.getNota() != null){
				colaboradorAvaliacaoPratica.setCertificacao(certificacao);
				colaboradorAvaliacaoPratica.setColaborador(colaborador);
				
				if(colaboradorCertificacao != null && colaboradorCertificacao.getId() != null){
					if(colaboradorAvaliacaoPratica.getAvaliacaoPratica().getNotaMinima() > colaboradorAvaliacaoPratica.getNota())	{
						colaboradorCertificacaoManager.descertificarColaborador(colaboradorCertificacao.getId());
					}else{
						colaboradorAvaliacaoPratica.setColaboradorCertificacao(colaboradorCertificacao);
						atualizaDataColaboradorCertificacao();
					}
				}
				
				if(colaboradorAvaliacaoPratica.getId() == null)
					colaboradorAvaliacaoPraticaManager.save(colaboradorAvaliacaoPratica);
				else
					colaboradorAvaliacaoPraticaManager.update(colaboradorAvaliacaoPratica);

				if(colaboradorCertificacao == null || colaboradorCertificacao.getId() == null){
					Collection<ColaboradorCertificacao> colaboradorCertificacoes = colaboradorCertificacaoManager.certificaColaborador(null, colaborador.getId(), certificacao.getId(), certificacaoManager); 
					if(colaboradorCertificacoes.size() > 0){
						ColaboradorCertificacao colaboradorCertificacaoTemp = (ColaboradorCertificacao) colaboradorCertificacoes.toArray()[0];
						colaboradorAvaliacaoPratica.setColaboradorCertificacao(colaboradorCertificacaoTemp);
						colaboradorAvaliacaoPraticaManager.update(colaboradorAvaliacaoPratica);
					}
				}
			}else if(colaboradorAvaliacaoPratica.getId() != null){
				if(colaboradorCertificacao.getId() != null){
					colaboradorAvaliacaoPraticaManager.removeByColaboradorCertificacaoId(colaboradorCertificacao.getId());
					colaboradorCertificacaoManager.descertificarColaborador(colaboradorCertificacao.getId());
				}else
					colaboradorAvaliacaoPraticaManager.remove(colaboradorAvaliacaoPratica.getId());
			}
		}

		if(colaboradorAvaliacaoPraticas.size() > 1)
			setActionMsg("Avaliações gravadas com sucesso");
		else if(colaboradorAvaliacaoPraticas.size() > 0)
			setActionMsg("Avaliação gravada com sucesso");
		
		colaboradorCertificacao = new ColaboradorCertificacao();//Não remover 
		
		return buscaColaboradores();
	}
	
	private void atualizaDataColaboradorCertificacao(){
		colaboradorAvaliacaoPraticas = new CollectionUtil<ColaboradorAvaliacaoPratica>().sortCollectionDate(colaboradorAvaliacaoPraticas, "data", "desc");
		Date maiorDataDasTurmas = colaboradorCertificacaoManager.getMaiorDataDasTurmasDaCertificacao(colaboradorCertificacao.getId());
		Date maiorDataAvaliacaoPratica = ((ColaboradorAvaliacaoPratica)colaboradorAvaliacaoPraticas.toArray()[0]).getData();
		Date dataColaboradorCertificacao = maiorDataAvaliacaoPratica.after(maiorDataDasTurmas) ? maiorDataAvaliacaoPratica : maiorDataDasTurmas;
		
		colaboradorCertificacao = colaboradorCertificacaoManager.findById(colaboradorCertificacao.getId());
		if(!colaboradorCertificacao.getData().equals(dataColaboradorCertificacao)){
			colaboradorCertificacao.setData(dataColaboradorCertificacao);
			colaboradorCertificacaoManager.update(colaboradorCertificacao);
		}	
	}
	
	public String prepareLote() throws Exception
	{
		certificacoes = certificacaoManager.findOsQuePossuemAvaliacaoPratica(getEmpresaSistema().getId());

		if(certificacao != null && certificacao.getId() != null)
			avaliacoesPraticas = avaliacaoPraticaManager.findByCertificacaoId(certificacao.getId());
		
		return Action.SUCCESS;
	}
	
	public String buscaColaboradoresLote() throws Exception
	{
		prepareLote();
		if((certificacao == null || certificacao.getId() == null) || (avaliacaoPratica ==null || avaliacaoPratica.getId()==null))
			return Action.SUCCESS;

		colaboradorCertificacaos = colaboradorCertificacaoManager.possuemAvaliacoesPraticasRealizadas(certificacao.getId(), colaboradorTurmaManager);
		
		return Action.SUCCESS;
	}
	
	public String insertOrUpdateLote() throws Exception
	{
		avaliacaoPratica = avaliacaoPraticaManager.findById(avaliacaoPratica.getId());
		colaboradorCertificacaos.removeAll(Collections.singleton(null));
		
		for (ColaboradorCertificacao colabCertificacao : colaboradorCertificacaos) 
		{
			if(colabCertificacao.getColaboradorAvaliacaoPraticaAtual().getData() != null && colabCertificacao.getColaboradorAvaliacaoPraticaAtual().getNota() != null){
				
				colaboradorAvaliacaoPratica = colabCertificacao.getColaboradorAvaliacaoPraticaAtual();
				colaboradorAvaliacaoPratica.setColaborador(colabCertificacao.getColaborador());
				colaboradorAvaliacaoPratica.setCertificacao(certificacao);
				colaboradorAvaliacaoPratica.setAvaliacaoPratica(avaliacaoPratica);

				if(colabCertificacao.getId() != null){
					if(avaliacaoPratica.getNotaMinima() > colabCertificacao.getColaboradorAvaliacaoPraticaAtual().getNota()){	
						colaboradorCertificacaoManager.descertificarColaborador(colabCertificacao.getId());
					}else{
						colaboradorAvaliacaoPratica.setColaboradorCertificacao(colabCertificacao);
						atualizaDataColaboradorCertificacaoLote(colabCertificacao.getId(), colabCertificacao.getColaboradorAvaliacaoPraticaAtual().getData());
					}
				}
				
				if(colaboradorAvaliacaoPratica.getId() != null)
					colaboradorAvaliacaoPraticaManager.update(colaboradorAvaliacaoPratica);
				else if(!existeColaboradorAvalPraticaComMesmaData(colabCertificacao))
						colaboradorAvaliacaoPraticaManager.save(colaboradorAvaliacaoPratica);

				if(colabCertificacao.getId() == null){
					Collection<ColaboradorCertificacao> colaboradorCertificacoes = colaboradorCertificacaoManager.certificaColaborador(null, colabCertificacao.getColaborador().getId(), certificacao.getId(), certificacaoManager); 
					if(colaboradorCertificacoes.size() > 0){
						ColaboradorCertificacao colaboradorCertificacaoTemp = (ColaboradorCertificacao) colaboradorCertificacoes.toArray()[0];
						colaboradorAvaliacaoPratica.setColaboradorCertificacao(colaboradorCertificacaoTemp);
						colaboradorAvaliacaoPraticaManager.update(colaboradorAvaliacaoPratica);
					}
				}
			}else if(colabCertificacao.getColaboradorAvaliacaoPraticaAtual().getId() != null && colabCertificacao.getUltimaCertificacao()){
				if(colabCertificacao.getId() != null){
					colaboradorAvaliacaoPraticaManager.removeByColaboradorCertificacaoId(colabCertificacao.getId());
					colaboradorCertificacaoManager.descertificarColaborador(colabCertificacao.getId());
				}else
					colaboradorAvaliacaoPraticaManager.remove(colabCertificacao.getColaboradorAvaliacaoPraticaAtual().getId());
			}
		}

		if(colaboradorCertificacaos.size() > 1)
			setActionMsg("Avaliações gravadas com sucesso");
		else if(colaboradorCertificacaos.size() > 0)
			setActionMsg("Avaliação gravada com sucesso");
		
		colaboradorCertificacao = new ColaboradorCertificacao();//Não remover 
		
		return buscaColaboradoresLote();
	}

	private boolean existeColaboradorAvalPraticaComMesmaData(ColaboradorCertificacao colabCertificacao) 
	{
		boolean existeAvPraticaComaMesmaDataPassada = false;
		
		Collection<ColaboradorAvaliacaoPratica> colabAvPraticasTemp = colaboradorAvaliacaoPraticaManager.findByColaboradorIdAndCertificacaoId(colabCertificacao.getColaborador().getId(), certificacao.getId(), null, avaliacaoPratica.getId(), true, true);
		
		for (ColaboradorAvaliacaoPratica colabAvaliacaoPratica : colabAvPraticasTemp)
			if(colabAvaliacaoPratica.getData().getTime() == colaboradorAvaliacaoPratica.getData().getTime())
				existeAvPraticaComaMesmaDataPassada = true;
		
		return existeAvPraticaComaMesmaDataPassada;
	}
	
	private void atualizaDataColaboradorCertificacaoLote(Long colaboradorCertificacaoId, Date dataAvPraticaRealizada){
		Date maiorDataDasTurmas = colaboradorCertificacaoManager.getMaiorDataDasTurmasDaCertificacao(colaboradorCertificacaoId);
		Date dataColaboradorCertificacao = dataAvPraticaRealizada.after(maiorDataDasTurmas) ? dataAvPraticaRealizada : maiorDataDasTurmas;
		
		colaboradorCertificacao = colaboradorCertificacaoManager.findById(colaboradorCertificacaoId);
		if(!colaboradorCertificacao.getData().equals(dataColaboradorCertificacao)){
			colaboradorCertificacao.setData(dataColaboradorCertificacao);
			colaboradorCertificacaoManager.update(colaboradorCertificacao);
		}	
	}
	
	public ColaboradorAvaliacaoPratica getColaboradorAvaliacaoPratica()
	{
		if(colaboradorAvaliacaoPratica == null)
			colaboradorAvaliacaoPratica = new ColaboradorAvaliacaoPratica();
		return colaboradorAvaliacaoPratica;
	}

	public void setColaboradorAvaliacaoPratica(ColaboradorAvaliacaoPratica colaboradorAvaliacaoPratica)
	{
		this.colaboradorAvaliacaoPratica = colaboradorAvaliacaoPratica;
	}

	public Collection<ColaboradorAvaliacaoPratica> getColaboradorAvaliacaoPraticas()
	{
		return colaboradorAvaliacaoPraticas;
	}

	public Collection<Certificacao> getCertificacoes() {
		return certificacoes;
	}

	public void setCertificacao(Certificacao certificacao) {
		this.certificacao = certificacao;
	}
	
	public Colaborador getColaborador() {
		return colaborador;
	}
	
	public void setColaborador(Colaborador colaborador){
		this.colaborador = colaborador;
	}

	public void setCertificacaoManager(CertificacaoManager certificacaoManager) {
		this.certificacaoManager = certificacaoManager;
	}

	public void setColaboradorAvaliacaoPraticaManager(ColaboradorAvaliacaoPraticaManager colaboradorAvaliacaoPraticaManager)
	{
		this.colaboradorAvaliacaoPraticaManager = colaboradorAvaliacaoPraticaManager;
	}

	public Collection<Colaborador> getColaboradores() {
		return colaboradores;
	}

	public Certificacao getCertificacao() {
		return certificacao;
	}

	public Collection<ColaboradorTurma> getColaboradorTurmas() {
		return colaboradorTurmas;
	}

	public void setColaboradorTurmaManager(ColaboradorTurmaManager colaboradorTurmaManager) {
		this.colaboradorTurmaManager = colaboradorTurmaManager;
	}

	public void setColaboradorAvaliacaoPraticas(Collection<ColaboradorAvaliacaoPratica> colaboradorAvaliacaoPraticas) {
		this.colaboradorAvaliacaoPraticas = colaboradorAvaliacaoPraticas;
	}

	public void setAvaliacaoPraticaManager(
			AvaliacaoPraticaManager avaliacaoPraticaManager) {
		this.avaliacaoPraticaManager = avaliacaoPraticaManager;
	}

	public Date getHoje() {
		return new Date();
	}

	public Collection<ColaboradorCertificacao> getColaboradorCertificacaos() {
		return colaboradorCertificacaos;
	}

	public void setColaboradorCertificacao(ColaboradorCertificacao colaboradorCertificacao) {
		this.colaboradorCertificacao = colaboradorCertificacao;
	}

	public void setColaboradorCertificacaoManager(ColaboradorCertificacaoManager colaboradorCertificacaoManager) {
		this.colaboradorCertificacaoManager = colaboradorCertificacaoManager;
	}

	public ColaboradorCertificacao getColaboradorCertificacao() {
		return colaboradorCertificacao;
	}

	public void setAvaliacaoPratica(AvaliacaoPratica avaliacaoPratica) {
		this.avaliacaoPratica = avaliacaoPratica;
	}

	public AvaliacaoPratica getAvaliacaoPratica() {
		return avaliacaoPratica;
	}

	public Collection<AvaliacaoPratica> getAvaliacoesPraticas() {
		return avaliacoesPraticas;
	}

	public void setColaboradorCertificacaos(
			Collection<ColaboradorCertificacao> colaboradorCertificacaos) {
		this.colaboradorCertificacaos = colaboradorCertificacaos;
	}

	public boolean isPossivelInserirNotaAvPratica() {
		return possivelInserirNotaAvPratica;
	}

	public void setPossivelInserirNotaAvPratica(boolean possivelInserirNotaAvPratica) {
		this.possivelInserirNotaAvPratica = possivelInserirNotaAvPratica;
	}
}
