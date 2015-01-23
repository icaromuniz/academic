
    create table PessoaFisica (
        id int4 not null,
        usuarioUltimaAlteracao varchar(30) not null,
        versao int4 not null,
        bairro varchar(255),
        cpf int4,
        email varchar(255),
        endereço varchar(255),
        nascimento timestamp,
        nome varchar(255),
        telefoneCelular int4,
        telefoneFixo int4,
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
