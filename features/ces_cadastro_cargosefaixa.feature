# language: pt

Funcionalidade: Cadastrar Cargos e Faixas

  Cenário: Cadastro de Cargos e Faixas
    Dado que eu esteja logado com o usuário "fortes"
    Dado que exista a área organizacional "administracao"
    Dado que exista a área organizacional "administracao > desenvolvimento"
    Dado que exista a etapa seletiva "Entrevista com RH"
    Dado que exista um conhecimento "testes"
    Dado que exista um conhecimento "java"
    Dado que exista um conhecimento "testes" na area organizacional "administracao"
    Dado que exista um conhecimento "java" na area organizacional "administracao > desenvolvimento"
    Dado que exista um nivel de competencia "ruim"
    Dado que exista um nivel de competencia "regular"
    Dado que exista um nivel de competencia "bom"
    Dado que exista um historico de nivel de competencia na data "01/01/2010"
    Dado que exista uma configuracao de nivel de competencia com nivel "ruim" no historico do nivel de data "01/01/2010" na ordem 1
    Dado que exista uma configuracao de nivel de competencia com nivel "regular" no historico do nivel de data "01/01/2010" na ordem 2
    Dado que exista uma configuracao de nivel de competencia com nivel "bom" no historico do nivel de data "01/01/2010" na ordem 3

    Quando eu acesso o menu "C&S > Cadastros > Cargos e Faixas"
    Então eu devo ver o título "Cargos"
    E eu clico no botão "Inserir"
    Então eu devo ver o título "Inserir Cargo"
    E eu clico no botão "Gravar"
    Então eu devo ver o alert do valida campos e clico no ok
    E eu clico no botão "Cancelar"
    Então eu devo ver o título "Cargos"

    Então eu clico no botão "Inserir"
    E eu devo ver o título "Inserir Cargo"
    E eu preencho "Nomenclatura" com "_Desenvolvedor"
    E eu marco "Exibir no modulo externo"
    E eu preencho "Nomenclatura de Mercado" com "_Programador"
    E eu preencho "Cód. CBO" com "123"
    E eu preencho "Descrição" com "Desenvolvedor"
    E eu marco "administracao"
    E eu marco "administracao > desenvolvimento"
    E eu marco "java"
    E eu marco "testes"
    E eu preencho "Missão do Cargo" com "Programar"
    E eu preencho "Fontes de Recrutamento" com "Teste"
    E eu marco "Entrevista com RH"
    E eu preencho "Responsabilidades Correlatas" com "Regras de negócio"
    E eu preencho "Complemento dos Conhecimentos" com "Testes"
    E eu preencho "Complemento das Habilidades" com "Testes"
    E eu preencho "Complemento das Atitudes" com "Testes"
    E eu seleciono "Especialização completa" de "Escolaridade"
    E eu marco "Administrativa"
    E eu preencho "Experiência Desejada" com "Testes"
    E eu preencho "Observações" com "Testes"
    E eu clico no botão "Gravar"

    E eu devo ver o título "Inserir Faixa Salarial"
    E eu preencho "Faixa" com "Faixa I"
    E eu preencho o campo (JS) "A partir de" com "03/06/2011"
    E eu seleciono "Por valor" de "Tipo"
    E eu preencho "Valor" com "2000"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Faixas Salariais"
    E eu devo ver "Faixa I"
    E eu clico no botão "Voltar"

    E eu devo ver o título "Cargos"
    E eu clico em editar "_Desenvolvedor"
    E eu devo ver o título "Editar Cargo"
    E o campo "Nomenclatura" deve conter "_Desenvolvedor"
    E eu preencho "Nomenclatura" com "_Analista"
    E eu clico no botão "Gravar"
    E eu devo ver o título "Cargos"

    E eu clico na linha "_Analista" da imagem "Faixas Salariais"
    E eu devo ver "Faixa I"
    E eu clico em editar "Faixa I"
    E eu devo ver o título "Editar Faixa Salarial"

    E eu devo ver "Por Valor"
    E eu clico em editar "03/06/2011"
    E eu devo ver o título "Editar Histórico da Faixa Salarial"
    E eu preencho "Valor" com "3000"
    Então eu clico no botão "Gravar"

    E eu devo ver o título "Editar Faixa Salarial"
    E o campo "Faixa" deve conter "Faixa I"
    E eu preencho "Faixa" com "Faixa II"
    Então eu clico no botão "Gravar"

    Então eu devo ver o título "Faixas Salariais"
    E eu devo ver "Faixa II"
    E eu clico na linha "Faixa II" da imagem "Níveis de Competência"
    E eu clico no botão "Inserir"
    E eu devo ver o título "Competências da Faixa Salarial"
    E eu marco "java"
    E eu escolho "niveisCompetenciaFaixaSalariaisConhecimento[0].nivelCompetencia.id"
    E eu marco "testes"
    E eu clico no botão "Gravar"
    E eu devo ver o alert "Confira os pesos e os níveis para as competências indicadas." e clico no ok
    E eu escolho "niveisCompetenciaFaixaSalariaisConhecimento[1].nivelCompetencia.id"
    E eu clico no botão "Gravar"
    E eu clico no botão "Voltar"

    Então eu devo ver o título "Faixas Salariais"
    E eu devo ver "Faixa II"
    Então eu clico no botão "Voltar"

    E eu devo ver o título "Cargos"
    E eu devo ver "_Analista"

    Então eu clico em excluir "_Analista"
    E eu devo ver o alert do confirmar exclusão e clico no ok
    Então eu devo ver "possui dependências em"
