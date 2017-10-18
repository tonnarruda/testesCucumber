package com.fortes.rh.test.business.geral;

import java.util.ArrayList;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.geral.EstabelecimentoManagerImpl;
import com.fortes.rh.dao.geral.EstabelecimentoDao;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.Estabelecimento;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.geral.EstabelecimentoFactory;

public class EstabelecimentoManagerTest extends MockObjectTestCase
{
    private final String CODIGO = "123456";
    private final String EMP_CODIGO = "001122";
    private final Long EMP_ID = 1L;

    EstabelecimentoManagerImpl estabelecimentoManager = null;
    Mock estabelecimentoDao = null;

    protected void setUp() throws Exception
    {
        super.setUp();
        estabelecimentoManager = new EstabelecimentoManagerImpl();

        estabelecimentoDao = new Mock(EstabelecimentoDao.class);
        estabelecimentoManager.setDao((EstabelecimentoDao) estabelecimentoDao.proxy());
    }

    public void testFindByCodigo()
    {
        Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity();
        estabelecimento.setId(EMP_ID);

        estabelecimentoDao.expects(once()).method("findByCodigo").with(new Constraint[]{eq(CODIGO), eq(EMP_CODIGO), eq("XXX")}).will(returnValue(estabelecimento));

        assertEquals(estabelecimento, estabelecimentoManager.findByCodigo(CODIGO, EMP_CODIGO, "XXX"));
    }

    public void testRemove()
    {
        estabelecimentoDao.expects(once()).method("remove").with(new Constraint[]{eq(CODIGO), eq(1L)}).will(returnValue(true));
        assertTrue(estabelecimentoManager.remove(CODIGO, EMP_ID));

        estabelecimentoDao.expects(once()).method("remove").with(new Constraint[]{eq(CODIGO), eq(1L)}).will(returnValue(false));
        assertFalse(estabelecimentoManager.remove(CODIGO, EMP_ID));
    }

    public void testFindAllSelect()
    {
        Collection<Estabelecimento> estabelecimentos = new ArrayList<Estabelecimento>();

        estabelecimentoDao.expects(once()).method("findAllSelect").with(eq(EMP_ID)).will(returnValue(estabelecimentos));

        assertEquals(estabelecimentos, estabelecimentoManager.findAllSelect(EMP_ID));
    }

    public void testFindEstabelecimentoCodigoAc()
    {
        Estabelecimento estabelecimentoRetorno = new Estabelecimento();
        estabelecimentoRetorno.setId(1L);
        estabelecimentoRetorno.setCodigoAC("0001");

        estabelecimentoDao.expects(once()).method("findEstabelecimentoCodigoAc").with(eq(estabelecimentoRetorno.getId())).will(returnValue(estabelecimentoRetorno));

        Estabelecimento estabelecimeto = estabelecimentoManager.findEstabelecimentoCodigoAc(estabelecimentoRetorno.getId());
        assertEquals("0001", estabelecimeto.getCodigoAC());
    }

    public void testVerificaCnpjExiste()
    {
        estabelecimentoDao.expects(once()).method("verificaCnpjExiste").with(ANYTHING, ANYTHING, ANYTHING).will(returnValue(false));

        assertFalse(estabelecimentoManager.verificaCnpjExiste("0001", 1L, 1L));
    }

    public void testCalculaDV()
    {
        String cnpj = "123456781234";

        assertEquals("32", estabelecimentoManager.calculaDV(cnpj));
    }

    public void testPopulaCheckBox()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);

    	Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
    	estabelecimento.setNome("estabelecimento");

    	Collection<Estabelecimento> estabelecimentos = new ArrayList<Estabelecimento>();
    	estabelecimentos.add(estabelecimento);

    	estabelecimentoDao.expects(once()).method("findAllSelect").with(eq(empresa.getId())).will(returnValue(estabelecimentos));

    	assertEquals(1, estabelecimentoManager.populaCheckBox(empresa.getId()).size());
    }
    
    public void testNomeEstabelecimentos()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	
    	Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
    	estabelecimento.setEmpresa(empresa);
    	estabelecimento.setNome("estabelecimento");

    	Estabelecimento estabelecimento2 = EstabelecimentoFactory.getEntity(2L);
    	estabelecimento2.setNome("matriz");
    	
    	Collection<Estabelecimento> estabelecimentos = new ArrayList<Estabelecimento>();
    	estabelecimentos.add(estabelecimento);
    	
    	estabelecimentoDao.expects(once()).method("findEstabelecimentos").with(eq(null), eq(empresa.getId())).will(returnValue(estabelecimentos));
    	
    	assertEquals("estabelecimento", estabelecimentoManager.nomeEstabelecimentos(null, empresa.getId()));

    	estabelecimentos.add(estabelecimento2);
    	
    	estabelecimentoDao.expects(once()).method("findEstabelecimentos").with(eq(null), eq(null)).will(returnValue(estabelecimentos));
    	assertEquals("estabelecimento, matriz", estabelecimentoManager.nomeEstabelecimentos(null, null));
    }
    
    public void testPopulaCheckBoxArrayEmpresa()
    {
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	
    	Estabelecimento estabelecimento = EstabelecimentoFactory.getEntity(1L);
    	estabelecimento.setNome("estabelecimento");
    	
    	Collection<Estabelecimento> estabelecimentos = new ArrayList<Estabelecimento>();
    	estabelecimentos.add(estabelecimento);
    	
    	estabelecimentoDao.expects(once()).method("findAllSelect").with(ANYTHING).will(returnValue(estabelecimentos));
    	
    	assertEquals(1, estabelecimentoManager.populaCheckBox(new Long[]{empresa.getId()}).size());
    }
}