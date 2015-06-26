<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
	<#if cat.id?exists>
		<title>Editar Ficha de Investigação de Acidente (CAT)</title>
		<#assign formAction="update.action"/>
		<#assign accessKey="A"/>
		<#assign edicao=true>
	<#else>
		<title>Inserir Ficha de Investigação de Acidente (CAT)</title>
		<#assign formAction="insert.action"/>
		<#assign accessKey="I"/>
	</#if>

	<#if cat?exists && cat.data?exists>
		<#assign data = cat.data/>
	<#else>
		<#assign data = "" />
	</#if>

	<#include "../ftl/mascarasImports.ftl" />
	
	<style type="text/css">
		#ambiente, #funcao { font-weight: bold; }
	</style>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ColaboradorDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript'>
		$(function() {
			$("#emitiuCAT , #gerouAfastamento").click(function() {
				liberaCampo($(this));
			});
			
			$("#usavaEPI").click(function() {
				liberaMultiSelect();
			});
			
			liberaCampo($("#emitiuCAT"));
			liberaCampo($("#gerouAfastamento"));
			
			liberaMultiSelect();	
			
			$(".mascaraHora").blur(function() {
				$(this).val( $(this).val().replace(/ /g, '0') );
			});
		});
		
		function liberaMultiSelect(){
			if ($("#usavaEPI").attr('checked')){
				$(".listCheckBoxContainer [name='episChecked']").removeAttr('disabled');
				$(".listCheckBoxContainer").css('background-color', '#FFF');
			} else {
				$(".listCheckBoxContainer [name='episChecked']").attr('disabled', 'disabled');
				$(".listCheckBoxContainer").css('background-color', '#ECECEC');
			}
		}
		
		function getFuncaoAmbiente(colaboradorId){
			ColaboradorDWR.findFuncaoAmbiente(colaboradorId, {
				callback: function(dados) {
					$('#ambiente').text(dados['ambienteNome']);
					$('#funcao').text(dados['funcaoNome']);
					
					$('#ambienteId').val(dados['ambienteId']);
					$('#funcaoId').val(dados['funcaoId']);
				}
			});
		}
	</script>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<#if !edicao?exists>
		<@ww.form name="formFiltro" action="filtrarColaboradores.action" method="POST">
		<li>
			<@ww.div cssClass="divInfo" cssStyle="width: 490px;">
				<ul>
					<@ww.textfield label="Nome" name="colaborador.nome" id="nomeColaborador" cssStyle="width: 300px;"/>
					<@ww.textfield label="Matrícula" name="colaborador.matricula" id="matriculaBusca" liClass="liLeft" cssStyle="width: 60px;"/>
					<@ww.textfield label="CPF" name="colaborador.pessoal.cpf" id="cpfColaborador" cssClass="mascaraCpf"/>

					<div class="buttonGroup">
						<button onclick="validaFormulario('formFiltro', null, null);" class="btnPesquisar grayBGE"></button>
						<button onclick="document.formFiltro.action='list.action';document.formFiltro.submit();" class="btnVoltar grayBGE"></button>
					</div>
				</ul>
			</@ww.div>
		</li>
		</@ww.form>
	</#if>

	<#assign validarCampos="return validaFormulario('form', new Array('colaborador', 'data', 'horario'), new Array('data'))"/>

	<#if (colaboradors?exists && colaboradors?size > 0) || (edicao?exists)>

		<@ww.form name="form" id="form" action="${formAction}" method="POST" onsubmit="${validarCampos}" validate="true" enctype="multipart/form-data">

			<#if cat.colaborador?exists>
				<b>Colaborador: ${cat.colaborador.nome}<br>
					Ambiente: <#if cat.ambienteColaborador?exists>${cat.ambienteColaborador.nome}</#if><br>
					Função: <#if cat.funcaoColaborador?exists>${cat.funcaoColaborador.nome}</#if>
				</b>
				<p></p>
				<@ww.hidden id="colaborador" name="cat.colaborador.id" />
			</#if>

			<#if (colaboradors?exists && colaboradors?size > 0)>
				<@ww.select label="Colaborador" name="cat.colaborador.id" id="colaborador" required="true" list="colaboradors" listKey="id" listValue="nomeCpfMatricula" onchange="getFuncaoAmbiente(this.value)" headerKey="" headerValue="Selecione..." cssStyle="width:502px;"/>
				Ambiente atual do Colaborador: <span id="ambiente"></span><br />
				Função atual do Colaborador: <span id="funcao"></span><br /><br clear="all"/>
			</#if>
			<@ww.hidden name="cat.ambienteColaborador.id" id="ambienteId" />
			<@ww.hidden name="cat.funcaoColaborador.id" id="funcaoId" />
			
			<@ww.datepicker label="Data de Emissão" required="true" id="data" name="cat.data" value="${data}" cssClass="mascaraData" liClass="liLeft"/>
			<@ww.textfield label="Horário" required="true" id="horario" name="cat.horario" cssStyle="width:40px;" maxLength="5" liClass="liLeft" cssClass="mascaraHora"/>
			<@ww.textfield label="Local do Acidente" id="local" name="cat.local" cssStyle="width:305px;" maxLength="100"/>
			<@ww.select label="Natureza da Lesão" name="cat.naturezaLesao.id" id="naturezaLesao" list="naturezaLesaos" listKey="id" listValue="descricao" headerKey="" headerValue="Selecione..." cssStyle="width:502px;"/>
			<@ww.textfield label="Fonte da Lesão" id="fonteLesao" name="cat.fonteLesao" cssStyle="width:245px;" maxLength="100" liClass="liLeft"/>
			<@ww.textfield label="Parte do Corpo Atingida" id = "parteAtingida" name="cat.parteAtingida" cssStyle="width:245px;" maxLength="100"/>
			<@ww.select label="Tipo de Acidente" name="cat.tipoAcidente" id="tipoAcidente" list="tipoAcidentes" cssStyle="width:334px;"  headerKey="" headerValue="Selecione..." liClass="liLeft" />
			<@ww.textfield label="Qtde de dias debitados" id="qtdDiasDebitados" name="cat.qtdDiasDebitados" cssStyle="width:25px;" maxLength="3" onkeypress="return(somenteNumeros(event,''));"/>
			<br />
			<@ww.checkbox label="" id="emitiuCAT" name="cat.emitiuCAT" theme="simple"/>
			Emitiu CAT? &nbsp; Número CAT: <@ww.textfield label="" required="true" id = "numero" name="cat.numeroCat" cssStyle="width:152px;" maxLength="20" theme="simple"/>
			<br />
			<@ww.checkbox label="" id="gerouAfastamento" name="cat.gerouAfastamento" theme="simple"/>
			Gerou afastamento? &nbsp;&nbsp; Qtde de dias afastados: <@ww.textfield label="" id="qtdDiasAfastado" name="cat.qtdDiasAfastado" cssStyle="width:25px;" maxLength="3" theme="simple"  onkeypress="return(somenteNumeros(event,''));"/>

			<@ww.checkbox label="Foi Treinado para a Função?" id="treinado" name="cat.foiTreinadoParaFuncao" labelPosition="left" />
			
			<@ww.checkbox label="Usava EPI?" id="usavaEPI" name="cat.usavaEPI" labelPosition="left"/>
			<@frt.checkListBox form="document.getElementById('form')" label="EPIs" name="episChecked" id="episChecked" list="episCheckList" filtro="true"/>
			
			<@ww.textarea label="Descrição do Acidente" name="cat.observacao" cssStyle="width:500px;" />
			<@ww.textarea label="Conclusão da Comissão" name="cat.conclusao" cssStyle="width:500px;" />
			
			<fieldset style="width:480px;">
				<@ww.checkbox label="Limitação funcional?" id="limitacaoFuncional" name="cat.limitacaoFuncional" labelPosition="left"/>
				<@ww.textarea label="Observação" name="cat.obsLimitacaoFuncional" cssStyle="width:477px;" />
			</fieldset>
			
			<br />
			
			<@ww.hidden name="cat.id" />
			<@ww.hidden name="cat.fotoUrl" />
			
	        <#if cat.fotoUrl?exists >
				<div id="divFoto">
					<table style="border:none;">
						<tr>
							<td>
								<#if cat.id?exists && cat.fotoUrl != "">
									<a title="Clique para ver a foto no tamanho original" href="<@ww.url includeParams="none" value="showFoto.action?cat.fotoUrl=${cat.fotoUrl}"/>" target="_blank">
										<img src="<@ww.url includeParams="none" value="showFoto.action?cat.fotoUrl=${cat.fotoUrl}"/>" width="300" id="fotoImg"/>
									</a>
								</#if>
							</td>
							<td>
								<@ww.checkbox label="Manter foto do acidente" name="manterFoto" onclick="$('#divFotoUpload').toggle(!this.checked);" value="true" labelPosition="left" checked="checked"/>
								<div id="divFotoUpload" style="display:none;">
									<@ww.file label="Nova foto do acidente" name="fotoAcidente" id="fotoAcidente"/>
								</div>
							</td>
						</tr>
					</table>
				</div>
	        <#else>
				<@ww.file label="Foto do acidente" name="fotoAcidente" id="fotoAcidente"/>
	        </#if>
		</@ww.form>

		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}"> </button>
			<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V"> </button>
		</div>
	</#if>

</body>
</html>