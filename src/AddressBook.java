import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class AddressBook {
	JFrame f = new JFrame("ͨѶ¼");
	JLabel l1 = new JLabel("��ţ�");
	JLabel l2 = new JLabel("������");
	JLabel l3 = new JLabel("�绰��");
	JLabel l4 = new JLabel("    ѧ�ţ�1502030203",JLabel.CENTER);
	JTextField tf1 = new JTextField(15);
	JTextField tf2 = new JTextField(15);
	JTextField tf3 = new JTextField(15);
	JButton b1 = new JButton("����");
	JButton b2 = new JButton("����");
	JButton b3 = new JButton("ɾ��");
	JPanel firstp = new JPanel();
	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JPanel p3 = new JPanel();
	JPanel mp = new JPanel();
	JPanel bp = new JPanel();
	private boolean insertCheck;
	private boolean userCheck;
	private boolean delCheck;
	
	public boolean Check(int function,String string)
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql=null;
		if(function==1)
			sql="select * from book where id='"+string+"' limit 1;";
		else if(function==2)
			sql="select * from book where name='"+string+"' order by id desc limit 1;";
		else if(function==3)
			sql="select * from book where phone_num='"+string+"' order by id desc limit 1;";
		else
			return false;
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance(); // newһ��Driver
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/address_book","root","root");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				tf1.setText(rs.getString("id"));
				tf2.setText(rs.getString("name"));
				tf3.setText(rs.getString("phone_num"));
				if(rs.getString("phone_num").length()>=1)
					return true;
				else
					return false;
			}
		 }
		catch(SQLException ex){
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		catch(InstantiationException e){
			e.printStackTrace(); 
		}
		catch(IllegalAccessException e){
			e.printStackTrace(); 
		}
		catch(ClassNotFoundException e){
			e.printStackTrace(); 
		}
		finally{
			try{
				if(conn != null){
					conn.close();
					conn = null;
				}
				if(stmt != null){
					stmt.close();
					stmt = null;
				}
				if(rs != null){
					rs.close();
					rs = null;
				}
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public boolean Insert(String name,String phone_num)
	{
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		String sql="insert into book (name,phone_num) values('"+name+"','"+phone_num+"');";
		String check="select * from book where phone_num='"+phone_num+"';";
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/address_book","root","root");
			st = con.createStatement();
			st.executeUpdate(sql);
			rs = (ResultSet)st.executeQuery(check);
			while(rs.next())
            {
				if(rs.getObject(1)!=null){
					Check(3, tf3.getText());
					return true;
				}
				else
					return false;
            }
			st.close();
			con.close();
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
			System.out.println("����������ʧ�ܣ�");
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean Deleted(int id)
	{
		Connection con=null;
		Statement st=null;
		ResultSet rs=null;
		String sql="delete from book where id='"+id+"';";
		String check="select * from book where id='"+id+"';";
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/address_book","root","root");
			st = con.createStatement();
			st.executeUpdate(sql);
			rs = (ResultSet)st.executeQuery(check);
			while(rs.next())
            {
				if(rs.getObject(1)!=null)
					return true;
				else
					return false;
            }
			st.close();
			con.close();
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
			System.out.println("����������ʧ�ܣ�");
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	public void init(){
		l4.setFont(new Font("����",1,20));
		l4.setForeground(Color.RED);
		firstp.setLayout(new FlowLayout(1,10,10));
		firstp.add(l1);
		firstp.add(tf1);
		p1.setLayout(new BorderLayout());
		p1.add(l4,BorderLayout.NORTH);
		p1.add(firstp,BorderLayout.SOUTH);
		p2.setLayout(new FlowLayout(1,10,10));
		p2.add(l2);
		p2.add(tf2);
		p3.setLayout(new FlowLayout(1,10,10));
		p3.add(l3);
		p3.add(tf3);
		bp.setLayout(new FlowLayout(1,30,20));
		mp.setLayout(new BorderLayout());
		mp.add(p1,BorderLayout.NORTH);
		mp.add(p2,BorderLayout.CENTER);
		mp.add(p3,BorderLayout.SOUTH);
		bp.add(b1);
		bp.add(b2);
		bp.add(b3);
		f.setLayout(new BorderLayout());
		f.add(mp,BorderLayout.NORTH);
		f.add(bp,BorderLayout.SOUTH);
		f.pack();
		f.setVisible(true);
		f.setResizable(false);
		f.setLocationRelativeTo(null);
		
		f.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent arg0){
				System.exit(0);
			}
		});
		
		b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				if(tf2.getText().length()<1 || tf3.getText().length()<1)
					JOptionPane.showMessageDialog(null, "�����͵绰����Ϊ�գ�", "Warning",JOptionPane.WARNING_MESSAGE);
				else if(tf3.getText().length()>=1&&tf3.getText().length()<11)
					JOptionPane.showMessageDialog(null, "�绰С��11λ��������Ч��", "Warning",JOptionPane.WARNING_MESSAGE);
				else if(tf3.getText().length()>12)
					JOptionPane.showMessageDialog(null, "�绰����12λ��������Ч��", "Warning",JOptionPane.WARNING_MESSAGE);
				else{
					insertCheck=Insert(tf2.getText(),tf3.getText());
					if(insertCheck==true)
						JOptionPane.showMessageDialog(null, "��ӳɹ���", "Warning",JOptionPane.WARNING_MESSAGE);
					else
						JOptionPane.showMessageDialog(null, "���ʧ�ܣ������ԣ�", "Warning",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		
		b2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(tf3.getText().length()>=1)
					userCheck=Check(3, tf3.getText());
				else if(tf2.getText().length()>=1)
					userCheck=Check(2, tf2.getText());
				else if(tf1.getText().length()>=1)
					userCheck=Check(1, tf1.getText());
				if(userCheck==false){
					tf1.setText("");
					tf2.setText("");
					tf3.setText("");
					JOptionPane.showMessageDialog(null, "û�в�ѯ�������", "Warning",JOptionPane.WARNING_MESSAGE);
				}
				else if(tf1.getText().length()<1&&tf2.getText().length()<1&&tf3.getText().length()<1)
					JOptionPane.showMessageDialog(null, "�������ѯ���ݣ�", "Warning",JOptionPane.WARNING_MESSAGE);
				else if(userCheck==true)
					JOptionPane.showMessageDialog(null, "��ѯ�������", "Warning",JOptionPane.WARNING_MESSAGE);
			}
		});
		
		b3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				delCheck=Deleted(Integer.parseInt(tf1.getText()));
				if(delCheck==false){
					tf1.setText("");
					tf2.setText("");
					tf3.setText("");
					JOptionPane.showMessageDialog(null, "ɾ���ɹ���", "Warning",JOptionPane.WARNING_MESSAGE);
				}
				else
					JOptionPane.showMessageDialog(null, "ɾ��ʧ�ܣ�", "Warning",JOptionPane.WARNING_MESSAGE);
			}
		});
	}
	
	public static void  main(String[] args){
		new AddressBook().init();
	}
}
