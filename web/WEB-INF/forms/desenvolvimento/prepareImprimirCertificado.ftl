<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/TurmaDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js"/>"></script>

<@ww.head/>
	<title>Impressão de Certificado</title>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
		#text {margin:50px auto; width:500px}
		.hotspot {color:#900; padding-bottom:1px; cursor:pointer}
		#tt {position:absolute; display:block;}
		#tttop {display:block; height:5px; margin-left:5px; overflow:hidden}
		#ttcont {display:block; padding:2px 12px 3px 7px; margin-left:5px; background:#666; color:#FFF}
		#ttbot {display:block; height:5px; margin-left:5px; overflow:hidden}
	</style>
</head>
<body>
	<@ww.actionmessage />
	<#if colaboradoresCheckList?exists && colaboradoresCheckList?size!=0 >
		<#assign act="imprimirCertificado.action"/>
	<#else>
		<#assign act="getColaboradoresByFiltro.action"/>
	</#if>

	<@ww.form name="form" action="${act}" validate="true" method="POST">
		<@ww.select label="Certificado de" name="certificadoDe" id="certificadoDe" list=r"#{'T':'Treinamento','C':'Certificação'}" />

		<li>
			<@ww.div id="divTreinamento">
				<ul>
					<@ww.select label="Curso" name="curso.id" id="curso" required="true" list="cursos" listKey="id" listValue="nome"  headerKey="-1" headerValue="Selecione um Curso" cssStyle="width: 603px;"/>
					<@ww.select label="Turma" name="turma.id" id="turma" required="true" list="turmas" listKey="id" listValue="descricao" headerKey="-1" headerValue="Selecione uma Turma" cssStyle="width: 603px;"/>
				</ul>
			</@ww.div>
		</li>
		<li>
			<@ww.div id="divCertificacao" cssStyle="display:none;">
				<ul>
					<@ww.select label="Certificação" name="certificacao.id" id="certificacao" required="true" list="certificacaos" listKey="id" listValue="nome" headerKey="-1" headerValue="Selecione uma Certificação"/>
				</ul>
			</@ww.div>
		</li>

		<div class="buttonGroup">
			<button onclick="return enviaForm(1);" class="btnPesquisar"></button>
		</div>

		<#if colaboradoresCheckList?exists && colaboradoresCheckList?size!=0 >
			<@frt.checkListBox name="colaboradoresCheck" label="Colaboradores" list="colaboradoresCheckList" width="600" height="300" filtro="true" required="true"/>
 			<@ww.textfield label="Título do Certificado" name="certificado.titulo" maxlength="200" cssStyle="width: 600px;" />

			<span style="position: relative">
				Conteúdo:
				<img id="help_conteudo" style="position: absolute; top: 0px; _top: 6px; left: 70px;" src='<@ww.url value='/imgs/help.gif'/>'/>
				<@ww.textarea name="certificado.conteudo" cssStyle="width:600px;"/>
			</span>

			<fieldset style="padding: 5px 0px 5px 5px; width: 595px;">
				<legend>Assinaturas</legend>
				<@ww.textarea label="Assinatura 1" name="certificado.ass1" cssStyle="width: 296px;height: 30px;"/>
				Assinatura 2:
				<@ww.div id="divAssinaturaInstrutor">
					<@ww.checkbox label="Usar assinatura do instrutor inserido na turma" name="exibirAssinaturaDigital" id = "exibirAssinaturaDigital" labelPosition="left" onclick="abiltaOuDesabilitaCampoAssinatura2()"/>
				</@ww.div>
				<@ww.textarea name="certificado.ass2" id = "assinatura2" cssStyle="width: 296px;height: 30px;"/>
			</fieldset><br />
			
 			<@ww.textfield label="Data Completa" name="certificado.data" cssStyle="width: 296px;" />
			<@ww.select label="Tamanho do Certificado" id="certificadoTamanho" name="certificado.tamanho" list=r"#{'1':'1 por página','2':'2 por página','declaracao':'Declaração'}" />
			<@ww.checkbox label="Imprimir Moldura" name="certificado.imprimirMoldura" labelPosition="left"/>
			<@ww.checkbox label="Imprimir Logotipo" name="certificado.imprimirLogo" labelPosition="left"/>
			<div>
				<@ww.checkbox name="imprimirNotaNoVerso" theme="simple"/>
			    Imprimir Nota no Verso
				<img id="tooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: 0px;margin-top: 0px;vertical-align: top;"/>
			</div>

			<@ww.hidden name="turma.assinaturaDigitalUrl" />
			<@ww.hidden name="turma.instrutor" />

			<div class="buttonGroup">
				<button onclick="return enviaForm(2)" class="btnImprimirPdf"></button>
				<button onclick="return enviaForm(3)" class="btnImprimirVersoPdf"></button>
			</div>
			
		</#if>
	</@ww.form>
	
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
	<script type='text/javascript'>
		function mostrarCheckAssInstrutor()
		{
			if($('#certificadoDe').val() == 'T')
			{
				<#if turma?exists && turma.assinaturaDigitalUrl?exists && turma.assinaturaDigitalUrl != "">
					$('#divAssinaturaInstrutor').css('display','');
				<#else>
					$('#divAssinaturaInstrutor').css('display','none');
				</#if>
			}else
				$('#divAssinaturaInstrutor').css('display','none');
		}
		
		function abiltaOuDesabilitaCampoAssinatura2()
		{
			if ($('#exibirAssinaturaDigital').is(":checked"))
				$('#assinatura2').val('').attr('disabled', true).css('background', '#F6F6F6');
			else
				$('#assinatura2').removeAttr('disabled').css('background', '#FFFFFF')
		} 

		function enviaForm(button) {
			if(button == 1)	{
				document.form.action="getColaboradoresByFiltro.action";
				if(document.getElementById('certificadoDe').value == 'T')
					return validaFormulario('form', new Array('curso','turma'), null);
				else
					return validaFormulario('form', new Array('certificacao'), null);
			}
			else if(button == 2) {
				document.form.action="imprimirCertificado.action";
				return validaFormulario('form', new Array('@colaboradoresCheck'), null);
			}
			else if(button == 3) {
				if(document.getElementById('certificadoDe').value == 'T')
					document.form.action="imprimirCertificadoVerso.action";
				else
					document.form.action="imprimirCertificacaoVerso.action";

				return validaFormulario('form', new Array('@colaboradoresCheck'), null);
			}
		}

		function filtrarOpt(value, limpaColaboradores)	{
			if(limpaColaboradores && document.getElementById('colaboradoresCheck') != null)
				addChecks('colaboradoresCheck', null);

			if(value == "T") {
				document.getElementById('divTreinamento').style.display = "";
				document.getElementById('divCertificacao').style.display = "none";
				$('#imprimirCertificado_imprimirNotaNoVerso').removeAttr('disabled');
			}
			else if(value == "C"){
				document.getElementById('divTreinamento').style.display = "none";
				document.getElementById('divCertificacao').style.display = "";
				$('#imprimirCertificado_imprimirNotaNoVerso').attr('checked',false).attr('disabled', 'disabled');
			}
			
			mostrarCheckAssInstrutor();
		}

		$(document).ready(function() {

			$('#certificadoDe').change(function(){
				filtrarOpt($(this).val(), true);
			});
			
			$('#certificadoTamanho').change(function(){
				if ($(this).val() == 'declaracao')
					$('.btnImprimirVersoPdf').attr('disabled', 'disabled').fadeTo(100, 0.3);
				else
					$('.btnImprimirVersoPdf').removeAttr('disabled').fadeTo(100, 1);
			});

			$('#curso').change(function(){
				DWRUtil.useLoadingMessage('Carregando...');
				TurmaDWR.getTurmasFinalizadas(function(data) {
					DWRUtil.removeAllOptions("turma");
					DWRUtil.addOptions("turma", data);
				}, $(this).val());
			});

			$('#help_conteudo').qtip({
				content: 'Utilize a expressão #NOMECOLABORADOR# no local onde deve aparecer o nome do colaborador.'
				, style: { width: '100px' }
			});
			
			$('#tooltipHelp').qtip({
				content: 'A nota será impressa somente se houver apenas uma avaliação do curso com resultado registrado.'
				, style: { width: '100px' }
			});
			
			
			mostrarCheckAssInstrutor();
			abiltaOuDesabilitaCampoAssinatura2();
		});
	
	</script>
	
	<script type='text/javascript'>
		filtrarOpt('${certificadoDe}', false);
	</script>
	
</body>
</html>