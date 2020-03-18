package transaction;

public class Transaction {
	
	String TRANSACTION_TYPE;
	String DATE;
	String HOUR;
	String TERMINAL_ID;
	int OPERATOR_ID;
	int SEQUENCE_NB;
	String OPERATOR_NAME = "";
	String COLLECT_ID = "";
	int SANGRIA_ID;
	int PDV_NUMBER;
	int[] DENOMINATIONS = new int[6];
	int TOTAL;
	String SEAL_CD = "";
	
	public String getTransaction_Type() {
		return TRANSACTION_TYPE;
	}
	public void setTransaction_Type(String TRANSACTION_TYPE) {
		this.TRANSACTION_TYPE = TRANSACTION_TYPE;
	}
	public String getDate() {
		return this.DATE;
	}
	public void setDate(String date) {
		this.DATE = date;
	}
	public String getHour() {
		return HOUR;
	}
	public void setHour(String hour) {
		this.HOUR = hour;
	}
	public String getTerminal_ID() {
		return TERMINAL_ID;
	}
	public void setTerminal_ID(String terminal_ID) {
		this.TERMINAL_ID = terminal_ID;
	}
	public int getOperator_ID() {
		return OPERATOR_ID;
	}
	public void setOperator_ID(int operator_ID) {
		this.OPERATOR_ID = operator_ID;
	}
	public int getSequence_NB() {
		return SEQUENCE_NB;
	}
	public void setSequence_NB(int sequence_NB) {
		this.SEQUENCE_NB = sequence_NB;
	}
	public String getOperator_Name() {
		return OPERATOR_NAME;
	}
	public void setOperator_Name(String operator_Name) {
		this.OPERATOR_NAME += operator_Name;
	}
	public String getCollect_ID() {
		return COLLECT_ID;
	}
	public void setCollect_ID(String collect_ID) {
		this.COLLECT_ID = collect_ID;
	}
	public int getSangria_ID() {
		return SANGRIA_ID;
	}
	public void setSangria_ID(int sangria_ID) {
		this.SANGRIA_ID = sangria_ID;
	}
	public int getPDV_NB() {
		return PDV_NUMBER;
	}
	public void setPDV_NB(int PDV) {
		this.PDV_NUMBER = PDV;
	}
	
	public int getDenomination(int index) {
		return this.DENOMINATIONS[index];
	}
	
	public void setDenomination(int value, int index) {
		this.DENOMINATIONS[index] = value;
	}
	public int getTotal() {
		return TOTAL;
	}
	public void setTotal(int total) {
		this.TOTAL = total;
	}
	public String getSeal_CD() {
		return SEAL_CD;
	}
	public void setSeal_CD(String seal_CD) {
		this.SEAL_CD = seal_CD;
	}
	
	
}
