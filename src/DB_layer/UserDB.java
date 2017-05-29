package DB_layer;

import java.sql.*;
import java.util.ArrayList;
/*
 Our DB for the project.
*/

/*
 Created by Soeren Schou 31-05-2017.
 Created by Magnus Thygesen 31-05-2017.
 Created by Jonas Overgaard 31-05-2017.
*/
public class UserDB
{
    //our fields for the class
    public ArrayList<Integer> kunder = new ArrayList<>();
    public ArrayList<Integer> konkurrence = new ArrayList<>();
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    int id;
    int konkurrence_id;

    public void insertIntoDB (String sNavn, String sHar_Boern, int sAntal_Boern, int sTLF, String sEmail, String sAdresse)
    {
        //STEP 1: set base value for connection
        Connection conn = null;
        Statement stmt = null;
        try
        {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");

            String DB_Url = System.getProperty("JDBC_CONNECTION_STRING");
            String DB_User = System.getProperty("JDBC_USER");
            String DB_Password = System.getProperty("JDBC_PASSWORD");
            String DB_Connection_String = DB_Url + "?user=" + DB_User + "&password=" + DB_Password;

            conn = DriverManager.getConnection(DB_Connection_String);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Inserting records into the tables...");
            stmt = conn.createStatement();

            //Inserting the values from our GUI into the DB table called kunder
            String sql ="insert into kunder(Navn, Har_Boern, Antal_Boern, TLF, Email, Adresse)"
                    + " values (?, ?, ?, ?, ?, ?)";
            //Selecting a certion value from the tables
            //where statementet to set a specific condition
            String sql2 = "Select Id from kunder";
            String sql3 = "Select id from konkurrence where aktiv = 'Yes'";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            PreparedStatement preparedStatement1 = conn.prepareStatement(sql2);
            PreparedStatement preparedStatement2 = conn.prepareStatement(sql3);
            preparedStatement.setString(1, sNavn);
            preparedStatement.setString(2, sHar_Boern);
            preparedStatement.setInt(3, sAntal_Boern);
            preparedStatement.setInt(4, sTLF);
            preparedStatement.setString(5, sEmail);
            preparedStatement.setString(6, sAdresse);

            //executes the SQL statement
            preparedStatement.executeUpdate();

            //execustes the result set of the second and third SQL statement
            ResultSet rs = preparedStatement1.executeQuery(sql2);
            ResultSet rs1 = preparedStatement2.executeQuery(sql3);

            //the result set checking the next colume for values.
            //the while loop makes it so rs, check each colume
            while(rs.next())
            {
                //insert the the values "id" from the kunder table into our array list
                kunder.add(Integer.parseInt(rs.getString("Id")));
            }
            System.out.println("Inserted records into the table...");
            System.out.println(kunder.toString());
            while (rs1.next())
            {
                //Same here, except now with its the konkurrence id it adds to our array list
                konkurrence.add(Integer.parseInt(rs1.getString("id")));
            }


        } catch (SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally
        {
            //finally block used to close resources
            try
            {
                if (stmt != null)
                    conn.close();
            } catch (SQLException se)
            {
            }// do nothing
            try
            {
                if (conn != null)
                    conn.close();
            } catch (SQLException se)
            {
                se.printStackTrace();
            }//end finally try
        }//end try
    }

    public void findLastID()
    {
        //Setting up values
        int lastIndex;
        int lastKonkurrence_id;
        lastIndex = kunder.size();
        lastKonkurrence_id = konkurrence.size();

        //Checkting the Last index of our ArrayList
        for(int i = 0; i <= lastIndex - 1; i++)
        {
                System.out.println(i + ":" + kunder.get(i));
                this.id = kunder.get(i);
        }
        System.out.println("Last index is " + id);

        //Cheakting the lastes index for our aktiv contest id arraylist
        for (int j = 0; j <= lastKonkurrence_id - 1; j++)
        {

                this.konkurrence_id = konkurrence.get(j);

        }
        //Printsout our indexes to be sure we got the correct values
        System.out.println("The last index is" + konkurrence_id);

        System.out.println("id = " + id);
    }



    public void addToCompetionTable(String sFavoriteProdukt)
    {
        //STEP 1: setting the connection to null
        Connection conn = null;
        Statement stmt = null;
        try
        {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");

            String DB_Url = System.getProperty("JDBC_CONNECTION_STRING");
            String DB_User = System.getProperty("JDBC_USER");
            String DB_Password = System.getProperty("JDBC_PASSWORD");
            String DB_Connection_String = DB_Url + "?user=" + DB_User + "&password=" + DB_Password;

            conn = DriverManager.getConnection(DB_Connection_String);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Inserting records into the tables...");
            stmt = conn.createStatement();

            //Our SQL String that insert the value from our lastest kunde_id and konurrence_id arraylists,
            // to the kunder_tilmedlt table
            String sql ="insert into kunder_tilmeldt(kunde_id, konkurrence_id, Favorite_Produkt)"
                    + " values (?,?,?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            //setting up the values
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2,konkurrence_id);
            preparedStatement.setString(3, sFavoriteProdukt);

            preparedStatement.executeUpdate();


        } catch (SQLException se)
        {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e)
        {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally
        {
            //finally block used to close resources
            try
            {
                if (stmt != null)
                    conn.close();
            } catch (SQLException se)
            {
            }// do nothing
            try
            {
                if (conn != null)
                    conn.close();
            } catch (SQLException se)
            {
                se.printStackTrace();
            }//end finally try
        }//end try

    }
}
