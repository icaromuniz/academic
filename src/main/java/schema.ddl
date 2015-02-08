
    create table PessoaFisica (
        id int4 not null,
        usuarioUltimaAlteracao varchar(30) not null,
        versao int4 not null,
        bairro varchar(255),
        cpf varchar(11) not null unique,
        dddAlternativo varchar(2),
        dddFixo varchar(2),
        email varchar(255),
        endereço varchar(255),
        nascimento date,
        nome varchar(255) not null,
        telefoneAlternativo varchar(9),
        telefoneFixo varchar(8),
        primary key (id)
    );

    create table Usuario (
        id int4 not null,
        usuarioUltimaAlteracao varchar(30) not null,
        versao int4 not null,
        login varchar(255),
        senha varchar(255),
        tipoUsuario varchar(255),
        pessoaFisica_id int4,
        primary key (id)
    );

    alter table Usuario 
        add constraint FK5B4D8B0E9C886F0D 
        foreign key (pessoaFisica_id) 
        references PessoaFisica;

    create sequence seq_pessoa_fisica;
