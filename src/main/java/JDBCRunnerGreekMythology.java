import java.sql.*;

public class JDBCRunnerGreekMythology {

    private static final String PROTOCOL = "jdbc:postgresql://";        // URL-prefix
    private static final String DRIVER = "org.postgresql.Driver";       // Driver name
    private static final String URL_LOCALE_NAME = "localhost/";         // ваш компьютер + порт по умолчанию

    private static final String DATABASE_NAME = "GreekMythology";          // FIXME имя базы

    public static final String DATABASE_URL = PROTOCOL + URL_LOCALE_NAME + DATABASE_NAME;
    public static final String USER_NAME = "postgres";                  // FIXME имя пользователя
    public static final String DATABASE_PASS = "doctorwho";              // FIXME пароль базы данных


    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Connected JDBC Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("JDBC Driver is not found. Include it in your library path");
        }

        // проверка возможности подключения
        checkDriver();
        checkDB();
        System.out.println("Подключение к базе данных | " + DATABASE_URL + "\n");

        try (Connection connection = DriverManager.getConnection(DATABASE_URL, USER_NAME, DATABASE_PASS)) {
            //TODO show all tables
            sos();
            getGods(connection); System.out.println();
            getSpheres(connection); System.out.println();
            getHeroes(connection); System.out.println();
            getMyths(connection); System.out.println();

            // TODO show with param
            System.out.println("----------------------------------------------------------------------------------------------------------------- MYTHS WITH ODYSSEUS -------------------------------------------------------------------------------------------------------------------------");
            getMythsNamed(connection, "Odysseus", false); System.out.println();// возьмем всех и найдем перебором
            getMythsNamed(connection, "Odysseus", true); System.out.println(); // тоже самое сделает БД
            System.out.println("-------- ALL SKY-TYPE GODS ----------");
            getGodsType(connection, "sky"); System.out.println();


            // TODO correction
            System.out.println("----------------- SPHERE'S TABLE ACTIONS ------------------");
            addSphere(connection, "trade"); System.out.println();
            correctSphere(connection, "trade", 11); System.out.println();
            removeSphere(connection, "trade"); System.out.println();

            addSphere(connection, "fire"); System.out.println();
            correctSphere(connection, "fire", 11); System.out.println();

            System.out.println(" ---------------------- GOD'S TABLE ACTIONS --------------------------------");
            addGod(connection, "Hephaestus", 1, 11, 3); System.out.println();


        } catch (SQLException e) {
            // При открытии соединения, выполнении запросов могут возникать различные ошибки
            // Согласно стандарту SQL:2008 в ситуациях нарушения ограничений уникальности (в т.ч. дублирования данных) возникают ошибки соответствующие статусу (или дочерние ему): SQLState 23000 - Integrity Constraint Violation
            if (e.getSQLState().startsWith("23")) {
                System.out.println("Произошло дублирование данных");
            } else throw new RuntimeException(e);
        }
    } // конец main-a
    // -------------------------------------------------------------------------------------------------------------------------

    public static void checkDriver() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("Нет JDBC-драйвера! Подключите JDBC-драйвер к проекту согласно инструкции.");
            throw new RuntimeException(e);
        }
    }

    public static void checkDB() {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, USER_NAME, DATABASE_PASS);
        } catch (SQLException e) {
            System.out.println("Нет базы данных! Проверьте имя базы, путь к базе или разверните локально резервную копию согласно инструкции");
            throw new RuntimeException(e);
        }
    }

    // ------------------------------------------------- ЗАПРОСЫ -------------------------------------------------------

    public static void sos() {
        System.out.println("Hello!! Welcome to the GreekMythology database. Here are some information from main tables and actions with their features below. Please enjoy!");
    }
    private static void getSpheres(Connection connection) throws SQLException {
        System.out.println("--------------- SPHERES ------------------");
        PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM sphereofinfluence;");
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            System.out.println(rs.getString(1) + " | " + rs.getString(2));
        }
    }


    private static void getGods(Connection connection) throws SQLException {
        System.out.println("--------------- GODS ------------------");
        PreparedStatement statement = connection.prepareStatement(
                "SELECT gods.id, gods.name, typeofgod.type, gender.gender, sphereofinfluence.sphere " +
                        "FROM gods " +
                        "JOIN typeofgod ON gods.type_id = typeofgod.id " +
                        "JOIN gender ON gods.gender_id = gender.id " +
                        "JOIN sphereofinfluence ON gods.sphere_id = sphereofinfluence.id;");
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            System.out.println(rs.getString(1) + " | " + rs.getString(2) + " | " + rs.getString(3) + " | " + rs.getString(4) + " | " + rs.getString(5));
        }
    }

    private static void getHeroes(Connection connection) throws SQLException {
        System.out.println("----------- HEROES ------------");
        PreparedStatement statement = connection.prepareStatement(
                "SELECT heroes.id, heroes.name,  gender.gender, status.status " +
                        "FROM heroes " +
                        "JOIN gender ON heroes.gender_id = gender.id " +
                        "JOIN status ON heroes.status_id = status.id;");
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            System.out.println(rs.getString(1) + " | " + rs.getString(2) + " | " + rs.getString(3) + " | " + rs.getString(4));
        }
    }

    private static void getMyths(Connection connection) throws SQLException {
        System.out.println("------------------------------------------------------------------------------------------------------------------------- MYTHS --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        PreparedStatement statement = connection.prepareStatement(
                "SELECT myths.id, " +
                        "CASE " +
                        "WHEN myths.hero_id IS NULL THEN '-' " +
                        "ELSE heroes.name " +
                        "END AS hero_name, " +
                        "CASE " +
                        "WHEN myths.monster_id IS NULL THEN '-' " +
                        "ELSE monsters.monster " +
                        "END AS monster_name, " +
                        "CASE " +
                        "WHEN myths.god_id IS NULL THEN '-' " +
                        "ELSE gods.name " +
                        "END AS god_name, myths.synopsis " +
                        "FROM myths " +
                        "LEFT JOIN heroes ON myths.hero_id = heroes.id " +
                        "LEFT JOIN monsters ON myths.monster_id = monsters.id " +
                        "LEFT JOIN gods ON myths.god_id = gods.id;");
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            System.out.println(rs.getString(1) + " | " + rs.getString(2) + " | " + rs.getString(3) + " | " + rs.getString(4) + " | " + rs.getString(5));
        }
    }


    // -------------------------------------------------- ЗАПРОСЫ С ПАРАМЕТРОМ ---------------------------------------------------------------
    private static void getMythsNamed(Connection connection, String name, boolean fromSQL) throws SQLException {
        if (name == null || name.isBlank()) return;// проверка "на дурака"

        if (fromSQL) {
            getMythsNamed(connection, name);               // если флаг верен, то выполняем аналогичный запрос c условием (WHERE)
        } else {
            long time = System.currentTimeMillis();
            Statement statement = connection.createStatement();      // создаем оператор для простого запроса (без параметров)
            ResultSet rs = statement.executeQuery(
                    "SELECT myths.id, " +
                            "CASE " +
                            "WHEN myths.hero_id IS NULL THEN '-' " +
                            "ELSE heroes.name " +
                            "END AS hero_name, " +
                            "CASE " +
                            "WHEN myths.monster_id IS NULL THEN '-' " +
                            "ELSE monsters.monster " +
                            "END AS monster_name, " +
                            "CASE " +
                            "WHEN myths.god_id IS NULL THEN '-' " +
                            "ELSE gods.name " +
                            "END AS god_name, myths.synopsis " +
                            "FROM myths " +
                            "LEFT JOIN heroes ON myths.hero_id = heroes.id " +
                            "LEFT JOIN monsters ON myths.monster_id = monsters.id " +
                            "LEFT JOIN gods ON myths.god_id = gods.id");
            while (rs.next()) {  // пока есть данные перебираем их
                if (rs.getString(2).contains(name)) { // и выводим только определенный параметр
                    System.out.println(rs.getString(1) + " | " + rs.getString(2) + " | " + rs.getString(3) + " | " + rs.getString(4) + " | " + rs.getString(5));
                }
            }
            System.out.println("SELECT ALL and FIND (" + (System.currentTimeMillis() - time) + " мс.)");
        }
    }

    private static void getMythsNamed(Connection connection, String name) throws SQLException {
        if (name == null || name.isBlank()) return;
        name = '%' + name + '%';

        long time = System.currentTimeMillis();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT myths.id, " +
                        "CASE " +
                        "WHEN myths.hero_id IS NULL THEN '-' " +
                        "ELSE heroes.name " +
                        "END AS hero_name, " +
                        "CASE " +
                        "WHEN myths.monster_id IS NULL THEN '-' " +
                        "ELSE monsters.monster " +
                        "END AS monster_name, " +
                        "CASE " +
                        "WHEN myths.god_id IS NULL THEN '-' " +
                        "ELSE gods.name " +
                        "END AS god_name, myths.synopsis " +
                        "FROM myths " +
                        "LEFT JOIN heroes ON myths.hero_id = heroes.id " +
                        "LEFT JOIN monsters ON myths.monster_id = monsters.id " +
                        "LEFT JOIN gods ON myths.god_id = gods.id " +
                        "WHERE heroes.name LIKE ?");
        statement.setString(1, name);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            System.out.println(rs.getString(1) + " | " + rs.getString(2) + " | " + rs.getString(3) + " | " + rs.getString(4) + " | " + rs.getString(5));
        }
        System.out.println("SELECT with WHERE (" + (System.currentTimeMillis() - time) + " мс.)");
    }

    private static void getGodsType(Connection connection, String type) throws SQLException {
        if (type == null || type.isBlank()) return;
        long time = System.currentTimeMillis();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(
                "SELECT gods.id, gods.name, typeofgod.type, gender.gender, sphereofinfluence.sphere " +
                        "FROM gods " +
                        "JOIN typeofgod ON gods.type_id = typeofgod.id " +
                        "JOIN gender ON gods.gender_id = gender.id " +
                        "JOIN sphereofinfluence ON gods.sphere_id = sphereofinfluence.id;");
        while (rs.next()) {
            if (rs.getString(3).contains(type)) {
                System.out.println(rs.getString(1) + " | " + rs.getString(2) + " | " + rs.getString(3) + " | " + rs.getString(4) + " | " + rs.getString(5));
            }
        }
    }
    // ------------------------------------------- ЗАПРОСЫ НА ИЗМЕНЕНИЕ БД ----------------------------------------------------------------
    private static void addSphere(Connection connection, String sphere)  throws SQLException {
        if (sphere == null || sphere.isBlank()) return;

        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO sphereofinfluence(sphere) VALUES (?) returning id;", Statement.RETURN_GENERATED_KEYS);    // создаем оператор шаблонного-запроса с "включаемыми" параметрами - ?
        statement.setString(1, sphere);    // "безопасное" добавление имени

        int count = statement.executeUpdate();  // выполняем запрос на коррекцию и возвращаем количество измененных строк

        ResultSet rs = statement.getGeneratedKeys(); // прочитать запрошенные данные от БД
        if (rs.next()) { // прокрутить к первой записи, если они есть
            System.out.println("New sphere's ID: " + rs.getInt(1));
        }

        System.out.println("INSERTed " + count + " sphere of influence");
        getSpheres(connection);
    }

    private static void removeSphere(Connection connection, String sphere) throws SQLException {
        if (sphere == null || sphere.isBlank()) return;

        PreparedStatement statement = connection.prepareStatement("DELETE from sphereofinfluence WHERE sphere=?;");
        statement.setString(1, sphere);

        int count = statement.executeUpdate(); // выполняем запрос на удаление и возвращаем количество измененных строк
        System.out.println("DELETEd " + count + " sphere of influence");
        getSpheres(connection);
    }
    private static void correctSphere(Connection connection, String sphere, int id) throws SQLException {
        if (sphere == null || sphere.isBlank() || id < 0) return;

        PreparedStatement statement = connection.prepareStatement("UPDATE sphereofinfluence SET id=? WHERE sphere=?;");
        statement.setInt(1, id); // сначала что передаем
        statement.setString(2, sphere);   // затем по чему ищем

        int count = statement.executeUpdate();  // выполняем запрос на коррекцию и возвращаем количество измененных строк

        System.out.println("UPDATEd " + count + " sphere");
        getSpheres(connection);
    }


    private static void addGod(Connection connection, String name, int gender, int sphere, int type)  throws SQLException {
        if (name == null || name.isBlank() || gender > 2 || gender < 1 || sphere < 1 || type < 1) return;

        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO gods(name, gender_id, sphere_id, type_id) VALUES (?, ?, ?, ?) returning id;", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, name);
        statement.setInt(2, gender);
        statement.setInt(3, sphere);
        statement.setInt(4, type);

        int count = statement.executeUpdate();

        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()) {
            System.out.println("New god's ID: " + rs.getInt(1));
        }

        System.out.println("INSERTed " + count + " god");
        getGods(connection);
    }
}
