package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;

import com.fortes.rh.business.geral.CamposExtrasManagerImpl;
import com.fortes.rh.business.geral.ConfiguracaoCampoExtraManager;
import com.fortes.rh.dao.geral.CamposExtrasDao;
import com.fortes.rh.model.geral.CamposExtras;
import com.fortes.rh.model.geral.ConfiguracaoCampoExtra;
import com.fortes.rh.test.factory.geral.CamposExtrasFactory;
import com.fortes.rh.test.factory.geral.ConfiguracaoCampoExtraFactory;
import com.fortes.rh.util.DateUtil;

public class CamposExtrasManagerTest extends MockObjectTestCase
{
	private CamposExtrasManagerImpl camposExtrasManager = new CamposExtrasManagerImpl();
	private Mock camposExtrasDao;
	private Mock configuracaoCampoExtraManager;
	
	protected void setUp() throws Exception
    {
        super.setUp();
        camposExtrasDao = new Mock(CamposExtrasDao.class);
        configuracaoCampoExtraManager = new Mock(ConfiguracaoCampoExtraManager.class);
        camposExtrasManager.setDao((CamposExtrasDao) camposExtrasDao.proxy());
        camposExtrasManager.setConfiguracaoCampoExtraManager((ConfiguracaoCampoExtraManager) configuracaoCampoExtraManager.proxy());
    }
	
	public void testUpdate()
	{
		CamposExtras camposExtrasBanco = CamposExtrasFactory.getEntity(1L);
		CamposExtras camposExtras = CamposExtrasFactory.getEntity(2L);
		camposExtras.setTexto1("t1");
		camposExtras.setTexto2("t2");
		camposExtras.setTexto3("t3");
		camposExtras.setTexto4("t4");
		camposExtras.setTexto5("t5");
		camposExtras.setTexto6("t6");
		camposExtras.setTexto7("t7");
		camposExtras.setTexto8("t8");
		camposExtras.setTexto9("t9");
		camposExtras.setTexto10("t10");
		
		Date date1 = DateUtil.criarDataMesAno(1, 1, 1000);
		Date date2 = DateUtil.criarDataMesAno(2, 2, 2000);
		Date date3 = DateUtil.criarDataMesAno(3, 3, 3000);
		camposExtras.setData1(date1);
		camposExtras.setData2(date2);
		camposExtras.setData3(date3);
		
		camposExtras.setValor1(1.0);
		camposExtras.setValor2(2.0);
		camposExtras.setNumero1(1);
		
		Collection<ConfiguracaoCampoExtra> configuracaoCampoExtras = configuracaoCampoExtras = new ArrayList<ConfiguracaoCampoExtra>();
		ConfiguracaoCampoExtra texto1 = ConfiguracaoCampoExtraFactory.getEntity("texto1");
		ConfiguracaoCampoExtra texto2 = ConfiguracaoCampoExtraFactory.getEntity("texto2");
		ConfiguracaoCampoExtra texto3 = ConfiguracaoCampoExtraFactory.getEntity("texto3");
		ConfiguracaoCampoExtra texto4 = ConfiguracaoCampoExtraFactory.getEntity("texto4");
		ConfiguracaoCampoExtra texto5 = ConfiguracaoCampoExtraFactory.getEntity("texto5");
		ConfiguracaoCampoExtra texto6 = ConfiguracaoCampoExtraFactory.getEntity("texto6");
		ConfiguracaoCampoExtra texto7 = ConfiguracaoCampoExtraFactory.getEntity("texto7");
		ConfiguracaoCampoExtra texto8 = ConfiguracaoCampoExtraFactory.getEntity("texto8");
		ConfiguracaoCampoExtra texto9 = ConfiguracaoCampoExtraFactory.getEntity("texto9");
		ConfiguracaoCampoExtra texto10 = ConfiguracaoCampoExtraFactory.getEntity("texto10");

		ConfiguracaoCampoExtra data1 = ConfiguracaoCampoExtraFactory.getEntity("data1");
		ConfiguracaoCampoExtra data2 = ConfiguracaoCampoExtraFactory.getEntity("data2");
		ConfiguracaoCampoExtra data3 = ConfiguracaoCampoExtraFactory.getEntity("data3");

		ConfiguracaoCampoExtra valor1 = ConfiguracaoCampoExtraFactory.getEntity("valor1");
		ConfiguracaoCampoExtra valor2 = ConfiguracaoCampoExtraFactory.getEntity("valor2");

		ConfiguracaoCampoExtra numero1 = ConfiguracaoCampoExtraFactory.getEntity("numero1");
		
		configuracaoCampoExtras.add(texto1);
		configuracaoCampoExtras.add(texto2);
		configuracaoCampoExtras.add(texto3);
		configuracaoCampoExtras.add(texto4);
		configuracaoCampoExtras.add(texto5);
		configuracaoCampoExtras.add(texto6);
		configuracaoCampoExtras.add(texto7);
		configuracaoCampoExtras.add(texto8);
		configuracaoCampoExtras.add(texto9);
		configuracaoCampoExtras.add(texto10);
		configuracaoCampoExtras.add(data1);
		configuracaoCampoExtras.add(data2);
		configuracaoCampoExtras.add(data3);
		configuracaoCampoExtras.add(valor1);
		configuracaoCampoExtras.add(valor2);
		configuracaoCampoExtras.add(numero1);
		
		configuracaoCampoExtraManager.expects(once()).method("find").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(configuracaoCampoExtras));
		camposExtrasDao.expects(once()).method("findById").with(ANYTHING).will(returnValue(camposExtrasBanco));
		camposExtrasDao.expects(once()).method("update").with(ANYTHING);
		
		CamposExtras camposExtrasTemp = camposExtrasManager.update(camposExtras, 2L);
		assertEquals("t1", camposExtrasTemp.getTexto1());
		assertEquals("t2", camposExtrasTemp.getTexto2());
		assertEquals("t3", camposExtrasTemp.getTexto3());
		assertEquals("t4", camposExtrasTemp.getTexto4());
		assertEquals("t5", camposExtrasTemp.getTexto5());
		assertEquals("t6", camposExtrasTemp.getTexto6());
		assertEquals("t7", camposExtrasTemp.getTexto7());
		assertEquals("t8", camposExtrasTemp.getTexto8());
		assertEquals("t9", camposExtrasTemp.getTexto9());
		assertEquals("t10", camposExtrasTemp.getTexto10());
		assertEquals(date1, camposExtrasTemp.getData1());
		assertEquals(date2, camposExtrasTemp.getData2());
		assertEquals(date3, camposExtrasTemp.getData3());
		assertEquals(1.0, camposExtrasTemp.getValor1());
		assertEquals(2.0, camposExtrasTemp.getValor2());
		assertEquals(new Integer(1), camposExtrasTemp.getNumero1());
		
		camposExtrasDao.expects(once()).method("save").with(ANYTHING);
		camposExtrasTemp = camposExtrasManager.update(camposExtras, null);
	}
}
