package aplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import db.DB;

public class Program {

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = DB.getConnection();
			
			st = conn.prepareStatement(
					"INSERT INTO seller"
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentID)"
					+ "VALUES"
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			/*
			st = conn.prepareStatement( //Exemplo de inserção de mais de um valor no banco de dados
					"insert into department (Name) values ('D1'),('D2')"
					Statement.RETURN_GENERATED_KEYS);
			*/	
			st.setString(1, "Carl Purple");
			st.setString(2, "carl@gmail.com");
			st.setDate(3, new java.sql.Date(sdf.parse("22/04/1986").getTime()));
			st.setDouble(4, 3000.0);
			st.setInt(5, 4);
			
			int rowsAffected = st.executeUpdate(); //quantas linhas foram alteradas
			
			if(rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				while (rs.next()) {
					int id = rs.getInt(1);//valor da primeira coluna do banco de dados
					System.out.println("Done! Id = "+ id);
				}
			}
			else {
				System.out.println("No rown affected!");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		catch(ParseException e) {
			e.printStackTrace();
		}
		finally{
			DB.closeStatemente(st);
			DB.closeConnection();
		}

}
}
