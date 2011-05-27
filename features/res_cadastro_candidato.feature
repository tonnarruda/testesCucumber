# language: pt

Funcionalidade: Cadastrar Candidato

  @dev
  Cenário: Cadastro de Candidato
    Dado que eu esteja logado

    Quando eu acesso o menu "R&S > Cadastros > Candidatos"
    Então eu devo ver o título "Candidatos"
    Quando eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Candidato"
    E eu clico no botão "Gravar"
    Quando eu aperto "OK"
    E eu clico no botão "Cancelar"
    Quando eu aperto "OK"
    Então eu devo ver o título "Candidatos"
    Quando eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Candidato"
    Quando eu preencho "nome" com "_Pedro do Teste"
    E eu preencho "Nascimento" com "01/01/1987"
    E eu preencho "Naturalidade" com "fortaleza"
    E eu preencho "CPF" com "881.028.771-11"
    E eu seleciono "Sem escolaridade" de "Escolaridade"
    E eu preencho "Logradouro" com "Eretides"
    E eu preencho "num" com "11"
    E eu preencho "Complemento" com "apto"
    E eu seleciono "CE" de "Estado"
    E eu seleciono "Fortaleza" de "cidade"
    E eu preencho "Bairro" com "Nossa Senhora da Apresentação"
    E eu saio do campo "Bairro"
    E eu preencho "E-mail" com "franciscobarroso@grupofortes.com.br"
    E eu preencho "DDD" com "85"
    E eu preencho "Telefone" com "88438383"
    E eu preencho "Celular" com "88438309"
    E eu preencho "Contato" com "Maria"
    E eu preencho "Parentes/Amigos na empresa" com "Pedro"
    E eu preencho "Indicado Por" com "Maria"
    E eu preencho "Senha" com "1234"
    E eu preencho "Confirmar Senha" com "1234"
    Quando eu clico no botão "Gravar"
    Então eu devo ver "Operação Efetuada com Sucesso!"
    Quando eu clico no botão "Voltar"
    Então eu devo ver "Candidatos"
    E eu devo ver "_Pedro do Teste"

    Quando eu clico em editar "_Pedro do Teste"
    Então o campo "Nome" deve conter "_Pedro do Teste"
    Quando eu preencho "Nome" com "_Pedro do Teste 2"
    E eu preencho "CEP" com "60320-100"
    E eu saio do campo "CEP"
    E eu espero o campo "Logradouro" ficar abilitado
    Entao o campo "Logradouro" deve conter "Rua Haroldo Torres"
    E o campo "Estado" deve ter "CE" selecionado
    E o campo "Cidade" deve ter "Fortaleza" selecionado
    E o campo "Bairro" deve conter "São Gerardo"
    Quando eu clico no botão "Gravar"
    Então eu devo ver "_Pedro do Teste 2"

    Quando eu clico em excluir "_Pedro do Teste 2"
    Então eu devo ver "Confirma exclusão?"
    Quando eu aperto "OK"
    Então eu devo ver "Candidato excluído com sucesso!"
    E eu não devo ver "_Pedro do Teste 2"
