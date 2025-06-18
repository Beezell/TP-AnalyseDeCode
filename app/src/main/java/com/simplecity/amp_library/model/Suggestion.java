package com.simplecity.amp_library.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Suggestion {

    private final AlbumArtist mostPlayedArtist;
    private final Album mostPlayedAlbum;
    private final Song mostPlayedSong;
    private final List<Song> favouriteSongsOne;
    private final List<Song> favouriteSongsTwo;
    private final List<Album> recentlyPlayedAlbums;
    private final List<Album> recentlyAddedAlbumsOne;
    private final List<Album> recentlyAddedAlbumsTwo;

    private Suggestion(Builder builder) {
        this.mostPlayedArtist = builder.mostPlayedArtist;
        this.mostPlayedAlbum = builder.mostPlayedAlbum;
        this.mostPlayedSong = builder.mostPlayedSong;
        this.favouriteSongsOne = Collections.unmodifiableList(new ArrayList<>(builder.favouriteSongsOne));
        this.favouriteSongsTwo = Collections.unmodifiableList(new ArrayList<>(builder.favouriteSongsTwo));
        this.recentlyPlayedAlbums = Collections.unmodifiableList(new ArrayList<>(builder.recentlyPlayedAlbums));
        this.recentlyAddedAlbumsOne = Collections.unmodifiableList(new ArrayList<>(builder.recentlyAddedAlbumsOne));
        this.recentlyAddedAlbumsTwo = Collections.unmodifiableList(new ArrayList<>(builder.recentlyAddedAlbumsTwo));
    }

    public AlbumArtist getMostPlayedArtist() {
        return mostPlayedArtist;
    }

    public Album getMostPlayedAlbum() {
        return mostPlayedAlbum;
    }

    public Song getMostPlayedSong() {
        return mostPlayedSong;
    }

    public List<Song> getFavouriteSongsOne() {
        return favouriteSongsOne;
    }

    public List<Song> getFavouriteSongsTwo() {
        return favouriteSongsTwo;
    }

    public List<Album> getRecentlyPlayedAlbums() {
        return recentlyPlayedAlbums;
    }

    public List<Album> getRecentlyAddedAlbumsOne() {
        return recentlyAddedAlbumsOne;
    }

    public List<Album> getRecentlyAddedAlbumsTwo() {
        return recentlyAddedAlbumsTwo;
    }

    public static class Builder {
        private AlbumArtist mostPlayedArtist;
        private Album mostPlayedAlbum;
        private Song mostPlayedSong;
        private List<Song> favouriteSongsOne = new ArrayList<>();
        private List<Song> favouriteSongsTwo = new ArrayList<>();
        private List<Album> recentlyPlayedAlbums = new ArrayList<>();
        private List<Album> recentlyAddedAlbumsOne = new ArrayList<>();
        private List<Album> recentlyAddedAlbumsTwo = new ArrayList<>();

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

        public Builder favouriteSongsOne(List<Song> favouriteSongsOne) {
            this.favouriteSongsOne = favouriteSongsOne == null ? new ArrayList<>() : new ArrayList<>(favouriteSongsOne);
            return this;
        }

        public Builder favouriteSongsTwo(List<Song> favouriteSongsTwo) {
            this.favouriteSongsTwo = favouriteSongsTwo == null ? new ArrayList<>() : new ArrayList<>(favouriteSongsTwo);
            return this;
        }

        public Builder recentlyPlayedAlbums(List<Album> recentlyPlayedAlbums) {
            this.recentlyPlayedAlbums = recentlyPlayedAlbums == null ? new ArrayList<>() : new ArrayList<>(recentlyPlayedAlbums);
            return this;
        }

        public Builder recentlyAddedAlbumsOne(List<Album> recentlyAddedAlbumsOne) {
            this.recentlyAddedAlbumsOne = recentlyAddedAlbumsOne == null ? new ArrayList<>() : new ArrayList<>(recentlyAddedAlbumsOne);
            return this;
        }

        public Builder recentlyAddedAlbumsTwo(List<Album> recentlyAddedAlbumsTwo) {
            this.recentlyAddedAlbumsTwo = recentlyAddedAlbumsTwo == null ? new ArrayList<>() : new ArrayList<>(recentlyAddedAlbumsTwo);
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
