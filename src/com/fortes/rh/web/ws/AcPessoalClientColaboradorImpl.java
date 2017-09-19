package com.fortes.rh.web.ws;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.encoding.ser.ArrayDeserializerFactory;
import org.apache.axis.encoding.ser.ArraySerializerFactory;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;

import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.ws.TEmpregado;
import com.fortes.rh.model.ws.TFeedbackPessoalWebService;
import com.fortes.rh.model.ws.TItemTabelaEmpregados;
import com.fortes.rh.model.ws.TNaturalidadeAndNacionalidade;
import com.fortes.rh.model.ws.TPeriodoGozo;
import com.fortes.rh.model.ws.TRemuneracaoVariavel;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.util.DateUtil;
import com.fortes.rh.util.StringUtil;

public class AcPessoalClientColaboradorImpl implements AcPessoalClientColaborador {
	private AcPessoalClient acPessoalClient;

	public void setAcPessoalClient(AcPessoalClient acPessoalClient) {
		this.acPessoalClient = acPessoalClient;
	}

	//atualiza apenas o epg
	public void atualizar(TEmpregado empregado, Empresa empresa, Date dataAlteracao) throws IntegraACException, Exception {
		try {
			StringBuilder token = new StringBuilder();
			GrupoAC grupoAC = new GrupoAC();
			Call call = acPessoalClient.createCall(empresa, token, grupoAC, "atualizarEmpregadoComHistorico");

			QName qname = new QName(grupoAC.getAcUrlWsdl(), "TEmpregado");
			call.registerTypeMapping(TEmpregado.class, qname, new BeanSerializerFactory(TEmpregado.class, qname), new BeanDeserializerFactory(TEmpregado.class, qname));

			QName xmltype = new QName("xs:TEmpregado");
			QName xmlstring = new QName("xs:string");

			call.addParameter("Token", xmlstring, ParameterMode.IN);
			call.addParameter("Empregado", xmltype, ParameterMode.IN);
			call.addParameter("DataHistorico", xmlstring, ParameterMode.IN);

			acPessoalClient.setReturnType(call, grupoAC.getAcUrlWsdl());

			Object[] param = new Object[] { token.toString(), empregado, DateUtil.formataDiaMesAno(dataAlteracao)};

			TFeedbackPessoalWebService result =  (TFeedbackPessoalWebService) call.invoke(param);
			boolean retorno = result.getSucesso("atualizarEmpregadoComHistorico", param, this.getClass()); 
			if(!retorno){
				throw new Exception(result.getException());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IntegraACException(e, "Não foi possível gravar a atualização das informações. " + e.getMessage());
		}
	}

	public boolean contratar(TEmpregado empregado, TSituacao situacao, Empresa empresa) {
		try {
			StringBuilder token = new StringBuilder();
			GrupoAC grupoAC = new GrupoAC();
			Call call = acPessoalClient.createCall(empresa, token, grupoAC, "contratar");

			QName qnameEmpregado = new QName(grupoAC.getAcUrlWsdl(), "TEmpregado");
			call.registerTypeMapping(TEmpregado.class, qnameEmpregado, new BeanSerializerFactory(TEmpregado.class, qnameEmpregado), new BeanDeserializerFactory(TEmpregado.class, qnameEmpregado));

			QName qnameSituacao = new QName(grupoAC.getAcUrlWsdl(), "TSituacao");
			call.registerTypeMapping(TSituacao.class, qnameSituacao, new BeanSerializerFactory(TSituacao.class, qnameSituacao), new BeanDeserializerFactory(TSituacao.class, qnameSituacao));

			QName xmltype = new QName("ns1:TEmpregado");
			QName xmltype2 = new QName("ns1:TSituacao");
			QName xmlstring = new QName("xs:string");

			call.addParameter("Token", xmlstring, ParameterMode.IN);
			call.addParameter("Empregado", xmltype, ParameterMode.IN);
			call.addParameter("Situacao", xmltype2, ParameterMode.IN);

			acPessoalClient.setReturnType(call, grupoAC.getAcUrlWsdl());
			Object[] param = new Object[] { token.toString(), empregado, situacao };

			TFeedbackPessoalWebService result =  (TFeedbackPessoalWebService) call.invoke(param);
			return result.getSucesso("contratar", param, this.getClass()); 
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean verificaHistoricoNaFolhaAC(Long historicoColaboradorId, String colaboradorCodigoAC, Empresa empresa) {
		try {
			StringBuilder token = new StringBuilder();
			GrupoAC grupoAC = new GrupoAC();
			Call call = acPessoalClient.createCall(empresa, token, grupoAC, "GetSepEfo");

			QName xmlstring = new QName("xs:string");
			QName xmlint = new QName("xs:int");

			call.addParameter("Token", xmlstring, ParameterMode.IN);
			call.addParameter("Empresa", xmlstring, ParameterMode.IN);
			call.addParameter("Empregado", xmlstring, ParameterMode.IN);
			call.addParameter("RH_SEP_ID", xmlint, ParameterMode.IN);

			acPessoalClient.setReturnType(call, grupoAC.getAcUrlWsdl());

			Object[] param = new Object[] { token.toString(), empresa.getCodigoAC(), colaboradorCodigoAC, historicoColaboradorId };

			TFeedbackPessoalWebService result = (TFeedbackPessoalWebService) call.invoke(param);
			return result.getSucesso("GetSepEfo", param, this.getClass());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean remove(Colaborador colaborador, Empresa empresa) {
		try {
			StringBuilder token = new StringBuilder();
			GrupoAC grupoAC = new GrupoAC();
			Call call = acPessoalClient.createCall(empresa, token, grupoAC, "removerEmpregado");

			QName xmlstring = new QName("xs:string");
			QName xmlint = new QName("xs:int");

			call.addParameter("Token", xmlstring, ParameterMode.IN);
			call.addParameter("empresa", xmlstring, ParameterMode.IN);
			call.addParameter("empregado", xmlstring, ParameterMode.IN);
			call.addParameter("rh_epg_id", xmlint, ParameterMode.IN);

			acPessoalClient.setReturnType(call, grupoAC.getAcUrlWsdl());

			Object[] param = new Object[] { token.toString(), empresa.getCodigoAC(), colaborador.getCodigoAC(), colaborador.getId() };

			TFeedbackPessoalWebService result = (TFeedbackPessoalWebService) call.invoke(param);
			return result.getSucesso("removerEmpregado", param, this.getClass());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public TRemuneracaoVariavel[] getRemuneracoesVariaveis(Empresa empresa, String[] colaboradoresIds, String anoMesInicial, String anoMesFinal) throws Exception {
		
		TRemuneracaoVariavel[] result = null;
		try {
			StringBuilder token = new StringBuilder();
			Call call = acPessoalClient.createCall(empresa, token, null, "GetRemuneracoesVariaveis");

			QName qname = new QName("urn:UnTypesPessoalWebService", "TRemuneracaoVariavel");
			QName qnameArr = new QName("urn:UnTypesPessoalWebService", "TRemuneracoesVariaveis");

			call.registerTypeMapping(TRemuneracaoVariavel.class, qname , new org.apache.axis.encoding.ser.BeanSerializerFactory(TRemuneracaoVariavel.class, qname ), new org.apache.axis.encoding.ser.BeanDeserializerFactory(TRemuneracaoVariavel.class, qname));
			call.registerTypeMapping(TRemuneracaoVariavel[].class, qnameArr ,
					new org.apache.axis.encoding.ser.ArraySerializerFactory(),
					new org.apache.axis.encoding.ser.ArrayDeserializerFactory());
			
			QName xmlstring = new QName("xs:string");
			QName xmlstringArray = new QName("xs:string[]");

			call.addParameter("Token", xmlstring, ParameterMode.IN);
			call.addParameter("Empresa", xmlstring, ParameterMode.IN);
			call.addParameter("Empregados", xmlstringArray, ParameterMode.IN);
			call.addParameter("AnoMesInicial", xmlstring, ParameterMode.IN);
			call.addParameter("AnoMesFinal", xmlstring, ParameterMode.IN);

			call.setReturnType(qnameArr);

			Object[] param = new Object[] { token.toString(), empresa.getCodigoAC(), colaboradoresIds, anoMesInicial, anoMesFinal };

			result = (TRemuneracaoVariavel[]) call.invoke(param);

		} catch (Exception e) {
			e.printStackTrace();
			throw new IntegraACException(e, "Não foi possível obter remunerações variáveis.");
		}
		
		return result;
	}

	public TFeedbackPessoalWebService solicitacaoDesligamentoAc(Collection<HistoricoColaborador> historicosAc, Empresa empresa) throws IntegraACException 
	{
		try {
				StringBuilder token = new StringBuilder();
				GrupoAC grupoAC = new GrupoAC();
				Call call = acPessoalClient.createCall(empresa, token, grupoAC, "SetRescisaoRhEmpregados");

	            QName qname = new QName(grupoAC.getAcUrlWsdl(),"TItemTabelaEmpregados");
	            call.registerTypeMapping(TItemTabelaEmpregados.class, qname, new BeanSerializerFactory(TItemTabelaEmpregados.class, qname), new BeanDeserializerFactory(TItemTabelaEmpregados.class, qname));

		        call.addParameter("Token",org.apache.axis.encoding.XMLType.XSD_STRING,ParameterMode.IN);
		        call.addParameter("Tabela",org.apache.axis.encoding.XMLType.SOAP_ARRAY,ParameterMode.IN);

		        acPessoalClient.setReturnType(call, grupoAC.getAcUrlWsdl());

		        TItemTabelaEmpregados[] arrayReajuste = new TItemTabelaEmpregados[historicosAc.size()];

		        int cont = 0;
		        for (HistoricoColaborador historico : historicosAc)
				{
		        	TItemTabelaEmpregados item = new TItemTabelaEmpregados();
		        	item.setObs(StringUtil.subStr(historico.getObsACPessoal(), 255));
		        	item.setDataRescisao(DateUtil.formataDiaMesAno(historico.getDataSolicitacaoDesligamento()));
		        	AcPessoalClientUtil.montaParametrosTItemTabelaEmpregado(empresa, historico, item);
		        	arrayReajuste[cont++] = item;
				}

		       	Object[] param = new Object[]{token.toString(), arrayReajuste};

		       	return (TFeedbackPessoalWebService) call.invoke(param);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String getReciboPagamento(Colaborador colaborador, Date mesAno) throws Exception
	{
		if(colaborador.getCodigoAC() == null || "".equals(colaborador.getCodigoAC()))
			throw new IntegraACException("Este colaborador não está integrado com o Fortes Pessoal ou não possui código Fortes Pessoal.");
		
		StringBuilder token = new StringBuilder();
		GrupoAC grupoAC = new GrupoAC();
		Call call = acPessoalClient.createCall(colaborador.getEmpresa(), token, grupoAC, "GetReciboDePagamento");

		QName xmlstring = new QName("xs:string");
		QName xmlint = new QName("xs:int");

		call.addParameter("Token", xmlstring, ParameterMode.IN);
		call.addParameter("Empresa", xmlstring, ParameterMode.IN);
		call.addParameter("Empregado", xmlstring, ParameterMode.IN);
		call.addParameter("Ano", xmlint, ParameterMode.IN);
		call.addParameter("Mes", xmlint, ParameterMode.IN);
		
		acPessoalClient.setReturnType(call, grupoAC.getAcUrlWsdl());
		
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(mesAno);		
		
		Object[] param = new Object[]{ token.toString(), colaborador.getEmpresa().getCodigoAC(), colaborador.getCodigoAC(), calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1 };
    	
		TFeedbackPessoalWebService result = (TFeedbackPessoalWebService) call.invoke(param);
       	Boolean retorno = result.getSucesso("GetReciboDePagamento", param, this.getClass());
		
       	if (!retorno)
        	throw new IntegraACException(result.getMensagem());
       	
       	return result.getRetorno();
	}
	
	public String getReciboDeDecimoTerceiro(Colaborador colaborador, String dataCalculo) throws Exception
	{
		StringBuilder token = new StringBuilder();
		GrupoAC grupoAC = new GrupoAC();
		Call call = acPessoalClient.createCall(colaborador.getEmpresa(), token, grupoAC, "GetReciboDeDecimoTerceiro");

		QName xmlstring = new QName("xs:string");

		call.addParameter("Token", xmlstring, ParameterMode.IN);
		call.addParameter("Empresa", xmlstring, ParameterMode.IN);
		call.addParameter("Empregado", xmlstring, ParameterMode.IN);
		call.addParameter("DataCalculo", xmlstring, ParameterMode.IN);
		
		acPessoalClient.setReturnType(call, grupoAC.getAcUrlWsdl());
		
		Object[] param = new Object[]{ token.toString(), colaborador.getEmpresa().getCodigoAC(), colaborador.getCodigoAC(), dataCalculo };
    	
		TFeedbackPessoalWebService result = (TFeedbackPessoalWebService) call.invoke(param);
       	Boolean retorno = result.getSucesso("getReciboDeDecimoTerceiro", param, this.getClass());
		
       	if (!retorno)
        	throw new IntegraACException(result.getMensagem());
       	
       	return result.getRetorno();
	}
	
	public String getDeclaracaoRendimentos(Colaborador colaborador, String ano) throws Exception
	{
		StringBuilder token = new StringBuilder();
		GrupoAC grupoAC = new GrupoAC();
		Call call = acPessoalClient.createCall(colaborador.getEmpresa(), token, grupoAC, "GetDeclaracaoRendimentos");

		QName xmlstring = new QName("xs:string");

		call.addParameter("Token", xmlstring, ParameterMode.IN);
		call.addParameter("Empresa", xmlstring, ParameterMode.IN);
		call.addParameter("Empregado", xmlstring, ParameterMode.IN);
		call.addParameter("Ano", xmlstring, ParameterMode.IN);
		
		acPessoalClient.setReturnType(call, grupoAC.getAcUrlWsdl());
		
		Object[] param = new Object[]{ token.toString(), colaborador.getEmpresa().getCodigoAC(), colaborador.getCodigoAC(), ano };
    	
		TFeedbackPessoalWebService result = (TFeedbackPessoalWebService) call.invoke(param);
       	Boolean retorno = result.getSucesso("GetDeclaracaoRendimentos", param, this.getClass());
		
       	if (!retorno)
        	throw new IntegraACException(result.getMensagem());
       	
       	return result.getRetorno();
	}
	
	public String getAvisoReciboDeFerias(Colaborador colaborador, String dataInicioGozo, String dataFimGozo) throws Exception
	{
		StringBuilder token = new StringBuilder();
		GrupoAC grupoAC = new GrupoAC();
		Call call = acPessoalClient.createCall(colaborador.getEmpresa(), token, grupoAC, "GetAvisoReciboDeFerias");

		QName xmlstring = new QName("xs:string");

		call.addParameter("Token", xmlstring, ParameterMode.IN);
		call.addParameter("Empresa", xmlstring, ParameterMode.IN);
		call.addParameter("Empregado", xmlstring, ParameterMode.IN);
		call.addParameter("DataInicioGozo", xmlstring, ParameterMode.IN);
		call.addParameter("DataFimGozo", xmlstring, ParameterMode.IN);
		
		acPessoalClient.setReturnType(call, grupoAC.getAcUrlWsdl());
		
		Object[] param = new Object[]{ token.toString(), colaborador.getEmpresa().getCodigoAC(), colaborador.getCodigoAC(), dataInicioGozo, dataFimGozo };
    	
		TFeedbackPessoalWebService result = (TFeedbackPessoalWebService) call.invoke(param);
       	Boolean retorno = result.getSucesso("GetAvisoReciboDeFerias", param, this.getClass());
		
       	if (!retorno)
        	throw new IntegraACException(result.getMensagem());
       	
       	return result.getRetorno();
	}
	
	public String[] getDatasPeriodoDeGozoPorEmpregado(Colaborador colaborador) throws Exception
	{
		return montaInvokDecimoTerceiroEDatasPeriodoDeGozoPorEmpregado(colaborador, "TPeriodosDeGozo", "GetDatasPeriodoDeGozoPorEmpregado");
	}
	
	public String[] getDatasDecimoTerceiroPorEmpregado(Colaborador colaborador) throws Exception
	{
		return montaInvokDecimoTerceiroEDatasPeriodoDeGozoPorEmpregado(colaborador, "TDatasDecimo", "GetDatasDecimoTerceiroPorEmpregado");
	}

	private String[] montaInvokDecimoTerceiroEDatasPeriodoDeGozoPorEmpregado(Colaborador colaborador, String nomeEntidadeWs, String nomeMetodoWS) {
		String[] result = null;
		try
		{
			StringBuilder token = new StringBuilder();
			GrupoAC grupoAC = new GrupoAC();
			Call call = acPessoalClient.createCall(colaborador.getEmpresa(), token, grupoAC, nomeMetodoWS);

            QName qnameAr = new QName("urn:UnTypesPessoalWebService", nomeEntidadeWs);
            call.registerTypeMapping(String[].class, qnameAr, new ArraySerializerFactory(qnameAr), new ArrayDeserializerFactory(qnameAr));

			QName xmlstring = new QName("xs:string");

			call.addParameter("Token",xmlstring,ParameterMode.IN);
			call.addParameter("Empresa",xmlstring,ParameterMode.IN);
			call.addParameter("Empregado",xmlstring,ParameterMode.IN);

			call.setReturnType(qnameAr);

			Object[] params = new Object[]{ token.toString(), colaborador.getEmpresa().getCodigoAC(), colaborador.getCodigoAC() };

			result = (String[]) call.invoke(params);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return result;
	}
	
	public void confirmarReenvio(TFeedbackPessoalWebService tFeedbackPessoalWebService, Empresa empresa) throws Exception 
	{
		try {
			StringBuilder token = new StringBuilder();
			GrupoAC grupoAC = new GrupoAC();
			Call call = acPessoalClient.createCall(empresa, token, grupoAC, "ConfirmarSucessoResincronizacao");

			QName qname = new QName(grupoAC.getAcUrlWsdl(), "TFeedbackPessoalWebService");
			call.registerTypeMapping(TFeedbackPessoalWebService.class, qname, new BeanSerializerFactory(TFeedbackPessoalWebService.class, qname), new BeanDeserializerFactory(TFeedbackPessoalWebService.class, qname));

			QName xmltype = new QName("xs:TFeedbackPessoalWebService");
			QName xmlstring = new QName("xs:string");

			call.addParameter("token", xmlstring, ParameterMode.IN);
			call.addParameter("FeedBack", xmltype, ParameterMode.IN);

			Object[] param = new Object[] { token.toString(), tFeedbackPessoalWebService };

			call.invoke(param);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IntegraACException(e, "Não foi possível confirmar reenvio dos dados da tabela temporária.");
		}
	}
	
	public String getReciboDePagamentoComplementar(Colaborador colaborador, Date mesAno) throws Exception
	{
		return getClientePadraoByNomeWS(colaborador, mesAno, "GetReciboDePagamentoComplementar");
	}
	
	public String getReciboPagamentoAdiantamentoDeFolha(Colaborador colaborador, Date mesAno) throws Exception
	{
		return getClientePadraoByNomeWS(colaborador, mesAno, "GetReciboDePagamentoAdiantamento");
	}

	private String getClientePadraoByNomeWS(Colaborador colaborador, Date mesAno, String nomeMetodoWS) throws Exception, RemoteException, IntegraACException {
		StringBuilder token = new StringBuilder();
		GrupoAC grupoAC = new GrupoAC();
		Call call = acPessoalClient.createCall(colaborador.getEmpresa(), token, grupoAC, nomeMetodoWS);

		QName xmlstring = new QName("xs:string");
		QName xmlint = new QName("xs:int");

		call.addParameter("Token", xmlstring, ParameterMode.IN);
		call.addParameter("Empresa", xmlstring, ParameterMode.IN);
		call.addParameter("Empregado", xmlstring, ParameterMode.IN);
		call.addParameter("Ano", xmlint, ParameterMode.IN);
		call.addParameter("Mes", xmlint, ParameterMode.IN);
		
		acPessoalClient.setReturnType(call, grupoAC.getAcUrlWsdl());
		
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(mesAno);		
		
		Object[] param = new Object[]{ token.toString(), colaborador.getEmpresa().getCodigoAC(), colaborador.getCodigoAC(), calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1};
		
		TFeedbackPessoalWebService result = (TFeedbackPessoalWebService) call.invoke(param);
       	Boolean retorno = result.getSucesso(nomeMetodoWS, param, this.getClass());
		
       	if (!retorno)
        	throw new IntegraACException(result.getMensagem());
       	
       	return result.getRetorno();
	}
	
	
	public TPeriodoGozo[] getFerias(Empresa empresa, String[] colaboradoresCodigosACs, String dataInicioGozo, String dataFimGozo) throws Exception
	{
		try {
			StringBuilder token = new StringBuilder();
			GrupoAC grupoAC = new GrupoAC();
			Call call = acPessoalClient.createCall(empresa, token, grupoAC, "GetPrevisaoFerias");

			QName qnamePeriodosGozo = new QName("urn:UnTypesPessoalWebService", "TPeriodoGozo");
			QName qnameArrPeriodosGozos = new QName("urn:UnTypesPessoalWebService", "TPeriodosGozos");

			call.registerTypeMapping(TPeriodoGozo.class, qnamePeriodosGozo, new org.apache.axis.encoding.ser.BeanSerializerFactory(TPeriodoGozo.class, qnamePeriodosGozo), new org.apache.axis.encoding.ser.BeanDeserializerFactory(TPeriodoGozo.class, qnamePeriodosGozo));
			call.registerTypeMapping(TPeriodoGozo[].class, qnameArrPeriodosGozos, new org.apache.axis.encoding.ser.ArraySerializerFactory(), new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

			QName xmlstring = new QName("xs:string");
			QName xmlstringArray = new QName("xs:string[]");

			call.addParameter("Token", xmlstring, ParameterMode.IN);
			call.addParameter("Empresa", xmlstring, ParameterMode.IN);
			call.addParameter("DataInicial", xmlstring, ParameterMode.IN);
			call.addParameter("DataFinal", xmlstring, ParameterMode.IN);
			call.addParameter("Empregados", xmlstringArray, ParameterMode.IN);

			call.setReturnType(qnameArrPeriodosGozos);

			Object[] param = new Object[] { token.toString(), empresa.getCodigoAC(), dataInicioGozo, dataFimGozo, colaboradoresCodigosACs };

			TPeriodoGozo[] result = (TPeriodoGozo[]) call.invoke(param);
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IntegraACException(e, "Não foi possível obter os dados solicitadoss.");
		}
	}
	
	public TNaturalidadeAndNacionalidade[] getNaturalidadesAndNacionalidades(Empresa empresa, String[] colaboradoresIds) throws IntegraACException {
		try {
			StringBuilder token = new StringBuilder();
			GrupoAC grupoAC = new GrupoAC();
			Call call = acPessoalClient.createCall(empresa, token, grupoAC, "GetNaturalidadesAndNacionalidades");

			QName qname = new QName("urn:UnTypesPessoalWebService", "TNaturalidadeAndNacionalidade");
			QName qnameArr = new QName("urn:UnTypesPessoalWebService", "TNaturalidadeAndNacionalidades");

			call.registerTypeMapping(TNaturalidadeAndNacionalidade.class, qname , new org.apache.axis.encoding.ser.BeanSerializerFactory(TNaturalidadeAndNacionalidade.class, qname ), new org.apache.axis.encoding.ser.BeanDeserializerFactory(TNaturalidadeAndNacionalidade.class, qname));
			call.registerTypeMapping(TNaturalidadeAndNacionalidade[].class, qnameArr,new org.apache.axis.encoding.ser.ArraySerializerFactory(),new org.apache.axis.encoding.ser.ArrayDeserializerFactory());
			
			QName xmlstring = new QName("xs:string");
			QName xmlstringArray = new QName("xs:string[]");

			call.addParameter("Token", xmlstring, ParameterMode.IN);
			call.addParameter("Empresa", xmlstring, ParameterMode.IN);
			call.addParameter("Empregados", xmlstringArray, ParameterMode.IN);

			call.setReturnType(qnameArr);

			Object[] param = new Object[] { token.toString(), empresa.getCodigoAC(), colaboradoresIds};

			TNaturalidadeAndNacionalidade[] result = (TNaturalidadeAndNacionalidade[]) call.invoke(param);

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			if(colaboradoresIds.length == 1)
				throw new IntegraACException(e, "Não foi possível obter a naturalidade e a nacionalidade do colaborador.");
			else
				throw new IntegraACException(e, "Não foi possível obter as naturalidades e as nacionalidades dos colaboradores.");
		}
	}
	
	public boolean isExisteHistoricoCadastralDoColaboradorComPendenciaNoESocial(Empresa empresa, String codigoAcColaborador) throws Exception{
		try {
			StringBuilder token = new StringBuilder();
			GrupoAC grupoAC = new GrupoAC();
			Call call = acPessoalClient.createCall(empresa, token, grupoAC,"ExisteHistoricoCadastralPendente");

			QName xmlstring = new QName("xs:string");

			call.addParameter("Token", xmlstring, ParameterMode.IN);
			call.addParameter("Emp_Codigo", xmlstring, ParameterMode.IN);
			call.addParameter("Epg_Codigo", xmlstring, ParameterMode.IN);

			acPessoalClient.setReturnType(call, grupoAC.getAcUrlWsdl());

			Object[] param = new Object[] { token.toString(), empresa.getCodigoAC(), codigoAcColaborador };

			TFeedbackPessoalWebService result = (TFeedbackPessoalWebService) call .invoke(param);
			return result.getSucesso("ExisteHistoricoCadastralPendente", param, this.getClass());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public boolean isHistoricoCadastralDoColaboradorEInicioVinculo(Empresa empresa, String colaboradorCodigoAC) throws Exception{
		try {
			StringBuilder token = new StringBuilder();
			GrupoAC grupoAC = new GrupoAC();
			Call call = acPessoalClient.createCall(empresa, token, grupoAC, "SituacaoCadastralEhInicioVinculo");
			QName xmlstring = new QName("xs:string");

			call.addParameter("Token", xmlstring, ParameterMode.IN);
			call.addParameter("Emp_Codigo", xmlstring, ParameterMode.IN);
			call.addParameter("Epg_Codigo", xmlstring, ParameterMode.IN);

			acPessoalClient.setReturnType(call, grupoAC.getAcUrlWsdl());
			
			Object[] param = new Object[] { token.toString(), empresa.getCodigoAC(), colaboradorCodigoAC };

			TFeedbackPessoalWebService result = (TFeedbackPessoalWebService) call .invoke(param);
			return result.getSucesso("SituacaoCadastralEhInicioVinculo", param, this.getClass());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public Integer statusAdmissaoNoFortesPessoal(Empresa empresa, Long colaboradorId) throws Exception{
		try {
			StringBuilder token = new StringBuilder();
			GrupoAC grupoAC = new GrupoAC();
			Call call = acPessoalClient.createCall(empresa, token, grupoAC, "SituacaoAdmissao");
			
			QName xmlstring = new QName("xs:string");
			QName xmlint = new QName("xs:int");

			call.addParameter("Token", xmlstring, ParameterMode.IN);
			call.addParameter("Emp_Codigo", xmlstring, ParameterMode.IN);
			call.addParameter("Id", xmlint, ParameterMode.IN);

			call.setReturnType(xmlint);
			
			Object[] param = new Object[] { token.toString(), empresa.getCodigoAC(), new Integer(colaboradorId.toString()) };
			int result = (int) call.invoke(param);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public String getUltimaCategoriaESocial(Empresa empresa, String colaboradorCodigoAC) throws Exception{
		try {
			StringBuilder token = new StringBuilder();
			GrupoAC grupoAC = new GrupoAC();
			Call call = acPessoalClient.createCall(empresa, token, grupoAC, "UltimaCategoriaESocialColaborador");
			
			QName xmlstring = new QName("xs:string");

			call.addParameter("Token", xmlstring, ParameterMode.IN);
			call.addParameter("Emp_Codigo", xmlstring, ParameterMode.IN);
			call.addParameter("Epg_Codigo", xmlstring, ParameterMode.IN);

			call.setReturnType(xmlstring);
			
			Object[] param = new Object[] { token.toString(), empresa.getCodigoAC(), colaboradorCodigoAC};
			String result = (String) call.invoke(param);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public TEmpregado[] getDddCelularAndUFHabilitacao(Empresa empresa) throws Exception {
		try {
			StringBuilder token = new StringBuilder();
			GrupoAC grupoAC = new GrupoAC();
			Call call = acPessoalClient.createCall(empresa, token, grupoAC, "AtualizaDDDAlternativoEHabilitacaoUFEmpregado");
			
			QName xmlstring = new QName("xs:string");

			call.addParameter("Token", xmlstring, ParameterMode.IN);
			call.addParameter("Emp_Codigo", xmlstring, ParameterMode.IN);

			call.setReturnType(xmlstring);
			
			Object[] param = new Object[] { token.toString(), empresa.getCodigoAC()};
			TEmpregado[] result = (TEmpregado[]) call.invoke(param);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}