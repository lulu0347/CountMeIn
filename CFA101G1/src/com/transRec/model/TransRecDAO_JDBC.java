package com.transRec.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import DbUtil.BaseDAO;

public class TransRecDAO_JDBC extends BaseDAO implements TransRecDAO_interface {
	;

	private final String INSERT_STMT = "insert into TRANSREC(MemNo,TransAmount,TransRecTime,TransState,MallName,OrderNo,TransCont) VALUES (?, ?, ?, ?, ?, ?, ? )";
	private final String UPDATE_STMT = "Update TransRec set MemNo=?,TransAmount=?,TransRecTime=?,TransState=?,MallName=?,OrderNo=?,TransCont=? where TransRecNo=?";
	private final String FIND_BY_MEMNO = "Select * from TRANSREC where MemNo=? order by TransRecNo desc";
	private final String FIND_BY_MALL_ORD = "Select * from TRANSREC where MallName=? and OrderNo=? ";
	private final String GET_ALL = "Select * from TRANSREC order by TransRecNo desc";
	private final String FIND_DEPOSIT = "select sum(TransAmount) as sum from TransRec where MemNo=? and TransRecTime <?";
	private final String FIND_BY_orderNo="Select * from TRANSREC where OrderNo=?";

	static {
		try {
			Class.forName(getDriver());
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}

	public Connection getConnection() throws SQLException {
		Connection con = DriverManager.getConnection(getUrl(), getUser(), getPassword());
		return con;
	}

	@Override
	public Integer add(TransRecVO transRec) {
		Connection con = null;
		PreparedStatement pstmt = null;
		Integer next_TransOrdNo = null;

		try {

			con = DriverManager.getConnection(getUrl(), getUser(), getPassword());
			String[] col = { "TransRecNo" };
			pstmt = con.prepareStatement(INSERT_STMT, col);

			pstmt.setInt(1, transRec.getMemNo());
			pstmt.setInt(2, transRec.getTransAmount());
			pstmt.setTimestamp(3, transRec.getTransRecTime());
			pstmt.setInt(4, transRec.getTransState());
			pstmt.setString(5, transRec.getMallName());

			pstmt.setObject(6, transRec.getOrderNo(), java.sql.Types.INTEGER);
			pstmt.setString(7, transRec.getTransCont());

			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				next_TransOrdNo = rs.getInt(1);

			}

			rs.close();

		} catch (SQLException se) {
			se.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return next_TransOrdNo;
	}

	@Override
	public void update(TransRecVO transRec) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = DriverManager.getConnection(getUrl(), getUser(), getPassword());
			pstmt = con.prepareStatement(UPDATE_STMT);

			pstmt.setInt(1, transRec.getMemNo());
			pstmt.setInt(2, transRec.getTransAmount());
			pstmt.setTimestamp(3, transRec.getTransRecTime());
			pstmt.setInt(4, transRec.getTransState());
			pstmt.setString(5, transRec.getMallName());
			pstmt.setInt(6, transRec.getOrderNo());
			pstmt.setString(7, transRec.getTransCont());
			pstmt.setInt(8, transRec.getTransRecNo());

			pstmt.executeUpdate();

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
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
	public List<TransRecVO> findByMemNo(Integer memNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<TransRecVO> memRecordList = new ArrayList<TransRecVO>();

		try {
			con = DriverManager.getConnection(getUrl(), getUser(), getPassword());
			pstmt = con.prepareStatement(FIND_BY_MEMNO);
			pstmt.setInt(1, memNo);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				TransRecVO transRecVO = new TransRecVO();
				transRecVO.setTransRecNo(rs.getInt("transRecNo"));
				transRecVO.setMemNo(rs.getInt("memNo"));
				transRecVO.setTransAmount(rs.getInt("transAmount"));
				transRecVO.setTransRecTime(rs.getTimestamp("transRecTime"));
				transRecVO.setTransState(rs.getInt("transState"));
				transRecVO.setMallName(rs.getString("mallName"));
				transRecVO.setOrderNo(rs.getInt("orderNo"));
				transRecVO.setTransCont(rs.getString("transCont"));
				memRecordList.add(transRecVO);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

		return memRecordList;
	}

	@Override
	public TransRecVO findByMall_Order(String mallName, Integer orderNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		TransRecVO transRecVO = null;

		try {
			con = DriverManager.getConnection(getUrl(), getUser(), getPassword());
			pstmt = con.prepareStatement(FIND_BY_MALL_ORD);
			pstmt.setString(1, mallName);
			pstmt.setInt(2, orderNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				transRecVO = new TransRecVO();
				transRecVO.setTransRecNo(rs.getInt("transRecNo"));
				transRecVO.setMemNo(rs.getInt("memNo"));
				transRecVO.setTransAmount(rs.getInt("transAmount"));
				transRecVO.setTransRecTime(rs.getTimestamp("transRecTime"));
				transRecVO.setTransState(rs.getInt("transState"));
				transRecVO.setMallName(rs.getString("mallName"));
				transRecVO.setOrderNo(rs.getInt("orderNo"));
				transRecVO.setTransCont(rs.getString("transCont"));

			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

		return transRecVO;
	}

	@Override
	public List<TransRecVO> getAll() {
		List<TransRecVO> transRecList = new ArrayList<>();
		TransRecVO transRecVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = DriverManager.getConnection(getUrl(), getUser(), getPassword());
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				transRecVO = new TransRecVO();
				transRecVO.setTransRecNo(rs.getInt("transRecNo"));
				transRecVO.setMemNo(rs.getInt("memNo"));
				transRecVO.setTransAmount(rs.getInt("transAmount"));
				transRecVO.setTransRecTime(rs.getTimestamp("transRecTime"));
				transRecVO.setTransState(rs.getInt("transState"));
				transRecVO.setMallName(rs.getString("mallName"));
				transRecVO.setOrderNo(rs.getInt("orderNo"));
				transRecVO.setTransCont(rs.getString("transCont"));

			}

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

		return transRecList;
	}

	@Override
	public Integer getDeposit(Integer memNo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Integer sum = null;

		try {
			con = DriverManager.getConnection(getUrl(), getUser(), getPassword());
			pstmt = con.prepareStatement(FIND_DEPOSIT);
			pstmt.setInt(1, memNo);
			long now = new Date().getTime();
			pstmt.setTimestamp(2, new Timestamp(now));
			rs = pstmt.executeQuery();

			while (rs.next()) {

				sum = rs.getInt("sum");
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return sum;
	}

	@Override
	// 建立錢包與訂單的連線
	public Integer addInTransaction(Connection con, TransRecVO transRec) throws SQLException {

		PreparedStatement pstmt = null;
		String[] col = { "TransRecNo" };
		Integer next_TransOrdNo = null;
		con.setAutoCommit(false);

		pstmt = con.prepareStatement(INSERT_STMT, col);

		pstmt.setInt(1, transRec.getMemNo());
		pstmt.setInt(2, transRec.getTransAmount());
		pstmt.setTimestamp(3, transRec.getTransRecTime());
		pstmt.setInt(4, transRec.getTransState());
		pstmt.setString(5, transRec.getMallName());
		pstmt.setObject(6, transRec.getOrderNo(), java.sql.Types.INTEGER);
		pstmt.setString(7, transRec.getTransCont());
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()) {
			next_TransOrdNo = rs.getInt(1);
		}
		rs.close();
		
		return next_TransOrdNo;
	}

	@Override
	public List<TransRecVO> findByOrderNo(Integer orderNo) {
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			List<TransRecVO> memOrdRecList = new ArrayList<TransRecVO>();

			try {
				con = DriverManager.getConnection(getUrl(), getUser(), getPassword());
				pstmt = con.prepareStatement(FIND_BY_orderNo);
				pstmt.setInt(1, orderNo);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					TransRecVO transRecVO = new TransRecVO();
					transRecVO.setTransRecNo(rs.getInt("transRecNo"));
					transRecVO.setMemNo(rs.getInt("memNo"));
					transRecVO.setTransAmount(rs.getInt("transAmount"));
					transRecVO.setTransRecTime(rs.getTimestamp("transRecTime"));
					transRecVO.setTransState(rs.getInt("transState"));
					transRecVO.setMallName(rs.getString("mallName"));
					transRecVO.setOrderNo(rs.getInt("orderNo"));
					transRecVO.setTransCont(rs.getString("transCont"));
					memOrdRecList.add(transRecVO);
				}

			} catch (SQLException se) {
				se.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (Exception e) {
						e.printStackTrace(System.err);
					}
				}
			}

			return memOrdRecList;
		}

}
