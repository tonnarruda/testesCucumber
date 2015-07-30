<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
<#if curso.id?exists>
	<title>Editar Curso</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir Curso</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>
<#assign validarCampos="return validaFormulario('form', new Array('nome'), null)"/>

<#include "../ftl/mascarasImports.ftl" />

<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js"/>"></script>
<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/formataValores.js?version=${versao}"/>'></script>
<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.price_format.1.6.min.js"/>"></script><!-- Usado para o function.js cssClass=hora-->

<script>
	$(document).ready(function($){
		<#if curso?exists && curso.periodicidade?exists >
			if( ${curso.periodicidade} != null && ${curso.periodicidade} == 0){
				$("#periodicidade").val("");
			} 
		</#if>			
	});

	$(function() {
		$('#avaliacaoCursoCheckToolTipHelp').qtip({
			content: 'Não será possível desmarcar nenhuma das avaliações dos alunos quando houver resposta para pelo menos uma das avaliações.'
		});
	});
	
</script>

<style>
	<#if avaliacaoAlunoRespondida>
		#listCheckBoxavaliacaoCursoCheck { background-color: #E9E9E9; }
	</#if>
</style>

</head>
<body>
	<#assign linkFiltro=""/>
	<#if nomeCursoBusca?exists>
		<#assign linkFiltro="${linkFiltro}&nomeCursoBusca=${nomeCursoBusca}"/>
	</#if>

	<@ww.actionerror />
	<@ww.actionmessage />
	
	<@ww.form name="form" action="${formAction}" id="form" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.textfield label="Nome" name="curso.nome" id="nome" cssClass="inputNome" required="true" cssStyle="width:409px;" maxLength="100"/>
	
		<#if codigoTRUCurso>
			<@ww.textfield label="Código da Ocorrência no TRU" name="curso.codigoTru" id="codigoTRU" onkeypress="return(somenteNumeros(event,''));" size="3"  maxLength="3"/>
		<#else>
			<@ww.hidden name="curso.codigoTru" />
		</#if>
	
		<@ww.textfield label="Carga Horária" name="curso.cargaHorariaMinutos" cssStyle="width:55px;text-align:right" maxLength="7" cssClass="hora"/>
		<@ww.textfield label="Percentual mínimo de frequência para aprovação (%)" id ="percentualMinimoFrequencia" name="curso.percentualMinimoFrequencia" maxLength="6" cssStyle="width:50px;text-align: right;" onblur="validaPercentual()"/>
		<@ww.textfield label="Periodicidade em meses" name="curso.periodicidade" id="periodicidade" cssStyle="width:30px; text-align:right;" maxLength="4" onkeypress = "return(somenteNumeros(event,''));"/>

		<div style="display:<#if compartilharCursos>block<#else>none</#if>">
			<@frt.checkListBox label="Compartilhar com as empresas" name="empresasCheck" list="empresasCheckList"/>
		</div>
		
		<@ww.textarea label="Conteúdo Programático" name="curso.conteudoProgramatico" cssStyle="width:500px;"/>
		<@ww.textarea label="Critérios de Avaliação" name="curso.criterioAvaliacao" cssStyle="width:500px;"/>
		
		<#if avaliacaoAlunoRespondida>
			<@frt.checkListBox label="Avaliações dos Alunos" name="avaliacaoCursoCheck" id="avaliacaoCursoCheck" list="avaliacaoCursoCheckList" readonly=true />
			<div style="width: 500px;">
				<strong>Não é possível modificar as avaliações dos alunos, pois já existe resposta para pelo menos uma das avaliações acima.</strong>
			</div>
		<#else>
			<@frt.checkListBox label="Avaliações dos Alunos" name="avaliacaoCursoCheck" id="avaliacaoCursoCheck" list="avaliacaoCursoCheckList" filtro="true" readonly=false tooltipHelp="true"/>
		</#if>

		<@ww.hidden name="curso.id" />
		<@ww.hidden name="curso.empresa.id" />
		<@ww.hidden name="nomeCursoBusca" />
		<@ww.hidden name="page" />
	</@ww.form>


	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}">
		</button>
		<button onclick="window.location='list.action?page=${page}${linkFiltro}'" class="btnCancelar" accesskey="V">
		</button>
	</div>
</body>
</html>