<!--
  Autor: Bruno Bachiega
  Data: 6/06/2006
  Requisito: RFA0026
 -->
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
</style>
<title>Pessoais</title>
</head>
<body>
	<@display.table name="pessoals" id="pessoal" pagesize=10 class="dados" defaultsort=2 >
		<@display.column title="Ações" media="html" class="acao">
			<a href="prepareUpdate.action?pessoal.id=${pessoal.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url includeParams="none" value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?pessoal.id=${pessoal.id}'});"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="cpf" title="Cpf"/>
		<@display.column property="sexo" title="Sexo"/>
		<@display.column property="dataNascimento" title="DataNascimento" format="{0,date,dd/MM/yyyy}"/>
		<@display.column property="escolaridade" title="Escolaridade"/>
		<@display.column property="estadoCivil" title="EstadoCivil"/>
		<@display.column property="conjugue" title="Conjugue"/>
		<@display.column property="conjugueTrabalha" title="ConjugueTrabalha"/>
	</@display.table>

	<div class="buttonGroup">
		<button onclick="window.location='prepareInsert.action'" class="btnInserir" accesskey="V">
		</button>
	</div>
</body>
</html>