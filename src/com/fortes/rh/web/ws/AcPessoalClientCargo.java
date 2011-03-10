package com.fortes.rh.web.ws;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;

import com.fortes.rh.exception.ColecaoVaziaException;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.ws.TCargo;

public class AcPessoalClientCargo
{
	private AcPessoalClient acPessoalClient;

	public boolean deleteCargo(String[] codigoACs, Empresa empresa) throws Exception
	{
		try
		{
			StringBuilder token = new StringBuilder();
			GrupoAC grupoAC = new GrupoAC();
			Call call = acPessoalClient.createCall(empresa, token, grupoAC, "DelCargo");

			QName qname = new QName(grupoAC.getAcUrlWsdl(),"TCargo");
	        call.registerTypeMapping(TCargo.class, qname, new BeanSerializerFactory(TCargo.class, qname), new BeanDeserializerFactory(TCargo.class, qname));

			QName xmlstring = new QName("xs:string");

			call.addParameter("Token",xmlstring,ParameterMode.IN);
			call.addParameter("Empresa",xmlstring,ParameterMode.IN);
			call.addParameter("Cargos",org.apache.axis.encoding.XMLType.SOAP_ARRAY,ParameterMode.IN);

			call.setReturnType(org.apache.axis.encoding.XMLType.SOAP_BOOLEAN);

			TCargo[] cargosAC = new TCargo[codigoACs.length];

			int i = 0;
			for (String codigoAC: codigoACs)
			{
				TCargo cargoAC = new TCargo();
				cargoAC.setCodigo(codigoAC);

				cargosAC[i++] = cargoAC;
			}

			Object[] param = new Object[]{token.toString(), empresa.getCodigoAC(), cargosAC};
			boolean result = (Boolean) call.invoke(param);

			return result;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new ColecaoVaziaException("Erro ao remover Cargo no AC Pessoal.");
		}
	}

	public String criarCargo(FaixaSalarial faixaSalarial, FaixaSalarialHistorico faixaSalarialHistorico, Empresa empresa) throws Exception
	{
        try
        {
        	StringBuilder token = new StringBuilder();
        	Call call = acPessoalClient.createCall(empresa, token, null, "SetCargoComSituacao");

            QName xmlstring = new QName("xs:string");
            QName xmldouble = new QName("xs:double");
            QName xmlint = new QName("xs:int");

            //Seta os parâmetros com os tipos e modos
        	call.addParameter("Token",xmlstring,ParameterMode.IN);
        	call.addParameter("Empresa",xmlstring,ParameterMode.IN);
        	call.addParameter("Codigo",xmlstring,ParameterMode.IN);
        	call.addParameter("Nome",xmlstring,ParameterMode.IN);
        	call.addParameter("Data",xmlstring,ParameterMode.IN);
        	call.addParameter("Indice",xmlstring,ParameterMode.IN);
        	call.addParameter("Tipo",xmlstring,ParameterMode.IN);
        	call.addParameter("NomeACPessoal",xmlstring,ParameterMode.IN);
        	call.addParameter("Valor",xmldouble,ParameterMode.IN);
        	call.addParameter("IndiceQuantidade",xmldouble,ParameterMode.IN);
        	call.addParameter("rh_sca_id",xmlint,ParameterMode.IN);

        	//Seta o tipo de resultado
        	call.setReturnType(xmlstring);

        	DateFormat formata = new SimpleDateFormat("dd/MM/yyyy");

        	String indiceCodicoAC = "";
        	if(faixaSalarialHistorico.getIndice() != null && faixaSalarialHistorico.getIndice().getCodigoAC() != null)
        		indiceCodicoAC = faixaSalarialHistorico.getIndice().getCodigoAC();

        	if(faixaSalarialHistorico.getQuantidade() == null)
        		faixaSalarialHistorico.setQuantidade(0.0);

        	Double valor = 0.0;
        	if(faixaSalarialHistorico.getTipo() == TipoAplicacaoIndice.VALOR)
        		valor = faixaSalarialHistorico.getValor();

        	Object[] param = new Object[]{token.toString(), empresa.getCodigoAC(), "", faixaSalarial.getNome(),
        			formata.format(faixaSalarialHistorico.getData()),indiceCodicoAC, TipoAplicacaoIndice.getCodigoAC(faixaSalarialHistorico.getTipo()), faixaSalarial.getNomeACPessoal(), valor,
        			faixaSalarialHistorico.getQuantidade(), faixaSalarialHistorico.getId()};

        	//Retorna codigo caso insert ocorra (que será inserido no codigoAC)
            String codigoRetorno = (String) call.invoke(param);

            return codigoRetorno;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
	}

	public String updateCargo(FaixaSalarial faixaSalarial, Empresa empresa) throws Exception
	{
		try
		{
			StringBuilder token = new StringBuilder();
			Call call = acPessoalClient.createCall(empresa, token, null, "SetCargo");

			QName xmlstring = new QName("xs:string");

			//Seta os parâmetros com os tipos e modos
			call.addParameter("Token",xmlstring,ParameterMode.IN);
			call.addParameter("Empresa",xmlstring,ParameterMode.IN);
			call.addParameter("Codigo",xmlstring,ParameterMode.IN);
			call.addParameter("Nome",xmlstring,ParameterMode.IN);
			call.addParameter("NomeACPessoal",xmlstring,ParameterMode.IN);

			//Seta o tipo de resultado
			call.setReturnType(xmlstring);

			String codigo = "";
			if(faixaSalarial.getCodigoAC() != null && !faixaSalarial.getCodigoAC().equals(""))
				codigo = faixaSalarial.getCodigoAC();
			
			//Seta os valores e invoca o serviço não passa codigo para inserir
			Object[] param = new Object[]{token.toString(), empresa.getCodigoAC(), codigo, faixaSalarial.getNome(), faixaSalarial.getNomeACPessoal()};

			//Retorna codigo caso insert ocorra (que será inserido no codigoAC)
			String codigoRetorno = (String) call.invoke(param);

			return codigoRetorno;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	public boolean criarFaixaSalarialHistorico(FaixaSalarialHistorico faixaSalarialHistorico, Empresa empresa) throws Exception
	{
        try
        {
        	StringBuilder token = new StringBuilder();
            Call call = acPessoalClient.createCall(empresa, token, null, "SetRhCargos");

            QName xmlstring = new QName("xs:string");
            QName xmldouble = new QName("xs:double");
            QName xmlint = new QName("xs:int");

            //Seta os parâmetros com os tipos e modos
        	call.addParameter("Token",xmlstring,ParameterMode.IN);
        	call.addParameter("Empresa",xmlstring,ParameterMode.IN);
        	call.addParameter("Codigo",xmlstring,ParameterMode.IN);
        	call.addParameter("Data",xmlstring,ParameterMode.IN);
        	call.addParameter("Indice",xmlstring,ParameterMode.IN);
        	call.addParameter("Tipo",xmlstring,ParameterMode.IN);
        	call.addParameter("Valor",xmldouble,ParameterMode.IN);
        	call.addParameter("IndiceQuantidade",xmldouble,ParameterMode.IN);
        	call.addParameter("rh_sca_id",xmlint,ParameterMode.IN);

        	//Seta o tipo de resultado
        	call.setReturnType(xmlstring);

        	DateFormat formata = new SimpleDateFormat("dd/MM/yyyy");

        	String indiceCodicoAC = "";
        	if(faixaSalarialHistorico.getIndice() != null && faixaSalarialHistorico.getIndice().getCodigoAC() != null)
        		indiceCodicoAC = faixaSalarialHistorico.getIndice().getCodigoAC();

        	if(faixaSalarialHistorico.getQuantidade() == null)
        		faixaSalarialHistorico.setQuantidade(0.0);

        	Double valor = 0.0;
        	if(faixaSalarialHistorico.getTipo() == TipoAplicacaoIndice.VALOR)
        		valor = faixaSalarialHistorico.getValor();

        	Object[] param = new Object[]{token.toString(), empresa.getCodigoAC(), faixaSalarialHistorico.getFaixaSalarial().getCodigoAC(), formata.format(faixaSalarialHistorico.getData()),
        			indiceCodicoAC, TipoAplicacaoIndice.getCodigoAC(faixaSalarialHistorico.getTipo()), valor,
        			faixaSalarialHistorico.getQuantidade(), faixaSalarialHistorico.getId()};

            if(!(Boolean)call.invoke(param))
            	throw new Exception("Erro ao cadastrar historico da faixa salarial no AC Pessoal");
            
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
	}

	public boolean deleteFaixaSalarialHistorico(Long faixaSalarialHistoricoId, Empresa empresa) throws Exception
	{
		try
        {
			StringBuilder token = new StringBuilder();
            Call call = acPessoalClient.createCall(empresa, token, null, "DelRhCargos");

            QName xmlstring = new QName("xs:string");
            QName xmlint = new QName("xs:int");

            //Seta os parâmetros com os tipos e modos
        	call.addParameter("Token",xmlstring,ParameterMode.IN);
        	call.addParameter("id",xmlint,ParameterMode.IN);

        	//Seta o tipo de resultado
        	call.setReturnType(xmlstring);

        	Object[] param = new Object[]{token.toString(), faixaSalarialHistoricoId};

            if(!(Boolean)call.invoke(param))
            	throw new Exception("Erro ao deletar historico da faixa salarial no AC Pessoal");
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
	}

	public void setAcPessoalClient(AcPessoalClient acPessoalClient)
	{
		this.acPessoalClient = acPessoalClient;
	}
}