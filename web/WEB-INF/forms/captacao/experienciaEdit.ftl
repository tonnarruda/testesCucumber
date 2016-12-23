	<#if empresaId?exists>
		<#assign empresaIdTmp = empresaId />
	<#else>
		<#assign empresaIdTmp = ""/>
	</#if>
	
	<#if experiencia.id?exists>
		<#assign formActionExp="update.action"/>
		<#assign labelExperiencia="Atualizar Experiência"/>
	<#else>
		<#assign formActionExp="insert.action"/>
		<#assign labelExperiencia="Inserir Experiência"/>
	</#if>
	
	<#if experiencia.funcao?exists>
		<#assign funcaoNome = cargo.nomeMercado/>
	<#else>
		<#assign funcaoNome = "" />
	</#if>
	
	<#if checkNomeCargo>
		<#assign nomeCargoDesativado = 'false'/>
		<#assign selectCargoDesativado = 'true'/>
	<#else>
		<#assign nomeCargoDesativado = 'true'/>
		<#assign selectCargoDesativado = 'false'/>
	</#if>
	
	
	<#if experiencia.cargo?exists && experiencia.cargo.id?exists>
		<#assign nomeCargoDesativado = 'true'/>
		<#assign selectCargoDesativado = 'false'/>
	</#if>
	
	<#if experiencia?exists && experiencia.dataAdmissao?exists>
		<#assign dataAdm = experiencia.dataAdmissao?date/>
	<#else>
		<#assign dataAdm = ""/>
	</#if>
	
	<#if experiencia?exists && experiencia.dataDesligamento?exists>
		<#assign dataDesl = experiencia.dataDesligamento?date/>
	<#else>
		<#assign dataDesl = ""/>
	</#if>
	
	<script src='<@ww.url includeParams="none" value="/js/functions.js?version=${versao}"/>'></script>
	<script src='<@ww.url includeParams="none" value="/js/fortes.js?version=${versao}"/>'></script>
	
	<script type='text/javascript'>
	    $(function(){
	    	$('#contatoEmpresa').mask("(99) 99999-9999")
		        .focusout(function (event) {  
		            var target, phone, element;  
		            target = (event.currentTarget) ? event.currentTarget : event.srcElement;  
		            phone = target.value.replace(/\D/g, '');
		            element = $(target);  
		            element.unmask();  
		            element.mask("(99) 99999-9999");  
		        });
	    });
	    
	    function voltarExperiencia()
	    {
	    	$("#expProfissional").load('<@ww.url includeParams="none" value="/captacao/experiencia/list.action?empresaId=${empresaIdTmp}"/>');
	    }
	    
	    function validarFormExperiencia()
	    {
			if(!document.getElementById('cargoNomeId').disabled && document.getElementById('cargoNomeId').value == "")
			{
				jAlert("Selecione um cargo.");
				return false;
			}
			
			if(document.getElementById('cargoNomeId').disabled && document.getElementById('nomeCargo').value.trim() == "")
			{
				jAlert("Informe o nome do cargo.");
				document.getElementById('nomeCargo').focus();
				return false;
			}
			
			return validaFormularioEPeriodo('frmExperiencia', new Array('exp_empresa','dt_admissao'), new Array('dt_admissao','dt_desligamento','salario'), true);
	    }
	    
	    <!-- ajuste necessario para limpar a data de desligamento se estiver vazia ( /  / ) -->
	    function limpaData(frm)
	    {
			if (frm.dt_desligamento.value == '  /  /    ')
			{
		    	frm.dt_desligamento.value = frm.dt_desligamento.value.replace('/','');
				frm.dt_desligamento.value = frm.dt_desligamento.value.replace('/','');
				frm.dt_desligamento.value = frm.dt_desligamento.value.trim();
			}
	    }	   
	</script>
	
	<style type="text/css">
		@import url('<@ww.url includeParams="none" value="/css/fortes.css?version=${versao}"/>');
	</style>

	<#assign caminhoPopUp><@ww.url includeParams="none" value="/captacao/candidato/listCbo.action" /></#assign>
	
	<div style="width:99%; display:block; border:1px solid #CCCCCC;">
		<h3 style="padding:10px;">${labelExperiencia}</h3>
		<@ww.actionerror />
		<@ww.form id="frmExperiencia" name="frmExperiencia" onsubmit="limpaData(this);" action="/captacao/experiencia/${formActionExp}" method="POST" cssStyle="width:99%; padding:5px;">
			<@ww.textfield label="Empresa" name="experiencia.empresa" theme="css_xhtml" cssClass="inputNome" maxLength="100" id="exp_empresa" required="true"/>
		 	<@ww.textfield label="Contato Empresa"  name="experiencia.contatoEmpresa" id="contatoEmpresa" cssStyle="width:120px;"/>
		 	<@ww.select label="Cargo" name="experiencia.cargo.id" theme="css_xhtml" id="cargoNomeId" list="nomeCargos" cssClass="inputNome" listKey="id" listValue="nomeMercado" disabled="${selectCargoDesativado}" headerKey="" headerValue="Selecione..." onchange="this.form.cargoNomeMercado.value = options[selectedIndex].text;" required="true"/>
		 	<@ww.checkbox label="Outro Cargo" onclick="ativaOuDesativa('nomeCargo');ativaOuDesativa('cargoNomeId');" name="checkNomeCargo" theme="css_xhtml" id="chk" />
			<@ww.textfield disabled="${nomeCargoDesativado}" name="experiencia.nomeMercado" theme="css_xhtml" cssClass="inputNome" maxLength="100" id="nomeCargo"/>
	
			<#--Veja complemento destes datepickers no final da página-->
			<@ww.datepicker label="Data de Admissão" theme="css_xhtml" id="dt_admissao" name="experiencia.dataAdmissao" value="${dataAdm}" cssClass="mascaraData validaDataIni" required="true"/>
			<@ww.textfield label="Salário" theme="css_xhtml" id="salario" name="experiencia.salario" maxLength="12" cssClass="currency" cssStyle="width:85px; text-align:right;"/>
			<@ww.datepicker label="Data de Desligamento" theme="css_xhtml" id="dt_desligamento" name="experiencia.dataDesligamento" value="${dataDesl}" cssClass="mascaraData validaDataFim"/>
			<@ww.textfield label="Motivo da saída" theme="css_xhtml" cssClass="inputNome" id="motivo_saida" name="experiencia.motivoSaida" maxLength="100" />
			
			<@ww.textarea label="Observações" name="experiencia.observacao" cssStyle="width:685px;" theme="css_xhtml"/>
			
			<#if experiencia?exists && experiencia.nomeMercado?exists>
				<#assign nomeMercadoCargo = experiencia.nomeMercado/>
			<#else>
				<#assign nomeMercadoCargo = ""/>
			</#if>
			
			<@ww.hidden name="experiencia.cargo.nomeMercado" id="cargoNomeMercado" value="${nomeMercadoCargo}"/>
			<@ww.hidden name="experiencia.id" />
			<@ww.hidden name="empresaId" />
	
			<@ww.submit value=" " cssClass="btnGravar grayBG" cssStyle="float:left; display:block;"/>
			<input type="button" onclick="voltarExperiencia();" class="btnCancelar grayBG" />
	
		</@ww.form>
	</div>
	<script type="text/javascript">
		$(function(){
			$("#dt_admissao").mask("99/99/9999",{placeholder:" "});
			$("#dt_desligamento").mask("99/99/9999",{placeholder:" "});
		});
	</script>

<br />