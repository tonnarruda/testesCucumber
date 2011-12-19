package com.fortes.rh.test.web.dwr;

import java.io.IOException;

import org.apache.commons.httpclient.methods.GetMethod;

public class MockHttpMethod extends GetMethod {

	@Override
	public String getResponseBodyAsString() throws IOException {
		StringBuilder json = new StringBuilder();
		json.append("               <div class=\"caixacampobranco\">");
		json.append("                            <span class=\"resposta\">Logradouro: </span>");
		json.append("                            <span class=\"respostadestaque\">");
		json.append("                                Avenida Heróis do Acre 1-*|@#%&()+=§!?;:><,./");
		json.append("                            </span><br/>");
		json.append("                            <span class=\"resposta\">Bairro: </span><span class=\"respostadestaque\">Passaré*</span><br/>");
		json.append("                            <span class=\"resposta\">Localidade / UF: </span>");
		json.append("                            <span class=\"respostadestaque\">");
		json.append("                                Fortaleza");
		json.append("								/CE");
		json.append("                            </span><br/>");
		json.append("                            <span class=\"resposta\">CEP: </span><span class=\"respostadestaque\">60743760</span><br/>");
		json.append("");
		json.append("                        <div style=\"text-align: right;\" class=\"mopcoes orientacao\"><span>Opções <img style=\"position: relative right:0px;\" src=\"images/template/mais.png\"/></span></div>");
		json.append("");
		json.append("                    </div>");		
		
		return json.toString();
	}
	
}
