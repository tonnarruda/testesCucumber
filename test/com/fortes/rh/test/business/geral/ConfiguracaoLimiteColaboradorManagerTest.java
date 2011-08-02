package com.fortes.rh.test.business.geral;

import java.util.Arrays;
import java.util.Collection;

import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;
import org.jmock.core.Constraint;

import com.fortes.rh.business.cargosalario.CargoManager;
import com.fortes.rh.business.geral.AreaOrganizacionalManager;
import com.fortes.rh.business.geral.ConfiguracaoLimiteColaboradorManagerImpl;
import com.fortes.rh.business.geral.EmpresaManager;
import com.fortes.rh.model.cargosalario.Cargo;
import com.fortes.rh.model.geral.AreaOrganizacional;
import com.fortes.rh.model.geral.ConfiguracaoLimiteColaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.QuantidadeLimiteColaboradoresPorCargo;
import com.fortes.rh.test.factory.captacao.AreaOrganizacionalFactory;
import com.fortes.rh.test.factory.captacao.EmpresaFactory;
import com.fortes.rh.test.factory.cargosalario.CargoFactory;
import com.fortes.rh.test.factory.geral.ConfiguracaoLimiteColaboradorFactory;
import com.fortes.rh.util.Mail;

public class ConfiguracaoLimiteColaboradorManagerTest extends MockObjectTestCase
{
	private ConfiguracaoLimiteColaboradorManagerImpl configuracaoLimiteColaboradorManagerImpl = new ConfiguracaoLimiteColaboradorManagerImpl();
	private Mock cargoManager;
	private Mock  areaOrganizacionalManager;
	private Mock  empresaManager;
	private Mock mail = null;

    protected void setUp() throws Exception
    {
        super.setUp();
        
        cargoManager = new Mock(CargoManager.class);
        configuracaoLimiteColaboradorManagerImpl.setCargoManager((CargoManager) cargoManager.proxy());

        areaOrganizacionalManager = new Mock(AreaOrganizacionalManager.class);
        configuracaoLimiteColaboradorManagerImpl.setAreaOrganizacionalManager((AreaOrganizacionalManager) areaOrganizacionalManager.proxy());

        empresaManager = new Mock(EmpresaManager.class);
        configuracaoLimiteColaboradorManagerImpl.setEmpresaManager((EmpresaManager) empresaManager.proxy());
        
        mail = mock(Mail.class);
        configuracaoLimiteColaboradorManagerImpl.setMail((Mail) mail.proxy());
    }

    public void testEnviaEmail()
    {
    	AreaOrganizacional areaOrganizacional = AreaOrganizacionalFactory.getEntity(1L);
    	
    	ConfiguracaoLimiteColaborador configuracaoLimiteColaborador = ConfiguracaoLimiteColaboradorFactory.getEntity(1L);
    	configuracaoLimiteColaborador.setDescricao("contrato");
    	configuracaoLimiteColaborador.setAreaOrganizacional(areaOrganizacional);
    	
    	QuantidadeLimiteColaboradoresPorCargo qtdLimiteColabCargo = new QuantidadeLimiteColaboradoresPorCargo();
    	qtdLimiteColabCargo.setCargo(CargoFactory.getEntity(1L));
    	qtdLimiteColabCargo.setLimite(3);
    	Collection<QuantidadeLimiteColaboradoresPorCargo> quantidadeLimiteColaboradoresPorCargos = Arrays.asList(qtdLimiteColabCargo);
    	
    	Empresa empresa = EmpresaFactory.getEmpresa(1L);
    	empresa.setEmailRespLimiteContrato("dsfs@fasda.com");
    	
    	empresaManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(empresa));
    	areaOrganizacionalManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(new AreaOrganizacional()));
    	cargoManager.expects(once()).method("findByIdProjection").with(ANYTHING).will(returnValue(new Cargo()));
    	mail.expects(once()).method("send").with(new Constraint[]{ANYTHING,ANYTHING,ANYTHING,ANYTHING,ANYTHING});

    	Exception exception = null;
    	try {
    		configuracaoLimiteColaboradorManagerImpl.enviaEmail(configuracaoLimiteColaborador, quantidadeLimiteColaboradoresPorCargos, empresa);
		} catch (Exception e) {
			exception = e;
		}
		
		assertNull(exception);
    }
}
