package databaseOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import transaction.Transaction;

public class TransactionDAO {
	private Connection con;
	private PreparedStatement ps;
	
	public TransactionDAO(Connection con) throws SQLException {
		this.con = con;
	}

	public void insertTransactions(ArrayList<Transaction> list) {
		try {
			ps = con.prepareStatement("insert into transaction ("
					+ "TRANSACTION_ID, TRANSACTION_TYPE, TERMINAL_ID, SEQUENCE_NB, OPERATOR_ID, OPERATOR_NAME, COLLECT_ID, SANGRIA_ID, PDV_NB, "
					+ "DENOMINATION_2, DENOMINATION_5, DENOMINATION_10, DENOMINATION_20, DENOMINATION_50, DENOMINATION_100, TOTAL, SEAL_CD, DATE) "
					+ "values (nextval('SEQ_TRANSACTION'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			for (Transaction op : list) {
				ps.setString(1, op.getTransaction_Type());
				ps.setString(2, op.getTerminal_ID());
				ps.setInt(3, (op.getSequence_NB()));
				ps.setInt(4, (op.getOperator_ID()));
				ps.setString(5, op.getOperator_Name());
				ps.setString(6, op.getCollect_ID());
				ps.setInt(7, (op.getSangria_ID()));
				ps.setInt(8, op.getPDV_NB());
				int cont = 9;
				for (int i = 0; i < 6; i++) {
					ps.setInt(cont, op.getDenomination(i));
					cont++;
				}
				ps.setInt(15, (op.getTotal()));
				ps.setString(16, op.getSeal_CD());

				String date = op.getDate() + " " + op.getHour();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
				LocalDateTime dateTime = LocalDateTime.parse(date, formatter);
				ps.setTimestamp(17, Timestamp.valueOf(dateTime));
				ps.addBatch();
				ps.clearParameters();
			}
			ps.executeBatch();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

//	public void deleteTransaction(int id) {
//		try {
//			PreparedStatement ps = con.prepareStatement("select * from tabela where TRANSACTION_ID = ?");
//			ps.setInt(1, id);
//			boolean found = ps.execute();
//			if (found) {
//				ps = con.prepareStatement("delete from tabela where TRANSACTION_ID = ?");
//				ps.setInt(1, id);
//				ps.executeUpdate();
//				System.out.println("Transaction with ID = " + id + " deleted successfully!");
//			} else {
//				System.out.println("There isn't a transaction with this ID");
//			}
//
//		} catch (SQLException ex) {
//			ex.printStackTrace();
//		}
//	}
//
//	public void updateTransaction(int id, String coluna, Object obj) {
//		try {
//			PreparedStatement ps = con.prepareStatement("select * from tabela where TRANSACTION_ID = ?");
//			ps.setInt(1, id);
//			boolean achou = ps.execute();
//			if (achou) {
//				ps = con.prepareStatement("Update tabela set " + coluna + " = ? where TRANSACTION_ID = ?");
//				if (obj instanceof String) {
//					ps.setString(1, (String) obj);
//				} else if (obj instanceof Integer) {
//					ps.setInt(1, (int) obj);
//				}
//				ps.setInt(2, id);
//				ps.executeUpdate();
//				System.out.println("Update performed successfully!");
//			} else {
//				System.out.println("There isn't a transaction with this ID");
//			}
//		} catch (SQLException ex) {
//			ex.getMessage();
//		}
//	}
}
