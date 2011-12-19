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
</head>
<body>
	<#assign linkFiltro=""/>
	<#if nomeCursoBusca?exists>
		<#assign linkFiltro="${linkFiltro}&nomeCursoBusca=${nomeCursoBusca}"/>
	</#if>

	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" id="form" onsubmit="${validarCampos}" validate="true" method="POST">
		<@ww.textfield label="Nome" name="curso.nome" id="nome" cssClass="inputNome" required="true" liClass="liLeft" cssStyle="width:409px;" maxLength="100"/><br><br><br>
		<@ww.textfield label="Carga Horária" name="curso.cargaHorariaMinutosFormatado" cssStyle="width:55px;text-align:right" cssClass="mascaraHoraCurso"/>
		<@ww.textfield label="Percentual mínimo de frequência para aprovação (%)" id ="percentualMinimoFrequencia" name="curso.percentualMinimoFrequencia" maxLength="6" cssStyle="width:50px;text-align: right;" onblur="validaPercentual()"/>
		<@ww.textarea label="Conteúdo Programático" name="curso.conteudoProgramatico" cssStyle="width:500px;"/>
		<@ww.textarea label="Critérios de Avaliação" name="curso.criterioAvaliacao" cssStyle="width:500px;"/>
		<@frt.checkListBox label="Avaliações dos Alunos" name="avaliacaoCursoCheck" id="avaliacaoCursoCheck" list="avaliacaoCursoCheckList"/>

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