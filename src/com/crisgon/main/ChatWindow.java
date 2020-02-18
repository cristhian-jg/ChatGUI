package com.crisgon.main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import com.crisgon.main.DataPacket;
import java.awt.Font;
import java.awt.Window.Type;

/**
 * Created by @crishtian-jg on 13/02/2020
 * 
 * Clase que funciona como cliente que se comunica a traves de un Servidor
 * utilizando Sockets.
 */

public class ChatWindow extends JFrame implements Runnable {

	private static final String TAG = "ChatClient";
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField textField;
	private JTextField tfIp;
	private JTextArea textArea;

	private JLabel lblNickname;

	private Thread thread;

	public ChatWindow(String nickname) {
		setType(Type.POPUP);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 250, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setBounds(10, 280, 135, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Socket socket;
				ObjectOutputStream oos;

				if (!textField.equals("")) {
					textArea.append("Yo: " + textField.getText() + "\n");
			
					try {
						socket = new Socket("192.168.1.39", 9999);

						DataPacket data = new DataPacket();

						data.setNickname(nickname);
						data.setIp(tfIp.getText());
						data.setMessage(textField.getText());

						oos = new ObjectOutputStream(socket.getOutputStream());
						oos.writeObject(data);
						oos.close();
						
						textField.setText("");
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				}
			}
		});
		btnSend.setBounds(155, 279, 69, 23);
		contentPane.add(btnSend);

		textArea = new JTextArea();
		textArea.setBounds(10, 36, 214, 232);
		contentPane.add(textArea);

		tfIp = new JTextField();
		tfIp.setBounds(138, 8, 86, 20);
		contentPane.add(tfIp);
		tfIp.setColumns(10);

		lblNickname = new JLabel(nickname);
		lblNickname.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNickname.setBounds(10, 8, 118, 20);
		contentPane.add(lblNickname);

		/** Arrancar el hilo */
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		ServerSocket serverSocket;
		Socket client;
		DataPacket data;
		ObjectInputStream ois;

		try {
			serverSocket = new ServerSocket(9090);

			while (true) {
				client = serverSocket.accept();
				ois = new ObjectInputStream(client.getInputStream());
				data = (DataPacket) ois.readObject();
				textArea.append(data.getNickname() + ": " + data.getMessage() + "\n");
			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
