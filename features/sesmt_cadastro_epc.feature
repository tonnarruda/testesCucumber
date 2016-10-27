# language: pt

Funcionalidade: EPCs (Equipamentos de Proteção Coletiva)

  Cenário: Cadastro de EPCs (Equipamentos de Proteção Coletiva)
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > EPC"
    Então eu devo ver o título "EPCs (Equipamentos de Proteção Coletiva)"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir EPC"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"

    Então eu devo ver o título "EPCs (Equipamentos de Proteção Coletiva)"
    E eu clico no botão "Inserir"
    E eu preencho "Descrição" com "capacete"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "EPCs (Equipamentos de Proteção Coletiva)"
    Então eu devo ver "capacete"

    Entao eu clico em editar "capacete"
    E eu devo ver o título "Editar EPC"
    E o campo "Descrição" deve conter "capacete"
    E eu preencho "Descrição" com "bota"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "EPCs (Equipamentos de Proteção Coletiva)"
    Então eu devo ver "bota"

    Então eu clico em excluir "bota"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "EPC excluído com sucesso."
    Então eu não devo ver na listagem "bota"












