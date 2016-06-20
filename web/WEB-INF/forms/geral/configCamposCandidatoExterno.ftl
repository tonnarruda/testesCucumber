<script type='text/javascript'>
		var camposVisivel = '${parametrosDoSistema.camposCandidatoExternoVisivel}';
		var camposObrigatorio = '${parametrosDoSistema.camposCandidatoExternoObrigatorio}';
		
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
				
				$(this).parents("div[class*=content]").find("input[name='camposVisivels']:enabled").each(function() {
					$(this).attr('checked', marcado).change();
				}); 
				
				$("#marcador").text(marcado ? "Desmarcar Todos" : "Marcar Todos");
			});
		});
</script>

<div class="abas">
	<div class="option-aba1 abaDadosPessoais" style="border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: rgb(246, 246, 246); background: rgb(246, 246, 246);"><a href="javascript: abas(1)">Dados Pessoais</a></div>
	<div class="option-aba2 abaFormacaoEscolar" style="border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: rgb(204, 204, 204); background: rgb(217, 217, 217);"><a href="javascript: abas(2)">Formação Escolar</a></div>
	<div class="option-aba3 abaPerfilProfissional" style="border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: rgb(204, 204, 204); background: rgb(217, 217, 217);"><a href="javascript: abas(3)">Perfil Profissional</a></div>
	<div class="option-aba4 abaExperiencias" style="border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: rgb(204, 204, 204); background: rgb(217, 217, 217);"><a href="javascript: abas(4)">Experiências</a></div>
	<div class="option-aba5 abaDocumentos" style="border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: rgb(204, 204, 204); background: rgb(217, 217, 217);"><a href="javascript: abas(5)">Documentos</a></div>
	<div class="option-aba6 abaCurriculo" style="display: none;"><a href="javascript: abas(6)">Currículo</a></div>
	<#if habilitaCampoExtra && configuracaoCampoExtras?exists && configuracaoCampoExtras?size != 0>
		<div class="option-aba7 abaExtra"><a href="javascript: abas(7)">Extra</a></div>
	</#if>
</div>

<@ww.form name="form" action="updateCamposCandidatoExterno.action"  method="POST">
	<@ww.hidden id="camposTabs" name="camposTabs"/>
	
	<div class="content1">
		<table id="abaDadosPessoais" cellspacing="0" class="configCampos">
		    <thead>
			<tr>
				<th>
					<input id="marcarTodosCandidatoExt1" class="marcarTodos check-visivel" type="checkbox" />
    				<label for="marcarTodosCandidatoExt1" class="label-visivel"></label>
				</th>
				<th>Campos</th>
				<th>Obrigatório</th>
			</tr>
			</thead>
			<tbody>
			<tr class="desabilitado">
				<td>
					<input id="visivel-candidatoExterno-nome" class="check-visivel" name="camposVisivels" value="nome" type="checkbox" checked disabled />
    				<label for="visivel-candidatoExterno-nome" class="label-visivel"></label>
				</td>
				<td class="campo">Nome</td>
				<td><input type="checkbox" value="nome" name="camposObrigatorios" checked disabled class="desabilitado"/></td>
				
				<input type="hidden" id="nomeObr" value="nome" name="camposObrigatorios"/>
				<input type="hidden" id="nomeVis" value="nome" name="camposVisivels" />
			</tr>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-nascimento" class="check-visivel" name="camposVisivels" value="nascimento" type="checkbox"/>
    				<label for="visivel-candidatoExterno-nascimento" class="label-visivel"></label>
				</td>
				<td class="campo">Nascimento</td>
				<td><input type="checkbox" value="nascimento" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-naturalidade" class="check-visivel" name="camposVisivels" value="naturalidade" type="checkbox" />
    				<label for="visivel-candidatoExterno-naturalidade" class="label-visivel"></label>
				</td>
				<td class="campo">Naturalidade</td>
				<td><input type="checkbox" value="naturalidade" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-sexo" class="check-visivel" name="camposVisivels" value="sexo" type="checkbox" />
    				<label for="visivel-candidatoExterno-sexo" class="label-visivel"></label>
				</td>
				<td class="campo">Sexo</td>
				<td><input type="checkbox" value="sexo" name="camposObrigatorios" /></td>
			</tr>
			<tr class="desabilitado">
				<td>
					<input id="visivel-candidatoExterno-cpf" class="check-visivel" name="camposVisivels" value="cpf" type="checkbox" checked disabled />
    				<label for="visivel-candidatoExterno-cpf" class="label-visivel"></label>
				</td>
				<td class="campo">CPF</td>
				<td><input type="checkbox" value="cpf" name="camposObrigatorios" checked disabled  class="desabilitado"/></td>
				
				<input type="hidden"  id="cpfObr" value="cpf" name="camposObrigatorios" />
				<input type="hidden"  id="cpfVis_" value="cpf" name="camposVisivels" />
			</tr>
			<tr class="desabilitado">
				<td>
					<input id="visivel-candidatoExterno-escolaridade" class="check-visivel" name="camposVisivels" value="escolaridade" type="checkbox" checked disabled />
    				<label for="visivel-candidatoExterno-escolaridade" class="label-visivel"></label>
				</td>
				<td class="campo">Escolaridade</td>
				<td><input type="checkbox" value="escolaridade" name="camposObrigatorios" checked disabled class="desabilitado"/></td>
				
				<input type="hidden"  id="escolaridadeObr" value="escolaridade" name="camposObrigatorios" />
				<input type="hidden"  id="escolaridadeVis_" value="escolaridade" name="camposVisivels" />
			</tr>
			
			<tr>
				<td id="endRight">
					<input id="visivel-candidatoExterno-endereco" class="check-visivel" name="camposVisivels" value="endereco" type="checkbox" />
    				<label for="visivel-candidatoExterno-endereco" class="label-visivel"></label>
				</td>
				<td class="campo">Endereço</td>
				<td id="endLeft"></td>
			</tr>
			<tr>
				<td></td>
				<td class="campo" class="grupoObrigatorio" >CEP</td>
				<td>
					<input name="camposObrigatorios" value="cep" type="checkbox" class="endereco"/>
				</td>
			</tr>
			<tr>
				<td></td>
				<td class="campo" class="grupoObrigatorio">Logradouro</td>
				<td>
					<input name="camposObrigatorios" value="ende" type="checkbox" class="endereco"/>
				</td>
			</tr>
			<tr>
				<td></td>
				<td class="campo" class="grupoObrigatorio">Nº</td>
				<td>
					<input name="camposObrigatorios" value="num" type="checkbox" class="endereco"/>
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
			<tr>
				<td></td>
				<td class="campo" class="grupoObrigatorio">Estado/Cidade</td>
				<td>
					<input name="camposObrigatorios" value="cidade" type="checkbox" class="endereco"/>
				</td>
			</tr>

			<tr>
				<td>
					<input id="visivel-candidatoExterno-email" class="check-visivel" name="camposVisivels" value="email" type="checkbox"/>
    				<label for="visivel-candidatoExterno-email" class="label-visivel"></label>
				</td>
				<td class="campo">E-mail</td>
				<td><input type="checkbox" value="email" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-fone" class="check-visivel" name="camposVisivels" value="fone" type="checkbox"/>
    				<label for="visivel-candidatoExterno-fone" class="label-visivel"></label>
				</td>
				<td class="campo">Telefone</td>
				<td><input type="checkbox" value="fone" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-celular" class="check-visivel" name="camposVisivels" value="celular" type="checkbox"/>
    				<label for="visivel-candidatoExterno-celular" class="label-visivel"></label>
				</td>
				<td class="campo">Celular</td>
				<td><input type="checkbox" value="celular" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-nomeContato" class="check-visivel" name="camposVisivels" value="nomeContato" type="checkbox"/>
    				<label for="visivel-candidatoExterno-nomeContato" class="label-visivel"></label>
				</td>
				<td class="campo">Contato</td>
				<td><input type="checkbox" value="nomeContato" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-parentes" class="check-visivel" name="camposVisivels" value="parentes" type="checkbox"/>
    				<label for="visivel-candidatoExterno-parentes" class="label-visivel"></label>
				</td>
				<td class="campo">Parentes/Amigos na empresa</td>
				<td><input type="checkbox" value="parentes" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-estadoCivil" class="check-visivel" name="camposVisivels" value="estadoCivil" type="checkbox"/>
    				<label for="visivel-candidatoExterno-estadoCivil" class="label-visivel"></label>
				</td>
				<td class="campo">Estado Civil</td>
				<td><input type="checkbox" value="estadoCivil" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-qtdFilhos" class="check-visivel" name="camposVisivels" value="qtdFilhos" type="checkbox"/>
    				<label for="visivel-candidatoExterno-qtdFilhos" class="label-visivel"></label>
				</td>
				<td class="campo">Qtd. Filhos</td>
				<td><input type="checkbox" value="qtdFilhos" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-nomeConjuge" class="check-visivel" name="camposVisivels" value="nomeConjuge" type="checkbox"/>
    				<label for="visivel-candidatoExterno-nomeConjuge" class="label-visivel"></label>
				</td>
				<td class="campo">Nome do Cônjuge</td>
				<td><input type="checkbox" value="nomeConjuge" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-profConjuge" class="check-visivel" name="camposVisivels" value="profConjuge" type="checkbox"/>
    				<label for="visivel-candidatoExterno-profConjuge" class="label-visivel"></label>
				</td>
				<td class="campo">Profissão do Cônjuge</td>
				<td><input type="checkbox" value="profConjuge" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-nomePai" class="check-visivel" name="camposVisivels" value="nomePai" type="checkbox"/>
    				<label for="visivel-candidatoExterno-nomePai" class="label-visivel"></label>
				</td>
				<td class="campo">Nome do Pai</td>
				<td><input type="checkbox" value="nomePai" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-profPai" class="check-visivel" name="camposVisivels" value="profPai" type="checkbox"/>
    				<label for="visivel-candidatoExterno-profPai" class="label-visivel"></label>
				</td>
				<td class="campo">Profissão do Pai</td>
				<td><input type="checkbox" value="profPai" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-nomeMae" class="check-visivel" name="camposVisivels" value="nomeMae" type="checkbox"/>
    				<label for="visivel-candidatoExterno-nomeMae" class="label-visivel"></label>
				</td>
				<td class="campo">Nome da Mãe</td>
				<td><input type="checkbox" value="nomeMae" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-profMae" class="check-visivel" name="camposVisivels" value="profMae" type="checkbox"/>
    				<label for="visivel-candidatoExterno-profMae" class="label-visivel"></label>
				</td>
				<td class="campo">Profissão da Mãe</td>
				<td><input type="checkbox" value="profMae" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-pensao" class="check-visivel" name="camposVisivels" value="pensao" type="checkbox"/>
    				<label for="visivel-candidatoExterno-pensao" class="label-visivel"></label>
				</td>
				<td class="campo">Paga Pensão</td>
				<td><input type="checkbox" value="pensao" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-possuiVeiculo" class="check-visivel" name="camposVisivels" value="possuiVeiculo" type="checkbox"/>
    				<label for="visivel-candidatoExterno-possuiVeiculo" class="label-visivel"></label>
				</td>
				<td class="campo">Possui Veículo</td>
				<td><input type="checkbox" value="possuiVeiculo" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-deficiencia" class="check-visivel" name="camposVisivels" value="deficiencia" type="checkbox"/>
    				<label for="visivel-candidatoExterno-deficiencia" class="label-visivel"></label>
				</td>
				<td class="campo">Deficiência</td>
				<td><input type="checkbox" value="deficiencia" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-comoFicouSabendoVaga" class="check-visivel" name="camposVisivels" value="comoFicouSabendoVaga" type="checkbox"/>
    				<label for="visivel-candidatoExterno-comoFicouSabendoVaga" class="label-visivel"></label>
				</td>
				<td class="campo">Como ficou sabendo da vaga</td>
				<td><input type="checkbox" value="comoFicouSabendoVaga" name="camposObrigatorios" /></td>
			</tr>
			<tr class="desabilitado">
				<td>
					<input id="visivel-candidatoExterno-senha" class="check-visivel" name="camposVisivels" checked disabled value="senha" type="checkbox"/>
    				<label for="visivel-candidatoExterno-senha" class="label-visivel"></label>
				</td>
				<td class="campo">Senha</td>
				<td><input type="checkbox" value="senha" name="camposObrigatorios" checked disabled class="desabilitado"/></td>
				<input type="hidden"  id="comfirmaSenha_Vis_" value="comfirmaSenha" name="camposVisivels" />
				<input type="hidden"  id="comfirmaSenhaObr" value="comfirmaSenha" name="camposObrigatorios" />
				<input type="hidden"  id="senha_Obr" value="senha" name="camposObrigatorios" />
				<input type="hidden"  id="senhaVis_" value="senha" name="camposVisivels" />
			</tr>
			</tbody>
		</table>
	</div>
	
	<div class="content2">
		<table id="abaFormacaoEscolar" cellspacing="0" class="configCampos">
		    <thead>		
			<tr>
				<th>
					<input id="marcarTodosCandidatoExt2" class="marcarTodos check-visivel" type="checkbox" />
    				<label for="marcarTodosCandidatoExt2" class="label-visivel"></label>
				</th>
				<th>Campos</th>
				<th>Obrigatório</th>
			</tr>
			</thead>
			<tbody>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-formacao" class="check-visivel" name="camposVisivels" value="formacao" type="checkbox"/>
    				<label for="visivel-candidatoExterno-formacao" class="label-visivel"></label>
				</td>
				<td class="campo">Formação Escolar</td>
				<td><input type="checkbox" value="formacao" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-idioma" class="check-visivel" name="camposVisivels" value="idioma" type="checkbox"/>
    				<label for="visivel-candidatoExterno-idioma" class="label-visivel"></label>
				</td>
				<td class="campo">Idiomas</td>
				<td><input type="checkbox" value="idioma" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-desCursos" class="check-visivel" name="camposVisivels" value="desCursos" type="checkbox"/>
    				<label for="visivel-candidatoExterno-desCursos" class="label-visivel"></label>
				</td>
				<td class="campo">Outros Cursos</td>
				<td><input type="checkbox" value="desCursos" name="camposObrigatorios" /></td>
			</tr>
		</table>
	</div>
	
	<div class="content3">
		<table id="abaPerfilProfissional" cellspacing="0" class="configCampos">
		    <thead>		
			<tr>
				<th>
					<input id="marcarTodosCandidatoExt3" class="marcarTodos check-visivel" type="checkbox" />
    				<label for="marcarTodosCandidatoExt3" class="label-visivel"></label>
				</th>
				<th>Campos</th>
				<th>Obrigatório</th>
			</tr>
			</thead>		
			<tr>
				<td>
					<input id="visivel-candidatoExterno-cargosCheck" class="check-visivel" name="camposVisivels" value="cargosCheck" type="checkbox"/>
    				<label for="visivel-candidatoExterno-cargosCheck" class="label-visivel"></label>
				</td>
				<td class="campo">Cargo / Função Pretendida</td>
				<td><input type="checkbox" value="cargosCheck" name="camposObrigatorios"/></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-areasCheck" class="check-visivel" name="camposVisivels" value="areasCheck" type="checkbox"/>
    				<label for="visivel-candidatoExterno-areasCheck" class="label-visivel"></label>
				</td>
				<td class="campo">Áreas de Interesse</td>
				<td><input type="checkbox" value="areasCheck" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-conhecimentosCheck" class="check-visivel" name="camposVisivels" value="conhecimentosCheck" type="checkbox"/>
    				<label for="visivel-candidatoExterno-conhecimentosCheck" class="label-visivel"></label>
				</td>
				<td class="campo">Conhecimentos</td>
				<td><input type="checkbox" value="conhecimentosCheck" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-colocacao" class="check-visivel" name="camposVisivels" value="colocacao" type="checkbox"/>
    				<label for="visivel-candidatoExterno-colocacao" class="label-visivel"></label>
				</td>
				<td class="campo">Colocação</td>
				<td><input type="checkbox" value="colocacao" name="camposObrigatorios" /></td>
			</tr>
		</table>
	</div>
	
	<div class="content4">
		<table id="abaExperiencias" cellspacing="0" class="configCampos">
		    <thead>		
			<tr>
				<th>
					<input id="marcarTodosCandidatoExt4" class="marcarTodos check-visivel" type="checkbox" />
    				<label for="marcarTodosCandidatoExt4" class="label-visivel"></label>
				</th>
				<th>Campos</th>
				<th>Obrigatório</th>
			</tr>
			</thead>			
			<tr>
				<td>
					<input id="visivel-candidatoExterno-expProfissional" class="check-visivel" name="camposVisivels" value="expProfissional" type="checkbox"/>
    				<label for="visivel-candidatoExterno-expProfissional" class="label-visivel"></label>
				</td>
				<td class="campo">Experiência Profissional</td>
				<td><input type="checkbox" value="expProfissional" name="camposObrigatorios"/></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-infoAdicionais" class="check-visivel" name="camposVisivels" value="infoAdicionais" type="checkbox"/>
    				<label for="visivel-candidatoExterno-infoAdicionais" class="label-visivel"></label>
				</td>
				<td class="campo">Informações Adicionais</td>
				<td><input type="checkbox" value="infoAdicionais" name="camposObrigatorios"/></td>
			</tr>
		</table>
	</div>
	
	<div class="content5">
		<table id="abaDocumentos" cellspacing="0" class="configCampos">
		    <thead>		
			<tr>
				<th>
					<input id="marcarTodosCandidatoExt5" class="marcarTodos check-visivel" type="checkbox" />
    				<label for="marcarTodosCandidatoExt5" class="label-visivel"></label>
				</th>
				<th>Campos</th>
				<th>Obrigatório</th>
			</tr>
			</thead>			
			<tr>
				<td>
					<input id="visivel-candidatoExterno-identidade" class="check-visivel" name="camposVisivels" value="identidade" type="checkbox"/>
    				<label for="visivel-candidatoExterno-identidade" class="label-visivel"></label>
				</td>
				<td class="campo">Identidade</td>
				<td><input type="checkbox" value="identidade" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-carteiraHabilitacao" class="check-visivel" name="camposVisivels" value="carteiraHabilitacao" type="checkbox"/>
    				<label for="visivel-candidatoExterno-carteiraHabilitacao" class="label-visivel"></label>
				</td>
				<td class="campo">Carteira de Habilitação</td>
				<td><input type="checkbox" value="carteiraHabilitacao" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-tituloEleitoral" class="check-visivel" name="camposVisivels" value="tituloEleitoral" type="checkbox"/>
    				<label for="visivel-candidatoExterno-tituloEleitoral" class="label-visivel"></label>
				</td>
				<td class="campo">Título Eleitoral</td>
				<td><input type="checkbox" value="tituloEleitoral" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-certificadoMilitar" class="check-visivel" name="camposVisivels" value="certificadoMilitar" type="checkbox"/>
    				<label for="visivel-candidatoExterno-certificadoMilitar" class="label-visivel"></label>
				</td>
				<td class="campo">Certificado Militar</td>
				<td><input type="checkbox" value="certificadoMilitar" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-ctps" class="check-visivel" name="camposVisivels" value="ctps" type="checkbox"/>
    				<label for="visivel-candidatoExterno-ctps" class="label-visivel"></label>
				</td>
				<td class="campo">CTPS - Carteira de Trabalho e Previdência Social</td>
				<td><input type="checkbox" value="ctps" name="camposObrigatorios" /></td>
			</tr>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-pis" class="check-visivel" name="camposVisivels" value="pis" type="checkbox"/>
    				<label for="visivel-candidatoExterno-pis" class="label-visivel"></label>
				</td>
				<td class="campo">PIS - Programa de Integração Social</td>
				<td><input type="checkbox" value="pis" name="camposObrigatorios" /></td>
			</tr>
		</table>
	</div>
	
	<div class="content6">
		<table id="abaCurriculo" cellspacing="0" class="configCampos">
		   <thead>		
			<tr>
				<th>
					<input id="marcarTodosCandidatoExt6" class="marcarTodos check-visivel" type="checkbox" />
    				<label for="marcarTodosCandidatoExt6" class="label-visivel"></label>
				</th>
				<th>Campos</th>
				<th>Obrigatório</th>
			</tr>
		    </thead>		
			<tr>
				<td>
					<input id="visivel-candidatoExterno-ocrTexto" class="check-visivel" name="camposVisivels" value="ocrTexto" type="checkbox"/>
    				<label for="visivel-candidatoExterno-ocrTexto" class="label-visivel"></label>
				</td>
				<td class="campo">Descrição do Currículo</td>
				<td><input type="checkbox" value="ocrTexto" name="camposObrigatorios"/></td>
			</tr>
		</table>
	</div>

	<#if habilitaCampoExtra && configuracaoCampoExtras?exists && configuracaoCampoExtras?size != 0>
	<div class="content7">
		<table id="abaExtra" cellspacing="0" class="configCampos">
		   <thead>		
			<tr>
				<th>
					<input id="marcarTodosCandidatoExt7" class="marcarTodos check-visivel" type="checkbox" />
					<label for="marcarTodosCandidatoExt7" class="label-visivel"></label>
				</th>
				<th>Campos</th>
				<th>Obrigatório</th>
			</tr>
		    </thead>		
		    <#list configuracaoCampoExtras as campoExtra>
			<tr>
				<td>
					<input id="visivel-candidatoExterno-${campoExtra.nome}" class="check-visivel" name="camposVisivels" value="${campoExtra.nome}" type="checkbox"/>
					<label for="visivel-candidatoExterno-${campoExtra.nome}" class="label-visivel"></label>
				</td>
				<td class="campo">${campoExtra.titulo}</td>
				<td><input type="checkbox" value="${campoExtra.nome}" name="camposObrigatorios"/></td>
			</tr>
			</#list>
		</table>
	</div>
	</#if>
</@ww.form>