
    create table Aula (
        id int4 not null,
        dataCriacao timestamp not null,
        usuarioCriacao varchar(11) not null,
        versao int4 not null,
        aula_ativa bool,
        data timestamp not null,
        data_cancelamento timestamp,
        id_disciplina int4 not null,
        id_docente int4 not null,
        id_usuario_cancelamento int4,
        primary key (id)
    );

    create table Disciplina (
        id int4 not null,
        dataCriacao timestamp not null,
        usuarioCriacao varchar(11) not null,
        versao int4 not null,
        cargaHoraria int4 not null,
        ementa varchar(255),
        nome varchar(100) not null,
        sala varchar(50),
        id_docente int4 not null,
        id_turma int4 not null,
        primary key (id)
    );

    create table Docente (
        id int4 not null,
        dataCriacao timestamp not null,
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
        dataCriacao timestamp not null,
        usuarioCriacao varchar(11) not null,
        versao int4 not null,
        comoConheceu varchar(100) not null,
        dataCancelamento timestamp,
        formaPagamento varchar(100) not null,
        matricula_ativa bool,
        modulo1 bool not null,
        modulo2 bool not null,
        modulo3 bool not null,
        observacao varchar(255),
        id_pessoa_fisica int4 not null,
        id_turma int4 not null,
        id_usuario_cancelamento int4,
        primary key (id)
    );

    create table PessoaFisica (
        id int4 not null,
        dataCriacao timestamp not null,
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
        dataCriacao timestamp not null,
        usuarioCriacao varchar(11) not null,
        versao int4 not null,
        dataInicio date not null,
        dataTermino date,
        nome varchar(255) not null,
        sala varchar(50),
        unidade varchar(50) not null,
        valor numeric(19, 2),
        primary key (id)
    );

    create table Usuario (
        id int4 not null,
        dataCriacao timestamp not null,
        usuarioCriacao varchar(11) not null,
        versao int4 not null,
        administrador bool not null,
        ativo bool not null,
        senha varchar(10),
        pessoaFisica_id int4 not null,
        primary key (id)
    );

    alter table Aula 
        add constraint FK1F50C9B7D9972F 
        foreign key (id_disciplina) 
        references Disciplina;

    alter table Aula 
        add constraint FK1F50C9F445E57A 
        foreign key (id_usuario_cancelamento) 
        references Usuario;

    alter table Aula 
        add constraint FK1F50C9C5E5D32D 
        foreign key (id_docente) 
        references Docente;

    alter table Disciplina 
        add constraint FK8C4D778CC5E5D32D 
        foreign key (id_docente) 
        references Docente;

    alter table Disciplina 
        add constraint FK8C4D778C2DADA413 
        foreign key (id_turma) 
        references Turma;

    alter table Docente 
        add constraint FKD03455F2B9FD90DC 
        foreign key (ID_PESSOA_FISICA) 
        references PessoaFisica;

    alter table Matricula 
        add constraint FKB5B91D5EB9FD90DC 
        foreign key (id_pessoa_fisica) 
        references PessoaFisica;

    alter table Matricula 
        add constraint FKB5B91D5EF445E57A 
        foreign key (id_usuario_cancelamento) 
        references Usuario;

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

    create sequence seq_aula;

    create sequence seq_disciplina;

    create sequence seq_matricula;

    create sequence seq_pessoa_fisica;

    create sequence seq_turma;
