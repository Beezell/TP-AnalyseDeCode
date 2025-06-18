package com.simplecity.amp_library.model;

import java.util.ArrayList;

public class Suggestion {

    public AlbumArtist mostPlayedArtist;
    public Album mostPlayedAlbum;
    public Song mostPlayedSong;
    public ArrayList<Song> favouriteSongsOne = new ArrayList<>(3);
    public ArrayList<Song> favouriteSongsTwo = new ArrayList<>(3);
    public ArrayList<Album> recentlyPlayedAlbums = new ArrayList<>(4);
    public ArrayList<Album> recentlyAddedAlbumsOne = new ArrayList<>(2);
    public ArrayList<Album> recentlyAddedAlbumsTwo = new ArrayList<>(2);

    private Suggestion(Builder builder) {
        this.mostPlayedArtist = builder.mostPlayedArtist;
        this.mostPlayedAlbum = builder.mostPlayedAlbum;
        this.mostPlayedSong = builder.mostPlayedSong;
        this.favouriteSongsOne = builder.favouriteSongsOne;
        this.favouriteSongsTwo = builder.favouriteSongsTwo;
        this.recentlyPlayedAlbums = builder.recentlyPlayedAlbums;
        this.recentlyAddedAlbumsOne = builder.recentlyAddedAlbumsOne;
        this.recentlyAddedAlbumsTwo = builder.recentlyAddedAlbumsTwo;
    }

    public static class Builder {
        private AlbumArtist mostPlayedArtist;
        private Album mostPlayedAlbum;
        private Song mostPlayedSong;
        private ArrayList<Song> favouriteSongsOne = new ArrayList<>(3);
        private ArrayList<Song> favouriteSongsTwo = new ArrayList<>(3);
        private ArrayList<Album> recentlyPlayedAlbums = new ArrayList<>(4);
        private ArrayList<Album> recentlyAddedAlbumsOne = new ArrayList<>(2);
        private ArrayList<Album> recentlyAddedAlbumsTwo = new ArrayList<>(2);

        public Builder mostPlayedArtist(AlbumArtist mostPlayedArtist) {
            this.mostPlayedArtist = mostPlayedArtist;
            return this;
        }

        public Builder mostPlayedAlbum(Album mostPlayedAlbum) {
            this.mostPlayedAlbum = mostPlayedAlbum;
            return this;
        }

        public Builder mostPlayedSong(Song mostPlayedSong) {
            this.mostPlayedSong = mostPlayedSong;
            return this;
        }

        public Builder favouriteSongsOne(ArrayList<Song> favouriteSongsOne) {
            this.favouriteSongsOne = favouriteSongsOne;
            return this;
        }

        public Builder favouriteSongsTwo(ArrayList<Song> favouriteSongsTwo) {
            this.favouriteSongsTwo = favouriteSongsTwo;
            return this;
        }

        public Builder recentlyPlayedAlbums(ArrayList<Album> recentlyPlayedAlbums) {
            this.recentlyPlayedAlbums = recentlyPlayedAlbums;
            return this;
        }

        public Builder recentlyAddedAlbumsOne(ArrayList<Album> recentlyAddedAlbumsOne) {
            this.recentlyAddedAlbumsOne = recentlyAddedAlbumsOne;
            return this;
        }

        public Builder recentlyAddedAlbumsTwo(ArrayList<Album> recentlyAddedAlbumsTwo) {
            this.recentlyAddedAlbumsTwo = recentlyAddedAlbumsTwo;
            return this;
        }

        public Suggestion build() {
            return new Suggestion(this);
        }
    }

    @Override
    public String toString() {
        return "Suggestion{" +
                "mostPlayedArtist=" + mostPlayedArtist +
                ", mostPlayedAlbum=" + mostPlayedAlbum +
                ", mostPlayedSong=" + mostPlayedSong +
                ", favouriteSongsOne=" + favouriteSongsOne +
                ", favouriteSongsTwo=" + favouriteSongsTwo +
                ", recentlyPlayedAlbums=" + recentlyPlayedAlbums +
                ", recentlyAddedAlbumsOne=" + recentlyAddedAlbumsOne +
                ", recentlyAddedAlbumsTwo=" + recentlyAddedAlbumsTwo +
                '}';
    }
}
