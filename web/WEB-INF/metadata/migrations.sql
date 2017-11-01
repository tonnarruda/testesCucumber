--Campos CAT --.go
ALTER TABLE cat ADD COLUMN tipoRegistardor BIGINT;--.go
ALTER TABLE cat ADD COLUMN tipoInscricao BIGINT;--.go
ALTER TABLE cat ADD COLUMN horasTrabalhadasAntesAcidente character varying(5);--.go
ALTER TABLE cat ADD COLUMN tipo BIGINT;--.go
ALTER TABLE cat ADD COLUMN obito boolean;--.go
ALTER TABLE cat ADD COLUMN dataObito date;--.go
ALTER TABLE cat ADD COLUMN dataCatOrigem date;--.go
ALTER TABLE cat ADD COLUMN comunicouPolicia boolean;--.go
ALTER TABLE cat ADD COLUMN iniciatCAT BIGINT;--.go
ALTER TABLE cat ADD COLUMN tipoLocal BIGINT;--.go
ALTER TABLE cat ADD COLUMN logradouro character varying(80);--.go
ALTER TABLE cat ADD COLUMN numero character varying(10);--.go
ALTER TABLE cat ADD COLUMN bairro character varying(60);--.go
ALTER TABLE cat ADD COLUMN cep character varying(8);--.go
ALTER TABLE cat ADD COLUMN cidade_id bigint;--.go
ALTER TABLE cat ADD COLUMN uf_id bigint;--.go
ALTER TABLE cat ADD COLUMN complemento character varying(30);--.go
ALTER TABLE cat ADD COLUMN cnpjLocalAcidente character varying(30);--.go
ALTER TABLE cat ADD COLUMN cpfRegistardor character varying(11);--.go
ALTER TABLE cat ADD COLUMN cnpjRegistardor character varying(12);--.go

--Atestado --.go
ALTER TABLE cat ADD COLUMN possuiAtestado boolean default false ;--.go
ALTER TABLE cat ADD COLUMN codigoCNES character varying(7);--.go 
ALTER TABLE cat ADD COLUMN dataAtendimento date;--.go
ALTER TABLE cat ADD COLUMN horaAtendimento character varying(5);--.go
ALTER TABLE cat ADD COLUMN duracaoTratamentoEmDias character varying(4);--.go
ALTER TABLE cat ADD COLUMN descricaoComplementarLesao character varying(200);--.go
ALTER TABLE cat ADD COLUMN diagnosticoProvavel character varying(100);--.go
ALTER TABLE cat ADD COLUMN codCID character varying(4);--.go
ALTER TABLE cat ADD COLUMN observacaoAtestado character varying(255);--.go
ALTER TABLE cat ADD COLUMN medicoNome character varying(70);--.go
ALTER TABLE cat ADD COLUMN orgaoDeClasse BIGINT;--.go
ALTER TABLE cat ADD COLUMN numericoInscricao character varying(14);--.go
ALTER TABLE cat ADD COLUMN indicativoInternacao boolean ;--.go
ALTER TABLE cat ADD COLUMN indicativoAfastamento boolean;--.go
ALTER TABLE cat ADD COLUMN ufAtestado_id bigint; --.go
ALTER TABLE cat ADD CONSTRAINT cat_estado_fk FOREIGN KEY (ufAtestado_id) REFERENCES estado(id);--.go

--Table Codificacao Acidente de Trabalho --.go
CREATE TABLE CodificacaoAcidenteTrabalho (
	id bigint NOT NULL,
	codigo character varying(6),
	descricao text
);--.go

ALTER TABLE CodificacaoAcidenteTrabalho ADD CONSTRAINT CodificacaoAcidenteTrabalho_pkey PRIMARY KEY(id);--.go
CREATE SEQUENCE CodificacaoAcidenteTrabalho_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

ALTER TABLE cat ADD COLUMN CodificacaoAcidenteTrabalho_id bigint;--.go
ALTER TABLE cat ADD CONSTRAINT cat_CodificacaoAcidenteTrabalho_fk FOREIGN KEY (CodificacaoAcidenteTrabalho_id) REFERENCES CodificacaoAcidenteTrabalho(id);--.go

INSERT INTO CodificacaoAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('CodificacaoAcidenteTrabalho_sequence'),'1.0.01','Lesão corporal que cause a morte ou a perda ou redução, permanente ou temporária, da capacidade para o trabalho, desde que não enquadrada em nenhum dos demais códigos.');--.go
INSERT INTO CodificacaoAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('CodificacaoAcidenteTrabalho_sequence'),'1.0.02','Perturbação funcional que cause a morte ou a perda ou redução, permanente ou temporária, da capacidade para o trabalho, desde que não enquadrada em nenhum dos demais códigos.');--.go
INSERT INTO CodificacaoAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('CodificacaoAcidenteTrabalho_sequence'),'2.0.01','Doença profissional, assim entendida a produzida ou desencadeada pelo exercício do trabalho peculiar a determinada atividade e constante da respectiva relação elaborada pelo Ministério do Trabalho e Previdência Social, desde que não enquadrada em nenhum dos demais códigos.');--.go
INSERT INTO CodificacaoAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('CodificacaoAcidenteTrabalho_sequence'),'2.0.02','Doença do trabalho, assim entendida a adquirida ou desencadeada em função de condições especiais em que o trabalho é realizado e com ele se relacione diretamente, constante da respectiva relação elaborada pelo Ministério do Trabalho e Previdência Social, desde que não enquadrada em nenhum dos demais códigos.');--.go
INSERT INTO CodificacaoAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('CodificacaoAcidenteTrabalho_sequence'),'2.0.03','Doença proveniente de contaminação acidental do empregado no exercício de sua atividade.');--.go
INSERT INTO CodificacaoAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('CodificacaoAcidenteTrabalho_sequence'),'2.0.04','Doença endêmica adquirida por segurado habitante de região em que ela se desenvolva quando resultante de exposição ou contato direto determinado pela natureza do trabalho.');--.go
INSERT INTO CodificacaoAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('CodificacaoAcidenteTrabalho_sequence'),'2.0.05','Doença profissional ou do trabalho não incluída na relação elaborada pelo Ministério do Trabalho e Previdência Social quando resultante das condições especiais em que o trabalho é executado e com ele se relaciona diretamente.');--.go
INSERT INTO CodificacaoAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('CodificacaoAcidenteTrabalho_sequence'),'2.0.06','Doença profissional ou do trabalho enquadrada na relação elaborada pelo Ministério do Trabalho e Previdência Social relativa nexo técnico epidemiológico previdenciário - NTEP.');--.go
INSERT INTO CodificacaoAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('CodificacaoAcidenteTrabalho_sequence'),'3.0.01','Acidente ligado ao trabalho que, embora não tenha sido a causa única, haja contribuído diretamente para a morte do segurado, para redução ou perda da sua capacidade para o trabalho, ou produzido lesão que exija atenção médica para a sua recuperação.');--.go
INSERT INTO CodificacaoAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('CodificacaoAcidenteTrabalho_sequence'),'3.0.02','Acidente sofrido pelo segurado no local e no horário do trabalho, em consequência de ato de agressão, sabotagem ou terrorismo praticado por terceiro ou companheiro de trabalho.');--.go
INSERT INTO CodificacaoAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('CodificacaoAcidenteTrabalho_sequence'),'3.0.03','Acidente sofrido pelo segurado no local e no horário do trabalho, em consequência de ofensa física intencional, inclusive de terceiro, por motivo de disputa relacionada ao trabalho.');--.go
INSERT INTO CodificacaoAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('CodificacaoAcidenteTrabalho_sequence'),'3.0.04','Acidente sofrido pelo segurado no local e no horário do trabalho, em consequência de ato de imprudência, de negligência ou de imperícia de terceiro ou de companheiro de trabalho.');--.go
INSERT INTO CodificacaoAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('CodificacaoAcidenteTrabalho_sequence'),'3.0.05','Acidente sofrido pelo segurado no local e no horário do trabalho, em consequência de ato de pessoa privada do uso da razão.');--.go
INSERT INTO CodificacaoAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('CodificacaoAcidenteTrabalho_sequence'),'3.0.06','Acidente sofrido pelo segurado no local e no horário do trabalho, em consequência de desabamento, inundação, incêndio e outros casos fortuitos ou decorrentes de força maior.');--.go
INSERT INTO CodificacaoAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('CodificacaoAcidenteTrabalho_sequence'),'3.0.07','Acidente sofrido pelo segurado ainda que fora do local e horário de trabalho na execução de ordem ou na realização de serviço sob a autoridade da empresa.');--.go
INSERT INTO CodificacaoAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('CodificacaoAcidenteTrabalho_sequence'),'3.0.08','Acidente sofrido pelo segurado ainda que fora do local e horário de trabalho na prestação espontânea de qualquer serviço à empresa para lhe evitar prejuízo ou proporcionar proveito.');--.go
INSERT INTO CodificacaoAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('CodificacaoAcidenteTrabalho_sequence'),'3.0.09','Acidente sofrido pelo segurado ainda que fora do local e horário de trabalho em viagem a serviço da empresa, inclusive para estudo quando financiada por esta dentro de seus planos para melhor capacitação da mão-de-obra, independentemente do meio de locomoção utilizado, inclusive veículo de propriedade do segurado.');--.go
INSERT INTO CodificacaoAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('CodificacaoAcidenteTrabalho_sequence'),'3.0.10','Acidente sofrido pelo segurado ainda que fora do local e horário de trabalho no percurso da residência para o local de trabalho ou deste para aquela, qualquer que seja o meio de locomoção, inclusive veículo de propriedade do segurado.');--.go
INSERT INTO CodificacaoAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('CodificacaoAcidenteTrabalho_sequence'),'3.0.11','Acidente sofrido pelo segurado nos períodos destinados a refeição ou descanso, ou por ocasião da satisfação de outras necessidades fisiológicas, no local do trabalho ou durante este.');--.go
INSERT INTO CodificacaoAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('CodificacaoAcidenteTrabalho_sequence'),'4.0.01','Suspeita de doenças profissionais ou do trabalho produzidas pelas condições especiais de trabalho, nos termos do art 169 da CLT.');--.go
INSERT INTO CodificacaoAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('CodificacaoAcidenteTrabalho_sequence'),'4.0.02','Constatação de ocorrência ou agravamento de doenças profissionais, através de exames médicos que incluam os definidos na NR 07; ou sendo verificadas alterações que revelem qualquer tipo de disfunção de órgão ou sistema biológico, através dos exames constantes dos Quadros I (apenas aqueles com interpretação SC) e II, e do item 7.4.2.3 desta NR, mesmo sem sintomatologia, caberá ao médico-coordenador ou encarregado.');--.go
INSERT INTO CodificacaoAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('CodificacaoAcidenteTrabalho_sequence'),'5.0.01','Outros.');--.go

--Table Situacao Geradora de Acidente de Trabalho --.go
CREATE TABLE SituacaoGeradoraAcidenteTrabalho (
	id bigint NOT NULL,
	codigo character varying(9),
	descricao text
);--.go

ALTER TABLE SituacaoGeradoraAcidenteTrabalho ADD CONSTRAINT SituacaoGeradoraAcidenteTrabalho_pkey PRIMARY KEY(id);--.go
CREATE SEQUENCE SituacaoGeradoraAcidenteTrabalho_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

ALTER TABLE cat ADD COLUMN situacaoGeradoraAcidenteTrabalho_id bigint;--.go
ALTER TABLE cat ADD CONSTRAINT cat_situacaoGeradoraAcidenteTrabalho_fk FOREIGN KEY (situacaoGeradoraAcidenteTrabalho_id) REFERENCES situacaoGeradoraAcidenteTrabalho(id);--.go

INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200004300','Impacto de pessoa contra objeto parado');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200004600','Impacto de pessoa contra objeto em movimento');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200008300','Impacto sofrido por pessoa de objeto que cai');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200008600','Impacto sofrido por pessoa de objeto projetado');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200008900','Impacto sofrido por pessoa, NIC');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200012200','Queda de pessoa com diferença de nível de andaime, passagem, plataforma, etc.');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200012300','Queda de pessoa com diferença de nível de escada móvel ou fixada cujos degraus.');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200012400','Queda de pessoa com diferença de nível de material empilhado');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200012500','Queda de pessoa com diferença de nível de veículo');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200012600','Queda de pessoa com diferença de nível em escada permanente');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200012700','Queda de pessoa com diferença de nível em poço, escavação, abertura no piso, etc.');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200012900','Queda de pessoa com diferença de nível, NIC');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200016300','Queda de pessoa em mesmo nível em passagem ou superfície de sustentação');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200016600','Queda de pessoa em mesmo nível sobre ou contra alguma coisa');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200016900','Queda de pessoa em mesmo nível, NIC');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200020100','Aprisionamento em, sobre ou entre objetos em movimento convergente');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200020300','Aprisionamento em, sobre ou entre objeto parado e outro em movimento');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200020500','Aprisionamento em, sobre ou entre dois ou mais objetos em movimento');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200020700','Aprisionamento em, sobre ou entre desabamento ou desmoronamento');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200020900','Aprisionamento em, sob ou entre, NIC');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200024300','Atrito ou abrasão por encostar, pisar, ajoelhar ou sentar em objeto');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200024400','Atrito ou abrasão por manusear objeto');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200024500','Atrito ou abrasão por objeto em vibração');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200024600','Atrito ou abrasão por corpo estranho no olho');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200024700','Atrito ou abrasão por compressão repetitiva');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200024900','Atrito ou abrasão, NIC');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200028300','Reação do corpo a movimento involuntário');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200028600','Reação do corpo a movimento voluntário');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200032200','Esforço excessivo ao erguer objeto');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200032400','Esforço excessivo ao empurrar ou puxar objeto');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200032600','Esforço excessivo ao manejar, sacudir ou arremessar objeto');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200032900','Esforço excessivo, NIC');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200036000','Elétrica, exposição a energia');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200040300','Temperatura muito alta, contato com objeto ou substância a');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200040600','Temperatura muito baixa, contato com objeto ou substância a');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200044300','Temperatura ambiente elevada, exposição a');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200044600','Temperatura ambiente baixa, exposição a');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200048200','Inalação de substância cáustica, tóxica ou nociva');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200048400','Ingestão de substância cáustica');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200048600','Absorção de substância cáustica');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200048900','Inalação, ingestão ou absorção, NIC');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200052000','Imersão');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200056000','Radiação não ionizante, exposição a');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200060000','Radiação ionizante, exposição a');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200064000','Ruído, exposição a');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200068000','Vibração, exposição a');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200072000','Pressão ambiente, exposição a');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200072300','Exposição à pressão ambiente elevada');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200072600','Exposição à pressão ambiente baixa');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200076200','Poluição da água, ação da (exposição à poluição da água)');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200076400','Poluição do ar, ação da (exposição à poluição do ar)');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200076600','Poluição do solo, ação da (exposição à poluição do solo)');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200076900','Poluição, NIC, exposição a (exposição à poluição, NIC)');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200080200','Ataque de ser vivo por mordedura, picada, chifrada, coice, etc.');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200080400','Ataque de ser vivo com peçonha');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200080600','Ataque de ser vivo com transmissão de doença');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'200080900','Ataque de ser vivo, NIC');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'209000000','Tipo, NIC');--.go
INSERT INTO SituacaoGeradoraAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraAcidenteTrabalho_sequence'),'209500000','Tipo inexistente');--.go

--Table Agente Causador do Acidente de Trabalho --.go
CREATE TABLE AgenteCausadorAcidenteTrabalho (
	id bigint NOT NULL,
	codigo character varying(9),
	descricao text
);--.go

ALTER TABLE AgenteCausadorAcidenteTrabalho ADD CONSTRAINT AgenteCausadorAcidenteTrabalho_pkey PRIMARY KEY(id);--.go
CREATE SEQUENCE AgenteCausadorAcidenteTrabalho_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

CREATE TABLE cat_AgenteCausadorAcidenteTrabalho(
    cat_id bigint NOT NULL,
    agentesCausadoresAcidenteTrabalho_id bigint NOT NULL
);--.go
ALTER TABLE cat_AgenteCausadorAcidenteTrabalho ADD CONSTRAINT cat_AgenteCausadorAcidenteTrabalho_cat_fk FOREIGN KEY (cat_id) REFERENCES cat(id);--.go 
ALTER TABLE cat_AgenteCausadorAcidenteTrabalho ADD CONSTRAINT cat_AgenteCausadorAcidenteTrabalho_fk FOREIGN KEY (agentesCausadoresAcidenteTrabalho_id) REFERENCES AgenteCausadorAcidenteTrabalho(id);--.go

INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'302010200','Rua e estrada - superfície utilizada para sustentar pessoas');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'302010250','Calçada ou caminho para pedestre - superfície utilizada para sustentar pessoas');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'302010300','Piso de edifício - superfície utilizada para sustentar pessoas');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'302010350','Escada permanente cujos degraus permitem apoio integral do pé, degrau - superfície utilizada para sustentar pessoas');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'302010400','Rampa - superfície utilizada para sustentar pessoas');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'302010450','Passarela ou plataforma permanentes - superfície utilizada para sustentar pessoas');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'302010500','Piso de mina - superfície utilizada para sustentar pessoas');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'302010550','Chão - superfície utilizada para sustentar pessoas');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'302010600','Piso de andaime e plataforma desmontável - superfície utilizada para sustentar pessoas');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'302010650','Piso de veiculo - superfície utilizada para sustentar pessoas');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'302010700','Telhado');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'302010900','Superfície de sustentação, NIC - superfície utilizada para sustentar pessoas');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'302030900','Escada móvel ou fixada, NIC');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'302050100','Edifício - edifício ou estrutura');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'302050200','Depósito fixo (tanque, silo, paiol, etc.) - edifício ou estrutura');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'302050300','Cais, doca - edifício ou estrutura');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'302050400','Dique, barragem – edifício ou estrutura');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'302050500','Ponte, viaduto - edifício ou estrutura');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'302050600','Arquibancada, estádio - edifício ou estrutura');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'302050700','Andaime, plataforma - edifício ou estrutura');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'302050800','Torre, poste - edifício ou estrutura');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'302050900','Edifício ou estrutura (exceto piso, superfície de sustentação ou área de circulação), NIC');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'302070100','Escavação (para edifício, estrada, etc.)');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'302070300','Canal, fosso');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'302070500','Poço, entrada, galeria, etc., de mina');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'302070700','Túnel');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'302070900','Escavação, fosso, túnel, NIC');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'302090000','Superfície e estrutura, NIC');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303010040','Martelo, malho, marreta - ferramenta manual sem força motriz');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303010080','Machadinha, enxó - ferramenta manual sem força motriz');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303010120','Faca, facão - ferramenta manual sem força motriz');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303010160','Tesoura, tesourão - ferramenta manual sem força motriz');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303010200','Formão, cinzel - ferramenta manual sem força motriz');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303010240','Serra, serrote - ferramenta manual sem força motriz');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303010280','Alicate, torques, tenaz - ferramenta manual sem força motriz');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303010320','Plaina - ferramenta manual sem força motriz');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303010360','Lima, grosa - ferramenta manual sem força motriz');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303010400','Punção, ponteiro, vazador, talhadeira - ferramenta manual sem força motriz');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303010440','Pua, trado, verruma, máquina de furar manual - ferramenta manual sem força motriz');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303010480','Chave de parafuso - ferramenta manual sem força motriz');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303010520','chave de porca ou de abertura regulável, chave de boca - ferramenta manual sem força motriz');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303010560','Alavanca, pé-de-cabra - ferramenta manual sem força motriz');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303010600','Corda, cabo, corrente - ferramenta manual sem força motriz');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303010640','Machado - ferramenta manual sem força motriz');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303010680','Enxada, enxadão, sacho - ferramenta manual sem força motriz');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303010720','Pá, cavadeira - ferramenta manual sem força motriz');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303010760','Picareta - ferramenta manual sem força motriz');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303010800','Garfo, ancinho, forcado - ferramenta manual sem força motriz');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303010900','Ferramenta manual sem força motriz, NIC');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303015050','Martelete, socador - ferramenta portátil com força motriz ou aquecimento');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303015100','Talhadeira - ferramenta portátil com força motriz ou aquecimento');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303015150','Cortadeira, guilhotina - ferramenta portátil com força motriz ou aquecimento');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303015200','Serra - ferramenta portátil com força motriz ou aquecimento');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303015250','Punção, ponteiro, vazador - ferramenta portátil com força motriz ou aquecimento');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303015300','Perfuratriz - ferramenta portátil com força motriz ou aquecimento');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303015350','Rebitadeira - ferramenta portátil com força motriz ou aquecimento');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303015400','Máquina de aparafusar - ferramenta portátil com força motriz ou aquecimento');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303015450','Esmeril - ferramenta portátil com força motriz ou aquecimento');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303015500','Politriz, enceradeira - ferramenta portátil com força motriz ou aquecimento');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303015550','Ferro de passar - ferramenta portátil com força motriz ou aquecimento');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303015600','Ferramenta de soldagem - ferramenta portátil com força motriz ou aquecimento');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303015650','Maçarico - ferramenta portátil com força motriz ou aquecimento');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303015700','Ferramenta acionada por explosivo - ferramenta portátil com força motriz ou aquecimento');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303015750','Jato de areia - ferramenta portátil com força motriz ou aquecimento');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303015900','Ferramenta portátil com forca motriz ou aquecimento, NIC');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303020040','Serra - máquina');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303020080','Tesoura, guilhotina, máquina de cortar - máquina');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303020120','Laminadora, calandra - máquina');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303020160','Furadeira, broqueadeira, torno, freza - máquina');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303020200','Prensa - máquina');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303020240','Plaina, tupia - máquina');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303020280','Máquina de fundir, de forjar, de soldar');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303020320','Britador, moinho - máquina');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303020360','Misturador, batedeira, agitador - máquina');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303020400','Peneira mecânica, máquina separadora - máquina');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303020440','Politriz, lixadora, esmeril - máquina');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303020480','Máquina de terraplenagem e construção de estrada');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303020520','Máquina de mineração e perfuração (de túnel, poço, etc.)');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303020560','Máquina agrícola');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303020600','Máquina têxtil');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303020640','Máquina de costurar e de pespontar');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303020680','Máquina de imprimir');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303020720','Máquina de escritório');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303020760','Máquina de embalar ou empacotar');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303020900','Máquina, NIC');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303025300','Transportador por gravidade');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303025600','Transportador com força motriz');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303025900','Transportador, NIC');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303030050','Guindaste - equipamento de guindar');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303030100','Ponte rolante - equipamento de guindar');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303030150','Elevador - equipamento de guindar');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303030200','Elevador de caçamba para mineração - equipamento de guindar');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303030250','Pá mecânica, draga - equipamento de guindar');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303030300','Talha - equipamento de guindar');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303030350','Pau de carga - equipamento de guindar');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303030400','Macaco (mecânico, hidráulico, pneumático) - equipamento de guindar');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303030450','Guincho pneumático - equipamento de guindar');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303030500','Guincho elétrico - equipamento de guindar');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303030900','Equipamento de guindar, NIC');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303035300','Correia - dispositivo de transmissão de energia mecânica');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303035400','Corrente, corda, cabo - dispositivo de transmissão de energia mecânica');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303035500','Tambor, polia, roldana - dispositivo de transmissão de energia mecânica');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303035600','Embreagem de fricção - dispositivo de transmissão de energia mecânica');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303035700','Engrenagem - dispositivo de transmissão de energia mecânica');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303035900','Dispositivo de transmissão de energia mecânica, NIC');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303040100','Gerador - equipamento elétrico');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303040200','Condutor - equipamento elétrico');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303040300','Transformador, conversor - equipamento elétrico');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303040400','Painel de controle, barramento, chave, interruptor, disjuntor, fusível - equipamento elétrico');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303040500','Reostato, dispositivo de partida e aparelho de controle, capacitor, retificador, bateria de acumuladores - equipamento elétrico');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303040600','Motor elétrico - equipamento elétrico');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303040700','Equipamento magnético - equipamento elétrico');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303040750','Equipamento eletrolítico - equipamento elétrico');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303040800','Equipamento de aquecimento elétrico - equipamento elétrico');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303040900','Equipamento elétrico, NIC');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303045200','Motor (combustão interna, vapor)');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303045400','Bomba');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303045600','Turbina');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303045900','Motor, bomba, turbina, NIC');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303050200','Caldeira');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303050400','Vaso sob pressão (para líquido, gás ou vapor)');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303050600','Tubo sob pressão (mangueira ou tubo para liquido, gás ou vapor)');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303050900','Caldeira, vaso sob pressão, NIC');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303055200','Caixão pneumático - equipamento para trabalho em ambiente de pressão anormal');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303055400','Escafandro - equipamento para trabalho em ambiente de pressão anormal');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303055600','Equipamento de mergulho - equipamento para trabalho em ambiente de pressão anormal');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303055900','Equipamento para trabalho em ambiente de pressão anormal, NIC');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303060000','Forno, estufa, retorta, aquecedor de ambiente, fogão, etc., exceto quando a lesão principal for choque elétrico ou eletroplessão - equipamento de aquecimento');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303065000','Equipamento emissor de radiação não ionizante');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303065300','Equipamento de iluminação - equipamento emissor de radiação não ionizante');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303065600','Arco elétrico - equipamento emissor de radiação não ionizante');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303065900','Equipamento emissor de radiação não ionizante, NIC');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303066300','Equipamento de iluminação');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303066600','Arco elétrico');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303070200','Equipamento de raios X - equipamento ou substância emissores de radiação ionizante');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303070400','Reator (inclui combustível e resíduo) - equipamento ou substância emissores de radiação ionizante');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303070600','Fonte de radioisótopo - equipamento ou substância emissores de radiação ionizante');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303070900','Equipamento ou substância emissores de radiação ionizante, NIC');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303075100','Bicicleta');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303075150','Triciclo');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303075200','Motocicleta, motoneta');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303075250','Veículo rodoviário motorizado');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303075300','Veículo sobre trilho');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303075350','Veículo aquático');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303075400','Aeronave');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303075450','Empilhadeira');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303075500','Rebocador mecânico, mula mecânica');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303075550','Carro-de-mão');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303075600','Trator');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303075650','Veículo de terraplenagem');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303075700','Veículo de tração animal');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303075750','Veículo deslizante');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303075800','Veículo funicular (tração por cabo)');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303075900','Veículo, NIC');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'303090000','Ferramenta, máquina, equipamento, veículo, NIC');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305004100','Composto metálico (de chumbo, mercúrio, zinco, cadmio, cromo, etc.)');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305004150','Composto de arsênio');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305004200','Gás carbônico (dióxido de carbono, CO2)');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305004250','Monóxido de carbono (CO)');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305004300','Óxidos de Nitrogênio (vapores nitrosos)');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305004350','Ácido');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305004400','Álcali');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305004450','Composto de fósforo');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305004500','Dissulfeto de carbono');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305004550','Cianeto ou composto de cianogênio');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305004600','Álcool');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305004650','Tetracloreto de carbono');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305004700','Composto orgânico halogenado (tricloretileno, percloretileno, cloreto de metilo, substâncias refrigerantes)');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305004750','Composto aromático (benzol, toluol, xilol, anilina, etc.)');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305004900','Substância química, NIC');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305008500','Água - usar quando o estado líquido contribuir preponderantemente para a ocorrência');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305008900','Líquido, NIC');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305020000','Partículas - não identificadas');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305024100','Pele, crina, pelo, lã (em bruto) - produto animal');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305024300','Pena - produto animal');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305024500','Couro cru ou curtido - produto animal');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305024700','Osso - produto animal');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305024900','Produto animal, NIC');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305028000','Madeira (toro, madeira serrada, pranchão, poste, barrote, ripa e produto de madeira)');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305032000','Produto mineral metálico - produto de mineração em bruto ou beneficiado, como minério e concentrado de minério');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305032500','Metal - inclui liga ferrosa e não ferrosa, tubo, placa, perfil, trilho, vergalhão, arame, porca, rebite, prego, etc. inclui metal fundido, lingote e sucata de fundição, exceto minério');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305036000','Produto mineral não metálico - produto de mineração, escavação, desbarrancamento, etc., como detrito, argila, areia, cascalho, pedra, etc.');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305040100','Petróleo bruto, bruto reduzido');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305040150','Asfalto, alcatrão, piche');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305040200','Óleo combustível');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305040250','Parafina, óleo lubrificante e de corte, graxas');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305040300','Gasóleo, óleo diesel');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305040350','Querosene');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305040400','Nafta e solvente de nafta (éter de petróleo, álcool mineral, solvente aromático, etc.)');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305040450','Gasolina (exceto quando a ocorrência for causada preponderantemente por composto de chumbo)');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305040500','Hidrocarboneto gasoso (inclui gás liquefeito, gás encanado de nafta, gás natural)');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305040600','Carvão');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305040650','Coque');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305040700','Gás encanado de carvão');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305040900','Produto de petróleo e de carvão, NIC');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305044000','Vidraria, fibra de vidro, lâmina, etc., exceto frasco, garrafa');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305048000','Cerâmica');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305048300','Tijolo e telha - cerâmica');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305048400','Louça de mesa e outros utensílios (de porcelana, barro, etc.) - cerâmica');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305048500','Tubo, manilha - cerâmica');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305048600','Revestimento cerâmico (azulejo, mosaico, etc.) - cerâmica');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305048700','Louca sanitária (pia, vaso sanitário, etc.) - cerâmica');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305048900','Cerâmica, NIC');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305052000','Têxteis - inclui fibras animais após o primeiro desengorduramento e limpeza, fibras vegetais e sintéticas (exceto vidro), fio, linha, tecido, passamanaria, feltro e produtos têxteis em geral');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305056000','Plástico - inclui pó, folha, trefilado, barra, perfil, etc., não incluindo produto a ser usado no fabrico de plástico');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305060000','Papel e pasta para papel');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305064000','Produtos alimentícios inclui carne leite e derivados legumes frutas cerais e derivados');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305064300','Carne e derivados - inclusive de origem animal');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305064400','Leite e derivados - inclusive de origem animal');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305064500','Legume, verdura e derivados');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305064600','Fruta e derivados');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305064700','Cereal e derivados');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305064900','Produto alimentício - inclusive de origem animal, NIC');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305068300','Medicamento em geral (exceto produto biológico)');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305068600','Produto biológico (soro, toxina, antitoxina, vacina, plasma) - medicamento');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305072000','Produto de limpeza, sabão, detergente');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305076000','Sucata, entulho, resíduo');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'305090000','Substância química, material, produto, NIC');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'306020000','Animal vivo');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'306040000','Vegetal - planta, árvore, em estado natural, não beneficiada (não inclui grão debulhado, fruto colhido, toro mesmo com galho)');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'306060000','Agente infeccioso ou parasitário - inclui bactéria, fungo, organismo parasitário, vírus, etc., não incluindo produto químico, preparado farmacêutico ou alimento');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'306090000','Ser vivo, NIC');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'307030100','Cadeira banco - mobiliário e acessórios');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'307030200','Mesa, carteira, exceto mesa elástica desmontável - mobiliário e acessórios');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'307030250','Mesa elástica desmontável - mobiliário e acessórios');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'307030300','Balcão, bancada - mobiliário e acessórios');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'307030400','Arquivo, fichário, estante - mobiliário e acessórios');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'307030500','Tapete, forração de piso, capacho - mobiliário e acessórios');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'307030600','Luminária, globo, lâmpada - mobiliário e acessórios');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'307030900','Mobiliário e acessórios, NIC');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'307040100','Caixa, engradado, caixote - embalagem, recipiente, vazio ou cheio');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'307040300','Frasco, garrafa - embalagem, recipiente, vazio ou cheio');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'307040500','Barril, barrica, barrilete, tambor - embalagem, recipiente, vazio ou cheio');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'307040700','Tanque, cilindro (transportáveis e não sob pressão) - embalagem, recipiente, vazio ou cheio');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'307040900','Embalagem e recipiente, vazio ou cheio, NIC');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'307050900','Vestuário, NIC');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'307070000','Área ou ambiente de trabalho - o agente do acidente ocorrido em consequência de fenômeno atmosférico, meteoro, etc., assim como da ação da radiação solar, deverá ser incluído neste item');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'309000000','Agente do acidente, NIC');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'309500000','Agente do acidente inexistente');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'354000000','Energia');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'354010300','Pressão ambiente alta trabalho em caixão pneumático mergulho');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'354010600','Pressão ambiente baixa ar rarefeito');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'354020000','Ruído');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'354040000','Fogo chama material incandescente ou quente fumaça');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'354050300','Temperatura ambiente - não inclui a de objeto ou substância quente');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'355016000','Aerodispersóides');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'355016600','Neblina');--.go
INSERT INTO AgenteCausadorAcidenteTrabalho(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('AgenteCausadorAcidenteTrabalho_sequence'),'355016800','Gás e vapor');--.go

--Tabela Agente Causador/Situação Geradora de Doença Profissional --.go
CREATE TABLE SituacaoGeradoraDoencaProfissional (
	id bigint NOT NULL,
	codigo character varying(9),
	descricao text
);--.go

ALTER TABLE SituacaoGeradoraDoencaProfissional ADD CONSTRAINT SituacaoGeradoraDoencaProfissional_pkey PRIMARY KEY(id);--.go
CREATE SEQUENCE SituacaoGeradoraDoencaProfissional_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

CREATE TABLE cat_SituacaoGeradoraDoencaProfissional(
    cat_id bigint NOT NULL,
    situacoesGeradoraDoencaProfissional_id bigint NOT NULL
);--.go
ALTER TABLE cat_SituacaoGeradoraDoencaProfissional ADD CONSTRAINT cat_SituacaoGeradoraDoencaProfissional_cat_fk FOREIGN KEY (cat_id) REFERENCES cat(id);--.go 
ALTER TABLE cat_SituacaoGeradoraDoencaProfissional ADD CONSTRAINT cat_SituacaoGeradoraDoencaProfissional_fk FOREIGN KEY (situacoesGeradoraDoencaProfissional_id) REFERENCES SituacaoGeradoraDoencaProfissional(id);--.go

INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200004300','Impacto de pessoa contra objeto parado. Aplica-se a casos em que a lesão foi produzida por impacto da pessoa acidentada contra a fonte da lesão, tendo sido o movimento que produziu o contato originalmente o da pessoa e não o da fonte da lesão, exceto quando o movimento do acidentado tiver sido provocado por queda. Inclui casos de alguém chocar-se contra alguma coisa, tropeçar em alguma coisa, ser empurrado ou projetado contra alguma coisa, etc. Não inclui casos de salto para nível inferior.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200004600','Impacto de pessoa contra objeto em movimento. Aplica-se a casos em que a lesão foi produzida por impacto da pessoa acidentada contra a fonte da lesão, tendo sido o movimento que produziu o contato originalmente o da pessoa e não o da fonte da lesão, exceto quando o movimento do acidentado tiver sido provocado por queda. Inclui casos de alguém chocar-se contra alguma coisa, tropeçar em alguma coisa, ser empurrado ou projetado contra alguma coisa, etc. Não inclui casos de salto para nível inferior.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200008300','Impacto sofrido por pessoa, de objeto que cai. Aplica-se a casos em que a lesão foi produzida por impacto entre o acidentado e a fonte da lesão, tendo sido da fonte da lesão e não do acidentado o movimento que originou o contato.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200008600','Impacto sofrido por pessoa, de objeto projetado. Aplica-se a casos em que a lesão foi produzida por impacto entre o acidentado e a fonte da lesão, tendo sido da fonte da lesão e não do acidentado o movimento que originou o contato.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200008900','Impacto sofrido por pessoa, NIC. Aplica-se a casos em que a lesão foi produzida por impacto entre o acidentado e a fonte da lesão, tendo sido da fonte da lesão e não do acidentado o movimento que originou o contato.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200012200','Queda de pessoa com diferença de nível de andaime, passagem, plataforma, etc. Aplica-se a casos em que a lesão foi produzida por impacto entre o acidentado e a fonte da lesão, tendo sido do acidentado o movimento que produziu o contato, nas seguintes circunstâncias: 1) O movimento do acidentado foi devido à ação da gravidade. 2) O ponto de contato com a fonte da lesão estava abaixo da superfície que suportava o acidentado no início da queda. Inclui salto para nível inferior.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200012300','Queda de pessoa com diferença de nível de escada móvel ou fixada cujos degraus não permitem o apoio integral do pé. Aplica-se a casos em que a lesão foi produzida por impacto entre o acidentado e a fonte da lesão, tendo sido do acidentado o movimento que produziu o contato, nas seguintes circunstâncias: 1) O movimento do acidentado foi devido à ação da gravidade. 2) O ponto de contato com a fonte da lesão estava abaixo da superfície que suportava o acidentado no início da queda. Inclui salto para nível inferior.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200012400','Queda de pessoa com diferença de nível de material empilhado. Aplica-se a casos em que a lesão foi produzida por impacto entre o acidentado e a fonte da lesão, tendo sido do acidentado o movimento que produziu o contato, nas seguintes circunstâncias: 1) O movimento do acidentado foi devido à ação da gravidade. 2) O ponto de contato com a fonte da lesão estava abaixo da superfície que suportava o acidentado no início da queda. Inclui salto para nível inferior.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200012500','Queda de pessoa com diferença de nível de veículo. Aplica-se a casos em que a lesão foi produzida por impacto entre o acidentado e a fonte da lesão, tendo sido do acidentado o movimento que produziu o contato, nas seguintes circunstâncias: 1) O movimento do acidentado foi devido à ação da gravidade. 2) O ponto de contato com a fonte da lesão estava abaixo da superfície que suportava o acidentado no início da queda. Inclui salto para nível inferior.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200012600','Queda de pessoa com diferença de nível em escada permanente cujos degraus permitem apoio integral do pé. Aplica-se a casos em que a lesão foi produzida por impacto entre o acidentado e a fonte da lesão, tendo sido do acidentado o movimento que produziu o contato, nas seguintes circunstâncias: 1) O movimento do acidentado foi devido à ação da gravidade. 2) O ponto de contato com a fonte da lesão estava abaixo da superfície que suportava o acidentado no início da queda. Inclui salto para nível inferior.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200012700','Queda de pessoa com diferença de nível em poço, escavação, abertura no piso, etc. (da borda da abertura). Aplica-se a casos em que a lesão foi produzida por impacto entre o acidentado e a fonte da lesão, tendo sido do acidentado o movimento que produziu o contato, nas seguintes circunstâncias: 1) O movimento do acidentado foi devido à ação da gravidade. 2) O ponto de contato com a fonte da lesão estava abaixo da superfície que suportava o acidentado no início da queda. Inclui salto para nível inferior.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200012900','Queda de pessoa com diferença de nível, NIC. Aplica-se a casos em que a lesão foi produzida por impacto entre o acidentado e a fonte da lesão, tendo sido do acidentado o movimento que produziu o contato, nas seguintes circunstâncias: 1) O movimento do acidentado foi devido à ação da gravidade. 2) O ponto de contato com a fonte da lesão estava abaixo da superfície que suportava o acidentado no início da queda. Inclui salto para nível inferior.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200016300','Queda de pessoa em mesmo nível em passagem ou superfície de sustentação. Aplica-se a casos em que a lesão foi produzida por Impacto entre o acidentado e um objeto externo, tendo sido do acidentado o movimento que produziu o contato, nas seguintes circunstâncias: 1) O movimento do acidentado foi devido à ação da gravidade com perda do equilíbrio e impossibilidade de manter-se de pé. 2) O ponto de contato com a fonte da lesão estava, no momento do início da queda, ao nível ou acima da superfície que suportava o acidentado.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200016600','Queda de pessoa em mesmo nível sobre ou contra alguma coisa. Aplica-se a casos em que a lesão foi produzida por Impacto entre o acidentado e um objeto externo, tendo sido do acidentado o movimento que produziu o contato, nas seguintes circunstâncias: 1) O movimento do acidentado foi devido à ação da gravidade com perda do equilíbrio e impossibilidade de manter-se de pé. 2) O ponto de contato com a fonte da lesão estava, no momento do início da queda, ao nível ou acima da superfície que suportava o acidentado.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200016900','Queda de pessoa em mesmo nível, NIC. Aplica-se a casos em que a lesão foi produzida por Impacto entre o acidentado e um objeto externo, tendo sido do acidentado o movimento que produziu o contato, nas seguintes circunstâncias: 1) O movimento do acidentado foi devido à ação da gravidade com perda do equilíbrio e impossibilidade de manter-se de pé. 2) O ponto de contato com a fonte da lesão estava, no momento do início da queda, ao nível ou acima da superfície que suportava o acidentado.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200020100','Aprisionamento em, sob ou entre objetos em movimento convergente (calandra) ou de encaixe. Aplica-se a casos, sem impacto, em que a lesão foi produzida por compressão, pinçamento ou esmagamento entre um objeto em movimento e outro parado, entre dois objetos em movimento ou entre partes de um mesmo objeto. Não se aplica quando a fonte da lesão for um objeto livremente projetado ou em queda livre.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200020300','Aprisionamento em, sob ou entre um objeto parado e outro em movimento. Aplica-se a casos, sem impacto, em que a lesão foi produzida por compressão, pinçamento ou esmagamento entre um objeto em movimento e outro parado, entre dois objetos em movimento ou entre partes de um mesmo objeto. Não se aplica quando a fonte da lesão for um objeto livremente projetado ou em queda livre.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200020500','Aprisionamento em, sob ou entre dois ou mais objetos em movimento (sem encaixe). Aplica-se a casos, sem impacto, em que a lesão foi produzida por compressão, pinçamento ou esmagamento entre um objeto em movimento e outro parado, entre dois objetos em movimento ou entre partes de um mesmo objeto. Não se aplica quando a fonte da lesão for um objeto livremente projetado ou em queda livre.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200020700','Aprisionamento em, sob ou entre desabamento ou desmoronamento de edificação, barreira, etc. Aplica-se a casos, sem impacto, em que a lesão foi produzida por compressão, pinçamento ou esmagamento entre um objeto em movimento e outro parado, entre dois objetos em movimento ou entre partes de um mesmo objeto. Não se aplica quando a fonte da lesão for um objeto livremente projetado ou em queda livre.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200020900','Aprisionamento em, sob ou entre, NIC. Aplica-se a casos, sem impacto, em que a lesão foi produzida por compressão, pinçamento ou esmagamento entre um objeto em movimento e outro parado, entre dois objetos em movimento ou entre partes de um mesmo objeto. Não se aplica quando a fonte da lesão for um objeto livremente projetado ou em queda livre.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200024300','Atrito ou abrasão por encostar, pisar, ajoelhar ou sentar em objeto (não em vibração). Aplica-se a casos, sem impacto, em que a lesão foi produzida por pressão, vibração ou atrito entre o acidentado e a fonte da lesão.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200024400','Atrito ou abrasão por manusear objeto (não em vibração). Aplica-se a casos, sem impacto, em que a lesão foi produzida por pressão, vibração ou atrito entre o acidentado e a fonte da lesão.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200024500','Atrito ou abrasão por objeto em vibração. Aplica-se a casos, sem impacto, em que a lesão foi produzida por pressão, vibração ou atrito entre o acidentado e a fonte da lesão.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200024600','Atrito ou abrasão por corpo estranho no olho. Aplica-se a casos, sem impacto, em que a lesão foi produzida por pressão, vibração ou atrito entre o acidentado e a fonte da lesão.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200024700','Atrito ou abrasão por compressão repetitiva Aplica-se a casos, sem impacto, em que a lesão foi produzida por pressão, vibração ou atrito entre o acidentado e a fonte da lesão.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200024900','Atrito ou abrasão, NIC. Aplica-se a casos, sem impacto, em que a lesão foi produzida por pressão, vibração ou atrito entre o acidentado e a fonte da lesão.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200028300','Reação do corpo a seus movimentos - movimento involuntário (escorregão sem queda, etc.). Aplica-se a casos, sem impacto, em que a lesão foi causada exclusivamente por movimento livre do corpo humano que causou tensão ou torção em alguma parte do corpo. Geralmente, aplica-se à ocorrência de torções, distensões, rupturas ou outras lesões internas, resultantes da adoção de uma posição forçada ou de movimentos involuntários provocados por sustos ou esforços de recuperação da posição normal em casos de escorregão ou perda de equilíbrio. Inclui casos de lesão muscular ou interna resultantes de movimentos individuais como andar, subir, correr, tentar alcançar algo, voltar-se, curvar-se, etc., quando tais movimentos forem a própria fonte da lesão. Não se aplica a esforço excessivo ao erguer, puxar ou empurrar objetos ou a casos em que o movimento do corpo, voluntário ou involuntário, tenha tido por resultado contato violento com algum objeto.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200028600','Reação do corpo a seus movimentos - movimento voluntário. Aplica-se a casos, sem impacto, em que a lesão foi causada exclusivamente por movimento livre do corpo humano que causou tensão ou torção em alguma parte do corpo. Geralmente, aplica-se à ocorrência de torções, distensões, rupturas ou outras lesões internas, resultantes da adoção de uma posição forçada ou de movimentos involuntários provocados por sustos ou esforços de recuperação da posição normal em casos de escorregão ou perda de equilíbrio. Inclui casos de lesão muscular ou interna resultantes de movimentos individuais como andar, subir, correr, tentar alcançar algo, voltar-se, curvar-se, etc., quando tais movimentos forem a própria fonte da lesão. Não se aplica a esforço excessivo ao erguer, puxar ou empurrar objetos ou a casos em que o movimento do corpo, voluntário ou involuntário, tenha tido por resultado contato violento com algum objeto.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200032200','Esforço excessivo ao erguer objeto. Ver explicações da classificação anterior (200028000).');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200032400','Esforço excessivo ao empurrar ou puxar objeto. Ver explicações da classificação anterior (200028000).');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200032600','Esforço excessivo ao manejar, sacudir ou arremessar objeto. Ver explicações da classificação anterior (200028000).');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200032900','Esforço excessivo, NIC. Ver explicações da classificação anterior (200028000).');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200036000','Exposição a energia elétrica. Aplica-se somente a casos sem impacto, em que a lesão consiste em choque elétrico, queimadura ou eletroplessão (eletrocussão).');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200040300','Contato com objeto ou substância a temperatura muito alta. Aplica-se somente a casos, sem impacto, em que a lesão consiste em queimadura, geladura, etc., resultante queimadura, geladura, etc., resultante de contato com objetos, ar, gases, vapores ou líquidos quentes ou frios. Não se aplica a casos em que a lesão foi provocada pelas características tóxicas ou cáusticas de produtos químicos ou a queimadura por descarga elétrica.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200040600','Contato com objeto ou substância a temperatura muito baixa. Aplica-se somente a casos, sem impacto, em que a lesão consiste em queimadura, geladura, etc., resultante queimadura, geladura, etc., resultante de contato com objetos, ar, gases, vapores ou líquidos quentes ou frios. Não se aplica a casos em que a lesão foi provocada pelas características tóxicas ou cáusticas de produtos químicos ou a queimadura por descarga elétrica.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200044300','Exposição à temperatura ambiente elevada. Não se aplica aos casos de lesão proveniente de exposição à radiação solar ou outras radiações. Também não se aplica a casos de queimadura ou geladura provocada por contato com objeto ou substância a temperaturas extremas ou queimadura devida à energia elétrica.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200044600','Não se aplica aos casos de lesão proveniente de exposição à radiação solar ou outras radiações. Também não se aplica a casos de queimadura ou geladura provocada por contato com objeto ou substância a temperaturas extremas ou queimadura devida à energia elétrica.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200048200','Inalação de substância cáustica, tóxica ou nociva. Aplica-se somente a casos, sem impacto, em que a lesão foi provocada por inalação, absorção ou ingestão de substâncias nocivas. Geralmente, refere-se a intoxicações, envenenamentos, queimaduras, irritações ou reações alérgicas por produtos químicos.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200048400','Ingestão de substancia cáustica, tóxica ou nociva. Aplica-se somente a casos, sem impacto, em que a lesão foi provocada por inalação, absorção ou ingestão de substâncias nocivas. Geralmente, refere-se a intoxicações, envenenamentos, queimaduras, irritações ou reações alérgicas por produtos químicos.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200048600','Absorção (por contato) de substância cáustica, tóxica ou nociva. Aplica-se somente a casos, sem impacto, em que a lesão foi provocada por inalação, absorção ou ingestão de substâncias nocivas. Geralmente, refere-se a intoxicações, envenenamentos, queimaduras, irritações ou reações alérgicas por produtos químicos.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200048900','Inalação, ingestão e absorção, NIC. Aplica-se somente a casos, sem impacto, em que a lesão foi provocada por inalação, absorção ou ingestão de substâncias nocivas. Geralmente, refere-se a intoxicações, envenenamentos, queimaduras, irritações ou reações alérgicas por produtos químicos.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200052000','Imersão. Aplica-se aos acidentes que têm por consequência o afogamento.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200056000','Exposição à radiação não ionizante. Aplica-se a casos em que as lesões são provocadas por exposição à radiação solar ou outras radiações não ionizantes (por exemplo: ultravioleta e infravermelho).');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200060000','Exposição à radiação ionizante.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200064000','Exposição ao ruído.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200068000','Exposição à vibração.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200072300','Exposição à pressão ambiente elevada.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200072600','Exposição à pressão ambiente baixa.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200076200','Exposição à poluição da água.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200076400','Exposição à poluição do ar.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200076600','Exposição à poluição do solo.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200076900','Exposição à poluição, NIC.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200080200','Ataque de ser vivo por mordedura, picada, chifrada, coice, etc., não se aplicando no caso de haver peçonha ou transmissão de doença.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200080400','Ataque de ser vivo com peçonha.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200080600','Ataque de ser vivo com transmissão de doença.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200080900','Ataque de ser vivo (inclusive do homem), NIC.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'200080901','Contato com pessoas doentes ou material infecto-contagiante - agentes biológicos.');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'209000000','Tipo, NIC');--.go
INSERT INTO SituacaoGeradoraDoencaProfissional(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('SituacaoGeradoraDoencaProfissional_sequence'),'209500000','Tipo inexistente');--.go

--Tabela Parte do Corpo Atingida --.go
CREATE TABLE ParteCorpoAtingida (
	id bigint NOT NULL,
	codigo character varying(9),
	descricao text
);--.go

ALTER TABLE ParteCorpoAtingida ADD CONSTRAINT ParteCorpoAtingida_pkey PRIMARY KEY(id);--.go
CREATE SEQUENCE ParteCorpoAtingida_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

CREATE TABLE parteAtingida(
    id bigint NOT NULL,
    lateralidade bigint NOT NULL,
    parteCorpoAtingida_id bigint NOT NULL
);--.go
ALTER TABLE ParteAtingida ADD CONSTRAINT parteAtingida_pkey PRIMARY KEY(id);--.go
ALTER TABLE ParteAtingida ADD CONSTRAINT parteAtingida_parteCorpoAtingida_fk FOREIGN KEY (parteCorpoAtingida_id) REFERENCES parteCorpoAtingida(id);--.go

CREATE TABLE cat_parteAtingida(
    cat_id bigint NOT NULL,
    partesAtingida_id bigint NOT NULL
);--.go
ALTER TABLE cat_parteAtingida ADD CONSTRAINT cat_parteAtingida_cat_fk FOREIGN KEY (cat_id) REFERENCES cat(id);--.go 
ALTER TABLE cat_parteAtingida ADD CONSTRAINT cat_parteAtingida_fk FOREIGN KEY (partesAtingida_id) REFERENCES parteAtingida(id);--.go
CREATE SEQUENCE parteAtingida_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'753030000','Crânio (inclusive encéfalo)');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'753050000','Ouvido (externo, médio, interno, audição e equilíbrio)');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'753070100','Olho (inclusive nervo ótico e visão)');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'753070300','Nariz (inclusive fossas nasais, seios da face e olfato)');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'753070500','Boca (inclusive lábios, dentes, língua, garganta e paladar)');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'753070700','Mandíbula (inclusive queixo)');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'753070800','Face, partes múltiplas (qualquer combinação das partes acima)');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'753080000','Cabeça, partes múltiplas (qualquer combinação das partes acima)');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'753090000','Cabeça, NIC');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'753510000','Braço (entre o punho a o ombro)');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'753510200','Braço (acima do cotovelo)');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'754000000','Pescoço');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'755010400','Cotovelo');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'755010600','Antebraço (entre o punho e o cotovelo)');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'755030000','Punho');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'755050000','Mão (exceto punho ou dedos)');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'755070000','Dedo');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'755080000','Membros superiores, partes múltiplas (qualquer combinação das partes acima)');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'755090000','Membros superiores, NIC');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'756020000','Ombro');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'756030000','Tórax (inclusive órgãos internos)');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'756040000','Dorso (inclusive músculos dorsais, coluna e medula espinhal)');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'756050000','Abdome (inclusive órgãos internos)');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'756060000','Quadris (inclusive pélvis, órgãos pélvicos e nádegas)');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'756070000','Tronco, partes múltiplas (qualquer combinação das partes acima)');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'756090000','Tronco, NIC');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'757010000','Perna (entre o tornozelo e a pélvis)');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'757010200','Coxa');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'757010400','Joelho');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'757010600','Perna (do tornozelo, exclusive, ao joelho, exclusive)');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'757030000','Articulação do tornozelo');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'757050000','Pé (exceto artelhos)');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'757070000','Artelho');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'757080000','Membros inferiores, partes múltiplas (qualquer combinação das partes acima)');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'757090000','Membros inferiores, NIC');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'758000000','Partes múltiplas. Aplica-se quando mais de uma parte importante do corpo for afetada, como por exemplo, um braço e uma perna');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'758500000','Sistemas e aparelhos. Aplica-se quando o funcionamento de todo um sistema ou aparelho do corpo humano for afetado, sem lesão específica de qualquer outra parte, como no caso do envenenamento, ação corrosiva que afete órgãos internos, lesão dos centros nervosos, etc. não se aplica quando a lesão sistêmica for provocada por lesão externa, como lesão dorsal que afete nervos da medula espinhal.');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'758520000','Aparelho circulatório');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'758530000','Aparelho respiratório');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'758540000','Sistema nervoso');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'758550000','Aparelho digestivo');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'758560000','Aparelho gênito-urinário');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'758570000','Sistema musculoesquelético');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'758590000','Sistemas e aparelhos, NIC');--.go
INSERT INTO ParteCorpoAtingida(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('ParteCorpoAtingida_sequence'),'759000000','Localização da lesão, NIC');--.go

--Table Descrição da Natureza da Lesão --.go
CREATE TABLE DescricaoNaturezaLesao (
	id bigint NOT NULL,
	codigo character varying(9),
	descricao text
);--.go

ALTER TABLE DescricaoNaturezaLesao ADD CONSTRAINT DescricaoNaturezaLesao_pkey PRIMARY KEY(id);--.go
CREATE SEQUENCE DescricaoNaturezaLesao_sequence START WITH 1 INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;--.go

ALTER TABLE cat ADD COLUMN descricaoNaturezaLesao_id bigint;--.go
ALTER TABLE cat ADD CONSTRAINT cat_descricaoNaturezaLesao_fk FOREIGN KEY (descricaoNaturezaLesao_id) REFERENCES descricaoNaturezaLesao(id);--.go

INSERT INTO DescricaoNaturezaLesao(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('DescricaoNaturezaLesao_sequence'),'702000000','Lesão imediata');--.go
INSERT INTO DescricaoNaturezaLesao(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('DescricaoNaturezaLesao_sequence'),'702005000','Escoriação, abrasão (ferimento superficial)');--.go
INSERT INTO DescricaoNaturezaLesao(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('DescricaoNaturezaLesao_sequence'),'702010000','Corte, laceração, ferida contusa, punctura (ferida aberta)');--.go
INSERT INTO DescricaoNaturezaLesao(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('DescricaoNaturezaLesao_sequence'),'702015000','Contusão, esmagamento (superfície cutânea intacta)');--.go
INSERT INTO DescricaoNaturezaLesao(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('DescricaoNaturezaLesao_sequence'),'702020000','Distensão, torção');--.go
INSERT INTO DescricaoNaturezaLesao(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('DescricaoNaturezaLesao_sequence'),'702025000','Inflamação de articulação, tendão ou músculo - inclui sinovite, tenossionovite, etc. Não inclui distensão, torção ou suas consequências');--.go
INSERT INTO DescricaoNaturezaLesao(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('DescricaoNaturezaLesao_sequence'),'702030000','Luxação');--.go
INSERT INTO DescricaoNaturezaLesao(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('DescricaoNaturezaLesao_sequence'),'702035000','Fratura');--.go
INSERT INTO DescricaoNaturezaLesao(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('DescricaoNaturezaLesao_sequence'),'702040000','Queimadura ou escaldadura - efeito de temperatura elevada. Efeito do contato com substância quente. Inclui queimadura por eletricidade, mas não inclui choque elétrico. Não inclui queimadura por substância química, efeito de radiação, queimadura de sol, incapacidade sistêmica como intermação, queimadura por atrito, etc.');--.go
INSERT INTO DescricaoNaturezaLesao(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('DescricaoNaturezaLesao_sequence'),'702042000','Queimadura química (lesão de tecido provocada pela ação corrosiva de produto químico, suas emanações, etc.)');--.go
INSERT INTO DescricaoNaturezaLesao(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('DescricaoNaturezaLesao_sequence'),'702045000','Efeito de radiação (imediato) - queimadura de sol e toda forma de lesão de tecido, osso ou fluido orgânico, por exposição à radiação');--.go
INSERT INTO DescricaoNaturezaLesao(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('DescricaoNaturezaLesao_sequence'),'702048000','Congelamento, geladura e outros efeitos da exposição à baixa temperatura');--.go
INSERT INTO DescricaoNaturezaLesao(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('DescricaoNaturezaLesao_sequence'),'702050000','Asfixia, estrangulamento, afogamento');--.go
INSERT INTO DescricaoNaturezaLesao(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('DescricaoNaturezaLesao_sequence'),'702055000','Intermação, insolação, cãibra, exaustão e outros efeitos da temperatura ambiente elevada - não inclui queimadura de sol ou outros efeitos de radiação');--.go
INSERT INTO DescricaoNaturezaLesao(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('DescricaoNaturezaLesao_sequence'),'702060000','Choque elétrico e eletroplessão (eletrocussão)');--.go
INSERT INTO DescricaoNaturezaLesao(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('DescricaoNaturezaLesao_sequence'),'702065000','Hérnia de qualquer natureza, ruptura');--.go
INSERT INTO DescricaoNaturezaLesao(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('DescricaoNaturezaLesao_sequence'),'702070000','Amputação ou enucleação');--.go
INSERT INTO DescricaoNaturezaLesao(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('DescricaoNaturezaLesao_sequence'),'702075000','Perda ou diminuição de sentido (audição, visão, olfato, paladar e tato, desde que não seja sequela de outra lesão)');--.go
INSERT INTO DescricaoNaturezaLesao(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('DescricaoNaturezaLesao_sequence'),'702080000','Concussão cerebral');--.go
INSERT INTO DescricaoNaturezaLesao(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('DescricaoNaturezaLesao_sequence'),'702090000','Lesão imediata, NIC');--.go
INSERT INTO DescricaoNaturezaLesao(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('DescricaoNaturezaLesao_sequence'),'704020000','Doença contagiosa ou infecciosa (tuberculose, brucelose, etc.)');--.go
INSERT INTO DescricaoNaturezaLesao(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('DescricaoNaturezaLesao_sequence'),'704030000','Pneumoconiose (silicose, asbestose, etc.)');--.go
INSERT INTO DescricaoNaturezaLesao(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('DescricaoNaturezaLesao_sequence'),'706090000','Outras lesões, NIC');--.go
INSERT INTO DescricaoNaturezaLesao(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('DescricaoNaturezaLesao_sequence'),'704040000','Dermatose (erupção, inflamação da pele, inclusive furúnculo, etc.). Geralmente provocada pelo contato direto com substâncias ou agentes sensibilizantes ou irritantes, tais como medicamentos, óleos, agentes biológicos, plantas, madeiras ou metais. Não inclui lesão provocada pela ação corrosiva de produtos químicos, queimadura por contato com substâncias quentes, efeito de exposição à radiação, efeito de exposição a baixas temperaturas ou inflamação ou irritação causada por fricção ou impacto');--.go
INSERT INTO DescricaoNaturezaLesao(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('DescricaoNaturezaLesao_sequence'),'704050000','Envenenamento sistêmico - condição mórbida sistêmica provocada por inalação, ingestão ou absorção cutânea de substância tóxica, que afete o metabolismo, o funcionamento do sistema nervoso, do aparelho circulatório, do aparelho digestivo, do aparelho respiratório, dos órgãos de excreção, do sistema músculo-esquelético, etc., inclui ação de produto químico, medicamento, metal ou peçonha. Não inclui efeito de radiação, pneumoconiose, efeito corrosivo de produto químico, irritação cutânea, septicemia ou caso de ferida infectada');--.go
INSERT INTO DescricaoNaturezaLesao(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('DescricaoNaturezaLesao_sequence'),'704060000','Perda ou diminuição mediatas de sentido (audição, visão, olfato, paladar e tato, desde que não seja sequela de outra lesão)');--.go
INSERT INTO DescricaoNaturezaLesao(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('DescricaoNaturezaLesao_sequence'),'704070000','Efeito de radiação (mediato) - queimadura do sol e toda forma de lesão de tecido, osso, ou fluido orgânico por exposição à radiação.');--.go
INSERT INTO DescricaoNaturezaLesao(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('DescricaoNaturezaLesao_sequence'),'704090000','Doença, NIC');--.go
INSERT INTO DescricaoNaturezaLesao(ID, CODIGO, DESCRICAO) VALUES (NEXTVAL('DescricaoNaturezaLesao_sequence'),'706050000','Lesões múltiplas');--.go