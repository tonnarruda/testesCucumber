# language: pt

Funcionalidade: Cadastrar Bairro

  Cenário: Cadastro de Bairro
    Dado que eu esteja logado com o usuário "SOS"

    Quando eu acesso o menu "Utilitários > Cadastros > Bairros"
    Então eu devo ver o título "Bairros"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Bairro"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"

    Então eu devo ver o título "Bairros"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Bairro"
    E eu seleciono "Ceará" de "Estado"
    E eu saio do campo "Estado"
    E eu seleciono "Fortaleza" de "Cidade"
    E eu preencho "Nome" com "pontal"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Bairros"
    Então eu devo ver "pontal"

    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Bairro"
    E eu seleciono "Ceará" de "Estado"
    E eu saio do campo "Estado"
    E eu seleciono "Fortaleza" de "Cidade"
    E eu preencho "Nome" com "beira-rio"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Bairros"
    Então eu devo ver "beira-rio"

    E eu clico no botão "UnirBairros"
    Então eu devo ver o título "Unir Bairros"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu seleciono "Ceará" de "Estado"
    E eu saio do campo "Estado"
    E eu seleciono "Fortaleza" de "Cidade"
    E eu seleciono "beira-rio" de "Bairro (ATENÇÃO: este bairro será excluído)"
    E eu seleciono "pontal" de "Transferir registros para este Bairro"

    Então eu clico no botão "Gravar"
    E eu devo ver "Registros transferidos com sucesso."
    Então eu devo ver o título "Unir Bairros"
    Então eu clico no botão "Voltar"
    E eu não devo ver na listagem "beira-rio"

    Entao eu clico em editar "pontal"
    E eu devo ver o título "Editar Bairro"
    E o campo "Nome" deve conter "pontal"
    E eu preencho "Nome" com "centro"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Bairros"
    E eu não devo ver na listagem "pontal"
    Então eu devo ver "centro"

    Então eu clico em excluir "centro"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Bairro excluído com sucesso."
    Então eu não devo ver na listagem "pontal"