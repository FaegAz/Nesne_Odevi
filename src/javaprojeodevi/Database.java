/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaprojeodevi;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.table.DefaultTableModel;

public class Database {

    static final String DB_URL = "jdbc:mysql://localhost:3306/";
    static final String DB_URL_Kelime = DB_URL + "KelimeApp2";
    static final String USER = "root";
    static final String PASSWORD = "root";

    public static void dbCreate() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        Statement st = conn.createStatement();
        String sql = "CREATE DATABASE KelimeApp2";
        st.execute(sql);
        st.close();
    }

    public static void dbTableCreate() throws SQLException {

        Connection conn = DriverManager.getConnection(DB_URL_Kelime, USER, PASSWORD);
        Statement st = conn.createStatement();
        String sql = "CREATE TABLE KELIMELER " + "(id INTEGER not NULL," + "kelime VARCHAR(100)," + "kelimeAnlam VARCHAR(100),"
                + "PRIMARY KEY(id))";
        st.execute(sql);
        st.close();
    }

    public static void dbTableCreateTest() throws SQLException {

        Connection conn = DriverManager.getConnection(DB_URL_Kelime, USER, PASSWORD);
        Statement st = conn.createStatement();
        String sql = "CREATE TABLE TEST " + "(id INTEGER not NULL," + "soru VARCHAR(100)," + "cevap1 VARCHAR(100),"
                + "cevap2 VARCHAR(100)," + "cevap3 VARCHAR(100)," + "cevap4 VARCHAR(100)," + "PRIMARY KEY(id))";
        st.execute(sql);
        st.close();
    }

    public static void dbTableAddValue(int id, String kelime, String kelimeAnlam) throws SQLException {

        Connection conn = DriverManager.getConnection(DB_URL_Kelime, USER, PASSWORD);
        String sorgu = "INSERT INTO kelimeler(id, kelime, kelimeAnlam) VALUES" + "(?,?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sorgu);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, kelime);
        preparedStatement.setString(3, kelimeAnlam);
        preparedStatement.executeUpdate();
    }

    public static int dbLastid() throws SQLException {
        int lastId = 0;
        Connection conn = DriverManager.getConnection(DB_URL_Kelime, USER, PASSWORD);
        Statement st = conn.createStatement();
        String sql = "SELECT * FROM kelimeler";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {

            lastId = rs.getInt("id");
        }
        return lastId;
    }

    public static void dbToTable(DefaultTableModel tblModel) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL_Kelime, USER, PASSWORD);
        PreparedStatement ps = conn.prepareStatement("Select * from kelimeler");
        ResultSet rs = ps.executeQuery();
        tblModel.setRowCount(0);
        while (rs.next()) {

            Object o[] = {rs.getInt("id"), rs.getString("kelime"), rs.getString("kelimeAnlam")};
            tblModel.addRow(o);

        }

    }

    public static void dbDeleteRow(int id) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL_Kelime, USER, PASSWORD);
        PreparedStatement ps = conn.prepareStatement("DELETE FROM kelimeler WHERE id =?");
        ps.setInt(1, id);
        ps.executeUpdate();

    }

    public static void dbSearch(String arama, DefaultTableModel tblModel) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL_Kelime, USER, PASSWORD);
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM kelimeler WHERE kelime like ? ");
        ps.setString(1, "%" + arama + "%");
        ResultSet rs = ps.executeQuery();

        tblModel.setRowCount(0);
        while (rs.next()) {

            Object o[] = {rs.getInt("id"), rs.getString("kelime"), rs.getString("kelimeAnlam")};
            tblModel.addRow(o);

        }
    }

    public static void dbUpdate(int index, String kelime, String kelimeAnlam) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL_Kelime, USER, PASSWORD);
        PreparedStatement ps = conn.prepareStatement("UPDATE kelimeler SET kelime =?, kelimeAnlam =? WHERE id =?");
        ps.setInt(3, index);
        ps.setString(1, kelime);
        ps.setString(2, kelimeAnlam);
        ps.executeUpdate();

    }

    public static ArrayList<Integer> dbGetIdArray() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL_Kelime, USER, PASSWORD);
        PreparedStatement ps = conn.prepareStatement("Select * from kelimeler");
        ResultSet rs = ps.executeQuery();
        ArrayList<Integer> arylst = new ArrayList<>();

        while (rs.next()) {

            arylst.add(rs.getInt("id"));

        }
        return arylst;
    }

    public static void dbAddTestId() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL_Kelime, USER, PASSWORD);
        ArrayList<Integer> sorular = Database.dbGetIdArray();
        ArrayList<String> cevaplar = new ArrayList<>();
        for (int i = 0; i < sorular.size(); i++) {
            String sorgu = "SELECT * FROM kelimeler WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sorgu);
            ps.setInt(1, sorular.get(i));
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("id");
                String soru = rs.getString("kelime");
                String cevap = rs.getString("kelimeAnlam");

                String sorgu2 = "SELECT * FROM kelimeler WHERE id !=?";
                PreparedStatement ps2 = conn.prepareStatement(sorgu2);
                ps2.setInt(1, sorular.get(i));
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    cevaplar.add(rs2.getString("kelimeAnlam"));

                }
                Random random = new Random();
                int a = random.nextInt(cevaplar.size());
                String cevap1 = cevaplar.get(a);
                cevaplar.remove(a);
                int a1 = random.nextInt(cevaplar.size());
                String cevap2 = cevaplar.get(a1);
                cevaplar.remove(a1);
                int a2 = random.nextInt(cevaplar.size());
                String cevap3 = cevaplar.get(a2);
                cevaplar.remove(a2);
                PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO test(id,soru,cevap1,cevap2,cevap3,cevap4) VALUES" + "(?,?,?,?,?,?)");
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, soru);
                preparedStatement.setString(3, cevap);
                preparedStatement.setString(4, cevap1);
                preparedStatement.setString(5, cevap2);
                preparedStatement.setString(6, cevap3);
                preparedStatement.executeUpdate();
                cevaplar.clear();

            }

        }

    }

    public static void dbGetQuestion(int id, JLabel soru, JRadioButton cevap, JRadioButton cevap1, JRadioButton cevap2, JRadioButton cevap3) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL_Kelime, USER, PASSWORD);
        ArrayList<String> cevaplar = new ArrayList<>();
        String sorgu = "SELECT * FROM test WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sorgu);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            soru.setText(rs.getString("soru")+" kelimesinin karşılığı nedir ?");
            cevaplar.add(rs.getString("cevap1"));
            cevaplar.add(rs.getString("cevap2"));
            cevaplar.add(rs.getString("cevap3"));
            cevaplar.add(rs.getString("cevap4"));
        }
        Random random = new Random();
        int a = random.nextInt(cevaplar.size());
        String c1 = cevaplar.get(a);
        cevaplar.remove(a);
        int a1 = random.nextInt(cevaplar.size());
        String c2 = cevaplar.get(a1);
        cevaplar.remove(a1);
        int a2 = random.nextInt(cevaplar.size());
        String c3 = cevaplar.get(a2);
        cevaplar.remove(a2);
        int a3 = random.nextInt(cevaplar.size());
        String c4 = cevaplar.get(a3);
        cevaplar.remove(a3);
        cevap.setText(c1);
        cevap1.setText(c2);
        cevap2.setText(c3); 
        cevap3.setText(c4); 
         

    }
    public static ArrayList<Integer> dbGetIdTest() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL_Kelime, USER, PASSWORD);
        PreparedStatement ps = conn.prepareStatement("Select * from test");
        ResultSet rs = ps.executeQuery();
        ArrayList<Integer> arylst = new ArrayList<>();

        while (rs.next()) {

            arylst.add(rs.getInt("id"));

        }
        return arylst;
    }
    public static String dbGetAnswer(int id) throws SQLException{
        String cevap ="";
        Connection conn = DriverManager.getConnection(DB_URL_Kelime, USER, PASSWORD);
        PreparedStatement ps = conn.prepareStatement("Select * from test WHERE id = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            cevap = rs.getString("cevap1");
        }
        
        
        return cevap;
        
    }
    public static void dbDeleteRowTest(int id) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL_Kelime, USER, PASSWORD);
        PreparedStatement ps = conn.prepareStatement("DELETE FROM test WHERE id =?");
        ps.setInt(1, id);
        ps.executeUpdate();

    }
    
    public static void dbTableClear() throws SQLException{
        Connection conn = DriverManager.getConnection(DB_URL_Kelime, USER, PASSWORD);
        PreparedStatement ps = conn.prepareStatement("TRUNCATE test");
        ps.executeUpdate();
        
    }

}
