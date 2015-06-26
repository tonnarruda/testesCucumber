<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>

	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.autocomplete.js"/>"></script>
	
	<#include "../ftl/mascarasImports.ftl" />

	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		@import url('<@ww.url includeParams="none" value="/css/cssYui/fonts-min.css"/>');
		@import url('<@ww.url includeParams="none" value="/css/jquery.autocomplete.css"/>');
		@import url('<@ww.url includeParams='none' value='/webwork/jscalendar/calendar-blue.css' encode='false'/>');
		
		#text {margin:50px auto; width:500px}
		#help_fardamento { cursor:pointer; margin-left:-5px; }
		#tt {position:absolute; display:block;}
		#tttop {display:block; height:5px; margin-left:5px; overflow:hidden}
		#ttcont {display:block; padding:2px 12px 3px 7px; margin-left:5px; background:#666; color:#FFF}
		#ttbot {display:block; height:5px; margin-left:5px; overflow:hidden}
	</style>


	<script type="text/javascript">
		var fabricantes = [${fabricantes}];
	
		$(document).ready(function() {
			$("#fabricante").autocomplete(fabricantes);
		
			$('#help_fardamento').qtip({
				content: 'Caso esta opção seja marcada, não é <br/>obrigatório o preenchimento dos campos <br/>Número do CA e Vencimento do CA.'
				, style: { width: '100px' }
			});
		});
	
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
	
		// remove/insere asterisco dos campos obrigatórios do histórico
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
	</script>

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
		<@ww.select label="Categoria de EPI" name="epi.tipoEPI.id" id="tipoEpi" listKey="id" listValue="nome" list="tipos" required="true" headerKey="" headerValue="Selecione..." />
		<@ww.textfield label="Nome" name="epi.nome" id="nome" cssClass="inputNome" maxLength="100" required="true" />
		<@ww.textarea label="Descrição" name="epi.descricao" id="descricao" />
		<@ww.textfield label="Nome do Fabricante" id="fabricante" name="epi.fabricante" cssClass="inputNome" maxLength="100"  required="true"/>

		<@ww.select label="Status" name="epi.ativo" id="ativo" list=r"#{true:'Ativo',false:'Inativo'}"/>
  		<@ww.checkbox theme="simple" id="fardamento" name="epi.fardamento" onclick="setCamposObrigatorios(this);" /> 
  		Fardamento
		<img id="help_fardamento" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" align="absmiddle"/>
		<@ww.hidden name="epi.id" />
		<@ww.token/>
		
		<#if !epi.id?exists>
			</BR></BR>
			<#if dataHoje?exists>
				<#assign dataEpi = dataHoje?date/>
			<#else>
				<#assign dataEpi = ""/>
			</#if>
			<#assign dataVencimento = ""/>
			<li>
				<@ww.div cssClass="divInfo" name="epiHistorico" id="epiHistorico" style="display:'none';">
					<h2>Primeiro Histórico</h2>
					<ul>
						<label for="data" class="desc"> A partir de:<span class="req" id="req">* </span></label>
						<@ww.datepicker name="epiHistorico.data" id="data" value="${dataEpi}" cssClass="mascaraData"/>
						<label for="CA" class="desc">Número do CA:<span class="req" id="requiredCA">* </span></label>
						<@ww.textfield name="epiHistorico.CA" id="CA" maxLength="20" />
						<label for="vencimentoCA" class="desc">Vencimento do CA:<span class="req" id="requiredVencimentoCA">* </span></label>
						<@ww.datepicker id="vencimentoCA" name="epiHistorico.vencimentoCA" value="${dataVencimento}" cssClass="mascaraData"/>
						<label for="atenuacao" class="desc" liClass="liLeft">Percentual de Atenuação do Risco:</label>
						<@ww.textfield id="atenuacao" name="epiHistorico.atenuacao" onkeypress="return somenteNumeros(event,'');" cssStyle="text-align: right; width: 30px;" maxLength="3" liClass="liLeft" after="%"/>
						<label for="validadeUso" class="desc">Período Recomendado de uso (em dias):<span class="req" style="display:''">* </span></label>
						<@ww.textfield id="validadeUso" name="epiHistorico.validadeUso" onkeypress="return somenteNumeros(event,'');" cssStyle="text-align: right; width: 40px;" maxLength="4"/>
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
				<#if 1 < epiHistoricos?size>
					<a href="#" onclick="newConfirm('Confirma exclusão?', function(){window.location='../epiHistorico/delete.action?epiHistorico.id=${epiHistorico.id}&epi.id=${epi.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
				<#else>
					<a href="javascript:;"><img border="0" title="Não é possível remover o único histórico do EPI" src="<@ww.url value="/imgs/delete.gif"/>"  style="opacity:0.2;filter:alpha(opacity=20);"></a>
				</#if>
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
		<button onclick="window.location='list.action'" class="btnCancelar">
		</button>
	</div>
</body>
</html>