<html>
<head>
	<@ww.head/>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/fortes.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AfastamentoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>

	<script type='text/javascript'>
		function isInss(id)
		{
			AfastamentoDWR.isAfastamentoInss(verifica, id);
		}

		function verifica(inss)
		{
			elemInss = document.getElementById('inss')

			if (inss)
				elemInss.style.display="";
			else
				elemInss.style.display="none";
		}
	</script>
	
	<#include "../ftl/mascarasImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

	<#assign displayDivInss="display:none" />

	<#if colaboradorAfastamento.id?exists>
		<title>Editar Afastamento</title>
		<#assign formAction="update.action"/>
		<#assign edicao=true/>

		<#if colaboradorAfastamento.afastamento.inss>
			<#assign displayDivInss="width: 160px;" />
		</#if>

	<#else>
		<title>Inserir Afastamento</title>
		<#assign formAction="insert.action"/>
	</#if>

	<#if colaboradorAfastamento.inicio?exists >
		<#assign inicio = colaboradorAfastamento.inicio?date/>
	<#else>
		<#assign inicio = ""/>
	</#if>
	<#if colaboradorAfastamento.fim?exists>
		<#assign fim = colaboradorAfastamento.fim?date/>
	<#else>
		<#assign fim = ""/>
	</#if>

	<#assign validarCampos="return validaFormularioEPeriodo('form', new Array('tipo','inicio'), new Array('inicio','fim'))"/>
</head>
<body>
<@ww.actionerror />
<@ww.actionmessage />

	<#if !edicao?exists>
		<@ww.form name="formFiltro" action="filtrarColaboradores.action" method="POST">
		<li>
			<@ww.div cssClass="divInfo" cssStyle="width: 500px;">
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

	<#if (colaboradors?exists && colaboradors?size > 0) || (edicao?exists)>

		<@ww.form name="form" action="${formAction}" method="POST">
			<#if colaboradorAfastamento.colaborador?exists && edicao?exists>
				<h4> Colaborador: ${colaboradorAfastamento.colaborador.nome} </h4>
				<@ww.hidden name="colaboradorAfastamento.colaborador.id" />
			</#if>

			<#if (colaboradors?exists && colaboradors?size > 0)>
				<@ww.select label="Colaborador" name="colaboradorAfastamento.colaborador.id" id="colaborador" required="true" list="colaboradors" listKey="id" listValue="nomeCpfMatricula "/>
			</#if>

			<@ww.select label="Motivo" name="colaboradorAfastamento.afastamento.id" id="tipo" required="true" onchange="isInss(this.value);" list="afastamentos" listKey="id" listValue="descricao" headerKey="" headerValue="Selecione..."/>

			<@ww.div id="inss" delay="1" cssClass="divInfoSemBackground" cssStyle="width: 225px; ${displayDivInss}">
			Motivo de afastamento pelo INSS
			</@ww.div>

			<br>Período:
			<br>
			<@ww.datepicker label="Inicio" id="inicio" name="colaboradorAfastamento.inicio" value="${inicio}" required="true" cssClass="mascaraData validaDataIni" theme="simple"/>*
			a <@ww.datepicker label="Fim" id="fim"  name="colaboradorAfastamento.fim" value="${fim}" cssClass="mascaraData validaDataFim" theme="simple"/>
			<br>
			<br>
			<@ww.textfield label="CID (Classificação Internacional de Doenças)" id="cid" name="colaboradorAfastamento.cid" cssStyle="width: 80px;" maxLength="10"/>
			<@ww.textfield label="Médico" name="colaboradorAfastamento.medicoNome" cssClass="inputNome"/>
			<@ww.textfield label="CRM" name="colaboradorAfastamento.medicoCrm" maxLength="20"/>
			<@ww.textarea label="Observações" name="colaboradorAfastamento.observacao" cssStyle="width: 505px;"/>

			<@ww.hidden name="colaboradorAfastamento.id" />
			<@ww.token/>
		</@ww.form>

		<div class="buttonGroup">
			<button onclick="${validarCampos}" class="btnGravar"></button>
			<button onclick="window.location='list.action'" class="btnVoltar" accesskey="V"></button>
		</div>
	</#if>
</body>
</html>