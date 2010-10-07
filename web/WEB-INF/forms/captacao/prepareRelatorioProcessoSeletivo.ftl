<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<title>Relatório de Processos Seletivos</title>
	<#assign validarCampos="return validaFormulario('form', new Array('ano', 'cargo', '@etapaCheck'), null)"/>
	
</head>

<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	<@ww.form name="form" action="imprimirRelatorioProcessoSeletivo.action" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.textfield label="Ano" name="ano" id="ano" required="true" size="4" maxLength="4" onkeypress="return(somenteNumeros(event,''));" after="(ex: 2007)"/>
	    <@ww.select label="Cargo" id="cargo" name="cargoId" required="true" list="cargos" cssStyle="width: 220px;" listKey="id" listValue="nome" headerKey="-1" headerValue="[Selecione...]"/>
		<@frt.checkListBox name="etapaCheck" id="etapaCheck" label="Etapas" list="etapaSeletivaCheckList" />
	</@ww.form>
	
	
	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnRelatorio" ></button>
	</div>
</body>
</html>