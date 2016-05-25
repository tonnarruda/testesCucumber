ALTER TABLE perfil ADD COLUMN acessorestrito BOOLEAN DEFAULT false; --.go
INSERT INTO perfil (id, nome, acessorestrito) VALUES (nextval('perfil_sequence'), 'Usuário Comum (Acesso Restrito)', true); --.go
INSERT INTO perfil_papel (perfil_id, papeis_id) VALUES (currval('perfil_sequence'), 495); --.go
INSERT INTO perfil_papel (perfil_id, papeis_id) VALUES (currval('perfil_sequence'), 582); --.go
INSERT INTO perfil_papel (perfil_id, papeis_id) VALUES (currval('perfil_sequence'), 583); --.go
INSERT INTO perfil_papel (perfil_id, papeis_id) VALUES (currval('perfil_sequence'), 584); --.go
INSERT INTO perfil_papel (perfil_id, papeis_id) VALUES (currval('perfil_sequence'), 373); --.go
INSERT INTO perfil_papel (perfil_id, papeis_id) VALUES (currval('perfil_sequence'), 377); --.go
INSERT INTO perfil_papel (perfil_id, papeis_id) VALUES (currval('perfil_sequence'), 572); --.go
INSERT INTO perfil_papel (perfil_id, papeis_id) VALUES (currval('perfil_sequence'), 663); --.go
INSERT INTO perfil_papel (perfil_id, papeis_id) VALUES (currval('perfil_sequence'), 667); --.go
INSERT INTO perfil_papel (perfil_id, papeis_id) VALUES (currval('perfil_sequence'), 664); --.go
INSERT INTO perfil_papel (perfil_id, papeis_id) VALUES (currval('perfil_sequence'), 666); --.go
INSERT INTO perfil_papel (perfil_id, papeis_id) VALUES (currval('perfil_sequence'), 647); --.go
INSERT INTO perfil_papel (perfil_id, papeis_id) VALUES (currval('perfil_sequence'), 656); --.go
INSERT INTO perfil_papel (perfil_id, papeis_id) VALUES (currval('perfil_sequence'), 37 ); --.go
INSERT INTO perfil_papel (perfil_id, papeis_id) VALUES (currval('perfil_sequence'), 38 ); --.go