<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<title>Plano de Desenvolvimento Individual (PDI) - Criação de Turmas para Treinamentos</title>
	
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	
		#formDialog { display: none; }
		.tabela_colabs { float: right; width: 400px; height: 689px; overflow-y: scroll; overflow-x: hidden; border: 1px solid #BEBEBE; }
		.tabela_colabs .dados { width: 385px; border: none; }
		fieldset { padding: 10px; margin-bottom: 20px; }
		.legend { font-weight: bold; }
		
		.diasTable { border: none; }
		.diasTable td { padding: 0px; }
		.diasTable input[type='text'] { border: 1px solid #BEBEBE; width: 50px; }
		.hora { text-align: right; }
		.invalido { background-color: #FFEEC2 !important; }
	</style>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/TurmaDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/DiaTurmaDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.price_format.1.6.min.js"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/formataValores.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/json2.js"/>'></script>
	
	<#include "../ftl/mascarasImports.ftl" />
	
	<#assign temPresencasRegistradas = turma.temPresenca?exists && turma.temPresenca/>
	
	<script type="text/javascript">
		$(function() {
			DWRUtil.useLoadingMessage('Carregando...');
		
			$('.despesa').blur(function() {
				var valor = somaDespesas();
				$('#totalCustos').text(float2moeda(valor));
			});
			
			$('.turma').change(function() {
				var id = $(this).val();
				var fset = $(this).closest('fieldset');
				var i = fset.attr('indice');
				
				$('#diasTable' + i).empty();
				$('#listCheckBoxFilterDiasCheck' + i).val('');
				
				if (id)
				{
					TurmaDWR.getTurma(id, populaTurma);
				}
				else
				{
					fset.find('input:text,input:checkbox,.listCheckBox').removeAttr('disabled').css('background-color','#fff');
					fset.find('input:text').val('');
					fset.find('#prevIni' + i + '_button > img, #prevFim' + i + '_button > img, #detalharCusto' + i + ' img').show();
					fset.find('#porTurno' + i).removeAttr('disabled').css('background-color','#fff');
					fset.find('.mascaraData').val('  /  /    ');
					fset.find(':checkbox').removeAttr('checked');
					fset.find('#listCheckBoxdiasTurmasCheck\\[' + i + '\\]').empty().removeAttr('disabled');
				}
			});
			
			function checkListBoxDiasSearch(filter) {
		        var texto = removerAcento( $( filter ).val().toUpperCase() );
			    $( filter ).parents( '.listCheckBoxContainer' ).find( ':checkbox' ).each( function() {
			    	 nomeTeste = removerAcento( $( this ).parents( 'tr' ).text().toUpperCase() );
					 $( filter ).parents('tr').toggle( nomeTeste.indexOf( texto ) >= 0 );
		    	});
			}
		});
		
		$.datepicker.setDefaults({ dayNames: ['domingo','segunda-feira','terça-feira','quarta-feira','quinta-feira','sexta-feira','sábado'] });
		
		String.prototype.capitalize = function() {
		    return this.charAt(0).toUpperCase() + this.slice(1);
		}
		
		function populaTurma(turma)
		{
			var fset = $('.fset_' + turma.curso.id);
			var i = fset.attr('indice');
			
			$("#desc" + i).val(turma.descricao);
			$("#horario" + i).val(turma.horario);
			$("#inst" + i).val(turma.instrutor);
			$("#instituicao" + i).val(turma.instituicao);
			$("#custo" + i).val(turma.custoFormatado);
			$("#porTurno" + i).val('' + turma.porTurno).attr('disabled', 'disabled').css('background-color','#ececec');
			$("#listCheckBoxdiasTurmasCheck\\[" + i + "\\]").attr('disabled', 'disabled');
			
			var datas = turma.periodoFormatado.split(' a ');
			$("#prevIni" + i).val(datas[0]);
			$("#prevFim" + i).val(datas[1]);
			
			populaDias(i, turma);
			
			$(turma.avaliacaoTurmas).each(function(j, avaliacaoTurma) {
				$("input[name='avaliacaoTurmasCheck[" + i + "]'][value='" + avaliacaoTurma.id + "']").attr("checked","checked");
			});
			
			fset.find('input:text,input:checkbox,.listCheckBox').attr('disabled','disabled').css('background-color','#ececec');
			fset.find('#prevIni' + i + '_button > img, #prevFim' + i + '_button > img, #detalharCusto' + i + ' img').hide();
		}
		
		function populaDias(indice, turma)
		{
			$('#diasTable' + indice).empty();
			$('#listCheckBoxFilterDiasCheck' + indice).val('');
		
			var dIni = document.getElementById('prevIni' + indice);
			var dFim = document.getElementById('prevFim' + indice);
	
			if(validaDate(dIni) && validaDate(dFim))
			{
				if(dIni.value != "  /  /    " && dFim.value != "  /  /    ")
				{
					DWRUtil.useLoadingMessage('Carregando...');
					DiaTurmaDWR.getDias(dIni.value, dFim.value, $('#porTurno' + indice).val(), function(dados) { montaListDias(dados, indice, turma); });
				}
			}
		}
		
		function montaListDias(diasTurma, indice, turma)
		{
			if (diasTurma != null)
			{
				var dataFmt, id;
				var onclick = turma ? "onclick='return false;'" : "";
				
				for (var i = 0; i < diasTurma.length; i++)
				{
					dataFmt = diasTurma[i].diaFormatado;
					id = dataFmt.replace(/\//g,'') + diasTurma[i].turno + indice;
					
					var row = 	"<tr class='" + (i%2 == 0 ? 'even' : 'odd') + "'>\n";
					row += 		"	<td><input name='diasTurmasCheck[" + indice + "]' id='" + id + "' value='" + dataFmt + ';' + diasTurma[i].turno + "' type='checkbox' " + onclick + " /></td>\n";
					row += 		"	<td><label for='" + id + "'>" + dataFmt + "</label></td>\n";
					row += 		"	<td><label for='" + id + "'>" + diasTurma[i].diaSemanaDescricao.capitalize() + "</label></td>\n";
					if (diasTurma[i].turno != 'D')
					{
						row +=	"	<td><label for='" + id + "'>" + diasTurma[i].turnoDescricao.capitalize() + "</label></td>\n";
					}
					row += 		'	<td><input type="text" id="horaIni-' + id + '" name="horariosIni[' + indice + ']" class="mascaraHora hora" maxlength="5" disabled="disabled" /> às <input type="text" id="horaFim-' + id + '" name="horariosFim[' + indice + ']" class="mascaraHora hora" maxlength="5" disabled="disabled" /></td>\n';
					row += 		"</tr>\n";
				
					$('#diasTable' + indice).append(row);
					$('#horaIni-' + id).css('background-color', '#ECECEC');
					$('#horaFim-' + id).css('background-color', '#ECECEC');
				}
				
				if (turma)
				{
					$(turma.diasTurma).each(function(k, diaTurma) {
						var id = diaTurma.diaFormatado.replace(/\//g,'') + diaTurma.turno + indice;
						$('#' + id).attr("checked", "checked");
						$('#horaIni-' + id).val(diaTurma.horaIni);
						$('#horaFim-' + id).val(diaTurma.horaFim);
					});
				}
				
				$("input[name='diasTurmasCheck\[" + indice + "\]']").change(function() {
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
			}
			else
				jAlert("Data inválida.");
		}
		
		function marcarDesmarcarTodosDiasTurmas(indice, marcar)
		{
			if ($('#listCheckBoxdiasTurmasCheck\\[' + indice + '\\]').attr('disabled') == 'disabled')
				return false;
		
			var checks = $("input[name='diasTurmasCheck\[" + indice + "\]']");
			
			if (marcar)
				checks.attr('checked','checked');
			else
				checks.removeAttr('checked');
			
			checks.each( function() {
				$('#horaIni-' + this.id).attr("disabled", false).css('background-color', marcar ? '#FFF' : '#ECECEC');
				$('#horaFim-' + this.id).attr("disabled", false).css('background-color', marcar ? '#FFF' : '#ECECEC');
		    });
		}
	
		function abrirPopupDespesas(indice) 
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
													
													$('#custos' + indice).val(despesasJSON);
													$('#custo' + indice).val(float2moeda(despesasTotal));
													
													somenteLeitura((despesasTotal <= 0), 'custo' + indice);
													
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
											var custos = $('#custos' + indice).val();
											
											if (custos != '') 
											{
												custos = JSON.parse(custos);

												$(custos).each(function() {
													$("#tipoDespesa :input[name='" + this.tipoDespesaId + "']").val( float2moeda(this.despesa) );
												});
												
												$('#totalCustos').text(float2moeda(somaDespesas()));
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
		
		function limpaDespesas()
		{
			 $('#totalCustos').text('0,00');
			 $('.despesa').val('');
		}
				
		function aplicar()
		{
			var obrigatorios = [];
			var validados = [];
			var horariosValidos = true;
			
			for (var i = 0; i < ${cursosColaboradores?size}; i++)
			{
				if ($("#fset_" + i).length > 0)
				{
					obrigatorios.push('desc' + i);
					obrigatorios.push('inst' + i);
					obrigatorios.push('prevIni' + i);
					obrigatorios.push('prevFim' + i);
					obrigatorios.push('custo' + i);
					obrigatorios.push('@diasTurmasCheck[' + i + ']');
	
					validados.push('prevIni' + i);
					validados.push('prevFim' + i);
					
					var horaIni, horaFim;
					$("input:checkbox:checked[name='diasTurmasCheck\[" + i + "\]']").each(function(i, check) {
						horaIni = $('#horaIni-' + this.id).val();
						horaFim = $('#horaFim-' + this.id).val();
						
						if((!horaIni && horaFim) || (horaIni && !horaFim))
						{
							$('#horaIni-' + this.id).val('');
							$('#horaFim-' + this.id).val('');
						}
						
						if ( (horaIni && horaFim) && ((!horaIni != !horaFim) || !(validaHora(horaIni) && validaHora(horaFim)) || (parseInt(horaFim.replace(':','')) < parseInt(horaIni.replace(':',''))) ))
						{
							$('#horaIni-' + this.id).css('background-color', '#FFEEC2');
							$('#horaFim-' + this.id).css('background-color', '#FFEEC2');
							horariosValidos = false;
						}
						else
						{
							$('#horaIni-' + this.id).css('background-color', '#FFF');
							$('#horaFim-' + this.id).css('background-color', '#FFF');
						}
					});
				}
			}
			
			if (!horariosValidos)
			{
				jAlert('Existe(m) horário(s) definidos incorretamente.<br />Informe os horários de início e término para a data ou deixe ambos em branco.');
				return false;
			}

			return validaFormularioEPeriodo('form', obrigatorios, validados);
		}
		
		function ajustaListras(indice)
		{
			$('#tabela_' + indice + ' tr').each(function(i, linha) { $(linha).attr('class', (i % 2 == 0 ? 'even' : 'odd')); });
		}
	</script>
	
	<@ww.head/>
</head>
<body>
	<@ww.actionmessage />
	<@ww.actionerror />
	
	<@ww.form name="form" action="aplicarPdi.action" method="POST">
		<#assign i = 0/>
		
		<#list cursosColaboradores?keys as curso>
			<fieldset id="fset_${i}" class="fset_${curso.id}" indice="${i}">
				<legend class="legend">
					<a href="javascript:;" onclick="newConfirm('Deseja realmente cancelar a criação da turma para o curso<br /> ${curso.nome}?', function(){ $('#fset_${i}').remove(); });"><img border="0" title="Cancelar criação de turma para este curso" src="<@ww.url value="/imgs/delete.gif"/>"></a>
					Curso: ${curso.nome}
				</legend>
				
				<div class="tabela_colabs">
					<table class="dados" id="tabela_${i}">
						<thead>
							<tr>
								<th width="30">&nbsp;</th>
								<th>Colaboradores Inscritos</th>
							</tr>
						</thead>
						<tbody>
							<#assign j = 0/>
							<#list action.getColaboradoresCurso(curso) as colab>
								<tr id="colab_${i}_${j}" class="<#if j%2 == 0>odd<#else>even</#if>">
									<td align="center">
										<a href="javascript:;" onclick="newConfirm('Confirma exclusão do colaborador ${colab.nome}?', function(){ $('#colab_${i}_${j}').remove(); ajustaListras(${i}); });"><img border="0" title="Excluir" src="<@ww.url value="/imgs/delete.gif"/>"></a>
									</td>
									<td>
										${colab.nome}
										<@ww.hidden name="turmas[${i}].colaboradorTurmas[${j}].colaborador.id" value="${colab.id}"/>
									</td>
								</tr>
								<#assign j = j + 1/>
							</#list>
						</tbody>
					</table>
				</div>
				
				<li>
					<div class="wwlbl">
						<label class="desc" for="turma${i}"> Turma:<span class="req">* </span></label>
					</div> 
					<div class="wwctrl">
						<select name="turmas[${i}].id" id="turma${i}" class="turma" style="width: 500px;">
							<option value="">Criar nova</option>
							<#list curso.turmas as turma>
								<option value="${turma.id}">${turma.descricao}</option>
							</#list>
						</select>
					</div> 
				</li>
				
				<div class="form_turma">
					<li>&nbsp;</li>
					
					<@ww.hidden name="custos[${i}]" id="custos${i}"/>
					<@ww.hidden name="turmas[${i}].curso.id" value="${curso.id}"/>
					
					<@ww.textfield required="true" label="Descrição" name="turmas[${i}].descricao" id="desc${i}" cssStyle="width: 500px;" maxLength="100"/>
					<@ww.textfield label="Horário" maxLength="20" name="turmas[${i}].horario" id="horario${i}" liClass="liLeft" />
					<@ww.textfield required="true" label="Instrutor" name="turmas[${i}].instrutor" id="inst${i}" cssStyle="width: 347px;" maxLength="100"/>
					<@ww.textfield label="Instituição" maxLength="100" name="turmas[${i}].instituicao" id="instituicao${i}"  cssStyle="width: 385px;" liClass="liLeft"/>
					<li id="wwgrp_custo">
						<div class="wwlbl" id="wwlbl_custo${i}">
							<label class="desc" for="custo${i}"> Custo (R$):<span class="req">* </span></label>
						</div> 
						<div class="wwctrl" id="wwctrl_custo${i}">
							<@ww.textfield theme="simple" id="custo${i}" name="turmas[${i}].custo" cssClass="moeda" maxlength="12" size="12" cssStyle="width:90px; text-align:right;"/>
							<a href="javascript:;" id="detalharCusto${i}"  onclick="abrirPopupDespesas(${i});" title="Detalhamento dos custos"><img src="<@ww.url includeParams="none" value="/imgs/agrupar.gif"/>" border="0" align="absMiddle"/></a>
						</div> 
					</li>
					<br />
					<fieldset style="padding: 5px 0px 5px 5px; width: 490px;">
						<legend>Dias Previstos</legend>
						<@ww.select label="Realizar turma por" name="turmas[${i}].porTurno" id="porTurno${i}" list=r"#{false:'Dia',true:'Turno'}" onchange="populaDias(${i});"/>
						Período:* <br />
						<@ww.datepicker required="true" name="turmas[${i}].dataPrevIni" id="prevIni${i}" liClass="liLeft" onblur="populaDias(${i});" onchange="populaDias(${i});"  cssClass="mascaraData validaDataIni"/>
						<@ww.label value="a" liClass="liLeft" />
						<@ww.datepicker required="true" name="turmas[${i}].dataPrevFim" id="prevFim${i}" onblur="populaDias(${i});" onchange="populaDias(${i});"  cssClass="mascaraData validaDataFim" />
						
						<br />
			
						<div id="wwctrl_diasCheck" class="wwctrl">
							<div class="listCheckBoxContainer" style="width: 490px;">
								<div class="listCheckBoxBarra">
									<input id="listCheckBoxFilterDiasCheck${i}" class="listCheckBoxFilter" title="Digite para filtrar" type="text">
									&nbsp;<span class="linkCheck" onclick="marcarDesmarcarTodosDiasTurmas(${i}, true);">Marcar todos</span> | <span class="linkCheck" onclick="marcarDesmarcarTodosDiasTurmas(${i}, false);">Desmarcar todos</span>
								</div>
								<div id="listCheckBoxdiasTurmasCheck[${i}]" class="listCheckBox">
									<table id="diasTable${i}" class="dados diasTable">
										
									</table>
								</div>
							</div>
						</div>
					</fieldset>
					
					<@frt.checkListBox id="avaliacaoTurmasCheck${i}" name="avaliacaoTurmasCheck[${i}]" label="Questionários de Avaliação do Curso" list="avaliacaoTurmasCheckList" filtro="true"/>
				</div>
			</fieldset>

			<#assign i = i + 1/>
		</#list>
	</@ww.form>
	
	<div class="buttonGroup">
		<button type="button" onclick="aplicar()" class="btnAplicar"></button>
		<button onclick="javascript:history.go(-1);" class="btnVoltar" />
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
</body>
</html>