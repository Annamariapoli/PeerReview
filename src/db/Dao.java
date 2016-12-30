package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import bean.Article;
import bean.Creator;

public class Dao {
	
	public boolean isPresente(int codiceAutore) throws SQLException{
		Connection conn = DBConnect.getConnection();
		String query = "select * from creator where id_creator=?";
		try{
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1,codiceAutore);
			ResultSet res = st.executeQuery();
			if(res.next()){
				return true;
			} else {return false;}
		}catch(SQLException e ){
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Article> articoliScrittiDaLui(int matricola) throws SQLException{
		Connection conn = DBConnect.getConnection();
		String query = "select a.eprintid, a.year, a.title from article a , authorship au  "
				+ "where a.eprintid= au.eprintid "
				+ "and au.id_creator=? order by a.year DESC";
		try{
			List<Article> articoli = new LinkedList<Article>();
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1,matricola);
			ResultSet res = st.executeQuery();
			while(res.next()){
				Article a = new Article (res.getLong("eprintid"), res.getInt("year"), res.getString("title"));
				articoli.add(a);
				}
			conn.close();
			return articoli;
		}catch(SQLException e ){
			e.printStackTrace();
			return null;
		}
	}
		
		
		public boolean isPresenteArticolo(long codiceArticolo) throws SQLException{
			Connection conn = DBConnect.getConnection();
			String query ="select * from article where eprintid=?";
			try{
				PreparedStatement st = conn.prepareStatement(query);
				st.setLong(1, codiceArticolo);
				ResultSet res = st.executeQuery();
				if(res.next()){
					return true;
				} else {return false;}
			}catch(SQLException e ){
				e.printStackTrace();
				return false;
			}
	}
	
	
	
	public List<Creator > getAllAutori() throws SQLException{
		Connection conn = DBConnect.getConnection();
		String query ="select * from creator";
		List<Creator> autori = new LinkedList<Creator>();
		try{
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet res = st.executeQuery();
			while(res.next()){
				Creator c = new Creator (res.getInt("id_creator"), res.getString("family_name"), res.getString("given_name"));
				autori.add(c);
			}
			conn.close();
			return autori;
		}catch(SQLException e ){
			e.printStackTrace();
			return null;
		}
	}
	
	public Map<Integer, Integer> coppieDiAutoriCheHannoScrittoAlmenoUnArticoloInsieme() throws SQLException{            //metto archi tra questi
		Connection conn = DBConnect.getConnection();
		String query =    "select a1.id_creator , a2.id_creator "
				          + "from authorship a1, authorship a2 "
				          + "where a1.eprintid=a2.eprintid      and        a1.id_creator<>a2.id_creator";
		Map<Integer, Integer> coppie = new TreeMap<>();
		try{
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet res = st.executeQuery();
			while(res.next()){
				coppie.put(res.getInt("id_creator"), res.getInt("id_creator"));
			}
			conn.close();
			return coppie;
		}catch(SQLException e ){
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	public List<Creator> autoriCheHannoCollaboratoConLui(int id_creator) throws SQLException{
		Connection conn = DBConnect.getConnection();
		String query = "select distinct c2.id_creator, c2.family_name, c2.given_name "
				+ "from creator c1, creator c2, authorship a1, authorship a2 "
				+ "where c1.id_creator<>c2.id_creator and "
				+ "c1.id_creator=? and c1.id_creator=a1.id_creator and  "
				+ "a2.id_creator=c2.id_creator and  "
				+ "a2.eprintid=a1.eprintid";
		List<Creator > autori = new LinkedList<Creator>();
		try{
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, id_creator);
			ResultSet res = st.executeQuery();
			while(res.next()){
				Creator c = new Creator (res.getInt("id_creator"), res.getString("family_name"), res.getString("given_name"));
				autori.add(c);
			}
			conn.close();
			return autori;
		}catch(SQLException e ){
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	public List<Creator> cercoAutoriCheHannoScrittoArticolo(long eprintid) throws SQLException{
		Connection conn = DBConnect.getConnection();
		String query = "select c.id_creator,c.family_name,c.given_name "
				+ "from creator c , authorship au "
				+ "where au.eprintid=? and c.id_creator=au.id_creator";
		List<Creator> aut = new LinkedList<Creator>();
		try{
			PreparedStatement st = conn.prepareStatement(query);
			st.setLong(1, eprintid);
			ResultSet res = st.executeQuery();
			while(res.next()){
				Creator c = new Creator(res.getInt("id_creator"), res.getString("family_name"), res.getString("given_name"));
				aut.add(c);
			
			}
			conn.close();
			return aut;
		}catch(SQLException e ){
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	public List<String> getArticoli(int idAutore) throws SQLException{   //ok
//		Connection conn =DBConnect.getConnection();
//		String query= "select title, a.eprintid from article a , authorship au where a.eprintid=au.eprintid and au.id_creator=? order by year DESC";
//		try{
//			List<String> questi = new LinkedList<String>();
//			PreparedStatement st = conn.prepareStatement (query);
//			st.setInt(1, idAutore);
//			ResultSet res = st.executeQuery();
//			while(res.next()){
//				questi.add("Titolo : " +res.getString("title")+ "    ID:  " +res.getInt("eprintid")+"\n");
//			}
//			conn.close();
//			return questi;		
//		}catch(SQLException e ){
//			e.printStackTrace();
//			return null;
//		}
//	} 
//	
//	public boolean isCodiceAutorePresente(int id_creator) throws SQLException{
//		Connection conn =DBConnect.getConnection();
//		String query= "select * from creator where id_creator=?;";
//		try{
//			PreparedStatement st = conn.prepareStatement (query);
//			st.setInt(1, id_creator);
//			ResultSet res = st.executeQuery();
//			while(res.next()){
//				return true;
//			}
//			st.close();
//			conn.close();
//		}catch(SQLException e ){
//			e.printStackTrace();
//			return false;
//		}
//		return false;
//	}
//	
//	
//	public List<Creator> getDammiAutoriDiArticolo(int id_articolo) throws SQLException{   //autori di quell'articolo
//		Connection conn =DBConnect.getConnection();
//		String query= "select c.id_creator, c.family_name, c.given_name from creator c,authorship au where au.eprintid=?and c.id_creator=au.id_creator";
//		try{
//			List<Creator> questi = new LinkedList<>();
//			PreparedStatement st = conn.prepareStatement (query);
//			st.setInt(1, id_articolo);
//			ResultSet res = st.executeQuery();
//			while(res.next()){
//				Creator a = new Creator(res.getInt("id_creator"), res.getString("family_name"), res.getString("given_name"));
//				questi.add(a);
//			}
//			conn.close();
//			return questi;		
//		}catch(SQLException e ){
//			e.printStackTrace();
//			return null;
//		}
//	}
//	
	//autori ke hanno almeno un articolo in comune:
//	select c1.id_creator, c2.id_creator
//	from creator c1, creator c2, authorship a1, authorship a2
//	where a1.id_creator=c1.id_creator and a2.id_creator=c2.id_creator
//	and a1.eprintid=a2.eprintid
//	and c1.id_creator<>c2.id_creator


}
