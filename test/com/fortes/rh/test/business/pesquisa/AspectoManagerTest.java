package com.fortes.rh.test.business.pesquisa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;

import com.fortes.rh.business.pesquisa.AspectoManagerImpl;
import com.fortes.rh.dao.pesquisa.AspectoDao;
import com.fortes.rh.model.avaliacao.Avaliacao;
import com.fortes.rh.model.pesquisa.Aspecto;
import com.fortes.rh.model.pesquisa.Pergunta;
import com.fortes.rh.model.pesquisa.Pesquisa;
import com.fortes.rh.model.pesquisa.Questionario;
import com.fortes.rh.test.factory.avaliacao.AvaliacaoFactory;
import com.fortes.rh.test.factory.pesquisa.AspectoFactory;
import com.fortes.rh.test.factory.pesquisa.PerguntaFactory;
import com.fortes.rh.test.factory.pesquisa.PesquisaFactory;
import com.fortes.rh.test.factory.pesquisa.QuestionarioFactory;
import com.fortes.web.tags.CheckBox;

public class AspectoManagerTest extends MockObjectTestCase
{
    private AspectoManagerImpl aspectoManager = new AspectoManagerImpl();
    private Mock aspectoDao;

    protected void setUp() throws Exception
    {
        super.setUp();
        aspectoDao = new Mock(AspectoDao.class);
        aspectoManager.setDao((AspectoDao) aspectoDao.proxy());
    }

    public void testUpdate() throws Exception
    {
        Aspecto aspecto = AspectoFactory.getEntity(1L);

        Exception exception = null;
        try
        {
            aspectoDao.expects(once()).method("update").with(eq(aspecto));
            aspectoManager.update(aspecto);
        }
        catch (Exception e)
        {
            exception = e;
        }
        assertNull(exception);
    }

    public void testUpdateException() throws Exception
    {
        Aspecto aspecto = AspectoFactory.getEntity(1L);

        Exception exception = null;
        try
        {
            aspectoDao.expects(once()).method("update").with(eq(aspecto)).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(aspecto.getId(),""))));
            aspectoManager.update(aspecto);
        }
        catch (Exception e)
        {
            exception = e;
        }
        assertNotNull(exception);
    }


    public void testDelete() throws Exception
    {
        Long aspectoId = 1L;

        Exception exception = null;
        try
        {
            aspectoDao.expects(once()).method("remove").with(eq(aspectoId));
            aspectoManager.remove(aspectoId);
        }
        catch (Exception e)
        {
            exception = e;
        }
        assertNull(exception);
    }

    public void testDeleteEmpresaException() throws Exception
    {
        Long aspectoId = 1L;

        Exception exception = null;
        try
        {
            aspectoDao.expects(once()).method("remove").with(eq(aspectoId)).will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(aspectoId,""))));
            aspectoManager.remove(aspectoId);
        }
        catch (Exception e)
        {
            exception = e;
        }
        assertNotNull(exception);
    }

    public void testFindByIdProjection()
    {
        Long aspectoId = 1L;

        aspectoDao.expects(once()).method("findByIdProjection").with(eq(aspectoId)).will(returnValue(AspectoFactory.getEntity(aspectoId)));

        Aspecto retorno = aspectoManager.findByIdProjection(aspectoId);

        assertEquals(aspectoId, retorno.getId());
    }
    
    public void testRemoverAspectosDoQuestionario()
    {
    	Long questionarioId = 1L;
    	aspectoDao.expects(once()).method("removerAspectosDoQuestionario").with(eq(questionarioId));
    	
    	aspectoManager.removerAspectosDoQuestionario(questionarioId);
    }

    public void testFindByPesquisa()
    {
        Questionario questionario = QuestionarioFactory.getEntity(1L);

        Aspecto aspecto1 = AspectoFactory.getEntity(1L);
        aspecto1.setQuestionario(questionario);

        Aspecto aspecto2 = AspectoFactory.getEntity(2L);
        aspecto2.setQuestionario(questionario);

        Collection<Aspecto> aspectos = new ArrayList<Aspecto>();
        aspectos.add(aspecto1);
        aspectos.add(aspecto2);

        Pesquisa pesquisa = PesquisaFactory.getEntity(1L);
        pesquisa.setQuestionario(questionario);

        aspectoDao.expects(once()).method("findByQuestionario").with(eq(pesquisa.getId())).will(returnValue(aspectos));
        Collection<Aspecto> retorno = aspectoManager.findByQuestionario(pesquisa.getId());
        assertEquals(2, retorno.size());
    }
    
    public void testGetAspectosByAvaliacao()
    {
    	Collection<String> aspectos = new ArrayList<String>();
    	aspectos.add("Comunicação");
    	aspectos.add("Liderança");
    	
    	aspectoDao.expects(once()).method("getNomesByAvaliacao").with(ANYTHING).will(returnValue(aspectos));
    	assertEquals("teste 01", "\"Comunicação\",\"Liderança\"", aspectoManager.getAspectosByAvaliacao(1L));

    	aspectoDao.expects(once()).method("getNomesByAvaliacao").with(ANYTHING).will(returnValue(null));
    	assertEquals("teste 02", "", aspectoManager.getAspectosByAvaliacao(1L));
    }

    public void testAgruparPerguntasByAspecto()
    {
        Aspecto aspecto1 = AspectoFactory.getEntity(1L);
        Aspecto aspecto2 = AspectoFactory.getEntity(2L);

        Collection<Aspecto> aspectos = new ArrayList<Aspecto>();
        aspectos.add(aspecto1);
        aspectos.add(aspecto2);

        Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
        pergunta1.setAspecto(aspecto1);
        Pergunta pergunta2 = PerguntaFactory.getEntity(2L);
        pergunta2.setAspecto(aspecto1);
        Pergunta pergunta3 = PerguntaFactory.getEntity(3L);
        pergunta3.setAspecto(aspecto2);

        Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
        perguntas.add(pergunta1);
        perguntas.add(pergunta2);
        perguntas.add(pergunta3);

        Collection<Aspecto> retorno = aspectoManager.agruparPerguntasByAspecto(aspectos,perguntas,1);

        assertEquals(aspectos.size(), retorno.size());

        Aspecto retorno1 = (Aspecto) retorno.toArray()[0];
        assertEquals(2, retorno1.getPerguntas().size());

        Aspecto retorno2 = (Aspecto) retorno.toArray()[1];
        assertEquals(1, retorno2.getPerguntas().size());
    }

    public void testDesagruparPerguntasByAspecto()
    {

    	Pergunta pergunta1 = PerguntaFactory.getEntity(1L);
    	Pergunta pergunta2 = PerguntaFactory.getEntity(2L);
    	Pergunta pergunta3 = PerguntaFactory.getEntity(3L);

    	Collection<Pergunta> perguntas = new ArrayList<Pergunta>();
    	perguntas.add(pergunta1);
    	perguntas.add(pergunta2);

    	Collection<Pergunta> perguntas2 = new ArrayList<Pergunta>();
    	perguntas2.add(pergunta3);

    	Aspecto aspecto1 = AspectoFactory.getEntity(1L);
    	aspecto1.setPerguntas(perguntas);
    	Aspecto aspecto2 = AspectoFactory.getEntity(2L);
    	aspecto2.setPerguntas(perguntas2);

    	Collection<Aspecto> aspectos = new ArrayList<Aspecto>();
    	aspectos.add(aspecto1);
    	aspectos.add(aspecto2);

    	Collection<Pergunta> retorno = aspectoManager.desagruparPerguntasByAspecto(aspectos);

    	assertEquals(perguntas.size()+perguntas2.size(), retorno.size());
    }

    public void testSaveOrGetAspectoByNome()
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);

    	Aspecto aspecto = AspectoFactory.getEntity();

    	Aspecto aspectoSalvo = AspectoFactory.getEntity(1L);

    	aspectoDao.expects(once()).method("findByNomeQuestionario").with(eq(aspecto.getNome()),eq(questionario.getId())).will(returnValue(null));
    	aspectoDao.expects(once()).method("save").with(ANYTHING).will(returnValue(aspectoSalvo));

    	Aspecto aspectoRetorno = aspectoManager.saveOrGetAspectoByNome(aspecto.getNome(),questionario.getId());

    	assertEquals(aspectoSalvo.getId(), aspectoRetorno.getId());
    }
    
    public void testSaveByAvaliacao()
    {
    	Avaliacao avaliacao = AvaliacaoFactory.getEntity(1L);
    	Aspecto aspecto = AspectoFactory.getEntity();
    	
    	Aspecto aspectoSalvo = AspectoFactory.getEntity(1L);
    	
    	aspectoDao.expects(once()).method("findByNomeAvaliacao").with(eq(aspecto.getNome()),eq(avaliacao.getId())).will(returnValue(null));
    	aspectoDao.expects(once()).method("save").with(ANYTHING).will(returnValue(aspectoSalvo));
    	
    	Aspecto aspectoRetorno = aspectoManager.saveByAvaliacao(aspecto, avaliacao);
    	
    	assertEquals(aspectoSalvo.getId(), aspectoRetorno.getId());
    }

    public void testPopulaCheckOrderNome()
	{
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
		Collection<Aspecto> aspectos = new ArrayList<Aspecto>();

		aspectoDao.expects(once()).method("findByQuestionario").with(eq(questionario.getId())).will(returnValue(aspectos));

		assertEquals(aspectoManager.populaCheckOrderNome(questionario.getId()), new ArrayList<CheckBox>());
	}

    public void testPopulaCheckOrderNomeException()
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);

    	aspectoManager.setDao(null);

    	assertEquals(aspectoManager.populaCheckOrderNome(questionario.getId()), new ArrayList<CheckBox>());
    }
    
    public void testClonarAspectos()
    {
    	Questionario questionario = QuestionarioFactory.getEntity(1L);
    	Questionario questionarioClonado = QuestionarioFactory.getEntity(2L);

    	Aspecto aspecto1 = AspectoFactory.getEntity(1L);
    	aspecto1.setQuestionario(questionario);
    	
    	Aspecto aspecto2 = AspectoFactory.getEntity(2L);
    	aspecto2.setQuestionario(questionario);

    	//Aspectos clones
    	Aspecto aspecto3 = AspectoFactory.getEntity(3L);
    	aspecto3.setQuestionario(questionario);
    	
    	Aspecto aspecto4 = AspectoFactory.getEntity(4L);
    	aspecto4.setQuestionario(questionario);
    	
    	Collection<Aspecto> aspectos = new ArrayList<Aspecto>();
    	aspectos.add(aspecto1);
    	aspectos.add(aspecto2);
   
    	aspectoDao.expects(once()).method("findByQuestionario").with(eq(questionario.getId())).will(returnValue(aspectos));

    	aspectoDao.expects(once()).method("save").with(ANYTHING).will(returnValue(aspecto3));
    	aspectoDao.expects(once()).method("save").with(ANYTHING).will(returnValue(aspecto4));
    	
    	HashMap<Long, Aspecto> aspectosClonados = aspectoManager.clonarAspectos(questionario.getId(), questionarioClonado, null);

    	assertEquals("Quantidade de clones", 2, aspectosClonados.keySet().size());
    	assertEquals("Clone do aspecto1", aspecto4, aspectosClonados.get(aspecto1.getId()));
    	assertEquals("Clone do aspecto2", aspecto3, aspectosClonados.get(aspecto2.getId()));
    }
    
    public void testGetAspectosFormatadosByAvaliacaoSemAspectosCadastrados()
    {
    	aspectoDao.expects(once()).method("getNomesByAvaliacao").with(eq(1L)).will(returnValue(new ArrayList<String>()));
    	
    	assertEquals("Nenhum aspecto cadastrado", aspectoManager.getAspectosFormatadosByAvaliacao(1L));
    }
    
    public void testGetAspectosFormatadosByAvaliacao()
    {
    	Collection<String> nomesAspectos = Arrays.asList("Habilidades interpessoais", "Habilidades técnicas");
    	
    	aspectoDao.expects(once()).method("getNomesByAvaliacao").with(eq(1L)).will(returnValue(nomesAspectos));
    	
    	String aspectosFormatadosHtml = aspectoManager.getAspectosFormatadosByAvaliacao(1L);
    	assertEquals("<a href=\"#\" onclick=\"setAspecto(this.innerText)\">Habilidades interpessoais</a><br><a href=\"#\" onclick=\"setAspecto(this.innerText)\">Habilidades técnicas</a><br>", aspectosFormatadosHtml);
    }

}
