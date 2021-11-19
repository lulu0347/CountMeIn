//package com.item.model;
//
//import java.sql.*;
//import java.util.Date;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//public class ItemJDBCDAO implements ItemDAO_interface {
//
//	String driver = "com.mysql.cj.jdbc.Driver";
//	String url = "jdbc:mysql://localhost:3306/CFA101G1?serverTimezone=Asia/Taipei";
//	String userid = "cfa101g1";
//	String passwd = "cfa101g1";
//
//	private static final String INSERT_STMT = "INSERT INTO Item (KindNo, ItemName, ItemPrice, ItemState, LaunchedTime, WarrantyDate, ItemProdDescription) VALUES (?, ?, ?, ?, ?, ?, ?)";
//	private static final String GET_ALL_STMT = "SELECT ItemNo, KindNo, ItemName, ItemPrice, ItemState, SoldTime, LaunchedTime, WarrantyDate, ItemProdDescription FROM Item";
//	private static final String GET_ONE_STMT = "SELECT ItemNo, KindNo, ItemName, ItemPrice, ItemState, SoldTime, LaunchedTime, WarrantyDate, ItemProdDescription FROM Item WHERE ItemNo = ?";
//	private static final String UPDATE = "UPDATE Item SET KindNo=?, ItemName=?, ItemPrice=?, ItemState=?, SoldTime=?, LaunchedTime=?, WarrantyDate=?, ItemProdDescription=? WHERE ItemNo=?";
//	private static final String DELETE_ITEMPIC = "DELETE FROM ItemPic WHERE ItemNo = ?";
//	private static final String DELETE_ITEM = "DELETE FROM Item WHERE ItemNo = ?";
//	private static final String DELETE_ITEMDETAIL = "DELETE FROM ItemDetail WHERE ItemNo = ?";
//	private static final String DELETE_ITEMCOLLECTION = "DELETE FROM ItemCollection WHERE ItemNo = ?";
//
//	@Override
//	public void insert(ItemVO ItemVO) {
//		
//		
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
//			pstmt.setInt(1, ItemVO.getKindNo());
//			pstmt.setString(2, ItemVO.getItemName());
//			pstmt.setInt(3, ItemVO.getItemPrice());
//			pstmt.setInt(4, ItemVO.getItemState());
//			pstmt.setTimestamp(5, ItemVO.getLaunchedTime());
//			pstmt.setDouble(6, ItemVO.getWarrantyDate());
//			pstmt.setString(7, ItemVO.getItemProdDescription());
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
//	public void update(ItemVO ItemVO) {
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//--------------------------改道這
//		try {
//
//			Class.forName(driver);
//			con = DriverManager.getConnection(url, userid, passwd);
//			pstmt = con.prepareStatement(UPDATE);
//			
//			pstmt.setInt(1, ItemVO.getKindNo());
//			pstmt.setString(2, ItemVO.getItemName());
//			pstmt.setInt(3, ItemVO.getItemPrice());
//			pstmt.setInt(4, ItemVO.getItemState());
//			pstmt.setTimestamp(5, ItemVO.getSoldTime());
//			pstmt.setTimestamp(6, ItemVO.getLaunchedTime());
//			pstmt.setDouble(7, ItemVO.getWarrantyDate());
//			pstmt.setString(8, ItemVO.getItemProdDescription());
//			pstmt.setInt(9,ItemVO.getItemNo());
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
//	public void delete(Integer ItemNo) {
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
//	public ItemVO findByItemNo(Integer ItemNo) {
//
//		ItemVO itemVO = null;
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
//				// itemVo 也稱為 Domain objects
//				itemVO = new ItemVO();
//
//				itemVO.setItemNo(rs.getInt("itemNo"));
//				itemVO.setKindNo(rs.getInt("kindNo"));
//				itemVO.setItemName(rs.getString("itemName"));
//				itemVO.setItemPrice(rs.getInt("itemPrice"));
//				itemVO.setItemState(rs.getInt("itemState"));
//				itemVO.setSoldTime(rs.getTimestamp("soldTime"));
//				itemVO.setLaunchedTime(rs.getTimestamp("launchedTime"));
//				itemVO.setWarrantyDate(rs.getDouble("warrantyDate"));
//				itemVO.setItemProdDescription(rs.getString("itemProdDescription"));
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
//		return itemVO;
//
//	}
//
//	@Override
//	public List<ItemVO> getAll() {
//		List<ItemVO> list = new ArrayList<ItemVO>();
//		ItemVO itemVO = null;
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
//				// itemVO is Domain objects
//				itemVO = new ItemVO();
//
//				itemVO.setItemNo(rs.getInt("ItemNo"));
//				itemVO.setKindNo(rs.getInt("KindNo"));
//				itemVO.setItemName(rs.getString("ItemName"));
//				itemVO.setItemPrice(rs.getInt("ItemPrice"));
//				itemVO.setItemState(rs.getInt("ItemState"));
//				itemVO.setSoldTime(rs.getTimestamp("SoldTime"));
//				itemVO.setLaunchedTime(rs.getTimestamp("LaunchedTime"));
//				itemVO.setWarrantyDate(rs.getDouble("WarrantyDate"));
//				itemVO.setItemProdDescription(rs.getString("ItemProdDescription"));
//				list.add(itemVO);
//
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
//
//		return list;
//	}
//	
//	
//	public static void main(String[] args) {
//		
//
//		
//
//		ItemJDBCDAO dao = new ItemJDBCDAO();
////
////		//新增
////		ItemVO itemVO1 = new ItemVO();
////		itemVO1.setKindNo(1);
////		itemVO1.setItemName("測試用名稱fromJDBC");
////		itemVO1.setItemPrice(10000);
////		itemVO1.setItemState(2);
////		
//////		
////		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////		Date laTime = null;
////		try {
////			laTime = sdf.parse("2021-08-10 18:30:00");
////		} catch (ParseException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////		long time = laTime.getTime();
////		Timestamp launchedTime = new Timestamp(time);
////		itemVO1.setLaunchedTime(launchedTime);
////		itemVO1.setWarrantyDate(new Double(1.0));
////		itemVO1.setItemProdDescription("測試用描述");
////		dao.insert(itemVO1);
////		System.out.println("------------新增完成------------");
////	
//		// 修改
////		ItemVO itemVO2 = new ItemVO();
////		itemVO2.setKindNo(2);
////		itemVO2.setItemName("測試用名稱fromJDBC(變更一次)");
////		itemVO2.setItemPrice(15000);
////		itemVO2.setItemState(2);
////		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////		Date soTime = null;
////		try {
////			soTime = sdf2.parse("2021-06-10 12:00:30");
////		} catch (ParseException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////		long sotime = soTime.getTime();
////		Timestamp soldTime = new Timestamp(sotime);
////		itemVO2.setSoldTime(soldTime);
////		
////		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////		Date laTime = null;
////		try {
////			laTime = sdf.parse("2021-08-10 18:30:00");
////		} catch (ParseException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////		long time = laTime.getTime();
////		Timestamp launchedTime = new Timestamp(time);
////		itemVO2.setLaunchedTime(launchedTime);
////		itemVO2.setWarrantyDate(new Double(1.5));
////		itemVO2.setItemProdDescription("測試用描述(變更一次)");
////		itemVO2.setItemNo(21008);
////		dao.update(itemVO2);
////		System.out.println("------------修改完成------------");
//		
//		//刪除
////		dao.delete(21008);
////		System.out.println("------------刪除完成------------");
//		
//		//查詢一個
//		ItemVO itemVO3 = dao.findByItemNo(21001);
//		System.out.print(itemVO3.getItemNo() + ",");
//		System.out.print(itemVO3.getKindNo() + ",");
//		System.out.print(itemVO3.getItemName() + ",");
//		System.out.print(itemVO3.getItemPrice() + ",");
//		System.out.print(itemVO3.getItemState() + ",");
//		System.out.print(itemVO3.getSoldTime() + ",");
//		System.out.print(itemVO3.getLaunchedTime() + ",");
//		System.out.print(itemVO3.getWarrantyDate() + ",");
//		System.out.println(itemVO3.getItemProdDescription());
//		System.out.println("-----------------------------------");
//		
//		
//		// 查詢全部
//		List<ItemVO> list = dao.getAll();
//		for(ItemVO aitem : list) {
//			System.out.print(aitem.getItemNo() + ",");
//			System.out.print(aitem.getKindNo() + ",");
//			System.out.print(aitem.getItemName() + ",");
//			System.out.print(aitem.getItemPrice() + ",");
//			System.out.print(aitem.getItemState() + ",");
//			System.out.print(aitem.getSoldTime() + ",");
//			System.out.print(aitem.getLaunchedTime() + ",");
//			System.out.print(aitem.getWarrantyDate() + ",");
//			System.out.print(aitem.getItemProdDescription());
//			System.out.println();
//		}
//		
//		
//		
//		
//	}
//}
