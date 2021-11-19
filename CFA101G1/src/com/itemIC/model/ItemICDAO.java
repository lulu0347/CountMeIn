package com.itemIC.model;

import java.util.*;
import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;



public class ItemICDAO implements ItemICDAO_interface{

	private static DataSource ds = null;

	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G1");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	//------讓會員搜尋收藏列表且查看時能同時顯示出商品資料------------------
	private static final String GET_ITEMCOLLECTIONBYMEMNO_STMT ="SELECT * FROM itemcollection AS itc INNER JOIN item AS it ON itc.itemno = it.itemno WHERE itc.memno = ?";

	@Override
	public List<ItemICVO> getCOllectionByMemNo(Integer memNo) {

		List<ItemICVO> list = new ArrayList<ItemICVO>();
		ItemICVO itemICVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ITEMCOLLECTIONBYMEMNO_STMT);
			
			pstmt.setInt(1, memNo);
			
			rs = pstmt.executeQuery();
		
			while (rs.next()) {
				itemICVO = new ItemICVO();
				itemICVO.setItemNo(rs.getInt("itemNo"));
				itemICVO.setItemName(rs.getString("itemName"));
				itemICVO.setItemPrice(rs.getInt("itemPrice"));
				itemICVO.setItemState(rs.getInt("itemState"));
				itemICVO.setWarrantyDate(rs.getDouble("warrantyDate"));
				itemICVO.setKindNo(rs.getInt("kindNo"));
				
				list.add(itemICVO);
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
		return list;

	}

	
	

}
