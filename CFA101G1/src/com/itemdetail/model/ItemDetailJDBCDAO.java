package com.itemdetail.model;

import java.util.*;
import java.sql.*;

public class ItemDetailJDBCDAO implements ItemDetailDAO_interface {

	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/CFA101G1?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "123456";

	private static final String INSERT_STMT = 
			"INSERT INTO ItemDetail (itemno, itemsales, itemprice, orderNo) VALUES (?, ?, ?, ?)";
		private static final String GET_ALL_STMT = 
			"SELECT orderno, itemno, itemsales, itemprice FROM ItemDetail ORDER BY orderno";
		private static final String GET_ONE_STMT = 
			"SELECT orderno, itemno, itemsales, itemprice FROM ItemDetail WHERE orderno = ? AND itemno = ?";
		private static final String DELETE = 
			"DELETE FROM ItemDetail WHERE orderno = ? AND itemno = ?";
		private static final String UPDATE = 
			"UPDATE ItemDetail SET itemSales = ? WHERE orderNo = ? AND itemNo = ?";
		private static final String GetAll =
			"SELECT * FROM ItemDetail";
		private static final String GetAllByOrderNo =
			"SELECT * FROM ItemDetail WHERE OrderNo = ?";

	@Override
	public void addItemDetail(ItemDetailVO ItemDetailVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			// not auto_increment
			pstmt.setInt(1, ItemDetailVO.getOrderNo());
			pstmt.setInt(2, ItemDetailVO.getItemNo());
			pstmt.setInt(3, ItemDetailVO.getItemSales());
			pstmt.setInt(4, ItemDetailVO.getItemPrice());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	public void updateItemDetail(ItemDetailVO ItemDetailVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			// not auto_increment
			pstmt.setInt(1, ItemDetailVO.getItemSales());
			pstmt.setInt(2, ItemDetailVO.getItemPrice());
			pstmt.setInt(3, ItemDetailVO.getOrderNo());
			pstmt.setInt(4, ItemDetailVO.getItemNo());
			
			pstmt.executeUpdate();
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	public void deleteItemDetail(ItemDetailVO ItemDetailVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, ItemDetailVO.getOrderNo());
			pstmt.setInt(2, ItemDetailVO.getItemNo());

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
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
	public List<ItemDetailVO> GetAllByOrderNo(Integer OrderNo) {
		List<ItemDetailVO> list = new ArrayList<ItemDetailVO>();
		ItemDetailVO itemDetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GetAllByOrderNo);

			pstmt.setInt(1, OrderNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// itemDetailVO is Domain objects
				itemDetailVO = new ItemDetailVO();

				itemDetailVO.setOrderNo(rs.getInt("orderno"));
				itemDetailVO.setItemNo(rs.getInt("itemno"));
				itemDetailVO.setItemSales(rs.getInt("itemsales"));
				itemDetailVO.setItemPrice(rs.getInt("itemprice"));
				list.add(itemDetailVO);
			}
			// Handle any driver errors
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
	public List<ItemDetailVO> getAll() {

		List<ItemDetailVO> list = new ArrayList<ItemDetailVO>();
		ItemDetailVO itemDetailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// itemDetailVO is Domain objects
				itemDetailVO = new ItemDetailVO();

				itemDetailVO.setOrderNo(rs.getInt("orderno"));
				itemDetailVO.setItemNo(rs.getInt("itemno"));
				itemDetailVO.setItemSales(rs.getInt("itemsales"));
				itemDetailVO.setItemPrice(rs.getInt("itemprice"));
				list.add(itemDetailVO);
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
			// Handle any SQL errors
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
	public ItemDetailVO findByPrimaryKey(Integer OrderNo, Integer ItemNo) {
		
		ItemDetailVO itemDetailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

//			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, OrderNo);
			pstmt.setInt(2, ItemNo);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				itemDetailVO = new ItemDetailVO();
				
				itemDetailVO.setOrderNo(rs.getInt("orderNo"));
				itemDetailVO.setItemNo(rs.getInt("itemNo"));
				itemDetailVO.setItemSales(rs.getInt("itemSales"));
				itemDetailVO.setItemPrice(rs.getInt("itemPrice"));
				
			}	
	}catch (SQLException se) {
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
		return itemDetailVO;
	
	}
	
	

	@Override
	public void updateByShopping(ItemDetailVO itemDetailVO, Connection con) {
		PreparedStatement pstmt = null;
		
		try {

     		pstmt = con.prepareStatement(INSERT_STMT);
     		
     		pstmt.setInt(1, itemDetailVO.getItemNo());
     		pstmt.setInt(2, itemDetailVO.getItemSales());
     		pstmt.setInt(3, itemDetailVO.getItemPrice());
     		pstmt.setInt(4, itemDetailVO.getOrderNo());
     		
//     		Statement stmt=	con.createStatement();
//     		stmt.executeUpdate("set auto_increment_offset=24001;");
//     		stmt.executeUpdate("set auto_increment_increment=1;");   //自增主鍵-遞增
			pstmt.executeUpdate();
		}catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-emp");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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

	public static void main(String[] args) {
		
		ItemDetailJDBCDAO dao = new ItemDetailJDBCDAO();
		
		// 新增
//		ItemDetailVO itemDetailVO1 = new ItemDetailVO();
//		itemDetailVO1.setOrderNo(24005);
//		itemDetailVO1.setItemNo(21005);
//		itemDetailVO1.setItemPrice(2000);
//		itemDetailVO1.setItemSales(1);
//		dao.insert(itemDetailVO1);
//		System.out.println("------------新增完成------------");
		
		// 修改
//		ItemDetailVO itemDetailVO2 = new ItemDetailVO();
//		itemDetailVO2.setItemSales(2);
//		itemDetailVO2.setItemPrice(3000);
//		itemDetailVO2.setOrderNo(24005);
//		itemDetailVO2.setItemNo(21005);
//		dao.update(itemDetailVO2);
//		System.out.println("------------修改完成------------");
		
		// 刪除
//		ItemDetailVO itemDetailVO3 = new ItemDetailVO();
//		itemDetailVO3.setOrderNo(24005);
//		itemDetailVO3.setItemNo(21005);
//		dao.delete(itemDetailVO3);
//		System.out.println("------------刪除完成------------");
		
		// 查詢一個
//		ItemDetailVO itemDetailVO4 = dao.findByOrderNo(24004);
//		System.out.print(itemDetailVO4.getOrderNo() + ",");
//		System.out.print(itemDetailVO4.getItemNo() + ",");
//		System.out.print(itemDetailVO4.getItemSales() + ",");
//		System.out.println(itemDetailVO4.getItemPrice());
//		System.out.println("--------------------------");
		
		// 查詢全部
		List<ItemDetailVO> list = dao.getAll();
				for(ItemDetailVO aitemDetail : list) {
					System.out.print(aitemDetail.getOrderNo() + ",");
					System.out.print(aitemDetail.getItemNo() + ",");
					System.out.print(aitemDetail.getItemSales() + ",");
					System.out.print(aitemDetail.getItemPrice());
					System.out.println();
				}
	}
	
}
