package com.itempic.model;

import java.util.*;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.Part;
import javax.sql.DataSource;

public class ItemPicDAO implements ItemPicDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G1");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO ItemPic (ItemNo, ItemPic) VALUES (?, ?)";
	private static final String GET_ALL_STMT = "SELECT itemPicNo, itemNo, itemPic FROM ItemPic ORDER BY itemPicNo";
	private static final String GET_ONE_STMT = "SELECT itemPicNo, itemNo, itemPic FROM ItemPic WHERE itemPicNo = ?";
	private static final String DELETE = "DELETE FROM ItemPic WHERE itempicno = ?";
	private static final String UPDATE = "UPDATE ItemPic set itempicno = ?, itemno = ?, itempic = ? WHERE itempicno = ?";
	private static final String GET_BYITEMNO_STMT = "SELECT itemPicNo, itemNo, itemPic FROM ItemPic WHERE itemNo = ?";
	private static final String GET_ITEMPICNO = "SELECT itemPic FROM ItemPic WHERE itemPicNo = ?";
	private static final String GET_ITEMNO = "SELECT itemPic FROM ItemPic WHERE itemNo = ?";

	@Override
	public void addPic(ItemPicVO ItemPicVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, ItemPicVO.getItemNo());
			pstmt.setBytes(2, ItemPicVO.getItemPic());

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
	public void insert2(ItemPicVO itemPicVO, Connection con) {

		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, itemPicVO.getItemNo());
			pstmt.setBytes(2, itemPicVO.getItemPic());
			pstmt.executeUpdate();

		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-pic");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. " + excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured." + se.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public void updatePic(ItemPicVO ItemPicVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, ItemPicVO.getItemPicNo());
			pstmt.setInt(2, ItemPicVO.getItemNo());
			pstmt.setBytes(3, ItemPicVO.getItemPic());
			pstmt.setInt(4, ItemPicVO.getItemPicNo());

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
	public void deletePic(Integer ItemPicNo) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, ItemPicNo);

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
	public ItemPicVO findByItemPicNo(Integer ItemPicNo) {

		ItemPicVO itemPicVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, ItemPicNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				itemPicVO = new ItemPicVO();

				itemPicVO.setItemPicNo(rs.getInt("itempicno"));
				itemPicVO.setItemNo(rs.getInt("itemno"));
				itemPicVO.setItemPic(rs.getBytes("itempic"));

			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
		return itemPicVO;
	}

	@Override
	public List<ItemPicVO> getAllPics() {

		List<ItemPicVO> list = new ArrayList<ItemPicVO>();
		ItemPicVO itemPicVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// itemPicVO is Domain objects
				itemPicVO = new ItemPicVO();

				itemPicVO.setItemPicNo(rs.getInt("itempicno"));
				itemPicVO.setItemNo(rs.getInt("itemno"));
				itemPicVO.setItemPic(rs.getBytes("itempic"));
				list.add(itemPicVO);

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
	public List<ItemPicVO> getByItemNo(Integer itemNo) {

		List<ItemPicVO> list = new ArrayList<ItemPicVO>();
		ItemPicVO itemPicVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BYITEMNO_STMT);

			pstmt.setInt(1, itemNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				itemPicVO = new ItemPicVO();

				itemPicVO.setItemPicNo(rs.getInt("itemPicNo"));
				itemPicVO.setItemNo(rs.getInt("itemNo"));
				itemPicVO.setItemPic(rs.getBytes("itemPic"));
				list.add(itemPicVO);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

	public byte[] getItemPic(Integer itemPicNo) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		byte[] itemPic = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ITEMPICNO);

			pstmt.setInt(1, itemPicNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				itemPic = rs.getBytes("itemPic");
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

		return itemPic;
	}

	public byte[] getItemPic2(Integer itemNo) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		byte[] itemPic = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ITEMNO);

			pstmt.setInt(1, itemNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				itemPic = rs.getBytes("itemPic");
			}

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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

		return itemPic;
	}

	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}

}
