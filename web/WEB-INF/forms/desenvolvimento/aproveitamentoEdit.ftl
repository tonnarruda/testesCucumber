<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"]/>
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>
	
	<title>Avaliações dos Alunos</title>
	
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	
	<script type='text/javascript'>
		var alterouCampo = false;
		var valorCampo = "";

		$(function(){
			insereHelp();
			calculaMediaDasNotas();
			
			$('#helpMedia').qtip({
				style: { width: true, height: true },
				content: "Média = (Soma das notas dos participantes) / (qtd total de participantes)</br>Obs: Os participantes que estiverem sem nota serão considerado com nota </br> 0(zero) para o cálculo."
			});
		});
		
		function verificaEdicao()
		{
			if (alterouCampo)
				newConfirm('Você alterou alguns campos, deseja mudar de avaliação sem gravar?', function(){document.formFiltro.submit();});
			else
				document.formFiltro.submit();
		}

		function setValor(valor)
		{
			valorCampo = valor;
		}

		function verificaValor(valor)
		{
			alterouCampo = valorCampo != valor;
		}
		
		function calculaMediaDasNotas() {
			var total = 0;
			qtdColabNaoNulo = 0;
			$("input[name=notas]").each(function(i,n){
			    if($(n).val() != null && $(n).val() != ""){
			    	total += parseFloat($(n).val()); 
			    	qtdColabNaoNulo++;
			    }
			});
			
			if(qtdColabNaoNulo == 0)
				qtdColabNaoNulo = 1;
			
			$(".media").html(parseFloat(total/qtdColabNaoNulo).toFixed(2));
		}
		
		function insereHelp()
		{
			<#if somenteLeitura>
				var id = "tooltipHelp" + 0;
				$("#colaboradorTurma th:eq(" + 2 + ")" ).append('<img id="' + id + '" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: 1px" />');
				
				$('#' + id).qtip({
					content: "O sistema não possibilita a edição da nota da avaliação, quando a mesma não é referente a última certificação do colaborador."
				});
			</#if>
		}
		
		function submit(){
			if(($('.colabTurmaIdsCertificados').length > 0) && ${empresaSistema.controlarVencimentoPorCertificacao?string}){
				var exibeDialog = false;
				
				$('.colabTurmaIdsCertificados').each(function(){
					<#if avaliacaoCurso?exists && avaliacaoCurso.minimoAprovacao?exists>
						colabCertificadoId = $(this).attr("value");
						if($('#nota_' + colabCertificadoId).val() < ${avaliacaoCurso.minimoAprovacao}){
							exibeDialog = true	
						}
					</#if>
				});
				
				if(exibeDialog)
					dialogCertificacao();
				else{
					processando('${urlImgs}');
					document.form.submit();
				}
			
			}else{
				processando('${urlImgs}');
				document.form.submit();
			}
		}
		
		function dialogCertificacao(){
			msg = "Existem colaboradores certificados que serão descertificados. </br>" +
					"Deseja realmente descertificar esses colaboradores?</br>" +
					"Ao confirmar, o colaborador será descertificado " +
					"e caso exista notas de avaliações prática as mesmas serão excluídas.";
			
			$('#dialog').html(msg).dialog({ 	modal: true, 
												width: 500,
												maxHeight: 360,
												buttons: 
												[
												    {
												        text: "Confirmar",
												        click: function() { 
												        	$(this).dialog("close");									        
												        	enviaForm();
												        }
												    },
												    {
												        text: "Cancelar",
												        click: function() { $(this).dialog("close"); }
												    }
												]
											});
		}
		
		function enviaForm(){
			processando('${urlImgs}');
			document.form.submit();
		}
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="formFiltro" action="prepareAproveitamento.action" onsubmit="javascript:verificaEdicao();" method="POST">
		<@ww.select label="Avaliação" name="avaliacaoCurso.id" list="avaliacaoCursos" id="avaliacao" listKey="id" listValue="titulo"  headerKey="-1" headerValue="Selecione..." onchange="javascript:verificaEdicao();"/>
		<@ww.hidden name="turma.id"/>
		<@ww.hidden name="curso.id"/>
	</@ww.form>

	<#if colaboradoresTurma?exists && 0 < colaboradoresTurma?size>
	<div id="legendas" align="right"></div>
		<br />
		<@ww.form name="form" action="saveAproveitamentoCurso.action" onsubmit="" method="POST">
			<#assign qtdColaborador = 0/>
			<@display.table name="colaboradoresTurma" id="colaboradorTurma" class="dados">
				<#assign qtdColaborador = qtdColaborador + 1/>
				<@display.column title="Nome" style="width: 400px;">
					<p align="left" vertical-align="middle">
						${colaboradorTurma.colaborador.nome}
						<#if colaboradorTurma.certificado>
							<img style="vertical-align: top;" title="Colaborador Certificado" src="<@ww.url includeParams="none" value="/imgs/certificado.png"/>"/>
							<hidden class="colabTurmaIdsCertificados" value="${colaboradorTurma.id}" />
						</#if>
					</p>
				</@display.column>
				
				<@display.column property="colaborador.matricula" title="Matrícula" style="width: 80px;"/>

				<@display.column title="${avaliacaoCurso.titulo}" style="width: 300px;text-align: center;" >
					<#assign valorNota = "" />

					<#if avaliacaoCurso.tipo == 'a'>
						<a href="javascript: executeLink('../avaliacaoCurso/prepareResponderAvaliacaoAluno.action?colaborador.id=${colaboradorTurma.colaborador.id}&avaliacaoCurso.avaliacao.id=${avaliacaoCurso.avaliacao.id}&modeloAvaliacao=L&turma.id=${turma.id}&curso.id=${curso.id}&avaliacaoCurso.id=${avaliacaoCurso.id}&colaboradorTurmaId=${colaboradorTurma.id}');">
							<#if colaboradorTurma.respondeuAvaliacaoTurma>
								<img border="0" title="Editar respostas" src="<@ww.url value="/imgs/page_edit.gif"/>">
							<#else>
								<img border="0" title="Responder" src="<@ww.url value="/imgs/page_new.gif"/>">
							</#if>
						</a>
					<#else>
						<#assign valorNota = -1/>
						<#list aproveitamentos as aproveitamento>
							<#if aproveitamento.colaboradorTurma.id == colaboradorTurma.id>
								<#assign valorNota = aproveitamento.valor />
								<#if !colaboradorTurma.certificadoEmTurmaPosterior>
									<@ww.textfield id="nota_${colaboradorTurma.id}" name="notas" value="${valorNota}" maxLength="5" cssStyle="text-align: right;width: 40px;border:1px solid #BEBEBE;" onkeypress = "return(somenteNumeros(event,'.,,'));" onfocus="setValor(this.value);" onchange="verificaValor(this.value); calculaMediaDasNotas();"/>
								<#else>
									${valorNota}
									<@ww.hidden id="nota_${colaboradorTurma.id}" name="notas" value="${valorNota}"/>
								</#if>
							</#if>
						</#list>
						<#if valorNota == -1 >
							<@ww.textfield id="nota_${colaboradorTurma.id}" name="notas" value="" maxLength="5" cssStyle="text-align: right;width: 40px;border:1px solid #BEBEBE;" onkeypress = "return(somenteNumeros(event,'.,,'));" onfocus="setValor(this.value);" onchange="verificaValor(this.value);"/>
						</#if>
					</#if>
					<@ww.hidden name="colaboradorTurmaIds" value="${colaboradorTurma.id}"/>
				</@display.column>
			</@display.table>
			
			<div style="margin-left: -5px; margin-top: -13px; float: left;">
				<div style="padding-bottom: 12px;font-family: Arial, Helvetica, sans-serif; font-size: 13px; float:left; text-align: center; padding: 10px;">${qtdColaborador} Colaboradores</div>
			</div>
			
			<#if avaliacaoCurso.tipo != 'a'>
				<div style="margin-top: -5px; float: right; border-bottom: 2px solid #BEBEBE; background: #EFEFEF; border-radius: 4px;">
					<div style="font-family: Arial, Helvetica, sans-serif; font-size: 13px; width: 82px; float:left; text-align: center; padding: 8px; font-weight: bold;">Média: </div>
					<img id="helpMedia" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="padding: 8px; margin-left: -20px" />
					<div class="media" style="font-family: Arial, Helvetica, sans-serif; font-size: 13px; float:left; padding: 8px; width: 355px; text-align: center; float: right; ">00</div>
				</div>
				<div style="clear: both;"></div>
			</#if>
			
			<@ww.hidden name="turma.id"/>
			<@ww.hidden name="curso.id"/>
			<@ww.hidden name="avaliacaoCurso.id"/>
		</@ww.form>
	</#if>

	<div id="dialog" title="Confirmar Remoção da Certificação"></div>
	<div class="buttonGroup">
		<#if colaboradoresTurma?exists && 0 < colaboradoresTurma?size && avaliacaoCurso?exists && avaliacaoCurso.tipo != 'a'>
			<button class="btnGravar" onclick="submit()"></button>
		</#if>
		<button class="btnVoltar" onclick="javascript: executeLink('list.action?curso.id=${curso.id}');"></button>
	</div>
</body>
</html>