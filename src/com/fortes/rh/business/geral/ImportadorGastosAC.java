package com.fortes.rh.business.geral;

import java.util.Date;

import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.web.ws.AcPessoalImportadorGastos;

public class ImportadorGastosAC
{
	private GastoEmpresaManager gastoEmpresaManager;
	private EmpresaManager empresaManager;
	private AcPessoalImportadorGastos acPessoalImportadorGastos;

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

	public void setAcPessoalImportadorGastos(AcPessoalImportadorGastos acPessoalImportadorGastos)
	{
		this.acPessoalImportadorGastos = acPessoalImportadorGastos;
	}

	public void setEmpresaManager(EmpresaManager empresaManager)
	{
		this.empresaManager = empresaManager;
	}

	public void setGastoEmpresaManager(GastoEmpresaManager gastoEmpresaManager)
	{
		this.gastoEmpresaManager = gastoEmpresaManager;
	}
}