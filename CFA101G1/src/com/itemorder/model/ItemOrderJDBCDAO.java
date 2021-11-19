//package com.itemorder.model;
//
//import java.util.*;
//import java.util.Date;
//
//import com.itemdetail.model.ItemDetailDAO;
//import com.itemdetail.model.ItemDetailJDBCDAO;
//import com.itemdetail.model.ItemDetailVO;
//
//import java.sql.*;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//
//public class ItemOrderJDBCDAO implements ItemOrderDAO_interface {
//
//	String driver = "com.mysql.cj.jdbc.Driver";
//	String url = "jdbc:mysql://localhost:3306/CFA101G1?serverTimezone=Asia/Taipei";
//	String userid = "root";
//	String passwd = "123456";
//
//	private static final String INSERT_STMT = 
//		"INSERT INTO ItemOrder (memno, transrecno, tranTime, ordertotal, orderstate, receivername, receiveraddress, receiverphone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//	private static final String GET_ALL_STMT = 
//		"SELECT orderno, memno, transrecno, trantime, ordertotal, orderstate, receivername, receiveraddress, receiverphone FROM ItemOrder ORDER BY orderno";
//	private static final String GET_ONE_STMT = 
//		"SELECT orderno, memno, transrecno, trantime, ordertotal, orderstate, receivername, receiveraddress, receiverphone FROM ItemOrder WHERE orderno = ?";
//	private static final String DELETE = 
//		"DELETE FROM ItemOrder WHERE orderno = ?";
//	private static final String UPDATE = 
//		"UPDATE ItemOrder SET memno=?, transrecno=?, trantime=?, ordertotal=?, orderstate=?, receivername=?, receiveraddress=?, receiverphone=? WHERE orderno = ?";
//
//	@Override
//	public void addOrder(ItemOrderVO ItemOrderVO) {
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(INSERT_STMT);
//
//			pstmt.setInt(1, ItemOrderVO.getMemNo());
//			pstmt.setInt(2, ItemOrderVO.getTransRecNo());
//			pstmt.setInt(3, ItemOrderVO.getOrderTotal());
//			pstmt.setInt(4, ItemOrderVO.getOrderState());
//			pstmt.setString(5, ItemOrderVO.getReceiverName());
//			pstmt.setString(6, ItemOrderVO.getReceiverAddress());
//			pstmt.setString(7, ItemOrderVO.getReceiverPhone());
//
//			pstmt.executeUpdate();
//
//			// Handle any driver errors
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
//			// Handle any SQL errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. " + se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//	}
//
//	@Override
//	public void updateByOrderNo(ItemOrderVO ItemOrderVO) {
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(UPDATE);
//
//			pstmt.setInt(1, ItemOrderVO.getMemNo());
//			pstmt.setInt(2, ItemOrderVO.getTransRecNo());
//			pstmt.setTimestamp(3, ItemOrderVO.getTranTime());
//			pstmt.setInt(4, ItemOrderVO.getOrderTotal());
//			pstmt.setInt(5, ItemOrderVO.getOrderState());
//			pstmt.setString(6, ItemOrderVO.getReceiverName());
//			pstmt.setString(7, ItemOrderVO.getReceiverAddress());
//			pstmt.setString(8, ItemOrderVO.getReceiverPhone());
//			pstmt.setInt(9, ItemOrderVO.getOrderNo());
//
//			pstmt.executeUpdate();
//
//			// Handle any driver errors
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
//			// Handle any SQL errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. " + se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//	}
//
//	@Override
//	public void delete(Integer OrderNo) {
//		
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		
//		try {
//
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(DELETE);
//
//			pstmt.setInt(1,  OrderNo);
//
//			pstmt.executeUpdate();
//
//			// Handle any driver errors
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. "
//					+ e.getMessage());
//			// Handle any SQL errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//
//	}
//
//	@Override
//	public ItemOrderVO findByOrderNo(Integer OrderNo) {
//		
//		ItemOrderVO itemOrderVO = null;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(GET_ONE_STMT);
//
//			pstmt.setInt(1, OrderNo);
//
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				// itemOrderVO is Domain objects
//				itemOrderVO = new ItemOrderVO();
//				
//				itemOrderVO.setOrderNo(rs.getInt("orderno"));
//				itemOrderVO.setMemNo(rs.getInt("memno"));
//				itemOrderVO.setTransRecNo(rs.getInt("transrecno"));
//				itemOrderVO.setTranTime(rs.getTimestamp("trantime"));
//				itemOrderVO.setOrderTotal(rs.getInt("ordertotal"));
//				itemOrderVO.setOrderState(rs.getInt("orderstate"));
//				itemOrderVO.setReceiverName(rs.getString("receivername"));
//				itemOrderVO.setReceiverAddress(rs.getString("receiveraddress"));
//				itemOrderVO.setReceiverPhone(rs.getString("receiverphone"));
//			}
//
//			// Handle any driver errors
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. "
//					+ e.getMessage());
//			// Handle any SQL errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		return itemOrderVO;
//	}
//
//	@Override
//	public List<ItemOrderVO> getAll() {
//		
//		List<ItemOrderVO> list = new ArrayList<ItemOrderVO>();
//		ItemOrderVO itemOrderVO = null;
//		
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		try {
//
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(GET_ALL_STMT);
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				// itemOrderVO is Domain objects
//				itemOrderVO = new ItemOrderVO();
//				
//				itemOrderVO.setOrderNo(rs.getInt("orderno"));
//				itemOrderVO.setMemNo(rs.getInt("memno"));
//				itemOrderVO.setTransRecNo(rs.getInt("transrecno"));
//				itemOrderVO.setTranTime(rs.getTimestamp("trantime"));
//				itemOrderVO.setOrderTotal(rs.getInt("ordertotal"));
//				itemOrderVO.setOrderState(rs.getInt("orderstate"));
//				itemOrderVO.setReceiverName(rs.getString("receivername"));
//				itemOrderVO.setReceiverAddress(rs.getString("receiveraddress"));
//				itemOrderVO.setReceiverPhone(rs.getString("receiverphone"));
//				list.add(itemOrderVO);
//			
//			}
//
//			// Handle any driver errors
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. "
//					+ e.getMessage());
//			// Handle any SQL errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. "
//					+ se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (Exception e) {
//					e.printStackTrace(System.err);
//				}
//			}
//		}
//		return list;
//	}
//	
//	
//
//	@Override
//	//應該會缺少一個TranRecNo
//	public void insertWithItemDetails(ItemOrderVO ItemOrderVO, List<ItemDetailVO> list) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//			
//				Class.forName(driver);
//		
//			
//			con = DriverManager.getConnection(url, userid, passwd);
//			
//			// 1●設定於 pstm.executeUpdate()之前
//    		con.setAutoCommit(false);
//			
//			// 先給我新增訂單啦
//			String cols[] = {"ORDERNO"};
//			pstmt = con.prepareStatement(INSERT_STMT, cols);
//			pstmt.setInt(1, ItemOrderVO.getMemNo());
//			pstmt.setInt(2, ItemOrderVO.getTransRecNo());
//			pstmt.setTimestamp(3, ItemOrderVO.getTranTime());
//			pstmt.setInt(4, ItemOrderVO.getOrderTotal());
//			pstmt.setInt(5, ItemOrderVO.getOrderState());
//			pstmt.setString(6, ItemOrderVO.getReceiverName());
//			pstmt.setString(7, ItemOrderVO.getReceiverAddress() ) ;
//			pstmt.setString(8, ItemOrderVO.getReceiverPhone());
//			Statement stmt=	con.createStatement();
//			stmt.executeUpdate("set auto_increment_offset=24001;");    //自增主鍵-初始值
//			stmt.executeUpdate("set auto_increment_increment=1;"); //自增主鍵-遞增
//						pstmt.executeUpdate();
//			
//			String next_OrderNo = null;
//			ResultSet rs = pstmt.getGeneratedKeys(); // 取出綁定資料庫自增主鍵值
//			if (rs.next()) {
//				next_OrderNo = rs.getString(1);
//				System.out.println("自增主鍵值= " + next_OrderNo + "(剛新增成功的訂單編號)");
//			} else {
//				System.out.println("未取得自增主鍵值");
//			}
//			rs.close();
//			
//			// 再同時新增訂單明細啦
//			ItemDetailJDBCDAO dao = new ItemDetailJDBCDAO();
//			System.out.println("list.size()-A="+list.size());
//			for (ItemDetailVO itemDetailVO : list) {
//				itemDetailVO.setOrderNo(new Integer(next_OrderNo));
//				dao.updateByShopping(itemDetailVO, con);
//			}
//				
//			con.commit();
//			System.out.println("commit結束");
//			con.setAutoCommit(true);
//			System.out.println("list.size()-B="+list.size());
//			System.out.println("新增訂單編號" + next_OrderNo + "時,共有明細" + list.size()
//			+ "筆同時被新增");
//		}catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. "
//					+ e.getMessage());
//			// Handle any SQL errors
//		}catch (SQLException se) {
//			if (con != null) {
//				try {
//					// 3●設定於當有exception發生時之catch區塊內
//					System.err.print("Transaction is being ");
//					System.err.println("rolled back from itemOrder");
//					con.rollback();
//				} catch (SQLException excep) {
//					throw new RuntimeException("rollback error occured. " + excep.getMessage());
//				}
//			}
//			throw new RuntimeException("A database error occured. " + se.getMessage());
//			// Clean up JDBC resources
//		} finally {
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException se) {
//					se.printStackTrace(System.err);
//				}
//			}
//
//		}
//	}
//	
//	
//
//	@Override
//	public List<ItemOrderVO> GetOrderByMemNo(Integer MemNo) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public static void main(String[] args) {
//		ItemOrderJDBCDAO dao = new ItemOrderJDBCDAO();
//		
//		ItemOrderVO VO = new ItemOrderVO();
//		VO.setMemNo(11004);
//		VO.setTransRecNo(15021);
//		VO.setTranTime(java.sql.Timestamp.valueOf("2021-07-11 10:00:00"));
//		VO.setOrderTotal(30000);
//		VO.setOrderState(0);
//		VO.setReceiverName("嚕嚕");
//		VO.setReceiverAddress("嚕嚕國");
//		VO.setReceiverPhone("0912345678");
//		
//		
//		List<ItemDetailVO> testlist = new ArrayList<ItemDetailVO>();
//		ItemDetailVO idxx = new ItemDetailVO();
//		idxx.setItemNo(21001);
//		idxx.setItemPrice(15000);
//		idxx.setItemSales(1);
//		
//		ItemDetailVO idyy = new ItemDetailVO();
//		idyy.setItemNo(21002);
//		idyy.setItemPrice(7500);
//		idyy.setItemSales(2);
//		
//		testlist.add(idxx);
//		testlist.add(idyy);
//		
//		dao.insertWithItemDetails(VO, testlist);
//		
//	}
//
//}
