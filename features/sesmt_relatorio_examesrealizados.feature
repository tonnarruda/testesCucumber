# language: pt

Funcionalidade: Relatório de Exames Realizados

  Cenário: Relatório de Exames Realizados
    Dado que exista um medico coordenador "eufrasio"

    Dado que eu esteja logado
    Quando eu acesso o menu "SESMT > Relatórios > Exames Realizados"
    Então eu devo ver o título "Exames Realizados"
    E eu clico no botão "Relatorio"
    E eu devo ver o alert do valida campos e clico no ok

    Então eu preencho o campo (JS) "dataIni" com "01/01/2011"
    E eu preencho o campo (JS) "dataFim" com "29/07/2011"
    E eu seleciono "Candidato & Colaborador" de "Vínculo"
    E eu preencho "Colaborador" com "agnaldo"
    E eu seleciono "Todos" de "Motivo do Atendimento"
    E eu seleciono "Todas" de "Clínica"
    E eu marco "ASO"
    E eu seleciono "Normal" de "Resultado do Exame"

    Então eu clico no botão "Relatorio"
    E eu devo ver "Não existem exames realizados para os filtros informados."