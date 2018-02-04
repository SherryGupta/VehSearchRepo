package com.mycar.utility;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.HttpHeaders;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaMetadataKeys;
import org.apache.tika.mime.MediaType;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.io.*;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;



public class FileUtility {
    private final static Logger logger = Logger.getLogger(FileUtility.class.getName());

    /**
     *
     * @param filename
     * @return file mime type
     * @throws IOException
     * this function is using apache tika library to get file mime type.
     */
    public static String getFileMime(String filename) throws IOException {
        logger.log(Level.INFO,"fileName is ---"+filename);

        File file = new File(filename);
        AutoDetectParser parser = new AutoDetectParser();
        parser.setParsers(new HashMap<MediaType, Parser>());
        Metadata metadata = new Metadata();
        metadata.add(TikaMetadataKeys.RESOURCE_NAME_KEY, file.getName());

        try (InputStream stream = new FileInputStream(file)){
            parser.parse(stream, new DefaultHandler(), metadata, new ParseContext());

        }
        catch (SAXException|TikaException e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
        String mimeType= metadata.get(HttpHeaders.CONTENT_TYPE);
        logger.log(Level.INFO,"file Mime Type is ---"+mimeType);
        return  mimeType;

    }
}
