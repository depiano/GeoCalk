package it.unisa.geocalk.model;

public class citiBike {
	
	private String id;
	private String tripduration;
	private String starttime;
	private String stoptime;
	private String startStationId;
	private String startStationName;
	private double startStationLatitude;
	private double startStationLongitude;
	private String endStationId;
	private String endStationName;
	private double endStationLatitude;
	private double endStationLongitude;
	private String bikeId;
	private String userType;
	private String birthYear;
	private String gender;
	
	
	public citiBike()
	{
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	

	public String getTripduration() {
		return tripduration;
	}

	public void setTripduration(String tripduration) {
		this.tripduration = tripduration;
	}

	public String getStartStationId() {
		return startStationId;
	}

	public void setStartStationId(String startStationId) {
		this.startStationId = startStationId;
	}

	public String getEndStationId() {
		return endStationId;
	}

	public void setEndStationId(String endStationId) {
		this.endStationId = endStationId;
	}

	public String getBikeId() {
		return bikeId;
	}

	public void setBikeId(String bikeId) {
		this.bikeId = bikeId;
	}

	public String getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(String birthYear) {
		this.birthYear = birthYear;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getStoptime() {
		return stoptime;
	}
	public void setStoptime(String stoptime) {
		this.stoptime = stoptime;
	}
	
	public String getStartStationName() {
		return startStationName;
	}
	public void setStartStationName(String startStationName) {
		this.startStationName = startStationName;
	}
	public double getStartStationLatitude() {
		return startStationLatitude;
	}
	public void setStartStationLatitude(double startStationLatitude) {
		this.startStationLatitude = startStationLatitude;
	}
	public double getStartStationLongitude() {
		return startStationLongitude;
	}
	public void setStartStationLongitude(double startStationLongitude) {
		this.startStationLongitude = startStationLongitude;
	}
	
	public String getEndStationName() {
		return endStationName;
	}
	public void setEndStationName(String endStationName) {
		this.endStationName = endStationName;
	}
	public double getEndStationLatitude() {
		return endStationLatitude;
	}
	public void setEndStationLatitude(double endStationLatitude) {
		this.endStationLatitude = endStationLatitude;
	}
	
	@Override
	public String toString() {
		return "citiBike [id=" + id + ", tripduration=" + tripduration + ", starttime=" + starttime + ", stoptime="
				+ stoptime + ", startStationId=" + startStationId + ", startStationName=" + startStationName
				+ ", startStationLatitude=" + startStationLatitude + ", startStationLongitude=" + startStationLongitude
				+ ", endStationId=" + endStationId + ", endStationName=" + endStationName + ", endStationLatitude="
				+ endStationLatitude + ", endStationLongitude=" + endStationLongitude + ", bikeId=" + bikeId
				+ ", userType=" + userType + ", birthYear=" + birthYear + ", gender=" + gender + "]";
	}

	public double getEndStationLongitude() {
		return endStationLongitude;
	}

	public void setEndStationLongitude(double endStationLongitude) {
		this.endStationLongitude = endStationLongitude;
	}

	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
		
}
