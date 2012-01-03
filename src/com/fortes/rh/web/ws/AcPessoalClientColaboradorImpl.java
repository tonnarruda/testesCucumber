package com.fortes.rh.web.ws;

import static org.apache.axis.Constants.XSD_BOOLEAN;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;

import com.fortes.rh.exception.IntegraACException;
import com.fortes.rh.model.geral.Colaborador;
import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.model.geral.GrupoAC;
import com.fortes.rh.model.ws.TEmpregado;
import com.fortes.rh.model.ws.TRemuneracaoVariavel;
import com.fortes.rh.model.ws.TSituacao;

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

			call.setReturnType(XSD_BOOLEAN);

			Object[] param = new Object[] { token.toString(), empregado };

			if (!(Boolean) call.invoke(param))
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

			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_BOOLEAN);
			Object[] param = new Object[] { token.toString(), empregado, situacao };

			return (Boolean) call.invoke(param);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean verificaHistoricoNaFolhaAC(Long historicoColaboradorId, String colaboradorCodigoAC, Empresa empresa) {
		try {
			StringBuilder token = new StringBuilder();
			Call call = acPessoalClient.createCall(empresa, token, null, "GetSepEfo");

			QName xmlstring = new QName("xs:string");
			QName xmlint = new QName("xs:int");

			call.addParameter("Token", xmlstring, ParameterMode.IN);
			call.addParameter("Empresa", xmlstring, ParameterMode.IN);
			call.addParameter("Empregado", xmlstring, ParameterMode.IN);
			call.addParameter("RH_SEP_ID", xmlint, ParameterMode.IN);

			call.setReturnType(XSD_BOOLEAN);

			Object[] param = new Object[] { token.toString(), empresa.getCodigoAC(), colaboradorCodigoAC, historicoColaboradorId };

			return (Boolean) call.invoke(param);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean remove(Colaborador colaborador, Empresa empresa) {
		try {
			StringBuilder token = new StringBuilder();
			Call call = acPessoalClient.createCall(empresa, token, null, "removerEmpregado");

			QName xmlstring = new QName("xs:string");
			QName xmlint = new QName("xs:int");

			call.addParameter("Token", xmlstring, ParameterMode.IN);
			call.addParameter("empresa", xmlstring, ParameterMode.IN);
			call.addParameter("empregado", xmlstring, ParameterMode.IN);
			call.addParameter("rh_epg_id", xmlint, ParameterMode.IN);

			call.setReturnType(XSD_BOOLEAN);

			Object[] param = new Object[] { token.toString(), empresa.getCodigoAC(), colaborador.getCodigoAC(), colaborador.getId() };

			return (Boolean) call.invoke(param);
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
			QName qname = new QName("urn:AcPessoal", "TRemuneracaoVariavel");
			QName qnameArr = new QName("urn:AcPessoal", "TRemuneracoesVariaveis");

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
}