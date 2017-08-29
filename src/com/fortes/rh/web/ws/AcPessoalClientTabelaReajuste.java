package com.fortes.rh.web.ws;

import java.util.Collection;
import java.util.Date;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;

import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.ws.TFeedbackPessoalWebService;
import com.fortes.rh.model.ws.TItemTabelaEmpregados;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.util.DateUtil;

public class AcPessoalClientTabelaReajuste implements AcPessoalClientTabelaReajusteInterface
{
	private AcPessoalClient acPessoalClient;

	public void aplicaReajuste(Collection<HistoricoColaborador> historicosAc, Empresa empresa) throws Exception
	{
		saveHistoricoColaborador(historicosAc, empresa, 0.0, true);
	}

	public void saveHistoricoColaborador(Collection<HistoricoColaborador> historicosAc, Empresa empresa, Double salarioAntigo, boolean ehRealinhamento) throws Exception{
		String exceptionMessage = "Erro ao inserir Situação no Fortes Pessoal";
		 if (ehRealinhamento)
			 exceptionMessage = "Inserção do Planejamento de Realinhamento falhou no Fortes Pessoal";
		
		try{
			if(salarioAntigo == null)
				salarioAntigo = 0.0;

			StringBuilder token = new StringBuilder();
			GrupoAC grupoAC = new GrupoAC();
			Call call = acPessoalClient.createCall(empresa, token, grupoAC, "SetTabelaRhEmpregados");
            QName qname = new QName(grupoAC.getAcUrlWsdl(),"TItemTabelaEmpregados");
            call.registerTypeMapping(TItemTabelaEmpregados.class, qname, new BeanSerializerFactory(TItemTabelaEmpregados.class, qname), new BeanDeserializerFactory(TItemTabelaEmpregados.class, qname));
	        call.addParameter("Token",org.apache.axis.encoding.XMLType.XSD_STRING,ParameterMode.IN);
	        call.addParameter("Tabela",org.apache.axis.encoding.XMLType.SOAP_ARRAY,ParameterMode.IN);
	        acPessoalClient.setReturnType(call, grupoAC.getAcUrlWsdl());
	        TItemTabelaEmpregados[] arrayReajuste = new TItemTabelaEmpregados[historicosAc.size()];

	        int cont = 0;
	        for (HistoricoColaborador historico : historicosAc){
	         	TItemTabelaEmpregados item = new TItemTabelaEmpregados();
	         	item.setObs(historico.getObsACPessoal());
	         	item.setRh_sep_id(Integer.valueOf(historico.getId().toString()));
	         	item.setValor_alt(salarioAntigo); // Este valor só é utilizado no update de um único histórico.
	         	AcPessoalClientUtil.montaParametrosTItemTabelaEmpregado(empresa, historico, item);
	        	arrayReajuste[cont++] = item;
			}

	       	Object[] param = new Object[]{token.toString(), arrayReajuste};
	       	TFeedbackPessoalWebService result =  (TFeedbackPessoalWebService) call.invoke(param);
	       	boolean retorno = result.getSucesso("SetTabelaRhEmpregados", param, this.getClass()); 
	       	if(!retorno) throw new IntegraACException(exceptionMessage);
		}catch (Exception exception){
			exception.printStackTrace();
			throw new IntegraACException(exception, exceptionMessage);
		}
	}

	public void deleteHistoricoColaboradorAC(Empresa empresa, TSituacao... situacao) throws Exception
	{
		try
		{
			StringBuilder token = new StringBuilder();
			GrupoAC grupoAC = new GrupoAC();
			Call call = acPessoalClient.createCall(empresa, token, grupoAC, "DelTabelaRhEmpregados");

			QName xmlstring = new QName("xs:string");

			QName qname = new QName(grupoAC.getAcUrlWsdl(),"TSituacao");
			call.registerTypeMapping(TSituacao.class, qname, new BeanSerializerFactory(TSituacao.class, qname), new BeanDeserializerFactory(TSituacao.class, qname));

			call.addParameter("token",xmlstring,ParameterMode.IN);
			call.addParameter("situacoes", org.apache.axis.encoding.XMLType.SOAP_ARRAY, ParameterMode.IN);

			acPessoalClient.setReturnType(call, grupoAC.getAcUrlWsdl());

			Object[] param = new Object[]{token.toString(), situacao};
			TFeedbackPessoalWebService result =  (TFeedbackPessoalWebService) call.invoke(param);
			boolean retorno = result.getSucesso("DelTabelaRhEmpregados", param, this.getClass()); 
			if(!retorno)
				throw new Exception();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new IntegraACException(e, "Erro ao excluir Situação no Fortes Pessoal.");
		}
	}
	
	public Boolean existeHistoricoContratualComPendenciaNoESocial(Empresa empresa, String colaboradorCodigoAC) throws Exception{
		try {
			StringBuilder token = new StringBuilder();
			GrupoAC grupoAC = new GrupoAC();
			Call call = acPessoalClient.createCall(empresa, token, grupoAC, "ExisteHistoricoContratualPendente");
			QName xmlstring = new QName("xs:string");

			call.addParameter("Token", xmlstring, ParameterMode.IN);
			call.addParameter("Emp_Codigo", xmlstring, ParameterMode.IN);
			call.addParameter("Epg_Codigo", xmlstring, ParameterMode.IN);

			acPessoalClient.setReturnType(call, grupoAC.getAcUrlWsdl());
			
			Object[] param = new Object[] { token.toString(), empresa.getCodigoAC(), colaboradorCodigoAC };

			TFeedbackPessoalWebService result = (TFeedbackPessoalWebService) call .invoke(param);
			return result.getSucesso("ExisteHistoricoContratualPendente", param, this.getClass());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public Boolean situacaoContratualEhInicioVinculo(Empresa empresa, String colaboradorCodigoAC, Date dataSituacao) throws Exception{
		try {
			StringBuilder token = new StringBuilder();
			GrupoAC grupoAC = new GrupoAC();
			Call call = acPessoalClient.createCall(empresa, token, grupoAC, "SituacaoContratualEhInicioVinculo");
			QName xmlstring = new QName("xs:string");

			call.addParameter("Token", xmlstring, ParameterMode.IN);
			call.addParameter("Emp_Codigo", xmlstring, ParameterMode.IN);
			call.addParameter("Epg_Codigo", xmlstring, ParameterMode.IN);
			call.addParameter("Data", xmlstring, ParameterMode.IN);

			acPessoalClient.setReturnType(call, grupoAC.getAcUrlWsdl());
			
			Object[] param = new Object[] { token.toString(), empresa.getCodigoAC(), colaboradorCodigoAC, DateUtil.formataDiaMesAno(dataSituacao) };
			TFeedbackPessoalWebService result = (TFeedbackPessoalWebService) call .invoke(param);
			return result.getSucesso("SituacaoContratualEhInicioVinculo", param, this.getClass());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void setAcPessoalClient(AcPessoalClient acPessoalClient)
	{
		this.acPessoalClient = acPessoalClient;
	}
}