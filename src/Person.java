/*
* 不用看
* */
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Person {
	private double latitude;
	private double longitude;
	private String ID;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	private boolean online =false;
	
	public boolean isOnline() {
		return online;
	}
	public void setOnline(boolean online) {
		this.online = online;
	}
	public void setDataInputStream(DataInputStream dataInputStream) {
		this.dataInputStream = dataInputStream;
	}
	public DataOutputStream getDataOutputStream() {
		return dataOutputStream;
	}
	public void setDataOutputStream(DataOutputStream dataOutputStream) {
		this.dataOutputStream = dataOutputStream;
	}
	public Person(double latitude,double longitude) {
		this.latitude=latitude;
		this.longitude=longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
}
