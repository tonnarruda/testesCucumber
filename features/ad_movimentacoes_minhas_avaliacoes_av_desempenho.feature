# language: pt

Funcionalidade: Minhas Avaliações - Avaliação Desempenho

  Cenário: Minhas Avaliações - Avaliação Desempenho
    Dado que exista um colaborador "Samuel", da area "Desenvolvimento", com o cargo "Desenvolvedor" e a faixa salarial "3"
    Dado que eu esteja logado com o usuário "fortes"

    Quando eu acesso o menu "Aval. Desempenho > Cadastros > Avaliações de Desempenho/Acomp. do Período de Experiência"
    Então eu devo ver o título "Modelos de Avaliação de Desempenho/Acompanhamento do Per. de Experiência"
    E eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Modelo de Avaliação"
    E eu preencho "Título" com "_avaliacao I"
    E eu preencho "Observação" com "_experiencia"
    E eu seleciono "Avaliação de Desempenho" de "Tipo de Avaliação"
    E eu clico no botão "Avancar"

    Então eu devo ver o título "Inserir Pergunta da Avaliação"
    E eu seleciono "1" de "Ordem"
    E eu preencho "pergunta" com "_pergunta 1"
    E eu preencho "peso" com "1"
    E eu seleciono "Nota" de "Tipo de Resposta"
    E eu preencho "notaMinima" com "5"
    E eu preencho "notaMaxima" com "10"
    E eu clico no botão "Gravar"
    E eu devo ver "Pergunta gravada com sucesso."

    Quando eu acesso o menu "Aval. Desempenho > Movimentações > Avaliações de Desempenho"
    Então eu devo ver o título "Avaliações de Desempenho"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Avaliação de Desempenho"
    E eu preencho "Título" com "_avaliacao 1"
    E eu preencho o campo (JS) "inicio" com "01/06/2011"
    E eu preencho o campo (JS) "fim" com "30/06/2011"
    E eu seleciono "_avaliacao I" de "modelo"
    E eu seleciono "Não" de "anonima"
    E eu seleciono "Sim" de "permiteAutoAvaliacao"
    E eu clico no botão "Avancar"

    Então eu devo ver o título "Participantes - _avaliacao 1"
    E eu clico no botão de Id "inserir_Avaliado"
    E eu espero 1 segundos
    Então eu devo ver "Inserir Avaliado"
    E eu clico no botão "Pesquisar"
    E eu marco "Samuel"
    E eu clico no botão "Gravar"

    Entao eu clico no botão de Id "inserir_Avaliador"
    E eu espero 1 segundos
    E eu clico no botão "Pesquisar"
    E eu marco "Samuel"
    E eu clico no botão "Gravar"

    Entao eu clico no botão de Id "btnGravar"
    E eu devo ver "Gravado com sucesso"
    E eu adiciono o avaliado no avaliador da avaliação de desempenho
    E eu clico no botão de Id "btnGravar"
    E eu devo ver "Gravado com sucesso"
    E eu clico no botão "Voltar"

    Então eu clico na linha "_avaliacao 1" da imagem "Liberar"
    Então eu devo ver o alert do confirmar e clico no ok

    Quando eu acesso o menu "Aval. Desempenho > Movimentações > Responder Avaliações de Desempenho"
    E eu clico "Exibir Filtro"
    E eu clico no botão "Pesquisar"
    E eu devo ver o alert "Preencha os campos indicados." e clico no ok

    E eu seleciono "Samuel" de "Avaliador"
    E eu seleciono "Todas" de "Situação"
    E eu clico no botão "Pesquisar"

    Quando eu clico na imagem com o título "Responder"
    E eu devo ver o título "Responder Avaliação de Desempenho"
    E eu seleciono "8" de "Selecione a nota de 5 a 10"
    E eu clico no botão "Gravar"
    E eu devo ver "Respostas gravadas com sucesso."
    Entao eu devo ver "80%"

    Quando eu acesso o menu "Info. Funcionais > Cadastros > Colaboradores"
    Então eu clico na linha "Samuel" da imagem "Criar Acesso ao Sistema"
    E eu preencho "Nome" com "Samuel"
    E eu preencho "Login" com "samuel"
    E eu preencho "Senha" com "1234"
    E eu preencho "Confirmar Senha" com "1234"
    E eu seleciono (JS) "1" de "selectPerfil_1"
    E eu clico no botão "Gravar"

    Quando eu acesso o menu "Sair"
    Dado que eu esteja logado com o usuário "samuel"

    Quando eu acesso o menu "Aval. Desempenho > Movimentações > Minhas Avaliações"
    E eu devo ver o título "Respostas das Minhas Avaliações"
    E eu clico em editar "_avaliacao 1"
    E eu devo ver "Responder Avaliação de Desempenho"
    E eu clico no botão "Cancelar"
    E eu devo ver o título "Respostas das Minhas Avaliações"

    Então eu clico em editar "_avaliacao 1"
    E eu seleciono "6" de "Selecione a nota de 5 a 10"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Respostas das Minhas Avaliações"
    E eu devo ver "Respostas gravadas com sucesso."

    Quando eu acesso o menu "Sair"
