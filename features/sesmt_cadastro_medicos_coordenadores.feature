# language: pt

Funcionalidade: Médicos Coordenadores

  Cenário: Cadastro de Médicos Coordenadores
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Médicos Coordenadores"
    Então eu devo ver o título "Médicos Coordenadores"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Médico Coordenador"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Médicos Coordenadores"

    E eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Médico Coordenador"
    E eu preencho "Nome" com "pererinha"
    E eu preencho o campo (JS) "Início do contrato" com "23/02/2011"
    E eu preencho "CRM" com "1122"
    E eu clico no botão "Gravar"

    Então eu devo ver o título "Médicos Coordenadores"
    E eu devo ver "pererinha"
    E eu devo ver "23/02/2011"

    Entao eu clico em editar "pererinha"
    E eu devo ver o título "Editar Médico Coordenador"
    E o campo "Nome" deve conter "pererinha"
    E eu preencho "Nome" com "perera"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Médicos Coordenadores"
    Então eu devo ver "perera"

    Então eu clico em excluir "perera"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Médico coordenador excluído com sucesso."
    Então eu não devo ver na listagem "perera"
