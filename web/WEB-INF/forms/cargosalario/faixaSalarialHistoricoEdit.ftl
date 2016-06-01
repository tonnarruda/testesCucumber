<html>
<head>
<@ww.head/>
	<#if faixaSalarialHistorico.id?exists>
		<title>Editar Histórico da Faixa Salarial</title>
		<#assign formAction="update.action"/>
	<#else>
		<title>Novo Histórico da Faixa Salarial</title>
		<#assign formAction="insert.action"/>
	</#if>
	
	<script type="text/javascript">
		function validaForm(indice)
		{
			if(document.getElementById('tipo').value == indice)
			{
				return validaFormulario('form', new Array('data', 'indice', 'quantidade'), new Array('data'));
			}
			else
			{
				return validaFormulario('form', new Array('data','valor'), new Array('data', 'valor'));
			}
		}
	</script>
	
	<#include "faixaSalarialHistoricoCadastroHeadInclude.ftl" />
</head>
<body>
<@ww.actionerror />
<@ww.actionmessage />
	<@ww.form name="form" action="${formAction}" onsubmit="validaForm(${tipoAplicacaoIndice.getIndice()});" validate="true" method="POST">
		<b>Cargo:</b> ${faixaSalarialAux.cargo.nome}<br>
		<b>Faixa Salarial:</b> ${faixaSalarialAux.nome}<br><br>

		<#include "faixaSalarialHistoricoCadastroInclude.ftl" />
		<@ww.hidden name="faixaSalarialHistorico.id" />
		<@ww.hidden name="faixaSalarialHistorico.status" />
		<@ww.hidden name="faixaSalarialAux.id" />
		<@ww.token/>
	</@ww.form>

	<div class="buttonGroup">
		<button onclick="validaForm(${tipoAplicacaoIndice.getIndice()});" class="btnGravar"></button>
		<button onclick="javascript: executeLink('../faixaSalarial/prepareUpdateBack.action?faixaSalarialAux.id=${faixaSalarialAux.id}');" class="btnCancelar"></button>
	</div>
	
	<#if faixaSalarialHistorico.id?exists>
		<script type="text/javascript">
			alteraTipo(${faixaSalarialHistorico.tipo}, ${tipoAplicacaoIndice.getIndice()});
			calculaValor();
		</script>
	</#if>
</body>
</html>