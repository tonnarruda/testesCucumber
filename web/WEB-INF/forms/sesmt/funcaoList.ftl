<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
</style>
	<#include "../ftl/showFilterImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
<title>Funções</title>
	
	<script type="text/javascript">
		function pesquisar()
		{
			$('#pagina').val(1);
			$('#formBusca').attr('action','list.action');
			$('#formBusca').submit();
		}
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	
	<#include "../util/topFiltro.ftl" />
		<@ww.form name="formBusca" method="POST" id="formBusca">
			<@ww.textfield label="Nome da Função" name="funcao.nome" cssStyle="width: 350px;"/>
			
			<@ww.hidden id="pagina" name="page"/>
			<@ww.hidden id="showFilter" name="showFilter"/>
			<input type="submit" value="" class="btnPesquisar grayBGE" onclick="pesquisar();">
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>
	
	<@display.table name="funcaos" id="funcao" class="dados" sort="list">
		<@display.column title="Ações" class="acao">
			<a href="prepareUpdate.action?funcao.id=${funcao.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?funcao.id=${funcao.id}&page=${page}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		<@display.column property="nome" title="Nome"/>
	</@display.table>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action?" page='${page}'/>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="I">
		</button>
		<button onclick="window.location='../../cargosalario/cargo/list.action'" class="btnVoltar" accesskey="V">
		</button>
	</div>
</body>
</html>