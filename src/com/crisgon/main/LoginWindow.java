package com.crisgon.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Window.Type;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginWindow extends JFrame {

	private JPanel contentPane;
	private JTextField tfUsuario;
	private JTextField tfContrasenya;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow frame = new LoginWindow();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginWindow() {

		setType(Type.UTILITY);
		setTitle("Chat - Iniciar sesi\u00F3n");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 250, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tfUsuario = new HintTextField("Usuario");
		tfUsuario.setToolTipText("");
		tfUsuario.setBounds(10, 92, 214, 20);
		contentPane.add(tfUsuario);
		tfUsuario.setColumns(10);

		tfContrasenya = new HintTextField("Contrasenya");
		tfContrasenya.setBounds(10, 123, 214, 20);
		contentPane.add(tfContrasenya);
		tfContrasenya.setColumns(10);

		JButton btnIniciarSesin = new JButton("INICIAR SESI\u00D3N");
		btnIniciarSesin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String usuario;
				String contrasenya;
				
				usuario = tfUsuario.getText();
				contrasenya = tfContrasenya.getText();
				
				if (tfContrasenya.getText().equals(contrasenya)) { //CAMBIAR
					setVisible(false);
					ChatWindow chat = new ChatWindow(usuario);
					chat.setVisible(true);
					chat.setLocationRelativeTo(null);
					JOptionPane.showMessageDialog(null, "Bienvenido " + tfUsuario.getText() + ", ha iniciado sesión correctamente.",
							"Sesión iniciada", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnIniciarSesin.setBounds(10, 154, 214, 23);
		contentPane.add(btnIniciarSesin);

		JButton btnRegistrarse = new JButton("REGISTRARSE");
		btnRegistrarse.setBounds(10, 188, 214, 23);
		contentPane.add(btnRegistrarse);

	}
}
