package com.acabeza.mailer.model;

import java.util.List;

import com.wrapper.spotify.enums.AlbumType;
import com.wrapper.spotify.model_objects.specification.Image;

public class MyAlbum {

	private String id;
	private String name;
	private String artist;
	private AlbumType album_type;
	private String release_date;
	private String image;
	private String genres;
	private String[] prev_url;
	private List<Track> tracks;
	

	public List<Track> getTracks() {
		return tracks;
	}

	public void setTracks(List<Track> tracks) {
		this.tracks = tracks;
	}

	public String[] getPrev_url() {
		return prev_url;
	}

	public void setPrev_url(String[] prev_url) {
		this.prev_url = prev_url;
	}

	public MyAlbum() {}
	
	public MyAlbum(String id, String name, String artist, AlbumType album_type, String release_date, String image, String genres) {
		super();
		this.id = id;
		this.name = name;
		this.artist = artist;
		this.album_type = album_type;
		this.release_date = release_date;
		this.image = image;
		this.genres = genres;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public AlbumType getAlbum_type() {
		return album_type;
	}

	public void setAlbum_type(AlbumType album_type) {
		this.album_type = album_type;
	}

	public String getRelease_date() {
		return release_date;
	}

	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getGenres() {
		return genres;
	}

	public void setGenres(String genres) {
		this.genres = genres;
	}


	
	
}
