<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	@import url('<@ww.url value="/css/font-awesome.min.css?version=${versao}"/>');
	@import url('<@ww.url value="/css/font-awesome_buttons.css"/>');
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
			
			<button type="submit" value="" onclick="pesquisar();">Pesquisar</button>
		
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>
	
	<@display.table name="funcaos" id="funcao" class="dados" sort="list">
		<@display.column title="Ações" class="acao">
			<@frt.link href="prepareUpdate.action?funcao.id=${funcao.id}" imgTitle="Históricos" iconeClass="fa-list"/>
			<@frt.link href="javascript:;" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?funcao.id=${funcao.id}&page=${page}'});" imgTitle="Excluir" iconeClass="fa-times"/>
		</@display.column>
		<@display.column property="nome" title="Nome"/>
	</@display.table>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action?" page='${page}'/>

	<div class="buttonGroup">
		<button onclick="window.location='prepareInsert.action'" accesskey="I">Inserir</button>
	</div>
</body>
</html>