import java.net.ServerSocket;
import java.net.Socket;
/*等待客户端连接的线程，一直等待用户的连接
每当连接一个用户，为该用户开辟一个线程
* */
public class ServerThread extends Thread{
	private ServerSocket serverSocket;
	private boolean flag=true;
	private Couple couple=new Couple();
	public ServerThread(ServerSocket serverSocket) {
		this.serverSocket=serverSocket;
	}
	@Override
	public void run() {
		super.run();
		while(flag) {
			try {
				System.out.println("server waiting...");
				Socket socket=serverSocket.accept();
				ServerAgentThread serverAgentThread=new ServerAgentThread(couple,socket);
				serverAgentThread.start();
				System.out.println("connect one...");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
