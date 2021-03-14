package com.mryensei.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.mryensei.core.Cliente;
import com.mryensei.core.Servidor;


public class ViewChat extends JFrame implements ActionListener, WindowListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenuBar menuBar;
	private JMenu accion;
	private JMenuItem conect, create, exit;
	
	
	private JTextArea textAreaMensajes;
	private JTextField fieldMensaje;
	private JButton enviar;
	private JScrollPane scroll;
	
	
	private Servidor servidor;
	private Cliente cliente;
	
	private static final int PORT =9900;
	private static final String HOST ="localhost";
	
	
	public ViewChat() throws HeadlessException {
		super("Ventana de Chat Cliente");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("LAN Chat");
		this.setSize(400, 600);
		this.addWindowListener(this);
		this.setComponets();
		this.habilitaCampos(false);
		this.setVisible(true);
		
	}
	
	private void habilitaCampos(boolean b){
		enviar.setEnabled(b);
		textAreaMensajes.setEnabled(b);
		fieldMensaje.setEnabled(b);
	}
	


	public void setComponets(){
		menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		accion = new JMenu("Accion");
		menuBar.add(accion);
		
		conect = new JMenuItem("Conectar");
		conect.addActionListener(this);
		
		accion.add(conect);
		create = new JMenuItem("Crear Servidor");
		create.addActionListener(this);
		accion.add(create);
		
		exit = new JMenuItem("Salir");
		exit.addActionListener(this);
		accion.add(exit);
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx =0;
		gbc.gridy=0;
		gbc.gridheight=1;
		gbc.gridwidth=2;
		gbc.fill=GridBagConstraints.BOTH;
		gbc.weightx=1.0;
		gbc.weighty=1.0;
		textAreaMensajes = new JTextArea();
		textAreaMensajes.setFocusable(false);
		scroll=new JScrollPane(textAreaMensajes);
		this.add(scroll,gbc);
		
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.gridheight=1;
		gbc.gridwidth=1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx=1.0;
		gbc.weighty=0.0;
		fieldMensaje = new JTextField(20);
		this.add(fieldMensaje,gbc);
		
		
		gbc.gridx=1;
		gbc.gridy=1;
		gbc.gridheight=1;
		gbc.gridwidth=1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx=0.0;
		gbc.weighty=0.0;
		enviar = new JButton("Enviar");
		
		enviar.addActionListener(this);
		this.add(enviar,gbc);
		
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		Thread thread;
		if(e.getSource()==create){
			servidor = new Servidor(PORT, this);
			this.habilitaCampos(true);
			thread = new Thread(servidor);
			thread.start();			
			
			
		}else 
			if(e.getSource()== conect){
				cliente = new Cliente(HOST, PORT, this);	
				this.habilitaCampos(true);
				thread = new Thread(cliente);
				thread.start();	
				
			
				}else{
					if(e.getSource()== exit){
						this.dispose();//este llamará al método ActionEvent WindowClosed()
					
					}else {
						if(e.getSource()== enviar){
							String mensaje = fieldMensaje.getText();
							textAreaMensajes.append("\nYO: "+fieldMensaje.getText());
							fieldMensaje.setText("");//vacia el fieldText
							if(servidor!=null){
								servidor.writeLine(mensaje);//envia lo tipeado						
								
							}
							if(cliente!=null){
								cliente.writeLine(mensaje);//envia lo tipeado
								
							}
							
						}
					}
				}
				
			
	}


	public JTextArea getTextAreaMensajes() {
		return textAreaMensajes;
	}


	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowClosed(WindowEvent e) {
		if(servidor!=null){
			servidor.closeServer();
		}
		if(cliente!=null){
			cliente.closeConnection();
			
		}
		
	}


	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
