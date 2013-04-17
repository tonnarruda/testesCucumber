<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		@import url('<@ww.url value="/css/jquery-ui/jquery-ui-1.8.9.custom.css"/>');
	
		#formDialog { display: none; width: 600px; }
	</style>

	<#if modeloAvaliacao?exists && modeloAvaliacao = tipoModeloAvaliacao.getSolicitacao()>
		<title>Modelos de Avaliação de Solicitação</title>
	<#else>
		<title>Modelos de Avaliação de Desempenho/Acompanhamento do Per. de Experiência</title>
	</#if>
	
	<#include "../ftl/showFilterImports.ftl" />
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-ui-1.8.6.custom.min.js"/>'></script>
	
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	
	<script type='text/javascript'>
		function clonar(avaliacaoId, modeloAvaliacao, titulo)
		{
			$('#avaliacaoId').val(avaliacaoId);
			$('#modeloAvaliacao').val(modeloAvaliacao);
			$('#formDialog').dialog({ modal: true, width: 530, title: 'Clonar: ' + titulo });
		}
	</script>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<#include "../util/topFiltro.ftl" />
		<@ww.form name="formBusca" action="list.action" validate="true" method="POST" id="formBusca">
			<@ww.textfield label="Avaliação" name="titulo" id="titulo" cssStyle="width: 550px;"/>
			<@ww.select label="Ativos" name="ativos" id="ativos" list=r"#{'S':'Sim', 'N':'Não'}" cssStyle="width: 65px;" headerKey="T" headerValue="Todos"/>
			<@ww.hidden id="pagina" name="page"/>
			<@ww.hidden id="modeloAvaliacao" name="modeloAvaliacao"/>
			<input type="submit" value="" class="btnPesquisar grayBGE" onclick="$('#pagina').val(1);">
		</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>
	
	<div align="right">&nbsp;<span class="encerradaBkg">&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;Inativa(s)</div>
	<br />
	
	<@display.table name="avaliacaos" id="avaliacao" class="dados">

		<#assign classe=""/>
		<#if !avaliacao.ativo>
			<#assign classe="encerrada"/>
		</#if>

		<@display.column title="Ações" style = "width:160px;">
			<a href="visualizar.action?avaliacao.id=${avaliacao.id}&modeloAvaliacao=${modeloAvaliacao}"><img border="0" title="Visualizar" src="<@ww.url includeParams="none" value="/imgs/olho.jpg"/>"></a>
			<a href="../perguntaAvaliacao/list.action?avaliacao.id=${avaliacao.id}&modeloAvaliacao=${modeloAvaliacao}"><img border="0" title="Perguntas" src="<@ww.url includeParams="none" value="/imgs/question.gif"/>"></a>
			<a href="../../pesquisa/aspecto/listAvaliacao.action?avaliacao.id=${avaliacao.id}&modeloAvaliacao=${modeloAvaliacao}"><img border="0" title="Aspectos" src="<@ww.url includeParams="none" value="/imgs/agrupar.gif"/>"></a>
			<a href="prepareUpdate.action?avaliacao.id=${avaliacao.id}&modeloAvaliacao=${modeloAvaliacao}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="javascript:;" onclick="javascript:clonar(${avaliacao.id},'${modeloAvaliacao}','${avaliacao.titulo}');"><img border="0" title="Clonar" src="<@ww.url includeParams="none" value="/imgs/clonar.gif"/>"></a>
			<a href="imprimir.action?avaliacao.id=${avaliacao.id}&modeloAvaliacao=${modeloAvaliacao}"><img border="0" title="Imprimir Modelo da Avaliação" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"></a>
			<a href="imprimir.action?avaliacao.id=${avaliacao.id}&modeloAvaliacao=${modeloAvaliacao}&imprimirFormaEconomica=true"><img border="0" title="Imprimir Modelo da Avaliação em formato econômico" src="<@ww.url includeParams="none" value="/imgs/iconPrint.gif"/>"></a>
			<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?avaliacao.id=${avaliacao.id}&modeloAvaliacao=${modeloAvaliacao}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
		</@display.column>
		
		<@display.column title="Avaliação" property="titulo" class="${classe}"/>
		<@display.column title="Tipo de Avaliação" property="tipoModeloAvaliacaoDescricao" class="${classe}" style="width:250px"/>
	</@display.table>
	
	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="formBusca"/>
	
	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action?modeloAvaliacao=${modeloAvaliacao}'"></button>
	</div>
	
	<div id="formDialog">
		<@ww.form name="formModal" id="formModal" action="clonar.action" method="POST">
			<@frt.checkListBox label="Selecione as empresas para as quais deseja clonar este modelo de avaliação" name="empresasCheck" list="empresasCheckList" form="document.getElementById('formModal')"/>
			* Caso nenhuma empresa seja selecionada, o modelo de avaliação será clonado apenas para a empresa <@authz.authentication operation="empresaNome"/><br>
			<@ww.hidden name="avaliacao.id" id="avaliacaoId"/>
			<@ww.hidden name="modeloAvaliacao" id="modeloAvaliacao"/>
			<button class="btnClonar" type="submit"></button>
		</@ww.form>
	</div>
</body>
</html>
