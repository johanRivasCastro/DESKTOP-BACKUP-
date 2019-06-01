package controlador;

import java.awt.Color;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Target;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import modelo.*;
import vista.FramePrinsipal;
import vista.MyTreeCellRenderer;

class ControlaCopia {

	private JFileChooser chooser;
	private String choosertitle;
	private File source, target;
	static ManageFileAppData manageFileAppData;
	private ArrayList<File> rutasTree, padres, desincronizar;
	FramePrinsipal prinsipal;
	private static ControlaCopia ctrl;
	private String selectNode = "source";
	private File cancelarNode = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		manageFileAppData = new ManageFileAppData();

		manageFileAppData.creaBatStartUp();
		ctrl = new ControlaCopia();
		ctrl.accionesButtons();

		CompruevaCopia c = new CompruevaCopia();
		Thread r = new Thread(c);
		r.start();
		try {
			manageFileAppData.eliminaDatosTarge();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		ctrl.ActualizaTree();
		ctrl.accionBotones2();
	}

	// ---------PONEMOS A LA ESCUCHA A LOS BUTTON CORESPONDIENTES A LAS COPIAS Y
	// SELECCIONAMOS LOS RESPECTIVOS ARCHIVOS CON filechooser
	public void accionesButtons() {

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (InstantiationException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} catch (IllegalAccessException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		} catch (UnsupportedLookAndFeelException e5) {
			// TODO Auto-generated catch block
			e5.printStackTrace();
		}

		prinsipal = new FramePrinsipal();

		prinsipal.setVisible(true);

		prinsipal.carpetaOrigen.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				System.out.println("4");
				chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos txt", "txt", "jpg", "jpeg",
						"docx", "html"); /* clase con la que filtramos el formato de los documentos */

				chooser.setFileFilter(filter);
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle(choosertitle);
				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				chooser.setAcceptAllFileFilterUsed(false);
				//
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					source = chooser.getSelectedFile();
					prinsipal.lblSource.setText(chooser.getSelectedFile().getName());
				}
			}

		});

		prinsipal.carpetaDestino.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos txt", "txt", "jpg",
						"jpeg"); /* clase con la que filtramos el formato de los documentos */

				chooser.setFileFilter(filter);
				chooser.setCurrentDirectory(new java.io.File("."));
				chooser.setDialogTitle(choosertitle);
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setAcceptAllFileFilterUsed(false);
				//
				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					target = chooser.getSelectedFile();
					prinsipal.lblTarget.setText(chooser.getSelectedFile().getName());

				}

			}

		});

		prinsipal.CreateCopy.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				System.out.println("5");
				try {
					if (source != null && target != null) {
						manageFileAppData.writeAppData(target);
						HacerCopia ctrlCopia = new HacerCopia();
						ctrlCopia.controlCopia(source, target);
						prinsipal.lblSource.setText("?");
						prinsipal.lblTarget.setText("?");
						// manageFileAppData.compruevaTipo(source,target);
						ActualizaTree();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		});

	}

	// -------------------------------------------------------------------------------------

	// -------ACTUALIZAMOS EL JTtree CADA VEZ QUE SE HAGA UNA NUEVA COPIA---------
	public void ActualizaTree() {
		prinsipal.tree = new JTree();
		prinsipal.tree.setCellRenderer(new MyTreeCellRenderer());
		System.out.println("6");
		padres = new ArrayList();
		desincronizar = new ArrayList();

		try {
			rutasTree = manageFileAppData.getValidTargets();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		prinsipal.treeModel.removeAllChildren();
		for (int i = 0; i < rutasTree.size(); i++) {
			System.out.println("7");
			if (rutasTree.get(i).exists()) {

				Padre padre = new Padre(rutasTree.get(i).getName());
				padre.setIcon(prinsipal.padreIcon);

				DefaultMutableTreeNode targets = new DefaultMutableTreeNode(padre);
				prinsipal.treeModel.add(targets);

				padres.add(new File(rutasTree.get(i).getAbsolutePath())); // agregamos cada padre al array list padres
				desincronizar.add(new File(rutasTree.get(i).getAbsolutePath()));

				String insideFiles[] = rutasTree.get(i).list();
				System.out.println(rutasTree.get(i).getAbsolutePath());
				for (int j = 0; j < insideFiles.length; j++) {

					File f = new File(rutasTree.get(i).getAbsolutePath(), insideFiles[j]);

					DefaultMutableTreeNode targetsChildren;
					if (!f.isDirectory()) {
						Hijo hijo = new Hijo(insideFiles[j]);
						hijo.setIcon(prinsipal.hijoIcon);
						targetsChildren = new DefaultMutableTreeNode(hijo);
					} else {
						HijoFolder hijoFolder = new HijoFolder(insideFiles[j]);
						hijoFolder.setIcon(prinsipal.hijoFolderIcon);
						targetsChildren = new DefaultMutableTreeNode(hijoFolder);
					}

					// confirmamos si cada hijo de target esta sincronizado antes de agregarlo a
					// JTree
					try {
						if (manageFileAppData.confirmaHijoTarget(f)) {
							desincronizar.add(new File(f.getAbsolutePath())); // agregamos cada hijo ya confirmado
							targets.add(targetsChildren);
						}
					} catch (IOException e) {
						e.getMessage();
					}
				}
			}
		}

		DefaultTreeModel modelo = new DefaultTreeModel(prinsipal.treeModel);
		prinsipal.tree.setModel(modelo);
		prinsipal.tree.setRowHeight(26);
		prinsipal.scrollPane.setViewportView(prinsipal.tree);
		getFocusJtree();

	}

	// ---------------------------------------------------------------------------------------

	// ----COMPROVAMOS SI EL USUARIO A PULSADO SOBRE UNA CARPETA UN DOCUMENTO HIJO
	// EN EL FILE

	public void getFocusJtree() {
		System.out.println("1");
		prinsipal.cancelarSincr.setForeground(new Color(153, 153, 255));
		prinsipal.cancelarSincr.setOpaque(true);
		prinsipal.sicronizarD.setForeground(new Color(153, 153, 255));
		prinsipal.sicronizarD.setOpaque(false);

		prinsipal.tree.addTreeSelectionListener(new TreeSelectionListener() {

			public void valueChanged(TreeSelectionEvent arg0) {

				selectNode = "source";
				// TODO Auto-generated method stub

				System.out.println("aaaaaaa");
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) prinsipal.tree.getSelectionPath()
						.getLastPathComponent();

				System.out.println("ddddd");
				String select = node.getUserObject().toString();

				System.out.println("????" + select);

				for (int i = 0; i < padres.size(); i++) {
					if (padres.get(i).getName().equals(select)) {
						target = padres.get(i).getAbsoluteFile();
						prinsipal.sicronizarD.setForeground(new Color(0, 0, 255));
						prinsipal.sicronizarD.setOpaque(true);
						prinsipal.cancelarSincr.setForeground(new Color(0, 0, 255));
						prinsipal.cancelarSincr.setOpaque(true);
						selectNode = "target";
						break;
					}
				}

				for (int i = 0; i < desincronizar.size(); i++) {
					if (desincronizar.get(i).getName().equals(select)) {
						cancelarNode = new File(desincronizar.get(i).getAbsolutePath());
						System.out.println(cancelarNode);
						if (!selectNode.equals("target")) {
							selectNode = "source";
							prinsipal.cancelarSincr.setForeground(new Color(0, 0, 255));
							prinsipal.cancelarSincr.setOpaque(true);
							prinsipal.sicronizarD.setForeground(new Color(153, 153, 255));
							prinsipal.sicronizarD.setOpaque(false);
						} else {
							selectNode = "target";

						}
						break;
					}
				}
			}

		});
	}

	public void accionBotones2() {
		prinsipal.sicronizarD.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {

				System.out.println("3");
				if (selectNode.equals("target")) {
					System.out.println("ppp-" + target);
					prinsipal.carpetaOrigen.doClick();
					prinsipal.CreateCopy.doClick();

				}
			}
		});

		prinsipal.cancelarSincr.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				if (selectNode.equals("source") || selectNode.equals("target")) {
					try {
						manageFileAppData.cancelaSincronizacion(cancelarNode);
						JOptionPane.showMessageDialog(null, "Sincronizacion Cancelada");
						ActualizaTree();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

	}

	// ---------------------------------------------------------------------------------------
}
