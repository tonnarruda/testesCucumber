<script type='text/javascript'>
		var camposVisivel = '${campoExtraVisivelObrigadotorio.camposExtrasVisiveis?if_exists}';
		var camposObrigatorio = '${campoExtraVisivelObrigadotorio.camposExtrasObrigatorios?if_exists}';
		
		$(function() {
			var $obrigatorios = $('.configCampos input[name=camposObrigatorios]');
			var $visiveis = $('.configCampos input[name=camposVisivels]');
			
			abas(1);
			
			$(camposObrigatorio.split(',')).each(function (){
			    $('[value=' + this + ']').attr('checked', true);
			});
			
			$(camposVisivel.split(',')).each(function (){
			    $visiveis.filter('[value=' + this + ']').attr('checked', true);
			});
			
			$visiveis.change(function(){
                $obrigatorios.filter('[value=' + this.value + '][class!=desabilitado]').attr('disabled', !this.checked);
                $obrigatorios.filter('[class=' + this.value + '][class!=desabilitado]').attr('disabled', !this.checked);
            });

			$visiveis.filter(':not(:checked)').each(function(){
				$obrigatorios.filter('[value=' + this.value + ']').attr('disabled', true);
				$obrigatorios.filter('[class=' + this.value + '][class!=desabilitado]').attr('disabled', true);
			});
			
			
			$('.marcarTodos').click(function(e) {
				var marcado = $(this).attr('checked');
				
				$(this).parents("div[class*=content]").find("input[name='camposVisivels']:enabled").each(function() {
					$(this).attr('checked', marcado).change();
				}); 
				
				$("#marcador").text(marcado ? "Desmarcar Todos" : "Marcar Todos");
			});
			
		});
		
		
		function atualizarCamposExtras(empresaId){
			$("#box-candidato").load("configCamposExtras.action?entidade=candidato&empresa.id="+empresaId);
		}
</script>

<@ww.select label="Selecione a empresa para a qual deseja realizar a configuração" name="empresa.id" id="empresa" listKey="id" listValue="nome" list="empresas" cssStyle="width: 235px;" onchange="atualizarCamposExtras(this.value)";/>

<@ww.form name="form" action="updateConfigCamposExtras.action"  method="POST">
	<@ww.hidden name="campoExtraVisivelObrigadotorio.id" />
	<@ww.hidden name="campoExtraVisivelObrigadotorio.empresa.id" />
	<@ww.hidden name="campoExtraVisivelObrigadotorio.tipoConfiguracaoCampoExtra" />
	<div class="content1">
		<table id="abaExtra" cellspacing="0" class="configCampos">
		   <thead>		
			<tr>
				<th>
					<input id="marcarTodosCandidato1" class="marcarTodos check-visivel" type="checkbox" />
					<label for="marcarTodosCandidato1" class="label-visivel"></label>
				</th>
				<th>Campos</th>
				<th>Obrigatório</th>
			</tr>
		    </thead>		
		    <#list configuracaoCampoExtras as campoExtra>
			<tr>
				<td>
					<input id="visivel-candidato-${campoExtra.nome}" class="check-visivel" name="camposVisivels" value="${campoExtra.nome}" type="checkbox"/>
					<label for="visivel-candidato-${campoExtra.nome}" class="label-visivel"></label>
				</td>
				<td class="campo">${campoExtra.titulo}</td>
				<td><input type="checkbox" value="${campoExtra.nome}" name="camposObrigatorios"/></td>
			</tr>
			</#list>
		</table>
	</div>
</@ww.form>