import java.sql.*;
import java.util.*;
class Govindh {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        batchwithRollback();
    }

    public static void insertrecord() throws Exception {
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String passwaord = "arjitha";
        String querry = "insert into st values(4,'arjitha',9.1)";
        Connection con = DriverManager.getConnection(url, username, passwaord);
        Statement st = con.createStatement();
        int row = st.executeUpdate(querry);
        System.out.println("no of row affected" + row);
        con.close();

    }

    public static void insertvariable() throws Exception {
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String passwaord = "arjitha";
        int id = 5;
        String name = "govind";
        float cgpa = 8.6f;
        // "insert into st values(4,'arjitha',9.1)"
        String querry = "insert into st values(" + id + ",'" + name + "'," + cgpa + ");";
        Connection con = DriverManager.getConnection(url, username, passwaord);
        Statement st = con.createStatement();
        int row = st.executeUpdate(querry);
        System.out.println("no of row affected" + row);
        con.close();

    }

    public static void deletevariable() throws Exception {
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String passwaord = "arjitha";
        int id = 5;
        String querry = "delete from st where std_id =" + id;
        Connection con = DriverManager.getConnection(url, username, passwaord);
        Statement st = con.createStatement();
        int row = st.executeUpdate(querry);
        System.out.println("no of row affected" + row);
        con.close();

    }

    public static void update() throws Exception {
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String passwaord = "arjitha";
        int id = 5;
        String querry = "update st set cgpa = 10 where std_id = 1";
        Connection con = DriverManager.getConnection(url, username, passwaord);
        Statement st = con.createStatement();
        int row = st.executeUpdate(querry);
        System.out.println("no of row affected" + row);
        con.close();

    }

    public static void updateprepared() throws Exception {
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String passwaord = "arjitha";
        int id = 5;
        String querry = "update st set cgpa = ? where std_id = 1";
        Connection con = DriverManager.getConnection(url, username, passwaord);
        PreparedStatement ps = con.prepareStatement(querry);
        ps.setInt(1, 11);
        int row = ps.executeUpdate();
        System.out.println("no of row affected" + row);
        con.close();

    }

    public static void insertpre() throws Exception {
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String passwaord = "arjitha";
        int id = 6;
        String name = "shivani";
        float cgpa = 8.6f;
        // "insert into st values(4,'arjitha',9.1)"
        String querry = "insert into st values(?,?,?);";
        Connection con = DriverManager.getConnection(url, username, passwaord);
        PreparedStatement ps = con.prepareStatement(querry);
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setFloat(3, cgpa);
        int row = ps.executeUpdate();
        System.out.println("no of row affected" + row);
        con.close();

    }

    public static void readrecord() throws Exception {
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String passwaord = "arjitha";
        String querry = "select * from st";
        Connection con = DriverManager.getConnection(url, username, passwaord);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(querry);
        while (rs.next()) {
            System.out.println("roll is " + rs.getInt(1));
            System.out.println("name is " + rs.getString(2));
            System.out.println("salary is " + rs.getFloat(3));
        }
        con.close();
    }

    // callling store procedure
    // three type of statement
    //* normal create statement ( select, update, read, delete, insert) this kind of function we can use both
    //* prepared statement    all the funtions of normal if many no of variable needed..
    //* callable statement   (stored procedure) cal Get emp--example
    public static void sp() throws Exception {
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String passwaord = "arjitha";
        Connection con = DriverManager.getConnection(url, username, passwaord);
        CallableStatement cst = con.prepareCall("{call getstd()}");
        ResultSet rs = cst.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getInt(1));
            System.out.println(rs.getString(2));
            System.out.println(rs.getFloat(3));
        }
        con.close();
    }

    // store procedure with parameter
    public static void spParameter() throws Exception {
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String passwaord = "arjitha";
        int id = 1;
        Connection con = DriverManager.getConnection(url, username, passwaord);
        CallableStatement cst = con.prepareCall("{call getstdbyID(?)}");
        cst.setInt(1, id);
        ResultSet rs = cst.executeQuery();
        rs.next();
        System.out.println(rs.getInt(1));
        System.out.println(rs.getString(2));
        System.out.println(rs.getFloat(3));

        con.close();
    }

    // store procedure with out parameter
    public static void spoutParameter() throws Exception {
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String passwaord = "arjitha";
        int id = 1;
        Connection con = DriverManager.getConnection(url, username, passwaord);
        CallableStatement cst = con.prepareCall("{call getnamebyID(?,?)}");
        cst.setInt(1, id);
        cst.registerOutParameter(2, Types.VARCHAR);
        cst.executeUpdate();
        System.out.println(cst.getString(2));


        con.close();
    }

    // commit without call commit its act like auto commit /// use auto commit false
    public static void commit() throws Exception {
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String passwaord = "arjitha";
        String querry1 = "update st set cgpa = 0 where  std_id = 1";
        String querry2 = "update st set cgpa = 0 where  std_id = 2";


        Connection con = DriverManager.getConnection(url, username, passwaord);
        Statement st = con.createStatement();
        con.setAutoCommit(false);
        int rows1 =  st.executeUpdate(querry1);
        System.out.println("no of rows affected in querry 1 "+rows1);
        int rows2 = st.executeUpdate(querry2);
        System.out.println("no of rows affected in querry 2 "+ rows2);
        if (rows1 > 0 && rows2 > 0){
            con.commit();
        }
        con.close();
    }
    // batch processing
    public static void batchwithRollback() throws Exception {
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String passwaord = "arjitha";
        String querry1 = "update st set cgpa = 1 where  std_id = 1";
        String querry2 = "update st set cgpa = 2 where  std_id = 2";
        String querry3 = "update st set cgpa = 3 where  std_id = 3";
        String querry4 = "update st set cgpa = 4 where  std_id = 4";

        Connection con = DriverManager.getConnection(url, username, passwaord);
        con.setAutoCommit(false);
        Statement st = con.createStatement();
        st.addBatch(querry1);
        st.addBatch(querry2);
        st.addBatch(querry3);
        st.addBatch(querry4);

        int arr[] = st.executeBatch();
        for (int i : arr){
            if (i > 0)
                continue;
            else
                con.rollback();
        }
        con.commit();
        con.close();
    }

}
