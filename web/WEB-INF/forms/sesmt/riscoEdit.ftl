<#assign frt=JspTaglibs["/WEB-INF/tlds/fortes.tld"] />
<#assign display=JspTaglibs["/WEB-INF/tlds/displaytag.tld"] />

<html>
<head>
<@ww.head/>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/engine.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/util.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/FatorDeRiscoDWR.js?version=${versao}"/>'></script>
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/dwr/interface/UsuarioAjudaESocialDWR.js?version=${versao}"/>'></script>
	
	<script type='text/javascript' src='<@ww.url includeParams="none" value="/js/formModal.js?version=${versao}"/>'></script>

<style type="text/css">
	@import url('<@ww.url value="/css/displaytag.css?version=${versao}"/>');
	@import url('<@ww.url value="/css/formModal.css?version=${versao}"/>');
	@import url('<@ww.url value="/css/font-awesome.min.css?version=${versao}"/>');
	@import url('<@ww.url value="/css/buttons.css"/>');
		
#formDialog { display: none; width: 600px; list-style-type: none;}
	
#fatoresDeRisco-list .ui-selecting { background: #7BB5DF; }
#fatoresDeRisco-list .ui-selected { background: #5292C0; color: white; }
#fatoresDeRisco-list li{ margin: 3px; padding: 7px; display: block;margin-left: -35px; cursor: pointer;}

#divDescricaoFator{
	width: 480px; 
	text-align: justify; 
	border: 1px solid #BEBEBE; 
	padding: 10px; 
	margin-bottom: 4px;
    border-radius: 3px;
	background-color: #f3f3f3;
}
#descricaoFator{
    margin-left: 8px;
}

.box{
	height: auto;
    max-height: 600px;
}


.box-search {
   	padding: 5px;
   	display: none;
   	color: #A1A1A1;
	background: #F9F9F9;
	border-left: 1px solid #e7e7e7;
	border-right: 1px solid #e7e7e7;
}

.box-search .search {
   	width: 90%;
	border-radius: 3px;
	padding: 3px 2%;
	margin: 0 3%;
   	font-family: sans-serif !important;
	border-color: #C8C8C8;
}

.searchAcao {
	border-radius: 3px;
	padding: 0px 80px 0px 0px;
	margin-top: -25px;
	margin-right: 10px;
   	font-family: sans-serif !important;
	border-color: #C8C8C8;
	float: right;
	width: 200px;
}
	
</style>

<#if risco.id?exists>
	<title>Editar Risco</title>
	<#assign formAction="update.action"/>
	<#assign accessKey="A"/>
<#else>
	<title>Inserir Risco</title>
	<#assign formAction="insert.action"/>
	<#assign accessKey="I"/>
</#if>

	<#assign validarCampos="return validaFormulario('form', new Array('descricao','grupoRisco', 'grupoRiscoESocial', 'hidenFatorDeRisco'), null)"/>

	<script type="text/javascript">
		 $( function() {
		 	$("#fatoresDeRisco-list .ui-selected").live("dblclick", function(event){
				setFator();
			});
			
			constroiContainsIgnoreAccents();
		    $(".search").keyup(function(e){
		    	$(this).parents(".box").find(".column").find(".fatoresDeRiscoLi").hide();
		    	$(this).parents(".box").find(".column").find(".fatoresDeRiscoLi").find(".nome:contains-IgnoreAccents('"+$(this).val()+"')").parent().show();
		    });
		    $( "#fatoresDeRisco-list" ).selectable();
		    
		    setAjudaESocial('Estamos nos adequando as exigências impostas pelo Governo Federal para atender as normas do eSocial.<br><br>'+
           					'Desta forma, a partir da versão <strong>1.1.185.217</strong>, o cadastro de riscos passa a ter dois novos campos:<br><br>' + 
		    				'<strong>Tipo de risco eSocial:</strong> Classificação de riscos definida pelo eSocial na tabela 23 de seu leiaute.<br><br>'+
							'<strong>Fator de risco:</strong> Detalhamento dos riscos de acordo com a classificação do eSocial. Define todos os riscos que o colaborador ' + 
							'poderá estar exposto.', '<@ww.url value="/imgs/esocial.png"/>', 'imgAjudaEsocial');
									
			<#if exibeDialogAJuda>
				dialogAjudaESocial();
				UsuarioAjudaESocialDWR.saveUsuarioAjuda(${usuarioLogado.id}, "${telaAjuda?string}");
			</#if>
			
		 });
		 
		function constroiContainsIgnoreAccents() {
			var accent_map = {
            'á':'a',
            'à':'a',
            'â':'a',
            'å':'a',
            'ä':'a',
            'a':'a',
            'ã':'a',
            'ç':'c',
            'é':'e',
            'è':'e',
            'ê':'e',
            'ë':'e',
            'í':'i',
            'ì':'i',
            'î':'i',
            'ï':'i',
            'ñ':'n',
            'ó':'o',
            'ò':'o',
            'ô':'o',
            'ö':'o',
            'õ':'o',
            'ú':'u',
            'ù':'u',
            'û':'u',
            'ü':'u',};


			String.prototype.replaceEspecialChars = function() {
		        var ret = '', s = this.toString();
		        if (!s) { return ''; }
		        for (var i=0; i<s.length; i++) {
		            ret += accent_map[s.charAt(i)] || s.charAt(i);
		        }
		        return ret;
			};
		
		    String.prototype.contains = function(otherString) {
		        return this.toString().indexOf(otherString) !== -1;
		    };
			
			
		    $.extend($.expr[':'], {
		        'contains-IgnoreAccents' : function(elemt, idx, math) {
		            var expression1 = math[3].toLowerCase(),
		                semAcent1 = expression1.replaceEspecialChars(),
		                expression2 = elemt.innerHTML.toLowerCase(),
		                semAcent2 = expression2.replaceEspecialChars();
		
		            return semAcent2.contains(semAcent1);             
		        }
		    });
		}
		
		
		function onchangeGrupoRiscoESocial(){
			$('#divDescricaoFator').html('');
			
			$('#divDescricaoFator').append('<span id="selecionarFator" onclick="populaFatoresDeRisco();" style="cursor: pointer; color: #1c96e8;">'+
						'<i class="fa fa-plus-circle" aria-hidden="true" style="font-size: 16px;"></i> Selecione um fator de risco</span>');
        	$('#hidenFatorDeRisco').val('');
		}
	
		function openBox(){
			$('#formDialog').dialog({
								title:'Selecione um Fator de Risco', 	
								modal: true, 
								width: 750,
								buttons: 
										[
										    {
										        text: "Selecionar",
										        click: function() { 
										        	if($('#fatoresDeRisco-list .ui-selected').text() == ""){
										        		jAlert("Selecione um Fator de Risco.")
										        	}
										        	else{
										        		setFator();
										        	}  
										        }
										    },
										    {
										        text: "Sair",
										        click: function() { 
										        	$(this).dialog("close");
										        }
										    }
										]
							});
		}
	
		function setFator(){
			$('#divDescricaoFator').html('');
			$('#divDescricaoFator').append('<span id="selecionarFator" title="Modificar fator de risco" onclick="populaFatoresDeRisco();">'+  
						'<i class="fa fa-pencil-square-o" aria-hidden="true" style="color: #1c96e8;font-size: 16px;cursor: pointer;"></i>'+
						'</span>');
        	$('#divDescricaoFator').append('<span id="descricaoFator">' + $('#fatoresDeRisco-list .ui-selected').text() + '</span>');
        	$('#hidenFatorDeRisco').val($('#fatoresDeRisco-list .ui-selected').find('.fatorDeRisco').val());
        	$('#formDialog').dialog("close");
		}
	
		function populaFatoresDeRisco(){
			if($('#grupoRiscoESocial').val() == ''){
				jAlert("Selecione um 'Tipo de Risco do eSocial'");			
			}
			else{
				DWREngine.setAsync(true);
				DWRUtil.useLoadingMessage('Carregando...');
				FatorDeRiscoDWR.findByGrupoRiscoESocial($('#grupoRiscoESocial').val(), populaListFatorDeRisco);
			}
		}		
	
		function populaListFatorDeRisco(fatores) {
			$("#fatoresDeRisco ol").html("");			
			for (var countFator in fatores)
			{
				$("#fatoresDeRisco ol").append('<li class="ui-widget-content fatoresDeRiscoLi">' +
												'<input type="hidden" class="fatorDeRisco" value="'+fatores[countFator].id+'"/>'+
												'<div class="nome">'+fatores[countFator].descricaoComCodigo+'</div>'+
												'<div style="clear:both;float: none;"></div>'+
												'</li>');
			}
			openBox();
		}
		
		function submitForm(){
			var formValido = validaFormulario('form', new Array('descricao','grupoRisco', 'grupoRiscoESocial'), null, true);
			var fatorDeRisco = $('#hidenFatorDeRisco').val() != '' ? true : false;
			if(fatorDeRisco && !formValido)
				$('#divDescricaoFator').css('backgroundColor','#f3f3f3');
			if(formValido){
				if(!fatorDeRisco){
					$('#divDescricaoFator').css('backgroundColor','#ffeec2');
					jAlert("Selecione um fator de risco.")
				}
				else
					$("form").submit();
			}
		}

	</script>	
	
	
</head>
<body>
	<@ww.actionerror />
	
	<div id="formDialog">
		<div id="fatoresDeRisco" class="box">	
			<div class="box-search" style="display: block;">
				<input type="text" class="search" placeholder="Pesquisar...">
			</div>
			<div class="column">
			    <ol id="fatoresDeRisco-list">
			    </ol>
			</div>
		</div>
	</div>
	
	<@ww.form name="form" id="form" action="${formAction}" method="POST">
		<@ww.textfield label="Nome" name="risco.descricao" id="descricao" cssClass="inputNome" maxLength="100" cssStyle="width: 502px;" required="true"/>
		<@ww.select label="Tipo de Risco" name="risco.grupoRisco" id="grupoRisco" list="grupoRiscos" cssStyle="width: 502px;" headerKey="-1" headerValue="[Selecione...]" required="true"/>
		<@ww.select label="Tipo de Risco eSocial" name="risco.grupoRiscoESocial" id="grupoRiscoESocial" list="grupoRiscosESocial" cssStyle="width: 502px;" headerKey="" headerValue="[Selecione...]" required="true"  onchange="onchangeGrupoRiscoESocial();"/>
		
		<div style="margin-top: 4px;">
			<span>Fator de Risco:*<span>
			<div id="divDescricaoFator">
				<#if !risco.fatorDeRisco?exists>
					<span id="selecionarFator" onclick="populaFatoresDeRisco();" style="cursor: pointer; color: #1c96e8;">
						<i class="fa fa-plus-circle" aria-hidden="true" style="font-size: 16px;"></i> Selecione um fator de risco
					</span>
				
				<#else>
					<span id="selecionarFator" title="Modificar fator de risco" onclick="populaFatoresDeRisco();">
						<i class="fa fa-pencil-square-o" aria-hidden="true" style="color: #1c96e8;font-size: 16px;cursor: pointer;"></i>
					</span>
				</#if>
				<span id="descricaoFator">
				<#if risco.fatorDeRisco?exists && risco.fatorDeRisco.id?exists>
					${risco.fatorDeRisco.descricaoComCodigo}
				</#if>
				<span/>
			</div>
		</div>
		<@frt.checkListBox label="Equipamentos Obrigatórios (EPIs)" name="episCheck" list="episCheckList" filtro="true"/>
		<@ww.hidden label="Id" name="risco.id" />
		<@ww.hidden name="risco.fatorDeRisco.id" id="hidenFatorDeRisco"/>
		<@ww.token />
	</@ww.form>

	<div class="buttonGroup">
		<button type="button" onclick="submitForm()" accesskey="${accessKey}">Gravar</button>
		<button type="button" onclick="window.location='list.action'" accesskey="V">Cancelar</button>
	</div>
</body>
</html>