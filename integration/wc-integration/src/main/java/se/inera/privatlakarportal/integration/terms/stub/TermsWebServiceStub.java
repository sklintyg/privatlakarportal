package se.inera.privatlakarportal.integration.terms.stub;

import org.apache.commons.io.FileUtils;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import se.riv.infrastructure.directory.privatepractitioner.getprivatepractitionerterms.v1.rivtabp21.GetPrivatePractitionerTermsResponderInterface;
import se.riv.infrastructure.directory.privatepractitioner.getprivatepractitionertermsresponder.v1.GetPrivatePractitionerTermsResponseType;
import se.riv.infrastructure.directory.privatepractitioner.getprivatepractitionertermsresponder.v1.GetPrivatePractitionerTermsType;
import se.riv.infrastructure.directory.privatepractitioner.terms.v1.AvtalType;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by pebe on 2015-08-25.
 */
public class TermsWebServiceStub implements GetPrivatePractitionerTermsResponderInterface {

    private static final Logger LOG = LoggerFactory.getLogger(TermsWebServiceStub.class);

    @Autowired
    private Environment env;

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public GetPrivatePractitionerTermsResponseType getPrivatePractitionerTerms(String s, GetPrivatePractitionerTermsType getPrivatePractitionerTermsType) {

        List<File> list = getFiles(System.getProperty("java.class.path"));
        for (File file: list) {
            LOG.debug(file.getPath());
        }

        AvtalType avtalType = new AvtalType();
        avtalType.setAvtalVersion(1);
        avtalType.setAvtalVersionDatum(LocalDateTime.parse("2015-09-30"));

        String fileEncoding = "UTF-8";
        String fileUrl = "classpath:bootstrap-webcertvillkor/webcertvillkor.html";

        LOG.debug("Loading terms file '{}' using encoding '{}'", fileUrl, fileEncoding);

        String avtalText;
        try {
            Resource resource = resourceLoader.getResource(fileUrl);

            if (!resource.exists()) {
                LOG.error("Could not load avtal file since the resource '{}' does not exist", fileUrl);
            } else {
                avtalText = FileUtils.readFileToString(resource.getFile());
                avtalType.setAvtalText(avtalText);
            }

        } catch (IOException ioe) {
            LOG.error("IOException occured when loading avtal file '{}'", fileUrl);
            throw new RuntimeException("Error occured when loading avtal file", ioe);
        }

        GetPrivatePractitionerTermsResponseType response = new GetPrivatePractitionerTermsResponseType();
        response.setAvtal(avtalType);

        return response;
    }

    /**
     * list files in the given directory and subdirs (with recursion)
     * @param paths
     * @return
     */
    public static List<File> getFiles(String paths) {
        List<File> filesList = new ArrayList<File>();
        for (final String path : paths.split(File.pathSeparator)) {
            final File file = new File(path);
            if( file.isDirectory()) {
                recurse(filesList, file);
            }
            else {
                filesList.add(file);
            }
        }
        return filesList;
    }

    private static void recurse(List<File> filesList, File f) {
        File list[] = f.listFiles();
        for (File file : list) {
            if (file.isDirectory()) {
                recurse(filesList, file);
            }
            else {
                filesList.add(file);
            }
        }
    }

    /**
     * List directory contents for a resource folder. Not recursive.
     * This is basically a brute-force implementation.
     * Works for regular files and also JARs.
     *
     * @author Greg Briggs
     * @param clazz Any java class that lives in the same place as the resources you want.
     * @param path Should end with "/", but not start with one.
     * @return Just the name of each member item, not the full paths.
     * @throws URISyntaxException
     * @throws IOException
     */
    String[] getResourceListing(Class clazz, String path) throws URISyntaxException, IOException {
        URL dirURL = clazz.getClassLoader().getResource(path);
        if (dirURL != null && dirURL.getProtocol().equals("file")) {
        /* A file path: easy enough */
            return new File(dirURL.toURI()).list();
        }

        if (dirURL == null) {
        /*
         * In case of a jar file, we can't actually find a directory.
         * Have to assume the same jar as clazz.
         */
            String me = clazz.getName().replace(".", "/") + ".class";
            dirURL = clazz.getClassLoader().getResource(me);
        }

        if (dirURL.getProtocol().equals("jar")) {
        /* A JAR path */
            String jarPath = dirURL.getPath().substring(5, dirURL.getPath().indexOf("!")); //strip out only the JAR file
            JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
            Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries in jar
            Set<String> result = new HashSet<String>(); //avoid duplicates in case it is a subdirectory
            while(entries.hasMoreElements()) {
                String name = entries.nextElement().getName();
                if (name.startsWith(path)) { //filter according to the path
                    String entry = name.substring(path.length());
                    int checkSubdir = entry.indexOf("/");
                    if (checkSubdir >= 0) {
                        // if it is a subdirectory, we just return the directory name
                        entry = entry.substring(0, checkSubdir);
                    }
                    result.add(entry);
                }
            }
            return result.toArray(new String[result.size()]);
        }

        throw new UnsupportedOperationException("Cannot list files for URL " + dirURL);
    }

}
