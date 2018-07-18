/*
* 每一个用户连接上之后都会专门为他开辟一个线程
* 当用户断开连接时  会报异常
* 通过异常处理  关闭该用户的资源
* */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerAgentThread extends Thread {
	private Socket socket;
	private DataInputStream dataInputStream;
	private DataOutputStream dataOutputStream;
	boolean flag=true;
	private Person person;
	private int id;
	private Couple couple;
	public ServerAgentThread(Couple couple,Socket socket) {
		try {
			this.socket=socket;
			this.couple=couple;
			dataInputStream=new DataInputStream(socket.getInputStream());
			dataOutputStream=new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		super.run();
		while(flag) {
			String string;
			try {
				string = dataInputStream.readUTF();
				if (string.startsWith("<#ONLINE#>")) {
					String data=string.substring(10);
					ONLINEmethod(data);
				}else if (string.startsWith("<#OFFLINE#>")) {
					OFFLINEmethod();
				}else if (string.startsWith("<#POSITION#>")) {
					String[] data=string.split("@");
					POSITIONmethod(Double.parseDouble(data[1]), Double.parseDouble(data[2]));
				}
			} catch (IOException e) {
				//断开连接时进行异常处理，读取并处理信息的循环结束，释放资源
				flag=false;
				closeConnect();
				OFFLINEmethod();
			}

		}
	}
	private synchronized void POSITIONmethod(double latitude,double longitude) {
		person.setLatitude(latitude);
		person.setLongitude(longitude);
		System.out.println(id+"@"+latitude+"@"+longitude);
		try {
			if (this.id==couple.FEMALE) {
				dataOutputStream.writeUTF("<#POSITION#>"+"@"+couple.getMale().getLatitude()+"@"+couple.getMale().getLongitude());
				dataOutputStream.flush();
			}else if(this.id==couple.MALE){
				dataOutputStream.writeUTF("<#POSITION#>"+"@"+couple.getFemale().getLatitude()+"@"+couple.getFemale().getLongitude());
				dataOutputStream.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private synchronized void OFFLINEmethod() {
		try {
			if (this.id==couple.FEMALE) {
				couple.femaleLeave();
				if (couple.getMale().isOnline())
					couple.getMale().getDataOutputStream().writeUTF("<#OFFLINE#>");
			}else if(this.id==couple.MALE){
				couple.maleLeave();
				if (couple.getFemale().isOnline())
					couple.getFemale().getDataOutputStream().writeUTF("<#OFFLINE#>");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		closeConnect();
		System.out.println("close connect");
	}
	private synchronized void ONLINEmethod(String data) {
		try {
			if (data.equals("FEMALE")) {
				person=couple.getFemale();
				person.setDataInputStream(dataInputStream);
				person.setDataOutputStream(dataOutputStream);
				person.setOnline(true);
				this.id=Couple.FEMALE;
				if (couple.getMale().isOnline()) {
					couple.getFemale().getDataOutputStream().writeUTF("<#YOU_ARE_NOT_ALONE#>");
				}else {
					couple.getFemale().getDataOutputStream().writeUTF("<#YOU_ARE_ALONE#>");
				}
			}else if(data.equals("MALE")){
				person=couple.getMale();
				person.setDataInputStream(dataInputStream);
				person.setDataOutputStream(dataOutputStream);
				person.setOnline(true);
				this.id=Couple.MALE;
				if (couple.getFemale().isOnline()) {
					couple.getMale().getDataOutputStream().writeUTF("<#YOU_ARE_NOT_ALONE#>");
				}else {
					couple.getMale().getDataOutputStream().writeUTF("<#YOU_ARE_ALONE#>");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void closeConnect() {
		try {
			if (dataInputStream!=null) {
				dataInputStream.close();
			}
			if (dataOutputStream!=null) {
				dataOutputStream.close();
			}
			if (socket!=null) {
				socket.close();	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
