<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<html>
<head>
	<title>Plano de Desenvolvimento Individual (PDI) - Criação de Turmas para Treinamentos</title>
	
	<style type="text/css">
		@import url('<@ww.url value="/css/displaytag.css"/>');
		@import url('<@ww.url value="/css/jquery-ui/jquery-ui-1.8.9.custom.css"/>');
	
		#formDialog { display: none; }
		.tabela_colabs { float: right; width: 400px; height: 592px; overflow-y: scroll; overflow-x: hidden; border: 1px solid #7E9DB9; }
		.tabela_colabs .dados { width: 385px; border: none; }
		fieldset { padding: 10px; margin-bottom: 20px; }
		legend { font-weight: bold; }
	</style>

	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/TurmaDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/DiaTurmaDWR.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js"/>'></script>
	<script type="text/javascript" src="<@ww.url includeParams="none" value="/js/jQuery/jquery.price_format.1.6.min.js"/>"></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/jQuery/jquery-ui-1.8.6.custom.min.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/formataValores.js"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/json2.js"/>'></script>
	
	<#include "../ftl/mascarasImports.ftl" />
	
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
				
				if (id)
				{
					TurmaDWR.getTurma(id, populaTurma);
				}
				else
				{
					fset.find('input:text,input:checkbox,.listCheckBox').removeAttr('disabled').css('background','#fff');
					fset.find('input:text').val('');
					fset.find('#prevIni' + i + '_button > img, #prevFim' + i + '_button > img, #detalharCusto' + i + ' img').show();
					fset.find('.mascaraData').val('  /  /    ');
					fset.find(':checkbox').removeAttr('checked');
					fset.find('#listCheckBoxdiasTurmasCheck\\[' + i + '\\]').empty();
				}
			});
		});
		
		$.datepicker.setDefaults({ dayNames: ['domingo','segunda-feira','terça-feira','quarta-feira','quinta-feira','sexta-feira','sábado'] });
		
		function populaTurma(turma)
		{
			var fset = $('.fset_' + turma.curso.id);
			var i = fset.attr('indice');
			
			$("#desc" + i).val(turma.descricao);
			$("#horario" + i).val(turma.horario);
			$("#inst" + i).val(turma.instrutor);
			$("#instituicao" + i).val(turma.instituicao);
			$("#custo" + i).val(turma.custoFormatado);
			
			var datas = turma.periodoFormatado.split(' a ');
			$("#prevIni" + i).val(datas[0]);
			$("#prevFim" + i).val(datas[1]);
			
			populaDiasTurma(turma.dataPrevIni, turma.dataPrevFim, i);
			
			$(turma.diasTurma).each(function(k, diaTurma) {
				$("input[name='diasTurmasCheck[" + i + "]'][value='" + diaTurma.descricao + "']").attr("checked","checked");
			});
			
			$(turma.avaliacaoTurmas).each(function(j, avaliacaoTurma) {
				$("input[name='avaliacaoTurmasCheck[" + i + "]'][value='" + avaliacaoTurma.id + "']").attr("checked","checked");
			});
			
			fset.find('input:text,input:checkbox,.listCheckBox').attr('disabled','disabled').css('background','#ececec');
			fset.find('#prevIni' + i + '_button > img, #prevFim' + i + '_button > img, #detalharCusto' + i + ' img').hide();
		}
		
		function populaDiasTurma(dataIni, dataFim, i)
		{
			var atual = dataIni;
			var atualDesc;
			var datas = [];
			
			while (atual <= dataFim) 
			{
				datas.push($.datepicker.formatDate('dd/mm/yy - DD', atual));
				atual.setDate(atual.getDate() + 1);
			}
			
			addChecksArray('diasTurmasCheck[' + i + ']', datas);
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
		
		function populaDias(indice)
		{
			var dIni = document.getElementById('prevIni' + indice);
			var dFim = document.getElementById('prevFim' + indice);
	
			if(dIni.value != "  /  /    " && dFim.value != "  /  /    " && validaDate(dIni) && validaDate(dFim))
			{
				DWRUtil.useLoadingMessage('Carregando...');
				DiaTurmaDWR.getDias(dIni.value, 
									dFim.value, 
									function(dados) 
									{
										if(dados != null)
										{
											addChecks('diasTurmasCheck[' + indice + ']', dados);
										}
										else
											jAlert("Data inválida.");
									});
			}
		}
		
		function aplicar()
		{
			var obrigatorios = [];
			var validados = [];
			
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
				}
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
				<legend>
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
					
					Período:* <br />
					<@ww.datepicker required="true" name="turmas[${i}].dataPrevIni" id="prevIni${i}" liClass="liLeft" onblur="populaDias(${i});" onchange="populaDias(${i});"  cssClass="mascaraData validaDataIni"/>
					<@ww.label value="a" liClass="liLeft" />
					<@ww.datepicker required="true" name="turmas[${i}].dataPrevFim" id="prevFim${i}" onblur="populaDias(${i});" onchange="populaDias(${i});"  cssClass="mascaraData validaDataFim" />
					<@frt.checkListBox id="diasCheck${i}" name="diasTurmasCheck[${i}]" label="Dias Previstos *" list="diasTurmasCheckList"/>
					
					<@frt.checkListBox id="avaliacaoTurmasCheck${i}" name="avaliacaoTurmasCheck[${i}]" label="Questionários de Avaliação do Curso" list="avaliacaoTurmasCheckList"/>
				</div>
			</fieldset>

			<#assign i = i + 1/>
		</#list>
	</@ww.form>
	
	<div class="buttonGroup">
		<button type="button" onclick="aplicar()" class="btnAplicar"></button>
	</div>
	
	<div id="formDialog" title="Detalhamento dos custos">
		<br />
		
		Total R$  
		<span id="totalCustos"></span>
		
		<br /><br />
		
		<@display.table name="tipoDespesas" id="tipoDespesa" class="dados" style="width:450px;">
			<@display.column property="descricao" title="Descrição"/>
			<@display.column title="Custo (R$)" style="text-align: center; width:120px;">
				<input type="text" name="${tipoDespesa.id}" class="despesa moeda" maxlength="10" size="12" style="text-align:right; width: 90px;border:1px solid #7E9DB9;"/>
			</@display.column>
		</@display.table>
	</div>
</body>
</html>