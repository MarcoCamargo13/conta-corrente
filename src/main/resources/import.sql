INSERT INTO tb_cpf(cpf) VALUES (123456781);
INSERT INTO tb_cpf(cpf) VALUES (123456778);

INSERT INTO tb_user(id,nome, senha, endereco) VALUES (1,'Maria', '132456', 'Rua 1, 2');
INSERT INTO tb_user(id, nome, senha, endereco) VALUES (2,'Bob', '132456', 'Rua 2, 3');

INSERT INTO tb_conta(user_id, agencia, status, saldo, num_conta) VALUES (1,'1234',true, 2451.22 , '987654');
INSERT INTO tb_conta(user_id, agencia, status, saldo, num_conta) VALUES (2,'1285',true, 25896.22, '585858');
