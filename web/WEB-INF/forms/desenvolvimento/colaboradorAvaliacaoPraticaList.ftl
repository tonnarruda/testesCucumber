<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<title>Notas da Avaliação Prática</title>
	
	<script type="text/javascript">
		$(function(){
			$('.mascaraData').css("border","1px solid #BEBEBE");

		});
		
		function dataHoje(){
			return $.datepicker.formatDate('dd/mm/yy',new Date());
		}
	</script>
	
</head>
<body>
	<#assign validarCampos="return validaFormulario('form', null, null)"/>
	<#include "../ftl/mascarasImports.ftl" />

	<@ww.actionmessage />
	<@ww.actionerror />

	<@ww.form name="form" action="buscaColaboradores.action" onsubmit="${validarCampos}" validate="true" method="POST">
		<li>
			<@ww.div cssClass="divInfo" cssStyle="width: 950px;">
				<ul>
					<@ww.select label="Certificações" name="certificacao.id" id="colab" list="certificacoes" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." onchange="document.form.submit();" />
					<@ww.select label="Colaborador" name="colaborador.id" id="colab" list="colaboradores" listKey="id" listValue="nomeCpf" headerKey="" headerValue="Selecione..." onchange="document.form.submit();" />
					<br><br>
				</ul>
			</@ww.div>
		</li>
	</@ww.form>
	
	<#if colaborador?exists && colaborador.id?exists>

		<br/>
		<#assign i = 0/>		
		<@display.table name="colaboradorAvaliacaoPraticas" id="colaboradorAvaliacaoPratica" class="dados">
			<@display.caption><div style="background-color: #EFEFEF;color:#5C5C5A;">Avaliações Práticas</div> </@display.caption>
			
			
			<#if colaboradorAvaliacaoPratica.nota?exists>
				<#assign aNota = "${colaboradorAvaliacaoPratica.nota}"/>
			<#else>
				<#assign aNota = ""/>
			</#if>
			
			<#if colaboradorAvaliacaoPratica.data?exists>
				<#assign aData = "${colaboradorAvaliacaoPratica.data?date}"/>
			<#else>
				<#assign aData = "dataHoje();"/>
			</#if>
			
			<@display.column property="avaliacaoPratica.titulo" title="Título" style="width: 500px;"/>
			<@display.column property="avaliacaoPratica.notaMinima" title="Nota Mínima Aprovação" style="width: 100px;text-align: center;" />
			<@display.column title="Realizada em" style="width: 160px;text-align: center;height: 30px !important">
				<@ww.datepicker name="colaboradorAvaliacaoPraticas[${i}].data" cssClass="mascaraData" value="${aData}" theme="simple"/><br>
			</@display.column>
			<@display.column title="Nota" style="width: 80px;text-align: center;height: 30px !important">
				<@ww.textfield id="nota" name="colaboradorAvaliacaoPraticas[${i}].nota" value="${aNota}" maxLength="5" cssStyle="text-align:right;width:50px;border:1px solid #BEBEBE;" onkeypress = "return(somenteNumeros(event,'.,,'));" onchange="verificaValor(this.value);"  theme="simple"/>
			</@display.column>
			<#assign i = i + 1/>
		</@display.table>
		
	
		<div class="buttonGroup">
			<button class="btnGravar" onclick="window.location='insertOrUpdate.action?certificacao.id=${certificacao.id}&colaborador.id=${colaborador.id}'"></button>
		</div>
		<br/>
		
		<@display.table name="colaboradorTurmas" id="colaboradorTurma" class="dados">
			<@display.caption><div style="background-color: #EFEFEF;color:#5C5C5A;">Cursos Realizados Para a Certificação</div> </@display.caption>
			<@display.column property="curso.nome" title="Curso" style="width: 300px;"/>
			<@display.column property="turma.descricao" title="Turma" />
			<@display.column title="Período" style="text-align:center; width:180px">
				${colaboradorTurma.turma.dataPrevIni?string("dd'/'MM'/'yyyy")} - ${colaboradorTurma.turma.dataPrevFim?string("dd'/'MM'/'yyyy")}
			</@display.column>
			<@display.column property="turma.realizadaFormatada" title="Realizada" style="width: 50px;" />
			<@display.column property="aprovadoMaisNota" title="Aprovado" style="width: 50px;" />
		</@display.table>
	</#if>	
</body>
</html>
