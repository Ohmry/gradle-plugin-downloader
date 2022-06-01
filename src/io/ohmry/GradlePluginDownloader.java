package io.ohmry;

import io.ohmry.file.FileReader;
import io.ohmry.logger.Logger;
import io.ohmry.regex.RegexMatcher;
import io.ohmry.url.URLDownloader;
import io.ohmry.url.URLReader;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class GradlePluginDownloader {
    private static final String GRADLE_PLUGIN_URL = "https://plugins.gradle.org/plugin/";
    private static final String GRADLE_CLASSPATH_REGEX = "classpath\\(\\\"(.+?)\\\"\\)";
    private static final String GRADLE_REPOSITORY_REGEX = "uri\\(\\\"(.+?)\\\"\\)";
    private static final String GRADLE_REPOSITORY_FILE_REGEX = "a href=\\\"(.+?)\\\"";
    private static final String POM_FILE_REGEX = "(.+?).pom";
    private static final String DIRECTORY_NAME = "downloads";

    public static void main(String[] args) {
        // org.asciidoctor.convert:2.3.0
        if (args.length < 1) {
            Logger.error("Usage: java -jar GradleArtifactsDownloader ${artifactId:version}");
            return;
        }

        final String[] fileObject = args[0].split(":");
        boolean DEBUG_MODE = false;
        String downloadPath = "";

        for (int index = 1; index < args.length;) {
            String option = args[index];

            if (option.equals("--debug")) {
                DEBUG_MODE = true;
            } else if (option.equals("--download")) {
                index++;
                downloadPath = args[index];
            }
            index++;
        }

        String pluginName = fileObject[0];
        String pluginVersion = fileObject[1];

        Logger.info("Find plugin \"" + pluginName + "\", version \"" + pluginVersion + "\"");

        if (downloadPath.isEmpty()) {
            Path path = Paths.get("");
            downloadPath = path.toAbsolutePath() + "/" + DIRECTORY_NAME;
            Logger.info("Default download path: " + downloadPath);
            Logger.warn("You can change download path using option. \"--download ${downloadPath}\"");
        }

        try {
            if (DEBUG_MODE) {
                Logger.debug("Connect to " + GRADLE_PLUGIN_URL + pluginName + "/" + pluginVersion);
            }
            String contents = URLReader.read(GRADLE_PLUGIN_URL + pluginName + "/" + pluginVersion);
            String repositoryUrl = RegexMatcher.find(GRADLE_REPOSITORY_REGEX, contents);
            String classPath = RegexMatcher.find(GRADLE_CLASSPATH_REGEX, contents);

            if (DEBUG_MODE) {
                Logger.debug("Repository is \"" + repositoryUrl + "\"");
                Logger.debug("Classpath is \"" + classPath + "\"");
            }

            String[] dependencyInfo = classPath.split(":");
            String dependencyPath = dependencyInfo[0].replaceAll("[.]", "/") + "/" + dependencyInfo[1] + "/" + dependencyInfo[2];
            String downloadUrl = repositoryUrl + dependencyPath;

            Logger.info ("Download all contents from " + downloadUrl);

            contents = URLReader.read(downloadUrl);
            List<String> files = RegexMatcher.findAll(GRADLE_REPOSITORY_FILE_REGEX, contents);

            for (String file : files) {
                if (DEBUG_MODE) {
                    Logger.debug("File: " + file);
                }
                URLDownloader.download(downloadUrl, file, downloadPath + "/" + dependencyPath);

//                if (file.matches(POM_FILE_REGEX)) {
//                    Logger.debug(file + " is POM, retrieve all declared dependency in file.");
//                    List<String> dependencies = FileReader.readMatches(downloadPath + "/" + classDownloadPath + "/" + file, POM_FILE_REGEX);
//                    for (String dependency : dependencies) {
//                        Logger.info("Find appendix dependency \"" + dependency + "\"");
//                    }
//                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.error("Failed to download plugins");
            Logger.warn("If you want to see more information, using --debug");
            return;
        }

        Logger.info("Completed to download plugins!");
    }
}
