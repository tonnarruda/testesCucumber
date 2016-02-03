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
			insereHelp();
		});
		
		function submeter(action)
		{
			if(action == 'buscaColaboradores.action'){
				document.formBusca.action = action; 
				return document.formBusca.submit();
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
		
		function insereHelp(posicao, help)
		{
			<#if colaboradorCertificacao?exists &&  colaboradorCertificacao.id?exists && !colaboradorCertificacao.ultimaCertificacao>
			$("#tituloTabelaAP").append('<img id="tooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin: 1px 0px -2px 10px;" />');
			
			$('#tooltipHelp').qtip({
				content:"O sistema não possibilita a edição da data e nota da avaliação prática, quando a mesma não é referente a última certificação do colaborador."
			});
			</#if>
		}
	</script>
</head>
<body>
	<#include "../ftl/mascarasImports.ftl" />

	<@ww.actionmessage />
	<@ww.actionerror />

	<@ww.form name="formBusca" action="buscaColaboradores.action" method="POST">
		<li>
			<@ww.div cssClass="divInfo" cssStyle="width: 950px;">
				<ul>
					<@ww.select label="Certificações" name="certificacao.id" list="certificacoes" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." onchange="submeter('buscaColaboradores.action');" cssStyle="width: 800px;" />
					<@ww.select label="Colaborador" name="colaborador.id" list="colaboradores" listKey="id" listValue="nomeCpf" headerKey="" headerValue="Selecione..." onchange="submeter('buscaColaboradores.action');" cssStyle="width: 800px;"/>
					<@ww.select label="Certificações aprovadas pelo colaborador" name="colaboradorCertificacao.id" list="colaboradorCertificacaos" listKey="id" listValue="dataFormatada" headerKey="" headerValue="Selecione..." onchange="submeter('buscaColaboradores.action');" cssStyle="width: 800px;"/>
					<br><br>
				</ul>
			</@ww.div>
		</li>
	</@ww.form>
	
		<#if colaborador?exists && colaborador.id?exists && certificacao?exists && certificacao.id?exists>
			<@ww.form name="form" action="insertOrUpdate.action" method="POST">
				<@ww.hidden name="certificacao.id" value="${certificacao.id}"/>
				<@ww.hidden name="colaborador.id" value="${colaborador.id}"/>
				
				<#if colaboradorCertificacao?exists &&  colaboradorCertificacao.id?exists>
					<@ww.hidden name="colaboradorCertificacao.id" value="${colaboradorCertificacao.id}"/>
				<#else>
					<@ww.hidden name="colaboradorCertificacao.id" value=""/>
				</#if>
				<br/>
				<#assign i = 0/>		
				
				<@display.table name="colaboradorAvaliacaoPraticas" id="colaboradorAvaliacaoPratica" class="dados">
					<@display.caption><div style="background-color: #EFEFEF;color:#5C5C5A;" id="tituloTabelaAP" >Avaliações Práticas</div> </@display.caption>
					
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
						<#assign colaboradorAvaliacaoPraticaData = "${hoje?date}"/>
					</#if>
					
						<@display.column property="avaliacaoPratica.titulo" title="Título" style="width: 500px;"/>
						<@display.column property="avaliacaoPratica.notaMinima" title="Nota Mínima Aprovação" style="width: 100px;text-align: center;" />
						
						<#if colaboradorCertificacao?exists &&  colaboradorCertificacao.id?exists && !colaboradorCertificacao.ultimaCertificacao>
							<@display.column title="Realizada em" style="width: 160px;text-align: center;height: 30px !important">
								${colaboradorAvaliacaoPraticaData}
							</@display.column>

							<@display.column title="Nota" style="width: 80px;text-align: center;height: 30px !important">
								${colaboradorAvaliacaoPraticaNota}
							</@display.column>
						<#else>						
							<@display.column title="Realizada em" style="width: 160px;text-align: center;height: 30px !important">
								<#if colaboradorAvaliacaoPraticas?exists && (0 < colaboradorAvaliacaoPraticas?size) &&  colaboradorTurmas?exists && (0 < colaboradorTurmas?size) >
										<@ww.datepicker id="data[${i}]" name="colaboradorAvaliacaoPraticas[${i}].data" cssClass="mascaraData" value="${colaboradorAvaliacaoPraticaData}" theme="simple"/><br>
								</#if>
								<@ww.hidden name="colaboradorAvaliacaoPraticas[${i}].avaliacaoPratica.id" value="${colaboradorAvaliacaoPratica.avaliacaoPratica.id}"/>
								<@ww.hidden name="colaboradorAvaliacaoPraticas[${i}].id" value="${colaboradorAvaliacaoPraticaId}"/>
							</@display.column>
							<@display.column title="Nota" style="width: 80px;text-align: center;height: 30px !important">
								<#if colaboradorAvaliacaoPraticas?exists && (0 < colaboradorAvaliacaoPraticas?size) &&  colaboradorTurmas?exists && (0 < colaboradorTurmas?size) >
										<@ww.textfield id="nota[${i}]" name="colaboradorAvaliacaoPraticas[${i}].nota" value="${colaboradorAvaliacaoPraticaNota}" maxLength="4" cssStyle="text-align:right;width:50px;border:1px solid #BEBEBE;" onkeypress = "return(somenteNumeros(event,'.,,'));"/>
								</#if>
							</@display.column>
						</#if>
						<#assign i = i + 1/>
					</@display.table>
				
					<#if colaboradorAvaliacaoPraticas?exists && (0 < colaboradorAvaliacaoPraticas?size) &&  colaboradorTurmas?exists && (0 < colaboradorTurmas?size) &&  !(colaboradorCertificacao?exists &&  colaboradorCertificacao.id?exists && !colaboradorCertificacao.ultimaCertificacao) >
						<div class="buttonGroup">
							<button type="button" class="btnGravar" onclick="submeter('insertOrUpdate.action');"></button>
						</div>
					</#if>
				
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
			</@ww.form>
		</#if>	
</body>
</html>
