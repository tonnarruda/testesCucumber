<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<title>Avaliação Prática - Notas Individuais</title>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	
	<script type="text/javascript">
		$(function(){
			$('.mascaraData').css("border","1px solid #BEBEBE");
			insereHelp();
			
			calculaDataPermitida();
		});
		
		function submeter(action)
		{
			processando('${urlImgs}');
			if(action == 'buscaColaboradores.action'){
				$('#colaboradorAvaliacaoPratica').remove();
				$('#colaboradorTurma').remove();
				document.formBusca.action = action; 
				document.formBusca.submit();
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
				else
					$('.processando').remove();
			}
		}
		
		function insereHelp()
		{
			<#if colaboradorCertificacao?exists && colaboradorCertificacao.id?exists && (colaboradorCertificacao.ultimaCertificacao?exists && !colaboradorCertificacao.ultimaCertificacao)>
				$("#tituloTabelaAP").append('<img id="tooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin: 1px 0px -2px 10px;" />');
				
				$('#tooltipHelp').qtip({
					content:"O sistema não possibilita a edição da data e nota da avaliação prática, quando a mesma não é referente a última certificação do colaborador."
				});
			</#if>
		}
		
		function apagarNota(i){
			$('#nota-' + i).val('');
			$('#data-' + i).val('  /  /    ');
		}
		
		function onKeyPressData(dataAlterada)
		{
			dataDigitadoArray = dataAlterada.val().split("/");
			exibirMensagem = false;
			
			if(dataDigitadoArray.length == 3 && (dataDigitadoArray[0]).length == 2 && (dataDigitadoArray[1]).length == 2 && (dataDigitadoArray[2]).length == 4)
			{
				dataDigitada = new Date(dataDigitadoArray[2], dataDigitadoArray[1] - 1, dataDigitadoArray[0]);
				
				$('#colaboradorCertificacaoId').find('option').each(function(){
	    			if($(this).val() && $('#colaboradorCertificacaoId :selected').text() !=  $(this).text()){
		    			dataArray = $(this).text().split("/");
						data = new Date(dataArray[2], dataArray[1] - 1, dataArray[0]);
		    			
						if(dataDigitada.getTime() <= data.getTime()) 
		    				exibirMensagem = true;
	    			}
				});
			}
			
			if(exibirMensagem){
				jAlert('Não é possível inserir uma data igual ou inferior a data da última avaliação.');
				dataAlterada.val('  /  /    ');			
				calculaDataPermitida();
			}
		}
		
		function calculaDataPermitida()
		{
			if(!$('#colaboradorCertificacaoId :selected').val())
			{
				var maiorData;
				$('#colaboradorCertificacaoId').find('option').each(function(){
	    			if($(this).val()){
		    			dataArray = $(this).text().split("/");
						data = new Date(dataArray[2], dataArray[1] - 1, dataArray[0]);
		    			
						if(!maiorData || maiorData.getTime() <= data.getTime()) 
		    				maiorData = data;
	    			}
				});
	
				if(maiorData != undefined){
					maiorData.setDate(maiorData.getDate() + 1);
					$('.mascaraData').each(function(){
						if($(this).val() == '  /  /    ')
							$(this).val($.datepicker.formatDate('dd/mm/yy',maiorData));
					});
				}
			}
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
					<@ww.select label="Certificações com avaliações práticas" name="certificacao.id" id="certificacaoId" list="certificacoes" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." onchange="$('#colaboradorId option').val('');submeter('buscaColaboradores.action');" cssStyle="width: 800px;" />
					<@ww.select label="Colaborador" name="colaborador.id" id="colaboradorId" list="colaboradores" listKey="id" listValue="nomeCpf" headerKey="" headerValue="Selecione..." onchange="$('#colaboradorCertificacaoId option').val('');submeter('buscaColaboradores.action');" cssStyle="width: 800px;"/>
					<#if possivelInserirNotaAvPratica>
						<@ww.select label="Certificações em que o colaborador foi aprovado" id="colaboradorCertificacaoId" name="colaboradorCertificacao.id" list="colaboradorCertificacaos" listKey="id" listValue="dataFormatada" headerKey="" headerValue="Nova Certificação" onchange="submeter('buscaColaboradores.action');" cssStyle="width: 800px;"/>
					<#else>
						<@ww.select label="Certificações em que o colaborador foi aprovado" id="colaboradorCertificacaoId" name="colaboradorCertificacao.id" list="colaboradorCertificacaos" listKey="id" listValue="dataFormatada" onchange="submeter('buscaColaboradores.action');" cssStyle="width: 800px;"/>
					</#if>
					
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
						<#assign colaboradorAvaliacaoPraticaData = "  /  /    "/>
					</#if>
					
						<@display.column property="avaliacaoPratica.titulo" title="Título" style="width: 500px;"/>
						<@display.column property="avaliacaoPratica.notaMinima" title="Nota Mínima Aprovação" style="width: 100px;text-align: center;" />
						
						<#if colaboradorCertificacao?exists && colaboradorCertificacao.id?exists && (colaboradorCertificacao.ultimaCertificacao?exists && !colaboradorCertificacao.ultimaCertificacao)>
							<@display.column title="Realizada em" style="width: 160px;text-align: center;height: 30px !important">
								${colaboradorAvaliacaoPraticaData}
							</@display.column>

							<@display.column title="Nota" style="width: 80px;text-align: center;height: 30px !important">
								${colaboradorAvaliacaoPraticaNota}
							</@display.column>
						<#else>						
							<@display.column title="Realizada em" style="width: 160px;text-align: center;height: 30px !important">
								<#if colaboradorAvaliacaoPraticas?exists && (0 < colaboradorAvaliacaoPraticas?size)>
									<@ww.datepicker id="data-${i}" name="colaboradorAvaliacaoPraticas[${i}].data" cssClass="mascaraData" value="${colaboradorAvaliacaoPraticaData}" theme="simple" onchange="onKeyPressData($(this));" onblur="onKeyPressData($(this));"/><br>
									<@ww.hidden name="colaboradorAvaliacaoPraticas[${i}].avaliacaoPratica.id" value="${colaboradorAvaliacaoPratica.avaliacaoPratica.id}"/>
									<@ww.hidden name="colaboradorAvaliacaoPraticas[${i}].avaliacaoPratica.notaMinima" value="${colaboradorAvaliacaoPratica.avaliacaoPratica.notaMinima}"/>
									<@ww.hidden name="colaboradorAvaliacaoPraticas[${i}].id" value="${colaboradorAvaliacaoPraticaId}"/>
								</#if>
							</@display.column>
							<@display.column title="Nota" style="width: 80px;text-align: center;height: 30px !important">
								<#if colaboradorAvaliacaoPraticas?exists && (0 < colaboradorAvaliacaoPraticas?size)>
									<@ww.textfield id="nota-${i}" name="colaboradorAvaliacaoPraticas[${i}].nota" value="${colaboradorAvaliacaoPraticaNota}" maxLength="4" cssStyle="text-align:right;width:50px;border:1px solid #BEBEBE;" onkeypress = "return(somenteNumeros(event,'.,,'));" liClass="liLeft"/>
									<a href="#" onclick="apagarNota(${i})"><img border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
								</#if>
							</@display.column>
						</#if>
						<#assign i = i + 1/>
					</@display.table>
				
					<#if colaboradorAvaliacaoPraticas?exists && (0 < colaboradorAvaliacaoPraticas?size) &&  !(colaboradorCertificacao?exists &&  colaboradorCertificacao.id?exists && (colaboradorCertificacao.ultimaCertificacao?exists && !colaboradorCertificacao.ultimaCertificacao) ) >
						<div class="buttonGroup">
							<button type="button" class="btnGravar" onclick="submeter('insertOrUpdate.action');"></button>
						</div>
					</#if>
				
				<br/>
				<@display.table name="colaboradorTurmas" id="colaboradorTurma" class="dados">
					<@display.caption><div style="background-color: #EFEFEF;color:#5C5C5A;">Cursos da certificação</div> </@display.caption>
					<@display.column property="curso.nome" title="Curso" style="width: 300px;"/>
					<@display.column property="turma.descricao" title="Turma" />
					<@display.column title="Período" style="text-align:center; width:180px">
						<#if colaboradorTurma.turma.dataPrevIni?exists && colaboradorTurma.turma.dataPrevFim?exists>
							${colaboradorTurma.turma.dataPrevIni?string("dd'/'MM'/'yyyy")} - ${colaboradorTurma.turma.dataPrevFim?string("dd'/'MM'/'yyyy")}
						<#else>
							-
						</#if>
					</@display.column>
					<@display.column property="turma.realizadaFormatada" title="Realizada" style="width: 50px;" />
					<@display.column property="aprovadoMaisNota" title="Aprovado" style="width: 50px;" />
				</@display.table>
			</@ww.form>
		</#if>	
</body>
</html>