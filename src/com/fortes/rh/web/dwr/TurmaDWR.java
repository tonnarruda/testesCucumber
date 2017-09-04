package com.fortes.rh.web.dwr;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.rh.business.desenvolvimento.DiaTurmaManager;
import com.fortes.rh.business.desenvolvimento.TurmaAvaliacaoTurmaManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.business.geral.GerenciadorComunicacaoManager;
import com.fortes.rh.business.geral.TurmaTipoDespesaManager;
import com.fortes.rh.business.pesquisa.AvaliacaoTurmaManager;
import com.fortes.rh.model.desenvolvimento.DiaTurma;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.geral.TurmaTipoDespesa;
import com.fortes.rh.model.pesquisa.AvaliacaoTurma;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;

public class TurmaDWR
{
	private final Boolean LIBERADA = true;
	private final Boolean BLOQUEADA = false;
	
	private TurmaManager turmaManager;
	private DiaTurmaManager diaTurmaManager;
	private AvaliacaoTurmaManager avaliacaoTurmaManager;
	private TurmaAvaliacaoTurmaManager turmaAvaliacaoTurmaManager;
	private TurmaTipoDespesaManager turmaTipoDespesaManager;
	private GerenciadorComunicacaoManager gerenciadorComunicacaoManager;

	public Turma getTurma(Long turmaId)
	{
		Turma turma = turmaManager.findByIdProjection(turmaId);
		
		Collection<DiaTurma> diasTurma = diaTurmaManager.findByTurma(turmaId);
		turma.setDiasTurma(diasTurma);
		
		Collection<AvaliacaoTurma> avaliacaoTurmas = avaliacaoTurmaManager.findByTurma(turmaId);
		turma.setAvaliacaoTurmas(avaliacaoTurmas);
		
		return turma;
	}
	
	public Map getTurmas(String cursoId)
	{
		if(cursoId == null || cursoId.trim().equals(""))
			return null;

		Collection<Turma> turmas = turmaManager.findToList(new String[]{"id", "descricao"}, new String[]{"id", "descricao"}, new String[]{"curso.id"},new Object[]{new Long(cursoId)}, new String[]{"descricao"});

		return new CollectionUtil<Turma>().convertCollectionToMap(turmas,"getId","getDescricao");
	}
	
	public String enviarAviso(Long turmaId, Long empresaId)
	{
		Turma turma = turmaManager.findByIdProjection(turmaId);
		
		gerenciadorComunicacaoManager.enviarAvisoEmail(turma, empresaId);
		return "Email enviado com sucesso.";
	}

	public Map getTurmasByFiltro(String dataIni, String dataFim, char realizada, Long empresaId) throws Exception
	{
		Date dataPrevIni = null;
		Date dataPrevFim = null;

		try
		{
			dataPrevIni = DateUtil.montaDataByString(dataIni);
			dataPrevFim = DateUtil.montaDataByString(dataFim);
		}
		catch (Exception e)
		{
			throw new Exception("Data no formato inválido.");
		}

		Collection<Turma> turmas = turmaManager.findByFiltro(dataPrevIni, dataPrevFim, realizada, new Long[]{empresaId}, null);

		if(turmas.isEmpty())
			throw new Exception("Não existe Turmas para o filtro informado.");

		return new CollectionUtil<Turma>().convertCollectionToMap(turmas,"getId","getDescricaoCurso");
	}
	
	public Map getTurmasByCursoNotTurmaId(Long cursoId, Long notTurmaId) throws Exception
	{
		Collection<Turma> turmas = turmaManager.getTurmasByCursoNotTurmaId(cursoId, notTurmaId);
		return new CollectionUtil<Turma>().convertCollectionToMap(turmas,"getId","getDescricao");
	}
	
	public Map getTurmasByCursos(Long[] cursoIds)throws Exception
	{
		if (cursoIds.length == 0)
			return null;
		
		Collection<Turma> turmas = turmaManager.findByCursos(cursoIds);
		
		return new CollectionUtil<Turma>().convertCollectionToMap(turmas,"getId","getDescricaoCurso");
	}

	public Map getTurmasFinalizadas(String cursoId)
	{
		if(cursoId == null || cursoId.trim().equals(""))
			return null;

		Collection<Turma> turmas = turmaManager.getTurmaFinalizadas(new Long(cursoId));
		if(turmas == null || turmas.isEmpty())
		{
			Turma turma = new Turma();
			turma.setDescricao("Nenhuma turma finalizada");
			turma.setId(-1L);
			turmas.add(turma);
		}
		return new CollectionUtil<Turma>().convertCollectionToMap(turmas,"getId","getDescricao");
	}
	
	public Collection<AvaliacaoTurma> getAvaliacaoTurmas(Long turmaId) 
	{
		return avaliacaoTurmaManager.findByTurma(turmaId);
	}

	public boolean updateRealizada(Long turmaId, boolean realizada) throws Exception
	{
		try
		{
			turmaManager.updateRealizada(turmaId, realizada);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Exception("Erro ao alterar Turma");
		}

		return realizada;
	}
	
	public Long liberar(Long turmaId, Long avaliacaoTurmaId, Long empresaId)  
	{
		turmaAvaliacaoTurmaManager.updateLiberada(turmaId, avaliacaoTurmaId, LIBERADA, empresaId);
		
		return turmaId;
	}
	
	public Long bloquear(Long turmaId, Long avaliacaoTurmaId, Long empresaId) 
	{
		turmaAvaliacaoTurmaManager.updateLiberada(turmaId, avaliacaoTurmaId, BLOQUEADA, empresaId);
		
		return turmaId;
	}
	
	public Collection<TurmaTipoDespesa> getDespesas(Long turmaId)
	{
		return turmaTipoDespesaManager.findTipoDespesaTurma(turmaId);
	}
	
	public void saveDespesas(String turmaTipoDespesasJSON, Long turmaId, double totalCusto)
	{
		turmaTipoDespesaManager.save(turmaTipoDespesasJSON, turmaId);
		turmaManager.updateCusto(turmaId, totalCusto);
	}

	public void setTurmaManager(TurmaManager turmaManager)
	{
		this.turmaManager = turmaManager;
	}

	public void setAvaliacaoTurmaManager(AvaliacaoTurmaManager avaliacaoTurmaManager) 
	{
		this.avaliacaoTurmaManager = avaliacaoTurmaManager;
	}

	public void setTurmaTipoDespesaManager(TurmaTipoDespesaManager turmaTipoDespesaManager) 
	{
		this.turmaTipoDespesaManager = turmaTipoDespesaManager;
	}


	public void setGerenciadorComunicacaoManager(GerenciadorComunicacaoManager gerenciadorComunicacaoManager) {
		this.gerenciadorComunicacaoManager = gerenciadorComunicacaoManager;
	}


	public void setTurmaAvaliacaoTurmaManager(TurmaAvaliacaoTurmaManager turmaAvaliacaoTurmaManager) {
		this.turmaAvaliacaoTurmaManager = turmaAvaliacaoTurmaManager;
	}

	public void setDiaTurmaManager(DiaTurmaManager diaTurmaManager) {
		this.diaTurmaManager = diaTurmaManager;
	}
}
