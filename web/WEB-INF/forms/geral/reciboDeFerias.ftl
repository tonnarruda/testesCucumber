<html>
	<head>
		<@ww.head/>
		<title>Recibo de férias</title>
		
		<#include "../ftl/mascarasImports.ftl" />
		
		<script>
			function validaForm() {
				var intervaloDeDatas = $("select[name=dataCalculo]").val().split(" ");
				console.log(intervaloDeDatas);
				$("input[name=dataInicioGozo").val(intervaloDeDatas[0]);
				$("input[name=dataFimGozo").val(intervaloDeDatas[2]);
				return validaFormulario('form', new Array('mesAno'), new Array('mesAno') );
			}
		</script>
	
		<#assign validarCampos="return validaForm()"/>
	</head>
	<body>
		<@ww.actionerror />
		<@ww.actionmessage />
		
		<#if colaborador.id?exists && empresaSistema.acIntegra>
			<@ww.form name="form" action="reciboDeFerias.action" onsubmit="${validarCampos}" method="POST">
				<input type="hidden" name="dataInicioGozo"/>
				<input type="hidden" name="dataFimGozo"/>
				<@ww.select label="Data" name="dataCalculo" list="dataCalculos"/>
				<div class="buttonGroup">
					<button onclick="${validarCampos};" class="btnDownload"></button>
				</div>
			</@ww.form>
		</#if>
	</body>
</html>
