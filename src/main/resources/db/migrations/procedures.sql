-- Создание базы данных (используется dblink для PostgreSQL)
CREATE OR REPLACE FUNCTION sp_createDatabase()
    RETURNS VOID AS $$
BEGIN
    PERFORM dblink_exec('dbname=postgres', 'CREATE DATABASE IF NOT EXISTS LibraryDB');
END;
$$ LANGUAGE plpgsql;

-- Удаление базы данных (используется dblink для PostgreSQL)
CREATE OR REPLACE FUNCTION sp_dropDatabase()
    RETURNS VOID AS $$
BEGIN
    PERFORM dblink_exec('dbname=postgres', 'DROP DATABASE IF EXISTS LibraryDB');
END;
$$ LANGUAGE plpgsql;

-- Функция для очистки таблицы
CREATE OR REPLACE FUNCTION sp_clearTable(table_name VARCHAR)
    RETURNS VOID AS $$
BEGIN
    EXECUTE 'TRUNCATE TABLE ' || quote_ident(table_name);
END;
$$ LANGUAGE plpgsql;

-- Вставка книги в таблицу Book
CREATE OR REPLACE FUNCTION sp_insertBook(
    p_isbn VARCHAR(20),
    p_title VARCHAR(255),
    p_author VARCHAR(255),
    p_genre VARCHAR(100),
    p_yearPublished INT,
    p_copies INT,
    p_availability BOOLEAN
)
    RETURNS VOID AS $$
BEGIN
    INSERT INTO Book (ISBN, Title, Author, Genre, YearPublished, Copies, Availability)
    VALUES (p_isbn, p_title, p_author, p_genre, p_yearPublished, p_copies, p_availability);
END;
$$ LANGUAGE plpgsql;

-- Обновление книги по ISBN
CREATE OR REPLACE FUNCTION sp_updateBook(
    p_isbn VARCHAR(20),
    p_title VARCHAR(255),
    p_author VARCHAR(255),
    p_genre VARCHAR(100),
    p_yearPublished INT,
    p_copies INT,
    p_availability BOOLEAN
)
    RETURNS VOID AS $$
BEGIN
    UPDATE Book
    SET Title = p_title,
        Author = p_author,
        Genre = p_genre,
        YearPublished = p_yearPublished,
        Copies = p_copies,
        Availability = p_availability
    WHERE ISBN = p_isbn;
END;
$$ LANGUAGE plpgsql;

-- Поиск книги по названию (по частичному совпадению)
CREATE OR REPLACE FUNCTION sp_searchBookByTitle(p_title VARCHAR(255))
    RETURNS TABLE(ISBN VARCHAR(20), Title VARCHAR(255), Author VARCHAR(255), Genre VARCHAR(100), YearPublished INT, Copies INT, Availability BOOLEAN) AS $$
BEGIN
    RETURN QUERY
        SELECT ISBN, Title, Author, Genre, YearPublished, Copies, Availability
        FROM Book
        WHERE Title LIKE '%' || p_title || '%';
END;
$$ LANGUAGE plpgsql;

-- Удаление книги по названию
CREATE OR REPLACE FUNCTION sp_deleteBookByTitle(p_title VARCHAR(255))
    RETURNS VOID AS $$
BEGIN
    DELETE FROM Book WHERE Title = p_title;
END;
$$ LANGUAGE plpgsql;

-- Создание нового пользователя с заданным режимом доступа
CREATE OR REPLACE FUNCTION sp_createUser(
    p_username VARCHAR(64),
    p_password VARCHAR(64),
    p_role VARCHAR(10)  -- Например, 'ADMIN' или 'GUEST'
)
    RETURNS VOID AS $$
BEGIN
    -- Создаём пользователя в PostgreSQL
    EXECUTE 'CREATE USER ' || quote_ident(p_username) || ' WITH PASSWORD ' || quote_literal(p_password);

    -- Назначаем роль в зависимости от типа
    IF p_role = 'ADMIN' THEN
        EXECUTE 'GRANT ALL PRIVILEGES ON DATABASE LibraryDB TO ' || quote_ident(p_username);
    ELSEIF p_role = 'GUEST' THEN
        EXECUTE 'GRANT CONNECT ON DATABASE LibraryDB TO ' || quote_ident(p_username);
    END IF;

    -- Применяем изменения
    EXECUTE 'ALTER ROLE ' || quote_ident(p_username) || ' SET role TO ' || quote_ident(p_role);
END;
$$ LANGUAGE plpgsql;

