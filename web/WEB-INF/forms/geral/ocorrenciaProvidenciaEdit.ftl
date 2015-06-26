<html>
<head>
	<@ww.head/>

	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>
	
	<#include "../ftl/mascarasImports.ftl" />

	<#if colaboradorOcorrencia.providencia?exists>
		<title>Editar Providência</title>
	<#else>
		<title>Inserir Providência</title>
	</#if>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@ww.form name="form" action="update.action" method="POST">

	<div style="font-weight:bold;">Colaborador: ${colaboradorOcorrencia.colaborador.nome}</div>
	<br>
		<div style="">Ocorrencia: ${colaboradorOcorrencia.ocorrencia.descricao}</div>
		<div style="">Data de Início: ${colaboradorOcorrencia.dataIni}</div>
		
		<div style="">Data de Término:
			<#if colaboradorOcorrencia.dataFim?exists>
				${colaboradorOcorrencia.dataFim}
			</#if>
		</div>
		
		<div style="">Observação:</div>
		<#if colaboradorOcorrencia.observacao?exists>
			<div style="pading: 5px">${colaboradorOcorrencia.observacao}</div>
		</#if>
		
		</br>
		<@ww.select label="Providência" name="colaboradorOcorrencia.providencia.id" id="providencia" list="providencias" cssStyle="width:530px;" listKey="id" listValue="descricao" headerValue="Selecione..." headerKey=""/>
		</br>
		
		<@ww.hidden name="colaboradorOcorrencia.colaborador.id"/>
		<@ww.hidden name="colaboradorOcorrencia.ocorrencia.id"/>
		<@ww.hidden name="colaboradorOcorrencia.dataIni"/>
		<@ww.hidden name="colaboradorOcorrencia.dataFim"/>
		<@ww.hidden name="colaboradorOcorrencia.observacao"/>
		<@ww.hidden name="colaboradorOcorrencia.id" />
		<@ww.token/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="return validaFormulario('form', new Array(), new Array());"  class="btnGravar"> </button>
		<button onclick="window.location='list.action'" class="btnCancelar"></button>
	</div>

</body>
</html>