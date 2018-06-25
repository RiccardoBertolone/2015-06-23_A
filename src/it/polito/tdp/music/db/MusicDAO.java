package it.polito.tdp.music.db;

import it.polito.tdp.music.model.Artist;
import it.polito.tdp.music.model.ArtistNumber;
import it.polito.tdp.music.model.City;
import it.polito.tdp.music.model.Country;
import it.polito.tdp.music.model.Listening;
import it.polito.tdp.music.model.MieiArchi;
import it.polito.tdp.music.model.Track;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;

public class MusicDAO {
	
	public List<Country> getAllCountries() {
		final String sql = "SELECT id, country FROM country" ;
		
		List<Country> countries = new LinkedList<Country>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while( res.next() ) {
				countries.add( new Country(res.getInt("id"), res.getString("country"))) ;
			}
			
			conn.close() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		return countries ;
		
	}
	
	public List<City> getAllCities() {
		final String sql = "SELECT id, city FROM city" ;
		
		List<City> cities = new LinkedList<City>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while( res.next() ) {
				cities.add( new City(res.getInt("id"), res.getString("city"))) ;
			}
			
			conn.close() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		return cities ;
		
	}

	
	public List<Artist> getAllArtists() {
		final String sql = "SELECT id, artist FROM artist" ;
		
		List<Artist> artists = new LinkedList<Artist>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while( res.next() ) {
				artists.add( new Artist(res.getInt("id"), res.getString("artist"))) ;
			}
			
			conn.close() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		return artists ;
		
	}

	public List<Track> getAllTracks() {
		final String sql = "SELECT id, track FROM track" ;
		
		List<Track> tracks = new LinkedList<Track>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while( res.next() ) {
				tracks.add( new Track(res.getInt("id"), res.getString("track"))) ;
			}
			
			conn.close() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		return tracks ;
		
	}
	
	public List<Listening> getAllListenings() {
		final String sql = "SELECT id, userid, month, weekday, longitude, latitude, countryid, cityid, artistid, trackid FROM listening" ;
		
		List<Listening> listenings = new LinkedList<Listening>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while( res.next() ) {
				listenings.add( new Listening(res.getLong("id"), res.getLong("userid"), res.getInt("month"), res.getInt("weekday"),
						res.getDouble("longitude"), res.getDouble("latitude"), res.getInt("countryid"), res.getInt("cityid"),
						res.getInt("artistid"), res.getInt("trackid"))) ;
			}
			
			conn.close() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		return listenings ;
		
	}


	
	
	public static void main(String[] args) {
		MusicDAO dao = new MusicDAO() ;
		
		List<Country> countries = dao.getAllCountries() ;
		//System.out.println(countries) ;
		
		List<City> cities = dao.getAllCities() ;
		//System.out.println(cities) ;
		
		List<Artist> artists = dao.getAllArtists() ;
		
		List<Track> tracks = dao.getAllTracks() ;
		
		List<Listening> listenings = dao.getAllListenings() ;



		System.out.format("Loaded %d countries, %d cities, %d artists, %d tracks, %d listenings\n", 
				countries.size(), cities.size(), artists.size(), tracks.size(), listenings.size()) ;
	}

	public List<Month> getAllMesi() {
		final String sql = "SELECT Distinct month " + 
							"FROM listening " + 
							"ORDER BY month asc " ;
		
		List<Month> mesi = new LinkedList<>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while( res.next() ) {
				mesi.add(Month.of(res.getInt("month"))) ;
			}
			
			conn.close() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		return mesi ;
		
	}

	public List<ArtistNumber> getTop20Artisti(Integer mese) {
		final String sql = "SELECT a.id, a.artist, COUNT(*) as cnt " + 
					"FROM listening as l, artist as a " + 
					"Where l.artistid = a.id " + 
					"AND month = ? " + 
					"GROUP BY l.artistid " + 
					"Order by cnt desc " ;
		
		List<ArtistNumber> artists = new LinkedList<>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, mese);
			
			ResultSet res = st.executeQuery() ;
			int i=1;
			
			while( res.next() ) {
				Artist a = new Artist(res.getInt("a.id"), res.getString("a.artist")) ;
				artists.add(new ArtistNumber(a, res.getInt("cnt")) ) ;
				i++;
				if(i==21)
					break ;
			}
			
			conn.close() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		return artists ;
	}

	public List<Country> getPaesi(Artist artist, int mese) {
		final String sql = "SELECT DISTINCT c.id, c.country " + 
							"FROM listening as l, artist as a, country as c " + 
							"Where l.artistid = a.id " + 
							"AND c.id = l.countryid " + 
							"AND month = ? " + 
							"AND a.id = ? " ;
		
		List<Country> countries = new LinkedList<Country>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, mese);
			st.setInt(2, artist.getId());
			
			ResultSet res = st.executeQuery() ;
			
			while( res.next() ) {
				countries.add( new Country(res.getInt("id"), res.getString("country"))) ;
			}
			
			conn.close() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
		return countries ;
	}

	public List<MieiArchi> getMieiArchi(int mese) {
		final String sql = "SELECT l1.countryid, c1.country, l2.countryid, c2.country, COUNT(DISTINCT l1.artistid) as cnt " + 
							"FROM listening l1, listening l2, country as c1, country as c2 " + 
							"WHERE l1.month = ? " + 
							"AND l2.month = ? " + 
							"AND l1.countryid < l2.countryid " + 
							"AND l1.artistid = l2.artistid " + 
							"AND c1.id = l1.countryid " + 
							"AND c2.id = l2.countryid " + 
							"GROUP BY l1.countryid,l2.countryid " ;
		
		List<MieiArchi> archi = new LinkedList<>() ;
		
		try {
		Connection conn = DBConnect.getConnection() ;
		
		PreparedStatement st = conn.prepareStatement(sql) ;
		st.setInt(1, mese);
		st.setInt(1, mese);
		
		ResultSet res = st.executeQuery() ;
		
		while( res.next() ) {
			Country c1 = new Country(res.getInt("l1.countryid"), res.getString("c1.country")) ;
			Country c2 = new Country(res.getInt("l2.countryid"), res.getString("c2.country")) ;
			archi.add(new MieiArchi(c1, c2, res.getInt("cnt"))) ;
		}
		
		conn.close() ;
		} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null ;
		}
		
		return archi ;
	}

}
