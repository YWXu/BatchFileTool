package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.Principal;
import java.security.acl.LastOwnerException;
import java.security.acl.NotOwnerException;
import java.security.acl.Owner;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;

public class BatchFiles extends JFrame implements ActionListener {

	JButton button1;
	JButton button2;
	JTextField textField1;
	JTextField textField2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BatchFiles frame = new BatchFiles();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BatchFiles() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("�ļ�������");
		setSize(450, 200);
		setLocationRelativeTo(null);
		setResizable(false);
		setLayout(null);

		Container container = getContentPane();
		container.setBackground(Color.WHITE);

		JPanel panel1 = new JPanel();
		panel1.setBounds(0, 0, 450, 50);
		panel1.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		container.add(panel1);

		JLabel textLabel1 = new JLabel("Ŀ¼��");
		panel1.add(textLabel1);

		textField1 = new JTextField(20);
		panel1.add(textField1);

		button1 = new JButton("��");
		panel1.add(button1);
		button1.addActionListener(this);

		JPanel panel2 = new JPanel();
		panel2.setBounds(0, 50, 450, 50);
		panel2.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		container.add(panel2);

		JLabel textLabel2 = new JLabel("������");
		panel2.add(textLabel2);

		ArrayList<String> list = new ArrayList<String>();
		list.add("�ײ�����");
		list.add("β������");
		JComboBox<String> comboBox = new JComboBox<>(new ComboxBox(list));
		comboBox.setSelectedIndex(0);
		panel2.add(comboBox);

		textField2 = new JTextField(15);
		panel2.add(textField2);

		button2 = new JButton("ִ��");
		panel2.add(button2);
		button2.addActionListener(this);

		JTextArea textArea = new JTextArea();
//		textArea.setFocusable(false);
		textArea.setLineWrap(true);
		JScrollPane JSP = new JScrollPane(textArea);
		JSP.setBounds(0, 100, 450, 100);
		JSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		container.add(JSP);

		setVisible(true);
	}

	/*
	 * �ļ�������
	 */
	public static boolean renameFile(String file, String toFile) {

		File toBeRenamed = new File(file);
		// ���Ҫ���������ļ��Ƿ���ڣ��Ƿ����ļ�
		if (!toBeRenamed.exists() || toBeRenamed.isDirectory()) {

			System.out.println("�ļ�������: " + file);
			return false;
		}

		File newFile = new File(toFile);

		// �޸��ļ���
		if (toBeRenamed.renameTo(newFile)) {
			System.out.println("�������ɹ�.");
			return true;
		} else {
			System.out.println("������ʧ��");
			return false;
		}

	}

	/*
	 * �ļ������ļ������ļ�չʾ
	 */
	public static void getFileName(String dirPath, String text, int operationType) {
		String path = dirPath; // ·��
		File f = new File(path);

		File fa[] = f.listFiles();
		for (int i = 0; i < fa.length; i++) {
			File fs = fa[i];
			if (fs.isDirectory()) {
				System.out.println(fs.getName() + " [Ŀ¼]");
			} else {
				String nameString = fs.getName();
				System.out.println(fs.getName() + " [�ļ�]");

				StringBuilder sb = new StringBuilder(nameString);
				sb.insert(0, text);
				nameString = sb.toString();
				if (renameFile(path + fs.getName(), path + nameString)) {
					System.out.println(fs.getName() + "  ������Ϊ �� " + nameString);
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button1) {
			// �ļ�ѡ����
			JFileChooser chooser = new JFileChooser();
			FileSystemView fsv = FileSystemView.getFileSystemView(); // ע���ˣ�������Ҫ��һ��
			chooser.setCurrentDirectory(fsv.getHomeDirectory());
			// �����ļ�ѡ����ֻ��ѡ��0���ļ�����1���ļ��У�
			chooser.setFileSelectionMode(1);
			// ���ļ�����������ȡ���򷵻�1
			int status = chooser.showOpenDialog(null);
			if (status == 1) {
				return;
			} else {
				// ��ȡѡ����ѡ�񵽵��ļ�
				File file = chooser.getSelectedFile();
				// ��ȡ�ļ�����·����д�뵽�ı�����
				textField1.setText(file.getAbsolutePath());
			}
		} else if (e.getSource() == button2) {
//			new ShowDialog(BatchFiles.this, "���ļ�").setVisible(true);
			getFileName(textField1.getText() + "/", textField2.getText(), 0);
		}
	}

	class ShowDialog extends JDialog {
		public ShowDialog(JFrame jFrame, String hint) {
			super(jFrame, "��ʾ", true);
			setSize(100, 100);
			setLocationRelativeTo(null);
			Container container = getContentPane();
			container.add(new JLabel(hint));

		}
	}

	class ComboxBox extends AbstractListModel<String> implements ComboBoxModel<String> {

		private ArrayList<String> mList = new ArrayList<String>();
		private String selectItem = null;

		public ComboxBox(ArrayList<String> list) {
			super();
			mList = list;
		}

		@Override
		public String getElementAt(int arg0) {
			return mList.get(arg0);
		}

		@Override
		public int getSize() {
			return mList.size();
		}

		@Override
		public Object getSelectedItem() {
			return selectItem;
		}

		@Override
		public void setSelectedItem(Object anItem) {
			selectItem = (String) anItem;
		}
	}
}
