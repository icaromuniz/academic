
    create table PessoaFisica (
        id int4 not null,
        usuarioUltimaAlteracao varchar(30) not null,
        versao int4 not null,
        bairro varchar(255),
        cpf varchar(11) not null,
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
        pessoaFisica bytea,
        tipoUsuario varchar(255),
        primary key (id)
    );

    create sequence seq_pessoa_fisica;
