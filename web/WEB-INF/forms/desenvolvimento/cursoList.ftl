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
		var novoTitulo="";
		
		function clonar(cursoId, titulo){
			novoTitulo = titulo + " (Clone)";
			$("#nomeDoCurso").empty();
			$('#formClonar').prepend('<div id="nomeDoCurso">Curso: ' + titulo + '</br></br></div>');
			
			if(novoTitulo.length > 150){
				$("#divInserirNomeCursoClonado").show();
			}
			else{
				novoTitulo="";
				$("#divInserirNomeCursoClonado").hide();
			}
			$('#cursoId').val(cursoId);
			$('#formClonar').dialog({ modal: true, width: 530, title: 'Clonar Curso'});
		}
		
		function enviaFormClonar (){
			if(novoTitulo !=""){
				return validaFormulario('formClonar', new Array('novoTituloCursoClonado'), null);
			}
			return true;
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
		<@ww.textfield label="Nome" name="nomeCursoBusca" id="nome" cssClass="inputNome" maxLength="150" cssStyle="width: 502px;"/>
		<@ww.hidden id="pagina" name="page"/>
		<input type="submit" value="" class="btnPesquisar grayBGE" onclick="document.getElementById('pagina').value = 1;">
	</@ww.form>
	<#include "../util/bottomFiltro.ftl" />
	<br>

	<@display.table name="cursos" id="curso" class="dados">
		<@display.column title="Ações" class="acao" style = "width:95px";>
			<a href="prepareUpdate.action?curso.id=${curso.id}&curso.empresa.id=${curso.empresa.id}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="../turma/list.action?curso.id=${curso.id}"><img border="0" title="Turmas" src="<@ww.url value="/imgs/db_add.gif"/>"></a>
			<a href="../../geral/documentoAnexo/list.action?documentoAnexo.origem=U&documentoAnexo.origemId=${curso.id}"><img border="0" title="Inserir Anexos" src="<@ww.url includeParams="none" value="/imgs/anexos.gif"/>"></a>
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
		<@ww.form name="formClonar" id="formClonar" action="clonar.action" onsubmit="return enviaFormClonar();" method="POST">
			<div id="divInserirNomeCursoClonado"  style="display: none;">
				<label>O nome para o curso clonado ultrapassa o limite de 150 caracteres. Infome o nome que deseja para o novo curso.</label>
				</br></br>
				<@ww.textfield label="Nome do novo curso" name="novoTituloCursoClonado" id="novoTituloCursoClonado" cssClass="inputNome" required="true" cssStyle="width:502px;" maxLength="150"/>
				</br>
			</div>
		
			<@frt.checkListBox label="Selecione as empresas para as quais deseja clonar este curso" name="empresasCheck" list="empresasCheckList" form="document.getElementById('formModal')" filtro="true"/>
			* Caso nenhuma empresa seja selecionada, o curso será clonado apenas para a empresa <@authz.authentication operation="empresaNome"/><br>
			<@ww.hidden name="curso.id" id="cursoId"/>
			<button onclick="enviaFormClonar();" class="btnClonar">
		</@ww.form>
	</div>
</body>
</html>