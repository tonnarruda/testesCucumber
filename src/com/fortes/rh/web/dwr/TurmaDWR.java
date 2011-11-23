package com.fortes.rh.web.dwr;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fortes.rh.business.desenvolvimento.ColaboradorTurmaManager;
import com.fortes.rh.business.desenvolvimento.TurmaManager;
import com.fortes.rh.business.pesquisa.AvaliacaoTurmaManager;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.pesquisa.AvaliacaoTurma;
import com.fortes.rh.util.CollectionUtil;
import com.fortes.rh.util.DateUtil;

public class TurmaDWR
{
	private TurmaManager turmaManager;
	private AvaliacaoTurmaManager avaliacaoTurmaManager;
	private ColaboradorTurmaManager colaboradorTurmaManager; 

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
		
		colaboradorTurmaManager.enviarAvisoEmail(turma, empresaId);
		return "Email enviado com sucesso.";
	}

	public Map getTurmasByFiltro(String dataIni, String dataFim, char realizada, Long empresaId)throws Exception
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

		Collection<Turma> turmas = turmaManager.findByFiltro(dataPrevIni, dataPrevFim, realizada, empresaId);

		if(turmas.isEmpty())
			throw new Exception("Não existe Turmas para o filtro informado.");

		return new CollectionUtil<Turma>().convertCollectionToMap(turmas,"getId","getDescricaoCurso");
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

	public void setTurmaManager(TurmaManager turmaManager)
	{
		this.turmaManager = turmaManager;
	}


	public void setColaboradorTurmaManager(ColaboradorTurmaManager colaboradorTurmaManager) {
		this.colaboradorTurmaManager = colaboradorTurmaManager;
	}


	public void setAvaliacaoTurmaManager(AvaliacaoTurmaManager avaliacaoTurmaManager) {
		this.avaliacaoTurmaManager = avaliacaoTurmaManager;
	}

}
