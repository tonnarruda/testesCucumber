<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
	<title>Acompanhamento do Período de Experiência</title>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

	<#assign DataAtual = "${dataDoDia}"/>
	<#assign validarCampos="return validaFormulario('form', new Array('data','tempoDeEmpresa'), new Array('data'))"/>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<#include "../ftl/mascarasImports.ftl" />

		<@ww.form name="form" action="imprimeRelatorioPeriodoDeAcompanhamentoDeExperiencia.action" onsubmit="${validarCampos}" method="POST">
		
			<@ww.datepicker label="Data de Referência" id="data" name="dataReferencia" required="true" value="${DataAtual}" cssClass="mascaraData"/>

			<br>
			Considerar colaboradores com até  
			<@ww.textfield theme="simple" name="tempoDeEmpresa" id="tempoDeEmpresa" cssStyle="width:60px; text-align:right;" maxLength="8" onkeypress = "return(somenteNumeros(event,''));"/> 
			dias de empresa.*
			<br><br>
			
			<@frt.checkListBox label="Período de Acompanhamento" name="periodoCheck" id="periodoCheck" list="periodoCheckList"/>						
			<@frt.checkListBox label="Estabelecimento" name="estabelecimentoCheck" id="estabelecimentoCheck" list="estabelecimentoCheckList"/>						
			<@frt.checkListBox label="Áreas Organizacionais" name="areasCheck" id="areasCheck" list="areasCheckList"/>
			
		</@ww.form>

		<div class="buttonGroup">
			<button class="btnRelatorio" onclick="${validarCampos};" ></button>
		</div>
</body>
</html>