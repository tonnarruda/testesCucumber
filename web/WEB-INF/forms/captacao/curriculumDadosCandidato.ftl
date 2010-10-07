<!--
 * autor Moésio Medeiros
 * data: 23/06/2006
 * Requisito: RFA029 - Cadastro de curriculum
-->
<table id="master">
	<tbody>
		<tr>
			<td class="label">
				Nome: 
			</td>
			<td class="value">
				${CANDIDATO_SESSION.nome}
			</td>
		</tr>
		<tr>
			<td class="label">
				Endereço: 
			</td>
			<td class="value">
				${CANDIDATO_SESSION.endereco.logradouro} - ${CANDIDATO_SESSION.endereco.bairro}
			</td>
		</tr>
		<tr>
			<td class="label">
				Cidade: 
			</td>
			<td class="value">
				${CANDIDATO_SESSION.endereco.cidade} - ${CANDIDATO_SESSION.endereco.uf} - CEP: ${CANDIDATO_SESSION.endereco.cep}
			</td>
		</tr>
		<tr>
			<td class="label">
				Telefones: 
			</td>
			<td class="value">
				${CANDIDATO_SESSION.contato.foneFixo}<#if CANDIDATO_SESSION.contato.foneCelular != "">, ${CANDIDATO_SESSION.contato.foneCelular}</#if>
			</td>
			<td>
		</tr>
		<tr>
			<td class="label">
				Email: 
			</td>
			<td class="value">
				${CANDIDATO_SESSION.contato.email}
			</td>
		</tr>
		<tr>
			<td class="label">
				CPF: 
			</td>
			<td class="value">
				${CANDIDATO_SESSION.pessoal.cpf}
			</td>
		</tr>
		<tr>
			<td class="label">
				Data de Nascimento: 
			</td>
			<td class="value">
				${CANDIDATO_SESSION.pessoal.dataNascimento}
			</td>
		</tr>
		<tr>
			<td class="label">
				Estado Civil: 
			</td>
			<td class="value">
			<#switch CANDIDATO_SESSION.pessoal.estadoCivil>
				<#case "01">
					<#assign estadoCivil="Solteiro" />
				<#break>
				<#case "02">
					<#assign estadoCivil="Casado - Comunhão universal" />
				<#break>
				<#case "03">
					<#assign estadoCivil="Casado - Comunhão parcial" />
				<#break>
				<#case "04">
					<#assign estadoCivil="Casado - Separação de bens" />
				<#break>
				<#case "05">
					<#assign estadoCivil="Viúvo" />
				<#break>
				<#case "06">
					<#assign estadoCivil="Separado judicialmente" />
				<#break>
				<#case "07">
					<#assign estadoCivil="Divorciado" />
				<#break>
				<#case "08">
					<#assign estadoCivil="Casado - Regime total" />
				<#break>
				<#case "09">
					<#assign estadoCivil="Casado - Regime misto ou especial" />
				<#break>
				<#case "10">
					<#assign estadoCivil="União estável" />
				<#break>
			</#switch>
			<#switch CANDIDATO_SESSION.pessoal.sexo>
				<#case 'M'>
					<#assign sexo="Masculino" />
				<#break>
				<#case 'F'>
					<#assign sexo="Feminino" />
				<#break>
			</#switch>
				${estadoCivil} - <strong>Sexo:</strong> ${sexo} - <strong>Dependentes:</strong> ${CANDIDATO_SESSION.qtdDependentes}
			</td>
		</tr>
		<#if CANDIDATO_SESSION.pessoal.conjugeTrabalha?exists>
		<tr>
			<td class="label">
				Cônjuge Trabalha?
			</td>
			<td class="value">
				<#if CANDIDATO_SESSION.pessoal.conjugeTrabalha==true>
					Sim
				<#else>
					Não
				</#if>
			</td>
		</tr>
		</#if>
		<tr>
			<td class="label">
				Observação:
			</td>
			<td class="value">
				${CANDIDATO_SESSION.observacao}
			</td>
		</tr>
	</tbody>
</table>
<br>