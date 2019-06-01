package vista;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

import java.awt.*;

public class FramePrinsipal extends JFrame {
	
	private ImageIcon imagen;
	private Icon icono;
	public JButton carpetaOrigen, carpetaDestino,CreateCopy;
	public DefaultMutableTreeNode treeModel;
	public JScrollPane scrollPane ;
	public JTree tree;
	public JLabel lblSource,lblTarget,sicronizarD,cancelarSincr;
	public ImageIcon padreIcon = new ImageIcon(getClass().getResource("res/folder.png")); 
	public ImageIcon hijoIcon = new ImageIcon(getClass().getResource("res/file.png")); 
	public ImageIcon hijoFolderIcon = new ImageIcon(getClass().getResource("res/folderS.png")); 

	public  FramePrinsipal() {
		
		setTitle("Backup");
		setBounds(600,300,366,328);
		vista.PanelCopia panelCopia = new PanelCopia();
		getContentPane().add(panelCopia);
		panelCopia.setLayout(null);
		
		panelCopia.setBackground(Color.WHITE);
	
		
		 CreateCopy = new JButton("Sincronizar");
	
		CreateCopy.setBounds(134, 76, 85, 23);
		panelCopia.add(CreateCopy);
		
		JLabel imageSource = new JLabel();
		imageSource.setBounds(71, 59, 46, 39);
		imagen = new ImageIcon(getClass().getResource("res/archive.png"));
		icono = new ImageIcon(imagen.getImage().getScaledInstance(imageSource.getWidth(), imageSource.getHeight(), Image.SCALE_DEFAULT));
		imageSource.setIcon(icono);
		panelCopia.add(imageSource);
		
		
		JLabel imageTarget = new JLabel();
		imageTarget.setBounds(241, 59, 44, 39);
		imagen = new ImageIcon(getClass().getResource("res/archiveT.png"));
		icono = new ImageIcon(imagen.getImage().getScaledInstance(imageTarget.getWidth(), imageTarget.getHeight(), Image.SCALE_DEFAULT));
		imageTarget.setIcon(icono);
		panelCopia.add(imageTarget);
		
	    lblSource = new JLabel("?",JLabel.CENTER);
		lblSource.setFont(new Font("Arial", Font.BOLD, 16));
		lblSource.setBounds(56, 38, 85, 14);
		panelCopia.add(lblSource);
		
	    lblTarget = new JLabel("?",JLabel.CENTER);
		lblTarget.setFont(new Font("Arial", Font.BOLD, 16));
		lblTarget.setBounds(220, 38, 85, 14);
		panelCopia.add(lblTarget);
		
		
		carpetaOrigen = new JButton("Archivo/directorio a sincronizar");
		carpetaOrigen.setBounds(0, 0, 180, 22);
		panelCopia.add(carpetaOrigen);
		
		carpetaDestino = new JButton("Carpeta destino");
		
		carpetaDestino.setBounds(174, 0, 186, 22);
		panelCopia.add(carpetaDestino);
		
		 scrollPane = new JScrollPane();
		 scrollPane.setViewportBorder(null);
		scrollPane.setBounds(0, 143, 360, 125);
		panelCopia.add(scrollPane);
		
		treeModel = new DefaultMutableTreeNode("Carpetas Copias");
	    
		 tree = new JTree();
		 tree.setBounds(0, 144, 358, 124);
		 panelCopia.add(tree);
		 
		 sicronizarD = new JLabel("Sincronizar a este directorio");
		 sicronizarD.setForeground(new Color(153,153,255));
		 sicronizarD.setFont(new Font("Arial", Font.PLAIN, 13));
		 sicronizarD.setBounds(21, 278, 143, 14);
//		 sicronizarD.setOpaque(true);
		 panelCopia.add(sicronizarD);
		 
		 cancelarSincr = new JLabel("Cancelar sicronizacion");
		 cancelarSincr.setForeground(new Color(153,153,255));
		 cancelarSincr.setFont(new Font("Arial", Font.PLAIN, 13));
		 cancelarSincr.setBounds(206, 279, 131, 14);
//		 cancelarSincr.setOpaque(true);
		 panelCopia.add(cancelarSincr);
		 

		setResizable(false);
	}
}

class PanelCopia extends JPanel {
	
	public PanelCopia() {
		
		setLayout(null);
	}
}


