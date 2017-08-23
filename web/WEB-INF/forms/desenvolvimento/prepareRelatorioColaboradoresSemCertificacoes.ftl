<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<@ww.head/>
	<title>Colaboradores Sem Certificações</title>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CertificacaoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
	
	<script type="text/javascript">
		$(function() {
			$('#listCheckBoxFiltercertificacoesCheck').parent().find('#mt').removeAttr('onclick').css('color', '#5C5C5A' );
			
			$('#tooltipHelpPeriodo').qtip({
				content: 'Os campos "Período Certificado" e "Com certificação a vencer em até" só serão habilitados ao marcar a opção "Certificados".', style: { width: '100px' }
			});

			populaColaborador();
			
			$('#listCheckBoxcolaboradoresCheck tbody').remove();
			$('#listCheckBoxcolaboradoresCheck').append('<tbody> <tr> <td colspan="7"> <div class="info">  <ul> <li>Utilize os filtros acima para popular os colaboradores. </br> Filtro obrigatório: "Certificações".</li> </ul> </div> </tr></td> </tbody>');
		});
		
		function submit(){
			validaFormulario('form', new Array('@certificacoesCheck'), new Array(), false, '${urlImgs}');
		}
		
		var background = document.createElement('div');
		background.style.cssText = 'position: fixed; z-index: 4000; background-color: black; opacity: 0.1; bottom: 0; right: 0; top: 0; left: 0;'

		function populaColaborador()
		{
			DWRUtil.useLoadingMessage('Carregando...');

			var areasIds = getArrayCheckeds(document.forms[0], 'areasCheck');
			var estabelecimentosIds = getArrayCheckeds(document.forms[0], 'estabelecimentosCheck');
			var certificacoesIds = getArrayCheckeds(document.forms[0], 'certificacoesCheck');
			
			document.body.appendChild(background);
			if(certificacoesIds.length > 0){
				CertificacaoDWR.getColaboradoresSemCertificacao(createListColaborador, ${empresaSistema.id}, areasIds, estabelecimentosIds, certificacoesIds, $('#situacao').val());
			} else {
				createListColaborador([]);
			}
		}
		
		function createListColaborador(data)
		{
			document.body.removeChild(background);
			$('#listCheckBoxcolaboradoresCheck tbody').remove();
			$('#listCheckBoxcolaboradoresCheck label').remove();
			if(data.length > 0)
				addChecksByCollection("colaboradoresCheck", data);
			else
				$('#listCheckBoxcolaboradoresCheck').append('<tbody> <tr> <td colspan="7"> <div class="info">  <ul> <li>Não existem colaboradores para o filtro informado acima.</li> </ul> </div> </tr></td> </tbody>');
		}
		
		function validaQtd()
		{
		    if($("input[name='certificacoesCheck']:checked").size() >= 15)
		        $("input[name='certificacoesCheck']").not(':checked').attr('disabled','disabled').parent().css('color', '#DEDEDE');
		    else
		    	$("input[name='certificacoesCheck']").removeAttr('disabled').parent().css('color', '#5C5C5A');
		}
	</script>
	
	<style type="text/css">
		.div{
			padding-top: 5px;
			padding-bottom: 5px;
		}
		#iframeZone, #messageZone{
			position: fixed;
			left: 50%;
			transform: translate(0%, 0%);
		}
		
	</style>
	
	<#include "../ftl/mascarasImports.ftl" />
	
	<#if dataIni?exists>
		<#assign dateIni = dataIni?date/>
	<#else>
		<#assign dateIni = "  /  /    "/>
	</#if>
	<#if dataFim?exists>
		<#assign dateFim = dataFim?date/>
	<#else>
		<#assign dateFim = "  /  /    "/>
	</#if>
	
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@ww.form name="form" action="imprimirColaboradoresSemCertificacoes.action" method="POST">
		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimentos" list="estabelecimentosCheckList" filtro="true" onClick="populaColaborador();"/>
		<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList" filtro="true" selectAtivoInativo="true" onClick="populaColaborador();"/>
		<@frt.checkListBox name="certificacoesCheck" label="Certificações (máx. 15 opções)" id="certificacoesCheck" list="certificacoesCheckList" filtro="true" onClick="populaColaborador();validaQtd();" required="true"/>
		<fieldset style="padding: 5px 0px 5px 5px; width: 495px;">
			<legend>Colaboradores</legend>
			<@ww.select label="Situação" name="situacao" id="situacao" list="situacaos" onchange="populaColaborador();" cssStyle="width: 500px;"/>
			<@frt.checkListBox name="colaboradoresCheck" id="colaboradoresCheck" list="colaboradoresCheckList" filtro="true"/>
		</fieldset>
		
		<@ww.select label="Agrupar por" name="agruparPor" id="agruparPor" list=r"#{'C':'Colaborador','T':'Certificação'}" cssStyle="width: 500px;"/>
	</@ww.form>
	<div class="buttonGroup">
		<button onclick="$('form[name=form]').attr('action', 'imprimirColaboradoresSemCertificacoes.action');submit();" class="btnRelatorio" ></button>
		<button onclick="$('form[name=form]').attr('action', 'imprimirColaboradoresSemCertificacoesXlS.action');submit();" class="btnRelatorioExportar" ></button>
	</div>
</body>
</html>