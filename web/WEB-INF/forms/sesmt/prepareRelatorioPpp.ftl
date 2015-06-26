<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<#include "../ftl/mascarasImports.ftl" />
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
</style>

<title>PPP do colaborador - ${colaborador.nome}</title>
<#assign validarCampos="return validaFormulario('form', new Array('data','nit','responsavel'), new Array('data'))"/>

<#if data?exists>
	<#assign dataDoDia = data?date/>
<#else>
	<#assign dataDoDia = ""/>
</#if>

</head>
<body>
<@ww.actionmessage />
<@ww.actionerror />
	<@ww.form name="form" action="gerarRelatorio.action" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.datepicker label="Data" name="data" id="data" required="true" cssClass="mascaraData" value="${dataDoDia}"/>
		
		CNAE <br />
		<select name="cnae" style="width: 150px; margin-bottom:8px;">
	  		<#if empresa.cnae?exists && empresa.cnae != "">
	  			<option value="${empresa.cnae}">${empresa.cnae}</option>
	  		</#if>
	  		<#if empresa.cnae2?exists && empresa.cnae2 != "">
		  		<option value="${empresa.cnae2}">${empresa.cnae2}</option>
	  		<#elseif !empresa.cnae?exists || empresa.cnae == "">
		  		<option value="">Não existe CNAE</option>
	  		</#if>
		</select>
		
		<@ww.textfield label="NIT do Representante Legal" name="nit" id="nit" required="true" cssStyle="width: 250px;"/>
		<@ww.textfield label="Representante Legal" name="responsavel" id="responsavel" cssStyle="width: 450px;" required="true"/>
		
		<li>
			<fieldset class="fieldsetPadrao" style="width:640px;">
				<ul>
				<legend>Atendimento aos requisitos das NR-06 e NR-09 do MTE pelos EPI informados</legend>
			
				<@ww.select label="Foi tentada a implementação de medidas de proteção coletiva, de caráter administrativo ou de organização do trabalho, optando-se pelo EPI por inviabilidade técnica, insuficiência ou interinidade, ou ainda em caráter complementar ou emergencial"
				 name="respostas" list=r"#{'S':'Sim','N':'Não'}"/>
				<@ww.select label="Foram observadas as condições de funcionamento e do uso ininterrupto do EPI ao longo do tempo, conforme especificação técnica do fabricante, ajustada às condições de campo"
				 name="respostas" list=r"#{'S':'Sim','N':'Não'}"/>
				<@ww.select label="Foi observado o prazo de validade, conforme Certificado de Aprovação-CA do MTE"
				 name="respostas" list=r"#{'S':'Sim','N':'Não'}"/>
				<@ww.select label="Foi observada a periodicidade de troca definida pelos programas ambientais, comprovada mediante recibo assinado pelo usuário em época própria"
				 name="respostas" list=r"#{'S':'Sim','N':'Não'}"/>
				<@ww.select label="Foi observada a higienização"
				 name="respostas" list=r"#{'S':'Sim','N':'Não'}"/>
				</ul>
			</fieldset>
		</li>
		<ul>
		<@ww.textarea label="Observações" name="observacoes" id="observacoes" cssStyle="width: 450px;clear:both;"/>
		</ul>
		<@ww.checkbox label="Imprimir espaço para assinatura do colaborador" name="imprimirRecibo" labelPosition="left"/>
		
		<@ww.hidden id="colaboradorId" name="colaborador.id"/>
	</@ww.form>
	
	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnRelatorio" accesskey="G"></button>
		<button onclick="window.location='list.action';" class="btnVoltar" accesskey="V"></button>
	</div>

</body>
</html>