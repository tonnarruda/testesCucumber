package com.fortes.rh.web.ws;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;

import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.cargosalario.FaixaSalarial;
import com.fortes.rh.model.cargosalario.FaixaSalarialHistorico;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.ws.TCargo;
import com.fortes.rh.model.ws.TFeedbackPessoalWebService;

public class AcPessoalClientCargo
{
	private AcPessoalClient acPessoalClient;

	public boolean deleteCargo(String[] codigoACs, Empresa empresa) throws Exception{
		try{
			StringBuilder token = new StringBuilder();
			GrupoAC grupoAC = new GrupoAC();
			Call call = acPessoalClient.createCall(empresa, token, grupoAC, "DelCargo");
			QName qname = new QName(grupoAC.getAcUrlWsdl(),"TCargo");
	        call.registerTypeMapping(TCargo.class, qname, new BeanSerializerFactory(TCargo.class, qname), new BeanDeserializerFactory(TCargo.class, qname));
			QName xmlstring = new QName("xs:string");
			call.addParameter("Token",xmlstring,ParameterMode.IN);
			call.addParameter("Empresa",xmlstring,ParameterMode.IN);
			call.addParameter("Cargos",org.apache.axis.encoding.XMLType.SOAP_ARRAY,ParameterMode.IN);
			acPessoalClient.setReturnType(call, grupoAC.getAcUrlWsdl());
			TCargo[] cargosAC = new TCargo[codigoACs.length];

			int i = 0;
			for (String codigoAC: codigoACs){
				TCargo cargoAC = new TCargo();
				cargoAC.setCodigo(codigoAC);
				cargosAC[i++] = cargoAC;
			}

			Object[] param = new Object[]{token.toString(), empresa.getCodigoAC(), cargosAC};
			TFeedbackPessoalWebService result =  (TFeedbackPessoalWebService) call.invoke(param);
			
			return result.getSucesso("DelCargo", param, this.getClass());
		}catch (Exception e){
			e.printStackTrace();
			throw new Exception("Erro ao remover Cargo no Fortes Pessoal.");
		}
	}

	public String criarCargo(FaixaSalarial faixaSalarial, FaixaSalarialHistorico faixaSalarialHistorico, Empresa empresa) throws Exception{
        try {
        	StringBuilder token = new StringBuilder();
        	GrupoAC grupoAC = new GrupoAC();
        	Call call = acPessoalClient.createCall(empresa, token, grupoAC, "SetCargoComSituacao");
            montaParametrosCallCriarCargo(call);
        	acPessoalClient.setReturnType(call, grupoAC.getAcUrlWsdl());
        	DateFormat formata = new SimpleDateFormat("dd/MM/yyyy");

        	String indiceCodicoAC = "";
        	if(faixaSalarialHistorico != null && faixaSalarialHistorico.getIndice() != null && faixaSalarialHistorico.getIndice().getCodigoAC() != null)
        		indiceCodicoAC = faixaSalarialHistorico.getIndice().getCodigoAC();
        	if(faixaSalarialHistorico != null && faixaSalarialHistorico.getQuantidade() == null)
        		faixaSalarialHistorico.setQuantidade(0.0);
        	Double valor = 0.0;
        	if(faixaSalarialHistorico != null && faixaSalarialHistorico.getTipo() == TipoAplicacaoIndice.VALOR)
        		valor = faixaSalarialHistorico.getValor();
        	
        	Object[] param = new Object[]{token.toString(), empresa.getCodigoAC(), "", faixaSalarial.getNome(),null,indiceCodicoAC, null, faixaSalarial.getNomeACPessoal(), valor, 0.0, 0};
        	if(faixaSalarialHistorico != null)
        		param = new Object[]{token.toString(), empresa.getCodigoAC(), "", faixaSalarial.getNome(),formata.format(faixaSalarialHistorico.getData()),indiceCodicoAC, TipoAplicacaoIndice.getCodigoAC(faixaSalarialHistorico.getTipo()), faixaSalarial.getNomeACPessoal(), valor,	faixaSalarialHistorico.getQuantidade(), faixaSalarialHistorico.getId()};
      
        	TFeedbackPessoalWebService result =  (TFeedbackPessoalWebService) call.invoke(param);
        	result.getSucesso("SetCargoComSituacao", param, this.getClass());

            return result.getCodigoretorno();
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
	}

	private void montaParametrosCallCriarCargo(Call call) {
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
	}

	public String createOrUpdateCargo(FaixaSalarial faixaSalarial, Empresa empresa) throws Exception{
		try{
			StringBuilder token = new StringBuilder();
			GrupoAC grupoAC = new GrupoAC();
			Call call = acPessoalClient.createCall(empresa, token, grupoAC, "SetCargo");
			QName xmlstring = new QName("xs:string");
			call.addParameter("Token",xmlstring,ParameterMode.IN);
			call.addParameter("Empresa",xmlstring,ParameterMode.IN);
			call.addParameter("Codigo",xmlstring,ParameterMode.IN);
			call.addParameter("Nome",xmlstring,ParameterMode.IN);
			call.addParameter("NomeACPessoal",xmlstring,ParameterMode.IN);

			acPessoalClient.setReturnType(call, grupoAC.getAcUrlWsdl());
			String codigo = "";
			if(faixaSalarial.getCodigoAC() != null && !faixaSalarial.getCodigoAC().equals(""))
				codigo = faixaSalarial.getCodigoAC();
			
			Object[] param = new Object[]{token.toString(), empresa.getCodigoAC(), codigo, faixaSalarial.getNome(), faixaSalarial.getNomeACPessoal()};
        	TFeedbackPessoalWebService result =  (TFeedbackPessoalWebService) call.invoke(param);
        	result.getSucesso("SetCargo", param, this.getClass());
			return result.getCodigoretorno();
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	public boolean criarFaixaSalarialHistorico(FaixaSalarialHistorico faixaSalarialHistorico, Empresa empresa) throws Exception{
        try{
        	StringBuilder token = new StringBuilder();
        	GrupoAC grupoAC = new GrupoAC();
        	Call call = acPessoalClient.createCall(empresa, token, grupoAC, "SetRhCargos");
            montaParametrosCall(call);
        	acPessoalClient.setReturnType(call, grupoAC.getAcUrlWsdl());
        	DateFormat formata = new SimpleDateFormat("dd/MM/yyyy");

        	Double valor = 0.0;
        	String indiceCodicoAC = "";
        	if(faixaSalarialHistorico.getIndice() != null && faixaSalarialHistorico.getIndice().getCodigoAC() != null)
        		indiceCodicoAC = faixaSalarialHistorico.getIndice().getCodigoAC();
        	if(faixaSalarialHistorico.getQuantidade() == null)
        		faixaSalarialHistorico.setQuantidade(0.0);
        	if(faixaSalarialHistorico.getTipo() == TipoAplicacaoIndice.VALOR)
        		valor = faixaSalarialHistorico.getValor();

        	Object[] param = new Object[]{token.toString(), empresa.getCodigoAC(), faixaSalarialHistorico.getFaixaSalarial().getCodigoAC(), formata.format(faixaSalarialHistorico.getData()), indiceCodicoAC, TipoAplicacaoIndice.getCodigoAC(faixaSalarialHistorico.getTipo()), valor,	faixaSalarialHistorico.getQuantidade(), faixaSalarialHistorico.getId()};
        	TFeedbackPessoalWebService result =  (TFeedbackPessoalWebService) call.invoke(param);
        	boolean retorno = result.getSucesso("SetRhCargos", param, this.getClass()); 
            if(!retorno)
            	throw new IntegraACException("Erro ao cadastrar histórico da faixa salarial no Fortes Pessoal.");
            
            return true;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
	}

	private void montaParametrosCall(Call call){
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
	}

	public boolean deleteFaixaSalarialHistorico(Long faixaSalarialHistoricoId, Empresa empresa) throws Exception{
		try{
			StringBuilder token = new StringBuilder();
			GrupoAC grupoAC = new GrupoAC();
            Call call = acPessoalClient.createCall(empresa, token, grupoAC, "DelRhCargos");
            QName xmlstring = new QName("xs:string");
            QName xmlint = new QName("xs:int");
        	call.addParameter("Token",xmlstring,ParameterMode.IN);
        	call.addParameter("id",xmlint,ParameterMode.IN);
        	acPessoalClient.setReturnType(call, grupoAC.getAcUrlWsdl());
        	Object[] param = new Object[]{token.toString(), faixaSalarialHistoricoId};
        	
        	TFeedbackPessoalWebService result =  (TFeedbackPessoalWebService) call.invoke(param);
        	boolean retorno = result.getSucesso("DelRhCargos", param, this.getClass()); 
            if(!retorno)
            	throw new Exception("Erro ao deletar historico da faixa salarial no Fortes Pessoal");
            
            return true;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
	}

	public void setAcPessoalClient(AcPessoalClient acPessoalClient)
	{
		this.acPessoalClient = acPessoalClient;
	}
}