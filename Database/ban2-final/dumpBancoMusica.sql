--
-- PostgreSQL database dump
--

-- Dumped from database version 13.2
-- Dumped by pg_dump version 13.2

-- Started on 2021-07-23 12:40:33

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 202 (class 1259 OID 25134)
-- Name: album; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.album (
    id integer NOT NULL,
    nome character varying(50) NOT NULL,
    datalanc date NOT NULL,
    notamedia real NOT NULL,
    rank integer NOT NULL,
    idartista integer NOT NULL
);


ALTER TABLE public.album OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 25124)
-- Name: artista; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.artista (
    id integer NOT NULL,
    nome character varying(50) NOT NULL,
    bio character varying(300),
    tipo character(1) NOT NULL,
    dataorigem date NOT NULL,
    localorigem character varying(50) NOT NULL
);


ALTER TABLE public.artista OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 25139)
-- Name: avaliacao; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.avaliacao (
    id integer NOT NULL,
    texto character varying(300),
    nota real NOT NULL,
    idusuario integer NOT NULL,
    idalbum integer NOT NULL
);


ALTER TABLE public.avaliacao OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 25180)
-- Name: catalogo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.catalogo (
    id integer NOT NULL,
    idalbum integer NOT NULL,
    idusuario integer NOT NULL
);


ALTER TABLE public.catalogo OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 25144)
-- Name: genero; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.genero (
    id integer NOT NULL,
    nome character varying(25) NOT NULL,
    descricao character varying(300)
);


ALTER TABLE public.genero OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 25175)
-- Name: generoref; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.generoref (
    idgenero integer NOT NULL,
    idref integer NOT NULL
);


ALTER TABLE public.generoref OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 25152)
-- Name: gravadora; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.gravadora (
    id integer NOT NULL,
    nome character varying(50) NOT NULL,
    pais character varying(25) NOT NULL
);


ALTER TABLE public.gravadora OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 25170)
-- Name: membros; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.membros (
    id integer NOT NULL,
    idgrupo integer NOT NULL,
    idpessoa integer NOT NULL
);


ALTER TABLE public.membros OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 25149)
-- Name: musica; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.musica (
    id integer NOT NULL,
    nome character varying(50) NOT NULL,
    duracao real NOT NULL,
    idalbum integer NOT NULL
);


ALTER TABLE public.musica OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 25165)
-- Name: subgenero; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.subgenero (
    idgenero integer NOT NULL,
    idsubgenero integer NOT NULL
);


ALTER TABLE public.subgenero OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 25116)
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario (
    id integer NOT NULL,
    username character varying(25) NOT NULL,
    senha character varying(25) NOT NULL,
    nome character varying(50) NOT NULL,
    idade integer,
    sexo character(1),
    local character varying(50),
    descricao character varying(300),
    email character varying(50) NOT NULL,
    papel character(1) NOT NULL
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 25157)
-- Name: versao; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.versao (
    id integer NOT NULL,
    ano integer NOT NULL,
    midia character varying NOT NULL,
    pais character varying NOT NULL,
    idgravadora integer NOT NULL,
    idalbum integer NOT NULL
);


ALTER TABLE public.versao OWNER TO postgres;

--
-- TOC entry 3060 (class 0 OID 25134)
-- Dependencies: 202
-- Data for Name: album; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.album VALUES (1, 'Master of Puppets', '1986-01-01', 4.2, 1, 10);
INSERT INTO public.album VALUES (2, 'Technique', '1989-01-01', 3.9, 3, 20);
INSERT INTO public.album VALUES (3, 'Hounds of Love', '1985-01-01', 4.1, 2, 30);
INSERT INTO public.album VALUES (4, 'Metallica', '1991-01-01', 3.5, 4, 10);


--
-- TOC entry 3059 (class 0 OID 25124)
-- Dependencies: 201
-- Data for Name: artista; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.artista VALUES (10, 'Metallica', 'Banda de thrash metal', 'G', '1981-01-01', 'Estados Unidos');
INSERT INTO public.artista VALUES (20, 'New Order', 'Banda de synth pop', 'G', '1980-01-01', 'Reino Unido');
INSERT INTO public.artista VALUES (30, 'Kate Bush', 'Artista de art pop', 'G', '1958-01-01', 'Reino Unido');
INSERT INTO public.artista VALUES (100, 'James Hetfield', 'Guitarrista e vocalista da banda Metallica', 'P', '1963-01-01', 'Estados Unidos');


--
-- TOC entry 3061 (class 0 OID 25139)
-- Dependencies: 203
-- Data for Name: avaliacao; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.avaliacao VALUES (1, 'Perfeito', 5, 300, 1);
INSERT INTO public.avaliacao VALUES (2, NULL, 4, 200, 3);


--
-- TOC entry 3069 (class 0 OID 25180)
-- Dependencies: 211
-- Data for Name: catalogo; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3062 (class 0 OID 25144)
-- Dependencies: 204
-- Data for Name: genero; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.genero VALUES (1, 'Metal', NULL);
INSERT INTO public.genero VALUES (2, 'Thrash Metal', 'subgênero do Metal');
INSERT INTO public.genero VALUES (3, 'Pop', NULL);
INSERT INTO public.genero VALUES (4, 'Synth Pop', 'subgênero do Pop');
INSERT INTO public.genero VALUES (5, 'Art Pop', 'subgênero do Pop');
INSERT INTO public.genero VALUES (6, 'Alternative Dance', 'fusão de rock com dance');


--
-- TOC entry 3068 (class 0 OID 25175)
-- Dependencies: 210
-- Data for Name: generoref; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.generoref VALUES (2, 1);
INSERT INTO public.generoref VALUES (6, 2);
INSERT INTO public.generoref VALUES (4, 2);


--
-- TOC entry 3064 (class 0 OID 25152)
-- Dependencies: 206
-- Data for Name: gravadora; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.gravadora VALUES (99, 'Epic Records', 'Estados Unidos');
INSERT INTO public.gravadora VALUES (999, 'Capitol Records', 'Estados Unidos');


--
-- TOC entry 3067 (class 0 OID 25170)
-- Dependencies: 209
-- Data for Name: membros; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 3063 (class 0 OID 25149)
-- Dependencies: 205
-- Data for Name: musica; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.musica VALUES (111, 'Battery', 4, 1);
INSERT INTO public.musica VALUES (222, 'Master of Puppets', 6.5, 1);
INSERT INTO public.musica VALUES (11, 'Running Up That Hill', 3.5, 3);
INSERT INTO public.musica VALUES (22, 'Hounds of Love', 3.33, 3);


--
-- TOC entry 3066 (class 0 OID 25165)
-- Dependencies: 208
-- Data for Name: subgenero; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.subgenero VALUES (1, 2);


--
-- TOC entry 3058 (class 0 OID 25116)
-- Dependencies: 200
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.usuario VALUES (200, 'maria', '124', 'Maria', 19, 'M', 'SC', 'Ola', 'maria@gmail.com', 'U');
INSERT INTO public.usuario VALUES (100, 'joao', '124', 'Joao', 23, 'H', 'PR', 'Olaaa', 'joao@gmail.com', 'A');
INSERT INTO public.usuario VALUES (300, 'vitor', '124', 'Vitor', 20, 'H', 'SP', 'Olaaaaa', 'vitor@gmail.com', 'U');


--
-- TOC entry 3065 (class 0 OID 25157)
-- Dependencies: 207
-- Data for Name: versao; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.versao VALUES (12, 1986, 'Fita Cassete', 'Estados Unidos', 99, 1);
INSERT INTO public.versao VALUES (122, 1986, 'Vinil', 'Canadá', 99, 1);
INSERT INTO public.versao VALUES (13, 1985, 'Vinil', 'Reino Unido', 999, 3);
INSERT INTO public.versao VALUES (14, 1989, 'CD', 'Reino Unido', 99, 2);


--
-- TOC entry 2899 (class 2606 OID 25138)
-- Name: album album_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.album
    ADD CONSTRAINT album_pkey PRIMARY KEY (id);


--
-- TOC entry 2897 (class 2606 OID 25128)
-- Name: artista artista_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.artista
    ADD CONSTRAINT artista_pkey PRIMARY KEY (id);


--
-- TOC entry 2901 (class 2606 OID 25143)
-- Name: avaliacao avaliacao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.avaliacao
    ADD CONSTRAINT avaliacao_pkey PRIMARY KEY (id);


--
-- TOC entry 2913 (class 2606 OID 25184)
-- Name: catalogo catalogo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.catalogo
    ADD CONSTRAINT catalogo_pkey PRIMARY KEY (id);


--
-- TOC entry 2903 (class 2606 OID 25148)
-- Name: genero genero_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.genero
    ADD CONSTRAINT genero_pkey PRIMARY KEY (id);


--
-- TOC entry 2907 (class 2606 OID 25156)
-- Name: gravadora gravadora_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gravadora
    ADD CONSTRAINT gravadora_pkey PRIMARY KEY (id);


--
-- TOC entry 2911 (class 2606 OID 25174)
-- Name: membros membros_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.membros
    ADD CONSTRAINT membros_pkey PRIMARY KEY (id);


--
-- TOC entry 2905 (class 2606 OID 25186)
-- Name: musica musica_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.musica
    ADD CONSTRAINT musica_pkey PRIMARY KEY (id);


--
-- TOC entry 2895 (class 2606 OID 25123)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 2909 (class 2606 OID 25164)
-- Name: versao versao_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.versao
    ADD CONSTRAINT versao_pkey PRIMARY KEY (id);


--
-- TOC entry 2914 (class 2606 OID 25197)
-- Name: album album_id_artista_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.album
    ADD CONSTRAINT album_id_artista_fkey FOREIGN KEY (idartista) REFERENCES public.artista(id);


--
-- TOC entry 2916 (class 2606 OID 25192)
-- Name: avaliacao avaliacao_id_album_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.avaliacao
    ADD CONSTRAINT avaliacao_id_album_fkey FOREIGN KEY (idalbum) REFERENCES public.album(id);


--
-- TOC entry 2915 (class 2606 OID 25187)
-- Name: avaliacao avaliacao_id_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.avaliacao
    ADD CONSTRAINT avaliacao_id_usuario_fkey FOREIGN KEY (idusuario) REFERENCES public.usuario(id);


--
-- TOC entry 2926 (class 2606 OID 25237)
-- Name: catalogo catalogo_id_album_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.catalogo
    ADD CONSTRAINT catalogo_id_album_fkey FOREIGN KEY (idalbum) REFERENCES public.catalogo(id);


--
-- TOC entry 2927 (class 2606 OID 25242)
-- Name: catalogo catalogo_id_usuario_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.catalogo
    ADD CONSTRAINT catalogo_id_usuario_fkey FOREIGN KEY (idusuario) REFERENCES public.usuario(id);


--
-- TOC entry 2924 (class 2606 OID 25247)
-- Name: generoref generoref_id_genero_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.generoref
    ADD CONSTRAINT generoref_id_genero_fkey FOREIGN KEY (idgenero) REFERENCES public.genero(id);


--
-- TOC entry 2925 (class 2606 OID 25252)
-- Name: generoref generoref_id_ref_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.generoref
    ADD CONSTRAINT generoref_id_ref_fkey FOREIGN KEY (idref) REFERENCES public.album(id);


--
-- TOC entry 2922 (class 2606 OID 25227)
-- Name: membros membros_id_grupo_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.membros
    ADD CONSTRAINT membros_id_grupo_fkey FOREIGN KEY (idgrupo) REFERENCES public.artista(id);


--
-- TOC entry 2923 (class 2606 OID 25232)
-- Name: membros membros_id_pessoa_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.membros
    ADD CONSTRAINT membros_id_pessoa_fkey FOREIGN KEY (idpessoa) REFERENCES public.artista(id);


--
-- TOC entry 2917 (class 2606 OID 25202)
-- Name: musica musica_id_album_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.musica
    ADD CONSTRAINT musica_id_album_fkey FOREIGN KEY (idalbum) REFERENCES public.album(id);


--
-- TOC entry 2921 (class 2606 OID 25222)
-- Name: subgenero subgenero_id_genero_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subgenero
    ADD CONSTRAINT subgenero_id_genero_fkey FOREIGN KEY (idgenero) REFERENCES public.genero(id);


--
-- TOC entry 2920 (class 2606 OID 25217)
-- Name: subgenero subgenero_id_subgenero_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subgenero
    ADD CONSTRAINT subgenero_id_subgenero_fkey FOREIGN KEY (idsubgenero) REFERENCES public.genero(id);


--
-- TOC entry 2919 (class 2606 OID 25212)
-- Name: versao versao_id_album_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.versao
    ADD CONSTRAINT versao_id_album_fkey FOREIGN KEY (idalbum) REFERENCES public.album(id);


--
-- TOC entry 2918 (class 2606 OID 25207)
-- Name: versao versao_id_gravadora_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.versao
    ADD CONSTRAINT versao_id_gravadora_fkey FOREIGN KEY (idgravadora) REFERENCES public.gravadora(id);


-- Completed on 2021-07-23 12:40:34

--
-- PostgreSQL database dump complete
--

