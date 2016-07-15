<script type='text/javascript'>
		var camposVisivel = '${parametrosDoSistema.camposColaboradorVisivel}';
		var camposObrigatorio = '${parametrosDoSistema.camposColaboradorObrigatorio}';
		
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
			
			$('#bairroNome').click(function(e) {
				if ($(this).attr('checked'))
					$('#cidade').attr('checked', true);
			});
			
			$('#cidade').click(function(e) {
				if ($(this).attr('checked') == false)
					$('#bairroNome').removeAttr('checked');
			});
			
			$('.marcarTodos').click(function(e) {
				var marcado = $(this).attr('checked');
				
				$(this).parents("div[class*=content]").find("input[name='camposVisivels']:enabled").not("[type=hidden]").each(function() {
					$(this).attr('checked', marcado).change();
				}); 
				
				$("#marcador").text(marcado ? "Desmarcar Todos" : "Marcar Todos");
			});
		});
</script>

<div class="abas">
	<div class="option-aba1 abaDadosPessoais" style="border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: rgb(246, 246, 246); background: rgb(246, 246, 246);"><a href="javascript: abas(1)">Dados Pessoais</a></div>
	<div class="option-aba2 abaDadosFuncionais" style="border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: rgb(246, 246, 246); background: rgb(246, 246, 246);"><a href="javascript: abas(2)">Dados Funcionais</a></div>
	<div class="option-aba3 abaFormacaoEscolar" style="border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: rgb(204, 204, 204); background: rgb(217, 217, 217);"><a href="javascript: abas(3)">Formação Escolar</a></div>
	<div class="option-aba4 abaExperiencias" style="border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: rgb(204, 204, 204); background: rgb(217, 217, 217);"><a href="javascript: abas(4)">Experiências</a></div>
	<div class="option-aba5 abaDocumentos" style="border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: rgb(204, 204, 204); background: rgb(217, 217, 217);"><a href="javascript: abas(5)">Documentos</a></div>
	<div class="option-aba6 abaModelosAvaliacao" style="display: none;"><a href="javascript: abas(6)">Modelos de Avaliação</a></div>
	
	<#if habilitaCampoExtra && configuracaoCampoExtras?exists && configuracaoCampoExtras?size != 0>
		<div class="option-aba7 abaExtra"><a href="javascript: abas(7)">Extra</a></div>
	</#if>
</div>

<@ww.form name="form" action="updateCamposColaborador.action"  method="POST">
	<@ww.hidden id="camposTabs" name="camposTabs"/>
	
	<div class="content1">
		<table id="abaDadosPessoais" cellspacing="0" class="configCampos">
		    <thead>
			<tr>
				<th>
					<input id="marcarTodos1" class="marcarTodos check-visivel" type="checkbox" />
    				<label for="marcarTodos1" class="label-visivel"></label>
				</th>
				<th>Campos</th>
				<th>Obrigatório</th>
			</tr>
			</thead>
			<tbody>
			<tr class="desabilitado">
				<td>
					<input id="visivel-nome" class="check-visivel" name="camposVisivels" value="nome" type="checkbox" checked disabled />
    				<label for="visivel-nome" class="label-visivel"></label>
				</td>
				<td class="campo">Nome</td>
				<td><input type="checkbox" value="nome" name="camposObrigatorios" checked disabled class="desabilitado"/></td>
				
				<input type="hidden" id="nomeObr" value="nome" name="camposObrigatorios"/>
				<input type="hidden" id="nomeVis" value="nome" name="camposVisivels" />
			</tr>
			<tr class="desabilitado">
				<td>
					<input id="visivel-nomeComercial" class="check-visivel" name="camposVisivels" value="nomeComercial" type="checkbox" checked disabled />
    				<label for="visivel-nomeComercial" class="label-visivel"></label>
				</td>
				<td class="campo">Nome Comercial</td>
				<td><input type="checkbox" value="nomeComercial" name="camposObrigatorios" checked disabled class="desabilitado"/></td>
				
				<input type="hidden" id="nomeComercialObr" value="nomeComercial" name="camposObrigatorios"/>
				<input type="hidden" id="nomeComercialVis" value="nomeComercial" name="camposVisivels" />
			</tr>
			<tr class="desabilitado">
				<td>
					<input id="visivel-nascimento" class="check-visivel" name="camposVisivels" value="nascimento" checked disabled type="checkbox"/>
    				<label for="visivel-nascimento" class="label-visivel"></label>
				</td>
				<td class="campo">Nascimento</td>
				<td><input type="checkbox" value="nascimento" name="camposObrigatorios" checked disabled /></td>
				
				<input type="hidden" id="nascimentoObr" value="nascimento" name="camposObrigatorios"/>
				<input type="hidden" id="nascimentoVis" value="nascimento" name="camposVisivels" />
			</tr>
			<tr>
				<td>
					<input id="visivel-sexo" class="check-visivel" name="camposVisivels" value="sexo" type="checkbox" />
    				<label for="visivel-sexo" class="label-visivel"></label>
				</td>
				<td class="campo">Sexo</td>
				<td><input type="checkbox" value="sexo" name="camposObrigatorios" /></td>
			</tr>
			<tr class="desabilitado">
				<td>
					<input id="visivel-cpf" class="check-visivel" name="camposVisivels" value="cpf" type="checkbox" checked disabled />
    				<label for="visivel-cpf" class="label-visivel"></label>
				</td>
				<td class="campo">CPF</td>
				<td><input type="checkbox" value="cpf" name="camposObrigatorios" checked disabled  class="desabilitado"/></td>
				
				<input type="hidden"  id="cpfObr" value="cpf" name="camposObrigatorios" />
				<input type="hidden"  id="cpfVis_" value="cpf" name="camposVisivels" />
			</tr>
			<tr class="desabilitado">
				<td>
					<input id="visivel-escolaridade" class="check-visivel" name="camposVisivels" value="escolaridade" type="checkbox" checked disabled />
    				<label for="visivel-escolaridade" class="label-visivel"></label>
				</td>
				<td class="campo">Escolaridade</td>
				<td><input type="checkbox" value="escolaridade" name="camposObrigatorios" checked disabled class="desabilitado"/></td>
				
				<input type="hidden"  id="escolaridadeObr" value="escolaridade" name="camposObrigatorios" />
				<input type="hidden"  id="escolaridadeVis_" value="escolaridade" name="camposVisivels" />
			</tr>
			<tr class="desabilitado">
				<td id="endRight">
					<input id="visivel-endereco" class="check-visivel" name="camposVisivels" value="endereco" type="checkbox" checked disabled />
    				<label for="visivel-endereco" class="label-visivel"></label>
				</td>
				<td class="campo">Endereço</td>
				<td id="endLeft"></td>
				
				<input type="hidden" id="enderecoVis" value="endereco" name="camposVisivels" />
			</tr>
			<tr>
				<td></td>
				<td class="campo" class="grupoObrigatorio" >CEP</td>
				<td>
					<input name="camposObrigatorios" value="cep" type="checkbox" class="endereco"/>
				</td>
			</tr>
			<tr class="desabilitado">
				<td></td>
				<td class="campo" class="grupoObrigatorio">Logradouro</td>
				<td>
					<input name="camposObrigatorios" value="ende" type="checkbox" checked disabled class="endereco"/>
					<input type="hidden" id="endObr" value="ende" name="camposObrigatorios"/>
				</td>
			</tr>
			<tr class="desabilitado">
				<td></td>
				<td class="campo" class="grupoObrigatorio">Nº</td>
				<td>
					<input name="camposObrigatorios" value="num" type="checkbox" checked disabled class="endereco"/>
					<input type="hidden" id="numObr" value="num" name="camposObrigatorios"/>
				</td>
			</tr>
			<tr>
				<td></td>
				<td class="campo" class="grupoObrigatorio">Complemento</td>
				<td>
					<input name="camposObrigatorios" value="complemento" type="checkbox" class="endereco"/>
				</td>
			</tr>
			<tr>
				<td></td>
				<td class="campo" class="grupoObrigatorio">Bairro</td>
				<td>
					<input name="camposObrigatorios" value="bairroNome" type="checkbox" class="endereco"/>
				</td>
			</tr>
			<tr class="desabilitado">
				<td></td>
				<td class="campo" class="grupoObrigatorio">Estado/Cidade</td>
				<td>
					<input name="camposObrigatorios" value="cidade" type="checkbox" checked disabled class="endereco"/>
					<input type="hidden" id="cidadeObr" value="cidade" name="camposObrigatorios"/>
				</td>
			</tr>

			<tr>
				<td>
					<input id="visivel-email" class="check-visivel" name="camposVisivels" value="email" type="checkbox"/>
    				<label for="visivel-email" class="label-visivel"></label>
				</td>
				<td class="campo">E-mail</td>
				<td><input type="checkbox" value="email" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-fone" class="check-visivel" name="camposVisivels" value="fone" type="checkbox"/>
    				<label for="visivel-fone" class="label-visivel"></label>
				</td>
				<td class="campo">Telefone</td>
				<td><input type="checkbox" value="fone" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-celular" class="check-visivel" name="camposVisivels" value="celular" type="checkbox"/>
    				<label for="visivel-celular" class="label-visivel"></label>
				</td>
				<td class="campo">Celular</td>
				<td><input type="checkbox" value="celular" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-estadoCivil" class="check-visivel" name="camposVisivels" value="estadoCivil" type="checkbox"/>
    				<label for="visivel-estadoCivil" class="label-visivel"></label>
				</td>
				<td class="campo">Estado Civil</td>
				<td><input type="checkbox" value="estadoCivil" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-qtdFilhos" class="check-visivel" name="camposVisivels" value="qtdFilhos" type="checkbox"/>
    				<label for="visivel-qtdFilhos" class="label-visivel"></label>
				</td>
				<td class="campo">Qtd. Filhos</td>
				<td><input type="checkbox" value="qtdFilhos" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-nomeConjuge" class="check-visivel" name="camposVisivels" value="nomeConjuge" type="checkbox"/>
    				<label for="visivel-nomeConjuge" class="label-visivel"></label>
				</td>
				<td class="campo">Nome do Cônjuge</td>
				<td><input type="checkbox" value="nomeConjuge" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-nomePai" class="check-visivel" name="camposVisivels" value="nomePai" type="checkbox"/>
    				<label for="visivel-nomePai" class="label-visivel"></label>
				</td>
				<td class="campo">Nome do Pai</td>
				<td><input type="checkbox" value="nomePai" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-nomeMae" class="check-visivel" name="camposVisivels" value="nomeMae" type="checkbox"/>
    				<label for="visivel-nomeMae" class="label-visivel"></label>
				</td>
				<td class="campo">Nome da Mãe</td>
				<td><input type="checkbox" value="nomeMae" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-deficiencia" class="check-visivel" name="camposVisivels" value="deficiencia" type="checkbox"/>
    				<label for="visivel-deficiencia" class="label-visivel"></label>
				</td>
				<td class="campo">Deficiência</td>
				<td><input type="checkbox" value="deficiencia" name="camposObrigatorios" /></td>
			</tr>
			</tbody>
		</table>
	</div>
	
	<div class="content2">
		<table id="abaDadosFuncionais" cellspacing="0" class="configCampos">
		    <thead>		
			<tr>
				<th>
					<input id="marcarTodos2" class="marcarTodos check-visivel" type="checkbox" />
    				<label for="marcarTodos2" class="label-visivel"></label>
				</th>
				<th>Campos</th>
				<th>Obrigatório</th>
			</tr>
			</thead>		
			<tr>
				<td>
					<input id="visivel-matricula" class="check-visivel" name="camposVisivels" value="matricula" type="checkbox"/>
    				<label for="visivel-matricula" class="label-visivel"></label>
				</td>
				<td class="campo">Matrícula</td>
				<td><input type="checkbox" value="matricula" name="camposObrigatorios"/></td>
			</tr>
			<tr class="desabilitado">
				<td>
					<input id="visivel-dt_admissao" class="check-visivel" name="camposVisivels" value="dt_admissao" checked disabled type="checkbox"/>
    				<label for="visivel-dt_admissao" class="label-visivel"></label>
				</td>
				<td class="campo">Admissão</td>
				<td><input type="checkbox" value="dt_admissao" checked disabled name="camposObrigatorios" /></td>
				
				<input type="hidden"  id="dt_admissaoObr" value="dt_admissao" name="camposObrigatorios" />
				<input type="hidden"  id="dt_admissaoVis_" value="dt_admissao" name="camposVisivels" />
			</tr>
			<tr>
				<td>
					<input id="visivel-vinculo" class="check-visivel" name="camposVisivels" value="vinculo" type="checkbox"/>
    				<label for="visivel-vinculo" class="label-visivel"></label>
				</td>
				<td class="campo">Colocação</td>
				<td><input type="checkbox" value="vinculo" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-dt_encerramentoContrato" class="check-visivel" name="camposVisivels" value="dt_encerramentoContrato" type="checkbox"/>
    				<label for="visivel-dt_encerramentoContrato" class="label-visivel"></label>
				</td>
				<td class="campo">Encerramento do contrato</td>
				<td><input type="checkbox" value="dt_encerramentoContrato" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-regimeRevezamento" class="check-visivel" name="camposVisivels" value="regimeRevezamento" type="checkbox"/>
    				<label for="visivel-regimeRevezamento" class="label-visivel"></label>
				</td>
				<td class="campo">Regime de Revezamento (PPP)</td>
				<td><input type="checkbox" value="regimeRevezamento" name="camposObrigatorios" /></td>
			</tr>
		</table>
	</div>
	
	<div class="content3">
		<table id="abaFormacaoEscolar" cellspacing="0" class="configCampos">
		    <thead>		
			<tr>
				<th>
					<input id="marcarTodos3" class="marcarTodos check-visivel" type="checkbox" />
    				<label for="marcarTodos3" class="label-visivel"></label>
				</th>
				<th>Campos</th>
				<th>Obrigatório</th>
			</tr>
			</thead>
			<tbody>
			<tr>
				<td>
					<input id="visivel-formacao" class="check-visivel" name="camposVisivels" value="formacao" type="checkbox"/>
    				<label for="visivel-formacao" class="label-visivel"></label>
				</td>
				<td class="campo">Formação Escolar</td>
				<td><input type="checkbox" value="formacao" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-idioma" class="check-visivel" name="camposVisivels" value="idioma" type="checkbox"/>
    				<label for="visivel-idioma" class="label-visivel"></label>
				</td>
				<td class="campo">Idiomas</td>
				<td><input type="checkbox" value="idioma" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-desCursos" class="check-visivel" name="camposVisivels" value="desCursos" type="checkbox"/>
    				<label for="visivel-desCursos" class="label-visivel"></label>
				</td>
				<td class="campo">Outros Cursos</td>
				<td><input type="checkbox" value="desCursos" name="camposObrigatorios" /></td>
			</tr>
		</table>
	</div>
	
	<div class="content4">
		<table id="abaExperiencias" cellspacing="0" class="configCampos">
		    <thead>		
			<tr>
				<th>
					<input id="marcarTodos4" class="marcarTodos check-visivel" type="checkbox" />
    				<label for="marcarTodos4" class="label-visivel"></label>
				</th>
				<th>Campos</th>
				<th>Obrigatório</th>
			</tr>
			</thead>			
			<tr>
				<td>
					<input id="visivel-expProfissional" class="check-visivel" name="camposVisivels" value="expProfissional" type="checkbox"/>
    				<label for="visivel-expProfissional" class="label-visivel"></label>
				</td>
				<td class="campo">Experiência Profissional</td>
				<td><input type="checkbox" value="expProfissional" name="camposObrigatorios"/></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-infoAdicionais" class="check-visivel" name="camposVisivels" value="obs" type="checkbox"/>
    				<label for="visivel-infoAdicionais" class="label-visivel"></label>
				</td>
				<td class="campo">Informações Adicionais</td>
				<td><input type="checkbox" value="obs" name="camposObrigatorios"/></td>
			</tr>
		</table>
	</div>
	
	<div class="content5">
		<table id="abaDocumentos" cellspacing="0" class="configCampos">
		    <thead>		
			<tr>
				<th>
					<input id="marcarTodos5" class="marcarTodos check-visivel" type="checkbox" />
    				<label for="marcarTodos5" class="label-visivel"></label>
				</th>
				<th>Campos</th>
				<th>Obrigatório</th>
			</tr>
			</thead>			
			<tr>
				<td>
					<input id="visivel-identidade" class="check-visivel" name="camposVisivels" value="rg" type="checkbox"/>
    				<label for="visivel-identidade" class="label-visivel"></label>
				</td>
				<td class="campo">Identidade</td>
				<td><input type="checkbox" value="rg" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-carteiraHabilitacao" class="check-visivel" name="camposVisivels" value="carteiraHabilitacao" type="checkbox"/>
    				<label for="visivel-carteiraHabilitacao" class="label-visivel"></label>
				</td>
				<td class="campo">Carteira de Habilitação</td>
				<td><input type="checkbox" value="carteiraHabilitacao" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-tituloEleitoral" class="check-visivel" name="camposVisivels" value="titEleitNumero" type="checkbox"/>
    				<label for="visivel-tituloEleitoral" class="label-visivel"></label>
				</td>
				<td class="campo">Título Eleitoral</td>
				<td><input type="checkbox" value="titEleitNumero" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-certificadoMilitar" class="check-visivel" name="camposVisivels" value="certMilNumero" type="checkbox"/>
    				<label for="visivel-certificadoMilitar" class="label-visivel"></label>
				</td>
				<td class="campo">Certificado Militar</td>
				<td><input type="checkbox" value="certMilNumero" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-ctps" class="check-visivel" name="camposVisivels" value="ctpsNumero" type="checkbox"/>
    				<label for="visivel-ctps" class="label-visivel"></label>
				</td>
				<td class="campo">CTPS - Carteira de Trabalho e Previdência Social</td>
				<td><input type="checkbox" value="ctpsNumero" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-pis" class="check-visivel" name="camposVisivels" value="pis" type="checkbox"/>
    				<label for="visivel-pis" class="label-visivel"></label>
				</td>
				<td class="campo">PIS - Programa de Integração Social</td>
				<td><input type="checkbox" value="pis" name="camposObrigatorios" /></td>
			</tr>
		</table>
	</div>
	
	<div class="content6">
		<table id="abaModelosAvaliacao" cellspacing="0" class="configCampos">
		   <thead>		
			<tr>
				<th>
					<input id="marcarTodos6" class="marcarTodos check-visivel" type="checkbox" />
    				<label for="marcarTodos6" class="label-visivel"></label>
				</th>
				<th>Campos</th>
				<th>Obrigatório</th>
			</tr>
		    </thead>		
			<tr>
				<td>
					<input id="visivel-modelosAvaliacao" class="check-visivel" name="camposVisivels" value="modelosAvaliacao" type="checkbox"/>
    				<label for="visivel-modelosAvaliacao" class="label-visivel"></label>
				</td>
				<td class="campo">Modelo do Acompanhamento do Período de Experiência</td>
				<td><input disabled type="checkbox" value="modelosAvaliacao" name=""/></td>
			</tr>
		</table>
	</div>

	<#if habilitaCampoExtra && configuracaoCampoExtras?exists && configuracaoCampoExtras?size != 0>
		<div class="content7">
			<table id="abaExtra" cellspacing="0" class="configCampos">
			   <thead>		
				<tr>
					<th>
						<input id="marcarTodos7" class="marcarTodos check-visivel" type="checkbox" />
						<label for="marcarTodos7" class="label-visivel"></label>
					</th>
					<th>Campos</th>
					<th>Obrigatório</th>
				</tr>
			    </thead>		
			    <#list configuracaoCampoExtras as campoExtra>
				<tr>
					<td>
						<input id="visivel-${campoExtra.nome}" class="check-visivel" name="camposVisivels" value="${campoExtra.nome}" type="checkbox"/>
						<label for="visivel-${campoExtra.nome}" class="label-visivel"></label>
					</td>
					<td class="campo">${campoExtra.titulo}</td>
					<td><input type="checkbox" value="${campoExtra.nome}" name="camposObrigatorios"/></td>
				</tr>
				</#list>
			</table>
		</div>
	</#if>
</@ww.form>