<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />

<html>
<head>
<@ww.head/>
	<title>Avaliações de Desempenho</title>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
	</style>
</head>
<body>
	<#assign validarCampos="return validaFormulario('form', null, null)"/>

	<@ww.actionmessage />
	<@ww.actionerror />
	<@ww.form name="form" action="periodoExperienciaQuestionarioList.action" onsubmit="${validarCampos}" validate="true" method="POST">
		<li>
			<@ww.div cssClass="divInfo" cssStyle="width: 950px;">
				<ul>
					<@ww.textfield label="Nome" name="colaborador.nome" id="nome" cssClass="inputNome" maxLength="100" cssStyle="width: 400px;"/>
					<@ww.textfield label="CPF" id="cpf" name="colaborador.pessoal.cpf" liClass="liLeft" maxLength="11" onkeypress="return(somenteNumeros(event,''));" cssStyle="width:150px;"/>
					<@ww.textfield label="Matrícula" id="matricula" name="colaborador.matricula" cssStyle="width:150px;"  maxLength="20"/>

					<button onclick="${validarCampos};" class="btnPesquisar grayBGDivInfo"></button>

					<br><br>
					<@ww.select label="Colaborador" name="colaborador.id" id="colab" list="colaboradors" listKey="id" listValue="nomeCpf" headerKey="" headerValue="Selecione..." onchange="document.form.submit();" />
				</ul>
			</@ww.div>
		</li>
	</@ww.form>

	<#if colaborador?exists && colaborador.id?exists>
		<br/>
		<b>Colaborador: ${colaborador.nome}</b> <br/>
		<b>Cargo: ${colaborador.faixaSalarial.descricao}</b> <br/>
		<b>Área Organizacional: ${colaborador.areaOrganizacional.descricao}</b> <br/><br/>

		<@display.table name="colaboradorQuestionarios" id="colaboradorQuestionario" class="dados">
			<@display.column title="Ações" class="acao">
				<a href="prepareUpdateAvaliacaoExperiencia.action?colaboradorQuestionario.id=${colaboradorQuestionario.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
				<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='deleteAvaliacaoExperiencia.action?colaboradorQuestionario.id=${colaboradorQuestionario.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
			</@display.column>
			<@display.column property="data" title="Data" style="width: 140px;"/>
			<@display.column property="avaliacao.titulo" title="Avaliação" />
			<@display.column property="performanceFormatada" title="Performance" style="width: 100px;" />
			<@display.column title="Obs." style="text-align: center;width: 50px">
				<#if colaboradorQuestionario.observacao?exists && colaboradorQuestionario.observacao?trim != "">
					<span href=# style="cursor: help;" onmouseout="hideTooltip()" onmouseover="showTooltip(event,'${colaboradorQuestionario.observacao?j_string}');return false">...</span>
				</#if>
			</@display.column>
		</@display.table>
	
		<div class="buttonGroup">
			<button class="btnInserir" onclick="window.location='prepareInsertAvaliacaoExperiencia.action?colaboradorQuestionario.colaborador.id=${colaborador.id}'"></button>
		</div>
	</#if>	
</body>
</html>