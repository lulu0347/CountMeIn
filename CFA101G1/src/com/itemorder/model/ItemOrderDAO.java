package com.itemorder.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.itemdetail.model.*;
import com.transRec.model.TransRecDAO_JDBC;
import com.transRec.model.TransRecDAO_interface;
import com.transRec.model.TransRecVO;


public class ItemOrderDAO implements ItemOrderDAO_interface {

	static TransRecDAO_interface transRecDAO = new TransRecDAO_JDBC();
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G1");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO ItemOrder (memno,trantime, ordertotal, orderstate, receivername, receiveraddress, receiverphone) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT orderno, memno, trantime, ordertotal, orderstate, receivername, receiveraddress, receiverphone FROM ItemOrder ORDER BY orderno";
	private static final String GET_ONE_STMT = "SELECT orderNo, memNo, tranTime, orderTotal, orderState, receiverName, receiverAddress, receiverPhone FROM ItemOrder WHERE orderNo = ?";
	private static final String DELETE_ORDER = "DELETE FROM ItemOrder WHERE orderno = ?";
	private static final String UPDATE = "UPDATE ItemOrder SET orderNo = ?, MemNo=?, TranTime=?, OrderTotal=?, OrderState=?, ReceiverName=?, ReceiverAddress=?, ReceiverPhone=? WHERE orderNo = ?";
	private static final String GET_BYMEMID_STMT = "SELECT orderNo, memNo, tranTime, orderTotal, orderState, receiverName, receiverAddress, receiverPhone FROM ItemOrder WHERE memno = ?";
	//(前台點選+後台新增)用於自增主鍵刪除訂單時使用
	private static final String DELETE_ITEMDETAIL = "DELETE FROM ItemDetail WHERE OrderNo = ?";
	//(後台)用於尋找未出貨的訂單
	private static final String GET_ORDER_BYSTATE = "SELECT * FROM itemOrder WHERE OrderState = 0";
	//(後台)用於尋找已出貨的訂單
	private static final String GET_ORDER_BYSTATE1 = "SELECT * FROM itemOrder WHERE OrderState = 1";
	//(後台)用於尋找已收貨的訂單
	private static final String GET_ORDER_BYSTATE2 = "SELECT * FROM itemOrder WHERE OrderState = 2";
	//(後台)用於尋找被退貨的訂單
	private static final String GET_ORDER_BYSTATE3 = "SELECT * FROM itemOrder WHERE OrderState = 3";
	//(後台)用於尋找被取消的訂單
	private static final String GET_ORDER_BYSTATE4 = "SELECT * FROM itemOrder WHERE OrderState = 4";
	//(前端點選)給會員尋找自己目前的訂單
	private static final String GET_BYMEMIDFORORDER_STMT = "SELECT orderNo, trantime, ordertotal, orderstate FROM itemorder WHERE memno = ?";	
	
	
	//(前端點選)給會員尋找自己目前的訂單	
	@Override
	public List<ItemOrderVO> gerOrderByMemNo(Integer MemNo) {
		List<ItemOrderVO> list = new ArrayList<ItemOrderVO>();
		ItemOrderVO itemOrderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BYMEMIDFORORDER_STMT);

			pstmt.setInt(1, MemNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				itemOrderVO = new ItemOrderVO();
				itemOrderVO.setOrderNo(rs.getInt("orderNo"));
				itemOrderVO.setTranTime(rs.getTimestamp("tranTime"));
				itemOrderVO.setOrderTotal(rs.getInt("orderTotal"));
				itemOrderVO.setOrderState(rs.getInt("orderState"));

				list.add(itemOrderVO);

			}
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public void addOrder(ItemOrderVO ItemOrderVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, ItemOrderVO.getMemNo());
			pstmt.setTimestamp(2, ItemOrderVO.getTranTime());
			pstmt.setInt(3, ItemOrderVO.getOrderTotal());
			pstmt.setInt(4, ItemOrderVO.getOrderState());
			pstmt.setString(5, ItemOrderVO.getReceiverName());
			pstmt.setString(6, ItemOrderVO.getReceiverAddress());
			pstmt.setString(7, ItemOrderVO.getReceiverPhone());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void updateByOrderNo(ItemOrderVO ItemOrderVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, ItemOrderVO.getOrderNo());
			pstmt.setInt(2, ItemOrderVO.getMemNo());
			pstmt.setTimestamp(3, ItemOrderVO.getTranTime());
			pstmt.setInt(4, ItemOrderVO.getOrderTotal());
			pstmt.setInt(5, ItemOrderVO.getOrderState());
			pstmt.setString(6, ItemOrderVO.getReceiverName());
			pstmt.setString(7, ItemOrderVO.getReceiverAddress());
			pstmt.setString(8, ItemOrderVO.getReceiverPhone());
			pstmt.setInt(9, ItemOrderVO.getOrderNo());

			pstmt.executeUpdate();

			// Handle any SQL errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void delete(Integer OrderNo) {
		// 計算被刪掉的商品明細個數
		int udpateCount_ItemDetail = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			con.setAutoCommit(false);
			
			//先刪除明細
			pstmt = con.prepareStatement(DELETE_ITEMDETAIL);
			pstmt.setInt(1, OrderNo);
			udpateCount_ItemDetail = pstmt.executeUpdate();
			
			//再刪除訂單
			pstmt = con.prepareStatement(DELETE_ORDER);
			pstmt.setInt(1, OrderNo);
			pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);
			System.out.println("form OrderDAO line151 :刪除訂單編號" + OrderNo + "時共有訂單明細" + udpateCount_ItemDetail + "筆被刪除");
			// Handle any driver errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back from OrderNo");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}

		}

	}

	@Override
	public ItemOrderVO findByOrderNo(Integer OrderNo) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ItemOrderVO itemOrderVO = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, OrderNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				itemOrderVO = new ItemOrderVO();
				itemOrderVO.setOrderNo(rs.getInt("orderNo"));
				itemOrderVO.setMemNo(rs.getInt("memNo"));
				itemOrderVO.setTranTime(rs.getTimestamp("tranTime"));
				itemOrderVO.setOrderTotal(rs.getInt("orderTotal"));
				itemOrderVO.setOrderState(rs.getInt("orderState"));
				itemOrderVO.setReceiverName(rs.getString("receiverName"));
				itemOrderVO.setReceiverAddress(rs.getString("receiverAddress"));
				itemOrderVO.setReceiverPhone(rs.getString("receiverPhone"));
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

		return itemOrderVO;

	}

	@Override
	public List<ItemOrderVO> GetOrderByMemNo(Integer MemNo) {

		List<ItemOrderVO> list = new ArrayList<ItemOrderVO>();
		ItemOrderVO itemOrderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BYMEMID_STMT);

			pstmt.setInt(1, MemNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				itemOrderVO = new ItemOrderVO();
				itemOrderVO.setOrderNo(rs.getInt("orderNo"));
				itemOrderVO.setMemNo(rs.getInt("memNo"));
				itemOrderVO.setTranTime(rs.getTimestamp("tranTime"));
				itemOrderVO.setOrderTotal(rs.getInt("orderTotal"));
				itemOrderVO.setOrderState(rs.getInt("orderState"));
				itemOrderVO.setReceiverName(rs.getString("receiverName"));
				itemOrderVO.setReceiverAddress(rs.getString("receiverAddress"));
				itemOrderVO.setReceiverPhone(rs.getString("receiverPhone"));
				list.add(itemOrderVO);

			}
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;

	}
	
	
	

	@Override
	public List<ItemOrderVO> findByOrderState2() {
		
		List<ItemOrderVO> list = new ArrayList<ItemOrderVO>();
		ItemOrderVO itemOrderVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ORDER_BYSTATE2);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// itemOrderVO is Domain objects
				itemOrderVO = new ItemOrderVO();

				itemOrderVO.setOrderNo(rs.getInt("orderno"));
				itemOrderVO.setMemNo(rs.getInt("memno"));
				itemOrderVO.setTranTime(rs.getTimestamp("trantime"));
				itemOrderVO.setOrderTotal(rs.getInt("ordertotal"));
				itemOrderVO.setOrderState(rs.getInt("orderstate"));
				itemOrderVO.setReceiverName(rs.getString("receivername"));
				itemOrderVO.setReceiverAddress(rs.getString("receiveraddress"));
				itemOrderVO.setReceiverPhone(rs.getString("receiverphone"));
				list.add(itemOrderVO);

			}
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public List<ItemOrderVO> findByOrderState3() {
		
		List<ItemOrderVO> list = new ArrayList<ItemOrderVO>();
		ItemOrderVO itemOrderVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ORDER_BYSTATE3);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// itemOrderVO is Domain objects
				itemOrderVO = new ItemOrderVO();

				itemOrderVO.setOrderNo(rs.getInt("orderno"));
				itemOrderVO.setMemNo(rs.getInt("memno"));
				itemOrderVO.setTranTime(rs.getTimestamp("trantime"));
				itemOrderVO.setOrderTotal(rs.getInt("ordertotal"));
				itemOrderVO.setOrderState(rs.getInt("orderstate"));
				itemOrderVO.setReceiverName(rs.getString("receivername"));
				itemOrderVO.setReceiverAddress(rs.getString("receiveraddress"));
				itemOrderVO.setReceiverPhone(rs.getString("receiverphone"));
				list.add(itemOrderVO);

			}
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public List<ItemOrderVO> findByOrderState4() {
		
		List<ItemOrderVO> list = new ArrayList<ItemOrderVO>();
		ItemOrderVO itemOrderVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ORDER_BYSTATE4);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// itemOrderVO is Domain objects
				itemOrderVO = new ItemOrderVO();

				itemOrderVO.setOrderNo(rs.getInt("orderno"));
				itemOrderVO.setMemNo(rs.getInt("memno"));
				itemOrderVO.setTranTime(rs.getTimestamp("trantime"));
				itemOrderVO.setOrderTotal(rs.getInt("ordertotal"));
				itemOrderVO.setOrderState(rs.getInt("orderstate"));
				itemOrderVO.setReceiverName(rs.getString("receivername"));
				itemOrderVO.setReceiverAddress(rs.getString("receiveraddress"));
				itemOrderVO.setReceiverPhone(rs.getString("receiverphone"));
				list.add(itemOrderVO);

			}
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public List<ItemOrderVO> findByOrderState1() {
		
		List<ItemOrderVO> list = new ArrayList<ItemOrderVO>();
		ItemOrderVO itemOrderVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ORDER_BYSTATE1);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// itemOrderVO is Domain objects
				itemOrderVO = new ItemOrderVO();

				itemOrderVO.setOrderNo(rs.getInt("orderno"));
				itemOrderVO.setMemNo(rs.getInt("memno"));
				itemOrderVO.setTranTime(rs.getTimestamp("trantime"));
				itemOrderVO.setOrderTotal(rs.getInt("ordertotal"));
				itemOrderVO.setOrderState(rs.getInt("orderstate"));
				itemOrderVO.setReceiverName(rs.getString("receivername"));
				itemOrderVO.setReceiverAddress(rs.getString("receiveraddress"));
				itemOrderVO.setReceiverPhone(rs.getString("receiverphone"));
				list.add(itemOrderVO);

			}
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
		
	}

	@Override
	public List<ItemOrderVO> findByOrderState() {
		
		List<ItemOrderVO> list = new ArrayList<ItemOrderVO>();
		ItemOrderVO itemOrderVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ORDER_BYSTATE);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// itemOrderVO is Domain objects
				itemOrderVO = new ItemOrderVO();

				itemOrderVO.setOrderNo(rs.getInt("orderno"));
				itemOrderVO.setMemNo(rs.getInt("memno"));
				itemOrderVO.setTranTime(rs.getTimestamp("trantime"));
				itemOrderVO.setOrderTotal(rs.getInt("ordertotal"));
				itemOrderVO.setOrderState(rs.getInt("orderstate"));
				itemOrderVO.setReceiverName(rs.getString("receivername"));
				itemOrderVO.setReceiverAddress(rs.getString("receiveraddress"));
				itemOrderVO.setReceiverPhone(rs.getString("receiverphone"));
				list.add(itemOrderVO);

			}
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public List<ItemOrderVO> getAll() {

		List<ItemOrderVO> list = new ArrayList<ItemOrderVO>();
		ItemOrderVO itemOrderVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// itemOrderVO is Domain objects
				itemOrderVO = new ItemOrderVO();

				itemOrderVO.setOrderNo(rs.getInt("orderno"));
				itemOrderVO.setMemNo(rs.getInt("memno"));
				itemOrderVO.setTranTime(rs.getTimestamp("trantime"));
				itemOrderVO.setOrderTotal(rs.getInt("ordertotal"));
				itemOrderVO.setOrderState(rs.getInt("orderstate"));
				itemOrderVO.setReceiverName(rs.getString("receivername"));
				itemOrderVO.setReceiverAddress(rs.getString("receiveraddress"));
				itemOrderVO.setReceiverPhone(rs.getString("receiverphone"));
				list.add(itemOrderVO);

			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	@Override
	public void insertWithItemDetails(ItemOrderVO ItemOrderVO, List<ItemDetailVO> list, TransRecVO transRecVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			con.setAutoCommit(false);
			// 先給我新增訂單啦
			String cols[] = { "orderNo" };
			pstmt = con.prepareStatement(INSERT_STMT, cols);
			pstmt.setInt(1, ItemOrderVO.getMemNo());
			pstmt.setTimestamp(2, ItemOrderVO.getTranTime());
			pstmt.setInt(3, ItemOrderVO.getOrderTotal());
			pstmt.setInt(4, ItemOrderVO.getOrderState());
			pstmt.setString(5, ItemOrderVO.getReceiverName());
			pstmt.setString(6, ItemOrderVO.getReceiverAddress());
			pstmt.setString(7, ItemOrderVO.getReceiverPhone());
			Statement stmt = con.createStatement();
			stmt.executeUpdate("set auto_increment_offset=24001;"); // 自增主鍵-初始值
			stmt.executeUpdate("set auto_increment_increment=1;"); // 自增主鍵-遞增
			pstmt.executeUpdate();

			String next_OrderNo = null;
			ResultSet rs = pstmt.getGeneratedKeys(); // 取出綁定資料庫自增主鍵值
			if (rs.next()) {
				next_OrderNo = rs.getString(1);
				System.out.println("自增主鍵值= " + next_OrderNo + "(剛新增成功的訂單編號)");
			} else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();

			// 再同時新增訂單明細啦
			ItemDetailDAO dao = new ItemDetailDAO();
			for (ItemDetailVO itemDetailVO : list) {
				itemDetailVO.setOrderNo(new Integer(next_OrderNo));
				dao.updateByShopping(itemDetailVO, con);
			}
			
			transRecVO.setOrderNo(new Integer(next_OrderNo));
			transRecDAO.addInTransaction(con, transRecVO);
			
			con.commit();
			con.setAutoCommit(true);
			
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back from itemOrder");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}

		}
	}
	

}
