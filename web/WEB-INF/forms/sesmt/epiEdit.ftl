<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>

	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js"/>"></script>
	<#include "../ftl/mascarasImports.ftl" />

	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
		#text {margin:50px auto; width:500px}
		.hotspot {color:#900; padding-bottom:1px; cursor:pointer}
		#tt {position:absolute; display:block;}
		#tttop {display:block; height:5px; margin-left:5px; overflow:hidden}
		#ttcont {display:block; padding:2px 12px 3px 7px; margin-left:5px; background:#666; color:#FFF}
		#ttbot {display:block; height:5px; margin-left:5px; overflow:hidden}
	</style>

<script language='javascript'>

	function validarEpi() {
		fardamentoCheck = document.getElementById('fardamento');
		if (document.getElementById('validadeUso')) {
			if (fardamentoCheck.checked)
				return validaFormulario('form', new Array('nome','tipoEpi','fabricante','data','validadeUso'), new Array('vencimentoCA','data'));
			else
				return validaFormulario('form', new Array('nome','tipoEpi','fabricante','CA','vencimentoCA','validadeUso','data'), new Array('vencimentoCA', 'validadeUso', 'data'));
		}
		else {
			return validaFormulario('form', new Array('nome','tipoEpi','fabricante'), null);
		}
	}

	// tira/coloca asterisco dos campos obrigatórios do histórico
	function setCamposObrigatorios(check) {
		divHistorico = document.getElementById('epiHistorico');
		disp = '';
		if (check.checked) disp = 'none';

		requiredCA=document.getElementById('requiredCA');
		if (requiredCA != null && requiredCA != 'undefined') {
			requiredCA.style.display=disp;
			document.getElementById('requiredVencimentoCA').style.display=disp;
		}
	}
	
	jQuery(document).ready(function() {

			jQuery('#help_fardamento').qtip({
				content: 'Caso esta opção seja marcada, não é <br/>obrigatório o preenchimento dos campos <br/>Número do CA e Vencimento do CA.'
				, style: { width: '100px' }
			});
	});
</script>

<@ww.head/>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css"/>');
</style>

	<#assign validarCampos="return validarEpi()"/>

	<#assign accessKey="G"/>
	<#if epi.id?exists>
		<title>Editar EPI</title>
		<#assign formAction="update.action"/>
	<#else>
		<title>Inserir EPI</title>
		<#assign formAction="insert.action"/>
	</#if>

</head>
<body>
	<@ww.actionerror />
	<@ww.actionmessage />
	<@ww.form name="form" action="${formAction}" onsubmit="${validarCampos}" method="POST">
		<@ww.textfield label="Nome" name="epi.nome" id="nome" cssClass="inputNome" maxLength="100" required="true" />
		<@ww.select label="Categorias de EPI" name="epi.tipoEPI.id" id="tipoEpi" listKey="id" listValue="nome" list="tipos" required="true" headerKey="" headerValue="Selecione..." />
		<@ww.textfield label="Nome do Fabricante" id="fabricante" name="epi.fabricante" cssClass="inputNome" maxLength="100"  required="true"/>

  		<@ww.checkbox label="Fardamento" id="fardamento" name="epi.fardamento" labelPosition="left" onclick="setCamposObrigatorios(this);" />
		<span style="position: absolute; top: 186px; _top: 6px; left: 128px;" id="help_fardamento" class="hotspot" ><img src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" /></span>
		<@ww.hidden name="epi.id" />
		<@ww.token/>

		<#if !epi.id?exists>
			<#if dataHoje?exists>
				<#assign dataEpi = dataHoje?date/>
			<#else>
				<#assign dataEpi = ""/>
			</#if>
			<#assign dataVencimento = ""/>
			<li>
				<@ww.div cssClass="divInfo" name="epiHistorico" id="epiHistorico" style="display:'none';">
					<ul>
						<h2>Primeiro Histórico</h2>
						<label for="data" class="desc"> A partir de:<span class="req" id="req" style="display:''">* </span>
						<@ww.datepicker name="epiHistorico.data" id="data" value="${dataEpi}" cssClass="mascaraData"/>
						<label for="CA" class="desc">Número do CA:<span class="req" id="requiredCA" style="display:''">* </span>
						<@ww.textfield name="epiHistorico.CA" id="CA" maxLength="20" />
						<label for="vencimentoCA" class="desc">Vencimento do CA:<span class="req" id="requiredVencimentoCA" style="display:''">* </span>
						<@ww.datepicker id="vencimentoCA" name="epiHistorico.vencimentoCA" value="${dataVencimento}" cssClass="mascaraData"/>
						<label for="atenuacao" class="desc" liClass="liLeft">Percentual de Atenuação do Risco:
						<br>
						<@ww.textfield id="atenuacao" name="epiHistorico.atenuacao" onkeypress="return(somenteNumeros(event,''));" cssStyle="text-align: right; width: 30px;" maxLength="3" liClass="liLeft"/>
						<@ww.label value="%" />
						<label for="validadeUso" class="desc">Período Recomendado de uso (em dias):<span class="req" style="display:''">* </span>
						<@ww.textfield id="validadeUso" name="epiHistorico.validadeUso" onkeypress="return(somenteNumeros(event,''));" cssStyle="text-align: right; width: 40px;" maxLength="4"/>
					</ul>
				</@ww.div>
			</li>
		</#if>

	</@ww.form>
	<div class="buttonGroup">
		<button onclick="${validarCampos}" class="btnGravar" accesskey="${accessKey}"></button>

	<#if epi.id?exists>
		</div>
		<br/><br/>
		<h4>Históricos deste EPI</h4>
		<@display.table name="epiHistoricos" id="epiHistorico" pagesize=10 class="dados" defaultsort=2 sort="list" defaultorder="descending">
			<@display.column title="Ações" class="acao">
				<a href="../epiHistorico/prepareUpdate.action?epiHistorico.id=${epiHistorico.id}&epi.id=${epi.id}&epi.nome=${epi.nome}"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
				<a href="#" onclick="if (confirm('Confirma exclusão?')) window.location='../epiHistorico/delete.action?epiHistorico.id=${epiHistorico.id}&epi.id=${epi.id}'"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
			</@display.column>
			<@display.column property="data" title="A partir de" style="width:140px;" format="{0,date,dd/MM/yyyy}"/>
			<@display.column property="atenuacao" title="Percentual Atenuação" style="width:40px;"/>
			<@display.column property="vencimentoCA" title="Vencimento do CA" style="width:100px;" format="{0,date,dd/MM/yyyy}"/>
			<@display.column property="validadeUso" title="Período Recomendado de uso" style="width:60px;"/>
			<@display.column property="CA" title="Número CA" style="width:60px;"/>
		</@display.table>
	<div class="buttonGroup">
		<button onclick="window.location='../epiHistorico/prepareInsert.action?epi.id=${epi.id}'" class="btnInserir_Historico" accesskey="I">
		</button>
	</#if>
		<button onclick="window.location='list.action'" class="btnVoltar" accesskey="V">
		</button>
	</div>
</body>
</html>