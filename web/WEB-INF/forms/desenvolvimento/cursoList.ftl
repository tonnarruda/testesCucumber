<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />

<html>
<head>
	<@ww.head/>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		#formDialog { display: none; width: 600px; }
	</style>

	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	<title>Cursos</title>

	<#include "../ftl/showFilterImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

	<script type='text/javascript'>
		function clonar(cursoId, titulo){
			$('#cursoId').val(cursoId);
			$('#formDialog').dialog({ modal: true, width: 530, title: 'Clonar: ' + titulo });
		}
	</script>

</head>
<body>
	<#assign linkFiltro=""/>
	<#if nomeCursoBusca?exists>
		<#assign linkFiltro="${linkFiltro}&nomeCursoBusca=${nomeCursoBusca}"/>
	</#if>

	<@ww.actionerror />
	<@ww.actionmessage />

	<#include "../util/topFiltro.ftl" />
	<@ww.form name="form" id="form" action="list.action" method="POST">
		<@ww.textfield label="Nome" name="nomeCursoBusca" id="nome" cssClass="inputNome" maxLength="100" cssStyle="width: 340px;"/>
		<@ww.hidden id="pagina" name="page"/>
		<input type="submit" value="" class="btnPesquisar grayBGE" onclick="document.getElementById('pagina').value = 1;">
	</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>

	<@display.table name="cursos" id="curso" class="dados">
		<@display.column title="Ações" class="acao" style = "width:95px";>
			<a href="prepareUpdate.action?curso.id=${curso.id}&curso.empresa.id=${curso.empresa.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="../turma/list.action?curso.id=${curso.id}"><img border="0" title="Turmas" src="<@ww.url value="/imgs/db_add.gif"/>"></a>
			<a href="../../geral/documentoAnexo/listCurso.action?documentoAnexo.origem=U&documentoAnexo.origemId=${curso.id}"><img border="0" title="Inserir Anexos" src="<@ww.url includeParams="none" value="/imgs/anexos.gif"/>"></a>
			<a href="javascript:;" onclick="javascript:clonar(${curso.id}, '${curso.nome?html?replace("'","\\'")}')"><img border="0" title="Clonar" src="<@ww.url includeParams="none" value="/imgs/clonar.gif"/>"></a>
			<#if curso.empresa.id == empresaSistema.id>
				<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?curso.id=${curso.id}&curso.empresa.id=${curso.empresa.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
			<#else>
				<img border="0" title="Curso compartilhado" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>" style="opacity:0.2;filter:alpha(opacity=20);">
			</#if>
		</@display.column>
		<@display.column property="nome" title="Nome"/>
		<@display.column property="cargaHorariaMinutos" title="Carga Horária" style="text-align:right;"/>
	</@display.table>

	<@frt.fortesPaging url="${urlImgs}" totalSize="${totalSize}" pagingSize="${pagingSize}" link="" page='${page}' idFormulario="form"/>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="window.location='prepareInsert.action'" accesskey="N">
		</button>
	</div>
	
	<div id="formDialog">
		<@ww.form name="formModal" id="formModal" action="clonar.action" method="POST">
			<@frt.checkListBox label="Selecione as empresas para as quais deseja clonar este curso" name="empresasCheck" list="empresasCheckList" form="document.getElementById('formModal')" filtro="true"/>
			* Caso nenhuma empresa seja selecionada, o curso será clonado apenas para a empresa <@authz.authentication operation="empresaNome"/><br>
			<@ww.hidden name="curso.id" id="cursoId"/>
			<button class="btnClonar" type="submit"></button>
		</@ww.form>
	</div>
</body>
</html>