<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<@ww.head/>
<#if cat.id?exists>
	<title>Editar Comunicação de Acidente de Trabalho</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
	<#assign edicao=true>
<#else>
	<title>Inserir Comunicação de Acidente de Trabalho</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>

	<#if cat?exists && cat.data?exists>
		<#assign data = cat.data/>
	<#else>
		<#assign data = "" />
	</#if>

	<#include "../ftl/mascarasImports.ftl" />
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

	<#assign validarCampos="return validaFormulario('form', new Array('data','numero'), new Array('data'))"/>

	<#if (colaboradors?exists && colaboradors?size > 0) || (edicao?exists)>

		<@ww.form name="form" action="${formAction}" method="POST" onsubmit="${validarCampos}" validate="true">

			<#if cat.colaborador?exists>
				<b>Colaborador: ${cat.colaborador.nome}</b>
				<p></p>
				<@ww.hidden name="cat.colaborador.id" />
			</#if>

			<#if (colaboradors?exists && colaboradors?size > 0)>
				<@ww.select label="Colaborador" name="cat.colaborador.id" id="colaborador" required="true" list="colaboradors" listKey="id" listValue="nomeCpfMatricula" cssStyle="width:502px;"/>
			</#if>
			<@ww.datepicker label="Data de Emissão" required="true" id="data" name="cat.data" value="${data}" cssClass="mascaraData" liClass="liLeft"/>
			<@ww.textfield label="Horário" id="horario" name="cat.horario" cssStyle="width:40px;" maxLength="5" liClass="liLeft" cssClass="mascaraHora"/>
			<@ww.select label="Local do Acidente" name="cat.ambiente.id" id="ambiente" list="ambientes" listKey="id" listValue="nome" cssStyle="width:316px;"/>
			<@ww.select label="Natureza da Lesão" name="cat.naturezaLesao.id" id="naturezaLesao" list="naturezaLesaos" listKey="id" listValue="descricao" cssStyle="width:502px;"/>
			<@ww.textfield label="Parte do Corpo Atingida" id = "parteAtingida" name="cat.parteAtingida" cssStyle="width:160px;" maxLength="100" liClass="liLeft"/>
			<@ww.select label="Tipo de Acidente" name="cat.tipoAcidente" id="tipoAcidente" list="tipoAcidentes" cssStyle="width:334px;"  headerKey="" headerValue=""/>

			<@ww.checkbox label="Foi Treinado para a Função?" id="treinado" name="cat.foiTreinadoParaFuncao" labelPosition="left" />
			
			<@ww.checkbox label="Usava EPI?" id="usavaEPI" name="cat.usavaEPI" labelPosition="left"/>
			<@frt.checkListBox label="EPIs" name="episChecked" id="epi" list="episCheckList"/>
			
			<@ww.checkbox label="Gerou Afastamento?" id="afastamento" name="cat.gerouAfastamento" labelPosition="left" liClass="liLeft"/>
			<@ww.textfield label="Quantidade de Dias Afastados" id="qtdDiasAfastado" name="cat.qtdDiasAfastado" cssStyle="width:40px;" maxLength="3"/>

			<@ww.checkbox label="Emitiu CAT?" id="emitiuCAT" name="cat.emitiuCAT" labelPosition="left" />
			<@ww.textfield label="Número CAT" required="true" id = "numero" name="cat.numeroCat" cssStyle="width:145px;" maxLength="20"/>
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