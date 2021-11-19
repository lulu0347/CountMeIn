//package com.itempic.model;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.servlet.http.Part;
//
//public class ItemPicJDBCDAO implements ItemPicDAO_interface{
//	
//	String driver = "com.mysql.cj.jdbc.Driver";
//	String url = "jdbc:mysql://localhost:3306/CFA101G1?serverTimezone=Asia/Taipei";
//	String userid = "root";
//	String passwd = "123456";
//	
//	private static final String INSERT_STMT = 
//			"INSERT INTO ItemPic (itempic, itemno) VALUES (?, ?)";
//	private static final String GET_ALL_STMT = 
//			"SELECT itempicno, itempic, itemno FROM ItemPic ORDER BY itempicno";
//	private static final String GET_ONE_STMT = 
//			"SELECT itempicno, itempic, itemno FROM ItemPic WHERE itempicno = ?";
//	private static final String DELETE = 
//			"DELETE FROM ItemPic WHERE itempicno = ?";
//	private static final String UPDATE = 
//			"UPDATE ItemPic set itempic = ?, itemno = ? WHERE itempicno = ?";
////	
////	
//	@Override
//	public void insert(ItemPicVO ItemPicVO) {
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
//			pstmt.setBytes(1, ItemPicVO.getItemPic());
//			pstmt.setInt(2, ItemPicVO.getItemNo());
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
//	}
////
////
//	@Override
//	public void update(ItemPicVO ItemPicVO) {
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
//			pstmt.setBytes(1, ItemPicVO.getItemPic());
//			pstmt.setInt(2, ItemPicVO.getItemNo());
//			pstmt.setInt(3, ItemPicVO.getItemPicNo());
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
//	}
////
////
////	@Override
////	public void delete(Integer ItemPicNo) {
////		
////		Connection con = null;
////		PreparedStatement pstmt = null;
////
////		try {
////
////			Class.forName(driver);
////			con = DriverManager.getConnection(url, userid, passwd);
////			pstmt = con.prepareStatement(DELETE);
////
////			pstmt.setInt(1, ItemPicNo);
////
////			pstmt.executeUpdate();
////
////			// Handle any driver errors
////		} catch (ClassNotFoundException e) {
////			throw new RuntimeException("Couldn't load database driver. "
////					+ e.getMessage());
////			// Handle any SQL errors
////		} catch (SQLException se) {
////			throw new RuntimeException("A database error occured. "
////					+ se.getMessage());
////			// Clean up JDBC resources
////		} finally {
////			if (pstmt != null) {
////				try {
////					pstmt.close();
////				} catch (SQLException se) {
////					se.printStackTrace(System.err);
////				}
////			}
////			if (con != null) {
////				try {
////					con.close();
////				} catch (Exception e) {
////					e.printStackTrace(System.err);
////				}
////			}
////		}
////
////	}
////
////
//	@Override
//	public ItemPicVO findByItemPicNo(Integer ItemPicNo) {
//		
//		ItemPicVO itemPicVO = null;
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
//			pstmt.setInt(1, ItemPicNo);
//
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				// itemPicVO is Domain objects
//				itemPicVO = new ItemPicVO();
//				itemPicVO.setItemPicNo(rs.getInt("itempicno"));
//				itemPicVO.setItemPic(rs.getBytes("itempic"));
//				itemPicVO.setItemNo(rs.getInt("itemno"));
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
//		return itemPicVO;
//	}
////
////
////	@Override
////	public List<ItemPicVO> getAll() {
////		
////		List<ItemPicVO> list = new ArrayList<ItemPicVO>();
////		ItemPicVO itempicVO = null;
////
////		Connection con = null;
////		PreparedStatement pstmt = null;
////		ResultSet rs = null;
////		
////		try {
////
////			Class.forName(driver);
////			con = DriverManager.getConnection(url, userid, passwd);
////			pstmt = con.prepareStatement(GET_ALL_STMT);
////			rs = pstmt.executeQuery();
////
////			while (rs.next()) {
////				// itemPicVO is Domain objects
////				itempicVO = new ItemPicVO();
////
////				itempicVO.setItemPicNo(rs.getInt("itempicno"));
////				itempicVO.setItemPic(rs.getBytes("itempic"));
////				itempicVO.setItemNo(rs.getInt("itemno"));
////
////			}
////
////			// Handle any driver errors
////		} catch (ClassNotFoundException e) {
////			throw new RuntimeException("Couldn't load database driver. "
////					+ e.getMessage());
////			// Handle any SQL errors
////		} catch (SQLException se) {
////			throw new RuntimeException("A database error occured. "
////					+ se.getMessage());
////			// Clean up JDBC resources
////		} finally {
////			if (rs != null) {
////				try {
////					rs.close();
////				} catch (SQLException se) {
////					se.printStackTrace(System.err);
////				}
////			}
////			if (pstmt != null) {
////				try {
////					pstmt.close();
////				} catch (SQLException se) {
////					se.printStackTrace(System.err);
////				}
////			}
////			if (con != null) {
////				try {
////					con.close();
////				} catch (Exception e) {
////					e.printStackTrace(System.err);
////				}
////			}
////		}
////		return list;		
////	}
////	
//	public static byte[] getPictureByteArray(String path) throws IOException {
//		File file = new File(path);
//		FileInputStream fis = new FileInputStream(file);
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		byte[] buffer = new byte[8192];
//		int i;
//		while ((i = fis.read(buffer)) != -1) {
//			baos.write(buffer, 0, i);
//		}
//		baos.close();
//		fis.close();
//		
//		return baos.toByteArray();
//	}
//
//	public static void readPicture(byte[] bytes) throws IOException {
//		FileOutputStream fos = new FileOutputStream("I_pic");
//		fos.write(bytes);
//		fos.flush();
//		fos.close();
//	}	
////	
////	
////
//	public static void main(String[] args) throws IOException {
//		
//		ItemPicJDBCDAO dao = new ItemPicJDBCDAO();
//		
//		//新增
//		ItemPicVO itemPicVO1 = new ItemPicVO();
//		byte[] pic = getPictureByteArray("C:/Workspace/eclipse_WTP-Workspace/CFA101G1/WebContent/resource/Images/item1.jpeg");
//		
//		itemPicVO1.setItemPic(pic);
//		itemPicVO1.setItemNo(21007);
//		dao.insert(itemPicVO1);
//		System.out.println("----------------新增成功-----------------");
//		
//		//修改
////		ItemPicVO itemPicVO2 = new ItemPicVO();
////		byte[] pic = getPictureByteArray("C:/Workspace/eclipse_WTP-Workspace/CFA101G1/WebContent/resource/Images/Images/item2.jpeg");
////		itemPicVO2.setItemPic(pic);
////		itemPicVO2.setItemNo(21007);
////		itemPicVO2.setItemPicNo(22008);
////		dao.update(itemPicVO2);
////		System.out.println("----------------修改成功-----------------");
//		
//		// 查詢
//    	ItemPicVO itemPicVO3 = dao.findByItemPicNo(22008);
//    	System.out.print(itemPicVO3.getItemPicNo() + ",");
//    	System.out.print(itemPicVO3.getItemPic() + ",");
//    	System.out.println(itemPicVO3.getItemNo());
//    	System.out.println("-----------------------------");
//		
//		
////		List<ItemPicVO> list = dao.getAll();
////		for(ItemPicVO aPicVo : list) {
////			System.out.println(aPicVo.getItemPicNo());
////			System.out.println(aPicVo.getItemPic());
////			System.out.println(aPicVo.getItemNo());
////			
////		}
////	}
//
//}
