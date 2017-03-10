package com.fortes.rh.web.action.desenvolvimento;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fortes.rh.business.avaliacao.AvaliacaoManager;
import com.fortes.rh.business.desenvolvimento.AvaliacaoCursoManager;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.dicionario.TipoAvaliacaoCurso;
import com.fortes.rh.web.action.MyActionSupport;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ModelDriven;

@SuppressWarnings( { "serial" })
public class AvaliacaoCursoEditAction extends MyActionSupport implements ModelDriven
{
	@Autowired private AvaliacaoCursoManager avaliacaoCursoManager;
	@Autowired private AvaliacaoManager avaliacaoManager;
	
	private AvaliacaoCurso avaliacaoCurso;
	private Map<Character, String> tipos = new TipoAvaliacaoCurso();
	private Collection<Avaliacao> avaliacoes;
	private boolean existeAvaliacaoRespondida;

	public String execute() throws Exception
	{
		return Action.SUCCESS;
	}

	private void prepare() throws Exception
	{
		if (avaliacaoCurso != null && avaliacaoCurso.getId() != null) {
			avaliacaoCurso = (AvaliacaoCurso) avaliacaoCursoManager.findById(avaliacaoCurso.getId());
			existeAvaliacaoRespondida =  avaliacaoCursoManager.existeAvaliacaoCursoRespondida(avaliacaoCurso.getId(), avaliacaoCurso.getTipo());
		}
		
		avaliacoes = avaliacaoManager.findToList(new String[] { "id", "titulo" }, new String[] { "id", "titulo" }, new String[] { "tipoModeloAvaliacao" }, new Object[] { "L" });
	}

	public String prepareInsert() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String prepareUpdate() throws Exception
	{
		prepare();
		return Action.SUCCESS;
	}

	public String insert() throws Exception
	{
		if (avaliacaoCurso.getAvaliacao() != null && avaliacaoCurso.getAvaliacao().getId() == null)
			avaliacaoCurso.setAvaliacao(null);
			
		avaliacaoCursoManager.save(avaliacaoCurso);
		
		addActionSuccess("Avaliação de aluno gravada com sucesso");
		
		return Action.SUCCESS;
	}

	public String update() throws Exception
	{
		if (avaliacaoCurso.getAvaliacao() != null && avaliacaoCurso.getAvaliacao().getId() == null)
			avaliacaoCurso.setAvaliacao(null);
		
		avaliacaoCursoManager.update(avaliacaoCurso);
		
		addActionSuccess("Avaliação de aluno alterada com sucesso");
		
		return Action.SUCCESS;
	}

	public Object getModel()
	{
		return getAvaliacaoCurso();
	}

	public AvaliacaoCurso getAvaliacaoCurso()
	{
		if (avaliacaoCurso == null)
			avaliacaoCurso = new AvaliacaoCurso();
		return avaliacaoCurso;
	}

	public void setAvaliacaoCurso(AvaliacaoCurso avaliacaoCurso)
	{
		this.avaliacaoCurso = avaliacaoCurso;
	}

	public Map<Character, String> getTipos()
	{
		return tipos;
	}

	public Collection<Avaliacao> getAvaliacoes() {
		return avaliacoes;
	}

	public void setAvaliacoes(Collection<Avaliacao> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}

	public boolean isExisteAvaliacaoRespondida() {
		return existeAvaliacaoRespondida;
	}
}