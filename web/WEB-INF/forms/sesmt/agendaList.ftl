<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>
	
	<#include "../ftl/showFilterImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<#include "../ftl/mascarasImports.ftl" />
	
	<title>Agenda</title>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
		<#include "../util/topFiltro.ftl" />
		<@ww.form name="formBusca" id="formBusca" action="list.action" validate="true" method="POST">
			<@ww.textfield label="Mês/Ano inicial" name="dataMesAnoIni" cssClass="mascaraMesAnoData" liClass="liLeft"/>
			<@ww.textfield label="Mês/Ano final" name="dataMesAnoFim" cssClass="mascaraMesAnoData"/>
			<@ww.select label="Estabelecimento" name="estabelecimento.id" id="estabelecimento" list="estabelecimentos" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." cssStyle="width: 300px;"  />
			
			<input type="submit" value="" class="btnPesquisar grayBGE">
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>
	
	<@display.table name="agendas" id="agenda" class="dados">
		<@display.column title="Ações" class="acao" style="text-align:center; width:80px" >
			<a href="prepareUpdate.action?agenda.id=${agenda.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?agenda.id=${agenda.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="mesAno" title="Mês/Ano" style="text-align:center; width:90px" />
		<@display.column property="evento.nome" title="Evento" style="width:800px" />
	</@display.table>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'"></button>
	</div>
</body>
</html>
