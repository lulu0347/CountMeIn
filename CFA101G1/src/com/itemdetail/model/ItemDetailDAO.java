package com.itemdetail.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ItemDetailDAO implements ItemDetailDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G1");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO ItemDetail (itemno, itemsales, itemprice, orderNo) VALUES (?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT orderno, itemno, itemsales, itemprice FROM ItemDetail ORDER BY orderno";
	private static final String GET_ONE_STMT = "SELECT orderno, itemno, itemsales, itemprice FROM ItemDetail WHERE orderno = ? AND itemno = ?";
	private static final String DELETE = "DELETE FROM ItemDetail WHERE orderno = ? AND itemno = ?";
	private static final String UPDATE = "UPDATE ItemDetail SET itemSales = ? WHERE orderNo = ? AND itemNo = ?";
	private static final String GetAll = "SELECT * FROM ItemDetail";
	private static final String GetAllByOrderNo = "SELECT * FROM ItemDetail WHERE OrderNo = ?";

	@Override
	// (未實裝)
	public void addItemDetail(ItemDetailVO ItemDetailVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);
			// not auto_increment
			pstmt.setInt(1, ItemDetailVO.getOrderNo());
			pstmt.setInt(2, ItemDetailVO.getItemNo());
			pstmt.setInt(3, ItemDetailVO.getItemSales());
			pstmt.setInt(4, ItemDetailVO.getItemPrice());

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
	public void updateItemDetail(ItemDetailVO ItemDetailVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			// not auto_increment
			pstmt.setInt(1, ItemDetailVO.getItemSales());
			pstmt.setInt(2, ItemDetailVO.getOrderNo());
			pstmt.setInt(3, ItemDetailVO.getItemNo());

			pstmt.executeUpdate();

			// Handle any driver errors
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
	// (未實裝)
	public void deleteItemDetail(ItemDetailVO ItemDetailVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, ItemDetailVO.getOrderNo());
			pstmt.setInt(2, ItemDetailVO.getItemNo());

			pstmt.executeUpdate();

			// Handle any driver errors
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

	// ------------------以訂單編號查詢單一訂單明細--------------------------------
	@Override
	public List<ItemDetailVO> GetAllByOrderNo(Integer OrderNo) {

		List<ItemDetailVO> list = new ArrayList<ItemDetailVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ItemDetailVO itemDetailVO = null;
		try {

			con = ds.getConnection();
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

	// ------------------以訂單與商品編號查詢單一訂單明細--------------------------------
	@Override
	public ItemDetailVO findByPrimaryKey(Integer OrderNo, Integer ItemNo) {

		ItemDetailVO itemDetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
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
		return itemDetailVO;
	}

	// ------------------以訂單編號查詢所有訂單明細--------------------------------
	@Override
	public List<ItemDetailVO> getAll() {
		List<ItemDetailVO> list = new ArrayList<ItemDetailVO>();
		ItemDetailVO itemDetailVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GetAll);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// order_detailVO 也稱為 Domain objects
				itemDetailVO = new ItemDetailVO();
				itemDetailVO.setOrderNo(rs.getInt("orderNo"));
				itemDetailVO.setItemNo(rs.getInt("itemNo"));
				itemDetailVO.setItemSales(rs.getInt("itemSales"));
				itemDetailVO.setItemPrice(rs.getInt("itemPrice"));
				list.add(itemDetailVO); // Store the row in the list
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
	public void updateByShopping(ItemDetailVO itemDetailVO, Connection con) {
		PreparedStatement pstmt = null;

		try {

     		pstmt = con.prepareStatement(INSERT_STMT);
     		
     		pstmt.setInt(1, itemDetailVO.getItemNo());
     		pstmt.setInt(2, itemDetailVO.getItemSales());
     		pstmt.setInt(3, itemDetailVO.getItemPrice());
     		pstmt.setInt(4, itemDetailVO.getOrderNo());
//     		Statement stmt=	con.createStatement();
//     		stmt.executeUpdate("set auto_increment_offset=23001;");
//     		stmt.executeUpdate("set auto_increment_increment=1;");   //自增主鍵-遞增
			pstmt.executeUpdate();
		}catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back from OrderDetail");
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
