import java.sql.*;
import java.util.Scanner;

public class Main {

    //указываем имя, пароль и путь, где находится база данных
    public static final String DB_USERNAME = "postgres";
    public static final String DB_PASSWORD = "postgres";
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";

    public static void main(String[] args) throws Exception {
        //создан объект, который считывает инфу с консоли
        Scanner scanner = new Scanner(System.in);

        //подключаемся к базе данных
        Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

        while (true) {
            System.out.println("1. Показать список всех задач");
            System.out.println("2. Выполнить задачу");
            System.out.println("3. Создать задачу");
            System.out.println("4. Выйти");

            int command = scanner.nextInt();

            if (command == 1) {
                //объект, который отправляет запросы в БД
                Statement statement = connection.createStatement();
                String SQL_SELECT_TASKS = "select * from task order by id desc";
                //объект, который хранит результат выполнения запроса
                ResultSet result = statement.executeQuery(SQL_SELECT_TASKS);

                while (result.next()) {
                    System.out.println(result.getInt("id" + " "
                            + result.getString("name") + result.getString("state")));
                    System.out.println("\n ----------------------------\n");
                }
            } else if (command == 2) {
                String sql = "update  task set state = 'DONE' where id = ?;";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                System.out.println("Введите идентификатор задачи: ");
                int taskId = scanner.nextInt();
                preparedStatement.setInt(1, taskId);
                preparedStatement.executeUpdate();
            } else if (command == 3) {
                String sql = "insert into task (name,state) values (?, 'IN_PROCESS');";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                System.out.println("Введите название задачи: ");
                scanner.nextLine();
                String taskName = scanner.nextLine();
                preparedStatement.setString(1, taskName);
                preparedStatement.executeUpdate();
            } else if (command == 4) {
                System.exit (0);
            } else {
                System.err.println("Команда не распознана");
                System.out.println("\n ----------------------------\n");
            }
        }
    }
}
