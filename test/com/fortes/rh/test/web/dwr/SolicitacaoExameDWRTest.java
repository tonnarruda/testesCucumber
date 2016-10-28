package com.fortes.rh.test.web.dwr;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.ObjectNotFoundException;
import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.springframework.orm.hibernate4.HibernateObjectRetrievalFailureException;
import org.springframework.transaction.PlatformTransactionManager;

import com.fortes.rh.business.sesmt.ExameSolicitacaoExameManager;
import com.fortes.rh.business.sesmt.RealizacaoExameManager;
import com.fortes.rh.model.dicionario.ResultadoExame;
import com.fortes.rh.model.sesmt.Exame;
import com.fortes.rh.model.sesmt.ExameSolicitacaoExame;
import com.fortes.rh.model.sesmt.RealizacaoExame;
import com.fortes.rh.model.sesmt.SolicitacaoExame;
import com.fortes.rh.test.factory.sesmt.ExameFactory;
import com.fortes.rh.test.factory.sesmt.RealizacaoExameFactory;
import com.fortes.rh.test.factory.sesmt.SolicitacaoExameFactory;
import com.fortes.rh.test.util.mockObjects.MockTransactionStatus;
import com.fortes.rh.web.dwr.SolicitacaoExameDWR;

public class SolicitacaoExameDWRTest extends MockObjectTestCase 
{
	private SolicitacaoExameDWR solicitacaoExameDWR;
	private Mock exameSolicitacaoExameManager;
	private Mock realizacaoExameManager;
	private Mock transactionManager;
	
	protected void setUp() throws Exception
	{
		solicitacaoExameDWR = new SolicitacaoExameDWR();

		exameSolicitacaoExameManager = new Mock(ExameSolicitacaoExameManager.class);
		solicitacaoExameDWR.setExameSolicitacaoExameManager((ExameSolicitacaoExameManager) exameSolicitacaoExameManager.proxy());
		realizacaoExameManager = new Mock(RealizacaoExameManager.class);
		solicitacaoExameDWR.setRealizacaoExameManager((RealizacaoExameManager) realizacaoExameManager.proxy());
		transactionManager = new Mock(PlatformTransactionManager.class);
		solicitacaoExameDWR.setTransactionManager((PlatformTransactionManager) transactionManager.proxy());
	}
	
	public void testMarcarNaoInformadosComoNormal()
	{
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(1L);
		
		Collection<ExameSolicitacaoExame> exameSolicitacaoExames = new ArrayList<ExameSolicitacaoExame>();
		Exame exame1 = ExameFactory.getEntity(1L);
		Exame exame2 = ExameFactory.getEntity(2L);
		Exame exame3 = ExameFactory.getEntity(3L);
		
		ExameSolicitacaoExame exameSolicitacaoExameSemResultadoGravado = new ExameSolicitacaoExame();
		exameSolicitacaoExameSemResultadoGravado.setExame(exame1);
		exameSolicitacaoExameSemResultadoGravado.setRealizacaoExame(new RealizacaoExame());
		exameSolicitacaoExameSemResultadoGravado.setSolicitacaoExame(solicitacaoExame);
		exameSolicitacaoExames.add(exameSolicitacaoExameSemResultadoGravado);
		
		
		ExameSolicitacaoExame exameSolicitacaoExame2ComResultadoNormal = new ExameSolicitacaoExame();
		exameSolicitacaoExame2ComResultadoNormal.setExame(exame2);
		RealizacaoExame realizacaoExameNormal = RealizacaoExameFactory.getEntity(2L);
		realizacaoExameNormal.setResultado(ResultadoExame.NORMAL.toString());
		exameSolicitacaoExame2ComResultadoNormal.setRealizacaoExame(realizacaoExameNormal);
		exameSolicitacaoExame2ComResultadoNormal.setSolicitacaoExame(solicitacaoExame);
		exameSolicitacaoExames.add(exameSolicitacaoExame2ComResultadoNormal);
		
		ExameSolicitacaoExame exameSolicitacaoExame3NaoInformado = new ExameSolicitacaoExame();
		exameSolicitacaoExame3NaoInformado.setExame(exame3);
		RealizacaoExame realizacaoExameNaoInformado = RealizacaoExameFactory.getEntity(1L);
		realizacaoExameNaoInformado.setResultado(ResultadoExame.NAO_REALIZADO.toString());
		exameSolicitacaoExame3NaoInformado.setRealizacaoExame(realizacaoExameNaoInformado);
		exameSolicitacaoExame3NaoInformado.setSolicitacaoExame(solicitacaoExame);
		exameSolicitacaoExames.add(exameSolicitacaoExame3NaoInformado);
		
		
		
		exameSolicitacaoExameManager.expects(once()).method("findBySolicitacaoExame").will(returnValue(exameSolicitacaoExames));
		transactionManager.expects(once()).method("getTransaction").will(returnValue(new MockTransactionStatus()));
		realizacaoExameManager.expects(once()).method("save");
		realizacaoExameManager.expects(once()).method("marcarResultadoComoNormal");
		transactionManager.expects(once()).method("commit");
		
		assertEquals("1_1", solicitacaoExameDWR.marcarNaoInformadosComoNormal(solicitacaoExame.getId())[0]);
	}
	
	public void testMarcarNaoInformadosComoNormalException()
	{
		SolicitacaoExame solicitacaoExame = SolicitacaoExameFactory.getEntity(33L);
		
		Collection<ExameSolicitacaoExame> exameSolicitacaoExames = new ArrayList<ExameSolicitacaoExame>();
		
		ExameSolicitacaoExame exameSolicitacaoExameSemResultadoGravado = new ExameSolicitacaoExame();
		exameSolicitacaoExameSemResultadoGravado.setRealizacaoExame(new RealizacaoExame());
		exameSolicitacaoExameSemResultadoGravado.setSolicitacaoExame(solicitacaoExame);
		exameSolicitacaoExames.add(exameSolicitacaoExameSemResultadoGravado);
		
		exameSolicitacaoExameManager.expects(once()).method("findBySolicitacaoExame").will(returnValue(exameSolicitacaoExames));
		transactionManager.expects(once()).method("getTransaction").will(returnValue(new MockTransactionStatus()));
		realizacaoExameManager.expects(once()).method("save").will(throwException(new HibernateObjectRetrievalFailureException(new ObjectNotFoundException(exameSolicitacaoExameSemResultadoGravado.getId(),""))));
		
		transactionManager.expects(once()).method("rollback");
		
		assertEquals(null, solicitacaoExameDWR.marcarNaoInformadosComoNormal(solicitacaoExame.getId()));
	}

}
