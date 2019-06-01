package vista;
import modelo.*;
import controlador.*;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
/**
 * @web http://www.jc-mouse.net/
 * @author Mouse
 */
public class MyTreeCellRenderer extends DefaultTreeCellRenderer{

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
    boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, selected,expanded, leaf, row, hasFocus);
        //altura de cada nodo
        tree.setRowHeight(26);

        setOpaque(true);     
        //color de texto
        setForeground( Color.black );
        if( selected )
            setForeground( Color.red );        
        //-- Asigna iconos
        // si value es la raiz
//        if ( tree.getModel().getRoot().equals( (DefaultMutableTreeNode) value ) ) {
//            setIcon( ((Padre)((DefaultMutableTreeNode) value).getUserObject()).getIcon() );
//        } 
         if( ((DefaultMutableTreeNode) value).getUserObject() instanceof Padre) 
        {
            setIcon( ((Padre)((DefaultMutableTreeNode) value).getUserObject()).getIcon() );            
        }
        else if( ((DefaultMutableTreeNode) value).getUserObject() instanceof Hijo) 
        {       
            setIcon( ((Hijo)((DefaultMutableTreeNode) value).getUserObject()).getIcon() );    
            
        }else if( ((DefaultMutableTreeNode) value).getUserObject() instanceof HijoFolder) {  
        	
            setIcon( ((HijoFolder)((DefaultMutableTreeNode) value).getUserObject()).getIcon() );                       
        }
        return this;
}

}//end:MyTreeCellRenderer