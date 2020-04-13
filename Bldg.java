public class Bldg {
	private int buildingNum;
	private int executionTime;
	private int totalTime;
	
	public Bldg() {
	}
	
	public Bldg(int bldgID, int execTime, int ttlTime) {
		this.buildingNum = bldgID;
		this.executionTime = execTime;
		this.totalTime = ttlTime;
	}
	
	//the unique ID or the buildingNum is returned
	public int getBldgID() {
		return buildingNum;
	}
	
	//the buildingNum of the bldg object is set with the parameter bldgID
	public void setBldgID(int bldgID) {
		this.buildingNum = bldgID;
	}
	
	//execution time is returned by this method
	public int getExecTime() {
		return executionTime;
	}
	
	//the executionTime of the bldg object is set with the parameter execTime
	public void setExecTime(int execTime) {
		this.executionTime = execTime;
	}
	
	//totaltime is returned
	public int getTtlTime() {
		return totalTime;
	}
	
	//the totalTime of the bldg object is set with the parameter totalTime
	public void setTtlTime(int ttlTime) {
		this.totalTime = ttlTime;
	}

	@Override
	public String toString() {
		return "bn=" + buildingNum + ", et=" + executionTime;
	}
}