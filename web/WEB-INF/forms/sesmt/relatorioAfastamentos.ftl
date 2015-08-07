<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
	<@ww.head/>
	<title>Afastamentosz</title>

	<#include "../ftl/mascarasImports.ftl" />
	<#assign urlImgs><@ww.url includeParams="none" value="/imgs/"/></#assign>

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

	<script type='text/javascript'>
		function submeterAction(action){
			$('form[name=form]').attr('action', action);
			return validaFormularioEPeriodo('form', null, new Array('inicio','fim'));
		}
		
		function exibirBtnRelatorio(){
			var exibirTempoServico = $('#exibirTempoServico').is(':checked');
			//$('.btnRelatorio').disabled( !exibirTempoServico );
			if (exibirTempoServico){
				$('#btnRelatorioPDF').attr("disabled", "disabled");
				$('#btnRelatorioPDF').removeClass('btnRelatorio');
				$('#btnRelatorioPDF').addClass('btnRelatorioDesabilitado').css("cursor", "default");
			}else{
				$('#btnRelatorioPDF').removeAttr("disabled");
				$('#btnRelatorioPDF').removeClass('btnRelatorioDesabilitado');
				$('#btnRelatorioPDF').addClass('btnRelatorio').css("cursor", "pointer");
			}
		}
	</script>

</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />

	<@ww.form name="form" id="form" action="relatorioAfastamentos.action" method="POST">
		Período:<br>
		<@ww.datepicker name="colaboradorAfastamento.inicio" id="inicio" value="${inicio}" liClass="liLeft" cssClass="mascaraData validaDataIni"/>
		<@ww.label value="a" liClass="liLeft" />
		<@ww.datepicker name="colaboradorAfastamento.fim" id="fim" value="${fim}" cssClass="mascaraData validaDataFim" />

		<@frt.checkListBox name="estabelecimentosCheck" id="estabelecimentosCheck" label="Estabelecimento" list="estabelecimentosCheckList" filtro="true"/>
		<@frt.checkListBox name="areasCheck" label="Área Organizacional" list="areasCheckList" filtro="true" selectAtivoInativo="true"/>

		<@ww.textfield label="Colaborador" name="nomeBusca" id="nomeBusca" cssStyle="width: 260px;"/>
		<@ww.select label="Motivo" name="colaboradorAfastamento.afastamento.id" id="tipo" required="true" list="afastamentos" listKey="id" listValue="descricao" headerKey="" headerValue="Todos"/>
		
		<@ww.select label="Ordenar por" name="ordenaColaboradorPorNome" id="ordenaColaboradorPorNome" list=r"#{true:'Nome',false:'Data'}" required="true"/>

		<@ww.select label="INSS" name="afastadoPeloINSS" id="afastadoPeloINSS" list=r"#{'T':'Todos','A':'Afastados','N':'Não afastados'}" required="true"/>
		<@ww.select label="Agrupar por" id="agruparPor" name="agruparPor" list=r"#{'N':'Sem Agrupamento','C':'CID','M':'Mês','O':'Colaborador','A':'Área Organizacional'}" />
		
		<@ww.checkbox label="exibir tempo de serviço" id="exibirTempoServico" name="exibirTempoServico" labelPosition="left" onchange="exibirBtnRelatorio();" />

		<div class="buttonGroup">
				<button id='btnRelatorioPDF' onclick="return submeterAction('relatorioAfastamentos.action');" class="btnRelatorio" ></button>
				<button onclick="return submeterAction('relatorioAfastamentosXls.action');" class="btnRelatorioExportar"></button>
		</div>
	</@ww.form>
</body>
</html>