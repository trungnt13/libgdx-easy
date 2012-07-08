package group.easy.JoglStarter;

import group.easy.List.EasyTestList;
import group.easy.tests.TestCore;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import okj.easy.core.Screen;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

class JoglStarter {
	static class TestList extends JPanel{
		public TestList(){
			setLayout(new BorderLayout());
			
			final JList list = new JList(EasyTestList.getNames());
			final JButton button = new JButton("Run Test");
			JScrollPane pane = new JScrollPane(list);
			
			DefaultListSelectionModel m = new DefaultListSelectionModel();
			m.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			m.setLeadAnchorNotificationEnabled(false);
			list.setSelectionModel(m);
			
			list.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() == 2)
						button.doClick();
				}
				
			});
			
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String testName = (String) list.getSelectedValue();
					Screen test = EasyTestList.newTest(testName);
					new LwjglApplication(new TestCore(test), testName, 800, 600, false);
				}
			});
			
			add(pane,BorderLayout.CENTER);
			add(button,BorderLayout.SOUTH);
		}
	}
	
	public static void main(String[] argv){
		JFrame frame = new JFrame("EasyGEngine Test Launcher");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new TestList());
		frame.pack();
		frame.setSize(frame.getWidth()+100,300);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
