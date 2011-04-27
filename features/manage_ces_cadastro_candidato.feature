# language: pt

Funcionalidade: Cadastrar Candidato

  @dev
  Cenário: Cadastro de Candidato
    Dado que eu esteja logado

    Quando eu acesso o menu "R&S > Cadastros > Candidatos"
    Então eu devo ver o título "Candidatos"

    Quando eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Candidato"
    Quando eu preencho "nome" com "Pedro do Teste"
    Quando eu preencho "nascimento" com "01/01/1987"
    Quando eu preencho "naturalidade" com "fortaleza"
    Quando eu preencho "cpf" com "881.028.771-11"
    E eu seleciono "Sem escolaridade" de "Escolaridade"
    Quando eu preencho "cep" com "60320-100"
    Quando eu preencho "ende" com "Eretides"
    Quando eu preencho "num" com "11"
    Quando eu preencho "complemento" com "apto"
    E eu seleciono "CE" de "uf"
    E eu seleciono "Fortaleza" de "cidade"
    Quando eu preencho "bairroNome" com "São Gerardo"
    Quando eu preencho "email" com "franciscobarroso@grupofortes.com.br"
    Quando eu preencho "ddd" com "85"
    Quando eu preencho "fone" com "88438383"
    Quando eu preencho "celular" com "88438309"
    Quando eu preencho "nomeContato" com "Maria"
    Quando eu preencho "parentes" com "Pedro"
    Quando eu preencho "indicado" com "Maria"
    Quando eu preencho "senha" com "1234"
    Quando eu preencho "comfirmaSenha" com "1234"

    Quando eu clico no botão "Gravar"
    Então eu devo ver "Operação Efetuada com Sucesso!"
    Então eu devo ver "Pedro do Teste"
    Quando eu clico no botão "Voltar"
    Então eu devo ver "Candidatos"
    Então eu devo ver "Pedro do Teste"

    Quando eu clico em excluir "Pedro do Teste"
    Então eu devo ver "Confirma exclusão?"
    Quando eu aperto "OK"
    Então eu devo ver "Candidato excluído com sucesso!"
