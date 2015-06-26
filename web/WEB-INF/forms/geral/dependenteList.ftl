<!--
  Autor: Bruno Bachiega
  Data: 16/06/2006
  Requisito: RFA0026
 -->
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Dependentes</title>
</head>
<body>

	<@display.table name="dependentes" id="dependente" pagesize=10 class="dados" defaultsort=2 >
		<@display.column title="Ações" media="html" class="acao">
			<a href="prepareUpdate.action?dependente.id=${dependente.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?dependente.id=${dependente.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="nome" title="Nome"/>
		<@display.column property="dataNascimento" title="DataNascimento" format="{0,date,dd/MM/yyyy}"/>
		<@display.column property="colaborador.nome" title="Colaborador"/>
	</@display.table>

	
	<div class="buttonGroup">
		<button onclick="window.location='prepareInsert.action?colaborador.id=${colaborador.id}'" class="btnInserir" ">
		</button>
		<button onclick="window.location='<@ww.url includeParams="none" value="/geral/colaborador/prepareUpdate.action?colaborador.id="/>${colaborador.id}'"  class="btnVoltar" accesskey="V">
		</button>
	</div>
</body>
</html>