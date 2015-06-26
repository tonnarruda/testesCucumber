<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/indice.js?version=${versao}"/>"></script>

<#function preparaSalario model>

	<#local retorno="">

	<#local retorno>
		<@ww.select label="Tipo de Salário" name="${model}.tipoSalarioProposto" id="tipoSalario" required="true" list="tiposSalarios"  headerValue="Selecione..." headerKey="" onchange="alteraTipoSalario(this.value);calculaSalario();"/>

		<div id="valorDiv" style="display:none; _margin-top: 10px;">
			<ul>
				<@ww.textfield label="Valor" name="${model}.salarioProposto" required="true" id="salarioProposto" cssClass="currency" cssStyle="width:85px; text-align:right;" maxLength="12"/>
			</ul>
		</div>
		<div id="indiceDiv" style="display:none; _margin-top: 10px;">
			<ul>
				<@ww.select label="Índice"  name="${model}.indiceProposto.id" id="indice" list="indices" listKey="id" listValue="nome" required="true" headerValue="Selecione..." headerKey="" onchange="calculaSalario();" liClass="liLeft" />
				<@ww.textfield label="Quantidade" name="${model}.quantidadeIndiceProposto" id="quantidade" required="true" onkeypress = "return(somenteNumeros(event,'{,}'));" cssStyle="width: 40px;text-align:right;" maxLength="6" onchange="calculaSalario();"/>
			</ul>
		</div>
		<div id="valorCalculadoDiv" style="_margin-top: 10px;">
			<ul>
				<@ww.textfield label="Valor" name="salarioCalculado" id="salarioCalculado" cssStyle="width:85px; text-align:right;" disabled= "true" cssClass="currency"/>
			</ul>
		</div>

	</#local>

	<#return retorno>
</#function>
