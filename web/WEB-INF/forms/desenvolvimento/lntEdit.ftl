<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />
<#assign authz=JspTaglibs["/WEB-INF/tlds/authz.tld"] />

<html>
	<head>
		<@ww.head/>
		<#if lnt.id?exists>
			<title>Editar LNT</title>
			<#assign formAction="update.action"/>
		<#else>
			<title>Inserir LNT</title>
			<#assign formAction="insert.action"/>
		</#if>
	
		<#assign validarCampos="return validaFormularioEPeriodo('form', new Array('descricao','dataInicio','dataFim','@areasCheck'), new Array('dataInicio','dataFim'))"/>
		
		<#include "../ftl/mascarasImports.ftl" />
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/AreaOrganizacionalDWR.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/LntDWR.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
		<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
		
		<style type="text/css">
			@import url('<@ww.url value="/css/lntList.css"/>');
		</style>
		
		<script type="text/javascript">
			var empresasPermitidasIds = new Array();
			var areasIdsDesabled = new Array();
			var validaParticipantes = !${lnt.prazoLntVencida?string}
			
			<#if empresaIds?exists>
				<#list empresaIds as empresaId>
					empresasPermitidasIds.push(${empresaId});
				</#list>
			</#if>
			
			var areasMarcadasIds = new Array();
			<#if areasCheckList?exists>
				<#list areasCheckList as areasChecked>
					<#if areasChecked.selecionado>
						areasMarcadasIds.push(${areasChecked.id});
					</#if>
				</#list>
			</#if>
			
			$(function() {
				eventoArea();
				
				<#if lnt.prazoLntVencida>
					$('#listCheckBoxempresasCheck').find('input').each(function(){$(this).attr('disabled', 'disabled')});
					$('#listCheckBoxempresasCheck').css('background-color', '#f7f7f7');
					
					$('#listCheckBoxareasCheck').find('input').each(function(){$(this).attr('disabled', 'disabled')});
					$('#listCheckBoxareasCheck').css('background-color', '#f7f7f7');
					$('.linkCheck').removeAttr('onclick');
				<#else>
					<#list empresasCheckListDialog as empresaCheckDisabled>
						$('#listCheckBoxempresasCheck').append('<input type="hidden" name="empresasCheck" value="' + ${empresaCheckDisabled.id} + '"/>');
					</#list>
					
					var i = 0;
					<#list areasCheckListDialog as areaCheckDisabled>
						$('#listCheckBoxareasCheck').append('<input class="areasidsDesabled" type="hidden" name="areasCheck" value="' + ${areaCheckDisabled.id} + '"/>');
						areasIdsDesabled[i] = new Array();
						areasIdsDesabled[i][0] = ${areaCheckDisabled.id};
						areasIdsDesabled[i][1] = '${areaCheckDisabled.nome}';
						i++;
					</#list>
				</#if>

				<#if lnt.finalizada>
					$('#form').append('<input class="mascaraData" type="hidden" name="lnt.dataInicio" value="' + $('#dataInicio').val() + '"/>')
					$('#dataInicio').attr('disabled', 'disabled');
					$('#dataInicio').css('background-color', '#f7f7f7');
					$('#dataInicio_button').remove();
					
					$('#form').append('<input class="mascaraData" type="hidden" name="lnt.dataFim" value="' + $('#dataFim').val() + '"/>')
					$('#dataFim').attr('disabled', 'disabled');
					$('#dataFim').css('background-color', '#f7f7f7');
					$('#dataFim_button').remove();
				</#if>
			});	
			
			function eventoArea(){
				$("input[name=areasCheck]").change(function(){
					addAreasMarcadasIds(parseInt(this.value));
					if($(this).is(":checked")){
						checarFilhos(this, true);
					}else{
						checarFilhos(this, false);
						removeAreasMarcadasIds(parseInt(this.value));
					}
					
					if($(this).attr("idareamae") != undefined)
						checarMae(this);
				});
			}
			
			function checarFilhos(areaMae, check){
				var idAreaMae = $(areaMae).val();
				$("input[idareamae="+idAreaMae+"]").each(function(){
					if(check)
						$(this).attr("checked", "checked");
					else
						$(this).removeAttr("checked");
					
				}).change();
			};
			
			function checarMae(areaFilha){
				var idAreaMae = $(areaFilha).attr("idareamae");
				if(idAreaMae != undefined){
					if($("input[idareamae="+idAreaMae+"]").size() == $("input[idareamae="+idAreaMae+"]:checked").size()){
						$("#checkGroupareasCheck"+idAreaMae).attr("checked", "checked");
						checarMae($("#checkGroupareasCheck"+idAreaMae));
					}else{
						$("#checkGroupareasCheck"+idAreaMae).removeAttr("checked");
						removeAreasMarcadasIds(parseInt(idAreaMae));
						checarMae($("#checkGroupareasCheck"+idAreaMae));
					}
				}
			};
			
			function enviaForm(){
				$("input[name=areasCheck][idareamae]").each(function(){
					$("input[name=areasCheck][value="+$(this).attr('idareamae')+"]").attr("name", "");
				});

				<#if lnt.id?exists>
					if(validaFormularioEPeriodo('form', new Array('descricao','dataInicio','dataFim','@areasCheck','@empresasCheck'), new Array('dataInicio','dataFim'), true)){
						var areasIds = $("input[name='areasCheck']:checked").map(function(){return $(this).val();}).get();										
						LntDWR.checaParticipantesNaLnt(${lnt.id},areasIds, function(retorno){
							if(!validaParticipantes || retorno == null || retorno.length == 0)				
								$('#form').submit();
							else
								dialog(retorno);
						});
						
					}					
				<#else>
					validaFormularioEPeriodo('form', new Array('descricao','dataInicio','dataFim','@areasCheck','@empresasCheck'), new Array('dataInicio','dataFim'));
				</#if>
			}
			
			function dialog(retorno){
				$('#relatorioDialog').html(retorno).dialog({ modal: true, width: 700, title: 'Aviso',
						  		buttons: 
								[
								    {
								        text: "Ok",
								        click: function() { 
								        	$('#form').submit();
								        	$(this).dialog("close");
								        }
								        
								    },
								    {
								        text: "Cancelar",
								        click: function() { $(this).dialog("close"); }
								    }
								]
							});
			}
			
			function populaAreas(){
				DWRUtil.useLoadingMessage('Carregando...');
				var empresaIds = $("input[name='empresasCheck']:checked:enabled").map(function(){return $(this).val();}).get(); 
				if(empresaIds.length > 0)
					AreaOrganizacionalDWR.getCheckboxByEmpresas(createListAreas, empresaIds);
				else{
					$('#listCheckBoxareasCheck > label').remove();
					repopulaAreasDesabled();
				}
			}	
			
			function createListAreas(data){
				addChecksCheckBox('areasCheck', data, areasMarcadasIds);
				eventoArea();
				$("input[name=areasCheck]:checked").each(function(){
					checarFilhos(this, true);
					if($(this).attr("idareamae") != undefined)
						checarMae(this);
				});
				repopulaAreasDesabled();
			}
			
			function addAreasMarcadasIds(valor){
				if(areasMarcadasIds && areasMarcadasIds.indexOf(valor) == -1) 
					areasMarcadasIds.push(valor);
			}
			
			function removeAreasMarcadasIds(valor){
				for(var i = 0; i < areasMarcadasIds.length ; i++) {
				    if(areasMarcadasIds[i] === valor) {
				       areasMarcadasIds.splice(i, 1);
				    }
				}
			}
			
			function repopulaAreasDesabled(){
				if(areasIdsDesabled && areasIdsDesabled.length > 0){
					for (var i = 0 ;i < areasIdsDesabled.length; i++){
						$('#listCheckBoxareasCheck').append('<input class="areasidsDesabled" type="hidden" name="areasCheck" value="' + areasIdsDesabled[i][0] + '"/>'
						+ '<label for="checkGroupareasCheck' + areasIdsDesabled[i][0] + '">'
						+ '<input name="areasCheck" value="' + areasIdsDesabled[i][0] + '" type="checkbox" disabled="disabled" id="checkGroupareasCheck' + areasIdsDesabled[i][0] + '" checked="" onclick=""/>'
						+ areasIdsDesabled[i][1] + '</label>');
					}
				}
			}
		</script>
		
	</head>
	<body>
		
		<#if lnt.dataInicio?exists>
			<#assign dataInicio=lnt.dataInicio?date />
		<#else>
			<#assign dataInicio="" />
		</#if>
		<#if lnt.dataFim?exists>
			<#assign dataFim=lnt.dataFim?date />
		<#else>
			<#assign dataFim="" />
		</#if>
	
		<@ww.actionerror />
		<@ww.actionmessage />
		
		<@ww.form id="form" name="form" action="${formAction}" method="POST">
			<@ww.hidden name="lnt.id" />
			<@ww.hidden name="lnt.empresa.id" />
			<@ww.hidden name="lnt.dataFinalizada" />
			
			<@ww.textfield label="Descrição" name="lnt.descricao" id="descricao" required="true" cssStyle="width:500px;" maxLength="70"/>
			<label>Período:*</label><br />
			<@ww.datepicker value="${dataInicio}" name="lnt.dataInicio" id="dataInicio" cssClass="mascaraData validaDataIni" after="a" liClass="liLeft" required="true"/>
			<@ww.datepicker value="${dataFim}" name="lnt.dataFim" id="dataFim" cssClass="mascaraData validaDataFim" required="true"/>
			<@frt.checkListBox label="Empresas" name="empresasCheck" id="empresasCheck" list="empresasCheckList" filtro="true" onClick="populaAreas();" required="true"/>
			<@frt.checkListBox name="areasCheck" id="areasCheck" label="Áreas Organizacionais" list="areasCheckList" required="true"  filtro="true" selectAtivoInativo="true"/>
			<@ww.token />
		</@ww.form>
	
		<div class="buttonGroup">
			<button onclick="enviaForm()" type="button">Gravar</button>
			<button onclick="window.location='list.action'" type="button">Voltar</button>
		</div>
		
		<script type="text/javascript">
			$(function() {
				$("input[name=areasCheck]:checked").change();
			});
		</script>
				
		<div id="relatorioDialog" style="display:none;">
			<ul></ul>
		</div>
	</body>
</html>
