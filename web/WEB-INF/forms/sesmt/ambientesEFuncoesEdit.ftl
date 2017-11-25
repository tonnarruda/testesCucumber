<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	</style>
	<title>Ambientes e Funções do Colaborador</title>

	<#if colaboradors?exists>
		<#assign headerValue="Selecione..."/>
	<#else>
		<#assign headerValue="Utilize o Filtro acima."/>
	</#if>

	<script type="text/javascript">
		function listSelect(colaboradorId)
		{
			document.formBusca.submit();
		}

		function list()
		{
			document.getElementById("colab").value = null;
			
			document.formBusca.submit();
		}
	</script>
	
	<style type="text/css">
		.dados td
		{
			padding: 2px 2px 2px 2px !important;
			margin-left:0px !important;
			margin-right:0px !important;
			vertical-align:middle !important;
		}
	</style>
</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />

	<@ww.form name="formBusca" action="prepareUpdateAmbientesEFuncoes.action" onsubmit="list();" method="POST">
		<li>
			<@ww.div cssClass="divInfo" cssStyle="width: 950px;">
				<ul>
					<@ww.textfield label="Nome" name="colaborador.nome" id="nome" cssClass="inputNome" maxLength="100" cssStyle="width: 400px;"/>
					<@ww.textfield label="CPF" id="cpf" name="colaborador.pessoal.cpf" liClass="liLeft" maxLength="11" onkeypress="return(somenteNumeros(event,''));" cssStyle="width:150px;"/>
					<@ww.textfield label="Matrícula" id="matricula" name="colaborador.matricula" cssStyle="width:150px;"  maxLength="20"/>
					<@ww.select label="Situação" name="situacao" id="situacao" list="situacaos" cssStyle="width: 310px;"/>

					<input type="submit" value="" class="btnPesquisar grayBGDivInfo" />

					<br><br>
					<@ww.select label="Colaborador" name="colaborador.id" id="colab" list="colaboradors" listKey="id" listValue="nomeCpf" headerKey="" headerValue="${headerValue}" onchange="listSelect(this.value);" cssStyle="width: 930px;" />
				</ul>
			</@ww.div>
		</li>
	</@ww.form>

	<#if colaborador?exists && colaborador.id?exists>
		<h4>Colaborador: ${colaboradorNome}</h4>
		
		<@ww.form name="form" action="updateAmbientesEFuncoes.action" method="POST">
		
			<@ww.hidden name="colaborador.id" />
		
			<#assign i = 0/>
			<@display.table name="historicoColaboradors" id="historico" class="dados">
				<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="text-align: center; width:50px;"/>
				
				<@display.column property="estabelecimento.nome" title="Estabelecimento" style="width:120px;"/>
				<@display.column property="faixaSalarial.descricao" title="Cargo" style="width:120px;"/>
				<@display.column property="areaOrganizacional.nome" title="Area Organizacional" style="width:120px;"/>
								
				<@display.column title="Função" style="width:160px;">
					<@ww.select label="" theme="simple" name="historicoColaboradors[${i}].funcao.id" list="historicoColaboradors[${i}].funcoes" listKey="id" listValue="nome" headerKey="" headerValue="Selecione..." cssStyle="width:200px; margin-right: -60px !important"/>
					<@ww.hidden name="historicoColaboradors[${i}].id" />
				</@display.column>
				
				
				<@display.column title="Ambiente" style="width:160px;">
					<@frt.selectOpGroup id="ambiente" label="" theme="simple" name="historicoColaboradors[${i}].ambiente.id" map="historicoColaboradors[${i}].mapAmbientes" optionValue="id" optionText="nome" cssStyle="width:200px; margin-right: -60px !important"/>
				</@display.column>
				
				<#assign i = i + 1/>
			</@display.table>
			
			<div class="buttonGroup">
				<button onclick="document.form.submit();" class="btnGravar"></button>
			</div>
			
		</@ww.form>
	</#if>

</body>
</html>