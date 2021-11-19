//package com.itemcollection.model;
//
//import java.util.*;
//import java.sql.*;
//
//public class ItemCollectionJDBCDAO implements ItemCollectionDAO_interface {
//
//	String driver = "com.mysql.cj.jdbc.Driver";
//	String url = "jdbc:mysql://localhost:3306/CFA101G1?serverTimezone=Asia/Taipei";
//	String userid = "root";
//	String passwd = "123456";
//
//	private static final String INSERT_STMT = 
//			"INSERT INTO ItemCollection (memno, itemno) VALUES (?, ?)";
//	private static final String GET_ALL_STMT = 
//			"SELECT memno, itemno FROM ItemCollection ORDER BY itemno";
//	private static final String GET_ONE_STMT = 
//			"SELECT memno, itemno FROM ItemCollection WHERE itemno = ?";
//	private static final String DELETE =
//			 "DELETE FROM ItemCollection where itemno = ? AND memNo = ?";
////	private static final String UPDATE = 
////			"UPDATE ItemCollection set memno = ? WHERE itemno = ?";
//
//	@Override
//	public void insert(ItemCollectionVO ItemCollectionVO) {
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
//			pstmt.setInt(1, ItemCollectionVO.getMemNo());
//			pstmt.setInt(2, ItemCollectionVO.getItemNo());
//
//			pstmt.executeUpdate();
//			// Handle any SQL errors
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
//
//	}
//
//	@Override
//	public void update(ItemCollectionVO ItemCollectionVO) {
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
//			pstmt.setInt(1, ItemCollectionVO.getMemNo());
//			pstmt.setInt(2, ItemCollectionVO.getItemNo());
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
//
//	}
//
//	@Override
//	public void delete(Integer ItemNo, Integer MemNo) {
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
//			pstmt.setInt(1, ItemNo);
//			pstmt.setInt(2, MemNo);
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
//
//	}
//
//	@Override
//	public ItemCollectionVO findByItemNo(Integer ItemNo) {
//		
//		ItemCollectionVO itemCollectionVO = null;
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
//			pstmt.setInt(1, ItemNo);
//
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				// itemCollectionVO is Domain objects
//				itemCollectionVO = new ItemCollectionVO();
//
//				itemCollectionVO.setMemNo(rs.getInt("memno"));
//				itemCollectionVO.setItemNo(rs.getInt("itemno"));
//			}
//			// Handle any driver errors
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
//			// Handle any SQL errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. " + se.getMessage());
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
//
//		return itemCollectionVO;
//	}
//
//	@Override
//	public List<ItemCollectionVO> getAll() {
//
//		List<ItemCollectionVO> list = new ArrayList<ItemCollectionVO>();
//		ItemCollectionVO itemCollectionVO = null;
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
//				// itemCollectionVO is Domain objects
//				itemCollectionVO = new ItemCollectionVO();
//
//				itemCollectionVO.setMemNo(rs.getInt("memno"));
//				itemCollectionVO.setItemNo(rs.getInt("itemno"));
//				list.add(itemCollectionVO);
//			}
//
//			// Handle any driver errors
//		} catch (ClassNotFoundException e) {
//			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
//			// Handle any SQL errors
//		} catch (SQLException se) {
//			throw new RuntimeException("A database error occured. " + se.getMessage());
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
//
//	}
//	
//	public static void main(String[] args) {
//		
//		ItemCollectionJDBCDAO dao = new ItemCollectionJDBCDAO();
//		
//		// 新增
////		ItemCollectionVO itemCollectionVO1 = new ItemCollectionVO();
////		itemCollectionVO1.setMemNo(11008);
////		itemCollectionVO1.setItemNo(21007);
////		dao.insert(itemCollectionVO1);
////		System.out.println("------------新增完成------------");
//		
//		
//		// 修改
//		ItemCollectionVO itemCollectionVO2 = new ItemCollectionVO();	
//		itemCollectionVO2.setItemNo(21005);
//		itemCollectionVO2.setMemNo(11010);
//		dao	.update(itemCollectionVO2);
//		System.out.println("------------修改完成------------");
//		
//		// 刪除
////		dao.delete(21001, 11004);
////		System.out.println("------------刪除完成------------");
//////		
//		// 查詢一個
//		ItemCollectionVO itemCollectionVO3 = dao.findByItemNo(21005);
//		System.out.print(itemCollectionVO3.getMemNo() + ",");
//		System.out.println(itemCollectionVO3.getItemNo());
//		System.out.println("-----------------------------");
//		
//		// 查詢全部
//		List<ItemCollectionVO> list = dao.getAll();
//		for(ItemCollectionVO aitemCollection : list) {
//			System.out.print(aitemCollection.getMemNo() + ",");
//			System.out.print(aitemCollection.getItemNo());
//			System.out.println();
//		}
//		
//		
//	}
//
//}
