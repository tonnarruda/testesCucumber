update mensagem set tipo = 'U' where remetente not in ('AC Pessoal', 'Fortes RH', 'RH');--.go
update mensagem set tipo = 'A' where texto ilike 'Período de Experiência%' or texto ilike 'Lembrete: Faltam%Período de Experiência%';--.go
update mensagem set tipo = 'A' where texto ilike 'A avaliação do período de experiência do colaborador%';--.go
update mensagem set tipo = 'F' where texto ilike 'Nova ocorrência cadastrada para%';--.go
update mensagem set tipo = 'S' where texto ilike 'Existem EPI%';--.go
update mensagem set tipo = 'S' where texto ilike 'Não foi criada uma Solicitação de EPI%';--.go
update mensagem set tipo = 'F' where texto ilike 'Cancelamento de % do Colaborador%';--.go
update mensagem set tipo = 'D' where texto ilike 'O Colaborador%foi desligado no AC Pessoal%';--.go
update mensagem set tipo = 'D' where texto ilike 'Cancelamento da solicitação de desligamento do colaborador%';--.go
update mensagem set tipo = 'S' where texto ilike 'Foi inserido um afastamento para o colaborador%';--.go
update mensagem set tipo = 'F' where texto ilike 'Contratação efetuada para o colaborador%';--.go
update mensagem set tipo = 'F' where texto ilike '%Dados originais:%' or texto ilike '%atualizou seu endereço%';--.go
update mensagem set tipo = 'C' where texto ilike 'Cancelamento de Histórico da Faixa Salarial%';--.go
update mensagem set tipo = 'U' where tipo is null or tipo = '';--.go