package com.item.model;

import java.util.*;
import java.sql.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import com.itempic.model.*;

import jdbc.Util.CompositeQuery.jdbcUtil_CompositeQuery_Item;

import com.itemdetail.model.*;
import com.itemcollection.model.*;
import com.itemorder.model.*;


public class ItemDAO implements ItemDAO_interface {

	private static DataSource ds = null;

	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G1");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_STMT = "INSERT INTO Item (KindNo, ItemName, ItemPrice, ItemState, LaunchedTime, WarrantyDate, ItemProdDescription) VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_ALL_STMT = "SELECT ItemNo, KindNo, ItemName, ItemPrice, ItemState, SoldTime, LaunchedTime, WarrantyDate, ItemProdDescription FROM Item WHERE ItemNo";
	private static final String GET_ONE_STMT = "SELECT ItemNo, KindNo, ItemName, ItemPrice, ItemState, SoldTime, LaunchedTime, WarrantyDate, ItemProdDescription FROM Item WHERE ItemNo = ?";
	private static final String UPDATE = "UPDATE Item SET KindNo=?, ItemName=?, ItemPrice=?, ItemState=?, SoldTime=?, LaunchedTime=?, WarrantyDate=?, ItemProdDescription=? WHERE ItemNo=?";
	private static final String DELETE_ITEMPIC = "DELETE FROM ItemPic WHERE ItemNo = ?";
	private static final String DELETE_ITEM = "DELETE FROM Item WHERE ItemNo = ?";
	private static final String DELETE_ITEMDETAIL = "DELETE FROM ItemDetail WHERE ItemNo = ?";
	private static final String DELETE_ITEMCOLLECTION = "DELETE FROM ItemCollection WHERE ItemNo = ?";
	
	//手機
	private static final String GET_ONE_STMT1 = "SELECT * FROM Item WHERE KindNo = 1";
	//電腦
	private static final String GET_ONE_STMT2 = "SELECT * FROM Item WHERE KindNo = 2";
	//相機
	private static final String GET_ONE_STMT3 = "SELECT * FROM Item WHERE KindNo = 3";
	//手錶
	private static final String GET_ONE_STMT4 = "SELECT * FROM Item WHERE KindNo = 4";
	//配件
	private static final String GET_ONE_STMT5 = "SELECT * FROM Item WHERE KindNo = 5";
	@Override
	public void insert(ItemVO ItemVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_STMT);

			pstmt.setInt(1, ItemVO.getKindNo());
			pstmt.setString(2, ItemVO.getItemName());
			pstmt.setInt(3, ItemVO.getItemPrice());
			pstmt.setInt(4, ItemVO.getItemState());
			pstmt.setTimestamp(5, ItemVO.getLaunchedTime());
			pstmt.setDouble(6, ItemVO.getWarrantyDate());
			pstmt.setString(7, ItemVO.getItemProdDescription());
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
	public void insertWithPics(ItemVO itemVO, List<byte[]> list) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			con.setAutoCommit(false);
			String cols[] = {"itemNo"};
			pstmt = con.prepareStatement(INSERT_STMT, cols);

			pstmt.setInt(1, itemVO.getKindNo());
			pstmt.setString(2, itemVO.getItemName());
			pstmt.setInt(3, itemVO.getItemPrice());
			pstmt.setInt(4, itemVO.getItemState());
			pstmt.setTimestamp(5, itemVO.getLaunchedTime());
			pstmt.setDouble(6, itemVO.getWarrantyDate());
			pstmt.setString(7, itemVO.getItemProdDescription());
			pstmt.executeUpdate();
			
			String next_itemNo = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				next_itemNo = rs.getString(1);
				System.out.println("自增主鍵值= " + next_itemNo +"(剛新增成功的商品編號)");
			} else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();
			System.out.println("ItemDAO取得的值from ItemService" + itemVO.getKindNo());
			System.out.println("ItemDAO取得的值from ItemService" + itemVO.getItemName());
			System.out.println("ItemDAO取得的值from ItemService" + itemVO.getItemPrice());
			System.out.println("ItemDAO取得的值from ItemService" + itemVO.getItemState());
			System.out.println("ItemDAO取得的值from ItemService" + itemVO.getLaunchedTime());
			System.out.println("ItemDAO取得的值from ItemService" + itemVO.getWarrantyDate());
			System.out.println("ItemDAO取得的值from ItemService" + itemVO.getItemProdDescription());
			System.out.println("測試List是否還在 : " + list );
			ItemPicDAO dao = new ItemPicDAO();
			System.out.println("list.size()-A="+list.size());
			for (int i = 0; i < list.size(); i++) {
				ItemPicVO itemPicVO = new ItemPicVO();
				itemPicVO.setItemNo(new Integer(next_itemNo)) ;
				itemPicVO.setItemPic(list.get(i)) ;
				System.out.println("ItemDAO跑迴圈取得的nextItemNo值為" + new Integer(next_itemNo));
				System.out.println("ItemDAO跑迴圈取得的List.get值為" + list.get(i));
				System.out.println(con);
				dao.insert2(itemPicVO,con);
			}
			
			con.commit();
			con.setAutoCommit(true);
			System.out.println("list.size()-B="+list.size());
			System.out.println("新增商品編號" + next_itemNo + "時,共有照片" + list.size()
					+ "張同時被新增");
		}catch (SQLException se) {
			if (con != null) {
				try {
					System.err.print("Transaction is being ");
					System.err.println("rolled back-由-item");
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured. "
							+ excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
	public void update(ItemVO ItemVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setInt(1, ItemVO.getKindNo());
			pstmt.setString(2, ItemVO.getItemName());
			pstmt.setInt(3, ItemVO.getItemPrice());
			pstmt.setInt(4, ItemVO.getItemState());
			pstmt.setTimestamp(5, ItemVO.getSoldTime());
			pstmt.setTimestamp(6, ItemVO.getLaunchedTime());
			pstmt.setDouble(7, ItemVO.getWarrantyDate());
			pstmt.setString(8, ItemVO.getItemProdDescription());
			pstmt.setInt(9, ItemVO.getItemNo());

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
	public void delete(Integer ItemNo) {
		// 計算被刪掉的商品照片個數
		int udpateCount_ItemPic = 0;
		// 計算被刪掉的商品明細個數
		int udpateCount_ItemDetail = 0;
		// 計算被刪掉的商品收藏個數
		int udpateCount_ItemCollection = 0;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			con.setAutoCommit(false);

			// 先刪除商品照片
			pstmt = con.prepareStatement(DELETE_ITEMPIC);
			pstmt.setInt(1, ItemNo);
			udpateCount_ItemPic = pstmt.executeUpdate();
			
			//再刪除商品明細
			pstmt = con.prepareStatement(DELETE_ITEMDETAIL);
			pstmt.setInt(1, ItemNo);
			udpateCount_ItemDetail = pstmt.executeUpdate();
			
			//再再再再刪除商品收藏列表
			pstmt = con.prepareStatement(DELETE_ITEMCOLLECTION);
			pstmt.setInt(1, ItemNo);
			udpateCount_ItemCollection = pstmt.executeUpdate();
			
			// 再刪除商品
			pstmt = con.prepareStatement(DELETE_ITEM);
			pstmt.setInt(1, ItemNo);
			pstmt.executeUpdate();

			con.commit();
			con.setAutoCommit(true);
			System.out.println("form itemDAO line160 :刪除商品編號" + ItemNo + "時共有商品照片" + udpateCount_ItemPic + "張被刪除");
			System.out.println("form itemDAO line161 :刪除商品編號" + ItemNo + "時共有商品明細" + udpateCount_ItemDetail + "筆被刪除");
			System.out.println("form itemDAO line162 :刪除商品編號" + ItemNo + "時共有商品收藏" + udpateCount_ItemCollection + "個被刪除");
			
			// Handle any driver errors
		} catch (SQLException se) {
			if (con != null) {
				try {
					// 3●設定於當有exception發生時之catch區塊內
					System.err.print("Transaction is being ");
					System.err.println("rolled back from itemNo");
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
	public ItemVO findByItemNo(Integer ItemNo) {

		ItemVO itemVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT);

			pstmt.setInt(1, ItemNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// itemVO is Domain objects
				itemVO = new ItemVO();

				itemVO.setItemNo(rs.getInt("itemNo"));
				itemVO.setKindNo(rs.getInt("kindNo"));
				itemVO.setItemName(rs.getString("itemName"));
				itemVO.setItemPrice(rs.getInt("itemPrice"));
				itemVO.setItemState(rs.getInt("itemState"));
				itemVO.setSoldTime(rs.getTimestamp("soldTime"));
				itemVO.setLaunchedTime(rs.getTimestamp("launchedTime"));
				itemVO.setWarrantyDate(rs.getDouble("warrantyDate"));
				itemVO.setItemProdDescription(rs.getString("itemProdDescription"));
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
		return itemVO;
	}

	@Override
	public List<ItemVO> getAll() {
		List<ItemVO> list = new ArrayList<ItemVO>();
		ItemVO itemVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_STMT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// itemVO is Domain objects
				itemVO = new ItemVO();
				itemVO.setItemNo(rs.getInt("ItemNo"));
				itemVO.setKindNo(rs.getInt("KindNo"));
				itemVO.setItemName(rs.getString("ItemName"));
				itemVO.setItemPrice(rs.getInt("ItemPrice"));
				itemVO.setItemState(rs.getInt("ItemState"));
				itemVO.setSoldTime(rs.getTimestamp("SoldTime"));
				itemVO.setLaunchedTime(rs.getTimestamp("LaunchedTime"));
				itemVO.setWarrantyDate(rs.getDouble("WarrantyDate"));
				itemVO.setItemProdDescription(rs.getString("ItemProdDescription"));
				list.add(itemVO);

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
		return list;

	}

	//找手機?
	@Override
	public List<ItemVO> getOneKind1() {
		
		List<ItemVO> list = new ArrayList<ItemVO>();
		ItemVO itemVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT1);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// itemVO is Domain objects
				itemVO = new ItemVO();
				itemVO.setItemNo(rs.getInt("ItemNo"));
				itemVO.setKindNo(rs.getInt("KindNo"));
				itemVO.setItemName(rs.getString("ItemName"));
				itemVO.setItemPrice(rs.getInt("ItemPrice"));
				itemVO.setItemState(rs.getInt("ItemState"));
				itemVO.setSoldTime(rs.getTimestamp("SoldTime"));
				itemVO.setLaunchedTime(rs.getTimestamp("LaunchedTime"));
				itemVO.setWarrantyDate(rs.getDouble("WarrantyDate"));
				itemVO.setItemProdDescription(rs.getString("ItemProdDescription"));
				list.add(itemVO);

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
		return list;

	}

	//找電腦?
	@Override
	public List<ItemVO> getOneKind2() {
		
		List<ItemVO> list = new ArrayList<ItemVO>();
		ItemVO itemVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT2);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// itemVO is Domain objects
				itemVO = new ItemVO();
				itemVO.setItemNo(rs.getInt("ItemNo"));
				itemVO.setKindNo(rs.getInt("KindNo"));
				itemVO.setItemName(rs.getString("ItemName"));
				itemVO.setItemPrice(rs.getInt("ItemPrice"));
				itemVO.setItemState(rs.getInt("ItemState"));
				itemVO.setSoldTime(rs.getTimestamp("SoldTime"));
				itemVO.setLaunchedTime(rs.getTimestamp("LaunchedTime"));
				itemVO.setWarrantyDate(rs.getDouble("WarrantyDate"));
				itemVO.setItemProdDescription(rs.getString("ItemProdDescription"));
				list.add(itemVO);

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
		return list;

	}

	//找手錶?
	@Override
	public List<ItemVO> getOneKind4() {
		
		List<ItemVO> list = new ArrayList<ItemVO>();
		ItemVO itemVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT4);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// itemVO is Domain objects
				itemVO = new ItemVO();
				itemVO.setItemNo(rs.getInt("ItemNo"));
				itemVO.setKindNo(rs.getInt("KindNo"));
				itemVO.setItemName(rs.getString("ItemName"));
				itemVO.setItemPrice(rs.getInt("ItemPrice"));
				itemVO.setItemState(rs.getInt("ItemState"));
				itemVO.setSoldTime(rs.getTimestamp("SoldTime"));
				itemVO.setLaunchedTime(rs.getTimestamp("LaunchedTime"));
				itemVO.setWarrantyDate(rs.getDouble("WarrantyDate"));
				itemVO.setItemProdDescription(rs.getString("ItemProdDescription"));
				list.add(itemVO);

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
		return list;

	}
		
	//找相機?
	@Override
	public List<ItemVO> getOneKind3() {
		
		List<ItemVO> list = new ArrayList<ItemVO>();
		ItemVO itemVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT3);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// itemVO is Domain objects
				itemVO = new ItemVO();
				itemVO.setItemNo(rs.getInt("ItemNo"));
				itemVO.setKindNo(rs.getInt("KindNo"));
				itemVO.setItemName(rs.getString("ItemName"));
				itemVO.setItemPrice(rs.getInt("ItemPrice"));
				itemVO.setItemState(rs.getInt("ItemState"));
				itemVO.setSoldTime(rs.getTimestamp("SoldTime"));
				itemVO.setLaunchedTime(rs.getTimestamp("LaunchedTime"));
				itemVO.setWarrantyDate(rs.getDouble("WarrantyDate"));
				itemVO.setItemProdDescription(rs.getString("ItemProdDescription"));
				list.add(itemVO);

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
		return list;

	}
		
	//找配件
	@Override
	public List<ItemVO> getOneKind5() {
		
		List<ItemVO> list = new ArrayList<ItemVO>();
		ItemVO itemVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_STMT5);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// itemVO is Domain objects
				itemVO = new ItemVO();
				itemVO.setItemNo(rs.getInt("ItemNo"));
				itemVO.setKindNo(rs.getInt("KindNo"));
				itemVO.setItemName(rs.getString("ItemName"));
				itemVO.setItemPrice(rs.getInt("ItemPrice"));
				itemVO.setItemState(rs.getInt("ItemState"));
				itemVO.setSoldTime(rs.getTimestamp("SoldTime"));
				itemVO.setLaunchedTime(rs.getTimestamp("LaunchedTime"));
				itemVO.setWarrantyDate(rs.getDouble("WarrantyDate"));
				itemVO.setItemProdDescription(rs.getString("ItemProdDescription"));
				list.add(itemVO);

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
		return list;

	}
	
	//
	@Override
	public List<ItemVO> getAll(Map<String, String[]> map){
		
		List<ItemVO> list = new ArrayList<ItemVO>();
		ItemVO itemVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			//item後保留一個空格
			//在這邊做SQL指令
			String finalSQL = "SELECT * FROM item "
					+ jdbcUtil_CompositeQuery_Item.get_WhereCondition(map)
					+"ORDER BY itemNo";
			
			pstmt = con.prepareStatement(finalSQL);
			System.out.println("測試finalSQL(by ItemDAO) = "+finalSQL);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				itemVO = new ItemVO();
				itemVO.setItemNo(rs.getInt("ItemNo"));
				itemVO.setKindNo(rs.getInt("KindNo"));
				itemVO.setItemName(rs.getString("ItemName"));
				itemVO.setItemPrice(rs.getInt("ItemPrice"));
				itemVO.setItemState(rs.getInt("ItemState"));
				itemVO.setSoldTime(rs.getTimestamp("SoldTime"));
				itemVO.setLaunchedTime(rs.getTimestamp("LaunchedTime"));
				itemVO.setWarrantyDate(rs.getDouble("WarrantyDate"));
				itemVO.setItemProdDescription(rs.getString("ItemProdDescription"));
				list.add(itemVO);
			}		
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
