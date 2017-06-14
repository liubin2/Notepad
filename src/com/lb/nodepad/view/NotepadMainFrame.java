package com.lb.nodepad.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.PrintJob;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.undo.UndoManager;

import com.lb.nodepad.util.Clock;
import com.lb.nodepad.util.MQFontChooser;
import com.lb.nodepad.util.SystemParam;
import com.lb.nodepad.util.TestLine;

/**
 * 主程序
 * 
 * @author liubin
 *
 */
public class NotepadMainFrame extends JFrame implements ActionListener {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 8585210209467333480L;

	// 定义变量
	private JPanel contentPane;
	private JTextArea textArea;

	private JMenu itemFile;// 文件
	private JMenuItem itemNew; // 新建
	private JMenuItem itemOpen; // 打开
	private JMenuItem itemSave; // 保存
	private JMenuItem itemSaveAs; // 另存为
	private JSeparator separator; // 分割线
	private JMenuItem itemPage; // 页面设置
	private JMenuItem itemPrint; // 打印
	private JSeparator separator_1; // 分割线
	private JMenuItem itemExit; // 退出

	private JMenu itemEdit; // 编辑
	private JMenuItem itemUndo; // 撤销
	private JMenuItem itemRedo; // 恢复
	private JMenuItem itemCut; // 剪切
	private JMenuItem itemCopy; // 复制
	private JMenuItem itemPaste; // 粘贴
	private JMenuItem itemDelete; // 删除
	private JMenuItem itemFind; // 查找
	private JMenuItem itemFindNext; // 查找下一个
	private JMenuItem itemReplace; // 替换
	private JMenuItem itemTurnTo; // 转到
	private JMenuItem itemSelectAll; // 全选
	private JMenuItem itemTime; // 时间/日期

	private JMenu itFormat; // 格式
	private JCheckBoxMenuItem itemNextLine; // 自动换行
	private JMenuItem itemFont; // 字体大小
	private JMenuItem itemColor; // 背景颜色
	private JMenuItem itemFontColor; // 字体颜色

	private JMenu itemCheck; // 查看
	private JCheckBoxMenuItem itemStatement; // 状态栏
	private JMenu itemHelp; // 帮助
	private JMenuItem itemSearchForHelp; // 查看帮助
	private JMenuItem itemAboutNotepad; // 关于记事本

	private JPopupMenu popupMenu;// 右键弹出窗口
	private JMenuItem popM_Undo;// 撤销
	private JMenuItem popM_Redo;// 恢复
	private JMenuItem popM_Cut;// 剪切
	private JMenuItem popM_Copy;// 复制
	private JMenuItem popM_Paste;// 粘贴
	private JMenuItem popM_Delete;// 删除
	private JMenuItem popM_SelectAll;// 全选
	private JMenuItem popM_toLeft;// 从右到左的阅读顺序
//	private JMenuItem popM_showUnicode;// 显示Unicode控制字符
//	private JMenuItem popM_InsertUnicode;// 插入Unicode控制字符(I)
//	private JMenuItem popM_closeIMe;// 关闭IME
//	private JMenuItem popM_RestartSelect;// 汉字重选
	private JSeparator separator_2;
	private JSeparator separator_3;
	private JSeparator separator_4;
	private JSeparator separator_5;
	private JSeparator separator_6;
	private JSeparator separator_7;
	private JSeparator separator_8;

	private JScrollPane scrollPane;
	private JToolBar toolState;// 工具栏组件
	public static JLabel label1;
	private JLabel label2;
	private JLabel label3;
	public UndoManager undoMgr = new UndoManager();// 撤销管理器
	public Clipboard clipboard = new Clipboard("系统剪切板");// 剪贴板
	// 文本的行数和列数
	public int linenum = 1;
	public int columnnum = 1;

	public int length = 0;
	public int sum = 0;
	public int flag = 0; // 1：新建,2：修改过,3：保存过的
	public String currentFileName = null;// 当前文件名
	public PrintJob printJob = null;// 声明一个要打印的对象
	public Graphics graphics = null;// 要打印的对象
	private String currentPath = null;// 当前文件路径
	public JColorChooser jcc1 = null;// 背景颜色
	public Color color = Color.BLACK;
	public TestLine view ;

	GregorianCalendar c = new GregorianCalendar();
	public int hour = c.get(Calendar.HOUR_OF_DAY);
	public int min = c.get(Calendar.MINUTE);
	public int second = c.get(Calendar.SECOND);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NotepadMainFrame frame = new NotepadMainFrame();
					Image img = ImageIO.read(new File("src/com/lb/resource/131.png"));
					frame.setIconImage(img);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 
	 */
	public NotepadMainFrame() {
		try {
			// 设置记事本的显示样式为windows风格
			// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		setTitle("无标题"); // 设置标题
		setDefaultCloseOperation(EXIT_ON_CLOSE); // 设置关闭方式，系统调用自定义关闭方式
//		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); // 设置关闭方式，系统调用自定义关闭方式
		setBounds(300, 100, 800, 550); // 设置默认的窗体打开的位置，以及界面大小

		JMenuBar menuBar = new JMenuBar(); // 存放JMenu组件
		setJMenuBar(menuBar);
		// ------------文件-------------------
		itemFile = new JMenu("文件(F)");
		itemFile.setMnemonic('F');
		menuBar.add(itemFile);

		itemNew = new JMenuItem("新建(N)", 'N');
		itemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
		itemNew.addActionListener(this);
		itemFile.add(itemNew);

		itemOpen = new JMenuItem("打开(O)", 'O');
		itemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
		itemOpen.addActionListener(this);
		itemFile.add(itemOpen);

		itemSave = new JMenuItem("保存(S)");
		itemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
		itemSave.addActionListener(this);
		itemFile.add(itemSave);

		itemSaveAs = new JMenuItem("另存为(A)");
		itemSaveAs.addActionListener(this);
		itemFile.add(itemSaveAs);

		separator = new JSeparator();// 分割线，默认为水平
		itemFile.add(separator);

		itemPage = new JMenuItem("页面设置(U)", 'U');
		itemPage.addActionListener(this);
		itemFile.add(itemPage);

		itemPrint = new JMenuItem("打印(P)...", 'P');
		itemPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK));
		itemPrint.addActionListener(this);
		itemFile.add(itemPrint);

		separator_1 = new JSeparator();
		itemFile.add(separator_1);

		itemExit = new JMenuItem("退出(X)", 'X');
		itemExit.addActionListener(this);
		itemFile.add(itemExit);

		// ------------编辑-------------------
		itemEdit = new JMenu("编辑(E)");
		itemEdit.setMnemonic('E');
		menuBar.add(itemEdit);

		itemUndo = new JMenuItem("撤销(U)", 'U');
		itemUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK));
		itemUndo.addActionListener(this);
		itemEdit.add(itemUndo);

		itemRedo = new JMenuItem("恢复(R)");
		itemRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, Event.CTRL_MASK));
		itemRedo.addActionListener(this);
		itemEdit.add(itemRedo);

		separator_6 = new JSeparator();
		itemEdit.add(separator_6);
		
		itemCut = new JMenuItem("剪切(T)", 'T');
		itemCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK));
		itemCut.addActionListener(this);
		itemEdit.add(itemCut);

		itemCopy = new JMenuItem("复制(C)", 'C');
		itemCopy.addActionListener(this);
		itemCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK));
		itemEdit.add(itemCopy);

		itemPaste = new JMenuItem("粘贴(P)", 'P');
		itemPaste.addActionListener(this);
		itemPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK));
		itemEdit.add(itemPaste);

		itemDelete = new JMenuItem("删除(L)", 'L');
		itemDelete.addActionListener(this);
		itemDelete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
		itemEdit.add(itemDelete);

		separator_7 = new JSeparator();
		itemEdit.add(separator_7);

		itemFind = new JMenuItem("查找(F)", 'F');
		itemFind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, Event.CTRL_MASK));
		itemFind.addActionListener(this);
		itemEdit.add(itemFind);

		itemFindNext = new JMenuItem("查找下一个(N)", 'N');
		itemFindNext.setAccelerator(KeyStroke.getKeyStroke("F3"));
		itemFindNext.addActionListener(this);
		itemEdit.add(itemFindNext);

		itemReplace = new JMenuItem("替换(R)", 'R');
		itemReplace.addActionListener(this);
		itemReplace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, Event.CTRL_MASK));
		itemEdit.add(itemReplace);

		itemTurnTo = new JMenuItem("转到(G)", 'G');
		itemTurnTo.addActionListener(this);
		itemTurnTo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, Event.CTRL_MASK));
		itemEdit.add(itemTurnTo);

		itemSelectAll = new JMenuItem("全选(A)", 'A');
		itemSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK));
		itemSelectAll.addActionListener(this);
		itemEdit.add(itemSelectAll);

		separator_8 = new JSeparator();
		itemEdit.add(separator_8);

		itemTime = new JMenuItem("时间/日期(D)", 'D');
		itemTime.addActionListener(this);
		itemTime.setAccelerator(KeyStroke.getKeyStroke("F5"));
		itemEdit.add(itemTime);

		// ------------格式-------------------
		itFormat = new JMenu("格式(O)");
		itFormat.setMnemonic('O');
		menuBar.add(itFormat);

		itemNextLine = new JCheckBoxMenuItem("自动换行(W)");
		itemNextLine.addActionListener(this);
		itFormat.add(itemNextLine);

		itemFont = new JMenuItem("字体大小(F)...");
		itemFont.addActionListener(this);
		itFormat.add(itemFont);

		itemColor = new JMenuItem("背景颜色(C)...");
		itemColor.addActionListener(this);
		itFormat.add(itemColor);

		itemFontColor = new JMenuItem("字体颜色(I)...");
		itemFontColor.addActionListener(this);
		itFormat.add(itemFontColor);

		// ------------查看-------------------
		itemCheck = new JMenu("查看(V)");
		itemCheck.setMnemonic('V');
		menuBar.add(itemCheck);

		itemStatement = new JCheckBoxMenuItem("状态栏(S)");
		itemStatement.addActionListener(this);
		itemCheck.add(itemStatement);

		// ------------帮助-------------------
		itemHelp = new JMenu("帮助(H)");
		itemHelp.setMnemonic('H');
		menuBar.add(itemHelp);

		itemSearchForHelp = new JMenuItem("查看帮助(H)", 'H');
		itemSearchForHelp.addActionListener(this);
		itemHelp.add(itemSearchForHelp);

		itemAboutNotepad = new JMenuItem("关于记事本(A)", 'A');
		itemAboutNotepad.addActionListener(this);
		itemHelp.add(itemAboutNotepad);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));// 设置边框布局
		setContentPane(contentPane); // 把contentPane面板添加到jframe容器中

		textArea = new JTextArea(); // 多行文本域
		// VERTICAL垂直 HORIZONTAL水平 当文档内容大于显示区域出现滚动条
		scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		// 创建一个行标题视口
		view = new TestLine();
		scrollPane.setRowHeaderView(view);
		// 右键弹出窗体
		popupMenu = new JPopupMenu();
		addPopup(textArea, popupMenu);

		popM_Undo = new JMenuItem("撤销(U)");
		popM_Undo.addActionListener(this);
		popupMenu.add(popM_Undo);

		popM_Redo = new JMenuItem("恢复(R)");
		popM_Redo.addActionListener(this);
		popupMenu.add(popM_Redo);

		separator_2 = new JSeparator();
		popupMenu.add(separator_2);

		popM_Cut = new JMenuItem("剪切(T)");
		popM_Cut.addActionListener(this);
		popupMenu.add(popM_Cut);

		popM_Copy = new JMenuItem("复制(C)");
		popM_Copy.addActionListener(this);
		popupMenu.add(popM_Copy);

		popM_Paste = new JMenuItem("粘贴(P)");
		popM_Paste.addActionListener(this);
		popupMenu.add(popM_Paste);

		popM_Delete = new JMenuItem("删除(D)");
		popM_Delete.addActionListener(this);
		popupMenu.add(popM_Delete);

		separator_3 = new JSeparator();
		popupMenu.add(separator_3);

		popM_SelectAll = new JMenuItem("全选(A)");
		popM_SelectAll.addActionListener(this);
		popupMenu.add(popM_SelectAll);

		separator_4 = new JSeparator();
		popupMenu.add(separator_4);

		popM_toLeft = new JMenuItem("从右到左的阅读顺序(R)");
		popM_toLeft.addActionListener(this);
		popupMenu.add(popM_toLeft);
		/*
		popM_showUnicode = new JMenuItem("显示Unicode控制字符(S)");
		popM_showUnicode.addActionListener(this);
		popupMenu.add(popM_showUnicode);

		popM_InsertUnicode = new JMenuItem("插入Unicode控制字符(I)");
		popM_InsertUnicode.addActionListener(this);
		popupMenu.add(popM_InsertUnicode);
		
		separator_5 = new JSeparator();
		popupMenu.add(separator_5);

		popM_closeIMe = new JMenuItem("关闭IME(L)");
		popM_closeIMe.addActionListener(this);
		popupMenu.add(popM_closeIMe);

		popM_RestartSelect = new JMenuItem("汉字重选(R)");
		popM_RestartSelect.addActionListener(this);
		popupMenu.add(popM_RestartSelect);
*/
		// 添加到面板jframe容器中间
		contentPane.add(scrollPane, BorderLayout.CENTER);

		// 为文本域添加撤销事件监听
		textArea.getDocument().addUndoableEditListener(undoMgr);

		toolState = new JToolBar();// 工具栏组件
		toolState.setSize(textArea.getSize().width, 10);
		label1 = new JLabel("          当前系统时间：" + hour + ":" + min + ":" + second + "  ");
		toolState.add(label1);
		toolState.add(new JLabel("                               "));
		toolState.addSeparator();
		label2 = new JLabel("    第 " + linenum + " 行, 第 " + columnnum + " 列  ");
		toolState.add(label2);
		toolState.addSeparator();

		label3 = new JLabel("    一共 " + length + " 字  ");
		toolState.add(label3);
		// CaretListener用于侦听文本组件插入符的位置更改的侦听器；
		textArea.addCaretListener(new CaretListener() { // 记录行数和列数
			// caretUpdate当插入符的位置被更新时调用。
			public void caretUpdate(CaretEvent e) {
				JTextArea editArea = (JTextArea) e.getSource();
				try {
					int caretpos = editArea.getCaretPosition();// 获取文本插入位置
					linenum = editArea.getLineOfOffset(caretpos);
					columnnum = caretpos - textArea.getLineStartOffset(linenum);
					linenum += 1;
					label2.setText("    第 " + linenum + " 行, 第 " + columnnum + " 列  ");
					length = NotepadMainFrame.this.textArea.getText().toString().length();
					label3.setText("  一共 " + length + " 字  ");
				} catch (Exception ex) {
				}
			}
		});

		contentPane.add(toolState, BorderLayout.SOUTH);
		toolState.setVisible(false);// 设置工具栏初始状态不可见
		toolState.setFloatable(false);// 设置工具栏不能移动
		Clock clock = new Clock();// 获取时间
		clock.start();

		// 创建右键弹出菜单事件监听
		textArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 只响应鼠标右键单击事件
				if (e.getButton() == MouseEvent.BUTTON3) {
					popupMenu.show(e.getComponent(), e.getX(), e.getY());// 在鼠标位置显示弹出式菜单
				}
			}
		});
		isChanged();
		this.MainFrameWidowListener();
	}

	/**
	 * 判断文件是否有变化
	 */
	private void isChanged() {
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// 在这里我进行了对使用快捷键，但是没有输入字符却没有改变textArea中内容的判断
				Character c = e.getKeyChar();
				if (c != null && !textArea.getText().toString().equals("")) {
					flag = 2;
				}
			}
		});
	}

	/**
	 * 主窗体监听，判断文件是否改变，改变后保存，未改变直接关闭
	 */
	private void MainFrameWidowListener() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (flag == 0 || flag == 1) {
					textArea.addKeyListener(new KeyAdapter() {
						@Override
						public void keyTyped(KeyEvent e) {
							// 在这里我进行了对使用快捷键，但是没有输入字符却没有改变textArea中内容的判断
							Character c = e.getKeyChar();
							if (c != null && !textArea.getText().toString().equals("")) {
								flag = 2;
							}
						}
					});
				} else if (flag == 2 && currentPath == null) {
					// 这是弹出小窗口
					// 1、（刚启动记事本为0，刚新建文档为1）条件下修改后
					int result = JOptionPane.showConfirmDialog(NotepadMainFrame.this, "是否将更改保存到无标题?", "记事本",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
					if (result == JOptionPane.OK_OPTION) {
						NotepadMainFrame.this.saveAs();
					} else if (result == JOptionPane.NO_OPTION) {
						NotepadMainFrame.this.dispose();
						// 取消后，退出应用程序后的设置默认窗口关闭操作。
						NotepadMainFrame.this.setDefaultCloseOperation(EXIT_ON_CLOSE);
					}
				} else if (flag == 2 && currentPath != null) {
					// 这是弹出小窗口
					// 1、（刚启动记事本为0，刚新建文档为1）条件下修改后
					int result = JOptionPane.showConfirmDialog(NotepadMainFrame.this, "是否将更改保存到" + currentPath + "?",
							"记事本", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
					if (result == JOptionPane.OK_OPTION) {
						NotepadMainFrame.this.save();
					} else if (result == JOptionPane.NO_OPTION) {
						NotepadMainFrame.this.dispose();
						NotepadMainFrame.this.setDefaultCloseOperation(EXIT_ON_CLOSE);
					}
				} else {
					// 这是弹出小窗口
					int result = JOptionPane.showConfirmDialog(NotepadMainFrame.this, "确定关闭？", "系统提示",
							JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
					if (result == JOptionPane.OK_OPTION) {
						NotepadMainFrame.this.dispose();
						NotepadMainFrame.this.setDefaultCloseOperation(EXIT_ON_CLOSE);
					}
				}
			}
		});
	}

	/**
	 * 行为动作
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == itemOpen) { // 打开
			openFile();
		} else if (e.getSource() == itemSave) { // 保存
			// 如果该文件是打开的，就可以直接保存
			save();
		} else if (e.getSource() == itemSaveAs) { // 另存为
			saveAs();
		} else if (e.getSource() == itemNew) { // 新建
			newFile();
		} else if (e.getSource() == itemExit) { // 退出
			exit();
		} else if (e.getSource() == itemPage) { // 页面设置
			/// 页面设置，百度到的，不知道具体的用法
			PageFormat pf = new PageFormat();
			PrinterJob.getPrinterJob().pageDialog(pf);
		} else if (e.getSource() == itemPrint) { // 打印
			// 打印机
			print();
		} else if (e.getSource() == itemUndo || e.getSource() == popM_Undo) { // 撤销
			if (undoMgr.canUndo()) {
				undoMgr.undo();
			}
		} else if (e.getSource() == itemRedo || e.getSource() == popM_Redo) { // 恢复
			if (undoMgr.canRedo()) {
				undoMgr.redo();
			}
		} else if (e.getSource() == itemCut || e.getSource() == popM_Cut) { // 剪切
			cut();
		} else if (e.getSource() == itemCopy || e.getSource() == popM_Copy) { // 复制
			copy();
		} else if (e.getSource() == itemPaste || e.getSource() == popM_Paste) { // 粘贴
			paste();
		} else if (e.getSource() == itemDelete || e.getSource() == popM_Delete) { // 删除
			String tem = textArea.getText().toString();
			textArea.setText(tem.substring(0, textArea.getSelectionStart()));
		} else if (e.getSource() == itemFind) { // 查找
			mySearch();
		} else if (e.getSource() == itemFindNext) { // 查找下一个
			mySearch();
		} else if (e.getSource() == itemReplace) { // 替换
			mySearch();
		} else if (e.getSource() == itemTurnTo) { // 转到
			turnTo();
		} else if (e.getSource() == itemSelectAll || e.getSource() == popM_SelectAll) { // 选择全部
			textArea.selectAll();
		} else if (e.getSource() == popM_toLeft) { // 从右到左顺序阅读
			textArea.setAlignmentY(1);
			
		} else if (e.getSource() == itemTime) { // 时间/日期
			int dot = textArea.getCaret().getDot();
			String str = hour + ":" + min + " " + c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH) + 1) + "/"
					+ c.get(Calendar.DAY_OF_MONTH);
			textArea.insert(str, dot);
		} else if (e.getSource() == itemNextLine) { // 设置自动换行
			// 设置文本区的换行策略。如果设置为 true，则当行的长度大于所分配的宽度时，将换行，此属性默认为 false。
			if (itemNextLine.isSelected()) {
				textArea.setLineWrap(true);
				itemStatement.setEnabled(false);// 将状态栏设置为不可用
			} else {
				textArea.setLineWrap(false);
				itemStatement.setEnabled(true);// 将状态栏设置为可用
			}
		} else if (e.getSource() == itemFont) { // 设置字体大小
			// 构造字体选择器，参数字体为预设值
			MQFontChooser fontChooser = new MQFontChooser(textArea.getFont());
			fontChooser.showFontDialog(this);
			Font font = fontChooser.getSelectFont();
			// 将字体设置到JTextArea中
			textArea.setFont(font);
			view.setTestLineFont(font);
			
		} else if (e.getSource() == itemColor) { // 设置背景颜色
			jcc1 = new JColorChooser();
			JOptionPane.showMessageDialog(this, jcc1, "选择背景颜色颜色", -1);
			color = jcc1.getColor();
			textArea.setBackground(color);
		} else if (e.getSource() == itemFontColor) { // 设置字体颜色
			jcc1 = new JColorChooser();
			JOptionPane.showMessageDialog(this, jcc1, "选择字体颜色", -1);
			color = jcc1.getColor();
			// String string=textArea.getSelectedText();
			textArea.setForeground(color);
		} else if (e.getSource() == itemStatement) { // 设置状态
			if (itemStatement.isSelected()) {
				// scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
				toolState.setVisible(true);
			} else {
				// scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				toolState.setVisible(false);
			}
		} else if (e.getSource() == itemSearchForHelp) {
			JOptionPane.showMessageDialog(this, "未设置", "查看帮助", 1);
		} else if (e.getSource() == itemAboutNotepad) {
			JOptionPane.showMessageDialog(this, "记事本\n作者：刘斌\n时间：2017-3-10", "软件说明 ", 1);
		}
	}

	private void turnTo() {
		final JDialog gotoDialog = new JDialog(this, "转到下列行");
		JLabel gotoLabel = new JLabel("行数(L):");
		final JTextField linenum = new JTextField(5);
		linenum.setText("1");
		linenum.selectAll();

		JButton okButton = new JButton("确定");
		okButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int totalLine = textArea.getLineCount();
				int[] lineNumber = new int[totalLine + 1];
				String s = textArea.getText();
				int pos = 0, t = 0;

				while (true) {
					pos = s.indexOf('\12', pos);
					// System.out.println("引索pos:"+pos);
					if (pos == -1)
						break;
					lineNumber[t++] = pos++;
				}

				int gt = 1;
				try {
					gt = Integer.parseInt(linenum.getText());
				} catch (NumberFormatException efe) {
					JOptionPane.showMessageDialog(null, "请输入行数!", "提示", JOptionPane.WARNING_MESSAGE);
					linenum.requestFocus(true);
					return;
				}

				if (gt < 2 || gt >= totalLine) {
					if (gt < 2)
						textArea.setCaretPosition(0);
					else
						textArea.setCaretPosition(s.length());
				} else
					textArea.setCaretPosition(lineNumber[gt - 2] + 1);

				gotoDialog.dispose();
			}

		});

		JButton cancelButton = new JButton("取消");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gotoDialog.dispose();
			}
		});

		Container con = gotoDialog.getContentPane();
		con.setLayout(new FlowLayout());
		con.add(gotoLabel);
		con.add(linenum);
		con.add(okButton);
		con.add(cancelButton);

		gotoDialog.setSize(200, 100);
		gotoDialog.setResizable(false);
		gotoDialog.setLocation(300, 280);
		gotoDialog.setVisible(true);
	}

	/**
	 * 退出按钮，和窗口的红叉实现一样的功能
	 */
	private void exit() {
		if (flag == 2 && currentPath == null) {
			// 这是弹出小窗口
			// 1、（刚启动记事本为0，刚新建文档为1）条件下修改后
			int result = JOptionPane.showConfirmDialog(NotepadMainFrame.this, "是否将更改保存到无标题?", "记事本",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.OK_OPTION) {
				NotepadMainFrame.this.saveAs();
			} else if (result == JOptionPane.NO_OPTION) {
				NotepadMainFrame.this.dispose();
				NotepadMainFrame.this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			}
		} else if (flag == 2 && currentPath != null) {
			// 这是弹出小窗口
			// 1、（刚启动记事本为0，刚新建文档为1）条件下修改后
			int result = JOptionPane.showConfirmDialog(NotepadMainFrame.this, "是否将更改保存到" + currentPath + "?", "记事本",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.OK_OPTION) {
				NotepadMainFrame.this.save();
			} else if (result == JOptionPane.NO_OPTION) {
				NotepadMainFrame.this.dispose();
				NotepadMainFrame.this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			}
		} else {
			// 这是弹出小窗口
			int result = JOptionPane.showConfirmDialog(NotepadMainFrame.this, "确定关闭？", "系统提示",
					JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.OK_OPTION) {
				NotepadMainFrame.this.dispose();
				NotepadMainFrame.this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			}
		}
	}

	/**
	 * 新建文件，只有改过的和保存过的需要处理
	 */
	private void newFile() {
		if (flag == 0 || flag == 1) { // 刚启动记事本为0，刚新建文档为1
			return;
		} else if (flag == 2 && this.currentPath == null) { // 修改后
			// 1、（刚启动记事本为0，刚新建文档为1）条件下修改后
			int result = JOptionPane.showConfirmDialog(NotepadMainFrame.this, "是否将更改保存到无标题?", "记事本",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.OK_OPTION) {
				this.saveAs(); // 另存为
			} else if (result == JOptionPane.NO_OPTION) {
				this.textArea.setText("");
				this.setTitle("无标题");
				flag = 1;
			}
			return;
		} else if (flag == 2 && this.currentPath != null) {
			// 2、（保存的文件为3）条件下修改后
			int result = JOptionPane.showConfirmDialog(NotepadMainFrame.this, "是否将更改保存到" + this.currentPath + "?",
					"记事本", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.OK_OPTION) {
				this.save(); // 直接保存，有路径
			} else if (result == JOptionPane.NO_OPTION) {
				this.textArea.setText("");
				this.setTitle("无标题");
				flag = 1;
			}
		} else if (flag == 3) { // 保存的文件
			this.textArea.setText("");
			flag = 1;
			this.setTitle("无标题");
		}
	}

	/**
	 * 另存为
	 */
	private void saveAs() {
		// 打开保存框
		JFileChooser choose = new JFileChooser();
		// 选择文件
		int result = choose.showSaveDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			// 取得选择的文件[文件名是自己输入的]
			File file = choose.getSelectedFile();
			FileWriter fw = null;
			// 保存
			try {
				fw = new FileWriter(file);
				fw.write(textArea.getText());
				currentFileName = file.getName();
				currentPath = file.getAbsolutePath();
				// 如果比较少，需要写
				fw.flush();
				this.flag = 3;
				this.setTitle(currentPath);
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				try {
					if (fw != null)
						fw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * 保存
	 */
	private void save() {
		if (this.currentPath == null) {
			this.saveAs();
			if (this.currentPath == null) {
				return;
			}
		}
		FileWriter fw = null;
		// 保存
		try {
			fw = new FileWriter(new File(currentPath));
			fw.write(textArea.getText());
			// 如果比较少，需要写
			fw.flush();
			flag = 3;
			this.setTitle(this.currentPath);
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (fw != null)
					fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 打开文件
	 */
	private void openFile() {
		if (flag == 2 && this.currentPath == null) {
			// 1、（刚启动记事本为0，刚新建文档为1）条件下修改后
			int result = JOptionPane.showConfirmDialog(NotepadMainFrame.this, "是否将更改保存到无标题?", "记事本",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.OK_OPTION) {
				this.saveAs();
			}
		} else if (flag == 2 && this.currentPath != null) {
			// 2、（打开的文件2，保存的文件3）条件下修改
			int result = JOptionPane.showConfirmDialog(NotepadMainFrame.this, "是否将更改保存到" + this.currentPath + "?",
					"记事本", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.OK_OPTION) {
				this.save();
			}
		}
		// 打开文件选择框
		JFileChooser choose = new JFileChooser();
		// 选择文件
		int result = choose.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			// 取得选择的文件
			File file = choose.getSelectedFile();
			// 打开已存在的文件，提前将文件名存起来
			currentFileName = file.getName();
			// 存在文件全路径
			currentPath = file.getAbsolutePath();
			flag = 3;
			this.setTitle(this.currentPath);
			BufferedReader br = null;
			try {
				// 建立文件流[字符流]
				InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "GBK");
				br = new BufferedReader(isr);// 动态绑定
				// 读取内容
				StringBuffer sb = new StringBuffer();
				String line = null;
				while ((line = br.readLine()) != null) {
					sb.append(line + SystemParam.LINE_SEPARATOR);
				}
				// 显示在文本框[多框]
				textArea.setText(sb.toString());
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				try {
					if (br != null)
						br.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * 打印
	 */
	public void print() {
		try {
			printJob = getToolkit().getPrintJob(this, "ok", null);// 创建一个Printfjob
																	// 对象 p
			graphics = printJob.getGraphics();// p 获取一个用于打印的 Graphics 的对象
			graphics.translate(300, 300);// 改变组建的位置
			this.textArea.printAll(graphics);
			printJob.end();// 释放对象 g
		} catch (Exception a) {

		}
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	/**
	 * 剪切
	 */
	public void cut() {
		copy();
		// 标记开始位置
		int start = this.textArea.getSelectionStart();
		// 标记结束位置
		int end = this.textArea.getSelectionEnd();
		// 删除所选段
		this.textArea.replaceRange("", start, end);

	}

	/**
	 * 复制
	 */
	public void copy() {
		// 拖动选取文本
		String temp = this.textArea.getSelectedText();
		// 把获取的内容复制到连续字符器，这个类继承了剪贴板接口
		StringSelection text = new StringSelection(temp);
		// 把内容放在剪贴板
		this.clipboard.setContents(text, null);
	}

	/**
	 * 粘贴
	 */
	public void paste() {
		// Transferable接口，把剪贴板的内容转换成数据
		Transferable contents = this.clipboard.getContents(this);
		// DataFalvor类判断是否能把剪贴板的内容转换成所需数据类型
		DataFlavor flavor = DataFlavor.stringFlavor;
		// 如果可以转换
		if (contents.isDataFlavorSupported(flavor)) {
			String str;
			try {// 开始转换
				str = (String) contents.getTransferData(flavor);
				// 如果要粘贴时，鼠标已经选中了一些字符
				if (this.textArea.getSelectedText() != null) {
					// 定位被选中字符的开始位置
					int start = this.textArea.getSelectionStart();
					// 定位被选中字符的末尾位置
					int end = this.textArea.getSelectionEnd();
					// 把粘贴的内容替换成被选中的内容
					this.textArea.replaceRange(str, start, end);
				} else {
					// 获取鼠标所在TextArea的位置
					int mouse = this.textArea.getCaretPosition();
					// 在鼠标所在的位置粘贴内容
					this.textArea.insert(str, mouse);
				}
			} catch (UnsupportedFlavorException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		}
	}

	public void mySearch() {
		final JDialog findDialog = new JDialog(this, "查找与替换", true);
		Container con = findDialog.getContentPane();
		con.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel searchContentLabel = new JLabel("查找内容(N) :");
		JLabel replaceContentLabel = new JLabel("替换为(P)　 :");
		final JTextField findText = new JTextField(22);
		final JTextField replaceText = new JTextField(22);
		final JCheckBox matchcase = new JCheckBox("区分大小写");
		ButtonGroup bGroup = new ButtonGroup();
		final JRadioButton up = new JRadioButton("向上(U)");
		final JRadioButton down = new JRadioButton("向下(D)");
		down.setSelected(true);
		bGroup.add(up);
		bGroup.add(down);
		JButton searchNext = new JButton("查找下一个(F)");
		JButton replace = new JButton("替换(R)");
		final JButton replaceAll = new JButton("全部替换(A)");
		searchNext.setPreferredSize(new Dimension(110, 22));
		replace.setPreferredSize(new Dimension(110, 22));
		replaceAll.setPreferredSize(new Dimension(110, 22));

		// "替换"按钮的事件处理
		replace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (replaceText.getText().length() == 0 && textArea.getSelectedText() != null)
					textArea.replaceSelection("");
				if (replaceText.getText().length() > 0 && textArea.getSelectedText() != null)
					textArea.replaceSelection(replaceText.getText());
			}
		});

		// "替换全部"按钮的事件处理
		replaceAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				textArea.setCaretPosition(0); // 将光标放到编辑区开头
				int a = 0, b = 0, replaceCount = 0;

				if (findText.getText().length() == 0) {
					JOptionPane.showMessageDialog(findDialog, "请填写查找内容!", "提示", JOptionPane.WARNING_MESSAGE);
					findText.requestFocus(true);
					return;
				}
				while (a > -1) {

					int FindStartPos = textArea.getCaretPosition();
					String str1, str2, str3, str4, strA, strB;
					str1 = textArea.getText();
					str2 = str1.toLowerCase();
					str3 = findText.getText();
					str4 = str3.toLowerCase();

					if (matchcase.isSelected()) {
						strA = str1;
						strB = str3;
					} else {
						strA = str2;
						strB = str4;
					}

					if (up.isSelected()) {
						if (textArea.getSelectedText() == null) {
							a = strA.lastIndexOf(strB, FindStartPos - 1);
						} else {
							a = strA.lastIndexOf(strB, FindStartPos - findText.getText().length() - 1);
						}
					} else if (down.isSelected()) {
						if (textArea.getSelectedText() == null) {
							a = strA.indexOf(strB, FindStartPos);
						} else {
							a = strA.indexOf(strB, FindStartPos - findText.getText().length() + 1);
						}

					}

					if (a > -1) {
						if (up.isSelected()) {
							textArea.setCaretPosition(a);
							b = findText.getText().length();
							textArea.select(a, a + b);
						} else if (down.isSelected()) {
							textArea.setCaretPosition(a);
							b = findText.getText().length();
							textArea.select(a, a + b);
						}
					} else {
						if (replaceCount == 0) {
							JOptionPane.showMessageDialog(findDialog, "找不到您查找的内容!", "记事本",
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(findDialog, "成功替换" + replaceCount + "个匹配内容!", "替换成功",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
					if (replaceText.getText().length() == 0 && textArea.getSelectedText() != null) {
						textArea.replaceSelection("");
						replaceCount++;
					}
					if (replaceText.getText().length() > 0 && textArea.getSelectedText() != null) {
						textArea.replaceSelection(replaceText.getText());
						replaceCount++;
					}
				} // end while
			}
		}); /* "替换全部"按钮的事件处理结束 */

		// "查找下一个"按钮事件处理
		searchNext.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int a = 0, b = 0;
				int FindStartPos = textArea.getCaretPosition();
				String str1, str2, str3, str4, strA, strB;
				str1 = textArea.getText();
				str2 = str1.toLowerCase();
				str3 = findText.getText();
				str4 = str3.toLowerCase();
				// "区分大小写"的CheckBox被选中
				if (matchcase.isSelected()) {
					strA = str1;
					strB = str3;
				} else {
					strA = str2;
					strB = str4;
				}

				if (up.isSelected()) {
					if (textArea.getSelectedText() == null) {
						a = strA.lastIndexOf(strB, FindStartPos - 1);
					} else {
						a = strA.lastIndexOf(strB, FindStartPos - findText.getText().length() - 1);
					}
				} else if (down.isSelected()) {
					if (textArea.getSelectedText() == null) {
						a = strA.indexOf(strB, FindStartPos);
					} else {
						a = strA.indexOf(strB, FindStartPos - findText.getText().length() + 1);
					}

				}
				if (a > -1) {
					if (up.isSelected()) {
						textArea.setCaretPosition(a);
						b = findText.getText().length();
						textArea.select(a, a + b);
					} else if (down.isSelected()) {
						textArea.setCaretPosition(a);
						b = findText.getText().length();
						textArea.select(a, a + b);
					}
				} else {
					JOptionPane.showMessageDialog(null, "找不到您查找的内容!", "记事本", JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});/* "查找下一个"按钮事件处理结束 */

		// "取消"按钮及事件处理
		JButton cancel = new JButton("取消");
		cancel.setPreferredSize(new Dimension(110, 22));
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				findDialog.dispose();
			}
		});

		// 创建"查找与替换"对话框的界面
		JPanel bottomPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel topPanel = new JPanel();

		JPanel direction = new JPanel();
		direction.setBorder(BorderFactory.createTitledBorder("方向 "));
		direction.add(up);
		direction.add(down);
		direction.setPreferredSize(new Dimension(170, 60));
		JPanel replacePanel = new JPanel();
		replacePanel.setLayout(new GridLayout(2, 1));
		replacePanel.add(replace);
		replacePanel.add(replaceAll);

		topPanel.add(searchContentLabel);
		topPanel.add(findText);
		topPanel.add(searchNext);
		centerPanel.add(replaceContentLabel);
		centerPanel.add(replaceText);
		centerPanel.add(replacePanel);
		bottomPanel.add(matchcase);
		bottomPanel.add(direction);
		bottomPanel.add(cancel);

		con.add(topPanel);
		con.add(centerPanel);
		con.add(bottomPanel);

		// 设置"查找与替换"对话框的大小、可更改大小(否)、位置和可见性
		findDialog.setSize(410, 210);
		findDialog.setResizable(false);
		findDialog.setLocation(230, 280);
		findDialog.setVisible(true);
	}

}