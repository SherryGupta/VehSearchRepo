package com.mycar;


import com.mycar.model.FileVO;
import com.mycar.service.FileServiceImpl;
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


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;

import java.util.HashMap;
import java.util.Set;

public class MyCarMain {

    public static void main(String[] args)  {

        FileServiceImpl fileService;
        fileService = new FileServiceImpl();
        try {
            Set<FileVO> set = fileService.filterFileonMimeType("files");
            for(FileVO file: set)
            {
                System.out.println(set.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


//      try(DirectoryStream<Path> files = (Files.newDirectoryStream(Paths.get(getClass().getClassLoader()
//              .getResource("resources").toURI())))     )
//      {
//          for (Path file: files) {
//                 System.out.println(file.getFileName());
//              }
//
//          DirectoryStream<Path> files1 = (Files.newDirectoryStream(Paths.get("."),path -> path.toString().endsWith(".csv")));
//          for (Path file: files1) {
//              System.out.println(file.getFileName());
//          }
//
//          DirectoryStream<Path> files2 = (Files.newDirectoryStream(Paths.get(".")));
//          for (Path file: files2) {
//              System.out.println("-------"+file.getFileName());
//          }
//
//          DirectoryStream<Path> files3 = (Files.newDirectoryStream(Paths.get(".")));
//          for (Path file: files3) {
//              //identifyFileTypeUsingUrlConnectionGetContentType(file.toFile().getAbsolutePath());
//              MimetypesFileTypeMap mapper=new MimetypesFileTypeMap();
//              //System.out.println("---"+ mapper.getContentType(file.toFile()));
//              apacheTikka(file.toFile().getAbsolutePath());
//          }
//
//
//

//              files.
//          Files.newDirectoryStream(Paths.get("."),
//                  path -> path.toString().endsWith(".java"))
//                  .forEach(System.out::println);



//          try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
//              for (Path file: stream) {
//                  System.out.println(file.getFileName());
//              }
//          } catch (IOException | DirectoryIteratorException x) {
//              // IOException can never be thrown by the iteration.
//              // In this snippet, it can only be thrown by newDirectoryStream.
//              System.err.println(x);
//          }


          //    files.forEach(s-> System.out.println(s));
//      } catch (IOException e) {
//          e.printStackTrace();
//      }

    }

//    public static String getMIMEType( String strFilename )
//    {
//        try
//        {
//            MimetypesFileTypeMap mimeTypeMap = new MimetypesFileTypeMap( AppPathService.getWebAppPath(  ) +
//                    File.separator + FILE_MIME_TYPE );
//
//            return mimeTypeMap.getContentType( strFilename.toLowerCase(  ) );
//        }
//        catch ( IOException e )
//        {
//            AppLogService.error( e );
//
//            return DEFAULT_MIME_TYPE;
//        }
//    }




    public static String identifyFileTypeUsingUrlConnectionGetContentType(final String fileName)
    {
        String fileType = "Undetermined";
        try
        {
            final URL url = new URL("file://" + fileName);
            System.out.println("url: - " + url.toString());
            final URLConnection connection = url.openConnection();
            fileType = connection.getContentType();
            System.out.println("fileType: - " + fileType);
        }
        catch (MalformedURLException badUrlEx)
        {
            System.out.println("ERROR: Bad URL - " + badUrlEx);
        }
        catch (IOException ioEx)
        {
            System.out.println("Cannot access URLConnection - " + ioEx);
        }
        return fileType;
    }


    public static String identifyFileTypeUsingUrlConnectionGuessContentTypeFromName(final String fileName)
    {
        return URLConnection.guessContentTypeFromName(fileName);
    }

    public static String identifyFileTypeUsingFilesProbeContentType(final String fileName)
    {
        String fileType = "Undetermined";
        final File file = new File(fileName);
        try
        {
            fileType = Files.probeContentType(file.toPath());
        }
        catch (IOException ioException)
        {
            System.out.println(
                    "ERROR: Unable to determine file type for " + fileName
                            + " due to exception " + ioException);
        }
        return fileType;
    }



    public static void apacheTikka(final String filename) {
        File file = new File(filename);
        AutoDetectParser parser = new AutoDetectParser();
        parser.setParsers(new HashMap<MediaType, Parser>());
        Metadata metadata = new Metadata();
        metadata.add(TikaMetadataKeys.RESOURCE_NAME_KEY, file.getName());
        InputStream stream;
        try {
            stream = new FileInputStream(file);
            parser.parse(stream, new DefaultHandler(), metadata, new ParseContext());
            stream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TikaException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String mimeType = metadata.get(HttpHeaders.CONTENT_TYPE);
        System.out.println(mimeType);

    }



    }


