import java.util.ArrayList;
import java.util.Iterator;
/**
 * A class to hold details of audio tracks.
 * Individual tracks may be played.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class MusicOrganizer
{
    // An ArrayList for storing music tracks.
    private ArrayList<Track> tracks;
    // A player for the music tracks.
    private MusicPlayer player;
    // A reader that can read music files and load them as tracks.
    private TrackReader reader;
    //Atributo booleano para saber si la musica esta en reproduccion
    private boolean reproduc;

    /**
     * Create a MusicOrganizer
     */
    public MusicOrganizer()
    {
        tracks = new ArrayList<Track>();
        player = new MusicPlayer();
        reader = new TrackReader();
        reproduc = false;
        readLibrary("audio");
        System.out.println("Music library loaded. " + getNumberOfTracks() + " tracks.");
        System.out.println();
    }

    /**
     * Add a track file to the collection.
     * @param filename The file name of the track to be added.
     */
    public void addFile(String filename)
    {
        tracks.add(new Track(filename));
    }

    /**
     * Add a track to the collection.
     * @param track The track to be added.
     */
    public void addTrack(Track track)
    {
        tracks.add(track);
    }

    /**
     *Metodo para saber si hay algo en reproduccion
     */
    public void isPlaying()
    {
        if ( reproduc)
        {
            System.out.print("Se esta reproduciendo un track");
        }
        else{
            System.out.print("No hay nada en reproduccion");
        }
    }

    /**
     * Play a track in the collection.
     * @param index The index of the track to be played.
     */
    public void playTrack(int index, boolean  reproduc1)
    {
        if(indexValid(index)) {
            reproduc1=reproduc;
            isPlaying();
            reproduc=true;
            Track track = tracks.get(index);
            player.startPlaying(track.getFilename());
            System.out.println("Now playing: " + track.getArtist() + " - " + track.getTitle());
            track.cancionRepetidaPlayCount();

        }
    }

    /**
     * Return the number of tracks in the collection.
     * @return The number of tracks in the collection.
     */
    public int getNumberOfTracks()
    {
        return tracks.size();
    }

    /**
     * List a track from the collection.
     * @param index The index of the track to be listed.
     */
    public void listTrack(int index)
    {
        System.out.print("Track " + index + ": ");
        Track track = tracks.get(index);
        System.out.println(track.getDetails());
    }

    /**
     * Show a list of all the tracks in the collection.
     */
    public void listAllTracks()
    {
        System.out.println("Track listing: ");

        for(Track track : tracks) {
            System.out.println(track.getDetails());
        }
        System.out.println();
    }

    /**
     * List all tracks by the given artist.
     * @param artist The artist's name.
     */
    public void listByArtist(String artist)
    {
        for(Track track : tracks) {
            if(track.getArtist().contains(artist)) {
                System.out.println(track.getDetails());
            }
        }
    }

    /**
     * Remove a track from the collection.
     * @param index The index of the track to be removed.
     */
    public void removeTrack(int index)
    {
        if(indexValid(index)) {
            tracks.remove(index);
        }
    }

    /**
     * Play the first track in the collection, if there is one.
     */
    public void playFirst()
    {
        if(reproduc){
            System.out.println("Hay una reproduccion en curso no podemos poner una nueva");
        }
        else{
            if(tracks.size() > 0) {
                isPlaying();
                player.startPlaying(tracks.get(0).getFilename());
                tracks.get(0).cancionRepetidaPlayCount();
                reproduc = true;
                //Track track = tracks.get(0);
                //track.cancionRepetidaPlayCount();
            }
        }
    }

    /**
     * Stop the player.
     */
    public void stopPlaying()
    {
        player.stop();
        tracks.get(0).cancionRepetidaPlayCount();
        reproduc=false;
    }

    /**
     * Determine whether the given index is valid for the collection.
     * Print an error message if it is not.
     * @param index The index to be checked.
     * @return true if the index is valid, false otherwise.
     */
    private boolean indexValid(int index)
    {
        // The return value.
        // Set according to whether the index is valid or not.
        boolean valid;

        if(index < 0) {
            System.out.println("Index cannot be negative: " + index);
            valid = false;
        }
        else if(index >= tracks.size()) {
            System.out.println("Index is too large: " + index);
            valid = false;
        }
        else {
            valid = true;
        }
        return valid;
    }

    private void readLibrary(String folderName)
    {
        ArrayList<Track> tempTracks = reader.readTracks(folderName, ".mp3");

        // Put all thetracks into the organizer.
        for(Track track : tempTracks) {
            addTrack(track);
        }
    }

    /**
     *Metodo que muestra por pantalla la informaci�n de los tracks que contienen dicha cadena en el t�tulo de la canci�n.
     *
     */
    public void findInTitle(String file)
    {
        for(Track track : tracks) {
            if (track.getTitle().contains(file)){
                System.out.println(track.getDetails());//return new Track(artist, title, filename);
            }
        }   
    }

    /**
     *Metodo que muestra los detalles de todos los tracks almacenados.
     *
     */
    public void listAllTrackWithIterator()
    {
        Iterator<Track> iter = tracks.iterator();
        while(iter.hasNext())
        {
            Track trackIter = iter.next();
            System.out.println(trackIter.getDetails());
        }
    }

    /**
     *Metodo que permita eliminar del organizador los tracks que contengan un determinado artista
     *
     */
    public void removeByArtist(String artist)
    {
        Iterator<Track> iter = tracks.iterator();
        while(iter.hasNext())
        {
            Track trackIter = iter.next();
            if(trackIter.getArtist().contains(artist)){
                iter.remove();
            }
        }

    }

    /**
     *Metodo que permita eliminar del organizador los tracks que contengan un determinado titulo
     *
     */
    public void removeByTitle(String title)
    {
        Iterator<Track> iter = tracks.iterator();
        while(iter.hasNext())
        {
            Track trackIter = iter.next();
            if(trackIter.getTitle().contains(title)){
                iter.remove();
            }
        }

    }
}
