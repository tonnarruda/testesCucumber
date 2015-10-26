<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
	</style>

	<title>ColaboradorAvaliacaoPratica</title>
</head>
<body>
	<#assign validarCampos="return validaFormulario('form', null, null)"/>

	<@ww.actionmessage />
	<@ww.actionerror />

	<@ww.form name="form" action="buscaColaboradores.action" onsubmit="${validarCampos}" validate="true" method="POST">
		<li>
			<@ww.div cssClass="divInfo" cssStyle="width: 950px;">
				<ul>
					<@ww.textfield label="Nome" name="colaborador.nome" id="nome" value="" cssClass="inputNome" maxLength="100" cssStyle="width: 400px;"/>
					<@ww.textfield label="CPF" id="cpf" name="colaborador.pessoal.cpf" liClass="liLeft" maxLength="11" onkeypress="return(somenteNumeros(event,''));" cssStyle="width:150px;"/>
					<@ww.textfield label="Matrícula" id="matricula" name="colaborador.matricula" cssStyle="width:150px;"  maxLength="20"/>
					<@ww.select label="Certificações" name="certificacao.id" id="colab" list="certificacoes" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." />

					<button onclick="${validarCampos};" class="btnPesquisar grayBGDivInfo"></button>
					<@ww.select label="Colaborador" name="colaborador.id" id="colab" list="colaboradors" listKey="id" listValue="nomeCpf" headerKey="" headerValue="Selecione..." onchange="document.form.submit();" />
					<br><br>
				</ul>
			</@ww.div>
		</li>
	</@ww.form>
	
		<@display.table name="colaboradorAvaliacaoPraticas" id="colaboradorAvaliacaoPratica" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?colaboradorAvaliacaoPratica.id=${colaboradorAvaliacaoPratica.id}"><img border="0" title="Editar" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="javascript:;" onclick="javascript:newConfirm('Confirma exclusão?', function(){window.location='delete.action?colaboradorAvaliacaoPratica.id=${colaboradorAvaliacaoPratica.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="" title="Nome"/>
	</@display.table>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'"></button>
	</div>
</body>
</html>
