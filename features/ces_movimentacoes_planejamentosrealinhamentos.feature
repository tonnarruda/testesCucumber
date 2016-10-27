# language: pt

Funcionalidade: Cadastrar Planejamentos de Realinhamentos

  Cenário: Cadastro de Planejamentos de Realinhamentos
    Dado que eu esteja logado com o usuário "SOS"
    Quando eu acesso o menu "C&S > Movimentações > Planejamentos de Realinhamentos"
    Então eu devo ver o título "Planejamentos de Realinhamentos"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Planejamento de Realinhamentos"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Voltar"
    Então eu devo ver o título "Planejamentos de Realinhamentos"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Planejamento de Realinhamentos"
    E eu preencho "Título" com "_Aumento Geral"
    E eu preencho o campo (JS) "Data de Aplicação" com "01/01/2011"
    E eu seleciono "Colaborador" de "Tipo do Reajuste"
    E eu preencho "Observação" com "Testando..."
    E eu clico no botão "Gravar"
    E eu devo ver o título "Planejamentos de Realinhamentos"

    E eu clico em editar "_Aumento Geral"
    E eu devo ver o título "Editar Planejamento de Realinhamentos"
    E o campo "Título" deve conter "_Aumento Geral"
    E eu preencho "Título" com "_Dissídio 2011"
    E eu preencho o campo (JS) "Data de Aplicação" com "01/06/2011"
    E eu preencho "Observação" com "Testando alteração..."
    E eu clico no botão "Gravar"
    E eu devo ver o título "Planejamentos de Realinhamentos"

    E eu clico em imprimir "_Dissídio 2011"
    E eu devo ver o título "Planejamento de Realinhamentos"
    Então eu clico no botão "Cancelar"
    
    E eu clico em excluir "_Dissídio 2011"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver "Planejamento de Realinhamento excluído com sucesso."
    E eu não devo ver "_Dissídio 2011"