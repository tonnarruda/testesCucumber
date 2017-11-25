<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<html>
<head>

	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		@import url('<@ww.url value="/css/buttons.css"/>');
	</style>
	<#include "../ftl/mascarasImports.ftl" />
	<title>Inserir Ambiente</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
	
	<#if historicoAmbiente?exists && historicoAmbiente.data?exists>
		<#assign data = historicoAmbiente.data>
	<#else>
		<#assign data = "">
	</#if>
	
	<script type="text/javascript">
		$(function() {
			$('#md').click(function() {
				var checked = $(this).attr('checked');
				$('input[name="riscoChecks"]').each(function() { $(this).attr('checked', checked); habilitarDesabilitarCamposLinha(this); });
			});
			
			$('input[name="riscoChecks"]').click(function() {
				habilitarDesabilitarCamposLinha(this);
			});
			
			$('#cnpjCampo').hide();
		});
		
		function habilitarDesabilitarCamposLinha(campoRisco)
		{
			$(campoRisco).parent().parent().find('input, select, textarea').not(campoRisco).attr('disabled', !campoRisco.checked);
		}
		
		function submitForm(){
			if($('#localAmbiente').val() == 1){
				if(validaFormulario('form', new Array('dataHist','descricao','nome', 'estabelecimento'), new Array('dataHist'), !validaRiscosExistentes()))
					$('#form').submit();
			}
			else{
				if(validaFormulario('form', new Array('dataHist','descricao','nome', 'cnpjEstabelecimentoDeTerceiros'), new Array('dataHist', 'cnpjEstabelecimentoDeTerceiros'), !validaRiscosExistentes()))
					$('#form').submit();
			}
		}
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.form name="form" action="${formAction}" method="POST">
		<#include "includeHistoricoAmbiente.ftl" />
		<script type="text/javascript">
			marcarDesmarcar(document.form.md);
		</script>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="submitForm();" accesskey="${accessKey}"> Gravar</button>
		<button onclick="window.location='list.action'"accesskey="V">Voltar</button>
	</div>
</body>
</html>