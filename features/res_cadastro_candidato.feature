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
    Quando eu preencho "nome" com "Pedro do Teste"
    E eu preencho "nascimento" com "01/01/1987"
    E eu preencho "naturalidade" com "fortaleza"
    E eu preencho "cpf" com "881.028.771-11"
    E eu seleciono "Sem escolaridade" de "Escolaridade"
    E eu preencho "Logradouro" com "Eretides"
    E eu preencho "num" com "11"
    E eu preencho "complemento" com "apto"
    E eu seleciono "CE" de "Estado"
    E eu seleciono "Fortaleza" de "cidade"
    E eu preencho "bairroNome" com "São Gerardo"
    E eu preencho "email" com "franciscobarroso@grupofortes.com.br"
    E eu preencho "ddd" com "85"
    E eu preencho "fone" com "88438383"
    E eu preencho "celular" com "88438309"
    E eu preencho "nomeContato" com "Maria"
    E eu preencho "Parentes/Amigos na empresa" com "Pedro"
    E eu preencho "indicado" com "Maria"
    E eu preencho "senha" com "1234"
    E eu preencho "comfirmaSenha" com "1234"
    E eu clico no botão "Gravar"
    Então eu devo ver "Operação Efetuada com Sucesso!"
    E eu devo ver "Pedro do Teste"
    Quando eu clico no botão "Voltar"
    Então eu devo ver "Candidatos"
    E eu devo ver "Pedro do Teste"

    Quando eu clico em editar "Pedro do Teste"
    E o campo "nome" deve conter "Pedro do Teste"
    E eu preencho "nome" com "Pedro do Teste 2"
    E eu preencho "cep" com "60320-100"
    E eu saio do campo "cep"
    E eu espero 6 segundos
    E o campo "Logradouro" deve conter "Rua Haroldo Torres"
    E o campo "Estado" deve ter "CE" selecionado
    E o campo "cidade" deve ter "Fortaleza" selecionado
    E o campo "bairroNome" deve conter "São Gerardo"
    E eu clico no botão "Gravar"
    E eu devo ver "Pedro do Teste 2"

    Quando eu clico em excluir "Pedro do Teste 2"
    Então eu devo ver "Confirma exclusão?"
    Quando eu aperto "OK"
    Então eu devo ver "Candidato excluído com sucesso!"
    E eu não devo ver "Pedro do Teste 2"
