-- Inserções na tabela tb_client
INSERT INTO tb_client(name, address, document, status) VALUES ('Maria', 'Rua um, 2', '123456781', 'ATIVA');
INSERT INTO tb_client(name, address, document, status) VALUES ('Bob', 'Rua dois, 3', '123456778', 'ATIVA');

-- Inserções na tabela tb_count, associando cada conta a um cliente
INSERT INTO tb_count(agencia, saldo, num_conta, password, client_id) VALUES ('1234', 5.0, '987654', '132456', 1);
INSERT INTO tb_count(agencia, saldo, num_conta, password, client_id) VALUES ('1285', 6.0, '585858', '132456', 2);
