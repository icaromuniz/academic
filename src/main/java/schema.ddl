
    create table Docente (
        id int4 not null,
        dataCriacao time not null,
        usuarioCriacao varchar(11) not null,
        versao int4 not null,
        areaFormacao varchar(255),
        digitoAgencia varchar(3),
        digitoConta varchar(3),
        nomeBanco varchar(255),
        numeroAgencia varchar(10),
        numeroConta varchar(20),
        tipoConta varchar(20),
        ID_PESSOA_FISICA int4 not null unique,
        primary key (id)
    );

    create table Matricula (
        id int4 not null,
        dataCriacao time not null,
        usuarioCriacao varchar(11) not null,
        versao int4 not null,
        comoConheceu varchar(100) not null,
        formaPagamento varchar(100) not null,
        modulo1 bool not null,
        modulo2 bool not null,
        modulo3 bool not null,
        observacao varchar(255),
        id_pessoa_fisica int4 not null,
        id_turma int4 not null,
        primary key (id)
    );

    create table PessoaFisica (
        id int4 not null,
        dataCriacao time not null,
        usuarioCriacao varchar(11) not null,
        versao int4 not null,
        bairro varchar(255),
        cpf varchar(11) not null unique,
        dddAlternativo varchar(2),
        dddFixo varchar(2),
        email varchar(255),
        endereco varchar(255),
        nascimento date,
        nome varchar(255) not null,
        telefoneAlternativo varchar(9),
        telefoneFixo varchar(8),
        primary key (id)
    );

    create table Turma (
        id int4 not null,
        dataCriacao time not null,
        usuarioCriacao varchar(11) not null,
        versao int4 not null,
        dataInicio date not null,
        dataTermino date,
        nome varchar(255) not null,
        sala varchar(50),
        unidade varchar(50) not null,
        primary key (id)
    );

    create table Usuario (
        id int4 not null,
        dataCriacao time not null,
        usuarioCriacao varchar(11) not null,
        versao int4 not null,
        administrador bool not null,
        ativo bool not null,
        senha varchar(10),
        pessoaFisica_id int4 not null,
        primary key (id)
    );

    alter table Docente 
        add constraint FKD03455F2B9FD90DC 
        foreign key (ID_PESSOA_FISICA) 
        references PessoaFisica;

    alter table Matricula 
        add constraint FKB5B91D5EB9FD90DC 
        foreign key (id_pessoa_fisica) 
        references PessoaFisica;

    alter table Matricula 
        add constraint FKB5B91D5E2DADA413 
        foreign key (id_turma) 
        references Turma;

    alter table Usuario 
        add constraint FK5B4D8B0E9C886F0D 
        foreign key (pessoaFisica_id) 
        references PessoaFisica;

    create sequence SEQ_USUARIO;

    create sequence SE_DOCENTE;

    create sequence seq_matricula;

    create sequence seq_pessoa_fisica;

    create sequence seq_turma;
