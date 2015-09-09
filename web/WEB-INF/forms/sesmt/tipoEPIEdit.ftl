<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
<#if tipoEPI.id?exists>
	<title>Editar Categoria de EPI</title>
	<#assign formAction="update.action"/>
<#else>
	<title>Inserir Categoria de EPI</title>
	<#assign formAction="insert.action"/>
</#if>

<#assign validarCampos="return valida();"/>

<style>
	select[disabled=disabled], select[disabled] { background-color: rgb(236, 236, 236); }
</style>

</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@ww.form name="form" action="${formAction}" validate="true"  onsubmit="${validarCampos}" method="POST">
		<@ww.textfield label="Nome" name="tipoEPI.nome" id="nome" cssClass="inputNome" maxLength="100"  required="true" />
		
		<div id="wwctrl_tamanhosCheck" class="wwctrl">
			<div class="listCheckBoxContainer" style="width: 450px;">
				<div class="listCheckBoxBarra">
					<input id="listCheckBoxFilterTamanhosCheck" class="listCheckBoxFilter" title="Digite para filtrar" type="text">
					&nbsp;<span class="linkCheck" onclick="marcaTodosTamanhos(true);">Marcar todos</span> | <span class="linkCheck" onclick="marcaTodosTamanhos(false);">Desmarcar todos</span>
				</div>
				<div id="listCheckBoxtamanhosCheck" class="listCheckBox">
					<table id="tamanhosTable" class="dados">
						<#assign i=0/>
						<#list tamanhosCheckList as tamanhoCheck>
							<tr class=''>
								<td><input id='tamanhosCheck_${tamanhoCheck.id}' value='${tamanhoCheck.id}' class="tamanhosEPIs" type='checkbox' <#if tamanhoCheck.desabilitado>disabled="disabled"</#if>/></td>
								<td><label for='tamanhosCheck_${tamanhoCheck.id}'>${tamanhoCheck.nome}</label></td>
								<td>
									<select>
										<option value="true">Ativo</option>
										<option value="false">Inativo</option>
									</select>
								</td>
							<#assign i=i+1 />
						</#list>
					</table>
				</div>
			</div>
		</div>
		
		<div style="width: 450px; margin-top: 5px; font-weight: bold;">
			Os tamanhos desabilitados representam que foram utilizados em alguma solicitação de EPI.
		</div>
		
		<!-- @frt.checkListBox name="tamanhosCheck" id="tamanhosCheck" label="Tamanhos" list="tamanhosCheckList" filtro="true" selectAtivoInativo="false" / -->
		
		<@ww.hidden label="Id" name="tipoEPI.id" />
		<@ww.token/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar"></button>
		<button onclick="window.location='list.action'" class="btnCancelar"></button>
	</div>
	
	<script>
		<#list tipoEPI.tamanhoEPIs as tipoTamanhoEPI>
			$("#tamanhosCheck_${tipoTamanhoEPI.tamanhoEPIs.id}").attr("checked", "checked");
			$("#tamanhosCheck_${tipoTamanhoEPI.tamanhoEPIs.id}").parents("tr").find("select").val("${tipoTamanhoEPI.ativoString}");
		</#list>
		organizarElementosTamanhoEPIs();
	
		$('#listCheckBoxFilterTamanhosCheck').unbind('keyup').keyup(function() {
	        var texto = removerAcento( $( this ).val().toUpperCase() );
		    $( this ).parents( '.listCheckBoxContainer' ).find( ':checkbox' ).each( function() {
		    	 nomeTeste = removerAcento( $( this ).parents( 'tr' ).text().toUpperCase() );
				 $( this ).parents('tr').toggle( nomeTeste.indexOf( texto ) >= 0 );
	    	});
		});
	
		$(".tamanhosEPIs").click(function(){
			organizarElementosTamanhoEPIs();
		});
		
		function organizarElementosTamanhoEPIs() {
			$(".tamanhosEPIs").removeAttr("name");
			$(".tamanhosEPIs").parents("tr").find("select").removeAttr("name");
			$(".tamanhosEPIs").parents("tr").find("select").toggleDisabled(true);
			
			var i = 0;
			$(".tamanhosEPIs:checked").each(function(){
				$(this).attr("name","tamanhoEPIs["+i+"].tamanhoEPIs.id");
				$(this).parents("tr").find("select").attr("name","tamanhoEPIs["+i+"].ativo");
				$(this).parents("tr").find("select").toggleDisabled(false);
				i++;
			});
		}
		
		function marcaTodosTamanhos(marcarTodos) {
			if (marcarTodos)
				$(".tamanhosEPIs:visible").not(":disabled").attr("checked", "checked");
			else
				$(".tamanhosEPIs:visible").not(":disabled").removeAttr("checked");
			
			organizarElementosTamanhoEPIs();
		}
		
		function valida() {
			$(".tamanhosEPIs:disabled").addClass("disabled");
			$(".tamanhosEPIs:disabled").each(function(){
				$(this).removeAttr("disabled");
			});
			
			var valid = validaFormulario('form', new Array('nome'), null);
		
			if (!valid)
				$(".tamanhosEPIs.disabled").attr("disabled", "disabled");
				
			return validaFormulario;
		}
	</script>
</body>
</html>