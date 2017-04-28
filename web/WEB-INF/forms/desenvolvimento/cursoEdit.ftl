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
	$(function() {
		<#if curso?exists && curso.periodicidade?exists >
			if( ${curso.periodicidade} != null && ${curso.periodicidade} == 0){
				$("#periodicidade").val("");
			}
		</#if>
					
		$('#percentualMinimoFrequenciaTooltipHelp').qtip({content: 'Não é possível realizar a edição deste campo, existem turmas realizadas e colaboradores com presenças registradas.'});
	});
</script>

<style>
	<#if avaliacaoAlunoRespondida || existeTurmaRealizada>
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
		<@ww.textfield label="Nome" name="curso.nome" id="nome" cssClass="inputNome" required="true" cssStyle="width:502px;" maxLength="150"/>
	
		<#if codigoTRUCurso>
			<@ww.textfield label="Código da Ocorrência no TRU" name="curso.codigoTru" id="codigoTRU" onkeypress="return(somenteNumeros(event,''));" size="3"  maxLength="3"/>
		<#else>
			<@ww.hidden name="curso.codigoTru" />
		</#if>
		
		<#if cursoLntId?exists>
			<@ww.hidden name="cursoLntId" value="${cursoLntId}" />
		</#if>
	
		<@ww.textfield label="Carga Horária" name="curso.cargaHorariaMinutos" cssStyle="width:55px;text-align:right" maxLength="7" cssClass="hora"/>
		
		<#if existeFrequencia>
			<@ww.textfield readonly="true" label="Percentual mínimo de frequência para aprovação (%)" id ="percentualMinimoFrequencia" name="curso.percentualMinimoFrequencia" maxLength="6" cssStyle="width:50px;text-align: right; background: #EBEBEB;" onblur="validaPercentual()"/>
			<img id="percentualMinimoFrequenciaTooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin:-20px 0 35px 60px;"/>
			<#if curso?exists && curso.percentualMinimoFrequencia?exists >
				<@ww.hidden name="curso.percentualMinimoFrequencia"/>
			</#if>
		<#else>
			<@ww.textfield label="Percentual mínimo de frequência para aprovação (%)" id ="percentualMinimoFrequencia" name="curso.percentualMinimoFrequencia" maxLength="6" cssStyle="width:50px;text-align: right;" onblur="validaPercentual()"/>
		</#if>
		
		<#if empresaSistema.controlarVencimentoPorCurso>
			<@ww.textfield label="Periodicidade em meses" name="curso.periodicidade" id="periodicidade" cssStyle="width:30px; text-align:right;" maxLength="4" onkeypress = "return(somenteNumeros(event,''));"/>
		</#if>

		<div style="display:<#if compartilharCursos>block<#else>none</#if>">
			<@frt.checkListBox label="Compartilhar com as empresas" name="empresasCheck" list="empresasCheckList"/>
		</div>
		
		<@ww.textarea label="Conteúdo Programático" name="curso.conteudoProgramatico" cssStyle="width:500px;"/>
		<@ww.textarea label="Critérios de Avaliação" name="curso.criterioAvaliacao" cssStyle="width:500px;"/>
		
		<#if avaliacaoAlunoRespondida || existeTurmaRealizada>
			<@frt.checkListBox label="Avaliações dos Alunos" name="avaliacaoCursoCheck" id="avaliacaoCursoCheck" list="avaliacaoCursoCheckList" readonly=true />
			<div style="width: 500px;">
				<strong>Não é possível modificar as avaliações dos alunos, pois já existe pelo menos uma turma realizada ou existem respostas para pelo menos uma das avaliações acima.</strong>
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
		<#if lntId?exists>
			<button onclick="window.location='../lnt/gerarCursosETurmas.action?lnt.id=${lntId}'" class="btnCancelar" accesskey="V"></button>
		<#else>
			<button onclick="window.location='list.action?page=${page}${linkFiltro}'" class="btnCancelar" accesskey="V"></button>
		</#if>
	</div>
</body>
</html>