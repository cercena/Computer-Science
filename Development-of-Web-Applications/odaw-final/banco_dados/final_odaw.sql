-- phpMyAdmin SQL Dump
-- version 4.6.6deb5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Tempo de geração: 01/10/2020 às 16:35
-- Versão do servidor: 5.7.31-0ubuntu0.18.04.1
-- Versão do PHP: 7.2.24-0ubuntu0.18.04.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `final_odaw`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `aluno_atividades`
--

CREATE TABLE `aluno_atividades` (
  `id_atividade` int(11) NOT NULL,
  `id_aluno` int(11) NOT NULL,
  `resposta` text NOT NULL,
  `nota` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Fazendo dump de dados para tabela `aluno_atividades`
--

INSERT INTO `aluno_atividades` (`id_atividade`, `id_aluno`, `resposta`, `nota`) VALUES
(6, 1, 'zxczxczxcxzczxc', 5);

-- --------------------------------------------------------

--
-- Estrutura para tabela `aluno_disciplina`
--

CREATE TABLE `aluno_disciplina` (
  `id_disciplina` int(11) NOT NULL,
  `id_aluno` int(11) NOT NULL,
  `nota` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Fazendo dump de dados para tabela `aluno_disciplina`
--

INSERT INTO `aluno_disciplina` (`id_disciplina`, `id_aluno`, `nota`) VALUES
(1, 1, 0),
(1, 5, 0),
(2, 5, 0);

-- --------------------------------------------------------

--
-- Estrutura para tabela `atividades`
--

CREATE TABLE `atividades` (
  `id_atividade` int(11) NOT NULL,
  `id_disciplina` int(11) NOT NULL,
  `titulo` varchar(50) NOT NULL,
  `descricao` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Fazendo dump de dados para tabela `atividades`
--

INSERT INTO `atividades` (`id_atividade`, `id_disciplina`, `titulo`, `descricao`) VALUES
(6, 1, 'Jogar futebol', 'Gasdasd'),
(7, 1, 'pular corda', ' asdasdasdas');

-- --------------------------------------------------------

--
-- Estrutura para tabela `chat_global`
--

CREATE TABLE `chat_global` (
  `id` int(11) NOT NULL,
  `mensagem` text NOT NULL,
  `from` varchar(50) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Fazendo dump de dados para tabela `chat_global`
--

INSERT INTO `chat_global` (`id`, `mensagem`, `from`, `timestamp`) VALUES
(1, 'asd', 'professor', '2020-09-29 16:30:44'),
(2, 'tudo bem', 'professor', '2020-09-29 16:31:00'),
(3, 'fala ai', 'professor', '2020-09-29 16:33:15'),
(4, 'qual Ã©', 'professor', '2020-09-29 16:33:19'),
(5, 'ola', 'professor2', '2020-09-29 16:34:39'),
(6, 'qq', 'professor2', '2020-09-29 16:34:44'),
(7, 'tudo beleza?', 'professor2', '2020-09-29 16:34:56'),
(8, 'asda', 'professor', '2020-10-01 19:29:27');

-- --------------------------------------------------------

--
-- Estrutura para tabela `presencas`
--

CREATE TABLE `presencas` (
  `id_aluno` int(11) NOT NULL,
  `id_disciplina` int(11) NOT NULL,
  `data` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Fazendo dump de dados para tabela `presencas`
--

INSERT INTO `presencas` (`id_aluno`, `id_disciplina`, `data`) VALUES
(1, 1, '2020-10-13');

-- --------------------------------------------------------

--
-- Estrutura para tabela `professor_disciplina`
--

CREATE TABLE `professor_disciplina` (
  `id_disciplina` int(11) NOT NULL,
  `nome_disciplina` varchar(30) NOT NULL,
  `id_professor` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Fazendo dump de dados para tabela `professor_disciplina`
--

INSERT INTO `professor_disciplina` (`id_disciplina`, `nome_disciplina`, `id_professor`) VALUES
(1, 'matematica', 3),
(2, 'fisica', 4);

-- --------------------------------------------------------

--
-- Estrutura para tabela `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `nome` varchar(30) NOT NULL,
  `senha` varchar(30) NOT NULL,
  `permissao` int(11) NOT NULL,
  `username` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Fazendo dump de dados para tabela `usuarios`
--

INSERT INTO `usuarios` (`id`, `nome`, `senha`, `permissao`, `username`) VALUES
(1, 'Nilton Mocelin', '214336414', 0, 'niltonmoc'),
(3, 'professor', 'professor', 1, 'professor'),
(4, 'professor2', 'professor2', 1, 'professor2'),
(5, 'joao', 'joao', 0, 'joao');

--
-- Índices de tabelas apagadas
--

--
-- Índices de tabela `aluno_atividades`
--
ALTER TABLE `aluno_atividades`
  ADD PRIMARY KEY (`id_atividade`,`id_aluno`);

--
-- Índices de tabela `aluno_disciplina`
--
ALTER TABLE `aluno_disciplina`
  ADD PRIMARY KEY (`id_disciplina`,`id_aluno`);

--
-- Índices de tabela `atividades`
--
ALTER TABLE `atividades`
  ADD PRIMARY KEY (`id_atividade`);

--
-- Índices de tabela `chat_global`
--
ALTER TABLE `chat_global`
  ADD PRIMARY KEY (`id`);

--
-- Índices de tabela `presencas`
--
ALTER TABLE `presencas`
  ADD PRIMARY KEY (`id_disciplina`,`id_aluno`);

--
-- Índices de tabela `professor_disciplina`
--
ALTER TABLE `professor_disciplina`
  ADD PRIMARY KEY (`id_disciplina`);

--
-- Índices de tabela `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de tabelas apagadas
--

--
-- AUTO_INCREMENT de tabela `atividades`
--
ALTER TABLE `atividades`
  MODIFY `id_atividade` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT de tabela `chat_global`
--
ALTER TABLE `chat_global`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT de tabela `professor_disciplina`
--
ALTER TABLE `professor_disciplina`
  MODIFY `id_disciplina` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de tabela `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
