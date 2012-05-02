package com.fortes.rh.web.ws;

import java.util.Collection;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;

import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.cargosalario.HistoricoColaborador;
import com.fortes.rh.model.dicionario.TipoAplicacaoIndice;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.ws.TEmpregado;
import com.fortes.rh.model.ws.TFeedbackPessoalWebService;
import com.fortes.rh.model.ws.TItemTabelaEmpregados;
import com.fortes.rh.model.ws.TRemuneracaoVariavel;
import com.fortes.rh.model.ws.TSituacao;
import com.fortes.rh.util.DateUtil;

public class AcPessoalClientColaboradorImpl implements AcPessoalClientColaborador {
	private AcPessoalClient acPessoalClient;

	public void setAcPessoalClient(AcPessoalClient acPessoalClient) {
		this.acPessoalClient = acPessoalClient;
	}

	//atualiza apenas o epg
	public void atualizar(TEmpregado empregado, Empresa empresa) throws Exception {
		try {
			StringBuilder token = new StringBuilder();
			GrupoAC grupoAC = new GrupoAC();
			Call call = acPessoalClient.createCall(empresa, token, grupoAC, "atualizarEmpregado");

			QName qname = new QName(grupoAC.getAcUrlWsdl(), "TEmpregado");
			call.registerTypeMapping(TEmpregado.class, qname, new BeanSerializerFactory(TEmpregado.class, qname), new BeanDeserializerFactory(TEmpregado.class, qname));

			QName xmltype = new QName("xs:TEmpregado");
			QName xmlstring = new QName("xs:string");

			call.addParameter("Token", xmlstring, ParameterMode.IN);
			call.addParameter("Empregado", xmltype, ParameterMode.IN);

			acPessoalClient.setReturnType(call, grupoAC.getAcUrlWsdl());

			Object[] param = new Object[] { token.toString(), empregado };

			TFeedbackPessoalWebService result =  (TFeedbackPessoalWebService) call.invoke(param);
			boolean retorno = result.getSucesso("atualizarEmpregado", param, this.getClass()); 
            if(!retorno)
				throw new Exception();

		} catch (Exception e) {
			e.printStackTrace();
			throw new IntegraACException(e, "Não foi possível atualizar este colaborador no AC Pessoal.");
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

	// TODO Utilizado apenas nos testes
	public TEmpregado getEmpregadoACAConfirmar(Integer colaboradorId, Empresa empresa) {
		TEmpregado empregado = null;
		try {
			StringBuilder token = new StringBuilder();
			Call call = acPessoalClient.createCall(empresa, token, null, "GetEmpregadoAConfirmar");

			QName qname = new QName("urn:AcPessoal", "TEmpregado");
			call.registerTypeMapping(TEmpregado.class, qname, new BeanSerializerFactory(TEmpregado.class, qname), new BeanDeserializerFactory(TEmpregado.class, qname));

			QName xmlstring = new QName("xs:string");
			QName xmlint = new QName("xs:int");

			call.addParameter("token", xmlstring, ParameterMode.IN);
			call.addParameter("empresa", xmlstring, ParameterMode.IN);
			call.addParameter("rh_id", xmlint, ParameterMode.IN);

			call.setReturnType(qname);

			Object[] param = new Object[] { token.toString(), empresa.getCodigoAC(), colaboradorId };
			empregado = (TEmpregado) call.invoke(param);
			call.clearHeaders();
			call.clearOperation();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return empregado;
	}

	public TRemuneracaoVariavel[] getRemuneracoesVariaveis(Empresa empresa, String[] colaboradoresIds, String anoMesInicial, String anoMesFinal) throws Exception {
		
		TRemuneracaoVariavel[] result = null;
		try {
			StringBuilder token = new StringBuilder();
			Call call = acPessoalClient.createCall(empresa, token, null, "GetRemuneracoesVariaveis");

//			urn:UnTypesPessoalWebService
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

	public boolean solicitacaoDesligamentoAc(Collection<HistoricoColaborador> historicosAc, Empresa empresa) 
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
		        	item.setCargo(historico.getFaixaSalarial().getCodigoAC());
		        	item.setCodigo(historico.getColaborador().getCodigoAC());
		        	item.setData(DateUtil.formataDiaMesAno(historico.getData()));
		        	item.setEmpresa(empresa.getCodigoAC());
		        	item.setLotacao(historico.getAreaOrganizacional().getCodigoAC());
		        	item.setRh_sep_id(Integer.valueOf(historico.getId().toString()));
		        	item.setEstabelecimento(historico.getEstabelecimento().getCodigoAC());
		        	item.setSaltipo(String.valueOf(TipoAplicacaoIndice.getCodigoAC(historico.getTipoSalario())));
		        	item.setDataRescisao(DateUtil.formataDiaMesAno(historico.getDataSolicitacaoDesligamento()));
		        	item.setObs(historico.getObsACPessoal());
		        	
		        	item.setExpAgenteNocivo(historico.getGfip());

		    		switch (historico.getTipoSalario())
		    		{
		    			case TipoAplicacaoIndice.CARGO:
		    			{
		    				item.setIndcodigosalario("");
		    				item.setIndqtde(0.0);
		    				item.setValor(0.0);
		    				break;
		    			}
		    			case TipoAplicacaoIndice.INDICE:
		    				item.setIndcodigosalario(historico.getIndice().getCodigoAC());
		    				item.setIndqtde(historico.getQuantidadeIndice());
		    				item.setValor(0.0);
		    				break;
		    			case TipoAplicacaoIndice.VALOR:
		    				item.setIndcodigosalario("");
		    				item.setIndqtde(0.0);
		    				item.setValor(historico.getSalarioCalculado());
		    				break;
		    		}

		        	arrayReajuste[cont++] = item;
				}

		       	Object[] param = new Object[]{token.toString(), arrayReajuste};

		       	TFeedbackPessoalWebService result =  (TFeedbackPessoalWebService) call.invoke(param);
		       	Boolean retorno = result.getSucesso("SetRescisaoRhEmpregados", param, this.getClass()); 
	       		if(!retorno)
		        	throw new IntegraACException("Situação do colaborador inexistente no Ac Pessoal.");
		       	return retorno;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}