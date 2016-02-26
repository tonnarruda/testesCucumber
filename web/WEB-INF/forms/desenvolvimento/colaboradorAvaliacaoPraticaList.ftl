<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>

	<title>Avaliação Prática - Notas em Lote</title>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorAvaliacaoPraticaDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	
	<script type="text/javascript">
		$(function(){
			$('.mascaraData').css("border","1px solid #BEBEBE");
		});
		
		function submeter(action)
		{
			if(action == 'buscaColaboradoresLote.action'){
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
					return document.form.submit();
			}
		}
		
		function verificaCertificacao(i, ColabAvPraticaId){
			removeAtributos(i);
			if(ColabAvPraticaId){
				DWRUtil.useLoadingMessage('Carregando...');
				ColaboradorAvaliacaoPraticaDWR.verificaUltimaCertificacao(ColabAvPraticaId, function(colabAvaliacaoPratica) {
						$('#nota-' + i).val(colabAvaliacaoPratica.nota);
						$('#data-' + i).val(colabAvaliacaoPratica.dataString);
						
						if(colabAvaliacaoPratica.colaboradorCertificacao)
						{
							$('#colaboradorCertificacaoId-' + i).val(colabAvaliacaoPratica.colaboradorCertificacao.id);
						
							if(!colabAvaliacaoPratica.colaboradorCertificacao.ultimaCertificacao){
								$('#data-' + i + '_button').hide();
								$('#data-' + i).attr('disabled','disabled').css("background-color", "#EFEFEF").css( { marginLeft : "-22px"} );
								$('#data-' + i).parent().append('<div id ="imagem-' + i +'" style="margin-left: 95px;margin-top: -16px;margin-bottom: -3px;vertical-align: top;" ><img id="tooltipHelp' + i + '" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" />');
								$('#tooltipHelp' + i).qtip({
									content:"O sistema não possibilita a edição da data da avaliação prática, quando a mesma não é referente a última certificação do colaborador."
								});
	
								$('#nota-' + i).attr('disabled','disabled').css("background-color", "#EFEFEF");
								$('#nota-' + i).parent().append('<div id ="imagemNota-' + i +'" style="margin-left: 80px;margin-top: -16px;margin-bottom: -3px;vertical-align: top;" ><img id="tooltipHelpNota' + i + '" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" />');
								$('#tooltipHelpNota' + i).qtip({
									content:"O sistema não possibilita a edição da nota da avaliação prática, quando a mesma não é referente a última certificação do colaborador."
								});
							}
						}
					});
			}else{
				$('#nota-' + i).val('');
				$('#data-' + i).val($.datepicker.formatDate('dd/mm/yy',new Date()));
				$('#colaboradorCertificacaoId-' + i).val('');
			}
		} 
		
		function removeAtributos(i){
			$('#data-' + i).removeAttr('disabled').css("background-color", "#FFF").css( { marginLeft : "0px"} );
			$('#nota-' + i).removeAttr('disabled').css("background-color", "#FFF");
			$('#data-' + i + '_button').show();
			$('#imagem-' + i).remove();
			$('#imagemNota-' + i).remove();
		}
		
		function onKeyPressData(i){
			
			dataDigitadoArray = $("#data-" + i).val().split("/");
			exibirmensagem = false;
			
			if(dataDigitadoArray.length == 3 && (dataDigitadoArray[0]).length == 2 && (dataDigitadoArray[1]).length == 2 && (dataDigitadoArray[2]).length == 4)
			{
				dataDigitada = new Date(dataDigitadoArray[2], dataDigitadoArray[1] - 1, dataDigitadoArray[0]);
				
				$('#avPraticas-' + i).find('option').each(function() {
	    			if($(this).val() && $('#avPraticas-' + i + ' :selected').text() !=  $(this).text()){
		    			dataArray = $(this).text().split("/");
						data = new Date(dataArray[2], dataArray[1] - 1, dataArray[0]);
		    			
						if(dataDigitada.getTime() <= data.getTime()) 
		    				exibirmensagem = true;
	    			}
				});
			}
			
			if(exibirmensagem){
				jAlert('Não é possível inserir uma data igual ou inferior a data da última avaliação.');
				$('#data-' + i).val($.datepicker.formatDate('dd/mm/yy',new Date()));
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
					<@ww.select label="Certificações com avaliações práticas" name="certificacao.id" list="certificacoes" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." onchange="submeter('buscaColaboradoresLote.action');" cssStyle="width: 800px;" />
					<@ww.select label="Avaliação prática" name="avaliacaoPratica.id" list="avaliacoesPraticas" listKey="id" listValue="titulo" headerKey="" headerValue="Selecione..." onchange="submeter('buscaColaboradoresLote.action');" cssStyle="width: 800px;" />
					<br><br>
				</ul>
			</@ww.div>
		</li>
	</@ww.form>
	</br>
	<#if avaliacaoPratica?exists && avaliacaoPratica.id?exists && certificacao?exists && certificacao.id?exists>
		<@ww.form name="form" action="insertOrUpdate.action" method="POST">
			<@ww.hidden name="certificacao.id" value="${certificacao.id}"/>
			<@ww.hidden name="avaliacaoPratica.id" value="${avaliacaoPratica.id}"/>
			
			<#assign i = 0/>		
			
			<@display.table name="colaboradorCertificacaos" id="colabCertificacao" class="dados">
				<@display.caption><div style="background-color: #EFEFEF;color:#5C5C5A;" id="tituloTabelaAP" >Colaboradores na Certificação</div> </@display.caption>
			
				<#if colabCertificacao.colaboradorAvaliacaoPraticaAtual?exists && colabCertificacao.colaboradorAvaliacaoPraticaAtual.colaboradorCertificacao?exists && colabCertificacao.colaboradorAvaliacaoPraticaAtual.colaboradorCertificacao.id?exists>
					<#assign colaboradorCertificacaoId = "${colabCertificacao.colaboradorAvaliacaoPraticaAtual.colaboradorCertificacao.id}"/>
				<#else>
					<#assign colaboradorCertificacaoId = ""/>
				</#if>
				
				<#if colabCertificacao.colaborador?exists && colabCertificacao.colaborador.id?exists>
					<#assign colaboradorId = "${colabCertificacao.colaborador.id}"/>
				<#else>
					<#assign colaboradorId = ""/>
				</#if>
	
				<#if colabCertificacao.colaboradorAvaliacaoPraticaAtual?exists && colabCertificacao.colaboradorAvaliacaoPraticaAtual.nota?exists>
					<#assign colaboradorAvaliacaoPraticaNota = "${colabCertificacao.colaboradorAvaliacaoPraticaAtual.nota}"/>
				<#else>
					<#assign colaboradorAvaliacaoPraticaNota = ""/>
				</#if>
				
				<#if colabCertificacao.colaboradorAvaliacaoPraticaAtual?exists && colabCertificacao.colaboradorAvaliacaoPraticaAtual.data?exists>
					<#assign colaboradorAvaliacaoPraticaData = "${colabCertificacao.colaboradorAvaliacaoPraticaAtual.data?date}"/>
				<#else>
					<#assign colaboradorAvaliacaoPraticaData = "${hoje?date}"/>
				</#if>
				
				<@display.column property="colaborador.nome" title="Nome do Colaborador" style="width: 500px;"/>
					
				<@display.column title="Avaliações Respondidas em" style="width: 80px;text-align: center;height: 30px !important">
					<#if (!colabCertificacao.colaboradoresAvaliacoesPraticas?exists) || (colabCertificacao.colaboradorAvaliacaoPraticaAtual?exists && colabCertificacao.colaboradorAvaliacaoPraticaAtual.colaboradorCertificacao?exists && colabCertificacao.colaboradorAvaliacaoPraticaAtual.colaboradorCertificacao.id?exists)>
						<@ww.select id="avPraticas-${i}" list="colaboradorCertificacaos[${i}].colaboradoresAvaliacoesPraticas" name="colaboradorCertificacaos[${i}].colaboradorAvaliacaoPraticaAtual.id" listKey="id" listValue="dataFormatada" headerKey="" headerValue="Nova nota" onchange="verificaCertificacao(${i}, this.value);"/>
					<#else>
						<@ww.select id="avPraticas-${i}" list="colaboradorCertificacaos[${i}].colaboradoresAvaliacoesPraticas" name="colaboradorCertificacaos[${i}].colaboradorAvaliacaoPraticaAtual.id" listKey="id" listValue="dataFormatada" onchange="verificaCertificacao(${i}, this.value);"/>
					</#if>
				</@display.column>
					
				<#if colabCertificacao.id?exists && (colabCertificacao.ultimaCertificacao?exists && !colabCertificacao.ultimaCertificacao)>
					<@display.column title="Realizada em" style="width: 160px;text-align: center;height: 30px !important">
						${colaboradorAvaliacaoPraticaData}
					</@display.column>

					<@display.column title="Nota" style="width: 80px;text-align: center;height: 30px !important">
						${colaboradorAvaliacaoPraticaNota}
					</@display.column>
				<#else>						
					<@display.column title="Realizada em" style="width: 160px;text-align: center;height: 30px !important">
						<@ww.datepicker id="data-${i}" name="colaboradorCertificacaos[${i}].colaboradorAvaliacaoPraticaAtual.data" cssClass="mascaraData" value="${colaboradorAvaliacaoPraticaData}" theme="simple" onchange="onKeyPressData(${i});" onblur="onKeyPressData(${i});" /> 
						<@ww.hidden name="colaboradorCertificacaos[${i}].id" id="colaboradorCertificacaoId-${i}" value="${colaboradorCertificacaoId}"/>
						<@ww.hidden name="colaboradorCertificacaos[${i}].colaborador.id" id="colaboradorId-${i}" value="${colaboradorId}"/>
					</@display.column>
					<@display.column title="Nota" style="width: 80px;text-align: center;height: 30px !important">
						<@ww.textfield id="nota-${i}" name="colaboradorCertificacaos[${i}].colaboradorAvaliacaoPraticaAtual.nota" value="${colaboradorAvaliacaoPraticaNota}" maxLength="4" cssStyle="text-align:right;width:50px;border:1px solid #BEBEBE;" onkeypress = "return(somenteNumeros(event,'.,,'));"/>
					</@display.column>
				</#if>
				
				<#assign i = i + 1/>
			</@display.table>
			
			<#if colaboradorCertificacaos?exists && (0 < colaboradorCertificacaos?size)>
				<div class="buttonGroup">
					<button type="button" class="btnGravar" onclick="submeter('insertOrUpdateLote.action');"></button>
				</div>
			</#if>
		</@ww.form>
	</#if>	
</body>
</html>
