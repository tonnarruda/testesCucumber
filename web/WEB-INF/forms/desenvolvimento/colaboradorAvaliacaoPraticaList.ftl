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
		
		function submeter(action)
		{
		
			if(action == 'buscaColaboradores.action'){
				document.form.action = action; 
				return document.form.submit();
			}else{ 			
				document.form.action = action;
				var arrayDataValida = [];
				
				$('.mascaraData').each(function(){
					if ($(this).val() == '  /  /    ')
			    		$(this).val('');
			    	else
			    		arrayDataValida.push($(this).attr('id'));
				});

				if(validaFormulario('form', new Array(), arrayDataValida, true))
					return	document.form.submit();
			}
		}
	</script>
	
</head>
<body>
	<#include "../ftl/mascarasImports.ftl" />

	<@ww.actionmessage />
	<@ww.actionerror />

	<@ww.form name="form" method="POST">
		<li>
			<@ww.div cssClass="divInfo" cssStyle="width: 950px;">
				<ul>
					<@ww.select label="Certificações" name="certificacao.id" list="certificacoes" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." onchange="submeter('buscaColaboradores.action');" />
					<@ww.select label="Colaborador" name="colaborador.id" list="colaboradores" listKey="id" listValue="nomeCpf" headerKey="" headerValue="Selecione..." onchange="submeter('buscaColaboradores.action');" />
					<br><br>
				</ul>
			</@ww.div>
		</li>
	
		<#if colaborador?exists && colaborador.id?exists>
			<br/>
			<#assign i = 0/>		
			<@display.table name="colaboradorAvaliacaoPraticas" id="colaboradorAvaliacaoPratica" class="dados">
				<@display.caption><div style="background-color: #EFEFEF;color:#5C5C5A;">Avaliações Práticas</div> </@display.caption>
				
				<#if colaboradorAvaliacaoPratica.id?exists>
					<#assign colaboradorAvaliacaoPraticaId = "${colaboradorAvaliacaoPratica.id}"/>
				<#else>
					<#assign colaboradorAvaliacaoPraticaId = ""/>
				</#if>
				
				<#if colaboradorAvaliacaoPratica.nota?exists>
					<#assign colaboradorAvaliacaoPraticaNota = "${colaboradorAvaliacaoPratica.nota}"/>
				<#else>
					<#assign colaboradorAvaliacaoPraticaNota = ""/>
				</#if>
				
				<#if colaboradorAvaliacaoPratica.data?exists>
					<#assign colaboradorAvaliacaoPraticaData = "${colaboradorAvaliacaoPratica.data?date}"/>
				<#else>
					<#assign colaboradorAvaliacaoPraticaData = ""/>
				</#if>
				
				<@display.column property="avaliacaoPratica.titulo" title="Título" style="width: 500px;"/>
				<@display.column property="avaliacaoPratica.notaMinima" title="Nota Mínima Aprovação" style="width: 100px;text-align: center;" />
				<@display.column title="Realizada em" style="width: 160px;text-align: center;height: 30px !important">
					<@ww.datepicker id="data[${i}]" name="colaboradorAvaliacaoPraticas[${i}].data" cssClass="mascaraData" value="${colaboradorAvaliacaoPraticaData}" theme="simple"/><br>
					<@ww.hidden name="colaboradorAvaliacaoPraticas[${i}].avaliacaoPratica.id" value="${colaboradorAvaliacaoPratica.avaliacaoPratica.id}"/>
					<@ww.hidden name="colaboradorAvaliacaoPraticas[${i}].id" value="${colaboradorAvaliacaoPraticaId}"/>
				</@display.column>
				<@display.column title="Nota" style="width: 80px;text-align: center;height: 30px !important">
					<@ww.textfield id="nota[${i}]" name="colaboradorAvaliacaoPraticas[${i}].nota" value="${colaboradorAvaliacaoPraticaNota}" maxLength="4" cssStyle="text-align:right;width:50px;border:1px solid #BEBEBE;" onkeypress = "return(somenteNumeros(event,'.,,'));"/>
				</@display.column>
				<#assign i = i + 1/>
			</@display.table>
		
			<div class="buttonGroup">
				<button type="button" class="btnGravar" onclick="submeter('insertOrUpdate.action');"></button>
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
	</@ww.form>
</body>
</html>
