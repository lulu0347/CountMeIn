package com.productkind.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductKindJDBCDAO implements ProductKindDAO_interface {

	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/CFA101G1?severTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "CFA101G1";

	private static final String INSERT_STMT = "INSERT INTO ProductKind (kindName) VALUES (?)";
	private static final String UPDATE = "UPDATE ProductKind SET kindName = ? WHERE kindNo = ?";
	private static final String GET_ALL_STMT = "SELECT kindNo, kindName FROM ProductKind order by kindNo";
	private static final String GET_ONE_STMT = "SELECT kindNo, kindName FROM ProductKind WHERE kindNo = ?";

	@Override
	public void insert(ProductKindVO productKindVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setString(1, productKindVO.getKindName());
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
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
	public void update(ProductKindVO productKindVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, productKindVO.getKindName());
			pstmt.setInt(2, productKindVO.getKindNo());
			pstmt.executeUpdate();

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
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
	public ProductKindVO findByPrimaryKey(Integer kindNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProductKindVO productKindVO = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, kindNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				productKindVO = new ProductKindVO();
				productKindVO.setKindNo(rs.getInt("kindNo"));
				productKindVO.setKindName(rs.getString("kindName"));
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
		}

		return productKindVO;
	}

	@Override
	public List<ProductKindVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ProductKindVO productKindVO = null;
		ResultSet rs = null;
		List<ProductKindVO> list = new ArrayList<ProductKindVO>();

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				productKindVO = new ProductKindVO();
				productKindVO.setKindNo(rs.getInt("kindNo"));
				productKindVO.setKindName(rs.getString("kindName"));
				list.add(productKindVO);
			}

		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	public static void main(String[] args) {
		ProductKindJDBCDAO dao = new ProductKindJDBCDAO();

//		//新增
//		ProductKindVO productKindVO1 = new ProductKindVO();
//		productKindVO1.setKindName("XX");
//		productKindVO1.setKindName("電腦");
//		productKindVO1.setKindName("手錶");
//		productKindVO1.setKindName("平板");
//		productKindVO1.setKindName("配件類");
//		dao.insert(productKindVO1);

//		//修改
//		ProductKindVO productKindVO2 = new ProductKindVO();
//		productKindVO2.setKindNo(1);
//		productKindVO2.setKindName("手機");
//		dao.update(productKindVO2);
//
//		//查詢一筆
//		ProductKindVO productKindVO3 = dao.findByPrimaryKey(2);
//		System.out.print(productKindVO3.getKindNo() + ",");
//		System.out.print(productKindVO3.getKindName());
//		System.out.println("---------------");
//
//		//查詢全部
		List<ProductKindVO> list = dao.getAll();
		for (ProductKindVO pkv : list) {
			System.out.print(pkv.getKindNo() + ",");
			System.out.print(pkv.getKindName() + ",");
			System.out.println("---------------");
		}
	}
}
