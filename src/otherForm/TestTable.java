package otherForm;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class TestTable extends JPanel {
    public TestTable() {
        initializePanel();
    }

    private void initializePanel() {
        setLayout(new BorderLayout());

        JTable table = new JTable(new test());

        table.setCellSelectionEnabled(false);


        table.setFocusable(false);

        JScrollPane pane = new JScrollPane(table);
        add(pane, BorderLayout.CENTER);
    }

    public static void showFrame() {
        JPanel panel = new TestTable();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
           
            	TestTable.showFrame();
           
    }

    class test extends AbstractTableModel {
        // TableModel's column names
        private String[] columnNames = {
                 "W", "D" };

        // TableModel's data
        private Object[][] data = {
                { 5, 5},
                { 5, 5},
                { 5, 5},
                { 5, 5},
        };



		@Override
		public int getRowCount() {
			
			return data.length;
		}



		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return columnNames.length;
		}



		@Override
		public Object getValueAt(int arg0, int arg1) {
			
			return data.length*columnNames.length;
		}
    }
}