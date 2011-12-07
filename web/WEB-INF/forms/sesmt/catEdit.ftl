<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
<#if cat.id?exists>
	<title>Editar Ficha de Investigação de Acidente(CAT)</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
	<#assign edicao=true>
<#else>
	<title>Inserir Ficha de Investigação de Acidente(CAT)</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>

	<#if cat?exists && cat.data?exists>
		<#assign data = cat.data/>
	<#else>
		<#assign data = "" />
	</#if>

	<#include "../ftl/mascarasImports.ftl" />
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

	<#assign validarCampos="return validaFormulario('form', new Array('data', 'horario'), new Array('data'))"/>

	<#if (colaboradors?exists && colaboradors?size > 0) || (edicao?exists)>

		<@ww.form name="form" id="form" action="${formAction}" method="POST" onsubmit="${validarCampos}" validate="true">

			<#if cat.colaborador?exists>
				<b>Colaborador: ${cat.colaborador.nome}</b>
				<p></p>
				<@ww.hidden name="cat.colaborador.id" />
			</#if>

			<#if (colaboradors?exists && colaboradors?size > 0)>
				<@ww.select label="Colaborador" name="cat.colaborador.id" id="colaborador" required="true" list="colaboradors" listKey="id" listValue="nomeCpfMatricula" cssStyle="width:502px;"/>
			</#if>
			<@ww.datepicker label="Data de Emissão" required="true" id="data" name="cat.data" value="${data}" cssClass="mascaraData" liClass="liLeft"/>
			<@ww.textfield label="Horário" required="true" id="horario" name="cat.horario" cssStyle="width:40px;" maxLength="5" liClass="liLeft" cssClass="mascaraHora"/>
			<@ww.select label="Local do Acidente (Ambiente)" name="cat.ambiente.id" id="ambiente" list="ambientes" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." cssStyle="width:307px;"/>
			<@ww.select label="Natureza da Lesão" name="cat.naturezaLesao.id" id="naturezaLesao" list="naturezaLesaos" listKey="id" listValue="descricao" headerKey="" headerValue="Selecione..." cssStyle="width:502px;"/>
			<@ww.textfield label="Fonte da Lesão" id="fonteLesao" name="cat.fonteLesao" cssStyle="width:160px;" maxLength="100"/>
			<@ww.textfield label="Parte do Corpo Atingida" id = "parteAtingida" name="cat.parteAtingida" cssStyle="width:160px;" maxLength="100" liClass="liLeft"/>
			<@ww.select label="Tipo de Acidente" name="cat.tipoAcidente" id="tipoAcidente" list="tipoAcidentes" cssStyle="width:334px;"  headerKey="" headerValue="Selecione..." />

			<@ww.checkbox label="Foi Treinado para a Função?" id="treinado" name="cat.foiTreinadoParaFuncao" labelPosition="left" />
			
			<@ww.checkbox label="Usava EPI?" id="usavaEPI" name="cat.usavaEPI" labelPosition="left"/>
			<@frt.checkListBox form="document.getElementById('form')" label="EPIs" name="episChecked" id="episChecked" list="episCheckList"/>
			
			<@ww.checkbox label="" id="gerouAfastamento" name="cat.gerouAfastamento" theme="simple"/>
			Gerou Afastamento? / Qtd. de dias Afastados: <@ww.textfield label="" id="qtdDiasAfastado" name="cat.qtdDiasAfastado" cssStyle="width:25px;" maxLength="3" theme="simple"  onkeypress="return(somenteNumeros(event,''));"/>
			
			<br>
			<@ww.checkbox label="" id="emitiuCAT" name="cat.emitiuCAT" theme="simple"/>
			Emitiu CAT? / Número CAT: <@ww.textfield label="" required="true" id = "numero" name="cat.numeroCat" cssStyle="width:152px;" maxLength="20"  theme="simple"/>

			<@ww.textarea label="Descrição do Acidente" name="cat.observacao" cssStyle="width:500px;" />
			<@ww.textarea label="Conclusão da Comissão" name="cat.conclusao" cssStyle="width:500px;" />
			
			<@ww.hidden label="Id" name="cat.id" />
		</@ww.form>

		<div class="buttonGroup">
			<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}"> </button>
			<button onclick="window.location='list.action'" class="btnCancelar" accesskey="V"> </button>
		</div>
	</#if>

</body>
</html>