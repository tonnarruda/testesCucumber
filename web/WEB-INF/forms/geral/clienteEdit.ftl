<html>
	<head>
		<@ww.head/>
		<#if cliente.id?exists>
			<title>Editar Cliente</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir Cliente</title>
			<#assign formAction="insert.action"/>
		</#if>
	
	<#if cliente?exists && cliente.dataAtualizacao?exists>
		<#assign dataFormatada = cliente.dataAtualizacao?date/>
	<#else>
	<#assign dataFormatada = ""/>
	</#if>
	
	<#assign validarCampos="return validaFormulario('form', new Array('nome'), new Array('dataAtualizacao') )"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
		
		<@ww.textfield label="Nome" id="nome" name="cliente.nome" cssClass="inputNome" maxLength="100" required="true"/>
		<@ww.textfield label="Endereço Interno" id="enderecoInterno" name="cliente.enderecoInterno" maxLength="50" liClass="liLeft" cssStyle="width:280px;"/>
		<@ww.textfield label="Endereço Externo" id="enderecoExterno" name="cliente.enderecoExterno" maxLength="50" cssStyle="width:280px;"/>
		<#if usuarioFortes>
			<@ww.textfield label="Senha Fortes" id="senhaFortes" name="cliente.senhaFortes" maxLength="50" cssStyle="width:80px;"/>
		</#if>
		<@ww.textfield label="Versão" id="versao" name="cliente.versao" maxLength="10" cssStyle="width:80px;"/>
		<@ww.datepicker label="Data da Atualização" id="dataAtualizacao" name="cliente.dataAtualizacao" cssClass="mascaraData" value="${dataFormatada}"/>
		<@ww.textarea label="Módulos Adquiridos" id="modulosAdquiridos" name="cliente.modulosAdquiridos" cssStyle="height:100px;"/>
		<@ww.textarea label="Contato Geral" id="contatoGeral" name="cliente.contatoGeral" cssStyle="height:100px;"/>
		<@ww.textarea label="Contato TI" id="contatoTI" name="cliente.contatoTI" cssStyle="height:100px;"/>
		<@ww.textarea label="Observações" id="observacao" name="cliente.observacao" cssStyle="height:100px;"/>
		
		<@ww.hidden name="cliente.id" />
			<@ww.token/>	
		</@ww.form>
		
		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar"></button>
		</div>
	</body>
</html>
