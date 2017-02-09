# language: pt

Funcionalidade: Cadastrar Candidato

  @dev
  Cenário: Cadastro de Candidato
    Dado que eu esteja logado com o usuário "SOS"

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
    Quando eu preencho "nome" com "_Pedro"
    E eu preencho o campo (JS) "Nascimento" com "01/01/1987"
    E eu preencho "Naturalidade" com "fortaleza"
    E eu preencho o campo (JS) "CPF" com "881.028.771-11"
    E eu seleciono "Analfabeto, inclusive o que, embora tenha recebido instrução, não se alfabetizou" de "Escolaridade"
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
    Então eu devo ver "Operação efetuada com sucesso"
    Quando eu clico no botão "Voltar"
    Então eu devo ver "Candidatos"
    E eu devo ver "_Pedro"

    Quando eu clico em editar "_Pedro"
    Então o campo "Nome" deve conter "_Pedro"
    Quando eu preencho "Nome" com "_Pedro 2"
    E eu preencho o campo (JS) "CEP" com "60320-104"
    E eu saio do campo "CEP"
    E eu espero 2 segundos
    #Então o campo "Logradouro" deve conter "Rua Haroldo Torres - de 501"
    E eu preencho "Logradouro" com "Rua Haroldo Torres - de 501"
   #E o campo "Estado" deve ter "CE" selecionado
    E eu seleciono "CE" de "Estado"
    E eu espero 1 segundo
   #E o campo "cidade" deve ter "Fortaleza" selecionado
    E eu seleciono "Fortaleza" de "Cidade"
   #E o campo "bairroNome" deve conter "São Gerardo"
    E eu preencho "Bairro" com "São Gerardo"
    Quando eu clico no botão "Gravar"
    Então eu devo ver "_Pedro 2"

    Quando eu clico em excluir "_Pedro 2"
    Então eu devo ver "Deseja realmente excluir o candidato _Pedro 2?"
    Quando eu aperto "OK"
    Então eu devo ver "Candidato excluído com sucesso!"
    E eu não devo ver "_Pedro 2"
