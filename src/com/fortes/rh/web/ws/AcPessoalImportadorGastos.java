package com.fortes.rh.web.ws;

import java.util.Date;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.encoding.ser.ArrayDeserializerFactory;
import org.apache.axis.encoding.ser.ArraySerializerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fortes.rh.model.geral.Empresa;
import com.fortes.rh.util.Mail;
import com.fortes.rh.util.SpringUtil;

@Component
public class AcPessoalImportadorGastos
{
	@Autowired private AcPessoalClient acPessoalClient;

	@SuppressWarnings("deprecation")
	public String[] importarGastos(Date data, Empresa empresa)
	{
		String[] result = null;
		try
		{
			StringBuilder token = new StringBuilder();
			Call call = acPessoalClient.createCall(empresa, token, null, "GetCustosEmpregadoEventoMesAno2");

            QName qnameAr = new QName("urn:AcPessoal","TCustosEmpregadoEventoMesAno");
            call.registerTypeMapping(String[].class, qnameAr, new ArraySerializerFactory(qnameAr), new ArrayDeserializerFactory(qnameAr));

			QName xmlstring = new QName("xs:string");
			QName xmlint = new QName("xs:int");

			call.addParameter("Token",xmlstring,ParameterMode.IN);
			call.addParameter("Empresa",xmlstring,ParameterMode.IN);
			call.addParameter("Empregado",xmlstring,ParameterMode.IN);
			call.addParameter("Ano",xmlint,ParameterMode.IN);
			call.addParameter("Mes",xmlint,ParameterMode.IN);

			call.setReturnType(qnameAr);

			Object[] params = new Object[]{token.toString(), empresa.getCodigoAC(), "", data.getYear() + 1900, data.getMonth() + 1};

			result = (String[]) call.invoke(params);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			enviaEmailParaRespSetorPessoal(e.getMessage(), empresa);
		}

		return result;
	}

	private static void enviaEmailParaRespSetorPessoal(String erro, Empresa empresa)
	{
		StringBuilder corpo = new StringBuilder();
		corpo.append("Houve erro na sincronização com o sistema Fortes Pessoal.<br><br>");
		corpo.append("Mensagem de erro:<br>" + erro);

		Mail mail = (Mail) SpringUtil.getBean("mail");
		try
		{
			mail.send(empresa, "[RH] Erro na sincronização de Gastos", corpo.toString(), null, empresa.getEmailRespSetorPessoal());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}