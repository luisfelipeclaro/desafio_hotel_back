create table hospedes (
	id int generated always as identity,
	nome varchar(255) not null,
	document varchar(255) not null,
	telefone varchar(255) not null,
	primary key(id)
);

create table reservas (
	id int generated always as identity,
	hospede_id int not null,
	dataEntrada timestamp,
	dataSaida timestamp,
	adicionalVeiculo boolean,
	valor decimal(15,4) not null,
	primary key(id),
		constraint fk_hospede
			foreign key(hospede_id)
				references hospedes(id)
);