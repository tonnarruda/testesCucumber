<script type="text/javascript">
	$(function() {
		$('#md').click(function() {
			var checked = $(this).attr('checked');
			$('input[name="riscoChecks"]').each(function() { $(this).attr('checked', checked); habilitarDesabilitarCamposLinha(this); });
		});
		
		$('input[name="riscoChecks"]').click(function() {
			habilitarDesabilitarCamposLinha(this);
		});
		
		var urlFind = "<@ww.url includeParams="none" value="/geral/codigoCBO/find.action"/>";
			
		$("#descricaoCBO").autocomplete({
			source: ajaxData(urlFind),				 
			minLength: 2,
			select: function( event, ui ) { 
				$("#codigoCBO").val(ui.item.id);
			}
		}).data( "autocomplete" )._renderItem = renderData;

		$('#descricaoCBO').focus(function() {
		    $(this).select();
		});
		
		setAjudaESocial('Estamos nos adequando as exigências impostas pelo Governo Federal para atender as normas do eSocial.<br><br>'+
			 'Desta forma, a partir da versão <strong>1.1.185.217</strong>, o histórico da função passa a contemplar as informações de '+
			 '<strong>Nome da Função</strong> e <strong>CBO</strong>.',
				 '<@ww.url value="/imgs/esocial.png"/>', 'imgAjudaEsocial');
									
									
									
		<#if exibeDialogAJuda>
			dialogAjudaESocial();
			UsuarioAjudaESocialDWR.saveUsuarioAjuda(${usuarioLogado.id}, "${telaAjuda?string}");
		</#if>
		
	});
		
	function habilitarDesabilitarCamposLinha(campoRisco)
	{
		$(campoRisco).parent().parent().find('input, select, textarea').not(campoRisco).attr('disabled', !campoRisco.checked);
	}
</script>

<#if historicoFuncao?exists && historicoFuncao.data?exists>
	<#assign data = historicoFuncao.data?date>
<#else>
	<#assign data = "">
</#if>

<@ww.datepicker label="A partir de" name="historicoFuncao.data" id="dataHist" required="true" value="${data}" cssClass="mascaraData"/>
<@ww.textfield label="Nome da Função" name="historicoFuncao.funcaoNome" id="funcaoNome" cssClass="inputNome" maxLength="100" required="true" cssStyle="width: 500px;"/>

<@ww.textfield label="Cód. CBO" name="historicoFuncao.codigoCbo" id="codigoCBO" onkeypress="return(somenteNumeros(event,''));" cssStyle="margin-top: 1px" size="6"  maxLength="6" liClass="liLeft" required="true"/>
<@ww.textfield label="Busca CBO (Código ou Descrição)" name="descricaoCBO" id="descricaoCBO" cssStyle="width: 414px;"/>
<div style="clear:both"></div></br>

<@ww.textarea label="Descrição das Atividades Executadas pela Função" name="historicoFuncao.descricao" id="descricao" cssClass="inputNome"  required="true" cssStyle="width: 495px;"/>
<@frt.checkListBox label="Exames Obrigatórios (SESMT)" name="examesChecked" id="exame" list="examesCheckList" filtro="true"/>
<@frt.checkListBox label="EPIs (PPRA)" name="episChecked" id="epi" list="episCheckList" filtro="true"/>
<label>Treinamentos (Ordem de Serviço):  
	<img class="tooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-bottom: -3px;" />
</label>
<@frt.checkListBox name="cursosChecked" id="curso" list="cursosCheckList" filtro="true"/>

<label>Normas Internas (Ordem de Serviço):  
	<img class="tooltipHelp" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-bottom: -3px;" />
</label>
<@ww.textarea name="historicoFuncao.normasInternas" id="normasInternas" cssStyle="width: 495px;"/>

<#assign i = 0/>
Riscos existentes:<br>
<@display.table name="riscosFuncoes" id="riscoFuncao" class="dados" style="width:100%;border:none;">
	<@display.column title="<input type='checkbox' id='md'/>" style="width: 30px; text-align: center;">
		<input type="checkbox" id="check${riscoFuncao.risco.id}" value="${riscoFuncao.risco.id}" name="riscoChecks" />
	</@display.column>
	<@display.column property="risco.descricao" title="Risco"/>
	<@display.column property="risco.descricaoGrupoRisco" title="Tipo" style="width: 240px;"/>
	<@display.column title="EPI Eficaz" style="width: 60px;text-align:center;">
		<#if riscoFuncao.risco.epiEficaz == true> 
			Sim
		<#else>
			NA 
		</#if>
	</@display.column>
	<@display.column title="Periodicidade" style="text-align:center;">
		<@ww.select name="riscosFuncoes[${i}].periodicidadeExposicao" id="perExposicao${riscoFuncao.risco.id}" headerKey="" headerValue="Selecione" list=r"#{'C':'Contínua','I':'Intermitente','E':'Eventual'}" disabled="true"/>
	</@display.column>
	<@display.column title="EPC Eficaz" style="width: 60px;text-align:center;">
		<@ww.checkbox id="epcEficaz${riscoFuncao.risco.id}" name="riscosFuncoes[${i}].epcEficaz" disabled="true"/>
		<@ww.hidden name="riscosFuncoes[${i}].risco.id"/>
	</@display.column>
	<@display.column title="Medida de Segurança">
		<@ww.textarea theme="simple" id="medidaDeSeguranca${riscoFuncao.risco.id}" name="riscosFuncoes[${i}].medidaDeSeguranca" cssStyle="width: 260px;height: 50px;" disabled="true"/>
	</@display.column>
	<#assign i = i + 1/>
</@display.table>
