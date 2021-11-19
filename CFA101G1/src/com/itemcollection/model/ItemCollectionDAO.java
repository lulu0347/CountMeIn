package com.itemcollection.model;

import java.util.*;
import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ItemCollectionDAO implements ItemCollectionDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G1");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = 
			"INSERT INTO ItemCollection (Memno, Itemno) VALUES (?, ?)";
	private static final String GET_ALL_STMT = 
			"SELECT Memno, Itemno FROM ItemCollection ORDER BY ItemNo";
	private static final String GET_ONE_STMT = 
			"SELECT Memno, Itemno FROM ItemCollection WHERE Memno = ?";
	private static final String DELETE =
			"DELETE FROM ItemCollection where Itemno = ? AND MemNo = ?";	
	private static final String UPDATE = 
			"UPDATE ItemCollection SET memNo = ?, ItemNo = ? WHERE MemNo = ?";
	private static final String GET_COUNT_COLLECT =
			"SELECT COUNT(itemNo) AS collect FROM itemCollection where itemNo =? and memNo = ?";
	
	
	
	
	@Override

	public void insert(ItemCollectionVO ItemCollectionVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, ItemCollectionVO.getMemNo());
			pstmt.setInt(2, ItemCollectionVO.getItemNo());

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
	public void update(ItemCollectionVO ItemCollectionVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, ItemCollectionVO.getMemNo());
			pstmt.setInt(2, ItemCollectionVO.getItemNo());

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
	public void delete(Integer ItemNo, Integer MemNo) {
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, ItemNo);
			pstmt.setInt(2, MemNo);

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
	public ItemCollectionVO  findOneByMemNo(Integer memNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ItemCollectionVO itemCollectionVO = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);
			
			pstmt.setInt(1, memNo);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				itemCollectionVO = new ItemCollectionVO();
				
				itemCollectionVO.setMemNo(rs.getInt("memNo"));
				itemCollectionVO.setItemNo(rs.getInt("itemNo"));
				
			}
		}catch(SQLException se) {
				se.printStackTrace();
			}finally{
				if(rs!=null) {
					try {
						rs.close();
					}catch(Exception e) {
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
			
			return itemCollectionVO;
	}

	@Override
	public List<ItemCollectionVO> findByMemNo(Integer memNo) {
		
		List<ItemCollectionVO> list = new ArrayList<ItemCollectionVO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ItemCollectionVO itemCollectionVO = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, memNo);
			

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// itemCollectionVO is Domain objects
				itemCollectionVO = new ItemCollectionVO();
				
				itemCollectionVO.setMemNo(rs.getInt("memno"));
				itemCollectionVO.setItemNo(rs.getInt("itemno"));
				list.add(itemCollectionVO);
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
	public List<ItemCollectionVO> getAll() {
		
		List<ItemCollectionVO> list =new ArrayList<ItemCollectionVO>();
		ItemCollectionVO itemCollectionVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();
			
			
			
			while (rs.next()) {
				// itemCollectionVO is Domain objects
				itemCollectionVO = new ItemCollectionVO();
				itemCollectionVO.setMemNo(rs.getInt("MemNo"));
				itemCollectionVO.setItemNo(rs.getInt("ItemNo"));
				list.add(itemCollectionVO);
			}
			
			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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

//----------------------------用於計算數字來判斷使否已加入過收藏列表-----------------------
	@Override
	public int getcount(Integer itemNo, Integer memNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int collect =0;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_COUNT_COLLECT);
			pstmt.setInt(1, itemNo);
			pstmt.setInt(2, memNo);
			rs = pstmt.executeQuery();
			while(rs.next()){
				collect = rs.getInt("collect");
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return collect;
	}
	
}	

