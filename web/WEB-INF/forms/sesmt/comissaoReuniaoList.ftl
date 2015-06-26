<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
<@ww.head/>
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
		@import url('<@ww.url value="/css/formModal.css?version=${versao}"/>');
	</style>
	<style type="text/css">#menuComissao a.ativaReuniao{border-bottom: 2px solid #5292C0;}</style>

	<script src='<@ww.url includeParams="none" value="/js/formModal.js?version=${versao}"/>'></script>
	<script src='<@ww.url includeParams="none" value="/js/functions.js?version=${versao}"/>'></script>
	<script src='<@ww.url includeParams="none" value="/js/fortes.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/ComissaoReuniaoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<title></title>
	<#assign validarCampos="return validaFormulario('form',new Array('reuniaoData','reuniaoHorario','reuniaoTipo'),new Array('reuniaoData'));"/>
	<script type="text/javascript">
		function limpaForm()
		{
			resetFormulario(new Array('reuniaoData','reuniaoDesc','reuniaoHorario','reuniaoLocal','comissaoReuniaoId','reuniaoAta','reuniaoObsAnterior'));
			document.getElementById('reuniaoTipo').value  = "O";
			document.form.action="insert.action"

			$('#colaborador tbody').empty();
		}

		function errorPreparaDados(msg)
		{
			jAlert(msg);
		}
		
		function desabilitaJustificativa(elementoCheck)
		{
			elemJustificativa = document.getElementById("justificativaId" + elementoCheck.value);
			elemJustificativa.readOnly = elementoCheck.checked;
			elemJustificativa.style.background=elementoCheck.checked ? "#EEE9E9" : "#FFFFFF";
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
				jAlert('Data inválida, ou fora do período válido da comissão.');
				document.form.reuniaoData.value = '  /  /    ';
			}else
				${validarCampos};
		}

		function preparaDadosUpdate(comissaoId)
		{
			DWREngine.setErrorHandler(errorPreparaDados);
			ComissaoReuniaoDWR.prepareDadosReuniao(carregaDados,comissaoId)
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

			if (validaDate(document.getElementById('reuniaoData')))
				ComissaoReuniaoDWR.findPresencaColaboradoresByReuniao( populaColaboradores, data.comissaoReuniaoId ); 

			openbox('Editar Reunião', 'reuniaoHorario');
		}

		function carregaListaColaboradores()
		{
			$('#colaborador tbody').empty();
			
			if (validaDate(document.getElementById('reuniaoData')))
				ComissaoReuniaoDWR.findColaboradoresByDataReuniao( populaColaboradores, $('#reuniaoData').val(), ${comissao.id} ); 
		}
		
		function populaColaboradores(colaboradores)
		{
			var linha;
			for (var i = 0; i < colaboradores.length; i++)
			{
				linha = '<tr class="' + (i%2==0 ? 'odd' : 'even') + '">';
				linha += '<td align="center"><input type="checkbox" ' + (colaboradores[i].presente == 'true' ? 'checked="checked"' : '') + ' onclick="desabilitaJustificativa(this);" id="check' + colaboradores[i].id + '" value="' + colaboradores[i].id + '" name="colaboradorChecks" /></td>';
				linha += '<td align="left">' + colaboradores[i].nome + '</td>';
				linha += '<td align="center">';
				linha += '  <input type="text" value="' + (colaboradores[i].justificativaFalta != null ? colaboradores[i].justificativaFalta : '') + '" name="justificativas" maxlength="100" id="justificativaId' + colaboradores[i].id + '" style="width:160px;border:1px solid #BEBEBE;"/>';
				linha += '  <input type="hidden" name="colaboradorIds" value="' + colaboradores[i].id + '"/>';
				linha += '</td>';
				linha += '</tr>';
				$('#colaborador tbody').append(linha);
			}
			
			$('#colaborador input:checkbox').not('#md').each(function() { desabilitaJustificativa(this); });
		}
	</script>
</head>

<body>
	<#include "comissaoLinks.ftl" />
	<#include "../ftl/mascarasImports.ftl" />

	<@display.table name="comissaoReuniaos" id="comissaoReuniao" class="dados">
		<@display.column title="Ações" class="acao">
			<a href="javascript:;" onclick="limpaForm(); preparaDadosUpdate(${comissaoReuniao.id});"><img border="0" title="<@ww.text name="list.edit.hint"/>" src="<@ww.url value="/imgs/edit.gif"/>"></a>
			<a href="javascript:;" onclick="newConfirm('Confirma exclusão?', function(){window.location='delete.action?comissaoReuniao.id=${comissaoReuniao.id}&comissao.id=${comissao.id}'});"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
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
		<button class="btnFrequencia" title="Frequência" onclick="window.location='relatorioPresenca.action?comissao.id=${comissao.id}'"></button>
		<button class="btnImprimirRelatorioFrequencia" title="Relatório de frequência" onclick="window.location='imprimirFrequencia.action?comissao.id=${comissao.id}'"></button>
	</div>

	<#--
		 Modal de edição da Reunião
	-->
	<div id="filter"></div>
	<div id="box" style="height:540px;">
		<span id="boxtitle"></span>
		<@ww.form name="form" action="insert.action" method="POST">
			<@ww.datepicker label="Data" id="reuniaoData" name="comissaoReuniao.data" onblur="carregaListaColaboradores()" eventOnUpdate="carregaListaColaboradores" cssClass="mascaraData" required="true"/>
			<@ww.textfield label="Horário" id="reuniaoHorario" name="comissaoReuniao.horario" value="" maxlength="20" cssStyle="width:40px;" required="true"/>
			<@ww.textfield label="Local" id="reuniaoLocal" name="comissaoReuniao.localizacao" value="" maxlength="100" cssStyle="width:220px;"/>
			<@ww.select label="Tipo" id="reuniaoTipo" name="comissaoReuniao.tipo" list=r"#{'O':'Ordinária','E':'Extraordinária'}" required="true"/>
			<@ww.textfield label="Descrição" id="reuniaoDesc" name="comissaoReuniao.descricao" value="" maxlength="100" cssStyle="width:400px;" />
			<@ww.textarea label="Pontos pendentes da reunião anterior (ata)" id="reuniaoObsAnterior" name="comissaoReuniao.obsReuniaoAnterior" cssStyle="width: 520px;height:35px;"/>
			<@ww.textarea label="Pontos discutidos (ata)" id="reuniaoAta" name="comissaoReuniao.ata" cssStyle="width: 520px;height:50px;"/>

			<@ww.hidden name="comissaoReuniao.id" id="comissaoReuniaoId" value=""/>
			<@ww.hidden name="comissao.id" id="comissaoId" />
			Presença:<br/>
			<div style="height:130px;width:520px;overflow-y:scroll;border:1px solid #BEBEBE;">
				<table name="colaboradors" id="colaborador" class="dados" style="width:500px;border:none;">
					<thead>
						<tr>
							<th width="30"><input type="checkbox" id="md" onclick="marcarDesmarcar(this);"></th>
							<th width="280">Nome</th>
							<th>Justificativa da ausência</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</@ww.form>

		<div class="buttonGroup">
			<button onclick="validaDataReuniaoCampos();" class="btnGravar"></button>
			<button onclick="closebox();" class="btnCancelar"></button>
		</div>
	</div>
</body>
</html>