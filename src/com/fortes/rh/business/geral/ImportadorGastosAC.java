package com.fortes.rh.business.geral;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.ws.AcPessoalImportadorGastos;

@Component
public class ImportadorGastosAC
{
	@Autowired private GastoEmpresaManager gastoEmpresaManager;
	@Autowired private EmpresaManager empresaManager;
	@Autowired private AcPessoalImportadorGastos acPessoalImportadorGastos;

	@SuppressWarnings("deprecation")
	public void execute()
	{
		Date hoje = new Date();
		hoje.setDate(1);
		Date data = DateUtil.retornaDataAnteriorQtdMeses(hoje, 1, true);

		for (Empresa empresa : empresaManager.findAll())
		{
			if (empresa.isAcIntegra() && empresa.getCodigoAC() != null && !empresa.getCodigoAC().equals(""))
			{
				String[] gastos = acPessoalImportadorGastos.importarGastos(data, empresa);
				try
				{
					if(gastos!= null && gastos.length > 0)
						gastoEmpresaManager.importarGastosAC(empresa, gastos, data);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}