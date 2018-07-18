import java.io.IOException;
import java.net.ServerSocket;
/*运行程序的main 方法
用来申请一个socket  开启服务器接收客户端连接的线程（Server Thread）
* */
public class Server {
	private static final int port=9999;
	private ServerThread serverThread;
	private ServerSocket serverSocket;
	public Server() {
		try {
			serverSocket=new ServerSocket(port);
			serverThread=new ServerThread(serverSocket);
			serverThread.start();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("error happen");
		}

	}
	public static void main(String[] args) {
		new Server();
		System.out.println("The server is opening");
	}
}
