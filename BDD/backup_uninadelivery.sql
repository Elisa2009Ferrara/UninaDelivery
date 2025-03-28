PGDMP      (                }            UninaDelivery    16.0    16.0 w    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    24576    UninaDelivery    DATABASE     �   CREATE DATABASE "UninaDelivery" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Italian_Italy.1252';
    DROP DATABASE "UninaDelivery";
                postgres    false            r           1247    24608    tipo_pagamento    TYPE     s   CREATE TYPE public.tipo_pagamento AS ENUM (
    'tipo_carta_di_credito',
    'tipo_paypal',
    'tipo_bonifico'
);
 !   DROP TYPE public.tipo_pagamento;
       public          postgres    false            	           1255    24854 *   aggiornaquantitaprodotto(integer, integer) 	   PROCEDURE     �  CREATE PROCEDURE public.aggiornaquantitaprodotto(IN p_id_prodotto integer, IN nuova_quantita integer)
    LANGUAGE plpgsql
    AS $$
DECLARE
BEGIN
		UPDATE Prodotto
		SET quantita_disp = nuova_quantita
		WHERE id_prodotto = p_id_prodotto;

		RAISE NOTICE 'Quantità del prodotto % modificata.', p_id_prodotto;

EXCEPTION
	WHEN NO_DATA_FOUND THEN
		RAISE NOTICE 'Il prodotto non è stato trovato.';
	WHEN OTHERS THEN
		RAISE NOTICE 'Errore durante la modifica della quantità del prodotto.';
END;$$;
 e   DROP PROCEDURE public.aggiornaquantitaprodotto(IN p_id_prodotto integer, IN nuova_quantita integer);
       public          postgres    false                       1255    24853 k   aggiungiprodotto(integer, character varying, double precision, double precision, integer, double precision) 	   PROCEDURE       CREATE PROCEDURE public.aggiungiprodotto(IN p_id_magazzino integer, IN p_nome_prodotto character varying, IN p_dimensioni double precision, IN p_peso double precision, IN p_quantita_disp integer, IN p_prezzo double precision)
    LANGUAGE plpgsql
    AS $$
DECLARE
nuovo_id_prodotto INTEGER;
BEGIN
		SELECT NVL(MAX(id_prodotto), 0) + 1
		INTO nuovo_id_prodotto
		FROM Prodotto;

		INSERT INTO Prodotto(id_prodotto, nome_prodotto, dimensioni, peso, quantita_disp, prezzo, id_magazzino)
		VALUES (nuovo_id_prodotto, p_nome_prodotto, p_dimensioni, p_peso, p_quantita_disp, p_prezzo, p_id_magazzino);

		RAISE NOTICE 'È stato inserito un nuovo prodotto: % ', nuovo_id_prodotto;

EXCEPTION
	WHEN OTHERS THEN
	RAISE NOTICE 'Si è verificato un errore durante l''aggiunta del prodotto.';
END;$$;
 �   DROP PROCEDURE public.aggiungiprodotto(IN p_id_magazzino integer, IN p_nome_prodotto character varying, IN p_dimensioni double precision, IN p_peso double precision, IN p_quantita_disp integer, IN p_prezzo double precision);
       public          postgres    false                       1255    24822    assegnacorriere()    FUNCTION     �  CREATE FUNCTION public.assegnacorriere() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
p_n_telefono_corriere character(10);
BEGIN
   SELECT n_telefono_corriere INTO p_n_telefono_corriere
   FROM Corriere
   WHERE disponibilita = TRUE
   LIMIT 1;
   
   UPDATE Spedizione
   SET n_telefono_corriere = NEW.n_telefono_corriere
   WHERE id_spedizione = NEW.id_spedizione;

   UPDATE Corriere
   SET disponibilita = FALSE
   WHERE n_telefono_corriere = p_n_telefono_corriere;

RETURN NEW;
END;
$$;
 (   DROP FUNCTION public.assegnacorriere();
       public          postgres    false                       1255    24819    assegnamezzoditrasporto()    FUNCTION     �  CREATE FUNCTION public.assegnamezzoditrasporto() RETURNS trigger
    LANGUAGE plpgsql
    AS $$DECLARE
    targa_mezzo VARCHAR(7);
BEGIN
    SELECT targa INTO targa_mezzo
    FROM Mezzo_di_Trasporto
    WHERE disponibilita = TRUE
    LIMIT 1;

    UPDATE Spedizione
    SET targa = targa_mezzo
    WHERE id_spedizione = NEW.id_spedizione;

    UPDATE Mezzo_di_Trasporto
    SET disponibilita = FALSE
    WHERE targa = targa_mezzo;

    RETURN NEW;
END;
$$;
 0   DROP FUNCTION public.assegnamezzoditrasporto();
       public          postgres    false            �            1255    24825    assegnaprogrammazione()    FUNCTION     9  CREATE FUNCTION public.assegnaprogrammazione() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF NEW.id_programmazione IS NOT NULL THEN
        UPDATE Spedizione
        SET id_programmazione = NEW.id_programmazione
        WHERE id_spedizione = NEW.id_spedizione;
    END IF;

    RETURN NEW;
END;
$$;
 .   DROP FUNCTION public.assegnaprogrammazione();
       public          postgres    false                       1255    24834    availablecorrieri()    FUNCTION     �   CREATE FUNCTION public.availablecorrieri() RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
N_Corrieri_Disp INTEGER;
BEGIN
		SELECT COUNT(*) INTO N_Corrieri_Disp
		FROM Corriere
		WHERE Disponibilita = TRUE;
RETURN N_Corrieri_Disp;
END;
$$;
 *   DROP FUNCTION public.availablecorrieri();
       public          postgres    false                       1255    24835    availablemezziditrasporto()    FUNCTION     	  CREATE FUNCTION public.availablemezziditrasporto() RETURNS integer
    LANGUAGE plpgsql
    AS $$
DECLARE
N_Mezzi_Disp INTEGER;
BEGIN
     SELECT COUNT(*) INTO N_Mezzi_Disp
     FROM Mezzo_di_trasporto
     WHERE Disponibilita = TRUE;
RETURN N_Mezzi_Disp;
END;
$$;
 2   DROP FUNCTION public.availablemezziditrasporto();
       public          postgres    false            �            1255    24801    beforeinsertupdatepassword()    FUNCTION     �   CREATE FUNCTION public.beforeinsertupdatepassword() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	NEW.password = MD5Password(NEW.password);
	RETURN NEW;
END $$;
 3   DROP FUNCTION public.beforeinsertupdatepassword();
       public          postgres    false            �            1255    24798 /   checkamountpagamento(integer, double precision)    FUNCTION     �  CREATE FUNCTION public.checkamountpagamento(id_pagamento_param integer, importo_param double precision) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
BEGIN
    RETURN importo_param = (
        SELECT COALESCE(SUM(DETT.quantita * PR.prezzo), 0)
        FROM Ordine AS O
        JOIN Dettaglio_Ordine AS DETT ON O.id_ordine = DETT.id_ordine
        JOIN Prodotto AS PR ON DETT.id_prodotto = PR.id_prodotto
        WHERE O.id_pagamento = id_pagamento_param
    );
END;
$$;
 g   DROP FUNCTION public.checkamountpagamento(id_pagamento_param integer, importo_param double precision);
       public          postgres    false            �            1255    24803 '   checkconstraintarrivo(integer, integer)    FUNCTION       CREATE FUNCTION public.checkconstraintarrivo(id_programmazione_param integer, id_spedizione_param integer) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
DECLARE
    prox_consegna_val DATE;
    arrivo_prev_val DATE;
BEGIN
    SELECT prox_consegna
    INTO prox_consegna_val
    FROM Programmazione AS P
    WHERE P.id_programmazione = id_programmazione_param;

    SELECT arrivo_prev
    INTO arrivo_prev_val
    FROM Spedizione AS S
    WHERE S.id_spedizione = id_spedizione_param;

    RETURN prox_consegna_val = arrivo_prev_val;
END;
$$;
 j   DROP FUNCTION public.checkconstraintarrivo(id_programmazione_param integer, id_spedizione_param integer);
       public          postgres    false            �            1255    24816    checkquantitascelta()    FUNCTION     �  CREATE FUNCTION public.checkquantitascelta() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
		quantita_prodotto INT;
BEGIN
			SELECT quantita_disp INTO quantita_prodotto
			FROM Prodotto
			WHERE id_prodotto = NEW.id_prodotto;
	
			IF NEW.quantita > quantita_prodotto THEN
				RAISE EXCEPTION'La quantità selezionata supera la quantità disponibile del Prodotto.';
			END IF;	
RETURN NEW;
END;
$$;
 ,   DROP FUNCTION public.checkquantitascelta();
       public          postgres    false                        1255    24833    costototordine(integer)    FUNCTION     q  CREATE FUNCTION public.costototordine(p_id_ordine integer) RETURNS double precision
    LANGUAGE plpgsql
    AS $$
DECLARE
CostoTotale FLOAT;
BEGIN
			SELECT SUM(DETT.quantita * PR.prezzo) INTO CostoTotale
			FROM Dettaglio_Ordine AS DETT
			JOIN Prodotto AS PR ON (DETT.id_prodotto = PR.id_prodotto)
			WHERE DETT.id_ordine = p_Id_Ordine;
RETURN CostoTotale;
END;
$$;
 :   DROP FUNCTION public.costototordine(p_id_ordine integer);
       public          postgres    false            �            1259    24707 
   spedizione    TABLE       CREATE TABLE public.spedizione (
    id_spedizione integer NOT NULL,
    destinazione character varying(200) NOT NULL,
    data_sped date,
    societa character varying(100) NOT NULL,
    stato character varying(20) DEFAULT 'ordinato'::character varying,
    arrivo_prev date NOT NULL,
    id_ordine integer NOT NULL,
    id_programmazione integer,
    targa character(7),
    n_telefono_corriere character(10),
    CONSTRAINT constraintarrivo CHECK (public.checkconstraintarrivo(id_programmazione, id_spedizione))
);
    DROP TABLE public.spedizione;
       public         heap    postgres    false    237            �            1255    24831 .   elencospedizioniprogrammate(character varying)    FUNCTION     �  CREATE FUNCTION public.elencospedizioniprogrammate(email_cliente character varying) RETURNS SETOF public.spedizione
    LANGUAGE plpgsql
    AS $$
DECLARE
    tmp_record Spedizione%ROWTYPE;
    SelezionaSpedizioni CURSOR FOR
        SELECT
            S.id_spedizione,
            S.destinazione,
            S.data_sped,
            S.societa,
            S.stato,
            S.arrivo_prev
        FROM Spedizione AS S
        JOIN Ordine AS O ON S.id_ordine = O.id_ordine
        JOIN Cliente AS C ON O.email_cliente = C.email_cliente
        JOIN Programmazione P ON S.id_programmazione = P.id_programmazione
        WHERE C.email_cliente = email_cliente AND
            P.frequenza IN ('settimanale', 'mensile', 'trimestrale');
BEGIN
    OPEN SelezionaSpedizioni;
    LOOP
        FETCH SelezionaSpedizioni INTO tmp_record;
        EXIT WHEN NOT FOUND;
        RETURN NEXT tmp_record;
    END LOOP;
    CLOSE SelezionaSpedizioni;
    RETURN;
END;
$$;
 S   DROP FUNCTION public.elencospedizioniprogrammate(email_cliente character varying);
       public          postgres    false    232                       1255    24846    gestionepagamento(integer) 	   PROCEDURE     N  CREATE PROCEDURE public.gestionepagamento(IN id_pagamento_param integer)
    LANGUAGE plpgsql
    AS $$
DECLARE
importo_pagato DOUBLE PRECISION;
p_id_ordine INTEGER;
BEGIN
		SELECT importo, id_ordine INTO importo_pagato, p_id_ordine
		FROM Pagamento AS P
		WHERE id_pagamento_param = P.id_pagamento;

		IF importo_pagato = (
			SELECT SUM(DETT.quantita * PR.prezzo)
			FROM Ordine AS O
			JOIN Dettaglio_Ordine AS DETT ON (O.id_ordine,DETT.id_ordine)
			JOIN Prodotto AS PR ON (DETT.id_prodotto,PR.id_prodotto)
			WHERE O.id_ordine = p_id_ordine) THEN
			
			UPDATE Pagamento
			SET esito = TRUE
			WHERE id_pagamento = id_pagamento;

			UPDATE Ordine
			SET completamento = TRUE
			WHERE id_ordine = p_id_ordine;
		
			RAISE NOTICE 'Pagamento andato a buon fine, ordine confermato.';
			ELSE
				UPDATE Pagamento
				SET esito = FALSE
				WHERE id_pagamento = id_pagamento;
			RAISE NOTICE 'Pagamento non riuscito.';
		END IF;

EXCEPTION
	WHEN NO_DATA_FOUND THEN
		RAISE NOTICE 'Non è stato trovato nessun pagamento.';
	WHEN OTHERS THEN
		RAISE NOTICE 'Errore nel pagamento dell''ordine.';

END; $$;
 H   DROP PROCEDURE public.gestionepagamento(IN id_pagamento_param integer);
       public          postgres    false            �            1255    24827    insertspedizioneprog()    FUNCTION     R  CREATE FUNCTION public.insertspedizioneprog() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
BEGIN
    IF NEW.prox_consegna <> OLD.prox_consegna THEN
        INSERT INTO Spedizione (destinazione, data_sped, societa, stato, arrivo_prev, id_programmazione, id_ordine)
        SELECT 'Indirizzo cliente', NULL, 'Società di spedizioni', 'ordinato', NEW.prox_consegna, NEW.id_programmazione, O.id_ordine
        FROM Ordine AS O
        WHERE O.email_cliente = NEW.email_cliente AND O.data_ordine >= NEW.prox_consegna AND O.data_ordine < NEW.data_fine;
    END IF;
    RETURN NEW;
END;
$$;
 -   DROP FUNCTION public.insertspedizioneprog();
       public          postgres    false            �            1255    24812    isvalidassegnacorriere()    FUNCTION     ]  CREATE FUNCTION public.isvalidassegnacorriere() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	IF NOT EXISTS (SELECT 1
				FROM Corriere
				WHERE n_telefono_corriere = NEW.n_telefono_corriere AND disponibilita = TRUE) THEN
		RAISE EXCEPTION 'Impossibile associare un operatore a un corriere non disponibile.';
	END IF;
	RETURN NEW;
END;
$$;
 /   DROP FUNCTION public.isvalidassegnacorriere();
       public          postgres    false            �            1255    24810    isvalidassegnamezzo()    FUNCTION     R  CREATE FUNCTION public.isvalidassegnamezzo() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	IF NOT EXISTS (SELECT 1
				FROM Mezzo_di_Trasporto
				WHERE targa = NEW.targa AND disponibilita = TRUE) THEN
		RAISE EXCEPTION 'Impossibile associare un operatore a un mezzo di trasporto non disponibile.';
	END IF;
	RETURN NEW;
END;
$$;
 ,   DROP FUNCTION public.isvalidassegnamezzo();
       public          postgres    false            �            1255    24800    md5password(character varying)    FUNCTION     �   CREATE FUNCTION public.md5password(password_before character varying) RETURNS character varying
    LANGUAGE plpgsql
    AS $$
BEGIN
	RETURN MD5(password_before);
END $$;
 E   DROP FUNCTION public.md5password(password_before character varying);
       public          postgres    false                       1255    24838    pianificaspedizione(integer) 	   PROCEDURE     J  CREATE PROCEDURE public.pianificaspedizione(IN id_ordine integer)
    LANGUAGE plpgsql
    AS $$
DECLARE
quantita_ordine INTEGER;
id_spedizione INTEGER;
BEGIN
			SELECT SUM(DETT.quantita) INTO quantita_ordine
			FROM Dettaglio_Ordine AS DETT
			WHERE DETT.id_ordine = id_ordine;

			IF quantita_ordine > (
				 SELECT SUM(P.quantita_disp)
				 FROM Prodotto AS P
				 JOIN Dettaglio_Ordine AS DETT
				 ON (P.id_prodotto,DETT.id_prodotto)
				 WHERE DETT.id_ordine = id_ordine) THEN
			RAISE EXCEPTION 'La quantità richiesta supera la quantità disponibile.';
			ELSE
					INSERT INTO Spedizione(destinazione, data_sped, società, stato, arrivo_prev, id_ordine)
					VALUES ('Indirizzo cliente', NULL, 'Società di spedizione', 'ordinato', NULL, id_ordine);

      UPDATE Mezzo_di_Trasporto
      SET disponibilita = FALSE
      WHERE targa = (
            SELECT targa
            FROM Mezzo_di_Trasporto
            WHERE capacita_peso >= quantita_ordine
            AND disponibilita = TRUE
        );

      UPDATE Corriere
      SET disponibilita = FALSE
      WHERE n_telefono_corriere = (
            SELECT n_telefono_corriere
            FROM Corriere
            WHERE disponibilita = TRUE
        );

      UPDATE Spedizione
      SET stato = 'spedito', data_sped = CURRENT_DATE
      WHERE id_spedizione = id_spedizione;
    END IF;
END;$$;
 A   DROP PROCEDURE public.pianificaspedizione(IN id_ordine integer);
       public          postgres    false                       1255    24850    statospedizione(integer) 	   PROCEDURE       CREATE PROCEDURE public.statospedizione(IN s_id_spedizione integer)
    LANGUAGE plpgsql
    AS $$
DECLARE
s_stato_spedizione VARCHAR(20);
BEGIN
      SELECT stato
      FROM Spedzione
      WHERE id_spedizione = s_id_spedizione;

      IF s_stato_spedizione = 'spedito' THEN 
         UPDATE Spedizione 
         SET stato = 'consegnato'
         WHERE id_spedizione = s_id_spedizione;
		 RAISE NOTICE 'Stato della spedizione % aggiornato a consegnato.', s_id_spedizione;
	  ELSE
         RAISE NOTICE 'La spedizione % non è ancora in consegna.', s_id_spedizione;
      END IF;
   
EXCEPTION
      WHEN NO_DATA_FOUND THEN
         RAISE NOTICE 'Spedizione non trovata.';
      WHEN OTHERS THEN
         RAISE NOTICE 'Errore durante la conferma di consegna della spedizione.';
   END; $$;
 C   DROP PROCEDURE public.statospedizione(IN s_id_spedizione integer);
       public          postgres    false            �            1255    24805    updatecompletamentoordine()    FUNCTION       CREATE FUNCTION public.updatecompletamentoordine() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
BEGIN
  IF NEW.Esito = TRUE THEN
    UPDATE Ordine
    SET completamento = TRUE
    WHERE id_pagamento = NEW.id_pagamento;
  END IF;
  RETURN NEW;
END;
$$;
 2   DROP FUNCTION public.updatecompletamentoordine();
       public          postgres    false            �            1255    24829    updatedatasped()    FUNCTION     3  CREATE FUNCTION public.updatedatasped() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
BEGIN
    IF NEW.stato = 'spedito' AND OLD.stato <> 'spedito' THEN
        UPDATE Spedizione
        SET data_sped = CURRENT_DATE
        WHERE id_spedizione = NEW.id_spedizione;
    END IF;
RETURN NEW;
END;
$$;
 '   DROP FUNCTION public.updatedatasped();
       public          postgres    false            �            1259    24600    cliente    TABLE     )  CREATE TABLE public.cliente (
    email_cliente character varying(100) NOT NULL,
    n_telefono_cliente character(10),
    nome character varying(30) NOT NULL,
    cognome character varying(30) NOT NULL,
    data_nascita date NOT NULL,
    indirizzo_predefinito character varying(200) NOT NULL
);
    DROP TABLE public.cliente;
       public         heap    postgres    false            �            1259    24680    corriere    TABLE       CREATE TABLE public.corriere (
    n_telefono_corriere character(10) NOT NULL,
    nome_corriere character varying(30) NOT NULL,
    cognome_corriere character varying(30) NOT NULL,
    disponibilita boolean,
    email_operatore character varying(100) NOT NULL
);
    DROP TABLE public.corriere;
       public         heap    postgres    false            �            1259    24633    dettaglio_ordine    TABLE     �   CREATE TABLE public.dettaglio_ordine (
    id_dettaglio integer NOT NULL,
    quantita integer NOT NULL,
    id_ordine integer NOT NULL,
    id_prodotto integer NOT NULL
);
 $   DROP TABLE public.dettaglio_ordine;
       public         heap    postgres    false            �            1259    24632 !   dettaglio_ordine_id_dettaglio_seq    SEQUENCE     �   CREATE SEQUENCE public.dettaglio_ordine_id_dettaglio_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 8   DROP SEQUENCE public.dettaglio_ordine_id_dettaglio_seq;
       public          postgres    false    221            �           0    0 !   dettaglio_ordine_id_dettaglio_seq    SEQUENCE OWNED BY     g   ALTER SEQUENCE public.dettaglio_ordine_id_dettaglio_seq OWNED BY public.dettaglio_ordine.id_dettaglio;
          public          postgres    false    220            �            1259    24638 	   magazzino    TABLE        CREATE TABLE public.magazzino (
    id_magazzino integer NOT NULL,
    indirizzo character varying(200) NOT NULL,
    num_prodotti integer NOT NULL,
    data_controllo date NOT NULL,
    capacita integer NOT NULL,
    CONSTRAINT "constraintCapacita" CHECK ((num_prodotti <= capacita))
);
    DROP TABLE public.magazzino;
       public         heap    postgres    false            �            1259    24637    magazzino_id_magazzino_seq    SEQUENCE     �   CREATE SEQUENCE public.magazzino_id_magazzino_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 1   DROP SEQUENCE public.magazzino_id_magazzino_seq;
       public          postgres    false    223            �           0    0    magazzino_id_magazzino_seq    SEQUENCE OWNED BY     Y   ALTER SEQUENCE public.magazzino_id_magazzino_seq OWNED BY public.magazzino.id_magazzino;
          public          postgres    false    222            �            1259    24670    mezzo_di_trasporto    TABLE       CREATE TABLE public.mezzo_di_trasporto (
    targa character varying(7) NOT NULL,
    capacita_peso integer NOT NULL,
    capacita_spazio integer NOT NULL,
    disponibilita boolean,
    modello character varying(300) NOT NULL,
    email_operatore character varying(100) NOT NULL
);
 &   DROP TABLE public.mezzo_di_trasporto;
       public         heap    postgres    false            �            1259    24658 	   operatore    TABLE     8  CREATE TABLE public.operatore (
    email_operatore character varying(100) NOT NULL,
    password character varying(32) NOT NULL,
    nome_operatore character varying(30) NOT NULL,
    cognome_operatore character varying(30) NOT NULL,
    n_telefono_operatore character(10),
    id_magazzino integer NOT NULL
);
    DROP TABLE public.operatore;
       public         heap    postgres    false            �            1259    24628    ordine    TABLE     �   CREATE TABLE public.ordine (
    id_ordine integer NOT NULL,
    data_ordine date NOT NULL,
    completamento boolean,
    id_pagamento integer,
    email_cliente character varying(100) NOT NULL
);
    DROP TABLE public.ordine;
       public         heap    postgres    false            �            1259    24627    ordine_id_ordine_seq    SEQUENCE     �   CREATE SEQUENCE public.ordine_id_ordine_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 +   DROP SEQUENCE public.ordine_id_ordine_seq;
       public          postgres    false    219            �           0    0    ordine_id_ordine_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE public.ordine_id_ordine_seq OWNED BY public.ordine.id_ordine;
          public          postgres    false    218            �            1259    24616 	   pagamento    TABLE     �   CREATE TABLE public.pagamento (
    id_pagamento integer NOT NULL,
    importo double precision NOT NULL,
    tipo public.tipo_pagamento NOT NULL,
    esito boolean,
    email_cliente character varying(100) NOT NULL
);
    DROP TABLE public.pagamento;
       public         heap    postgres    false    882            �            1259    24615    pagamento_id_pagamento_seq    SEQUENCE     �   CREATE SEQUENCE public.pagamento_id_pagamento_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 1   DROP SEQUENCE public.pagamento_id_pagamento_seq;
       public          postgres    false    217            �           0    0    pagamento_id_pagamento_seq    SEQUENCE OWNED BY     Y   ALTER SEQUENCE public.pagamento_id_pagamento_seq OWNED BY public.pagamento.id_pagamento;
          public          postgres    false    216            �            1259    24646    prodotto    TABLE     �  CREATE TABLE public.prodotto (
    id_prodotto integer NOT NULL,
    nome_prodotto character varying(300) NOT NULL,
    dimensioni character varying(40) NOT NULL,
    peso double precision NOT NULL,
    quantita_disp integer NOT NULL,
    prezzo double precision NOT NULL,
    id_magazzino integer NOT NULL,
    CONSTRAINT "minValorePrezzo" CHECK ((prezzo > (0)::double precision))
);
    DROP TABLE public.prodotto;
       public         heap    postgres    false            �            1259    24645    prodotto_id_prodotto_seq    SEQUENCE     �   CREATE SEQUENCE public.prodotto_id_prodotto_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.prodotto_id_prodotto_seq;
       public          postgres    false    225            �           0    0    prodotto_id_prodotto_seq    SEQUENCE OWNED BY     U   ALTER SEQUENCE public.prodotto_id_prodotto_seq OWNED BY public.prodotto.id_prodotto;
          public          postgres    false    224            �            1259    24691    programmazione    TABLE     �  CREATE TABLE public.programmazione (
    id_programmazione integer NOT NULL,
    prox_consegna date,
    data_fine date NOT NULL,
    orario character varying(20) DEFAULT 'mattina'::character varying,
    frequenza character varying(20),
    email_cliente character varying(100) NOT NULL,
    CONSTRAINT "checkFrequenza" CHECK (((frequenza)::text = ANY ((ARRAY['settimanale'::character varying, 'mensile'::character varying, 'trimestrale'::character varying])::text[]))),
    CONSTRAINT "checkOrario" CHECK (((orario)::text = ANY ((ARRAY['mattina'::character varying, 'pomeriggio'::character varying])::text[]))),
    CONSTRAINT "validDateRange" CHECK ((prox_consegna <= data_fine))
);
 "   DROP TABLE public.programmazione;
       public         heap    postgres    false            �            1259    24690 $   programmazione_id_programmazione_seq    SEQUENCE     �   CREATE SEQUENCE public.programmazione_id_programmazione_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ;   DROP SEQUENCE public.programmazione_id_programmazione_seq;
       public          postgres    false    230            �           0    0 $   programmazione_id_programmazione_seq    SEQUENCE OWNED BY     m   ALTER SEQUENCE public.programmazione_id_programmazione_seq OWNED BY public.programmazione.id_programmazione;
          public          postgres    false    229            �            1259    24706    spedizione_id_spedizione_seq    SEQUENCE     �   CREATE SEQUENCE public.spedizione_id_spedizione_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 3   DROP SEQUENCE public.spedizione_id_spedizione_seq;
       public          postgres    false    232            �           0    0    spedizione_id_spedizione_seq    SEQUENCE OWNED BY     ]   ALTER SEQUENCE public.spedizione_id_spedizione_seq OWNED BY public.spedizione.id_spedizione;
          public          postgres    false    231            �           2604    24883    dettaglio_ordine id_dettaglio    DEFAULT     �   ALTER TABLE ONLY public.dettaglio_ordine ALTER COLUMN id_dettaglio SET DEFAULT nextval('public.dettaglio_ordine_id_dettaglio_seq'::regclass);
 L   ALTER TABLE public.dettaglio_ordine ALTER COLUMN id_dettaglio DROP DEFAULT;
       public          postgres    false    221    220    221            �           2604    24884    magazzino id_magazzino    DEFAULT     �   ALTER TABLE ONLY public.magazzino ALTER COLUMN id_magazzino SET DEFAULT nextval('public.magazzino_id_magazzino_seq'::regclass);
 E   ALTER TABLE public.magazzino ALTER COLUMN id_magazzino DROP DEFAULT;
       public          postgres    false    223    222    223            �           2604    24885    ordine id_ordine    DEFAULT     t   ALTER TABLE ONLY public.ordine ALTER COLUMN id_ordine SET DEFAULT nextval('public.ordine_id_ordine_seq'::regclass);
 ?   ALTER TABLE public.ordine ALTER COLUMN id_ordine DROP DEFAULT;
       public          postgres    false    219    218    219            �           2604    24886    pagamento id_pagamento    DEFAULT     �   ALTER TABLE ONLY public.pagamento ALTER COLUMN id_pagamento SET DEFAULT nextval('public.pagamento_id_pagamento_seq'::regclass);
 E   ALTER TABLE public.pagamento ALTER COLUMN id_pagamento DROP DEFAULT;
       public          postgres    false    217    216    217            �           2604    24887    prodotto id_prodotto    DEFAULT     |   ALTER TABLE ONLY public.prodotto ALTER COLUMN id_prodotto SET DEFAULT nextval('public.prodotto_id_prodotto_seq'::regclass);
 C   ALTER TABLE public.prodotto ALTER COLUMN id_prodotto DROP DEFAULT;
       public          postgres    false    224    225    225            �           2604    24888     programmazione id_programmazione    DEFAULT     �   ALTER TABLE ONLY public.programmazione ALTER COLUMN id_programmazione SET DEFAULT nextval('public.programmazione_id_programmazione_seq'::regclass);
 O   ALTER TABLE public.programmazione ALTER COLUMN id_programmazione DROP DEFAULT;
       public          postgres    false    230    229    230            �           2604    24889    spedizione id_spedizione    DEFAULT     �   ALTER TABLE ONLY public.spedizione ALTER COLUMN id_spedizione SET DEFAULT nextval('public.spedizione_id_spedizione_seq'::regclass);
 G   ALTER TABLE public.spedizione ALTER COLUMN id_spedizione DROP DEFAULT;
       public          postgres    false    231    232    232            l          0    24600    cliente 
   TABLE DATA           x   COPY public.cliente (email_cliente, n_telefono_cliente, nome, cognome, data_nascita, indirizzo_predefinito) FROM stdin;
    public          postgres    false    215   ��       y          0    24680    corriere 
   TABLE DATA           x   COPY public.corriere (n_telefono_corriere, nome_corriere, cognome_corriere, disponibilita, email_operatore) FROM stdin;
    public          postgres    false    228   ��       r          0    24633    dettaglio_ordine 
   TABLE DATA           Z   COPY public.dettaglio_ordine (id_dettaglio, quantita, id_ordine, id_prodotto) FROM stdin;
    public          postgres    false    221   �       t          0    24638 	   magazzino 
   TABLE DATA           d   COPY public.magazzino (id_magazzino, indirizzo, num_prodotti, data_controllo, capacita) FROM stdin;
    public          postgres    false    223   ��       x          0    24670    mezzo_di_trasporto 
   TABLE DATA           |   COPY public.mezzo_di_trasporto (targa, capacita_peso, capacita_spazio, disponibilita, modello, email_operatore) FROM stdin;
    public          postgres    false    227   ��       w          0    24658 	   operatore 
   TABLE DATA           �   COPY public.operatore (email_operatore, password, nome_operatore, cognome_operatore, n_telefono_operatore, id_magazzino) FROM stdin;
    public          postgres    false    226   ��       p          0    24628    ordine 
   TABLE DATA           d   COPY public.ordine (id_ordine, data_ordine, completamento, id_pagamento, email_cliente) FROM stdin;
    public          postgres    false    219   ��       n          0    24616 	   pagamento 
   TABLE DATA           V   COPY public.pagamento (id_pagamento, importo, tipo, esito, email_cliente) FROM stdin;
    public          postgres    false    217   ��       v          0    24646    prodotto 
   TABLE DATA           u   COPY public.prodotto (id_prodotto, nome_prodotto, dimensioni, peso, quantita_disp, prezzo, id_magazzino) FROM stdin;
    public          postgres    false    225   ��       {          0    24691    programmazione 
   TABLE DATA           w   COPY public.programmazione (id_programmazione, prox_consegna, data_fine, orario, frequenza, email_cliente) FROM stdin;
    public          postgres    false    230   ��       }          0    24707 
   spedizione 
   TABLE DATA           �   COPY public.spedizione (id_spedizione, destinazione, data_sped, societa, stato, arrivo_prev, id_ordine, id_programmazione, targa, n_telefono_corriere) FROM stdin;
    public          postgres    false    232   e�       �           0    0 !   dettaglio_ordine_id_dettaglio_seq    SEQUENCE SET     P   SELECT pg_catalog.setval('public.dettaglio_ordine_id_dettaglio_seq', 20, true);
          public          postgres    false    220            �           0    0    magazzino_id_magazzino_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('public.magazzino_id_magazzino_seq', 1, true);
          public          postgres    false    222            �           0    0    ordine_id_ordine_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.ordine_id_ordine_seq', 63, true);
          public          postgres    false    218            �           0    0    pagamento_id_pagamento_seq    SEQUENCE SET     H   SELECT pg_catalog.setval('public.pagamento_id_pagamento_seq', 8, true);
          public          postgres    false    216            �           0    0    prodotto_id_prodotto_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.prodotto_id_prodotto_seq', 8, true);
          public          postgres    false    224            �           0    0 $   programmazione_id_programmazione_seq    SEQUENCE SET     R   SELECT pg_catalog.setval('public.programmazione_id_programmazione_seq', 4, true);
          public          postgres    false    229            �           0    0    spedizione_id_spedizione_seq    SEQUENCE SET     K   SELECT pg_catalog.setval('public.spedizione_id_spedizione_seq', 67, true);
          public          postgres    false    231            �           2606    24604    cliente PKEmailCliente 
   CONSTRAINT     a   ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT "PKEmailCliente" PRIMARY KEY (email_cliente);
 B   ALTER TABLE ONLY public.cliente DROP CONSTRAINT "PKEmailCliente";
       public            postgres    false    215            �           2606    24662    operatore PKEmailOperatore 
   CONSTRAINT     g   ALTER TABLE ONLY public.operatore
    ADD CONSTRAINT "PKEmailOperatore" PRIMARY KEY (email_operatore);
 F   ALTER TABLE ONLY public.operatore DROP CONSTRAINT "PKEmailOperatore";
       public            postgres    false    226            �           2606    24751    dettaglio_ordine PKIdDettaglio 
   CONSTRAINT     h   ALTER TABLE ONLY public.dettaglio_ordine
    ADD CONSTRAINT "PKIdDettaglio" PRIMARY KEY (id_dettaglio);
 J   ALTER TABLE ONLY public.dettaglio_ordine DROP CONSTRAINT "PKIdDettaglio";
       public            postgres    false    221            �           2606    24644    magazzino PKIdMagazzino 
   CONSTRAINT     a   ALTER TABLE ONLY public.magazzino
    ADD CONSTRAINT "PKIdMagazzino" PRIMARY KEY (id_magazzino);
 C   ALTER TABLE ONLY public.magazzino DROP CONSTRAINT "PKIdMagazzino";
       public            postgres    false    223            �           2606    24749    ordine PKIdOrdine 
   CONSTRAINT     X   ALTER TABLE ONLY public.ordine
    ADD CONSTRAINT "PKIdOrdine" PRIMARY KEY (id_ordine);
 =   ALTER TABLE ONLY public.ordine DROP CONSTRAINT "PKIdOrdine";
       public            postgres    false    219            �           2606    24742    pagamento PKIdPagamento 
   CONSTRAINT     a   ALTER TABLE ONLY public.pagamento
    ADD CONSTRAINT "PKIdPagamento" PRIMARY KEY (id_pagamento);
 C   ALTER TABLE ONLY public.pagamento DROP CONSTRAINT "PKIdPagamento";
       public            postgres    false    217            �           2606    24652    prodotto PKIdProdotto 
   CONSTRAINT     ^   ALTER TABLE ONLY public.prodotto
    ADD CONSTRAINT "PKIdProdotto" PRIMARY KEY (id_prodotto);
 A   ALTER TABLE ONLY public.prodotto DROP CONSTRAINT "PKIdProdotto";
       public            postgres    false    225            �           2606    24700 !   programmazione PKIdProgrammazione 
   CONSTRAINT     p   ALTER TABLE ONLY public.programmazione
    ADD CONSTRAINT "PKIdProgrammazione" PRIMARY KEY (id_programmazione);
 M   ALTER TABLE ONLY public.programmazione DROP CONSTRAINT "PKIdProgrammazione";
       public            postgres    false    230            �           2606    24753    spedizione PKIdSpedizione 
   CONSTRAINT     d   ALTER TABLE ONLY public.spedizione
    ADD CONSTRAINT "PKIdSpedizione" PRIMARY KEY (id_spedizione);
 E   ALTER TABLE ONLY public.spedizione DROP CONSTRAINT "PKIdSpedizione";
       public            postgres    false    232            �           2606    24684    corriere PKNTelCorriere 
   CONSTRAINT     h   ALTER TABLE ONLY public.corriere
    ADD CONSTRAINT "PKNTelCorriere" PRIMARY KEY (n_telefono_corriere);
 C   ALTER TABLE ONLY public.corriere DROP CONSTRAINT "PKNTelCorriere";
       public            postgres    false    228            �           2606    24674    mezzo_di_trasporto PKTarga 
   CONSTRAINT     ]   ALTER TABLE ONLY public.mezzo_di_trasporto
    ADD CONSTRAINT "PKTarga" PRIMARY KEY (targa);
 F   ALTER TABLE ONLY public.mezzo_di_trasporto DROP CONSTRAINT "PKTarga";
       public            postgres    false    227            �           2606    24755 !   pagamento constraintTypePagamento    CHECK CONSTRAINT     �   ALTER TABLE public.pagamento
    ADD CONSTRAINT "constraintTypePagamento" CHECK ((tipo = ANY (ARRAY['tipo_carta_di_credito'::public.tipo_pagamento, 'tipo_paypal'::public.tipo_pagamento, 'tipo_bonifico'::public.tipo_pagamento]))) NOT VALID;
 H   ALTER TABLE public.pagamento DROP CONSTRAINT "constraintTypePagamento";
       public          postgres    false    882    217    217            �           2606    24754    pagamento minValoreImporto    CHECK CONSTRAINT     x   ALTER TABLE public.pagamento
    ADD CONSTRAINT "minValoreImporto" CHECK ((importo > (0)::double precision)) NOT VALID;
 A   ALTER TABLE public.pagamento DROP CONSTRAINT "minValoreImporto";
       public          postgres    false    217    217            �           2606    24780 #   dettaglio_ordine minValoreQuantità    CHECK CONSTRAINT     n   ALTER TABLE public.dettaglio_ordine
    ADD CONSTRAINT "minValoreQuantità" CHECK ((quantita > 0)) NOT VALID;
 J   ALTER TABLE public.dettaglio_ordine DROP CONSTRAINT "minValoreQuantità";
       public          postgres    false    221    221            �           2606    24769    ordine successRuleOrdine    CHECK CONSTRAINT     k   ALTER TABLE public.ordine
    ADD CONSTRAINT "successRuleOrdine" CHECK ((completamento = true)) NOT VALID;
 ?   ALTER TABLE public.ordine DROP CONSTRAINT "successRuleOrdine";
       public          postgres    false    219    219            �           2606    24756    pagamento successRulePagamento    CHECK CONSTRAINT     i   ALTER TABLE public.pagamento
    ADD CONSTRAINT "successRulePagamento" CHECK ((esito = true)) NOT VALID;
 E   ALTER TABLE public.pagamento DROP CONSTRAINT "successRulePagamento";
       public          postgres    false    217    217            �           2606    24664    operatore uniqueNTelOperatore 
   CONSTRAINT     j   ALTER TABLE ONLY public.operatore
    ADD CONSTRAINT "uniqueNTelOperatore" UNIQUE (n_telefono_operatore);
 I   ALTER TABLE ONLY public.operatore DROP CONSTRAINT "uniqueNTelOperatore";
       public            postgres    false    226            �           2606    24606    cliente uniqueNumTelCliente 
   CONSTRAINT     f   ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT "uniqueNumTelCliente" UNIQUE (n_telefono_cliente);
 G   ALTER TABLE ONLY public.cliente DROP CONSTRAINT "uniqueNumTelCliente";
       public            postgres    false    215            �           2620    24881 "   spedizione trigger_assegnacorriere    TRIGGER     �   CREATE TRIGGER trigger_assegnacorriere AFTER INSERT ON public.spedizione FOR EACH ROW EXECUTE FUNCTION public.assegnacorriere();
 ;   DROP TRIGGER trigger_assegnacorriere ON public.spedizione;
       public          postgres    false    232    260            �           2620    24820 *   spedizione trigger_assegnamezzoditrasporto    TRIGGER     �   CREATE TRIGGER trigger_assegnamezzoditrasporto AFTER INSERT ON public.spedizione FOR EACH ROW EXECUTE FUNCTION public.assegnamezzoditrasporto();
 C   DROP TRIGGER trigger_assegnamezzoditrasporto ON public.spedizione;
       public          postgres    false    261    232            �           2620    24856 (   spedizione trigger_assegnaprogrammazione    TRIGGER     �   CREATE TRIGGER trigger_assegnaprogrammazione AFTER INSERT ON public.spedizione FOR EACH ROW EXECUTE FUNCTION public.assegnaprogrammazione();
 A   DROP TRIGGER trigger_assegnaprogrammazione ON public.spedizione;
       public          postgres    false    232    233            �           2620    24817 ,   dettaglio_ordine trigger_checkquantitascelta    TRIGGER     �   CREATE TRIGGER trigger_checkquantitascelta BEFORE INSERT ON public.dettaglio_ordine FOR EACH ROW EXECUTE FUNCTION public.checkquantitascelta();
 E   DROP TRIGGER trigger_checkquantitascelta ON public.dettaglio_ordine;
       public          postgres    false    221    251            �           2620    24802 !   operatore trigger_hashingpassword    TRIGGER     �   CREATE TRIGGER trigger_hashingpassword BEFORE INSERT OR UPDATE ON public.operatore FOR EACH ROW EXECUTE FUNCTION public.beforeinsertupdatepassword();
 :   DROP TRIGGER trigger_hashingpassword ON public.operatore;
       public          postgres    false    226    236            �           2620    24828 +   programmazione trigger_insertspedizioneprog    TRIGGER     �   CREATE TRIGGER trigger_insertspedizioneprog AFTER UPDATE OF prox_consegna ON public.programmazione FOR EACH ROW EXECUTE FUNCTION public.insertspedizioneprog();
 D   DROP TRIGGER trigger_insertspedizioneprog ON public.programmazione;
       public          postgres    false    230    253    230            �           2620    24813 '   corriere trigger_isvalidassegnacorriere    TRIGGER     �   CREATE TRIGGER trigger_isvalidassegnacorriere BEFORE UPDATE ON public.corriere FOR EACH ROW EXECUTE FUNCTION public.isvalidassegnacorriere();
 @   DROP TRIGGER trigger_isvalidassegnacorriere ON public.corriere;
       public          postgres    false    228    240            �           2620    24811 .   mezzo_di_trasporto trigger_isvalidassegnamezzo    TRIGGER     �   CREATE TRIGGER trigger_isvalidassegnamezzo BEFORE UPDATE ON public.mezzo_di_trasporto FOR EACH ROW EXECUTE FUNCTION public.isvalidassegnamezzo();
 G   DROP TRIGGER trigger_isvalidassegnamezzo ON public.mezzo_di_trasporto;
       public          postgres    false    239    227            �           2620    24806 +   pagamento trigger_updatecompletamentoordine    TRIGGER     �   CREATE TRIGGER trigger_updatecompletamentoordine AFTER UPDATE ON public.pagamento FOR EACH ROW EXECUTE FUNCTION public.updatecompletamentoordine();
 D   DROP TRIGGER trigger_updatecompletamentoordine ON public.pagamento;
       public          postgres    false    217    238            �           2620    24830 !   spedizione trigger_updatedatasped    TRIGGER     �   CREATE TRIGGER trigger_updatedatasped BEFORE UPDATE OF stato ON public.spedizione FOR EACH ROW EXECUTE FUNCTION public.updatedatasped();
 :   DROP TRIGGER trigger_updatedatasped ON public.spedizione;
       public          postgres    false    254    232    232            �           2606    24775 %   dettaglio_ordine existDettaglioOrdine    FK CONSTRAINT     �   ALTER TABLE ONLY public.dettaglio_ordine
    ADD CONSTRAINT "existDettaglioOrdine" FOREIGN KEY (id_ordine) REFERENCES public.ordine(id_ordine) NOT VALID;
 Q   ALTER TABLE ONLY public.dettaglio_ordine DROP CONSTRAINT "existDettaglioOrdine";
       public          postgres    false    219    221    4786            �           2606    24685    corriere isValidAssegnaCorriere    FK CONSTRAINT     �   ALTER TABLE ONLY public.corriere
    ADD CONSTRAINT "isValidAssegnaCorriere" FOREIGN KEY (email_operatore) REFERENCES public.operatore(email_operatore);
 K   ALTER TABLE ONLY public.corriere DROP CONSTRAINT "isValidAssegnaCorriere";
       public          postgres    false    226    4794    228            �           2606    24675 &   mezzo_di_trasporto isValidAssegnaMezzo    FK CONSTRAINT     �   ALTER TABLE ONLY public.mezzo_di_trasporto
    ADD CONSTRAINT "isValidAssegnaMezzo" FOREIGN KEY (email_operatore) REFERENCES public.operatore(email_operatore);
 R   ALTER TABLE ONLY public.mezzo_di_trasporto DROP CONSTRAINT "isValidAssegnaMezzo";
       public          postgres    false    227    4794    226            �           2606    24653    prodotto isValidContenere    FK CONSTRAINT     �   ALTER TABLE ONLY public.prodotto
    ADD CONSTRAINT "isValidContenere" FOREIGN KEY (id_magazzino) REFERENCES public.magazzino(id_magazzino);
 E   ALTER TABLE ONLY public.prodotto DROP CONSTRAINT "isValidContenere";
       public          postgres    false    223    4790    225            �           2606    24665    operatore isValidControlloMag    FK CONSTRAINT     �   ALTER TABLE ONLY public.operatore
    ADD CONSTRAINT "isValidControlloMag" FOREIGN KEY (id_magazzino) REFERENCES public.magazzino(id_magazzino);
 I   ALTER TABLE ONLY public.operatore DROP CONSTRAINT "isValidControlloMag";
       public          postgres    false    223    4790    226            �           2606    24764    ordine isValidEffettuareOrdine    FK CONSTRAINT     �   ALTER TABLE ONLY public.ordine
    ADD CONSTRAINT "isValidEffettuareOrdine" FOREIGN KEY (email_cliente) REFERENCES public.cliente(email_cliente) NOT VALID;
 J   ALTER TABLE ONLY public.ordine DROP CONSTRAINT "isValidEffettuareOrdine";
       public          postgres    false    219    4780    215            �           2606    24743 $   pagamento isValidEffettuarePagamento    FK CONSTRAINT     �   ALTER TABLE ONLY public.pagamento
    ADD CONSTRAINT "isValidEffettuarePagamento" FOREIGN KEY (email_cliente) REFERENCES public.cliente(email_cliente) NOT VALID;
 P   ALTER TABLE ONLY public.pagamento DROP CONSTRAINT "isValidEffettuarePagamento";
       public          postgres    false    215    217    4780            �           2606    24872 &   spedizione isValidEffettuareSpedizione    FK CONSTRAINT     �   ALTER TABLE ONLY public.spedizione
    ADD CONSTRAINT "isValidEffettuareSpedizione" FOREIGN KEY (n_telefono_corriere) REFERENCES public.corriere(n_telefono_corriere) NOT VALID;
 R   ALTER TABLE ONLY public.spedizione DROP CONSTRAINT "isValidEffettuareSpedizione";
       public          postgres    false    232    4800    228            �           2606    24770     dettaglio_ordine isValidIndicare    FK CONSTRAINT     �   ALTER TABLE ONLY public.dettaglio_ordine
    ADD CONSTRAINT "isValidIndicare" FOREIGN KEY (id_prodotto) REFERENCES public.prodotto(id_prodotto) NOT VALID;
 L   ALTER TABLE ONLY public.dettaglio_ordine DROP CONSTRAINT "isValidIndicare";
       public          postgres    false    225    4792    221            �           2606    24781    spedizione isValidInviare    FK CONSTRAINT     �   ALTER TABLE ONLY public.spedizione
    ADD CONSTRAINT "isValidInviare" FOREIGN KEY (id_ordine) REFERENCES public.ordine(id_ordine) NOT VALID;
 E   ALTER TABLE ONLY public.spedizione DROP CONSTRAINT "isValidInviare";
       public          postgres    false    4786    219    232            �           2606    24786    spedizione isValidProgrammare    FK CONSTRAINT     �   ALTER TABLE ONLY public.spedizione
    ADD CONSTRAINT "isValidProgrammare" FOREIGN KEY (id_programmazione) REFERENCES public.programmazione(id_programmazione) NOT VALID;
 I   ALTER TABLE ONLY public.spedizione DROP CONSTRAINT "isValidProgrammare";
       public          postgres    false    4802    232    230            �           2606    24701    programmazione isValidScegliere    FK CONSTRAINT     �   ALTER TABLE ONLY public.programmazione
    ADD CONSTRAINT "isValidScegliere" FOREIGN KEY (email_cliente) REFERENCES public.cliente(email_cliente);
 K   ALTER TABLE ONLY public.programmazione DROP CONSTRAINT "isValidScegliere";
       public          postgres    false    215    230    4780            �           2606    24759    ordine isValidTransazione    FK CONSTRAINT     �   ALTER TABLE ONLY public.ordine
    ADD CONSTRAINT "isValidTransazione" FOREIGN KEY (id_pagamento) REFERENCES public.pagamento(id_pagamento) NOT VALID;
 E   ALTER TABLE ONLY public.ordine DROP CONSTRAINT "isValidTransazione";
       public          postgres    false    217    4784    219            �           2606    24862    spedizione isValidTrasportare    FK CONSTRAINT     �   ALTER TABLE ONLY public.spedizione
    ADD CONSTRAINT "isValidTrasportare" FOREIGN KEY (targa) REFERENCES public.mezzo_di_trasporto(targa) NOT VALID;
 I   ALTER TABLE ONLY public.spedizione DROP CONSTRAINT "isValidTrasportare";
       public          postgres    false    227    232    4798            l   D  x�m�ˎ�0���)��JI�kb�U�h5�n�`�%'FN��<}'�� �l '|9sv>4�5Ec��|�4���"\SF�$m&�%�Z�Ҝq��>��d�K�aE(xo��;��_~����xg>�aW��:ׇI
�uEz�dh=�L��Bg>?~���e�]׻S"0��Sr��֛�fВ���h7*��"s�s�s��Є���P�����6�������L*��4�4ɗ�E������mW�r�B�����)�jX�\��S;S@m�C�$B?�d���ܹh I`%s�p�჉���e����u(�6F� U����.@�6�$qxg{��b�	^�r7�<��mۻa�Pq�&)$+)A�wYb'Y
ܕy����V�봏q��+n�w�iO1W�� ��T������0	�TNT����@r4Fg ����*x�"!�h�$h;J� �v���l�l:b|F��[g�W[�[�ͧ�K�ZUp�F:&MbМC�U�]�o���O��RD��:O�jylEPY�Jqt_���T
����[|���� �����X,� ��Z�      y     x����j�0���S�	B��_n�-�F;���N*H,p�K�~�`�^������$�*��0��p�9�B�&����6�C"�e�i���݀ԗ��bW9k����)a�a�e�i��+e7/�%��Z:�#6���hĸR�ϯ�)a��(ev�n��u�M=m�\�F� {L���z�S�2V�y�{ʍڊ����*�؇��.��4m���R(�������טTk`�Ǖ��tg�򀑷�ZdK���ԭ��-��͖��s*.'l�����EQ�\F��      r   ]   x�%��� C�v1+<zI�u��ܞ�� ��EU袩F�&5��C-t���i�=X��N4%wC��5Bm���B�G\b��Ē���NZß��      t   Y   x�3��LT�L��K�IUp.-HTp��+�L�Q02�Q�0042S�K,���T��s��Q�,I��L�4200 F&���F���@>W� �)'      x   �   x����n�0Dח�A�a ���Z"��6A�X�A�����;Tj�*���̙�X�<�f1F����6�S���"�ZY+��kZ�Ϯ4�S<��� !��6��SY-����L��Z%�fK��fH��ڊN��4n3\�Ӝ��YN�'As��}�����2��Cof_�k|�N����yz�X��c%�D��4+��!CS�`����q��@�mn ���SE�?
�E�9�p�b�,���u�IK��      w   �  x�M���!���)�`��V����J�=�b��F��h�T�>}M���0p����]�lK�}�u9���G^�}G��	E���eP��]q��`��k e�H.�q;Y��je�x�'�-��
@��\�@ ���]��/T�4�q:!Xu��OU.u��oOR�αz疉B�ݑTn���4_���U���h5��Sg^י���&O�+�Ɗ�]�4zB�b���j/�!<���t(�Dk��-P�HPR��R�ٙ�	���a0DM�R��7�Mv�� �5G��Z����Z����+�.�i1Ӓ��0�r����ax۲�Z��JP��U�3w��U���৩�R~��Or�'g�;HM�FJgv�,�7�	���xE����6�f���0��1�I���v��@�Ɲ      p   �   x�U�=�� ���ň�v{�4���H�X`���wlY^\�<�寧�Vڴ ���*���~}�2PƟw������	u�f��H���#Ɍ��tP;�Z-�L{��@�'+�M���t�%~��À6Qm�e�a;�7҇/D��Bכ6�Z��D�ّ|����l8!�C �������Z���k-�b�3�\������tzӪ~�F���D���r�$샡㾭�e�2o��q�.{�q��������4����C      n   �   x�u��n�0E��c�	�>v��J�5�Z
q䄑f���`�*�{�{��
'�(�0}��&��Ǽ C�4=t�/I���R?r��D&�"F�hK9x���%���鉃,>�Y0Fܤ_����Pj��ā1�{wp�Y�u6����o�o`ۣ2*���*���������;�w9U�`�G�|Q��wl��>��)�T�w���Uͼf��7��u��{��b$��!���4M�� nT��      v   8  x�E��n� D���,�96�%R��J{�z���:F%�B��.�V���@+�HD.ΐx�mvw�HJ���$��<}�Ɇ�_|6FJ&�d!B/!�zxp����]��8qrbEײ�_�E�D-/��
�ƞ]@3�B��q�b�Y35!K�H�婂��v~J�S�3[����yp�l�-%���FU{"�G?n��ń9J�UX�+�qf4:�Y��*�5<��6���@8������=�?�١�5��3V��ct����'�J���;�����Q�c_�����&���379T[ޮɩ�ꪪ~N"v      {   k   x�M�A
�0Dѵ|�N�=H7�w@�B��S�j�f~������f����0���5�B���~�ٍ�������7k�nr�w8���U8���A���"�B� )�      }   �  x���Mk1�ϳ�B���[���أ�8�B�M����"P%����_�Ѻ�)�CA���>�$�yK�)�CM�151�rUcm��{��}�ɧl���)o��an����Ұ�����K���Tz�*��LK�*�N���<�����PGhW��Y��g:��_޼D�d+X�;�g�d�a�㼒�0��M]���e$�Ɍ
:�f�]
y"�˗t��:a߱�+��������FJɍ���ko�bp�/�6��M�e���������@"-�W�t�}�v�h���g��������ɕ>'��dew��'��mo�p��)�P!����+����3�|����~�6��ol� �����_o����E�D��\���'�RW���mޘ�a���=~���9��cUU� -M��     