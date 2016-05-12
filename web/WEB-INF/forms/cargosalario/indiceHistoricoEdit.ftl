<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<#if indiceHistorico.id?exists>
		<title>Editar Histórico do Índice</title>
		<#assign formAction="../indiceHistorico/update.action"/>
	<#else>
		<title>Novo Histórico do Índice</title>
		<#assign formAction="../indiceHistorico/insert.action"/>
	</#if>

	<#assign validarCampos="return validaFormulario('form', new Array('dataHist','valor'), new Array('dataHist'))"/>
	
	<#include "indiceHistoricoCadastroHeadInclude.ftl" />
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<#if !integradoAC>
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST">
			<b>Índice:</b> ${indiceAux.nome}<br><br>
			
			<#include "indiceHistoricoCadastroInclude.ftl" />
	
			<@ww.hidden name="indiceHistorico.indice.id"/>
			<@ww.hidden name="indiceHistorico.id"/>
			<@ww.hidden name="indiceAux.id"/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar">
			</button>
	
			<button onclick="javascript: executeLink('../indice/prepareUpdate.action?indiceAux.id=${indiceAux.id}');" class="btnCancelar">
			</button>
		</div>
	</#if>
</body>
</html>