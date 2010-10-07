<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		@import url('<@ww.url value="/css/formModal.css"/>');
	</style>
	<style type="text/css">#menuComissao a.ativaReuniao{color: #FFCB03;}</style>

	<script src='<@ww.url includeParams="none" value="/js/formModal.js"/>'></script>
	<script src='<@ww.url includeParams="none" value="/js/functions.js"/>'></script>
	<script src='<@ww.url includeParams="none" value="/js/fortes.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ComissaoReuniaoDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<title></title>
	<#assign validarCampos="return validaFormulario('form',new Array('reuniaoData','reuniaoHorario','reuniaoTipo'),new Array('reuniaoData'));"/>
	<script type="text/javascript">
		function limpaForm()
		{
			resetFormulario(new Array('reuniaoData','reuniaoDesc','reuniaoHorario','reuniaoLocal','comissaoReuniaoId','reuniaoAta','reuniaoObsAnterior'));
			document.getElementById('reuniaoTipo').value  = "O";
			document.form.action="insert.action"

			// presenças
			<#list colaboradors as colab>
				colabId = ${colab.id};
				elementoCheck = document.getElementById("check"+colabId);
				elementoCheck.checked = "";
				desabilitaJustificativa(elementoCheck);
			</#list>
		}

		function preparaDadosUpdate(comissaoId)
		{
			DWREngine.setErrorHandler(errorPreparaDados);
			ComissaoReuniaoDWR.prepareDadosReuniao(carregaDados,comissaoId)
		}

		function errorPreparaDados(msg)
		{
			alert(msg);
		}

		function carregaDados(data)
		{
			//campos do form e hidden
			document.getElementById("reuniaoData").value = data.reuniaoData;
			document.getElementById("reuniaoDesc").value = data.reuniaoDesc;
			document.getElementById("reuniaoHorario").value = data.reuniaoHorario;
			document.getElementById("reuniaoLocal").value = data.reuniaoLocal;
			document.getElementById("reuniaoTipo").value = data.reuniaoTipo;
			document.getElementById("reuniaoAta").value = data.reuniaoAta;
			document.getElementById("reuniaoObsAnterior").value = data.reuniaoObsAnterior;
			document.getElementById("comissaoReuniaoId").value = data.comissaoReuniaoId;
			//action
			document.form.action="update.action";

			// populando as presenças
			<#list colaboradors as colab>
				colabId = ${colab.id};
				checado = data.check${colab.id};

				elementoCheck = document.getElementById("check"+colabId);

				if (checado)
				{
					elementoCheck.checked = "checked";

					// reseta propriedades da justificativa
					desabilitaJustificativa(elementoCheck);
				}
				else
				{
					// reseta propriedades da justificativa
					desabilitaJustificativa(elementoCheck);

					justificativa = data.justificativaId${colab.id};
				    if (justificativa != null && justificativa != 'undefined')
						document.getElementById("justificativaId"+colabId).value = data.justificativaId${colab.id};
					else
						document.getElementById("justificativaId"+colabId).value = "";
				}
			</#list>

			openbox('Editar Reunião', 'reuniaoHorario');
		}

		function desabilitaJustificativa(elementoCheck)
		{
			elemJustificativa = document.getElementById("justificativaId" + elementoCheck.value);
			elemJustificativa.readOnly = elementoCheck.checked;
			elemJustificativa.style.background=elementoCheck.checked ? "#EEE9E9" : "#FFFFFF";
			elemJustificativa.value = "";
		}

		function marcarDesmarcar(mdCheck)
		{
			var vMarcar;

			if (mdCheck.checked)
			{
				vMarcar = true;
			}
			else
			{
				vMarcar = false;
			}

			for (var i = 0; i < document.form.elements.length; i++)
			{
				var elementForm = document.form.elements[i];
				if ((elementForm != null) && (elementForm.type == 'checkbox') && (elementForm.id != 'md'))
				{
					elementForm.checked = vMarcar;
					desabilitaJustificativa(elementForm);
				}
			}
		}
		
		function validaDataReuniaoCampos()
		{
			var dataReuniao = document.getElementById("reuniaoData").value;
			ComissaoReuniaoDWR.validaDataNoPeriodoDaComissao(processaValidacao,dataReuniao,${comissao.id});
		}
		function processaValidacao(data)
		{
			if (!data)
			{
				alert('Data inválida, ou fora do período válido da comissão.');
				document.form.reuniaoData.value = '  /  /    ';
			}else
				${validarCampos};
		}
		
	</script>

	
</head>
<body>
	<#include "comissaoLinks.ftl" />
	<#include "../ftl/mascarasImports.ftl" />

	<@display.table name="comissaoReuniaos" id="comissaoReuniao" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="#" onclick="limpaForm(); preparaDadosUpdate(${comissaoReuniao.id});"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="#" onclick="if (confirm('Confirma exclusão?')) window.location='delete.action?comissaoReuniao.id=${comissaoReuniao.id}&comissao.id=${comissao.id}'"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
			<a href="imprimirAta.action?comissaoReuniao.id=${comissaoReuniao.id}&comissao.id=${comissao.id}"><img border="0" title="Imprimir ata da reunião" src="<@ww.url includeParams="none" value="/imgs/printer.gif"/>"></a>
		</@display.column>
		<@display.column property="data" title="Data" format="{0,date,dd/MM/yyyy}" style="width:70px;"/>
		<@display.column property="descricao" title="Descrição"/>
		<@display.column property="localizacao" title="Local"/>
		<@display.column property="horario" title="Horário" style="width:60px;"/>
		<@display.column property="tipoDic" title="Tipo" style="width:70px;"/>
	</@display.table>

	<div class="buttonGroup">
		<button class="btnInserir" onclick="limpaForm(); openbox('Inserir Reunião', 'reuniaoHorario');"></button>
		<#if comissaoReuniaos?size < 4 >
			<button class="btnSugerirReunioesOrdinarias" onclick="window.location='sugerirReuniao.action?comissao.id=${comissao.id}'" ></button>
		</#if>
		<button class="btnImprimirCalendario" onclick="window.location='imprimirCalendario.action?comissao.id=${comissao.id}'"></button>
		<button class="btnImprimirRelatorioFrequencia" title="Relatório de frequência" onclick="window.location='relatorioPresenca.action?comissao.id=${comissao.id}'"></button>
	</div>

	<#--
		 Modal de edição da Reunião
	-->
	<div id="filter"></div>
	<div id="box">
		<span id="boxtitle"></span>
		<@ww.form name="form" action="insert.action" method="POST">
			<@ww.datepicker label="Data" id="reuniaoData" name="comissaoReuniao.data" cssClass="mascaraData" required="true"/>
			<@ww.textfield label="Horário" id="reuniaoHorario" name="comissaoReuniao.horario" value="" maxlength="20" cssStyle="width:40px;" required="true"/>
			<@ww.textfield label="Local" id="reuniaoLocal" name="comissaoReuniao.localizacao" value="" maxlength="100" cssStyle="width:220px;"/>
			<@ww.select label="Tipo" id="reuniaoTipo" name="comissaoReuniao.tipo" list=r"#{'O':'Ordinária','E':'Extraordinária'}" required="true"/>
			<@ww.textfield label="Descrição" id="reuniaoDesc" name="comissaoReuniao.descricao" value="" maxlength="100" cssStyle="width:400px;" />
			<@ww.textarea label="Pontos pendentes da reunião anterior (ata)" id="reuniaoObsAnterior" name="comissaoReuniao.obsReuniaoAnterior" cssStyle="width: 520px;height:35px;"/>
			<@ww.textarea label="Pontos discutidos (ata)" id="reuniaoAta" name="comissaoReuniao.ata" cssStyle="width: 520px;height:50px;"/>

			<@ww.hidden name="comissaoReuniao.id" id="comissaoReuniaoId" value=""/>
			<@ww.hidden name="comissao.id" id="comissaoId" />
			Presença:<br/>
			<div style="height:130px;width:520px;overflow-y:scroll;border:1px solid #7E9DB9;">
				<@display.table name="colaboradors" id="colaborador" class="dados" style="width:500px;border:none;">
					<@display.column title="<input type='checkbox' id='md' onclick='marcarDesmarcar(this);' />" style="width: 30px; text-align: center;">
						<input type="checkbox" onclick="desabilitaJustificativa(this);" id="check${colaborador.id}" value="${colaborador.id}" name="colaboradorChecks" />
					</@display.column>
					<@display.column property="nome" title="Nome" style="width: 240px;"/>
					<@display.column title="Justificativa da ausência" style="width: 140px;text-align:center;">
				        <@ww.textfield name="justificativas" theme="simple" readonly="false" maxlength="100" id="justificativaId${colaborador.id}" cssStyle="width:160px;border:1px solid #7E9DB9;"/>
				        <@ww.hidden name="colaboradorIds" value="${colaborador.id}"/>
					</@display.column>
				</@display.table>
			</div>
		</@ww.form>

		<div class="buttonGroup">
			<button onclick="validaDataReuniaoCampos();" class="btnGravar"></button>
			<button onclick="closebox();" class="btnCancelar"></button>
		</div>
	</div>
</body>
</html>