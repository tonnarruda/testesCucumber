<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>
	<title>Informações do Candidato</title>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-1.4.4.min.js"/>'></script>
	
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/fortes.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/botoes.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/candidato.css"/>');
		@import url('<@ww.url value="/css/formModal.css"/>');

		body {
			background-color: #EBECF1;
		}
		.xz{
		background-color:#fbfa99;
		color:red;
		}
		
		#box
		{
			top: -520px !important;
			left: 20% !important;
			width: 400px !important;
			height: 480px !important;
			z-index: 9999;
		}
		#boxtitle
		{
			width:496px !important;
		}
		.fieldsetPadrao
		{
			width: 300px !important;
		}

		/* Coisas do Gogs */
		#content1 th,
		#content2 th,
		#content3 th,
		#content4 th,
		#content5 th {
			background-color:#999999;
			color:#FFF;
			font-weight:bold;
			padding:8px 15px;
		}
		#content1,
		#content2,
		#content3,
		#content4,
		#content5 {
			background: #FFF;
			border: 1px solid #BEBEBE;
		}
		#abas #aba1,
		#abas #aba2,
		#abas #aba3,
		#abas #aba4,
		#abas #aba5 {
			background: #CCC;
			border: 1px solid #BEBEBE;
		}
		#abas #aba1 {
			background: #FFF;
			border: 1px solid #BEBEBE;
			border-bottom: 1px solid #FFF;
		}
		</style>
		
		<!--[if IE]>
			<style type="text/css" media="screen">
				span.botao
				{
					padding:2px 0 1px 0;
				}
				fieldset
				{
					padding: 10px;
				}
			</style>
		<![endif]-->

		<#if !palavras?exists>
			<#assign palavras=""/>
		</#if>
		<#if !forma?exists>
			<#assign forma=""/>
		</#if>
		
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/formModal.js"/>'></script>
	<script language="javascript">
		function mudaAba(id)
		{
			for(i = 1; i <= 5; i++)
			{
				document.getElementById('content' + i).style.display = id == i ? "block" : "none";
				document.getElementById('aba' + i).style.background  = id == i ? "#FFF" : "#CCC";
				document.getElementById('aba' + i).style.borderBottom = id == i ? "1px solid #FFF" : "1px solid #BEBEBE";
			}
			
			$('#btnImprimirCurriculo, #btnImprimirCurriculoEscaneado').hide();
			
			if (id==1)
				$('#btnImprimirCurriculo').show();
			else if (id==3)
				$('#btnImprimirCurriculoEscaneado').show();
		}

		function popUp(caminho)
		{
			path = caminho;
			window.open(caminho,'Currículo','toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,menubar=no,left=10,top=10')
		}
		
		function habilitarCampo(check, campo)
		{
			if(check.checked)
				document.getElementById(campo).disabled=false;
			else
				document.getElementById(campo).disabled=true;
		}
		
		function imprimirCurriculoEscaneado()
		{
			var imprimeViaExec = window.frames['printFrame'].document.execCommand('print', false, null);
		
			if (!imprimeViaExec)
                window.frames['printFrame'].print();
		}
		
		<#if origemList?exists && origemList='CA'>
			<#assign role = "ROLE_INFORM_CANDIDATO_CURRICULO">
		<#else>
			<#assign role = "ROLE_COLAB_LIST_VISUALIZARCURRICULO">
		</#if>
		
		<#if solicitacao.id?exists>
			var paramsCompetencia = {'candidato.id':'${candidato.id}','solicitacao.id':'${solicitacao.id}'};
		<#else>
			var paramsCompetencia = {'candidato.id':'${candidato.id}'};
		</#if>
		
		$(function() {
			<@authz.authorize ifAllGranted="${role}">
				$('#aba1').css("display", "inline");
				$("#cv").load('<@ww.url includeParams="none" value="/captacao/candidato/verCurriculo.action"/>', {'candidato.id':'${candidato.id}'});
			</@authz.authorize>

			<@authz.authorize ifAllGranted="ROLE_INFORM_CANDIDATO_HISTORICO">
				$('#aba2').css("display", "inline");
				$("#historico").load('<@ww.url includeParams="none" value="/captacao/candidatoSolicitacao/verHistoricoCandidato.action"/>', {'candidato.id':'${candidato.id}'});
			</@authz.authorize>
			
			$("#imagens").load('<@ww.url includeParams="none" value="/captacao/candidato/verCurriculoEscaneado.action"/>', {'candidato.id':'${candidato.id}'});
			$("#textoOcr").load('<@ww.url includeParams="none" value="/captacao/candidato/verCurriculoTextoOcr.action"/>', {'candidato.id':'${candidato.id}',palavras:'${palavras}',forma:'${forma}'});

			<@authz.authorize ifAllGranted="ROLE_INFORM_CANDIDATO_COMPETENCIA">
				$('#aba5').css("display", "inline");
				$("#competencia").load('<@ww.url includeParams="none" value="/captacao/nivelCompetencia/visualizarCandidato.action"/>', paramsCompetencia);
			</@authz.authorize>
		});
	</script>
	<@ww.head/>
</head>

<body>
	<br>
	<div id="abas">
		<div id="aba1" style="display: none">
			<a href="javascript:mudaAba(1)">CURRÍCULO</a>
		</div>
	
		<div id="aba2" style="display: none">
			<a href="javascript:mudaAba(2)">HISTÓRICO</a>
		</div>

		<div id="aba3">
			<a href="javascript:mudaAba(3)">CURRÍCULO ESCANEADO</a>
		</div>

		<div id="aba4">
			<a href="javascript:mudaAba(4)">TEXTO DIGITALIZADO</a>
		</div>
	
		<div id="aba5" style="display: none">
			<a href="javascript:mudaAba(5)">COMPETÊNCIAS</a>
		</div>
		
		<!-- Exame Palografico removido ate segunda ordem do Denis
		<div id="aba5">
			<a href="javascript:mudaAba(5)">EXAME PALOGRÁFICO</a>
		</div>
		-->
	</div>

	<div id="content1">
		<@ww.div id="cv"/>
	</div>

	<div id="content2" style="display: none;">
		<@ww.div id="historico"/>
	</div>

	<div id="content3" style="display: none;">
		<@ww.div id="imagens"/>
	</div>

	<div id="content4" style="display: none;">
		<@ww.div id="textoOcr"/>
	</div>
	
	<div id="content5" style="display: none;">
		<@ww.div id="competencia"/>
	</div>

	<@ww.hidden id="palavras" name="palavras"/>
	<@ww.hidden id="forma" name="forma"/>

	<div class="buttonGroup" style="position:relative;">
	<div id="box">
		<span id="boxtitle"></span>
		<@ww.form name="form" id="form" action="imprimirCurriculo.action" method="POST">
		
			<#assign desabilita1="true"/>
			<#assign desabilita2="true"/>
			<#assign desabilita3="true"/>
			
			<#if configuracaoImpressaoCurriculo.exibirAssinatura1>
				<#assign desabilita1="false"/>
			</#if>
			<#if configuracaoImpressaoCurriculo.exibirAssinatura2>
				<#assign desabilita2="false"/>
			</#if>
			<#if configuracaoImpressaoCurriculo.exibirAssinatura3>
				<#assign desabilita3="false"/>
			</#if>
			
			<li>
				<fieldset class="fieldsetPadrao">
					<ul>
						<legend>Imprimir:</legend>
						<@ww.checkbox label="Informações Sócio-Econômicas" name="configuracaoImpressaoCurriculo.exibirInformacaoSocioEconomica" labelPosition="left"/>
						<@ww.checkbox label="Formação Escolar" name="configuracaoImpressaoCurriculo.exibirFormacao" labelPosition="left"/>
						<@ww.checkbox label="Idiomas" name="configuracaoImpressaoCurriculo.exibirIdioma" labelPosition="left"/>
						<@ww.checkbox label="Conhecimentos" name="configuracaoImpressaoCurriculo.exibirConhecimento" labelPosition="left"/>
						<@ww.checkbox label="Cursos" name="configuracaoImpressaoCurriculo.exibirCurso" labelPosition="left"/>
						<@ww.checkbox label="Experiências Profissionais" name="configuracaoImpressaoCurriculo.exibirExperiencia" labelPosition="left"/>
						<@ww.checkbox label="Informações Adicionais" name="configuracaoImpressaoCurriculo.exibirInformacao" labelPosition="left"/>
						<@ww.checkbox label="Observações do RH" name="configuracaoImpressaoCurriculo.exibirObservacao" labelPosition="left"/>
						<@ww.checkbox label="Histórico" name="configuracaoImpressaoCurriculo.exibirHistorico" labelPosition="left"/>
						<@ww.checkbox label="Texto Digitalizado" name="configuracaoImpressaoCurriculo.exibirTextoOCR" labelPosition="left"/>
						<@ww.checkbox label="Extra" name="configuracaoImpressaoCurriculo.exibirCamposExtras" labelPosition="left"/>
						<@ww.checkbox label="Cabeçalho" name="configuracaoImpressaoCurriculo.exibirCabecalho" labelPosition="left"/>
						<#if mostraOpcaoSolicitacaoPessoal>
							<@ww.checkbox label="Solicitação de Pessoal" name="configuracaoImpressaoCurriculo.exibirSolicitacaoPessoal" labelPosition="left"/>
						<#else>
							<@ww.hidden name="configuracaoImpressaoCurriculo.exibirSolicitacaoPessoal" />
						</#if>
						<!--<@ww.checkbox label="Exame Palográfico" name="configuracaoImpressaoCurriculo.exibirExamePalografico" labelPosition="left"/>-->
			
						<br>
						<@ww.checkbox label="Campo para aprovação 1" name="configuracaoImpressaoCurriculo.exibirAssinatura1" labelPosition="left" onclick="habilitarCampo(this, 'ass1');"/>
						<@ww.textfield label="Assinatura 1" id="ass1" name="configuracaoImpressaoCurriculo.assinatura1" disabled="${desabilita1}" maxLength="50" cssStyle="width: 200px;"/>
						
						<@ww.checkbox label="Campo para aprovação 2" name="configuracaoImpressaoCurriculo.exibirAssinatura2" labelPosition="left" onclick="habilitarCampo(this, 'ass2');"/>
						<@ww.textfield label="Assinatura 2" id="ass2" name="configuracaoImpressaoCurriculo.assinatura2" disabled="${desabilita2}" maxLength="50" cssStyle="width: 200px;"/>
						
						<@ww.checkbox label="Campo para aprovação 3" name="configuracaoImpressaoCurriculo.exibirAssinatura3" labelPosition="left" onclick="habilitarCampo(this, 'ass3');"/>
						<@ww.textfield label="Assinatura 3" id="ass3" name="configuracaoImpressaoCurriculo.assinatura3" disabled="${desabilita3}" maxLength="50" cssStyle="width: 200px;"/>
					</ul>
				</fieldset>
			</li>
			
			
			
			<@ww.hidden name="configuracaoImpressaoCurriculo.id" />
			<@ww.hidden name="candidato.id" />
			<@ww.hidden name="solicitacao.id" />
		</@ww.form>

		<div class="buttonGroup">
			<button onclick="closebox();document.form.submit();" class="btnImprimirPdf"></button>
			<button onclick="closebox();" class="btnCancelar"></button>
		</div>
	
		</div>
		<@authz.authorize ifAllGranted="ROLE_INFORM_CANDIDATO_CURRICULO">
			<br>
				<#--p><input type="checkbox" name="imprimirHC" id="impHC" /> <label for="impHC">Imprimir HistÃ³rico</label></p>
				<p><input type="checkbox" name="imprimirAS" id="impAS" /> <label for="impAS">Imprimir Assinatura</label></p-->
				<button id="btnImprimirCurriculo" class="btnImprimirPdfB" onclick="openbox('Configurar Impressão', '');" ></button>
				<button id="btnImprimirCurriculoEscaneado" disabled='disabled' class="btnImprimirPdfB" onclick="imprimirCurriculoEscaneado();" style="display:none;"></button>
			<br>
		</@authz.authorize>
	</div>
	
	<iframe id="printFrame" name="printFrame" src="verCurriculoEscaneado.action?candidato.id=${candidato.id}&modoImpressao=true" onload="$('#btnImprimirCurriculoEscaneado').removeAttr('disabled')" style="display:none;"></iframe>
</body>
</html>