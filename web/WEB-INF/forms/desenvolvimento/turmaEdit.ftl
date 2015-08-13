<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<html>
<head>
<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	#formDialog { display: none; }
	
	#diasTable { border: none; }
	#diasTable td { padding: 0px; }
	#diasTable input[type='text'] { border: 1px solid #BEBEBE; width: 50px; }
	.hora { text-align: right; }
	.invalido { background-color: #FFEEC2 !important; }
</style>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/CursoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/TurmaDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/DiaTurmaDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.price_format.1.6.min.js"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/formataValores.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/json2.js"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/qtip.js?version=${versao}"/>"></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.price_format.1.6.min.js"/>"></script><!-- Usado para o function.js cssClass=hora-->

	<#include "../ftl/mascarasImports.ftl" />

	<#if turma?exists && turma.id?exists>
		<title>Editar Turma - ${turma.descricao}</title>
		<#assign formAction="update.action"/>
		<#assign accessKey="A"/>
	<#else>
		<title>Inserir Turma</title>
		<#assign formAction="insert.action"/>
		<#assign accessKey="I"/>
	</#if>

	<#if avaliacaoRespondida == true>
		<#assign somenteLeitura = true/>
	<#else>
		<#assign somenteLeitura = false/>
	</#if>

	<#if turma.custo?exists>
		<#assign custo = turma.custo/>
	<#else>
		<#assign custo = ''/>
	</#if>
	
	<#assign temPresencasRegistradas = turma.temPresenca?exists && turma.temPresenca/>
	
	<script type="text/javascript">
		String.prototype.capitalize = function() {
		    return this.charAt(0).toUpperCase() + this.slice(1);
		}
		
		function getDocumentoAnexos(cursoId) {	
			CursoDWR.getDocumentoAnexos(populaDocumentoAnexos, cursoId);
		};
		
		function populaDocumentoAnexos(data)
		{
			console.log(data);
			addChecks('documentoAnexoCheck', data, 'descricao');
		}
		
		function populaDias(frm)
		{
			$('#diasTable').empty();
			$('#listCheckBoxFilterDiasCheck').val('');
		
			var dIni = document.getElementById('prevIni');
			var dFim = document.getElementById('prevFim');
	
			if(validaDate(dIni) && validaDate(dFim))
			{
				if(dIni.value != "  /  /    " && dFim.value != "  /  /    ")
				{
					DWRUtil.useLoadingMessage('Carregando...');
					DiaTurmaDWR.getDias(montaListDias, dIni.value, dFim.value, $('#porTurno').val());
				}
			}
		}
		
		function montaListDias(diasTurma)
		{
			if (diasTurma != null)
			{
				var dataFmt, id;
				var onclick = <#if temPresencasRegistradas>"onclick='return false;'"<#else>""</#if>;
				
				for (var i = 0; i < diasTurma.length; i++)
				{
					dataFmt = diasTurma[i].diaFormatado;
					id = dataFmt.replace(/\//g,'') + diasTurma[i].turno;
					
					var row = 	"<tr class='" + (i%2 == 0 ? 'even' : 'odd') + "'>\n";
					row += 		"	<td><input name='diasCheck' id='" + id + "' value='" + dataFmt + ';' + diasTurma[i].turno + "' type='checkbox' " + onclick + " /></td>\n";
					row += 		"	<td><label for='" + id + "'>" + dataFmt + "</label></td>\n";
					row += 		"	<td><label for='" + id + "'>" + diasTurma[i].diaSemanaDescricao.capitalize() + "</label></td>\n";
					if (diasTurma[i].turno != 'D')
					{
						row +=	"	<td><label for='" + id + "'>" + diasTurma[i].turnoDescricao.capitalize() + "</label></td>\n";
					}
					row += 		'	<td><input type="text" id="horaIni-' + id + '" name="horariosIni" class="hora" maxlength="5" disabled=true /> às <input type="text" id="horaFim-' + id + '" name="horariosFim" class="hora" maxlength="5" disabled=true /></td>\n';
					row += 		"</tr>\n";
				
					$('#diasTable').append(row);
					$('#horaIni-' + id).css('background-color', '#ECECEC');
					$('#horaFim-' + id).css('background-color', '#ECECEC');
				}
				
				$("input[name='diasCheck']").change(function() {
					var marcado = $(this).is(":checked");
					
					$('#horaIni-' + this.id).attr("disabled", !marcado);
					$('#horaFim-' + this.id).attr("disabled", !marcado);
					
					if(marcado){
						$('#horaIni-' + this.id).css('background-color', '#FFF');
						$('#horaFim-' + this.id).css('background-color', '#FFF');
					}else{
						$('#horaIni-' + this.id).css('background-color', '#ECECEC');
						$('#horaFim-' + this.id).css('background-color', '#ECECEC');
					}
				});
				
				marcaCheckListBoxString();

				<#if temPresencasRegistradas>
					$("input[name='horariosIni']").attr('disabled', true).css('background-color', '#ECECEC');
					$("input[name='horariosFim']").attr('disabled', true).css('background-color', '#ECECEC');
				</#if>
			}
			else
				jAlert("Data inválida.");
		}
		
		function marcaTodos()
		{
			$('input[name=diasCheck]').attr('checked','checked');
			$("input[name='diasCheck']").each( function() {
				$('#horaIni-' + this.id).attr("disabled", false).css('background-color', '#FFF');
				$('#horaFim-' + this.id).attr("disabled", false).css('background-color', '#FFF');
		    });
		}
		
		function desmarcaTodos()
		{
			$('input[name=diasCheck]').removeAttr('checked');
			$("input[name='diasCheck']").each( function() {
				$('#horaIni-' + this.id).attr("disabled", true).css('background-color', '#ECECEC');
				$('#horaFim-' + this.id).attr("disabled", true).css('background-color', '#ECECEC');
		    });
		}
		
		var diasTurmasMarcados = [<#if diaTurmas?exists>
									<#list diaTurmas as diaTurmaMarcado>
										'${diaTurmaMarcado.dia?string('ddMMyyyy') + diaTurmaMarcado.turno}'
										<#if diaTurmaMarcado.horaIni?exists && diaTurmaMarcado.horaFim?exists>
											 + '-${diaTurmaMarcado.horaIni}' + '-${diaTurmaMarcado.horaFim}'
										</#if>
										<#if diaTurmaMarcado_has_next>
											, 
										</#if>
									</#list>
								</#if>];
								
		function marcaCheckListBoxString()
		{
			for(var count = 0; count < diasTurmasMarcados.length; count++)
			{
				var identificador = diasTurmasMarcados[count].split("-");
			
				$('#' + identificador[0]).attr('checked','checked');
				$('#horaIni-' + identificador[0]).val(identificador[1]).css('background-color', '#FFF').attr("disabled", false);
				$('#horaFim-' + identificador[0]).val(identificador[2]).css('background-color', '#FFF').attr("disabled", false);
			}
		}
		
		function mostraAssinatura()
		{
			mostrar(document.getElementById('assinaturaUpLoad'));
			$('#divAssinatura hr').remove();
		}
	
		function limpaDespesas()
		{
			 $('#totalCustos').text('0,00');
			 $('.despesa').val('');
		}
		
		function abrirPopupDespesas() 
		{
			var camposOcultos = '';
			$('#formDialog').dialog({ 	modal: true, 
										width: 500,
										height: 360,
										buttons: 
										[
										    {
										        text: "Gravar",
										        click: function() { 
										        	var despesas = new Array();

													$('.despesa').each(function(){
													    if (this.value && moeda2float(this.value) > 0)
													        despesas.push({tipoDespesaId:this.name, despesa:moeda2float(this.value)});
													});
													
													var despesasJSON = JSON.stringify(despesas);
													var despesasTotal = somaDespesas();
													var turmaId = $('#turmaId').val();
													
													if (turmaId != '')
														TurmaDWR.saveDespesas(despesasJSON, turmaId, despesasTotal);
													
													$('#custos').val(despesasJSON);
													$('#custo').val(float2moeda(despesasTotal));
													
													somenteLeitura((despesasTotal <= 0), 'custo');
													
										        	$(this).dialog("close"); 
										        }
										    },
										    {
										        text: "Limpar",
										        click: function() { limpaDespesas(); }
										    }
										] ,
										open: function(event, ui) 
										{ 
											var turmaId = $('#turmaId').val();
											if (turmaId != '')
											{
												TurmaDWR.getDespesas(turmaId, function(turmaTipoDespesas) 
												{
													$(turmaTipoDespesas).each(function(i, turmaTipoDespesa) {
														$("#tipoDespesa :input[name='" + turmaTipoDespesa.tipoDespesa.id + "']").val( float2moeda(turmaTipoDespesa.despesa) );
													});
													
													$('#totalCustos').text(float2moeda(somaDespesas()));
												});
											
											} else 
											{
												var custos = $('#custos').val();
												
												if (custos != '') 
												{
													custos = JSON.parse(custos);
	
													$(custos).each(function() {
														$("#tipoDespesa :input[name='" + this.tipoDespesaId + "']").val( float2moeda(this.despesa) );
													});
													
													$('#totalCustos').text(float2moeda(somaDespesas()));
												}
											}
										},
										close: function(event, ui)
										{
											limpaDespesas();
										}
									});
		}
		
		function somaDespesas() 
		{
			var total = 0;

			$('.despesa').each(function (i, item) {
			    var valor = $(item).val();
			    if (valor && valor != '')
			        total += moeda2float(valor);
			});
			
			return total;
		}
		
		function validarCampos()
		{
			var chave, horaIni, horaFim, horariosValidos = true;
			$('input[name=diasCheck]:checked').each(function() {

				chaveArray = $(this).val().replace(/\//g,'').split(";");
				id = chaveArray[0] + chaveArray[1];   
				
				horaIni = $('#horaIni-' + id).val();
				horaFim = $('#horaFim-' + id).val();
				
				if((!horaIni && horaFim) || (horaIni && !horaFim))
				{
					$('#horaIni-' + id).val('');
					$('#horaFim-' + id).val('');
				}
				
				if ( (horaIni && horaFim) && ((!horaIni != !horaFim) || !(validaHora(horaIni) && validaHora(horaFim)) || (parseInt(horaFim.replace(':','')) < parseInt(horaIni.replace(':',''))) ))
				{
					$('#horaIni-' + id).css('background-color', '#FFEEC2');
					$('#horaFim-' + id).css('background-color', '#FFEEC2');
					horariosValidos = false;
				}
				else
				{
					$('#horaIni-' + id).css('background-color', '#FFF');
					$('#horaFim-' + id).css('background-color', '#FFF');
				}
				
			});
			
			if (!horariosValidos)
			{
				jAlert('Existe(m) horário(s) definidos incorretamente.<br />Informe os horários de início e término para a data ou deixe ambos em branco.');
				return false;
			}
			
			return validaFormularioEPeriodo('form', new Array('curso','desc','inst','custo','prevIni','prevFim'), new Array('prevIni', 'prevFim'));
		}
		
		$(function() {
			<#if contemCustosDetalhados>
				somenteLeitura(false, 'custo');
			</#if>
	
			$('.despesa').blur(function() {
				var valor = somaDespesas();
				$('#totalCustos').text(float2moeda(valor));
			});

			$('#tooltipCurso').qtip({
				content: 'A alteração do curso está restrita à empresa que o criou.'
			});
			$('#tooltipCusto').qtip({
				content: 'A alteração do custo está restrita à empresa que o criou.'
			});
			$('#tooltipDias').qtip({
				content: 'Não é possível editar os dias previstos, pois existem presenças.'
			});
			$('#tooltipAvaliacao').qtip({
				content: 'A alteração da avaliação do curso está restrita à empresa que o criou.'
			});
			
			$('#listCheckBoxFilterDiasCheck').unbind('keyup').keyup(function() {
		        var texto = removerAcento( $( this ).val().toUpperCase() );
			    $( this ).parents( '.listCheckBoxContainer' ).find( ':checkbox' ).each( function() {
			    	 nomeTeste = removerAcento( $( this ).parents( 'tr' ).text().toUpperCase() );
					 $( this ).parents('tr').toggle( nomeTeste.indexOf( texto ) >= 0 );
		    	});
			});
			
			$('#divAssinatura hr').remove();
			
			populaDias(document.forms[0]);
		});
	</script>
<@ww.head/>

<#assign validarCampos="return validarCampos();"/>
</head>
<body>
	<@ww.actionerror />
	<@ww.form id="formTurma" name="form" action="${formAction}" onsubmit="${validarCampos}" validate="true" method="POST" enctype="multipart/form-data">
		
		<#if turmaPertenceAEmpresaLogada>
			<@ww.select label="Curso" name="turma.curso.id" list="cursos" id="curso" listKey="id" listValue="nome"  headerKey="" headerValue="Selecione..." onchange="getDocumentoAnexos(this.value);" cssStyle="width: 611px;"/>
		<#else>
			<strong>Curso: ${turma.curso.nome}</strong>
			<img id="tooltipCurso" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: -3px; margin-top: -9px;" />
			<@ww.hidden name="turma.curso.id" id="curso" value="${turma.curso.id}" />
			<br /><br />
		</#if>

		<@ww.textfield required="true" label="Descrição" name="turma.descricao" id="desc" maxLength="100" cssStyle="width: 608px;"/>
		<@ww.textfield label="Instituição" maxLength="100" name="turma.instituicao" id="instituicao"  cssStyle="width: 490px;" liClass="liLeft"/>
		
		<li id="wwgrp_custo">
			<div class="wwlbl" id="wwlbl_custo">
				<label class="desc" for="custo"> Custo (R$):<span class="req">* </span></label>
			</div> 
			<div class="wwctrl" id="wwctrl_custo">
				<#if turmaPertenceAEmpresaLogada>
					<@ww.textfield theme="simple" id="custo" name="turma.custo" cssClass="moeda" maxlength="12" size="12" cssStyle="width:90px; text-align:right;"/>
					<a href="javascript:;" id=detalharCusto  onclick="abrirPopupDespesas();" title="Detalhamento dos custos"><img src="<@ww.url includeParams="none" value="/imgs/agrupar.gif"/>" border="0" align="absMiddle"/></a>
				<#else>
					<@ww.textfield theme="simple" id="custo" name="turma.custo" cssClass="moeda" maxlength="12" size="12" cssStyle="width:90px; text-align:right;" readonly=true/>
					<img id="tooltipCusto" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: -3px" />
				</#if>
			</div> 
		</li>
		
		<@ww.select label="Realizada" name="turma.realizada" list=r"#{true:'Sim',false:'Não'}" liClass="liLeft" cssStyle="width: 80px;"/>
		<@ww.textfield label="Horário" maxLength="20" name="turma.horario" id="horario" liClass="liLeft"/>
		<@ww.textfield label="Qtd. Prevista de Participantes" name="turma.qtdParticipantesPrevistos" id="qtdParticipantesPrevistos" cssStyle="width:30px; text-align:right;" maxLength="3" onkeypress = "return(somenteNumeros(event,''));" />
		
		<fieldset style="padding: 5px 0px 5px 5px; width: 600px; height: 130px;">
			<legend>Instrutor</legend>
			<@ww.textfield required="true" label="Nome" size="55" name="turma.instrutor" id="inst" maxLength="100" cssStyle="width: 584px;"/>
			<#if turma.assinaturaDigitalUrl?exists >
				<div id="divAssinatura">
					<table style="border:0px;">
						<tr>
							<td>
								<#if turma.id?exists && turma.assinaturaDigitalUrl != "">
									<img src="<@ww.url includeParams="none" value="showAssinatura.action?turma.assinaturaDigitalUrl=${turma.assinaturaDigitalUrl}"/>" style="display:inline" id="assinaturaImg" width="250px" height="50px">
								</#if>
							</td>
							<td>
								<@ww.checkbox label="Manter assinatura" name="manterAssinatura" onclick="mostraAssinatura()" value="true" labelPosition="left" checked="checked"/>
								<div id="assinaturaUpLoad" style="display:none;">
									<@ww.file label="Nova Assinatura [250 x 50]" name="assinaturaDigital" id="assinaturaDigital"/>
								</div>
							</td>
						</tr>
					</table>
					<hr>
				</div>
	        <#else>
				<@ww.file label="Assinatura Digital [250 x 50]" name="assinaturaDigital" id="assinaturaDigital"/>
	        </#if>
		</fieldset><br />
		
		<#if turma.temPresenca?exists && turma.temPresenca>
			<img id="tooltipDias" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: 103px; margin-bottom:-18px" />
		</#if>
		<fieldset style="padding: 5px 0px 5px 5px; width: 600px;">
			<legend>Dias Previstos</legend>

			<#assign dataIni=""/>
			<#if turma?exists && turma.dataPrevIni?exists>
				<#assign dataIni=turma.dataPrevIni/>
			</#if>
			<#assign dataFim=""/>
			<#if turma?exists && turma.dataPrevFim?exists>
				<#assign dataFim=turma.dataPrevFim/>
			</#if>
				
			<#if temPresencasRegistradas>
				<@ww.select label="Realizar turma por" name="turma.porTurno" list=r"#{false:'Dia',true:'Turno'}" onchange="populaDias(document.forms[0]);" disabled=true/>
				<@ww.hidden name="turma.porTurno" />
				Período:*<br />
				<@ww.textfield name="turma.dataPrevIni" value="${dataIni}" id="prevIni" readonly=true maxlength="10" cssStyle="width:80px;" liClass="liLeft" />
				<@ww.label value="a" liClass="liLeft"/>
				<@ww.textfield name="turma.dataPrevFim" value="${dataFim}" id="prevFim" readonly=true maxlength="10" cssStyle="width:80px;" liClass="liLeft"/><br /><br />
			<#else>
				<@ww.select label="Realizar turma por" name="turma.porTurno" id="porTurno" list=r"#{false:'Dia',true:'Turno'}" onchange="populaDias(document.forms[0]);"/>
				Período:*<br>
				<@ww.datepicker required="true" name="turma.dataPrevIni" value="${dataIni}" id="prevIni" liClass="liLeft" onblur="populaDias(document.forms[0]);" onchange="populaDias(document.forms[0]);"  cssClass="mascaraData validaDataIni"/>
				<@ww.label value="a" liClass="liLeft" />
				<@ww.datepicker required="true" name="turma.dataPrevFim" value="${dataFim}" id="prevFim" onblur="populaDias(document.forms[0]);" onchange="populaDias(document.forms[0]);"  cssClass="mascaraData validaDataFim" /><br />
			</#if>

			<br />
			
			<div id="wwctrl_diasCheck" class="wwctrl">
				<div class="listCheckBoxContainer" style="width: 593px;">
					<div class="listCheckBoxBarra">
						<input id="listCheckBoxFilterDiasCheck" class="listCheckBoxFilter" title="Digite para filtrar" type="text">
						&nbsp;<span class="linkCheck" onclick="marcaTodos();">Marcar todos</span> | <span class="linkCheck" onclick="desmarcaTodos();">Desmarcar todos</span>
					</div>
					<div id="listCheckBoxdiasCheck" class="listCheckBox">
						<table id="diasTable" class="dados">
							
						</table>
					</div>
				</div>
			</div>
		</fieldset>
		
		<br />

		<#if turmaPertenceAEmpresaLogada>
			<@frt.checkListBox label="Questionários de Avaliação do Curso" name="avaliacaoTurmasCheck" list="avaliacaoTurmasCheckList" width="607" filtro="true"/>
			<@frt.checkListBox label="Anexos disponíveis para os colaboradores da turma" name="documentoAnexoCheck" list="documentoAnexoCheckList" width="607" filtro="true"/>
		<#else>
			<img id="tooltipAvaliacao" src="<@ww.url value="/imgs/help.gif"/>" width="16" height="16" style="margin-left: 250px; margin-bottom:-18px" />
			<@frt.checkListBox label="Questionários de Avaliação do Curso" name="avaliacaoTurmasCheck" list="avaliacaoTurmasCheckList" readonly=true  width="607" filtro="true"/>
		</#if>

		<@ww.hidden name="turma.id" id="turmaId" />
		<@ww.hidden name="turma.empresa.id" />
		<@ww.hidden name="turma.assinaturaDigitalUrl" />
		<@ww.hidden name="planoTreinamento" />
		<@ww.hidden name="avaliacaoRespondida" />
		<@ww.hidden name="custos" id="custos"/>
		<@ww.token/>
		
		<#if somenteLeitura>
			<#list avaliacaoTurmasCheckList as avaliacaoCheck>
				<#if avaliacaoCheck.selecionado>
					<@ww.hidden name="avaliacaoTurmasCheck" value="${avaliacaoCheck.id}" />
				</#if>
			</#list>
		</#if>
	</@ww.form>

	<#if planoTreinamento>
		<#assign urlVoltar="filtroPlanoTreinamento.action"/>
	<#else>
		<#assign urlVoltar="list.action?curso.id=${curso.id}"/>
	</#if>

	<div class="buttonGroup">
		<button onclick="${validarCampos};" class="btnGravar" accesskey="${accessKey}"></button>
		<button onclick="window.location='${urlVoltar}'" class="btnVoltar"></button>
	</div>
	
	<div id="formDialog" title="Detalhamento dos custos">
		<br />
		
		Total R$  
		<span id="totalCustos"></span>
		
		<br /><br />
		
		<@display.table name="tipoDespesas" id="tipoDespesa" class="dados" style="width:450px;">
			<@display.column property="descricao" title="Descrição"/>
			<@display.column title="Custo (R$)" style="text-align: center; width:120px;">
				<input type="text" name="${tipoDespesa.id}" class="despesa moeda" maxlength="10" size="12" style="text-align:right; width: 90px;border:1px solid #BEBEBE;"/>
			</@display.column>
		</@display.table>
	</div>
	
	<script type="text/javascript">
		$(function() {
			<#if somenteLeitura>
				 $("input[type='checkbox'][name='avaliacaoTurmasCheck']").attr("disabled" , "disabled");
			</#if>
		});
	</script>
</body>
</html>