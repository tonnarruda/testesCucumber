<meta http-equiv="Cache-Control" content="no-cache, no-store" />
<meta http-equiv="Pragma" content="no-cache, no-store" />
<meta http-equiv="Expires" content="0" />

<style type="text/css">
li {
	list-style-type: none;
}
</style>

<#if gastoEmpresaItem.id?exists>
	<#assign formAction="update.action"/>
	<#assign buttonLabel="Editar Item"/>
<#else>
	<#assign formAction="insert.action"/>
	<#assign buttonLabel="Inserir Item"/>
</#if>

<#assign validarCamposItem="validaFormulario('frmItem', new Array('valor', 'investimento'), null, true)"/><h3>:: ${buttonLabel}</h3>
<@ww.actionerror />
<@ww.actionmessage />
<@ww.form name="frmItem" id="frmItem" action="/geral/gastoEmpresaItem/${formAction}" method="POST" onsubmit="${validarCamposItem}">
		<@ww.select label="Investimento" id="investimento" required="true" name="gastoEmpresaItem.gasto.id" list="gastos" liClass="liLeft" listKey="id" listValue="nome" headerValue="Selecione..." headerKey="" theme="css_xhtml" />
		<@ww.textfield label="Valor" id="valor"  required="true" cssClass="currency" cssStyle="text-align:right; width:85px;" name="gastoEmpresaItem.valor" maxLength="12" theme="css_xhtml" />
		<@ww.hidden name="gastoEmpresaItem.id" />
		<div class="buttonGroup">
			<@ww.submit value=" " cssClass="btnGravar grayBG"/>
		</div>
</@ww.form>
<button onclick="voltarItem()" class="btnListarItens grayBG">
</button>
<br>