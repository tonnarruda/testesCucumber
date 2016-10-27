# language: pt

Funcionalidade: Clínicas e Médicos Autorizados

  Cenário: Cadastro de Clínicas e Médicos Autorizados
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "SESMT > Cadastros > Clínicas e Médicos Autorizados"
    Então eu devo ver o título "Clínicas e Médicos Autorizados"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Clínica ou Médico Autorizado"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Clínicas e Médicos Autorizados"

    E eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Clínica ou Médico Autorizado"
    E eu preencho "Nome" com "marina"
    E eu seleciono "Médico" de "Tipo"
    E eu preencho o campo (JS) "Início do contrato" com "23/02/2011"
    E eu clico no botão "Gravar"

    Então eu devo ver o título "Clínicas e Médicos Autorizados"
    E eu devo ver "marina"
    E eu devo ver "Médico"

    Entao eu clico em editar "marina"
    E eu devo ver o título "Editar Clínica ou Médico Autorizado"
    E o campo "Nome" deve conter "marina"
    E eu preencho "Nome" com "mariana"
    E eu clico no botão "Gravar"
    Então eu devo ver o título "Clínicas e Médicos Autorizados"
    Então eu devo ver "mariana"

    Então eu clico em excluir "mariana"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    E eu devo ver "Clínica/Médico Autorizada(o) excluída(o) com sucesso."
    Então eu não devo ver na listagem "mariana"
