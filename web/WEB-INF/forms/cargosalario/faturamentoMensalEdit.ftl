<html>
	<head>
		<@ww.head/>
		<#if faturamentoMensal.id?exists>
			<title>Editar Faturamento</title>
			<#assign formAction="update.action"/>
			<#assign somenteLeitura="true" />
		<#else>
			<title>Inserir Faturamento</title>
			<#assign formAction="insert.action"/>
			<#assign somenteLeitura="false" />
		</#if>
	<#include "../ftl/mascarasImports.ftl" />
	
	<#assign validarCampos="return validaFormulario('form', new Array('dataMesAno','valor'), new Array('dataMesAno'))"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.actionmessage />
		
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
			
			<@ww.textfield label="Mês/Ano" name="dataMesAno" id="dataMesAno" required="true" cssClass="mascaraMesAnoData" disabled="${somenteLeitura}" />
			<@ww.textfield label="Valor" id="valor"  required="true" cssClass="currency" cssStyle="text-align:right; width:85px;" name="faturamentoMensal.valor" maxLength="12" />
			<@ww.select label="Estabelecimentos" name="faturamentoMensal.estabelecimento.id" list="estabelecimentos" listKey="id" listValue="nome" headerKey="-1" headerValue="Sem estabelecimento definido."/>
			
			<@ww.hidden name="faturamentoMensal.id" />
			<@ww.hidden name="faturamentoMensal.mesAno" />
			<@ww.hidden name="faturamentoMensal.empresa.id" />
			<@ww.token/>
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnCancelar"></button>
		</div>
	</body>
</html>
