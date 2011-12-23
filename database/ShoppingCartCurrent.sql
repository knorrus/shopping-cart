--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- Name: ShopingCart; Type: COMMENT; Schema: -; Owner: ShoppingCartOwner
--

COMMENT ON DATABASE "ShopingCart" IS 'Logicify online shoping cart database';


--
-- Name: shopping_cart; Type: SCHEMA; Schema: -; Owner: ShoppingCartOwner
--

CREATE SCHEMA shopping_cart;


ALTER SCHEMA shopping_cart OWNER TO "ShoppingCartOwner";

--
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: postgres
--

CREATE OR REPLACE PROCEDURAL LANGUAGE plpgsql;


ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO postgres;

SET search_path = shopping_cart, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: category; Type: TABLE; Schema: shopping_cart; Owner: ShoppingCartOwner; Tablespace: 
--

CREATE TABLE category (
    id_category bigint NOT NULL,
    name character varying(200) NOT NULL,
    description character varying(1000) NOT NULL,
    date timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE shopping_cart.category OWNER TO "ShoppingCartOwner";

--
-- Name: category_id_seq; Type: SEQUENCE; Schema: shopping_cart; Owner: ShoppingCartOwner
--

CREATE SEQUENCE category_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE shopping_cart.category_id_seq OWNER TO "ShoppingCartOwner";

--
-- Name: category_id_seq; Type: SEQUENCE OWNED BY; Schema: shopping_cart; Owner: ShoppingCartOwner
--

ALTER SEQUENCE category_id_seq OWNED BY category.id_category;


--
-- Name: hibernate_unique_key; Type: TABLE; Schema: shopping_cart; Owner: ShoppingCartOwner; Tablespace: 
--

CREATE TABLE hibernate_unique_key (
    next_hi integer
);


ALTER TABLE shopping_cart.hibernate_unique_key OWNER TO "ShoppingCartOwner";

--
-- Name: product; Type: TABLE; Schema: shopping_cart; Owner: ShoppingCartOwner; Tablespace: 
--

CREATE TABLE product (
    id_product bigint NOT NULL,
    name character varying(50) DEFAULT ''::character varying NOT NULL,
    price numeric NOT NULL,
    description character varying(1000) DEFAULT ''::character varying NOT NULL,
    date timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE shopping_cart.product OWNER TO "ShoppingCartOwner";

--
-- Name: product_id_seq; Type: SEQUENCE; Schema: shopping_cart; Owner: ShoppingCartOwner
--

CREATE SEQUENCE product_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE shopping_cart.product_id_seq OWNER TO "ShoppingCartOwner";

--
-- Name: product_id_seq; Type: SEQUENCE OWNED BY; Schema: shopping_cart; Owner: ShoppingCartOwner
--

ALTER SEQUENCE product_id_seq OWNED BY product.id_product;


--
-- Name: product_to_category; Type: TABLE; Schema: shopping_cart; Owner: ShoppingCartOwner; Tablespace: 
--

CREATE TABLE product_to_category (
    product_id bigint NOT NULL,
    category_id bigint NOT NULL,
    date timestamp without time zone DEFAULT now() NOT NULL
);


ALTER TABLE shopping_cart.product_to_category OWNER TO "ShoppingCartOwner";

--
-- Name: id_category; Type: DEFAULT; Schema: shopping_cart; Owner: ShoppingCartOwner
--

ALTER TABLE category ALTER COLUMN id_category SET DEFAULT nextval('category_id_seq'::regclass);


--
-- Name: id_product; Type: DEFAULT; Schema: shopping_cart; Owner: ShoppingCartOwner
--

ALTER TABLE product ALTER COLUMN id_product SET DEFAULT nextval('product_id_seq'::regclass);


--
-- Name: Category_pkey; Type: CONSTRAINT; Schema: shopping_cart; Owner: ShoppingCartOwner; Tablespace: 
--

ALTER TABLE ONLY category
    ADD CONSTRAINT "Category_pkey" PRIMARY KEY (id_category);


--
-- Name: Product-Category_pkey; Type: CONSTRAINT; Schema: shopping_cart; Owner: ShoppingCartOwner; Tablespace: 
--

ALTER TABLE ONLY product_to_category
    ADD CONSTRAINT "Product-Category_pkey" PRIMARY KEY (product_id, category_id);


--
-- Name: Product_pkey; Type: CONSTRAINT; Schema: shopping_cart; Owner: ShoppingCartOwner; Tablespace: 
--

ALTER TABLE ONLY product
    ADD CONSTRAINT "Product_pkey" PRIMARY KEY (id_product);


--
-- Name: Product_name_idx; Type: INDEX; Schema: shopping_cart; Owner: ShoppingCartOwner; Tablespace: 
--

CREATE INDEX "Product_name_idx" ON product USING hash (name);


--
-- Name: CategoryRef; Type: FK CONSTRAINT; Schema: shopping_cart; Owner: ShoppingCartOwner
--

ALTER TABLE ONLY product_to_category
    ADD CONSTRAINT "CategoryRef" FOREIGN KEY (category_id) REFERENCES category(id_category) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: ProductRef; Type: FK CONSTRAINT; Schema: shopping_cart; Owner: ShoppingCartOwner
--

ALTER TABLE ONLY product_to_category
    ADD CONSTRAINT "ProductRef" FOREIGN KEY (product_id) REFERENCES product(id_product) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- Name: shopping_cart; Type: ACL; Schema: -; Owner: ShoppingCartOwner
--

REVOKE ALL ON SCHEMA shopping_cart FROM PUBLIC;
REVOKE ALL ON SCHEMA shopping_cart FROM "ShoppingCartOwner";
GRANT ALL ON SCHEMA shopping_cart TO "ShoppingCartOwner";
GRANT ALL ON SCHEMA shopping_cart TO PUBLIC;


--
-- Name: category; Type: ACL; Schema: shopping_cart; Owner: ShoppingCartOwner
--

REVOKE ALL ON TABLE category FROM PUBLIC;
REVOKE ALL ON TABLE category FROM "ShoppingCartOwner";
GRANT ALL ON TABLE category TO "ShoppingCartOwner";


--
-- Name: product_to_category; Type: ACL; Schema: shopping_cart; Owner: ShoppingCartOwner
--

REVOKE ALL ON TABLE product_to_category FROM PUBLIC;
REVOKE ALL ON TABLE product_to_category FROM "ShoppingCartOwner";
GRANT ALL ON TABLE product_to_category TO "ShoppingCartOwner";


--
-- PostgreSQL database dump complete
--

