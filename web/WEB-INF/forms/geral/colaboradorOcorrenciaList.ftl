<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css"/>');
</style>
<title>Ocorrências</title>
</head>
<body>
<#if colaboradors?exists>
	<#assign headerValue="Selecione..."/>
<#else>
	<#assign headerValue="Utilize o Filtro acima."/>
</#if>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<@ww.form name="form" action="list.action" method="POST">
		<li>
			<@ww.div cssClass="divInfo" cssStyle="width: 500px;">
				<ul>
					<@ww.textfield label="Nome" name="colaborador.nome" id="nome" cssClass="inputNome" maxLength="100" cssStyle="width: 400px;"/>
					<@ww.textfield label="CPF" id="cpf" name="colaborador.pessoal.cpf" liClass="liLeft" maxLength="11" onkeypress="return(somenteNumeros(event,''));" cssStyle="width:150px;"/>
					<@ww.textfield label="Matrícula" id="matricula" name="colaborador.matricula" cssStyle="width:150px;"  maxLength="20"/>

					<@ww.hidden name="prontuario.id" id="prontuario"/>
					<input type="submit" value="" class="btnPesquisar grayBGDivInfo" />

					<br><br>
					<@ww.select label="Colaborador" name="colaborador.id" id="colab" list="colaboradors" listKey="id" listValue="nomeCpf" headerKey="" headerValue="${headerValue}" onchange="document.form.submit();" />
				</ul>
			</@ww.div>
		</li>
	</@ww.form>

	<#if colaborador?exists && colaborador.id?exists>	
	<div style = "font-weight:bold;">Colaborador: ${colaborador.nome}</div><br>
	<@display.table name="colaboradorOcorrencias" id="colaboradorOcorrencia" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?colaboradorOcorrencia.id=${colaboradorOcorrencia.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?colaboradorOcorrencia.id=${colaboradorOcorrencia.id}&colaborador.id=${colaborador.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="ocorrencia.descricao" 	title="Descrição"/>
		<@display.column property="dataIni" 				title="Início" 	format="{0,date,dd/MM/yyyy}"/>
		<@display.column property="dataFim"					title="Término" format="{0,date,dd/MM/yyyy}"/>
		<@display.column property="observacao" 				title="Observação"/>
	</@display.table>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action?colaborador.id=${colaborador.id}&" page='${page}' idFormulario=""/>
			 
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action?colaborador.id=${colaborador.id}'" accesskey="I">
		</button>
	</div>
	</#if>
	 
</body>
</html>