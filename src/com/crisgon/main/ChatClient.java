package com.crisgon.main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

/**
 * Created by @crishtian-jg on 13/02/2020
 * 
 * Clase que funciona como cliente que se comunica a
 * traves de un Servidor utilizando Sockets.
 */

public class ChatClient extends JFrame implements Runnable {

	private static final String TAG = "ChatClient";
	private static final long serialVersionUID = -3834654030503859646L;

	private JPanel contentPane;
	private JTextField textField;
	private JTextField tfNick;
	private JTextField tfIp;
	private JTextArea textArea;
	
	private Thread thread;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatClient frame = new ChatClient();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ChatClient() {
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
				
				try {
					socket = new Socket("192.168.1.39", 3465);
					
					DataPacket data = new DataPacket();
					
					data.setNickname(tfNick.getText());
					data.setIp(tfIp.getText());
					data.setMessage(textField.getText());
					
					oos = new ObjectOutputStream(socket.getOutputStream());
					oos.writeObject(data);
					oos.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		});
		btnSend.setBounds(155, 279, 69, 23);
		contentPane.add(btnSend);

		JLabel lblIp = new JLabel("NICK:");
		lblIp.setBounds(10, 11, 41, 14);
		contentPane.add(lblIp);
		
		textArea = new JTextArea();
		textArea.setBounds(10, 36, 214, 232);
		contentPane.add(textArea);
		
		tfNick = new JTextField();
		tfNick.setBounds(51, 8, 77, 20);
		contentPane.add(tfNick);
		tfNick.setColumns(10);
		
		tfIp = new JTextField();
		tfIp.setBounds(138, 8, 86, 20);
		contentPane.add(tfIp);
		tfIp.setColumns(10);
		
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
			serverSocket = new ServerSocket(3535);
			
			while (true) {
				client = serverSocket.accept();
				ois = new ObjectInputStream(client.getInputStream());
				data = (DataPacket) ois.readObject();
				
				textArea.append("\n" + data.getNickname() + ":\n" + data.getMessage());
			}
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
