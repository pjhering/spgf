package org.petehering.spgf;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sound.midi.Sequence;
import javax.sound.sampled.Clip;
import static org.petehering.spgf.Utility.*;

public class Assets
{
    public Assets (String path)
    {
        try (InputStream stream = getClass ().getResourceAsStream(path))
        {
            InputStreamReader reader = new InputStreamReader (stream);
            BufferedReader buffer = new BufferedReader (reader);
            String line;
            
            while ((line = buffer.readLine()) != null)
            {
                String[] tokens = line.trim().split(SPACES);
                
                if (tokens.length > 0)
                {
                    switch (tokens[0])
                    {
                        case IMAGE:
                            parseImage (tokens);
                            break;
                        case BACKGROUND:
                            parseBackground (tokens);
                            break;
                        case TILESET:
                            parseTileset (tokens);
                            break;
                        case SPRITES:
                            parseSpritesheet (tokens);
                            break;
                        case TILES:
                            parseTileLayer (tokens);
                            break;
                        case ACTOR:
                            parseActor (tokens);
                            break;
                        case CLIP:
                            parseClip (tokens);
                            break;
                        case MIDI:
                            parseMidi (tokens);
                            break;
                        case TEXT:
                            parseText (tokens);
                            break;
                        case FONT:
                            parseFont (tokens);
                            break;
                        case COLOR:
                            parseColor (tokens);
                            break;
                    }
                }
            }
        }
        catch (Exception ex)
        {
            throw new RuntimeException (ex);
        }
    }

    private Map<String, BufferedImage> images = new HashMap<>();
    
    public BufferedImage getImage (String path)
    {
        return images.get(path);
    }
    
    private void parseImage(String[] tokens)//TODO
    {
    }

    private Map<String, Background> backgrounds = new HashMap<>();
    
    public Background getBackground (String path)//TODO
    {
        return backgrounds.get (path);
    }
    
    private void parseBackground(String[] tokens)//TODO
    {
    }
    
    private Map<String, Tileset> tilesets = new HashMap<>();
    
    public Tileset getTileset (String path)
    {
        return tilesets.get(path);
    }

    private void parseTileset(String[] tokens)//TODO
    {
    }
    
    private Map<String, Spritesheet> sprites = new HashMap<>();
    
    public Spritesheet getSprites (String path)
    {
        return sprites.get(path);
    }

    private void parseSpritesheet(String[] tokens)//TODO
    {
    }
    
    private Map<String, TileLayer> tiles = new HashMap<>();
    
    public TileLayer getTiles (String path)
    {
        return tiles.get(path);
    }

    private void parseTileLayer(String[] tokens)//TODO
    {
    }
    
    private Map<String, List<Actor>> actors = new HashMap<>();
    
    public List<Actor> getActors (String name)
    {
        return actors.get(name);
    }

    private void parseActor(String[] tokens)//TODO
    {
    }
    
    private Map<String, Clip> clips = new HashMap<>();
    
    public Clip getClip (String path)
    {
        return clips.get(path);
    }

    private void parseClip(String[] tokens)//TODO
    {
    }
    
    private Map<String, Sequence> midis = new HashMap<>();
    
    public Sequence getMidi (String path)
    {
        return midis.get(path);
    }

    private void parseMidi(String[] tokens)//TODO
    {
    }
    
    private Map<String, String> texts = new HashMap<>();
    
    public String getText (String name)
    {
        return texts.get(name);
    }

    private void parseText(String[] tokens)//TODO
    {
    }
    
    private Map<String, Font> fonts = new HashMap<>();
    
    public Font getFont (String name)
    {
        return fonts.get(name);
    }

    private void parseFont(String[] tokens)//TODO
    {
    }

    private Map<String, Color> colors = new HashMap<>();
    
    public Color getColor (String name)
    {
        return colors.get(name);
    }
    
    private void parseColor(String[] tokens)//TODO
    {
    }
}
