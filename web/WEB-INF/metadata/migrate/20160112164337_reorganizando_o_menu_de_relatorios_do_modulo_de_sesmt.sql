INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (657, 'ROLE_EPI', 'EPI', '#', 3, true, 387);--.go

INSERT INTO perfil_papel (perfil_id, papeis_id) SELECT distinct perfil_id, 657 FROM perfil_papel where papeis_id in(432, 434, 489, 436);--.go
update papel set papelmae_id = 657, ordem = 1 where id = 434 ;--.go
update papel set papelmae_id = 657, ordem = 2 where id = 436 ;--.go
update papel set papelmae_id = 657, ordem = 3 where id = 489 ;--.go
update papel set papelmae_id = 657, ordem = 4 where id = 432 ;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (658, 'ROLE_EXAMES', 'Exames', '#', 7, true, 387);--.go
INSERT INTO perfil_papel (perfil_id, papeis_id) SELECT distinct perfil_id, 658 FROM perfil_papel where papeis_id in(431, 460, 559);--.go
update papel set papelmae_id = 658, ordem = 1 where id = 431 ;--.go
update papel set papelmae_id = 658, ordem = 2 where id = 460 ;--.go
update papel set papelmae_id = 658, ordem = 3 where id = 559 ;--.go

INSERT INTO papel (id, codigo, nome, url, ordem, menu, papelmae_id) VALUES (659, 'ROLE_AFASTAMENTOS', 'Afastamentos', '#', 8, true, 387);--.go
INSERT INTO perfil_papel (perfil_id, papeis_id) SELECT distinct perfil_id, 659 FROM perfil_papel where papeis_id in(442, 536 );--.go
update papel set papelmae_id = 659, ordem = 2 where id = 442 ;--.go
update papel set papelmae_id = 659, ordem = 1 where id = 536 ;--.go


update papel set ordem = 4 where id = 448;--.go
update papel set ordem = 5 where id = 389;--.go
update papel set ordem = 6 where id = 429;--.go
update papel set ordem = 9  where id = 449;--.go
update papel set ordem = 10 where id = 458;--.go
update papel set ordem = 11 where id = 459;--.go
update papel set ordem = 12 where id = 488;--.go
update papel set ordem = 13 where id = 642;--.go

alter sequence papel_sequence restart with 660;--.go