<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		
		#preloader {
		    position: absolute;
		    left: 0px;
		    right: 0px;
		    bottom: 0px;
		    top: 0px;
		    background: #ccc;
		}
	</style>

	<title>Avaliação Prática - Notas em Lote</title>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorAvaliacaoPraticaDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	
	<script type="text/javascript">
		$(function(){
			$('.mascaraData').css("border","1px solid #BEBEBE");
		});
		
		function submeter(action)
		{
			if(action == 'buscaColaboradoresLote.action'){
				processando('${urlImgs}');
				document.formBusca.action = action; 
				document.formBusca.submit();
			}else{ 			
				document.formSubmit.action = action;
				var arrayDataValida = [];
				$('.mascaraData').each(function(){
					if ($(this).val() == '  /  /    ')
			    		$(this).val('');
			    	else
			    		arrayDataValida.push($(this).attr('id'));
				});

				if(validaFormulario('form', new Array(), arrayDataValida, true, '${urlImgs}'))
					return document.formSubmit.submit();
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
							$('#excluir-' + i).hide();
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
							
							$('#ultimaCertificacao-' + i).val(false);
						}
					}
				});
			}else{
				$('#nota-' + i).val('');
				calculaDataPermitida(i);
			}
		} 
		
		function removeAtributos(i){
			$('#data-' + i).removeAttr('disabled').css("background-color", "#FFF").css( { marginLeft : "0px"} );
			$('#nota-' + i).removeAttr('disabled').css("background-color", "#FFF");
			$('#colaboradorCertificacaoId-' + i).val('');
			$('#ultimaCertificacao-' + i).val(true);
			$('#data-' + i + '_button').show();
			$('#excluir-' + i).show();
			$('#imagemNota-' + i).remove();
			$('#imagem-' + i).remove();
		}
		
		function onKeyPressData(i){
			
			dataDigitadoArray = $("#data-" + i).val().split("/");
			exibirMensagem = false;
			
			if(dataDigitadoArray.length == 3 && (dataDigitadoArray[0]).length == 2 && (dataDigitadoArray[1]).length == 2 && (dataDigitadoArray[2]).length == 4)
			{
				dataDigitada = new Date(dataDigitadoArray[2], dataDigitadoArray[1] - 1, dataDigitadoArray[0]);
				
				$('#avPraticas-' + i).find('option').each(function() {
	    			if($(this).val() && $('#avPraticas-' + i + ' :selected').text() !=  $(this).text()){
		    			dataArray = $(this).text().split("/");
						data = new Date(dataArray[2], dataArray[1] - 1, dataArray[0]);
		    			
						if(dataDigitada.getTime() <= data.getTime()) 
		    				exibirMensagem = true;
	    			}
				});
			}
			
			if(exibirMensagem){
				jAlert('Não é possível inserir uma data igual ou inferior a data da última avaliação.');
				calculaDataPermitida(i);
			}
		}
		
		function calculaDataPermitida(i)
		{
			var maiorData;
			$('#avPraticas-' + i).find('option').each(function(){
    			if($(this).val()){
	    			dataArray = $(this).text().split("/");
					data = new Date(dataArray[2], dataArray[1] - 1, dataArray[0]);
	    			
					if(!maiorData || maiorData.getTime() <= data.getTime()) 
	    				maiorData = data;
    			}
			});

			maiorData.setDate(maiorData.getDate() + 1);
			$('#data-' + i).val($.datepicker.formatDate('dd/mm/yy',maiorData));
		}
		
		function apagarNota(i){
			$('#nota-' + i).val('');
			$('#data-' + i).val('');
			
			if($("#avPraticas-" + i).val())
				ajustaFormSubmit(i);
		}
		
		function ajustaFormSubmit(i){
			setTimeout(function() { 
				if($('#colaboradorIdSubmit-' + i).val() != "undefined")
					$(".submit-" + i).remove();							
			
				if($("#ultimaCertificacao-" + i).val() == 'true'){
					$('#formSubmit ul').append('<input type="hidden" class="submit-' + i + '" name="colaboradorCertificacaos[' + i + '].colaboradorAvaliacaoPraticaAtual.data" value="' +  $("#data-" + i).val() + '" />');
					$('#formSubmit ul').append('<input type="hidden" class="submit-' + i + '" name="colaboradorCertificacaos[' + i + '].id" value="' +  $("#colaboradorCertificacaoId-" + i).val() + '" />');
					$('#formSubmit ul').append('<input type="hidden" class="submit-' + i + '" id="colaboradorIdSubmit-' + i + '" name="colaboradorCertificacaos[' + i + '].colaborador.id" value="' +  $("#colaboradorId-" + i).val() + '" />');
					$('#formSubmit ul').append('<input type="hidden" class="submit-' + i + '" name="colaboradorCertificacaos[' + i + '].colaboradorAvaliacaoPraticaAtual.id" value="' +  $("#avPraticas-" + i).val() + '" />');
					$('#formSubmit ul').append('<input type="hidden" class="submit-' + i + '" name="colaboradorCertificacaos[' + i + '].colaboradorAvaliacaoPraticaAtual.nota" value="' +  $("#nota-" + i).val() + '" />')
					$('#formSubmit ul').append('<input type="hidden" class="submit-' + i + '" name="colaboradorCertificacaos[' + i + '].ultimaCertificacao" value="' +  $("#ultimaCertificacao-" + i).val() + '" />')
				} 
			}, 800);
		}
	</script>
</head>
<body>
	<#include "../ftl/mascarasImports.ftl" />
	<@ww.actionmessage />
	<@ww.actionerror />

	<@ww.form name="formBusca" action="buscaColaboradoresLote.action" method="POST">
		<li>
			<@ww.div cssClass="divInfo" cssStyle="width: 950px;">
				<ul>
					<@ww.select label="Certificações com avaliações práticas" name="certificacao.id" list="certificacoes" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." onchange="$('#avaliacaoPraticaId option').val('');submeter('buscaColaboradoresLote.action');" cssStyle="width: 800px;" />
					<@ww.select label="Avaliação prática" name="avaliacaoPratica.id" id="avaliacaoPraticaId" list="avaliacoesPraticas" listKey="id" listValue="titulo" headerKey="" headerValue="Selecione..." onchange="submeter('buscaColaboradoresLote.action');" cssStyle="width: 800px;" />
					<br><br>
				</ul>
			</@ww.div>
		</li>
	</@ww.form>
	</br>
	<#if avaliacaoPratica?exists && avaliacaoPratica.id?exists && certificacao?exists && certificacao.id?exists>
		<@ww.form name="formSubmit" id="formSubmit" action="" method="POST">
			<@ww.hidden name="certificacao.id" value="${certificacao.id}"/>
			<@ww.hidden name="avaliacaoPratica.id" value="${avaliacaoPratica.id}"/>
		</@ww.form>
		
		<@ww.form name="form" id="form" action="insertOrUpdateLote.action" method="POST">
			<@ww.hidden name="certificacao.id" value="${certificacao.id}"/>
			<@ww.hidden name="avaliacaoPratica.id" value="${avaliacaoPratica.id}"/>
			<#if colaboradorCertificacaos?exists && (0 < colaboradorCertificacaos?size)>
				<#assign i = 0/>		
				<@display.table name="colaboradorCertificacaos" id="colabCertificacao" class="dados">
					<@display.caption><div style="background-color: #EFEFEF;color:#5C5C5A;" id="tituloTabelaAP" >Colaboradores que participam da certificação e estão aprovados em seus cursos</div> </@display.caption>
				
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
						
					<@display.column title="Avaliações Certificadas Respondidas em" style="width: 180px;text-align: center;height: 30px !important">
						<#if (!colabCertificacao.colaboradoresAvaliacoesPraticas?exists) || (colabCertificacao.colaboradorAvaliacaoPraticaAtual?exists && colabCertificacao.colaboradorAvaliacaoPraticaAtual.colaboradorCertificacao?exists && colabCertificacao.colaboradorAvaliacaoPraticaAtual.colaboradorCertificacao.id?exists && colabCertificacao.possivelInserirNotaAvPratica)>
							<@ww.select id="avPraticas-${i}" list="colaboradorCertificacaos[${i}].colaboradoresAvaliacoesPraticas" name="colaboradorCertificacaos[${i}].colaboradorAvaliacaoPraticaAtual.id" listKey="id" listValue="dataFormatada" cssStyle="width: 100px;" headerKey="" headerValue="Nova nota" onchange="verificaCertificacao(${i}, this.value);ajustaFormSubmit(${i});"/>
						<#else>
							<@ww.select id="avPraticas-${i}" list="colaboradorCertificacaos[${i}].colaboradoresAvaliacoesPraticas" name="colaboradorCertificacaos[${i}].colaboradorAvaliacaoPraticaAtual.id" listKey="id" listValue="dataFormatada" cssStyle="width: 100px;" onchange="verificaCertificacao(${i}, this.value);ajustaFormSubmit(${i});"/>
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
						<@display.column title="Realizada em" style="width: 180px;text-align: center;height: 30px !important">
							<@ww.datepicker id="data-${i}" name="colaboradorCertificacaos[${i}].colaboradorAvaliacaoPraticaAtual.data" cssClass="mascaraData" value="${colaboradorAvaliacaoPraticaData}" theme="simple" onchange="onKeyPressData(${i});ajustaFormSubmit(${i});" onblur="onKeyPressData(${i});ajustaFormSubmit(${i});" /> 
							<@ww.hidden name="colaboradorCertificacaos[${i}].id" id="colaboradorCertificacaoId-${i}" value="${colaboradorCertificacaoId}"/>
							<@ww.hidden name="colaboradorCertificacaos[${i}].ultimaCertificacao" id="ultimaCertificacao-${i}" value="true"/>
							<@ww.hidden name="colaboradorCertificacaos[${i}].colaborador.id" id="colaboradorId-${i}" value="${colaboradorId}"/>
						</@display.column>
						<@display.column title="Nota" style="width: 80px;text-align: center;height: 30px !important">
							<@ww.textfield id="nota-${i}" name="colaboradorCertificacaos[${i}].colaboradorAvaliacaoPraticaAtual.nota" value="${colaboradorAvaliacaoPraticaNota}" maxLength="4" cssStyle="text-align:right;width:50px;border:1px solid #BEBEBE;" onkeyup = "ajustaFormSubmit(${i});return(somenteNumeros(event,'.,,'));" liClass="liLeft"/>
							<a href="#" onclick="apagarNota(${i})"><img id="excluir-${i}" border="0" title="<@ww.text name="list.del.hint"/>" src="<@ww.url includeParams="none" value="/imgs/delete.gif"/>"></a>
						</@display.column>
					</#if>
					
					<#assign i = i + 1/>
				</@display.table>
			
				<div class="buttonGroup">
					<button type="button" class="btnGravar" onclick="submeter('insertOrUpdateLote.action');"></button>
				</div>
			<#else>	
				<div class="info">
					<ul>
						<li>Não existem colaboradores participantes nessa certificação</li>
					</ul>
				</div>
			</#if>
		</@ww.form>
	</#if>	
</body>
</html>
