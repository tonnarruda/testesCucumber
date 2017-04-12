<html>
	<head>
		<@ww.head/>
		<title>Recibo de Pagamento - Férias</title>
		
		<#include "../ftl/mascarasImports.ftl" />
		<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>
		
		<style type="text/css">
			.info{height: 50px !important;padding: 30px 8px 0px 60px;}
		</style>
		
		<script>
			function validaForm() {
				var intervaloDeDatas = $("select[name=dataCalculo]").val().split(" ");
				console.log(intervaloDeDatas);
				$("input[name=dataInicioGozo]").val(intervaloDeDatas[0]);
				$("input[name=dataFimGozo]").val(intervaloDeDatas[2]);
				return validaFormulario('form', new Array('dataCalculo'), new Array('dataCalculo'), false, '${urlImgs}');
			}
		</script>
	
		<#assign validarCampos="return validaForm()"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.actionmessage />
		
		<#if colaborador.id?exists && empresaSistema.acIntegra && colaborador.codigoAC?exists>
			<#if dataCalculos?exists && 0 < dataCalculos?size >
				<@ww.form name="form" action="reciboDeFerias.action" onsubmit="${validarCampos}" method="POST">
				
					<input type="hidden" name="dataInicioGozo"/>
					<input type="hidden" name="dataFimGozo"/>
					<@ww.select label="Data" name="dataCalculo" id="dataCalculo" list="dataCalculos"/>
					<div class="buttonGroup">
						<button onclick="${validarCampos};" class="btnDownload"></button>
					</div>
				</@ww.form>
			<#else>
				<div class="info">
					<ul>
						Não existe recibo de férias para o colaborador no Fortes Pessoal.
					</ul>
				</div>
			</#if>
		</#if>
	</body>
</html>
