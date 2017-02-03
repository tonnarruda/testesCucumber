<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/displaytag.css?version=${versao}"/>');
		#importar { display: none; }
	</style>

	<title>Importar Cadastros</title>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/EmpresaDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/OcorrenciaDWR.js?version=${versao}"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
	
	<script type="text/javascript">
	
	function dialog()
	{
		OcorrenciaDWR.getByEmpresa($('#origem').val(), function(data){
			if(data.length > 0){
				addChecks('ocorrenciaCheckFom1',data);
				
				$('#importar').dialog({title: 'Importar Cadastros',
									modal: true, 
									height: 340,
									width: 520,
									buttons: [
									    {
									        text: "Importar",
									        click: function() { 
									        			var ocorrenciaCheckFom1 = $("input[name='ocorrenciaCheckFom1']:checked");
														var ocorrenciaIds = new Array();
														$(ocorrenciaCheckFom1).each(function() {
															ocorrenciaIds.push($(this).val());
														});
														
														$('#ocorrenciaCheck').val(ocorrenciaIds);
		
														validarForm();
									        }
									    },
									    {
									        text: "Cancelar",
									        click: function() { $(this).dialog("close"); }
									    }
									] 
								});
								
			} else {
				validarForm();
			}	
		});
	}
	
	function validaTipoOcorrencia()
	{
		var possuiTipoOcorrenciaMarcado = false; 
		
		var cadastrosCheck = $("input[name='cadastrosCheck']:checked");
		$(cadastrosCheck).each(function() {
			if($(this).val() == 7)//7 é numero para Tipo de Ocorrencia
				possuiTipoOcorrenciaMarcado = true;
		});
		
		if(possuiTipoOcorrenciaMarcado)
		{
			EmpresaDWR.isAcintegra($('#origem').val(), function(acIntegra) {
				if(!acIntegra)
				{	
					EmpresaDWR.isAcintegra($('#destino').val(), function(acIntegra) {
						if(acIntegra)
							dialog();
						else
						 	validarForm();
					});
				} else
				 	validarForm();
			});
		} else
			validarForm();
	}	
	
	function validarForm()
	{ 
		valida = validaFormulario('form', new Array('origem', 'destino', '@cadastrosCheck'), null, true);
		
		if (valida && $('#origem').val() == $('#destino').val())
		{
			jAlert('Selecione empresas Origem e Destino diferentes.');
			valida = false;
		}
		
		if(valida)
			document.form.submit();
		else
			return false;
	}
	
	</script>

</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@ww.form name="form" action="importarCadastros.action" method="POST">
		<@ww.select label="Origem" required="true" name="empresaOrigem.id" id="origem" listKey="id" listValue="nome" list="empresas" cssClass="selectEmpresa" headerValue="Selecione..." headerKey=""/>
		<@ww.select label="Destino" required="true" name="empresaDestino.id" id="destino" listKey="id" listValue="nome" list="empresas" cssClass="selectEmpresa" headerValue="Selecione..." headerKey=""/>
		<@frt.checkListBox  name="cadastrosCheck" label="Cadastros" list="cadastrosCheckList" />
		<@ww.hidden id="ocorrenciaCheck" name="ocorrenciaCheck"/>
		
		<li>
			<fieldset class="fieldsetPadrao" style="width:510px;text-align:justify;">
				<legend>Atenção</legend>
				Os cargos possuem vínculos com <strong>Áreas Organizacionais, Conhecimentos, Habilidades e Atitudes</strong>. Se desejar manter esses vínculos após a importação, marque também essas opções.
				<br /><br />
				Se optar por importar cadastros de <strong>Ambiente</strong>, os mesmos serão importados para cada estabelecimento cadastrado na empresa destino e com apenas seu histórico atual.
				<br /><br />
				Cuidado ao importar o cadastro entre empresas, pois o mesmo poderá ficar <strong>duplicado</strong> caso aconteça mais de uma importação.
			</fieldset>
		</li>

	</@ww.form>
	<div id="importar" title="Importar Cadastros">
		<@ww.form name="formOcorrencia" id="formOcorrencia" action="importarCadastros.action" method="POST">
			Selecione as ocorrências que deseja Importar para o Fortes Pessoal.<br />
			<@frt.checkListBox name="ocorrenciaCheckFom1" label="Ocorrência" list="ocorrenciaCheckList" width="500" height="120" form="document.getElementById('formOcorrencia')"/>
		</@ww.form>
	</div>
	
	<div class="buttonGroup">
		<button onclick="return validaTipoOcorrencia();" class="btnGravar"> </button>
	</div>

</body>
</html>