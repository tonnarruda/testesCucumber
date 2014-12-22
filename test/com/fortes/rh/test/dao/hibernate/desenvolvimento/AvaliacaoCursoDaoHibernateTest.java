package com.fortes.rh.test.dao.hibernate.desenvolvimento;

import java.util.ArrayList;
import java.util.Collection;

import com.fortes.dao.GenericDao;
import com.fortes.rh.dao.desenvolvimento.AproveitamentoAvaliacaoCursoDao;
import com.fortes.rh.dao.desenvolvimento.AvaliacaoCursoDao;
import com.fortes.rh.dao.desenvolvimento.CursoDao;
import com.fortes.rh.dao.desenvolvimento.TurmaDao;
import com.fortes.rh.dao.geral.EmpresaDao;
import com.fortes.rh.dao.pesquisa.ColaboradorQuestionarioDao;
import com.fortes.rh.model.desenvolvimento.AproveitamentoAvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.AvaliacaoCurso;
import com.fortes.rh.model.desenvolvimento.Curso;
import com.fortes.rh.model.desenvolvimento.Turma;
import com.fortes.rh.model.dicionario.TipoAvaliacaoCurso;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.pesquisa.ColaboradorQuestionario;
import com.fortes.rh.test.dao.GenericDaoHibernateTest;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.desenvolvimento.AvaliacaoCursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.CursoFactory;
import com.fortes.rh.test.factory.desenvolvimento.TurmaFactory;
import com.fortes.rh.test.factory.pesquisa.ColaboradorQuestionarioFactory;

public class AvaliacaoCursoDaoHibernateTest extends GenericDaoHibernateTest<AvaliacaoCurso>
{
	private AvaliacaoCursoDao avaliacaoCursoDao;
	private ColaboradorQuestionarioDao colaboradorQuestionarioDao;
	private AproveitamentoAvaliacaoCursoDao aproveitamentoAvaliacaoCursoDao;
	private CursoDao cursoDao;
	private EmpresaDao empresaDao;
	private TurmaDao turmaDao;

	public AvaliacaoCurso getEntity()
	{
		AvaliacaoCurso avaliacaoCurso = new AvaliacaoCurso();

		avaliacaoCurso.setId(null);

		return avaliacaoCurso;
	}
	public GenericDao<AvaliacaoCurso> getGenericDao()
	{
		return avaliacaoCursoDao;
	}

	public void setAvaliacaoCursoDao(AvaliacaoCursoDao AvaliacaoCursoDao)
	{
		this.avaliacaoCursoDao = AvaliacaoCursoDao;
	}
	
	public void setCursoDao(CursoDao cursoDao)
	{
		this.cursoDao = cursoDao;
	}
	public void testFindByCurso()
	{
		Empresa empresa = EmpresaFactory.getEmpresa();
		empresaDao.save(empresa);
		
		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity();
		avaliacaoCursoDao.save(avaliacaoCurso);
		AvaliacaoCurso avaliacaoCurso2 = AvaliacaoCursoFactory.getEntity();
		avaliacaoCursoDao.save(avaliacaoCurso2);
		
		Collection<AvaliacaoCurso> avaliacaoCursos = new ArrayList<AvaliacaoCurso>();
		avaliacaoCursos.add(avaliacaoCurso);
		avaliacaoCursos.add(avaliacaoCurso2);
		
		Curso curso = CursoFactory.getEntity();
		curso.setEmpresa(empresa);
		curso.setAvaliacaoCursos(avaliacaoCursos);
		cursoDao.save(curso);
		
		assertEquals(2, avaliacaoCursoDao.findByCursos(new Long[]{curso.getId()}).size());
	}
	
	public void testCountAvaliacaoCursos()
	{				
		AvaliacaoCurso avaliacaoCurso1 = AvaliacaoCursoFactory.getEntity();
		avaliacaoCursoDao.save(avaliacaoCurso1);
		
		AvaliacaoCurso avaliacaoCurso2 = AvaliacaoCursoFactory.getEntity();
		avaliacaoCursoDao.save(avaliacaoCurso2);
		
		Collection<AvaliacaoCurso> avaliacaoCursos1 = new ArrayList<AvaliacaoCurso>();
		avaliacaoCursos1.add(avaliacaoCurso1);
		avaliacaoCursos1.add(avaliacaoCurso2);
		
		Curso curso1 = CursoFactory.getEntity();
		curso1.setAvaliacaoCursos(avaliacaoCursos1);
		cursoDao.save(curso1);
		
		Collection<AvaliacaoCurso> avaliacaoCursos2 = new ArrayList<AvaliacaoCurso>();
		avaliacaoCursos2.add(avaliacaoCurso2);
		Curso curso2 = CursoFactory.getEntity();
		curso2.setAvaliacaoCursos(avaliacaoCursos2);
		cursoDao.save(curso2);
		
		assertEquals(new Integer(3), avaliacaoCursoDao.countAvaliacoes(new Long[]{curso1.getId(), curso2.getId()}));
	}
	
	public void testCountAvaliacaoWherePorCurso()
	{				
		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity();
		avaliacaoCursoDao.save(avaliacaoCurso);
		
		Collection<AvaliacaoCurso> avaliacaoCursos = new ArrayList<AvaliacaoCurso>();
		avaliacaoCursos.add(avaliacaoCurso);
		
		Curso curso = CursoFactory.getEntity();
		curso.setAvaliacaoCursos(avaliacaoCursos);
		cursoDao.save(curso);
		
		assertEquals(new Integer(1), avaliacaoCursoDao.countAvaliacoes(curso.getId(), "C"));
	}
	
	public void testCountAvaliacao()
	{				
		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity();
		avaliacaoCursoDao.save(avaliacaoCurso);
	
		Collection<AvaliacaoCurso> avaliacaoCursos = new ArrayList<AvaliacaoCurso>();
		avaliacaoCursos.add(avaliacaoCurso);
		
		Curso curso = CursoFactory.getEntity();
		curso.setAvaliacaoCursos(avaliacaoCursos);
		cursoDao.save(curso);
		
		Turma turma = TurmaFactory.getEntity();
		turma.setCurso(curso);
		turmaDao.save(turma);
		
		assertEquals(new Integer(1), avaliacaoCursoDao.countAvaliacoes(turma.getId(), "T"));
	}
	
	public void testCountAvaliacaoSemAvaliacao()
	{				
		Curso curso = CursoFactory.getEntity();
		cursoDao.save(curso);
		
		Turma turma = TurmaFactory.getEntity();
		turma.setCurso(curso);
		turmaDao.save(turma);
		
		assertEquals(new Integer(0), avaliacaoCursoDao.countAvaliacoes(turma.getId(), "T"));
	}
	
	public void testBuscaFiltro()
	{
		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity();
		avaliacaoCurso.setTitulo("Avaliação de Teste 1");
		avaliacaoCursoDao.save(avaliacaoCurso);
		
		AvaliacaoCurso avaliacaoCurso2 = AvaliacaoCursoFactory.getEntity();
		avaliacaoCurso2.setTitulo("Avaliação de Teste 2");
		avaliacaoCursoDao.save(avaliacaoCurso2);
		
		assertEquals(2, avaliacaoCursoDao.buscaFiltro("AVALIAÇÃO de tes").size());
	}
	
	public void testExisteAvaliacaoCursoRespondida()
	{
		// Com resposta
		AvaliacaoCurso avaliacaoCurso1 = criaAvaliacaoCurso(TipoAvaliacaoCurso.NOTA, true);
		AvaliacaoCurso avaliacaoCurso2 = criaAvaliacaoCurso(TipoAvaliacaoCurso.AVALIACAO, true);
		AvaliacaoCurso avaliacaoCurso3 = criaAvaliacaoCurso(TipoAvaliacaoCurso.PORCENTAGEM, true);
		
		boolean existeAvaliacaoCurso1Respondida = avaliacaoCursoDao.existeAvaliacaoCursoRespondida(avaliacaoCurso1.getId(), avaliacaoCurso1.getTipo()); 
		boolean existeAvaliacaoCurso2Respondida = avaliacaoCursoDao.existeAvaliacaoCursoRespondida(avaliacaoCurso2.getId(), avaliacaoCurso2.getTipo()); 
		boolean existeAvaliacaoCurso3Respondida = avaliacaoCursoDao.existeAvaliacaoCursoRespondida(avaliacaoCurso3.getId(), avaliacaoCurso3.getTipo()); 
		
		assertTrue("Avaliação por nota com resposta", existeAvaliacaoCurso1Respondida);
		assertTrue("Avaliação por avaliação com resposta", existeAvaliacaoCurso2Respondida);
		assertTrue("Avaliação por porcentagem com resposta", existeAvaliacaoCurso3Respondida);
		
		// Sem resposta
		AvaliacaoCurso avaliacaoCurso4 = criaAvaliacaoCurso(TipoAvaliacaoCurso.NOTA, false);
		AvaliacaoCurso avaliacaoCurso5 = criaAvaliacaoCurso(TipoAvaliacaoCurso.AVALIACAO, false);
		AvaliacaoCurso avaliacaoCurso6 = criaAvaliacaoCurso(TipoAvaliacaoCurso.PORCENTAGEM, false);
		
		boolean existeAvaliacaoCurso4Respondida = avaliacaoCursoDao.existeAvaliacaoCursoRespondida(avaliacaoCurso4.getId(), avaliacaoCurso4.getTipo()); 
		boolean existeAvaliacaoCurso5Respondida = avaliacaoCursoDao.existeAvaliacaoCursoRespondida(avaliacaoCurso5.getId(), avaliacaoCurso5.getTipo()); 
		boolean existeAvaliacaoCurso6Respondida = avaliacaoCursoDao.existeAvaliacaoCursoRespondida(avaliacaoCurso6.getId(), avaliacaoCurso6.getTipo()); 
		
		assertFalse("Avaliação por nota sem resposta", existeAvaliacaoCurso4Respondida);
		assertFalse("Avaliação por avaliação sem resposta", existeAvaliacaoCurso5Respondida);
		assertFalse("Avaliação por porcentagem sem resposta", existeAvaliacaoCurso6Respondida);
	}
	
	private AvaliacaoCurso criaAvaliacaoCurso(char tipoAvaliacaoCurso, boolean comResposta)
	{
		AvaliacaoCurso avaliacaoCurso = AvaliacaoCursoFactory.getEntity();
		avaliacaoCurso.setTipo(tipoAvaliacaoCurso);
		avaliacaoCursoDao.save(avaliacaoCurso);
		
		if(comResposta){
			if(tipoAvaliacaoCurso == TipoAvaliacaoCurso.AVALIACAO){
				ColaboradorQuestionario colaboradorQuestionario = ColaboradorQuestionarioFactory.getEntity();
				colaboradorQuestionario.setAvaliacaoCurso(avaliacaoCurso);
				colaboradorQuestionarioDao.save(colaboradorQuestionario);
			} else {
				AproveitamentoAvaliacaoCurso aproveitamentoAvaliacaoCurso = new AproveitamentoAvaliacaoCurso();
				aproveitamentoAvaliacaoCurso.setAvaliacaoCurso(avaliacaoCurso);
				aproveitamentoAvaliacaoCursoDao.save(aproveitamentoAvaliacaoCurso);
			}
		}
		
		return avaliacaoCurso;
	}

	public void setEmpresaDao(EmpresaDao empresaDao)
	{
		this.empresaDao = empresaDao;
	}
	
	public void setTurmaDao(TurmaDao turmaDao)
	{
		this.turmaDao = turmaDao;
	}
	
	public void setColaboradorQuestionarioDao(ColaboradorQuestionarioDao colaboradorQuestionarioDao)
	{
		this.colaboradorQuestionarioDao = colaboradorQuestionarioDao;
	}
	
	public void setAproveitamentoAvaliacaoCursoDao(AproveitamentoAvaliacaoCursoDao aproveitamentoAvaliacaoCursoDao)
	{
		this.aproveitamentoAvaliacaoCursoDao = aproveitamentoAvaliacaoCursoDao;
	}
}