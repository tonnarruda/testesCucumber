<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />

<html>
<head>
<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		@import url('<@ww.url value="/css/buttons.css"/>');
	</style>

	<#include "../ftl/showFilterImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<title>Ambientes</title>
	
	<script type="text/javascript">
		function pesquisar()
		{
			$('#pagina').val(1);
			$('#formBusca').attr('action','list.action');
			$('#formBusca').submit();
		}
		
		function imprimir()
		{ 
			$('#formBusca').attr('action','imprimirLista.action');
			$('#formBusca').submit();
		}
		
		function toggleEstabecimento(){
			if($('#localAmbiente').val() == 2){
				$('#estabelecimento').val('');
				$('#estabelecimento').parent().parent().hide();
			}
			else
				$('#estabelecimento').parent().parent().show();
		}
		
		$(function() {
			insereHelp(2, 'O estabelecimento vinculado ao ambiente pode ser um estabelecimento do próprio empregador ou estabelecimento de terceiros.');		
		});
		
		function insereHelp(posicao, help)
		{
			var id = "tooltipHelp" + posicao;
			$("#ambiente th:eq(" + posicao + ")" ).append('<img id="' + id + '" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: 1px" />');
			
			$('#' + id).qtip({
				content: help
			});
		}
		
	</script>
</head>

<body>
<@ww.actionerror />
<@ww.actionmessage />

	<#include "../util/topFiltro.ftl" />
		<@ww.form name="formBusca" method="POST" id="formBusca">
			<@ww.textfield label="Nome do Ambiente" name="historicoAmbiente.nomeAmbiente" cssStyle="width: 350px;"/>
			<@ww.select label="Local do Ambiente" name="historicoAmbiente.localAmbiente" id="localAmbiente" list="locaisAmbiente" headerKey="" headerValue="Todos" cssStyle="width: 447px;" onchange="toggleEstabecimento();"/>
			<@ww.select label="Estabelecimento" name="historicoAmbiente.estabelecimento.id" id="estabelecimento" list="estabelecimentos" listKey="id" listValue="nome" headerKey="" headerValue="Todos" cssStyle="width: 447px;"/>
			<@ww.hidden id="pagina" name="page"/>
			<@ww.hidden id="showFilter" name="showFilter"/>
			<button type="submit" value="" onclick="pesquisar();">Pesquisar</button>
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>

	<@display.table name="ambientes" id="ambiente" class="dados">
		<@display.column title="Ações" class="acao">
			<@frt.link href="../historicoAmbiente/list.action?ambiente.id=${ambiente.id}" imgTitle="Históricos" iconeClass="fa-list"/>
			<@frt.link href="javascript:;" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?ambiente.id=${ambiente.id}&page=${page}'});" imgTitle="Excluir" iconeClass="fa-times"/>	
		</@display.column>
		<@display.column property="nome" title="Ambiente"/>
		<@display.column property="historicoAtual.estabelecimento.nome" title="Estabelecimento do Ambiente"/>
	</@display.table>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="list.action?" page='${page}'/>

	<div class="buttonGroup">
		<button onclick="window.location='prepareInsert.action'">Inserir</button>
		<button onclick="imprimir();">Imprimir</button>
	</div>
</body>
</html>